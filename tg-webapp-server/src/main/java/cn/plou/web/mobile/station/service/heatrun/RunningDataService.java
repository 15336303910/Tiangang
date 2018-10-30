package cn.plou.web.mobile.station.service.heatrun;

import java.util.List;
import java.util.Map;

import cn.plou.web.mobile.station.vo.RunningDataVo;
import cn.plou.web.station.stationshowdevtype.entity.StationShowDevType;

/**
 * @Project : tg-webapp-server
 * @File : RunningDataService.java
 * @Author : WangJiWei
 * @Date : 2018年10月9日下午12:57:06
 *
 * @Comments : 关注页
 * 
 */
public interface RunningDataService {

    /**
     * 实时指标
     */
    public List<RunningDataVo> findRunningData(String userId, String systemId) throws Exception;

    /**
     * showDevType信息
     */
    public Map<String, StationShowDevType> showDevTypeMap();
}
