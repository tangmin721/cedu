package com.yanxiu.ce.system.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.Update;

/**
 * 角色
 * @author tangmin
 * @table sys_role
 * @date 2016年3月2日
 */
public class Role extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 排序序号
	 */
	@NotNull
	@Range(min=0)
	private Integer seq = 0;
	
	/**
	 * 名称
	 */
	@NotBlank(message="名称不能为空",groups={Insert.class,Update.class})
	@Size(max = 50,groups={Insert.class,Update.class})
	private String name;
	
	/**
	 * 编码(唯一)
	 */
	@NotBlank(message="编码不能为空",groups={Insert.class,Update.class})
	@Length(max = 50,groups={Insert.class,Update.class})
	private String code;
	
	/**
	 * 激活状态:默认未激活 0 未激活，1已激活
	 */
	private Boolean active;
	
	/**
	 * 备注
	 */
	@Length(max = 200,groups={Insert.class,Update.class})
	private String memo;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	/**
	 * 激活状态:默认未激活 0 未激活，1已激活
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * 激活状态:默认未激活 0 未激活，1已激活
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public String toString() {
		return "Role [seq=" + seq + ", name=" + name + ", code=" + code
				+ ", memo=" + memo + "]";
	}
	
}
