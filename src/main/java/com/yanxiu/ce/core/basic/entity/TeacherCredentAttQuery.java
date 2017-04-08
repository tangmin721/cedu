package com.yanxiu.ce.core.basic.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 附件管理
 * @author tangmin
 * @date 2016-04-13 11:03:13
 */
public class TeacherCredentAttQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String primaryId;
	private Boolean primaryIdLike = false;
	
	private String attId;
	private Boolean attIdLike = false;
	

	public String getPrimaryId() {
		return primaryId;
	}
	public void setPrimaryId(String primaryId) {
		this.primaryId = primaryId;
	}
	public Boolean getPrimaryIdLike() {
		return primaryIdLike;
	}
	public void setPrimaryIdLike(Boolean primaryIdLike) {
		this.primaryIdLike = primaryIdLike;
	}
	public String getAttId() {
		return attId;
	}
	public void setAttId(String attId) {
		this.attId = attId;
	}
	public Boolean getAttIdLike() {
		return attIdLike;
	}
	public void setAttIdLike(Boolean attIdLike) {
		this.attIdLike = attIdLike;
	}
	
}