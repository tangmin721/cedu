package com.yanxiu.ce.core.train.entity;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.mybatis.annotation.Transient;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.Update;

/**
 * 项目执行人
 * @author tangmin
 * @date 2016年4月19日
 */
public class ProjectOperator extends BaseEntity{
	private static final long serialVersionUID = 2935067907474978185L;
	
	/**
	 * 省
	 */
	private Integer province=0;
	@Transient
	private String provinceName;
	/**
	 * 市
	 */
	private Integer city=0;
	@Transient
	private String cityName;
	/**
	/**
	 * 县
	 */
	private Integer town=0;
	@Transient
	private String townName;
	
	/**
	 * 校
	 */
	private Long school=0l;
	@Transient
	private String schoolName;
	
	/**
	 * 登录账号
	 */
	private String loginName;
	
	/**
	 * 姓名
	 */
	@NotBlank(message="姓名不能为空",groups={Insert.class,Update.class})
	@Length(max=20,message="姓名长度不能超过20",groups={Insert.class,Update.class})
	private String name;
	
	/**
	 * 办公电话
	 */
	@Length(max = 50,groups={Insert.class,Update.class})
	private String tel;
	
	/**
	 * 手机号
	 */
	@Size(max = 11,groups={Insert.class,Update.class})
	private String mobile;
	
	/**
	 * 邮箱
	 */
	@Size(max = 100,groups={Insert.class,Update.class})
	private String email;
	
	/**
	 * 所在部门
	 */
	private String dept;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
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

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}
	
}
