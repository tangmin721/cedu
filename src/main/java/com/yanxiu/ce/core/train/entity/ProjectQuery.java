package com.yanxiu.ce.core.train.entity;

import com.google.common.collect.Lists;
import com.yanxiu.ce.common.core.entity.BasePctsQuery;

import java.util.List;

/**
 * 培训项目管理
 *
 * @author tangmin
 * @date 2016-04-11 14:46:02
 */
public class ProjectQuery extends BasePctsQuery {
    private static final long serialVersionUID = 1L;

    private String userId;

    /**
     * 不是我的项目，userId!=
     */
    private Boolean notMy = false;

    private String name;
    private Boolean nameLike = true;

    private String pno;
    private Boolean pnoLike = true;

    private String stage;
    private Boolean stageLike = true;

    private String course;
    private Boolean courseLike = true;

    private String trainType;
    private Boolean trainTypeLike = true;

    private String trainLevel;

    private String notEqTrainLevel;//不等于校级

    private String schoolYear;
    private Boolean schoolYearLike = true;

    private String hour;
    private Boolean hourLike = true;

    private String startDate;
    private Boolean startDateLike = true;

    private String endDate;
    private Boolean endDateLike = true;

    private String description;
    private Boolean descriptionLike = true;

    private String implWay;
    private Boolean implWayLike = true;

    private String memo;
    private Boolean memoLike = true;

    private String userType;
    private Boolean userTypeLike = false;

    private Boolean selectJoinFlag = false;//查询参与flag
    private List<Long> joinPids = Lists.newArrayList();

    private String status;
    
    private String cstatus;

    private String scoreCreateType;

    private String notEqStatus;//不等于的状态

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }

    public Boolean getPnoLike() {
        return pnoLike;
    }

    public void setPnoLike(Boolean pnoLike) {
        this.pnoLike = pnoLike;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public Boolean getStageLike() {
        return stageLike;
    }

    public void setStageLike(Boolean stageLike) {
        this.stageLike = stageLike;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Boolean getCourseLike() {
        return courseLike;
    }

    public void setCourseLike(Boolean courseLike) {
        this.courseLike = courseLike;
    }

    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    public Boolean getTrainTypeLike() {
        return trainTypeLike;
    }

    public void setTrainTypeLike(Boolean trainTypeLike) {
        this.trainTypeLike = trainTypeLike;
    }

    public String getTrainLevel() {
        return trainLevel;
    }

    public void setTrainLevel(String trainLevel) {
        this.trainLevel = trainLevel;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public Boolean getSchoolYearLike() {
        return schoolYearLike;
    }

    public void setSchoolYearLike(Boolean schoolYearLike) {
        this.schoolYearLike = schoolYearLike;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public Boolean getHourLike() {
        return hourLike;
    }

    public void setHourLike(Boolean hourLike) {
        this.hourLike = hourLike;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Boolean getStartDateLike() {
        return startDateLike;
    }

    public void setStartDateLike(Boolean startDateLike) {
        this.startDateLike = startDateLike;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Boolean getEndDateLike() {
        return endDateLike;
    }

    public void setEndDateLike(Boolean endDateLike) {
        this.endDateLike = endDateLike;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDescriptionLike() {
        return descriptionLike;
    }

    public void setDescriptionLike(Boolean descriptionLike) {
        this.descriptionLike = descriptionLike;
    }

    public String getImplWay() {
        return implWay;
    }

    public void setImplWay(String implWay) {
        this.implWay = implWay;
    }

    public Boolean getImplWayLike() {
        return implWayLike;
    }

    public void setImplWayLike(Boolean implWayLike) {
        this.implWayLike = implWayLike;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Boolean getMemoLike() {
        return memoLike;
    }

    public void setMemoLike(Boolean memoLike) {
        this.memoLike = memoLike;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Boolean getUserTypeLike() {
        return userTypeLike;
    }

    public void setUserTypeLike(Boolean userTypeLike) {
        this.userTypeLike = userTypeLike;
    }

    public Boolean getSelectJoinFlag() {
        return selectJoinFlag;
    }

    public void setSelectJoinFlag(Boolean selectJoinFlag) {
        this.selectJoinFlag = selectJoinFlag;
    }

    public List<Long> getJoinPids() {
        return joinPids;
    }

    public void setJoinPids(List<Long> joinPids) {
        this.joinPids = joinPids;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getNotMy() {
        return notMy;
    }

    public void setNotMy(Boolean notMy) {
        this.notMy = notMy;
    }

    public String getNotEqStatus() {
        return notEqStatus;
    }

    public void setNotEqStatus(String notEqStatus) {
        this.notEqStatus = notEqStatus;
    }

    public String getNotEqTrainLevel() {
        return notEqTrainLevel;
    }

    public void setNotEqTrainLevel(String notEqTrainLevel) {
        this.notEqTrainLevel = notEqTrainLevel;
    }

    public String getScoreCreateType() {
        return scoreCreateType;
    }

    public void setScoreCreateType(String scoreCreateType) {
        this.scoreCreateType = scoreCreateType;
    }

	public String getCstatus() {
		return cstatus;
	}

	public void setCstatus(String cstatus) {
		this.cstatus = cstatus;
	}
    
}