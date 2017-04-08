package com.yanxiu.ce.core.basic.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.mybatis.annotation.Transient;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.Update;

/**
 * 11、证书
 * @author tangmin
 * @date 2016年3月31日
 * @Table (t_tch_credent)
 */
public class TeacherCredent extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 教师id
	 */
	private Long tid;
	
	/**
	 * 排序序号
	 */
	@NotNull
	@Range(min = 0)
	private Integer seq = 0;
	
	/**
	 * 语种  字典表 LANGUAGE_TYPE
	 */
	private Long languageType;
	
	/**
	 * 掌握程度  字典表   PROFICIENCY_LEVEL 
	 */
	private Long proficiencyLevel;
	
	/**
	 * 其他技能名称
	 */
	private String otherSkillName;
	
	/**
	 * 其他技能掌握程度   字典表  PROFICIENCY_LEVEL
	 */
	private Long otherProficiencyLevel;
	
	/**
	 * 语言证书名称  字典表 LANGUAGE_CREDENT_TYPE
	 */
	private Long languageCredentType;
	
	/**
	 * 证书类型  字典：CREDENT_TYPE
	 */
	private Long type;
	@Transient
	private String typeName;
	
	/**
	 * 证书名称
	 */
	@Length(max=100,message="证书名称长度不能超过100",groups={Insert.class,Update.class})
	 private String name;
	
	/**
	 * 证书编号
	 */
	@Length(max=100,message="证书编号长度不能超过100",groups={Insert.class,Update.class})
	private String credentNo;
	
	/**
	 * 发证年月
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	@Past(message="取得时间只能为过去的时间",groups={Insert.class,Update.class})
	private Date takeDate;
	
	/**
	 * 有效期  已无用
	 */
	private Date expDate;
	
	/**
	 * 发证单位
	 */
	@Size(max = 100,groups={Insert.class,Update.class})
	private String dept;
	
	/**
	 * 备注  已无用
	 */
	@Size(max = 200,groups={Insert.class,Update.class})
	private String memo;
	
	
	
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

	public String getCredentNo() {
		return credentNo;
	}

	public void setCredentNo(String credentNo) {
		this.credentNo = credentNo;
	}

	public Date getTakeDate() {
		return takeDate;
	}

	public void setTakeDate(Date takeDate) {
		this.takeDate = takeDate;
	}

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
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

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Long getLanguageType() {
		return languageType;
	}

	public void setLanguageType(Long languageType) {
		this.languageType = languageType;
	}

	public Long getProficiencyLevel() {
		return proficiencyLevel;
	}

	public void setProficiencyLevel(Long proficiencyLevel) {
		this.proficiencyLevel = proficiencyLevel;
	}

	public String getOtherSkillName() {
		return otherSkillName;
	}

	public void setOtherSkillName(String otherSkillName) {
		this.otherSkillName = otherSkillName;
	}

	public Long getOtherProficiencyLevel() {
		return otherProficiencyLevel;
	}

	public void setOtherProficiencyLevel(Long otherProficiencyLevel) {
		this.otherProficiencyLevel = otherProficiencyLevel;
	}

	public Long getLanguageCredentType() {
		return languageCredentType;
	}

	public void setLanguageCredentType(Long languageCredentType) {
		this.languageCredentType = languageCredentType;
	}
	
}
