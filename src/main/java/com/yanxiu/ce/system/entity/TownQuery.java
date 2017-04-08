package com.yanxiu.ce.system.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 市管理
 * @author tangmin
 * @date 2016-03-09 10:30:00
 */
public class TownQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String cityNo;
	private Boolean cityNoLike = true;
	
	private String townNo;
	private Boolean townNoLike = true;
	
	private String townName;
	private Boolean townNameLike = true;
	
	private String seq;
	private Boolean seqLike = true;
	

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
	public String getTownNo() {
		return townNo;
	}
	public void setTownNo(String townNo) {
		this.townNo = townNo;
	}
	public Boolean getTownNoLike() {
		return townNoLike;
	}
	public void setTownNoLike(Boolean townNoLike) {
		this.townNoLike = townNoLike;
	}
	public String getTownName() {
		return townName;
	}
	public void setTownName(String townName) {
		this.townName = townName;
	}
	public Boolean getTownNameLike() {
		return townNameLike;
	}
	public void setTownNameLike(Boolean townNameLike) {
		this.townNameLike = townNameLike;
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