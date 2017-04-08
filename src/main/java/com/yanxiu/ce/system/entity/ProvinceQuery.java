package com.yanxiu.ce.system.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 省管理
 * @author tangmin
 * @date 2016-03-09 10:29:19
 */
public class ProvinceQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String provinceNo;
	private Boolean provinceNoLike = true;
	
	private String provinceName;
	private Boolean provinceNameLike = true;
	
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
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public Boolean getProvinceNameLike() {
		return provinceNameLike;
	}
	public void setProvinceNameLike(Boolean provinceNameLike) {
		this.provinceNameLike = provinceNameLike;
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