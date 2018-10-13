package cn.plou.web.system.commonMessage.mapPoint.service;

import cn.plou.web.system.commonMessage.mapPoint.entity.MapPoint;
import cn.plou.web.system.commonMessage.mapPoint.vo.MapPointInfo;
import cn.plou.web.system.commonMessage.mapPoint.vo.MapPointListInfo;
import cn.plou.web.system.commonMessage.mapPoint.vo.MapPointVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MapPointService {

    //添加地图点信息
    int addMapPoint(MapPoint mapPoint);

    //批量删除地图点信息
    int deleteBatchByIds(List<String> ids);

    //单改
    int modifyMapPoint(MapPoint mapPoint);

    //批量修改地图点信息
    int modifyMapPointBatch(MapPointVo mapPointVo);

    //按ID查询地图点信息
    MapPoint getMapPointById(String id);

    String getNewId();

    //按条件查询所有信息
    MapPointListInfo getAllMapPoint(String companyId, String stationId, String commuityId, MapPointVo mapPointVo, String order, String sortby, Integer page, Integer pageSize);
}
