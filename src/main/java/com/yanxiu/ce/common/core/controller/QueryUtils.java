package com.yanxiu.ce.common.core.controller;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Lists;
import com.yanxiu.ce.common.core.entity.BaseQuery;
import com.yanxiu.ce.common.core.entity.OrderField;

/**
 * 获取request中的参数，得到以".operator"为后缀的查询参数
 * @author tangmin
 * @date 2016年2月24日
 */
public class QueryUtils<Q extends BaseQuery> {
	public static List<String> getParmKeys(HttpServletRequest request) {
		Enumeration<String> keys = request.getParameterNames();
		List<String> parmKeys = Lists.newArrayList(); 
		while(keys.hasMoreElements()) {
		    String k = keys.nextElement();
		    //截取.operator
		    if(k.contains(".")){
		    	parmKeys.add(k.substring(0,k.length()-9));
		    }
		} 
		return parmKeys;
	}
	
	public Q queryObjForPage(Long pageCurrent, Long pageSize,
			String orderField, String orderDirection, Q query) {
		//分页
		query.setPageCurrent(pageCurrent);
		query.setPageSize(pageSize);
		//排序
		List<OrderField> orderFields = Lists.newArrayList();
		orderFields.add(new OrderField(orderField, orderDirection));
		//可同时多列排序	orderFields.add(new OrderField("id", "asc"));
		query.setOrderFields(orderFields );
		return query;
	}
}
