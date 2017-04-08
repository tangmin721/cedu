package com.yanxiu.ce.core.basic.entity;

import com.yanxiu.ce.common.core.entity.BasePctsQuery;

/**
 * 教师调动管理
 * @author tangmin
 * @date 2016-09-27 12:18:18
 */
public class TeacherMovePojoQuery extends BasePctsQuery {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Boolean nameLike = true;
	
	private String sex;
	private Boolean sexLike = true;
	
	private String birthday;
	private Boolean birthdayLike = true;
	
	private String idCard;
	private Boolean idCardLike = true;
	
	private String tno;
	private Boolean tnoLike = true;
	
	private String type;
	private Boolean typeLike = true;
	
	private Boolean moveStatus;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getNameLike() {
		return nameLike;
	}

	public void setNameLike(Boolean nameLike) {
		this.nameLike = nameLike;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Boolean getSexLike() {
		return sexLike;
	}

	public void setSexLike(Boolean sexLike) {
		this.sexLike = sexLike;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Boolean getBirthdayLike() {
		return birthdayLike;
	}

	public void setBirthdayLike(Boolean birthdayLike) {
		this.birthdayLike = birthdayLike;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Boolean getIdCardLike() {
		return idCardLike;
	}

	public void setIdCardLike(Boolean idCardLike) {
		this.idCardLike = idCardLike;
	}

	public String getTno() {
		return tno;
	}

	public void setTno(String tno) {
		this.tno = tno;
	}

	public Boolean getTnoLike() {
		return tnoLike;
	}

	public void setTnoLike(Boolean tnoLike) {
		this.tnoLike = tnoLike;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getTypeLike() {
		return typeLike;
	}

	public void setTypeLike(Boolean typeLike) {
		this.typeLike = typeLike;
	}

	public Boolean getMoveStatus() {
		return moveStatus;
	}

	public void setMoveStatus(Boolean moveStatus) {
		this.moveStatus = moveStatus;
	}
}