package com.yanxiu.ce.core.basic.service.impl;

import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.core.basic.dao.TeacherMoveRecodeDao;
import com.yanxiu.ce.core.basic.entity.Teacher;
import com.yanxiu.ce.core.basic.entity.TeacherMoveRecode;
import com.yanxiu.ce.core.basic.entity.TeacherMoveRecodeQuery;
import com.yanxiu.ce.core.basic.enums.TeacherMoveStatusEnum;
import com.yanxiu.ce.core.basic.service.TeacherMovePojoService;
import com.yanxiu.ce.core.basic.service.TeacherMoveRecodeService;
import com.yanxiu.ce.core.basic.service.TeacherService;
import com.yanxiu.ce.core.score.entity.Score;
import com.yanxiu.ce.core.score.entity.ScoreQuery;
import com.yanxiu.ce.core.score.service.ScoreService;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 教师调动管理
 * @author tangmin
 * @date 2016-09-27 16:50:41
 */
@Service("teacherMoveRecodeService")
public class TeacherMoveRecodeServiceImpl extends BaseServiceImpl<TeacherMoveRecode, TeacherMoveRecodeQuery> implements TeacherMoveRecodeService{
	@Autowired
	private TeacherMoveRecodeDao dao;

	@Autowired
	private ScoreService scoreService;

	@Autowired
	private UserService userService;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private TeacherMovePojoService teacherMovePojoService;

	@Override
	protected BaseDao<TeacherMoveRecode, TeacherMoveRecodeQuery> dao() {
		return this.dao;
	}
	
	/**
	 * 获取seq
	 */
	@Override
	public Integer selectMaxSeq() {
		Integer selectMaxSeq = this.dao.selectMaxSeq();
		if(selectMaxSeq!=null){
			return selectMaxSeq;
		}
		return 0;
	}

	/**
	 * 校验entity是否可修改（code是否存在）
	 */
	@Override
	public Boolean checkNameExit(TeacherMoveRecode entity) {
//		Long count = this.dao.selectCheckNameExit(entity.getName(), entity.getId());
//		if(count>0){
//			return false;
//		}
		return true;
	}
	
	/**
	 * 新增or修改
	 */
	@Override
	@Transactional
	public String saveOrUpdate(TeacherMoveRecode entity) {
		String msg = "";
		Long tid = entity.getTid();
		Teacher teacher = teacherService.selectById(tid);
		entity.setProvince(teacher.getProvince());
		entity.setCity(teacher.getCity());
		entity.setTown(teacher.getTown());
		entity.setSchool(teacher.getSchool());
		if(entity.getId()==null){
			entity.setSendDate(new Date());
			entity.setMoveStatus(TeacherMoveStatusEnum.MOVING.getValue());//初始状态-调动中
			this.insert(entity);

			//同时更新t_teacher的调动状态，设置为1
			teacherMovePojoService.updateMoveStatus(1,tid);

			msg = "添加成功！";
		}else {
			this.update(entity);
				msg = "编辑成功！";

			//若是调动完成，更新t_teacher的省市县校信息
			if(entity.getMoveStatus()==TeacherMoveStatusEnum.MOVED.getValue()){
				teacherMovePojoService.updatePcts(entity.getTprovince(),entity.getTcity(),entity.getTtown(),entity.getTschool(),tid);
			}
			//同时更新t_teacher的调动状态,置为空
			teacherMovePojoService.updateMoveStatus(null,tid);
		}
		return msg;
	}

    @Override
	@Transactional
    public void cancelMove(Long id) {

		TeacherMoveRecode teacherMoveRecode = this.selectById(id);
		//同时更新t_teacher的调动状态,置为空
		teacherMovePojoService.updateMoveStatus(null,teacherMoveRecode.getTid());
		//删除
		this.deleteById(id);
    }

	@Override
	@Transactional
	public void acceptMove(Long id) {
		TeacherMoveRecode entity = this.selectById(id);
		//同时更新t_teacher的调动状态,置为空
		Long tid = entity.getTid();
		teacherMovePojoService.updateMoveStatus(null, tid);

		//t_teacher表  更新省市区县
		teacherMovePojoService.updatePcts(entity.getTprovince(),entity.getTcity(),entity.getTtown(),entity.getTschool(), tid);

		updateUserAndScore(tid,entity);

		//更新调入状态
		entity.setMoveStatus(TeacherMoveStatusEnum.MOVED.getValue());
		entity.setTakeDate(new Date());
		this.update(entity);
	}

	/**
	 * 同时更新SYS_USER和学时表
	 * @param tid
	 */
	private void updateUserAndScore(Long tid,TeacherMoveRecode recode) {
		Teacher teacher = teacherService.selectById(tid);
		//User更新省市区县
		User user = userService.selectByLoginName(teacher.getIdCard()+"");
		if(user!=null){
			user.setProvince(recode.getTprovince());
			user.setCity(recode.getTcity());
			user.setTown(recode.getTtown());
			user.setSchool(recode.getTschool());
			userService.update(user);
		}

		ScoreQuery scoreQuery = new ScoreQuery();
		scoreQuery.setTid(tid+"");
		List<Score> scores = scoreService.selectList(scoreQuery );
		for(Score score:scores){
			score.setProvince(recode.getTprovince());
			score.setCity(recode.getTcity());
			score.setTown(recode.getTtown());
			score.setSchool(recode.getTschool());
			scoreService.update(score);
		}
	}

	@Override
	public void refuseMove(Long id, String takeMemo) {
		TeacherMoveRecode entity = this.selectById(id);

		//同时更新t_teacher的调动状态,置为空
		teacherMovePojoService.updateMoveStatus(null,entity.getTid());

		//更新调入状态
		entity.setMoveStatus(TeacherMoveStatusEnum.UNMOVE.getValue());
		entity.setTakeDate(new Date());
		entity.setTakeMemo(takeMemo);
		this.update(entity);
	}

	@Override
	public Long selectInListTotal(TeacherMoveRecodeQuery query) {
		return this.dao.selectInListTotal(query);
	}

	@Override
	public Pagination<TeacherMoveRecode> selectInListPagination(TeacherMoveRecodeQuery query) {
		Pagination<TeacherMoveRecode> pagination = new Pagination<TeacherMoveRecode>(query.getPageCurrent(),query.getPageSize(),this.selectInListTotal(query));
		pagination.setList(this.dao.selectInListPage(query));
		return pagination;
	}

	@Override
	public Long selectAllListTotal(TeacherMoveRecodeQuery query) {
		return this.dao.selectInListTotal(query);
	}

	@Override
	public Pagination<TeacherMoveRecode> selectAllListPagination(TeacherMoveRecodeQuery query) {
		Pagination<TeacherMoveRecode> pagination = new Pagination<TeacherMoveRecode>(query.getPageCurrent(),query.getPageSize(),this.selectAllListTotal(query));
		pagination.setList(this.dao.selectAllListPage(query));
		return pagination;
	}


}