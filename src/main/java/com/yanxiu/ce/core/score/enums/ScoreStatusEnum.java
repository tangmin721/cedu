package com.yanxiu.ce.core.score.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学时申报 状态
 * 校级待审、区县待审、省级待审、校级不通过、区县不通过、省级不通过、学时认证完成
 * @author tangmin
 * @date 2016年8月1日
 */
public enum ScoreStatusEnum {

	SCHOOL_CHECKING("校级待审", 10), 
	SCHOOL_UNPASS("校级不通过",11),
	TOWN_CHECKING("区县待审", 20), 
	TOWN_UNPASS("区县不通过",21),
	PROVINCE_CHECKING("省级待审", 30), 
	PROVINCE_UNPASS("省级不通过",31),
	CHECK_END("学时认证完成", 40);

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value;

	private ScoreStatusEnum(String desc, int value) {
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

	public static ScoreStatusEnum getEnum(int value) {
		ScoreStatusEnum resultEnum = null;
		ScoreStatusEnum[] enumAry = ScoreStatusEnum.values();
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
		ScoreStatusEnum[] ary = ScoreStatusEnum.values();
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
		ScoreStatusEnum[] enumAry = ScoreStatusEnum.values();
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
		ScoreStatusEnum[] enumAry = ScoreStatusEnum.values();
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
		ScoreStatusEnum[] ary = ScoreStatusEnum.values();
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
