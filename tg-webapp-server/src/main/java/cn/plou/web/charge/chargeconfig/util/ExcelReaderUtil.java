package cn.plou.web.charge.chargeconfig.util;

import cn.plou.web.charge.chargeconfig.dto.BankChargeExcelDTO;
import cn.plou.web.charge.chargeconfig.dto.BankChargeListDTO;
import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.common.utils.a1.DateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: ExcelReaderUtil
 * @Description: 解析银行对账 Excel
 * @Author: youbc
 * @Date 2018-09-11 13:51
 */
public class ExcelReaderUtil {

    private static Logger logger = LoggerFactory.getLogger(ExcelReaderUtil.class);

    public static final String XLS = "xls";
    public static final String XLSX = "xlsx";
    public static final String FILE_HEAD = "DZ_bank";

    /**
     * @Description: 根据 fileType 不同读取 excel 文件
     * @Param: [path]
     * @Return: cn.plou.web.charge.chargeconfig.dto.BankChargeExcelDTO
     * @Author: youbc
     * @Date: 2018/9/11 15下午46
     */
    public static BankChargeExcelDTO ReadExcel(String path) {
        //读取excel文件
        InputStream is = null;
        try {
            logger.info("【银行对账测试】is start");
            is = new FileInputStream(path);
            logger.info("【银行对账测试】is end");
            // 获取工作薄
            Workbook wb;
            if (XLS.equals(path.substring(path.lastIndexOf(".") + 1))) {
                logger.info("【银行对账测试】xls start");
                wb = new HSSFWorkbook(is);
                logger.info("【银行对账测试】xls end");
            } else if (XLSX.equals(path.substring(path.lastIndexOf(".") + 1))) {
                logger.info("【银行对账测试】xlsx start");
                wb = new XSSFWorkbook(is);
                logger.info("【银行对账测试】xlsx end");
            } else {
                logger.warn("请上传 ." + XLS + " 或 ." + XLSX + " 格式文件");
                throw new BusinessException("请上传 ." + XLS + " 或 ." + XLSX + " 格式文件");
            }

            BankChargeExcelDTO dto = new BankChargeExcelDTO();

            // 读取第一个工作页sheet
            Sheet sheet = wb.getSheetAt(0);
            logger.info("【银行对账测试】已读取第一个工作页sheet");
            Row row2 = sheet.getRow(1); // 第二行
            logger.info("【银行对账测试】已读取第二行");
            // 获取最大列数
            int colnum2 = row2.getPhysicalNumberOfCells();
            logger.info("【银行对账测试】已获取最大列数（colnum2）：" + colnum2);
            for (int j = 0; j < colnum2; j++) {
                Cell cell = row2.getCell(j);
                logger.info("【银行对账测试】已获取 cell:j:" + j);
                if (cell != null) {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    logger.info("【银行对账测试】已 cell.setCellType(Cell.CELL_TYPE_STRING)");
                    String value = cell.getStringCellValue();
                    logger.info("【银行对账测试】已 cell.getStringCellValue()：" + value);
                    if (j == 0) {
                        dto.setTransactionTotal(Integer.valueOf(value));
                        logger.info("【银行对账测试】已 setTransactionTotal");
                    } else if (j == 1) {
                        dto.setTransactionMoney(new BigDecimal(value));
                        logger.info("【银行对账测试】已 setTransactionMoney");
                    } else if (j == 2) {
                        dto.setTransactionDate(new SimpleDateFormat("yyyyMMdd").parse(value));
                        logger.info("【银行对账测试】已 setTransactionDate");
                    }
                }
            }

            Row row3 = sheet.getRow(2); // 第三行
            logger.info("【银行对账测试】已读取第三行");
            // 获取最大列数
            int colnum3 = row3.getPhysicalNumberOfCells();
            logger.info("【银行对账测试】已获取最大列数（colnum3）：" + colnum3);
            // 获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            logger.info("【银行对账测试】已获取最大行数（rownum）：" + rownum);
            List<BankChargeListDTO> list = new ArrayList<>();
            Row row;
            for (int i = 3; i < rownum; i++) {
                row = sheet.getRow(i);
                logger.info("【银行对账测试】已 row = sheet.getRow(i):i:" + i);
                if (row != null) {
                    BankChargeListDTO listDTO = new BankChargeListDTO();
                    for (int j = 1; j < colnum3; j++) {
                        Cell cell = row.getCell(j);
                        logger.info("【银行对账测试】已 cell = row.getCell(j):j!" + j);
                        if (cell != null) {
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            logger.info("【银行对账测试】已 setCellType(Cell.CELL_TYPE_STRING)!");
                            String cellValue = cell.getStringCellValue();
                            logger.info("【银行对账测试】已 cellValue!" + cellValue);
                            if (j == 1) {
                                listDTO.setConsumerId(cellValue);
                                logger.info("【银行对账测试】已 setConsumerId!");
                            } else if (j == 2) {
                                listDTO.setThirdPartyFlowCode(cellValue);
                                logger.info("【银行对账测试】已 setThirdPartyFlowCode!");
                            } else if (j == 3) {
                                listDTO.setPayDate(DateUtil.parseString(cellValue, DateUtil.BANK_DEFAULT_DATE_FORMAT));
                                logger.info("【银行对账测试】已 setPayDate!");
                            } else if (j == 4) {
                                listDTO.setPayMoney(new BigDecimal(cellValue));
                                logger.info("【银行对账测试】已 setPayMoney!");
                            } else if (j == 5) {
                                listDTO.setOperatorCode(cellValue);
                                logger.info("【银行对账测试】已 setOperatorCode!");
                            } else if (j == 6) {
                                listDTO.setOperatorArea(cellValue);
                                logger.info("【银行对账测试】已 setOperatorArea!");
                            } else if (j == 7) {
                                listDTO.setPayMode(cellValue);
                                logger.info("【银行对账测试】已 setPayMode!");
                            } else if (j == 8) {
                                listDTO.setPushTel(cellValue);
                                logger.info("【银行对账测试】已 setPushTel!");
                            } else if (j == 9) {
                                listDTO.setPushMail(cellValue);
                                logger.info("【银行对账测试】已 setPushMail!");
                            }
                        }
                    }
                    logger.info("【银行对账测试】list.add(listDTO) start");
                    list.add(listDTO);
                    logger.info("【银行对账测试】list.add(listDTO) end");
                }
            }
            logger.info("【银行对账测试】dto.setList(list) start");
            dto.setList(list);
            logger.info("【银行对账测试】dto.setList(list) end");
            return dto;
        } catch (IOException | ParseException e) {
            logger.error("【银行对账测试】" + e.getMessage());
            e.printStackTrace();
        } finally {
            logger.info("【银行对账测试】finally start");
            try {
                logger.info("【银行对账测试】finally-try start");
                if (is != null) is.close();
                logger.info("【银行对账测试】finally-try end");
            } catch (IOException e) {
                logger.error("【银行对账测试】finally-catch" + e.getMessage());
                e.printStackTrace();
            }
            logger.info("【银行对账测试】finally end");
        }
        logger.warn("【银行对账测试】return null");
        return null;
    }


    public static void main(String[] args) {
//        String path = "C:\\Users\\Administrator\\Desktop\\a.xls";
//        String path = "C:\\Users\\Administrator\\Desktop\\01.xlsx";
        String path = "C:\\Users\\Administrator\\Desktop\\b.txt";
        ReadExcel(path);
    }


}
