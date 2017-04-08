package com.yanxiu.ce.system.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 附件管理
 * @author tangmin
 * @date 2016-04-12 16:56:21
 */
public class AttachmentQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Boolean nameLike = true;
	
	private String size;
	private Boolean sizeLike = true;
	
	private String path;
	private Boolean pathLike = true;
	
	private String userId;
	private Boolean userIdLike = true;
	

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
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public Boolean getSizeLike() {
		return sizeLike;
	}
	public void setSizeLike(Boolean sizeLike) {
		this.sizeLike = sizeLike;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Boolean getPathLike() {
		return pathLike;
	}
	public void setPathLike(Boolean pathLike) {
		this.pathLike = pathLike;
	}
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
	
}