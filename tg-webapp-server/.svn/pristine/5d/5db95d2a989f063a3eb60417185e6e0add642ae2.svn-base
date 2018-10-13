package cn.plou.web.common.utils;

import static cn.plou.web.common.utils.ExcelTools.getCellValue;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.common.importDataBatch.ErrorInfo;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import lombok.Data;

@Data
public class ExcelTools {
	public static void addErrorInfo(List<ErrorInfo> errorInfos, int i, int j, String string, boolean b, Row row,
			CellStyle style) {
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setPosition("第" + (i + 1) + "行，第" + (j + 1) + "列");
		errorInfo.setErrorType(string);
		errorInfo.setRequired(b);
		errorInfos.add(errorInfo);
		try {
			row.getCell(j).setCellStyle(style);
		} catch (Exception e) {
		}
	}

	public static String getTypeMst(List<TypeMst> typeMstList, String typeKbn, int j, Row row, Boolean isCheckNull) {
		String strName = "";
		if (getCellValue(row.getCell(j)).length() != 0) {
			try {
				strName = getCellValue(row.getCell(j));
				return getTypeIdByName(typeMstList, typeKbn, strName,isCheckNull);
			} catch (Exception e) {
				System.out.println("---------ExcelTools:" + e.getMessage());
				return "";
			}
		} else {
			return "";
		}

	}

	public static String getTypeIdByName(List<TypeMst> types, String typeKbn, String typeName, Boolean isCheckNull) {
		for (TypeMst type : types) {
			if (type.getTypeName().equals(typeName) && type.getTypeKbn().equals(typeKbn)) {
				return type.getId();
			}
		}
		if(isCheckNull){
			return "";
		}
		return typeName;
	}

	/**
	 * 获取Cell内容
	 */
	public static String getCellValue(Cell cell) {
		String value = "";
		if (cell != null) {
			// 判断数据的类型
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC:
				value = cell.getNumericCellValue() + "";// 数字
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					if (date != null) {
						value = new SimpleDateFormat("yyyy-MM-dd").format(date);
					} else {
						value = "";
					}
				} else {
					value = new DecimalFormat("0").format(cell.getNumericCellValue());
				}
				break;
			case HSSFCell.CELL_TYPE_STRING: // 字符串
				value = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
				value = cell.getBooleanCellValue() + "";
				break;
			case HSSFCell.CELL_TYPE_FORMULA: // 公式
				value = cell.getCellFormula() + "";
				break;
			case HSSFCell.CELL_TYPE_BLANK: // 空值
				value = "";
				break;
			case HSSFCell.CELL_TYPE_ERROR: // 故障
				value = "非法字符";
				break;
			default:
				value = "未知类型";
				break;
			}
		}
		return value.trim();
	}

	// 处理公式
	public static String getCellValueFormula(Cell cell, FormulaEvaluator formulaEvaluator) {
		if (cell == null || formulaEvaluator == null) {
			return null;
		}

		if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
			return String.valueOf(formulaEvaluator.evaluate(cell).getNumberValue());
		}
		return getCellValue(cell);
	}

	public Boolean isCellVolid(Row row, int num, List<ErrorInfo> errorInfos, CellStyle style) {
		Boolean flag = true;
		if (getCellValue(row.getCell(0)).trim().length() == 0) {
			flag = false;
		}

		for (int i = 1; i < num; i++) {
			if (getCellValue(row.getCell(i)).trim().length() != 0) {
				if (!flag) {
					ErrorInfo errorInfo = new ErrorInfo();
					errorInfo.setPosition("第" + (1) + "行，第1列");
					errorInfo.setErrorName("公司名称");
					errorInfo.setErrorType("公司名为空");
					errorInfo.setRequired(true);
					errorInfos.add(errorInfo);
					try {
						row.getCell(0).setCellStyle(style);
					} catch (Exception e) {
					}
				}
				return true;
			}
		}
		return false;
	}

	int rows = 0;
	CellStyle style = null;
	Workbook workbook = null;
	FormulaEvaluator formulaEvaluator = null;
	Boolean isSpeed = false;

	public Sheet getFile(MultipartFile multipartFile, String sheetName) {
		String fileName = multipartFile.getOriginalFilename();
		if (fileName.endsWith("xls")) {
			try {
				workbook = new HSSFWorkbook(multipartFile.getInputStream());
				style = (HSSFCellStyle) workbook.createCellStyle();
				style.setFillForegroundColor(HSSFColor.RED.index);
				formulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
				// style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (fileName.endsWith("xlsx")) {
			try {
				workbook = new XSSFWorkbook(multipartFile.getInputStream());
				style = ((XSSFWorkbook) workbook).createCellStyle();
				style.setFillForegroundColor(IndexedColors.RED.getIndex());
				((XSSFCellStyle) style).setFillPattern(CellStyle.SOLID_FOREGROUND);
				formulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			throw new BusinessException("文件不是Excel文件");
		}
		Sheet sheet = workbook.getSheet("快速" + sheetName);
		if (sheet == null) {
			sheet = workbook.getSheet(sheetName);
			isSpeed = false;
			if (sheet == null) {
				throw new BusinessException("未找到名为”" + sheetName + "“的sheet页，请检查模板");
			}
		} else {
			isSpeed = true;
		}
		rows = sheet.getLastRowNum();
		if (rows == 0) {
			throw new BusinessException("请填写数据");
		}
		return sheet;
	}

	public static Boolean checkRep(Map<String, List<String>> houseReCheck, String key, String value) {
		if(houseReCheck.containsKey(key)){
			List<String> list = houseReCheck.get(key);
			for(String val:list){
				if(val.equals(value)){
					return true;
				}
			}
			list.add(value);
		}else{
			List<String> list = new ArrayList<>();
			list.add(value);
			houseReCheck.put(key, list);
		}
		return false;
	}
}
