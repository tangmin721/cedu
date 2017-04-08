package com.yanxiu.ce.core.score.entity;

import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.mybatis.annotation.Transient;

import java.util.Date;

/**
 * 学时申报
 * @author tangmin
 * @date 2016年8月1日
 */
public class Score extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 教师id
	 */
	private Long tid;
	
	/**
	 * 教师姓名
	 */
	private String tname;
	
	/**
	 * 标识码
	 */
	private String tno;
	/**
	 * 省市县字段做冗余，便于查询统计。在修改学校的时候，同时更改教师、学时申报省市县的信息，
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
	 * 学分，用于显示
	 */
	@Transient
	private String scoreNum;
	
	/**
	 * 项目id
	 */
	private Long pid;
	
	/**
	 * 冗余字段  项目编号
	 */
	private String pno;
	
	/**
	 * 名称【公共:项目名称】
	 */
	private String name;
	
	/**
	 * 申请学时数【公共】
	 */
	private Integer score;
	
	/**
	 * 申报类型(集中0、非集中1)【公共】
	 */
	private Integer type;
	
	/**
	 * 申报类型（学历提高、。。。。。）【公共】
	 */
	private Integer scoreType;
	
	/**
	 * 年度 字典  SCHOOL_YEAR     【公共:项目年度】
	 */
	private Long year; 
	@Transient
	private String yearName;
	
	/**
	 * 学历  字典DEGREE
	 */
	private Long degree;
	
	/**
	 * 开始时间
	 */
	private Date startDate;
	
	/**
	 * 结束时间
	 */
	private Date endDate;
	
	/**
	 * 所学专业
	 */
	private String major;
	
	/**
	 * 毕业院校
	 */
	private String univ;
	
	/**
	 * 篇数
	 */
	private Integer pnum;
	
	/**
	 * 字数
	 */
	private Integer zsize;
	
	/**
	 * 出版源
	 */
	private String csource;
	
	/**
	 * 角色（字典  SCORE_ROLE_TYPE）
	 */
	private Long scoreRoleType;
	
	/**
	 * 级别（字典  SCORE_LEVEL）
	 */
	private Long level; 
	
	/**
	 * 申请理由【公共】
	 */
	private String memo;
	
	/**
	 * 审批状态  ScoreStatusEnum【公共】
	 */
	private Integer status;
	
	/**
	 * 审批意见
	 */
	private String checkDesc;

	/**
	 * 学校管理员修改学分时填的修改原因
	 */
	private String schoolUpdateMemo;

	/**
	 * 区县管理员修改学分时填的修改原因
	 */
	private String townUpdateMemo;
	
	/**
	 * 认证时间（省级认证通过的时间）
	 */
	private Date checkDate;

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getTno() {
		return tno;
	}

	public void setTno(String tno) {
		this.tno = tno;
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

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getPno() {
		return pno;
	}

	public void setPno(String pno) {
		this.pno = pno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getScoreType() {
		return scoreType;
	}

	public void setScoreType(Integer scoreType) {
		this.scoreType = scoreType;
	}

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}
	
	public String getYearName() {
		return yearName;
	}

	public void setYearName(String yearName) {
		this.yearName = yearName;
	}

	public Long getDegree() {
		return degree;
	}

	public void setDegree(Long degree) {
		this.degree = degree;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getUniv() {
		return univ;
	}

	public void setUniv(String univ) {
		this.univ = univ;
	}

	public Integer getPnum() {
		return pnum;
	}

	public void setPnum(Integer pnum) {
		this.pnum = pnum;
	}

	public Integer getZsize() {
		return zsize;
	}

	public void setZsize(Integer zsize) {
		this.zsize = zsize;
	}

	public String getCsource() {
		return csource;
	}

	public void setCsource(String csource) {
		this.csource = csource;
	}

	public Long getScoreRoleType() {
		return scoreRoleType;
	}

	public void setScoreRoleType(Long scoreRoleType) {
		this.scoreRoleType = scoreRoleType;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCheckDesc() {
		return checkDesc;
	}

	public void setCheckDesc(String checkDesc) {
		this.checkDesc = checkDesc;
	}

	/**
	 * 审批时间
	 * @return
	 */
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getSchoolUpdateMemo() {
		return schoolUpdateMemo;
	}

	public void setSchoolUpdateMemo(String schoolUpdateMemo) {
		this.schoolUpdateMemo = schoolUpdateMemo;
	}

	public String getTownUpdateMemo() {
		return townUpdateMemo;
	}

	public void setTownUpdateMemo(String townUpdateMemo) {
		this.townUpdateMemo = townUpdateMemo;
	}

	public String getScoreNum() {
		return scoreNum;
	}

	public void setScoreNum(String scoreNum) {
		this.scoreNum = scoreNum;
	}
	
}
