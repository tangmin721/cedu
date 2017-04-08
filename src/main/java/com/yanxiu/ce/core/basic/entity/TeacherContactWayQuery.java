package com.yanxiu.ce.core.basic.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 联系方式管理
 * @author tangmin
 * @date 2016-12-29 17:58:06
 */
public class TeacherContactWayQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private Long tid;
	private Boolean tidLike = false;
	
	private Integer seq;
	private Boolean seqLike = false;
	
	private String address;
	private Boolean addressLike = false;
	
	private String telphone;
	private Boolean telphoneLike = false;
	
	private String phoneNo;
	private Boolean phoneNoLike = false;
	
	private String email;
	private Boolean emailLike = false;
	
	private String otherContact;
	private Boolean otherContactLike = false;
	
	
	public Long getTid() {
		return tid;
	}
	public void setTid(Long tid) {
		this.tid = tid;
	}
	public Boolean getTidLike() {
		return tidLike;
	}
	public void setTidLike(Boolean tidLike) {
		this.tidLike = tidLike;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public Boolean getSeqLike() {
		return seqLike;
	}
	public void setSeqLike(Boolean seqLike) {
		this.seqLike = seqLike;
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
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public Boolean getTelphoneLike() {
		return telphoneLike;
	}
	public void setTelphoneLike(Boolean telphoneLike) {
		this.telphoneLike = telphoneLike;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public Boolean getPhoneNoLike() {
		return phoneNoLike;
	}
	public void setPhoneNoLike(Boolean phoneNoLike) {
		this.phoneNoLike = phoneNoLike;
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
	public String getOtherContact() {
		return otherContact;
	}
	public void setOtherContact(String otherContact) {
		this.otherContact = otherContact;
	}
	public Boolean getOtherContactLike() {
		return otherContactLike;
	}
	public void setOtherContactLike(Boolean otherContactLike) {
		this.otherContactLike = otherContactLike;
	}
	
	
	
}