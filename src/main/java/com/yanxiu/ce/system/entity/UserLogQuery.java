package com.yanxiu.ce.system.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 用户操作日志管理
 * @author tangmin
 * @date 2016-07-13 12:05:53
 */
public class UserLogQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String userNo;
	private Boolean userNoLike = true;
	
	private String loginName;
	private Boolean loginNameLike = true;
	
	private String operateStatus;
	private Boolean operateStatusLike = true;
	
	private String ip;
	private Boolean ipLike = true;
	
	private String description;
	private Boolean descriptionLike = true;
	
	private String content;
	private Boolean contentLike = true;
	
	private String operType;
	private Boolean operTypeLike = true;
	

	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public Boolean getUserNoLike() {
		return userNoLike;
	}
	public void setUserNoLike(Boolean userNoLike) {
		this.userNoLike = userNoLike;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public Boolean getLoginNameLike() {
		return loginNameLike;
	}
	public void setLoginNameLike(Boolean loginNameLike) {
		this.loginNameLike = loginNameLike;
	}
	public String getOperateStatus() {
		return operateStatus;
	}
	public void setOperateStatus(String operateStatus) {
		this.operateStatus = operateStatus;
	}
	public Boolean getOperateStatusLike() {
		return operateStatusLike;
	}
	public void setOperateStatusLike(Boolean operateStatusLike) {
		this.operateStatusLike = operateStatusLike;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Boolean getIpLike() {
		return ipLike;
	}
	public void setIpLike(Boolean ipLike) {
		this.ipLike = ipLike;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getDescriptionLike() {
		return descriptionLike;
	}
	public void setDescriptionLike(Boolean descriptionLike) {
		this.descriptionLike = descriptionLike;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Boolean getContentLike() {
		return contentLike;
	}
	public void setContentLike(Boolean contentLike) {
		this.contentLike = contentLike;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public Boolean getOperTypeLike() {
		return operTypeLike;
	}
	public void setOperTypeLike(Boolean operTypeLike) {
		this.operTypeLike = operTypeLike;
	}
	
}