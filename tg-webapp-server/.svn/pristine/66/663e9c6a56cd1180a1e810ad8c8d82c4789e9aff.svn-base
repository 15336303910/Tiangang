package cn.plou.web.system.meterMessage.mbusReadmodel.dao;


import cn.plou.web.system.meterMessage.mbusReadmodel.entity.MbusReadmodel;
import cn.plou.web.system.meterMessage.mbusReadmodel.vo.MbusReadmodelInfo;
import cn.plou.web.system.meterMessage.mbusReadmodel.vo.MbusReadmodelVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface MbusReadmodelMapper {
    int deleteByPrimaryKey(String mbusReadmodelId);

    int deleteMbusReadmodelBatchByIds(List<String> mbusReadmodelIds);

    int insert(MbusReadmodel record);

    int insertSelective(MbusReadmodel record);

    MbusReadmodel selectByPrimaryKey(String mbusReadmodelId);

    int updateByPrimaryKeySelective(MbusReadmodel record);

    int updateMbusReadmodelBatch(MbusReadmodelVo mbusReadmodelVo);

    int updateByPrimaryKey(MbusReadmodel record);

    int setDelMbusReadmodelBatch(@Param("mbusReadmodelIds") List<String> mbusReadmodelIds,
                                 @Param("updateUser")String updateUser,
                                 @Param("updateDate")Date updateDate);

    List<MbusReadmodel>selectMbusReadmodelByCompanyId(List<String>companyIds);

    List<MbusReadmodelInfo> selectAllMbusReadmodel(@Param("companyIds") List<String> companyIds,@Param("mbusIds") List<String> mbusIds,
                                                   @Param("mbusReadmodelVo") MbusReadmodelVo mbusReadmodelVo,
                                                   @Param("order") String order, @Param("sortby") String sortby,
                                                   @Param("page")Integer page,@Param("pageSize") Integer pageSize);
    List<MbusReadmodel> selectByMbusId(String mbusId);

		int updateMbusReadmodelBatchAllStation(MbusReadmodelVo mbusReadmodelVo);

		int updateMbusReadmodelBatchAll(MbusReadmodelVo mbusReadmodelVo);
}