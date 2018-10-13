package cn.plou.web.system.meterMessage.mbus.service;

import cn.plou.web.system.meterMessage.mbus.entity.MeterTimingDefine;
import cn.plou.web.system.meterMessage.mbus.vo.MeterTimingDefineVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MeterTimingDefineService {
    int deleteTimingDefineBatch(List<String> primaryIds);

    int addOrDelTimingDefineBatch(MeterTimingDefineVo meterTimingDefineVo);

    List<MeterTimingDefine> getTimingDefineByMbusCodes(String mbusCode);
}
