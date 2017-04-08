package com.yanxiu.ce.core.basic.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.yanxiu.ce.common.core.entity.BasePctsQuery;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 教师基本信息管理
 * @author tangmin
 * @date 2016-08-17 12:30:32
 */
public class TeacherQuery extends BasePctsQuery{
	private static final long serialVersionUID = 1L;
	
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date startBirthday;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date endBirthday;
	
	private String name;
	private Boolean nameLike = true;
	
	private String oldName;
	private Boolean oldNameLike = true;
	
	private String gender;
	private Boolean genderLike = false;
	
	private String tchWorkNum;
	private Boolean tchWorkNumLike = true;
	
	private String nationality;
	private Boolean nationalityLike = false;
	
	private String paperType;
	private Boolean paperTypeLike = false;
	
	private String idCard;
	private Boolean idCardLike = true;
	
	private String birthday;
	private Boolean birthdayLike = false;
	
	private String path;
	private Boolean pathLike = false;
	
	private String nativer;
	private Boolean nativerLike = true;
	
	private String birthPlace;
	private Boolean birthPlaceLike = true;
	
	private String nation;
	private Boolean nationLike = true;
	
	private String politicType;
	private Boolean politicTypeLike = false;
	
	private String marry;
	private Boolean marryLike = false;
	
	private String health;
	private Boolean healthLike = false;
	
	private String workDay;
	private Boolean workDayLike = false;
	
	private String joinSchoolDay;
	private Boolean joinSchoolDayLike = false;
	
	private String workerFrom1;
	private Boolean workerFrom1Like = false;
	
	private String workerFrom2;
	private Boolean workerFrom2Like = false;
	
	private String workerType1;
	private Boolean workerType1Like = false;
	
	private String workerType2;
	private Boolean workerType2Like = false;
	
	private String workerType3;
	private Boolean workerType3Like = false;
	
	private String workerType4;
	private Boolean workerType4Like = false;
	
	private String atSchool;
	private Boolean atSchoolLike = false;
	
	private String usePersonType;
	private Boolean usePersonTypeLike = false;
	
	private String signContract;
	private Boolean signContractLike = false;
	
	private String isQrz;
	private Boolean isQrzLike = false;
	
	private String isTjpx;
	private Boolean isTjpxLike = false;
	
	private String isTszs;
	private Boolean isTszsLike = false;
	
	private String computerAbility;
	private Boolean computerAbilityLike = false;
	
	private String isMian;
	private Boolean isMianLike = false;
	
	private String isJoinBase;
	private Boolean isJoinBaseLike = false;
	
	private String joinBaseStart;
	private Boolean joinBaseStartLike = false;
	
	private String joinBaseEnd;
	private Boolean joinBaseEndLike = false;
	
	private String isTj;
	private Boolean isTjLike = false;
	
	private String isTownUpBone;
	private Boolean isTownUpBoneLike = false;
	
	private String isHealth;
	private Boolean isHealthLike = false;
	
	private String personStatus;
	private Boolean personStatusLike = false;
	
	private String isDoubleTch;
	private Boolean isDoubleTchLike = false;
	
	private String isProfessCard;
	private Boolean isProfessCardLike = false;
	
	private String workDateTimer;
	private Boolean workDateTimerLike = false;
	
	private String isPreTch;
	private Boolean isPreTchLike = false;
	
	private String isPreTrain;
	private Boolean isPreTrainLike = false;
	
	private String schoolType;
	private Boolean schoolTypeLike = false;
	
	private String status;
	private Boolean statusLike = false;
	
	private String remark;
	private Boolean remarkLike = true;
	
	private String tno;
	private Boolean tnoLike = true;
	
	private String type;
	
