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
public enum ScoreTypeEnum {

	XIANGMU("项目学时", 1), 
	XUELI("学历提升",2),
	XIAOBEN("校本研修", 3), 
	LUNWEN("论文/专著",4),
	XUESHU("学术交流", 5), 
	KETI("课题研究",6),
	QITA("其他", 7),
	MINEYANXIU("自主研修", 8);

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value;

	private ScoreTypeEnum(String desc, int value) {
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

	public static ScoreTypeEnum getEnum(int value) {
		ScoreTypeEnum resultEnum = null;
		ScoreTypeEnum[] enumAry = ScoreTypeEnum.values();
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
		ScoreTypeEnum[] ary = ScoreTypeEnum.values();
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
		ScoreTypeEnum[] enumAry = ScoreTypeEnum.values();
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
		ScoreTypeEnum[] enumAry = ScoreTypeEnum.values();
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
		ScoreTypeEnum[] ary = ScoreTypeEnum.values();
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
