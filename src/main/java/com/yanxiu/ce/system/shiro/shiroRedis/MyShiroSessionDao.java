package com.yanxiu.ce.system.shiro.shiroRedis;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.ibatis.transaction.Transaction;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * 针对自定义的ShiroSession的Redis CRUD操作，通过isChanged标识符，确定是否需要调用Update方法 
 * 通过配置securityManager在属性cacheManager查找从缓存中查找Session是否存在，如果找不到才调用下面方法 
 * Shiro内部相应的组件（DefaultSecurityManager）会自动检测相应的对象（如Realm）是否实现了CacheManagerAware并自动注入相应的CacheManager。 
 */  
public class MyShiroSessionDao extends CachingSessionDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(MyShiroSessionDao.class);
	
	/**
	 * shiro-redis的session对象前缀
	 */
	private RedisManager redisManager;
	
	/**
	 * The Redis key prefix for the sessions 
	 */
	private String keyPrefix = "shiro_redis_session:";

	@Override
	public void update(Session session) throws UnknownSessionException {
		this.saveSession(session);
	}
	
	/**
	 * save session
	 * @param session
	 * @throws UnknownSessionException
	 */
	private void saveSession(Session session) throws UnknownSessionException{
		if(session == null || session.getId() == null){
			logger.error("session or session id is null");
			return;
		}
		
		byte[] key = getByteKey(session.getId());
		byte[] value = SerializeUtils.serialize(session);
		session.setTimeout(redisManager.getExpire()*1000);		
		this.redisManager.set(key, value, redisManager.getExpire());
	}

	@Override
	public void delete(Session session) {
		if(session == null || session.getId() == null){
			logger.error("session or session id is null");
			return;
		}
		redisManager.del(this.getByteKey(session.getId()));

	}

	@Override
	public Collection<Session> getActiveSessions() {
		Set<Session> sessions = new HashSet<Session>();
		
		Set<byte[]> keys = redisManager.keys(this.keyPrefix + "*");
		if(keys != null && keys.size()>0){
			for(byte[] key:keys){
				Session s = (Session)SerializeUtils.deserialize(redisManager.get(key));
				sessions.add(s);
			}
		}
		
		return sessions;
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = this.generateSessionId(session);  
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
//        if(sessionId == null){
//			logger.error("session id is null");
//			return null;
//		}
//        logger.info("shiro session id {} 被读取", sessionId);  
//		Session s = (Session)SerializeUtils.deserialize(redisManager.get(this.getByteKey(sessionId)));
//		return s;
		
		logger.debug("开始 doReadSession {} ", sessionId);
        Session session = null;
        try {
            byte[] key = this.getByteKey(sessionId);
            byte[] value = redisManager.get(key);
            if(value!=null){
            	session = (Session)SerializeUtils.deserialize(value);
            	logger.info("sessionId {} ttl {}: ", sessionId, redisManager.ttl(key));
            	
            	// 重置Redis中缓存过期时间
            	redisManager.set(key, value);
            	logger.info("sessionId {} name {} 被读取", sessionId, session.getClass().getName());
            }
        } catch (Exception e) {
            logger.warn("读取Session失败", e);
        }
        return session;
	}

	 /**
     * 重写CachingSessionDAO中readSession方法，如果Session中没有登陆信息就调用doReadSession方法从Redis中重读
     * session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null 代表没有登录，登录后Shiro会放入该值
     */
    @Override
    public Session readSession(Serializable sessionId) throws UnknownSessionException {
        Session cached = null;  
        try {  
            cached = super.getCachedSession(sessionId);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        // 如果缓存不存在或者缓存中没有登陆认证后记录的信息就重新从Redis中读取  
        if (cached == null || cached.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {  
            try {  
                cached = this.doReadSession(sessionId);  
                if (cached == null) {  
                    throw new UnknownSessionException();  
                } else {  
                    // 重置Redis中缓存过期时间并缓存起来 只有设置change才能更改最后一次访问时间  
                    ((ShiroSession) cached).setChanged(true);  
                    super.update(cached);  
                }  
            } catch (Exception e) {  
                logger.warn("There is no session with id [" + sessionId + "]");  
            }  
        }  
        return cached;  
    }
    
    
    /**
     * 读取，不重置过期时间
     * @param sessionId
     * @return
     */
    public Session doReadSessionWithoutExpire(Serializable sessionId) {
    	logger.debug("开始 doReadSession {} ", sessionId);
        Session session = null;
        try {
            byte[] key = this.getByteKey(sessionId);
            byte[] value = redisManager.get(key);
            if(value!=null){
            	session = (Session)SerializeUtils.deserialize(value);
            	logger.info("doReadSessionWithoutExpire sessionId {} ttl {}: ", sessionId, redisManager.ttl(key));
            }
        } catch (Exception e) {
            logger.warn("读取Session失败", e);
        }
        return session;
    }
    
    /**
	 * 获得byte[]型的key
	 * @param key
	 * @return
	 */
	private byte[] getByteKey(Serializable sessionId){
		String preKey = this.keyPrefix + sessionId;
		return preKey.getBytes();
	}

	public RedisManager getRedisManager() {
		return redisManager;
	}

	public void setRedisManager(RedisManager redisManager) {
		this.redisManager = redisManager;
		
		/**
		 * 初始化redisManager
		 */
		this.redisManager.init();
	}

	/**
	 * Returns the Redis session keys
	 * prefix.
	 * @return The prefix
	 */
	public String getKeyPrefix() {
		return keyPrefix;
	}

	/**
	 * Sets the Redis sessions key 
	 * prefix.
	 * @param keyPrefix The prefix
	 */
	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	@Override
	protected void doUpdate(Session session) {
		//如果会话过期/停止 没必要再更新了
        try {
            if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
                return;
            }
        } catch (Exception e) {
            logger.error("ValidatingSession error");
        }
        try {
            if (session instanceof ShiroSession) {
                // 如果没有主要字段(除lastAccessTime以外其他字段)发生改变
                ShiroSession ss = (ShiroSession) session;
                if (!ss.isChanged()) {
                    return;
                }
                
                ss.setChanged(false);
                ss.setLastAccessTime(DateTime.now().toDate());
                redisManager.set(this.getByteKey(session.getId()), SerializeUtils.serialize(ss));
                redisManager.publish("shiro.session.uncache", session.getId());
                logger.debug("sessionId {} name {} 被更新", session.getId(), session.getClass().getName());
            } else {
                logger.debug("sessionId {} name {} 更新失败", session.getId(), session.getClass().getName());
            }
        } catch (Exception e) {
            logger.warn("更新Session失败", e);
        }
		
	}

	 /**
     * 删除会话；当会话过期/会话停止（如用户退出时）会调用
     */
    @Override
    public void doDelete(Session session) {
        logger.debug("begin doDelete {} ", session);
        try {
        	redisManager.del(this.getByteKey(session.getId()));
            this.uncache(session.getId());
            logger.debug("shiro session id {} 被删除", session.getId());
        } catch (Exception e) {
            logger.warn("删除Session失败", e);
        }
    }

    /**
     * 删除cache中缓存的Session
     */
    public void uncache(Serializable sessionId) {
        try {
            Session session = super.getCachedSession(sessionId);
            super.uncache(session);
            logger.debug("shiro session id {} 的缓存失效", sessionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
