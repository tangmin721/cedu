package com.yanxiu.ce.core.basic.entity;

import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.mybatis.annotation.Transient;

import java.util.Date;

/**
 * 教师调整记录实体类
 * @Table   t_tch_move
 */
public class TeacherMoveRecode extends BaseEntity{

	private static final long serialVersionUID = 1L;

	/**
	 * tid
	 */
	private Long tid;

	/**
	 * 调动状态
	 */
	private Integer moveStatus;

	/**
	 * 调动申请发起时间
	 */
	private Date sendDate;
	/**
	 * 申请原因
	 */
	private String sendMemo;

	/**
	 * 接收方处理时间
	 */
	private Date takeDate;

	/**
	 * 调动拒绝理由
	 */
	private String takeMemo;

	/**
	 * from 省市县
	 */
	private Integer province;
	private Integer city;
	private Integer town;
	private Long school;
	@Transient
	private String provinceName;
	@Transient
	private String cityName;
	@Transient
	private String townName;
	@Transient
	private String schoolName;

	/**
	 * to 省市县
	 */
	private Integer tprovince;
	private Integer tcity;
	private Integer ttown;
	private Long tschool;
	@Transient
	private String tprovinceName;
	@Transient
	private String tcityName;
	@Transient
	private String ttownName;
	@Transient
	private String tschoolName;
	@Transient
	private String name;
	@Transient
	private String tno;
	@Transient
	private String idCard;
	@Transient
	private Integer type;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public Integer getMoveStatus() {
		return moveStatus;
	}

	public void setMoveStatus(Integer moveStatus) {
		this.moveStatus = moveStatus;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getSendMemo() {
		return sendMemo;
	}

	public void setSendMemo(String sendMemo) {
		this.sendMemo = sendMemo;
	}

	public Date getTakeDate() {
		return takeDate;
	}

	public void setTakeDate(Date takeDate) {
		this.takeDate = takeDate;
	}

	public String getTakeMemo() {
		return takeMemo;
	}

	public void setTakeMemo(String takeMemo) {
		this.takeMemo = takeMemo;
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

	public Integer getTprovince() {
		return tprovince;
	}

	public void setTprovince(Integer tprovince) {
		this.tprovince = tprovince;
	}

	public Integer getTcity() {
		return tcity;
	}

	public void setTcity(Integer tcity) {
		this.tcity = tcity;
	}

	public Integer getTtown() {
		return ttown;
	}

	public void setTtown(Integer ttown) {
		this.ttown = ttown;
	}

	public Long getTschool() {
		return tschool;
	}

	public void setTschool(Long tschool) {
		this.tschool = tschool;
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

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getTprovinceName() {
		return tprovinceName;
	}

	public void setTprovinceName(String tprovinceName) {
		this.tprovinceName = tprovinceName;
	}

	public String getTcityName() {
		return tcityName;
	}

	public void setTcityName(String tcityName) {
		this.tcityName = tcityName;
	}

	public String getTtownName() {
		return ttownName;
	}

	public void setTtownName(String ttownName) {
		this.ttownName = ttownName;
	}

	public String getTschoolName() {
		return tschoolName;
	}

	public void setTschoolName(String tschoolName) {
		this.tschoolName = tschoolName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTno() {
		return tno;
	}

	public void setTno(String tno) {
		this.tno = tno;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
