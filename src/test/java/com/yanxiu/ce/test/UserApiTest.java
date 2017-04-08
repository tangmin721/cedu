package com.yanxiu.ce.test;

import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import com.google.common.collect.Maps;
import com.yanxiu.ce.common.utils.HttpClientUtil;
import com.yanxiu.ce.common.utils.code.MD5;
import com.yanxiu.ce.test.junit.SpringJunitTest;

/**
 * sso统一用户
 * @author tangmin
 * @date 2016年8月8日
 */
public class UserApiTest extends SpringJunitTest {
	
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
	 * 同步url
	 */
	@Value("${teachehubei.syncUserUrl}")
	private String  SYNC_URL;
	
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
	 * 注册用户
	 */
	@Test
	public void registerUser(){
		
		Map<String, String> param = Maps.newHashMap();
		param.put("appKey", APP_KEY);
		
		
		
		param.put("password", "e10adc3949ba59abbe56e057f20f883e");
		param.put("passport", "42020200404869111111F");
		param.put("realname", "刘白4868");
		param.put("mobile", "13112345678");
		param.put("email", "12345678@163.com");
		param.put("idCard", "412345198908081001"); 
		param.put("schoolName", "汉阳一中");
		param.put("course", "物理");//高中一年级
		param.put("stage", "高中");//高中
		param.put("grade", "一年级");//物理
		param.put("province", "湖北省");
		param.put("city", "武汉市");  
		param.put("area", "汉阳区"); 
		param.put("gender", "男");//1  男，0女
		
		String doPost = HttpClientUtil.doPost(REGISTER_URL, param );
		System.out.println("::::::::::::::::::::"+doPost);
	} 
	
	
	/**
	 * 同步用户
	 */
	@Test
	public void syncUser(){
		
		Map<String, String> param = Maps.newHashMap();
		
		param.put("appKey", APP_KEY);
		
		param.put("uid", "22473531");
		
		param.put("passport", "4201050000000002");
		param.put("mobile", "13401033333");
		param.put("email", "12345678@163.com");
		param.put("idCard", "412345196812231001");
		param.put("schoolName", "铁山第一小学");
		param.put("grade", "二年级");//高中一年级
		param.put("stage", "小学");//高中
		param.put("course", "语文");//物理
		param.put("province", "湖北省");
		param.put("city", "黄石市"); 
		param.put("area", "铁山区");
		param.put("gender", "男");//1  男，0女
		String doPost = HttpClientUtil.doPost(SYNC_URL, param );
		System.out.println("::::::::::::::::::::"+doPost);
	}
	
	/**
	 * MD5加密
	 */
	@Test
	public void md5(){
		System.out.println(MD5.encode("123456"));
	}
	

	/**
	 * 登录
	 */
	@Test
	public void login(){
		
		Map<String, String> param = Maps.newHashMap();
		param.put("appKey", APP_KEY);
		param.put("loginName", "jijiaotest");
		param.put("password", "e10adc3949ba59abbe56e057f20f883e");
		param.put("crossCallback", "ccb");
		String doPost = HttpClientUtil.doPost(LONGIN_URL, param );
		System.out.println(doPost);
	}
	
	/**
	 * 注销
	 */
	@Test
	public void logout(){
		Map<String, String> param = Maps.newHashMap();
		param.put("appKey", APP_KEY);
		param.put("crossCallback", "ccb");
		String doPost = HttpClientUtil.doPost(LOGOUT_URL, param );
		System.out.println("logout:"+doPost);
	}
	
	
}
