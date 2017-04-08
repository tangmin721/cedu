package com.yanxiu.ce.core.basic.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 学段管理
 * @author tangmin
 * @date 2016-04-01 10:52:45
 */
public class StageQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String seq;
	private Boolean seqLike = true;
	
	private String name;
	private Boolean nameLike = true;
	
	private String code;
	private Boolean codeLike = true;
	
	private String memo;
	private Boolean memoLike = true;
	

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