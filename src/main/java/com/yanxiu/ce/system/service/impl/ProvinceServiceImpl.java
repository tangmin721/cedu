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
import com.yanxiu.ce.system.dao.ProvinceDao;
import com.yanxiu.ce.system.entity.Province;
import com.yanxiu.ce.system.entity.ProvinceQuery;
import com.yanxiu.ce.system.service.ProvinceService;

/**
 * 省管理
 * @author tangmin
 * @date 2016-03-08 17:13:15
 */
@Service("provinceService")
public class ProvinceServiceImpl extends BaseServiceImpl<Province, ProvinceQuery> implements ProvinceService{
	
	@Autowired
	private ProvinceDao dao;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${PROVINCE_KEY}")
	private String PROVINCE_KEY;
	
	@Value("${COMMON_EXPIRE}")
	private Integer COMMON_EXPIRE;
	
	@Override
	protected BaseDao<Province, ProvinceQuery> dao() {
		return this.dao;
	}
	
	
	/**
	 * 新增or修改
	 */
	@Override
	@Transactional
	public String saveOrUpdate(Province entity) {
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
//	@Cacheable(value="com.yanxiu.ce.system.Province", key="'provinces'")
	public List<Province> provinces() {
		
		//得到结果之前从redis中获取数据
		try {
			//String json = jedisClient.hget(REDIS_DICT_KEY, code);
			String json = jedisClient.get(PROVINCE_KEY+"_LIST:");
			if(StringUtils.isNoneBlank(json)){
				List<Province> list = JSON.parseArray(json, Province.class);
				return list;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		System.out.println("====================provinces 查询数据库==================================");
		ProvinceQuery query = new ProvinceQuery();
		query.setFields("provinceNo,provinceName");
		List<Province> selectList = this.selectList(query);
		
		//返回结果之前向redis中添加数据
		try {
			jedisClient.set(PROVINCE_KEY+"_LIST:", JSON.toJSONString(selectList));
			//设置过期时间
			jedisClient.expire(PROVINCE_KEY+"_LIST:", COMMON_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return selectList;
	}

	@Override
//	@Cacheable(value="com.yanxiu.ce.system.Province", key="'provinceMap'")
	public Map<String, Integer> provinceMap() {
		
		//得到结果之前从redis中获取数据
		try {
			String json = jedisClient.get(PROVINCE_KEY+"_MAP:");
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
		
		System.out.println("====================provinceMap 查询数据库==================================");
		Map<String, Integer> map = Maps.newHashMap();
		List<Province> provinces = this.provinces();
		for(Province province:provinces){
			map.put(province.getProvinceName(), province.getProvinceNo());
		}
		
		
		//返回结果之前向redis中添加数据
		try {
			jedisClient.set(PROVINCE_KEY+"_MAP:", JSON.toJSONString(map));
			jedisClient.expire(PROVINCE_KEY+"_MAP:", COMMON_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return map;
	}


	@Override
	public Long syncSelectItems() {
		jedisClient.del(PROVINCE_KEY+"_MAP:");
		return jedisClient.del(PROVINCE_KEY+"_LIST:");
	}


	@Override
	public void removeAllCache() {
		syncSelectItems();
	    System.out.println("移除缓存中的所有PROVINCE_KEY数据");
	}


	@Override
	public String getNameByNo(Integer provinceNo) {
		String name = "";
		String result = this.dao.selectNameByNo(provinceNo);
		if(StringUtils.isNotBlank(result)){
			name = result;
		}
		return name;
	}
	

}