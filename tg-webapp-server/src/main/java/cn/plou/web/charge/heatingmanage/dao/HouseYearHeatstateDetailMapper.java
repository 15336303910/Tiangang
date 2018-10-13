package cn.plou.web.charge.heatingmanage.dao;

import cn.plou.web.charge.heatingmanage.domain.HouseYearHeatstateDetail;
import cn.plou.web.charge.heatingmanage.domain.ServeResultByType;
import cn.plou.web.charge.heatingmanage.dto.HeatingServeSearchDTO;
import cn.plou.web.charge.heatingmanage.vo.HeatingServeListVO;
import cn.plou.web.charge.heatingmanage.vo.HeatingServeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Description: 用户年度供暖管理明细
 * @Param:
 * @Return:
 * @Author: youbc
 * @Date: 2018/8/15 17下午22
 */
@Mapper
public interface HouseYearHeatstateDetailMapper {

    int insert(HouseYearHeatstateDetail record);

    HouseYearHeatstateDetail selectByPrimaryKey(String primaryId);

    HeatingServeVO get(String primaryId);

    int updateByPrimaryKey(HouseYearHeatstateDetail record);

    List<HeatingServeListVO> getListBySearch(HeatingServeSearchDTO heatingServeSearchDTO);

    Integer getListCountBySearch(HeatingServeSearchDTO heatingServeSearchDTO);

    HouseYearHeatstateDetail getTodayLast(String companyId);

    List<HeatingServeListVO> getListBySearchOfStation(@Param("dto") HeatingServeSearchDTO heatingServeSearchDTO, @Param("list") List<String> ids);

    Integer getListCountBySearchOfStation(@Param("dto") HeatingServeSearchDTO heatingServeSearchDTO, @Param("list") List<String> ids);


    List  <ServeResultByType> getTotalResultOfNormalByType(HeatingServeSearchDTO heatingServeSearchDTO);

    List  <ServeResultByType>getTotalResultOfStationByType(@Param("dto") HeatingServeSearchDTO heatingServeSearchDTO,
                                                      @Param("list") List<String> ids);




    Integer    getCountOfNormalByendState(HeatingServeSearchDTO heatingServeSearchDTO);


    Integer    getCountOfStationByendState(@Param("dto") HeatingServeSearchDTO heatingServeSearchDTO,
                                           @Param("list") List<String> ids);

}