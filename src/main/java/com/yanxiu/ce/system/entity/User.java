package com.yanxiu.ce.system.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.mybatis.annotation.Transient;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.Update;
import com.yanxiu.ce.common.validate.UpdatePwd;

/**
 * 用户  管理员（权限管理-用户操作员）
 * @author tangmin
 * @table sys_user
 * @date 2016年3月7日
 */
public class User extends BaseEntity{
	private static final long serialVersionUID = 4839089117571175337L;
	
	/**
	 * 省
	 */
	private Integer province=0;
	@Transient
	private String provinceName;
	/**
	 * 市
	 */
	private Integer city=0;
	@Transient
	private String cityName;
	/**
	/**
	 * 县
	 */
	private Integer town=0;
	@Transient
	private String townName;
	
	/**
	 * 校
	 */
	private Long school=0l;
	
	/**
	 * 培训机构id
	 */
	private Long org;
	@Transient
	private String schoolName;
	
	/** 用户编号(用户编号，用于)*/
	@NotBlank(message="用户编号不能为空",groups={Insert.class})
	@Size(max = 50,groups={Insert.class})
	private String userNo; 
	
	/** 登录名 */
	@NotBlank(message="登录名不能为空",groups={Insert.class,Update.class})
	@Size(max = 100,groups={Insert.class,Update.class})
	private String loginName;
	
	/** 登录密码 */
	@NotBlank(message="登录密码不能为空",groups={Insert.class,UpdatePwd.class})
	@Size(max = 255,groups={Insert.class,UpdatePwd.class})
	private String loginPwd;
	
	/**不对应数据库的字段**/
	private String plainPassword;
	
	/** 加盐 */
	@NotBlank(message="盐不能为空",groups={Insert.class,UpdatePwd.class})
	@Size(max = 255,groups={Insert.class,UpdatePwd.class})
	private String salt;
	
	/**
	 * md5密文(用于研修接口。。)
	 */
	private String md5;
	
	/**
	 * 发展网uid
	 */
	private Long uid;
	
	/**
	 * 单位
	 */
	private String unit;
	
	/**
	 * 是否开通发展网账号0 不开通，1开通
	 */
	private Boolean reflag=false;
	
	/**
	 * 研修网新增字段冗余
	 */
	private String idCard;
	private String course;
	private String stage;
	private String grade;
	private String gender;
	
	/** 姓名 */
	@NotBlank(message="姓名不能为空",groups={Insert.class,Update.class})
	@Size(max = 50,groups={Insert.class,Update.class})
	private String realName;
	
	/** 手机号 */
//	@NotBlank(message="手机号不能为空",groups={Insert.class,Update.class})
	@Size(max = 11,groups={Insert.class,Update.class})
	private String mobileNo;
	/** Email */
	
//	@NotBlank(message="Email不能为空",groups={Insert.class,Update.class})
	@Size(max = 100,groups={Insert.class,Update.class})
	private String email;
	
	/** 状态 UserStatusEnum */
	@NotNull(message="状态不能为空",groups={Insert.class,Update.class})
	private Integer status;
	
	/** 用户类型UserTypeEnum */
	@NotNull(message="用户类型不能为空",groups={Insert.class,Update.class})
	private Integer type;
	
	/** 是否更改过密码(0:否,1:是) **/
	@NotNull(message="是否更改过密码",groups={UpdatePwd.class})
	private Integer isChangedPwd;
	
	/** 登录密码错误次数 **/
	private Integer pwdErrorTimes;
	/** 最后登录时间 **/
	private Date lastLoginTime;
	/** 最后一次登录密码错误时间 **/
	private Date pwdErrorLastTime;
	
	/** 最后一次修改时间 **/
	@NotNull(message="最后一次修改时间不能为空",groups={UpdatePwd.class})
	private Date lastAlertPwdTime;
	
	public Integer getProvince() {
		return province;
	}
	public void setProvince(Integer province) {
		this.province = province;
	}
	public Integer getCity() {
		return city;
	}
	public void setCity(Integer city) {
		this.city = city;
	}
	public Integer getTown() {
		return town;
	}
	public void setTown(Integer town) {
		this.town = town;
	}
	/**
	 * 用户编号
	 * @return
	 */
	public String getUserNo() {
		return userNo;
	}
	
