package com.yanxiu.ce.core.train.service;

import java.util.List;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.train.dto.ProjectFileCheckDto;
import com.yanxiu.ce.core.train.entity.ProjectFile;
import com.yanxiu.ce.core.train.entity.ProjectFileQuery;

/**
 * 项目资料文件管理
 * @author tangmin
 * @date 2016-07-26 10:07:34
 */
public interface ProjectFileService extends BaseService<ProjectFile, ProjectFileQuery>{
	/**
	 * 获取seq
	 * @return Integer
	 */
	Integer selectMaxSeq();
	
	/**
	 * 校验是否存在
	 * @param 
	 * @return
	 */
	Boolean checkNameExit(ProjectFile entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	Long saveFile(ProjectFile entity);
	
	
	List<ProjectFile> selectByPidOrgType(Long pid,Long orgId,Integer type);
	
	/**
	 * 根据pid获取dto
	 * @return
	 */
	List<ProjectFileCheckDto> getFileCheckDtoByPid(Long pid);
	
	
	/**
	 * 审批通过的保存
	 * @param projectFile
	 * @return
	 */
	String saveFilePass(ProjectFile projectFile);
	
	/**
	 * 审批不通过的保存
	 * @param projectFile
	 * @return
	 */
	String saveFileUnPass(ProjectFile projectFile);

}
