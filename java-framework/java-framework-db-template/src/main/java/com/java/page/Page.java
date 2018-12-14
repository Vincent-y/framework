package com.java.page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>分页
 * <p>@author dragon
 * <p>2015年8月24日
 * <p>@version 1.0
 */
public class Page implements Serializable{

	private static final long serialVersionUID = 9149699483090034336L;
	
	private static final Integer DEFAULT_CURRENT_PAGE = 1;
	private static final Integer DEFAULT_PAGE_NUMBER = 10;
	
	private Integer currentPage; //当前页码
	private Integer pageSize; //每页显示条数
	private Integer totalRows; //总行数
	private Integer totalPages; //总页数
	private Integer startPage; //起始页
	private Integer endPage; //结束页
	private String   orderBy; //排序 如"p_id desc"
	
	// 查询的数据list
	private Object list;
	// 查询的关键字
	private Map<String, Object> keywords = new HashMap<String, Object>();
	
	private Integer offset;	 // 物理起始页，即实际当前页减去1
	private Integer startLine;	// 起始行数
	
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	public String getOrderBy() {
		return orderBy;
	}
	
	//外部设置关键字的方法
	public void setKeyword(String key, Object value) {
		if (null == value) {
			return;
		}
		keywords.put(key,value);
	}
	
	public Page(Integer currentPage, Integer pageSize) {
		
		if (currentPage == null || currentPage <= 0) {
			this.currentPage = DEFAULT_CURRENT_PAGE;
		} else {
			this.currentPage = currentPage; 
		}
		
		if (pageSize == null || pageSize <= 0)  {
			this.pageSize = DEFAULT_PAGE_NUMBER;
		} else {
			this.pageSize = pageSize; 
		}
		
	}
	
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}
	public Integer getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	public Integer getStartPage() {
		return startPage;
	}
	public void setStartPage(Integer startPage) {
		this.startPage = startPage;
	}
	public Integer getEndPage() {
		return endPage;
	}
	public void setEndPage(Integer endPage) {
		this.endPage = endPage;
	}	
	public Object getList() {
		return list;
	}
	public void setList(Object list) {
		this.list = list;
	}
	public Map<String, Object> getKeywords() {
		return keywords;
	}
	public void setKeywords(Map<String, Object> keywords) {
		this.keywords = keywords;
	}

	// 初始化数据
	public void initDatas(){
		countTotalPage();
		countStartAndEnd();
	}
	
	//计算起始页和结束页
	private void countStartAndEnd() {
		
		startPage = currentPage  -  2;
		endPage   = currentPage + 2;
		
		if (totalPages<=5) {
			startPage = 1;
			endPage   = totalPages;
		}else{
			
			if (startPage<1) {
				startPage = 1;
				endPage   = 5;
			}
			
			if(endPage > totalPages){
				endPage = totalPages;
				startPage= totalPages - 5+ 1;
			}
			
		}
	} 
	
	// 计算总页数
	private void countTotalPage() {
		totalPages = totalRows % pageSize == 0 ? totalRows/pageSize : (totalRows/pageSize + 1);
	}

	public Integer getOffset() {
		return currentPage - 1;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getStartLine() {
		return (currentPage - 1) * pageSize;
	}

	public void setStartLine(Integer startLine) {
		this.startLine = startLine;
	}
	
	
}
