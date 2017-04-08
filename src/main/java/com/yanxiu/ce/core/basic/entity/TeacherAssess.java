package com.yanxiu.ce.core.basic.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.Update;

/**
 * 10、考核情况
 * @author tangmin
 * @date 2016年3月30日
 * @table t_tch_assess
 */
public class TeacherAssess extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 教师id
	 */
	private Long tid;
	
	/**
	 * 考核年度 :字典 SCHOOL_YEAR  年
	 */
	private Long cyear;
	
	/**
	 * 考核结果 :字典 CHECK_RESULT
	 */
	private Long checkResult;
	
	/**
	 * 备注
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
	 * 考核单位名称
	 * @return
	 */
	private String assessUnit;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public Long getCyear() {
		return cyear;
	}

	public void setCyear(Long cyear) {
		this.cyear = cyear;
	}

	public Long getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(Long checkResult) {
		this.checkResult = checkResult;
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

	public String getAssessUnit() {
		return assessUnit;
	}

	public void setAssessUnit(String assessUnit) {
		this.assessUnit = assessUnit;
	}


}
