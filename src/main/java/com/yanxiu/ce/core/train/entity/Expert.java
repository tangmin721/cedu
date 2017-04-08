package com.yanxiu.ce.core.train.entity;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.Update;

/**
 * 专家信息
 * @author tangmin
 * @date 2016年7月29日
 */
public class Expert extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 名称	身份证	单位	职称	联系电话	电子邮件	研究方向	授课时间	优良率

	 */
	private String name;
	
	/**
	 * 身份证
	 */
	private String idCard;
	
	/**
	 * 单位
	 */
	private String dept;
	
	/**
	 * 职称
	 */
	private String title;
	
	/**
	 *联系电话
	 */
	private String mobile;
	
	/**
	 * 电子邮件
	 */
	private String email;
	
	/**
	 * 研究方向
	 */
	private String direction;
	
	/**
	 * 授课时间
	 */
	private  Date time; 
	
	/**
	 * 优良率
	 */
	private String goodRate;
	
	/**
	 * 课程名称
	 */
	private String courseName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getGoodRate() {
		return goodRate;
	}

	public void setGoodRate(String goodRate) {
		this.goodRate = goodRate;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
}
