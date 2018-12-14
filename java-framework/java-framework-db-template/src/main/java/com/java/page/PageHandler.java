package com.java.page;

import org.apache.ibatis.session.RowBounds;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <p>分页查询--只适合单表分页查询
 * <p>@author dragon
 * <p>@date 2015年12月13日
 * <p>@version 1.0
 */
public class PageHandler<T> {

	public Page listPage(Class<T> cls,Page page,Mapper<T> dao){
		
		Page requestPage = new Page(page.getCurrentPage(), page.getPageSize());
		Map<String, Object> keywordMap = page.getKeywords();
		
		Example example = new Example(cls);
		for (Iterator<String> iterator = keywordMap.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next().toString();
			example.createCriteria().andEqualTo(key, keywordMap.get(key));
		}
		example.setOrderByClause(page.getOrderBy());
		
		RowBounds rowBounds = new RowBounds(requestPage.getCurrentPage(), requestPage.getPageSize()); 
		
		List<T>  lists  = dao.selectByExampleAndRowBounds(example, rowBounds);
		int totalRows = dao.selectCountByExample(example);
		
		page.setList(lists);
		page.setTotalRows(totalRows);
		page.initDatas();
		
		return page;
	}
	
}
