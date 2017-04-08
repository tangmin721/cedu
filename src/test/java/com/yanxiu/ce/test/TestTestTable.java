package com.yanxiu.ce.test;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yanxiu.ce.common.utils.DateUtils;
import com.yanxiu.ce.test.dao.TestTableDao;
import com.yanxiu.ce.test.entity.TestTable;
import com.yanxiu.ce.test.entity.TestTableQuery;
import com.yanxiu.ce.test.junit.SpringJunitTest;
import com.yanxiu.ce.test.service.TestTableService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.Rollback;

import java.text.ParseException;
import java.util.Date;
import java.util.List;


/**
 * 测试环境mybaits环境搭建
 * @author tangm
 */
public class TestTestTable extends SpringJunitTest {

	private Logger logger = Logger.getLogger(TestTestTable.class);

	@Autowired
	private TestTableService service;

	@Autowired
	private TestTableDao dao;
	
	@Value("${TEST_KEY}")
	private String TEST_KEY;
	
	/**
	 * 测试日志配置
	 */
	@Test
	public void testLog(){
		logger.debug("你好debug!!!");
		logger.info("你好info!!!");
		logger.debug("你好debug!!!");
		logger.info("你好info!!!");
		logger.debug("你好debug!!!");
		logger.info("你好info!!!");



		logger.warn("你好warn!!!");
		logger.error("你好error!!!");
		System.out.println(TEST_KEY);
	}
	
	/*****************************************************dao************************************************/
	
	/**
	 * dao插入
	 * @throws ParseException
	 */
	@Rollback(true)
	@Test
	public void daoTestInsert() throws ParseException {
		TestTable testTable = getTestTable("1995-01-01", 25, "王五");
		
		System.out.println(":::id::"+ dao.insert(testTable));
	}

	private TestTable getTestTable(String source, int age, String 王五) throws ParseException {
		TestTable testTable = new TestTable();

		Date date = DateUtils.SHORT_DATE_FORMAT.parse(source);
		testTable.setAge(age);
		testTable.setName(王五);
		testTable.setBirthday(date);
		return testTable;
	}

	/**
	 * 批量插入
	 * @throws ParseException
	 */
	@Rollback(true)
	@Test
	public void daoTestInsertBatch() throws ParseException {
		long startTime = System.nanoTime();
		List<TestTable> list = Lists.newArrayList();
		for(int i=1;i<10001;i++){
			TestTable entity = getTestTable("1990-01-01", 18, "张三" + i);
			list.add(entity);
		}
		dao.insertBatch(list);
		System.out.println(System.nanoTime() - startTime);
		/*1秒=1000豪秒 
		1毫秒=1000微秒 
		1微秒=1000毫微秒 
		所以1秒=1000*1000*1000=1000000000毫微秒*/
		//2164967612毫微秒=2.16秒  第一次
		//2327620438   2.3秒  第2次
		//2157578330   2.15秒 第3次
	}
	
	/**
	 * 批量插入(循环10000)
	 * @throws ParseException
	 */
	@Rollback(true)
	@Test
	public void daoTestInsertBatchFor() throws ParseException {
		long startTime = System.nanoTime();
		for(int i=1;i<10001;i++){
			TestTable entity = getTestTable("1990-01-01", 18, "李四" + i);
			dao.insert(entity);
		}
		System.out.println(System.nanoTime() - startTime);
		//8299221344毫微秒=8.29秒  第一次
		//8493227388   8.49秒  第2次
		//8637662643   8.63秒 第3次
	}
	
	
	/**
	 * dao根据id查询
	 */
	@Test
	public void daoTestSelectById() {
		TestTable entity = dao.selectById(10l);
		System.out.println(entity);
	}
	
	/**
	 * dao根据ids批量查询
	 */
	@Test
	public void daoTestSelectByIds() {
		List<Long> ids = Lists.newArrayList(1l,2l,3l);
		List<TestTable> selectByIds = dao.selectByIds(ids);
		System.out.println(selectByIds);
	}
	
	/**
	 * dao更新
	 * @throws ParseException
	 */
	@Rollback(true)
	@Test
	public void daoTestUpdate() throws ParseException {
		TestTable entity = dao.selectById(4l);

		Date date = DateUtils.SHORT_DATE_FORMAT.parse("1998-01-01");
		entity.setAge(15);
		entity.setName("张三111");
		entity.setBirthday(date);
		
		entity.setDeleted(false);
		
		dao.update(entity);
	}

	/**
	 * dao单条记录根据id彻底删除
	 */
	@Rollback(true)
	@Test
	public void daoTestDeleteById() {
		dao.deleteById(1l);
	}
	
	/**
	 * dao多条记录根据ids彻底删除
	 */
	@Rollback(true)
	@Test
	public void daoTestDeleteByIds() {
		List<Long> ids = Lists.newArrayList(1l,2l,3l);
		dao.deleteByIds(ids);
	}
	
