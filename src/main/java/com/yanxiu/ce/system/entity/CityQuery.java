package com.yanxiu.ce.system.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 市管理
 * @author tangmin
 * @date 2016-03-09 10:29:39
 */
public class CityQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String provinceNo;
	private Boolean provinceNoLike = true;
	
	private String cityNo;
	private Boolean cityNoLike = true;
	
	private String cityName;
	private Boolean cityNameLike = true;
	
	private String seq;
	private Boolean seqLike = true;
	

	public String getProvinceNo() {
		return provinceNo;
	}
	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}
	public Boolean getProvinceNoLike() {
		return provinceNoLike;
	}
	public void setProvinceNoLike(Boolean provinceNoLike) {
		this.provinceNoLike = provinceNoLike;
	}
	public String getCityNo() {
		return cityNo;
	}
	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}
	public Boolean getCityNoLike() {
		return cityNoLike;
	}
	public void setCityNoLike(Boolean cityNoLike) {
		this.cityNoLike = cityNoLike;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Boolean getCityNameLike() {
		return cityNameLike;
	}
	public void setCityNameLike(Boolean cityNameLike) {
		this.cityNameLike = cityNameLike;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public Boolean getSeqLike() {
		return seqLike;
	}
	public void setSeqLike(Boolean seqLike) {
		this.seqLike = seqLike;
	}
	
}