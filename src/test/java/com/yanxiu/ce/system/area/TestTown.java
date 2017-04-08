package com.yanxiu.ce.system.area;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yanxiu.ce.system.entity.Town;
import com.yanxiu.ce.system.entity.TownQuery;
import com.yanxiu.ce.system.service.TownService;
import com.yanxiu.ce.test.junit.SpringJunitTest;

public class TestTown extends SpringJunitTest {
	
	@Autowired
	private TownService service;

	@Rollback(true)
	@Test
	public void testInsert() {
		Town entity = new Town();
		//@ToDo
		System.out.println("testInsert==>"+service.insert(entity));
	}

	@Rollback(true)
	@Test
	public void testInsertBatch() {
		List<Town> list = Lists.newArrayList();
		for(int i=0;i<3150;i++){
			Town entity = new Town();
			entity.setSeq(0);
			entity.setTownNo(111111);
			entity.setTownName("222222");
			entity.setCityNo(333333);
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
		Town entity = service.selectById(21l);
		//@Todo
		System.out.println("testUpdate==>"+service.update(entity));
	}

	@Rollback(true)
	@Test
	public void testUpdateBatch() {
		Town entity1 = service.selectById(41l);
		//@TODO
		
		Town entity2 = service.selectById(42l);
		//@TODO
		
		Town entity3 = service.selectById(43l);
		
		//测试锁
		//service.update(entity3);
		//@TODO
		
		List<Town> list = Lists.newArrayList(entity1,entity2,entity3);
		
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
		TownQuery query = new TownQuery();
		query.setDeleted(1);
		System.out.println("testSelectList==>"+service.selectList(query));
	}

	@Test
	public void testSelectListPage() {
		TownQuery query = new TownQuery();
		//@TODO
		System.out.println("testSelectListPage==>"+service.selectListPage(query));
	}

	@Test
	public void testSelectListTotal() {
		TownQuery query = new TownQuery();
		query.setDeleted(1);
		System.out.println("testSelectListTotal==>"+service.selectListTotal(query));
	}

	@Test
	public void testSelectListPagination() {
		TownQuery query = new TownQuery();
		query.setPageCurrent(2l);
		query.setPageSize(5l);
		System.out.println("testSelectListTotal==>"+JSON.toJSONString(service.selectListPagination(query)));
	}

}
