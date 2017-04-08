package com.yanxiu.ce.system.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.system.dto.AttDto;
import com.yanxiu.ce.system.entity.Attachment;
import com.yanxiu.ce.system.entity.AttachmentQuery;

/**
 * 附件管理
 * @author tangmin
 * @date 2016-04-12 16:56:21
 */
public interface AttachmentService extends BaseService<Attachment, AttachmentQuery>{
	
	/**
	 * 创建附件
	 * @param file
	 * @param u
	 * @return
	 */
	public List<Long> createFile(MultipartFile[] files, Long userId)throws IllegalStateException, IOException;

	/**
	 * 获取根路径
	 * @return
	 */
	public String getStorageRoot();

	/**
	 * 获取文件流
	 * @param fs
	 * @return
	 */
	public InputStream getInputStream(Attachment att) throws FileNotFoundException ;
	
	/**
	 * 删除文件
	 * @param id
	 */
	public void deleteAtt(String tableName,Long id);
	
	/**
	 * 获取附件的attDto
	 * @param tableName
	 * @param attId
	 * @param isImg
	 * @return
	 */
	public AttDto getAttDto(String tableName,Long entityId);
	

}
