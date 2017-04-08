package com.yanxiu.ce.system.entity;

import com.yanxiu.ce.common.core.entity.BasePctsQuery;

/**
 * 用户管理
 * @author tangmin
 * @date 2016-03-09 11:17:37
 */
public class UserQuery extends BasePctsQuery{
	private static final long serialVersionUID = 1L;
	
	private String org;
	
	private String userNo;
	private Boolean userNoLike = true;
	
	private String loginName;
	private Boolean loginNameLike = true;
	
	private String loginPwd;
	private Boolean loginPwdLike = true;
	
	private String md5;
	private String uid;
	private String reflag;
	/**
	 * 研修网新增字段冗余
	 
	private String idCard;
	private Boolean idCardLike = true;
	private String course;
	private Boolean courseLike = true;
	private String stage;
	private Boolean stageLike = true;
	private String grade;
	private Boolean gradeLike = true;
	private String gender;
	private Boolean genderLike = true;*/
	
	private String salt;
	private Boolean saltLike = true;
	
	private String realName;
	private Boolean realNameLike = true;
	
	private String mobileNo;
	private Boolean mobileNoLike = true;
	
	private String email;
	private Boolean emailLike = true;
	
	private String unit;
	private Boolean unitLike = true;
	
	private String status;
	private Boolean statusLike = true;
	
	private String type;
	private Boolean typeLike = true;
	
	private String isChangedPwd;
	private Boolean isChangedPwdLike = true;
	
	private String pwdErrorTimes;
	private Boolean pwdErrorTimesLike = true;
	
	private String lastLoginTime;
	private Boolean lastLoginTimeLike = true;
	
	private String pwdErrorLastTime;
	private Boolean pwdErrorLastTimeLike = true;
	
	private String lastAlertPwdTime;
	private Boolean lastAlertPwdTimeLike = true;
	
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
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
	public String getLoginPwd() {
		return loginPwd;
	}
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
	public Boolean getLoginPwdLike() {
		return loginPwdLike;
	}
	public void setLoginPwdLike(Boolean loginPwdLike) {
		this.loginPwdLike = loginPwdLike;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public Boolean getSaltLike() {
		return saltLike;
	}
	public void setSaltLike(Boolean saltLike) {
		this.saltLike = saltLike;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Boolean getRealNameLike() {
		return realNameLike;
	}
	public void setRealNameLike(Boolean realNameLike) {
		this.realNameLike = realNameLike;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public Boolean getMobileNoLike() {
		return mobileNoLike;
	}
	public void setMobileNoLike(Boolean mobileNoLike) {
		this.mobileNoLike = mobileNoLike;
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
	public String getIsChangedPwd() {
		return isChangedPwd;
	}
	public void setIsChangedPwd(String isChangedPwd) {
		this.isChangedPwd = isChangedPwd;
	}
	public Boolean getIsChangedPwdLike() {
		return isChangedPwdLike;
	}
	public void setIsChangedPwdLike(Boolean isChangedPwdLike) {
		this.isChangedPwdLike = isChangedPwdLike;
	}
	public String getPwdErrorTimes() {
		return pwdErrorTimes;
	}
	public void setPwdErrorTimes(String pwdErrorTimes) {
		this.pwdErrorTimes = pwdErrorTimes;
	}
	public Boolean getPwdErrorTimesLike() {
		return pwdErrorTimesLike;
	}
	public void setPwdErrorTimesLike(Boolean pwdErrorTimesLike) {
		this.pwdErrorTimesLike = pwdErrorTimesLike;
	}
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Boolean getLastLoginTimeLike() {
		return lastLoginTimeLike;
	}
	public void setLastLoginTimeLike(Boolean lastLoginTimeLike) {
		this.lastLoginTimeLike = lastLoginTimeLike;
	}
	public String getPwdErrorLastTime() {
		return pwdErrorLastTime;
	}
	public void setPwdErrorLastTime(String pwdErrorLastTime) {
		this.pwdErrorLastTime = pwdErrorLastTime;
	}
	public Boolean getPwdErrorLastTimeLike() {
		return pwdErrorLastTimeLike;
	}
	public void setPwdErrorLastTimeLike(Boolean pwdErrorLastTimeLike) {
		this.pwdErrorLastTimeLike = pwdErrorLastTimeLike;
	}
	public String getLastAlertPwdTime() {
		return lastAlertPwdTime;
	}
	public void setLastAlertPwdTime(String lastAlertPwdTime) {
		this.lastAlertPwdTime = lastAlertPwdTime;
	}
	public Boolean getLastAlertPwdTimeLike() {
		return lastAlertPwdTimeLike;
	}
	public void setLastAlertPwdTimeLike(Boolean lastAlertPwdTimeLike) {
		this.lastAlertPwdTimeLike = lastAlertPwdTimeLike;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getReflag() {
		return reflag;
	}
	public void setReflag(String reflag) {
		this.reflag = reflag;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Boolean getUnitLike() {
		return unitLike;
	}
	public void setUnitLike(Boolean unitLike) {
		this.unitLike = unitLike;
	}
	
}