package com.yanxiu.ce.system.entity;

import com.yanxiu.ce.common.core.entity.BaseEntity;

/**
 * 系统自带图标类
 * @author tangmin
 * @table sys_faicon
 * @date 2016年2月29日
 */
public class Faicon extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
