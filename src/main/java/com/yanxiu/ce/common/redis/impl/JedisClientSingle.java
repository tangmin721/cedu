package com.yanxiu.ce.common.redis.impl;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.yanxiu.ce.common.redis.JedisClient;

/**
 * 单机版实现类
 * @author tangmin
 * @date 2016年5月30日
 */
public class JedisClientSingle implements JedisClient{
	
	@Autowired
	private JedisPool jedisPool; 
	
	@Override
	public String get(String key) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.get(key);
		jedis.close();
		return string;
	}

	@Override
	public String set(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.set(key, value);
		jedis.close();
		return string;
	}

	@Override
	public String hget(String hkey, String key) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.hget(hkey, key);
		jedis.close();
		return string;
	}

	@Override
	public long hset(String hkey, String key, String value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hset(hkey, key, value);
		jedis.close();
		return result;
	}

	@Override
	public long incr(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.incr(key);
		jedis.close();
		return result;
	}
	
	@Override
	public long decr(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.decr(key);
		jedis.close();
		return result;
	}

	@Override
	public long expire(String key, int second) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.expire(key, second);
		jedis.close();
		return result;
	}

	@Override
	public long ttl(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.ttl(key);
		jedis.close();
		return result;
	}
	
	@Override
	public long ttl(byte[] key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.ttl(key);
		jedis.close();
		return result;
	}
	

	@Override
	public long del(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.del(key);
		jedis.close();
		return result;
	}

	@Override
	public long hdel(String hkey, String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hdel(hkey, key);
		jedis.close();
		return result;
	}

	@Override
	public String flushDB() {
		Jedis jedis = jedisPool.getResource();
		String flushDB = jedis.flushDB();
		jedis.close();
		return flushDB;
	}

	@Override
	public Set<String> keys(String pattern) {
		Jedis jedis = jedisPool.getResource();
		Set<String> keys = jedis.keys(pattern);
		jedis.close();
		return keys;
	}

	@Override
	public byte[] get(byte[] key) {
		Jedis jedis = jedisPool.getResource();
		byte[] b = jedis.get(key);
		jedis.close();
		return b;
	}

	@Override
	public String set(byte[] key, byte[] value) {
		Jedis jedis = jedisPool.getResource();
		String set = jedis.set(key,value);
		jedis.close();
		return set;
	}

	@Override
	public long expire(byte[] key, int second) {
		Jedis jedis = jedisPool.getResource();
		Long expire = jedis.expire(key, second);
		jedis.close();
		return expire;
	}

	@Override
	public long del(byte[] key) {
		Jedis jedis = jedisPool.getResource();
		Long del = jedis.del(key);
		jedis.close();
		return del;
	}

	@Override
	public Set<byte[]> keys(byte[] pattern) {
		Jedis jedis = jedisPool.getResource();
		Set<byte[]> keys = jedis.keys(pattern);
		jedis.close();
		
		return keys;
	}


	@Override
	public Long dbSize() {
		Jedis jedis = jedisPool.getResource();
		Long dbSize = jedis.dbSize();
		return dbSize;
	}
	
	@Override
	public void publish(String channel, Serializable message) {
        if (StringUtils.isBlank(channel) || message == null) {
            return;
        }
        Jedis jedis = jedisPool.getResource();
        jedis.publish(channel, (String) message);
        jedis.close();
    }

	
	@Override
	public void delKeys(String pattern) {
		Set<String> keys = this.keys(pattern);
		for(String key:keys){
			this.del(key);
		}
		
	}

	@Override
	public void delKeys(byte[] pattern) {
		Set<byte[]> keys = this.keys(pattern);
		for(byte[] key:keys){
			this.del(key);
		}
	}
}