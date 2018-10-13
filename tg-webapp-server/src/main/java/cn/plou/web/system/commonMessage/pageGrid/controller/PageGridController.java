package cn.plou.web.system.commonMessage.pageGrid.controller;

import cn.plou.web.common.config.common.BaseController;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.system.commonMessage.pageGrid.entity.PageGrid;
import cn.plou.web.system.commonMessage.pageGrid.entity.PageGridKey;
import cn.plou.web.system.commonMessage.pageGrid.service.PageGridService;
import cn.plou.web.system.commonMessage.pageGrid.vo.PageGridInfo;
import cn.plou.web.system.commonMessage.pageGrid.vo.PageGridVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Calendar;
import java.util.List;

import static cn.plou.web.common.config.common.Cond.getCond;
import static cn.plou.web.common.utils.PageInfo.getPageInfo;

@RestController
@EnableSwagger2
@RequestMapping("${systemPath}/gridCol")
@Api(description = "页面grid显示管理")
public class PageGridController  {
    @Autowired
    private PageGridService pageGridService;

    @ApiOperation("新增页面grid显示")
    @PostMapping("addGridCol")
    @RequiresPermissions("30601")
    public Boolean addGridCol(@RequestBody PageGrid pageGrid) {
        return pageGridService.addPageGrid(pageGrid)==1;
    }

    @ApiOperation("批量删除页面grid显示")
    @DeleteMapping("/deleteGridColBatch")
    @RequiresPermissions("30602")
    public Boolean dropGridColBatch(@RequestBody List<PageGridKey> pageGridKeys) {
        return pageGridService.deletePageGridBatchByIds(pageGridKeys)==pageGridKeys.size();
    }

    @ApiOperation("批量修改grid页面显示")
    @PutMapping("modifyGridColBatch")
    @RequiresPermissions("30603")
    public Boolean modifyGridColBatch(@RequestBody PageGridVo pageGridVo) {
        return pageGridService.updateBatch(pageGridVo)==pageGridVo.getPageGridKeys().size();
    }

    @ApiOperation("修改一条grid页面显示信息")
    @PutMapping("modifyGridCol")
    @RequiresPermissions("30603")
    public Boolean modifyGridCol(@RequestBody PageGrid pageGrid) {
        return pageGridService.updatePageGridById(pageGrid)==1;
    }

    /*@ApiOperation("根据主键查询页面grid显示")
    @GetMapping("/getGridColById")
    public Root getGridColById(@RequestParam String id){
        Root root = new Root();
        return root;
    }*/
    @ApiOperation("根据条件获取全部页面grid显示")
    @PostMapping("/getAllGridCol")
    //@RequiresPermissions("30604")
    public Root getAllGridCol(@RequestBody PageGridVo pageGridVo) {
        /*if(pageGridVo.getPage()!=null&&pageGridVo.getPageSize()!=null){
            PageHelper.startPage(pageGridVo.getPage(),pageGridVo.getPageSize());
        }*/
        List<PageGridInfo> pageGridList = pageGridService.getAllPageGrid(pageGridVo.getPage(),pageGridVo.getPageSize(),pageGridVo.getOrder(),pageGridVo.getSortby(),pageGridVo.getSearchCondition(),pageGridVo.getPageNo());
        Root root = new Root();
        root.setData(pageGridList);
       /* if(pageGridVo.getPage()!=null&&pageGridVo.getPageSize()!=null){
            PageInfo pageInfo = new PageInfo(pageGridList);
            root.setCond(getCond(pageInfo.getPageNum(),pageInfo.getPageSize(),(int)pageInfo.getTotal(),pageGridVo.getOrder(),pageGridVo.getSortby()));
        }else{
            root.setCond(getCond(null,null,pageGridList.size(),pageGridVo.getOrder(),pageGridVo.getSortby()));
        }*/
        root.setCond(getCond(pageGridVo.getPage(),pageGridVo.getPageSize(),pageGridList.size(),pageGridVo.getOrder(),pageGridVo.getSortby()));
        return root;
    }

    /*@ApiOperation("根据角色获取全部页面grid显示")
    @GetMapping("/getAllGridColByRole")
    public Root getAllGridColByRole(@RequestParam(value = "page",required = false) Integer page, @RequestParam(value = "pageSize",required = false) Integer pageSize,
                                    @RequestParam(required = false) String order,
                                    @RequestParam(required = false) String sortby,
                                    @RequestParam(required = false) String display,
                                    @RequestParam(value="pageNo") String pageNo) {
       *//* System.out.println(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" +
                Calendar.getInstance().get(Calendar.MINUTE) + ":" + Calendar.getInstance().get(Calendar.SECOND));*//*
        List<PageGrid> pageGridList = pageGridService.getAllPageGridByUser(order, sortby, display, pageNo);
        Root root = new Root();
        if(page==null || pageSize==null){
            root.setData(pageGridList);
        }else if(page!=null&&pageSize!=null){
            root.setCond(getCond(page,pageSize,pageGridList.size(),order,sortby));
            root.setData(getPageInfo(pageGridList,page,pageSize));
        }
        return root;
    }*/

}