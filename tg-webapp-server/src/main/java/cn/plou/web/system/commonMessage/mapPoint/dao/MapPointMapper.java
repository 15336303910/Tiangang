package cn.plou.web.system.commonMessage.mapPoint.dao;

import cn.plou.web.system.commonMessage.mapPoint.entity.MapPoint;
import cn.plou.web.system.commonMessage.mapPoint.vo.MapPointInfo;
import cn.plou.web.system.commonMessage.mapPoint.vo.MapPointVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MapPointMapper {

    //添加地图点信息
    int insertMapPoint(MapPoint mapPoint);

    //批量删除地图点信息
    int delBatchByIds(List<String> ids);

    //单改
    int updateMapPoint(MapPoint mapPoint);

    //批量修改地图点信息
    int updateMapPointBatch(MapPointVo mapPoingVo);

    //按ID查询地图点信息
    MapPoint selectMapPointById(String id);

    List<String> selectAllIds();

    //按条件查询所有信息
    List<MapPointInfo> selectAllMapPoint(@Param("companyIds") List<String> companyIds, @Param("stationId") String stationId,
                                         @Param("commuityId") String commuityId,
                                         @Param("mapPointVo")  MapPointVo mapPointVo,
                                         @Param("order")String order, @Param("sortby")String sortby,
                                         @Param("page")Integer page,@Param("pageSize")Integer pageSize);

    Integer selectMapPointCount(@Param("companyIds") List<String> companyIds, @Param("stationId") String stationId,
                                @Param("commuityId") String commuityId,
                                @Param("mapPointVo")  MapPointVo mapPointVo);
}
