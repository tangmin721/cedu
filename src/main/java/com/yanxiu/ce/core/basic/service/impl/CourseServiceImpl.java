package com.yanxiu.ce.core.basic.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.exception.ValidateOtherException;
import com.yanxiu.ce.common.redis.JedisClient;
import com.yanxiu.ce.core.basic.dao.CourseDao;
import com.yanxiu.ce.core.basic.entity.Course;
import com.yanxiu.ce.core.basic.entity.CourseQuery;
import com.yanxiu.ce.core.basic.entity.School;
import com.yanxiu.ce.core.basic.service.CourseService;

/**
 * 学科管理
 * @author tangmin
 * @date 2016-04-01 12:06:26
 */
@Service("courseService")
public class CourseServiceImpl extends BaseServiceImpl<Course, CourseQuery> implements CourseService{
	@Autowired
	private CourseDao dao;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${COURSE_KEY}")
	private String COURSE_KEY;
	
	@Value("${COMMON_EXPIRE}")
	private Integer COMMON_EXPIRE;


	@Override
	protected BaseDao<Course, CourseQuery> dao() {
		return this.dao;
	}
	
	@Override
	public Long deleteById(Long id) {
		Long deleteById = this.dao().deleteById(id);
		//同步缓存
		try {
			Course entity = this.selectById(id);
			syncRedisCourseMap(entity.getStageId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return deleteById;
	}

	@Override
	public Long deleteByIds(List<Long> ids) {
		Long deleteByIds = this.dao().deleteByIds(ids);
		//同步缓存
		try {
			for(Long id:ids){
				Course entity = this.selectById(id);
				syncRedisCourseMap(entity.getStageId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return deleteByIds;
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
	public Boolean checkNameExit(Course entity) {
		Long count = this.dao.selectCheckNameExit(entity.getName(),entity.getStageId(), entity.getId());
		if(count>0){
			return false;
		}
		return true;
	}
	
	/**
	 * 新增or修改
	 */
	@Override
	@Transactional
	public String saveOrUpdate(Course entity) {
		if(!checkNameExit(entity)){
			if(entity.getId()==null){
				throw new ValidateOtherException(ValidateOtherException.INSERT_FAILD,"名称已经存在，新增失败");
			}else {
				throw new ValidateOtherException(ValidateOtherException.UPDATE_FAILD,"名称已经存在，修改失败");
			}
		}
		String msg = "";
		if(entity.getId()==null){
			this.insert(entity);
			msg = "添加成功！";
		}else {
			this.update(entity);
				msg = "编辑成功！";
		}
		
		//同步缓存
		try {
			syncRedisCourseMap(entity.getStageId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return msg;
	}

	/**
	 * 根据deleteId删除
	 */
	@Override
	@Transactional
	public Long deleteByStageId(Long stageId) {
		
		//同步缓存
		try {
			syncRedisCourseMap(stageId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return this.dao.deleteByStageId(stageId);
	}

	@Override
	public Map<String, Long> courseMap(Long stageId) {
		
		//得到结果之前从redis中获取数据
		try {
			String json = jedisClient.get(COURSE_KEY+"_MAP:"+stageId);
			if(StringUtils.isNoneBlank(json)){
				Map parseObject = JSON.parseObject(json);
				Map<String, Long> map = Maps.newHashMap();
				for (Object o : parseObject.entrySet()) { 
				      @SuppressWarnings("unchecked")
				      Map.Entry<String,Integer> entry = (Map.Entry<String,Integer>)o; 
				      map.put(entry.getKey(), Long.parseLong(entry.getValue().toString()));
				    } 
				return map;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		Map<String, Long> map = Maps.newHashMap();
		CourseQuery query = new CourseQuery();
		query.setFields("id,name");
		query.setStageId(stageId.toString());
		query.setStageIdLike(false);
		List<Course> courses = this.selectList(query );
		for(Course course:courses){
			map.put(course.getName().replaceAll("　", ""), course.getId());
		}
		
		

		//返回结果之前向redis中添加数据
		try {
			jedisClient.set(COURSE_KEY+"_MAP:"+stageId, JSON.toJSONString(map));
			jedisClient.expire(COURSE_KEY+"_MAP:"+stageId, COMMON_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
		
	}

	@Override
	public Long syncRedisCourseMap(Long stageId) {
		return jedisClient.del(COURSE_KEY+"_MAP:"+stageId);
	}
}