package cn.plou.web.system.baseMessage.system.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * @Project : tg-webapp-server
 * @File : SystemInfoVo.java
 * @Author : WangJiWei
 * @Date : 2018年9月19日下午4:37:03
 *
 * @Comments :
 * 
 */
@Data
public class SystemInfoVo implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private String systemId;
    private String systemName;

    private String companyId;
    private String companyName;

    private String stationId;
    private String stationName;
    
    private String provianceName;
    private String cityName;
    private String areaName;

}
