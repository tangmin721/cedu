package com.yanxiu.ce.common.core.service;

import java.util.List;

import com.yanxiu.ce.common.core.entity.BaseEntity;
import com.yanxiu.ce.common.core.entity.BaseQuery;
import com.yanxiu.ce.common.core.entity.Pagination;

/**
 * <p>Description:服务层 </p>
 * @author tangm
 * @date 2016年2月16日 
 * @version 1.0
 */
public interface BaseService<T extends BaseEntity,Q extends BaseQuery> {
	/**
	 * 根据实体对象新增记录.
	 * @param entity
	 * @return 返回entity.getId()
	 */
	Long insert(T entity);

	/**
	 * 批量保存对象
	 * @param list
	 * @return 返回插入的条数
	 */
	Long insertBatch(List<T> list);
	
	/**
	 * 根据ID查找记录.
	 * @param id
	 * @return 返回T
	 */
	T selectById(Long id);
	
	/**
	 * 批量根据ids查找记录.
	 * @param ids
	 * @return 返回List<T>
	 */
	List<T> selectByIds(List<Long> ids);

	/**
	 * 更新实体对应的记录.
	 * @param entity
	 * @return
	 */
	Long update(T entity);
	
	/**
	 * 批量更新list
	 * @param list
	 * @return 返回更新的条数
	 */
	Long updateBatch(List<T> list);
	
	/**
	 * 根据id 彻底删除 
	 * @param id
	 * @return
	 */
	Long deleteById(Long id);
	
	/**
	 * 根据ids 彻底删除 
	 * @param id
	 * @return  返回删除的条数
	 */
	Long deleteByIds(List<Long> ids);
	
	/**
	 * 查询所有记录
	 * @param query
	 * @return
	 */
	List<T> selectList(Q query);
	
	/**
	 * limit Page查询
	 * @param query
	 * @return 返回结果集
	 */
	List<T> selectListPage(Q query);
	
	/**
	 * 总条数
	 * @param query
	 * @return
	 */
	Long selectListTotal(Q query);
	
	/**
	 * 页面分页
	 * @param query
	 * @return 返回分页对象
	 */
	Pagination<T> selectListPagination(Q query);


}
