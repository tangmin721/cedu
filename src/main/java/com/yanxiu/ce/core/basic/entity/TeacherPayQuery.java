package com.yanxiu.ce.core.basic.entity;

import com.yanxiu.ce.common.core.entity.BaseQuery;

/**
 * 基本待遇管理
 * @author tangmin
 * @date 2016-12-19 11:26:56
 */
public class TeacherPayQuery extends BaseQuery{
	private static final long serialVersionUID = 1L;
	
	private Long tid;
	private Boolean tidLike = false;
	
	private Integer seq;
	private Boolean seqLike = false;
	
	private Integer year;
	private Boolean yearLike = false;
	
	private Double yearPay;
	private Boolean yearPayLike = false;
	
	private Double basicPay;
	private Boolean basicPayLike = false;
	
	private Double meritPay;
	private Boolean meritPayLike = false;
	
	private Double countrySubsidy;
	private Boolean countrySubsidyLike = false;
	
	private Double subsidy;
	private Boolean subsidyLike = false;
	
	private Double otherSubsidy;
	private Boolean otherSubsidyLike = false;
	
	private Double otherPay;
	private Boolean otherPayLike = false;
	
	private Long insuranceHousFund;
	private Boolean insuranceHousFundLike = false;
	
	
	public Long getTid() {
		return tid;
	}
	public void setTid(Long tid) {
		this.tid = tid;
	}
	public Boolean getTidLike() {
		return tidLike;
	}
	public void setTidLike(Boolean tidLike) {
		this.tidLike = tidLike;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public Boolean getSeqLike() {
		return seqLike;
	}
	public void setSeqLike(Boolean seqLike) {
		this.seqLike = seqLike;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Boolean getYearLike() {
		return yearLike;
	}
	public void setYearLike(Boolean yearLike) {
		this.yearLike = yearLike;
	}
	public Double getYearPay() {
		return yearPay;
	}
	public void setYearPay(Double yearPay) {
		this.yearPay = yearPay;
	}
	public Boolean getYearPayLike() {
		return yearPayLike;
	}
	public void setYearPayLike(Boolean yearPayLike) {
		this.yearPayLike = yearPayLike;
	}
	public Double getBasicPay() {
		return basicPay;
	}
	public void setBasicPay(Double basicPay) {
		this.basicPay = basicPay;
	}
	public Boolean getBasicPayLike() {
		return basicPayLike;
	}
	public void setBasicPayLike(Boolean basicPayLike) {
		this.basicPayLike = basicPayLike;
	}
	public Double getMeritPay() {
		return meritPay;
	}
	public void setMeritPay(Double meritPay) {
		this.meritPay = meritPay;
	}
	public Boolean getMeritPayLike() {
		return meritPayLike;
	}
	public void setMeritPayLike(Boolean meritPayLike) {
		this.meritPayLike = meritPayLike;
	}
	public Double getCountrySubsidy() {
		return countrySubsidy;
	}
	public void setCountrySubsidy(Double countrySubsidy) {
		this.countrySubsidy = countrySubsidy;
	}
	public Boolean getCountrySubsidyLike() {
		return countrySubsidyLike;
	}
	public void setCountrySubsidyLike(Boolean countrySubsidyLike) {
		this.countrySubsidyLike = countrySubsidyLike;
	}
	public Double getSubsidy() {
		return subsidy;
	}
	public void setSubsidy(Double subsidy) {
		this.subsidy = subsidy;
	}
	public Boolean getSubsidyLike() {
		return subsidyLike;
	}
	public void setSubsidyLike(Boolean subsidyLike) {
		this.subsidyLike = subsidyLike;
	}
	public Double getOtherSubsidy() {
		return otherSubsidy;
	}
	public void setOtherSubsidy(Double otherSubsidy) {
		this.otherSubsidy = otherSubsidy;
	}
	public Boolean getOtherSubsidyLike() {
		return otherSubsidyLike;
	}
	public void setOtherSubsidyLike(Boolean otherSubsidyLike) {
		this.otherSubsidyLike = otherSubsidyLike;
	}
	public Double getOtherPay() {
		return otherPay;
	}
	public void setOtherPay(Double otherPay) {
		this.otherPay = otherPay;
	}
	public Boolean getOtherPayLike() {
		return otherPayLike;
	}
	public void setOtherPayLike(Boolean otherPayLike) {
		this.otherPayLike = otherPayLike;
	}
	public Long getInsuranceHousFund() {
		return insuranceHousFund;
	}
	public void setInsuranceHousFund(Long insuranceHousFund) {
		this.insuranceHousFund = insuranceHousFund;
	}
	public Boolean getInsuranceHousFundLike() {
		return insuranceHousFundLike;
	}
	public void setInsuranceHousFundLike(Boolean insuranceHousFundLike) {
		this.insuranceHousFundLike = insuranceHousFundLike;
	}
	

}