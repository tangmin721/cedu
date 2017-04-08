package com.yanxiu.ce.system.service;

import java.util.List;
import java.util.Map;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.system.entity.Province;
import com.yanxiu.ce.system.entity.ProvinceQuery;

/**
 * 省管理
 * @author tangmin
 * @date 2016-03-08 17:13:15
 */
public interface ProvinceService extends BaseService<Province, ProvinceQuery>{
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(Province entity);
	
	/**
	 * 所有省下拉框
	 * @return
	 */
	List<Province> provinces();
	
	/**
	 * 所有省的 Map  <名称  no>
	 * @return
	 */
	Map<String,Integer> provinceMap();
	
	
	/**
	 * 根据编号获取名称
	 * @return
	 */
	String getNameByNo(Integer provinceNo);
	
	
	/**
	 * redis同步缓存(其实就是删除缓存)
	 * @param code
	 * @return
	 */
	public Long syncSelectItems();
	
	/**
	 * 清除所有缓存
	 */
	void removeAllCache();

}
