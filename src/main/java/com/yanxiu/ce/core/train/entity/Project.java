package com.yanxiu.ce.core.train.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.mybatis.annotation.Transient;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.Update;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 项目信息
 * @author tangmin
 * @date 2016年4月11日
 */
public class Project extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/**
	 * userId
	 * 谁创建的，谁管理
	 */
	private Long userId;
	
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
	@Transient
	private String schoolName;
	
	@NotBlank(message="项目名称不能为空",groups={Insert.class,Update.class})
	@Length(max=200,message="项目名称长度不能超过200",groups={Insert.class,Update.class})
	private String name;
	
	/**
	 * 项目编号 SEQUE
	 */
	private String pno;
	
	/**
	 * 学段
	 */
	@NotNull(message="学段不能为空",groups={Insert.class,Update.class})
	private Long stage;
	@Transient
	private String stageName;
	
	/**
	 * 学科
	 */
	@NotNull(message="学科不能为空",groups={Insert.class,Update.class})
	private Long course;
	@Transient
	private String courseName;
	
	/**
	 * 国家级	省级	地市级	区县级	学校级
	 */
	@NotNull(message="培训等级 不能为空",groups={Insert.class,Update.class})
	private Long trainLevel;
	@Transient
	private String trainLevelName;
	
	/**
	 * 项目类型    字典TRAIN_TYPE  : 班主任培训	新教师培训	信息技术应用能力培训	校长任职培训	校长提高培训	校长高级研修	校长专题培训
	 */
	@NotNull(message="培训类型 不能为空",groups={Insert.class,Update.class})
	private Long trainType;
	@Transient
	private String trainTypeName;
	
	/**
	 * 学年  字典：SCHOOL_YEAR
	 */
	private Long schoolYear;
	@Transient
	private String schoolYearName;
	
	/**
	 * 学时
	 */
	@NotNull(message="学时 不能为空",groups={Insert.class,Update.class})
	private Double hour;
	
	/**
	 * 开始时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@NotNull(message="开始时间 不能为空",groups={Insert.class,Update.class})
	private Date startDate;
	
	/**
	 * 结束时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@NotNull(message="结束时间 不能为空",groups={Insert.class,Update.class})
	private Date endDate;
	
	/**
	 * 项目内容
	 */
	@Size(max = 400,groups={Insert.class,Update.class})
	private String description;
	
	/**  
	 * 培训方式  （ProjectImplWayEnum集中培训     网络研修	影子学习	在岗实践	混合式学习	校本研修	专题培训）
	 */
	private String implWay;
	
	/**
	 * 备注
	 */
	@Size(max = 400,groups={Insert.class,Update.class})
	private String memo;
	
	/**
	 * 创建该项目的管理员的userType  对应UserTypeEnum
	 */
	private Integer userType;
	
	/**
	 * 项目状态 ：ProjectEnum
	 * @return
	 */
	private Integer status;

	/**
	 * 项目学分生成方式 ScoreCreateTypeEnum
	 */
	private Integer scoreCreateType;
	
	
	/**
	 * 项目审批状态（学校创建的项目需要审批）
	 * @return
	 */
	private Integer cstatus;
	/**
	 * 项目审批意见（学校创建的项目需要审批）
	 */
	private String coption;
	
//	1) 增加“面向岗位”，内容如附件，多选。
//	2) 学段，变更下拉项内容，为多选。
//	3) 增加“人数”。文本框填写
//	4) 增加“学时获取规则" 优秀：———学时，合格———学时
	
	/**
	 * 面向岗位     教师岗位，其他专业技术岗位，管理岗位，工勤技能岗位   FACE_DEPTS
	 */
	private String faceDepts;
	/**
	 * 学段  TeacherTypeEnum
	 * @return
	 */
	private String teacherTypes;
	
	/**
	 * 人数
	 * @return
	 */
	private Integer personNum;
	
	/**
	 * 优秀   多少学分
	 * @return
	 */
	private Double goodScore;
	/**
	 * 及格  多少学分
	 */
	private Double passScore;
	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getTown() {
		return town;
	}

	public void setTown(Integer town) {
		this.town = town;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public Long getSchool() {
		return school;
	}

	public void setSchool(Long school) {
		this.school = school;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPno() {
		return pno;
	}

	public void setPno(String pno) {
		this.pno = pno;
	}

	public Long getStage() {
		return stage;
	}

	public void setStage(Long stage) {
		this.stage = stage;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public Long getCourse() {
		return course;
	}

	public void setCourse(Long course) {
		this.course = course;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Long getTrainLevel() {
		return trainLevel;
	}

	public void setTrainLevel(Long trainLevel) {
		this.trainLevel = trainLevel;
	}

	public String getTrainLevelName() {
		return trainLevelName;
	}

	public void setTrainLevelName(String trainLevelName) {
		this.trainLevelName = trainLevelName;
	}

	public Long getTrainType() {
		return trainType;
	}

	public void setTrainType(Long trainType) {
		this.trainType = trainType;
	}

	public String getTrainTypeName() {
		return trainTypeName;
	}

	public void setTrainTypeName(String trainTypeName) {
		this.trainTypeName = trainTypeName;
	}

	public Long getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Long schoolYear) {
		this.schoolYear = schoolYear;
	}

	public String getSchoolYearName() {
		return schoolYearName;
	}

	public void setSchoolYearName(String schoolYearName) {
		this.schoolYearName = schoolYearName;
	}

	public Double getHour() {
		return hour;
	}

	public void setHour(Double hour) {
		this.hour = hour;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImplWay() {
		return implWay;
	}

	public void setImplWay(String implWay) {
		this.implWay = implWay;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getScoreCreateType() {
		return scoreCreateType;
	}

	public void setScoreCreateType(Integer scoreCreateType) {
		this.scoreCreateType = scoreCreateType;
	}

	public Integer getCstatus() {
		return cstatus;
	}

	public void setCstatus(Integer cstatus) {
		this.cstatus = cstatus;
	}

	public String getCoption() {
		return coption;
	}

	public void setCoption(String coption) {
		this.coption = coption;
	}

	public String getFaceDepts() {
		return faceDepts;
	}

	public void setFaceDepts(String faceDepts) {
		this.faceDepts = faceDepts;
	}

	public String getTeacherTypes() {
		return teacherTypes;
	}

	public void setTeacherTypes(String teacherTypes) {
		this.teacherTypes = teacherTypes;
	}

	public Integer getPersonNum() {
		return personNum;
	}

	public void setPersonNum(Integer personNum) {
		this.personNum = personNum;
	}

	public Double getGoodScore() {
		return goodScore;
	}

	public void setGoodScore(Double goodScore) {
		this.goodScore = goodScore;
	}

	public Double getPassScore() {
		return passScore;
	}

	public void setPassScore(Double passScore) {
		this.passScore = passScore;
	}
}
