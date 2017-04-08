package com.yanxiu.ce.system.sso.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yanxiu.ce.common.core.dto.AjaxCallback;

/**
 * 单点登录系统测试
 * @author tangmin
 * @date 2016年6月1日
 */
@Controller
@RequestMapping("/sso")
public class SSOController {
	
	/**
	 * appKey
	 */
	@Value("${teachehubei.appKey}")
	private String  APP_KEY;
	
	/**
	 * 注册url
	 */
	@Value("${teachehubei.registerUserUrl}")
	private String  REGISTER_URL;
	
	/**
	 * 登录url
	 */
	@Value("${teachehubei.loginUrl}")
	private String  LONGIN_URL;
	
	/**
	 * 退出url
	 */
	@Value("${teachehubei.logoutUrl}")
	private String  LOGOUT_URL;
	
	/**
	 * 测试jsonp返回结果
	 * @param callback
	 * @return
	 * callbackfunc({"data":null,"statusCode":200,"message":"chengg!","tabid":null,
	 * "dialogid":null,"divid":null,"closeCurrent":false,"forward":null,"forwardConfirm":null});
	 */
	@RequestMapping("jsonp")
	@ResponseBody
	public Object list(String callback){
		MappingJacksonValue mjv = new MappingJacksonValue(AjaxCallback.OK("chengg!"));
		mjv.setJsonpFunction(callback);
		return mjv;
	}
	
	
	/**
	 * 系统登录 首页
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public String login(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		System.out.println("username:"+username+",password;"+password);
		return "hello";
	}

}
