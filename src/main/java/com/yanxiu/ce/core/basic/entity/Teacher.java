package com.yanxiu.ce.core.basic.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.mybatis.annotation.Transient;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.Update;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 1、教师基本信息
 * @author tangmin
 * @date 2016年3月30日
 */
public class Teacher extends BaseEntity{

	private static final long serialVersionUID = 1L;

	/**
	 * 1 *姓名
	 */
	@NotBlank(message="姓名不能为空",groups={Insert.class,Update.class})
	@Length(max=20,message="姓名长度不能超过20",groups={Insert.class,Update.class})
	private String name;
	
	/**
	 * 2+曾用名
	 */
	@Length(max=20,message="姓名长度不能超过20",groups={Insert.class,Update.class})
	private String oldName;
	
	/**
	 * 3 +* 性别  字典 GENDER
	 */
	@NotNull(message="性别不能为空",groups={Insert.class,Update.class})
	private Long gender;
	@Transient
	private String genderName;
	
	/**
	 * 4+教职工号   默认0
	 */
	private String tchWorkNum;
	
	/**
	 * 5+* 国籍/地区  字典COUNTRY_TYPE
	 */
	@NotNull(message="国籍/地区不能为空",groups={Insert.class,Update.class})
	private Long nationality;
	@Transient
	private String nationalityName;
	
	/**
	 * 6+*  身份证件类型  字典 PAPER_TYPE
	 */
	@NotNull(message="身份证件类型不能为空",groups={Insert.class,Update.class})
	private Long paperType;
	@Transient
	private String paperTypeName;
	
	/**
	 * 7+*  身份证件号
	 */
	@Length(message="身份证件号最大长度不能超过18位",max = 18,groups={Insert.class,Update.class})
	private String idCard;
	
	
	/**
	 * 8 *出生日期
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@NotNull(message="出生日期",groups={Insert.class,Update.class})
	@Past(message="出生日期只能为过去的时间",groups={Insert.class,Update.class})
	private Date birthday;
	

	/**
	 * 照片文件名称
	 */
	private String path;
	
	/**
	 * 9+籍贯
	 */
	private String nativer;

	/**
	 * 10+出生地
	 */
	private String birthPlace;

	/**
	 *11* 民族  字典NATION
	 */
	private Long nation;
	@Transient
	private String nationName;


	/**
	 *12+* 政治面貌  POLITIC_TYPE
	 */
	private String politicType;
	@Transient
	private String politicTypeName;
	
	/**
	 * 13+ 婚姻状况
	 */
	private Long marry;
	@Transient
	private String marryName;
	
	/**
	 * 14+健康状况 HEALTH_CONDITION
	 */
	private Long health;
	@Transient
	private String healthName;
	
//	/**
//	 * 15+ 最高学历 引自“学习经历信息表”
//	 */
//	@Transient
//	private String highestEdu;
//	/**
//	 * 16+ 获得最高学历的院校或机构 引自“学习经历信息表”
//	 */
//	@Transient
//	private String highestEduOrg;
//	/**
//	 * 17+ 最高学位层次 引自“学习经历信息表”
//	 */
//	@Transient
//	private String highestDegreeLevel;
//	/**
//	 * 18+ 最高学位名称 引自“学习经历信息表”
//	 */
//	@Transient
//	private String highestDegree;
//
//	/**
//	 * 19+ 获得最高学位的院校或机构 引自“学习经历信息表”
//	 */
//	@Transient
//	private String highestDegreeOrg;
	
	/**
	 * 20+* 参加工作年月
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@NotNull(message="参加工作年月",groups={Insert.class,Update.class})
	@Past(message="参加工作年月只能为过去的时间",groups={Insert.class,Update.class})
	private Date workDay;

	/**
	 * 21+*进本校年月
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@NotNull(message="进本校年月",groups={Insert.class,Update.class})
	@Past(message="进本校年月只能为过去的时间",groups={Insert.class,Update.class})
	private Date joinSchoolDay;

	/**
	 * 22_1+*教职工来源   字典tree，单选，可选父节点   WORKER_FROM_1
	 */
	private Long workerFrom1;
	@Transient
	private String workerFrom1Name;

	/**
	 * 22_2+*教职工来源   字典tree，单选，可选父节点   WORKER_FROM_2
	 */
	private Long workerFrom2;
	@Transient
	private String workerFrom2Name;
	
