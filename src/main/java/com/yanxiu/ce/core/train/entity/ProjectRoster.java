package com.yanxiu.ce.core.train.entity;

import java.util.Date;

import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.mybatis.annotation.Transient;

/**
 * 报名  申请、审批单
 * @author tangmin
 * @date 2016年4月20日
 * @tabel (t_project_roster)
 */
public class ProjectRoster extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/**
	 * pid
	 */
	private Long pid;
	
	/**
	 * 申请单编号
	 */
	private String rosterNo;
	
	/**
	 * 上报时间
	 */
	private Date reportDate;
	
	/**
	 * 上报人
	 */
	private Long applyUserId;
	@Transient
	private String applyUserName;
	
	/**
	 * 备注
	 */
	private String memo;
	
	/**
	 * 审批人
	 */
	private Long checkUserId;
	@Transient
	private String checkUserName;
	
	/**
	 * 审批时间
	 */
	private Date checkDate;
	
	 /**
	  * 审批意见
	  */
	private String checkDesc;
	
	/**
	 * 状态  RegisterStatusEnum
	 */
	private Integer status;

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getRosterNo() {
		return rosterNo;
	}

	public void setRosterNo(String rosterNo) {
		this.rosterNo = rosterNo;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public Long getApplyUserId() {
		return applyUserId;
	}

	public void setApplyUserId(Long applyUserId) {
		this.applyUserId = applyUserId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getCheckUserId() {
		return checkUserId;
	}

	public void setCheckUserId(Long checkUserId) {
		this.checkUserId = checkUserId;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getCheckDesc() {
		return checkDesc;
	}

	public void setCheckDesc(String checkDesc) {
		this.checkDesc = checkDesc;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getApplyUserName() {
		return applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	public String getCheckUserName() {
		return checkUserName;
	}

	public void setCheckUserName(String checkUserName) {
		this.checkUserName = checkUserName;
	}
	
}
