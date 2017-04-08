package com.yanxiu.ce.core.basic.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.Update;

/**
 * 专业技术职务聘任
 * 
 * @author guowenyi
 * @table  t_tch_profession_tech
 * @date 20161216
 */
public class TeacherProfessionTech extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5180703095695169606L;

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
	 * 聘任专业技术职务 对应字典表  PROFESSION_DUTY
	 */
	@NotNull
	private Long professionDuty;
	private String professionDutyName;
	
	/**
	 * 聘任开始年月
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@Past(message="聘任开始年月只能为过去的时间",groups={Insert.class,Update.class})
	private Date startDate;
	
	/**
	 * 聘任结束年月
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@Past(message="聘任结束年月只能为过去的时间",groups={Insert.class,Update.class})
	private Date endDate;
	
	/**
	 * 聘任单位名称
	 */
	private String unitName;

	
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

	public Long getProfessionDuty() {
		return professionDuty;
	}

	public void setProfessionDuty(Long professionDuty) {
		this.professionDuty = professionDuty;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getProfessionDutyName() {
		return professionDutyName;
	}

	public void setProfessionDutyName(String professionDutyName) {
		this.professionDutyName = professionDutyName;
	}
}
