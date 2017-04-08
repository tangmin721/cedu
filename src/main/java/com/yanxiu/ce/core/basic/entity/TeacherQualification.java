package com.yanxiu.ce.core.basic.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.yanxiu.ce.common.core.entity.BaseEntity;


/**
 * 基本待遇信息
 * 
 * @author guowenyi
 * @table  t_tch_qualify
 * @date 20161219
 */
public class TeacherQualification extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6959211996631261308L;

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
	 * 教师资格证种类  字典表 TCH_QUALIFY_TYPE
	 */
	private Long qualifyType;
	
	/**
	 * 教师资格证号码
	 */
	private String qualifyNo;
	
	/**
	 * 任教学科
	 */
	private String sbuject;
	
	/**
	 * 证书颁发日期
	 */
	private Date certificateDate;
	
	/**
	 * 颁发机构
	 */
	private String issueAgency;
	
	/**
	 * 首次注册日期
	 */
	private Date firstRegistDate;
	
	/**
	 * 首次注册结论 对应字典表  REGIST_TYPE
	 */
	private Long firstRegistResult;
	
	/**
	 * 定期注册日期
	 */
	private Date fixRegistDate;
	
	/**
	 * 定期注册结论 对应字典表  REGIST_TYPE
	 */
	private Long fixRegistResult;

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

	public Long getQualifyType() {
		return qualifyType;
	}

	public void setQualifyType(Long qualifyType) {
		this.qualifyType = qualifyType;
	}

	public String getQualifyNo() {
		return qualifyNo;
	}

	public void setQualifyNo(String qualifyNo) {
		this.qualifyNo = qualifyNo;
	}

	public String getSbuject() {
		return sbuject;
	}

	public void setSbuject(String sbuject) {
		this.sbuject = sbuject;
	}

	public Date getCertificateDate() {
		return certificateDate;
	}

	public void setCertificateDate(Date certificateDate) {
		this.certificateDate = certificateDate;
	}

	public String getIssueAgency() {
		return issueAgency;
	}

	public void setIssueAgency(String issueAgency) {
		this.issueAgency = issueAgency;
	}

	public Date getFirstRegistDate() {
		return firstRegistDate;
	}

	public void setFirstRegistDate(Date firstRegistDate) {
		this.firstRegistDate = firstRegistDate;
	}

	public Long getFirstRegistResult() {
		return firstRegistResult;
	}

	public void setFirstRegistResult(Long firstRegistResult) {
		this.firstRegistResult = firstRegistResult;
	}

	public Date getFixRegistDate() {
		return fixRegistDate;
	}

	public void setFixRegistDate(Date fixRegistDate) {
		this.fixRegistDate = fixRegistDate;
	}

	public Long getFixRegistResult() {
		return fixRegistResult;
	}

	public void setFixRegistResult(Long fixRegistResult) {
		this.fixRegistResult = fixRegistResult;
	}
}
