package com.yanxiu.ce.core.score.entity;

import java.util.List;

import com.google.common.collect.Lists;
import com.yanxiu.ce.common.core.entity.BasePctsQuery;
import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 学时申报管理
 * @author tangmin
 * @date 2016-08-02 14:59:01
 */
public class ScoreQuery extends BasePctsQuery{
	private static final long serialVersionUID = 1L;
	
	private String tid;
	
	private String tname;
	private Boolean tnameLike = true;
	
	private String tno;
	private Boolean tnoLike = true;
	
	private String pid;
	
	private String pno;
	private Boolean pnoLike = true;
	
	private String name;
	private Boolean nameLike = true;
	
	private String score;
	private Boolean scoreLike = true;
	
	private String type;
	
	private String scoreType;
	
	private String year;
	
	private String degree;
	
	private String startDate;
	
	private String endDate;
	
	private String major;
	private Boolean majorLike = true;
	
	private String univ;
	private Boolean univLike = true;
	
	private String pnum;
	
	private String zsize;
	
	private String csource;
	private Boolean csourceLike = true;
	
	private String scoreRoleType;
	
	private String level;
	
	private String memo;
	private Boolean memoLike = true;
	
	private String status;
	
	private Boolean selectJoinFlag = false;//查询已审flag
	private List<Integer> joinStatus = Lists.newArrayList();
	
	private String checkDesc;
	private Boolean checkDescLike = true;
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public Boolean getTnameLike() {
		return tnameLike;
	}
	public void setTnameLike(Boolean tnameLike) {
		this.tnameLike = tnameLike;
	}
	public String getTno() {
		return tno;
	}
	public void setTno(String tno) {
		this.tno = tno;
	}
	public Boolean getTnoLike() {
		return tnoLike;
	}
	public void setTnoLike(Boolean tnoLike) {
		this.tnoLike = tnoLike;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPno() {
		return pno;
	}
	public void setPno(String pno) {
		this.pno = pno;
	}
	public Boolean getPnoLike() {
		return pnoLike;
	}
	public void setPnoLike(Boolean pnoLike) {
		this.pnoLike = pnoLike;
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
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public Boolean getScoreLike() {
		return scoreLike;
	}
	public void setScoreLike(Boolean scoreLike) {
		this.scoreLike = scoreLike;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getScoreType() {
		return scoreType;
	}
	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public Boolean getMajorLike() {
		return majorLike;
	}
	public void setMajorLike(Boolean majorLike) {
		this.majorLike = majorLike;
	}
	public String getUniv() {
		return univ;
	}
	public void setUniv(String univ) {
		this.univ = univ;
	}
	public Boolean getUnivLike() {
		return univLike;
	}
	public void setUnivLike(Boolean univLike) {
		this.univLike = univLike;
	}
	public String getPnum() {
		return pnum;
	}
	public void setPnum(String pnum) {
		this.pnum = pnum;
	}
	public String getZsize() {
		return zsize;
	}
	public void setZsize(String zsize) {
		this.zsize = zsize;
	}
	public String getCsource() {
		return csource;
	}
	public void setCsource(String csource) {
		this.csource = csource;
	}
	public Boolean getCsourceLike() {
		return csourceLike;
	}
	public void setCsourceLike(Boolean csourceLike) {
		this.csourceLike = csourceLike;
	}
	public String getScoreRoleType() {
		return scoreRoleType;
	}
	public void setScoreRoleType(String scoreRoleType) {
		this.scoreRoleType = scoreRoleType;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCheckDesc() {
		return checkDesc;
	}
	public void setCheckDesc(String checkDesc) {
		this.checkDesc = checkDesc;
	}
	public Boolean getCheckDescLike() {
		return checkDescLike;
	}
	public void setCheckDescLike(Boolean checkDescLike) {
		this.checkDescLike = checkDescLike;
	}
	public Boolean getSelectJoinFlag() {
		return selectJoinFlag;
	}
	public void setSelectJoinFlag(Boolean selectJoinFlag) {
		this.selectJoinFlag = selectJoinFlag;
	}
	public List<Integer> getJoinStatus() {
		return joinStatus;
	}
	public void setJoinStatus(List<Integer> joinStatus) {
		this.joinStatus = joinStatus;
	}
	
}