package cn.plou.web.system.commonMessage.pageGrid.dao;

import cn.plou.web.system.commonMessage.pageGrid.entity.PageGrid;
import cn.plou.web.system.commonMessage.pageGrid.entity.PageGridKey;
import cn.plou.web.system.commonMessage.pageGrid.vo.PageGridInfo;
import cn.plou.web.system.commonMessage.pageGrid.vo.PageGridSelectInfo;
import cn.plou.web.system.commonMessage.pageGrid.vo.PageGridVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PageGridMapper {
    int deleteByPrimaryKey(PageGridKey key);

    int insert(PageGrid record);

    int insertSelective(PageGrid record);

    PageGrid selectByPrimaryKey(PageGridKey key);

    int updateByPrimaryKeySelective(PageGrid record);

    int updateByPrimaryKey(PageGrid record);

    List<PageGrid> selectAllPageGrid(@Param("start")Integer start,
                                     @Param("pageSize")Integer pageSize,
                                             @Param("order") String order,
                                             @Param("sortby") String sortby,
                                             @Param("searchCondition") PageGridSelectInfo searchCondition,
                                             @Param("pageNo") String pageNo);

    List<PageGridInfo> selectAllPageGridByUserId(@Param("start")Integer start,
                                                 @Param("pageSize")Integer pageSize,
                                                 @Param("order") String order,
                                                 @Param("sortby") String sortby,
                                                 @Param("searchCondition") PageGridSelectInfo searchCondition,
                                                 @Param("pageNo") String pageNo,
                                                 @Param("userId") String userId);

    int updateBatch(PageGridVo pageGridVo);

    List<PageGridInfo> selectAllPageGridNoUserId(@Param("start")Integer start,
                                             @Param("pageSize")Integer pageSize,
                                             @Param("order") String order,
                                             @Param("sortby") String sortby,
                                             @Param("searchCondition") PageGridSelectInfo searchCondition,
                                             @Param("pageNo") String pageNo,
                                             @Param("userId") String userId);

    Integer selectPageGridByUserIdCount(@Param("searchCondition") PageGridSelectInfo searchCondition,
                                        @Param("pageNo") String pageNo,
                                        @Param("userId") String userId);
    Integer selectPageGridNoUserIdCount(@Param("searchCondition") PageGridSelectInfo searchCondition,
                                        @Param("pageNo") String pageNo,
                                        @Param("userId") String userId);
}