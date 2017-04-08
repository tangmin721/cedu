package com.yanxiu.ce.core.basic.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 学习经历管理
 * @author tangmin
 * @date 2016-04-03 13:11:04
 */
public class TeacherStudyExpQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String tid;
	private Boolean tidLike = false;
	
	private String startDate;
	private Boolean startDateLike = false;
	
	private String endDate;
	private Boolean endDateLike = false;
	
	private String univ;
	private Boolean univLike = false;
	
	private String degreeType;
	private Boolean degreeTypeLike = false;
	
	private String degree;
	private Boolean degreeLike = false;
	
	private String major;
	private Boolean majorLike = false;
	
	private String memo;
	private Boolean memoLike = false;
	
	private String seq;
	private Boolean seqLike = false;
	
	private String degreeCountryType;
	private Boolean degreeCountryTypeLike = false;
	
	private String isNormalCollege;
	private Boolean isNormalCollegeLike = false;
	
	private String academicDegree;
	private Boolean academicDegreeLike = false;
	
	private String academicDegreeName;
	private Boolean academicDegreeNameLike = false;
	
	private String academicCountryType;
	private Boolean academicCountryTypeLike = false;
	
	private String academicUnit;
	private Boolean academicUnitLike = false;
	
	private String grantDate;
	private Boolean grantDateLike = false;
	
	private String studyMode;
	private Boolean studyModeLike = false;
	
	private String studyUnitType;
	private Boolean studyUnitTypeLike = false;
	

	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public Boolean getTidLike() {
		return tidLike;
	}
	public void setTidLike(Boolean tidLike) {
		this.tidLike = tidLike;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public Boolean getStartDateLike() {
		return startDateLike;
	}
	public void setStartDateLike(Boolean startDateLike) {
		this.startDateLike = startDateLike;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Boolean getEndDateLike() {
		return endDateLike;
	}
	public void setEndDateLike(Boolean endDateLike) {
		this.endDateLike = endDateLike;
	}
	public String getUniv() {
		return univ;
	}
	public void setUniv(String univ) {
		this.univ = univ;
	}
	public Boolean getUnivLike() {
		return univLike;
	}
	public void setUnivLike(Boolean univLike) {
		this.univLike = univLike;
	}
	public String getDegreeType() {
		return degreeType;
	}
	public void setDegreeType(String degreeType) {
		this.degreeType = degreeType;
	}
	public Boolean getDegreeTypeLike() {
		return degreeTypeLike;
	}
	public void setDegreeTypeLike(Boolean degreeTypeLike) {
		this.degreeTypeLike = degreeTypeLike;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public Boolean getDegreeLike() {
		return degreeLike;
	}
	public void setDegreeLike(Boolean degreeLike) {
		this.degreeLike = degreeLike;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public Boolean getMajorLike() {
		return majorLike;
	}
	public void setMajorLike(Boolean majorLike) {
		this.majorLike = majorLike;
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
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public Boolean getSeqLike() {
		return seqLike;
	}
	public void setSeqLike(Boolean seqLike) {
		this.seqLike = seqLike;
	}
	public String getDegreeCountryType() {
		return degreeCountryType;
	}
	public void setDegreeCountryType(String degreeCountryType) {
		this.degreeCountryType = degreeCountryType;
	}
	public Boolean getDegreeCountryTypeLike() {
		return degreeCountryTypeLike;
	}
	public void setDegreeCountryTypeLike(Boolean degreeCountryTypeLike) {
		this.degreeCountryTypeLike = degreeCountryTypeLike;
	}
	public String getIsNormalCollege() {
		return isNormalCollege;
	}
	public void setIsNormalCollege(String isNormalCollege) {
		this.isNormalCollege = isNormalCollege;
	}
	public Boolean getIsNormalCollegeLike() {
		return isNormalCollegeLike;
	}
	public void setIsNormalCollegeLike(Boolean isNormalCollegeLike) {
		this.isNormalCollegeLike = isNormalCollegeLike;
	}
	public String getAcademicDegree() {
		return academicDegree;
	}
	public void setAcademicDegree(String academicDegree) {
		this.academicDegree = academicDegree;
	}
	public Boolean getAcademicDegreeLike() {
		return academicDegreeLike;
	}
	public void setAcademicDegreeLike(Boolean academicDegreeLike) {
		this.academicDegreeLike = academicDegreeLike;
	}
	public String getAcademicDegreeName() {
		return academicDegreeName;
	}
	public void setAcademicDegreeName(String academicDegreeName) {
		this.academicDegreeName = academicDegreeName;
	}
	public Boolean getAcademicDegreeNameLike() {
		return academicDegreeNameLike;
	}
	public void setAcademicDegreeNameLike(Boolean academicDegreeNameLike) {
		this.academicDegreeNameLike = academicDegreeNameLike;
	}
	public String getAcademicCountryType() {
		return academicCountryType;
	}
	public void setAcademicCountryType(String academicCountryType) {
		this.academicCountryType = academicCountryType;
	}
	public Boolean getAcademicCountryTypeLike() {
		return academicCountryTypeLike;
	}
	public void setAcademicCountryTypeLike(Boolean academicCountryTypeLike) {
		this.academicCountryTypeLike = academicCountryTypeLike;
	}
	public String getAcademicUnit() {
		return academicUnit;
	}
	public void setAcademicUnit(String academicUnit) {
		this.academicUnit = academicUnit;
	}
	public Boolean getAcademicUnitLike() {
		return academicUnitLike;
	}
	public void setAcademicUnitLike(Boolean academicUnitLike) {
		this.academicUnitLike = academicUnitLike;
	}
	public String getGrantDate() {
		return grantDate;
	}
	public void setGrantDate(String grantDate) {
		this.grantDate = grantDate;
	}
	public Boolean getGrantDateLike() {
		return grantDateLike;
	}
	public void setGrantDateLike(Boolean grantDateLike) {
		this.grantDateLike = grantDateLike;
	}
	public String getStudyMode() {
		return studyMode;
	}
	public void setStudyMode(String studyMode) {
		this.studyMode = studyMode;
	}
	public Boolean getStudyModeLike() {
		return studyModeLike;
	}
	public void setStudyModeLike(Boolean studyModeLike) {
		this.studyModeLike = studyModeLike;
	}
	public String getStudyUnitType() {
		return studyUnitType;
	}
	public void setStudyUnitType(String studyUnitType) {
		this.studyUnitType = studyUnitType;
	}
	public Boolean getStudyUnitTypeLike() {
		return studyUnitTypeLike;
	}
	public void setStudyUnitTypeLike(Boolean studyUnitTypeLike) {
		this.studyUnitTypeLike = studyUnitTypeLike;
	}
	
}