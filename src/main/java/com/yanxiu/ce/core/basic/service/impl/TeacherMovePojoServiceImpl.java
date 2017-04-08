package com.yanxiu.ce.core.basic.service.impl;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.core.basic.dao.TeacherMovePojoDao;
import com.yanxiu.ce.core.basic.entity.TeacherMovePojo;
import com.yanxiu.ce.core.basic.entity.TeacherMovePojoQuery;
import com.yanxiu.ce.core.basic.service.TeacherMovePojoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 教师调动管理
 * @author tangmin
 * @date 2016-09-27 12:18:18
 */
@Service("teacherMovePojoService")
public class TeacherMovePojoServiceImpl extends BaseServiceImpl<TeacherMovePojo, TeacherMovePojoQuery> implements TeacherMovePojoService {
	@Autowired
	private TeacherMovePojoDao dao;

	@Override
	protected BaseDao<TeacherMovePojo, TeacherMovePojoQuery> dao() {
		return this.dao;
	}


	/**
	 * 更新教师调动状态
	 *
	 * @param moveStatus
	 * @param tid
	 * @return
	 */
	@Override
	public Long updateMoveStatus(Integer moveStatus, Long tid) {
		return this.dao.updateMoveStatus(moveStatus,tid);
	}

	/**
	 * 更新省市县校
	 *
	 * @param province
	 * @param city
	 * @param town
	 * @param school
	 * @param tid
	 * @return
	 */
	@Override
	public Long updatePcts(Integer province, Integer city, Integer town, Long school, Long tid) {
		return this.dao.updatePcts(province, city, town, school, tid);
	}
}