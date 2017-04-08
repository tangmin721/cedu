package com.yanxiu.ce.system.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.yanxiu.ce.common.yanxiuapi.PassportHelper;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.service.UserService;

/**
 * 单点登录sso过滤器
 * @author tangmin
 * @date 2016年9月5日
 */
public class YanxiuSSOFilter extends AuthorizationFilter{
	
	@Autowired
	private UserService userService;

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		String passport = PassportHelper.getPassport((HttpServletRequest)request);
		System.out.println("进来了！passport:"+passport);
		
		passport = "4201050000000002";//服务器已获取
		if(StringUtils.isBlank(passport)){
			return false;
		}
		
		User user = userService.selectByLoginName(passport);
		
		
		if(user!=null && user.getLoginName().endsWith(passport)){
			userService.authenticateSSO(passport);
			return true;
		}
		return false;
	}

}
