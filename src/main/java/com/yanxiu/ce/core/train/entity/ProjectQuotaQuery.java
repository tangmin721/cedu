package com.yanxiu.ce.core.train.entity;

import com.yanxiu.ce.common.core.entity.BasePctsQuery;

/**
 * 名额分配管理
 * @author tangmin
 * @date 2016-04-21 10:27:55
 */
public class ProjectQuotaQuery extends BasePctsQuery{
	private static final long serialVersionUID = 1L;
	
	private String pid;
	private Boolean pidLike = true;
	
	private String num;
	private Boolean numLike = true;
	
	private String memo;
	private Boolean memoLike = true;
	
	private String seq;
	private Boolean seqLike = true;
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public Boolean getPidLike() {
		return pidLike;
	}
	public void setPidLike(Boolean pidLike) {
		this.pidLike = pidLike;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public Boolean getNumLike() {
		return numLike;
	}
	public void setNumLike(Boolean numLike) {
		this.numLike = numLike;
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