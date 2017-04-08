package com.yanxiu.ce.common.yanxiuapi.user;

import java.io.Serializable;

/**
 * 用户返回结果封装类
 * @author tangmin
 * @date 2016年8月8日
 */
public class YanxiuResultDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * code = 0 表示注册成功
	 */
	private Integer code;
	
	/**
	 * 说明
	 */
	private String desc;
	
	/**
	 * uid
	 */
	private Object data;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	
}
