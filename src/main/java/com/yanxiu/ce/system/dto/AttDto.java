package com.yanxiu.ce.system.dto;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 附件的包装类
 * @author tangmin
 * @date 2016年4月15日
 */
public class AttDto {
	private List<String> previews = Lists.newArrayList();
	private List<AttPreviewDataDto> previewDatas = Lists.newArrayList();
	public List<String> getPreviews() {
		return previews;
	}
	public void setPreviews(List<String> previews) {
		this.previews = previews;
	}
	public List<AttPreviewDataDto> getPreviewDatas() {
		return previewDatas;
	}
	public void setPreviewDatas(List<AttPreviewDataDto> previewDatas) {
		this.previewDatas = previewDatas;
	}
}
