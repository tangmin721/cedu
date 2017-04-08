package com.yanxiu.ce.common.web.aop;
import java.io.Serializable;  
import java.net.URL;  
import org.aspectj.lang.ProceedingJoinPoint;  
import net.sf.ehcache.Cache;  
import net.sf.ehcache.CacheException;  
import net.sf.ehcache.CacheManager;  
import net.sf.ehcache.Element;  
import net.sf.ehcache.ObjectExistsException;  
  
public class CacheHander {  
      
  
    private CacheManager cacheManager;  
    //缓存变量  
    private Cache cache;  
      
    private static CacheHander cacheHander=new CacheHander();  
    //缓存名称  
    private final String cacheName="DATA_METHOD_CACHE";  
    /** 
     * 私有构造方法 
     */  
    private CacheHander()  
    {  
        try {  
            //1.创建cachemanager  
            URL url=getClass().getResource("/customEHCache.xml");  
            System.out.println("encache.xml url="+url);  
              
            CacheManager cacheManager = CacheManager.create(url);  
            this.cacheManager=cacheManager;  
            cache=cacheManager.getCache(cacheName);  
              
            if(cache==null){  
                cache=new Cache("DATA_METHOD_CACHE", 10000, true, false, 600000, 300000);  
                cacheManager.addCache(cache);  
            }  
            System.out.println("cache.getSize()="+cache.getSize());  
            System.out.println("cache object="+cache);  
              
        } catch (CacheException e) {  
            e.printStackTrace();  
        }  
    }  
    /** 
     * 获取缓存类 
     * @returns 
     */  
    public static CacheHander getCacheHander()  
    {  
        if (cacheHander==null) {  
            cacheHander=new CacheHander();  
        }  
        return cacheHander;  
    }  
      
      
    public Object putResultToCache(ProceedingJoinPoint pjp) throws Throwable  
    {  
  
            //原实体类名（包括包名）  
            String className=pjp.getTarget().getClass().getName();  
            //原方法名  
            String methodName=pjp.getSignature().getName();  
            //原方法实参列表  
            Object[] arguments=pjp.getArgs();  
              
            if (methodName.startsWith("get"))   
            {  
                String cacheKey=getCacheKey(className,methodName,arguments);  
                Element element=cache.get(cacheKey);  
                if (element==null) {  
                    // 执行目标方法，并保存目标方法执行后的返回值  
                    Object resuObject=pjp.proceed();   
                    element=new Element(cacheKey, (Serializable)resuObject);  
                    cache.put(element);  
                    System.out.println("将查询结果放到缓存里面,缓存key="+cacheKey);  
                }else {  
                    System.out.println("已经存在从缓存中取出来="+cacheKey);  
                }  
                return element.getValue();  
            }  
            return  pjp.proceed();  
    }  
  
    /** 
     * @MethodName  : getCacheKey 
     * @Description : 获得cache key的方法，cache key是Cache中一个Element的唯一标识 cache key包括 
     * 包名+类名+方法名+各个参数的具体指，如com.co.cache.service.UserServiceImpl.getAllUser 
     * @param targetName    类名 
     * @param methodName    方法名 
     * @param arguments     方法实参数组 
     * @return                      cachekey 
     */  
    private String getCacheKey(String targetName, String methodName,  
            Object[] arguments) {  
        StringBuffer sb = new StringBuffer();  
        sb.append(targetName).append(".").append(methodName);  
        if ((arguments != null) && (arguments.length != 0)) {  
            for (int i = 0; i < arguments.length; i++) {  
                if(arguments[i] instanceof String[]){  
                    String[] strArray = (String[])arguments[i];  
                    sb.append(".");  
                    for(String str : strArray){  
                        sb.append(str);  
                    }  
                }else{  
                    sb.append(".").append(arguments[i]);  
                }  
            }  
        }  
        return sb.toString();  
    }  
    public Cache addCache(String cacheName) throws IllegalStateException, ObjectExistsException, CacheException  
    {  
        Cache cache=cacheManager.getCache(cacheName);  
        if (cache==null) {  
            cache=new Cache(cacheName,10000, true, false, 1000,100);  
            cacheManager.addCache(cache);  
        }  
        return cache;  
    }  
    public Cache getCache() {  
        return cache;  
    }  
    public void setCache(Cache cache) {  
        this.cache = cache;  
    }  
    public static void setCacheHander(CacheHander cacheHander) {  
        CacheHander.cacheHander = cacheHander;  
    }  
      
}  