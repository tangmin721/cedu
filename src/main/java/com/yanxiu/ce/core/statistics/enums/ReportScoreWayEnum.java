package com.yanxiu.ce.core.statistics.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学时统计
 * @author tangmin
 * @date 2016年8月22日
 */
public enum ReportScoreWayEnum {
	
	NUMBERS("人数分布", 0);
//	DEGREE("学历", 1), 
//	TITLE("职称",2),
//	STAGE("学段", 3), 
//	COURSE("学科",4),
//	DUTY("行政职务", 5), 
//	BONETYPE("骨干类型",6),
//	NATION("民族", 9);

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value;

	private ReportScoreWayEnum(String desc, int value) {
		this.desc = desc;
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static ReportScoreWayEnum getEnum(int value) {
		ReportScoreWayEnum resultEnum = null;
		ReportScoreWayEnum[] enumAry = ReportScoreWayEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getValue() == value) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	/**
	 * 转换为map
	 * @return
	 */
	public static Map<String, Map<String, Object>> toMap() {
		ReportScoreWayEnum[] ary = ReportScoreWayEnum.values();
		Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String key = String.valueOf(getEnum(ary[num].getValue()));
			map.put("value", String.valueOf(ary[num].getValue()));
			map.put("desc", ary[num].getDesc());
			enumMap.put(key, map);
		}
		return enumMap;
	}

	/**
	 * 根据值获取名称（大写英文）
	 * @param value
	 * @return
	 */
	public static String getName(int value) {
		String result = null;
		ReportScoreWayEnum[] enumAry = ReportScoreWayEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (value == enumAry[i].getValue()) {
				result = enumAry[i].name();
				break;
			}
		}
		return result;
	}
	
	/**
	 * 根据值获取描述（汉字）
	 * @param value
	 * @return
	 */
	public static String getDesc(int value) {
		String result = null;
		ReportScoreWayEnum[] enumAry = ReportScoreWayEnum.values();
		for (int i = 0; i < enumAry.length; i++) {
			if (value == enumAry[i].getValue()) {
				result = enumAry[i].getDesc();
				break;
			}
		}
		return result;
	}

	/**
	 * 转换为list
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList() {
		ReportScoreWayEnum[] ary = ReportScoreWayEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}
	
}
