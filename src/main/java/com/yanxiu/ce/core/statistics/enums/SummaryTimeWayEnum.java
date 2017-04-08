package com.yanxiu.ce.core.statistics.enums;

/**
 * 数据汇总模块类型
 * @author guowenyi
 * @date 2017年2月14日
 */
public enum SummaryTimeWayEnum {

	scoreSummaryType("学时汇总模块", "0");

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private String value;

	private SummaryTimeWayEnum(String desc, String value) {
		this.desc = desc;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
