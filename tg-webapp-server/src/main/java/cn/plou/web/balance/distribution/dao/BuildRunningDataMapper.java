package cn.plou.web.balance.distribution.dao;

import cn.plou.web.balance.distribution.entity.BuildRunningData;

public interface BuildRunningDataMapper {
    int deleteByPrimaryKey(String primaryId);

    int insert(BuildRunningData record);

    int insertSelective(BuildRunningData record);

    BuildRunningData selectByPrimaryKey(String primaryId);

    int updateByPrimaryKeySelective(BuildRunningData record);

    int updateByPrimaryKey(BuildRunningData record);
}