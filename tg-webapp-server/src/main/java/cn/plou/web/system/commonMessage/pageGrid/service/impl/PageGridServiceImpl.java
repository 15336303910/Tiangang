package cn.plou.web.system.commonMessage.pageGrid.service.impl;

import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.common.config.common.Constant;
import cn.plou.web.common.utils.RedisUtil;
import cn.plou.web.common.utils.SerializeUtil;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.system.commonMessage.pageGrid.vo.PageGridInfo;
import cn.plou.web.system.commonMessage.pageGrid.vo.PageGridSelectInfo;
import cn.plou.web.system.commonMessage.pageGridUser.entity.PageGridUser;
import cn.plou.web.system.commonMessage.pageGridUser.service.PageGridUserService;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import cn.plou.web.system.commonMessage.typeMst.service.TypeMstService;
import cn.plou.web.system.permission.menu.service.impl.MenuServiceImpl;
import cn.plou.web.system.commonMessage.pageGrid.dao.PageGridMapper;
import cn.plou.web.system.commonMessage.pageGrid.entity.PageGrid;
import cn.plou.web.system.commonMessage.pageGrid.entity.PageGridKey;
import cn.plou.web.system.commonMessage.pageGrid.service.PageGridService;
import cn.plou.web.system.commonMessage.pageGrid.vo.PageGridVo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

@Component
public class PageGridServiceImpl implements PageGridService {
    @Autowired
    private MenuServiceImpl menuService;
    @Autowired
    private PageGridMapper pageGridMapper;
    @Autowired
    private PageGridUserService pageGridUserService;
    @Autowired
    private TypeMstService typeMstService;
  /*  @Override
    public List<PageGrid> getAllPageGrid(String order, String sortby, PageGridSelectInfo pageGridSelectInfo, String pageNo) {
         List<PageGrid> pageGrids = pageGridMapper.selectAllPageGrid(order, sortby, pageGridSelectInfo, pageNo);
         List<PageGrid> pageGrids1 = pageGridMapper.selectAllPageGridByUserId(order,sortby,pageGridSelectInfo,pageNo,UserUtils.getUserId());
         for(int i=0;i<pageGrids.size();i++){
             for(int j=0;j<pageGrids1.size();j++){
                if(pageGrids.get(i).getName().equals(pageGrids1.get(j).getName())
                        &&pageGrids.get(i).getGridName().equals(pageGrids1.get(j).getGridName())
                        &&pageGrids.get(i).getPageNo().equals(pageGrids1.get(j).getPageNo())){
                    pageGrids.remove(pageGrids.get(i));
                    pageGrids.add(pageGrids1.get(j));
                }
             }
         }
         return pageGrids;
    }*/
   @Override
   public List<PageGridInfo> getAllPageGrid(Integer page,Integer pageSize,String order, String sortby, PageGridSelectInfo pageGridSelectInfo, String pageNo) {
       Integer start = null;
       if(page!=null){
           start = (page-1)*pageSize;
       }
       List<PageGridInfo> pageGrids = pageGridMapper.selectAllPageGridByUserId(start,pageSize,order,sortby,pageGridSelectInfo,pageNo,UserUtils.getUserId());
       List<PageGridInfo> pageGridsNoUserId = pageGridMapper.selectAllPageGridNoUserId(start,pageSize,order,sortby,pageGridSelectInfo,pageNo,UserUtils.getUserId());
       List<PageGridInfo> allPageGrid = new ArrayList<>();
       allPageGrid.addAll(pageGrids);
       allPageGrid.addAll(pageGridsNoUserId);
       List<TypeMst> typeMstList = typeMstService.getAllTypeMstByRedis();
       for(PageGridInfo pageGridInfo:allPageGrid){
           pageGridInfo.setAlignName(typeMstService.getTypeNameById(typeMstList,pageGridInfo.getAlign()));
//           if(pageGridInfo.getAlign()!=null){
//               if(typeMstService.getTypeMstById(pageGridInfo.getAlign())!=null){
//                   pageGridInfo.setAlignName(typeMstService.getTypeMstById(pageGridInfo.getAlign()).getTypeName());
//               }
//           }
       }
       return allPageGrid;
   }
    @Override
    public int addPageGrid(PageGrid pageGrid) {
       pageGrid.setId(pageGrid.getPageNo()+"_"+pageGrid.getGridName()+"_"+pageGrid.getName());
        return pageGridMapper.insertSelective(pageGrid);
    }

