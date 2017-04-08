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
 * 8、论文/专著academic
 * @author tangmin
 * @date 2016年3月30日
 * @table t_tch_academic
 */
public class TeacherAcademic extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 教师id
	 */
	private Long tid;
	
	/**
	 * 论文标题
	 */
	@NotBlank(message="论文标题不能为空",groups={Insert.class,Update.class})
	@Length(max=20,message="论文标题长度不能超过20",groups={Insert.class,Update.class})
	private String name;
	
	/**
	 * 发布年度
	 */
	private Long publishDate;
	
	/**
	 * 篇数
	 */
	private Long pnum;
	
	/**
	 * 字数
	 */
	private Long zsize;
	
	/**
	 * 出版源
	 */
	private String publishSource;
	
	/**
	 * 角色
	 */
	private String roleType;
	
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
	
	/**
	 * 是否审核通过标志  通过:true
	 * 
	 */
	private boolean flag;
	
	
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

	public Long getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Long publishDate) {
		this.publishDate = publishDate;
	}

	public String getPublishSource() {
		return publishSource;
	}

	public void setPublishSource(String publishSource) {
		this.publishSource = publishSource;
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

	public Long getPnum() {
		return pnum;
	}

	public void setPnum(Long pnum) {
		this.pnum = pnum;
	}

	public Long getZsize() {
		return zsize;
	}

	public void setZsize(Long zsize) {
		this.zsize = zsize;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	

}
