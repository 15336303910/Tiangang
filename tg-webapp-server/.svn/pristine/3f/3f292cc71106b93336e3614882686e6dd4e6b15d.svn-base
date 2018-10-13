package cn.plou.web.charge.heatingmanage.dao;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

import cn.plou.web.charge.heatingmanage.domain.HouseHeatDetail;

public interface HouseHeatDetailMapper {

    int deleteByPrimaryKey(String primaryId);


    int insert(HouseHeatDetail record);


    int insertSelective(HouseHeatDetail record);


    HouseHeatDetail selectByPrimaryKey(String primaryId);


    int updateByPrimaryKeySelective(HouseHeatDetail record);


    int updateByPrimaryKey(HouseHeatDetail record);


    List<HouseHeatDetail> find(@Param("id")String id, @Param("keyWord") String keyWord, @Param("annual") String annual, @Param("sortby") String sortby, @Param("order") String order);


    List<HouseHeatDetail> findByUser(@Param("consumerId")String consumerId,@Param("annual")String annual,@Param("order")String order, @Param("sortby")String sortby);

    BigDecimal getSumMeterRead(@Param("consumerId")String consumerId,@Param("annual")String annual);

    BigDecimal getSumHeatAdj(@Param("consumerId")String consumerId,@Param("annual")String annual);

}