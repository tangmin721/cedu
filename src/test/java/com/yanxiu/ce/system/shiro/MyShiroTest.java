package com.yanxiu.ce.system.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yanxiu.ce.system.enums.UserTypeEnum;
import com.yanxiu.ce.test.junit.SpringJunitTest;

/**
 * 测试shiro
 * @author tangmin
 * @date 2016年3月17日
 */
public class MyShiroTest extends SpringJunitTest{
	
	@Autowired
	private org.apache.shiro.mgt.SecurityManager securityManager;
	
	@Before
	public void login(){
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("admin", "123456");
		try {
			// 4、登录，即身份验证
			subject.login(token);
			System.out.println("=================================================================================================");
			System.out.println("=====================================@Before login()=============================================");
			System.out.println("=================================================================================================");
		} catch (DisabledAccountException e) {
			// 5、身份验证失败
			System.out.println("禁用的帐号");
			e.printStackTrace();
		} catch (UnknownAccountException e) {
			System.out.println("错误的帐号");
			e.printStackTrace();
		} catch (ExcessiveAttemptsException e) {
			System.out.println("登录失败次数过多");
			e.printStackTrace();
		} catch (IncorrectCredentialsException e) {
			System.out.println("错误的凭证");
			e.printStackTrace(); 
		} catch (ExpiredCredentialsException e) {
			System.out.println("过期的凭证");
			e.printStackTrace();
		} catch (AuthenticationException e) {
			// 5、身份验证失败
			System.out.println("父类异常");
			e.printStackTrace();
		}
	}
	
	@Test
	public void assertLogin(){
		Subject subject = SecurityUtils.getSubject();
		Assert.assertEquals(true, subject.isAuthenticated()); // 断言用户已经登录
	}
	
	@Test
	public void hasRole(){
	    Subject subject = SecurityUtils.getSubject();  
	    if(subject.hasRole(UserTypeEnum.CITY_ADMIN.toString())) {  
	        System.out.println("有此角色");
	    } else {  
	        //无权限  
	    	 System.out.println("无此角色");
	    }   
	}
	
	
//	@After
//    public void tearDown() throws Exception {
//		Subject subject = SecurityUtils.getSubject();
//		subject.logout();//退出
//        ThreadContext.unbindSubject();//退出时请解除绑定Subject到线程 否则对下次测试造成影响
//    }
	
	

}
