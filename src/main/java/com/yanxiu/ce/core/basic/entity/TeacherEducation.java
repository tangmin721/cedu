package com.yanxiu.ce.core.basic.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.yanxiu.ce.common.core.entity.BaseEntity;

/**
 * 教育教学
 * @author guowenyi
 * @date 20161223
 */
public class TeacherEducation extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4345857418388998878L;

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
	 * 学年
	 */
	private Integer year;
	
	/**
	 * 学期  字典表 TERM_TYPE
	 */
	private Long term;
	
	/**
	 * 任教学段  TCH_SEGMENT_TYPE
	 */
	private String tchSegment;
	
	/**
	 * 任课状况  字典表 TCH_STATUS
	 */
	private Long tchStatus;
	
	/**
	 * 任课状况为其他情况的具体说明
	 */
	private String tchStatusDesc;
	
	/**
	 * 任课教程  	TCH_COURSE_TYPE
	 */
	private String tchCourseType;
	
	/**
	 * 任课课程类别 中职学校专有 字典表 SECONDARY_COURSE_TYPE
	 */
	private Long secondaryCourseType;
	
	/**
	 * 任课学科类别 中职学校专有  字典表 SECONDARY_SUBJECT_TYPE
	 */
	private Long secondarySubjectType;
	
	/**
	 * 平均每周课堂教学课时数
	 */
	private Integer classHour;
	
	/**
	 * 承担的其他工作  特教专有  字典表 OTHER_JOB_TYPE
	 */
	private Long otherJobType;
	
	/**
	 * 平均每周其他工作折合教学课时数
	 */
	private Integer otherClassHour;
	
	/**
	 * 兼任工作   PART_TIME_JOB
	 */
	private Long partTimeJob;
	
	/**
	 * 兼任其他工作名称
	 */
	private String partTimeName;
	
	/**
	 * 特教任课教程 字典表 TCH_SPECIAL_COURSE_TYPE
	 */
	private String specialTchCourseType;
	

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Long getTerm() {
		return term;
	}

	public void setTerm(Long term) {
		this.term = term;
	}

	public String getTchSegment() {
		return tchSegment;
	}

	public void setTchSegment(String tchSegment) {
		this.tchSegment = tchSegment;
	}

	public Long getTchStatus() {
		return tchStatus;
	}

	public void setTchStatus(Long tchStatus) {
		this.tchStatus = tchStatus;
	}

	public String getTchStatusDesc() {
		return tchStatusDesc;
	}

	public void setTchStatusDesc(String tchStatusDesc) {
		this.tchStatusDesc = tchStatusDesc;
	}

	public String getTchCourseType() {
		return tchCourseType;
	}

	public void setTchCourseType(String tchCourseType) {
		this.tchCourseType = tchCourseType;
	}

	public Long getSecondaryCourseType() {
		return secondaryCourseType;
	}

	public void setSecondaryCourseType(Long secondaryCourseType) {
		this.secondaryCourseType = secondaryCourseType;
	}

	public Long getSecondarySubjectType() {
		return secondarySubjectType;
	}

	public void setSecondarySubjectType(Long secondarySubjectType) {
		this.secondarySubjectType = secondarySubjectType;
	}

	public Integer getClassHour() {
		return classHour;
	}

	public void setClassHour(Integer classHour) {
		this.classHour = classHour;
	}

	public Long getOtherJobType() {
		return otherJobType;
	}

	public void setOtherJobType(Long otherJobType) {
		this.otherJobType = otherJobType;
	}

	public Integer getOtherClassHour() {
		return otherClassHour;
	}

	public void setOtherClassHour(Integer otherClassHour) {
		this.otherClassHour = otherClassHour;
	}

	public Long getPartTimeJob() {
		return partTimeJob;
	}

	public void setPartTimeJob(Long partTimeJob) {
		this.partTimeJob = partTimeJob;
	}

	public String getPartTimeName() {
		return partTimeName;
	}

	public String getSpecialTchCourseType() {
		return specialTchCourseType;
	}

	public void setSpecialTchCourseType(String specialTchCourseType) {
		this.specialTchCourseType = specialTchCourseType;
	}

	public void setPartTimeName(String partTimeName) {
		this.partTimeName = partTimeName;
	}
}
