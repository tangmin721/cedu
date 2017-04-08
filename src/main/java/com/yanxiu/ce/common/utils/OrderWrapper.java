package com.yanxiu.ce.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串中有数字的排序包装容器
 * @author tangmin
 * @date 2016年4月8日
 */
public class OrderWrapper implements Comparable<OrderWrapper> {
	String name = null;

	public OrderWrapper(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return String.valueOf(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (obj instanceof OrderWrapper) {
			OrderWrapper other = (OrderWrapper) obj;

			if (null == this.name) {
				return false;
			} else {
				return this.name.equals(other.name);
			}
		}
		return false;
	}

	// 比较方法,相当于减法。 (return this - wrapper)
	public int compareTo(OrderWrapper wrapper) {
		if (null == wrapper) {
			return 1;
		}
		// 直接相等
		if (this == wrapper || this.equals(wrapper)) {
			return 0;
		}
		String name1 = this.name;
		String name2 = wrapper.name;
		// 特殊情形，name有一个为空的情况.
		if (null == name1) {
			// 都为空，认为相对
			if (null == name2) {
				return 0;
			} else {
				return -1;
			}
		} else if (null == name2) {
			return 1;
		}
		// 中间 1-多个数字
		Pattern pattern = Pattern.compile("\\D*(\\d+)\\D*");
		Matcher matcher1 = pattern.matcher(name1);
		Matcher matcher2 = pattern.matcher(name2);
		// System.out.println(pattern.pattern());
		//
		int index1_step = 0;
		int index2_step = 0;
		while (matcher1.find()) {
			String s1 = matcher1.group(1);
			String s2 = null;
			if (matcher2.find()) {
				s2 = matcher2.group(1);
			}
			int index1 = name1.indexOf(s1, index1_step);
			int index2 = name2.indexOf(s2, index2_step);
			//
			index1_step = index1;
			index2_step = index2;
			// 索引相等的情况下
			if (index1 == index2) {
				String pre1 = name1.substring(0, index1);
				String pre2 = name2.substring(0, index2);
				if (pre1.equals(pre2)) {
					//
					long num1 = Long.parseLong(s1);
					long num2 = Long.parseLong(s2);
					//
					if (num1 == num2) {
						// 比较下一组
						continue;
					} else {
						return (int) (num1 - num2);
					}
				} else {
					break;
				}
			} else {
				break;
			}
		}

		// 最后的情形.
		return this.name.compareTo(wrapper.name);
	}

	public static void testNew() {
		List<OrderWrapper> chinesesOrderList = new ArrayList<OrderWrapper>();
		chinesesOrderList.add(new OrderWrapper("aaa12-1.mp3"));
		chinesesOrderList.add(new OrderWrapper("aaa111-10.mp3"));
		chinesesOrderList.add(new OrderWrapper("aaa22-11.mp3"));
		chinesesOrderList.add(new OrderWrapper("aaa11-12.mp3"));
		chinesesOrderList.add(new OrderWrapper("aaa3-13.mp3"));
		chinesesOrderList.add(new OrderWrapper("aaa1-13.mp3"));

		// Collator collatorChinese =
		// Collator.getInstance(java.util.Locale.CHINA);
		// collatorChinese = Collator.getInstance(java.util.Locale.CHINESE);//中文排序
		// Collections.sort(chinesesOrderList, collatorChinese);
		Collections.sort(chinesesOrderList);

		System.out.println("中文+数字排序： = ");
		for (int i = 0; i < chinesesOrderList.size(); i++) {
			OrderWrapper chinese = chinesesOrderList.get(i);
			System.out.println("" + chinese);
		}

	}

	public static void main(String[] args) {
		testNew();
	}
}