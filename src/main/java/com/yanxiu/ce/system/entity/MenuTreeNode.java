package com.yanxiu.ce.system.entity;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 系统加载的导航菜单
 * @author tangm
 */
public class MenuTreeNode extends Menu{

	private static final long serialVersionUID = 3616955955675879392L;
	
	public MenuTreeNode(){
		
	}
	
	public MenuTreeNode(Menu menu){
		this.setId(menu.getId());
		this.setVersion(menu.getVersion());
		this.setDeleted(menu.getDeleted());
		this.setCreateTime(menu.getCreateTime());
		this.setPid(menu.getPid());
		this.setName(menu.getName());
		this.setModelClzName(menu.getModelClzName());
		this.setFaicon(menu.getFaicon());
		this.setLevel(menu.getLevel());
		this.setTabid(menu.getTabid());
		this.setUrl(menu.getUrl());
		this.setTheLast(menu.getTheLast());
		this.setAuthrizer(menu.getAuthrizer());
		this.setSeq(menu.getSeq());
		this.setMemo(menu.getMemo());
	}
	/**
	 * 子节点
	 */
	List<MenuTreeNode> levelOnechildren = Lists.newArrayList();
	
	/**
	 * 递归子节点
	 */
	List<MenuTreeNode> levelTwochildren = Lists.newArrayList();

	public List<MenuTreeNode> getLevelOnechildren() {
		return levelOnechildren;
	}

	public void setLevelOnechildren(List<MenuTreeNode> levelOnechildren) {
		this.levelOnechildren = levelOnechildren;
	}

	public List<MenuTreeNode> getLevelTwochildren() {
		return levelTwochildren;
	}

	public void setLevelTwochildren(List<MenuTreeNode> levelTwochildren) {
		this.levelTwochildren = levelTwochildren;
	}

	@Override
	public String toString() {
		return "MenuTreeNode [levelOnechildren=" + levelOnechildren
				+ ", levelTwochildren=" + levelTwochildren + ", getPid()="
				+ getPid() + ", getName()=" + getName() + ", getFaicon()="
				+ getFaicon() + ", getId()=" + getId() + "]";
	}

}
