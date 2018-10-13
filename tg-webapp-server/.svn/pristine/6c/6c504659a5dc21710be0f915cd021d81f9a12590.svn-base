package cn.plou.web.system.meterMessage.meterKey.dao;


import cn.plou.web.system.meterMessage.meterKey.entity.MeterKey;
import cn.plou.web.system.meterMessage.meterKey.vo.MeterKeyInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MeterKeyMapper {
    int deleteByPrimaryKey(String id);

    int insert(MeterKey record);

    int insertSelective(MeterKey record);

    MeterKey selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MeterKey record);

    int updateByPrimaryKey(MeterKey record);

    List<MeterKey> selectAllMeterKey(@Param("meterIds") List<String> meterIds, @Param("order")String order, @Param("sortby")String sortby,
                                     @Param("page")Integer page,@Param("pageSize")Integer pageSize);

    List<MeterKey> selectAllMeterKeyByAddress2nds(@Param("address2nds")List<String> address2nd,
                                                  @Param("order")String order,@Param("sortby")String sortby,
                                                  @Param("page")Integer page,@Param("pageSize")Integer pageSize);
}