package com.yanxiu.ce.core.train.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.fastdfs.jersey.JerseyUtils;
import com.yanxiu.ce.core.train.dto.ProjectFileCheckDto;
import com.yanxiu.ce.core.train.entity.ProjectFile;
import com.yanxiu.ce.core.train.entity.TrainOrg;
import com.yanxiu.ce.core.train.enums.OrgFileEnum;
import com.yanxiu.ce.core.train.enums.OrgFileStatusEnum;
import com.yanxiu.ce.core.train.service.ExpertService;
import com.yanxiu.ce.core.train.service.ProjectFileService;
import com.yanxiu.ce.core.train.service.TrainOrgService;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.security.ShiroUtils;

/**
 * 项目文件管理
 * 
 * @author tangmin
 * @date 2016年7月25日
 */
@Controller
@RequestMapping("/core/train/project/file")
public class ProjectFileController {

	@Autowired
	private ProjectFileService projectFileService;
	
	@Autowired
	private ExpertService expertService;
	
	@Value("${JERSEY_FILE_SYS_URL_DOWNLOAD}")
	private String JERSEY_FILE_SYS_URL_DOWNLOAD;
	
	@Value("${JERSEY_FILE_SYS_URL_UPLOAD}")
	private String JERSEY_FILE_SYS_URL_UPLOAD;
	
	@Value("${FILE_MAX_SIZE}")
	private Long FILE_MAX_SIZE;
	
	@Autowired
	private TrainOrgService trainOrgService;
	
	/**
	 * 进入check页面
	 * @param pid
	 * @param model
	 * @return
	 */
	@RequestMapping(value="fileCheck/{pid}")
	@RequiresPermissions("Project:orgFileCheck")
	public String fileCheck(@PathVariable Long pid,Model model){
		
		List<ProjectFileCheckDto> dtos = projectFileService.getFileCheckDtoByPid(pid);
		model.addAttribute("dtos", dtos);
		
		return "core/train/project/file/fileCheck";
	}
	
	/**
	 * 进入view页面
	 * @param pid
	 * @param model
	 * @return
	 */
	@RequestMapping(value="fileView/{pid}")
	public String fileView(@PathVariable Long pid,Model model){
		
		List<ProjectFileCheckDto> dtos = projectFileService.getFileCheckDtoByPid(pid);
		model.addAttribute("dtos", dtos);
		
		return "core/train/project/file/fileView";
	}
	/**
	 * 进入审批不通过，输入不通过意见的form页面
	 * @param pid
	 * @param orgId
	 * @param type
	 * @param model
	 * @return
	 */
	@RequestMapping(value="fileUnPassForm/{pid}/{orgId}/{type}")
	@RequiresPermissions("Project:orgFileCheck")
	public String fileUnPassForm(@PathVariable Long pid,@PathVariable Long orgId,@PathVariable Integer type,Model model){
		model.addAttribute("pid", pid);
		model.addAttribute("orgId", orgId);
		model.addAttribute("type", type);
		
		String checkDesc = "";
		List<ProjectFile> locals = projectFileService.selectByPidOrgType(pid,orgId,type);
		if(locals!=null && locals.size()>0){
			checkDesc = locals.get(0).getCheckDesc();
		}
		
		model.addAttribute("checkDesc", checkDesc);
		return "core/train/project/file/fileUnPassForm";
	}
	
