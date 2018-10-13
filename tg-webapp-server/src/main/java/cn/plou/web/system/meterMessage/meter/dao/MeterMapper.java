package cn.plou.web.system.meterMessage.meter.dao;

import cn.plou.web.system.meterMessage.mbus.vo.MbusVo;
import cn.plou.web.system.meterMessage.meter.entity.Meter;
import cn.plou.web.system.meterMessage.meter.vo.MeterInfo;
import cn.plou.web.system.meterMessage.meter.vo.MeterVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface MeterMapper {
    int deleteByPrimaryKey(String meterId);

    int deleteBatchByIds(List<String> meterIds);

    int insert(Meter record);

    int insertSelective(Meter record);

    MeterInfo selectByPrimaryKey(String meterId);

    int updateByPrimaryKeySelective(Meter record);

    int updateByPrimaryKey(Meter record);

    int updateBatchByIds(MeterVo meterVo);

    List<String> selectALLAddress2nd(@Param("meterIds") List<String> meterIds);

    int updateDelBatchByIds(@Param("meterIds") List<String> meterIds,
                            @Param("updateUser")String updateUser,
                            @Param("updateDate")Date updateDate);

    List<Meter> selectMeterDownInforByConsumer(String consumerId);
    List<String> selectAllIds(@Param("meterType") String meterType,@Param("consumerId") String consumerId);
    List<MeterInfo> selectAllMeter(@Param("start")Integer start,
                                   @Param("pageSize")Integer pageSize,
                                   @Param("companyIds")List<String> companyIds,
                                   @Param("stationId")String stationId,
                                   @Param("commuityId")String commuityId,
                                   @Param("buildingId")String buildingId,
                                   @Param("unitId")String unitId,
                                   @Param("houseId")String houseId,
                                   @Param("order")String order,
                                   @Param("sortby")String sortby,
                                   @Param("searchCondition")MeterVo searchCondition,
                                   @Param("consumerIds")List<String> consumerIds);
    Integer selectMeterListCount(@Param("companyIds")List<String> companyIds,
                                 @Param("stationId")String stationId,
                                 @Param("commuityId")String commuityId,
                                 @Param("buildingId")String buildingId,
                                 @Param("unitId")String unitId,
                                 @Param("houseId")String houseId,
                                 @Param("searchCondition")MeterVo searchCondition,
                                 @Param("consumerIds")List<String> consumerIds);

		int updateMeterBatchAllStation(MeterVo meterVo);

		int updateMeterBatchAll(MeterVo meterVo);

		String getMaxMeterId(String typeAndConsumerId);

		Integer getMeterCountbyCommuityId(String commuityId);

		void addBatch(List<Meter> list);

		List<Meter> selectMeterByIds(List<String> meterIds);

		List<Meter> selectMeterBycomuitysIds(List<String> comuitys);

		List<Meter> selectMeterByotherId(String consumerId);

		List<MeterInfo> selectAllMeter2(@Param("start")Integer start,
        @Param("pageSize")Integer pageSize,
        @Param("companyIds")List<String> companyIds,
        @Param("stationIds")List<String> stationIds,
        @Param("commuityIds")List<String> commuityIds,
        @Param("buildingId")String buildingId,
        @Param("unitId")String unitId,
        @Param("houseId")String houseId,
        @Param("order")String order,
        @Param("sortby")String sortby,
        @Param("searchCondition")MeterVo searchCondition,
        @Param("consumerIds")List<String> consumerIds);

		Integer selectMeterListCount2(@Param("companyIds")List<String> companyIds,
        @Param("stationIds")List<String> stationIds,
        @Param("commuityIds")List<String> commuityIds,
        @Param("buildingId")String buildingId,
        @Param("unitId")String unitId,
        @Param("houseId")String houseId,
        @Param("searchCondition")MeterVo searchCondition,
        @Param("consumerIds")List<String> consumerIds);

		void modifyMeterAddressbyConsumer(@Param("consumerId")String consumerId,@Param("nkey")String nkey, @Param("changedAdress")String changedAdress, @Param("orgAddress")String orgAddress);

		void modifyMeterAddress(@Param("consumerId")String consumerId, @Param("address")String address);
}