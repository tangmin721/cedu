package com.yanxiu.ce.system.security;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.shiro.ShiroUser;


/**
 * shiro工具类
 * @author tangmin
 * @date 2016年3月10日
 */
public abstract class ShiroUtils {
	public static User getCurrentUser() {
		return (User) getSession().getAttribute("currentUser");
	}
	
	public static ShiroUser getShiroUser() {
		Subject subject = getSubject();
		ShiroUser shiroUser = (ShiroUser)subject.getPrincipal();
		return shiroUser;
	}

	public static Subject getSubject() {
		return org.apache.shiro.SecurityUtils.getSubject();
	}
	
	public static Session getSession(){
		return getSubject().getSession();
	}
	
}
