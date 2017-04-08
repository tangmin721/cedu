package com.yanxiu.ce.common.redis;

import java.io.Serializable;
import java.util.Set;

/**
 * 为单机版，集群版做一个通用的接口
 * @author tangmin
 * @date 2016年5月30日
 */
public interface JedisClient {
	
	/**
	 * 根据key获取值
	 * @param key
	 * @return
	 */
	String get(String key);
	byte[] get(byte[] key);
	
	/**
	 * 设置key，value
	 * @param key
	 * @param value
	 * @return
	 */
	String set(String key, String value);
	String set(byte[] key, byte[] value);
	
	/**
	 * hget获取（两层key）值
	 * @param hkey
	 * @param key
	 * @return
	 */
	String hget(String hkey, String key);
	
	/**
	 * hset 设置（两层key）值
	 * @param hkey
	 * @param key
	 * @param value
	 * @return
	 */
	long hset(String hkey, String key, String value);
	/**
	 * 增加1
	 * @param key
	 * @return
	 */
	long incr(String key);
	
	/**
	 * 减1
	 * @param key
	 * @return
	 */
	long decr(String key);
	
	/**
	 * 过期
	 * @param key
	 * @param second
	 * @return
	 */
	long expire(String key, int second);
	long expire(byte[] key, int second);
	
	/**
	 * 如果等于-2 则为过期了
	 * @param key    
	 * @return
	 */
	long ttl(String key);
	long ttl(byte[] key);
	
	/**
	 * 删除key
	 * @param key
	 * @return
	 */
	long del(String key);
	long del(byte[] key);
	/**
	 * hash 删除hset设置的key
	 * @param hkey
	 * @param key
	 * @return
	 */
	long hdel(String hkey, String key);

	/**
	 * 清空当前db
	 * @return
	 */
	String flushDB();
	
	/**
	 * 获取 匹配的所有key
	 * @param pattern
	 * @return
	 */
	Set<String> keys(String pattern);
	Set<byte[]> keys(byte[] pattern);
	
	/**
	 * 根据匹配删除
	 * @param pattern
	 */
	void delKeys(String pattern);
	void delKeys(byte[] pattern);

	/**
	 * dbSize()
	 */
	Long dbSize();
	
	/**
	 * 发布消息
	 * @param channel
	 * @param message
	 */
	void publish(String channel, Serializable message);
	
}
