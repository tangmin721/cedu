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
import com.yanxiu.ce.core.basic.dao.StageDao;
import com.yanxiu.ce.core.basic.entity.Grade;
import com.yanxiu.ce.core.basic.entity.Stage;
import com.yanxiu.ce.core.basic.entity.StageQuery;
import com.yanxiu.ce.core.basic.service.CourseService;
import com.yanxiu.ce.core.basic.service.GradeService;
import com.yanxiu.ce.core.basic.service.StageService;

/**
 * 学段管理
 * @author tangmin
 * @date 2016-04-01 11:06:21
 */
@Service("stageService")
public class StageServiceImpl extends BaseServiceImpl<Stage, StageQuery> implements StageService{
	@Autowired
	private StageDao dao;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private GradeService gradeService;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${STAGE_KEY}")
	private String STAGE_KEY;
	
	@Value("${COMMON_EXPIRE}")
	private Integer COMMON_EXPIRE;

	@Override
	protected BaseDao<Stage, StageQuery> dao() {
		return this.dao;
	}
	
	@Override
	public Long deleteById(Long id) {
		Long deleteById = this.dao().deleteById(id);
		//同步缓存
		try {
			syncRedisStageMap();
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
			syncRedisStageMap();
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
	public Boolean checkNameExit(Stage entity) {
		Long count = this.dao.selectCheckNameExit(entity.getName(), entity.getId());
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
	public String saveOrUpdate(Stage entity) {
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
			syncRedisStageMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return msg;
	}

	/**
	 * 学段下拉框
	 */
	@Override
	public List<Stage> selectStages() {
		StageQuery query = new StageQuery();
		query.setFields("id,name");
		return this.selectList(query);
	}

	
	/**
	 * 根据ids 彻底删除  删除同时删除课程和年级的
	 * @param id
	 * @return  返回删除的条数
	 */
	@Override
	@Transactional
	public Long deleteByIdsAndCg(List<Long> ids) {
		//先删除关联的课程  年级的
		for(Long stageId:ids){
			courseService.deleteByStageId(stageId);
			gradeService.deleteByStageId(stageId);
		}
		//同步缓存
		try {
			syncRedisStageMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//再删除自己本身的
		return this.deleteByIds(ids);
	}

	@Override
	public Map<String, Long> stageMap() {
		
		//得到结果之前从redis中获取数据
		try {
			String json = jedisClient.get(STAGE_KEY+"_MAP:");
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
		List<Stage> stages = this.selectStages();
		for(Stage stage:stages){
			map.put(stage.getName().replaceAll("　", ""), stage.getId());
		}
		

		//返回结果之前向redis中添加数据
		try {
			jedisClient.set(STAGE_KEY+"_MAP:", JSON.toJSONString(map));
			jedisClient.expire(STAGE_KEY+"_MAP:", COMMON_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}

	@Override
	public Long syncRedisStageMap() {
		return jedisClient.del(STAGE_KEY+"_MAP:");
	}

}