package com.yanxiu.ce.common.core.solr;

import com.yanxiu.ce.common.constant.AppConstant;

/**
 * solr查询公共对象
 * @author tangmin
 * @date 2016年5月27日
 */
public class BaseSolrQuery {
	
	private String q;//查询参数keyword
	/**
	 * 每页记录条数
	 */
	protected Integer pageSize = Integer.parseInt(AppConstant.PAGE_SIZE);
	
	/**
	 * 起始行
	 */
	protected Integer startRow;
	
	/**
	 * 当前页
	 */
	protected Integer pageCurrent = 1;
	
	
	public BaseSolrQuery() {
		this.startRow = (this.pageCurrent - 1) * this.pageSize;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		this.startRow = (this.pageCurrent - 1) * this.pageSize;
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getPageCurrent() {
		return pageCurrent;
	}

	public void setPageCurrent(Integer pageCurrent) {
		this.pageCurrent = pageCurrent;
		this.startRow = (this.pageCurrent - 1) * this.pageSize;
	}

}