	/**
	 * 23_1+* 教职工类别  字典tree，单选，可选父节点    WORKER_TYPE_1
	 */
	private Long workerType1;
	@Transient
	private String workerType1Name;

	/**
	 * 23_2+* 教职工类别  字典tree，单选，可选父节点    WORKER_TYPE_2
	 */
	private Long workerType2;
	@Transient
	private String workerType2Name;
	
	private Long workerType3;
	@Transient
	private String workerType3Name;
	
	private Long workerType4;
	@Transient
	private String workerType4Name;

	/**
	 * 24+* 是否在编    字典：  IS_FLAG
	 */
	private Long atSchool;
	@Transient
	private String atSchoolName;

	/**
	 * 25+* 用人形式    字典：USE_PERSON_TYPE
	 */
	private Long usePersonType;
	@Transient
	private String usePersonTypeName;

	/**
	 * 26+* 签订合同情况 字典：SIGN_CONTRACT
	 */
	private Long signContract;
	@Transient
	private String signContractName;

//	/**
//	 * 27+ 现任岗位类别	　	引自“岗位聘任信息表”
//	 */
//	@Transient
//	private String postType;
//	/**
//	 * 28+ 现任岗位等级	　	引自“岗位聘任信息表”
//	 */
//	@Transient
//	private String postLevel;
//	/**
//	 * 29+ 现任专业技术职务	　	引自“专业技术职务聘任信息表”
//	 */
//	@Transient
//	private String professionDuty;


	/**
	 * 30+*是否全日制师范类专业毕业    字典：  IS_FLAG
	 */
	private Long isQrz;
	@Transient
	private String isQrzName;

	/**
	 * 31+*是否受过特教专业培养培训   字典：  IS_FLAG
	 */
	private Long isTjpx;
	@Transient
	private String isTjpxName;
	
	/**
	 * 32+*是否有特殊教育从业证书   字典：  IS_FLAG
	 */
	private Long isTszs;
	@Transient
	private String isTszsName;

	/**
	 * 33+* 信息技术应用能力    字典：  COMPUTER_ABILITY
	 */
	private Long computerAbility;
	@Transient
	private String computerAbilityName;

	/**
	 * 34+ 是否属于免费（公费）师范生    字典：  IS_MAIN_FLAG
	 */
	private Long isMian;
	@Transient
	private String isMianName;


	/**
	 * 35+*是否参加基层服务项目  字典tree：  IS_JOIN_BASE
	 */
	private Long isJoinBase;
	@Transient
	private String isJoinBaseName;

	/**
	 * 36+参加基层服务项目起始年月
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@Past(message="参加基层服务项目起始年月只能为过去的时间",groups={Insert.class,Update.class})
	private Date joinBaseStart;

	/**
	 * 37+参加基层服务项目结束年月
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@Past(message="参加基层服务项目结束年月只能为过去的时间",groups={Insert.class,Update.class})
	private Date joinBaseEnd;


	/**
	 * 38+是否是特级教师     字典：  IS_FLAG
	 */
	private Long isTj;
	@Transient
	private String isTjName;

	/**
	 * 39+是否县级及以上骨干教师  字典:IS_FLAG
	 */
	private Long isTownUpBone;
	@Transient
	private String isTownUpBoneName;

	/**
	 * 40+ 是否心理健康教育教师   字典：IS_HEALTH_FLAG
	 */
	private Long isHealth;
	@Transient
	private String isHealthName;


	/**
	 * 41+ 人员状态   字典：PERSON_STATUS
	 */
	private Long personStatus;
	@Transient
	private String personStatusName;

	/**
	 * _2+  是否“双师型”教师     字典：IS_FLAG
	 */
	private Long isDoubleTch;
	@Transient
	private String isDoubleTchName;

	/**
	 * _2+ 是否具备职业技能等级证书     字典：IS_FLAG
	 */
	private Long isProfessCard;
	@Transient
	private String isProfessCardName;

	/**
	 * _2+企业工作(实践)时长      字典：WORK_DATE_TIMER
	 */
	private Long workDateTimer;
	@Transient
	private String workDateTimerName;
	
	/**
	 * _4+是否全日制学前教育专业毕业
	 */
	private Long isPreTch;
	@Transient
	private String isPreTchName;
	
	/**
	 * _4+是否受过学前教育专业培养培训
	 */
	private Long isPreTrain;
	@Transient
	private String isPreTrainName;
	
