package com.yanxiu.ce.test.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yanxiu.ce.common.constant.AppConstant;
import com.yanxiu.ce.common.core.controller.BaseController;
import com.yanxiu.ce.common.core.entity.OrderField;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.common.utils.DateUtils;
import com.yanxiu.ce.test.entity.TestTable;
import com.yanxiu.ce.test.entity.TestTableQuery;
import com.yanxiu.ce.test.service.TestTableService;

/**
 * 测试框架搭建
 * @author tangmin
 * @date 2016年2月14日
 */
@Controller
@RequestMapping("/test")
public class TestTableController extends BaseController{
	
	@Autowired
	private TestTableService service;
	
	@RequestMapping("/list")
	public String list() {
		return "test/list";
	}
	
	
	
	@RequestMapping("/list.json")
	@ResponseBody
	public String listJson(
			@RequestParam(value="pageCurrent",required=false,defaultValue="1") Long pageCurrent,
			@RequestParam(value="pageSize",required=false,defaultValue=AppConstant.PAGE_SIZE) Long pageSize,
			@RequestParam(value="orderField",required=false,defaultValue="id") String orderField,
			@RequestParam(value="orderDirection",required=false,defaultValue="asc") String orderDirection
			){
		TestTableQuery query = new TestTableQuery();
		//分页
		query.setPageCurrent(pageCurrent);
		query.setPageSize(pageSize);
		//排序
		List<OrderField> orderFields = Lists.newArrayList();
		orderFields.add(new OrderField(orderField, orderDirection));
		//可同时多列排序	orderFields.add(new OrderField("id", "asc"));
		query.setOrderFields(orderFields );
		
		Pagination<TestTable> page = service.selectListPagination(query);
		return JSON.toJSONString(page);
	}
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	@ResponseBody
	public String save(@Valid TestTable entity,BindingResult result){
		
		return JSON.toJSONString(null);
		
	}

	@RequestMapping("save.json")
	@ResponseBody
	public String save(@RequestParam(value="json",required=false,defaultValue="1") Long json){
		
		return JSON.toJSONString(null);
		
	}
	
	
	/**
	 * 弹出input框  测试异常
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value="testCommonEx",method=RequestMethod.GET)
	public String testCommonEx()throws ParseException{
		TestTable testTable = new TestTable();
		Date date = DateUtils.SHORT_DATE_FORMAT.parse("1991-01-01");
		testTable.setAge(18);
		testTable.setName("张三nv");
		testTable.setSex(12);
		testTable.setBirthday(date);
		service.insert(testTable);
		
//		if(true){
//			throw new UsernameNotMatchPasswordException(70010001,"usernm-passwd说点什么异常");
//		}
		
		return "test/testEx";
	}
	
	/**
	 * 弹出input框  测试异常
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("testAjaxEx")
	@ResponseBody
	public String testAjaxEx()throws ParseException{
		
		TestTable testTable = new TestTable();
		Date date = DateUtils.SHORT_DATE_FORMAT.parse("1991-01-01");
		testTable.setAge(18);
		//testTable.setName("张三nv");
		testTable.setSex(12);
		testTable.setBirthday(date);
		service.insert(testTable);
		
//		if(true){
//			throw new UsernameNotMatchPasswordException(70010001,"usernm-passwd说点什么异常");
//		}
		
//		if(true){
//			throw new RuntimeException("RuntimeException说点什么异常");
//		}
		
		return "test/testEx";
	}
	
	
}
