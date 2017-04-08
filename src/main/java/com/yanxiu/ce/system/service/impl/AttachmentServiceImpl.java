package com.yanxiu.ce.system.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.fastdfs.jersey.JerseyUtils;
import com.yanxiu.ce.common.utils.DateUtils;
import com.yanxiu.ce.system.dao.AttachmentDao;
import com.yanxiu.ce.system.dto.AttDto;
import com.yanxiu.ce.system.dto.AttPreviewDataDto;
import com.yanxiu.ce.system.entity.Attachment;
import com.yanxiu.ce.system.entity.AttachmentQuery;
import com.yanxiu.ce.system.entity.Config;
import com.yanxiu.ce.system.service.AttachmentService;
import com.yanxiu.ce.system.service.ConfigService;

/**
 * 附件管理
 * @author tangmin
 * @date 2016-04-12 16:56:21
 */
@Service("attachmentService")
public class AttachmentServiceImpl extends BaseServiceImpl<Attachment, AttachmentQuery> implements AttachmentService{
	@Autowired
	private AttachmentDao dao;
	
	@Autowired
	private ConfigService configService;
	
	@Value("${JERSEY_FILE_SYS_URL_DOWNLOAD}")
	private String JERSEY_FILE_SYS_URL_DOWNLOAD;
	
	@Value("${JERSEY_FILE_SYS_URL_UPLOAD}")
	private String JERSEY_FILE_SYS_URL_UPLOAD;

	@Override
	protected BaseDao<Attachment, AttachmentQuery> dao() {
		return this.dao;
	}

	
	/**
	 * 单机版 创建附件
	 * @param file
	 * @param u
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	
	@Override
	@Transactional
	public List<Long> createFile(MultipartFile[] files, Long userId) throws IllegalStateException, IOException {
		
		List<Long> attIds = Lists.newArrayList();
		//保存附件
		if (files != null && files.length > 0) {
			for (MultipartFile file : files) {
				if (!file.isEmpty()) {
					Attachment att = new Attachment();
					att.setSize(file.getSize());
					att.setContentType(file.getContentType());
					att.setName(file.getOriginalFilename());
					if (userId != null) {
						att.setUserId(userId);
					}
					String root = this.getStorageRoot();
					String filePath = File.separator
							+ DateUtils.SHORTDATEFORMAT.format(new Date())
							+ File.separator;
					File dir = new File(root + filePath);
					if (!dir.exists()) {
						dir.mkdirs();
					}
					filePath += att.getAttId();
					att.setPath(filePath);
					
					File saveToDir = new File(root + filePath);
					System.out.println(saveToDir.getAbsolutePath());
					file.transferTo(saveToDir);
					
					//保存
					Long attId = this.insert(att);
					attIds.add(attId);
				}
			}
		}
		
		return attIds;
	}
	 */
	
	/**
	 * 单独的文件系统  利用jersey 保存文件
	 */
	@Override
	@Transactional
	public List<Long> createFile(MultipartFile[] files, Long userId) throws IllegalStateException, IOException {
		
		List<Long> attIds = Lists.newArrayList();
		//保存附件
		if (files != null && files.length > 0) {
			for (MultipartFile file : files) {
				if (!file.isEmpty()) {
					Attachment att = new Attachment();
					att.setSize(file.getSize());
					att.setContentType(file.getContentType());
					att.setName(file.getOriginalFilename());
					if (userId != null) {
						att.setUserId(userId);
					}
					String filePath = JerseyUtils.makeFileId(file);
					
					//上传
					JerseyUtils.uploadFile(file, JERSEY_FILE_SYS_URL_UPLOAD+filePath);
					att.setPath(filePath);
					
					//保存
					Long attId = this.insert(att);
					attIds.add(attId);
				}
			}
		}
		
		return attIds;
	}
	
	/**
	 * 获取根路径
	 * @return
	 */
	@Override
	public String getStorageRoot() {
		String key = "Storage_Root";
		Config config = configService.selectByTheKey(key);

		if (config != null) {
			String path = config.getTheValue();
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			return config.getTheValue();
		}
		return System.getProperty("java.io.tmpdir");
	}

	/**
	 * 获取文件流   单机版
	 * @param fs
	 * @return
	 * @throws FileNotFoundException 
	 
	@Override
	public InputStream getInputStream(Attachment att) throws FileNotFoundException {
		FileInputStream fis = null;
		String root = this.getStorageRoot();
		String filePath = root + File.separator + att.getPath();
		fis = new FileInputStream(filePath);
		return fis;
	}
	 */
	
	/**
	 * 获取文件流
	 * @param fs
	 * @return
	 * @throws FileNotFoundException 
	 */
	@Override
	public InputStream getInputStream(Attachment att) throws FileNotFoundException {
		FileInputStream fis = null;
		String downloadUrl = JERSEY_FILE_SYS_URL_DOWNLOAD+ att.getPath();
		fis = new FileInputStream(downloadUrl);
		return fis;
	}


	/**
	 * 删除文件 单机
	 
	@Override
	@Transactional
	public void deleteAtt(String tableName,Long attId) {
		Attachment att = this.dao.selectById(attId);
		if (att != null) {
			String root = this.getStorageRoot();
			String filePath = root + File.separator + att.getPath();
			File file = new File(filePath);
			file.delete();
			file = null;
			this.dao.deleteFromTableByAttId(tableName, attId);
			this.deleteById(attId);
		}
	}
	*/
	
