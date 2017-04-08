package com.yanxiu.ce.core.basic.entity;

import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.mybatis.annotation.Transient;

/**
 * 9、学时学分
 * @author tangmin
 * @date 2016年3月31日
 */
public class TeacherScore extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 教师id
	 */
	private Long tid;
	
	/**
	 * 项目id
	 */
	private Long pid;
	
	/**
	 * 项目名称
	 */
	@Transient
	private String pname;
	
	/**
	 * 项目编号
	 */
	@Transient
	private String pno;
	/**
	 * 项目类型
	 */
	@Transient
	private String ptype;
	
	/**
	 * 学年
	 */
	@Transient
	private String pschoolYear;
	
	/**
	 * 获得学分
	 */
	private Double score;
	
	/**
	 * 获得学时
	 */
	private Double hour;

	/**
	 * 备注
	 */
	private String memo;
	
	/**
	 * 排序
	 */
	private String seq;
	
	/**
	 * 考核 状态  0：不及格  1及格
	 */
	private Boolean status=false;
	
	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Double getHour() {
		return hour;
	}

	public void setHour(Double hour) {
		this.hour = hour;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}


	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getPno() {
		return pno;
	}

	public void setPno(String pno) {
		this.pno = pno;
	}

	public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	public String getPschoolYear() {
		return pschoolYear;
	}

	public void setPschoolYear(String pschoolYear) {
		this.pschoolYear = pschoolYear;
	}
	
}
