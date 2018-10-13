package cn.plou.web.charge.heatingmanage.dao;

import cn.plou.web.charge.heatingmanage.domain.HeatCheckDetail;
import cn.plou.web.common.config.common.BasePageEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeatCheckDetailMapper {

    int deleteByPrimaryKey(String primaryId);


    int insert(HeatCheckDetail record);


    int insertSelective(HeatCheckDetail record);


    HeatCheckDetail selectByPrimaryKey(String primaryId);


    int updateByPrimaryKeySelective(HeatCheckDetail record);


    int updateByConsumerIdSelective(HeatCheckDetail record);


    int updateByPrimaryKey(HeatCheckDetail record);

    List<HeatCheckDetail> find(@Param("beginCreateDate")String beginCreateDate, @Param("endCreateDate")String endCreateDate, @Param("detail") HeatCheckDetail heatCheckDetail, @Param("base") BasePageEntity basePageEntity);

    List<HeatCheckDetail> findByConsumerIdAndCompanyIdAndAnnual(@Param("consumerId")String consumerId,@Param("companyId")String companyId,@Param("annual")String annual);


    int updateHeatingStatus(HeatCheckDetail heatCheckDetail);
}