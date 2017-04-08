package com.yanxiu.ce.system.shiro.shiroRedis;


import java.io.Serializable;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yanxiu.ce.common.redis.JedisClient;

/**
 * 注入自己封装的jedisClient
 * @author tangmin
 * @date 2016年6月13日
 */
public class RedisManager {
	
	private static Logger logger = LoggerFactory.getLogger(RedisManager.class);

	// 0 - never expire
	private int expire = 0;
	
	private JedisClient jedisClient;
	
	public RedisManager(JedisClient jedisClient){
		this.jedisClient = jedisClient;
	}
	
	/**
	 * 初始化方法
	 */
	public void init(){
	}
	
	/**
	 * get value from redis
	 * @param key
	 * @return
	 */
	public byte[] get(byte[] key){
		//logger.debug("RedisManager get byte[] key {}",key.toString());
		byte[] value = null;
		value = jedisClient.get(key);
		return value;
	}
	
	/**
	 * set 
	 * @param key
	 * @param value
	 * @return
	 */
	public byte[] set(byte[] key,byte[] value){
		jedisClient.set(key,value);
		if(this.expire != 0){
			jedisClient.expire(key, this.expire);
	 	}
		return value;
	}
	
	/**
	 * set 
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 */
	public byte[] set(byte[] key,byte[] value,int expire){
		if (value == null){
			return value;
		}
		jedisClient.set(key,value);
		if(expire != 0){
			jedisClient.expire(key, expire);
	 	}
		return value;
	}
	
	/**
	 * del
	 * @param key
	 */
	public void del(byte[] key){
		jedisClient.del(key);
	}
	
	/**
	 * flush
	 */
	public void flushDB(){
		jedisClient.flushDB();
	}
	
	/**
	 * size
	 */
	public Long dbSize(){
		Long dbSize = 0L;
		dbSize = jedisClient.dbSize();
		return dbSize;
	}

	/**
	 * keys
	 * @param regex
	 * @return
	 */
	public Set<byte[]> keys(String pattern){
		Set<byte[]> keys = null;
		keys = jedisClient.keys(pattern.getBytes());
		return keys;
	}
	
	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}
	
	/**
	 * 过期时间
	 * @param key
	 * @return
	 */
	public Long ttl(String key){
		return jedisClient.ttl(key);
	}
	public Long ttl(byte[] key){
		return jedisClient.ttl(key);
	}
	
	/**
	 * 发布消息
	 */
	void publish(String channel, Serializable message){
		jedisClient.publish(channel, message);
	}
}

