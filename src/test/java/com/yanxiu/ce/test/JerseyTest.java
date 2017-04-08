package com.yanxiu.ce.test;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.io.FileUtils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.yanxiu.ce.common.utils.DateUtils;

/**
 * 发送文件到 文件服务器  （tomcat）
 * @author tangmin
 * @date 2016年7月25日
 */
public class JerseyTest {
	public static void main(String[] args) throws IOException {
		testJersey();
	}

	private static void testJersey() throws IOException {
		Client client = new Client(); 
		
		//图片id生成策略
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fileId = df.format(new Date());
		
		//随机三位数
		Random r = new Random();
		for(int i=0;i<3;i++){
			fileId += r.nextInt(10);
		}
		
		System.out.println(fileId);
		
		
		//把要上传的文件存放的路径
		String url = "http://hubei.yanxiu.com/upload/"+fileId+".png";
		//String url = "http://192.168.12.190:8088/upload/"+fileId+".jar";
		//String url = "http://192.168.12.190:8088/upload/testtt.jpg";
		WebResource resource = client.resource(url);
		
		
		//本地文件
		//String path = "D:\\fastdfs_client_v1.24.jar";
		//String path = "D:\\BaiduYunDownload\\Dubbo视频教程--高可用架构篇--第03节--ActiveMQ集群的安装、配置、高可用测试.rar";
		String path = "D:\\test.png";
		
		//把文件转换为二进制流
		byte[] readFileToByteArray = FileUtils.readFileToByteArray(new File(path));
		
		resource.put(String.class,readFileToByteArray);
		
		System.out.println("发送完毕");
	}
	
}
