package cn.plou.web.system.meterMessage.mbusReadmodel.service;

import cn.plou.web.common.importDataBatch.ImportResult;
import cn.plou.web.system.meterMessage.mbusReadmodel.entity.MbusReadmodel;
import cn.plou.web.system.meterMessage.mbusReadmodel.vo.MbusReadmodelInfo;
import cn.plou.web.system.meterMessage.mbusReadmodel.vo.MbusReadmodelSelectInfo;
import cn.plou.web.system.meterMessage.mbusReadmodel.vo.MbusReadmodelVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import java.util.List;

@Service
public interface MbusReadmodelService {
    int addMbusReadmodel(MbusReadmodel mbusReadmodel);
    MbusReadmodel getMbusReadmodelById(String mbusReadmodelId);
    int modifyMbusReadmodelBatch(MbusReadmodelVo mbusReadmodelVo);
    int modifyMbusReadmodel(MbusReadmodel mbusReadmodel);
    int deleteMbusReadmodelBatchByIds(List<String> mbusReadmodelIds);
    int setDelMbusReadmodelBatch(List<String> mbusReadmodelIds);
    PageInfo<MbusReadmodelInfo> getAllMbusReadmodel(String companyId, String stationId, String commuityId, String buildingNo, String unitId,
                                                    String consumerId, MbusReadmodelVo mbusReadmodelVo, String order, String sortby, Integer page, Integer pageSize);
    List<MbusReadmodel> getMbusReadmodelDownInfo( String companyId);
    List<MbusReadmodel> getMbusReadmodelByMbusId(String mbusId);
    ImportResult importExcel(MultipartFile multipartFile, ServletRequest request);
    int addBatch(List<MbusReadmodel>mbusReadmodelList);
    String exportExcel(MbusReadmodelSelectInfo mbusReadmodelSelectInfo, ServletRequest request);
}
