package cn.plou.web.charge.chargeconfig.dao;
import java.util.List;
import java.util.Date;
import java.util.Map;

import cn.plou.web.charge.chargeconfig.entity.SmsInfo;import org.apache.ibatis.annotations.Param;

public interface SmsInfoMapper {

    int deleteByPrimaryKey(@Param("primaryId")String primaryId);


    int insert(SmsInfo record);


    int insertSelective(SmsInfo record);

    SmsInfo selectByPrimaryKey(@Param("primaryId")String primaryId);

    int updateByPrimaryKeySelective(SmsInfo record);


    int updateByPrimaryKey(SmsInfo record);

    List<SmsInfo> findBycreateDatebetweenOrEqualTo(Map<String, Object> param);


}