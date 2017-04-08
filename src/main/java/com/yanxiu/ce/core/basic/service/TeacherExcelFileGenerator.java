package com.yanxiu.ce.core.basic.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.yanxiu.ce.core.basic.entity.TeacherQuery;

/**
 * 根据数据库分批查询，导出excel
 * @author tangmin
 * @date 2016年8月30日
 */
public interface TeacherExcelFileGenerator {
	
	public void excelExportDownload(HttpServletResponse response,
			List<String> fieldName, TeacherQuery query,String fileName, Map<String, String> sercherNameMap)
			throws UnsupportedEncodingException, IOException, Exception;

}
