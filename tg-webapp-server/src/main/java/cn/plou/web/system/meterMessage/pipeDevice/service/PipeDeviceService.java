package cn.plou.web.system.meterMessage.pipeDevice.service;

import cn.plou.web.common.importDataBatch.ImportResult;
import cn.plou.web.system.meterMessage.pipeDevice.entity.PipeDevice;
import cn.plou.web.system.meterMessage.pipeDevice.vo.PipeDeviceInfo;
import cn.plou.web.system.meterMessage.pipeDevice.vo.PipeDeviceSelectInfo;
import cn.plou.web.system.meterMessage.pipeDevice.vo.PipeDeviceVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import java.util.List;

@Service
public interface PipeDeviceService {
    int addPipeDevice( PipeDevice pipeDevice);
    int deletePipeDeviceBatchByIds(List<String> pipeDeviceIds);
    int modifyPipeDeviceBatch( PipeDeviceVo pipeDeviceVo);
    int modifyPipeDevice( PipeDevice pipeDevice);
    PipeDevice getPipeDeviceById( String pipeDeviceId);
    List<PipeDevice> getPipeDeviceByAscriptionId(String id);
    PageInfo<PipeDeviceInfo> getAllPipeDevice(String companyId, String stationId, PipeDeviceVo pipeDeviceVo , String order, String sortby, Integer page, Integer pageSize );
    ImportResult importExcel(MultipartFile multipartFile, ServletRequest request);
    int addBatch(List<PipeDevice> pipeDeviceList);
    String exportExcel(PipeDeviceSelectInfo pipeDeviceSelectInfo, ServletRequest request);
}
