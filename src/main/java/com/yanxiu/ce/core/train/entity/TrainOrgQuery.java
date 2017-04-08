package com.yanxiu.ce.core.train.entity;

import com.yanxiu.ce.common.core.entity.BasePctsQuery;

/**
 * 培训机构管理
 * @author tangmin
 * @date 2016-04-11 17:38:15
 */
public class TrainOrgQuery extends BasePctsQuery{
	private static final long serialVersionUID = 1L;
	
	
	private String orgNo;
	private Boolean orgNoLike = true;
	
	private String userType;
	
	private String name;
	private Boolean nameLike = true;
	
	private String type;
	private Boolean typeLike = true;
	
	private String level;
	private Boolean levelLike = true;
	
	private String address;
	private Boolean addressLike = true;
	
	private String postCode;
	private Boolean postCodeLike = true;
	
	private String linkman;
	private Boolean linkmanLike = true;
	
	private String idCard;
	private Boolean idCardLike = true;
	
	private String tel;
	private Boolean telLike = true;
	
	private String phone;
	private Boolean phoneLike = true;
	
	private String email;
	private Boolean emailLike = true;
	
	private String memo;
	private Boolean memoLike = true;
	

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
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public Boolean getLevelLike() {
		return levelLike;
	}
	public void setLevelLike(Boolean levelLike) {
		this.levelLike = levelLike;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Boolean getAddressLike() {
		return addressLike;
	}
	public void setAddressLike(Boolean addressLike) {
		this.addressLike = addressLike;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public Boolean getPostCodeLike() {
		return postCodeLike;
	}
	public void setPostCodeLike(Boolean postCodeLike) {
		this.postCodeLike = postCodeLike;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public Boolean getLinkmanLike() {
		return linkmanLike;
	}
	public void setLinkmanLike(Boolean linkmanLike) {
		this.linkmanLike = linkmanLike;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public Boolean getIdCardLike() {
		return idCardLike;
	}
	public void setIdCardLike(Boolean idCardLike) {
		this.idCardLike = idCardLike;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Boolean getPhoneLike() {
		return phoneLike;
	}
	public void setPhoneLike(Boolean phoneLike) {
		this.phoneLike = phoneLike;
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
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Boolean getMemoLike() {
		return memoLike;
	}
	public void setMemoLike(Boolean memoLike) {
		this.memoLike = memoLike;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public Boolean getOrgNoLike() {
		return orgNoLike;
	}
	public void setOrgNoLike(Boolean orgNoLike) {
		this.orgNoLike = orgNoLike;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
}