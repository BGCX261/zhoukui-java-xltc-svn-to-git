package org.sunney.help.util;

import java.util.List;

/**
 * @author fisher
 * @param <T>
 * @description 通用查询对象
 * @date 2011-9-16
 */
public class Query<T> {
	
	/** 分页查询对象*/
	private Pagenation page;
	
	/** 查询记录 */
	private List<T> results;
	
	/**
	 * 
	 */
	public Query() {
	}
	
	/**
	 * @param page
	 * @param results
	 */
	
	public Query(Pagenation page, List<T> results) {
		this.page = page;
		this.results = results;
	}

	public Pagenation getPage() {
		return page;
	}

	public void setPage(Pagenation page) {
		this.page = page;
	}

	public List<?> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}
	
}
