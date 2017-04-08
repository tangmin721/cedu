package com.yanxiu.ce.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.mybatis.annotation.MybatisDao;
import com.yanxiu.ce.system.entity.Attachment;
import com.yanxiu.ce.system.entity.AttachmentQuery;

/**
 * 附件管理
 * @author tangmin
 * @date 2016-04-12 16:56:21
 */
@MybatisDao
public interface AttachmentDao extends BaseDao<Attachment, AttachmentQuery>{
	
	/**
	 * 获取seq
	 * @return Integer
	 */
	Integer selectMaxSeq();
	
	/**
	 * 查找name为@name，且id不为@id的记录条数
	 * @param code
	 * @param id
	 * @return
	 */
	public Long selectCheckNameExit(@Param("name")String name,@Param("id")Long id);
	
	/**
	 * 根据表名 ,实体类的id获取附件的att id集合
	 * @param tableName
	 * @param attId
	 */
	public List<Long> selectFromTableById(@Param("tableName")String tableName,@Param("primaryId")Long primaryId);
	
	/**
	 * 根据表名   删除附件
	 * @param tableName
	 * @param attId
	 */
	public void deleteFromTableByAttId(@Param("tableName")String tableName,@Param("attId")Long attId);
	
}
