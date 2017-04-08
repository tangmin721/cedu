package com.yanxiu.ce.system.dto;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 上传组件的initialPreviewConfig  json数据封装类
 * @author tangmin
 * @date 2016年4月14日
 */
public class AttPreviewDataDto {
	
//	caption: '<a href="${ctx}/system/att/download?id=52">hello</a>',
//    width: '120px',
//    url: '${ctx}/system/att/deleteFile',
//    key: 100,
//    extra: {attId: 100,tableName:"sys_file_table"}
	
	private String caption;
	
	private String width="120px";
	
	private String url;
	
	private Long key;
	
	private AttExtraData extra = new AttExtraData();
	
//	private HttpServletRequest getRequest() {
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
//				.getRequestAttributes()).getRequest();
//		return request;
//	}
//	/**
//	 * 构造
//	 * @param attId
//	 * @param tableName
//	 */
//	public AttPreviewDataDto(Long attId,String fileName,String tableName){
//		this.caption = "<a href='"+getRequest().getServletContext().getContextPath()+"/system/att/download?id="+attId.toString()+"'>"+fileName+"</a>";
//		this.extra.setAttId(attId);
//		this.extra.setTableName(tableName);
//	}
	
	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public AttExtraData getExtra() {
		return extra;
	}

	public void setExtra(AttExtraData extra) {
		this.extra = extra;
	}
	
	
	
	
}
