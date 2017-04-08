package com.yanxiu.ce.system.service.impl;

import com.google.common.collect.Lists;
import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.system.dao.DictItemDao;
import com.yanxiu.ce.system.dto.DictItemNodeDto;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.entity.DictItemQuery;
import com.yanxiu.ce.system.service.DictItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("dictItemService")
public class DictItemServiceImpl extends BaseServiceImpl<DictItem, DictItemQuery> implements DictItemService{
	@Autowired
	private DictItemDao dao;

	@Override
	protected BaseDao<DictItem, DictItemQuery> dao() {
		return this.dao;
	}
	
	/**
	 * 获取seq
	 */
	@Override
	public Integer selectMaxSeq(Long catlogId) {
		Integer selectMaxSeq = this.dao.selectMaxSeq(catlogId);
		if(selectMaxSeq!=null){
			return selectMaxSeq;
		}
		return 0;
	}

	/**
	 * 校验entity是否可修改（code是否存在）
	 */
	@Override
	public Boolean checkModifyCodeExit(DictItem entity) {
		Long count = this.dao.selectCheckCodeExit(entity.getCode(), entity.getId());
		if(count>0){
			return false;
		}
		return true;
	}
	
	/**
	 * 新增or修改
	 */
	@Override
	public String saveOrUpdate(DictItem entity) {
//		if(!checkModifyCodeExit(entity)){
//			if(entity.getId()==null){
//				throw new ValidateOtherException(ValidateOtherException.INSERT_FAILD,"编码已经存在，新增失败");
//			}else {
//				throw new ValidateOtherException(ValidateOtherException.UPDATE_FAILD,"编码已经存在，修改失败");
//			}
//		}
		String msg = "";
		if(entity.getId()==null){
			this.insert(entity);
			msg = "添加成功！";
		}else {
			this.update(entity);
				msg = "编辑成功！";
		}
		return msg;
	}

	/**
	 * 根据catlogId获取itemIds
	 */
	@Override
	public List<Long> selectItemIdsByCatlogId(Long catlogId) {
		return this.dao.selectItemIdsByCatlogId(catlogId);
	}

    @Override
	@Transactional
    public void saveDictItemTree(Long catlogId, List<DictItemNodeDto> dictItemNodeDtos) {
		List<Long> ids = Lists.newArrayList();
		for (DictItemNodeDto itemNodeDto : dictItemNodeDtos) {
			if(itemNodeDto.getId()==null){
				DictItem dictItem = new DictItem();
				tranFromDto(catlogId, itemNodeDto, dictItem);
				this.dao.insert(dictItem);
				ids.add(dictItem.getId());
			}else {
				DictItem dictItem = this.selectById(Long.valueOf(itemNodeDto.getId()));
				tranFromDto(catlogId, itemNodeDto, dictItem);
				this.dao.update(dictItem);
				ids.add(dictItem.getId());
			}
		}

		//删除不在dictItemNodeDtos的节点
		DictItemQuery query = new DictItemQuery();
		query.setCatlogId(catlogId.toString());
		query.setFields("id");
		List<DictItem> dictItems = this.selectList(query);
		for (DictItem dictItem : dictItems) {
			if(!ids.contains(dictItem.getId())){
				this.deleteById(dictItem.getId());
			}
		}
	}

	//set属性
	private void tranFromDto(Long catlogId, DictItemNodeDto itemNodeDto, DictItem dictItem) {
		dictItem.setCatlogId(catlogId);
		dictItem.setName(itemNodeDto.getName());
		dictItem.setCode(itemNodeDto.getCode());
		//排序序号
		if (itemNodeDto.getSeq() != null) {
			dictItem.setSeq(itemNodeDto.getSeq());
		}else {
			dictItem.setSeq(1);
		}
		dictItem.setParentId(itemNodeDto.getpId());
	}

}