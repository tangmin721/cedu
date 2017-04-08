package com.yanxiu.ce.core.train.dto;

import java.util.List;

import com.google.common.collect.Lists;
import com.yanxiu.ce.core.train.entity.ProjectFile;


/**
 * 项目审核的时候，加载机构列表时用到的包装类
 * @author tangmin
 * @date 2016年7月27日
 */
public class ProjectFileCheckDto {
	
	private ProjectFile reqProjectFile;
	
	private ProjectFile shishiProjectFile;
	
	private List<ProjectFile> otherProjectFiles = Lists.newArrayList();
	
	private Long pid;
	
	private Long orgId;
	
	private String orgName;
	
	public ProjectFile getReqProjectFile() {
		return reqProjectFile;
	}

	public void setReqProjectFile(ProjectFile reqProjectFile) {
		this.reqProjectFile = reqProjectFile;
	}

	public ProjectFile getShishiProjectFile() {
		return shishiProjectFile;
	}

	public void setShishiProjectFile(ProjectFile shishiProjectFile) {
		this.shishiProjectFile = shishiProjectFile;
	}

	public List<ProjectFile> getOtherProjectFiles() {
		return otherProjectFiles;
	}

	public void setOtherProjectFiles(List<ProjectFile> otherProjectFiles) {
		this.otherProjectFiles = otherProjectFiles;
	}

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

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
}
