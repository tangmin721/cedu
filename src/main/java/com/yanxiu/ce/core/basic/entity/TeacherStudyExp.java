package com.yanxiu.ce.core.basic.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.Update;

/**
 * 4、学习经历
 * @author tangmin
 * @date 2016年3月30日
 */
public class TeacherStudyExp extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
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
	 * 获得学历   字典：DEGREE
	 */
	@NotNull(message="学历不能为空",groups={Insert.class,Update.class})
	private Long degree;
	private String degreeName;
	
	/**
	 * 入学年月
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@Past(message="开始时间只能为过去的时间",groups={Insert.class,Update.class})
	private Date startDate;

	/**
	 * 毕业年月
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@Past(message="结束日期只能为过去的时间",groups={Insert.class,Update.class})
	private Date endDate;
	
	/**
	 * 获得学历的院校或机构
	 */
	@Size(max = 50,groups={Insert.class,Update.class})
	private String univ;
	
	/**
	 * 所学专业
	 */
	@Size(max = 50,groups={Insert.class,Update.class})
	private String major;
	
	/**
	 * 学历类型     字典：高考统招、自考、 网络教育、成人高考、电大        DEGREE_TYPE  已无用
	 */
	private Long degreeType;
	
	
	/**
	 *最高学历标志  已无用
	 * 
	 */
	private String degreeFlag;
	
	
	/**
	 * 备注  已无用
	 * @return
	 */
	@Size(max = 200,groups={Insert.class,Update.class})
	private String memo;
	
	/**
	 * 审核通过标识  true：审核通过  已无用
	 */
	private boolean flag;
	
	/**
	 * 获得学历的国家（地区）字典表 COUNTRY_TYPE
	 */
	private Long degreeCountryType;
	
	/**
	 * 是否师范类专业 字典表  IS_FLAG  0：否  1：是
	 */
	private Long isNormalCollege;
	
	/**
	 * 学位层次  字典表  ACADEMIC_DEGREE_TYPE
	 */
	private Long academicDegree;
	private String academicDegreeN;
	
	/**
	 * 学位名称 字典表  ACADEMIC_DEGREE_NAME
	 */
	private Long academicDegreeName;
	private String academicDegreeNameN;
	
	/**
	 * 获得学位的国家（地区） COUNTRY_TYPE
	 */
	private Long academicCountryType;
	
	/**
	 * 获得学位的院校或机构
	 */
	private String academicUnit;
	
	/**
	 * 学位授予年月
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date grantDate;
	
	/**
	 * 学习方式  字典表  STUDY_MODE
	 */
	private Long studyMode;
	
	/*
	 * 在学单位类别 STUDY_UNIT_TYPE
	 */
	private Long studyUnitType;
	
	
	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
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

	public String getUniv() {
		return univ;
	}

	public void setUniv(String univ) {
		this.univ = univ;
	}

	public Long getDegreeType() {
		return degreeType;
	}

	public void setDegreeType(Long degreeType) {
		this.degreeType = degreeType;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getDegreeFlag() {
		return degreeFlag;
	}

	public void setDegreeFlag(String degreeFlag) {
		this.degreeFlag = degreeFlag;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Long getDegreeCountryType() {
		return degreeCountryType;
	}

	public void setDegreeCountryType(Long degreeCountryType) {
		this.degreeCountryType = degreeCountryType;
	}

	public Long getIsNormalCollege() {
		return isNormalCollege;
	}

	public void setIsNormalCollege(Long isNormalCollege) {
		this.isNormalCollege = isNormalCollege;
	}

	public Long getAcademicDegree() {
		return academicDegree;
	}

	public void setAcademicDegree(Long academicDegree) {
		this.academicDegree = academicDegree;
	}

	public Long getAcademicDegreeName() {
		return academicDegreeName;
	}

	public void setAcademicDegreeName(Long academicDegreeName) {
		this.academicDegreeName = academicDegreeName;
	}

	public Long getAcademicCountryType() {
		return academicCountryType;
	}

	public void setAcademicCountryType(Long academicCountryType) {
		this.academicCountryType = academicCountryType;
	}

	public String getAcademicUnit() {
		return academicUnit;
	}

	public void setAcademicUnit(String academicUnit) {
		this.academicUnit = academicUnit;
	}

	public Date getGrantDate() {
		return grantDate;
	}

	public void setGrantDate(Date grantDate) {
		this.grantDate = grantDate;
	}

	public Long getStudyMode() {
		return studyMode;
	}

	public void setStudyMode(Long studyMode) {
		this.studyMode = studyMode;
	}

	public Long getStudyUnitType() {
		return studyUnitType;
	}

	public void setStudyUnitType(Long studyUnitType) {
		this.studyUnitType = studyUnitType;
	}

	public String getDegreeName() {
		return degreeName;
	}

	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

	public String getAcademicDegreeN() {
		return academicDegreeN;
	}

	public void setAcademicDegreeN(String academicDegreeN) {
		this.academicDegreeN = academicDegreeN;
	}

	public String getAcademicDegreeNameN() {
		return academicDegreeNameN;
	}

	public void setAcademicDegreeNameN(String academicDegreeNameN) {
		this.academicDegreeNameN = academicDegreeNameN;
	}
	

}
