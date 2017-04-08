package com.yanxiu.ce.system;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.entity.DictItemQuery;
import com.yanxiu.ce.system.service.DictItemService;
import com.yanxiu.ce.test.junit.SpringJunitTest;

public class TestDictItem extends SpringJunitTest {
	
	@Autowired
	private DictItemService service;

	@Rollback(true)
	@Test
	public void testInsert() {
		DictItem entity = new DictItem();
		//@ToDo
		System.out.println("testInsert==>"+service.insert(entity));
	}

	@Rollback(true)
	@Test
	public void testInsertBatch() {
		List<DictItem> list = Lists.newArrayList();
		for(int i=1;i<88;i++){
			DictItem entity = new DictItem();
			entity.setSeq(i);
			entity.setCatlogId(198l);
			entity.setName("字典198项"+i);
			entity.setCode("198ITEMCODE"+i);
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
		DictItem entity = service.selectById(21l);
		//@Todo
		System.out.println("testUpdate==>"+service.update(entity));
	}

	@Rollback(true)
	@Test
	public void testUpdateBatch() {
		DictItem entity1 = service.selectById(41l);
		//@TODO
		
		DictItem entity2 = service.selectById(42l);
		//@TODO
		
		DictItem entity3 = service.selectById(43l);
		
		//测试锁
		//service.update(entity3);
		//@TODO
		
		List<DictItem> list = Lists.newArrayList(entity1,entity2,entity3);
		
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
		DictItemQuery query = new DictItemQuery();
		query.setDeleted(1);
		System.out.println("testSelectList==>"+service.selectList(query));
	}

	@Test
	public void testSelectListPage() {
		DictItemQuery query = new DictItemQuery();
		//@TODO
		System.out.println("testSelectListPage==>"+service.selectListPage(query));
	}

	@Test
	public void testSelectListTotal() {
		DictItemQuery query = new DictItemQuery();
		query.setDeleted(1);
		System.out.println("testSelectListTotal==>"+service.selectListTotal(query));
	}

	@Test
	public void testSelectListPagination() {
		DictItemQuery query = new DictItemQuery();
		query.setPageCurrent(2l);
		query.setPageSize(5l);
		System.out.println("testSelectListTotal==>"+JSON.toJSONString(service.selectListPagination(query)));
	}

}
