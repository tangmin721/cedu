package com.yanxiu.ce.core.statistics.dto;

/**
 * name  value
 * @author tangmin
 * @date 2016年8月16日
 */
public class NameValue {
	private String name;
	
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "NameValue [name=" + name + ", value=" + value + "]";
	}
	
}
