package com.yanxiu.ce.core.train.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 项目培训机构管理
 * @author tangmin
 * @date 2016-05-10 15:22:39
 */
public class ProjectTrainOrgQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String pid;
	
	private String orgid;
	
	private String name;
	
	private String userType;
	
	private Boolean nameLike= true;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
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

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
}