package com.yanxiu.ce.core.train.entity;

import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.mybatis.annotation.Transient;

/**
 * 项目的培训机构
 * @author tangmin
 * @date 2016年5月10日
 * @Tabel (t_project_trainorg)
 */
public class ProjectTrainOrg extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 项目id
	 */
	private Long pid;
	
	/**
	 * 培训机构id
	 */
	private Long orgid;
	
	/**
	 * 机构名称
	 */
	@Transient
	private String name;
	
	@Transient
	private String type;
	@Transient
	private String typeName;
	
	@Transient
	private String userType;
	
	@Transient
	private String level;
	@Transient
	private String levelName;
	
	@Transient
	private String address;
	
	@Transient
	private String linkman;
	
	@Transient
	private String tel;
	
	@Transient
	private String orgNo;
	

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	
	
}
