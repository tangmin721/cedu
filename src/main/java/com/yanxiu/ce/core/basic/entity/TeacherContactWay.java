package com.yanxiu.ce.core.basic.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.yanxiu.ce.common.core.entity.BaseEntity;


/**
 * 联系方式信息
 * 
 * @author guowenyi
 * @table  t_tch_contact_way
 * @date 20161229
 */
public class TeacherContactWay extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3924169945123012680L;

	/**
	 * 教师id
	 */
	private Long tid;
	
	/**
	 * 排序序号
	 */
	@NotNull
	@Range(min = 0)
	private Integer seq = 0;
	
	/**
	 * 通讯地址
	 */
	private String address;
	
	/**
	 * 联系电话
	 */
	private String telphone;
	
	/**
	 * 手机
	 */
	private String phoneNo;
	
	/**
	 * email
	 */
	private String email;
	
	/**
	 * 其他联系方式
	 */
	private String otherContact;

	
	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOtherContact() {
		return otherContact;
	}

	public void setOtherContact(String otherContact) {
		this.otherContact = otherContact;
	}
}
