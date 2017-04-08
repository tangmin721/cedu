package com.yanxiu.ce.core.basic.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 证书管理
 * @author tangmin
 * @date 2016-04-12 15:37:53
 */
public class TeacherCredentQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String tid;
	private Boolean tidLike = false;
	
	private String type;
	private Boolean typeLike = false;
	
	private String name;
	private Boolean nameLike = true;
	
	private String credentNo;
	private Boolean credentNoLike = true;
	
	private String takeDate;
	private Boolean takeDateLike = true;
	
	private String expDate;
	private Boolean expDateLike = true;
	
	private String dept;
	private Boolean deptLike = true;
	
	private String memo;
	private Boolean memoLike = true;
	
	private String seq;
	private Boolean seqLike = true;
	

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
	public String getCredentNo() {
		return credentNo;
	}
	public void setCredentNo(String credentNo) {
		this.credentNo = credentNo;
	}
	public Boolean getCredentNoLike() {
		return credentNoLike;
	}
	public void setCredentNoLike(Boolean credentNoLike) {
		this.credentNoLike = credentNoLike;
	}
	public String getTakeDate() {
		return takeDate;
	}
	public void setTakeDate(String takeDate) {
		this.takeDate = takeDate;
	}
	public Boolean getTakeDateLike() {
		return takeDateLike;
	}
	public void setTakeDateLike(Boolean takeDateLike) {
		this.takeDateLike = takeDateLike;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public Boolean getExpDateLike() {
		return expDateLike;
	}
	public void setExpDateLike(Boolean expDateLike) {
		this.expDateLike = expDateLike;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getTypeLike() {
		return typeLike;
	}
	public void setTypeLike(Boolean typeLike) {
		this.typeLike = typeLike;
	}
	
	
}