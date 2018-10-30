package cn.plou.web.common.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import cn.plou.web.system.commonMessage.pageGrid.entity.PageGrid;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.plou.common.utils.DateUtil;
import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.system.commonMessage.pageGrid.service.PageGridService;
import cn.plou.web.system.commonMessage.pageGrid.vo.PageGridInfo;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CommonServiceImp {
	@Autowired
	private PageGridService pageGridService;
	
	/**
	 * 导出pageNo页面的main列表数据，默认只导出显示项（grid的hide属性为false的列）
	 * @param request
	 * @param pageNo
	 * @param desc
	 * @param datasT
	 * @return
	 */
	public<T> String getHisDataToExcel(ServletRequest request, String pageNo,String desc, List<T> datasT) {
		return getHisDataByGridNameToExcel(request, pageNo, "main", desc, datasT, true);
	}

	/**
	 * 导出pageNo页面的gridName列表数据
	 * @param request 请求
	 * @param pageNo 页面
	 * @param gridName 列表
	 * @param desc 导出的报表名
	 * @param datasT 数据
	 * @param isShowValid 是否显示隐藏字段
	 * @return
	 */
	public<T> String getHisDataByGridNameToExcel(ServletRequest request, String pageNo,String gridName,String desc, List<T> datasT, Boolean isShowValid) {
		String path = desc + DateUtil.getSysdateTimeString().replace(" ", "").replace(":", "").replace("-", "") + ".xlsx";
		String filePath = request.getServletContext().getRealPath("/") + path;// 文件路径
		System.out.println("路径：" + filePath);
		XSSFWorkbook workbook = new XSSFWorkbook();// 创建Excel文件(Workbook)
		XSSFSheet sheet = workbook.createSheet(desc);// 创建工作表(Sheet)
		XSSFRow row = sheet.createRow(0);// 创建行,从0开始
		XSSFCell cell = row.createCell(0);// 创建行的单元格,也是从0开始
		if (!getPageHisHeadByGridName(cell, row, gridName, pageNo,isShowValid)) {
			throw new BusinessException("未找到要到出的列名，请检查配置！");
		}

		List<PageGridInfo> infos = pageGridService.getPageGridByGridName("asc", "colsort", gridName,pageNo);
		for (int i = 0; i < datasT.size(); i++) {
			row = sheet.createRow(i + 1);// 创建行,从0开始
			cell = row.createCell(0);// 创建行的单元格,也是从0开始
			setObjectToCell(row, cell, datasT.get(i), infos,isShowValid);
		}
		try {
			FileOutputStream out = new FileOutputStream(filePath);
			workbook.write(out);// 保存Excel文件
			out.close();// 关闭文件流
			System.out.println("导出成功!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

	private Boolean getPageHisHeadByGridName(XSSFCell cell, XSSFRow row, String gridName,String pageNo, Boolean isShowValid) {
		List<PageGridInfo> infos = pageGridService.getPageGridByGridName("asc", "colsort", gridName,pageNo);
		if (infos.size() == 0) {
			return false;
		}
		int index = 0;
		Boolean flag = false;

		for (int i = 0; i < infos.size(); i++) {
			PageGrid info = infos.get(i);
			if (isShowValid) {
				if ("true".equals(info.getHide())) {
					continue;
				}
			}
			if (!flag) {
				cell.setCellValue(infos.get(index).getDisplay());// 设置单元格内容
				flag = true;
			} else {
				row.createCell(index).setCellValue(info.getDisplay());
			}
			index++;
		}
		return index > 0;
	}
	
	private <T> Boolean setObjectToCell(XSSFRow row, XSSFCell cell, T DataDO, List<PageGridInfo> infos, Boolean isShowValid) {
		if (infos.size() == 0) {
			return false;
		}
		int index = 0;
		for (int i = 0; i < infos.size(); i++) {
			PageGridInfo info  = infos.get(i);
			if (isShowValid) {
				if ("true".equals(info.getHide())) {
					continue;
				}
			}
			String gridName = info.getName();
			Object objt = null;
			objt = Tools.getObjValue(DataDO, gridName);
			if (objt != null) {
				cell.setCellValue(objt.toString());
			} else {
				cell.setCellValue("");
			}
			index++;
			break;
		}

		for (int i = index; i < infos.size(); i++) {
			PageGridInfo info  = infos.get(i);
			if (isShowValid) {
				if ("true".equals(info.getHide())) {
					continue;
				}
			}
			String gridName = info.getName();
			Object objt = null;
			objt = Tools.getObjValue(DataDO, gridName);
			if (objt != null) {
				row.createCell(index).setCellValue(objt.toString());
			} else {
				row.createCell(index).setCellValue("");
			}
			index++;
		}
		return index > 0;
	}
	/**
	 * 导出pageNo页面的gridName列表数据（json数据）
	 * @param request 请求
	 * @param pageNo 页面
	 * @param gridName 列表
	 * @param desc 导出的报表名
	 * @param datasT 数据
	 * @param isShowValid 是否显示隐藏字段
	 * @return
	 */
	public String getHisJSONArrayToExcel(ServletRequest request, String pageNo,String gridName,String desc, JSONArray array,Boolean isShowValid) {
		String path = desc + DateUtil.getSysdateTimeString().replace(" ", "").replace(":", "").replace("-", "") + ".xls";
		String filePath = request.getServletContext().getRealPath("/") + path;// 文件路径
		System.out.println("路径：" + filePath);
		XSSFWorkbook workbook = new XSSFWorkbook();// 创建Excel文件(Workbook)
		XSSFSheet sheet = workbook.createSheet(desc);// 创建工作表(Sheet)
		XSSFRow row = sheet.createRow(0);// 创建行,从0开始
		XSSFCell cell = row.createCell(0);// 创建行的单元格,也是从0开始
		if (!getPageHisHeadByGridName(cell, row, gridName, pageNo,isShowValid)) {
			throw new BusinessException("未找到要到出的列名，请检查配置！");
		}

		List<PageGridInfo> infos = pageGridService.getPageGridByGridName("asc", "colsort", gridName,pageNo);
		for (int i = 0; i < array.size(); i++) {
			row = sheet.createRow(i + 1);// 创建行,从0开始
			cell = row.createCell(0);// 创建行的单元格,也是从0开始
			setJSONObjToCell(row, cell, (JSONObject)(array.get(i)), infos);
		}
		try {
			FileOutputStream out = new FileOutputStream(filePath);
			workbook.write(out);// 保存Excel文件
			out.close();// 关闭文件流
			System.out.println("导出成功!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	
	/**
	 * 导出pageNo页面的gridName列表数据（json数据）
	 * @param request 请求
	 * @param desc 导出的报表名
	 * @param datasT 数据
	 * @param excelNames 列名<k,V> <对应json的列名，导出的名称>
	 * @return
	 */
	public String getHisJSONArrayToExcelByNameList(ServletRequest request, String desc, JSONArray array, Map<String,String> excelNames) {
		String path = desc + DateUtil.getSysdateTimeString().replace(" ", "").replace(":", "").replace("-", "") + ".xls";
		String filePath = request.getServletContext().getRealPath("/") + path;// 文件路径
		System.out.println("路径：" + filePath);
		XSSFWorkbook workbook = new XSSFWorkbook();// 创建Excel文件(Workbook)
		XSSFSheet sheet = workbook.createSheet(desc);// 创建工作表(Sheet)
		XSSFRow row = sheet.createRow(0);// 创建行,从0开始
		XSSFCell cell = row.createCell(0);// 创建行的单元格,也是从0开始
		if (!getPageHisHeadByGridNameByExcelNames(cell, row, excelNames)) {
			throw new BusinessException("未找到要到出的列名，请检查配置！");
		}

		for (int i = 0; i < array.size(); i++) {
			row = sheet.createRow(i + 1);// 创建行,从0开始
			cell = row.createCell(0);// 创建行的单元格,也是从0开始
			setJSONObjToCellByExcelNames(row, cell, (JSONObject)(array.get(i)), excelNames);
		}
		try {
			FileOutputStream out = new FileOutputStream(filePath);
			workbook.write(out);// 保存Excel文件
			out.close();// 关闭文件流
			System.out.println("导出成功!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	
	private boolean getPageHisHeadByGridNameByExcelNames(XSSFCell cell, XSSFRow row, Map<String,String> excelNames) {
		if (excelNames.size() == 0) {
			return false;
		}
		for (String gridName:excelNames.values()) {	
				cell.setCellValue(gridName);// 设置单元格内容
		}
		return true;
	}

	private Boolean setJSONObjToCellByExcelNames(XSSFRow row, XSSFCell cell, JSONObject object, Map<String,String> excelNames) {
		if (excelNames.size() == 0) {
			return false;
		}
		int index = 0;
		for (String gridName:excelNames.keySet()) {	
				Object objt = null;	
				objt = object.get(gridName);
				if (objt != null) {
					cell.setCellValue(objt.toString());
				}else{
					cell.setCellValue("");
				}
				index++;
			}
		return index > 0;
	}

	private Boolean setJSONObjToCell(XSSFRow row, XSSFCell cell, JSONObject object, List<PageGridInfo> infos) {
		if (infos.size() == 0) {
			return false;
		}
		int index = 0;
		for (int i = 0; i < infos.size(); i++) {
			PageGridInfo info  = infos.get(i);
			if ("false".equals(info.getHide())) {
				String gridName = info.getName();
				Object objt = null;	
				objt = object.get(gridName);
				if (objt != null) {
					cell.setCellValue(objt.toString());
				}else{
					cell.setCellValue("");
				}
				index++;
				break;
			}
		}

		for (int i = index; i < infos.size(); i++) {
			PageGridInfo info  = infos.get(i);
			if ("false".equals(info.getHide())) {
				String gridName = info.getName();
				Object objt = null;
				objt = object.get(gridName);
				if (objt != null) {
					row.createCell(index).setCellValue(objt.toString());
				}else{
					row.createCell(index).setCellValue("");
				}
				index++;
			}
		}
		return index > 0;
	}
}
