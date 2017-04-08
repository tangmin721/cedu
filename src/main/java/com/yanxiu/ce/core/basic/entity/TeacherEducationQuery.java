package com.yanxiu.ce.core.basic.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 教育教学管理
 * @author tangmin
 * @date 2016-12-23 16:30:32
 */
public class TeacherEducationQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private Long tid;
	private Boolean tidLike = false;
	
	private Integer seq;
	private Boolean seqLike = false;
	
	private Integer year;
	private Boolean yearLike = false;
	
	private Long term;
	private Boolean termLike = false;
	
	private String tchSegment;
	private Boolean tchSegmentLike = false;
	
	private Long tchStatus;
	private Boolean tchStatusLike = false;
	
	private String tchStatusDesc;
	private Boolean tchStatusDescLike = false;
	
	private String tchCourseType;
	private Boolean tchCourseTypeLike = false;
	
	private Long secondaryCourseType;
	private Boolean secondaryCourseTypeLike = false;
	
	private Long secondarySubjectType;
	private Boolean secondarySubjectTypeLike = false;
	
	private Integer classHour;
	private Boolean classHourLike = false;
	
	private Long otherJobType;
	private Boolean otherJobTypeLike = false;
	
	private Integer otherClassHour;
	private Boolean otherClassHourLike = false;
	
	private Long partTimeJob;
	private Boolean partTimeJobLike = false;
	
	private String partTimeName;
	private Boolean partTimeNameLike = false;
	
	
	public Long getTid() {
		return tid;
	}
	public void setTid(Long tid) {
		this.tid = tid;
	}
	public Boolean getTidLike() {
		return tidLike;
	}
	public void setTidLike(Boolean tidLike) {
		this.tidLike = tidLike;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public Boolean getSeqLike() {
		return seqLike;
	}
	public void setSeqLike(Boolean seqLike) {
		this.seqLike = seqLike;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Boolean getYearLike() {
		return yearLike;
	}
	public void setYearLike(Boolean yearLike) {
		this.yearLike = yearLike;
	}
	public Long getTerm() {
		return term;
	}
	public void setTerm(Long term) {
		this.term = term;
	}
	public Boolean getTermLike() {
		return termLike;
	}
	public void setTermLike(Boolean termLike) {
		this.termLike = termLike;
	}
	public String getTchSegment() {
		return tchSegment;
	}
	public void setTchSegment(String tchSegment) {
		this.tchSegment = tchSegment;
	}
	public Boolean getTchSegmentLike() {
		return tchSegmentLike;
	}
	public void setTchSegmentLike(Boolean tchSegmentLike) {
		this.tchSegmentLike = tchSegmentLike;
	}
	public Long getTchStatus() {
		return tchStatus;
	}
	public void setTchStatus(Long tchStatus) {
		this.tchStatus = tchStatus;
	}
	public Boolean getTchStatusLike() {
		return tchStatusLike;
	}
	public void setTchStatusLike(Boolean tchStatusLike) {
		this.tchStatusLike = tchStatusLike;
	}
	public String getTchStatusDesc() {
		return tchStatusDesc;
	}
	public void setTchStatusDesc(String tchStatusDesc) {
		this.tchStatusDesc = tchStatusDesc;
	}
	public Boolean getTchStatusDescLike() {
		return tchStatusDescLike;
	}
	public void setTchStatusDescLike(Boolean tchStatusDescLike) {
		this.tchStatusDescLike = tchStatusDescLike;
	}
	public String getTchCourseType() {
		return tchCourseType;
	}
	public void setTchCourseType(String tchCourseType) {
		this.tchCourseType = tchCourseType;
	}
	public Boolean getTchCourseTypeLike() {
		return tchCourseTypeLike;
	}
	public void setTchCourseTypeLike(Boolean tchCourseTypeLike) {
		this.tchCourseTypeLike = tchCourseTypeLike;
	}
	public Long getSecondaryCourseType() {
		return secondaryCourseType;
	}
	public void setSecondaryCourseType(Long secondaryCourseType) {
		this.secondaryCourseType = secondaryCourseType;
	}
	public Boolean getSecondaryCourseTypeLike() {
		return secondaryCourseTypeLike;
	}
	public void setSecondaryCourseTypeLike(Boolean secondaryCourseTypeLike) {
		this.secondaryCourseTypeLike = secondaryCourseTypeLike;
	}
	public Long getSecondarySubjectType() {
		return secondarySubjectType;
	}
	public void setSecondarySubjectType(Long secondarySubjectType) {
		this.secondarySubjectType = secondarySubjectType;
	}
	public Boolean getSecondarySubjectTypeLike() {
		return secondarySubjectTypeLike;
	}
	public void setSecondarySubjectTypeLike(Boolean secondarySubjectTypeLike) {
		this.secondarySubjectTypeLike = secondarySubjectTypeLike;
	}
	public Integer getClassHour() {
		return classHour;
	}
	public void setClassHour(Integer classHour) {
		this.classHour = classHour;
	}
	public Boolean getClassHourLike() {
		return classHourLike;
	}
	public void setClassHourLike(Boolean classHourLike) {
		this.classHourLike = classHourLike;
	}
	public Long getOtherJobType() {
		return otherJobType;
	}
	public void setOtherJobType(Long otherJobType) {
		this.otherJobType = otherJobType;
	}
	public Boolean getOtherJobTypeLike() {
		return otherJobTypeLike;
	}
	public void setOtherJobTypeLike(Boolean otherJobTypeLike) {
		this.otherJobTypeLike = otherJobTypeLike;
	}
	public Integer getOtherClassHour() {
		return otherClassHour;
	}
	public void setOtherClassHour(Integer otherClassHour) {
		this.otherClassHour = otherClassHour;
	}
	public Boolean getOtherClassHourLike() {
		return otherClassHourLike;
	}
	public void setOtherClassHourLike(Boolean otherClassHourLike) {
		this.otherClassHourLike = otherClassHourLike;
	}
	public Long getPartTimeJob() {
		return partTimeJob;
	}
	public void setPartTimeJob(Long partTimeJob) {
		this.partTimeJob = partTimeJob;
	}
	public Boolean getPartTimeJobLike() {
		return partTimeJobLike;
	}
	public void setPartTimeJobLike(Boolean partTimeJobLike) {
		this.partTimeJobLike = partTimeJobLike;
	}
	public String getPartTimeName() {
		return partTimeName;
	}
	public void setPartTimeName(String partTimeName) {
		this.partTimeName = partTimeName;
	}
	public Boolean getPartTimeNameLike() {
		return partTimeNameLike;
	}
	public void setPartTimeNameLike(Boolean partTimeNameLike) {
		this.partTimeNameLike = partTimeNameLike;
	}
	

	
}