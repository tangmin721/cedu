package com.yanxiu.ce.core.basic.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 教师类型
 * @author tangmin
 * @date 2016年8月18日
 */
public enum TeacherTypeEnum {

	MIDDLE_SCHOOL("中小学校", 0),
	TECHNICAL_SCHOOL ("中等职业学校", 1),
	SPECIAL_SCHOOL ("特殊教育学校", 2),
	KINDERGARTEN("幼儿园", 3);

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value;

	private TeacherTypeEnum(String desc, int value) {
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

	public static TeacherTypeEnum getEnum(int value) {
		TeacherTypeEnum resultEnum = null;
		TeacherTypeEnum[] enumAry = TeacherTypeEnum.values();
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
		TeacherTypeEnum[] ary = TeacherTypeEnum.values();
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
	 * 转换为map
	 * @return
	 */
	public static Map<String, Integer> toSimpleMap() {
		TeacherTypeEnum[] ary = TeacherTypeEnum.values();
		Map<String, Integer> enumMap = new HashMap<String, Integer>();
		for (int num = 0; num < ary.length; num++) {
			enumMap.put(ary[num].getDesc(), ary[num].getValue());
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
		TeacherTypeEnum[] enumAry = TeacherTypeEnum.values();
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
		TeacherTypeEnum[] enumAry = TeacherTypeEnum.values();
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
		TeacherTypeEnum[] ary = TeacherTypeEnum.values();
		List list = new ArrayList();
		for (int i = 0; i < ary.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("value", String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}
	
	
	public static void main(String[] args) {
		System.out.println(TeacherTypeEnum.toSimpleMap());
	}
}
