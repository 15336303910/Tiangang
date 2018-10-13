package cn.plou.web.system.commonMessage.heatingTime.dao;

import cn.plou.web.system.commonMessage.heatingTime.entity.HeatingTime;
import cn.plou.web.system.commonMessage.heatingTime.entity.HeatingTimeKey;
import cn.plou.web.system.commonMessage.heatingTime.vo.HeatingTimeInfo;
import cn.plou.web.system.commonMessage.heatingTime.vo.HeatingTimeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface HeatingTimeMapper {

    //添加供暖时间信息
    int insertHeatingTime(HeatingTime heatingTime);

    List<String> selectAllIds();

    //批量删除
    int deleteBatchByIds(@Param("primaryIds") List<String> primaryIds);

    int updateByPrimaryKeySelective(HeatingTime heatingTime);

    //单改
    int updateHeatingById(HeatingTime heatingTime);

    //批量改
    int updateHeatingTimeBatch(HeatingTimeVo heatingTimeVo);

    //按ID查
    HeatingTime selectHeatingTimeById(String priamryId);

    List<HeatingTimeInfo> selectHeatingTimes(@Param("companyIds") List<String> companyIds,
                                               @Param("stationIds") List<String> stationIds,
                                               @Param("year") String year);

    //查全部
    List<HeatingTimeInfo> selectAllHeatingTime(@Param("companyIds") List<String> companyIds,
                                               @Param("stationId") String stationId,
                                               @Param("heatingTimeVo") HeatingTimeVo heatingTimeVo,
                                               @Param("order")String order,
                                               @Param("sortby")String sortby,
                                               @Param("page")Integer page,
                                               @Param("pageSize")Integer pageSize);

//    int insert(HeatingTime record);
    Integer selectHeatingTimeCount(@Param("companyIds") List<String> companyIds,
                                   @Param("stationId") String stationId,
                                   @Param("heatingTimeVo") HeatingTimeVo heatingTimeVo);



    List  <String>   getAnnualByCompanyId(String companyId);
    
    /**
     * 当前站的采暖季开始时间
     */
    Date getStartByStation(String stationId);
    Date getStartByCompany(String companyId);

}