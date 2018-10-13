package cn.plou.web.system.commonMessage.mapPoint.service.impl;

import cn.plou.web.common.utils.CamelCaseUtil;
import cn.plou.web.common.utils.RedisUtil;
import cn.plou.web.common.utils.SerializeUtil;
import cn.plou.web.system.baseMessage.commuity.dao.CommuityMapper;
import cn.plou.web.system.baseMessage.company.dao.CompanyMapper;
import cn.plou.web.system.baseMessage.company.service.impl.CompanyServiceImpl;
import cn.plou.web.system.baseMessage.station.dao.StationMapper;
import cn.plou.web.system.commonMessage.mapLine.vo.MapLineInfo;
import cn.plou.web.system.commonMessage.mapPoint.dao.MapPointMapper;
import cn.plou.web.system.commonMessage.mapPoint.entity.MapPoint;
import cn.plou.web.system.commonMessage.mapPoint.service.MapPointService;
import cn.plou.web.system.commonMessage.mapPoint.vo.MapPointInfo;
import cn.plou.web.system.commonMessage.mapPoint.vo.MapPointListInfo;
import cn.plou.web.system.commonMessage.mapPoint.vo.MapPointVo;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import cn.plou.web.system.commonMessage.typeMst.service.TypeMstService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

import static cn.plou.web.common.utils.Tools.getMaxIdNoSize;

@Component
public class MapPointServiceImpl implements MapPointService {
    @Autowired
    private MapPointMapper mapPointMapper;

    @Autowired
    private CompanyServiceImpl companyServiceImpl;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private StationMapper stationMapper;

    @Autowired
    private CommuityMapper commuityMapper;

    @Autowired
    private TypeMstService typeMstService;

    //添加地图点信息
    @Override
    public int addMapPoint(MapPoint mapPoint) {
        if (mapPoint.getStationId() != null) {
            mapPoint.setCompanyId(stationMapper.selectByPrimaryKey(mapPoint.getStationId()).getCompanyId());
        }
        if (mapPoint.getCommuityId() != null) {
            mapPoint.setCompanyId((mapPoint.getCommuityId()).substring(0, 5));
            mapPoint.setStationId(commuityMapper.selectById(mapPoint.getCommuityId()).getStationId());
        }
        return mapPointMapper.insertMapPoint(mapPoint);
    }

    //批量删除地图点信息
    @Override
    public int deleteBatchByIds(List<String> ids) {
        return mapPointMapper.delBatchByIds(ids);
    }

    @Override
    public int modifyMapPoint(MapPoint mapPoint) {
        if (mapPoint.getCompanyId() != null) {
            mapPoint.setCommuityId("");
            mapPoint.setStationId("");
        } else if (mapPoint.getStationId() != null) {
            mapPoint.setCompanyId(stationMapper.selectByPrimaryKey(mapPoint.getStationId()).getCompanyId());
            mapPoint.setCommuityId("");
        } else if (mapPoint.getCommuityId() != null) {
            mapPoint.setCompanyId(commuityMapper.selectById(mapPoint.getCommuityId()).getCompanyId());
            mapPoint.setStationId(commuityMapper.selectById(mapPoint.getCommuityId()).getStationId());
        }
        return mapPointMapper.updateMapPoint(mapPoint);
    }

    //批量修改地图点信息
    @Override
    public int modifyMapPointBatch(MapPointVo mapPointVo) {
        if (mapPointVo.getCompanyId() != null) {
            mapPointVo.setCommuityId("");
            mapPointVo.setStationId("");
        } else if (mapPointVo.getStationId() != null) {
            mapPointVo.setCompanyId(stationMapper.selectByPrimaryKey(mapPointVo.getStationId()).getCompanyId());
            mapPointVo.setCommuityId("");
        } else if (mapPointVo.getCommuityId() != null) {
            mapPointVo.setCompanyId(commuityMapper.selectById(mapPointVo.getCommuityId()).getCompanyId());
            mapPointVo.setStationId(commuityMapper.selectById(mapPointVo.getCommuityId()).getStationId());
        }
        return mapPointMapper.updateMapPointBatch(mapPointVo);
    }

    //按ID查询地图点信息
    @Override
    public MapPoint getMapPointById(String id) {
        return mapPointMapper.selectMapPointById(id);
    }

    @Override
    public String getNewId() {
        if (mapPointMapper.selectAllIds().size() == 0) {
            return "1";
        } else {
            return getMaxIdNoSize(mapPointMapper.selectAllIds());
        }
    }

    //按条件查询所有信息
    @Override
    public MapPointListInfo getAllMapPoint(String companyId, String stationId, String commuityId, MapPointVo mapPointVo, String order, String sortby, Integer page, Integer pageSize) {
        MapPointListInfo mapPointListInfo = new MapPointListInfo();
        if (page != null) {
            page = (page - 1) * pageSize;
        }
        if (sortby != null) {
//            if (sortby.equals("colorName")){
//                sortby = "color";
//            }
//            if (sortby.equals("mapPointType")){
//                sortby = "point_type";
//            }
            if (sortby.equals("meterTypeName")) {
                sortby = "meter_type";
            }
        }
        List<String> companyIds = companyServiceImpl.getCompanyIdsByCompanyId(companyId);
        List<MapPointInfo> mapPointInfoList = mapPointMapper.selectAllMapPoint(companyIds, stationId, commuityId, mapPointVo, order, sortby, page, pageSize);
        List<TypeMst> typeMstList = typeMstService.getAllTypeMstByRedis();
        for (MapPointInfo mapPointInfo : mapPointInfoList) {
            mapPointInfo.setMeterTypeName(typeMstService.getTypeNameById(typeMstList, mapPointInfo.getMeterType()));
            /*if(mapPointInfo.getMeterType()!=null) {
                if(typeMstService.getTypeMstById(mapPointInfo.getMeterType())!=null) {
                    mapPointInfo.setMeterTypeName(typeMstService.getTypeMstById(mapPointInfo.getMeterType()).getTypeName());
                }
            }*/
            if (mapPointInfo.getStationId() != null) {
                if (stationMapper.selectByPrimaryKey(mapPointInfo.getStationId()) == null) {
                    mapPointInfo.setStationName(null);
                } else {
                    mapPointInfo.setStationName(stationMapper.selectByPrimaryKey(mapPointInfo.getStationId()).getStationName());
                }
            }
            if (mapPointInfo.getCommuityId() != null) {
                if (commuityMapper.selectById(mapPointInfo.getCommuityId()) == null) {
                    mapPointInfo.setCommuityName(null);
                } else {
                    mapPointInfo.setCommuityName(commuityMapper.selectById(mapPointInfo.getCommuityId()).getCommuityName());
                }
            }
            mapPointInfo.setCompanyName(companyMapper.selectByPrimaryKey(mapPointInfo.getCompanyId()).getCompanyName());
           /* if(mapPointInfo.getColor()!=null){
                if(mapPointInfo.getColor().equals("balck")){

                }
            }*/
        }
        mapPointListInfo.setMapPointInfoList(mapPointInfoList);
        mapPointListInfo.setCount(mapPointMapper.selectMapPointCount(companyIds, stationId, commuityId, mapPointVo));
        return mapPointListInfo;
    }
}
