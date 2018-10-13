package cn.plou.web.system.permission.rlRoleData.service;

import cn.plou.web.system.permission.rlRoleData.entity.RlRoleData;
import cn.plou.web.system.permission.rlRoleData.vo.RlRoleDataVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RlRoleDataService {

//    int addRlRoleData(RlRoleData rlRoleData);

    int modifyRlRoleDataBatch(RlRoleDataVo rlRoleDataVo);

//    List<RlRoleDataInfo> getAllRlRoleData(RlRoleDataVo rlRoleDataVo, String order, String sortby );

    List<RlRoleData> getRlRoleDataByRoleId(String roleId);

    List<String> getRlRoleData();

    String getNewId();

    List<RlRoleData> getRlRoleData(String userId);

    int deleteRlRoleDataByColumn(String columnValue);
}
