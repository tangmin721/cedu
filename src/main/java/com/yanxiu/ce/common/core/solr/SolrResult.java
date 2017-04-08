package com.yanxiu.ce.common.core.solr;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 返回solr查询结果数据
 * @author tangmin
 * @date 2016年5月27日
 */
public class SolrResult<T> {
	/**
	 * 查询结果的状态码
	 */
	private String code;
	
	/**
	 * 带额外的结果说明
	 */
	private String msg;
	
	/**
	 * 实体的list
	 */
	private List<T> list = Lists.newArrayList();

	/**
	 * 总记录条数
	 */
	private Long total;

	/**
	 * 总页数
	 */
	private Long totalPage;

	/**
	 * 每页记录条数
	 */
	private Integer pageSize;

	/**
	 * 当前页码
	 */
	private Integer pageCurrent;

	/**
	 * 起始行,用于页面序号相加
	 */
	private Integer pageStart;
	
	public SolrResult(Integer pageCurrent, Integer pageSize, Long total) {
		this.pageCurrent = pageCurrent;
		this.pageSize = pageSize;
		this.total = total;

		if (total > 0) {
			this.totalPage = (total + pageSize - 1) / pageSize;
			this.pageStart = pageSize * (pageCurrent - 1);
		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Long totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageCurrent() {
		return pageCurrent;
	}

	public void setPageCurrent(Integer pageCurrent) {
		this.pageCurrent = pageCurrent;
	}

	public Integer getPageStart() {
		return pageStart;
	}

	public void setPageStart(Integer pageStart) {
		this.pageStart = pageStart;
	}
	
}
