package com.yanxiu.ce.test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.alibaba.fastjson.JSON;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.redis.JedisClient;
import com.yanxiu.ce.system.service.DictCatlogService;
import com.yanxiu.ce.test.junit.SpringJunitTest;

/**
 * redis测试
 * @author tangmin
 * @date 2016年5月30日
 */
public class RedisTest extends SpringJunitTest {
	
	@Autowired
	private JedisPool jedisPool;
	
	@Autowired
	private JedisCluster jedisCluster;
	
	@Autowired
	private DictCatlogService dictCatlogService;
	
	/**
	 * 自定义的通用接口
	 */
	@Autowired
	private JedisClient jedisClient;
	
	//单例测试
	@Test
	public void testJedisSingle() { 

		Jedis jedis = new Jedis("192.168.3.101", 6379);
		jedis.set("name", "bar-singel");
		String name = jedis.get("name");
		System.out.println(name);
		jedis.close();
	}

	/**
	 * 连接池测试
	 */
	@Test
	public void pool() {
		JedisPoolConfig config = new JedisPoolConfig();
		//最大连接数
		config.setMaxTotal(30);
		//最大连接空闲数
		config.setMaxIdle(2);
		
		JedisPool pool = new JedisPool(config, "192.168.3.101", 6379);
		Jedis jedis = null;

		try  {
			jedis = pool.getResource();
			
			jedis.set("name", "lisi-pool~~");
			String name = jedis.get("name");
			System.out.println(name);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(jedis != null){ 
				//关闭连接
				jedis.close();
			}
			if(pool != null){
				//关闭连接
				pool.close();
			}
		}
	}
	
	/**
	 * 连接池spring注入测试
	 */
	@Test
	public void poolSpring() {
		
		Jedis jedis = null;

		try  {
			jedis = jedisPool.getResource();
			
			jedis.set("name", "lisi334-poolSpring");
			String name = jedis.get("name");
			System.out.println(name);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(jedis != null){
				//关闭连接
				jedis.close();
			}
		}
	}
	
	/**
	 * 集群版
	 * @throws IOException 
	 */
	@Test
	public void cluster() throws IOException {
		
		HashSet<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.3.101", 7001));
		nodes.add(new HostAndPort("192.168.3.101", 7002));
		nodes.add(new HostAndPort("192.168.3.101", 7003));
		nodes.add(new HostAndPort("192.168.3.101", 7004));
		nodes.add(new HostAndPort("192.168.3.101", 7005));
		nodes.add(new HostAndPort("192.168.3.101", 7006));
		
		JedisCluster cluster = new JedisCluster(nodes);

		cluster.set("name", "bar-cluster");
		String name = cluster.get("name");
		System.out.println(name);
		cluster.close();//正常使用的时候，不要关闭
	}
	
	/**
	 * 集群版注入
	 * @throws IOException
	 */
	@Test
	public void clusterSpring() throws IOException {
		jedisCluster.set("name", "bar-cluster-spring-xxx123");
		String name = jedisCluster.get("name");
		System.out.println(name);
		jedisCluster.close();
	}
	
	/**
	 * 通用接口(使用的时候放开一个)
	 * @throws IOException
	 */
	@Test
	public void clientSpring() throws IOException {
		jedisClient.set("name", "bar-client-spring-xxx123");
		String name = jedisClient.get("name");
		System.out.println(name);
	}
	
	/**
	 * 测试字典redis缓存
	 */
	@Test
	public void testDict(){
		dictCatlogService.getSelectItems("TRAIN_TYPE");
	}
	 
	/**
	 * 同步缓存
	 */
	@Test
	public void testSyncDict(){
	//	dictCatlogService.syncSelectItems("SCHOOL_TYPE");
	}
	
	@Test
	public void testMappingJacksonValue(){
		dictCatlogService.getSelectItems("TRAIN_TYPE");
		MappingJacksonValue mjv = new MappingJacksonValue(AjaxCallback.OK("chengg!"));
		mjv.setJsonpFunction("functionname");
		System.out.println(JSON.toJSONString(mjv));
	}
	
	/**
	 * 清空
	 */
	@Test
	public void flushDB(){
		String flushDB = jedisClient.flushDB();
		System.out.println(flushDB);
	}
	
	
	@Test
	public void testKeys(){
		Set<String> keys = jedisClient.keys("sin*");
		System.out.println(keys);
		
	}
	
	@Test
	public void testKeysDel(){
		jedisClient.delKeys("sin*");
	}
}
