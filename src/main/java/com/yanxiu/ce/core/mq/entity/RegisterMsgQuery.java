package com.yanxiu.ce.core.mq.entity;

import java.util.List;

import com.google.common.collect.Lists;
import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 消息管理
 * @author tangmin
 * @date 2016-08-12 18:39:04
 */
public class RegisterMsgQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String status;
	private Boolean statusLike = false;
	
	private String type;
	private Boolean typeLike = false;
	
	private String num;
	private Boolean numLike = false;
	
	private String memo;
	private Boolean memoLike = true;
	
	private String producerTime;
	private Boolean producerTimeLike = false;
	
	private String consumerTime;
	private Boolean consumerTimeLike = false;
	
	private String uid;
	private Boolean uidLike = false;
	
	private String realname;
	private Boolean realnameLike = false;
	
	private String password;
	private Boolean passwordLike = false;
	
	private String passport;
	private Boolean passportLike = false;
	
	private String appKey;
	private Boolean appKeyLike = false;
	
	private String mobile;
	private Boolean mobileLike = false;
	
	private String email;
	private Boolean emailLike = false;
	
	private String idCard;
	private Boolean idCardLike = false;
	
	private String schoolName;
	private Boolean schoolNameLike = true;
	
	private String course;
	private Boolean courseLike = false;
	
	private String stage;
	private Boolean stageLike = false;
	
	private String grade;
	private Boolean gradeLike = false;
	
	private String province;
	private Boolean provinceLike = false;
	
	private String city;
	private Boolean cityLike = false;
	
	private String area;
	private Boolean areaLike = false;
	
	private String gender;
	private Boolean genderLike = false;
	
	private Boolean selectJoinFlag = false;//查询多状态flag
	private List<Integer> joinPids = Lists.newArrayList();
	
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public Boolean getRealnameLike() {
		return realnameLike;
	}
	public void setRealnameLike(Boolean realnameLike) {
		this.realnameLike = realnameLike;
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
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public Boolean getNumLike() {
		return numLike;
	}
	public void setNumLike(Boolean numLike) {
		this.numLike = numLike;
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
	public String getProducerTime() {
		return producerTime;
	}
	public void setProducerTime(String producerTime) {
		this.producerTime = producerTime;
	}
	public Boolean getProducerTimeLike() {
		return producerTimeLike;
	}
	public void setProducerTimeLike(Boolean producerTimeLike) {
		this.producerTimeLike = producerTimeLike;
	}
	public String getConsumerTime() {
		return consumerTime;
	}
	public void setConsumerTime(String consumerTime) {
		this.consumerTime = consumerTime;
	}
	public Boolean getConsumerTimeLike() {
		return consumerTimeLike;
	}
	public void setConsumerTimeLike(Boolean consumerTimeLike) {
		this.consumerTimeLike = consumerTimeLike;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Boolean getUidLike() {
		return uidLike;
	}
	public void setUidLike(Boolean uidLike) {
		this.uidLike = uidLike;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getPasswordLike() {
		return passwordLike;
	}
	public void setPasswordLike(Boolean passwordLike) {
		this.passwordLike = passwordLike;
	}
	public String getPassport() {
		return passport;
	}
	public void setPassport(String passport) {
		this.passport = passport;
	}
	public Boolean getPassportLike() {
		return passportLike;
	}
	public void setPassportLike(Boolean passportLike) {
		this.passportLike = passportLike;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public Boolean getAppKeyLike() {
		return appKeyLike;
	}
	public void setAppKeyLike(Boolean appKeyLike) {
		this.appKeyLike = appKeyLike;
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
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public Boolean getSchoolNameLike() {
		return schoolNameLike;
	}
	public void setSchoolNameLike(Boolean schoolNameLike) {
		this.schoolNameLike = schoolNameLike;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public Boolean getCourseLike() {
		return courseLike;
	}
	public void setCourseLike(Boolean courseLike) {
		this.courseLike = courseLike;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public Boolean getStageLike() {
		return stageLike;
	}
	public void setStageLike(Boolean stageLike) {
		this.stageLike = stageLike;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public Boolean getGradeLike() {
		return gradeLike;
	}
	public void setGradeLike(Boolean gradeLike) {
		this.gradeLike = gradeLike;
	}
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
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Boolean getAreaLike() {
		return areaLike;
	}
	public void setAreaLike(Boolean areaLike) {
		this.areaLike = areaLike;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Boolean getGenderLike() {
		return genderLike;
	}
	public void setGenderLike(Boolean genderLike) {
		this.genderLike = genderLike;
	}
	public Boolean getSelectJoinFlag() {
		return selectJoinFlag;
	}
	public void setSelectJoinFlag(Boolean selectJoinFlag) {
		this.selectJoinFlag = selectJoinFlag;
	}
	public List<Integer> getJoinPids() {
		return joinPids;
	}
	public void setJoinPids(List<Integer> joinPids) {
		this.joinPids = joinPids;
	}
	
}