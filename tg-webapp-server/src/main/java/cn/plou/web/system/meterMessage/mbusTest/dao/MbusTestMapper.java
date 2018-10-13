package cn.plou.web.system.meterMessage.mbusTest.dao;

import cn.plou.web.system.meterMessage.mbusTest.entity.MbusTest;
import cn.plou.web.system.meterMessage.mbusTest.vo.MbusTestInfo;
import cn.plou.web.system.meterMessage.mbusTest.vo.MbusTestVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MbusTestMapper {
    int deleteByPrimaryKey(String id);

    int insert(MbusTest record);

    int insertSelective(MbusTest record);

    MbusTest selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MbusTest record);

    int updateByPrimaryKey(MbusTest record);

    int updateBatch(MbusTestVo mbusTestVo);

    List<String> selectAllIds();

    List<MbusTestInfo> selectAllMbusTest(@Param("start")Integer start,@Param("pageSize")Integer pageSize,
                                         @Param("order") String order, @Param("sortby") String sortby,
                                         @Param("mbusCodes") List<String> mbusCodes,@Param("mbusTestVo")MbusTestVo mbusTestVo);
    Integer selectMbusTestCount(@Param("mbusCodes") List<String> mbusCodes,@Param("mbusTestVo")MbusTestVo mbusTestVo);
}