	/**
	 * 删除文件  jersey
	 */
	@Override
	@Transactional
	public void deleteAtt(String tableName,Long attId) {
		Attachment att = this.dao.selectById(attId);
		if (att != null) {
//			String root = this.getStorageRoot();
//			String filePath = root + File.separator + att.getPath();
//			File file = new File(filePath);
//			file.delete();
//			file = null;
			this.dao.deleteFromTableByAttId(tableName, attId);
			this.deleteById(attId);
		}
	}
	

	/**
	 * 获取上线文路径
	 * @return
	 */
	private String getContextPath() {
		String contextPath = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest().getServletContext().getContextPath();
		return contextPath;
	}

//	/**
//	 * 获取附件信息到前端页面   单机
//	 */
//	@Override
//	public AttDto getAttDto(String tableName,Long entityId) {
//		AttDto attDto = new AttDto();
//		List<String> previewDtos = Lists.newArrayList();
//		List<AttPreviewDataDto> previewDataDtos = Lists.newArrayList();
//		
//		List<Long> ids = this.dao.selectFromTableById(tableName, entityId);
//		if(ids.size()==0){
//			return attDto;
//		}
//		List<Attachment> attachments = this.selectByIds(ids);
//		if(attachments!=null&&attachments.size()>0){
//			for(Attachment att:attachments){
//				/**************AttPreviewDto********************/
//				
//				String desc = "";
//				//图片
//				if(att.getContentType().indexOf("image")!=-1){
//					desc = "<a href='"+this.getContextPath()+"/system/att/download?id="+att.getId().toString()+"'>"+
//							"<img src='"+this.getContextPath()+"/system/att/download?id="+att.getId().toString()+"' class='file-preview-image' alt='查看原图' title='查看原图'>"
//							+"</a>";
//				//非图片	
//				}else{
//					desc = "<a href='"+this.getContextPath()+"/system/att/download?id="+att.getId().toString()+"'>"+
//							"<img src='"+this.getContextPath()+"/static/images/file.png' class='file-preview-image' alt='查看原图' title='查看原图'>"
//							+"</a>";
//				}
//				previewDtos.add(desc);
//				
//				/***************AttPreviewDataDto*******************/
//				AttPreviewDataDto attPreviewDataDto = new AttPreviewDataDto();
//				
//				String caption =  "<a href='"+this.getContextPath()+"/system/att/download?id="+att.getId().toString()+"'>"+att.getName()+"</a>";
//				attPreviewDataDto.setCaption(caption);
//				attPreviewDataDto.setUrl(this.getContextPath()+"/system/att/deleteFile");
//				attPreviewDataDto.setKey(att.getId());
//				attPreviewDataDto.getExtra().setAttId(att.getId());
//				attPreviewDataDto.getExtra().setTableName(tableName);
//				previewDataDtos.add(attPreviewDataDto);
//				
//			}
//		}
//		attDto.setPreviews(previewDtos);
//		attDto.setPreviewDatas(previewDataDtos);
//		return attDto;
//	}
//	
	/**
	 * 获取附件信息到前端页面
	 */
	@Override
	public AttDto getAttDto(String tableName,Long entityId) {
		AttDto attDto = new AttDto();
		List<String> previewDtos = Lists.newArrayList();
		List<AttPreviewDataDto> previewDataDtos = Lists.newArrayList();
		
		List<Long> ids = this.dao.selectFromTableById(tableName, entityId);
		if(ids.size()==0){
			return attDto;
		}
		List<Attachment> attachments = this.selectByIds(ids);
		if(attachments!=null&&attachments.size()>0){
			for(Attachment att:attachments){
				/**************AttPreviewDto********************/
				
				String desc = "";
				//图片
				if(att.getContentType().indexOf("image")!=-1){
					desc = "<a target='_blank' href='"+JERSEY_FILE_SYS_URL_DOWNLOAD+att.getPath()+"'>"+
							"<img src='"+JERSEY_FILE_SYS_URL_DOWNLOAD+att.getPath()+"' class='file-preview-image' alt='查看原图' title='查看原图'>"
							+"</a>";
				//非图片	
				}else{
					desc = "<a target='_blank' href='"+JERSEY_FILE_SYS_URL_DOWNLOAD+att.getPath()+"'>"+
							"<img src='"+this.getContextPath()+"/static/images/file.png' class='file-preview-image' alt='查看原图' title='查看原图'>"
							+"</a>";
				}
				previewDtos.add(desc);
				
				/***************AttPreviewDataDto*******************/
				AttPreviewDataDto attPreviewDataDto = new AttPreviewDataDto();
				
				String caption =  "<a target='_blank' href='"+JERSEY_FILE_SYS_URL_DOWNLOAD+att.getPath()+"'>"+att.getName()+"</a>";
				attPreviewDataDto.setCaption(caption);
				attPreviewDataDto.setUrl(this.getContextPath()+"/system/att/deleteFile");
				attPreviewDataDto.setKey(att.getId());
				attPreviewDataDto.getExtra().setAttId(att.getId());
				attPreviewDataDto.getExtra().setTableName(tableName);
				previewDataDtos.add(attPreviewDataDto);
				
			}
		}
		attDto.setPreviews(previewDtos);
		attDto.setPreviewDatas(previewDataDtos);
		return attDto;
	}

}