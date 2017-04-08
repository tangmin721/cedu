package com.yanxiu.ce.common.core.entity;

/**
 * @Description: 排序字段公共类
 * @author: tangm
 * @date: 2016年2月16日 
 * @version: 1.0
 */
public class OrderField {
	public OrderField(String fieldName, String order) {
		super();
		this.fieldName = fieldName.toLowerCase();
		this.order = order;
	}
	private String fieldName;
	private String order;

	public String getFieldName() {
		return fieldName;
	}
	public OrderField setFieldName(String fieldName) {
		this.fieldName = fieldName;
		return this;
	}
	public String getOrder() {
		return order;
	}
	public OrderField setOrder(String order) {
		this.order = order;
		return this;
	}
}