package cn.plou.web.system.baseMessage.commuity.dao;

import cn.plou.web.system.baseMessage.commuity.entity.Commuity;
import cn.plou.web.system.baseMessage.commuity.entity.CommuityKey;
import cn.plou.web.system.baseMessage.commuity.vo.CommuityInfo;
import cn.plou.web.system.baseMessage.commuity.vo.CommuityUnique;
import cn.plou.web.system.baseMessage.commuity.vo.CommuityVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface CommuityMapper {
    int deleteByPrimaryKey(CommuityKey key);

    int deleteBatchByIds(@Param("commuityIds") List<String> commuityIds);

    int insert(Commuity record);

    int insertSelective(Commuity record);

    Commuity selectByPrimaryKey(CommuityKey key);
    Commuity selectCommuityByName(@Param("commuityName")String commuityName,@Param("companyId")String companyId);

    int updateBatch(CommuityVo record);

    int updateByPrimaryKeySelective(Commuity record);

    List<Commuity> selectTreeList(@Param("stationId") String stationId,
                                  @Param("commuityIds")List<String> commuityIds);

    List<CommuityInfo> selectAllCommuity(@Param("start") Integer start,@Param("pageSize")Integer pageSize,
                                         @Param("order") String order, @Param("sortby") String sortby,
                                         @Param("companyIds") List<String> companyIds,
                                         @Param("stationId") String stationId,
                                         @Param("commuityId")String commuityId,
                                         @Param("commuityIds")List<String> commuityIds,
                                         @Param("commuityVo") CommuityVo commuityVo);

    List<String> selectCommuitysByCompanyId(String companyId);

    List<String> selectAllCommuityIds(@Param("companyId") String companyId);

    List<String> selectCommuityIdsByStationId(@Param("stationId")String stationId);
    Commuity selectById(String commuityId);
    Integer selectCommuityCount(@Param("companyIds") List<String> companyIds,
                                @Param("stationId") String stationId,
                                @Param("commuityId")String commuityId,
                                @Param("commuityIds")List<String> commuityIds,
                                @Param("commuityVo") CommuityVo commuityVo);
    List<CommuityUnique> selectAllCommuityUnique();

	String getMaxCommuityId(String companyId);

	List<Commuity> selectCommuityByConsumerIdLike(String consumerId);

	List<Commuity> selectCommuityByStationIds(@Param("stationIds")List<String> stationIds);
}