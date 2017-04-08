package com.yanxiu.ce.system.entity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.Update;

/**
 * 角色-菜单 关系表
 * @author tangmin
 * @table sys_role_menu
 * @date 2016年3月17日
 */
public class RoleMenu implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	/**
	 * 角色id
	 */
	@NotNull(message="roleId不能为空",groups={Insert.class,Update.class})
	private Long roleId;
	
	/**
	 * 菜单id
	 */
	@NotNull(message="menuId不能为空",groups={Insert.class,Update.class})
	private Long menuId;

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

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

}
