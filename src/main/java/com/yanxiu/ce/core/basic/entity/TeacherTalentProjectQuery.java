package com.yanxiu.ce.core.basic.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 入选人才项目管理
 * @author tangmin
 * @date 2016-12-27 16:21:49
 */
public class TeacherTalentProjectQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private Long tid;
	private Boolean tidLike = false;
	
	private Integer seq;
	private Boolean seqLike = false;
	
	private Long projectNo;
	private Boolean projectNoLike = false;
	
	private Long selectYear;
	private Boolean selectYearLike = false;
	
	
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
	public Long getProjectNo() {
		return projectNo;
	}
	public void setProjectNo(Long projectNo) {
		this.projectNo = projectNo;
	}
	public Boolean getProjectNoLike() {
		return projectNoLike;
	}
	public void setProjectNoLike(Boolean projectNoLike) {
		this.projectNoLike = projectNoLike;
	}
	public Long getSelectYear() {
		return selectYear;
	}
	public void setSelectYear(Long selectYear) {
		this.selectYear = selectYear;
	}
	public Boolean getSelectYearLike() {
		return selectYearLike;
	}
	public void setSelectYearLike(Boolean selectYearLike) {
		this.selectYearLike = selectYearLike;
	}
	

}