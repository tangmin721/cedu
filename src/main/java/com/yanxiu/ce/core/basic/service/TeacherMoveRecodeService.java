package com.yanxiu.ce.core.basic.service;

import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.TeacherMoveRecode;
import com.yanxiu.ce.core.basic.entity.TeacherMoveRecodeQuery;

/**
 * 教师调动管理
 * @author tangmin
 * @date 2016-09-27 16:50:41
 */
public interface TeacherMoveRecodeService extends BaseService<TeacherMoveRecode, TeacherMoveRecodeQuery>{
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
	Boolean checkNameExit(TeacherMoveRecode entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(TeacherMoveRecode entity);

	/**
	 * 取消调动
	 * @param id
	 * @return
	 */
	void cancelMove(Long id);

	/**
	 * 接收调动
	 * @param id
	 */
	void acceptMove(Long id);

	/**
	 * 拒绝调动
	 */
	void refuseMove(Long id,String takeMemo);

	/**
	 * 调入in总条数
	 * @param query
	 * @return
	 */
	Long selectInListTotal(TeacherMoveRecodeQuery query);

	/**
	 * 调入in页面分页
	 * @param query
	 * @return 返回分页对象
	 */
	Pagination<TeacherMoveRecode> selectInListPagination(TeacherMoveRecodeQuery query);

	/**
	 * all总条数
	 * @param query
	 * @return
	 */
	Long selectAllListTotal(TeacherMoveRecodeQuery query);

	/**
	 *all页面分页
	 * @param query
	 * @return 返回分页对象
	 */
	Pagination<TeacherMoveRecode> selectAllListPagination(TeacherMoveRecodeQuery query);


}
