package cn.plou.web.system.organizationMessage.department.dao;

import cn.plou.web.charge.heatingmanage.vo.HeatingServeDepartmentVO;
import cn.plou.web.system.baseMessage.company.vo.CompanyVo;
import cn.plou.web.system.organizationMessage.department.entity.Department;
import cn.plou.web.system.organizationMessage.department.vo.DepartmentInfo;
import cn.plou.web.system.organizationMessage.department.vo.DepartmentSelectInfo;
import cn.plou.web.system.organizationMessage.department.vo.DepartmentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface DepartmentMapper {
    int deleteByPrimaryKey(String departmentId);

    int deleteBatchByIds(List<String> departmentIds);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(String departmentId);

    List<DepartmentInfo> selectAll(@Param("start")Integer start,
                                   @Param("pageSize")Integer pageSize,
                                   @Param("companyIds")List<String> companyIds,
                                   @Param("order") String order,
                                   @Param("sortby") String sortby,
                                   @Param("departmentVo")DepartmentVo departmentVo);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    int updateBatch(DepartmentVo departmentVo);

    List<String> selectAllDepIds();

    List<Department> selectDownInfo(String companyId);

    Integer selectDepartmentCount( @Param("companyIds")List<String> companyIds,
                                   @Param("departmentVo")DepartmentVo departmentVo);

    List<HeatingServeDepartmentVO> selectDepartmentStaff(String companyId);

}