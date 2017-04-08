package com.yanxiu.ce.system;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yanxiu.ce.system.entity.Menu;
import com.yanxiu.ce.system.entity.MenuQuery;
import com.yanxiu.ce.system.service.MenuService;
import com.yanxiu.ce.test.junit.SpringJunitTest;

public class TestMenu extends SpringJunitTest {
	
	@Autowired
	private MenuService service;

	@Rollback(true)
	@Test
	public void testInsert() {
		Menu entity = new Menu();//7
		entity.setName("教师管理");
		entity.setFaicon("cog");
		entity.setTabid("teacher-manager");
		System.out.println("testInsert==>"+service.insert(entity));
	}
	
	@Rollback(true)
	@Test
	public void initMenu() {
		Menu entity = new Menu();//1
		entity.setName("系统管理");
		entity.setFaicon("cog");
		entity.setTabid("system-manager");
		System.out.println("testInsert==>"+service.insert(entity));
		
		Menu entity1 = new Menu();//2
		entity1.setName("系统配置");
		entity1.setFaicon("cog");
		entity1.setTabid("system-config");
		entity1.setPid(1l);
		service.insert(entity1);
		
		Menu entity11 = new Menu();//3
		entity11.setName("字典管理");
		entity11.setFaicon("cog");
		entity11.setTabid("system-dict-dict");
		entity11.setPid(2l);
		entity11.setUrl("system/dict/dict");
		service.insert(entity11);
		
		Menu entity12 = new Menu();//4
		entity12.setName("导航菜单");
		entity12.setFaicon("cog");
		entity12.setTabid("system-menu-menu");
		entity12.setPid(2l);
		entity12.setUrl("system/menu/menu");
		service.insert(entity12);
		/************************/
		Menu entity2 = new Menu();//5
		entity2.setName("用户角色权限");
		entity2.setFaicon("users");
		entity2.setPid(1l);
		service.insert(entity2);
		
		Menu entity21 = new Menu();//6
		entity21.setName("角色管理");
		entity21.setFaicon("users");
		entity21.setTabid("system-role");
		entity21.setPid(5l);
		entity21.setUrl("system/role/roleList");
		service.insert(entity21);
		
		
	}

	@Rollback(true)
	@Test
	public void testInsertBatch() {
		List<Menu> list = Lists.newArrayList();
		for(int i=1;i<1001;i++){
			Menu entity = new Menu();
			entity.setSeq(i);;
			entity.setPid(18l);
			entity.setName("测试"+i);
			entity.setFaicon("image");
			list.add(entity);
		}
		System.out.println("testInsertBatch==>"+service.insertBatch(list));
	}

	/**
	 * 测试mybatis缓存
	 */
	@Test
	public void testSelectById() {
		
		System.out.println("testSelectById==>"+service.selectById(1l));
	}
	
	
//	@Test
//	public void selectAllByPid() {
//		
//		System.out.println("selectAllByPid==>"+service.selectAllByPid(null));
//	}
//	
//	@Test
//	public void loadTreeNodes() {
//		
//		System.out.println("loadTreeNodes==>"+service.loadTreeNodes(null));
//	}
	/**
	 * 测试mybatis缓存
	 */
	@Test
	public void testSelectByPId() {
		
		System.out.println("testSelectById==>"+service.selectByPid(null));
		System.out.println("testSelectById==>"+service.selectByPid(1l));
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
		Menu entity = service.selectById(21l);
		//@Todo
		System.out.println("testUpdate==>"+service.update(entity));
	}

	@Rollback(true)
	@Test
	public void testUpdateBatch() {
		Menu entity1 = service.selectById(41l);
		//@TODO
		
		Menu entity2 = service.selectById(42l);
		//@TODO
		
		Menu entity3 = service.selectById(43l);
		
		//测试锁
		//service.update(entity3);
		//@TODO
		
		List<Menu> list = Lists.newArrayList(entity1,entity2,entity3);
		
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
		MenuQuery query = new MenuQuery();
		query.setDeleted(1);
		System.out.println("testSelectList==>"+service.selectList(query));
	}

	@Test
	public void testSelectListPage() {
		MenuQuery query = new MenuQuery();
		//@TODO
		System.out.println("testSelectListPage==>"+service.selectListPage(query));
	}

	@Test
	public void testSelectListTotal() {
		MenuQuery query = new MenuQuery();
		query.setDeleted(1);
		System.out.println("testSelectListTotal==>"+service.selectListTotal(query));
	}

	@Test
	public void testSelectListPagination() {
		MenuQuery query = new MenuQuery();
		query.setPageCurrent(2l);
		query.setPageSize(5l);
		System.out.println("testSelectListTotal==>"+JSON.toJSONString(service.selectListPagination(query)));
	}
	
//	@Test
//	public void testLoadTreeNodes(){
//		System.out.println(service.loadTreeNodes(0l));
//	}

	/**
	 * 清除menu缓存
	 */
	@Test
	public void removeMenuCache(){
		service.removeMenuCache();
	}
}
