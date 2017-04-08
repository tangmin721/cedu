package ${CONFIG.packagePath}.controller;

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
import ${CONFIG.packagePath}.entity.${entity.className};
import ${CONFIG.packagePath}.entity.${entity.className}Query;
import ${CONFIG.packagePath}.service.${entity.className}Service;

/**
 * ${CONFIG.modelName}管理
 * @author tangmin
 * @date ${.now?string("yyyy-MM-dd HH:mm:ss")}
 */
@Controller
@RequestMapping("/${CONFIG.requestMapPath}")
public class ${entity.className}Controller extends BaseController{
	
	@Autowired
	private ${entity.className}Service ${entity.firstLowName}Service;
	
	/**
	 * 进入list页面
	 * @return
	 */
	@RequestMapping("${entity.firstLowName}List")
	@RequiresPermissions("${entity.className}:read")
	public String list(${entity.className}Query query,Model model){
		//query.setOrderField("seq");//默认是按id排
		/*可同时多列排序
		query.setOrderField(null);//先清空排序字段
		query.getOrderFields().add(new OrderField("sortField1", "asc"));
		query.getOrderFields().add(new OrderField("sortField2", "asc"));*/
		Pagination<${entity.className}> page = ${entity.firstLowName}Service.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		return "${CONFIG.requestMapPath}/${entity.firstLowName}List";
	}
	
	/**
	 * 进入新增form表单页面
	 * @return
	 */
	@RequestMapping("${entity.firstLowName}Form")
	@RequiresPermissions("${entity.className}:create")
	public String ${entity.firstLowName}Form(Model model){
		${entity.className} entity = new ${entity.className}();
		entity.setSeq(${entity.firstLowName}Service.selectMaxSeq()+1);
		model.addAttribute("entity", entity);
		return "${CONFIG.requestMapPath}/${entity.firstLowName}Form";
	}
	
	/**
	 * 进入编辑form表单页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value="${entity.firstLowName}Form/{id}",method = RequestMethod.POST)
	@RequiresPermissions("${entity.className}:update")
	public String ${entity.firstLowName}Form(@PathVariable Long id,Model model){
		${entity.className} entity = ${entity.firstLowName}Service.selectById(id);
		model.addAttribute("entity", entity);
		return "${CONFIG.requestMapPath}/${entity.firstLowName}Form";
	}
	
	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="save${entity.className}",method = RequestMethod.POST)
	@RequiresPermissions(value={"${entity.className}:update","${entity.className}:create"},logical=Logical.AND)
	@ResponseBody
	public String save${entity.className}(${entity.className} entity){
		AjaxCallback ok = AjaxCallback.OK(${entity.firstLowName}Service.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="delete${entity.className}ByIds",method = RequestMethod.POST)
	@RequiresPermissions("${entity.className}:delete")
	@ResponseBody
	public String delete${entity.className}ByIds(@RequestParam(value="ids",required=true) String ids){
		List<Long> idList = Lists.newArrayList();
		
		String[] split = ids.split(",");
		for(String strId:split){
			idList.add(Long.parseLong(strId));
		}
		${entity.firstLowName}Service.deleteByIds(idList);
		
		AjaxCallback ok = AjaxCallback.OK("删除选中项成功！");
		return JSON.toJSONString(ok);
	}
	
	

}
