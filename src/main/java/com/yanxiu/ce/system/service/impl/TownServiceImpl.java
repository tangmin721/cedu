package com.yanxiu.ce.system.service.impl;

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
import com.yanxiu.ce.common.redis.JedisClient;
import com.yanxiu.ce.system.dao.TownDao;
import com.yanxiu.ce.system.entity.Town;
import com.yanxiu.ce.system.entity.TownQuery;
import com.yanxiu.ce.system.service.TownService;

/**
 * 区/县管理
 * @author tangmin
 * @date 2016-03-08 17:14:40
 */
@Service("townService")
public class TownServiceImpl extends BaseServiceImpl<Town, TownQuery> implements TownService{
	@Autowired
	private TownDao dao;

	@Autowired
	private JedisClient jedisClient;
	
	@Value("${TOWN_KEY}")
	private String TOWN_KEY;
	
	@Value("${COMMON_EXPIRE}")
	private Integer COMMON_EXPIRE;
	
	@Override
	protected BaseDao<Town, TownQuery> dao() {
		return this.dao;
	}
	
	/**
	 * 新增or修改
	 */
	@Override
	@Transactional
	public String saveOrUpdate(Town entity) {
		String msg = "";
		if(entity.getId()==null){
			this.insert(entity);
			msg = "添加成功！";
		}else {
			this.update(entity);
				msg = "编辑成功！";
		}
		return msg;
	}

	@Override
	//@Cacheable(value="com.yanxiu.ce.system.Town", key="'towns'+#cityNo")
	public List<Town> towns(Integer cityNo) {
		//得到结果之前从redis中获取数据
		try {
			//String json = jedisClient.hget(REDIS_DICT_KEY, code);
			String json = jedisClient.get(TOWN_KEY+"_LIST:"+cityNo);
			if(StringUtils.isNoneBlank(json)){
				List<Town> list = JSON.parseArray(json, Town.class);
				return list;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		System.out.println("====================Town 查询数据库==================================");
		TownQuery query = new TownQuery();
		query.setCityNo(cityNo.toString());
		query.setCityNoLike(false);
		query.setFields("townNo,townName");
		List<Town> selectList = this.selectList(query);
		
		//返回结果之前向redis中添加数据
		try {
			jedisClient.set(TOWN_KEY+"_LIST:"+cityNo, JSON.toJSONString(selectList));
			//设置过期时间
			jedisClient.expire(TOWN_KEY+"_LIST:"+cityNo, COMMON_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return selectList;
	}

	@Override
	//@Cacheable(value="com.yanxiu.ce.system.Town", key="'townMap'+#cityNo")
	public Map<String, Integer> townMap(Integer cityNo) {
		
		//得到结果之前从redis中获取数据
		try {
			String json = jedisClient.get(TOWN_KEY+"_MAP:"+cityNo);
			if(StringUtils.isNoneBlank(json)){
				Map parseObject = JSON.parseObject(json);
				Map<String, Integer> map = Maps.newHashMap();
				for (Object o : parseObject.entrySet()) { 
				      @SuppressWarnings("unchecked")
				      Map.Entry<String,Integer> entry = (Map.Entry<String,Integer>)o; 
				      map.put(entry.getKey(), entry.getValue());
				    } 
				return map;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		Map<String, Integer> map = Maps.newHashMap();
		List<Town> towns = this.towns(cityNo);
		for(Town town:towns){
			map.put(town.getTownName().replaceAll("　", ""), town.getTownNo());
		}
		
		
		//返回结果之前向redis中添加数据
		try {
			jedisClient.set(TOWN_KEY+"_MAP:"+cityNo, JSON.toJSONString(map));
			jedisClient.expire(TOWN_KEY+"_MAP:"+cityNo, COMMON_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}

	@Override
	public Long syncSelectItems(Integer cityNo) {
		jedisClient.del(TOWN_KEY+"_MAP:"+cityNo);
		return jedisClient.del(TOWN_KEY+"_LIST:"+cityNo);
	}

	@Override
	public void removeAllCache() {
		// TODO Auto-generated method stub
	}

	@Override
	public String getNameByNo(Integer townNo) {
		String name = "";
		String result = this.dao.selectNameByNo(townNo);
		if(StringUtils.isNotBlank(result)){
			name = result;
		}
		return name;
	}
}