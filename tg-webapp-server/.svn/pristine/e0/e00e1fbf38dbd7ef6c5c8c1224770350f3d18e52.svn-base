package cn.plou.web.common.utils;

import cn.plou.web.common.annotation.ExportExcel;
import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.common.utils.a1.DateUtil;
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.ServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * @ClassName: ExportExcelUtil
 * @Description: 导出 excel
 * @Author: youbc
 * @Date 2018-10-24 08:51
 */
public class ExportExcelUtil {

    @Data
    private static class Person {
        private String name;
        private int age;
        private Date accountTime;
        private BigDecimal ddd;

    }


    public static void main(String[] args) throws Exception {
        Person person = new Person();
        person.setAge(11);
        person.setName("旺旺");
        person.setAccountTime(new Date());
        person.setDdd(new BigDecimal(22.6));
        String field = "ddd";
        String ob = getGetMethod(person, field, Person.class.getDeclaredField(field).getType());
        System.out.println(ob);
    }

    /**
     * 根据属性，获取get方法
     *
     * @param ob        对象
     * @param name      属性名
     * @param typeClass 属性类型
     * @return 属性值
     * @throws Exception aM.invoke(ob) 异常
     */
    private static String getGetMethod(Object ob, String name, Class<?> typeClass) throws Exception {
        Method[] m = ob.getClass().getMethods();
        for (Method aM : m) {
            if (("get" + name).toLowerCase().equals(aM.getName().toLowerCase())) {
                return changeToString(aM.invoke(ob), typeClass);
            }
        }
        return "";
    }

    private static String changeToString(Object value, Class<?> typeClass) {
        if (value == null) {
            return "";
        }
        if (typeClass == int.class || typeClass == Integer.class) {
            return String.valueOf(value);
        } else if (typeClass == long.class || typeClass == Long.class) {
            return String.valueOf(value);
        } else if (typeClass == String.class) {
            return (String) value;
        } else if (typeClass == boolean.class || typeClass == Boolean.class) {
            return String.valueOf(value);
        } else if (typeClass == BigDecimal.class) {
            return value.toString();
        } else if (typeClass == Date.class) {
            return DateUtil.toDateTimeString((Date) value);
        } else {
            return value.toString();
        }
    }

    @Data
    private static class ExcelCell {
        private int index;
        private String name;
        private String typeName;
        private Class<?> typeClass;
    }


    public static <T> String ExportList(ServletRequest request, List<T> list, String fileName) {
        if (list == null || list.size() == 0) {
            throw new BusinessException("没有数据");
        }

        String path1 = fileName + cn.plou.common.utils.DateUtil.getSysdateTimeString().replace(" ", "").replace(":", "").replace("-", "") + ".xlsx";
        // 第一步，创建一个webbook，对应一个Excel文件
        XSSFWorkbook wb = new XSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        XSSFSheet sheet = wb.createSheet("Sheet1");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        XSSFRow row = sheet.createRow(0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        XSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        List<ExcelCell> excelCells = Lists.newArrayList();
        Field[] fields = (list.get(0)).getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ExportExcel.class)) {
                ExportExcel exportExcel = field.getAnnotation(ExportExcel.class);
                ExcelCell excelCell = new ExcelCell();
                excelCell.setIndex(exportExcel.index());
                excelCell.setName(exportExcel.name());
                excelCell.setTypeName(field.getName());
                excelCell.setTypeClass(field.getType());
                excelCells.add(excelCell);
            }
        }

        for (ExcelCell excelCell : excelCells) {
            XSSFCell cell = row.createCell(excelCell.getIndex());
            cell.setCellValue(excelCell.getName());
            cell.setCellStyle(style);
        }

        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            T vo = list.get(i);
            for (ExcelCell excelCell : excelCells) {
                try {
                    row.createCell(excelCell.getIndex()).setCellValue(getGetMethod(vo, excelCell.getTypeName(), excelCell.getTypeClass()));
                } catch (Exception e) {
                    throw new BusinessException("获取数据失败");
                }
            }
        }

        //第六步,输出Excel文件
        OutputStream output;
        String path = request.getServletContext().getRealPath("/");
        try {
            output = new FileOutputStream(path + path1);
            wb.write(output);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path1;
    }


}
