package com.yanxiu.ce.system.dto;


/**
 * 省市县学校查询封装类
 * @author tangm
 * @date 2016年3月25日
 */
public class PctsQueryDto {
	Integer province = 0;
	Integer city = 0;
	Integer town = 0;
	Long school = 0l;
	
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
}
