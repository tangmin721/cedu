package com.yanxiu.ce.core.score.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yanxiu.ce.common.core.controller.BaseController;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.core.score.entity.ScoreMineDetail;
import com.yanxiu.ce.core.score.entity.ScoreMineDetailQuery;
import com.yanxiu.ce.core.score.service.ScoreMineDetailService;

/**
 * 培训电子档案管理
 * @author tangmin
 * @date 2017-01-12 13:34:33
 */
@Controller
@RequestMapping("/core/score/mine/detail")
public class ScoreMineDetailController extends BaseController{
	
	@Autowired
	private ScoreMineDetailService scoreMineDetailService;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("scoreMineDetailList")
	@RequiresPermissions("ScoreMineDetail:read")
	public String list(ScoreMineDetailQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		/*可同时多列排序
		query.setOrderField(null);//先清空排序字段
		query.getOrderFields().add(new OrderField("sortField1", "asc"));
		query.getOrderFields().add(new OrderField("sortField2", "asc"));*/
		Pagination<ScoreMineDetail> page = scoreMineDetailService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		return "core/score/scoreMineDetailList";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("scoreMineDetailForm")
	@RequiresPermissions("ScoreMineDetail:create")
	public String scoreMineDetailForm(Model model){
		ScoreMineDetail entity = new ScoreMineDetail();
//		entity.setSeq(scoreMineDetailService.selectMaxSeq()+1);
		model.addAttribute("entity", entity);
		return "core/score/scoreMineDetailForm";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="scoreMineDetailForm/{id}",method = RequestMethod.POST)
	@RequiresPermissions("ScoreMineDetail:update")
	public String scoreMineDetailForm(@PathVariable Long id,Model model){
		ScoreMineDetail entity = scoreMineDetailService.selectById(id);
		model.addAttribute("entity", entity);
		return "core/score/scoreMineDetailForm";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveScoreMineDetail",method = RequestMethod.POST)
	@RequiresPermissions(value={"ScoreMineDetail:update","ScoreMineDetail:create"},logical=Logical.AND)
	@ResponseBody
	public String saveScoreMineDetail(ScoreMineDetail entity){
		AjaxCallback ok = AjaxCallback.OK(scoreMineDetailService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteScoreMineDetailByIds",method = RequestMethod.POST)
	@RequiresPermissions("ScoreMineDetail:delete")
	@ResponseBody
	public String deleteScoreMineDetailByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		scoreMineDetailService.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	
	/**
	 * 根据id删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deleteById/{id}",method = RequestMethod.POST)
	@ResponseBody
	public String deleteById(@PathVariable Long id){
		scoreMineDetailService.deleteById(id);
		AjaxCallback ok = AjaxCallback.OK("删除成功！");
		return JSON.toJSONString(ok);
	}
	

}
