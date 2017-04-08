package com.yanxiu.ce.core.basic.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.basic.entity.School;
import com.yanxiu.ce.core.basic.entity.SchoolQuery;

/**
 * 学校管理
 * @author tangmin
 * @date 2016-03-22 19:04:55
 */
public interface SchoolService extends BaseService<School, SchoolQuery>{
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
	Boolean checkNameExit(School entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(School entity);
	
	
	/**
	 * Excel导入的时候，单条数据数据的保存，用dao保存，不用校验了，导入之前已经校验过了
	 * @return
	 */
	void saveImpSchool(School entity);
	
	
	/**
	 * 根据twonNo获取School下拉列表框
	 * @param twonNo
	 * @return
	 */
	List<School> schools(Integer town);
	
	/**
	 * 获取导出表头
	 * @return
	 * @throws Exception
	 */
	List<String> getExcelFieldName();
	
	/**
	 * 根据查询条件获取导出表格数据
	 * @param query
	 * @return
	 */
	List<List<String>> getExcelDatasByQuery(SchoolQuery query);
	
	/**
	 * 根据ids获取导出表格数据
	 * @param ids
	 * @return
	 */
	List<List<String>> getExcelDatasByIds(List<Long> ids);
	
	/**
	 * 验证excel
	 * @param file
	 * @throws Exception
	 */
	List<String> checkExcel(MultipartFile file,SchoolQuery query);
	
	/**
	 * 从Excel中导入
	 * @param file
	 * @throws Exception
	 */
	Integer importExcel(MultipartFile file,SchoolQuery query);
	
	
	/**
	 * 区县下school的 Map  <名称  schoolid>，名词 id的对应关系，用于excel比对
	 * @return
	 */
	Map<String,Long> schoolMap(Integer townNo);
	
	/**
	 * 同步redis缓存
	 * @param townNo
	 * @return
	 */
	public Long syncRedisSchoolMap(Integer townNo);
	
	

}
