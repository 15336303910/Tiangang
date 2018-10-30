package cn.plou.web.mobile.station.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import cn.plou.web.station.stationDataPoint.vo.StationDataPointVo;
import lombok.Data;

/**
 * @Project : tg-webapp-server
 * @File : RunningDataVo.java
 * @Author : WangJiWei
 * @Date : 2018年9月19日下午5:49:35
 *
 * @Comments :
 * 
 */

@Data
public class RunningDataVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String category;
    private Date lastTime;
    private List<StationDataPointVo> metrics;
    private List<LineData> lineData;
    private String type;
    private Integer order = 0;
    /**
     * 0.普通点位(一对一) 1.热泵状态(一对一) 2.COP( Map = {
     * rtCOP:151,todayCOP:555,totalCOP:888, lineCOP:List<Map<String,Object>> } )
     */

}
