package cn.plou.web.charge.heatingmanage.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;



/**
*计量管理
*
*/
@Data
public class HouseHeatDetail {
    /** 记录号ＩＤ：ＵＵＩＤ*/
    private String primaryId;

    /** 用户ＩＤ：低保、事业、政府、工厂、门市、写字楼、学校、居民等*/
    private String consumerId;

    /** 仪表ＩＤ：水源、热源*/
    private String meterId;

    /** 公司ＩＤ*/
    private String companyId;

    /** 采暖年度*/
    private String annual;

    /** 表起始码（读取）：触发读取时，读取比设置的时间延后且最近时间的读表记录的数值*/
    private BigDecimal meterStart;

    /** 读取起始时间：可以选择，默认供暖开始时间，触发读取时，记录比设置的时间延后且有读表记录的最近时间*/
    private Date startTime;

    /** 起始确认表码：当没有数，自动等于表起始码（读取），当有数值时，保持不变，可以人工修改，并注明原因*/
    private BigDecimal meterStartConf;

    /** 表终止码（读取）：触发读取时，读取比设置的时间提前且最近时间的读表记录的数值*/
    private BigDecimal meterEnd;

    /** 读取终止时间：可以选择，默认供暖开始时间，触发读取时，记录比设置的时间提前且有读表记录的最近时间*/
    private Date endTime;

    /** 确认终止表码：当没有数，自动等于表终止码（读取），当有数值时，保持不变，可以人工修改，并注明原因*/
    private BigDecimal meterEndConf;

    /** 表码调整说明：注明表码调整的原因*/
    private String adjDesc;

    /** 热表码数：等于起始终止表码－起始确认表码*/
    private BigDecimal meterRead;

    /** 热量调整：填写计量数因各种原因引起的调整值*/
    private BigDecimal heatAdj;

    /** 热量调整原因*/
    private String heatAdjDesc;

    /** 热量小计：等于热表码数＋热量调整*/
    private BigDecimal meterAdjustSum;

    /** 保留１*/
    private String memo1;

    /** 保留２*/
    private String memo2;

    /** 创建时间*/
    private Date createDate;

    /** 创建者*/
    private String createUser;

    /** 更新时间*/
    private Date updateDate;

    /** 更新者*/
    private String updateUser;




    /** 用户地址*/
    private String address;




    /** 用户名称*/
    private String name;


}