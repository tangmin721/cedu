package com.yanxiu.ce.core.solr.service;

import com.yanxiu.ce.common.core.solr.BaseSolrQuery;
import com.yanxiu.ce.common.core.solr.SolrResult;
import com.yanxiu.ce.core.solr.dto.TeacherSolr;

/**
 * @author tangmin
 * @date 2016年5月27日
 */
public interface TeacherSolrService {
	
	/**
	 * 导入全部teacher数据到solr索引库
	 */
	public void importAll() throws Exception;
	
	
	/**
	 * solr查询teacher
	 */
	public SolrResult<TeacherSolr> queryByStr(BaseSolrQuery query) throws Exception;
	
	
	/**
	 * 更新索引
	 * @throws Exception
	 */
	public void update(TeacherSolr teacherSolr) throws Exception;

}
