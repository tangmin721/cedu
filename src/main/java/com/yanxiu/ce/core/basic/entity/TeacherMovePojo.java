package com.yanxiu.ce.core.basic.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.mybatis.annotation.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 1、教师基本信息　　　用来显示查询教师调动列表
 * @author tangmin
 * @date 2016年3月30日
 */
public class TeacherMovePojo extends BaseEntity{

	private static final long serialVersionUID = 1L;

	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 1 男 0 女 
	 */
	private Long gender;
	@Transient
	private String genderName;
	
	/**
	 * 出生年月
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date birthday;
	

	/**
	 * 身份证号
	 */
	private String idCard;
	
	
	/**
	 * 教师编号  16位编码 TEARCHER_NO_SEQ
	 */
	private String tno;
	
	/**
	 * 0 教师，1 校长
	 */
	private Integer type;

	/**
	 * teacher表的调动状态，＝１表示调动中，表示不能再被其他调动了　　其他值比如ｎｕｌｌ或０表示可调动
	 */
	private Integer moveStatus;
	
	/**
	 * 省市县字段做冗余，便于查询统计。在修改学校的时候，同时更改省市县的信息
	 */
	private Integer province;
	private Integer city;
	private Integer town;
	private Long school;
	/*@Transient
	private String provinceName;
	@Transient
	private String cityName;
	@Transient
	private String townName;
	@Transient
	private String schoolName;*/

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getTno() {
		return tno;
	}

	public void setTno(String tno) {
		this.tno = tno;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getMoveStatus() {
		return moveStatus;
	}

	public void setMoveStatus(Integer moveStatus) {
		this.moveStatus = moveStatus;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getTown() {
		return town;
	}

	public void setTown(Integer town) {
		this.town = town;
	}

	public Long getSchool() {
		return school;
	}

	public void setSchool(Long school) {
		this.school = school;
	}

	public Long getGender() {
		return gender;
	}

	public void setGender(Long gender) {
		this.gender = gender;
	}

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}
}
