package com.yanxiu.ce.core.train.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 项目执行人管理
 * @author tangmin
 * @date 2016-04-19 14:04:52
 */
public class ProjectOperatorQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String province;
	private Boolean provinceLike = true;
	
	private String city;
	private Boolean cityLike = true;
	
	private String town;
	private Boolean townLike = true;
	
	private String school;
	private Boolean schoolLike = true;
	
	private String loginName;
	private Boolean loginNameLike = true;
	
	private String name;
	private Boolean nameLike = true;
	
	private String tel;
	private Boolean telLike = true;
	
	private String mobile;
	private Boolean mobileLike = true;
	
	private String email;
	private Boolean emailLike = true;
	
	private String dept;
	private Boolean deptLike = true;
	

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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Boolean getTelLike() {
		return telLike;
	}
	public void setTelLike(Boolean telLike) {
		this.telLike = telLike;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Boolean getMobileLike() {
		return mobileLike;
	}
	public void setMobileLike(Boolean mobileLike) {
		this.mobileLike = mobileLike;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Boolean getEmailLike() {
		return emailLike;
	}
	public void setEmailLike(Boolean emailLike) {
		this.emailLike = emailLike;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public Boolean getDeptLike() {
		return deptLike;
	}
	public void setDeptLike(Boolean deptLike) {
		this.deptLike = deptLike;
	}
	
}