	/**
	 * 从事特教起始年月
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@Past(message="从事特教起始年月只能为过去的时间",groups={Insert.class,Update.class})
	private Date specStartDate;
	
	/**
	 * 学校类型
	 */
	private Integer schoolType;
	/**
	 * 教师状态
	 */
	private Integer status;
	/**
	 * 审批意见
	 */
	private String remark;

	
	/*********************************************************原系统字段start***************************************************/
	
	/**
	 * 教师编号  16位编码 TEARCHER_NO_SEQ
	 */
	private String tno;
	
	/**
	 * 0 教师，1 校长  （现在全为0）
	 */
	private Integer type=0;
	
	/**
	 * 省市县字段做冗余，便于查询统计。在修改学校的时候，同时更改省市县的信息
	 */
	private Integer province;
	private Integer city;
	private Integer town;
	private Long school;
	@Transient
	private String provinceName;
	@Transient
	private String cityName;
	@Transient
	private String townName;
	@Transient
	private String schoolName;
	
	/**
	 * 备注
	 * @return
	 */
	@Size(max = 200,groups={Insert.class,Update.class})
	private String memo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public Long getGender() {
		return gender;
	}

	public void setGender(Long gender) {
		this.gender = gender;
	}

	public String getTchWorkNum() {
		return tchWorkNum;
	}

	public void setTchWorkNum(String tchWorkNum) {
		this.tchWorkNum = tchWorkNum;
	}

	public Long getNationality() {
		return nationality;
	}

	public void setNationality(Long nationality) {
		this.nationality = nationality;
	}

	public Long getPaperType() {
		return paperType;
	}