	public Long getSchool() {
		return school;
	}
	public void setSchool(Long school) {
		this.school = school;
	}
	/**
	 * 用户编号
	 * @param userNo
	 */
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	/**
	 * 登录名
	 * @return
	 */
	public String getLoginName() {
		return loginName;
	}
	/**
	 * 登录名
	 * @param loginName
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	/**
	 *  登录密码
	 * @return
	 */
	public String getLoginPwd() {
		return loginPwd;
	}
	/**
	 *  登录密码
	 * @param loginPwd
	 */
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
	public String getPlainPassword() {
		return plainPassword;
	}
	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}
	/**
	 * 姓名
	 * @return
	 */
	public String getRealName() {
		return realName;
	}
	/**
	 * 姓名
	 * @param realName
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	/**
	 * 加盐
	 * @return
	 */
	public String getSalt() {
		return salt;
	}
	/**
	 * 加盐
	 * @return
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	/**
	 * 手机号 
	 * @return
	 */
	public String getMobileNo() {
		return mobileNo;
	}
	/**
	 * 手机号 
	 * @param mobileNo
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	/**
	 * Email
	 * @return
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * Email
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 *  状态 UserStatusEnum
	 * @return
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 状态 UserStatusEnum
	 * @param status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 操作员类型
	 * @return
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 操作员类型
	 * @param type
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 是否更改过密码(0:否,1:是)
	 * @return
	 */
	public Integer getIsChangedPwd() {
		return isChangedPwd;
	}
	/**
	 * 是否更改过密码(0:否,1:是)
	 * @param isChangedPwd
	 */
	public void setIsChangedPwd(Integer isChangedPwd) {
		this.isChangedPwd = isChangedPwd;
	}
	/**
	 * 登录密码错误次数
	 * @return
	 */
	public Integer getPwdErrorTimes() {
		return pwdErrorTimes;
	}
	/**
	 * 登录密码错误次数
	 * @param pwdErrorTimes
	 */
	public void setPwdErrorTimes(Integer pwdErrorTimes) {
		this.pwdErrorTimes = pwdErrorTimes;
	}
	/**
	 * 最后登录时间
	 * @return
	 */
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	/**
	 * 最后登录时间
	 * @param lastLoginTime
	 */
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	/**
	 * 最后一次登录密码错误时间
	 * @return
	 */
	public Date getPwdErrorLastTime() {
		return pwdErrorLastTime;
	}
	/**
	 * 最后一次登录密码错误时间
	 * @param pwdErrorLastTime
	 */
	public void setPwdErrorLastTime(Date pwdErrorLastTime) {
		this.pwdErrorLastTime = pwdErrorLastTime;
	}
	/**
	 * 最后一次修改时间
	 * @return
	 */
	public Date getLastAlertPwdTime() {
		return lastAlertPwdTime;
	}
	/**
	 * 最后一次修改时间
	 * @param lastAlertPwdTime
	 */
	public void setLastAlertPwdTime(Date lastAlertPwdTime) {
		this.lastAlertPwdTime = lastAlertPwdTime;
	}
	
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getTownName() {
		return townName;
	}
	public void setTownName(String townName) {
		this.townName = townName;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	
	public Long getOrg() {
		return org;
	}
	public void setOrg(Long org) {
		this.org = org;
	}
	
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	
	public Boolean getReflag() {
		return reflag;
	}
	public void setReflag(Boolean reflag) {
		this.reflag = reflag;
	}
	
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	@Override
	public String toString() {
		return "User [province=" + province + ", city=" + city + ", town="
				+ town + ", school=" + school + ", userNo=" + userNo
				+ ", loginName=" + loginName + ", loginPwd=" + loginPwd
				+ ", plainPassword=" + plainPassword + ", salt=" + salt
				+ ", realName=" + realName + ", mobileNo=" + mobileNo
				+ ", email=" + email + ", status=" + status + ", type=" + type
				+ ", isChangedPwd=" + isChangedPwd + ", pwdErrorTimes="
				+ pwdErrorTimes + ", lastLoginTime=" + lastLoginTime
				+ ", pwdErrorLastTime=" + pwdErrorLastTime
				+ ", lastAlertPwdTime=" + lastAlertPwdTime + "]";
	}
}
