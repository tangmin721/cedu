package com.yanxiu.ce.core.train.entity;

import com.yanxiu.ce.common.core.entity.BaseEntity;

/**
 * 项目 不同用户的参与状态
 * @author tangmin
 * @date 2016年6月23日
 */
public class ProjectUserStatus extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 省
	 */
	private Integer province=0;
	
	/**
	 * 市
	 */
	private Integer city=0;
	
	/**
	 * 县
	 */
	private Integer town=0;
	
	/**
	 * 校
	 */
	private Long school=0l;
	
	/**
	 * loginName（具体到一个用户）
	 */
	private String loginName;
	
	/**
	 * cruentUser的type
	 */
	private Integer userType;
	
	/**
	 * ProjectMenuEnum
	 * 2  参加   3不 参加      （查询的时候 没有这条记录标识未参加，但是有关）  查询条件为1表示有关，还未参加
	 */
	private Integer status;
	
	/**
	 * 项目id
	 */
	private Long pid;

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getTown() {
		return town;
	}

	public void setTown(Integer town) {
		this.town = town;
	}

	public Long getSchool() {
		return school;
	}

	public void setSchool(Long school) {
		this.school = school;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	
}
