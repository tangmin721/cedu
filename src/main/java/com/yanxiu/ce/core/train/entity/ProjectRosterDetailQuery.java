package com.yanxiu.ce.core.train.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 报名名单详单管理
 * @author tangmin
 * @date 2016-04-20 16:00:38
 * @Tabel (t_project_roster_detail)
 */
public class ProjectRosterDetailQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private String pid;
	
	private String rosterId;
	private Boolean rosterIdLike = true;
	
	private String tid;
	private Boolean tidLike = true;
	
	private String name;
	private Boolean nameLike = true;
	
	private String tno;
	private Boolean tnoLike = true;
	
	private String status;
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getRosterId() {
		return rosterId;
	}
	public void setRosterId(String rosterId) {
		this.rosterId = rosterId;
	}
	public Boolean getRosterIdLike() {
		return rosterIdLike;
	}
	public void setRosterIdLike(Boolean rosterIdLike) {
		this.rosterIdLike = rosterIdLike;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public Boolean getTidLike() {
		return tidLike;
	}
	public void setTidLike(Boolean tidLike) {
		this.tidLike = tidLike;
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
	public String getTno() {
		return tno;
	}
	public void setTno(String tno) {
		this.tno = tno;
	}
	public Boolean getTnoLike() {
		return tnoLike;
	}
	public void setTnoLike(Boolean tnoLike) {
		this.tnoLike = tnoLike;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}