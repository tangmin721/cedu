package com.yanxiu.ce.core.basic.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.mybatis.annotation.Transient;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.Update;

/**
 * 学科，课程
 * 
 * @author tangmin
 * @Tabel t_course
 * @date 2016年4月1日
 */
public class Course extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 排序序号
	 */
	@NotNull
	@Range(min = 0)
	private Integer seq = 0;

	/**
	 * 学段id
	 */
	@NotNull
	private Long stageId;
	@Transient
	private String stageName;

	/**
	 * 名称
	 */
	@NotBlank(message = "名称不能为空", groups = { Insert.class, Update.class })
	@Size(max = 50, groups = { Insert.class, Update.class })
	private String name;

	/**
	 * 编码(唯一)
	 */
	@NotBlank(message = "编码不能为空", groups = { Insert.class, Update.class })
	@Length(max = 50, groups = { Insert.class, Update.class })
	private String code;

	/**
	 * 备注
	 */
	@Length(max = 100, groups = { Insert.class, Update.class })
	private String memo;

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Long getStageId() {
		return stageId;
	}

	public void setStageId(Long stageId) {
		this.stageId = stageId;
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

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

}
