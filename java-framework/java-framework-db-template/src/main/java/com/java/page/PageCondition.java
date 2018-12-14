package com.java.page;

import java.io.Serializable;

/**
 * <p>分页基本条件
 * <p>@author dragon
 * <p>@date 2015年12月13日
 * <p>@version 1.0
 */
public class PageCondition implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7725723628868048110L;
	
	public Integer   currentPage;   //当前页码
	public Integer   pageSize;      //每页显示条数
	public int       ascending = 0; //0为降序 1为升序
	public Integer   startLine;	    // 起始行数
	
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
	public int getAscending() {
		return ascending;
	}
	public void setAscending(int ascending) {
		this.ascending = ascending;
	}
	public Integer getStartLine() {
		return startLine;
	}
	public void setStartLine(Integer startLine) {
		this.startLine = startLine;
	}
	
}
