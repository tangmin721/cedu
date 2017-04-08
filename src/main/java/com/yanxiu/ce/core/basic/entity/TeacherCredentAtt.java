package com.yanxiu.ce.core.basic.entity;

import com.yanxiu.ce.common.core.entity.BaseEntity;

/**
 * 证书 附件
 * @author tangmin
 * @date 2016年4月13日
 * @Table (t_tch_credent_att)
 */
public class TeacherCredentAtt extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 主键id
	 */
	private Long primaryId;
	
	/**
	 * 附件id
	 */
	private Long attId;

	public Long getPrimaryId() {
		return primaryId;
	}

	public void setPrimaryId(Long primaryId) {
		this.primaryId = primaryId;
	}

	public Long getAttId() {
		return attId;
	}

	public void setAttId(Long attId) {
		this.attId = attId;
	}

}