	/**
	 * 保存审核通过页面
	 * @param pid
	 * @param orgId
	 * @param type
	 * @param model
	 * @return
	 */
	@RequestMapping(value="saveFilePass/{pid}/{orgId}/{type}")
	@ResponseBody
	@RequiresPermissions("Project:orgFileCheck")
	public String saveFilePass(@PathVariable Long pid,@PathVariable Long orgId,@PathVariable Integer type, Model model){
		ProjectFile projectFile = new ProjectFile();
		projectFile.setPid(pid);
		projectFile.setOrgId(orgId);
		projectFile.setType(type);
		AjaxCallback ok = AjaxCallback.OK(this.projectFileService.saveFilePass(projectFile));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 保存不通过意见的form页面
	 * @param pid
	 * @param orgId
	 * @param type
	 * @param model
	 * @return
	 */
	@RequestMapping(value="saveFileUnPassForm")
	@ResponseBody
	@RequiresPermissions("Project:orgFileCheck")
	public String saveFileUnPassForm(ProjectFile projectFile, Model model){
		AjaxCallback ok = AjaxCallback.OK(this.projectFileService.saveFileUnPass(projectFile));
		return JSON.toJSONString(ok);
	}
	
	
	
	/**
	 * 培训机构材料管理
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="orgFileCreate/{pid}/{type}")
	@RequiresPermissions("Project:orgFileCreate")
	public String orgFileCreate(@PathVariable Long pid,@PathVariable Long type,Model model){
		User currentUser = ShiroUtils.getCurrentUser();
		String loginName = currentUser.getLoginName();
		TrainOrg org = trainOrgService.selectByOrgNo(loginName);
		Long orgId = org.getId();
		model.addAttribute("pid", pid);
		model.addAttribute("orgId", orgId);
		model.addAttribute("down_url", JERSEY_FILE_SYS_URL_DOWNLOAD);
		
		//1、需求分析
		ProjectFile objFile1 = null;
		List<ProjectFile> objFile1s = projectFileService.selectByPidOrgType(pid, orgId, OrgFileEnum.XUQIU.getValue());
		if(objFile1s!=null &&objFile1s.size()>0){
			objFile1 = objFile1s.get(0);
			String fileId = objFile1.getFileId();
			String downloadUrl = JERSEY_FILE_SYS_URL_DOWNLOAD+fileId;
			objFile1.setFileId(downloadUrl);
		}
		model.addAttribute("objFile1", objFile1);
		
		//2、实施方案
		ProjectFile objFile2 = null;
		List<ProjectFile> objFile2s = projectFileService.selectByPidOrgType(pid, orgId, OrgFileEnum.SHISHI.getValue());
		if(objFile2s!=null &&objFile2s.size()>0){
			objFile2 = objFile2s.get(0);
			String fileId = objFile2.getFileId();
			String downloadUrl = JERSEY_FILE_SYS_URL_DOWNLOAD+fileId;
			objFile2.setFileId(downloadUrl);
		}
		model.addAttribute("objFile2", objFile2);
		
		
		//3、参训通知
		ProjectFile objFile3 = null;
		List<ProjectFile> objFile3s = projectFileService.selectByPidOrgType(pid, orgId, OrgFileEnum.TONGZHI.getValue());
		if(objFile3s!=null &&objFile3s.size()>0){
			objFile3 = objFile3s.get(0);
			String fileId = objFile3.getFileId();
			String downloadUrl = JERSEY_FILE_SYS_URL_DOWNLOAD+fileId; 
			objFile3.setFileId(downloadUrl);
		}
		model.addAttribute("objFile3", objFile3);
		
		//4、执行课表
		ProjectFile objFile4 = null;
		List<ProjectFile> objFile4s = projectFileService.selectByPidOrgType(pid, orgId, OrgFileEnum.KEBIAO.getValue());
		if(objFile4s!=null &&objFile4s.size()>0){
			objFile4 = objFile4s.get(0);
			String fileId = objFile4.getFileId();
			String downloadUrl = JERSEY_FILE_SYS_URL_DOWNLOAD+fileId;
			objFile4.setFileId(downloadUrl);
		}
		model.addAttribute("objFile4", objFile4);
		
		/*XUQIU("需求分析", 1), 
		SHISHI("实施方案", 2), 
		TONGZHI("参训通知", 3),
		KEBIAO("执行课表", 4),
		MINGDAN("参训学员名单", 5),
		PINJIA("教学质量评价（限集中培训）", 6),
		BAOGAO("绩效报告", 7),
		ZIYUAN("典型资源", 8);*/
		
		//5、参训学员名单
		ProjectFile objFile5 = null;
		List<ProjectFile> objFile5s = projectFileService.selectByPidOrgType(pid, orgId, OrgFileEnum.MINGDAN.getValue());
		if(objFile5s!=null &&objFile5s.size()>0){
			objFile5 = objFile5s.get(0);
			String fileId = objFile5.getFileId();
			String downloadUrl = JERSEY_FILE_SYS_URL_DOWNLOAD+fileId;
			objFile5.setFileId(downloadUrl);
		}
		model.addAttribute("objFile5", objFile5);
		
		//6、教学质量评价（限集中培训）
		ProjectFile objFile6 = null;
		List<ProjectFile> objFile6s = projectFileService.selectByPidOrgType(pid, orgId, OrgFileEnum.PINJIA.getValue());
		if(objFile6s!=null &&objFile6s.size()>0){
			objFile6 = objFile6s.get(0);
			String fileId = objFile6.getFileId();
			String downloadUrl = JERSEY_FILE_SYS_URL_DOWNLOAD+fileId;
			objFile6.setFileId(downloadUrl);
		}
		model.addAttribute("objFile6", objFile6);
		
		//7、绩效报告
		ProjectFile objFile7 = null;
		List<ProjectFile> objFile7s = projectFileService.selectByPidOrgType(pid, orgId, OrgFileEnum.BAOGAO.getValue());
		if(objFile7s!=null &&objFile7s.size()>0){
			objFile7 = objFile7s.get(0);
			String fileId = objFile7.getFileId();
			String downloadUrl = JERSEY_FILE_SYS_URL_DOWNLOAD+fileId;
			objFile7.setFileId(downloadUrl);
		}
		model.addAttribute("objFile7", objFile7);
		
		//8、典型资源
		ProjectFile objFile8 = null;
		List<ProjectFile> objFile8s = projectFileService.selectByPidOrgType(pid, orgId, OrgFileEnum.ZIYUAN.getValue());
		if(objFile8s!=null &&objFile8s.size()>0){
			objFile8 = objFile8s.get(0);
			String fileId = objFile8.getFileId();
			String downloadUrl = JERSEY_FILE_SYS_URL_DOWNLOAD+fileId;
			objFile8.setFileId(downloadUrl);
		}
		model.addAttribute("objFile8", objFile8);
		
		if(type==1){
			return "core/train/project/file/orgFileView";
		}
		return "core/train/project/file/orgFileCreate";
	}
	
	
	/**
	 * 上传 1 需求分析
	 * @param file
	 * @param pid
	 * @param orgId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/upload1",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String upload1(@RequestParam(required = false) MultipartFile file,
			@RequestParam Long pid, @RequestParam Long orgId)
			throws IOException {
		AjaxCallback ok = AjaxCallback.OK("上传");
		
		long size = file.getSize();
		System.out.println(size);
		if(size>FILE_MAX_SIZE){
			ok.setStatusCode(AjaxCallback.ERROR);
			ok.setMessage("文件不能超过"+FILE_MAX_SIZE/1000000+"M");
			return JSON.toJSONString(ok);
		}
		
		Map<String, Object> map = Maps.newHashMap();
		String fileId = JerseyUtils.makeFileId(file);
		
		String downloadUrl = JERSEY_FILE_SYS_URL_DOWNLOAD+fileId;
		JerseyUtils.uploadFile(file, JERSEY_FILE_SYS_URL_UPLOAD+fileId);
		
		map.put("fileId", fileId);	
		map.put("downloadUrl", downloadUrl);
		
		ProjectFile projectFile = new ProjectFile();
		projectFile.setPid(pid);
		projectFile.setFileName(file.getOriginalFilename());
		projectFile.setOrgId(orgId);
		projectFile.setType(OrgFileEnum.XUQIU.getValue());
		projectFile.setFileId(fileId);
		projectFile.setStatus(OrgFileStatusEnum.UPLOAD.getValue());

		Long saveFileId = projectFileService.saveFile(projectFile);
		map.put("projectFileId", saveFileId);

		
		ok.setData(map);
		return JSON.toJSONString(ok);
	}

	/**
	 * 删除1 需求分析
	 */
	@RequestMapping("/deleteFile1/{projectFileId}")
	@ResponseBody
	public String deleteFile1(@PathVariable Long projectFileId){
		projectFileService.deleteById(projectFileId);
		AjaxCallback ok = AjaxCallback.OK("删除成功");
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 上传 2 实施方案
	 * @param file
	 * @param pid
	 * @param orgId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/upload2",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String upload2(@RequestParam(required = false) MultipartFile file,
			@RequestParam Long pid, @RequestParam Long orgId)
			throws IOException {
		
		AjaxCallback ok = AjaxCallback.OK("上传");
		
		long size = file.getSize();
		System.out.println(size);
		if(size>FILE_MAX_SIZE){
			ok.setStatusCode(AjaxCallback.ERROR);
			ok.setMessage("文件不能超过"+FILE_MAX_SIZE/1000000+"M");
			return JSON.toJSONString(ok);
		}
		
		Map<String, Object> map = Maps.newHashMap();
		String fileId = JerseyUtils.makeFileId(file);
		
		String downloadUrl = JERSEY_FILE_SYS_URL_DOWNLOAD+fileId;
		JerseyUtils.uploadFile(file, JERSEY_FILE_SYS_URL_UPLOAD+fileId);
		
		map.put("fileId", fileId);	
		map.put("downloadUrl", downloadUrl);
		
		ProjectFile projectFile = new ProjectFile();
		projectFile.setPid(pid);
		projectFile.setFileName(file.getOriginalFilename());
		projectFile.setOrgId(orgId);
		projectFile.setType(OrgFileEnum.SHISHI.getValue());
		projectFile.setFileId(fileId);
		projectFile.setStatus(OrgFileStatusEnum.UPLOAD.getValue());

		Long saveFileId = projectFileService.saveFile(projectFile);
		map.put("projectFileId", saveFileId);

		ok.setData(map);
		return JSON.toJSONString(ok);
	}

	
	/**
	 * 删除2实施方案
	 */
	@RequestMapping("/deleteFile2/{projectFileId}")
	@ResponseBody
	public String deleteFile2(@PathVariable Long projectFileId){
		projectFileService.deleteById(projectFileId);
		AjaxCallback ok = AjaxCallback.OK("删除成功");
		return JSON.toJSONString(ok);
	}
	
	
	/**
	 * 上传 3 参训通知
	 * @param file
	 * @param pid
	 * @param orgId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/upload3",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String upload3(@RequestParam(required = false) MultipartFile file,
			@RequestParam Long pid, @RequestParam Long orgId)
			throws IOException {
		
		AjaxCallback ok = AjaxCallback.OK("上传");
		
		long size = file.getSize();
		System.out.println(size);
		if(size>FILE_MAX_SIZE){
			ok.setStatusCode(AjaxCallback.ERROR);
			ok.setMessage("文件不能超过"+FILE_MAX_SIZE/1000000+"M");
			return JSON.toJSONString(ok);
		}
		
		Map<String, Object> map = Maps.newHashMap();
		String fileId = JerseyUtils.makeFileId(file);
		
		String downloadUrl = JERSEY_FILE_SYS_URL_DOWNLOAD+fileId;
		JerseyUtils.uploadFile(file, JERSEY_FILE_SYS_URL_UPLOAD+fileId);
		
		map.put("fileId", fileId);	
		map.put("downloadUrl", downloadUrl);
		
		ProjectFile projectFile = new ProjectFile();
		projectFile.setPid(pid);
		projectFile.setFileName(file.getOriginalFilename());
		projectFile.setOrgId(orgId);
		projectFile.setType(OrgFileEnum.TONGZHI.getValue());
		projectFile.setFileId(fileId);
		projectFile.setStatus(OrgFileStatusEnum.UPLOAD.getValue());

		Long saveFileId = projectFileService.saveFile(projectFile);
		map.put("projectFileId", saveFileId);

		ok.setData(map);
		return JSON.toJSONString(ok);
		
	}

	
	/**
	 * 删除3 参训通知
	 */
	@RequestMapping("/deleteFile3/{projectFileId}")
	@ResponseBody
	public String deleteFile3(@PathVariable Long projectFileId){
		projectFileService.deleteById(projectFileId);
		AjaxCallback ok = AjaxCallback.OK("删除成功");
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 上传 4 执行课表
	 * @param file
	 * @param pid
	 * @param orgId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/upload4",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String upload4(@RequestParam(required = false) MultipartFile file,
			@RequestParam Long pid, @RequestParam Long orgId)
			throws IOException {
		
		AjaxCallback ok = AjaxCallback.OK("上传");
		
		long size = file.getSize();
		System.out.println(size);
		if(size>FILE_MAX_SIZE){
			ok.setStatusCode(AjaxCallback.ERROR);
			ok.setMessage("文件不能超过"+FILE_MAX_SIZE/1000000+"M");
			return JSON.toJSONString(ok);
		}
		
		/**
		 * 导入专家信息
		 */
		try {
			expertService.importExcel(file);
		} catch (Exception e) {
			e.printStackTrace();
			ok = AjaxCallback.ERROR(e.getMessage());
			return JSON.toJSONString(ok);
		}

		Map<String, Object> map = Maps.newHashMap();
		String fileId = JerseyUtils.makeFileId(file);
		
		String downloadUrl = JERSEY_FILE_SYS_URL_DOWNLOAD+fileId;
		JerseyUtils.uploadFile(file, JERSEY_FILE_SYS_URL_UPLOAD+fileId);
		
		map.put("fileId", fileId);	
		map.put("downloadUrl", downloadUrl);
		
		ProjectFile projectFile = new ProjectFile();
		projectFile.setPid(pid);
		projectFile.setFileName(file.getOriginalFilename());
		projectFile.setOrgId(orgId);
		projectFile.setType(OrgFileEnum.KEBIAO.getValue());
		projectFile.setFileId(fileId);
		projectFile.setStatus(OrgFileStatusEnum.UPLOAD.getValue());

		Long saveFileId = projectFileService.saveFile(projectFile);
		map.put("projectFileId", saveFileId);

		ok.setData(map);
		return JSON.toJSONString(ok);
		
	}

	
	/**
	 * 删除4 执行课表
	 */
	@RequestMapping("/deleteFile4/{projectFileId}")
	@ResponseBody
	public String deleteFile4(@PathVariable Long projectFileId){
		projectFileService.deleteById(projectFileId);
		AjaxCallback ok = AjaxCallback.OK("删除成功");
		return JSON.toJSONString(ok);
	}
	
	
	/**
	 * 上传 5 参训学员名单
	 * @param file
	 * @param pid
	 * @param orgId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/upload5",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String upload5(@RequestParam(required = false) MultipartFile file,
			@RequestParam Long pid, @RequestParam Long orgId)
			throws IOException {
		
		AjaxCallback ok = AjaxCallback.OK("上传");
		
		long size = file.getSize();
		System.out.println(size);
		if(size>FILE_MAX_SIZE){
			ok.setStatusCode(AjaxCallback.ERROR);
			ok.setMessage("文件不能超过"+FILE_MAX_SIZE/1000000+"M");
			return JSON.toJSONString(ok);
		}
		
		Map<String, Object> map = Maps.newHashMap();
		String fileId = JerseyUtils.makeFileId(file);
		
		String downloadUrl = JERSEY_FILE_SYS_URL_DOWNLOAD+fileId;
		JerseyUtils.uploadFile(file, JERSEY_FILE_SYS_URL_UPLOAD+fileId);
		
		map.put("fileId", fileId);	
		map.put("downloadUrl", downloadUrl);
		
		ProjectFile projectFile = new ProjectFile();
		projectFile.setPid(pid);
		projectFile.setFileName(file.getOriginalFilename());
		projectFile.setOrgId(orgId);
		projectFile.setType(OrgFileEnum.MINGDAN.getValue());
		projectFile.setFileId(fileId);
		projectFile.setStatus(OrgFileStatusEnum.UPLOAD.getValue());

		Long saveFileId = projectFileService.saveFile(projectFile);
		map.put("projectFileId", saveFileId);

		ok.setData(map);
		return JSON.toJSONString(ok);
	}

	
	/**
	 * 删除5 参训学员名单
	 */
	@RequestMapping("/deleteFile5/{projectFileId}")
	@ResponseBody
	public String deleteFile5(@PathVariable Long projectFileId){
		projectFileService.deleteById(projectFileId);
		AjaxCallback ok = AjaxCallback.OK("删除成功");
		return JSON.toJSONString(ok);
	}
	
	
	/**
	 * 上传 6 教学质量评价
	 * @param file
	 * @param pid
	 * @param orgId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/upload6",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String upload6(@RequestParam(required = false) MultipartFile file,
			@RequestParam Long pid, @RequestParam Long orgId)
			throws IOException {
		AjaxCallback ok = AjaxCallback.OK("上传");
		
		long size = file.getSize();
		System.out.println(size);
		if(size>FILE_MAX_SIZE){
			ok.setStatusCode(AjaxCallback.ERROR);
			ok.setMessage("文件不能超过"+FILE_MAX_SIZE/1000000+"M");
			return JSON.toJSONString(ok);
		}
		
		Map<String, Object> map = Maps.newHashMap();
		String fileId = JerseyUtils.makeFileId(file);
		
		String downloadUrl = JERSEY_FILE_SYS_URL_DOWNLOAD+fileId;
		JerseyUtils.uploadFile(file, JERSEY_FILE_SYS_URL_UPLOAD+fileId);
		
		map.put("fileId", fileId);	
		map.put("downloadUrl", downloadUrl);
		
		ProjectFile projectFile = new ProjectFile();
		projectFile.setPid(pid);
		projectFile.setFileName(file.getOriginalFilename());
		projectFile.setOrgId(orgId);
		projectFile.setType(OrgFileEnum.PINJIA.getValue());
		projectFile.setFileId(fileId);
		projectFile.setStatus(OrgFileStatusEnum.UPLOAD.getValue());

		Long saveFileId = projectFileService.saveFile(projectFile);
		map.put("projectFileId", saveFileId);

		ok.setData(map);
		return JSON.toJSONString(ok);

	}

	
	/**
	 * 删除6 教学质量评价
	 */
	@RequestMapping("/deleteFile6/{projectFileId}")
	@ResponseBody
	public String deleteFile6(@PathVariable Long projectFileId){
		projectFileService.deleteById(projectFileId);
		AjaxCallback ok = AjaxCallback.OK("删除成功");
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 上传 7 绩效报告
	 * @param file
	 * @param pid
	 * @param orgId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/upload7",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String upload7(@RequestParam(required = false) MultipartFile file,
			@RequestParam Long pid, @RequestParam Long orgId)
			throws IOException {
		
		AjaxCallback ok = AjaxCallback.OK("上传");
		
		long size = file.getSize();
		System.out.println(size);
		if(size>FILE_MAX_SIZE){
			ok.setStatusCode(AjaxCallback.ERROR);
			ok.setMessage("文件不能超过"+FILE_MAX_SIZE/1000000+"M");
			return JSON.toJSONString(ok);
		}

		Map<String, Object> map = Maps.newHashMap();
		String fileId = JerseyUtils.makeFileId(file);
		
		String downloadUrl = JERSEY_FILE_SYS_URL_DOWNLOAD+fileId;
		JerseyUtils.uploadFile(file, JERSEY_FILE_SYS_URL_UPLOAD+fileId);
		
		map.put("fileId", fileId);	
		map.put("downloadUrl", downloadUrl);
		
		ProjectFile projectFile = new ProjectFile();
		projectFile.setPid(pid);
		projectFile.setFileName(file.getOriginalFilename());
		projectFile.setOrgId(orgId);
		projectFile.setType(OrgFileEnum.BAOGAO.getValue());
		projectFile.setFileId(fileId);
		projectFile.setStatus(OrgFileStatusEnum.UPLOAD.getValue());

		Long saveFileId = projectFileService.saveFile(projectFile);
		map.put("projectFileId", saveFileId);

		ok.setData(map);
		return JSON.toJSONString(ok);
		
	}

	
	/**
	 * 删除7 绩效报告
	 */
	@RequestMapping("/deleteFile7/{projectFileId}")
	@ResponseBody
	public String deleteFile7(@PathVariable Long projectFileId){
		projectFileService.deleteById(projectFileId);
		AjaxCallback ok = AjaxCallback.OK("删除成功");
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 上传 8 绩效报告
	 * @param file
	 * @param pid
	 * @param orgId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/upload8",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String upload8(@RequestParam(required = false) MultipartFile file,
			@RequestParam Long pid, @RequestParam Long orgId)
			throws IOException {

		AjaxCallback ok = AjaxCallback.OK("上传");
		
		long size = file.getSize();
		System.out.println(size);
		if(size>FILE_MAX_SIZE){
			ok.setStatusCode(AjaxCallback.ERROR);
			ok.setMessage("文件不能超过"+FILE_MAX_SIZE/1000000+"M");
			return JSON.toJSONString(ok);
		}
		
		Map<String, Object> map = Maps.newHashMap();
		String fileId = JerseyUtils.makeFileId(file);
		
		String downloadUrl = JERSEY_FILE_SYS_URL_DOWNLOAD+fileId;
		JerseyUtils.uploadFile(file, JERSEY_FILE_SYS_URL_UPLOAD+fileId);
		
		map.put("fileId", fileId);	
		map.put("downloadUrl", downloadUrl);
		
		ProjectFile projectFile = new ProjectFile();
		projectFile.setPid(pid);
		projectFile.setFileName(file.getOriginalFilename());
		projectFile.setOrgId(orgId);
		projectFile.setType(OrgFileEnum.ZIYUAN.getValue());
		projectFile.setFileId(fileId);
		projectFile.setStatus(OrgFileStatusEnum.UPLOAD.getValue());

		Long saveFileId = projectFileService.saveFile(projectFile);
		map.put("projectFileId", saveFileId);

		ok.setData(map);
		return JSON.toJSONString(ok);
		
	}

	
	/**
	 * 删除8 绩效报告
	 */
	@RequestMapping("/deleteFile8/{projectFileId}")
	@ResponseBody
	public String deleteFile8(@PathVariable Long projectFileId){
		projectFileService.deleteById(projectFileId);
		AjaxCallback ok = AjaxCallback.OK("删除成功");
		return JSON.toJSONString(ok);
	}
}
