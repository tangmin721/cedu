package com.yanxiu.ce.core.score.controller;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yanxiu.ce.common.core.controller.BasePctsController;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.core.score.entity.ScoreJoin;
import com.yanxiu.ce.core.score.entity.ScoreJoinQuery;
import com.yanxiu.ce.core.score.enums.ScoreStatusEnum;
import com.yanxiu.ce.core.score.service.ScoreJoinService;
import com.yanxiu.ce.system.service.DictCatlogService;

@Controller
@RequestMapping("/core/scoreJoin")
public class ScoreJoinController extends BasePctsController<ScoreJoinQuery>{
	
	@Autowired
	private ScoreJoinService scoreJoinService;
	
	@Autowired
	private DictCatlogService dictCatlogService;
	
	/**
	 * 区县待审列表(合并)
	 * @param query
	 * @param model
	 * @return
	 */
	@RequestMapping("townJoinCheckScoreList")
	@RequiresPermissions("Score:check")
	public String townJoinCheckScoreList(ScoreJoinQuery query,Model model){
		query = configPstsQuery(query);//配置省市区县查询条件

		query.setStatus(ScoreStatusEnum.TOWN_CHECKING.getValue()+"");
		
		Pagination<ScoreJoin> page = scoreJoinService.selectTownListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		
		return "core/score/join/townJoinCheckScoreList";
	}
	
	
	/**
	 * 省级待审列表(合并)
	 * @param query
	 * @param model
	 * @return
	 */
	@RequestMapping("provinceJoinCheckScoreList")
	@RequiresPermissions("Score:check")
	public String provinceJoinCheckScoreList(ScoreJoinQuery query,Model model){
		query = configPstsQuery(query);//配置省市区县查询条件

		query.setStatus(ScoreStatusEnum.PROVINCE_CHECKING.getValue()+"");
		
		Pagination<ScoreJoin> page = scoreJoinService.selectProvinceListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		
		return "core/score/join/provinceJoinCheckScoreList";
	}

}
