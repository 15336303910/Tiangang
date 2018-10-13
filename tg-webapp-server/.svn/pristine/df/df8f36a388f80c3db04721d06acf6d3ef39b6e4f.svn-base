package cn.plou.web.system.commonMessage.mapLine.service.impl;

import cn.plou.web.common.utils.RedisUtil;
import cn.plou.web.common.utils.SerializeUtil;
import cn.plou.web.system.baseMessage.commuity.dao.CommuityMapper;
import cn.plou.web.system.baseMessage.company.dao.CompanyMapper;
import cn.plou.web.system.baseMessage.company.service.impl.CompanyServiceImpl;
import cn.plou.web.system.baseMessage.station.dao.StationMapper;
import cn.plou.web.system.commonMessage.mapLine.dao.MapLineMapper;
import cn.plou.web.system.commonMessage.mapLine.entity.MapLine;
import cn.plou.web.system.commonMessage.mapLine.service.MapLineService;
import cn.plou.web.system.commonMessage.mapLine.vo.MapInfo;
import cn.plou.web.system.commonMessage.mapLine.vo.MapLineInfo;
import cn.plou.web.system.commonMessage.mapLine.vo.MapLineListInfo;
import cn.plou.web.system.commonMessage.mapLine.vo.MapLineVo;

import cn.plou.web.system.commonMessage.mapPoint.service.impl.MapPointServiceImpl;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import cn.plou.web.system.commonMessage.typeMst.service.TypeMstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

import static cn.plou.web.common.utils.Tools.getMaxIdNoSize;

@Component
public class MapLineServiceImpl implements MapLineService {
    @Autowired
    private MapLineMapper mapLineMapper;

    @Autowired
    private MapPointServiceImpl mapPointServiceImpl;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private StationMapper stationMapper;

    @Autowired
    private CommuityMapper commuityMapper;

    @Autowired
    private CompanyServiceImpl companyServiceImpl;

    @Autowired
    private TypeMstService typeMstService;

    //添加地图线信息
    @Override
    public int addMapLine(MapLine mapLine) {
        if (mapLine.getStationId() != null) {
            mapLine.setCompanyId(stationMapper.selectByPrimaryKey(mapLine.getStationId()).getCompanyId());
        }
        if (mapLine.getCommuityId() != null) {
            mapLine.setCompanyId((mapLine.getCommuityId()).substring(0, 5));
            mapLine.setStationId(commuityMapper.selectById(mapLine.getCommuityId()).getStationId());
        }
        return mapLineMapper.insertMapLine(mapLine);
    }

    //批量删除地图线信息
    @Override
    public int deleteBatchByIds(List<String> ids) {
        return mapLineMapper.deleteBatchByIds(ids);
    }

    //单改
    @Override
    public int modifyMapLine(MapLine mapLine) {
        if (mapLine.getCompanyId() != null) {
            mapLine.setCommuityId("");
            mapLine.setStationId("");
        } else if (mapLine.getStationId() != null) {
            mapLine.setCompanyId(stationMapper.selectByPrimaryKey(mapLine.getStationId()).getCompanyId());
            mapLine.setCommuityId("");
        } else if (mapLine.getCommuityId() != null) {
            mapLine.setCompanyId(commuityMapper.selectById(mapLine.getCommuityId()).getCompanyId());
            mapLine.setStationId(commuityMapper.selectById(mapLine.getCommuityId()).getStationId());
        }
        return mapLineMapper.updateMapLine(mapLine);
    }

    //批量修改地图线信息
    @Override
    public int modifyMapLineBatch(MapLineVo mapLineVo) {
        if (mapLineVo.getCompanyId() != null) {
            mapLineVo.setCommuityId("");
            mapLineVo.setStationId("");
        } else if (mapLineVo.getStationId() != null) {
            mapLineVo.setCompanyId(stationMapper.selectByPrimaryKey(mapLineVo.getStationId()).getCompanyId());
            mapLineVo.setCommuityId("");
        } else if (mapLineVo.getCommuityId() != null) {
            mapLineVo.setCompanyId(commuityMapper.selectById(mapLineVo.getCommuityId()).getCompanyId());
            mapLineVo.setStationId(commuityMapper.selectById(mapLineVo.getCommuityId()).getStationId());
        }
        return mapLineMapper.updateMapLineBatch(mapLineVo);
    }

    @Override
    public String getNewId() {
        if (mapLineMapper.selectAllIds().size() == 0) {
            return "1";
        } else {
            return getMaxIdNoSize(mapLineMapper.selectAllIds());
        }
    }

    //按ID查询地图线信息
    @Override
    public MapLine getMapLineById(String id) {
        return mapLineMapper.selectMapLineById(id);
    }

    //按条件查询所有信息
    @Override
    public MapLineListInfo getAllMapLine(String companyId, String commuityId, String stationId, MapLineVo mapLineVo, String order, String sortby, Integer page, Integer pageSize) {
        MapLineListInfo mapLineListInfo = new MapLineListInfo();
        if (page != null) {
            page = (page - 1) * pageSize;
        }
        if (sortby != null) {
            if (sortby.equals("meterTypeName")) {
                sortby = "meter_type";
            }
        }
        List<MapLineInfo> mapLineInfoList = mapLineMapper.selectAllMapLine(companyId, commuityId, stationId, mapLineVo, order, sortby, page, pageSize);
        List<TypeMst> typeMstList = typeMstService.getAllTypeMstByRedis();
        for (MapLineInfo mapLineInfo : mapLineInfoList) {
            mapLineInfo.setMeterTypeName(typeMstService.getTypeNameById(typeMstList, mapLineInfo.getMeterType()));
//            if(mapLineInfo.getMeterType()!=null) {
//                if(typeMstService.getTypeMstById(mapLineInfo.getMeterType())!=null) {
//                    mapLineInfo.setMeterTypeName(typeMstService.getTypeMstById(mapLineInfo.getMeterType()).getTypeName());
//                }
//            }
            if (mapLineInfo.getStationId() != null) {
                if (stationMapper.selectByPrimaryKey(mapLineInfo.getStationId()) != null) {
                    mapLineInfo.setStationName(stationMapper.selectByPrimaryKey(mapLineInfo.getStationId()).getStationName());
                }
            }
            if (mapLineInfo.getCommuityId() != null) {
                if (commuityMapper.selectById(mapLineInfo.getCommuityId()) != null) {
                    mapLineInfo.setCommuityName(commuityMapper.selectById(mapLineInfo.getCommuityId()).getCommuityName());
                }
            }
        }
        mapLineListInfo.setMapLineInfoList(mapLineInfoList);
        mapLineListInfo.setCount(mapLineMapper.selectMapLineCount(companyId, commuityId, stationId, mapLineVo));
        return mapLineListInfo;
    }

    @Override
    public MapInfo getMapInfo(String companyId, String stationId, String commuityId) {
        MapInfo mapInfo = new MapInfo();
        if (getAllMapLine(companyId, commuityId, stationId, null, null, null, null, null) != null) {
            mapInfo.setMapLineInfoList(getAllMapLine(companyId, commuityId, stationId, null, null, null, null, null).getMapLineInfoList());
        }
        if (mapPointServiceImpl.getAllMapPoint(companyId, stationId, commuityId, null, null, null, null, null) != null) {
            mapInfo.setMapPointInfoList(mapPointServiceImpl.getAllMapPoint(companyId, stationId, commuityId, null, null, null, null, null).getMapPointInfoList());
        }
        return mapInfo;
    }
}
