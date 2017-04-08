package com.yanxiu.ce.core.basic.entity;

import com.yanxiu.ce.common.core.entity.BasePctsQuery;

/**
 * 学校管理
 * @author tangmin
 * @date 2016-03-22 19:04:55
 */
public class SchoolQuery extends BasePctsQuery{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Boolean nameLike = true;
	
	private String schoolNo;
	private Boolean schoolNoLike = true;
	
	private String category;
	private Boolean categoryLike = false;
	
	private String type;
	private Boolean typeLike = false;
	
	private String schoolType;
	private Boolean schoolTypeLike = false;
	
	private String address;
	private Boolean addressLike = true;
	
	private String postCode;
	private Boolean postCodeLike = true;
	
	private String master;
	private Boolean masterLike = true;
	
	private String tel;
	private Boolean telLike = true;
	
	private String director;
	private Boolean directorLike = true;
	
	private String idCard;
	private Boolean idCardLike = true;
	
	private String theTel;
	private Boolean theTelLike = true;
	
	private String phone;
	private Boolean phoneLike = true;
	
	private String email;
	private Boolean emailLike = true;
	
	private String status;
	private Boolean statusLike = true;
	
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
	public String getSchoolNo() {
		return schoolNo;
	}
	public void setSchoolNo(String schoolNo) {
		this.schoolNo = schoolNo;
	}
	public Boolean getSchoolNoLike() {
		return schoolNoLike;
	}
	public void setSchoolNoLike(Boolean schoolNoLike) {
		this.schoolNoLike = schoolNoLike;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Boolean getCategoryLike() {
		return categoryLike;
	}
	public void setCategoryLike(Boolean categoryLike) {
		this.categoryLike = categoryLike;
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
	
	public String getSchoolType() {
		return schoolType;
	}
	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
	}
	public Boolean getSchoolTypeLike() {
		return schoolTypeLike;
	}
	public void setSchoolTypeLike(Boolean schoolTypeLike) {
		this.schoolTypeLike = schoolTypeLike;
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
	public String getMaster() {
		return master;
	}
	public void setMaster(String master) {
		this.master = master;
	}
	public Boolean getMasterLike() {
		return masterLike;
	}
	public void setMasterLike(Boolean masterLike) {
		this.masterLike = masterLike;
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
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public Boolean getDirectorLike() {
		return directorLike;
	}
	public void setDirectorLike(Boolean directorLike) {
		this.directorLike = directorLike;
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
	public String getTheTel() {
		return theTel;
	}
	public void setTheTel(String theTel) {
		this.theTel = theTel;
	}
	public Boolean getTheTelLike() {
		return theTelLike;
	}
	public void setTheTelLike(Boolean theTelLike) {
		this.theTelLike = theTelLike;
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
	
}