package cn.plou.web.common.task;

import cn.plou.web.charge.chargeconfig.controller.CostGatherController;
import cn.plou.web.charge.chargeconfig.dao.BankInterfaceInfoMapper;
import cn.plou.web.charge.chargeconfig.dto.BankChargeDTO;
import cn.plou.web.charge.chargeconfig.entity.BankInterfaceInfo;
import cn.plou.web.charge.chargeconfig.service.ChargeAccountService;
import cn.plou.web.charge.chargeconfig.util.ExcelReaderUtil;
import static cn.plou.web.charge.chargeconfig.util.ExcelReaderUtil.FILE_HEAD;
import cn.plou.web.common.utils.a1.DateUtil;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@Api(description = "滞纳金任务调度")
public class LateFeeJob {
    @Resource
    CostGatherController costGatherController;

    @Value("${bank-file-path}")
    private String bankFilePath;

    @Autowired
    private BankInterfaceInfoMapper bankInterfaceInfoMapper;

    @Autowired
    private ChargeAccountService chargeAccountService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String ERROR = "ERROR";
    private static final String SUCCESS = "SUCCESS";

    //@Scheduled(fixedRate = 1000)
    //"0 2 1 * * ?" 每天凌晨1点2分触发 ，这里可以考虑设置每年10月——次年4月触发，以降低服务器发压力
    @Scheduled(cron = "0 2 1 * * ?")
    public void run(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        String ymd = sdf.format(new Date());
        System.out.println("当前时间为:"+ymd);

        //我要根据当前时间，去判断采暖季（2018年10月——2019年5月，都应该算作2018年）
        costGatherController.calcLateFee();
        //滞纳金计算公式：
        //每个用户对应的热费单价类型，然后找到“热费单价类型表”price_define，然后用“用户年度采暖费用明细表”user_year_receivable_detail中，费用项目charging_item中的“金额”
        // （金额指的是除了面积费用、热量费用、滞纳金剩下的那些）,去在“热费单价类型表”price_define中滞纳金缴纳时间段内，乘以“热费单价类型表”price_define中的滞纳金率。每天计算一个滞纳金。
        //比如说，金额为100，滞纳金率为5%，那么每天会产生5元钱的滞纳金，每天五元钱。
        //所以这个要扫描所有的用户表，计算所有的情况，会比较消耗系统资源。

        //System.out.println("当前时间为:111");
    }

    /**
     * @Description: 银行对账文件处理
     * @Param: []
     * @Return: void
     * @Author: youbc
     * @Date: 2018/9/27 15下午29
     */
    @Scheduled(cron = "0 0/20 * * * ?")
    public void bankCharge() {
        File file = new File(bankFilePath);
        String[] fileList = file.list();
        if (fileList == null) {
            logger.warn("未找到文件列表！");
            return;
        }
        List<String> fileNames = Lists.newArrayList();
        for (String s : fileList) {
            String tail = s.substring(s.lastIndexOf(".") + 1);
            int fileLength = s.length();
            int headLength = FILE_HEAD.length();
            if ((fileLength > headLength) && StringUtils.equalsIgnoreCase(s.substring(0, headLength), FILE_HEAD) && (ExcelReaderUtil.XLSX.equals(tail) || ExcelReaderUtil.XLS.equals(tail))) {
                fileNames.add(s);
            }
        }
        for (String fileName : fileNames) {
            String[] fileNameNoHeadAndTail = fileName.substring(0, fileName.lastIndexOf(".")).split(FILE_HEAD);
            if (fileNameNoHeadAndTail.length != 2) {
                String msg = "对账文件名不正确！fileName：" + fileName;
                logger.error(msg);
                renameErrorFile(fileName, msg);
                continue;
            }
            String platformCodeAndDate = fileNameNoHeadAndTail[1];
            int index = platformCodeAndDate.lastIndexOf("_");
            if (index == -1) {
                String msg = "对账文件名不正确！fileName：" + fileName;
                logger.error(msg);
                renameErrorFile(fileName, msg);
                continue;
            }
            String platformCode = platformCodeAndDate.substring(0, index);
            BankInterfaceInfo bankInterfaceInfo = bankInterfaceInfoMapper.selectByPlatformCode(platformCode);
            if (bankInterfaceInfo == null) {
                String msg = "未找到银行对接设置信息！";
                logger.error(msg);
                renameErrorFile(fileName, msg);
                continue;
            }

            BankChargeDTO dto = new BankChargeDTO();
            dto.setPlatformCode(platformCode);
            dto.setReconciliationsDate(platformCodeAndDate.substring(index + 1));
            dto.setBankIp(bankInterfaceInfo.getBankIp());
            dto.setBankId(bankInterfaceInfo.getBankId());
            try {
                chargeAccountService.bankCharge(dto, fileName);
                renameSuccessFile(fileName);
            } catch (Exception e) {
                String msg = e.getMessage();
                logger.error(msg);
                renameErrorFile(fileName, msg);
            }
        }
    }

    private void renameSuccessFile(String fileName) {
        renameFile(fileName, SUCCESS, SUCCESS);
    }

    private void renameErrorFile(String fileName, String msg) {
        renameFile(fileName, msg, ERROR);
    }

    /**
     * @Description: 银行对账文件处理完成后重命名
     * @Param: [fileName, msg, code]
     * @Return: void
     * @Author: youbc
     * @Date: 2018/9/27 15下午29
     */
    private void renameFile(String fileName, String msg, String code) {
        String path = bankFilePath + fileName;
        File f = new File(path);
        f.renameTo(new File(path + code));

        if (ERROR.equalsIgnoreCase(code)) {
            File log = new File(bankFilePath + "error.log");
            if(!log.exists()){
                log.getParentFile().mkdirs();
            }
            try {
                log.createNewFile();
                // write
                FileWriter fw = new FileWriter(log, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(DateUtil.getSysdateTimeString());
                bw.write("\r\n");
                bw.write(fileName);
                bw.write("\r\n");
                bw.write(msg);
                bw.write("\r\n");
                bw.write("------------------------------");
                bw.write("\r\n");
                bw.flush();
                bw.close();
                fw.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }
}
