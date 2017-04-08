package com.yanxiu.ce.system.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.system.entity.DictCatlog;
import com.yanxiu.ce.system.entity.DictCatlogQuery;
import com.yanxiu.ce.system.entity.DictItem;

import java.util.List;
import java.util.Map;

public interface DictCatlogService extends BaseService<DictCatlog, DictCatlogQuery>{
	/**
	 * 获取seq
	 * @return Integer
	 */
	Integer selectMaxSeq();
	
	/**
	 * 校验是否存在
	 * @param 
	 * @return
	 */
	Boolean checkModifyCodeExit(DictCatlog entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(DictCatlog entity);

	/**
	 * 批量删除catlog，同时删除其下的item
	 * @param ids
	 * @return
	 */
	String deleteCatlogByIds(List<Long> ids);
	
	/**
	 * 根据catlog的code获取下拉框项的集合
	 * @param code
	 * @return
	 */
	List<DictItem> getSelectItems(String code);

	/**
	 * 根据catlog的code获取tree下拉框项的集合
	 * @param code
	 * @return
	 */
	List<DictItem> getSelectTreeItems(String code);
	
	/**
	 * redis同步缓存(其实就是删除缓存)
	 * @param code
	 * @return
	 */
	public Long syncSelectItems(String code);
	
	/**
	 * 根据catlog的code获取下拉框项的集合 Map  <名称  id>
	 * @return
	 */
	Map<String,Long> getSelectItemsMap(String code);
	
	/**
	 * 清除所有Dict缓存
	 */
	void removeAllCache();
}
