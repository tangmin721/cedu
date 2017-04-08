package com.yanxiu.ce.core.basic.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.Update;

/**
 * 5、工作经历
 * @author tangmin
 * @date 2016年3月30日
 * @table t_tch_work_exp
 */
public class TeacherWorkExp extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
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
	 * 任职单位名称
	 */
	@Size(max = 50,groups={Insert.class,Update.class})
	private String dept;
	
	/**
	 * 任职开始年月
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@Past(message="开始时间只能为过去的时间",groups={Insert.class,Update.class})
	private Date startDate;

	/**
	 * 任职结束年月
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@Past(message="结束日期只能为过去的时间",groups={Insert.class,Update.class})
	private Date endDate;
	
	/**
	 * 单位性质类别  字典表 DEPT_TYPE
	 */
	private String deptType;
	
	/**
	 * 任职岗位
	 */
	private String duty;

	/**
	 * 证明人
	 */
	private String voucher;
	/**
	 * 证明人手机
	 */
	private String mobile;
	
	/**
	 * 备注
	 * @return
	 */
	@Size(max = 200,groups={Insert.class,Update.class})
	private String memo;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
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

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getVoucher() {
		return voucher;
	}

	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}
}
