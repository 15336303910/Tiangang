package cn.plou.web.system.organizationMessage.staff.service;

import cn.plou.web.system.organizationMessage.staff.vo.StructureInfo;
import cn.plou.web.system.organizationMessage.staff.entity.Staff;
import cn.plou.web.system.organizationMessage.staff.vo.StaffInfo;
import cn.plou.web.system.organizationMessage.staff.vo.StaffVo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StaffService {

    Staff selectByPrimaryKey(String primaryId);

    int addStaff(Staff staff);

    int deleteBatchByIds(List<String> staffId);

    int modifyStaffBatch(StaffVo staffVo);

    int modifyStaffById(Staff staff);

    String getNewId();

    PageInfo<StaffInfo> getAllStaff(@Param("companyIds") String companyIds,@Param("staffVo") StaffVo staffVo,
                                    @Param("order") String order,@Param("sortby") String sortby,
                                    @Param("page") Integer page,@Param("pageSize") Integer pageSize);

    List<Staff> getStaffDownInfoByDepartment(String departmentId);

    List<Staff> getStaffDownInfoByStaffId(String staffId);

    public StructureInfo getStructureById(String staffId);

		Staff getStructureByStationAndName(String stationId, String chargePerson);
}
