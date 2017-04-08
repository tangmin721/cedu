package com.yanxiu.ce.core.statistics.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanxiu.ce.core.score.entity.Score;
import com.yanxiu.ce.core.score.entity.ScoreQuery;
import com.yanxiu.ce.core.score.enums.ScoreStatusEnum;
import com.yanxiu.ce.core.score.enums.ScoreTypeEnum;
import com.yanxiu.ce.core.score.service.ScoreService;
import com.yanxiu.ce.core.statistics.dao.ReportScoreDao;
import com.yanxiu.ce.core.statistics.dto.NameValue;
import com.yanxiu.ce.core.statistics.service.ReportScoreService;
import com.yanxiu.ce.system.entity.Config;
import com.yanxiu.ce.system.service.ConfigService;

/**
 * 学时统计管理
 * @author tangmin
 * @date 2016-08-22 12:22:10
 */
@Service("reportScoreService")
public class ReportScoreServiceImpl implements ReportScoreService{
	private static final String NULL = "未填";
	
	@Autowired
	private ReportScoreDao dao;
	
	@Autowired
	private ScoreService scoreService;
	
	@Autowired
	private ConfigService configService;
	
	/**
	 * 一次批量处理的个数
	 */
	private static final Integer BATCH_SIZE = 2;


	@Override
	public List<Long> selectUnsyncTids(Integer size) {
		return this.dao.selectUnsyncTids(size);
	}

	@Override
	public Integer sumScoreByTid(Long tid) {
		return this.dao.sumScoreByTid(tid);
	}

	@Override
	public void updateTotalScore(Integer totalScore, Long tid) {
		this.dao.updateTotalScore(totalScore, tid);
		
	}
	
	@Override
	public void updateTotalScoreNum(Double totalScoreNum, Long tid) {
		this.dao.updateTotalScoreNum(totalScoreNum, tid);
		
	}

	@Override
	public void updateSyncFlag(Long tid) {
		this.dao.updateSyncFlag(tid);
	}

	/**
	 * 学时汇总
	 */
	@Override
	@Transactional
	public void jobTotalScore() {
		//总的未处理tid数
		Integer totalNum = this.dao.countUnsyncTids();
		
		
		int sheetNum = 0;           //指定需要处理的次数（sheet页数）
		if (totalNum % BATCH_SIZE == 0) {
			sheetNum = totalNum / BATCH_SIZE;
		} else {
			sheetNum = totalNum / BATCH_SIZE + 1;
		}
		
		//转换学分start
		Config cRait = configService.selectByTheKey("C_RAIT");
		Config ucRait = configService.selectByTheKey("UC_RAIT");
		
		String[] splitCRait = cRait.getTheValue().split(":");
		String[] splitUCRait = ucRait.getTheValue().split(":");
		
		for (int i = 1; i <= sheetNum; i++) {//循环2个sheet的值
			List<Long> tids = this.selectUnsyncTids(BATCH_SIZE);
			for(Long tid:tids){
				syncTeacherScore(tid,splitCRait,splitUCRait);
			}
		}
	}

	
	@Override
	@Transactional
	public Integer syncTeacherScore(Long tid,String[] splitCRait,String[] splitUCRait) {
		Integer score = this.sumScoreByTid(tid);
		if(score==null){
			score = 0;
		}
		
		ScoreQuery query = new ScoreQuery();
		query.setTid(tid.toString());
		query.setStatus(String.valueOf(ScoreStatusEnum.CHECK_END.getValue()));
		List<Score> scoreList = this.scoreService.selectList(query);
		
		Integer totalScore = 0;
		Double totalScoreNum = 0.0;
		for(Score s:scoreList){
			Double sc = s.getScore().doubleValue();
			Double scNum = 0.0;
			if(s.getType()==0){
				scNum = sc * Double.valueOf(splitCRait[1])/Double.valueOf(splitCRait[0]);
			}else{
				scNum = sc * Double.valueOf(splitUCRait[1])/Double.valueOf(splitUCRait[0]);
			}
			BigDecimal   b   =   new   BigDecimal(scNum);  
			Double   result   =   b.setScale(1,   BigDecimal.ROUND_HALF_UP).doubleValue();  
			totalScoreNum += result;
			totalScore += s.getScore();
			s.setScoreNum(result.toString());
		}
		//转换学分end
		System.out.println(totalScore +"::::"+ score );
		this.updateTotalScore(score, tid);
		this.updateTotalScoreNum(totalScoreNum, tid);
		this.updateSyncFlag(tid);
		return score;
	}

	@Override
	public List<NameValue> groupByProperty(String iname,String property, String tableName,String joinOnName,
			Integer province, Integer city, Integer town, Long school,
			String startDate, String endDate, Integer startScore,
			Integer endScore) {
		List<NameValue> nvs = this.dao.groupByProperty(iname,property, tableName,joinOnName, province, city, town, school, startDate, endDate, startScore, endScore);
		for(NameValue nv:nvs){
			if(StringUtils.isBlank(nv.getName())){
				nv.setName(NULL);
			}
		}
		return nvs;
	}

	
	
}