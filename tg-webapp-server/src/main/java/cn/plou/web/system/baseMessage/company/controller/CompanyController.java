package cn.plou.web.system.baseMessage.company.controller;

import cn.plou.web.common.config.MyResponseBodyAdvice;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.utils.FileUtils;
import cn.plou.web.common.utils.TreeNode;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.system.baseMessage.company.entity.Company;
import cn.plou.web.system.baseMessage.company.service.CompanyService;
import cn.plou.web.system.baseMessage.company.vo.CompanyInfo;
import cn.plou.web.system.baseMessage.company.vo.CompanyListInfo;
import cn.plou.web.system.baseMessage.company.vo.CompanySelectInfo;
import cn.plou.web.system.baseMessage.company.vo.CompanyVo;
import cn.plou.web.system.permission.rlRoleData.service.RlRoleDataService;
import cn.plou.web.system.permission.rlUserRole.service.RlUserRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.UUID;

import static cn.plou.web.common.config.common.Cond.getCond;
import static cn.plou.web.common.utils.PageInfo.getPageInfo;

@RestController
@RequestMapping("${systemPath}/company")
@EnableSwagger2
@Api(description = "公司管理")
public class CompanyController extends MyResponseBodyAdvice {
    @Autowired
    CompanyService companyService;
    @Autowired
    RlRoleDataService rlRoleDataService;
    @Autowired
    RlUserRoleService rlUserRoleService;

   /* @ApiOperation(value = "获取全部公司id")
    @GetMapping("/getAllCompanyIds")
    public List<String> getAllCompanyIds(@RequestParam(required = false) String companyId){
       return companyService.getCompanyIdsByCompanyId(companyId);
    }*/


    @ApiOperation(value = "获取全部企业信息")
    @PostMapping("/getAllCompany")
    @RequiresPermissions("10004")
    public Root getAllCompany(@RequestBody CompanySelectInfo companySelectInfo){
        Root root = new Root();
        if(companySelectInfo.getSortby()!=null){
            if(companySelectInfo.getSortby().equals("companyNatureName")){
                companySelectInfo.setSortby("companyNature");
            }else if(companySelectInfo.getSortby().equals("companyTypeName")){
                companySelectInfo.setSortby("companyType");
            }else if(companySelectInfo.getSortby().equals("industryName")){
                companySelectInfo.setSortby("industry");
            }
        }
        //PageHelper.startPage(companySelectInfo.getPage(),companySelectInfo.getPageSize());
        CompanyListInfo companyListInfo = companyService.getAllCompany(companySelectInfo.getPage(),companySelectInfo.getPageSize(),companySelectInfo.getOrder(), companySelectInfo.getSortby(), companySelectInfo.getCompanyId(),companySelectInfo.getSearchCondition());
        //PageInfo pageInfo = new PageInfo<>(companyList);
        root.setData(companyListInfo.getCompanyInfoList());
        root.setCond(getCond(companySelectInfo.getPage(),companySelectInfo.getPageSize(),companyListInfo.getCount(),companySelectInfo.getOrder(),companySelectInfo.getSortby()));
        return root;
    }

    @ApiOperation(value = "根据id获取企业信息")
    @GetMapping("/getCompanyById")
    @RequiresPermissions("10004")
    public Root getCompanyById(@RequestParam("companyId") String companyId){
        Root root = new Root();
        root.setData(companyService.get(companyId));
        return root;
    }

    @ApiOperation(value = "根据id获取企业下拉框")
    @GetMapping("/getDownInfo")
    public Root getDownInfo(){
        Root root = new Root();
        String userId = UserUtils.getUserId();
        String roleId = rlUserRoleService.getRoleByUserId(userId);
        root.setData(companyService.getDownInfo(roleId));
        return root;
    }

    @ApiOperation(value="获取企业树信息")

    @GetMapping("/getCompanyTree")
    public TreeNode<Company> getCompanyTree(){
        //return companyService.getTree(companyId);
        /*if (companyId == null) {
            companyId = Constant.companyId;
        }*/
        String userId = UserUtils.getUserId();
        String roleId = rlUserRoleService.getRoleByUserId(userId);
        return companyService.buildCompanyTree(roleId);
    }

    @ApiOperation(value = "增加一条企业信息")
    @PostMapping("/addCompany")
    @RequiresPermissions("10001")
    public Boolean addCompany(@RequestBody Company company){
        return companyService.insertSelective(company)==1;
    }

    @ApiOperation(value="根据主键批量删除企信息")
    @DeleteMapping("/deleteBatch")
    @RequiresPermissions("10002")
    public Boolean deleteCompanyBatchByIds(@RequestBody List<String> companyIds){
        return companyService.deleteBatchByIds(companyIds)==companyIds.size();
    }

    @ApiOperation(value="批量修改企业信息")
    @PutMapping("/modifyCompanyBatch")
    @RequiresPermissions("10003")
    public Boolean modifyCompanyBatch(@RequestBody CompanyVo companyVo){
        return companyService.updateBatch(companyVo)==companyVo.getCompanyIds().size();
    }

    @ApiOperation(value="修改一条企业信息")
    @PutMapping("/modifyCompanyById")
    @RequiresPermissions("10003")
    public Boolean modifyCompanyById(@RequestBody Company company){
        return companyService.updateByIdSelective(company)==1;
    }

    @ApiOperation(value = "上传公司文件")
    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public Root  uploadCompanyFile(HttpServletRequest request, @RequestParam MultipartFile file) {
        //String path =request.getServletContext().getRealPath("/");
        String path = "/";
        Root root = new Root();
        if(file !=null){
            FileUtils fileUtils= new FileUtils();
            path += fileUtils.upload(request,"/",file);
            root.setCode(0);
            root.setMsg("图片上传成功");
            root.setData(path);
            return root;
        }else{
            root.setCode(1);
            root.setMsg("图片上传失败");
            return root;
        }
    }


    @ApiOperation(value = "删除公司上传文件")
    @RequestMapping(value = "/deleteFile", method = RequestMethod.DELETE)
    public Root delFile(ServletRequest request,  @RequestBody List<String> filePaths) {
        Root root = new Root();
        for(String filePath:filePaths){
            File delFile = new File(request.getServletContext().getRealPath("")+filePath);
            if(delFile.isFile() && delFile.exists()) {
                delFile.delete();
                root.setCode(0);
                root.setMsg("删除文件成功");
            }else {
                root.setCode(1);
                root.setMsg("没有该文件，删除失败");
                return root;
            }
        }
        return root;
    }
}
