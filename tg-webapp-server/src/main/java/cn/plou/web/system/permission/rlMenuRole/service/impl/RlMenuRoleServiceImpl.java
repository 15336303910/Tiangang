package cn.plou.web.system.permission.rlMenuRole.service.impl;

//import cn.plou.web.system.permission.roleData.dao.RoleMapper;
import cn.plou.web.system.permission.rlMenuRole.dao.RlMenuRoleMapper;
import cn.plou.web.system.permission.rlMenuRole.entity.RlMenuRole;
import cn.plou.web.system.permission.rlMenuRole.entity.RlMenuRoleKey;
import cn.plou.web.system.permission.rlMenuRole.service.RlMenuRoleService;
import cn.plou.web.system.permission.rlMenuRole.vo.RlMenuRoleInfo;
import cn.plou.web.system.permission.rlMenuRole.vo.RlMenuRoleVo;
import cn.plou.web.system.permission.role.dao.RoleMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static cn.plou.web.common.utils.Tools.getMaxIdNoSize;

@Component
public class RlMenuRoleServiceImpl implements RlMenuRoleService {

    @Autowired
    private RlMenuRoleMapper rlMenuRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    private final Integer synFlag = 0;
//    @Autowired
//    private RoleMapper roleMapper;

    //添加用户功能权限
//    @Override
//    public int addRlMenuRole(RlMenuRole rlMenuRole){
//        return rlMenuRoleMapper.insertRlMenuRole(rlMenuRole);
//    }

//    @Override
//    public int modifyRlMenuRole(RlMenuRole rlMenuRole) {
//        return rlMenuRoleMapper.updateRlMenuRole(rlMenuRole);
//    }

    //批量修改用户功能权限（先删后增）
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyRlMenuRoleBatch(RlMenuRoleVo rlMenuRoleVo){
    	  List<RlMenuRole> listOrg = rlMenuRoleMapper.selectRlMenuRoleByRoleId(rlMenuRoleVo.getRoleId());
        
    	  rlMenuRoleMapper.deleteRlMenuRoleByRoleId(rlMenuRoleVo.getRoleId());
        List<RlMenuRole> rlMenuRoles = new ArrayList<>();
        List<String> pageNames = rlMenuRoleVo.getPageNames();
        String roleId = rlMenuRoleVo.getRoleId();
        //同步
        synchronized (synFlag) {
          List<String> ids = getNewIds(pageNames.size());
          for(int i=0;i<pageNames.size();i++){
              RlMenuRole rlMenuRole = new RlMenuRole();
              rlMenuRole.setId(ids.get(i));
              rlMenuRole.setRoleId(roleId);
              rlMenuRole.setPageName(pageNames.get(i));
              rlMenuRole.setIsshow("true");
              rlMenuRoles.add(rlMenuRole);
          }
          rlMenuRoleMapper.insertRlMenuRoleBatch(rlMenuRoles);					
				}

        List<String> delList = new ArrayList<>();
        for(RlMenuRole org:listOrg){
        	Boolean flag = false;
        	for(RlMenuRole role:rlMenuRoles){
        		if(role.getPageName().equals(org.getPageName())){
        			flag = true;
        			break;
        		}
        	}
        	if(!flag){
        		delList.add(org.getPageName());
        	}
        }
        //删除子角色中存在但是上级角色不存在的权限
        List<String> cRoles = roleMapper.selectAllRoleIdsByRoleId(roleId);
        cRoles.remove(roleId);
        if(delList.size()>0 && cRoles.size() > 0){
            rlMenuRoleMapper.deleteCMenuNotInPMenu(cRoles,delList);
        }
        return rlMenuRoles.size();
    }
    
    @Override
    public  synchronized List<String> getNewIds(int count) {
        Integer maxIdNow;
        if(rlMenuRoleMapper.selectAllIds()==null||rlMenuRoleMapper.selectAllIds().size()==0){
            maxIdNow = 0;
        }else{
            maxIdNow = rlMenuRoleMapper.selectMaxId();
        }
		// maxId = maxIdNow+count;
        List<String> ids = new ArrayList<>();
        /*if (maxIdNow == null || maxIdNow == 0) {
            maxIdNow = 0;
        }*/
        for (int i = 1; i <= count; i++) {
            maxIdNow++;
            ids.add(String.valueOf(maxIdNow));
        }
        return ids;
		}
    //按roleId查权限
    @Override
    public List<RlMenuRole> getRlMenuRoleByRoleId(String roleId) {
        return rlMenuRoleMapper.selectRlMenuRoleByRoleId(roleId);
    }

    @Override
    public List<String> getAllMenuByRoleId(String roleId) {
        return rlMenuRoleMapper.selectAllMenuByRoleId(roleId);
    }

//    //查全部
//    @Override
//    public List<RlMenuRole> getAllRlMenuRole(){
//        return rlMenuRoleMapper.selectAllRlMenuRole();
//    }

}
