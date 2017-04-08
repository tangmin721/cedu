package com.yanxiu.ce.core.basic.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 学术交流管理
 * @author tangmin
 * @date 2016-08-24 11:23:09
 */
public class TeacherAcademicExchangeQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String tid;
	private Boolean tidLike = true;
	
	private String level;
	private Boolean levelLike = true;
	
	private String levelName;
	private Boolean levelNameLike = true;
	
	private String year;
	private Boolean yearLike = true;
	
	private String yearName;
	private Boolean yearNameLike = true;
	
	private String exchName;
	private Boolean exchNameLike = true;
	
	private String hours;
	private Boolean hoursLike = true;
	
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
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public Boolean getLevelLike() {
		return levelLike;
	}
	public void setLevelLike(Boolean levelLike) {
		this.levelLike = levelLike;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public Boolean getLevelNameLike() {
		return levelNameLike;
	}
	public void setLevelNameLike(Boolean levelNameLike) {
		this.levelNameLike = levelNameLike;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Boolean getYearLike() {
		return yearLike;
	}
	public void setYearLike(Boolean yearLike) {
		this.yearLike = yearLike;
	}
	public String getYearName() {
		return yearName;
	}
	public void setYearName(String yearName) {
		this.yearName = yearName;
	}
	public Boolean getYearNameLike() {
		return yearNameLike;
	}
	public void setYearNameLike(Boolean yearNameLike) {
		this.yearNameLike = yearNameLike;
	}
	public String getExchName() {
		return exchName;
	}
	public void setExchName(String exchName) {
		this.exchName = exchName;
	}
	public Boolean getExchNameLike() {
		return exchNameLike;
	}
	public void setExchNameLike(Boolean exchNameLike) {
		this.exchNameLike = exchNameLike;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public Boolean getHoursLike() {
		return hoursLike;
	}
	public void setHoursLike(Boolean hoursLike) {
		this.hoursLike = hoursLike;
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