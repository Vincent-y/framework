package com.java.jedis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Set;

/**
 * <p>jedis应用
 * <p> @author DRAGON
 * <p>@date 2015年6月3日
 * <p> @version 1.0
 */
public class SimpleRedisProxy {
	/** 日志 **/
	private final static Logger logger = LoggerFactory.getLogger(SimpleRedisProxy.class);
	/** 超时时间 **/
	private static final int EXPIRATION_TIME = 3600 * 10;// s
	/** jedis连接池 **/
	private JedisPool pool;

	public void setPool(JedisPool pool) {
		this.pool = pool;
	}

	/**
	 * 获取jedis句柄
	 * @return
	 */
	public Jedis getJedisFromResource() {
		return pool.getResource();
	}

	public void returnJedisToResource(Jedis jedis) {
		try {
			if (jedis != null) {
				//pool.returnResource(jedis);
				jedis.close();
				pool.destroy();
			}
		} catch (Exception e) {
			logger.error("Return redis connection to pool {}", e);
		}
	}

	public void destroy() {
		if (null != pool)
			pool.destroy();
	}

	public String set(Jedis jedis, byte[] key, byte[] value)throws RedisException {
		String ret = jedis.setex(key, EXPIRATION_TIME, value);
		return ret;
	}

	public byte[] get(Jedis jedis, byte[] key) throws RedisException {
		byte[] ret = jedis.get(key);
		return ret;
	}

	public Long remove(Jedis jedis, byte[]... keys) throws RedisException {
		Long ret = jedis.del(keys);
		return ret;
	}

	public Transaction multi(Jedis jedis) throws RedisException {
		Transaction tx = jedis.multi();
		return tx;
	}

	public List<Object> exec(Transaction tx) throws RedisException {
		List<Object> lst = tx.exec();
		return lst;
	}

	public Set<String> iteratorKeys(Jedis jedis) throws RedisException {
		return jedis.keys("*");
	}

}
