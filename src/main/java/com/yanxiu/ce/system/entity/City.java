package com.yanxiu.ce.system.entity;

import com.yanxiu.ce.common.core.entity.BaseEntity;

/**
 * 市
 * @author tangmin
 * @table sys_area_city
 * @date 2016年3月8日
 */
public class City extends BaseEntity{
	private static final long serialVersionUID = 1L;
	/**
	 * 省编号
	 */
	private Integer provinceNo;
	/**
	 * 市编号
	 */
	private Integer cityNo;
	/**
	 * 市名称
	 */
	private String cityName;
	/**
	 * 排序
	 */
	private Integer seq;
	public Integer getProvinceNo() {
		return provinceNo;
	}
	public void setProvinceNo(Integer provinceNo) {
		this.provinceNo = provinceNo;
	}
	public Integer getCityNo() {
		return cityNo;
	}
	public void setCityNo(Integer cityNo) {
		this.cityNo = cityNo;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
}
