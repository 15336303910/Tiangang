package cn.plou.web.system.meterMessage.meterModifyData.dao;


import cn.plou.web.system.meterMessage.meterModifyData.entity.MeterModifyData;
import cn.plou.web.system.meterMessage.meterModifyData.vo.MeterModifyDataInfo;
import cn.plou.web.system.meterMessage.meterModifyData.vo.MeterModifyDataVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MeterModifyDataMapper {
    int deleteByPrimaryKey(String meterId);

    int insert(MeterModifyData record);

    int insertSelective(MeterModifyData record);

    MeterModifyData selectByPrimaryKey(String meterId);

    int updateByPrimaryKeySelective(MeterModifyData record);

    int updateByPrimaryKey(MeterModifyData record);

    int updateMeterModifyDataBatch(MeterModifyDataVo meterModifyDataVo);

    List<MeterModifyDataInfo> selectAllMeterModifyData(@Param("meterIds") List<String> meterIds, @Param("meterModifyDataVo") MeterModifyDataVo meterModifyDataVo,
                                                       @Param("order")String order, @Param("sortby")String sortby,@Param("page")Integer page,@Param("pageSize")Integer pageSize);

}