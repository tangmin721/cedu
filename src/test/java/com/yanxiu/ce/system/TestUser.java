package com.yanxiu.ce.system;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.entity.UserQuery;
import com.yanxiu.ce.system.enums.UserStatusEnum;
import com.yanxiu.ce.system.enums.UserTypeEnum;
import com.yanxiu.ce.system.service.UserService;
import com.yanxiu.ce.test.junit.SpringJunitTest;

public class TestUser extends SpringJunitTest {
	
	@Autowired
	private UserService service;

	@Rollback(true)
	@Test
	public void testInsert() {
		User entity = new User();
		entity.setLoginName("admin");
		entity.setLoginPwd("123456");
		entity.setMobileNo("13401031397");
		entity.setEmail("191102902@qq.com");
		entity.setRealName("超级管理员");
		entity.setType(UserTypeEnum.SUP_ADMIN.getValue());
		entity.setStatus(UserStatusEnum.ACTIVED.getValue());
		System.out.println("testInsert==>"+service.saveOrUpdate(entity));
	}

	@Rollback(true)
	@Test
	public void testInsertBatch() {
		List<User> list = Lists.newArrayList();
		for(int i=1;i<101;i++){
			User entity = new User();
			//@ToDo
			list.add(entity);
		}
		System.out.println("testInsertBatch==>"+service.insertBatch(list));
	}

	/**
	 * 测试mybatis缓存
	 */
	@Test
	public void testSelectById() {
		
		System.out.println("testSelectById==>"+service.selectById(11l));
		System.out.println("testSelectById==>"+service.selectById(12l));
		System.out.println("testSelectById==>"+service.selectById(12l));
		
		System.out.println("testSelectById==>"+service.selectById(11l));
		System.out.println("testSelectById==>"+service.selectById(12l));
		System.out.println("testSelectById==>"+service.selectById(12l));
	}

	@Test
	public void testSelectByIds() {
		List<Long> ids = Lists.newArrayList(11l,12l,13l,14l);
		
		System.out.println("testSelectByIds==>"+service.selectByIds(ids));
		
		System.out.println("testSelectById==>"+service.selectById(11l));
		System.out.println("testSelectById==>"+service.selectById(12l));
	}

	@Rollback(true)
	@Test
	public void testUpdate() {
		User entity = service.selectById(21l);
		//@Todo
		System.out.println("testUpdate==>"+service.update(entity));
	}

	@Rollback(true)
	@Test
	public void testUpdateBatch() {
		User entity1 = service.selectById(41l);
		//@TODO
		
		User entity2 = service.selectById(42l);
		//@TODO
		
		User entity3 = service.selectById(43l);
		
		//测试锁
		//service.update(entity3);
		//@TODO
		
		List<User> list = Lists.newArrayList(entity1,entity2,entity3);
		
		System.out.println("testUpdateBatch==>"+service.updateBatch(list));
	}

	@Rollback(true)
	@Test
	public void testDeleteById() {
		System.out.println("testDeleteById==>"+service.deleteById(51l));
	}

	@Rollback(true)
	@Test
	public void testDeleteByIds() {
		List<Long> ids = Lists.newArrayList(51l,52l,53l,54l);
		System.out.println("testDeleteById==>"+service.deleteByIds(ids));
	}

	@Test
	public void testSelectList() {
		UserQuery query = new UserQuery();
		query.setDeleted(1);
		System.out.println("testSelectList==>"+service.selectList(query));
	}

	@Test
	public void testSelectListPage() {
		UserQuery query = new UserQuery();
		//@TODO
		System.out.println("testSelectListPage==>"+service.selectListPage(query));
	}

	@Test
	public void testSelectListTotal() {
		UserQuery query = new UserQuery();
		query.setDeleted(1);
		System.out.println("testSelectListTotal==>"+service.selectListTotal(query));
	}

	@Test
	public void testSelectListPagination() {
		UserQuery query = new UserQuery();
		query.setPageCurrent(2l);
		query.setPageSize(5l);
		System.out.println("testSelectListTotal==>"+JSON.toJSONString(service.selectListPagination(query)));
	}

}
