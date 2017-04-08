package com.yanxiu.ce.core.basic.entity;

import java.util.Date;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 教师资格管理
 * @author tangmin
 * @date 2016-12-20 18:22:10
 */
public class TeacherQualificationQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private Long tid;
	private Boolean tidLike = false;
	
	private Integer seq;
	private Boolean seqLike = false;
	
	private Long qualifyType;
	private Boolean qualifyTypeLike = false;
	
	private String qualifyNo;
	private Boolean qualifyNoLike = false;
	
	private String sbuject;
	private Boolean sbujectLike = false;
	
	private Date certificateDate;
	private Boolean certificateDateLike = false;
	
	private String issueAgency;
	private Boolean issueAgencyLike = false;
	
	private Date firstRegistDate;
	private Boolean firstRegistDateLike = false;
	
	private Long firstRegistResult;
	private Boolean firstRegistResultLike = false;
	
	private Date fixRegistDate;
	private Boolean fixRegistDateLike = false;
	
	private Long fixRegistResult;
	private Boolean fixRegistResultLike = false;
	
	
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
	public Long getQualifyType() {
		return qualifyType;
	}
	public void setQualifyType(Long qualifyType) {
		this.qualifyType = qualifyType;
	}
	public Boolean getQualifyTypeLike() {
		return qualifyTypeLike;
	}
	public void setQualifyTypeLike(Boolean qualifyTypeLike) {
		this.qualifyTypeLike = qualifyTypeLike;
	}
	public String getQualifyNo() {
		return qualifyNo;
	}
	public void setQualifyNo(String qualifyNo) {
		this.qualifyNo = qualifyNo;
	}
	public Boolean getQualifyNoLike() {
		return qualifyNoLike;
	}
	public void setQualifyNoLike(Boolean qualifyNoLike) {
		this.qualifyNoLike = qualifyNoLike;
	}
	public String getSbuject() {
		return sbuject;
	}
	public void setSbuject(String sbuject) {
		this.sbuject = sbuject;
	}
	public Boolean getSbujectLike() {
		return sbujectLike;
	}
	public void setSbujectLike(Boolean sbujectLike) {
		this.sbujectLike = sbujectLike;
	}
	public Date getCertificateDate() {
		return certificateDate;
	}
	public void setCertificateDate(Date certificateDate) {
		this.certificateDate = certificateDate;
	}
	public Boolean getCertificateDateLike() {
		return certificateDateLike;
	}
	public void setCertificateDateLike(Boolean certificateDateLike) {
		this.certificateDateLike = certificateDateLike;
	}
	public String getIssueAgency() {
		return issueAgency;
	}
	public void setIssueAgency(String issueAgency) {
		this.issueAgency = issueAgency;
	}
	public Boolean getIssueAgencyLike() {
		return issueAgencyLike;
	}
	public void setIssueAgencyLike(Boolean issueAgencyLike) {
		this.issueAgencyLike = issueAgencyLike;
	}
	public Date getFirstRegistDate() {
		return firstRegistDate;
	}
	public void setFirstRegistDate(Date firstRegistDate) {
		this.firstRegistDate = firstRegistDate;
	}
	public Boolean getFirstRegistDateLike() {
		return firstRegistDateLike;
	}
	public void setFirstRegistDateLike(Boolean firstRegistDateLike) {
		this.firstRegistDateLike = firstRegistDateLike;
	}
	public Long getFirstRegistResult() {
		return firstRegistResult;
	}
	public void setFirstRegistResult(Long firstRegistResult) {
		this.firstRegistResult = firstRegistResult;
	}
	public Boolean getFirstRegistResultLike() {
		return firstRegistResultLike;
	}
	public void setFirstRegistResultLike(Boolean firstRegistResultLike) {
		this.firstRegistResultLike = firstRegistResultLike;
	}
	public Date getFixRegistDate() {
		return fixRegistDate;
	}
	public void setFixRegistDate(Date fixRegistDate) {
		this.fixRegistDate = fixRegistDate;
	}
	public Boolean getFixRegistDateLike() {
		return fixRegistDateLike;
	}
	public void setFixRegistDateLike(Boolean fixRegistDateLike) {
		this.fixRegistDateLike = fixRegistDateLike;
	}
	public Long getFixRegistResult() {
		return fixRegistResult;
	}
	public void setFixRegistResult(Long fixRegistResult) {
		this.fixRegistResult = fixRegistResult;
	}
	public Boolean getFixRegistResultLike() {
		return fixRegistResultLike;
	}
	public void setFixRegistResultLike(Boolean fixRegistResultLike) {
		this.fixRegistResultLike = fixRegistResultLike;
	}
	


	
}