	private String memo;
	private Boolean memoLike = true;
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
	public String getOldName() {
		return oldName;
	}
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
	public Boolean getOldNameLike() {
		return oldNameLike;
	}
	public void setOldNameLike(Boolean oldNameLike) {
		this.oldNameLike = oldNameLike;
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
	public String getTchWorkNum() {
		return tchWorkNum;
	}
	public void setTchWorkNum(String tchWorkNum) {
		this.tchWorkNum = tchWorkNum;
	}
	public Boolean getTchWorkNumLike() {
		return tchWorkNumLike;
	}
	public void setTchWorkNumLike(Boolean tchWorkNumLike) {
		this.tchWorkNumLike = tchWorkNumLike;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public Boolean getNationalityLike() {
		return nationalityLike;
	}
	public void setNationalityLike(Boolean nationalityLike) {
		this.nationalityLike = nationalityLike;
	}
	public String getPaperType() {
		return paperType;
	}
	public void setPaperType(String paperType) {
		this.paperType = paperType;
	}
	public Boolean getPaperTypeLike() {
		return paperTypeLike;
	}
	public void setPaperTypeLike(Boolean paperTypeLike) {
		this.paperTypeLike = paperTypeLike;
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
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public Boolean getBirthdayLike() {
		return birthdayLike;
	}
	public void setBirthdayLike(Boolean birthdayLike) {
		this.birthdayLike = birthdayLike;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Boolean getPathLike() {
		return pathLike;
	}
	public void setPathLike(Boolean pathLike) {
		this.pathLike = pathLike;
	}
	public String getNativer() {
		return nativer;
	}
	public void setNativer(String nativer) {
		this.nativer = nativer;
	}
	public Boolean getNativerLike() {
		return nativerLike;
	}
	public void setNativerLike(Boolean nativerLike) {
		this.nativerLike = nativerLike;
	}
	public String getBirthPlace() {
		return birthPlace;
	}
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}
	public Boolean getBirthPlaceLike() {
		return birthPlaceLike;
	}
	public void setBirthPlaceLike(Boolean birthPlaceLike) {
		this.birthPlaceLike = birthPlaceLike;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public Boolean getNationLike() {
		return nationLike;
	}
	public void setNationLike(Boolean nationLike) {
		this.nationLike = nationLike;
	}
	public String getPoliticType() {
		return politicType;
	}
	public void setPoliticType(String politicType) {
		this.politicType = politicType;
	}
	public Boolean getPoliticTypeLike() {
		return politicTypeLike;
	}
	public void setPoliticTypeLike(Boolean politicTypeLike) {
		this.politicTypeLike = politicTypeLike;
	}
	public String getMarry() {
		return marry;
	}
	public void setMarry(String marry) {
		this.marry = marry;
	}
	public Boolean getMarryLike() {
		return marryLike;
	}
	public void setMarryLike(Boolean marryLike) {
		this.marryLike = marryLike;
	}
	public String getHealth() {
		return health;
	}
	public void setHealth(String health) {
		this.health = health;
	}
	public Boolean getHealthLike() {
		return healthLike;
	}
	public void setHealthLike(Boolean healthLike) {
		this.healthLike = healthLike;
	}
	public String getWorkDay() {
		return workDay;
	}
	public void setWorkDay(String workDay) {
		this.workDay = workDay;
	}
	public Boolean getWorkDayLike() {
		return workDayLike;
	}
	public void setWorkDayLike(Boolean workDayLike) {
		this.workDayLike = workDayLike;
	}
	public String getJoinSchoolDay() {
		return joinSchoolDay;
	}
	public void setJoinSchoolDay(String joinSchoolDay) {
		this.joinSchoolDay = joinSchoolDay;
	}
	public Boolean getJoinSchoolDayLike() {
		return joinSchoolDayLike;
	}
	public void setJoinSchoolDayLike(Boolean joinSchoolDayLike) {
		this.joinSchoolDayLike = joinSchoolDayLike;
	}
	public String getWorkerFrom1() {
		return workerFrom1;
	}
	public void setWorkerFrom1(String workerFrom1) {
		this.workerFrom1 = workerFrom1;
	}
	public Boolean getWorkerFrom1Like() {
		return workerFrom1Like;
	}
	public void setWorkerFrom1Like(Boolean workerFrom1Like) {
		this.workerFrom1Like = workerFrom1Like;
	}
	public String getWorkerFrom2() {
		return workerFrom2;
	}
	public void setWorkerFrom2(String workerFrom2) {
		this.workerFrom2 = workerFrom2;
	}
	public Boolean getWorkerFrom2Like() {
		return workerFrom2Like;
	}
	public void setWorkerFrom2Like(Boolean workerFrom2Like) {
		this.workerFrom2Like = workerFrom2Like;
	}
	public String getWorkerType1() {
		return workerType1;
	}
	public void setWorkerType1(String workerType1) {
		this.workerType1 = workerType1;
	}
	public Boolean getWorkerType1Like() {
		return workerType1Like;
	}
	public void setWorkerType1Like(Boolean workerType1Like) {
		this.workerType1Like = workerType1Like;
	}
	public String getWorkerType2() {
		return workerType2;
	}
	public void setWorkerType2(String workerType2) {
		this.workerType2 = workerType2;
	}
	public Boolean getWorkerType2Like() {
		return workerType2Like;
	}
	public void setWorkerType2Like(Boolean workerType2Like) {
		this.workerType2Like = workerType2Like;
	}
	public String getWorkerType3() {
		return workerType3;
	}
	public void setWorkerType3(String workerType3) {
		this.workerType3 = workerType3;
	}
	public Boolean getWorkerType3Like() {
		return workerType3Like;
	}
	public void setWorkerType3Like(Boolean workerType3Like) {
		this.workerType3Like = workerType3Like;
	}
	public String getWorkerType4() {
		return workerType4;
	}
	public void setWorkerType4(String workerType4) {
		this.workerType4 = workerType4;
	}
	public Boolean getWorkerType4Like() {
		return workerType4Like;
	}
	public void setWorkerType4Like(Boolean workerType4Like) {
		this.workerType4Like = workerType4Like;
	}
	public String getAtSchool() {
		return atSchool;
	}
	public void setAtSchool(String atSchool) {
		this.atSchool = atSchool;
	}
	public Boolean getAtSchoolLike() {
		return atSchoolLike;
	}
	public void setAtSchoolLike(Boolean atSchoolLike) {
		this.atSchoolLike = atSchoolLike;
	}
	public String getUsePersonType() {
		return usePersonType;
	}
	public void setUsePersonType(String usePersonType) {
		this.usePersonType = usePersonType;
	}
	public Boolean getUsePersonTypeLike() {
		return usePersonTypeLike;
	}
	public void setUsePersonTypeLike(Boolean usePersonTypeLike) {
		this.usePersonTypeLike = usePersonTypeLike;
	}
	public String getSignContract() {
		return signContract;
	}
	public void setSignContract(String signContract) {
		this.signContract = signContract;
	}
	public Boolean getSignContractLike() {
		return signContractLike;
	}
	public void setSignContractLike(Boolean signContractLike) {
		this.signContractLike = signContractLike;
	}
	public String getIsQrz() {
		return isQrz;
	}
	public void setIsQrz(String isQrz) {
		this.isQrz = isQrz;
	}
	public Boolean getIsQrzLike() {
		return isQrzLike;
	}
	public void setIsQrzLike(Boolean isQrzLike) {
		this.isQrzLike = isQrzLike;
	}
	public String getIsTjpx() {
		return isTjpx;
	}
	public void setIsTjpx(String isTjpx) {
		this.isTjpx = isTjpx;
	}
	public Boolean getIsTjpxLike() {
		return isTjpxLike;
	}
	public void setIsTjpxLike(Boolean isTjpxLike) {
		this.isTjpxLike = isTjpxLike;
	}
	public String getIsTszs() {
		return isTszs;
	}
	public void setIsTszs(String isTszs) {
		this.isTszs = isTszs;
	}
	public Boolean getIsTszsLike() {
		return isTszsLike;
	}
	public void setIsTszsLike(Boolean isTszsLike) {
		this.isTszsLike = isTszsLike;
	}
	public String getComputerAbility() {
		return computerAbility;
	}
	public void setComputerAbility(String computerAbility) {
		this.computerAbility = computerAbility;
	}
	public Boolean getComputerAbilityLike() {
		return computerAbilityLike;
	}
	public void setComputerAbilityLike(Boolean computerAbilityLike) {
		this.computerAbilityLike = computerAbilityLike;
	}
	public String getIsMian() {
		return isMian;
	}
	public void setIsMian(String isMian) {
		this.isMian = isMian;
	}
	public Boolean getIsMianLike() {
		return isMianLike;
	}
	public void setIsMianLike(Boolean isMianLike) {
		this.isMianLike = isMianLike;
	}
	public String getIsJoinBase() {
		return isJoinBase;
	}
	public void setIsJoinBase(String isJoinBase) {
		this.isJoinBase = isJoinBase;
	}
	public Boolean getIsJoinBaseLike() {
		return isJoinBaseLike;
	}
	public void setIsJoinBaseLike(Boolean isJoinBaseLike) {
		this.isJoinBaseLike = isJoinBaseLike;
	}
	public String getJoinBaseStart() {
		return joinBaseStart;
	}
	public void setJoinBaseStart(String joinBaseStart) {
		this.joinBaseStart = joinBaseStart;
	}
	public Boolean getJoinBaseStartLike() {
		return joinBaseStartLike;
	}
	public void setJoinBaseStartLike(Boolean joinBaseStartLike) {
		this.joinBaseStartLike = joinBaseStartLike;
	}
	public String getJoinBaseEnd() {
		return joinBaseEnd;
	}
	public void setJoinBaseEnd(String joinBaseEnd) {
		this.joinBaseEnd = joinBaseEnd;
	}
	public Boolean getJoinBaseEndLike() {
		return joinBaseEndLike;
	}
	public void setJoinBaseEndLike(Boolean joinBaseEndLike) {
		this.joinBaseEndLike = joinBaseEndLike;
	}
	public String getIsTj() {
		return isTj;
	}
	public void setIsTj(String isTj) {
		this.isTj = isTj;
	}
	public Boolean getIsTjLike() {
		return isTjLike;
	}
	public void setIsTjLike(Boolean isTjLike) {
		this.isTjLike = isTjLike;
	}
	public String getIsTownUpBone() {
		return isTownUpBone;
	}
	public void setIsTownUpBone(String isTownUpBone) {
		this.isTownUpBone = isTownUpBone;
	}
	public Boolean getIsTownUpBoneLike() {
		return isTownUpBoneLike;
	}
	public void setIsTownUpBoneLike(Boolean isTownUpBoneLike) {
		this.isTownUpBoneLike = isTownUpBoneLike;
	}
	public String getIsHealth() {
		return isHealth;
	}
	public void setIsHealth(String isHealth) {
		this.isHealth = isHealth;
	}
	public Boolean getIsHealthLike() {
		return isHealthLike;
	}
	public void setIsHealthLike(Boolean isHealthLike) {
		this.isHealthLike = isHealthLike;
	}
	public String getPersonStatus() {
		return personStatus;
	}
	public void setPersonStatus(String personStatus) {
		this.personStatus = personStatus;
	}
	public Boolean getPersonStatusLike() {
		return personStatusLike;
	}
	public void setPersonStatusLike(Boolean personStatusLike) {
		this.personStatusLike = personStatusLike;
	}
	public String getIsDoubleTch() {
		return isDoubleTch;
	}
	public void setIsDoubleTch(String isDoubleTch) {
		this.isDoubleTch = isDoubleTch;
	}
	public Boolean getIsDoubleTchLike() {
		return isDoubleTchLike;
	}
	public void setIsDoubleTchLike(Boolean isDoubleTchLike) {
		this.isDoubleTchLike = isDoubleTchLike;
	}
	public String getIsProfessCard() {
		return isProfessCard;
	}
	public void setIsProfessCard(String isProfessCard) {
		this.isProfessCard = isProfessCard;
	}
	public Boolean getIsProfessCardLike() {
		return isProfessCardLike;
	}
	public void setIsProfessCardLike(Boolean isProfessCardLike) {
		this.isProfessCardLike = isProfessCardLike;
	}
	public String getWorkDateTimer() {
		return workDateTimer;
	}
	public void setWorkDateTimer(String workDateTimer) {
		this.workDateTimer = workDateTimer;
	}
	public Boolean getWorkDateTimerLike() {
		return workDateTimerLike;
	}
	public void setWorkDateTimerLike(Boolean workDateTimerLike) {
		this.workDateTimerLike = workDateTimerLike;
	}
	public String getIsPreTch() {
		return isPreTch;
	}
	public void setIsPreTch(String isPreTch) {
		this.isPreTch = isPreTch;
	}
	public Boolean getIsPreTchLike() {
		return isPreTchLike;
	}
	public void setIsPreTchLike(Boolean isPreTchLike) {
		this.isPreTchLike = isPreTchLike;
	}
	public String getIsPreTrain() {
		return isPreTrain;
	}
	public void setIsPreTrain(String isPreTrain) {
		this.isPreTrain = isPreTrain;
	}
	public Boolean getIsPreTrainLike() {
		return isPreTrainLike;
	}
	public void setIsPreTrainLike(Boolean isPreTrainLike) {
		this.isPreTrainLike = isPreTrainLike;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Boolean getRemarkLike() {
		return remarkLike;
	}
	public void setRemarkLike(Boolean remarkLike) {
		this.remarkLike = remarkLike;
	}
	public String getTno() {
		return tno;
	}
	public void setTno(String tno) {
		this.tno = tno;
	}
	public Boolean getTnoLike() {
		return tnoLike;
	}
	public void setTnoLike(Boolean tnoLike) {
		this.tnoLike = tnoLike;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public Date getStartBirthday() {
		return startBirthday;
	}
	public void setStartBirthday(Date startBirthday) {
		this.startBirthday = startBirthday;
	}
	public Date getEndBirthday() {
		return endBirthday;
	}
	public void setEndBirthday(Date endBirthday) {
		this.endBirthday = endBirthday;
	}
	
}