package com.yanxiu.ce.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.exception.ValidateOtherException;
import com.yanxiu.ce.common.redis.JedisClient;
import com.yanxiu.ce.system.dao.DictCatlogDao;
import com.yanxiu.ce.system.entity.DictCatlog;
import com.yanxiu.ce.system.entity.DictCatlogQuery;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.entity.DictItemQuery;
import com.yanxiu.ce.system.service.DictCatlogService;
import com.yanxiu.ce.system.service.DictItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("dictCatlogService")
public class DictCatlogServiceImpl extends BaseServiceImpl<DictCatlog, DictCatlogQuery> implements DictCatlogService{
	@Autowired
	private DictCatlogDao dao;
	
	@Autowired
	private DictItemService dictItemService;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${REDIS_DICT_KEY}")
	private String REDIS_DICT_KEY;
	
	@Value("${REDIS_DICT_EXPIRE}")
	private Integer REDIS_DICT_EXPIRE;

	@Override
	protected BaseDao<DictCatlog, DictCatlogQuery> dao() {
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
	public Boolean checkModifyCodeExit(DictCatlog entity) {
		Long count = this.dao.selectCheckCodeExit(entity.getCode(), entity.getId());
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
	//@CacheEvict(value="com.yanxiu.ce.system.entity.Dict", allEntries=true)
	public String saveOrUpdate(DictCatlog entity) {
		if(!checkModifyCodeExit(entity)){
			if(entity.getId()==null){
				throw new ValidateOtherException(ValidateOtherException.INSERT_FAILD,"编码已经存在，新增失败");
			}else {
				throw new ValidateOtherException(ValidateOtherException.UPDATE_FAILD,"编码已经存在，修改失败");
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
			syncSelectItems(entity.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return msg;
	}
	
	/**
	 * 批量删除catlog，同时删除其下的item
	 */
	@Override
	@Transactional
	//@CacheEvict(value="com.yanxiu.ce.system.entity.Dict", allEntries=true)
	public String deleteCatlogByIds(List<Long> ids){
		
		if(ids.size()==0){
			return "没有选择目录，删除失败";
		}
		//先删除items
		for(Long catlogId:ids){
			
			//同步缓存
			try {
				syncSelectItems(this.selectById(catlogId).getCode());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			List<Long> itemIds = dictItemService.selectItemIdsByCatlogId(catlogId);
			if(itemIds.size()>0){
				dictItemService.deleteByIds(itemIds);
			}
		}
		//再删除catlog
		this.dao.deleteByIds(ids);
		
		return "删除成功";
	}

	/**
	 * encache缓存下拉框
	 */
//	@Override
//	@Cacheable(value="com.yanxiu.ce.system.entity.Dict", key="'getSelectItems'+#code")
//	public List<DictItem> getSelectItems(String code) {
//		System.out.println("====================Dict 查询数据库==================================");
//		return this.dao.getSelectItems(code);
//	}
	
	/**
	 * redis缓存
	 * @param code
	 * @return
	 */
	@Override
	public List<DictItem> getSelectItems(String code) {
		
		//得到结果之前从redis中获取数据
		try {
			String json = jedisClient.get(REDIS_DICT_KEY+":"+code);
			if(StringUtils.isNoneBlank(json)){
				List<DictItem> items = JSON.parseArray(json, DictItem.class);
				return items;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		System.out.println("====================Dict 查询数据库==================================");
		List<DictItem> selectItems = this.dao.getSelectItems(code);
		
		//返回结果之前向redis中添加数据
		try {
			jedisClient.set(REDIS_DICT_KEY+":"+code, JSON.toJSONString(selectItems));
			jedisClient.expire(REDIS_DICT_KEY+":"+code, REDIS_DICT_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return selectItems;
	}

	/**
	 * redis缓存
	 * @param code
	 * @return
	 */
	@Override
	public List<DictItem> getSelectTreeItems(String code) {

		//得到结果之前从redis中获取数据
		try {
			String json = jedisClient.get(REDIS_DICT_KEY + ":" + code);
			if (StringUtils.isNoneBlank(json)) {
				List<DictItem> items = JSON.parseArray(json, DictItem.class);
				return items;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		System.out.println("====================Dict 查询数据库==================================");
		DictCatlog dictCatlog = dao.selectByCode(code);
		List<DictItem> selectItems = this.dao.getSelectItems(code);
		//如果不能选中父节点
		if (!dictCatlog.getCanParent()) {
			for (DictItem item : selectItems) {
				DictItemQuery dictItemQuery = new DictItemQuery();
				dictItemQuery.setParentId(item.getId().toString());
				List<DictItem> dictItems = dictItemService.selectList(dictItemQuery);
				if (dictItems.size()>0) {
					item.setNocheck(true);
				}
			}
		}


		//返回结果之前向redis中添加数据
		try {
			jedisClient.set(REDIS_DICT_KEY+":"+code, JSON.toJSONString(selectItems));
			jedisClient.expire(REDIS_DICT_KEY+":"+code, REDIS_DICT_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return selectItems;
	}
	
	/**
     * redis同步下拉框缓存（其实就是删除缓存），在增删改的时候，在controller里调用用httpclient，
     * 这样可以不用在一个事务发送因为redis错误而回滚
     */
	@Override
	public Long syncSelectItems(String code) {
		jedisClient.del(REDIS_DICT_KEY+"_MAP:"+code);
		return jedisClient.del(REDIS_DICT_KEY+":"+code);
	}
	
	/**
	 * 导入比对下拉框map
	 */
	@Override
	//@Cacheable(value="com.yanxiu.ce.system.entity.Dict", key="'getSelectItemsMap'+#code")
	public Map<String, Long> getSelectItemsMap(String code) {
		
		//得到结果之前从redis中获取数据
		try {
			String json = jedisClient.get(REDIS_DICT_KEY+"_MAP:"+code);
			if(StringUtils.isNoneBlank(json)){
				Map parseObject = JSON.parseObject(json);
				Map<String, Long> map = Maps.newHashMap();
				for (Object o : parseObject.entrySet()) { 
				      @SuppressWarnings("unchecked")
				      Map.Entry<String,Integer> entry = (Map.Entry<String,Integer>)o; 
//				      System.out.println(entry.getKey()+"--->"+entry.getValue()); 
				      map.put(entry.getKey(), Long.parseLong(entry.getValue().toString()));
				    } 
				return map;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		System.out.println("====================Dict Map查询数据库==================================");
		Map<String, Long> map = Maps.newHashMap();
		List<DictItem> items = this.getSelectItems(code);
		for(DictItem item:items){
			map.put(item.getName(), item.getId());
		}
		
		//返回结果之前向redis中添加数据
		try {
			jedisClient.set(REDIS_DICT_KEY+"_MAP:"+code, JSON.toJSONString(map));
			jedisClient.expire(REDIS_DICT_KEY+"_MAP:"+code, REDIS_DICT_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return map;
	}
	
	/**
	 * 清除缓存
	 */
	@Override
	//@CacheEvict(value="com.yanxiu.ce.system.entity.Dict", allEntries=true)
	public void removeAllCache(){
		
		DictCatlogQuery query = new DictCatlogQuery();
		query.setFields("code");
		List<DictCatlog> selectList = this.selectList(query );
		
		for(DictCatlog catlog:selectList){
			this.syncSelectItems(catlog.getCode());
		}
		
	    System.out.println("移除缓存中的所有数据");
	}


	
	

}