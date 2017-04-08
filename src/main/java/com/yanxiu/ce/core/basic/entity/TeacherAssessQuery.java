package com.yanxiu.ce.core.basic.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 考核情况管理
 * @author tangmin
 * @date 2016-05-23 14:46:57
 */
public class TeacherAssessQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String tid;
	private Boolean tidLike = false;
	
	private String cyear;
	private Boolean cyearLike = false;
	
	private String checkResult;
	private Boolean checkResultLike = false;
	
	private String memo;
	private Boolean memoLike = false;
	
	private String seq;
	private Boolean seqLike = false;
	
	private String assessUnit;
	public String getAssessUnit() {
		return assessUnit;
	}
	public void setAssessUnit(String assessUnit) {
		this.assessUnit = assessUnit;
	}
	public Boolean getAssessUnitLike() {
		return assessUnitLike;
	}
	public void setAssessUnitLike(Boolean assessUnitLike) {
		this.assessUnitLike = assessUnitLike;
	}
	private Boolean assessUnitLike = false;

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
	public String getCyear() {
		return cyear;
	}
	public void setCyear(String cyear) {
		this.cyear = cyear;
	}
	public Boolean getCyearLike() {
		return cyearLike;
	}
	public void setCyearLike(Boolean cyearLike) {
		this.cyearLike = cyearLike;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public Boolean getCheckResultLike() {
		return checkResultLike;
	}
	public void setCheckResultLike(Boolean checkResultLike) {
		this.checkResultLike = checkResultLike;
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
	
}