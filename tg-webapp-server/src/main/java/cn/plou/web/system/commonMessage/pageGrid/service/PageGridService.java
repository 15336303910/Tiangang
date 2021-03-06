package cn.plou.web.system.commonMessage.pageGrid.service;

import cn.plou.web.system.commonMessage.pageGrid.entity.PageGrid;
import cn.plou.web.system.commonMessage.pageGrid.entity.PageGridKey;
import cn.plou.web.system.commonMessage.pageGrid.vo.PageGridInfo;
import cn.plou.web.system.commonMessage.pageGrid.vo.PageGridSelectInfo;
import cn.plou.web.system.commonMessage.pageGrid.vo.PageGridVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PageGridService {
    public List<PageGridInfo> getAllPageGrid(Integer page,Integer pageSize,String order, String sortby, PageGridSelectInfo pageGridSelectInfo, String pageNo);

    public List<PageGridInfo> getPageGridByGridName(String order, String sortby,String gridName,String pageNo);

    public int addPageGrid(PageGrid pageGrid);

    public int deletePageGridBatchByIds(List<PageGridKey> pageGridKeys);

    public int updateBatch(PageGridVo pageGridVo);

    public int updatePageGridById(PageGrid pageGrid);

    public List<PageGridInfo> getAllPageGridByUser(Integer page, Integer pageSize, String order, String sortby, PageGridSelectInfo pageGridSelectInfo, String pageNo);
}
