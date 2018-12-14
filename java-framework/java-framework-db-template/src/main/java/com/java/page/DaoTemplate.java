package com.java.page;

import java.util.List;
import java.util.Map;

/**
 * <p>数据持久层接口
 * <p>@author dragon
 * <p>2015年8月24日
 * <p>@version 1.0
 */
public interface DaoTemplate<T> {

	/**
	 * 通过关键字查找
	 * @param keywords
	 * @return
	 */
	public List<T> findByKeywords(Map<String, Object> keywords);
	
	/**
	 * 分页查询获取当前页数据
	 * @param condition
	 * @return
	 */
	public List<T> listByPage(PageCondition condition);
	
	/**
	 * 分页查询获取条目数
	 * @param condition
	 * @return
	 */
	public Integer getTotalRowsByPage(PageCondition condition);
	
	/**
	 * 通过ID查找
	 * @param id
	 * @return
	 */
	public T findById(Integer id);
	
	/**
	 * 通过ID查找
	 * @param id
	 * @return
	 */
	public T findById(Long id);
	
	/**
	 * 插入实体
	 * @param t
	 * @return
	 */
	public int insert(T t);
	
	/**
	 * 查询实体
	 * @param t
	 * @return
	 */
	public List<T> select(T t);
	
	/**
	 * 更新实体
	 * @param t
	 * @return
	 */
	public int update(T t);
	
	/**
	 * 删除实体
	 * @param t
	 * @return
	 */
	public int delete(T t);
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	public int deleteById(long id);
	
}
