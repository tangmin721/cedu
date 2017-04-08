package com.yanxiu.ce.system.shiro;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.yanxiu.ce.system.entity.User;

/**
 * 封装一个shirouser对象
 * @author tangmin
 * @date 2016年3月10日
 */
public class ShiroUser implements UserData,Serializable{

	private static final long serialVersionUID = 2629457773001669658L;

	public final static String USER_MENU_ATTRIBUTE = "USER-MENU";
	
	private Long id;
	private String name;
	private String loginName;
	private String ipAddress;
	
	private User user;
	
	/**
	 * 菜单id列表
	 */
	private Set<Long> hasMenus = Sets.newHashSet();
	
	//证书
	private Credentials credentials;
	
	/**
	 * 加入更多的自定义参数
	 */
	private Map<String, Object> attribute = Maps.newHashMap();

	public ShiroUser(){
		
	}
	
	public ShiroUser(String loginName) {
		this.loginName = loginName;
	}
	
	public ShiroUser(Long id,String loginName , String name) {
		this.id = id;
		this.loginName = loginName;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Map<String, Object> getAttribute() {
		return attribute;
	}

	public void setAttribute(Map<String, Object> attribute) {
		this.attribute = attribute;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addAttribute(String name,Object obj){
		attribute.put(name, obj);
	}
	
	public Object getAttribute(String name) {
		return attribute.get(name);
	}
	
	public Object removeAttribute(String name) {
		return attribute.remove(name);
	}
	
	public Map<String, Object> getAttributes() {
		return this.attribute;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public Set<Long> getHasMenus() {
		return hasMenus;
	}

	public void setHasMenus(Set<Long> hasMenus) {
		this.hasMenus = hasMenus;
	}
	
}
