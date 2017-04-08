package com.yanxiu.ce.core.basic.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.mybatis.annotation.Transient;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.Update;

/**
 * 学术交流
 * 
 * @author 郭文义
 * @table  t_tch_academic_exch
 * @date 20160824
 */
public class TeacherAcademicExchange extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1438479663736940336L;
	
	/**
	 * 教师id
	 */
	private Long tid;
	
	/**
	 * 级别（字典  SCORE_LEVEL）
	 */
	private Long level;
	
	@Transient
	private String levelName;
	/**
	 * 发布年度 
	 */
	private Long year; 
	
	@Transient
	private String yearName;
	
	/**
	 * 交流议题
	 */
	private String exchName;
	
	/**
	 * 
	 * 主讲时长
	 */
	private Integer hours; 
	
	/**
	 * 备注
	 * @return
	 */
	@Size(max = 200,groups={Insert.class,Update.class})
	private String memo;
	
	/**
	 * 排序序号
	 */
	@NotNull
	@Range(min = 0)
	private Integer seq = 0;
	
	/**
	 * 学术交流审核通过记录标志，通过：true 
	 * 
	 */
	private boolean flag;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	public String getYearName() {
		return yearName;
	}

	public void setYearName(String yearName) {
		this.yearName = yearName;
	}

	public String getExchName() {
		return exchName;
	}

	public void setExchName(String exchName) {
		this.exchName = exchName;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	

}
