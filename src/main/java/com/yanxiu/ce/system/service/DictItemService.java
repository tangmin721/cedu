package com.yanxiu.ce.system.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.system.dto.DictItemNodeDto;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.entity.DictItemQuery;

import java.util.List;

public interface DictItemService extends BaseService<DictItem, DictItemQuery>{
	/**
	 * 获取seq
	 * @return Integer
	 */
	Integer selectMaxSeq(Long catlogId);

	/**
	 * 校验是否存在
	 */
	Boolean checkModifyCodeExit(DictItem entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(DictItem entity);
	
	/**
	 * 根据catlogId获取ItemIds
	 */
	List<Long> selectItemIdsByCatlogId(Long catlogId);

	/**
	 * 保存树
	 * @param catlogId
	 * @param dictItemNodeDtos
	 */
    void saveDictItemTree(Long catlogId, List<DictItemNodeDto> dictItemNodeDtos);
}
