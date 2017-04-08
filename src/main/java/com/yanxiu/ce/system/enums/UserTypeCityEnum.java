package com.yanxiu.ce.system.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户类型     在User表里用来 设置项目的管理级别。比如说，如果是省级管理员，只能看到我这一级别的的管理员创建的项目
 * @author tangmin
 * @date 2016年3月9日
 */
public enum UserTypeCityEnum {

	CITY_ADMIN("市级管理员", 3), 
	TOWN_ADMIN("区县管理员", 4),
	SCHOOL_ADMIN("校级管理员", 5),
	TEACHER("教师",6),
//	MASTER("校长个人用户",7),
//	OTHER("其他",8),
	TRAINORG("培训机构",9);

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value;

	private UserTypeCityEnum(String desc, int value) {
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

	public static UserTypeCityEnum getEnum(int value) {
		UserTypeCityEnum resultEnum = null;
		UserTypeCityEnum[] enumAry = UserTypeCityEnum.values();
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
		UserTypeCityEnum[] ary = UserTypeCityEnum.values();
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
		UserTypeCityEnum[] enumAry = UserTypeCityEnum.values();
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
		UserTypeCityEnum[] enumAry = UserTypeCityEnum.values();
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
		UserTypeCityEnum[] ary = UserTypeCityEnum.values();
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
