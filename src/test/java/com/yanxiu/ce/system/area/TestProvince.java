package com.yanxiu.ce.system.area;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yanxiu.ce.system.entity.Province;
import com.yanxiu.ce.system.entity.ProvinceQuery;
import com.yanxiu.ce.system.service.ProvinceService;
import com.yanxiu.ce.test.junit.SpringJunitTest;

public class TestProvince extends SpringJunitTest {
	
	@Autowired
	private ProvinceService service;

	@Rollback(true)
	@Test
	public void testInsert() {
		Province entity = new Province();
		//@ToDo
		System.out.println("testInsert==>"+service.insert(entity));
	}

	@Rollback(true)
	@Test
	public void testInsertBatch() {
		List<Province> list = Lists.newArrayList();
		for(int i=0;i<34;i++){
			Province entity = new Province();
			entity.setProvinceNo(111111);
			entity.setProvinceName("222222");
			entity.setSeq(0);
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
		Province entity = service.selectById(21l);
		//@Todo
		System.out.println("testUpdate==>"+service.update(entity));
	}

	@Rollback(true)
	@Test
	public void testUpdateBatch() {
		Province entity1 = service.selectById(41l);
		//@TODO
		
		Province entity2 = service.selectById(42l);
		//@TODO
		
		Province entity3 = service.selectById(43l);
		
		//测试锁
		//service.update(entity3);
		//@TODO
		
		List<Province> list = Lists.newArrayList(entity1,entity2,entity3);
		
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
		ProvinceQuery query = new ProvinceQuery();
		query.setDeleted(1);
		System.out.println("testSelectList==>"+service.selectList(query));
	}

	@Test
	public void testSelectListPage() {
		ProvinceQuery query = new ProvinceQuery();
		//@TODO
		System.out.println("testSelectListPage==>"+service.selectListPage(query));
	}

	@Test
	public void testSelectListTotal() {
		ProvinceQuery query = new ProvinceQuery();
		query.setDeleted(1);
		System.out.println("testSelectListTotal==>"+service.selectListTotal(query));
	}

	@Test
	public void testSelectListPagination() {
		ProvinceQuery query = new ProvinceQuery();
		query.setPageCurrent(2l);
		query.setPageSize(5l);
		System.out.println("testSelectListTotal==>"+JSON.toJSONString(service.selectListPagination(query)));
	}

}
