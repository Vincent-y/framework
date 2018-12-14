package com.java.redis.cache.impl;

import com.java.redis.util.SerializingUtil;
import com.java.redis.cache.SessionCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <p>jedis cluster
 * <p>@author @DRAGON-Yeah
 * <p>@date 2016年6月25日
 * <p>@version 1.0
 */
public class SessionCacheImpl implements SessionCache {

	//日志
	private final static Logger logger = LoggerFactory.getLogger(SessionCacheImpl.class);
	
	private final static String JEDIS_SET_RESULT_OK = "OK";
	private final static Long  JEDIS_SET_RESULT_1   = 1L;

	//jedisCluster
	private JedisCluster jedisCluster;
	
	public void setJedisCluster(JedisCluster jedisCluster) {
		logger.info(jedisCluster.toString());
		this.jedisCluster = jedisCluster;
	}
	public JedisCluster getJedisCluster() {
		return jedisCluster;
	}
	
	@Override
	public String getStr(String key) {
		if (key == null || jedisCluster == null) {
			return null;
		}
		return jedisCluster.get(key);
	}

	@Override
	public boolean setStr(String key, String value, int seconds) {
		if (key == null || value == null || jedisCluster == null) {
			return false;
		}
		String  result =jedisCluster.setex(key, seconds, value);
		if (!result.equals(JEDIS_SET_RESULT_OK)) {
			return false;
		}
		return true;
	}

	@Override
	public Object getObject(String key) {
		if (key == null || jedisCluster == null) {
			return null;
		}
		byte[] bytes = jedisCluster.get(key.getBytes());
		if (bytes == null) {
			return null;
		}
		return SerializingUtil.deserialize(bytes);
	}


	@Override
	public boolean setObject(String key, Object object, int seconds) {
		if (key == null ||  object == null || jedisCluster == null) {
			return false;
		}
		String result = jedisCluster.setex(key.getBytes(), seconds,SerializingUtil.serialize(object));
		if (!result.equals(JEDIS_SET_RESULT_OK)) {
			return false;
		}
		return true;
	}

	@Override
	public List<Object> getMapListObject(String key,final byte[]... fields) {
		if (key == null || jedisCluster == null) {
			return null;
		}
		List<Object> list = new ArrayList<Object>();
		List<byte[]> listObject = jedisCluster.hmget(key.getBytes(),fields);
		for(byte[] obj: listObject){
			if(obj == null){
				continue;
			}
			list.add(SerializingUtil.deserialize(obj));
		}
		return list;
	}

	
	@Override
	public boolean pubMapObject(String key, Map<String, Object> map, int seconds) {
		if (key == null || map == null || jedisCluster == null) {
			return false;
		}
		Map<byte[] , byte[] > bMap = new HashMap<byte[], byte[]>();
		for(Entry<String, Object> entry: map.entrySet()){
			bMap.put(SerializingUtil.serialize(entry.getKey()), SerializingUtil.serialize(entry.getValue()));
		}
		String result = jedisCluster.hmset(key.getBytes(), bMap);
		jedisCluster.expire(key.getBytes(), seconds);
		if (!result.equals(JEDIS_SET_RESULT_OK)) {
			return false;
		}
		return true;
	}


