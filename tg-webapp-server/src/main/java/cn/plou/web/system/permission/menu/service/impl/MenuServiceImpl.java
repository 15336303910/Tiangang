package cn.plou.web.system.permission.menu.service.impl;

import cn.plou.web.common.config.common.Constant;
import cn.plou.web.common.utils.Tools;
import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.system.permission.menu.dao.MenuMapper;
import cn.plou.web.system.permission.menu.entity.Menu;
import cn.plou.web.system.permission.menu.service.MenuService;
import cn.plou.web.system.permission.menu.vo.MenuInfo;
import cn.plou.web.system.permission.rlUserRole.service.RlUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RlUserRoleService rlUserRoleService;
    @Override
    public Menu getMenuById(String menuId) {
        return menuMapper.selectByPrimaryKey(menuId);
    }

    public List<MenuInfo> getAllMenuByRoleId(String userId){
        List<MenuInfo> menuInfoList = new ArrayList<>();
        if(rlUserRoleService.getRoleByUserId(UserUtils.getUserId()).equals("1")){
             menuInfoList.addAll(menuMapper.selectAllMenuRoleIdIs1());
        }else{
            menuInfoList.addAll(menuMapper.selectAllMenuByRoleId(rlUserRoleService.getRoleByUserId(userId)));
        }
        List<MenuInfo> menuInfoList1 = new ArrayList<>();
//        for(MenuInfo menuInfo:menuInfoList ){
//            if(menuInfo.getType()==1){
//                Menu menu = getMenuById(menuInfo.getfMId());
//                MenuInfo menuInfo1=new MenuInfo();
//                menuInfo1.setfMId(menu.getfMId());
//                menuInfo1.setmId(menu.getmId());
//                menuInfo1.setTitle(menu.getmName());
//                menuInfo1.setKey(menu.getPageName());
//                menuInfo1.setType(0);
//                menuInfoList1.add(menuInfo1);
//            }
//            if(menuInfo.getfMId().equals("0")){
//                menuInfo.setSuperKey(null);
//            }else{
//                menuInfo.setSuperKey(getMenuById(menuInfo.getfMId()).getPageName());
//            }
//        }
        List<String> menuIds=new ArrayList<>();
        for (MenuInfo menuInfo:menuInfoList){
            menuIds.add(menuInfo.getmId());
        }
        if(menuIds.size()>0) {
            menuInfoList1 = menuMapper.selectFmenuByMenu(menuIds);
            menuInfoList.addAll(menuInfoList1);
            menuInfoList = Tools.removeDuplicate(menuInfoList);
        }
        return menuInfoList;
    }

    @Override
    public List<Menu> getAllMenu() {
        return menuMapper.selectAllMenu();
    }

    @Override
    public int modifyMenu(Menu menu) {
        return menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public List<String> listPerms(String userId) {
        List<String> menuIds = new ArrayList<>();
        for(MenuInfo menuInfo:getAllMenuByRoleId(userId)){
            menuIds.add(menuInfo.getmId());
        }
        return menuIds;
    }
}
