package com.yanxiu.ce.core.basic.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.mybatis.annotation.Transient;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.Update;

/**
 * 学校基本信息
 * @author tangmin
 * @date 2016年3月22日
 * @tabel t_school
 */
public class School extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 省
	 */
	private Integer province;
	@Transient
	private String provinceName;
	/**
	 * 市
	 */
	private Integer city;
	@Transient
	private String cityName;
	/**
	 * 县
	 */
	private Integer town;
	@Transient
	private String townName;
	
	/**
	 * 学校名称
	 */
	@NotBlank(message="学校名称不能为空",groups={Insert.class,Update.class})
	@Size(max = 100,groups={Insert.class,Update.class})
	private String name; 
	
	/**
	 * 学校编号  16位编码 SCHOOL_NO_SEQ
	 */
	private Long schoolNo;
	
	/**
	 * 学校类别：省直属。。  字典：SCHOOL_CATEGORY
	 */
	@NotNull(message="学校类别不能为空",groups={Insert.class})
	private Long category;
	@Transient
	private String categoryName;
	
	/**
	 * 学校大类,用于区分教师信息用什么模板：TeacherTypeEnum
	 */
	private Integer schoolType;
	
	/**
	 * 学校类型：小学 ，高中  字典：SCHOOL_TYPE
	 */
	private Long type;
	@Transient
	private String typeName;
	
	/**
	 * 学校地址
	 */
	@Length(max = 200,groups={Insert.class,Update.class})
	private String address;
	
	/**
	 * 邮政编码
	 */
	@Length(max = 6,groups={Insert.class,Update.class})
	private String postCode;
	
	/**
	 * 校长
	 */
	@NotNull(message="校长不能为空",groups={Insert.class})
	@Length(max = 50,groups={Insert.class,Update.class})
	private String master;

	/**
	 * 校办电话
	 */
	@NotNull(message="校办电话不能为空",groups={Insert.class})
	@Length(max = 50,groups={Insert.class,Update.class})
	private String tel;
	
	/**
	 * 继教负责人，主管
	 */
	@Length(max = 50,groups={Insert.class,Update.class})
	private String director;
	
	/**
	 * 负责人身份证
	 */
	@Length(max = 18,groups={Insert.class,Update.class})
	private String idCard;
	
	/**
	 * 负责人电话
	 */
	@Length(max = 50,groups={Insert.class,Update.class})
	private String theTel;
	/**
	 * 负责人手机
	 */
	@Length(max = 11,groups={Insert.class,Update.class})
	private String phone;
	
	/**
	 * 负责人邮箱
	 */
	@Length(max = 100,groups={Insert.class,Update.class})
	private String email;
	
	/**
	 * 学校状态 ENUM: SchoolStatusEnum
	 */
	private Integer status;
	
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

	/**
	 * 学校名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 学校名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 学校编号
	 */
	public Long getSchoolNo() {
		return schoolNo;
	}

	/**
	 * 学校编号
	 */
	public void setSchoolNo(Long schoolNo) {
		this.schoolNo = schoolNo;
	}

	/**
	 * 学校类别：省直属。。  字典：SCHOOL_CATEGORY
	 */
	public Long getCategory() {
		return category;
	}

	/**
	 * 学校类别：省直属。。  字典：SCHOOL_CATEGORY
	 */
	public void setCategory(Long category) {
		this.category = category;
	}

	/**
	 * 学校类型：小学 ，高中  字典：SCHOOL_TYPE
	 */
	public Long getType() {
		return type;
	}

	/**
	 * 学校类型：小学 ，高中  字典：SCHOOL_TYPE
	 */
	public void setType(Long type) {
		this.type = type;
	}

	/**
	 * 学校地址
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 学校地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 邮政编码
	 */
	public String getPostCode() {
		return postCode;
	}

	/**
	 * 邮政编码
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/**
	 * 校长
	 */
	public String getMaster() {
		return master;
	}

	/**
	 * 校长
	 */
	public void setMaster(String master) {
		this.master = master;
	}

	/**
	 * 校办电话
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * 校办电话
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * 继教负责人，主管
	 */
	public String getDirector() {
		return director;
	}

	/**
	 * 继教负责人，主管
	 */
	public void setDirector(String director) {
		this.director = director;
	}

	/**
	 * 负责人身份证
	 */
	public String getIdCard() {
		return idCard;
	}

	/**
	 * 负责人身份证
	 */
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	/**
	 * 负责人电话
	 */
	public String getTheTel() {
		return theTel;
	}

	/**
	 * 负责人电话
	 */
	public void setTheTel(String theTel) {
		this.theTel = theTel;
	}

	/**
	 * 负责人手机
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 负责人手机
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 负责人邮箱
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 负责人邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 学校状态 ENUM: SchoolStatusEnum
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 学校状态 ENUM: SchoolStatusEnum
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(Integer schoolType) {
		this.schoolType = schoolType;
	}
	
}
