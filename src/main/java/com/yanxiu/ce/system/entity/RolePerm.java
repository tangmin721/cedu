package com.yanxiu.ce.system.entity;

import javax.validation.constraints.NotNull;

import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.Update;

/**
 * 角色-权限资源  关系表
 * @author tangmin
 * @table sys_role_perm
 * @date 2016年3月17日
 */
public class RolePerm extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	/**
	 * 角色id
	 */
	@NotNull(message="roleId不能为空",groups={Insert.class,Update.class})
	private Long roleId;
	
	/**
	 * 权限id
	 */
	@NotNull(message="permId不能为空",groups={Insert.class,Update.class})
	private Long permId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getPermId() {
		return permId;
	}

	public void setPermId(Long permId) {
		this.permId = permId;
	}
	


}
