package com.yanxiu.ce.test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yanxiu.ce.test.junit.SpringJunitTest;

/**
 * sorlJ的测试
 * @author tangmin
 * @date 2016年5月26日
 */
public class SolrJTest extends SpringJunitTest {
	
	@Autowired
	private SolrServer solrServer;
	
	/**
	 * 添加到索引库
	 * @throws Exception
	 */
	@Test
	public void testSolrJAdd() throws Exception {
		//1、创建一个连接(单机版)
		SolrServer solrServer = new HttpSolrServer("http://192.168.3.99:8080/solr");
		
		for(int i=0;i<100;i++){
			//2、创建一个文件对象
			SolrInputDocument doc = new SolrInputDocument();
			//3、添加域
			doc.addField("id", i);
			doc.addField("tch_base_schoolname", "湖北省武汉市汉江区汉江二中"+i);
			doc.addField("tch_base_nationname", "中国"+i);
			doc.addField("tch_base_nativername", "汉族"+i);
			doc.addField("tch_base_name", "李四"+i);
			solrServer.add(doc);
		}
		
		//4、提交
		solrServer.commit();
		
	}
	
	/**
	 * 索引库删除
	 * @throws Exception
	 */
	@Test
	public void testSolrJDelete() throws Exception {
		//1、创建一个连接(单机版)
		SolrServer solrServer = new HttpSolrServer("http://192.168.3.99:8080/solr");
		
		solrServer.deleteById("1");
		//solrServer.deleteByQuery("*:*");
		
		//4、提交
		solrServer.commit();
		
	}
	
	/**
	 * solr查询
	 * @throws Exception
	 */
	@Test
	public void testSolrJQuery() throws Exception {
		//1、创建一个连接(单机版)
		SolrServer solrServer = new HttpSolrServer("http://192.168.3.99:8080/solr");
		//2、创建一个查询对象
		SolrQuery query = new SolrQuery();
		//3、配置查询    添加域   域：值
		
		query.setQuery("江汉");//queryStr查询的keyword
		
		//设置默认搜索域
		query.set("df", "tch_base_schoolname");
		
		//设置高亮
		query.setHighlight(true);
		query.addHighlightField("tch_base_schoolname");
		query.setHighlightSimplePre("<em>");//高亮前缀
		query.setHighlightSimplePost("</em>");//高亮后缀
		
		//设置起始页，每页条数
		query.setStart(0);//起始行
		query.setRows(20);//每页多少行
		
		//4、执行查询
		QueryResponse response = solrServer.query(query);
		
		//5、取查询结果
		SolrDocumentList results = response.getResults();
	
		System.out.println(results.getNumFound());//一共的记录条数
		System.out.println(results.getStart());//起始行
		
		//取高亮结果
		Map<String, Map<String, List<String>>> hhMap = response.getHighlighting();
		
		
		//6、遍历
		for (SolrDocument solrDocument : results) {
			System.out.println("【"+solrDocument.get("id")+solrDocument.get("tch_base_schoolname")
					+solrDocument.get("tch_base_nationname")+solrDocument.get("tch_base_name")+"】");
			
			//取高亮
			List<String> hhList = hhMap.get(solrDocument.get("id")).get("tch_base_schoolname");
			String hhName = "";
			if(hhList!=null && hhList.size()>0){
				hhName = hhList.get(0);
			}else{
				hhName = solrDocument.get("tch_base_schoolname").toString();
			}
			System.out.println("hhname:::::::::::::::::"+hhName);
		}
		
	}
	
	/********************************slorcloud
	 * @throws IOException 
	 * @throws SolrServerException **********************************/
	
	/**
	 * slorcloud新增
	 * @throws SolrServerException
	 * @throws IOException
	 */
	@Test
	public void cloudAddDoc() throws SolrServerException, IOException{
		
		//1、创建solr集群的连接(参数是zookeeper的地址列表,逗号分隔)
		String zkHost = "192.168.3.99:2181,192.168.3.99:2182,192.168.3.99:2183";
		CloudSolrServer solrServer = new CloudSolrServer(zkHost);
		solrServer.setDefaultCollection("collection2");//设置默认的collection2
		//2、创建一个文档对象
		for(int i=0;i<100;i++){
			//2、创建一个文件对象
			SolrInputDocument doc = new SolrInputDocument();
			//3、添加域
			doc.addField("id", i);
			doc.addField("tch_base_schoolname", "湖南省长沙市汉江区汉江二中"+i);
			doc.addField("tch_base_nationname", "中国"+i);
			doc.addField("tch_base_nativername", "汉族"+i);
			doc.addField("tch_base_name", "李四"+i);
			
			//4、把文档加入到索引库
			solrServer.add(doc);
		}
		
		//5、提交
		solrServer.commit();
	}
	
	/**
	 * 删除
	 * @throws SolrServerException
	 * @throws IOException
	 */
	@Test
	public void cloudDeleteDoc() throws SolrServerException, IOException{
		String zkHost = "192.168.3.99:2181,192.168.3.99:2182,192.168.3.99:2183";
		CloudSolrServer solrServer = new CloudSolrServer(zkHost);
		solrServer.setDefaultCollection("collection2");//设置默认的collection2
		
		solrServer.deleteByQuery("*:*");
		solrServer.commit();
	}
	
	
	/**
	 * 公用的solrServer注入
	 * @throws SolrServerException
	 * @throws IOException
	 */
	@Test
	public void testSpring() throws SolrServerException, IOException{
		//2、创建一个文档对象
		for(int i=0;i<100;i++){
			//2、创建一个文件对象
			SolrInputDocument doc = new SolrInputDocument();
			//3、添加域
			doc.addField("id", i);
			doc.addField("tch_base_schoolname", "湖南省长沙市汉江区汉江三中"+i);
			doc.addField("tch_base_nationname", "中国"+i);
			doc.addField("tch_base_nativername", "汉族"+i);
			doc.addField("tch_base_name", "李四"+i);
			
			//4、把文档加入到索引库
			solrServer.add(doc);
		}
				
		//5、提交
		solrServer.commit();
	}

}
