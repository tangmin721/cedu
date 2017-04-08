package com.yanxiu.ce.core.train.entity;

import com.yanxiu.ce.common.core.entity.BaseEntity;

public class ProjectFile  extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 项目id
	 */
	private Long pid;
	
	/**
	 * 机构id
	 */
	private Long orgId; 
	
	/**
	 * 资料类别  OrgFileEnum
	 */
	private Integer type;
	
	/**
	 * 文件名称     用于存储id，在返回的时候，还原文件名
	 */
	private String fileName;

	/**
	 * 文件id   fastDfs返回的id
	 */
	private String fileId;
	
	/**
	 * OrgFileStatusEnum   文件审核状态
	 */
	private Integer status;
	
	/**
	 * 审核意见
	 */
	private String checkDesc;

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCheckDesc() {
		return checkDesc;
	}

	public void setCheckDesc(String checkDesc) {
		this.checkDesc = checkDesc;
	}
	
}
