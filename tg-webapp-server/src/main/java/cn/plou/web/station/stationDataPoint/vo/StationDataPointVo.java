package cn.plou.web.station.stationDataPoint.vo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @Project : tg-webapp-server
 * @File : StationDataPointVo.java
 * @Author : WangJiWei
 * @Date : 2018年9月20日下午4:37:37
 *
 * @Comments :
 * 
 */
@Data
public class StationDataPointVo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String sourceid;
    private String pointid;
    private String pointName;
    private String showdevtype;
//    private String showdevtypeName;
    private String unit;
    private Double val;
    private Date time;

}
