package com.yanxiu.ce.system.entity;

import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.mybatis.annotation.Transient;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.Update;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 字典项
 * @table sys_dict_item
 * @author tangm
 */
public class DictItem extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 排序序号
	 */
	@NotNull
	@Range(min=0)
	private Integer seq = 0;
	
	/**
	 * 字典目录id
	 */
	@NotNull
	private Long catlogId;

	/**
	 * itemId  父节点
	 */
	private Long parentId;

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
	 * 备注
	 */
	@Length(max = 200,groups={Insert.class,Update.class})
	private String memo;

	/**
	 * 用于选中回显设置
	 */
	@Transient
	private Boolean checked;

    /**设置节点是否隐藏 checkbox / radio [setting.check.enable = true 时有效]*/
    @Transient
    private Boolean nocheck;
    
    private Long id;


	public Integer getSeq() {
		return seq;
	}


	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getCatlogId() {
		return catlogId;
	}


	public void setCatlogId(Long catlogId) {
		this.catlogId = catlogId;
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


	public String getMemo() {
		return memo;
	}


	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

    public Boolean getNocheck() {
        return nocheck;
    }

    public void setNocheck(Boolean nocheck) {
        this.nocheck = nocheck;
    }


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}
}
