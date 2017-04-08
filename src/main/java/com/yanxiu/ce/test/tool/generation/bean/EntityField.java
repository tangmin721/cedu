package com.yanxiu.ce.test.tool.generation.bean;

import org.apache.commons.lang3.StringUtils;

/**
 * 字段，数据库字段
 * @author tangmin
 * @date 2016年2月26日
 */
public class EntityField{
	/**
	 * entity字段
	 */
	String fieldName;
	
	/**
	 * 数据库字段
	 */
	String columnName;
	
	/**
	 * 首字母大写，用于模板的get  SET方法
	 * @return
	 */
	String supFiledName;
	
	public String getFieldName() {
		return fieldName;
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
		this.supFiledName = StringUtils.capitalize(this.fieldName);
	}
	
	public String getColumnName() {
		return columnName;
	}
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public String getSupFiledName() {
		return  supFiledName;//首字母大写
	}

	public void setSupFiledName(String supFiledName) {
		this.supFiledName = supFiledName;
	}

	@Override
	public String toString() {
		return "EntityField [fieldName=" + fieldName + ", columnName="
				+ columnName + ", supFiledName=" + supFiledName + "]";
	}
	
}