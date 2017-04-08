package com.yanxiu.ce.system.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 系统配置管理
 * @author tangmin
 * @date 2016-04-12 17:40:16
 */
public class ConfigQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String userId;
	private Boolean userIdLike = true;
	
	private String name;
	private Boolean nameLike = true;
	
	private String theKey;
	private Boolean theKeyLike = true;
	
	private String theValue;
	private Boolean theValueLike = true;
	
	private String memo;
	private Boolean memoLike = true;
	

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Boolean getUserIdLike() {
		return userIdLike;
	}
	public void setUserIdLike(Boolean userIdLike) {
		this.userIdLike = userIdLike;
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
	public String getTheKey() {
		return theKey;
	}
	public void setTheKey(String theKey) {
		this.theKey = theKey;
	}
	public Boolean getTheKeyLike() {
		return theKeyLike;
	}
	public void setTheKeyLike(Boolean theKeyLike) {
		this.theKeyLike = theKeyLike;
	}
	public String getTheValue() {
		return theValue;
	}
	public void setTheValue(String theValue) {
		this.theValue = theValue;
	}
	public Boolean getTheValueLike() {
		return theValueLike;
	}
	public void setTheValueLike(Boolean theValueLike) {
		this.theValueLike = theValueLike;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Boolean getMemoLike() {
		return memoLike;
	}
	public void setMemoLike(Boolean memoLike) {
		this.memoLike = memoLike;
	}
	
}