package cn.plou.web.common.shiro;

import java.util.HashSet;
import java.util.List;

import cn.plou.web.common.utils.RedisUtil;
import cn.plou.web.common.utils.SerializeUtil;
import cn.plou.web.system.permission.menu.service.MenuService;
import cn.plou.web.system.permission.userLogin.entity.UserLogin;
import cn.plou.web.system.permission.userLogin.service.UserLoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import cn.plou.web.common.config.ApplicationContextRegister;
import org.springframework.http.server.ServerHttpResponse;
import redis.clients.jedis.Jedis;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserLoginService userService;
    @Autowired
    MenuService menuService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        String userId = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        RedisUtil redisUtil = RedisUtil.getSingleton();
        Jedis jedis = null;
        try {
            jedis = redisUtil.getJedis();
            if (jedis.exists(userId) && jedis.ttl(userId) < 2 * 60 * 60L) {
                jedis.expire(userId, 2 * 60 * 60);
            }
                List<String> perms = SerializeUtil.unserializeForList(jedis.get("perms".getBytes()));
                info.setStringPermissions(new HashSet(perms));
                return info;
        } finally {
            RedisUtil.returnSource(jedis);
        }
//		MenuService menuService = ApplicationContextRegister.getBean(MenuService.class);
//		List<String> perms = menuService.listPerms(userId);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userId = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        //String password = token.getCredentials().toString();
        UserLogin user = userService.getUserLoginById(userId);
        //String userName = user.getUsername();
        // 账号不存在
        if (user == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }
        // 密码错误
//		if (!password.equals(user.getUserPsw())) {
//			throw new IncorrectCredentialsException("账号或密码不正确");
//		}
        //
        // 账号锁定
        if (user.getStatus().equals("0")) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userId, password, getName());
        return info;
    }

}
