package com.yanxiu.ce.system.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.yanxiu.ce.common.core.entity.BaseEntity;

/**
 * （对应menu的permissionId用户 最终通过角色组合 去获取权限）
 * @author tangmin
 * @table sys_permission
 * @date 2016年3月17日
 */
public class Permission extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 模块id
	 */
	private Long menuId;
	
	/**
	 * 模块对应的clzName  比如 ：对应（DictItem:delete）中的DictItem。
	 * 因为我现在的模块里面可能嵌入了不同的类，所以放到这一层来管理
	 */
	private String clzName;
	
	/**
	 * 权限名称(操作名称)
	 */
	@NotBlank
	@Length(max=64)
	private String name;
	
	/**
	 * 权限编码(操作编码:create,delete,update,read...)
	 */
	@NotBlank
	@Length(max=64)
	private String code;
	
	private String faicon;

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFaicon() {
		return faicon;
	}

	public void setFaicon(String faicon) {
		this.faicon = faicon;
	}

	public String getClzName() {
		return clzName;
	}

	public void setClzName(String clzName) {
		this.clzName = clzName;
	}
	
}
