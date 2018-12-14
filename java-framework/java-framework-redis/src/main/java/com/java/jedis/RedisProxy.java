package com.java.jedis;


import com.java.jedis.util.RedisKeyMaker;
import com.java.jedis.util.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * <p>redis代理
 * <p>@author DRAGON
 * <p>@date 2015年6月3日
 * <p>@version 1.0
 */
public class RedisProxy {
	/** 日志 **/
	private final static Logger logger = LoggerFactory.getLogger(RedisProxy.class);
	
	/** 代理 **/
	private SimpleRedisProxy simpleRedisProxy;
	
	public void setSimpleRedisProxy(SimpleRedisProxy simpleRedisProxy) {
		this.simpleRedisProxy = simpleRedisProxy;
	}
	
	
	/**
	 * redis set
	 * @param key
	 * @param object
	 * @return
	 */
    public String set(String key, Object object) {
		Jedis jedis = null;
		try {
			jedis = simpleRedisProxy.getJedisFromResource();
			byte[] keyByte = key.getBytes();
			byte[] valueByte = SerializeUtil.serialize(object);
			return simpleRedisProxy.set(jedis, keyByte, valueByte);
		} catch (RedisException e) {
			logger.error("Set redis value {}", e);
		} finally {
			if (null != jedis && jedis.isConnected()) {
				simpleRedisProxy.returnJedisToResource(jedis);
			}
		}
		return null;
	}
	
    /**
     * redis get
     * @param key
     * @return
     */
	public Object get(String key) {
		Jedis jedis = null;
		try {
			jedis = simpleRedisProxy.getJedisFromResource();
			byte[] keyByte = RedisKeyMaker.makeKey(key);
			byte[] valueByte = simpleRedisProxy.get(jedis, keyByte);
			Object object = SerializeUtil.unserialize(valueByte);
			return object;
		} catch (RedisException e) {
			logger.error("Get redis value {}", e);
		} finally {
			if (null != jedis && jedis.isConnected()) {
				simpleRedisProxy.returnJedisToResource(jedis);
			}
		}
		return null;
	}
	
	/**
	 * 移除key
	 * @param key
	 * @return
	 */
	public Long remove(String key) {
		Jedis jedis = null;
		try {
			jedis = simpleRedisProxy.getJedisFromResource();
			byte[] keyByte = RedisKeyMaker.makeKey(key);
			return simpleRedisProxy.remove(jedis, keyByte);
		} catch (RedisException e) {
			logger.error("Remove redis value {}", e);
		} finally {
			if (null != jedis && jedis.isConnected()) {
				simpleRedisProxy.returnJedisToResource(jedis);
			}
		}
		return null;
	}
	
	/**
	 * 遍历所有的key
	 * @return
	 */
	public Set<String> iteratorKeys() {
		Jedis jedis = null;
		try {
			jedis = simpleRedisProxy.getJedisFromResource();
			return simpleRedisProxy.iteratorKeys(jedis);
		} catch (RedisException e) {
			logger.error("Iterator redis keys {}", e);
		} finally {
			if (null != jedis && jedis.isConnected()) {
				simpleRedisProxy.returnJedisToResource(jedis);
			}
		}
		return null;
	}
}
