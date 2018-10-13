package cn.plou.web.system.commonMessage.pageGridUser.dao;

import cn.plou.web.system.commonMessage.pageGrid.entity.PageGrid;
import cn.plou.web.system.commonMessage.pageGrid.entity.PageGridKey;
import cn.plou.web.system.commonMessage.pageGridUser.entity.PageGridUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PageGridUserMapper {
    int deleteByPrimaryKey(PageGridKey pageGridKey);

    int insert(PageGridUser record);

    int insertSelective(PageGridUser record);

    PageGridUser selectByPrimaryKey(@Param("pageGridKey") PageGridKey pageGridKey,@Param("userId") String userId);

    List<PageGridUser> selectAllPageGridUser(String userId);

    int updateByPrimaryKeySelective(PageGridUser record);

    int updateByPrimaryKey(PageGridUser record);
    List<String> selectAllIds();
}