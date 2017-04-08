package com.yanxiu.ce.system;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yanxiu.ce.system.service.RoleMenuService;
import com.yanxiu.ce.test.junit.SpringJunitTest;

public class TestRoleMenu extends SpringJunitTest {
	
	@Autowired
	private RoleMenuService service;
	
	@Test
	public void selectRolesByUserId(){
		System.out.println(JSON.toJSONString(service.selectMenusByRoleId(1l)));
	}
	


}
