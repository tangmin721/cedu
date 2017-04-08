package com.yanxiu.ce.core.basic.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.yanxiu.ce.common.core.entity.BaseEntity;

/**
 * 入选人才项目信息
 * 
 * @author guowenyi
 * @table  t_tch_talent_pro
 * @date 20161227
 */
public class TeacherTalentProject extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6158704652805475943L;

	/**
	 * 教师id
	 */
	private Long tid;
	
	/**
	 * 排序序号
	 */
	@NotNull
	@Range(min = 0)
	private Integer seq = 0;
	
	/**
	 * 入选人才项目名称   字典表  TALENT_PROJECT_TYPE
	 */
	private Long projectNo;
	
	/**
	 * 入选年份
	 */
	private Long selectYear;

	
	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Long getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(Long projectNo) {
		this.projectNo = projectNo;
	}

	public Long getSelectYear() {
		return selectYear;
	}

	public void setSelectYear(Long selectYear) {
		this.selectYear = selectYear;
	}
}
