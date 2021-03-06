package cn.plou.web.system.baseMessage.build.dao;


import cn.plou.web.system.baseMessage.build.entity.Build;
import cn.plou.web.system.baseMessage.build.entity.BuildKey;
import cn.plou.web.system.baseMessage.build.vo.BuildInfo;
import cn.plou.web.system.baseMessage.build.vo.BuildVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BuildMapper {
    int deleteByPrimaryKey(BuildKey key);

    int insert(Build record);

    int insertSelective(BuildInfo record);

    Build selectByPrimaryKey(String buildingNo);
    Build selectBuildByName(@Param("commuityId")String commuityId,@Param("buildingName")String buildingName);

    int updateByPrimaryKeySelective(Build record);

    int updateByPrimaryKey(Build record);

    List<Build> selectBuildByCommuityId(String commuityId);

    int updateBuildBatch(BuildVo buildVo);

    int deleteBuildBatchByIds(List<String> buildingNos);

    List<BuildInfo> selectAllBuild(@Param("companyIds") List<String> companyIds, @Param("stationIds") List<String> stationIds,
                                   @Param("commuityIds") List<String> commuityIds,
                                   @Param("buildVo") BuildVo buildVo, @Param("order") String order, @Param("sortby") String sortby,
                                    @Param("page")Integer page,@Param("pageSize") Integer pageSize);

		List<String> getBuildNosByCommuityIds(List<String> commuityIds);

		int updateBuildBatchAll(BuildVo buildVo);

		int updateBuildBatchAllStation(BuildVo buildVo);

		String getMaxBuildId(String commuityId);

		int selectAllBuildCount(@Param("companyIds") List<String> companyIds, @Param("stationIds") List<String> stationIds,
        @Param("commuityIds") List<String> commuityIds,
        @Param("buildVo") BuildVo buildVo);

		void modifyBuildName(@Param("address")String address, @Param("nkey")String nkey, @Param("commuityId")String commuityId);
}