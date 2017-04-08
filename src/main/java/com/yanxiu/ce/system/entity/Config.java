package com.yanxiu.ce.system.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.Update;

/**
 * 系统配置
 * @author tangmin
 * @date 2016年4月12日
 */
public class Config extends BaseEntity{
	private static final long serialVersionUID = -7750757527756834749L;
	
	/**
	 * userId,创建配置项的人。用户个性化的配置保存
	 */
	private Long userId;
	
	/**
	 * 配置项名称
	 */
	@NotBlank(message="配置项名称不能为空",groups={Insert.class,Update.class})
	@Length(max=50,message="配置项名称长度不能超过20",groups={Insert.class,Update.class})
	private String name;
	
	/**
	 * Key
	 */
	@NotBlank(message="Key不能为空",groups={Insert.class,Update.class})
	@Length(max=50,message="Key长度不能超过50",groups={Insert.class,Update.class})
	private String theKey;
	
	/**
	 * Value
	 */
	@NotBlank(message="Value不能为空",groups={Insert.class,Update.class})
	@Length(max=100,message="Value长度不能超过100",groups={Insert.class,Update.class})
	private String theValue;
	
	/**
	 * 备注
	 */
	@Length(max=200,message="姓名长度不能超过200",groups={Insert.class,Update.class})
	private String memo;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTheKey() {
		return theKey;
	}

	public void setTheKey(String theKey) {
		this.theKey = theKey;
	}

	public String getTheValue() {
		return theValue;
	}

	public void setTheValue(String theValue) {
		this.theValue = theValue;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	

}