	/**
	 * 查询获取所有记录
	 */
	@Test
	public void daoTestSelectList(){
		TestTableQuery query = new TestTableQuery();
		query.setDeleted(1);
		List<TestTable> entitys = dao.selectList(query);
		System.out.println(":::::::::"+entitys);
	}
	
	@Test
	public void daoTestSelectListPage(){
		TestTableQuery query = new TestTableQuery();
		query.setDeleted(1);
		System.out.println(":::::::::"+ dao.selectListPage(query));
	}
	
	/**
	 * 总记录
	 */
	@Test
	public void daoTestSelectListTotal(){
		TestTableQuery query = new TestTableQuery();
		query.setDeleted(1);
		System.out.println(":::::::::"+ dao.selectListTotal(query));
	}
	
	/*********************************************service***************************************************************/
	
	/**
	 * 测试插入，异常捕获，日志打印
	 */
	@Rollback(false)
	@Test
	public void insert(){
		try {
			TestTable testTable = new TestTable();
			Date date = DateUtils.SHORT_DATE_FORMAT.parse("1991-01-01");
			testTable.setAge(181);
			testTable.setName("张三nv");
			testTable.setSex(1);
			testTable.setBirthday(date);

			System.out.println(":::id::"+service.insert(testTable));
		} catch (Exception e) {
			e.printStackTrace(); 
			logger.error(e.getMessage());
		}
	}
	
	@Rollback(true)
	@Test
	public void insertBatch(){
		try {
			List<TestTable> list = Lists.newArrayList();
			for(int i=0;i<100;i++){
				TestTable entity = new TestTable();
				Date date = DateUtils.SHORT_DATE_FORMAT.parse("1991-01-01");
				entity.setAge(18);
				entity.setSex(0);
				entity.setName("李四"+i);
				entity.setBirthday(date);
				list.add(entity);
			}
			Long insertBatch = service.insertBatch(list);
			System.out.println(insertBatch);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void selectById(){
		System.out.println(service.selectById(20003l));
	}
	
	@Test
	public void selectByIds(){
		List<Long> ids = Lists.newArrayList(1l,2l,3l,4l);
		System.out.println(service.selectByIds(ids));
	}
	
	@Rollback(true)
	@Test
	public void update(){
		TestTable entity = service.selectById(1l);
		entity.setName("update:"+entity.getName());
		entity.setAge(50+entity.getAge());
		service.update(entity);
	}
	
	@Rollback(true)
	@Test
	public void updateBatch(){
		TestTable entity1 = service.selectById(1l);
		entity1.setName("update1:张三");
		entity1.setAge(31);
		
		TestTable entity2 = service.selectById(2l);
		entity2.setName("update3:张三");
		entity2.setAge(32);
		
		TestTable entity3 = service.selectById(3l);
		entity3.setName("update2:张三");
		entity3.setAge(33);
		
		List<TestTable> list = Lists.newArrayList(entity1,entity2,entity3);
		
		System.out.println(service.updateBatch(list));
	}
	
	@Rollback(true)
	@Test
	public void deleteById(){
		System.out.println(service.deleteById(11l));
	}
	
	@Rollback(true)
	@Test
	public void deleteByIds(){
		List<Long> ids = Lists.newArrayList(11l,12l,13l,14l);
		System.out.println(service.deleteByIds(ids));
	}
	
	
	@Test
	public void selectList(){
		TestTableQuery query = new TestTableQuery();
		System.out.println(service.selectList(query));
	}
	
	@Test
	public void selectListPage(){
		TestTableQuery query = new TestTableQuery();
		System.out.println(service.selectListPage(query));
	}
	
	@Test
	public void selectListTotal(){
		TestTableQuery query = new TestTableQuery();
		query.setDeleted(1);
		System.out.println(service.selectListTotal(query));
	}
	
	@Test
	public void selectListPagination(){
		TestTableQuery query = new TestTableQuery();
		query.setPageCurrent(2l);
		query.setPageSize(5l);
		System.out.println(JSON.toJSONString(service.selectListPagination(query)));
	}
	
	/******************************************测试validate*********************************************************/

	
	@Test
	public void testAop(){
		service.testAop();
	}
	
	@Test
	public void testAopBase(){
		service.selectById(1l);
	}
	
	@Test
	@Rollback(false)
	public void testTra(){
		service.testTra();
	}

	/** 
	 * 测试插入，异常捕获，日志打印
	 */
	@Test
	@Rollback(false)
	public void insert2(){
			TestTable testTable = new TestTable();
			testTable.setAge(19);
			testTable.setName("张三nv");
			testTable.setSex(1);

			System.out.println(":::id::"+service.insert(testTable));
		
	}
}
