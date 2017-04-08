package com.yanxiu.ce.core.solr.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.yanxiu.ce.common.core.solr.BaseSolrQuery;
import com.yanxiu.ce.common.core.solr.SolrResult;
import com.yanxiu.ce.core.solr.dao.TeacherSolrDao;
import com.yanxiu.ce.core.solr.dto.TeacherSolr;
import com.yanxiu.ce.core.solr.service.TeacherSolrService;

@Service("teacherSolrService")
public class TeacherSolrServiceImpl implements TeacherSolrService{

	/**
	 * 注入solrServer  单机版or集群版都是这一个
	 */
	@Autowired
	private SolrServer solrServer;
	
	@Autowired
	private TeacherSolrDao teacherSolrDao;
	
	/**
	 * 导入
	 */
	@Override
	public void importAll() throws Exception{
		List<TeacherSolr> allTeacherSolr = teacherSolrDao.getAllTeacherSolr();
		System.out.println(allTeacherSolr.size());
		
		for (TeacherSolr teacherSolr : allTeacherSolr) {
			SolrInputDocument doc = changeToSolrDoc(teacherSolr);
			solrServer.add(doc);
		}
		solrServer.commit();
	}


	/**
	 * solr查询
	 * @throws Exception 
	 */
	@Override
	public SolrResult<TeacherSolr> queryByStr(BaseSolrQuery query) throws Exception {
		SolrResult<TeacherSolr> result = null;
		List<TeacherSolr> teacherSolrs = Lists.newArrayList();
		
		SolrQuery solrQuery = new SolrQuery();
		//3、配置查询    添加域   域：值
		String keyword = "";
		//转换get区域的字符串乱码
//		if(StringUtils.isNotBlank(query.getQ())){
//			keyword = new String(query.getQ().getBytes("iso8859-1"),"utf-8");
//		}
		keyword = query.getQ();//通过service调用，不用转码
		System.out.println("keyword::"+keyword);
		solrQuery.setQuery(keyword);
		//设置默认搜索域
		solrQuery.set("df", "tch_base_keywords");
		
		//设置高亮
//		solrQuery.setHighlight(true);
//		solrQuery.addHighlightField("tch_base_name");
//		solrQuery.setHighlightSimplePre("<em>");//高亮前缀
//		solrQuery.setHighlightSimplePost("</em>");//高亮后缀
		
		solrQuery.setStart(query.getStartRow());//起始行
		solrQuery.setRows(query.getPageSize());//每页多少行
		
		//4、执行查询
		QueryResponse response = solrServer.query(solrQuery);
		
		//5、取查询结果
		SolrDocumentList results = response.getResults();
		 //取高亮结果
        Map<String, Map<String, List<String>>> hhMap = response.getHighlighting();
        
		//6、遍历
		for (SolrDocument solrDocument : results) {
			TeacherSolr teacherSolr = changePojo(solrDocument);
			  
			//取高亮
//            List<String> hhList = hhMap.get(solrDocument.get("id")).get("tch_base_name");
//            String hhName = "";
//            if(hhList!=null && hhList.size()>0){
//                hhName = hhList.get(0);
//            }else{
//                hhName = solrDocument.get("tch_base_name").toString();
//            }
//            
//            teacherSolr.setName(hhName);
//			System.out.println("hhName::"+hhName);
			teacherSolrs.add(teacherSolr);
		}
		
		result = new SolrResult<TeacherSolr>(query.getPageCurrent(), query.getPageSize(), results.getNumFound());
		result.getList().addAll(teacherSolrs);
		return result;
	}

	
	/**
	 * 对象转换为solr文档对象
	 * @param teacherSolr
	 * @param doc
	 */
	private SolrInputDocument changeToSolrDoc(TeacherSolr teacherSolr) {
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", teacherSolr.getId());
		doc.addField("tch_base_schoolname", teacherSolr.getSchoolName());
		doc.addField("tch_base_nationname", teacherSolr.getNationName());
		doc.addField("tch_base_nativername", teacherSolr.getNativerName());
		doc.addField("tch_base_name", teacherSolr.getName());
		return doc;
	}
	
	/**
	 * solr文档对象，映射对象的转换
	 * @param solrDocument
	 * @param teacherSolr
	 */
	private TeacherSolr changePojo(SolrDocument solrDocument) {
		TeacherSolr teacherSolr = new TeacherSolr();
		teacherSolr.setId(Long.parseLong(solrDocument.get("id").toString()));
		teacherSolr.setName(solrDocument.get("tch_base_name").toString());
		teacherSolr.setNationName(solrDocument.get("tch_base_nationname").toString());
		teacherSolr.setNativerName(solrDocument.get("tch_base_nativername").toString());
		teacherSolr.setSchoolName(solrDocument.get("tch_base_schoolname").toString());
		return teacherSolr;
	}


	@Override
	public void update(TeacherSolr teacherSolr) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
