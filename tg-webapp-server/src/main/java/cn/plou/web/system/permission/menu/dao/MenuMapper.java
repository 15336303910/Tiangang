package cn.plou.web.system.permission.menu.dao;

import cn.plou.web.system.permission.menu.entity.Menu;
import cn.plou.web.system.permission.menu.vo.MenuInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface MenuMapper {
    int deleteByPrimaryKey(String mId);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(String mId);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<MenuInfo> selectAllMenuByRoleId(String roleId);

    List<Menu> selectAllMenu(String roleId);

    List<Menu> selectAllMenuList();

    List<MenuInfo> selectAllMenuRoleIdIs1();

    List<MenuInfo> selectFmenuByMenu(List<String> menuIds);
}