package com.yanxiu.ce.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.google.common.collect.Lists;
public class JsonUtils {

	@SuppressWarnings("unchecked")
	public static Map<String, Object> beanToMap(Object bean) {
		Map<String, Object> map = null;
		try {
			map = BeanUtils.describe(bean);
			map.remove("class");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return map;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Map<String, Object>> toMapList(List beans) {
		List<Map<String, Object>> lists = Lists.newArrayList();
		for (Object bean : beans) {
			Map<String, Object> map = null;
			try {
				map = BeanUtils.describe(bean);
				map.remove("class");
				lists.add(map);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		return lists;

	}
}
