package com.yanxiu.ce.system.dto;

/**
 * 用于解析角色中权限树提交json转换
 * @author tangmin
 * @date 2016年3月24日
 */
public class TreeCheckedIdDto {
	
	private Long id;
	/**
	 * 是否是权限的id
	 */
	private Boolean perm;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Boolean getPerm() {
		return perm;
	}
	public void setPerm(Boolean perm) {
		this.perm = perm;
	}
	@Override
	public String toString() {
		return "TreeCheckedIdDto [id=" + id + ", perm=" + perm + "]";
	}
	
}
