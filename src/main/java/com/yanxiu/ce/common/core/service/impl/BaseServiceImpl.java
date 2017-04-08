package com.yanxiu.ce.common.core.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.yanxiu.ce.common.core.controller.BaseController;
import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.core.entity.BaseQuery;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.common.exception.BizException;
import com.yanxiu.ce.common.exception.ValidateException;
import com.yanxiu.ce.common.validate.BeanValidator;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.Update;
import com.yanxiu.ce.common.validate.ValidatorResult;

/**
 * @Description: baseService实现
 * @author: tangm
 * @date: 2016年2月18日 
 * @version: 1.0
 */
public abstract class BaseServiceImpl<T extends BaseEntity,Q extends BaseQuery> implements BaseService<T,Q> {
	
	protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	protected abstract BaseDao<T,Q> dao();

	@Transactional
	public Long insert(T entity){
		if (entity == null){
			throw new RuntimeException("insert entity:T is null");
		}
		
		//校验
		ValidatorResult validateResult = BeanValidator.validateResult(entity, Insert.class);
		if(!validateResult.getFlag()){
			throw new ValidateException(ValidateException.INSERT_FAILD, JSON.toJSONString(validateResult.getErrorObjs()));
		}
		
		Long result = this.dao().insert(entity);

		if (result <= 0){
			throw BizException.DB_INSERT_RESULT_0;
		}

		if (entity != null && entity.getId() != null && result > 0){
			return entity.getId();
		}

		return result;
	}

	@Transactional
	public Long insertBatch(List<T> list) {
		if (list == null || list.size() <= 0){
			return 0l;
		}
			
		for(T entity:list){
			//校验
			ValidatorResult validateResult = BeanValidator.validateResult(entity, Insert.class);
			if(!validateResult.getFlag()){
				throw new ValidateException(ValidateException.INSERT_FAILD, JSON.toJSONString(validateResult.getErrorObjs()));
			}
		}

		Long result = this.dao().insertBatch(list);

		if (result <= 0){
			throw BizException.DB_INSERT_RESULT_0;
		}

		return result;
		
	}

	public T selectById(Long id) {
		return this.dao().selectById(id);
	}

	public List<T> selectByIds(List<Long> ids) {
		return this.dao().selectByIds(ids);
	}

	@Transactional
	public Long update(T entity) {
		if (entity == null){
			throw new RuntimeException("update entity:T is null");
		}
		
		//校验
		ValidatorResult validateResult = BeanValidator.validateResult(entity, Update.class);
		if(!validateResult.getFlag()){
			throw new ValidateException(ValidateException.UPDATE_FAILD, JSON.toJSONString(validateResult.getErrorObjs()));
		}

		Long result = this.dao().update(entity);

		if (result <= 0){
			throw BizException.DB_UPDATE_RESULT_0;
		}

		return result;
	}
	
	/**
	 * 批量更新 校验
	 */
	@Transactional
	public Long updateBatch(List<T> list){
		if (list == null || list.size() <= 0)
			return 0l;

		Long result = 0l;
		
		for(T entity:list){
			//校验
			ValidatorResult validateResult = BeanValidator.validateResult(entity, Update.class);
			if(!validateResult.getFlag()){
				throw new ValidateException(ValidateException.UPDATE_FAILD, JSON.toJSONString(validateResult.getErrorObjs()));
			}
			this.update(entity);
			result += 1;
		}

		if (result <= 0)
			throw BizException.DB_UPDATE_RESULT_0;

		return result;
	}

	public Long deleteById(Long id) {
		return this.dao().deleteById(id);
	}

	public Long deleteByIds(List<Long> ids) {
		return this.dao().deleteByIds(ids);
	}

	public List<T> selectList(Q query) {
		return this.dao().selectList(query);
	}

	public List<T> selectListPage(Q query) {
		return this.dao().selectListPage(query);
	}

	public Long selectListTotal(Q query) {
		return this.dao().selectListTotal(query);
	}
	
	public Pagination<T> selectListPagination(Q query){
		Pagination<T> pagination = new Pagination<T>(query.getPageCurrent(),query.getPageSize(),this.selectListTotal(query));
		pagination.setList(this.selectListPage(query));
		return pagination;
	}

}
