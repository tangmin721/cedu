package com.yanxiu.ce.system.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 权限管理
 * @author tangmin
 * @date 2016-03-23 16:53:39
 */
public class PermissionQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String menuId;
	
	private String clzName;
	private Boolean clzNameLike = true;
	
	private String name;
	private Boolean nameLike = true;
	
	private String code;
	private Boolean codeLike = true;
	

	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getClzName() {
		return clzName;
	}
	public void setClzName(String clzName) {
		this.clzName = clzName;
	}
	public Boolean getClzNameLike() {
		return clzNameLike;
	}
	public void setClzNameLike(Boolean clzNameLike) {
		this.clzNameLike = clzNameLike;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getNameLike() {
		return nameLike;
	}
	public void setNameLike(Boolean nameLike) {
		this.nameLike = nameLike;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Boolean getCodeLike() {
		return codeLike;
	}
	public void setCodeLike(Boolean codeLike) {
		this.codeLike = codeLike;
	}
	
}