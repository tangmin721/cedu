package com.yanxiu.ce.core.basic.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 学术论文管理
 * @author tangmin
 * @date 2016-04-19 11:38:55
 */
public class TeacherAcademicQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String tid;
	private Boolean tidLike = false;
	
	private String name;
	private Boolean nameLike = true;
	
	private String publishDate;
	private Boolean publishDateLike = true;
	
	private Long pnum;
	private Boolean pnumLike = false;
	
	private Long zsize;
	private Boolean zsizeLike = false;
	
	private String publishSource;
	private Boolean publishSourceLike = true;
	
	private String roleType;
	private Boolean roleTypeLike = true;
	
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
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	public Boolean getPublishDateLike() {
		return publishDateLike;
	}
	public void setPublishDateLike(Boolean publishDateLike) {
		this.publishDateLike = publishDateLike;
	}
	public String getPublishSource() {
		return publishSource;
	}
	public void setPublishSource(String publishSource) {
		this.publishSource = publishSource;
	}
	public Boolean getPublishSourceLike() {
		return publishSourceLike;
	}
	public void setPublishSourceLike(Boolean publishSourceLike) {
		this.publishSourceLike = publishSourceLike;
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
	public Long getPnum() {
		return pnum;
	}
	public void setPnum(Long pnum) {
		this.pnum = pnum;
	}
	public Boolean getPnumLike() {
		return pnumLike;
	}
	public void setPnumLike(Boolean pnumLike) {
		this.pnumLike = pnumLike;
	}
	public Long getZsize() {
		return zsize;
	}
	public void setZsize(Long zsize) {
		this.zsize = zsize;
	}
	public Boolean getZsizeLike() {
		return zsizeLike;
	}
	public void setZsizeLike(Boolean zsizeLike) {
		this.zsizeLike = zsizeLike;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public Boolean getRoleTypeLike() {
		return roleTypeLike;
	}
	public void setRoleTypeLike(Boolean roleTypeLike) {
		this.roleTypeLike = roleTypeLike;
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