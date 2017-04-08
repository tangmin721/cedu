package com.yanxiu.ce.system.dao;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.system.entity.DictCatlog;
import com.yanxiu.ce.system.entity.DictCatlogQuery;
import com.yanxiu.ce.system.entity.DictItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MybatisDao
public interface DictCatlogDao extends BaseDao<DictCatlog, DictCatlogQuery>{
	
	/**
	 * 获取seq
	 * @return Integer
	 */
	Integer selectMaxSeq();
	
	/**
	 * 查找code为@code，且id不为@id的记录条数
	 * @param code
	 * @param id
	 * @return
	 */
	public Long selectCheckCodeExit(@Param("code")String code,@Param("id")Long id);
	
	
	List<DictItem> getSelectItems(@Param("code")String code);

	DictCatlog selectByCode(@Param("code")String code);
	
}