	public void setPaperType(Long paperType) {
		this.paperType = paperType;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getNativer() {
		return nativer;
	}

	public void setNativer(String nativer) {
		this.nativer = nativer;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public Long getNation() {
		return nation;
	}

	public void setNation(Long nation) {
		this.nation = nation;
	}

	public String getNationName() {
		return nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	public String getPoliticType() {
		return politicType;
	}

	public void setPoliticType(String politicType) {
		this.politicType = politicType;
	}

	public Long getMarry() {
		return marry;
	}

	public void setMarry(Long marry) {
		this.marry = marry;
	}

	public String getMarryName() {
		return marryName;
	}

	public void setMarryName(String marryName) {
		this.marryName = marryName;
	}

	public Long getHealth() {
		return health;
	}

	public void setHealth(Long health) {
		this.health = health;
	}

	public Date getWorkDay() {
		return workDay;
	}

	public void setWorkDay(Date workDay) {
		this.workDay = workDay;
	}

	public Date getJoinSchoolDay() {
		return joinSchoolDay;
	}

	public void setJoinSchoolDay(Date joinSchoolDay) {
		this.joinSchoolDay = joinSchoolDay;
	}

	public Long getWorkerFrom1() {
		return workerFrom1;
	}

	public void setWorkerFrom1(Long workerFrom1) {
		this.workerFrom1 = workerFrom1;
	}

	public Long getWorkerFrom2() {
		return workerFrom2;
	}

	public void setWorkerFrom2(Long workerFrom2) {
		this.workerFrom2 = workerFrom2;
	}


	public Long getAtSchool() {
		return atSchool;
	}

	public void setAtSchool(Long atSchool) {
		this.atSchool = atSchool;
	}

	public Long getUsePersonType() {
		return usePersonType;
	}

	public void setUsePersonType(Long usePersonType) {
		this.usePersonType = usePersonType;
	}

	public Long getSignContract() {
		return signContract;
	}

	public void setSignContract(Long signContract) {
		this.signContract = signContract;
	}

//	public String getPostType() {
//		return postType;
//	}
//
//	public void setPostType(String postType) {
//		this.postType = postType;
//	}
//
//	public String getPostLevel() {
//		return postLevel;
//	}
//
//	public void setPostLevel(String postLevel) {
//		this.postLevel = postLevel;
//	}
//
//	public String getProfessionDuty() {
//		return professionDuty;
//	}
//
//	public void setProfessionDuty(String professionDuty) {
//		this.professionDuty = professionDuty;
//	}

	public Long getIsQrz() {
		return isQrz;
	}

	public void setIsQrz(Long isQrz) {
		this.isQrz = isQrz;
	}

	public Long getIsTjpx() {
		return isTjpx;
	}

	public void setIsTjpx(Long isTjpx) {
		this.isTjpx = isTjpx;
	}

	public Long getIsTszs() {
		return isTszs;
	}

	public void setIsTszs(Long isTszs) {
		this.isTszs = isTszs;
	}

	public Long getComputerAbility() {
		return computerAbility;
	}

	public void setComputerAbility(Long computerAbility) {
		this.computerAbility = computerAbility;
	}

	public Long getIsMian() {
		return isMian;
	}

	public void setIsMian(Long isMian) {
		this.isMian = isMian;
	}

	public Long getIsJoinBase() {
		return isJoinBase;
	}

	public void setIsJoinBase(Long isJoinBase) {
		this.isJoinBase = isJoinBase;
	}

	public Date getJoinBaseStart() {
		return joinBaseStart;
	}

	public void setJoinBaseStart(Date joinBaseStart) {
		this.joinBaseStart = joinBaseStart;
	}

	public Date getJoinBaseEnd() {
		return joinBaseEnd;
	}

	public void setJoinBaseEnd(Date joinBaseEnd) {
		this.joinBaseEnd = joinBaseEnd;
	}

	public Long getIsTj() {
		return isTj;
	}

	public void setIsTj(Long isTj) {
		this.isTj = isTj;
	}

	public Long getIsTownUpBone() {
		return isTownUpBone;
	}

	public void setIsTownUpBone(Long isTownUpBone) {
		this.isTownUpBone = isTownUpBone;
	}

	public Long getIsHealth() {
		return isHealth;
	}

	public void setIsHealth(Long isHealth) {
		this.isHealth = isHealth;
	}

	public Long getPersonStatus() {
		return personStatus;
	}

	public void setPersonStatus(Long personStatus) {
		this.personStatus = personStatus;
	}

	public Long getIsDoubleTch() {
		return isDoubleTch;
	}

	public void setIsDoubleTch(Long isDoubleTch) {
		this.isDoubleTch = isDoubleTch;
	}

	public Long getIsProfessCard() {
		return isProfessCard;
	}

	public void setIsProfessCard(Long isProfessCard) {
		this.isProfessCard = isProfessCard;
	}

	public Long getWorkDateTimer() {
		return workDateTimer;
	}

	public void setWorkDateTimer(Long workDateTimer) {
		this.workDateTimer = workDateTimer;
	}

	public Long getIsPreTch() {
		return isPreTch;
	}

	public void setIsPreTch(Long isPreTch) {
		this.isPreTch = isPreTch;
	}

	public Long getIsPreTrain() {
		return isPreTrain;
	}

	public void setIsPreTrain(Long isPreTrain) {
		this.isPreTrain = isPreTrain;
	}

	public Integer getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(Integer schoolType) {
		this.schoolType = schoolType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Long getSchool() {
		return school;
	}

	public void setSchool(Long school) {
		this.school = school;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getWorkerType1() {
		return workerType1;
	}

	public void setWorkerType1(Long workerType1) {
		this.workerType1 = workerType1;
	}

	public Long getWorkerType2() {
		return workerType2;
	}

	public void setWorkerType2(Long workerType2) {
		this.workerType2 = workerType2;
	}

	public Long getWorkerType3() {
		return workerType3;
	}

	public void setWorkerType3(Long workerType3) {
		this.workerType3 = workerType3;
	}

	public Long getWorkerType4() {
		return workerType4;
	}

	public void setWorkerType4(Long workerType4) {
		this.workerType4 = workerType4;
	}

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

	public String getNationalityName() {
		return nationalityName;
	}

	public void setNationalityName(String nationalityName) {
		this.nationalityName = nationalityName;
	}

	public String getPaperTypeName() {
		return paperTypeName;
	}

	public void setPaperTypeName(String paperTypeName) {
		this.paperTypeName = paperTypeName;
	}

	public String getPoliticTypeName() {
		return politicTypeName;
	}

	public void setPoliticTypeName(String politicTypeName) {
		this.politicTypeName = politicTypeName;
	}

	public String getHealthName() {
		return healthName;
	}

	public void setHealthName(String healthName) {
		this.healthName = healthName;
	}

	public String getWorkerFrom1Name() {
		return workerFrom1Name;
	}

	public void setWorkerFrom1Name(String workerFrom1Name) {
		this.workerFrom1Name = workerFrom1Name;
	}

	public String getWorkerFrom2Name() {
		return workerFrom2Name;
	}

	public void setWorkerFrom2Name(String workerFrom2Name) {
		this.workerFrom2Name = workerFrom2Name;
	}

	public String getWorkerType1Name() {
		return workerType1Name;
	}

	public void setWorkerType1Name(String workerType1Name) {
		this.workerType1Name = workerType1Name;
	}

	public String getWorkerType2Name() {
		return workerType2Name;
	}

	public void setWorkerType2Name(String workerType2Name) {
		this.workerType2Name = workerType2Name;
	}

	public String getWorkerType3Name() {
		return workerType3Name;
	}

	public void setWorkerType3Name(String workerType3Name) {
		this.workerType3Name = workerType3Name;
	}

	public String getWorkerType4Name() {
		return workerType4Name;
	}

	public void setWorkerType4Name(String workerType4Name) {
		this.workerType4Name = workerType4Name;
	}

	public String getAtSchoolName() {
		return atSchoolName;
	}

	public void setAtSchoolName(String atSchoolName) {
		this.atSchoolName = atSchoolName;
	}

	public String getUsePersonTypeName() {
		return usePersonTypeName;
	}

	public void setUsePersonTypeName(String usePersonTypeName) {
		this.usePersonTypeName = usePersonTypeName;
	}

	public String getSignContractName() {
		return signContractName;
	}

	public void setSignContractName(String signContractName) {
		this.signContractName = signContractName;
	}

	public String getIsQrzName() {
		return isQrzName;
	}

	public void setIsQrzName(String isQrzName) {
		this.isQrzName = isQrzName;
	}

	public String getIsTjpxName() {
		return isTjpxName;
	}

	public void setIsTjpxName(String isTjpxName) {
		this.isTjpxName = isTjpxName;
	}

	public String getIsTszsName() {
		return isTszsName;
	}

	public void setIsTszsName(String isTszsName) {
		this.isTszsName = isTszsName;
	}

	public String getComputerAbilityName() {
		return computerAbilityName;
	}

	public void setComputerAbilityName(String computerAbilityName) {
		this.computerAbilityName = computerAbilityName;
	}

	public String getIsMianName() {
		return isMianName;
	}

	public void setIsMianName(String isMianName) {
		this.isMianName = isMianName;
	}

	public String getIsJoinBaseName() {
		return isJoinBaseName;
	}

	public void setIsJoinBaseName(String isJoinBaseName) {
		this.isJoinBaseName = isJoinBaseName;
	}

	public String getIsTjName() {
		return isTjName;
	}

	public void setIsTjName(String isTjName) {
		this.isTjName = isTjName;
	}

	public String getIsTownUpBoneName() {
		return isTownUpBoneName;
	}

	public void setIsTownUpBoneName(String isTownUpBoneName) {
		this.isTownUpBoneName = isTownUpBoneName;
	}

	public String getIsHealthName() {
		return isHealthName;
	}

	public void setIsHealthName(String isHealthName) {
		this.isHealthName = isHealthName;
	}

	public String getPersonStatusName() {
		return personStatusName;
	}

	public void setPersonStatusName(String personStatusName) {
		this.personStatusName = personStatusName;
	}

	public String getIsDoubleTchName() {
		return isDoubleTchName;
	}

	public void setIsDoubleTchName(String isDoubleTchName) {
		this.isDoubleTchName = isDoubleTchName;
	}

	public String getIsProfessCardName() {
		return isProfessCardName;
	}

	public void setIsProfessCardName(String isProfessCardName) {
		this.isProfessCardName = isProfessCardName;
	}

	public String getWorkDateTimerName() {
		return workDateTimerName;
	}

	public void setWorkDateTimerName(String workDateTimerName) {
		this.workDateTimerName = workDateTimerName;
	}

	public String getIsPreTchName() {
		return isPreTchName;
	}

	public void setIsPreTchName(String isPreTchName) {
		this.isPreTchName = isPreTchName;
	}

	public String getIsPreTrainName() {
		return isPreTrainName;
	}

	public void setIsPreTrainName(String isPreTrainName) {
		this.isPreTrainName = isPreTrainName;
	}

	public Date getSpecStartDate() {
		return specStartDate;
	}

	public void setSpecStartDate(Date specStartDate) {
		this.specStartDate = specStartDate;
	}
	
	
	/*********************************************************原系统字段end***************************************************/

}
