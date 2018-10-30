package cn.plou.web.mobile.station.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import cn.plou.web.mobile.station.query.ParamQuery;
import cn.plou.web.mobile.station.vo.LineData;
import cn.plou.web.mobile.station.vo.ParamGroupVo;
import cn.plou.web.mobile.station.vo.RunningDataVo;
import cn.plou.web.mobile.station.vo.StationDataPointHistory;
import cn.plou.web.station.stationDataPoint.entity.StationDataPoint;
import cn.plou.web.station.stationDataPoint.vo.StationDataPointVo;

/**
 * @Project : tg-webapp-server
 * @File : ParamDetailService.java
 * @Author : WangJiWei
 * @Date : 2018年10月9日下午1:07:01
 *
 * @Comments : 全部
 * 
 */
public interface ParamDetailService {

    /**
     * 全部页,参数选项列表
     */
    public Collection<ParamGroupVo> findParamList(String stationId);

    /**
     * 参数类型下,所有点位的最新数据<br>
     * eg.压力参数页
     */
    public List<StationDataPointVo> findRunningDataByParam(
	    String stationId, /* String group, */
	    String showdevtype);

    /**
     * 点位历史数据查询
     */
    public List<LineData> findHistory(String stationId, String pointid, ParamQuery paramQuery)
	    throws Exception;

    /**
     * 参数历史数据列表 created by wzd.
     */
    public List<LineData> findParamHistoryDataList(String stationId, String pointid, String limit,
	    ParamQuery paramQuery) throws Exception;

    /**
     * 历史数据总条数
     */
    public Integer findParamHistoryCount(String stationId, String pointid, ParamQuery paramQuery)
	    throws Exception;

}
