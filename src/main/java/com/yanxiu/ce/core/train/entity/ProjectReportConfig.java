package com.yanxiu.ce.core.train.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.Update;

/**
 * 项目报名 配置
 * @author tangmin
 * @date 2016年4月21日
 * @Table (t_project_report_conf)
 */
public class ProjectReportConfig extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 项目id
	 */
	private Long pid;
	
	/**
	 * 培训人数
	 */
	private String trainCount;
	
	/**
	 * 报名方式：RegisterEnum
	 */
	private Integer registerType;
	
	/**
	 * 报名开始时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@NotNull(message="开始时间 不能为空",groups={Insert.class,Update.class})
	private Date startDate;
	
	/**
	 * 报名截止时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@NotNull(message="结束时间 不能为空",groups={Insert.class,Update.class})
	private Date endDate;
	
	/**
	 * 备注
	 */
	@Size(max = 400,groups={Insert.class,Update.class})
	private String memo;

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getTrainCount() {
		return trainCount;
	}

	public void setTrainCount(String trainCount) {
		this.trainCount = trainCount;
	}

	public Integer getRegisterType() {
		return registerType;
	}

	public void setRegisterType(Integer registerType) {
		this.registerType = registerType;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
