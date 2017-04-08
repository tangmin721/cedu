package com.yanxiu.ce.core.train.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.mybatis.annotation.Transient;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.Update;

/**
 * 报名管理（名额分配）   project:   1:N
 * @author tangmin
 * @date 2016年4月20日
 * @Table (t_project_quota)
 */
public class ProjectQuota  extends BaseEntity{

	private static final long serialVersionUID = 8915213133216242207L;
	
	/**
	 *  project ID
	 */
	private Long pid;
	
	/**
	 * 省
	 */
	private Integer province=0;
	@Transient
	private String provinceName;
	/**
	 * 市
	 */
	private Integer city=0;
	@Transient
	private String cityName;
	/**
	/**
	 * 县
	 */
	private Integer town=0;
	@Transient
	private String townName;
	
	/**
	 * 校
	 */
	private Long school=0l;
	@Transient
	private String schoolName;
	@Transient
	private String shoolCategoryName;
	
	/**
	 * 人数
	 */
	private Integer num;
	
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

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getTown() {
		return town;
	}

	public void setTown(Integer town) {
		this.town = town;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public Long getSchool() {
		return school;
	}

	public void setSchool(Long school) {
		this.school = school;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getShoolCategoryName() {
		return shoolCategoryName;
	}

	public void setShoolCategoryName(String shoolCategoryName) {
		this.shoolCategoryName = shoolCategoryName;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
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
	
}
