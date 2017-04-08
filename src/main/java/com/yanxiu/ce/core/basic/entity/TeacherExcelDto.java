package com.yanxiu.ce.core.basic.entity;

public class TeacherExcelDto {
	
	private String name;
	private String usedName;
	/**
	 * 1 男 0 女 
	 */
	private String sex;
	private String birthday;
	private String nativer;
	
	private String nationName;
	private String idCard;
	private String tno;
	
	/**
	 * 0 教师，1 校长
	 */
	private Integer type;
	private String tel;
	private String mobile;
	private String email;
	private String provinceName;
	private String cityName;
	private String townName;
	private String schoolName;
	
	/*****************TeacherArchival  start****/
	/**
	 * 
	 * 人员类别：专人教师、兼任教师    字典：PERSON_TYPE
	 */
	private String personType;
	
	/**
	 * 行政职务  行政类型 DUTY_TYPE
	 */
	private String duty;
	
	/**
	 * 职称    字典：TITLE_TYPE
	 */
	private String title;
	
	/**
	 * 教师资格   字典：QUALIFY_TYPE
	 */
	private String qualify;
	
	/**
	 * 骨干类型  字典 BONE_TYPE
	 */
	private String boneType;
	
	/**
	 * 岗位等级   字典 JOB_LEVEL
	 */
	private String jobLevel;
	
	/**
	 * 政治面貌    字典POLITIC_TYPE
	 */
	private String politic;
	
	/**
	 * 入党时间
	 */
	private String joinDay;
	
	/*********************TeacherArchival end***********/

	/**
	 * 最高学历   字典：DEGREE
	 */
	private Long degree;
	
	/**
	 * 所学专业
	 */
	private String major;
	
	/**
	 * 毕业院校
	 */
	private String univ;
	
	
	/**
	 * 从教时间
	 */
	private String teachedDay;
	
	/**
	 * 任职时间
	 */
	private String workedDay;
	
	/**
	 * 学段    Stage类
	 */
	private String stage;
	
	/**
	 * 学科 Course类
	 */
	private String course;
	
	/**
	 * 年级  Grade类
	 */
	private String grade;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsedName() {
		return usedName;
	}

	public void setUsedName(String usedName) {
		this.usedName = usedName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getNativer() {
		return nativer;
	}

	public void setNativer(String nativer) {
		this.nativer = nativer;
	}

	public String getNationName() {
		return nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getTno() {
		return tno;
	}

	public void setTno(String tno) {
		this.tno = tno;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
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

	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getQualify() {
		return qualify;
	}

	public void setQualify(String qualify) {
		this.qualify = qualify;
	}

	public String getBoneType() {
		return boneType;
	}

	public void setBoneType(String boneType) {
		this.boneType = boneType;
	}

	public String getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}

	public String getPolitic() {
		return politic;
	}

	public void setPolitic(String politic) {
		this.politic = politic;
	}

	public String getJoinDay() {
		return joinDay;
	}

	public void setJoinDay(String joinDay) {
		this.joinDay = joinDay;
	}

	public Long getDegree() {
		return degree;
	}

	public void setDegree(Long degree) {
		this.degree = degree;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getUniv() {
		return univ;
	}

	public void setUniv(String univ) {
		this.univ = univ;
	}

	public String getTeachedDay() {
		return teachedDay;
	}

	public void setTeachedDay(String teachedDay) {
		this.teachedDay = teachedDay;
	}

	public String getWorkedDay() {
		return workedDay;
	}

	public void setWorkedDay(String workedDay) {
		this.workedDay = workedDay;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
}
