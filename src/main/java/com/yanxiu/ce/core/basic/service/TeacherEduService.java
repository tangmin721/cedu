package com.yanxiu.ce.core.basic.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.TeacherEdu;
import com.yanxiu.ce.core.basic.entity.TeacherEduQuery;

/**
 * 教学和学历管理
 * @author tangmin
 * @date 2016-04-05 14:01:34
 */
public interface TeacherEduService extends BaseService<TeacherEdu, TeacherEduQuery>{
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
	Boolean checkNameExit(TeacherEdu entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(TeacherEdu entity);
	
	/**
	 * 根据TID查找记录.
	 * @param id
	 * @return 返回T
	 */
	TeacherEdu selectByTid(Long id);
	
	/**
	 * excel导入的保存方法，不用再校验了，前面已经校验完成了
	 */
	Long saveImpTeacherEdu(TeacherEdu entity);

}
