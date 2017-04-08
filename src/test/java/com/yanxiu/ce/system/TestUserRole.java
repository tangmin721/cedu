package com.yanxiu.ce.system;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yanxiu.ce.system.service.UserRoleService;
import com.yanxiu.ce.test.junit.SpringJunitTest;

public class TestUserRole extends SpringJunitTest {
	
	@Autowired
	private UserRoleService service;
	
	@Test
	public void selectRolesByUserId(){
		System.out.println(JSON.toJSONString(service.selectRolesByUserId(1l)));
	}
	
	@Test
	public void selectNotRolesByUserId(){
		System.out.println(JSON.toJSONString(service.selectNotRolesByUserId(1l)));
	}
	
	@Test
	public void selectUsersByRoleId(){
		System.out.println(JSON.toJSONString(service.selectUsersByRoleId(1l)));
	}
	
	@Test
	public void selectNotUsersByRoleId(){
		System.out.println(JSON.toJSONString(service.selectNotUsersByRoleId(1l)));
	}
	
	


}
