package com.yanxiu.ce.core.solr.dto;

import java.io.Serializable;

/**
 * solr索引库  teacher数据索引 mapper
 * @author tangmin
 * @date 2016年5月26日
 */
public class TeacherSolr implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String schoolName;
	private String nationName;
	private String nativerName;
	private String name;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getNationName() {
		return nationName;
	}
	public void setNationName(String nationName) {
		this.nationName = nationName;
	}
	public String getNativerName() {
		return nativerName;
	}
	public void setNativerName(String nativerName) {
		this.nativerName = nativerName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
