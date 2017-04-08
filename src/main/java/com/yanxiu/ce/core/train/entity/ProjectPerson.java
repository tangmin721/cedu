package com.yanxiu.ce.core.train.entity;

import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.mybatis.annotation.Transient;

/**
 * 参培人名单导入
 * Created by tangmin on 2016.10.24.
 */
public class ProjectPerson extends BaseEntity{
    private static final long serialVersionUID = 1L;

    private Long tid;
    private Long pid;
    private Long oid;

    @Transient
    private String name;
    @Transient
    private String tno;
    @Transient
    private String mobile;
    @Transient
    private String idCard;
    @Transient
    private Integer score;
    @Transient
    private Integer goodScore;
    @Transient
    private Integer passScore;

    @Transient
    private String provinceName;
    @Transient
    private String cityName;
    @Transient
    private String townName;
    @Transient
    private String schoolName;
    @Transient
    private String stageName;
    @Transient
    private String courseName;
    @Transient
    private String orgName;
    @Transient
    private String orgNo;
    /**
     * 成绩  1优秀,2合格,0不合格
     */
    private Integer pass;

    private Long scid;

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public Integer getPass() {
        return pass;
    }

    public void setPass(Integer pass) {
        this.pass = pass;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public Long getScid() {
        return scid;
    }

    public void setScid(Long scid) {
        this.scid = scid;
    }

	public Integer getGoodScore() {
		return goodScore;
	}

	public void setGoodScore(Integer goodScore) {
		this.goodScore = goodScore;
	}

	public Integer getPassScore() {
		return passScore;
	}

	public void setPassScore(Integer passScore) {
		this.passScore = passScore;
	}
    
}
