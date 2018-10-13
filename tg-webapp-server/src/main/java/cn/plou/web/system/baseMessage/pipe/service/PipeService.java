package cn.plou.web.system.baseMessage.pipe.service;

import cn.plou.web.system.baseMessage.pipe.entity.Pipe;
import cn.plou.web.system.baseMessage.pipe.entity.PipeKey;
import cn.plou.web.system.baseMessage.pipe.vo.PipeInfo;
import cn.plou.web.system.baseMessage.pipe.vo.PipeListInfo;
import cn.plou.web.system.baseMessage.pipe.vo.PipeVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PipeService {
    PipeListInfo getAllPipe(Integer page, Integer pageSize, String sortby, String order, PipeVo pipeVo, List<String> companyIds, String stationId);

    Pipe getPipeById(PipeKey pipeKey);

    int deleteBatchByIds(List<PipeKey> pipeKeyList);
    //int deleteBatchByIds(PipeKey pipeKey);

    int addPipe(Pipe pipe);

    int modifyBatch(PipeVo pipeVo);

    String getNewPipeId();

    int modifyPipe(Pipe pipe);

    List<Pipe> getPipeDownInfo(String ascriptionId);
}
