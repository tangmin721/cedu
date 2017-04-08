package com.yanxiu.ce.core.basic.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.Update;

/**
 * 7、获奖情况
 * @author tangmin
 * @date 2016年3月30日
 * @table t_tch_award
 */
public class TeacherAward extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 教师id
	 */
	private Long tid;
	
	/**
	 * 奖项名称
	 */
	@NotBlank(message="奖项名称不能为空",groups={Insert.class,Update.class})
	@Length(max=20,message="奖项名称长度不能超过20",groups={Insert.class,Update.class})
	private String name;
	
	/**
	 * 获奖时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@Past(message="取得时间只能为过去的时间",groups={Insert.class,Update.class})
	private Date takeDate;
	
	/**
	 * 颁发单位
	 */
	@Size(max = 100,groups={Insert.class,Update.class})
	private String dept;
	
	/**
	 * 备注
	 */
	@Size(max = 200,groups={Insert.class,Update.class})
	private String memo;
	
	/**
	 * 排序序号
	 */
	@NotNull
	@Range(min = 0)
	private Integer seq = 0;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getTakeDate() {
		return takeDate;
	}

	public void setTakeDate(Date takeDate) {
		this.takeDate = takeDate;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

}
