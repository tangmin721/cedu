package com.yanxiu.ce.core.score.service;

import com.yanxiu.ce.common.core.service.BaseService;
import com.yanxiu.ce.core.score.dto.CheckScoreDto;
import com.yanxiu.ce.core.score.entity.Score;
import com.yanxiu.ce.core.score.entity.ScoreMineDetail;
import com.yanxiu.ce.core.score.entity.ScoreQuery;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 学时申报管理
 * @author tangmin
 * @date 2016-08-02 14:59:01
 */
public interface ScoreService extends BaseService<Score, ScoreQuery>{
	
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
	Boolean checkNameExit(Score entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(Score entity);
	
	/**
	 * 保存或添加
	 * @return
	 */
	String saveOrUpdate(Score entity,List<ScoreMineDetail> details);
	
	/**
	 * 审批通过的保存
	 * @param id
	 * @param checkDesc
	 * @param status
	 * @return
	 */
	String saveStatus(Long id,String checkDesc,Integer status);
	

	/**
	 * 批量审批通过
	 * @param ids
	 * @return
	 */
	Long checkMultiByIds(List<Long> ids,String checkDesc,Integer status);
	
	/**
	 * checkOneKey
	 */
	Long checkOneKey(String checkDesc,Integer status,Integer province,Integer city,Integer town,Long school,Integer currentStatus);

	/**
	 * 验证学时申报
	 * @param entity
	 * @return
	 */
	CheckScoreDto checkScore(Score entity) throws ParseException;

    /**
     * 计算一个老师 本教育周期内获取 某种 scoreType
     * 用于校验 新增  和 其他统计
     * @param scoreType
     * @param tid
     * @return
     */
    Integer countScoreInyear(Date date,Integer scoreType,Long tid) throws ParseException;

    /**
     * 计算一个老师 除了本条score申报记录  其他本教育周期内获取集中OR非集中培训的学时
     * 仅用于校验  修改申报记录
     * @param scoreType
     * @param id
     * @param tid
     * @return
     */
    Integer countUpdateScoreInTyear(Date date,Integer scoreType,Long id,Long tid) throws ParseException;


	/**
	 * 学校管理员，县级管理员 修改 老师的学时
	 * @param score
	 * @return
	 */
	String adminUpdate(Score score);

	/**
	 * 校验是否存在
	 * @param
	 * @return
	 */
	Boolean checkPidTidExit(Score entity);
	
	/**
	 * 自己的delete，主要是需要判断form8，删除
	 * @param id
	 */
	void deleteScore(Long id);
}
