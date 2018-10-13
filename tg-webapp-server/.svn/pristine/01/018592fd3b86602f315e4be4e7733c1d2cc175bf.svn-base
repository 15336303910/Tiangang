package cn.plou.web.system.commonMessage.mapLine.dao;

import cn.plou.web.system.commonMessage.mapLine.entity.MapLine;
import cn.plou.web.system.commonMessage.mapLine.vo.MapLineInfo;
import cn.plou.web.system.commonMessage.mapLine.vo.MapLineVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MapLineMapper {

    //添加地图线信息
    int insertMapLine(MapLine mapLine);

    //批量删除地图线信息
    int deleteBatchByIds(List<String> ids);

    //单改
    int updateMapLine(MapLine mapLine);

    //批量修改地图线信息
    int updateMapLineBatch(MapLineVo mapLineVo);

    //按ID查询地图线信息
    MapLine selectMapLineById(String id);

    List<String> selectAllIds();

    //按条件查询所有信息
    List<MapLineInfo> selectAllMapLine(@Param("companyId") String companyId,
                                       @Param("commuityId") String commuityId,
                                       @Param("stationId") String stationId,
                                       @Param("mapLineVo")  MapLineVo mapLineVo,
                                       @Param("order")String order,
                                       @Param("sortby")String sortby,
                                       @Param("page")Integer page,@Param("pageSize")Integer pageSize);
    Integer selectMapLineCount(@Param("companyId") String companyId,
                               @Param("commuityId") String commuityId,
                               @Param("stationId") String stationId,
                               @Param("mapLineVo")  MapLineVo mapLineVo);
}