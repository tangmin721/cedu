package com.yanxiu.ce.system.dao;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.entity.DictItemQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MybatisDao
public interface DictItemDao extends BaseDao<DictItem, DictItemQuery>{
	
	/**
	 * 获取seq
	 * @return Integer
	 */
	Integer selectMaxSeq(@Param("catlogId")Long catlogId);

	/**
	 * 获取树根据当前父节点的最大排序序号
	 * @param catlogId
	 * @param parentId
	 * @return
	 */
	Integer selectMaxSeqParent(@Param("catlogId")Long catlogId,@Param("parentId")Long parentId);
	
	/**
	 * 查找code为@code，且id不为@id的记录条数
	 * @param code
	 * @param id
	 * @return
	 */
	public Long selectCheckCodeExit(@Param("code")String code,@Param("id")Long id);
	
	/**
	 * 根据catlogId获取ItemIds
	 */
	List<Long> selectItemIdsByCatlogId(@Param("catlogId")Long catlogId);
	
}
