package com.java.redis.cache;

import java.util.List;
import java.util.Map;


/**
 * <p>cache
 * <p>@author DRAGON
 * <p>@date   2016年6月5日
 * <p>@version 1.0
 */
public interface SessionCache {

	/**
	 * 根据缓存key获取值字符串
	 * @param key
	 * @return
	 */
	public String getStr(String key);
	
	/**
	 * 字符串存储(原子性)
	 * @param key
	 * @param value
	 * @param seconds--超时 s
	 * @return
	 */
	public boolean setStr(String key, String value, int seconds);


	/**
	 * 根据key获取对象
	 * @param key
	 * @return
	 */
	public Object getObject(String key);

	/**
	 * 保存对象，包含时间
	 * @param key
	 * @param object
	 * @param seconds
	 * @return
	 */
	public boolean setObject(String key, Object object, int seconds);


	/**
	 * 队列 左进
	 * @param key
	 * @param value
     * @return
     */
	public boolean lpush(String key, String value);

	/**
	 * 队列 右出
	 * @param key
	 * @return
     */
	public String rpop(String key);

	/**
	 * 获取链表数据
	 * @param key
	 * @param start
	 * @param end
	 */
	public List<String> lrange(String key, int start, int end);

	/**
	 * 获取长度
	 * @param key
	 * @return
	 */
	public Long llen(String key);


	/**
	 * 根据key获取map
	 * @param key
	 * @return
	 */
	public List<Object> getMapListObject(String key, final byte[]... fields);

	/**
	 * put  MapObject 保存时间
	 * @param key
	 * @param map
	 * @param seconds
	 * @return
	 */
	public boolean pubMapObject(String key, Map<String, Object> map, int seconds);


	/**
	 * get fild
	 * @param key
	 * @return
	 */
	public Map<String, Object> getFildObject(String key);


	public Map<String, Object> getFildStr(String key);

	/**
	 * 根据key和块获取
	 * @param key
	 * @param field
	 * @return
	 */
	public Object getObjByKeyAndField(String key, String field);

	/**
	 * 根据 key ,field 保存值
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public boolean setObjByKeyAndField(String key, String field, Object value);


	/**
	 * 根据key和块获取
	 * @param key
	 * @param field
	 * @return
	 */
	public String getStrByKeyAndField(String key, String field);

	/**
	 * 根据 key ,field 保存值
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public boolean setStrByKeyAndField(String key, String field, String value);



	//指定list对象
	/**
	 * 指定list集合添加对象，在list尾部添加对象
	 * @param key
	 * @param list
	 * @return
	 */
	public boolean pubListObj(String key, List<Object> list);

	/**
	 * 指定list集合添加对象，并指定坐标
	 * @param key
	 * @param list
	 * @param index
	 * @return
	 */
	public boolean pubListObj(String key, List<Object> list, int index);

	/**
	 * 指定坐标，返回list
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Object> getListObj(String key, int start, int end);

	/**
	 * 获取集合
	 * @param key
	 * @return
	 */
	public List<Object> getListObj(String key);

	/**
	 * 裁剪list集合
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public boolean trimListObj(String key, int start, int end);



	/**
	 * 移除
	 * @param key
	 * @return
	 */
	public boolean removeKey(String key);
	public boolean removeKey(String key, String field);

	/**
	 * 移除
	 * @param key
	 * @return
	 */
	public boolean removeKey(byte[] key);
	public boolean removeKey(byte[] key, byte[] field);

	/**
	 * 剩余时间
	 * @param key
	 * @return
	 */
	public Long keyTTL(String key);
	public Long keyTTL(byte[] key);

	/**
	 * 设置时间
	 * @param key
	 * @param seconds
	 * @return
	 */
	public Long timeSet(String key, int seconds);
	public Long timeSet(byte[] key, int seconds);

	/**
	 * 分布式锁
	 * @param key
	 * @return
     */
	public boolean lock(String key, int seconds);
	public boolean unlock(String key);


	public Long incrKey(String key);


}
