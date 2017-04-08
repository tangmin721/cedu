package com.yanxiu.ce.core.basic.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户状态
 * @author tangmin
 * @date 2016年3月9日
 */
public enum ExcelColEnum {

	A("A",	1	),
	B("B",	2	),
	C("C",	3	),
	D("D",	4	),
	E("E",	5	),
	F("F",	6	),
	G("G",	7	),
	H("H",	8	),
	I("I",	9	),
	J("J",	10	),
	K("K",	11	),
	L("L",	12	),
	M("M",	13	),
	N("N",	14	),
	O("O",	15	),
	P("P",	16	),
	Q("Q",	17	),
	R("R",	18	),
	S("S",	19	),
	T("T",	20	),
	U("U",	21	),
	V("V",	22	),
	W("W",	23	),
	X("X",	24	),
	Y("Y",	25	),
	Z("Z",	26	),
	AA("AA",	27	),
	AB("AB",	28	),
	AC("AC",	29	),
	AD("AD",	30	),
	AE("AE",	31	),
	AF("AF",	32	),
	AG("AG",	33	),
	AH("AH",	34	),
	AI("AI",	35	),
	AJ("AJ",	36	),
	AK("AK",	37	),
	AL("AL",	38	),
	AM("AM",	39	),
	AN("AN",	40	),
	AO("AO",	41	),
	AP("AP",	42	),
	AQ("AQ",	43	),
	AR("AR",	44	),
	AS("AS",	45	),
	AT("AT",	46	),
	AU("AU",	47	),
	AV("AV",	48	),
	AW("AW",	49	),
	AX("AX",	50	),
	AY("AY",	51	),
	AZ("AZ",	52	),
	BA("BA",	53	),
	BB("BB",	54	),
	BC("BC",	55	),
	BD("BD",	56	),
	BE("BE",	57	),
	BF("BF",	58	),
	BG("BG",	59	),
	BH("BH",	60	),
	BI("BI",	61	),
	BJ("BJ",	62	),
	BK("BK",	63	),
	BL("BL",	64	),
	BM("BM",	65	),
	BN("BN",	66	),
	BO("BO",	67	),
	BP("BP",	68	),
	BQ("BQ",	69	),
	BR("BR",	70	),
	BS("BS",	71	),
	BT("BT",	72	),
	BU("BU",	73	),
	BV("BV",	74	),
	BW("BW",	75	),
	BX("BX",	76	),
	BY("BY",	77	),
	BZ("BZ",	78	),
	CA("CA",	79	),
	CB("CB",	80	),
	CC("CC",	81	),
	CD("CD",	82	),
	CE("CE",	83	),
	CF("CF",	84	),
	CG("CG",	85	),
	CH("CH",	86	),
	CI("CI",	87	),
	CJ("CJ",	88	),
	CK("CK",	89	),
	CL("CL",	90	),
	CM("CM",	91	),
	CN("CN",	92	),
	CO("CO",	93	),
	CP("CP",	94	),
	CQ("CQ",	95	),
	CR("CR",	96	),
	CS("CS",	97	),
	CT("CT",	98	),
	CU("CU",	99	),
	CV("CV",	100	);

	/** 描述 */
	private String desc;
	/** 枚举值 */
	private int value;

	private ExcelColEnum(String desc, int value) {
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

	public static ExcelColEnum getEnum(int value) {
		ExcelColEnum resultEnum = null;
		ExcelColEnum[] enumAry = ExcelColEnum.values();
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
		ExcelColEnum[] ary = ExcelColEnum.values();
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
		ExcelColEnum[] enumAry = ExcelColEnum.values();
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
		ExcelColEnum[] enumAry = ExcelColEnum.values();
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
		ExcelColEnum[] ary = ExcelColEnum.values();
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
