package com.yanxiu.ce.system.entity;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.Update;

/**
 * 组织机构
 * @author tangmin
 * @date 2016年3月17日
 */
public class Org extends BaseEntity{

	private static final long serialVersionUID = 1L;

	/**
	 * 父节点id
	 */
	private Long pid;
	
	/**
	 * 组织机构名称
	 */
	@NotBlank(message="组织机构名称不能为空",groups={Insert.class,Update.class})
	@Length(max = 50,groups={Insert.class,Update.class})
	private String name;
	
	/**
	 * 组织机构编码
	 */
	@NotBlank(message="组织机构编码不能为空",groups={Insert.class,Update.class})
	@Size(max = 50,groups={Insert.class,Update.class})
	private String code;
	
	/**
	 * 排序序号
	 */
	private Integer seq;
	
	/**
	 * 备注
	 */
	@Size(max = 200,groups={Insert.class,Update.class})
	private String memo;

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
