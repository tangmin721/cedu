package com.yanxiu.ce.core.solr;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yanxiu.ce.common.core.solr.BaseSolrQuery;
import com.yanxiu.ce.common.core.solr.SolrResult;
import com.yanxiu.ce.core.solr.dao.TeacherSolrDao;
import com.yanxiu.ce.core.solr.dto.TeacherSolr;
import com.yanxiu.ce.core.solr.service.TeacherSolrService;
import com.yanxiu.ce.test.junit.SpringJunitTest;

public class TestTeacherSolr extends SpringJunitTest {
	
	@Autowired
	private TeacherSolrDao dao;
	
	@Autowired
	private TeacherSolrService teacherSolrService;

	@Test
	public void test(){
		List<TeacherSolr> allTeacherDto = dao.getAllTeacherSolr();
		System.out.println(allTeacherDto);
	}
	
	@Test
	public void testqueryByStr() throws Exception{
		BaseSolrQuery query = new BaseSolrQuery();
		query.setQ("张三"); 
		SolrResult<TeacherSolr> resultSolr = teacherSolrService.queryByStr(query);
		System.out.println("=====================================================");
		System.out.println(resultSolr.getList());
	}
}
