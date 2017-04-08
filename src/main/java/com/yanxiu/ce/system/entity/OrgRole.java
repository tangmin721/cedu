package com.yanxiu.ce.system.entity;

import java.io.Serializable;

/**
 * 组织机构-角色 关系表
 * @author tangmin
 * @Tabele sys_org_role
 * @date 2016年3月17日
 */
public class OrgRole implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long orgId;
	
	private Long roleId;

	public Long getId() {
		return id;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
