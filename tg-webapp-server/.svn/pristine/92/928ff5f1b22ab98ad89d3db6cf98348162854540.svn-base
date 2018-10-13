package cn.plou.web.system.baseMessage.pipe.dao;


import cn.plou.web.system.baseMessage.pipe.entity.Pipe;
import cn.plou.web.system.baseMessage.pipe.entity.PipeKey;
import cn.plou.web.system.baseMessage.pipe.vo.PipeInfo;
import cn.plou.web.system.baseMessage.pipe.vo.PipeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface PipeMapper {
    int deleteByPrimaryKey(PipeKey key);

    int insert(Pipe record);

    int insertSelective(Pipe record);

    Pipe selectByPrimaryKey(@Param("pipeKey") PipeKey pipeKey);

    int updateByPrimaryKeySelective(Pipe record);

    int updateByPrimaryKey(Pipe record);

    List<PipeInfo> selectAllPipe(@Param("start")Integer start,@Param("pageSize")Integer pageSize,
                                 @Param("sortby")String sortby, @Param("order")String order,
                                 @Param("pipeVo")PipeVo pipeVo, @Param("companyIds")List<String> companyIds,
                                 @Param("stationId")String stationId);

    /*int deletePipeBatchByIds(@Param("pipeKeyList") List<PipeKey> pipeKeyList);*/

    int deletePipeBatchByIds(PipeKey pipeKeys);
    int updateBatchSelective(PipeVo pipeVo);

    List<String> selectAllPipeIds();
    Integer selectPipeListCount(@Param("pipeVo")PipeVo pipeVo, @Param("companyIds")List<String> companyIds,
                                @Param("stationId")String stationId);
    List<Pipe> selectPipeDownInfo(String ascriptionId);
}