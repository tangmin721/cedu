package com.yanxiu.ce.system.dto;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 角色 权限 树
 * @author tangmin
 * @date 2016年3月24日
 */
public class MenuPermTreeDto {
	
	private Long id;
	
	/**
	 * 父id
	 */
	private Long pid;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 图标
	 */
	private String faicon;
	
	/**
	 * 是否选中
	 */
	private Boolean checked;
	
	/**
	 * 是否是permission节点
	 */
	private Boolean perm;
	
	/**
	 * 子节点集
	 */
	private List<MenuPermTreeDto> children = Lists.newArrayList();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFaicon() {
		return faicon;
	}

	public void setFaicon(String faicon) {
		this.faicon = faicon;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Boolean getPerm() {
		return perm;
	}

	public void setPerm(Boolean perm) {
		this.perm = perm;
	}

	public List<MenuPermTreeDto> getChildren() {
		return children;
	}

	public void setChildren(List<MenuPermTreeDto> children) {
		this.children = children;
	}
	
}
