package com.yanxiu.ce.core.basic.entity;

import com.yanxiu.ce.common.constant.AppConstant;
import com.yanxiu.ce.common.core.entity.BaseEntity;

/**
 * 照片
 * @author tangmin
 * @date 2016年4月1日
 */
public class Img extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long tid;
	private String url;
	private Integer isDef;
	
	//获取全Url
	public String getAllUrl(){
		return AppConstant.IMAGE_URL + url;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getIsDef() {
		return isDef;
	}

	public void setIsDef(Integer isDef) {
		this.isDef = isDef;
	}

}
