package com.yanxiu.ce.common.yanxiuapi.user;

import java.io.Serializable;
import java.util.Date;

public class UserInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer uid;
	private String passport;
	private String nickname;
	private String mobile;
	private String email;
	private String idCard;
	private String avatar;
	private Integer actiFlag;
	private Date createTime;
	private Date modifyTime;
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getPassport() {
		return passport;
	}
	public void setPassport(String passport) {
		this.passport = passport;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public Integer getActiFlag() {
		return actiFlag;
	}
	public void setActiFlag(Integer actiFlag) {
		this.actiFlag = actiFlag;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
}
