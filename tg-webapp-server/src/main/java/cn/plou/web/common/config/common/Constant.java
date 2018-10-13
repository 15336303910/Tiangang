package cn.plou.web.common.config.common;

import org.springframework.context.annotation.Scope;


@Scope("singleton")
public class Constant {

    public static final Integer SUCCESS_STATUS_CODE = 200;//接口返回成功status信息的状态码
    public static final Integer DEFAULT_ERROR_STATUS_CODE = 500;//接口返回错误status信息的状态码
    public static final Integer NO_CONTENT_STATUS_CODE = 204;
    public static final Integer NO_LOGIN_ERROR = 401;//没有登录

    public static final Integer SUCCESS=0;
    public static final Integer FAIL=1;

    public static final Integer DEFAULT_LOGIN_ADMIN_ID = 1;
    public static final String DEFAULT_LOGIN_ADMIN_NAME = "ADMIN";
    public static final Integer DEFAULT_IS_DELETE = 0;  //默认不删

    public static final String companyId="00001";
    public static final String userCode="00001";
    public static final String roleId="1";

    public static final Integer ISVALID=1;

    public static final String CURRENT_USER_ID = "CURRENT_USER_ID";
    public static final int TOKEN_EXPIRES_HOUR = 24;
    public static final String AUTHORIZATION = "Authorization";

    //public static final String DEFAULT_UPLOAD_DIR = "E:/apache-tomcat-8.5.29/webapps/tg-micro";
    public static final String DEFAULT_UPLOAD_DIR = "E:/benji";
    public static final String FILE_MAPPING_URL = "/uploads/";
    public static final String VERCODE_URL = "/picture/";

    public static final Integer MAX_LOGIN_COUNT = 6;

    public static final int STATION_ID_LENGTH = 6; // 换热站 id 的长度

    public static final String AES_RULES="tg-web";

    // 全局业务类型，生成流水号时需要
    public static final String GLOBAL_BUSINESS_TYPE = "global_business_type";
    public static final String GBT_HEATING_SERVE = "供热服务";
    public static final String GBT_CONTRACT_APPROVE = "合同审批";
    public static final String GBT_CONTRACT_PAY = "合同缴费";
    public static final String GBT_PRICE_DEFINE = "热费单价";
    public static final String GBT_RECEIPT_NO = "收据号";


}
