package cn.plou.web.system.organizationMessage.staff.controller;

import cn.plou.web.common.config.common.BaseController;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.system.organizationMessage.staff.entity.Staff;
import cn.plou.web.system.organizationMessage.staff.service.StaffService;
import cn.plou.web.system.organizationMessage.staff.vo.StaffInfo;
import cn.plou.web.system.organizationMessage.staff.vo.StaffSelectInfo;
import cn.plou.web.system.organizationMessage.staff.vo.StaffVo;
import cn.plou.web.system.organizationMessage.staff.vo.StructureInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static cn.plou.web.common.config.common.Cond.getCond;
import static cn.plou.web.common.utils.PageInfo.getPageInfo;

@RestController
@RequestMapping("${systemPath}/staff")
@EnableSwagger2
@Api(description = "员工信息")
public class StaffController  {

    @Autowired
    private StaffService staffService;

    @ApiOperation(value = "添加员工")
    @PostMapping("/addStaff")
    @RequiresPermissions("50201")
    public Boolean addStaff(@RequestBody Staff staff){
        staff.setStaffId(staffService.getNewId());
        return staffService.addStaff(staff) == 1;
    }

    @ApiOperation(value = "批量删除员工")
    @DeleteMapping("/deleteStaffBatchByIds")
    @RequiresPermissions("50202")
    public Boolean deleteRlRoleDataBatchByIds(@RequestBody List<String> ids){
        return staffService.deleteBatchByIds(ids) == ids.size();
    }

    @ApiOperation(value="修改用员工信息")
    @PutMapping("/modifyStaff")
    @RequiresPermissions("50203")
    public Boolean modifyStaffById(@RequestBody Staff staff){

        return staffService.modifyStaffById(staff) == 1;
    }

    @ApiOperation(value="批量修改用员工信息")
    @PutMapping("/modifyStaffBatch")
    @RequiresPermissions("50203")
    public Boolean modifyStaffBatch(@RequestBody StaffVo staffVo){
        return staffService.modifyStaffBatch(staffVo) == staffVo.getStaffIds().size();
    }

    @ApiOperation(value = "根据查询条件获取全部员工信息")
    @PostMapping("/getAllStaff")
    @RequiresPermissions("50204")
    public Root getAllRlRoleData(@RequestBody StaffSelectInfo staffSelectInfo){
        Root root = new Root();
//        PageHelper.startPage(staffSelectInfo.getPage(),staffSelectInfo.getPageSize());
        PageInfo<StaffInfo> pageInfo = staffService.getAllStaff(staffSelectInfo.getCompanyId(),staffSelectInfo.getSearchCondition(),
                staffSelectInfo.getOrder(),staffSelectInfo.getSortby(),staffSelectInfo.getPage(),staffSelectInfo.getPageSize());
        root.setData(pageInfo.getList());
//        PageInfo pageInfo = new PageInfo(staffInfoList);
        root.setCond(getCond(staffSelectInfo.getPage(),staffSelectInfo.getPageSize(),(int)pageInfo.getTotal(),
                staffSelectInfo.getOrder(),staffSelectInfo.getSortby()));
        return root;
    }

    @ApiOperation(value = "根据部门id获取员工下拉框")
    @GetMapping("/getStaffDownInfoByDepartmentId")
    public List<Staff> getStaffDownInfoByDepartmentId(@RequestParam("departmentId") String departmentId){
        return staffService.getStaffDownInfoByDepartment(departmentId);
    }

    @ApiOperation(value = "根据员工id获取三级下拉框")
    @GetMapping("/getAllDownInfoByStaffId")
    public StructureInfo getStructureById(@RequestParam("staffId") String staffId){
        return staffService.getStructureById(staffId);
    }

}
