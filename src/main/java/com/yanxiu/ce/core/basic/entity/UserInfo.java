package com.yanxiu.ce.core.basic.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.yanxiu.ce.common.core.entity.BaseEntity;

/**
 * 用户信息（教师信息）
 * @author tangmin
 * @table sys_user
 * @date 2016年3月7日
 */
public class UserInfo extends BaseEntity{
	
	private static final long serialVersionUID = 5740971260271933925L;
	
	/** 用户编号 **/
	private String userNo;
	/** 登录名 **/
	private String loginName;
	/** 用户类型 引用UserTypeEnum **/
	private Integer userType;
	/** 预留信息 **/
	private String greeting;
	/** 安全保护问题 **/
	private String question;
	/** 问题答案 **/
	private String answer;
	/** 绑定手机号码 **/
	private String bindMobileNo;
	/** 绑定邮箱 **/
	private String bindEmail;
	
	/** 省份 **/
	private String province;
	/** 所属地区 **/
	private String city;
	/** 所属区域 **/
	private String area;
	/**用户昵称**/
	private String name;
	/** 真实名字  **/
	private String realName;
	/** 性别**/
	private String sex;
	/**生日**/
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	/** 身份证号码**/
	private String cardNo;
	/** 状态 ENUM 已激活，未激活，锁定**/
	private Integer status;
	/** 是否邮箱验证(1:是,0:否,默认值为0) **/
	private Integer isEmailAuth;
	/** 是否手机验证(1:是,0:否,默认值为0) **/
	private Integer isMobileAuth;
	/** 是否实名认证(1:是,0:否,默认值为0) **/
	private Integer isRealNameAuth;
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
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
	public String getGreeting() {
		return greeting;
	}
	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getBindMobileNo() {
		return bindMobileNo;
	}
	public void setBindMobileNo(String bindMobileNo) {
		this.bindMobileNo = bindMobileNo;
	}
	public String getBindEmail() {
		return bindEmail;
	}
	public void setBindEmail(String bindEmail) {
		this.bindEmail = bindEmail;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIsEmailAuth() {
		return isEmailAuth;
	}
	public void setIsEmailAuth(Integer isEmailAuth) {
		this.isEmailAuth = isEmailAuth;
	}
	public Integer getIsMobileAuth() {
		return isMobileAuth;
	}
	public void setIsMobileAuth(Integer isMobileAuth) {
		this.isMobileAuth = isMobileAuth;
	}
	public Integer getIsRealNameAuth() {
		return isRealNameAuth;
	}
	public void setIsRealNameAuth(Integer isRealNameAuth) {
		this.isRealNameAuth = isRealNameAuth;
	}
	
}
