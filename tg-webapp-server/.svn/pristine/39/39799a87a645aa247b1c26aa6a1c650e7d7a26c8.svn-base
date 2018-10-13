package cn.plou.web.common.shiro;
import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

import cn.plou.web.common.config.common.Constant;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.system.permission.login.token.TokenManager;
import cn.plou.web.system.permission.login.token.TokenModel;

/**
 * 访问控制过滤器
 */
public class StatelessAccessControlFilter extends AccessControlFilter{
//public class StatelessAccessControlFilter{

    @Autowired
    private TokenManager manager;

   /**
     * 先执行：isAccessAllowed 再执行onAccessDenied
     * isAccessAllowed：表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，
     * 如果允许访问返回true，否则false；
     * 如果返回true的话，就直接返回交给下一个filter进行处理。
     * 如果返回false的话，回往下执行onAccessDenied
     */

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)throws Exception {
        return false;
    }

    /**
     * onAccessDenied：表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；
     * 如果返回false表示该拦截器实例已经处理了，将直接返回即可。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        String authorization = ((HttpServletRequest)request).getHeader(Constant.AUTHORIZATION);

        TokenModel tokenModel = manager.getToken(authorization);
        boolean success = manager.checkToken(tokenModel);

        if (!success){
            onLoginFail(response);
            return false;
        }
        try {
            //5、委托给Realm进行登录
            UsernamePasswordToken token  = new UsernamePasswordToken(tokenModel.getUserId(), tokenModel.getToken());
            getSubject(request, response).login(token);
        } catch (Exception e) {
            e.printStackTrace();
            //6、登录失败
            onLoginFail(response);
            return false;//就直接返回给请求者.
        }
        return true;
    }

    //登录失败时默认返回401 状态码
    private void onLoginFail(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        //httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Root root = new Root();
        root.setCode(401);
        root.setMsg("身份认证口令不存在或已过期！");
        //httpResponse.getWriter().write("login error");
        httpResponse.getWriter().write(JSON.toJSONString(root));
    }

}
