package com.yanxiu.ce.core.train.entity;

import com.yanxiu.ce.common.core.entity.BasePctsQuery;

/**
 * 参培人名单管理
 * @author tangmin
 * @date 2016-10-24 16:32:04
 */
public class ProjectPersonQuery extends BasePctsQuery {
	private static final long serialVersionUID = 1L;
	
	private String tid;

	private String pid;

	private String oid;

	private String pass;

	private String tno;
	private Boolean tnoLike=true;
	private String idCard;
	private Boolean idCardLike=true;
	private String name;
	private Boolean nameLike=true;


	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getTno() {
		return tno;
	}

	public void setTno(String tno) {
		this.tno = tno;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getTnoLike() {
		return tnoLike;
	}

	public void setTnoLike(Boolean tnoLike) {
		this.tnoLike = tnoLike;
	}

	public Boolean getIdCardLike() {
		return idCardLike;
	}

	public void setIdCardLike(Boolean idCardLike) {
		this.idCardLike = idCardLike;
	}

	public Boolean getNameLike() {
		return nameLike;
	}

	public void setNameLike(Boolean nameLike) {
		this.nameLike = nameLike;
	}
}