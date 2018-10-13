package cn.plou.web.charge.chargeconfig.util;

import cn.plou.web.charge.chargeconfig.dao.PriceAccuracyInfoMapper;
import cn.plou.web.charge.chargeconfig.entity.PriceAccuracyInfo;
import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.system.commonMessage.typeMst.dao.TypeMstMapper;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author yinxiaochen
 * 2018/8/28 9:21
 */
@Component
public class MoneyConverter {

    @Autowired
    private TypeMstMapper typeMstMapper;

    @Resource
    private PriceAccuracyInfoMapper priceAccuracyInfoMapper;

    public static MoneyConverter moneyConverter;

    @PostConstruct
    public void init() {
        moneyConverter = this;
    }

    public static BigDecimal Convert(String companyId, BigDecimal money, String typeId) {
        if (money == null) {
            money = BigDecimal.ZERO;
        }
        List<TypeMst> typeMstList = moneyConverter.typeMstMapper.selectDownInfoByTypeKbn("payment_method");
        String style = "";
        //通过typeId判断是现金还是非现金
        for (TypeMst typeMst : typeMstList) {
            if (typeMst.getTypeId().equals(typeId)) {
                style = typeMst.getNotes();
                break;
            }
        }
        PriceAccuracyInfo priceAccuracyInfo = moneyConverter.priceAccuracyInfoMapper.findByCompanyId(companyId);
        if(priceAccuracyInfo==null){
            throw new BusinessException("该公司收费精度尚未定义，请先设置！");
        }
        String accuracy = "";
        if (style.equals("现金")) {
            accuracy = priceAccuracyInfo.getCashAccuracy();
        } else {
            accuracy = priceAccuracyInfo.getNotCashAccuracy();
        }

        return handleNumber(money, accuracy);
    }



    public static BigDecimal Convert(String companyId,String id, BigDecimal money) {
        List<TypeMst> typeMstList = moneyConverter.typeMstMapper.selectDownInfoByTypeKbn("payment_method");
        String style = "";
        //通过typeId判断是现金还是非现金
        for (TypeMst typeMst : typeMstList) {
            if (typeMst.getId().equals(id)) {
                style = typeMst.getNotes();
            }
        }
        PriceAccuracyInfo priceAccuracyInfo = moneyConverter.priceAccuracyInfoMapper.findByCompanyId(companyId);
        if(priceAccuracyInfo==null){
            throw new BusinessException("该公司收费精度尚未定义，请先设置！");
        }
        String accuracy = "";
        if (style.equals("现金")) {
            accuracy = priceAccuracyInfo.getCashAccuracy();
        } else {
            accuracy = priceAccuracyInfo.getNotCashAccuracy();
        }

        return handleNumber(money, accuracy);
    }





    /**
     * @param number BigDecimal类型
     * @param style  处理方式
     * @return
     */
    public static BigDecimal handleNumber(BigDecimal number, String style) {

        int powNum = 0;
        String method = "";
        switch (style) {
            case "non_cash_amount_accuracy_0":  //保留到“分”四舍五入
                powNum = 2;
                method = "round";
                break;
            case "non_cash_amount_accuracy_1": //保留到“分”去尾法
                powNum = 2;
                method = "floor";
                break;
            case "cash_amount_accuracy_0": //保留到“角”四舍五入
            case "non_cash_amount_accuracy_2": //保留到“角”四舍五入
                powNum = 1;
                method = "round";
                break;
            case "cash_amount_accuracy_1": //保留到“角”去尾法
            case "non_cash_amount_accuracy_3": //保留到“角”去尾法
                powNum = 1;
                method = "floor";
                break;
            case "cash_amount_accuracy_2": //保留到“元”四舍五入
            case "non_cash_amount_accuracy_4": //保留到“元”四舍五入
                powNum = 0;
                method = "round";
                break;
            case "cash_amount_accuracy_3": //保留到“元”去尾法
            case "non_cash_amount_accuracy_5": //保留到“元”去尾法
                powNum = 0;
                method = "floor";
                break;
        }


        String numString = "";
        double numDouble = Double.parseDouble(number.toPlainString());
        if (method.equals("round")) {
            numString = String.valueOf((double) (Math.round(numDouble * Math.pow(10, powNum))) / Math.pow(10, powNum));
        } else {
            numString = String.valueOf((double) (Math.floor(numDouble * Math.pow(10, powNum))) / Math.pow(10, powNum));
        }
        int rightCount = 0;
        int dotIndex = numString.indexOf(".");
        if (dotIndex > 0) {
            rightCount = numString.substring(dotIndex).length() - 1;
        }
        if (rightCount < powNum) {
            for (int i = 0; i < powNum - rightCount; i++) {
                if (i == 0) {
                    if (rightCount == 0) {
                        numString += ".";
                    }
                }
                numString += "0";
            }
        } else if (rightCount > powNum) {
            numString = numString.substring(0, dotIndex + powNum + 1);
        }
        return new BigDecimal(numString);
    }


    public static void main(String[] args) {
        System.out.println(handleNumber(new BigDecimal("-1799.030"), "cash_amount_accuracy_0"));

        //System.out.println(  new BigDecimal("-1799.030").stripTrailingZeros().toPlainString());
    }

}
