package com.yanxiu.ce.core.train.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学分生成类型
 新增内容说明：
 1、自动生成学时 - 和培训机构对接，返回培训成绩后，自动根据成绩生成教师学时数据。 合格即获得项目学时，不合格没有项目学时。
 2、管理员确认生成学时 - 需要项目创建者在平台上进行确认操作，操作后学时根据成绩自动生成。合格即获得项目学时，不合格没有项目学时。
 3、不过关联，个人申报  - 项目下仅保留成绩，成绩不与学时有任何关联，需要教师个人进行学时申报。
 * @author tangmin
 * @date 2016年10月24日
 */
public enum ScoreCreateTypeEnum {

	ORG_CREATE("自动生成学时：由培训机构直接导入学时。", 0),
	ADMIN_CREATE("管理员确认生成学时：培训结束后，管理员通过【生成学时】按钮生成学时。", 1),
	UN_CREATE("个人申报学时：培训机构直接导入的参培人名单，不做关联，只做参考。", 2);

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value;

	private ScoreCreateTypeEnum(String desc, int value) {
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

	public static ScoreCreateTypeEnum getEnum(int value) {
		ScoreCreateTypeEnum resultEnum = null;
		ScoreCreateTypeEnum[] enumAry = ScoreCreateTypeEnum.values();
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
		ScoreCreateTypeEnum[] ary = ScoreCreateTypeEnum.values();
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
		ScoreCreateTypeEnum[] enumAry = ScoreCreateTypeEnum.values();
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
		ScoreCreateTypeEnum[] enumAry = ScoreCreateTypeEnum.values();
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
		ScoreCreateTypeEnum[] ary = ScoreCreateTypeEnum.values();
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
