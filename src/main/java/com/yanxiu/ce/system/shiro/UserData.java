package com.yanxiu.ce.system.shiro;

import com.yanxiu.ce.system.entity.User;

/**
 * 自定义一个包装接口
 * @author tangmin
 * @date 2016年3月10日
 */
public interface UserData {

	/**
	 * 取得用户信息
	 *
	 * @return
	 */
	User getUser();
	
	/**
	 * 取得属性
	 * 
	 * @param name
	 * @return
	 */
	Object getAttribute(String name);
	
	/**
	 * 添加属性
	 * 
	 * @param name
	 * @param data
	 */
	void addAttribute(String name,Object data);
}
