package cn.plou.web.mobile.station.vo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @Project : tg-webapp-server
 * @File : StationDataPointHistory.java
 * @Author : WangJiWei
 * @Date : 2018年10月12日下午3:53:58
 *
 * @Comments :
 * 
 */
@Data
public class StationDataPointHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String sourceid;
    private String pointid;
    private String pointName;
    private String showdevtype;
    private String unit;
    private Double val;
    private Date time;
    private Long sysReadTimeLong;
}
