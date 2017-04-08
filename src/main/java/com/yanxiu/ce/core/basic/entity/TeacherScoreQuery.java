package com.yanxiu.ce.core.basic.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 学时学分管理
 * @author tangmin
 * @date 2016-05-11 15:11:39
 */
public class TeacherScoreQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String tid;
	private Boolean tidLike = false;
	
	private String pid;
	private Boolean pidLike = false;
	
	private String score;
	private Boolean scoreLike = true;
	
	private String hour;
	private Boolean hourLike = true;
	
	private String memo;
	private Boolean memoLike = true;
	
	private String seq;
	private Boolean seqLike = true;
	
	private String checkStatus;
	private Boolean checkStatusLike = true;
	
	private String status;
	private Boolean statusLike = true;
	

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
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public Boolean getHourLike() {
		return hourLike;
	}
	public void setHourLike(Boolean hourLike) {
		this.hourLike = hourLike;
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
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public Boolean getCheckStatusLike() {
		return checkStatusLike;
	}
	public void setCheckStatusLike(Boolean checkStatusLike) {
		this.checkStatusLike = checkStatusLike;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Boolean getStatusLike() {
		return statusLike;
	}
	public void setStatusLike(Boolean statusLike) {
		this.statusLike = statusLike;
	}
	
}