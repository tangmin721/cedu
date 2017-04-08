package com.yanxiu.ce.common.fastdfs.jersey;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

/**
 * Jersey 
 * @author tangmin
 * @date 2016年8月19日
 */
public class JerseyUtils {
	
	/**
	 * wenjian sheng cheng celue
	 * @param file
	 * @return
	 */
	public static String makeFileId(MultipartFile file) {
		//文件后缀
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		//图片id生成策略
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fileName = df.format(new Date());
		
		//随机三位数
		Random r = new Random();
		for(int i=0;i<3;i++){
			fileName += r.nextInt(10);
		}
		fileName = fileName + "." + extension;
		return fileName;
	}
	
	/**
	 * shang chuan
	 * @param file
	 * @param downloadUrl
	 * @throws IOException
	 */
	public static void uploadFile(MultipartFile file, String downloadUrl)
			throws IOException {
		Client client = new Client(); 
		WebResource resource = client.resource(downloadUrl);
		//把文件转换为二进制流
		byte[] readFileToByteArray = file.getBytes();
		resource.put(String.class,readFileToByteArray);
	}
}