	public Map<String, Object> getFildObject(String key){
		if (key == null || jedisCluster == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Map<byte[] , byte[] > bMap = jedisCluster.hgetAll(key.getBytes());
		if(bMap == null){
			return null;
		}
		for(Entry<byte[] , byte[] > entry: bMap.entrySet()){
			map.put(SerializingUtil.deserialize(entry.getKey()).toString(), SerializingUtil.deserialize(entry.getValue()));
		}
		return map;
	}


	@Override
	public Map<String, Object> getFildStr(String key) {
		if (key == null || jedisCluster == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		Map<byte[] , byte[] > bMap = jedisCluster.hgetAll(key.getBytes());
		if(bMap == null){
			return null;
		}
		for(Entry<byte[] , byte[] > entry: bMap.entrySet()){
			map.put(new String(entry.getKey()), new String(entry.getValue()));
		}
		return map;
	}

	@Override
	public Object getObjByKeyAndField(String key, String field) {
		if (key == null || field == null || jedisCluster == null) {
			return null;
		}
		byte[] bytes = jedisCluster.hget(key.getBytes(), SerializingUtil.serialize(field));
		return SerializingUtil.deserialize(bytes);
	}
	
	@Override
	public boolean setObjByKeyAndField(String key, String field, Object value) {
		if (key == null || field == null || value == null) {
			return false;
		}
		Long result = jedisCluster.hset(key.getBytes(), SerializingUtil.serialize(field), SerializingUtil.serialize(value));
		if (result != JEDIS_SET_RESULT_1) {
			return false;
		}
		return true;
	}
	
	@Override
	public String getStrByKeyAndField(String key, String field) {
		if (key == null || field == null || jedisCluster == null) {
			return null;
		}
		return jedisCluster.hget(key, field);
	}
	
	@Override
	public boolean setStrByKeyAndField(String key, String field, String value) {
		if (key == null || field == null || value == null) {
			return false;
		}
		Long result = jedisCluster.hset(key, field, value);
		if (result != JEDIS_SET_RESULT_1) {
			return false;
		}
		return true;
	}
	

	

	@Override
	public boolean removeKey(String key) {
		if (key == null) {
			return false;
		}
		Long result = jedisCluster.del(key);
		if (result != JEDIS_SET_RESULT_1) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean removeKey(String key, String field) {
		if (key == null || field == null) {
			return false;
		}
		Long result = jedisCluster.hdel(key, field);
		if (result != JEDIS_SET_RESULT_1) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean removeKey(byte[] key) {
		if (key == null) {
			return false;
		}
		Long result = jedisCluster.del(key);
		if (result != JEDIS_SET_RESULT_1) {
			return false;
		}
		return true;
	}
	@Override
	public boolean removeKey(byte[] key, byte[] field) {
		if (key == null || field == null) {
			return false;
		}
		Long result = jedisCluster.hdel(key, field);
		if (result != JEDIS_SET_RESULT_1) {
			return false;
		}
		return true;
	}
	
	
	@Override
	public Long keyTTL(String key) {
		if (key == null) {
			return 0L;
		}
		return jedisCluster.ttl(key);
	}
	
	@Override
	public Long keyTTL(byte[] key) {
		if (key == null) {
			return 0L;
		}
		return jedisCluster.ttl(key);
	}
	
	@Override
	public Long timeSet(String key, int seconds) {
		if (key == null) {
			return 0L;
		}
		return jedisCluster.expire(key, seconds);
	}
	
	@Override
	public Long timeSet(byte[] key, int seconds) {
		if (key == null) {
			return 0L;
		}
		return jedisCluster.expire(key, seconds);
	}


	@Override
	public boolean lpush(String key,String value){
		if (key == null || value == null || jedisCluster == null) {
			return false;
		}
		Long num = jedisCluster.lpush(key,value);
		if(num>0){
			return true;
		}
		return false;
	}

	@Override
	public String rpop(String key){
		String value = jedisCluster.rpop(key);
		return value;
	}

	@Override
	public List<String> lrange(String key, int start, int end) {
		List<String> list = jedisCluster.lrange(key,start,end);
		return list;
	}

	@Override
	public Long llen(String key) {
		Long count  = jedisCluster.llen(key);
		return count;
	}

	@Override
	public boolean pubListObj(String key, List<Object> list) {
		if (key == null || list == null || jedisCluster == null) {
			return false;
		}
		Long num = jedisCluster.rpush(key.getBytes(), SerializingUtil.serialize(list));
		if (num>0) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean pubListObj(String key, List<Object> list, int index) {
		if (key == null || list == null || jedisCluster == null) {
			return false;
		}
		String result = jedisCluster.lset(key.getBytes(), index, SerializingUtil.serialize(list));
		if (!result.equals(JEDIS_SET_RESULT_OK)) {
			return false;
		}
		return true;
	}
	
	@Override
	public List<Object> getListObj(String key, int start, int end) {
		if (key == null || jedisCluster == null) {
			return null;
		}
		List<byte[]> bList = jedisCluster.lrange(key.getBytes(), start, end);
		
		if (bList == null || bList.size()<0) {
			return null;
		}else if(bList.size() == 0){
			new ArrayList<Object>();
		}

		List<Object> objectList = new ArrayList<Object>();
		for (byte[] bs : bList) {
			objectList.add(SerializingUtil.deserialize(bs));
		}
		
		return objectList;
	}
	@Override
	public List<Object> getListObj(String key) {
		return getListObj(key, 0, -1);
	}
	@Override
	public boolean trimListObj(String key, int start, int end) {
		if (key == null || jedisCluster == null) {
			return false;
		}
		
		String result = jedisCluster.ltrim(key.getBytes(), start, end);
		if (!result.equals(JEDIS_SET_RESULT_OK)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean lock(String key, int seconds) {
		if(key == null  || jedisCluster == null){
			return false;
		}
		long lock = jedisCluster.setnx(key,"lock");
		jedisCluster.expire(key, seconds);
		if(lock == 1){
			return true;
		}
		return false;
	}

	@Override
	public boolean unlock(String key) {
		Long lock = jedisCluster.del(key);
		if(lock == 1){
			return true;
		}
		return false;
	}

	@Override
	public Long incrKey(String key) {
		Long result = jedisCluster.incr(key);
		return result;
	}
}
