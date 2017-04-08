package com.yanxiu.ce.core.train.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.poi.ddf.EscherDggRecord.FileIdCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.fastdfs.FastDFSClient;
import com.yanxiu.ce.core.train.dao.ProjectFileDao;
import com.yanxiu.ce.core.train.dto.ProjectFileCheckDto;
import com.yanxiu.ce.core.train.entity.ProjectFile;
import com.yanxiu.ce.core.train.entity.ProjectFileQuery;
import com.yanxiu.ce.core.train.entity.ProjectTrainOrg;
import com.yanxiu.ce.core.train.entity.ProjectTrainOrgQuery;
import com.yanxiu.ce.core.train.enums.OrgFileEnum;
import com.yanxiu.ce.core.train.enums.OrgFileStatusEnum;
import com.yanxiu.ce.core.train.service.ProjectFileService;
import com.yanxiu.ce.core.train.service.ProjectTrainOrgService;
import com.yanxiu.ce.core.train.service.TrainOrgService;

/**
 * 项目资料文件管理
 * @author tangmin
 * @date 2016-07-26 10:07:34
 */
@Service("projectFileService")
public class ProjectFileServiceImpl extends BaseServiceImpl<ProjectFile, ProjectFileQuery> implements ProjectFileService{
	@Autowired
	private ProjectFileDao dao;
	
	@Autowired
	private ProjectTrainOrgService projectTrainOrgService;
	
	@Autowired
	private TrainOrgService trainOrgService;
	
	
	@Value("${JERSEY_FILE_SYS_URL_DOWNLOAD}")
	private String JERSEY_FILE_SYS_URL_DOWNLOAD;
	
	@Value("${JERSEY_FILE_SYS_URL_UPLOAD}")
	private String JERSEY_FILE_SYS_URL_UPLOAD;

	@Override
	protected BaseDao<ProjectFile, ProjectFileQuery> dao() {
		return this.dao;
	}
	
	/**
	 * 获取seq
	 */
	@Override
	public Integer selectMaxSeq() {
		Integer selectMaxSeq = this.dao.selectMaxSeq();
		if(selectMaxSeq!=null){
			return selectMaxSeq;
		}
		return 0;
	}

	/**
	 * 校验entity是否可修改（code是否存在）
	 */
	@Override
	public Boolean checkNameExit(ProjectFile entity) {
//		Long count = this.dao.selectCheckNameExit(entity.getName(), entity.getId());
//		if(count>0){
//			return false;
//		}
		return true;
	}
	
	/**
	 * 覆写deletById
	 */
	@Override
	public Long deleteById(Long id) {
		ProjectFile projectFile = this.dao.selectById(id);
		
		//删除fastDfs上的文件
		FastDFSClient.deleteFile(projectFile.getFileId());
		
		return this.dao.deleteById(id);
	}
	
	/**
	 * 新增or修改  (单个的文件)
	 */
	@Override
	@Transactional
	public Long saveFile(ProjectFile entity) {
		ProjectFile tmpFile = null;
		List<ProjectFile> projectFiles = this.dao.selectByPidOrgType(entity.getPid(), entity.getOrgId(),entity.getType());
		if(projectFiles!=null && projectFiles.size()>0){
			tmpFile = projectFiles.get(0);
			
			String oldFileId = tmpFile.getFileId();
			//删除旧的文件
			FastDFSClient.deleteFile(oldFileId);
			
			//更新为新的file
			tmpFile.setFileId(entity.getFileId());
			tmpFile.setFileName(entity.getFileName());
			tmpFile.setStatus(entity.getStatus());
			
		}else{
			tmpFile = entity;
		}
		
		if(tmpFile.getId()==null){
			this.insert(tmpFile);
		}else {
			this.update(tmpFile);
		}
		
		return tmpFile.getId();
	}

	@Override
	public List<ProjectFile> selectByPidOrgType(Long pid, Long orgId,
			Integer type) {
		return this.dao.selectByPidOrgType(pid, orgId,type);
	}

	@Override
	public List<ProjectFileCheckDto> getFileCheckDtoByPid(Long pid) {
		
		List<ProjectFileCheckDto> dtos = Lists.newArrayList();
		
		ProjectTrainOrgQuery query = new ProjectTrainOrgQuery();
		query .setPid(pid.toString());
		List<ProjectTrainOrg> projectOrgList = projectTrainOrgService.selectList(query);//获取项目分配的所有机构集合
		
		if(projectOrgList!=null && projectOrgList.size()>0){
			for(ProjectTrainOrg org:projectOrgList){
				Long orgId = org.getOrgid();
				ProjectFileCheckDto dto = new ProjectFileCheckDto();
				List<ProjectFile> files = this.selectByPidOrgType(pid, orgId, null);
				
				List<ProjectFile> otherFiles = Lists.newArrayList();
				//更新url地址
				for(ProjectFile file:files){
					String fileId = file.getFileId();
					String name = OrgFileEnum.getDesc(file.getType());
					String downloadUrl = JERSEY_FILE_SYS_URL_DOWNLOAD+fileId;
					file.setFileId(downloadUrl);
					file.setFileName(name);
					
					if(file.getType()==OrgFileEnum.XUQIU.getValue()){//需求
						dto.setReqProjectFile(file);
					}else if(file.getType()==OrgFileEnum.SHISHI.getValue()){//实施
						dto.setShishiProjectFile(file);
					}else{//其他材料
						otherFiles.add(file);
					}
				}
				dto.setOtherProjectFiles(otherFiles);
				
				dto.setPid(pid);
				dto.setOrgId(orgId);
				dto.setOrgName(trainOrgService.selectById(orgId).getName());
				
				dtos.add(dto);
			}
		}
		
		return dtos;
	}

	@Override
	public String saveFilePass(ProjectFile projectFile) {
		ProjectFile local = null;
		List<ProjectFile> locals = this.selectByPidOrgType(projectFile.getPid(), projectFile.getOrgId(), projectFile.getType());
		if(locals!=null && locals.size()>0){
			local = locals.get(0);
			local.setStatus(OrgFileStatusEnum.PASS.getValue());//修改状态
			this.update(local);
		}
		return "操作成功！";
	}

	@Override
	public String saveFileUnPass(ProjectFile projectFile) {
		ProjectFile local = null;
		List<ProjectFile> locals = this.selectByPidOrgType(projectFile.getPid(), projectFile.getOrgId(), projectFile.getType());
		if(locals!=null && locals.size()>0){
			local = locals.get(0);
			local.setStatus(OrgFileStatusEnum.UNPASS.getValue());
			local.setCheckDesc(projectFile.getCheckDesc());//保存修改意见
			this.update(local);
		}
		return "操作成功！";
	}

}