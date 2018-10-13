package cn.plou.web.system.organizationMessage.department.service;

import cn.plou.web.system.organizationMessage.department.entity.Department;
import cn.plou.web.system.organizationMessage.department.vo.DepartmentListInfo;
import cn.plou.web.system.organizationMessage.department.vo.DepartmentVo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface DepartmentService {
    int addDepartment(Department department);
    int dropDepartmentBatchByIds(List<String> departmentIds);
    Department getDepartmentById(String departmentId);
    int modifyDepartmentBatch(DepartmentVo departmentVo);
    DepartmentListInfo getAllDepartment(Integer page, Integer pageSize,
                                        List<String> companyIds,
                                        String order,
                                        String sortby,
                                        DepartmentVo departmentVo);
    String getNewDepartmentId();
    int modifyDepartment(Department department);
    List<Department> getDepartmentDownInfo(String companyId);
}
