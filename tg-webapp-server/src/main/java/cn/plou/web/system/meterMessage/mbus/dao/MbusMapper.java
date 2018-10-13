package cn.plou.web.system.meterMessage.mbus.dao;

import cn.plou.web.system.meterMessage.mbus.entity.Mbus;
import cn.plou.web.system.meterMessage.mbus.vo.MbusInfo;
import cn.plou.web.system.meterMessage.mbus.vo.MbusSelectInfo;
import cn.plou.web.system.meterMessage.mbus.vo.MbusVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface MbusMapper {
    int deleteByPrimaryKey(String mbusId);

    int insert(Mbus record);

    int insertSelective(Mbus record);

    Mbus selectByPrimaryKey(String mbusId);

    List<String> selectAllMbusCodesByConsumerIds(List<String> commuityIds);

    int updateByPrimaryKeySelective(Mbus record);

    int updateByPrimaryKey(Mbus record);

    int deleteMbusBatchByIds(List<String> mbusIds);

    int updateDelBatchByIds(@Param("mbusIds") List<String> mbusIds,
                            @Param("updateUser")String updateUser,
                            @Param("updateDate")Date updateDate);

    List<MbusInfo> selectAllMbus(@Param("start")Integer start,
                                 @Param("pageSize")Integer pageSize,
                                 @Param("companyIds")List<String> companyIds,
                                 @Param("stationId")String stationId,
                                 @Param("commuityId")String commuityId,
                                 @Param("buildingId")String buildingId,
                                 @Param("unitId")String unitId,
                                 @Param("houseId")String houseId,
                                 @Param("order")String order,
                                 @Param("sortby")String sortby,
                                 @Param("searchCondition")MbusVo searchCondition,
                                 @Param("consumerIds")List<String> consumerIds);
    
    Integer selectMbusListCount(@Param("companyIds")List<String> companyIds,
                                @Param("stationId")String stationId,
                                @Param("commuityId")String commuityId,
                                @Param("buildingId")String buildingId,
                                @Param("unitId")String unitId,
                                @Param("houseId")String houseId,
                                @Param("searchCondition")MbusVo searchCondition,
                                @Param("consumerIds")List<String> consumerIds);
    int updateBatch(MbusVo mbusVo);
    List<Mbus> selectDownInfo(String comsumerId);
    List<MbusInfo> selectAllMbusByCompany(@Param("companyIds")List<String> companyIds);
    List<String> selectAllIds();
    List<String> selectAllCodes();
    Mbus selectByMbusCode(String mbusCode);
    Mbus selectByConsumer(String companyId,String consumerId);

		int updateBatchAllStation(MbusVo mbusVo);

		int updateBatchAll(MbusVo mbusVo);

		int updateDownProt1(List<Mbus> mbus);

		List<Mbus> selectByMbusCodes(List<Mbus> list);

		void updateDownProt(Mbus bus);
		
		Integer selectMbusListCount2(@Param("companyIds")List<String> companyIds,
        @Param("stationIds")List<String> stationIds,
        @Param("commuityIds")List<String> commuityIds,
        @Param("buildingId")String buildingId,
        @Param("unitId")String unitId,
        @Param("houseId")String houseId,
        @Param("searchCondition")MbusVo searchCondition,
        @Param("consumerIds")List<String> consumerIds);
		
    List<MbusInfo> selectAllMbus2(@Param("start")Integer start,
        @Param("pageSize")Integer pageSize,
        @Param("companyIds")List<String> companyIds,
        @Param("stationIds")List<String> stationIds,
        @Param("commuityIds")List<String> commuityIds,
        @Param("buildingId")String buildingId,
        @Param("unitId")String unitId,
        @Param("houseId")String houseId,
        @Param("order")String order,
        @Param("sortby")String sortby,
        @Param("searchCondition")MbusVo searchCondition,
        @Param("consumerIds")List<String> consumerIds);
    
    void modifyMbusAddressbyConsumer(@Param("consumerId")String consumerId,@Param("nkey")String nkey, @Param("changedAddress")String changedAddress, @Param("orgAddress")String orgAddress);

		void modifyMbusAddress(@Param("consumerId")String consumerId, @Param("address")String address);
}