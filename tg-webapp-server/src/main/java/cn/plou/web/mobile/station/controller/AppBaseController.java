package cn.plou.web.mobile.station.controller;

import cn.plou.web.common.config.common.Cond;
import cn.plou.web.common.config.common.Constant;
import cn.plou.web.common.config.common.Root;

/**
 * @Project : tg-webapp-server
 * @File : AppBaseController.java
 * @Author : WangJiWei
 * @Date : 2018年9月19日上午10:22:59
 *
 * @Comments :
 * 
 */
public class AppBaseController {

    public Root success(Object data) {
	Root r = new Root();
	r.setData(data);
	return r;
    }
    
    public Root success(Object data,Cond cond) {
	Root r = new Root();
	r.setData(data);
	r.setCond(cond);
	return r;
    }

    public Root success() {
	return new Root();
    }

    public Root error(String msg) {
	Root r = new Root();
	r.setCode(Constant.DEFAULT_ERROR_STATUS_CODE);
	r.setMsg(msg);
	return r;
    }
}
