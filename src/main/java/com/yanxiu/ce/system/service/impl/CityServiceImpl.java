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
import com.yanxiu.ce.system.dao.CityDao;
import com.yanxiu.ce.system.entity.City;
import com.yanxiu.ce.system.entity.CityQuery;
import com.yanxiu.ce.system.service.CityService;

/**
 * 市管理
 * @author tangmin
 * @date 2016-03-08 17:13:48
 */
@Service("cityService")
public class CityServiceImpl extends BaseServiceImpl<City, CityQuery> implements CityService{
	@Autowired
	private CityDao dao;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${CITY_KEY}")
	private String CITY_KEY;
	
	@Value("${COMMON_EXPIRE}")
	private Integer COMMON_EXPIRE;


	@Override
	protected BaseDao<City, CityQuery> dao() {
		return this.dao;
	}
	
	/**
	 * 新增or修改
	 */
	@Override
	@Transactional
	public String saveOrUpdate(City entity) {
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
//	@Cacheable(value="com.yanxiu.ce.system.City", key="'citys'+#provinceNo")
	public List<City> citys(Integer provinceNo) {
		//得到结果之前从redis中获取数据
		try {
			//String json = jedisClient.hget(REDIS_DICT_KEY, code);
			String json = jedisClient.get(CITY_KEY+"_LIST:"+provinceNo);
			if(StringUtils.isNoneBlank(json)){
				List<City> list = JSON.parseArray(json, City.class);
				return list;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		System.out.println("====================City 查询数据库==================================");
		CityQuery query = new CityQuery();
		query.setProvinceNo(provinceNo.toString());
		query.setProvinceNoLike(false);
		query.setFields("cityNo,cityName");
		List<City> selectList = this.selectList(query);
		
		
		//返回结果之前向redis中添加数据
		try {
			jedisClient.set(CITY_KEY+"_LIST:"+provinceNo, JSON.toJSONString(selectList));
			//设置过期时间
			jedisClient.expire(CITY_KEY+"_LIST:"+provinceNo, COMMON_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return selectList;
	}

	@Override
//	@Cacheable(value="com.yanxiu.ce.system.City", key="'cityMap'+#provinceNo")
	public Map<String, Integer> cityMap(Integer provinceNo) {
		//得到结果之前从redis中获取数据
		try {
			String json = jedisClient.get(CITY_KEY+"_MAP:"+provinceNo);
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
		List<City> citys = this.citys(provinceNo);
		for(City city:citys){
			map.put(city.getCityName(), city.getCityNo());
		}
		
		
		//返回结果之前向redis中添加数据
		try {
			jedisClient.set(CITY_KEY+"_MAP:"+provinceNo, JSON.toJSONString(map));
			jedisClient.expire(CITY_KEY+"_MAP:"+provinceNo, COMMON_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
	}

	@Override
	public Long syncSelectItems(Integer provinceNo) {
		jedisClient.del(CITY_KEY+"_MAP:"+provinceNo);
		return jedisClient.del(CITY_KEY+"_LIST:"+provinceNo);
	}

	@Override
	public void removeAllCache() {
		
	    System.out.println("移除缓存中的CITY所有数据");
		
	}

	@Override
	public String getNameByNo(Integer cityNo) {
		String cityName = "";
		String result = this.dao.selectNameByNo(cityNo);
		if(StringUtils.isNotBlank(result)){
			cityName = result;
		}
		return cityName;
	}

}