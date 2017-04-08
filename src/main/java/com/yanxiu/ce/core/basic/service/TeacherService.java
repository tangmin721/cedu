package com.yanxiu.ce.core.basic.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.common.yanxiuapi.user.YanxiuUserRegister;
import com.yanxiu.ce.common.yanxiuapi.user.YanxiuUserSync;
import com.yanxiu.ce.core.basic.dto.TeacherSaveDto;
import com.yanxiu.ce.core.basic.entity.Teacher;
import com.yanxiu.ce.core.basic.entity.TeacherQuery;

/**
 * 教师管理
 * @author tangmin
 * @date 2016-03-30 18:03:20
 */
public interface TeacherService extends BaseService<Teacher, TeacherQuery>{
	
	/**
	 * 根据idcard查找
	 * @param idCard
	 * @return
	 */
	Teacher selectByIdCard(String idCard);
	

	/**
	 * 根据idcard查找
	 * @param idCard
	 * @return
	 */
	Teacher selectByTno(String tno);
	
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
	Boolean checkIdCardExit(Teacher entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	TeacherSaveDto saveOrUpdate(Teacher entity);
	
	/**
	 * 修改学校的省市县，则需要同时修改其下属的教师的省市县。根据school批量修改教师的省市县
	 * @param province
	 * @param city
	 * @param town
	 * @return
	 */
	Long updateBatchSchool(Integer province,Integer city,Integer town,Long school);
	
	
	/**************************excel start*****************************************/
	
	/**
	 * 获取教师信息导出表头
	 * @return
	 * @throws Exception
	 */
	List<String> getExcelFieldName(Integer schoolType);
	
	/**
	 * 获取校长信息导出表头
	 * @return
	 * @throws Exception
	 */
	List<String> getMasterExcelFieldName();
	
	/**
	 * 根据查询条件获取导出表格数据
	 * @param query
	 * @return
	 */
	List<List<String>> getExcelDatasByQuery(TeacherQuery query);
	
	/**
	 * 转换list
	 * @param teachers
	 * @return
	 */
	public List<List<String>> changeObjToStringList(List<Teacher> teachers, String schoolType);
	
	/**
	 * 验证excel
	 * @param file
	 * @throws Exception
	 */
	List<String> checkExcel(MultipartFile file,TeacherQuery query);
	
	/**
	 * 从Excel中导入
	 * @param file
	 * @throws Exception
	 */
	Integer importExcel(MultipartFile file,TeacherQuery query) throws ParseException;
	
	/**
	 * 不用验证的save方法
	 * @param entity
	 */
	Long saveImpTeacher(Teacher entity);
	/**************************excel start*****************************************/
	
	/**
	 * 转换教师->注册信息
	 * @param teacher
	 * @return
	 */
	public YanxiuUserRegister takeRegisterInfo(Teacher teacher);
	
	/**
	 * 转换教师->同步信息
	 * @param teacher
	 * @return
	 */
	public YanxiuUserSync takeSyncInfo(Teacher teacher);
	
	/**
	 * 查询教师详细信息
	 * 
	 */
	public Teacher selectViewInfo(Long id);
	
	/**
	 * 教师信息删除，数据库只进行标记，不删除数据
	 */
	Long removeByIds(List<Long> ids);
}
