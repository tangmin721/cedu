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
 * 交流轮岗信息
 * 
 * @author guowenyi
 * @table  t_tch_commun_rotate
 * @date 20161229
 */
public class TeacherCommunRotate extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6635916286017383343L;

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
	 * 交流轮岗类型  字典表  ROTATE_TYPE
	 */
	private Long rotateType;
	
	/**
	 * 是否调动人事关系  字典表 IS_FLAG
	 */
	private Long isTranRelation;
	
	/**
	 * 开始年月
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@Past(message="开始年月只能为过去的时间",groups={Insert.class,Update.class})
	private Date startDate;
	
	/**
	 * 结束年月
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date endDate;
	
	/**
	 * 原单位名称
	 */
	private String oldUnitName;
	
	/**
	 * 交流轮岗单位名称
	 */
	private String rotateUnitName;

	
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

	public Long getRotateType() {
		return rotateType;
	}

	public void setRotateType(Long rotateType) {
		this.rotateType = rotateType;
	}

	public Long getIsTranRelation() {
		return isTranRelation;
	}

	public void setIsTranRelation(Long isTranRelation) {
		this.isTranRelation = isTranRelation;
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

	public String getOldUnitName() {
		return oldUnitName;
	}

	public void setOldUnitName(String oldUnitName) {
		this.oldUnitName = oldUnitName;
	}

	public String getRotateUnitName() {
		return rotateUnitName;
	}

	public void setRotateUnitName(String rotateUnitName) {
		this.rotateUnitName = rotateUnitName;
	}
	
	
}
