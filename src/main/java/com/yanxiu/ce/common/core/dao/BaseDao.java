package com.yanxiu.ce.common.core.dao;

import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.core.entity.BaseQuery;

import java.util.List;

/**
 * @Description: Description:基础Dao，集成常用的方法,对应mybatis的Dao.xml
 * @author: tangm
 * @date: 2016年2月16日 
 * @version: 1.0
 */
public interface BaseDao<T extends BaseEntity,Q extends BaseQuery> {
	
	/**
	 * 根据实体对象新增记录.
	 * @param entity
	 * @return
	 */
	Long insert(T entity);

	/**
	 * 批量保存对象
	 * @param list
	 * @return
	 */
	Long insertBatch(List<T> list);
	
	/**
	 * 根据ID查找记录.
	 * @param id
	 * @return
	 */
	T selectById(Long id);
	
	/**
	 * 批量根据ids查找记录.
	 * @param ids
	 * @return
	 */
	List<T> selectByIds(List<Long> ids);

	/**
	 * 更新实体对应的记录.
	 * @param entity
	 * @return
	 */
	Long update(T entity);
	
	/**
	 * 根据id 彻底删除 
	 * @param id
	 * @return
	 */
	Long deleteById(Long id);
	
	/**
	 * 根据ids 彻底删除 
	 * @param ids
	 * @return
	 */
	Long deleteByIds(List<Long> ids);
	
	/**
	 * 查询所有记录
	 * @param query
	 * @return
	 */
	List<T> selectList(Q query);
	
	/**
	 * Limit Page查询
	 * @param query
	 * @return
	 */
	List<T> selectListPage(Q query);
	
	/**
	 * 总条数
	 * @param query
	 * @return
	 */
	Long selectListTotal(Q query);
	

}
