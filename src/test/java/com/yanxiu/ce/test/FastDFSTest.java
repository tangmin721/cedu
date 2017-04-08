package com.yanxiu.ce.test;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

import com.yanxiu.ce.common.fastdfs.FastDFSClient;

/**
 * 测试文件系统
 * @author tangmin
 * @date 2016年7月22日
 */
public class FastDFSTest {
	/**
	 * 上传测试.
	 * @throws Exception
	 */
	public static void upload() throws Exception {
		String filePath = "D:/test.png";//test.png
		File file = new File(filePath);
		String fileId = FastDFSClient.uploadFile(file, filePath);
		System.out.println("Upload local file " + filePath + " ok, fileid=" + fileId);
		// fileId:	group1/M00/00/00/wKgMv1eRoCiANVUJAANJM0rhcYU610.png
		// url:	http://192.168.12.191:8888/group1/M00/00/00/wKgMv1eRoCiANVUJAANJM0rhcYU610.png
	}
	
	/**  
	 * 下载测试.
	 * @throws Exception
	 */
	public static void download() throws Exception {
		String fileId = "group1/M00/00/00/wKgMv1eRoCiANVUJAANJM0rhcYU610.png";
		InputStream inputStream = FastDFSClient.downloadFile(fileId);
		File destFile = new File("D:/DownloadTest.png");
		FileUtils.copyInputStreamToFile(inputStream, destFile);
	}

	/**
	 * 删除测试
	 * @throws Exception 
	 */ 
	public static void delete() throws Exception {
		String fileId = "group1/M00/00/00/wKgMv1eRoCiANVUJAANJM0rhcYU610.png";
		int result = FastDFSClient.deleteFile(fileId);
		System.out.println(result == 0 ? "删除成功" : "删除失败:" + result);
	}


	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		upload();
		//download();
		//delete();

	}


}
