package com.yanxiu.ce.core.basic.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 工作经历管理
 * @author tangmin
 * @date 2016-04-05 15:50:01
 */
public class TeacherWorkExpQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String tid;
	private Boolean tidLike = false;
	
	private String startDate;
	private Boolean startDateLike = true;
	
	private String endDate;
	private Boolean endDateLike = true;
	
	private String dept;
	private Boolean deptLike = true;
	
	private String duty;
	private Boolean dutyLike = true;
	
	private String voucher;
	private Boolean voucherLike = true;
	
	private String mobile;
	private Boolean mobileLike = true;
	
	private String memo;
	private Boolean memoLike = true;
	
	private String seq;
	private Boolean seqLike = true;
	
	private String deptType;
	private Boolean deptTypeLike = true;
	

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
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public Boolean getDeptLike() {
		return deptLike;
	}
	public void setDeptLike(Boolean deptLike) {
		this.deptLike = deptLike;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public Boolean getDutyLike() {
		return dutyLike;
	}
	public void setDutyLike(Boolean dutyLike) {
		this.dutyLike = dutyLike;
	}
	public String getVoucher() {
		return voucher;
	}
	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}
	public Boolean getVoucherLike() {
		return voucherLike;
	}
	public void setVoucherLike(Boolean voucherLike) {
		this.voucherLike = voucherLike;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Boolean getMobileLike() {
		return mobileLike;
	}
	public void setMobileLike(Boolean mobileLike) {
		this.mobileLike = mobileLike;
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
	public String getDeptType() {
		return deptType;
	}
	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}
	public Boolean getDeptTypeLike() {
		return deptTypeLike;
	}
	public void setDeptTypeLike(Boolean deptTypeLike) {
		this.deptTypeLike = deptTypeLike;
	}
	
}