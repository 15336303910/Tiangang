package cn.plou.web.system.commonMessage.heatingTime.service;

import cn.plou.web.system.commonMessage.heatingTime.entity.HeatingTime;
import cn.plou.web.system.commonMessage.heatingTime.entity.HeatingTimeKey;
import cn.plou.web.system.commonMessage.heatingTime.vo.HeatingInfo;
import cn.plou.web.system.commonMessage.heatingTime.vo.HeatingTimeInfo;
import cn.plou.web.system.commonMessage.heatingTime.vo.HeatingTimeListInfo;
import cn.plou.web.system.commonMessage.heatingTime.vo.HeatingTimeVo;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HeatingTimeService {


    List<String> getAnnualByCompanyId(String  companyId);
    //添加供暖时间信息
    int addHeatingTime(HeatingTime heatingTime);

    String getNewId();

    //批量删
    int deleteBatchByIds(List<String> primaryIds);

    //单改
    int modifyHeatingById(HeatingTime heatingTime);

    //批量改
    int modifyHeatingTimeBatch(HeatingTimeVo heatingTimeVo);

    //按ID查
    HeatingTime getHeatingTimeById(String primaryId);

    //查全部
    HeatingTimeListInfo getAllHeatingTime(String companyId, String stationId, HeatingTimeVo heatingTimeVo,
                                          String order, String sortby, Integer page, Integer pageSize);


    List<HeatingTimeInfo> selectHeatingTimes(String companyIds, String stationIds,String year);

    HeatingInfo getAllCompanyAndAllStationDownInfo();

}
