package cn.plou.web.system.permission.rlRoleData.service.impl;

import cn.plou.web.common.utils.Tools;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.system.permission.rlMenuRole.entity.RlMenuRole;
import cn.plou.web.system.permission.rlRoleData.dao.RlRoleDataMapper;
import cn.plou.web.system.permission.rlRoleData.entity.RlRoleData;
import cn.plou.web.system.permission.rlRoleData.service.RlRoleDataService;
import cn.plou.web.system.permission.rlRoleData.vo.RlRoleDataVo;
import cn.plou.web.system.permission.rlRoleData.vo.RoleData;
import cn.plou.web.system.permission.role.dao.RoleMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static cn.plou.web.common.utils.Tools.getMaxIdNoSize;

@Component
public class RlRoleDataServiceImpl implements RlRoleDataService {

    @Autowired
    private RlRoleDataMapper rlRoleDataMapper;
    @Autowired
    private RoleMapper roleMapper;
    private final Integer synFlag = 0;
//    @Autowired
//    private RoleMapper roleMapper;

//    @Override
//    public int addRlRoleData(RlRoleData rlRoleData){
//        return rlRoleDataMapper.insertRlRoleData(rlRoleData);
//    }
    
	    public  synchronized List<String> getNewIds(int count) {
				 Integer maxIdNow = rlRoleDataMapper.selectMaxId();
					// maxId = maxIdNow+count;
					List<String> ids = new ArrayList<>();
					if (maxIdNow == null || maxIdNow == 0) {
						maxIdNow = 0;
					}
					for (int i = 0; i < count; i++) {
						maxIdNow++;
						ids.add(String.valueOf(maxIdNow));
					}
					return ids;
			}
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyRlRoleDataBatch(RlRoleDataVo rlRoleDataVo){
    	String roleId = rlRoleDataVo.getRoleId();
    	  List<RlRoleData> listOrg = getRlRoleDataByRoleId(roleId);
    	  List<RlRoleData> listNew = new ArrayList<>();
    	  rlRoleDataMapper.deleteRlRoleData(roleId);
        int count=0;
        List<RoleData> roleDataList = rlRoleDataVo.getRoleDataList();
        
        List<String> ids =  getNewIds(roleDataList.size());
        for(int i=0;i<roleDataList.size();i++){
        	  RoleData roleData = roleDataList.get(i);
            RlRoleData rlMenuData = new RlRoleData();
            rlMenuData.setColumnValue(roleData.getColumnValue());
            rlMenuData.setControlType(roleData.getControlType());
            rlMenuData.setId(ids.get(i));
            rlMenuData.setRoleId(roleId);
            rlMenuData.setColumnType(rlRoleDataVo.getColumnType());
            rlRoleDataMapper.insertRlRoleData(rlMenuData);
            listNew.add(rlMenuData);
            count++;
        }
        List<String> delList = new ArrayList<>();
        for(RlRoleData org:listOrg){
        	Boolean flag = false;
        	for(RlRoleData role:listNew){
        		if(role.getColumnValue().equals(org.getColumnValue())){
        			if(org.getControlType() == null){
        				flag = true;
        			}else{
        				if(role.getControlType() != null){
        					Integer rolenew = Integer.parseInt(role.getControlType());
        					Integer roleOrg = Integer.parseInt(org.getControlType());
        					if(rolenew >= roleOrg){
        						flag = true;
        					}
        				}
        			}  			
        			break;
        		}
        	}
        	if(!flag){
        		delList.add(org.getColumnValue());
        	}
        }
        //删除子角色中存在但是上级角色不存在的权限
        List<String> cRoles = roleMapper.selectAllRoleIdsByRoleId(roleId);
        cRoles.remove(roleId);
        if(delList.size()>0 && cRoles.size() > 0){
        	rlRoleDataMapper.deleteCDataNotInPData(cRoles,delList);
        }
        
        return count;
    }

//    @Override
//    public List<RlRoleDataInfo> getAllRlRoleData(RlRoleDataVo rlRoleDataVo, String order , String sortby){
//        List<RlRoleData> rlRoleDataList = rlRoleDataMapper.selectAllRlRoleData(rlRoleDataVo,order,sortby);
//        List<RlRoleDataInfo> rlRoleDataInfoList = new ArrayList<RlRoleDataInfo>();
//        for (RlRoleData rlRoleData : rlRoleDataList){
//            RlRoleDataInfo rlRoleDataInfo = new RlRoleDataInfo();
//            BeanUtils.copyProperties(rlRoleData,rlRoleDataInfo);
//           rlRoleValveInfo.getRoleName(roleMapper.get???(rlRoleData.getRoleId()).getRoleName());
//            rlRoleValveInfo.getValveName(roleMapper.get???(rlValve.getValveId()).getValveName());
//            rlRoleDataInfoList.add(rlRoleDataInfo);
//        }
//        return rlRoleDataInfoList;
//    }

    @Override
    public List<RlRoleData> getRlRoleDataByRoleId(String roleId){
        return rlRoleDataMapper.selectRlRoleDataByRoleId(roleId);
    }

    @Override
    public List<String> getRlRoleData() {
        return rlRoleDataMapper.selectAllRlRoleData();
    }

    @Override
    public String getNewId() {
        if(rlRoleDataMapper.selectAllIds().size() == 0){
            return "1";
        }else {
            return getMaxIdNoSize(rlRoleDataMapper.selectAllIds());
        }
    }

    @Override
    public List<RlRoleData> getRlRoleData(String userId) {
        //return rlRoleDataMapper.selectRlRoleDataByUserId(UserUtils.getUserId());
        return rlRoleDataMapper.selectRlRoleDataByUserId(UserUtils.getUserId());
    }

    @Override
    public int deleteRlRoleDataByColumn(String columnValue) {
        return rlRoleDataMapper.deleteRlRoleDataByColumn(columnValue);
    }

}
