package com.yanxiu.ce.core.train.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.mybatis.annotation.Transient;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.Update;

/**
 * 培训机构信息
 * @author tangmin
 * @date 2016年4月11日
 * @table t_train_org
 */
public class TrainOrg extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
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
	 * 机构编号  16位编码 ORG_NO_SEQ
	 */
	private String orgNo;
	
	/**
	 * 创建该项目的管理员的userType  对应UserTypeEnum
	 */
	private Integer userType;
	
	
	/**
	 * 培训机构名称
	 */
	@NotBlank(message="机构名称不能为空",groups={Insert.class,Update.class})
	@Length(max=200,message="机构名称不能超过200",groups={Insert.class,Update.class})
	private String name;
	
	/**
	 * 培训机构类型：TRAIN_ORG_TYPE
	 */
	private Long type;
	@Transient
	private String typeName;
	
	/**
	 * 培训机构级别：TRAIN_ORG_LEVEL
	 */
	private Long level;
	@Transient
	private String levelName;

	/**
	 * 机构地址
	 */
	@Length(max = 200,groups={Insert.class,Update.class})
	private String address;
	
	/**
	 * 邮政编码
	 */
	@Length(max = 6,groups={Insert.class,Update.class})
	private String postCode;
	
	
	/**
	 * 联系人
	 */
	@Length(max = 50,groups={Insert.class,Update.class})
	private String linkman;
	
	/**
	 * 联系人身份证
	 */
	@Length(max = 18,groups={Insert.class,Update.class})
	private String idCard;
	
	/**
	 * 联系人电话
	 */
	@Length(max = 50,groups={Insert.class,Update.class})
	private String tel;
	/**
	 * 联系人手机
	 */
	@Length(max = 11,groups={Insert.class,Update.class})
	private String phone;
	
	/**
	 * 联系人邮箱
	 */
	@Length(max = 100,groups={Insert.class,Update.class})
	private String email;
	
	/**
	 * 备注
	 */
	@Length(max = 200,groups={Insert.class,Update.class})
	private String memo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getTown() {
		return town;
	}

	public void setTown(Integer town) {
		this.town = town;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public Long getSchool() {
		return school;
	}

	public void setSchool(Long school) {
		this.school = school;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

}
