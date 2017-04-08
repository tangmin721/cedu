package com.yanxiu.ce.system.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

public class MenuQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String pid;
	
	private Boolean pidLike = true;
	
	/**
	 * 是否要生成树
	 */
	private Boolean createTree = false;
	
	private String name;
	
	private Boolean nameLike = true;
	private String modelClzName;
	
	private Boolean modelClzNameLike = true;
	private String faicon;
	
	private Boolean faiconLike = true;
	private String level;
	
	private Boolean levelLike = true;
	private String tabid;
	
	private Boolean tabidLike = true;
	private String url;
	
	private Boolean urlLike = true;
	
	private Boolean active;
	private Boolean activeLike = false;
	
	private Boolean shower;
	private Boolean showerLike = false;
	
	private String theLast;
	
	private Boolean theLastLike = true;
	private String authrizer;
	
	private Boolean authrizerLike = true;
	private String seq;
	
	private Boolean seqLike = true;
	private String memo;
	
	private Boolean memoLike = true;

	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public Boolean getPidLike() {
		return pidLike;
	}
	public void setPidLike(Boolean pidLike) {
		this.pidLike = pidLike;
	}
	
	public Boolean getCreateTree() {
		return createTree;
	}
	public void setCreateTree(Boolean createTree) {
		this.createTree = createTree;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getNameLike() {
		return nameLike;
	}
	public void setNameLike(Boolean nameLike) {
		this.nameLike = nameLike;
	}
	public String getModelClzName() {
		return modelClzName;
	}
	public void setModelClzName(String modelClzName) {
		this.modelClzName = modelClzName;
	}
	public Boolean getModelClzNameLike() {
		return modelClzNameLike;
	}
	public void setModelClzNameLike(Boolean modelClzNameLike) {
		this.modelClzNameLike = modelClzNameLike;
	}
	public String getFaicon() {
		return faicon;
	}
	public void setFaicon(String faicon) {
		this.faicon = faicon;
	}
	public Boolean getFaiconLike() {
		return faiconLike;
	}
	public void setFaiconLike(Boolean faiconLike) {
		this.faiconLike = faiconLike;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public Boolean getLevelLike() {
		return levelLike;
	}
	public void setLevelLike(Boolean levelLike) {
		this.levelLike = levelLike;
	}
	public String getTabid() {
		return tabid;
	}
	public void setTabid(String tabid) {
		this.tabid = tabid;
	}
	public Boolean getTabidLike() {
		return tabidLike;
	}
	public void setTabidLike(Boolean tabidLike) {
		this.tabidLike = tabidLike;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Boolean getUrlLike() {
		return urlLike;
	}
	public void setUrlLike(Boolean urlLike) {
		this.urlLike = urlLike;
	}
	
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	public Boolean getActiveLike() {
		return activeLike;
	}
	public void setActiveLike(Boolean activeLike) {
		this.activeLike = activeLike;
	}
	
	public Boolean getShower() {
		return shower;
	}
	public void setShower(Boolean shower) {
		this.shower = shower;
	}
	public Boolean getShowerLike() {
		return showerLike;
	}
	public void setShowerLike(Boolean showerLike) {
		this.showerLike = showerLike;
	}
	public String getTheLast() {
		return theLast;
	}
	public void setTheLast(String theLast) {
		this.theLast = theLast;
	}
	public Boolean getTheLastLike() {
		return theLastLike;
	}
	public void setTheLastLike(Boolean theLastLike) {
		this.theLastLike = theLastLike;
	}
	public String getAuthrizer() {
		return authrizer;
	}
	public void setAuthrizer(String authrizer) {
		this.authrizer = authrizer;
	}
	public Boolean getAuthrizerLike() {
		return authrizerLike;
	}
	public void setAuthrizerLike(Boolean authrizerLike) {
		this.authrizerLike = authrizerLike;
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
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Boolean getMemoLike() {
		return memoLike;
	}
	public void setMemoLike(Boolean memoLike) {
		this.memoLike = memoLike;
	}
	
}