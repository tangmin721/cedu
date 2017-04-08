package com.yanxiu.ce.common.yanxiuapi;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie读写工具类
 * 
 * @author 赵士昌<zhaoshichang@gmail.com> 2011-9-30
 */
public class CookieUtil {

	/**
	 * 
	 * @param key
	 * @param value
	 * @param response
	 * @throws Exception
	 */
	public static void setCookie(String key, String value, HttpServletResponse response) throws Exception {
		setCookie(key, value, -10, response);
	}
	/**
	 * 
	 * @param key     关键字
	 * @param value   值
	 * @param age     保存时间
	 * @param path    目录
	 * @param domain  域名
	 * @param response 响应
	 * @throws Exception  
	 */
	public static void setCookie(String key, String value, int age, String path, String domain, HttpServletResponse response) throws Exception {
		Cookie cookie = new Cookie(key, value);
		cookie.setPath(path);
		cookie.setDomain(domain);
		cookie.setMaxAge(age);
		response.addCookie(cookie);
	}
	/**
	 * 
	 * @param key    	关键字
	 * @param value  	值
	 * @param age    	保存时间
	 * @param response  响应
	 * @throws Exception
	 */
	public static void setCookie(String key, String value, int age, HttpServletResponse response) throws Exception {
		Cookie cookie = new Cookie(key, value);
		cookie.setPath("/");
		cookie.setDomain("yanxiu.com");
		cookie.setMaxAge(age);
		response.addCookie(cookie);
	}

	/**
	 * 清掉cookie
	 * @param key
	 * @param response
	 * @throws Exception
	 */
	public static void killCookie(String key, HttpServletResponse response) throws Exception {
		Cookie cookie = new Cookie(key, null);
		cookie.setPath("/");
		cookie.setDomain("yanxiu.com");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}
	/**
	 * 删除cookie,指定域和目录
	 * @param key
	 * @param response
	 * @throws Exception
	 */
	public static void killCookie(String key, String domain, String path, HttpServletResponse response) throws Exception {
		Cookie cookie = new Cookie(key, null);
		cookie.setPath(path);
		cookie.setDomain(domain);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}

	
	/**
	 * 获取cookie值
	 * @param key
	 * @param request
	 * @return
	 */
	public static String getCookieValue(String key, HttpServletRequest request) {
		try {
			Cookie cookies[] = request.getCookies();
			for (int i = 0; i < cookies.length; i++)
				if (cookies[i].getName().equals(key))
					return cookies[i].getValue();

			return null;
		} catch (Exception ex) {
			return null;
		}
	}
}