package com.yanxiu.ce.system.dto;

/**
 * 附件传输过程中额外信息封装
 * @author tangmin
 * @date 2016年4月14日
 */
public class AttExtraData {
	
	private Long attId;
	
	private String tableName;

	public Long getAttId() {
		return attId;
	}

	public void setAttId(Long attId) {
		this.attId = attId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
}
