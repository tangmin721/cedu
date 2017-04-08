package com.yanxiu.ce.system.entity;

import com.yanxiu.ce.common.core.entity.BaseEntity;

/**
 * 省
 * @author tangmin
 * @table sys_area_province
 * @date 2016年3月8日
 */
public class Province extends BaseEntity{
	private static final long serialVersionUID = 1L;
	/**
	 * 省编号
	 */
	private Integer provinceNo;
	
	/**
	 * 省名称
	 */
	private String provinceName;
	
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
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	

}
