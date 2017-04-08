package com.yanxiu.ce.system.entity;

import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.Update;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 字典目录
 * @table sys_dict_catlog
 * @author tangm
 */
public class DictCatlog extends BaseEntity{

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
	 * 是否是树:默认不是树 0 不是树，1是树
	 */
	private Boolean isTree=false;

	/**
	 * 父节点是否可选:默认可选   0不可选，1可选
	 */
	private Boolean canParent=false;

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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Boolean getIsTree() {
		return isTree;
	}

	public void setIsTree(Boolean isTree) {
		this.isTree = isTree;
	}

	public Boolean getCanParent() {
		return canParent;
	}

	public void setCanParent(Boolean canParent) {
		this.canParent = canParent;
	}

	@Override
	public String toString() {
		return "DictCatlog [seq=" + seq + ", name=" + name + ", code=" + code
				+ ", memo=" + memo + ", createTime=" + createTime
				+ ", getId()=" + getId() + ", getVersion()=" + getVersion()
				+ ", getDeleted()=" + getDeleted() + "]";
	}
	
}
