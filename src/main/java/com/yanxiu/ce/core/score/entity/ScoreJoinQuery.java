package com.yanxiu.ce.core.score.entity;

import com.yanxiu.ce.common.core.entity.BasePctsQuery;

/**
 * 学时申报管理
 * @author tangmin
 * @date 2016-08-04 13:48:34
 */
public class ScoreJoinQuery extends BasePctsQuery{
	private static final long serialVersionUID = 1L;
	
	private String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}