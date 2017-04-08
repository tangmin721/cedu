package com.yanxiu.ce.core.basic.controller;

import com.alibaba.fastjson.JSON;
import com.yanxiu.ce.common.core.controller.BaseController;
import com.yanxiu.ce.common.core.dto.AjaxCallback;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.core.basic.entity.Teacher;
import com.yanxiu.ce.core.basic.entity.TeacherMoveRecode;
import com.yanxiu.ce.core.basic.entity.TeacherMoveRecodeQuery;
import com.yanxiu.ce.core.basic.enums.TeacherMoveStatusEnum;
import com.yanxiu.ce.core.basic.service.SchoolService;
import com.yanxiu.ce.core.basic.service.TeacherMoveRecodeService;
import com.yanxiu.ce.core.basic.service.TeacherService;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.security.ShiroUtils;
import com.yanxiu.ce.system.service.CityService;
import com.yanxiu.ce.system.service.ProvinceService;
import com.yanxiu.ce.system.service.TownService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 教师调动管理
 * @author tangmin
 * @date 2016-09-27 16:50:41
 */
@Controller
@RequestMapping("/core/basic/teacher/move/recode")
public class TeacherMoveRecodeController extends BaseController{

	@Autowired
	private TeacherMoveRecodeService teacherMoveRecodeService;

	@Autowired
	private TeacherService teacherService;


	@Autowired
	protected ProvinceService provinceService;

	@Autowired
	protected CityService cityService;

	@Autowired
	protected TownService townService;

	@Autowired
	protected SchoolService schoolService;

	/**
	 * 待从本校调出list页面
	 * @return
	 */
	@RequestMapping("schoolOutList")
	@RequiresPermissions("TeacherMovePojo:move")
	public String schoolOutList(TeacherMoveRecodeQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		query.setSchool(ShiroUtils.getCurrentUser().getSchool());
		Pagination<TeacherMoveRecode> page = teacherMoveRecodeService.selectListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
        model.addAttribute("moveStatuses", TeacherMoveStatusEnum.toList());
		return "core/basic/teacher/move/recode/schoolOutList";
	}

    /**
     * 待调入本校list页面
     * @return
     */
    @RequestMapping("schoolInList")
    @RequiresPermissions("TeacherMovePojo:move")
    public String schoolInList(TeacherMoveRecodeQuery query,Model model){
        //query.setOrderField("seq");//默认是按id排
        //可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		query.setTschool(ShiroUtils.getCurrentUser().getSchool());
        Pagination<TeacherMoveRecode> page = teacherMoveRecodeService.selectInListPagination(query);
        model.addAttribute("query", query);
        model.addAttribute("page", page);
        model.addAttribute("moveStatuses", TeacherMoveStatusEnum.toList());
        return "core/basic/teacher/move/recode/schoolInList";
    }

	/**
	 * 省市县 查询与其相关的调动记录
	 * @return
	 */
	@RequestMapping("pctAllList")
	@RequiresPermissions("TeacherMovePojo:read")
	public String schoolAllList(TeacherMoveRecodeQuery query,Model model){
		//query.setOrderField("seq");//默认是按id排
		//可同时多列排序	query.getOrderFields().add(new OrderField("xxxxx", "asc"));
		User currentUser = ShiroUtils.getCurrentUser();
		query.setTschool(currentUser.getSchool());
		query.setTtown(currentUser.getTown());
		query.setTcity(currentUser.getCity());
		query.setTprovince(currentUser.getProvince());

		query.setSchool(currentUser.getSchool());
		query.setTown(currentUser.getTown());
		query.setCity(currentUser.getCity());
		query.setProvince(currentUser.getProvince());

		Pagination<TeacherMoveRecode> page = teacherMoveRecodeService.selectAllListPagination(query);
		model.addAttribute("query", query);
		model.addAttribute("page", page);
		model.addAttribute("moveStatuses", TeacherMoveStatusEnum.toList());
		return "core/basic/teacher/move/recode/pctAllList";
	}

	/**
	 * 进入新增form表单页面
	 * @param tid
	 * @return
	 */
	@RequestMapping(value="recodeForm/{tid}",method = RequestMethod.POST)
	@RequiresPermissions("TeacherMovePojo:move")
	public String recodeForm(@PathVariable Long tid,Model model){
		Teacher teacher = teacherService.selectById(tid);
		TeacherMoveRecode recode = new TeacherMoveRecode();
		recode.setTid(tid);
		recode.setProvince(teacher.getProvince());
		recode.setCity(teacher.getCity());
		recode.setTown(teacher.getTown());
		recode.setSchool(teacher.getSchool());

		model.addAttribute("teacher", teacher);
		model.addAttribute("entity", recode);

		model.addAttribute("tprovinces",provinceService.provinces());
		model.addAttribute("tcitys",cityService.citys(recode.getProvince()));
		model.addAttribute("ttowns",townService.towns(recode.getCity()));
		model.addAttribute("tschools",schoolService.schools(recode.getTown()));
		return "core/basic/teacher/move/recode/recodeForm";
	}

	/**
	 * 保存方法
	 * @return
	 */
	@RequestMapping(value="saveRecode",method = RequestMethod.POST)
	@RequiresPermissions("TeacherMovePojo:move")
	@ResponseBody
	public String saveRecode(TeacherMoveRecode entity){
        AjaxCallback ok = AjaxCallback.OK(teacherMoveRecodeService.saveOrUpdate(entity));
		return JSON.toJSONString(ok);
	}

	/**
	 * 取消调动
	 * @param id
	 * @return
	 */
	@RequestMapping(value="cancelMove/{id}",method = RequestMethod.POST)
    @RequiresPermissions("TeacherMovePojo:move")
    @ResponseBody
    public String cancelMove(@PathVariable Long id){
        teacherMoveRecodeService.cancelMove(id);
        AjaxCallback ok = AjaxCallback.OK("操作成功!");
        return JSON.toJSONString(ok);
    }


	/**
	 * 接收调动
	 * @param id
	 * @return
	 */
	@RequestMapping(value="acceptMove/{id}",method = RequestMethod.POST)
	@RequiresPermissions("TeacherMovePojo:move")
	@ResponseBody
	public String acceptMove(@PathVariable Long id){
		teacherMoveRecodeService.acceptMove(id);
		AjaxCallback ok = AjaxCallback.OK("操作成功!");
		return JSON.toJSONString(ok);
	}

	/**
	 * 拒绝调动 form
	 * @param id
	 * @return
	 */
	@RequestMapping(value="refuseMove/{id}",method = RequestMethod.POST)
	@RequiresPermissions("TeacherMovePojo:move")
	public String refuseMove(@PathVariable Long id,Model model){
		model.addAttribute("id",id);
		return "core/basic/teacher/move/recode/refuseMoveForm";
	}

	/**
	 * 拒绝调动 save
	 * @param id
	 * @return
	 */
	@RequestMapping(value="refuseMove",method = RequestMethod.POST)
	@RequiresPermissions("TeacherMovePojo:move")
	@ResponseBody
	public String refuseMove(@RequestParam(required = true,value = "id")Long id,@RequestParam(required = true,value = "takeMemo")String takeMemo){
		teacherMoveRecodeService.refuseMove(id,takeMemo);
		AjaxCallback ok = AjaxCallback.OK("操作成功!");
		return JSON.toJSONString(ok);
	}

}
