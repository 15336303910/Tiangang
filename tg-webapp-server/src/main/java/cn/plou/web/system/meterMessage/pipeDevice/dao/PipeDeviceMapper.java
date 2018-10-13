package cn.plou.web.system.meterMessage.pipeDevice.dao;


import cn.plou.web.system.meterMessage.pipeDevice.entity.PipeDevice;
import cn.plou.web.system.meterMessage.pipeDevice.vo.PipeDeviceInfo;
import cn.plou.web.system.meterMessage.pipeDevice.vo.PipeDeviceVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PipeDeviceMapper {
    int deleteByPrimaryKey(String pipeDeviceId);

    int insert(PipeDevice record);

    int insertSelective(PipeDevice record);

    PipeDevice selectByPrimaryKey(String pipeDeviceId);

    List<PipeDevice> selectPipeDeviceByAscriptionId(String id);

    int updateByPrimaryKeySelective(PipeDevice record);

    int updateByPrimaryKey(PipeDevice record);

    int deletePipeDeviceBatchByIds(List<String> pipeDeviceIds);

    int updatePipeDeviceBatch( PipeDeviceVo pipeDeviceVo);

    PipeDevice selectPipeDeviceById( String pipeDeviceId);

    List<PipeDevice> selectAll();

    List<PipeDeviceInfo> selectAllPipeDevice(@Param("companyIds") List<String> companyIds, @Param("ids")List<String> ids, @Param("pipeDeviceVo")PipeDeviceVo pipeDeviceVo ,
                                             @Param("order")String order, @Param("sortby")String sortby,@Param("page")Integer page,@Param("pageSize")Integer pageSize );
}