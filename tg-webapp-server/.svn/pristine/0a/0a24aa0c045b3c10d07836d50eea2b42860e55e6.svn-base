package cn.plou.web.system.commonMessage.mapLine.service;

import cn.plou.web.system.commonMessage.mapLine.entity.MapLine;
import cn.plou.web.system.commonMessage.mapLine.vo.MapInfo;
import cn.plou.web.system.commonMessage.mapLine.vo.MapLineInfo;
import cn.plou.web.system.commonMessage.mapLine.vo.MapLineListInfo;
import cn.plou.web.system.commonMessage.mapLine.vo.MapLineVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MapLineService {

    //添加地图线信息
    int addMapLine(MapLine mapLine);

    //批量删除地图线信息
    int deleteBatchByIds(List<String> ids);

    //单改
    int modifyMapLine(MapLine mapLine);

    //批量修改地图线信息
    int modifyMapLineBatch(MapLineVo mapLineVo);

    String getNewId();

    //按ID查询地图线信息
    MapLine getMapLineById(String id);

    //按条件查询所有信息
    MapLineListInfo getAllMapLine(String companyId, String commuityId, String stationId, MapLineVo mapPointVo, String order, String sortby, Integer page, Integer pageSize);

    MapInfo getMapInfo(String companyId,String stationId,String commuityId);
}