    @Override
    public int deletePageGridBatchByIds(List<PageGridKey> pageGridKeys) {
        for(PageGridKey pageGridKey:pageGridKeys){
            if(pageGridUserService.getPageGridUserByKey(pageGridKey,UserUtils.getUserId())!=null){
                pageGridUserService.deleteByPrimaryKey(pageGridKey);
            }
            pageGridMapper.deleteByPrimaryKey(pageGridKey);
        }
        return pageGridKeys.size();
    }

    @Override
    public int updateBatch(PageGridVo pageGridVo) {
       PageGridUser pageGridUser = new PageGridUser();
       String userId = UserUtils.getUserId();
       pageGridUser.setUserId(userId);
       //String hide = pageGridVo.getSearchCondition().getHide();
       String hide = pageGridVo.getHide();
        Integer colsort = pageGridVo.getColsort();
        //Integer colsort = pageGridVo.getSearchCondition().getColsort();
       for(PageGridKey pageGridKey:pageGridVo.getPageGridKeys()){
           pageGridUser.setPageNo(pageGridKey.getPageNo());
           pageGridUser.setGridName(pageGridKey.getGridName());
           pageGridUser.setName(pageGridKey.getName());
           if((hide!=null||colsort!=null)){
               pageGridUser.setHide(hide);
               pageGridUser.setColsort(colsort);
               if(pageGridUserService.getPageGridUserByKey(pageGridKey,userId)==null){
                   pageGridUserService.addPageGridUser(pageGridUser);
               }else{
                   pageGridUserService.modifyByPrimaryKey(pageGridUser);
               }
           }

       }
        return pageGridMapper.updateBatch(pageGridVo);
    }

    @Override
    public int updatePageGridById(PageGrid pageGrid) {
        PageGridUser pageGridUser = new PageGridUser();
        String userId = UserUtils.getUserId();
        pageGridUser.setUserId(userId);
        pageGridUser.setPageNo(pageGrid.getPageNo());
        pageGridUser.setGridName(pageGrid.getGridName());
        pageGridUser.setName(pageGrid.getName());
        String hide = pageGrid.getHide();
        Integer colsort = pageGrid.getColsort();
        if((hide!=null||colsort!=null)){
            pageGridUser.setHide(hide);
            pageGridUser.setColsort(colsort);
            if(pageGridUserService.getPageGridUserByKey(pageGrid,userId)==null){
                pageGridUserService.addPageGridUser(pageGridUser);
            }else{
                pageGridUserService.modifyByPrimaryKey(pageGridUser);
            }
        }
        pageGrid.setHide(null);
        pageGrid.setColsort(null);
        return pageGridMapper.updateByPrimaryKeySelective(pageGrid);
    }

    @Override
    public List<PageGridInfo> getAllPageGridByUser(Integer page,Integer pageSize,String order, String sortby, PageGridSelectInfo pageGridSelectInfo, String pageNo) {
        String userId = UserUtils.getUserId();
        List<PageGridInfo> pageGrids = new ArrayList<>();
        Integer start = null;
        if(page!=null){
            start = page+(page-1)*pageSize;
        }
        List<PageGridInfo> pageGridsByUser = pageGridMapper.selectAllPageGridByUserId(start,pageSize,order, sortby, pageGridSelectInfo, pageNo, userId);
        List<PageGridInfo> pageGridsNoUser = pageGridMapper.selectAllPageGridNoUserId(start,pageSize,order,sortby,pageGridSelectInfo,pageNo,userId);
        pageGrids.addAll(pageGridsByUser);
        pageGrids.addAll(pageGridsNoUser);
        return pageGrids;
    }
}
