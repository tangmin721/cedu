package com.yanxiu.ce.system.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.yanxiu.ce.common.core.controller.BaseController;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.fastdfs.jersey.JerseyUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * 文件上传 下载 删除 公共类
 * @author tangmin
 * @date 2016年12月15日
 */
@Controller
@RequestMapping("/system/upload")
public class UploadFileController extends BaseController{

    @Value("${JERSEY_FILE_SYS_URL_DOWNLOAD}")
    private String JERSEY_FILE_SYS_URL_DOWNLOAD;

    @Value("${JERSEY_FILE_SYS_URL_UPLOAD}")
    private String JERSEY_FILE_SYS_URL_UPLOAD;

    @RequestMapping(value = "upload.do", method = RequestMethod.POST,produces="text/html;charset=UTF-8")
    public @ResponseBody
    String upload(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {

        AjaxCallback ok = AjaxCallback.OK("上传成功");

        long size = file.getSize();
        System.out.println(size);
        if(size>60*1024){
            ok.setStatusCode(AjaxCallback.ERROR);
            ok.setMessage("文件不能超过"+60+"K");
            return JSON.toJSONString(ok);
        }

        Map<String, Object> map = Maps.newHashMap();
        String fileId = JerseyUtils.makeFileId(file);

        String downloadUrl = JERSEY_FILE_SYS_URL_DOWNLOAD+fileId;
        JerseyUtils.uploadFile(file, JERSEY_FILE_SYS_URL_UPLOAD+fileId);

        map.put("fileId", fileId);
        map.put("downloadUrl", downloadUrl);
        ok.setData(map);
        return JSON.toJSONString(ok);
    }
}
