package com.yanxiu.ce.system.entity;

import java.io.Serializable;

/**
 * 组织机构-用户 关系表
 * @author tangmin
 * @Tabele sys_user_org
 * @date 2016年3月17日
 */
public class UserOrg implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long userId;
	
	private Long orgId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	
}
