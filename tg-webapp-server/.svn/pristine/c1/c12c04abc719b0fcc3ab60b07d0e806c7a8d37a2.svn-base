package cn.plou.web.mobile.station.service;

import java.util.List;
import java.util.Map;

import cn.plou.web.mobile.station.query.AlarmsQuery;
import cn.plou.web.mobile.station.vo.RunningDataVo;
import cn.plou.web.station.alarm.entity.AlarmInfo;
import cn.plou.web.system.baseMessage.system.vo.SystemInfoVo;
import cn.plou.web.system.permission.rlRoleData.entity.RlRoleData;

/**
 * @Project : tg-webapp-server
 * @File : StationAppService.java
 * @Author : WangJiWei
 * @Date : 2018年9月19日下午1:31:17
 *
 * @Comments :
 * 
 */
public interface StationAppService {

    /**
     * 当前用户的数据权限
     */
    public List<RlRoleData> findRoleDataByUserId(String userId) throws Exception;

    /**
     * 当前用户权限内的所有系统
     */
    public List<SystemInfoVo> findSystemByUserId(String userId) throws Exception;

    
}
