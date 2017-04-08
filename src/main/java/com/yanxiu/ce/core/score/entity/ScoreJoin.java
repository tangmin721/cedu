package com.yanxiu.ce.core.score.entity;

import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.mybatis.annotation.Transient;

/**
 * 用于  区县、省级 的 审批列表
 * @author tangmin
 * @date 2016年8月4日
 */
public class ScoreJoin extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/**
	 * group by  count(*)待审数量
	 */
	private Integer dnum;
	
	/**
	 * 省市县字段做冗余，便于查询统计。在修改学校的时候，同时更改教师、学时申报省市县的信息，
	 */
	private Integer province;
	private Integer city;
	private Integer town;
	private Long school;
	@Transient
	private String provinceName;
	@Transient
	private String cityName;
	@Transient
	private String townName;
	@Transient
	private String schoolName;
	@Transient
	private String schoolNo;
	
	/**
	 * 审批状态  ScoreStatusEnum【公共】
	 */
	private Integer status;

	public Integer getDnum() {
		return dnum;
	}

	public void setDnum(Integer dnum) {
		this.dnum = dnum;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getTown() {
		return town;
	}

	public void setTown(Integer town) {
		this.town = town;
	}

	public Long getSchool() {
		return school;
	}

	public void setSchool(Long school) {
		this.school = school;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolNo() {
		return schoolNo;
	}

	public void setSchoolNo(String schoolNo) {
		this.schoolNo = schoolNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
