package com.yanxiu.ce.system.controller;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.google.common.collect.Lists;
import com.yanxiu.ce.common.constant.AppConstant;
import com.yanxiu.ce.common.core.controller.BaseController;
import com.yanxiu.ce.common.core.controller.QueryUtils;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.OrderField;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.system.entity.DictCatlog;
import com.yanxiu.ce.system.entity.DictCatlogQuery;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.entity.DictItemQuery;
import com.yanxiu.ce.system.service.DictCatlogService;
import com.yanxiu.ce.system.service.DictItemService;

/**
 * 字典管理,包括字典目录，字典项
 * @author tangmin
 * @date 2016年2月23日
 */
@Controller
@RequestMapping("/system/dictDg")
public class DictDataGridController extends BaseController{
	
	@Autowired
	private DictCatlogService dictCatlogService;
	
	@Autowired
	private DictItemService dictItemService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("list")
	public String list(){
		return "system/dict/list-dg";
	}
	
	/**
	 * list页面 加载catlogList
	 * @param pageCurrent
	 * @param pageSize
	 * @param orderField
	 * @param orderDirection
	 * @return
	 */
	@RequestMapping("catlogList.json")
	@ResponseBody
	public String catlogList(@RequestParam(value="pageCurrent",required=false,defaultValue="1") Long pageCurrent,
			@RequestParam(value="pageSize",required=false,defaultValue=AppConstant.PAGE_SIZE) Long pageSize,
			@RequestParam(value="orderField",required=false,defaultValue="id") String orderField,
			@RequestParam(value="orderDirection",required=false,defaultValue="asc") String orderDirection,
			HttpServletRequest request){
		DictCatlogQuery query = createQueryObjFormRequest(request);
		//分页
		query.setPageCurrent(pageCurrent);
		query.setPageSize(pageSize);
		//排序
		List<OrderField> orderFields = Lists.newArrayList();
		orderFields.add(new OrderField(orderField, orderDirection));
		//可同时多列排序	orderFields.add(new OrderField("id", "asc"));
		query.setOrderFields(orderFields );
		
		Pagination<DictCatlog> page = dictCatlogService.selectListPagination(query);
		
		//测试fastjson过滤数据
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
		filter.getExcludes().add("pageStart");
		filter.getExcludes().add("totalPage");
		return JSON.toJSONString(page,filter);
		
		//return JSON.toJSONString(page);
	}

	/**
	 * 根据查询参数，返回查询对象（以后用反射优化一下，抽取通用方法）
	 * @param request
	 * @return
	 */
	private DictCatlogQuery createQueryObjFormRequest(HttpServletRequest request) {
		List<String> parmKeys = QueryUtils.getParmKeys(request);
		DictCatlogQuery query = new DictCatlogQuery();
		for(String sqlKey:parmKeys){
			if(sqlKey.equalsIgnoreCase("seq")){
				query.setSeq(request.getParameter(sqlKey));
			}else if(sqlKey.equalsIgnoreCase("name")){
				query.setName(request.getParameter(sqlKey));
				query.setNameLike(true);
			}else if(sqlKey.equalsIgnoreCase("code")){
				query.setCode(request.getParameter(sqlKey));
				query.setCodeLike(true);
			}else if(sqlKey.equalsIgnoreCase("memo")){
				query.setMemo(request.getParameter(sqlKey));
			}else{
				
			}
		}
		return query;
	}
	
	/**
	 * list页面 加载itemList
	 * @param pageCurrent
	 * @param pageSize
	 * @param orderField
	 * @param orderDirection
	 * @return
	 */
	@RequestMapping("itemList.json")
	@ResponseBody
	public String itemList(@RequestParam(value="pageCurrent",required=false,defaultValue="1") Long pageCurrent,
			@RequestParam(value="pageSize",required=false,defaultValue=AppConstant.PAGE_SIZE) Long pageSize,
			@RequestParam(value="orderField",required=false,defaultValue="id") String orderField,
			@RequestParam(value="orderDirection",required=false,defaultValue="asc") String orderDirection,
			HttpServletRequest request){
		
		Enumeration<String> keys = request.getParameterNames();
		while(keys.hasMoreElements()) {
		    String k = keys.nextElement();
		    System.out.println(k + " = " + request.getParameter(k) );
		} 
		
		
		DictItemQuery query = new DictItemQuery();
		query.setFields("id,seq,name,code,memo");
		//分页
		query.setPageCurrent(pageCurrent);
		query.setPageSize(pageSize);
		//排序
		List<OrderField> orderFields = Lists.newArrayList();
		orderFields.add(new OrderField(orderField, orderDirection));
		//可同时多列排序	orderFields.add(new OrderField("id", "asc"));
		query.setOrderFields(orderFields );
		
		Pagination<DictItem> page = dictItemService.selectListPagination(query);
		return JSON.toJSONString(page);
	}
	
	/**
	 * 进入新增catlog form表单页面
	 * @return
	 */
	@RequestMapping("catlogForm")
	public String catlogForm(){
		return "system/dict/catlogForm";
	}
	
	/**
	 * 进入编辑catlog form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="catlogForm/{id}",method = RequestMethod.GET)
	public String catlogForm(@PathVariable Long id){
		return "system/dict/catlogForm";
	}
	
	/**
	 * 保存catlog  页面已限定只能单行编辑
	 * @param keyword
	 * @param pageNo
	 * @param pageSize
	 * @param model
	 * @return
	 */
	@RequestMapping(value="saveCatlog.json",method = RequestMethod.POST)
	@ResponseBody
	public String saveCatlog(@RequestParam(value="json",required=false) String json){
		String msg = "";
		List<DictCatlog> dcls = JSON.parseArray(json,DictCatlog.class);
		System.out.println(dcls);
		DictCatlog dictCatlog = dcls.get(0);
		if(dictCatlog.getId()==null){
			dictCatlogService.insert(dictCatlog);
			msg = "添加成功！";
		}else {
			dictCatlogService.update(dictCatlog);
			msg = "编辑成功！";
		}
		return JSON.toJSONString(AjaxCallback.OK(msg));
	}
	
	/**
	 * 删除catlog  页面传参是id是一个形如1,2,3,4,5,6,7,8,9,10的字符串
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="deleteCatlog.json",method = RequestMethod.POST)
	@ResponseBody
	public String deleteCatlog(@RequestParam(value="id",required=false) String id){
		List<Long> ids = Lists.newArrayList();
		
		String[] split = id.split(",");
		for(String strId:split){
			ids.add(Long.parseLong(strId));
		}
		
		dictCatlogService.deleteByIds(ids);
		return JSON.toJSONString(AjaxCallback.OK("删除成功"));
	}
	
	/***************************************************************************************************/
}
