package com.yanxiu.ce.core.basic.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.exception.ValidateOtherException;
import com.yanxiu.ce.core.basic.dao.TeacherCredentDao;
import com.yanxiu.ce.core.basic.entity.TeacherCredent;
import com.yanxiu.ce.core.basic.entity.TeacherCredentAtt;
import com.yanxiu.ce.core.basic.entity.TeacherCredentQuery;
import com.yanxiu.ce.core.basic.service.TeacherCredentAttService;
import com.yanxiu.ce.core.basic.service.TeacherCredentService;
import com.yanxiu.ce.system.service.AttachmentService;

/**
 * 证书管理
 * @author tangmin
 * @date 2016-04-12 15:26:41
 */
@Service("teacherCredentService")
public class TeacherCredentServiceImpl extends BaseServiceImpl<TeacherCredent, TeacherCredentQuery> implements TeacherCredentService{
	@Autowired
	private TeacherCredentDao dao;
	
	@Autowired
	private AttachmentService attService;
	
	@Autowired
	private TeacherCredentAttService teacherCredentAttService;

	@Override
	protected BaseDao<TeacherCredent, TeacherCredentQuery> dao() {
		return this.dao;
	}
	
	/**
	 * 获取seq
	 */
	@Override
	public Integer selectMaxSeq(Long tid) {
		Integer selectMaxSeq = this.dao.selectMaxSeq(tid);
		if(selectMaxSeq!=null){
			return selectMaxSeq;
		}
		return 0;
	}

	/**
	 * 校验entity是否可修改（code是否存在）
	 */
	@Override
	public Boolean checkNameExit(TeacherCredent entity) {
//		Long count = this.dao.selectCheckNameExit(entity.getName(), entity.getId());
//		if(count>0){
//			return false;
//		}
		return true;
	}
	
	/**
	 * 新增or修改
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@Override
	@Transactional
	public String saveOrUpdate(Long userId,TeacherCredent entity, String attIds) {
		if(!checkNameExit(entity)){
			if(entity.getId()==null){
				throw new ValidateOtherException(ValidateOtherException.INSERT_FAILD,"名称已经存在，新增失败");
			}else {
				throw new ValidateOtherException(ValidateOtherException.UPDATE_FAILD,"名称已经存在，修改失败");
			}
		}
		Long entityId = null;
		String msg = "";
		if(entity.getId()==null){
			entityId  = this.insert(entity);
			msg = "添加成功！";
		}else {
			this.update(entity);
			entityId = entity.getId();
			msg = "编辑成功！";
		}
		
		/**
		 * 保存附件
		 */
		if(StringUtils.isNotBlank(attIds)){
			List<Long> attLongIds = Lists.newArrayList();
			String[] split = attIds.split(",");
			for(String attStrId:split){
				attLongIds.add(Long.parseLong(attStrId));
			}
			List<TeacherCredentAtt> tatts = Lists.newArrayList();
			for(Long aid:attLongIds){
				TeacherCredentAtt entityAtt = new TeacherCredentAtt();
				entityAtt.setPrimaryId(entityId);
				entityAtt.setAttId(aid);
				tatts.add(entityAtt);
			}
			teacherCredentAttService.insertBatch(tatts);//批量插入
		}
		
		return msg;
	}


}