package com.yanxiu.ce.system.entity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.Update;


/**
 * 用户-角色 关系表
 * @author tangmin
 * @Tabel sys_user_role
 * @date 2016年3月17日
 */
public class UserRole implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;

	@NotNull(message="userID不能为空",groups={Insert.class,Update.class})
	private Long userId;
	
	@NotNull(message="roleID不能为空",groups={Insert.class,Update.class})
	private Long roleId;

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

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
