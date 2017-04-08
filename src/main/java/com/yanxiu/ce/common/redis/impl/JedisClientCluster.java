package com.yanxiu.ce.common.redis.impl;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import com.google.common.collect.Sets;
import com.yanxiu.ce.common.redis.JedisClient;

/**
 * 集群版实现类
 * @author tangmin
 * @date 2016年5月30日
 */
public class JedisClientCluster implements JedisClient{
	
	private static Logger logger = LoggerFactory.getLogger(JedisClientCluster.class);
	
	@Autowired
	private JedisCluster jedisCluster;
	
	@Override
	public String get(String key) {
		return jedisCluster.get(key);
	}

	@Override
	public String set(String key, String value) {
		return jedisCluster.set(key, value);
	}

	@Override
	public String hget(String hkey, String key) {
		return jedisCluster.hget(hkey, key);
	}

	@Override
	public long hset(String hkey, String key, String value) {
		return jedisCluster.hset(hkey, key, value);
	}

	@Override
	public long incr(String key) {
		return jedisCluster.incr(key);
	}

	@Override
	public long decr(String key) {
		return jedisCluster.decr(key);
	}
	
	@Override
	public long expire(String key, int second) {
		return jedisCluster.expire(key, second);
	}

	@Override
	public long ttl(String key) {
		return jedisCluster.ttl(key);
	}
	@Override
	public long ttl(byte[] key) {
		return jedisCluster.ttl(key);
	}

	@Override
	public long del(String key) {
		return jedisCluster.del(key);
	}

	@Override
	public long hdel(String hkey, String key) {
		return jedisCluster.hdel(hkey, key);
	}

	@Override
	public String flushDB() {
		return jedisCluster.flushDB();
	}

	@Override
	public Set<String> keys(String pattern) {
		//Set<byte[]> keys = jedisCluster.keys(pattern);
		Set<String> keys = Sets.newHashSet();
		Map<String, JedisPool> nodes = jedisCluster.getClusterNodes();
		for (String node : nodes.keySet()) {
            Jedis jedis = null;
            try {
                jedis = nodes.get(node).getResource();
                keys.addAll(jedis.keys(pattern));
            } catch (Exception e) {
            	e.printStackTrace();
            	logger.warn("获取redis集群 keys方法 {} : {}失败", node, e);
            }finally{
            	jedis.close();
            }
        }
		
		return keys;
	}
	
	@Override
	public byte[] get(byte[] key) {
		byte[] b = jedisCluster.get(key);
		return b;
	}

	@Override
	public String set(byte[] key, byte[] value) {
		String set = jedisCluster.set(key,value);
		return set;
	}

	@Override
	public long expire(byte[] key, int second) {
		Long expire = jedisCluster.expire(key, second);
		return expire;
	}

	@Override
	public long del(byte[] key) {
		Long del = jedisCluster.del(key);
		return del;
	}

	@Override
	public Set<byte[]> keys(byte[] pattern) {
		//Set<byte[]> keys = jedisCluster.keys(pattern);
		Set<byte[]> keys = Sets.newHashSet();
		Map<String, JedisPool> nodes = jedisCluster.getClusterNodes();
		for (String node : nodes.keySet()) {
            Jedis jedis = null;
            try {
                jedis = nodes.get(node).getResource();
                keys.addAll(jedis.keys(pattern));
            } catch (Exception e) {
            	logger.warn("获取redis集群 keys方法 {} : {}失败", node, e);
            }finally{
            	jedis.close();
            }
        }
		
		return keys;
	}

	@Override
	public Long dbSize() {
		Long dbSize = jedisCluster.dbSize();
		return dbSize;
	}
	
	public void publish(String channel, Serializable message) {
        if (StringUtils.isBlank(channel) || message == null) {
            return;
        }
        Map<String, JedisPool> nodes = jedisCluster.getClusterNodes();
        for (String node : nodes.keySet()) {
            Jedis jedis = null;
            try {
                jedis = nodes.get(node).getResource();
                jedis.publish(channel, (String) message);
            } catch (Exception e) {
                // 任意节点损坏都会在此抛出异常
                logger.warn("发送消息到{} {} : {}失败", node, channel, message, e);
            } 
        }
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
