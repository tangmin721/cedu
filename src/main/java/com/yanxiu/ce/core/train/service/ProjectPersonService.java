package com.yanxiu.ce.core.train.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.train.entity.ProjectPerson;
import com.yanxiu.ce.core.train.entity.ProjectPersonQuery;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 参培人名单管理
 * @author tangmin
 * @date 2016-10-24 16:32:04
 */
public interface ProjectPersonService extends BaseService<ProjectPerson, ProjectPersonQuery>{
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
	Boolean checkNameExit(ProjectPerson entity);

	/**
	 * 校验是否存在
	 * @param
	 * @return
	 */
	Boolean checkPidTidExit(ProjectPerson entity);

	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(ProjectPerson entity);

	/**
	 * 验证excle
	 * @param file
	 * @return
	 */
    List<String> checkExcel(MultipartFile file,Long pid);

	/**
	 * 根据pid，获取一共需要生成的学时记录条数
	 * @param pid
	 * @return
	 */
	Integer selectMakeScoreTotalNum(Long pid);

	/**
	 * 开始生成学时方法，需要返回总条数，和当前进程的redisKey
	 * @param pid
	 * @return
	 */
	Map<String,String> startMakeScore(Long pid);

	/**
	 * 生成学时  线程
	 * @param pid
	 */
	void makeScore(Long pid,String redisKey);

	/**
	 * 生成学时，用于导入后，直接生成
	 * @param pid
	 */
	void makeScoreOnly(Long pid);

	/**
	 * 取消生成学时，把已生成的学时删除掉，并把redisKey的值置为-1
	 * @param pid
	 * @param redisKey
	 */
	void cancelMakeScore(Long pid,String redisKey);

	/**
	 * 根据pid删除学分
	 * @param pid
	 * @return
	 */
	Long deleteScoreByPid(Long pid);

	/**
	 * 根据tid,pid删除学分
	 * @param pid
	 * @return
	 */
	Long deleteScoreByTidPid(Long tid,Long pid);

	List<ProjectPerson> selectMakeScoreProjectPerson(Long pid);

	/**
	 * 判断某个老师是否有某个项目学时
	 * @param tid
	 * @param pid
	 * @return
	 */
	Boolean checkScoreExist(Long tid, Long pid);


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
	List<List<String>> getExcelDatasByQuery(ProjectPersonQuery query);

	Integer importExcel(MultipartFile file, Long pid,Long oid);
}
