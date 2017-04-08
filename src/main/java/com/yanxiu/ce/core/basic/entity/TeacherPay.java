package com.yanxiu.ce.core.basic.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.yanxiu.ce.common.core.entity.BaseEntity;

/**
 * 基本待遇信息
 * 
 * @author guowenyi
 * @table  t_tch_pay
 * @date 20161219
 */
public class TeacherPay extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4282427680667095928L;

	/**
	 * 教师id
	 */
	private Long tid;
	
	/**
	 * 排序序号
	 */
	@NotNull
	@Range(min = 0)
	private Integer seq = 0;
	
	/**
	 * 年度
	 */
	private Integer year; 
	
	/**
	 * 年工资收入（元）
	 */
	private Double yearPay;
	
	/**
	 * 基本工资（元/月）
	 */
	private Double basicPay;
	
	/**
	 * 绩效工资（元/月）
	 */
	private Double meritPay;
	
	/**
	 * 乡村教师生活补助（元/月）
	 */
	private Double countrySubsidy;
	
	/**
	 * 津贴补贴(元/月)
	 */
	private Double subsidy;
	
	/**
	 * 其他津贴补助（元/月）
	 */
	private Double otherSubsidy;
	
	/**
	 * 其他（元/月）
	 */
	private Double otherPay;
	
	/**
	 * 五险一金  字典表对应 INSURANCE_HOUS_FUND
	 */
	private String insuranceHousFund;

	
	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Double getYearPay() {
		return yearPay;
	}

	public void setYearPay(Double yearPay) {
		this.yearPay = yearPay;
	}

	public Double getBasicPay() {
		return basicPay;
	}

	public void setBasicPay(Double basicPay) {
		this.basicPay = basicPay;
	}

	public Double getMeritPay() {
		return meritPay;
	}

	public void setMeritPay(Double meritPay) {
		this.meritPay = meritPay;
	}

	public Double getCountrySubsidy() {
		return countrySubsidy;
	}

	public void setCountrySubsidy(Double countrySubsidy) {
		this.countrySubsidy = countrySubsidy;
	}

	public Double getSubsidy() {
		return subsidy;
	}

	public void setSubsidy(Double subsidy) {
		this.subsidy = subsidy;
	}

	public Double getOtherSubsidy() {
		return otherSubsidy;
	}

	public void setOtherSubsidy(Double otherSubsidy) {
		this.otherSubsidy = otherSubsidy;
	}

	public Double getOtherPay() {
		return otherPay;
	}

	public void setOtherPay(Double otherPay) {
		this.otherPay = otherPay;
	}

	public String getInsuranceHousFund() {
		return insuranceHousFund;
	}

	public void setInsuranceHousFund(String insuranceHousFund) {
		this.insuranceHousFund = insuranceHousFund;
	}
	
}
