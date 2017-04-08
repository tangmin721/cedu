package com.yanxiu.ce.system.service;

import java.util.List;
import java.util.Map;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.system.entity.City;
import com.yanxiu.ce.system.entity.CityQuery;

/**
 * 市管理
 * @author tangmin
 * @date 2016-03-08 17:13:48
 */
public interface CityService extends BaseService<City, CityQuery>{
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(City entity);

	/**
	 * 根据provinceNo获取citys下拉框数据
	 * @param provinceNo
	 * @return
	 */
	List<City> citys(Integer provinceNo);
	
	/**
	 * 市的 Map  <名称  no>
	 * @return
	 */
	Map<String,Integer> cityMap(Integer provinceNo);
	
	/**
	 * 根据编号获取名称
	 * @return
	 */
	String getNameByNo(Integer cityNo);
	
	/**
	 * redis同步缓存(其实就是删除缓存)
	 * @param code
	 * @return
	 */
	public Long syncSelectItems(Integer provinceNo);
	
	/**
	 * 清除所有缓存
	 */
	void removeAllCache();

}
