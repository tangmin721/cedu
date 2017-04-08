package com.yanxiu.ce.core.train.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 项目资料文件管理
 * @author tangmin
 * @date 2016-07-26 10:07:34
 */
public class ProjectFileQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String pid;
	private Boolean pidLike = true;
	
	private String orgId;
	private Boolean orgIdLike = true;
	
	private String type;
	private Boolean typeLike = true;
	
	private String fileName;
	private Boolean fileNameLike = true;
	
	private String fileId;
	private Boolean fileIdLike = true;
	
	private String status;
	private Boolean statusLike = true;
	
	private String checkDesc;
	private Boolean checkDescLike = true;
	

	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public Boolean getPidLike() {
		return pidLike;
	}
	public void setPidLike(Boolean pidLike) {
		this.pidLike = pidLike;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public Boolean getOrgIdLike() {
		return orgIdLike;
	}
	public void setOrgIdLike(Boolean orgIdLike) {
		this.orgIdLike = orgIdLike;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getTypeLike() {
		return typeLike;
	}
	public void setTypeLike(Boolean typeLike) {
		this.typeLike = typeLike;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Boolean getFileNameLike() {
		return fileNameLike;
	}
	public void setFileNameLike(Boolean fileNameLike) {
		this.fileNameLike = fileNameLike;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public Boolean getFileIdLike() {
		return fileIdLike;
	}
	public void setFileIdLike(Boolean fileIdLike) {
		this.fileIdLike = fileIdLike;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Boolean getStatusLike() {
		return statusLike;
	}
	public void setStatusLike(Boolean statusLike) {
		this.statusLike = statusLike;
	}
	public String getCheckDesc() {
		return checkDesc;
	}
	public void setCheckDesc(String checkDesc) {
		this.checkDesc = checkDesc;
	}
	public Boolean getCheckDescLike() {
		return checkDescLike;
	}
	public void setCheckDescLike(Boolean checkDescLike) {
		this.checkDescLike = checkDescLike;
	}
	
}