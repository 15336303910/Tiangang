package cn.plou.web.system.organizationMessage.department.controller;

import cn.plou.web.common.config.common.BaseController;
import cn.plou.web.common.config.common.Constant;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.system.baseMessage.company.service.CompanyService;
import cn.plou.web.system.organizationMessage.department.entity.Department;
import cn.plou.web.system.organizationMessage.department.service.DepartmentService;
import cn.plou.web.system.organizationMessage.department.vo.DepartmentInfo;
import cn.plou.web.system.organizationMessage.department.vo.DepartmentListInfo;
import cn.plou.web.system.organizationMessage.department.vo.DepartmentSelectInfo;
import cn.plou.web.system.organizationMessage.department.vo.DepartmentVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static cn.plou.web.common.config.common.Cond.getCond;
import static cn.plou.web.common.utils.PageInfo.getPageInfo;

@RestController
@EnableSwagger2
@RequestMapping("${systemPath}/department")
@Api(description = "部门管理")
public class DepartmentController  {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private CompanyService companyService;

    @ApiOperation("新增一个部门信息")
    @PostMapping("/addDepartment")
    @RequiresPermissions("50101")
    public Boolean addDepartment(@RequestBody Department department){
        department.setDepartmentId(departmentService.getNewDepartmentId());
        return departmentService.addDepartment(department)==1;
    }

    @ApiOperation("批量删除部门信息")
    @DeleteMapping("/deleteDepartmentBatch")
    @RequiresPermissions("50102")
    public Boolean dropDepartment(@RequestBody List<String>departmentIds){
        return departmentService.dropDepartmentBatchByIds(departmentIds)==departmentIds.size();
    }

    @ApiOperation("批量修改部门信息")
    @PutMapping("/modifyDepartmentBatch")
    @RequiresPermissions("50103")
    public Boolean modifyDepartmentBatch(@RequestBody DepartmentVo departmentVo){
        return departmentService.modifyDepartmentBatch(departmentVo)==departmentVo.getDepartmentIds().size();
    }

    @ApiOperation("修改一条部门信息")
    @PutMapping("/modifyDepartment")
    @RequiresPermissions("50103")
    public Boolean modifyDepartment(@RequestBody Department department){
        return departmentService.modifyDepartment(department)==1;
    }

    @ApiOperation("根据主键获取对应部门信息")
    @GetMapping("/getDepartmentById")
    @RequiresPermissions("50104")
    public Department getDepartmentById(@RequestParam String departmentId){
        return departmentService.getDepartmentById(departmentId);
    }

    @ApiOperation("根据企业id和条件查询部门列表")
    @PostMapping("/getDepartmentList")
    @RequiresPermissions("50104")
    public Root getDepartmentListByCompanyId(@RequestBody DepartmentSelectInfo departmentSelectInfo){
        Root root = new Root();
        List<String> companyIds = companyService.getCompanyIdsByCompanyId(departmentSelectInfo.getCompanyId());
        if(departmentSelectInfo.getSortby()!=null) {
            if (departmentSelectInfo.getSortby().equals("superDepartmentName")) {
                departmentSelectInfo.setSortby("superDepartmentId");
            }
        }
        //PageHelper.startPage(departmentSelectInfo.getPage(),departmentSelectInfo.getPageSize());
        DepartmentListInfo departmentListInfo = departmentService.getAllDepartment(departmentSelectInfo.getPage(),departmentSelectInfo.getPageSize(),companyIds,departmentSelectInfo.getOrder(),departmentSelectInfo.getSortby(),departmentSelectInfo.getSearchCondition());
        //PageInfo pageInfo = new PageInfo(departmentList);
//        root.setCond(getCond(pageInfo.getPageNum(),pageInfo.getPageSize(),(int)pageInfo.getTotal(),departmentSelectInfo.getOrder(),departmentSelectInfo.getSortby()));
        root.setCond(getCond(departmentSelectInfo.getPage(),departmentSelectInfo.getPageSize(),departmentListInfo.getCount(),departmentSelectInfo.getOrder(),departmentSelectInfo.getSortby()));
        root.setData(departmentListInfo.getDepartmentInfoList());
        return root;
    }

    @ApiOperation("根据公司获取部门下拉框")
    @GetMapping("/getDepartmentDownInfo")
    public List<Department> getDepartmentDownInfo(@RequestParam(required = false) String companyId){
        return departmentService.getDepartmentDownInfo(companyId);
    }
}
