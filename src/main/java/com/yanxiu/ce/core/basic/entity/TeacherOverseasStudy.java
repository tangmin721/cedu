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
 * 海外研修(访学)
 * 
 * @author guowenyi
 * @table  t_tch_overseas_exp
 * @date 20161228
 */
public class TeacherOverseasStudy extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -440942849537864770L;

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
	 * 是否有海外研修（访学）经历  字典表 OVERSEAS_STUDY_FLAG
	 */
	private Long isOverseasExp;
	
	/**
	 * 开始日期
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@Past(message="开始日期只能为过去的时间",groups={Insert.class,Update.class})
	private Date startDate;
	
	/**
	 * 结束日期
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@Past(message="结束日期只能为过去的时间",groups={Insert.class,Update.class})
	private Date endDate;
	
	/**
	 * 国家（地区）字典表 COUNTRY_TYPE
	 */
	private Long nation;
	
	/**
	 * 研修（访学）机构名称
	 */
	private String orgName;
	
	/**
	 * 项目名称
	 */
	private String projectName;
	
	/**
	 * 项目组织单位名称
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

	public Long getIsOverseasExp() {
		return isOverseasExp;
	}

	public void setIsOverseasExp(Long isOverseasExp) {
		this.isOverseasExp = isOverseasExp;
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

	public Long getNation() {
		return nation;
	}

	public void setNation(Long nation) {
		this.nation = nation;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
}
