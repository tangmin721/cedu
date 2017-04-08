package com.yanxiu.ce.core.train.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.TeacherQuery;
import com.yanxiu.ce.core.train.entity.Expert;
import com.yanxiu.ce.core.train.entity.ExpertQuery;

/**
 * 专家信息管理
 * @author tangmin
 * @date 2016-07-29 15:49:04
 */
public interface ExpertService extends BaseService<Expert, ExpertQuery>{
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
	Boolean checkNameExit(Expert entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(Expert entity);
	
	/**
	 * 验证excel
	 * @param file
	 * @throws Exception
	 */
	List<String> checkExcel(MultipartFile file);
	
	/**
	 * 从Excel中导入
	 * @param file
	 * @throws Exception
	 */
	Long importExcel(MultipartFile file) throws ParseException;
	
	/**
	 * 获取导出表头
	 * @return
	 * @throws Exception
	 */
	List<String> getExcelFieldName();
	
	/**
	 * 导出excel
	 * 
	 * @param query
	 * @return
	 */
	List<List<String>> getExcelDatasByQuery(ExpertQuery query) ;

}
