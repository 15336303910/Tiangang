package cn.plou.web.system.permission.userDataHistory.dao;

import cn.plou.web.system.permission.userDataHistory.entity.UserDataHistory;
import cn.plou.web.system.permission.userDataHistory.vo.UserDataHistoryInfo;
import cn.plou.web.system.permission.userDataHistory.vo.UserDataHistoryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDataHistoryMapper {

    //添加数据操作记录
    int insertUserDataHistory(UserDataHistory userDataHistory);

    List<String> selectAllIds();

    //查询所有操作记录
    List<UserDataHistoryInfo> selectAllUserDataHistory(@Param("userDataHistoryVo")UserDataHistoryVo userDataHistoryVo, @Param("pageName")String pageName,
                                                       @Param("order") String order, @Param("sortby") String sortby,@Param("page")Integer page,
                                                       @Param("pageSize")Integer pageSize,@Param("userCodes")List<String> userCodes);

//    int deleteByPrimaryKey(String id);

//    int insert(UserDataHistory record);

//    int updateByPrimaryKeySelective(UserDataHistory record);

//    int updateByPrimaryKey(UserDataHistory record);
}