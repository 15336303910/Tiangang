package cn.plou.web.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;

import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.common.config.common.Constant;
import cn.plou.web.system.baseMessage.company.entity.Company;

public class UserUtils {
//    public static String getUserId(){
//        return getCurrentUser().getUserCode();
//    }
//    public static UserLogin getCurrentUser() {
//        try {
//            Subject subject = SecurityUtils.getSubject();
//            UserLogin userLogin = (UserLogin) subject.getPrincipal();
//            if (userLogin == null) {
//                throw new BusinessException(Constant.NO_LOGIN_ERROR, "您的登录身份已过期，请重新登录");
//            }
//            return userLogin;
//        } catch (UnavailableSecurityManagerException | InvalidSessionException e) {
//            throw new BusinessException(Constant.NO_LOGIN_ERROR, "请重新登录");
//        }
//    }
   /* public static String getUserId(){
        return Constant.userCode;
    }
    public static UserLogin getCurrentUser() {
        try {
            UserLogin userLogin = new UserLogin();
            userLogin.setUserCode("00001");
            userLogin.setUserPsw("string");
            userLogin.setUsername("string");
            if (userLogin == null) {
                throw new BusinessException(Constant.NO_LOGIN_ERROR, "您的登录身份已过期，请重新登录");
            }
            return userLogin;
        } catch (UnavailableSecurityManagerException | InvalidSessionException e) {
            throw new BusinessException(Constant.NO_LOGIN_ERROR, "请重新登录");
        }
    }*/
   public static String getUserId(){
       try {
           Subject subject = SecurityUtils.getSubject();
           String userCode = (String)subject.getPrincipal();
           //System.out.println("======================================="+subject.getPrincipal());
           if (userCode == null) {
               throw new BusinessException(Constant.NO_LOGIN_ERROR, "您的登录身份已过期，请重新登录");
           }
           return userCode;
           //return "00001";
       } catch (UnavailableSecurityManagerException | InvalidSessionException e) {
           throw new BusinessException(Constant.NO_LOGIN_ERROR, "请重新登录");
       }
   }
    /*public static String getCurrentUser() {
        try {
            Subject subject = SecurityUtils.getSubject();
            String userLogin = (String)subject.getPrincipal();
            if (userLogin == null) {
                throw new BusinessException(Constant.NO_LOGIN_ERROR, "您的登录身份已过期，请重新登录");
            }
            return userLogin;
        } catch (UnavailableSecurityManagerException | InvalidSessionException e) {
            throw new BusinessException(Constant.NO_LOGIN_ERROR, "请重新登录");
        }
    }*/
   private static Map<String, List<Company>> userCompany = new ConcurrentHashMap<>();
   
   public static List<Company> getUserCompany(String userCode){
  	 return userCompany.get(userCode);
 }
   
}
