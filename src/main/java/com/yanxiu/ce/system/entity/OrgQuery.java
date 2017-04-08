package com.yanxiu.ce.system.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 组织机构管理
 * @author tangmin
 * @date 2016-03-17 14:21:50
 */
public class OrgQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String pid;
	private Boolean pidLike = true;
	
	private String name;
	private Boolean nameLike = true;
	
	private String code;
	private Boolean codeLike = true;
	
	private String seq;
	private Boolean seqLike = true;
	
	private String memo;
	private Boolean memoLike = true;
	

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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getNameLike() {
		return nameLike;
	}
	public void setNameLike(Boolean nameLike) {
		this.nameLike = nameLike;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Boolean getCodeLike() {
		return codeLike;
	}
	public void setCodeLike(Boolean codeLike) {
		this.codeLike = codeLike;
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
	
}