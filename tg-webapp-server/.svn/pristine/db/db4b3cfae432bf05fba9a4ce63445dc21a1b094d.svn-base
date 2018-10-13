package cn.plou.web.system.commonMessage.typeMst.controller;

import cn.plou.web.common.config.common.BaseController;
import cn.plou.web.common.config.common.Cond;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.utils.PageInfo;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMstKey;
import cn.plou.web.system.commonMessage.typeMst.service.TypeMstService;
import cn.plou.web.system.commonMessage.typeMst.vo.TypeMstSelectInfo;
import cn.plou.web.system.commonMessage.typeMst.vo.TypeMstVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@RestController
@EnableSwagger2
@RequestMapping("${systemPath}/typeMst")
@Api(description = "数据字典")
public class TypeMstController  {
    @Autowired
    TypeMstService typeMstService;

    @ApiOperation(value = "增加一条数据字典信息")
    @PostMapping("/addTypeMst")
    @RequiresPermissions("30501")
    public Boolean addTypeMst(@RequestBody TypeMst typeMst){
        return typeMstService.addTypeMst(typeMst)==1;
    }

    @ApiOperation(value = "根据类别获取数据字典信息下拉框")
    @PostMapping ("/getDownInfoByTypeKbns")
    public Root getDownInfoByTypeKbns(@RequestBody List<String> typeKbns){
        Root root = new Root();
        root.setData(typeMstService.getDownInfoByTypeKbns(typeKbns));
        return root;
    }

    @ApiOperation(value = "根据类别获取数据字典信息列表")
    @PostMapping ("/getTypeMstByTypeKbn")
    @RequiresPermissions("30504")
    public Root getTypeMstByTypeKbn(@RequestBody TypeMstSelectInfo typeMstSelectInfo){
        Root root = new Root();
        com.github.pagehelper.PageInfo<TypeMst> pageInfo = typeMstService.getTypeMstByTypeKbn(typeMstSelectInfo.getTypeKbn(),typeMstSelectInfo.getSearchCondition(),typeMstSelectInfo.getOrder(),
                typeMstSelectInfo.getSortby(),typeMstSelectInfo.getPage(),typeMstSelectInfo.getPageSize());
        root.setData(pageInfo.getList());
        root.setCond(Cond.getCond(typeMstSelectInfo.getPage(),typeMstSelectInfo.getPageSize(),(int)pageInfo.getTotal(),
                typeMstSelectInfo.getOrder(),typeMstSelectInfo.getSortby()));
        return root;
    }

    @ApiOperation(value = "批量修改数据字典信息")
    @PutMapping("/modifyTypeMstBatch")
    @RequiresPermissions("30503")
    public Boolean modifyTypeMstBatch(@RequestBody TypeMstVo typeMstVo){
        return typeMstService.modifyTypeMstBatch(typeMstVo)==typeMstVo.getTypeMstKeys().size();
    }

    @ApiOperation(value = "修改数据字典信息")
    @PutMapping("/modifyTypeMst")
    @RequiresPermissions("30503")
    public Boolean modifyTypeMst(@RequestBody TypeMst typeMst){
        return typeMstService.modifyTypeMst(typeMst)==1;
    }

    @ApiOperation(value = "批量删除数据字典信息")
    @DeleteMapping("/deleteTypeMstBatchByIds")
    @RequiresPermissions("30502")
    public Boolean deleteTypeMstBatchByIds(@RequestBody List<TypeMstKey> typeMstKeys){

        return typeMstService.deleteTypeMstBatchByIds(typeMstKeys)==typeMstKeys.size();
    }

    @ApiOperation(value = "查询数据字典全部类型")
    @GetMapping("/getTypeMstAllType")
    public Root getTypeMstAllType(){
        Root root = new Root();
        root.setData(typeMstService.getTypeMstAllType());
        return root;
    }


}
