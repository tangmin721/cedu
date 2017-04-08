package com.yanxiu.ce.system.entity;

import java.util.UUID;

import com.yanxiu.ce.common.core.entity.BaseEntity;

/**
 * 附件  公共类
 * @author tangmin
 * @date 2016年4月12日
 */
public class Attachment extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 文件名称
	 */
	private String name;
	
	/**
	 * attId
	 */
	private String attId;
	
	/**
	 * 文件大小
	 */
	private Long size;
	
	/**
	 * 文件的contentType
	 */
	private String contentType;
	
	/**
	 * 保存路径
	 */
	private String path;
	
	/**
	 * 上传人id
	 */
	private Long userId;
	
	/**
	 * 构造的时候，生成uuid为文件名
	 */
	public Attachment() {
		this.attId = UUID.randomUUID().toString();
	}
	
	public String getAttId() {
		return attId;
	}

	public void setAttId(String attId) {
		this.attId = attId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}
	
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
