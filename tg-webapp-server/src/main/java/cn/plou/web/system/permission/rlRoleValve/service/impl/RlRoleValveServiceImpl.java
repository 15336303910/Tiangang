package cn.plou.web.system.permission.rlRoleValve.service.impl;

import cn.plou.web.common.utils.CamelCaseUtil;
import cn.plou.web.common.utils.Tools;
import cn.plou.web.system.permission.rlRoleValve.dao.RlRoleValveMapper;
//import cn.plou.web.system.permission.roleData.dao.RoleMapper;
import cn.plou.web.system.permission.rlRoleValve.entity.RlRoleValve;
import cn.plou.web.system.permission.rlRoleValve.service.RlRoleValveService;
import cn.plou.web.system.permission.rlRoleValve.vo.RlRoleValveInfo;
import cn.plou.web.system.permission.rlRoleValve.vo.RlRoleValveVo;
import cn.plou.web.system.permission.rlRoleValve.vo.RoleValve;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static cn.plou.web.common.utils.Tools.getMaxIdNoSize;

@Component
public class RlRoleValveServiceImpl implements RlRoleValveService {

    @Autowired
    private RlRoleValveMapper rlRoleValveMapper;

//    @Autowired
//    private RoleMapper roleMapper;

//    @Override
//    public int addRlRoleValve(RlRoleValve rlRoleValve){
//        return rlRoleValveMapper.insertRlRoleValve(rlRoleValve);
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int modifyRlRoleValve(RlRoleValveVo rlRoleValveVo) {
        rlRoleValveMapper.deleteRlRoleValve(rlRoleValveVo.getRoleId());
        int count = 0;
        List<RoleValve> roleValveList = rlRoleValveVo.getRoleValveList();
        for (RoleValve roleValve : roleValveList){
            RlRoleValve rlRoleValve = new RlRoleValve();
            rlRoleValve.setValveId(roleValve.getValveId());
            rlRoleValve.setValveType(roleValve.getValveType());
            List<String> ids = rlRoleValveMapper.selectAllIds();
            if(ids.size() == 0){
                rlRoleValve.setId("1");
            }else {
                rlRoleValve.setId(Tools.getMaxIdNoSize(ids));
            }
            rlRoleValve.setRoleId(rlRoleValveVo.getRoleId());
            rlRoleValveMapper.insertRlRoleValve(rlRoleValve);
            count++;
        }
        return count;
    }


//    @Override
//    public int modifyRlRoleValveBatch(RlRoleValveVo rlRoleValveVo){
//        return rlRoleValveMapper.updateRlRoleValveBatch(rlRoleValveVo);
//    }

    @Override
    public List<RlRoleValve> getRlRoleValveByRoleId(String roleId){
        return rlRoleValveMapper.selectRlRoleValveByRoleId(roleId);
    }

//    @Override
//    public String getNewId(){
//        if (rlRoleValveMapper.selectAllIds().size()==0){
//            return "1";
//        }else{
//            return getMaxIdNoSize(rlRoleValveMapper.selectAllIds());
//        }
//    }

//    @Override
//    public List<RlRoleValve> getAllRlRoleValve(RlRoleValveVo rlRoleValveVo, String order, String sortby){
//        sortby = CamelCaseUtil.toUnderscoreCase(sortby);
//        return rlRoleValveMapper.selectAllRlRoleValve(rlRoleValveVo,order,sortby);
//    }
}
