package com.yanxiu.ce.system.service;

import java.util.List;
import java.util.Map;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.system.entity.Town;
import com.yanxiu.ce.system.entity.TownQuery;

/**
 * 区/县管理
 * @author tangmin
 * @date 2016-03-08 17:14:40
 */
public interface TownService extends BaseService<Town, TownQuery>{
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(Town entity);

	/**
	 * 根据cityNo获取Town下拉列表框
	 * @param cityNo
	 * @return
	 */
	List<Town> towns(Integer cityNo);

	/**
	 * 区县的 Map  <名称  no>
	 * @return
	 */
	Map<String,Integer> townMap(Integer cityNo);
	
	/**
	 * 根据编号获取名称
	 * @return
	 */
	String getNameByNo(Integer townNo);
	
	/**
	 * redis同步缓存(其实就是删除缓存)
	 * @param code
	 * @return
	 */
	public Long syncSelectItems(Integer cityNo);
	
	/**
	 * 清除所有缓存
	 */
	void removeAllCache();
}
