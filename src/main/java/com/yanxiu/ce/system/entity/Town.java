package com.yanxiu.ce.system.entity;

import com.yanxiu.ce.common.core.entity.BaseEntity;

/**
 * 区县
 * @author tangmin
 * @table sys_area_town
 * @date 2016年3月8日
 */
public class Town extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 市编号
	 */
	private Integer cityNo;
	
	/**
	 * 县编号
	 */
	private Integer townNo;
	
	/**
	 * 县名称
	 */
	private String townName;
	/**
	 * 排序
	 */
	private Integer seq;
	public Integer getCityNo() {
		return cityNo;
	}
	public void setCityNo(Integer cityNo) {
		this.cityNo = cityNo;
	}
	public Integer getTownNo() {
		return townNo;
	}
	public void setTownNo(Integer townNo) {
		this.townNo = townNo;
	}
	public String getTownName() {
		return townName;
	}
	public void setTownName(String townName) {
		this.townName = townName;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}

}
