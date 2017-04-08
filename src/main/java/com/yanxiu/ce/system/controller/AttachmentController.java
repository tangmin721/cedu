package com.yanxiu.ce.system.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.yanxiu.ce.common.core.controller.BaseController;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.system.entity.Attachment;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.security.ShiroUtils;
import com.yanxiu.ce.system.service.AttachmentService;

/**
 * 附件上传 下载 删除 公共类
 * @author tangmin
 * @date 2016年4月12日
 */
@Controller
@RequestMapping("/system/att")
public class AttachmentController extends BaseController{
	
	@Autowired
	private AttachmentService attService;
	
	/**
	 * 根据tableName  attId删除
	 * @param tableName
	 * @param attId
	 * @return
	 */
	@RequestMapping(value = "deleteFile", method = RequestMethod.POST)
	@ResponseBody
	public String deleteFile(String tableName,Long attId) {
		attService.deleteAtt(tableName,attId);
		return JSON.toJSONString(AjaxCallback.OK("删除成功！"));
	}
	
	/**
	 * 文件上传
	 * @param token
	 * @param file
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping(value = "upload", method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	public @ResponseBody
	String handleFileUpload(@RequestParam("file") MultipartFile[] files) throws IllegalStateException, IOException {
		logger.info("handleFileUpload");
		User user = ShiroUtils.getCurrentUser();
		List<Long> attIds = attService.createFile(files, user.getId());
		AjaxCallback ok = AjaxCallback.OK("上传成功！");
		ok.setData(attIds);
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 下载附件
	 * @param token
	 * @param id
	 * @param response
	 */
	@RequestMapping(value = "download", method = RequestMethod.GET)
	public void download(Long id,HttpServletResponse response) {
		try {
			Attachment att = attService.selectById(id);
			if (att != null) {
				logger.info("download::" + att.getName());
				OutputStream output = null;
				InputStream input = null;
				try {
					response.reset();
					String contentType = getRequest().getSession().getServletContext().getMimeType(att.getName());
					logger.info(att.getName() + " contentType==" + contentType);
					if (StringUtils.isEmpty(contentType)) {
						response.setContentType("application/octet-stream");
					} else {
						response.setContentType(contentType);
					}
					response.addHeader("Content-Length",
							String.valueOf(att.getSize()));
					response.addHeader(
							"Content-Disposition",
							"attachment; filename=\""
									+ URLEncoder.encode(att.getName(), "UTF-8")
									+ "\"");
					output = response.getOutputStream();
					input = attService.getInputStream(att);
					IOUtils.copy(input, output);
					output.flush();
				} finally {
					IOUtils.closeQuietly(output);
					IOUtils.closeQuietly(input);
				}
			} else {
				response.reset();
				IOUtils.closeQuietly(response.getOutputStream());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
