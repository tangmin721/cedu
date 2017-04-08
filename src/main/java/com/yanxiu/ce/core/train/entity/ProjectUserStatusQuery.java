package com.yanxiu.ce.core.train.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 项目用户状态管理
 * @author tangmin
 * @date 2016-06-23 12:34:37
 */
public class ProjectUserStatusQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String province;
	private Boolean provinceLike = false;
	
	private String city;
	private Boolean cityLike = false;
	
	private String town;
	private Boolean townLike = false;
	
	private String school;
	private Boolean schoolLike = false;
	
	private String loginName;
	private Boolean loginNameLike = false;
	
	private String userType;
	private Boolean userTypeLike = false;
	
	private String status;
	private Boolean statusLike = false;
	
	private String pid;
	private Boolean pidLike = false;
	

	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public Boolean getProvinceLike() {
		return provinceLike;
	}
	public void setProvinceLike(Boolean provinceLike) {
		this.provinceLike = provinceLike;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Boolean getCityLike() {
		return cityLike;
	}
	public void setCityLike(Boolean cityLike) {
		this.cityLike = cityLike;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public Boolean getTownLike() {
		return townLike;
	}
	public void setTownLike(Boolean townLike) {
		this.townLike = townLike;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public Boolean getSchoolLike() {
		return schoolLike;
	}
	public void setSchoolLike(Boolean schoolLike) {
		this.schoolLike = schoolLike;
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
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public Boolean getUserTypeLike() {
		return userTypeLike;
	}
	public void setUserTypeLike(Boolean userTypeLike) {
		this.userTypeLike = userTypeLike;
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
	
}