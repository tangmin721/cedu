package com.yanxiu.ce.core.mq.entity;

import java.util.Date;

import com.yanxiu.ce.common.core.entity.BaseEntity;

/**
 * 注册消息  保存
 * @author tangmin
 * @date 2016年8月11日
 */
public class RegisterMsg extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	/** 处理状态 MsgStatusEnum **/
	private Integer status;
	
	/**
	 * 消息类型  0为注册，1为同步 MsgTypeEnum
	 */
	private Integer type;
	
	/**
	 * 发送到消息队列的次数
	 */
	private Integer num = 0;
	
	/**
	 * 返回结果说明
	 */
	private String memo;
	/**
	 * 最新一次发送到mq的时间
	 */
	private Date producerTime;
	
	/**
	 * 最新一次消费消息后  的更新时间
	 */
	private Date consumerTime;
	
	/**
	 * 消息实体，这里做冗余，是因为有些字段用户表里不存储
	 */
	/** uid**/
	private String uid;
	/** 密码，md5加密后的字符串**/
	private String password;
	/** 产品线生成**/
	private String passport;
	/** 必填，请求来自哪个产品线**/
	private String appKey;
	/** 手机号码**/
	private String mobile;
	/** 邮箱**/
	private String email;
	/**真实姓名**/
	private String realname;
	/** 身份证号**/
	private String idCard;
	/** 学校名**/
	private String schoolName;
	/** 学科**/
	private String course;
	/** 学段**/
	private String stage;
	/** 年级**/
	private String grade;
	/** 省**/
	private String province;
	/** 市**/
	private String city;
	/** 区域/县**/
	private String area;
	/** 性别**/
	private String gender;
	
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassport() {
		return passport;
	}
	public void setPassport(String passport) {
		this.passport = passport;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
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
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getProducerTime() {
		return producerTime;
	}
	public void setProducerTime(Date producerTime) {
		this.producerTime = producerTime;
	}
	public Date getConsumerTime() {
		return consumerTime;
	}
	public void setConsumerTime(Date consumerTime) {
		this.consumerTime = consumerTime;
	}

	
}
