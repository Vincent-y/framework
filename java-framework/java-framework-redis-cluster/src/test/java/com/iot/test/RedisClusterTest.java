package com.iot.test;

import com.java.redis.cache.CacheManager;
import com.java.redis.cache.SessionCache;
import com.java.redis.util.SerializingUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * <p>測試
 * <p>@author @DRAGON-Yeah
 * <p>@date 2015年6月25日
 * <p>@version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={ "classpath:spring/spring-context.xml"})
@ActiveProfiles(profiles={"UAT"})
public class RedisClusterTest {

    //日志
    private final static Logger logger = LoggerFactory.getLogger(RedisClusterTest.class);


    private SessionCache sessionCache;


    @Before
    public void Before(){
        sessionCache = CacheManager.getSessionCache();
    }

    @Test
    public void testInc(){
        sessionCache.setStr("user","6",200);
        int total = 10;
        long startTime = System.currentTimeMillis();
        for(int i = 0 ; i < total; i++){
            long sTime = System.currentTimeMillis();
            Long id = sessionCache.incrKey("user");
            logger.warn("-----------------id:{}",id);
            long eTime = System.currentTimeMillis();
            logger.debug("第:{}条耗时:{}ms",(i+1),(eTime-sTime));
        }
        long endTime = System.currentTimeMillis();
        logger.debug("总耗时:{}ms,平均耗时:{}ms",(endTime-startTime),(endTime-startTime)/total);
    }

    @Test
    public void testRtestRemoveemove(){
        long sTime = System.currentTimeMillis();
        sessionCache = CacheManager.getSessionCache();
        boolean result = sessionCache.removeKey("user");
        logger.warn("-----------------result:{}",result);
        long eTime = System.currentTimeMillis();
        logger.debug("threadId:{},耗时:{}ms",Thread.currentThread().getId(),(eTime-sTime));
    }


    @Test
    public void pressTestInc() throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        int threadNum = 1000;
        List<Future<Long>> results = new ArrayList<Future<Long>>();
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        int total = 100;
        for(int i = 0 ; i < total; i++){
            results.add(executorService.submit(new Task(i)));
        }
        long totalTime = 0L;
        for(Future<Long> result : results){
            totalTime = totalTime + result.get();
        }
        logger.debug("{}并发",threadNum);
        logger.debug("{}数据插入,线程平均执行耗时:{}ms,每条数据插入平均耗时:{}ms",total,totalTime/threadNum,(float)totalTime/(float)(threadNum*total));
        long endTime = System.currentTimeMillis();
        logger.debug("{}数据插入,总耗时:{}ms,平均耗时:{}ms",total,(endTime-startTime),(float)(endTime-startTime)/(float)total);
    }


    private class Task implements Callable<Long> {
        private int num = 0;
        public Task(int num) {
            this.num = num;
        }
        @Override
        public Long call() {
            long sTime = System.currentTimeMillis();
            Long id = sessionCache.incrKey("user");
            logger.warn("-----------------id:{}",id);
            long eTime = System.currentTimeMillis();
            logger.debug("threadId:{},第:{}条耗时:{}ms",Thread.currentThread().getId(),(num+1),(eTime-sTime));
            return (eTime-sTime);
        }
    }


    @Test
    public void testhset(){
        sessionCache = CacheManager.getSessionCache();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("hello","test111");
        sessionCache.setObjByKeyAndField("testaaa","testfild",map);
        sessionCache.setObjByKeyAndField("testaaa","testfild1",map);
        sessionCache.timeSet("testaaa",200);
    }

    @Test
    public void testhsetStr(){
        sessionCache = CacheManager.getSessionCache();
        sessionCache.setStrByKeyAndField("testbbb","testfild","13342");
        sessionCache.setStrByKeyAndField("testbbb","testfild1","245556");
        sessionCache.timeSet("testbbb",200);
    }

    @Test
    public void ttlTest(){
        sessionCache = CacheManager.getSessionCache();
        long result = sessionCache.keyTTL("testbbb");
        System.out.println(result);

        String test = sessionCache.getStrByKeyAndField("testbbb","testfild");
        System.out.println(test);
    }


    @Test
    public void tesGFieldStr(){
        sessionCache = CacheManager.getSessionCache();
        Map<String, Object> value = sessionCache.getFildStr("testbbb");
        System.out.println(value);

    }

    @Test
    public void tesGField(){
        sessionCache = CacheManager.getSessionCache();
        Map<String, Object> value = sessionCache.getFildObject("testaaa");
        System.out.println(value);

    }

    @Test
    public void testPutMap(){
        sessionCache = CacheManager.getSessionCache();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("a","aaa");
        map.put("b","ccc");
        sessionCache.pubMapObject("tt",map,200);
    }

    @Test
    public void testGetMap(){
        sessionCache = CacheManager.getSessionCache();
        //Map<String, Object> value = sessionCache.getFildObject("tt");
        List<Object> value  = sessionCache.getMapListObject("tt",SerializingUtil.serialize("a"),SerializingUtil.serialize("b"));
        System.out.println(value.toString());
    }

    @Test
    public void lockTest(){
        sessionCache = CacheManager.getSessionCache();
        boolean result = sessionCache.lock("HELLO",60);
        System.out.println(result);
    }

    @Test
    public void lockTestTTL(){
        sessionCache = CacheManager.getSessionCache();
        long result = sessionCache.keyTTL("HELLO");
        System.out.println(result);
    }


}
