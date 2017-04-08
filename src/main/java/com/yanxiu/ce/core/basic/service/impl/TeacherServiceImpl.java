package com.yanxiu.ce.core.basic.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.exception.ValidateOtherException;
import com.yanxiu.ce.common.utils.AppStringUtils;
import com.yanxiu.ce.common.utils.DateUtils;
import com.yanxiu.ce.common.utils.OrderWrapper;
import com.yanxiu.ce.common.utils.excel.ExcelImportUtils;
import com.yanxiu.ce.common.validate.BeanValidator;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.ValidatorBean;
import com.yanxiu.ce.common.validate.ValidatorResult;
import com.yanxiu.ce.common.yanxiuapi.user.YanxiuUserRegister;
import com.yanxiu.ce.common.yanxiuapi.user.YanxiuUserSync;
import com.yanxiu.ce.core.basic.dao.TeacherDao;
import com.yanxiu.ce.core.basic.dto.TeacherSaveDto;
import com.yanxiu.ce.core.basic.entity.School;
import com.yanxiu.ce.core.basic.entity.Teacher;
import com.yanxiu.ce.core.basic.entity.TeacherArchival;
import com.yanxiu.ce.core.basic.entity.TeacherEdu;
import com.yanxiu.ce.core.basic.entity.TeacherQuery;
import com.yanxiu.ce.core.basic.enums.ExcelColEnum;
import com.yanxiu.ce.core.basic.enums.TeacherTypeEnum;
import com.yanxiu.ce.core.basic.service.CourseService;
import com.yanxiu.ce.core.basic.service.GradeService;
import com.yanxiu.ce.core.basic.service.SchoolService;
import com.yanxiu.ce.core.basic.service.StageService;
import com.yanxiu.ce.core.basic.service.TeacherEduService;
import com.yanxiu.ce.core.basic.service.TeacherService;
import com.yanxiu.ce.core.mq.entity.RegisterMsg;
import com.yanxiu.ce.core.mq.enums.MsgStatusEnum;
import com.yanxiu.ce.core.mq.enums.MsgTypeEnum;
import com.yanxiu.ce.core.mq.service.RegisterMsgService;
import com.yanxiu.ce.core.score.entity.Score;
import com.yanxiu.ce.core.score.entity.ScoreQuery;
import com.yanxiu.ce.core.score.service.ScoreService;
import com.yanxiu.ce.system.entity.User;
import com.yanxiu.ce.system.service.CityService;
import com.yanxiu.ce.system.service.DictCatlogService;
import com.yanxiu.ce.system.service.ProvinceService;
import com.yanxiu.ce.system.service.TownService;
import com.yanxiu.ce.system.service.UserService;

/**
 * 教师管理
 * @author tangmin
 * @date 2016-03-30 18:03:20
 */
@Service("teacherService")
public class TeacherServiceImpl extends BaseServiceImpl<Teacher, TeacherQuery> implements TeacherService{
	@Autowired
	private TeacherDao dao;
	
	@Autowired
	private UserService userService;

	@Autowired
	private ProvinceService provinceService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private TownService townService;
	
	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private DictCatlogService dictCatlogService;
	
	@Autowired
	private StageService stageService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private GradeService gradeService;
	
	@Autowired
	private TeacherEduService teacherEduService;
	
	@Autowired
	private ScoreService scoreService;
	
	@Autowired
	private RegisterMsgService registerMsgService;
	
	@Value(value="${teachehubei.appKey}")
	private String APP_KEY;
	
	@Override
	protected BaseDao<Teacher, TeacherQuery> dao() {
		return this.dao;
	}
	
	@Override
	public Teacher selectByIdCard(String idCard) {
		TeacherQuery query = new TeacherQuery();
		query.setDeleted(0);
		query.setIdCard(idCard);
		query.setIdCardLike(false);
		
		Teacher teacher = null;
		List<Teacher> teachers = this.selectList(query);
		if(teachers!=null && teachers.size()>0){
			teacher = teachers.get(0);
		}
		return teacher;
	}
	
	@Override
	public Teacher selectByTno(String tno) {
		TeacherQuery query = new TeacherQuery();
		query.setDeleted(0);
		query.setIdCard(tno);//需求变为以身份证为登录账号了
		query.setIdCardLike(false);
		
		Teacher teacher = null;
		List<Teacher> teachers = this.selectList(query);
		if(teachers!=null && teachers.size()>0){
			teacher = teachers.get(0);
		}
		return teacher;
	}
	
	/**
	 * 获取seq
	 */
	@Override
	public Integer selectMaxSeq() {
		Integer selectMaxSeq = this.dao.selectMaxSeq();
		if(selectMaxSeq!=null){
			return selectMaxSeq;
		}
		return 0;
	}

	/**
	 * 校验entity是否可修改（code是否存在）
	 */
	@Override
	public Boolean checkIdCardExit(Teacher entity) {
		Long count = this.dao.selectCheckIdCardExit(entity.getIdCard(), entity.getId());
		if(count>0){
			return false;
		}
		return true;
	}
	
	/**
	 * 覆写deleteByIds,删除教师同时删除用户,关联的其他信息
	 * @param ids
	 * @return
	 */
	@Override
	@Transactional
	public Long deleteByIds(List<Long> ids){
		Long count = 0l;
		for(Long id:ids){
			Teacher teacher = this.selectById(id);
			if(teacher!=null && teacher.getIdCard()!=null){
				userService.deleteByLoginName(teacher.getIdCard());
			}
			count++;
			
			this.deleteById(id);
			//teacherArchivalService.deleteByTid(id);
		}
		return count;
	}
	
	/**
	 * 新增or修改
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor={RuntimeException.class, Exception.class})
	public TeacherSaveDto saveOrUpdate(Teacher entity) {
		TeacherSaveDto resultDto = new TeacherSaveDto();
		if(!checkIdCardExit(entity)){
			if(entity.getId()==null){
				throw new ValidateOtherException(ValidateOtherException.INSERT_FAILD,"身份证已经存在，新增失败");
			}else {
				throw new ValidateOtherException(ValidateOtherException.UPDATE_FAILD,"身份证已经存在，修改失败");
			}
		}
		String msg = "";
		if(entity.getId()==null){
			//教师编号
			String tno = "L"+entity.getTown()+""+AppStringUtils.addZero(12,this.dao.nextSequenceVal());       // ;
			entity.setTno(tno);
			Long tid = this.insert(entity);
			msg = "添加成功！";
			resultDto.setMsg(msg);
			resultDto.setTid(tid);
			resultDto.setVersion(0);
			resultDto.setTno(tno);
			
			
			entity.setId(tid);
			
			/**
			 * 生成user账号
			 */
			userService.createTeacherUser(entity);
		}else {
			Teacher old = this.selectById(entity.getId());
			
			if(checkInfoForSync(old,entity)){
				/**
				 * 如果信息有变更 同步 user账号
				 */
				entity.setTno(old.getTno());
				saveSyncRegisterMsg(entity);
			}
			
			this.update(entity);
				msg = "编辑成功！";
			resultDto.setMsg(msg);
			resultDto.setTid(entity.getId());
			Teacher teacherData = this.selectById(entity.getId());//tno不在表单里传递
			resultDto.setVersion(teacherData.getVersion());//乐观锁
			resultDto.setTno(teacherData.getTno());
			
			entity.setTno(teacherData.getTno());
			
			//更新教师的省市县校 同时需要更改  用户表，学时表的id
			if(checkInfoForUpdateUser(old,entity)){
				User user = userService.selectByLoginName(entity.getIdCard()+"");
				if(user!=null){
					user.setProvince(entity.getProvince());
					user.setCity(entity.getCity());
					user.setTown(entity.getTown());
					user.setSchool(entity.getSchool());
					user.setIdCard(entity.getIdCard());
					user.setRealName(entity.getName());
//					user.setMobileNo(entity.getMobile());
//					user.setEmail(entity.getEmail());
					userService.update(user);
				}
				
				ScoreQuery scoreQuery = new ScoreQuery();
				scoreQuery.setTid(entity.getId()+"");
				List<Score> scores = scoreService.selectList(scoreQuery );
				for(Score score:scores){
					score.setProvince(entity.getProvince());
					score.setCity(entity.getCity());
					score.setTown(entity.getTown());
					score.setSchool(entity.getSchool());
					scoreService.update(score);
				}
			}
				
		}
		
		return resultDto;
	}

	/**
	 * 判断 是否更新了信息，用于同步用户
	 * @param oldObj
	 * @param newObj
	 * @return
	 */
	boolean checkInfoForSync(Teacher oldObj,Teacher newObj){
		
		Integer province = oldObj.getProvince()==null?-1:oldObj.getProvince();
		Integer city = oldObj.getCity()==null?-1:oldObj.getCity();
		Integer town = oldObj.getTown()==null?-1:oldObj.getTown();
		Long school = oldObj.getSchool()==null?-1:oldObj.getSchool();
//		Integer sex = oldObj.getSex()==null?-1:oldObj.getSex();
//		Long stage = oldObj.getStage()==null?-1:oldObj.getStage();
//		Long course = oldObj.getCourse()==null?-1:oldObj.getCourse();
//		Long grade = oldObj.getGrade()==null?-1:oldObj.getGrade();
		String idCard = oldObj.getIdCard()==null?"":oldObj.getIdCard();
//		String mobile = oldObj.getMobile()==null?"":oldObj.getMobile();
//		String email = oldObj.getEmail()==null?"":oldObj.getEmail();
		
		return !province.equals(newObj.getProvince())
				||!city.equals(newObj.getCity())
				||!town.equals(newObj.getTown())
				||!school.equals(newObj.getSchool())
//				||!sex.equals(newObj.getSex())
//				||!stage.equals(newObj.getStage())
//				||!course.equals(newObj.getCourse())
//				||!grade.equals(newObj.getGrade())
				||!idCard.equals(newObj.getIdCard());
//				||!mobile.equals(newObj.getMobile())
//				||!email.equals(newObj.getEmail());
	}
	
	
	/**
	 * 判断 是否更新了信息，用于同步用户
	 * @param oldObj
	 * @param newObj
	 * @return
	 */
	boolean checkInfoForUpdateUser(Teacher oldObj,Teacher newObj){
		
		Integer province = oldObj.getProvince()==null?-1:oldObj.getProvince();
		Integer city = oldObj.getCity()==null?-1:oldObj.getCity();
		Integer town = oldObj.getTown()==null?-1:oldObj.getTown();
		Long school = oldObj.getSchool()==null?-1:oldObj.getSchool();
		String idCard = oldObj.getIdCard()==null?"":oldObj.getIdCard();
//		String email = oldObj.getEmail()==null?"":oldObj.getEmail();
//		String mobile = oldObj.getMobile()==null?"":oldObj.getMobile();
		String name = oldObj.getName()==null?"":oldObj.getName();
		
		return !province.equals(newObj.getProvince())
				||!city.equals(newObj.getCity())
				||!town.equals(newObj.getTown())
				||!school.equals(newObj.getSchool())
				||!idCard.equals(newObj.getIdCard())
//				||!mobile.equals(newObj.getMobile())
//				||!email.equals(newObj.getEmail())
				||!name.equals(newObj.getName());
	}
	
	/**
	 * 注册用户，保存到 消息队列的表中（然后通过遍历表，执行注册）
	 * @param teacher
	 */
	private void saveSyncRegisterMsg(Teacher teacher) {
		RegisterMsg registerMsg = new RegisterMsg();
		
		User user = userService.selectByLoginName(teacher.getIdCard()+"");
		if(user!=null&&user.getUid()!=null){
			registerMsg.setUid(user.getUid()+"");
		}
		
		registerMsg.setAppKey(APP_KEY);
		registerMsg.setPassport(teacher.getTno().toString());
//		registerMsg.setMobile(teacher.getMobile());
//		registerMsg.setEmail(teacher.getEmail());
		registerMsg.setIdCard(teacher.getIdCard());
		
		if(teacher.getSchool()!=null){
			School school = schoolService.selectById(teacher.getSchool());
			if(school!=null){
				registerMsg.setSchoolName(school.getName());
			}
		}
		
//		if(teacher.getStage()!=null){
//			Stage stage = stageService.selectById(teacher.getStage());
//			if(stage!=null){
//				registerMsg.setStage(stage.getName());
//			}
//		}
//		
//		if(teacher.getCourse()!=null){
//			Course course = courseService.selectById(teacher.getCourse());
//			if(course!=null){
//				registerMsg.setCourse(course.getName());
//			}
//		}
//		
//		if(teacher.getGrade()!=null){
//			Grade grade = gradeService.selectById(teacher.getGrade());
//			if(grade!=null){
//				registerMsg.setGrade(grade.getName());
//			}
//		}
//		
		registerMsg.setProvince(provinceService.getNameByNo(teacher.getProvince()));
		registerMsg.setCity(cityService.getNameByNo(teacher.getCity()));
		registerMsg.setArea(townService.getNameByNo(teacher.getTown()));
		
//		if(teacher.getSex()!=null){
//			if(teacher.getSex()==1){
//				registerMsg.setGender("男");
//			}else if(teacher.getSex()==0){
//				registerMsg.setGender("女");
//			}
//		}
		
		//设置状态
		registerMsg.setType(MsgTypeEnum.SYNC_USER.getValue());
		registerMsg.setStatus(MsgStatusEnum.UNPROCESS.getValue());
		registerMsgService.saveOrUpdate(registerMsg);
		
	}
	
	@Override
	@Transactional
	public Long updateBatchSchool(Integer province, Integer city, Integer town,
			Long school) {
		return this.dao.updateBatchSchool(province, city, town, school);
	}
	
	
	/************************excel start*****************************/
	/**
	 * 获取教师信息导出表头
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<String> getExcelFieldName(Integer schoolType){
		List<String> fieldNameList = new ArrayList<String>();
//		fieldNameList.add("省");
//		fieldNameList.add("市");
//		fieldNameList.add("区县");
		fieldNameList.add("学校名称");
		fieldNameList.add("姓名");
		fieldNameList.add("曾用名");
		fieldNameList.add("性别");
		fieldNameList.add("教职工号");
		fieldNameList.add("国籍/地区");
		fieldNameList.add("身份证件类型");
		fieldNameList.add("身份证件号");
		fieldNameList.add("出生日期");
		fieldNameList.add("籍贯");
		fieldNameList.add("出生地");
		fieldNameList.add("民族");
		fieldNameList.add("政治面貌");
		fieldNameList.add("婚姻状况");
		fieldNameList.add("健康状况");
		fieldNameList.add("参加工作年月");
		
		if(schoolType.intValue() == TeacherTypeEnum.MIDDLE_SCHOOL.getValue()){
			//*姓名	曾用名	*性别	教职工号	*国籍/地区	*身份证件类型	*身份证件号	*出生日期	籍贯	出生地	*民族	*政治面貌	婚姻状况	健康状况	*参加工作年月	
			//*进本校年月	*教职工来源	*教职工类别	*是否在编	*用人形式	*签订合同情况	*是否全日制师范类专业毕业	*是否受过特教专业培养培训	*是否有特殊教育从业证书	
			//*信息技术应用能力	*是否属于免费（公费）师范生	*是否参加基层服务项目	 参加基层服务项目起始年月	参加基层服务项目结束年月	*是否是特级教师	
			//*是否是县级及以上骨干教师	*是否心里健康教育教师	*人员状态	备注
			fieldNameList.add("进本校年月");
			
			fieldNameList.add("教职工来源");
			fieldNameList.add("教职工类别");
			fieldNameList.add("是否在编");
			fieldNameList.add("用人形式");
			fieldNameList.add("签订合同情况");
			
			fieldNameList.add("是否全日制师范类专业毕业");
			fieldNameList.add("是否受过特教专业培养培训");
			fieldNameList.add("是否有特殊教育从业证书");
			fieldNameList.add("信息技术应用能力");
			fieldNameList.add("是否属于免费（公费）师范生");
			fieldNameList.add("是否参加基层服务项目	");
			fieldNameList.add("参加基层服务项目起始年月");
			fieldNameList.add("参加基层服务项目结束年月");
			fieldNameList.add("是否是特级教师");
			fieldNameList.add("是否是县级及以上骨干教师");
			fieldNameList.add("是否心里健康教育教师");
			fieldNameList.add("人员状态");
			fieldNameList.add("备注");	
		}else if(schoolType.intValue() == TeacherTypeEnum.TECHNICAL_SCHOOL.getValue()){
			//*姓名	曾用名	*性别	教职工号	*国籍/地区	*身份证件类型	*身份证件号	*出生日期	籍贯	出生地	*民族	*政治面貌	婚姻状况	*健康状况	*参加工作年月	
			//*进本校年月	*教职工来源	*教职工类别	*是否在编	*用人形式	*签订合同情况	*信息技术应用能力	*是否是特级教师	*是否“双师型”教师	
			//*是否具备职业技能等级证书	*企业工作(实践)时长	*人员状态	备注
			fieldNameList.add("进本校年月");
			
			fieldNameList.add("教职工来源");
			fieldNameList.add("教职工类别");
			fieldNameList.add("是否在编");
			fieldNameList.add("用人形式");
			fieldNameList.add("签订合同情况");
			
			fieldNameList.add("信息技术应用能力");
			fieldNameList.add("是否是特级教师");
			fieldNameList.add("是否“双师型”教师");
			fieldNameList.add("是否具备职业技能等级证书");
			fieldNameList.add("企业工作(实践)时长");
			fieldNameList.add("人员状态");
			fieldNameList.add("备注");	
		}else if(schoolType.intValue() == TeacherTypeEnum.SPECIAL_SCHOOL.getValue()){
			//*姓名	曾用名	*性别	教职工号	*国籍/地区	*身份证件类型	*身份证件号	*出生日期	籍贯	出生地	*民族	*政治面貌	婚姻状况	*健康状况	*参加工作年月	
			//*从事特教起始年月	*进本校年月	*教职工来源	*教职工类别	*是否在编	*用人形式	*签订合同情况	*是否全日制师范类专业毕业	*是否全日制特殊教育专业毕业	
			//*是否受过特教专业培养培训	*是否有特殊教育从业证书	*信息技术应用能力	*是否属于免费（公费）师范生	*是否参加基层服务项目	 参加基层服务项目起始年月	
			//参加基层服务项目结束年月	*是否是特级教师	*是否是县级及以上骨干教师	*是否心里健康教育教师	*人员状态	备注
			fieldNameList.add("从事特教起始年月");
			fieldNameList.add("进本校年月");
			
			fieldNameList.add("教职工来源");
			fieldNameList.add("教职工类别");
			fieldNameList.add("是否在编");
			fieldNameList.add("用人形式");
			fieldNameList.add("签订合同情况");
			
			fieldNameList.add("是否全日制师范类专业毕业");
			fieldNameList.add("是否全日制特殊教育专业毕业");
			fieldNameList.add("是否受过特教专业培养培训");
			fieldNameList.add("是否有特殊教育从业证书");
			fieldNameList.add("信息技术应用能力");
			fieldNameList.add("是否属于免费（公费）师范生");
			fieldNameList.add("是否参加基层服务项目");
			fieldNameList.add("参加基层服务项目起始年月");
			fieldNameList.add("参加基层服务项目结束年月");
			fieldNameList.add("是否是特级教师");
			fieldNameList.add("是否是县级及以上骨干教师");
			fieldNameList.add("是否心里健康教育教师");
			fieldNameList.add("人员状态");
			fieldNameList.add("备注");	
		}else if(schoolType.intValue() == TeacherTypeEnum.KINDERGARTEN.getValue()){
			//*姓名	曾用名	*性别	教职工号	*国籍/地区	*身份证件类型	*身份证件号	*出生日期	籍贯	出生地	*民族	*政治面貌	婚姻状况	健康状况	*参加工作年月	
			//*进本校年月	*教职工来源	*教职工类别	*是否在编	*用人形式	*签订合同情况	*是否全日制师范类专业毕业	*是否全日制学前教育专业毕业	
			//*是否受过学前教育专业培养培训	*信息技术应用能力	*是否属于免费（公费）师范生	*是否参加基层服务项目	  参加基层服务项目起始年月	
			//参加基层服务项目结束年月	*是否是特级教师	*是否是县级及以上骨干教师	*是否心里健康教育教师	*人员状态	备注
			fieldNameList.add("进本校年月");
			
			fieldNameList.add("教职工来源");
			fieldNameList.add("教职工类别");
			fieldNameList.add("是否在编");
			fieldNameList.add("用人形式");
			fieldNameList.add("签订合同情况");
			
			fieldNameList.add("是否全日制师范类专业毕业");
			fieldNameList.add("是否全日制学前教育专业毕业");
			fieldNameList.add("是否受过学前教育专业培养培训");
			fieldNameList.add("信息技术应用能力");
			fieldNameList.add("是否属于免费（公费）师范生");
			fieldNameList.add("是否参加基层服务项目");
			fieldNameList.add("参加基层服务项目起始年月");
			fieldNameList.add("参加基层服务项目结束年月");
			fieldNameList.add("是否是特级教师");
			fieldNameList.add("是否是县级及以上骨干教师");
			fieldNameList.add("是否心里健康教育教师	");
			fieldNameList.add("人员状态");
			fieldNameList.add("备注");
		}else{
			return new ArrayList<String>();
		}
		return fieldNameList;
	}

	/**
	 * 获取校长信息导出表头
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<String> getMasterExcelFieldName(){
		String[] fieldNameArray = {"*省","*市","*区县","*学校名称","*姓名","*性别","*出生日期","*民族","*身份证","办公电话","手机号","邮箱",
				"*人员类型","行政职务","职称","*教师资格","骨干类型","任校级干部时间","*政治面貌","参加党派时间",
				"*学历","*所学专业","*毕业学校","从教时间","参加工作时间","*学段","*学科","年级","在岗状态"};
		return Arrays.asList(fieldNameArray);
	}
	
	/**
	 * 根据查询条件获取导出表格数据
	 * @param query
	 * @return
	 */
	@Override
	public List<List<String>> getExcelDatasByQuery(TeacherQuery query){
		List<Teacher> page = this.dao.selectExcelList(query);
		return changeObjToStringList(page,query.getSchoolType());
	}
	
	/**
	 * 导出 转换为excel数据
	 * @param teachers
	 * @return
	 */
	@Override
	public List<List<String>> changeObjToStringList(List<Teacher> teachers, String schoolType) {
		List<List<String>> result = Lists.newArrayList();
		int schoolTypeTmp = Integer.parseInt(schoolType);
		for(Teacher teacher:teachers){
			List<String> ls = Lists.newArrayList();
//			ls.add(teacher.getProvinceName());
//			ls.add(teacher.getCityName());
//			ls.add(teacher.getTownName());
			ls.add(teacher.getSchoolName());
			ls.add(teacher.getName());
			ls.add(teacher.getOldName());
			ls.add(teacher.getGenderName());
			ls.add(teacher.getTchWorkNum());
			ls.add(teacher.getNationalityName());
			ls.add(teacher.getPaperTypeName());
			ls.add(teacher.getIdCard());
			ls.add(DateUtils.sdfDateOnly.format(teacher.getBirthday()));
			ls.add(teacher.getNativer());
			ls.add(teacher.getBirthPlace());
			ls.add(teacher.getNationName());
			ls.add(teacher.getPoliticTypeName());
			ls.add(teacher.getMarryName());
			ls.add(teacher.getHealthName());
			ls.add(DateUtils.sdfDateOnly.format(teacher.getWorkDay()));
			if(schoolTypeTmp == TeacherTypeEnum.MIDDLE_SCHOOL.getValue()){
				//*姓名	曾用名	*性别	教职工号	*国籍/地区	*身份证件类型	*身份证件号	*出生日期	籍贯	出生地	*民族	*政治面貌	婚姻状况	健康状况	*参加工作年月	
				//*进本校年月	*教职工来源	*教职工类别	*是否在编	*用人形式	*签订合同情况	*是否全日制师范类专业毕业	*是否受过特教专业培养培训	*是否有特殊教育从业证书	
				//*信息技术应用能力	*是否属于免费（公费）师范生	*是否参加基层服务项目	 参加基层服务项目起始年月	参加基层服务项目结束年月	*是否是特级教师	
				//*是否是县级及以上骨干教师	*是否心里健康教育教师	*人员状态	备注
				ls.add(DateUtils.sdfDateOnly.format(teacher.getJoinSchoolDay()));
				ls.add(teacher.getWorkerFrom1Name());
				ls.add(teacher.getWorkerType1Name());
				ls.add(teacher.getAtSchoolName());
				ls.add(teacher.getUsePersonTypeName());
				ls.add(teacher.getSignContractName());
				
				ls.add(teacher.getIsQrzName());
				ls.add(teacher.getIsTjpxName());
				ls.add(teacher.getIsTszsName());
				ls.add(teacher.getComputerAbilityName());
				ls.add(teacher.getIsMianName());
				ls.add(teacher.getIsJoinBaseName());
				if(teacher.getJoinBaseStart() != null){
					ls.add(DateUtils.sdfDateOnly.format(teacher.getJoinBaseStart()));
				}else{
					ls.add("");
				}
				if(teacher.getJoinBaseEnd() != null){
					ls.add(DateUtils.sdfDateOnly.format(teacher.getJoinBaseEnd()));
				}else{
					ls.add("");
				}
				
				ls.add(teacher.getIsTjName());
				ls.add(teacher.getIsTownUpBoneName());
				ls.add(teacher.getIsHealthName());
				ls.add(teacher.getPersonStatusName());
				if(teacher.getMemo() != null && !("").equals(teacher.getMemo())){
					ls.add(teacher.getMemo());
				}else{
					ls.add("");
				}
			}else if(schoolTypeTmp == TeacherTypeEnum.TECHNICAL_SCHOOL.getValue()){
				//*姓名	曾用名	*性别	教职工号	*国籍/地区	*身份证件类型	*身份证件号	*出生日期	籍贯	出生地	*民族	*政治面貌	婚姻状况	*健康状况	*参加工作年月	
				//*进本校年月	*教职工来源	*教职工类别	*是否在编	*用人形式	*签订合同情况	*信息技术应用能力	*是否是特级教师	*是否“双师型”教师	
				//*是否具备职业技能等级证书	*企业工作(实践)时长	*人员状态	备注
				ls.add(DateUtils.sdfDateOnly.format(teacher.getJoinSchoolDay()));
				
				ls.add(teacher.getWorkerFrom1Name());
				ls.add(teacher.getWorkerType2Name());
				ls.add(teacher.getAtSchoolName());
				ls.add(teacher.getUsePersonTypeName());
				ls.add(teacher.getSignContractName());
				
				ls.add(teacher.getComputerAbilityName());
				ls.add(teacher.getIsTjName());
				ls.add(teacher.getIsDoubleTchName());
				ls.add(teacher.getIsProfessCardName());
				ls.add(teacher.getWorkDateTimerName());
				ls.add(teacher.getPersonStatusName());
				if(teacher.getMemo() != null && !("").equals(teacher.getMemo())){
					ls.add(teacher.getMemo());
				}else{
					ls.add("");
				}
			}else if(schoolTypeTmp == TeacherTypeEnum.SPECIAL_SCHOOL.getValue()){
				//*姓名	曾用名	*性别	教职工号	*国籍/地区	*身份证件类型	*身份证件号	*出生日期	籍贯	出生地	*民族	*政治面貌	婚姻状况	*健康状况	*参加工作年月	
				//*从事特教起始年月	*进本校年月	*教职工来源	*教职工类别	*是否在编	*用人形式	*签订合同情况	*是否全日制师范类专业毕业	*是否全日制特殊教育专业毕业	
				//*是否受过特教专业培养培训	*是否有特殊教育从业证书	*信息技术应用能力	*是否属于免费（公费）师范生	*是否参加基层服务项目	 参加基层服务项目起始年月	
				//参加基层服务项目结束年月	*是否是特级教师	*是否是县级及以上骨干教师	*是否心里健康教育教师	*人员状态	备注
				//fieldNameList.add("*从事特教起始年月");
				ls.add("");
				ls.add(DateUtils.sdfDateOnly.format(teacher.getJoinSchoolDay()));
				
				ls.add(teacher.getWorkerFrom1Name());
				ls.add(teacher.getWorkerType3Name());
				ls.add(teacher.getAtSchoolName());
				ls.add(teacher.getUsePersonTypeName());
				ls.add(teacher.getSignContractName());
				
				ls.add(teacher.getIsQrzName());
				//fieldNameList.add("*是否全日制特殊教育专业毕业");
				ls.add("");
				ls.add(teacher.getIsTjpxName());
				ls.add(teacher.getIsTszsName());
				ls.add(teacher.getComputerAbilityName());
				ls.add(teacher.getIsMianName());
				ls.add(teacher.getIsJoinBaseName());
				if(teacher.getJoinBaseStart() != null){
					ls.add(DateUtils.sdfDateOnly.format(teacher.getJoinBaseStart()));
				}else{
					ls.add("");
				}
				if(teacher.getJoinBaseEnd() != null){
					ls.add(DateUtils.sdfDateOnly.format(teacher.getJoinBaseEnd()));
				}else{
					ls.add("");
				}
				ls.add(teacher.getIsTjName());
				ls.add(teacher.getIsTownUpBoneName());
				ls.add(teacher.getIsHealthName());
				ls.add(teacher.getPersonStatusName());
				if(teacher.getMemo() != null && !("").equals(teacher.getMemo())){
					ls.add(teacher.getMemo());
				}else{
					ls.add("");
				}
			}else if(schoolTypeTmp == TeacherTypeEnum.KINDERGARTEN.getValue()){
				//*姓名	曾用名	*性别	教职工号	*国籍/地区	*身份证件类型	*身份证件号	*出生日期	籍贯	出生地	*民族	*政治面貌	婚姻状况	健康状况	*参加工作年月	
				//*进本校年月	*教职工来源	*教职工类别	*是否在编	*用人形式	*签订合同情况	*是否全日制师范类专业毕业	*是否全日制学前教育专业毕业	
				//*是否受过学前教育专业培养培训	*信息技术应用能力	*是否属于免费（公费）师范生	*是否参加基层服务项目	  参加基层服务项目起始年月	
				//参加基层服务项目结束年月	*是否是特级教师	*是否是县级及以上骨干教师	*是否心里健康教育教师	*人员状态	备注
				ls.add(DateUtils.sdfDateOnly.format(teacher.getJoinSchoolDay()));
				
				ls.add(teacher.getWorkerFrom2Name());
				ls.add(teacher.getWorkerType4Name());
				ls.add(teacher.getAtSchoolName());
				ls.add(teacher.getUsePersonTypeName());
				ls.add(teacher.getSignContractName());
				
				ls.add(teacher.getIsQrzName());
				ls.add(teacher.getIsPreTchName());
				ls.add(teacher.getIsPreTrainName());
				ls.add(teacher.getComputerAbilityName());
				ls.add(teacher.getIsMianName());
				ls.add(teacher.getIsJoinBaseName());
				if(teacher.getJoinBaseStart() != null){
					ls.add(DateUtils.sdfDateOnly.format(teacher.getJoinBaseStart()));
				}else{
					ls.add("");
				}
				if(teacher.getJoinBaseEnd() != null){
					ls.add(DateUtils.sdfDateOnly.format(teacher.getJoinBaseEnd()));
				}else{
					ls.add("");
				}
				ls.add(teacher.getIsTjName());
				ls.add(teacher.getIsTownUpBoneName());
				ls.add(teacher.getIsHealthName());
				ls.add(teacher.getPersonStatusName());
				if(teacher.getMemo() != null && !("").equals(teacher.getMemo())){
					ls.add(teacher.getMemo());
				}else{
					ls.add("");
				}
			}
			result.add(ls);
		}
		return result;
	}
	/**
	 * 验证excel
	 * @param file
	 * @throws Exception
	 */
	@Override
	public List<String> checkExcel(MultipartFile file,TeacherQuery query){
		List<String[]> fileToList = ExcelImportUtils.fileToList(file);
		String[] head = fileToList.get(0);//表头
		Map<String,Integer> headMap = Maps.newHashMap();
		Pattern p = Pattern.compile("\\*|\t|\r|\n");
		for(int i=0;i<head.length;i++){
			Matcher m = p.matcher(head[i]);
			String dest = m.replaceAll("");
			//headMap.put(head[i].replaceAll("\\*", ""), i);//获取表头名字，并去掉*，用于根据表头名匹配数据
			headMap.put(dest, i);
			//System.out.println("head["+i+"]"+head[i]+"--"+dest);
		}
		fileToList.remove(0);//删除表头数据，剩下的都是实际数据
		
		Map<Integer,Teacher> teacherMap = Maps.newHashMap();//teacher + 索引位置,用于记录行号，输出 出错的行号
//		Map<Integer,TeacherArchival> teacherArchivalMap = Maps.newHashMap();
//		Map<Integer,TeacherEdu> teacherEduMap = Maps.newHashMap();
		
		Map<String, Integer> provinceMap = provinceService.provinceMap();//省map缓存
		List<String> errorList = Lists.newArrayList();
		int schoolType = Integer.parseInt(query.getSchoolType());
		
		if(fileToList.size()==0){
			errorList.add("Excel里无数据，请检查！");
		}
		
		/*if(query.getProvince()==0){
			Integer index = headMap.get("省");
			if(index==null){
				errorList.add("找不到【省】列，请检查Excel模板是否正确！");
			}
		}
		
		if(query.getCity()==0){
			Integer index = headMap.get("市");
			if(index==null){
				errorList.add("找不到【市】列，请检查Excel模板是否正确！");
			}
		}
		
		if(query.getTown()==0){
			Integer index = headMap.get("区县");
			if(index==null){
				errorList.add("找不到【区县】列，请检查Excel模板是否正确！");
			}
		}
		
		if(query.getSchool()==0){
			Integer index = headMap.get("学校名称");
			if(index==null){
				errorList.add("找不到【学校名称】列，请检查Excel模板是否正确！");
			}
		}*/
		
		

		Integer nameIndex = headMap.get("姓名");
		if(nameIndex==null){
			errorList.add("找不到【姓名】列，请检查Excel模板是否正确！");
		}
		
		Integer hnameIndex = headMap.get("曾用名");
		if(hnameIndex==null){
			errorList.add("找不到【曾用名】列，请检查Excel模板是否正确！");
		}

		Integer sexIndex = headMap.get("性别");
		if(sexIndex==null){
			errorList.add("找不到【性别】列，请检查Excel模板是否正确！");
		}
		
		Integer tchWorkNumIndex = headMap.get("教职工号");
		if(tchWorkNumIndex==null){
			errorList.add("找不到【教职工号】列，请检查Excel模板是否正确！");
		}
		
		Integer nationalityIndex = headMap.get("国籍/地区");
		if(nationalityIndex==null){
			errorList.add("找不到【国籍/地区】列，请检查Excel模板是否正确！");
		}
		
		Integer paperTypeIndex = headMap.get("身份证件类型");
		if(paperTypeIndex==null){
			errorList.add("找不到【身份证件类型】列，请检查Excel模板是否正确！");
		}
		
		Integer idCardIndex = headMap.get("身份证件号");
		if(idCardIndex==null){
			errorList.add("找不到【身份证件号】列，请检查Excel模板是否正确！");
		}
		
		Integer birthIndex = headMap.get("出生日期(yyyy-MM-dd)");
		if(birthIndex==null){
			errorList.add("找不到【出生日期】列，请检查Excel模板是否正确！");
		}
		
		Integer nativerIndex = headMap.get("籍贯");
		if(nativerIndex==null){
			errorList.add("找不到【籍贯】列，请检查Excel模板是否正确！");
		}
		
		Integer birthPlaceIndex = headMap.get("出生地");
		if(birthPlaceIndex==null){
			errorList.add("找不到【出生地】列，请检查Excel模板是否正确！");
		}
		
		Integer nationIndex = headMap.get("民族");
		if(nationIndex==null){
			errorList.add("找不到【民族】列，请检查Excel模板是否正确！");
		}
		
		Integer politicTypeIndex = headMap.get("政治面貌");
		if(politicTypeIndex==null){
			errorList.add("找不到【政治面貌】列，请检查Excel模板是否正确！");
		}
		
		Integer marryIndex = headMap.get("婚姻状况");
		if(marryIndex==null){
			errorList.add("找不到【婚姻状况】列，请检查Excel模板是否正确！");
		}
		
		Integer healthIndex = headMap.get("健康状况");
		if(healthIndex==null){
			errorList.add("找不到【健康状况】列，请检查Excel模板是否正确！");
		}
		
		Integer workDayIndex = headMap.get("参加工作年月(yyyy-MM-dd)");
		if(workDayIndex==null){
			errorList.add("找不到【参加工作年月】列，请检查Excel模板是否正确！");
		}
		
		Integer specStartDayIndex = null;
		if(schoolType == TeacherTypeEnum.SPECIAL_SCHOOL.getValue()){
			specStartDayIndex = headMap.get("从事特教起始年月(yyyy-MM-dd)");
			if(specStartDayIndex==null){
				errorList.add("找不到【从事特教起始年月】列，请检查Excel模板是否正确！");
			}
		}

		Integer joinSchoolDayIndex = null;
		if(schoolType == TeacherTypeEnum.KINDERGARTEN.getValue()){
			joinSchoolDayIndex = headMap.get("进本园年月(yyyy-MM-dd)");
			if(joinSchoolDayIndex==null){
				errorList.add("找不到【进本园年月】列，请检查Excel模板是否正确！");
			}
		}else{
			joinSchoolDayIndex = headMap.get("进本校年月(yyyy-MM-dd)");
			if(joinSchoolDayIndex==null){
				errorList.add("找不到【进本校年月】列，请检查Excel模板是否正确！");
			}
		}
		
		Integer workerFrom1Index = headMap.get("教职工来源");
		if(workerFrom1Index==null){
			errorList.add("找不到【教职工来源】列，请检查Excel模板是否正确！");
		}
		
		Integer workerType1Index = headMap.get("教职工类别");
		if(workerType1Index==null){
			errorList.add("找不到【教职工类别】列，请检查Excel模板是否正确！");
		}
		
		Integer atSchoolIndex = headMap.get("是否在编");
		if(atSchoolIndex==null){
			errorList.add("找不到【是否在编】列，请检查Excel模板是否正确！");
		}
		
		Integer usePersonTypeIndex = headMap.get("用人形式");
		if(usePersonTypeIndex==null){
			errorList.add("找不到【用人形式】列，请检查Excel模板是否正确！");
		}
		
		Integer signContractIndex = headMap.get("签订合同情况");
		if(signContractIndex==null){
			errorList.add("找不到【签订合同情况】列，请检查Excel模板是否正确！");
		}
		
		Integer isQrzIndex = null;
		Integer isTjpxIndex = null;
		Integer isTszsIndex = null;
		Integer isTjzyIndex = null;
		Integer isPreTchIndex = null;
		Integer isPreTrainIndex = null;
		if(schoolType == TeacherTypeEnum.MIDDLE_SCHOOL.getValue() || schoolType == TeacherTypeEnum.SPECIAL_SCHOOL.getValue() || schoolType == TeacherTypeEnum.KINDERGARTEN.getValue()){
			isQrzIndex = headMap.get("是否全日制师范类专业毕业");
			if(isQrzIndex==null){
				errorList.add("找不到【是否全日制师范类专业毕业】列，请检查Excel模板是否正确！");
			}
			
			if(schoolType == TeacherTypeEnum.SPECIAL_SCHOOL.getValue()){
				isTjzyIndex = headMap.get("是否全日制特殊教育专业毕业");
				if(isTjzyIndex == null){
					errorList.add("找不到【是否全日制特殊教育专业毕业】列，请检查Excel模板是否正确！");
				}
			}
			if(schoolType == TeacherTypeEnum.MIDDLE_SCHOOL.getValue() || schoolType == TeacherTypeEnum.SPECIAL_SCHOOL.getValue()){
				isTjpxIndex = headMap.get("是否受过特教专业培养培训");
				if(isTjpxIndex==null){
					errorList.add("找不到【是否受过特教专业培养培训】列，请检查Excel模板是否正确！");
				}
		
				isTszsIndex = headMap.get("是否有特殊教育从业证书");
				if(isTszsIndex==null){
					errorList.add("找不到【是否有特殊教育从业证书】列，请检查Excel模板是否正确！");
				}
			}
			
			if(schoolType == TeacherTypeEnum.KINDERGARTEN.getValue()){
				//是否全日制学前教育专业毕业
				isPreTchIndex = headMap.get("是否全日制学前教育专业毕业");
				if(isPreTchIndex==null){
					errorList.add("找不到【是否全日制学前教育专业毕业】列，请检查Excel模板是否正确！");
				}
				//是否受过学前教育专业培养培训
				isPreTrainIndex = headMap.get("是否受过学前教育专业培养培训");
				if(isPreTrainIndex==null){
					errorList.add("找不到【是否受过学前教育专业培养培训】列，请检查Excel模板是否正确！");
				}
			}
		}
		
		Integer computerAbilityIndex = headMap.get("信息技术应用能力");
		if(computerAbilityIndex==null){
			errorList.add("找不到【信息技术应用能力】列，请检查Excel模板是否正确！");
		}
		
		Integer isMianIndex = null;
		Integer isJoinBaseIndex = null;
		Integer joinBaseStartIndex = null;
		Integer joinBaseEndIndex = null;
		if(schoolType == TeacherTypeEnum.MIDDLE_SCHOOL.getValue() || schoolType == TeacherTypeEnum.SPECIAL_SCHOOL.getValue() || schoolType == TeacherTypeEnum.KINDERGARTEN.getValue()){
			isMianIndex = headMap.get("是否属于免费（公费）师范生");
			if(isMianIndex==null){
				errorList.add("找不到【是否属于免费（公费）师范生】列，请检查Excel模板是否正确！");
			}
		
			isJoinBaseIndex = headMap.get("是否参加基层服务项目");
			if(isJoinBaseIndex==null){
				errorList.add("找不到【是否参加基层服务项目】列，请检查Excel模板是否正确！");
			}
		
			joinBaseStartIndex = headMap.get("参加基层服务项目起始年月(yyyy-MM-dd)");
			if(joinBaseStartIndex==null){
				errorList.add("找不到【参加基层服务项目起始年月】列，请检查Excel模板是否正确！");
			}
		
			joinBaseEndIndex = headMap.get("参加基层服务项目结束年月(yyyy-MM-dd)");
			if(joinBaseEndIndex==null){
				errorList.add("找不到【参加基层服务项目结束年月】列，请检查Excel模板是否正确！");
			}
		}
		
		Integer isTjIndex = headMap.get("是否是特级教师");
		if(isTjIndex==null){
			errorList.add("找不到【是否是特级教师】列，请检查Excel模板是否正确！");
		}
		
		Integer isTownUpBoneIndex = null;
		Integer isHealthIndex = null;
		if(schoolType == TeacherTypeEnum.MIDDLE_SCHOOL.getValue() || schoolType == TeacherTypeEnum.SPECIAL_SCHOOL.getValue() || schoolType == TeacherTypeEnum.KINDERGARTEN.getValue()){
			isTownUpBoneIndex = headMap.get("是否是县级及以上骨干教师");
			if (isTownUpBoneIndex == null) {
				errorList.add("找不到【是否是县级及以上骨干教师】列，请检查Excel模板是否正确！");
			}
		
			isHealthIndex = headMap.get("是否心里健康教育教师");
			if (isHealthIndex == null) {
				errorList.add("找不到【是否心里健康教育教师】列，请检查Excel模板是否正确！");
			}
		}
		
		Integer isDoubleTchIndex = null;
		Integer isProfessCardIndex = null;
		Integer workDateTimerIndex = null;
		if(schoolType == TeacherTypeEnum.TECHNICAL_SCHOOL.getValue()){
			isDoubleTchIndex = headMap.get("是否“双师型”教师");
			if (isDoubleTchIndex == null) {
				errorList.add("找不到【是否“双师型”教师】列，请检查Excel模板是否正确！");
			}
			isProfessCardIndex = headMap.get("是否具备职业技能等级证书");
			if (isProfessCardIndex == null) {
				errorList.add("找不到【是否具备职业技能等级证书】列，请检查Excel模板是否正确！");
			}
			workDateTimerIndex = headMap.get("企业工作(实践)时长");
			if (workDateTimerIndex == null) {
				errorList.add("找不到【企业工作(实践)时长】列，请检查Excel模板是否正确！");
			}
		}
		
		Integer personStatusIndex = headMap.get("人员状态");
		if (personStatusIndex == null) {
			errorList.add("找不到【人员状态】列，请检查Excel模板是否正确！");
		}
		
		Integer memoIndex = headMap.get("备注");
		if (memoIndex == null) {
			errorList.add("找不到【备注】列，请检查Excel模板是否正确！");
		}
		
		if(errorList.size()==0){
			//逐条判断
			//性别
			Map<String, Long> sexMap = dictCatlogService.getSelectItemsMap("GENDER");
			//国籍/地区
			Map<String, Long> nationalityMap = dictCatlogService.getSelectItemsMap("COUNTRY_TYPE");
			//身份证件类型
			Map<String, Long> paperTypeMap = dictCatlogService.getSelectItemsMap("PAPER_TYPE");
			//民族
			Map<String, Long> nationMap = dictCatlogService.getSelectItemsMap("NATION");
			//政治面貌
			Map<String, Long> politicTypeMap = dictCatlogService.getSelectItemsMap("POLITIC_TYPE");
			//婚姻状况
			Map<String, Long> marryMap = dictCatlogService.getSelectItemsMap("MARRY");
			//健康状况
			Map<String, Long> healthMap = dictCatlogService.getSelectItemsMap("HEALTH_CONDITION");
			//教职工来源
			Map<String, Long> workerFrom1Map = null;
			if (schoolType == TeacherTypeEnum.KINDERGARTEN.getValue()) {
				workerFrom1Map = dictCatlogService.getSelectItemsMap("WORKER_FROM_2");
			}else{
				workerFrom1Map = dictCatlogService.getSelectItemsMap("WORKER_FROM_1");
			}
			//教职工类别
			Map<String, Long> workerType1Map = null;
			if(schoolType == TeacherTypeEnum.MIDDLE_SCHOOL.getValue()){
				workerType1Map = dictCatlogService.getSelectItemsMap("WORKER_TYPE_1");
			}else if (schoolType == TeacherTypeEnum.TECHNICAL_SCHOOL.getValue()){
				workerType1Map = dictCatlogService.getSelectItemsMap("WORKER_TYPE_2");
			}else if (schoolType == TeacherTypeEnum.SPECIAL_SCHOOL.getValue()){
				workerType1Map = dictCatlogService.getSelectItemsMap("WORKER_TYPE_3");
			}else if (schoolType == TeacherTypeEnum.KINDERGARTEN.getValue()){
				workerType1Map = dictCatlogService.getSelectItemsMap("WORKER_TYPE_4");
			}
			//是否
			Map<String, Long> isFlagMap = dictCatlogService.getSelectItemsMap("IS_FLAG");
			//用人形式
			Map<String, Long> usePersonTypeMap = dictCatlogService.getSelectItemsMap("USE_PERSON_TYPE");
			//签订合同情况
			Map<String, Long> signContractMap = dictCatlogService.getSelectItemsMap("SIGN_CONTRACT");
			//信息技术应用能力
			Map<String, Long> computerAbilityMap = dictCatlogService.getSelectItemsMap("COMPUTER_ABILITY");
			//是否属于免费（公费）师范生
			Map<String, Long> isMianMap = dictCatlogService.getSelectItemsMap("IS_MAIN_FLAG");
			//是否参加基层服务项目
			Map<String, Long> isJoinBaseMap = dictCatlogService.getSelectItemsMap("IS_JOIN_BASE");
			//是否心里健康教育教师
			Map<String, Long> isHealthMap = dictCatlogService.getSelectItemsMap("IS_HEALTH_FLAG");
			//企业工作(实践)时长
			Map<String, Long> workDateTimerMap = dictCatlogService.getSelectItemsMap("WORK_DATE_TIMER");
			//人员状态
			Map<String, Long> personStatusMap = dictCatlogService.getSelectItemsMap("PERSON_STATUS");
			
			for(int i=0;i<fileToList.size();i++){
				
				Teacher teacher = new Teacher();
//				TeacherArchival teacherArchival = new TeacherArchival();
//				teacherArchival.setTid(1l);//为了hibernate validate校验通过,随便设置一个值
//				TeacherEdu teacherEdu = new TeacherEdu();
//				teacherEdu.setTid(1l);
				
				Integer province = 0;
				if(query.getProvince()==0){
					Integer index = headMap.get("省");
					String str = fileToList.get(i)[index];//省汉字名称
					if(StringUtils.isBlank(fileToList.get(i)[index])){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(index+1)+"】列，【省】不能为空！");
					}else{
						Integer no = provinceMap.get(str.replaceAll("　", ""));
						if(no==null){
							errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(index+1)+"】列，【省】名称错误，找不到对应的省名，请参照省市县下拉框！");
						}else{
							province = no;//省赋值
						}
					}
				}else{
					province = query.getProvince();
				}
				teacher.setProvince(province);
				
				Integer city = 0;
				Map<String, Integer> cityMap = cityService.cityMap(province);//市map缓存
				if(query.getCity()==0){
					Integer index = headMap.get("市");
					String str = fileToList.get(i)[index];//市汉字名称
					if(StringUtils.isBlank(fileToList.get(i)[index])){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(index+1)+"】列，【市】不能为空！");
					}else{
						Integer no = cityMap.get(str.replaceAll("　", ""));
						if(no==null){
							errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(index+1)+"】列，【市】名称错误，找不到对应的市名，请参照市县下拉框！");
						}else{
							city = no;//市赋值
						}
					}
				}else{
					city = query.getCity();
				}
				teacher.setCity(city);
				
				Integer town = 0;
				Map<String, Integer> townMap = townService.townMap(city);//区县map缓存
				if(query.getTown()==0){
					Integer index = headMap.get("区县");
					String str = fileToList.get(i)[index];//区县汉字名称
					if(StringUtils.isBlank(fileToList.get(i)[index])){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(index+1)+"】列，【区县】不能为空！");
					}else{
						Integer no = townMap.get(str.replaceAll("　", ""));
						if(no==null){
							errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(index+1)+"】列，【区县】名称错误，找不到对应的区县名，请参照市县下拉框！");
						}else{
							town = no; //区县赋值
						}
					}
				}else{
					town = query.getTown();
				}
				teacher.setTown(town);
				
				Long school = 0l;
				Map<String, Long> schoolMap = schoolService.schoolMap(town);//学校 名词，Id    map缓存
				if(query.getSchool()==0){
					Integer index = headMap.get("学校名称");
					String str = fileToList.get(i)[index];//学校名称汉字名称
					if(StringUtils.isBlank(fileToList.get(i)[index])){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(index+1)+"】列，【学校名称】不能为空！");
					}else{
						Long no = schoolMap.get(str.replaceAll("　", ""));
						if(no==null){
							errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(index+1)+"】列，【学校名称】名称错误，找不到对应的学校名称，请参照学校管理！");
						}else{
							school = no; //学校赋值
						}
					}
				}else{
					school = query.getSchool();
				}
				teacher.setSchool(school);
				
				if(StringUtils.isBlank(fileToList.get(i)[nameIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(nameIndex+1)+"】列，【姓名】不能为空！");
				}else{
					teacher.setName(fileToList.get(i)[nameIndex]);
				}
				
				teacher.setOldName(fileToList.get(i)[hnameIndex]);//曾用名
				
				if(StringUtils.isBlank(fileToList.get(i)[sexIndex])){//性别
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(sexIndex+1)+"】列，【性别】不能为空！");
				}else{
					Long sex = sexMap.get(fileToList.get(i)[sexIndex]);
					if(sex==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(sexIndex+1)+"】列，【性别】名称错误，找不到对应的类别名，请参照查询条件中的【性别】下拉框！");
					}else{
						teacher.setGender(sex);
					}
				}

				if(StringUtils.isBlank(fileToList.get(i)[tchWorkNumIndex])){
					teacher.setTchWorkNum("0");  //教职工号
				}else{
					teacher.setTchWorkNum(fileToList.get(i)[tchWorkNumIndex]);
				}
				
				//国籍/地区
				if(StringUtils.isBlank(fileToList.get(i)[nationalityIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(nationalityIndex+1)+"】列，【国籍/地区】不能为空！");
				}else{
					Long nationality = nationalityMap.get(fileToList.get(i)[nationalityIndex]);
					if(nationality==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(nationalityIndex+1)+"】列，【国籍/地区】名称错误，找不到对应的类别名，请参照查询条件中的【国籍/地区】下拉框！");
					}else{
						teacher.setNationality(nationality);
					}
				}
				
				//身份证件类型
				if(StringUtils.isBlank(fileToList.get(i)[paperTypeIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(paperTypeIndex+1)+"】列，【身份证件类型】不能为空！");
				}else{
					Long paperTyp = paperTypeMap.get(fileToList.get(i)[paperTypeIndex]);
					if(paperTyp==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(paperTypeIndex+1)+"】列，【身份证件类型】名称错误，找不到对应的类别名，请参照查询条件中的【身份证件类型】下拉框！");
					}else{
						teacher.setPaperType(paperTyp);
					}
				}
	
				//身份证件号
				if(StringUtils.isBlank(fileToList.get(i)[idCardIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(idCardIndex+1)+"】列，【身份证件号】不能为空！");
				}else{
					teacher.setIdCard(fileToList.get(i)[idCardIndex]);
				}
				
				//出生日期
				if(StringUtils.isBlank(fileToList.get(i)[birthIndex])){//出生日期
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(birthIndex+1)+"】列，【出生日期】不能为空！");
				}else{
					try {
						Date birthday = DateUtils.parseDate(fileToList.get(i)[birthIndex], DateUtils.DATE_FORMAT_DATEONLY);
						teacher.setBirthday(birthday);
					} catch (ParseException e) {
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(birthIndex+1)+"】列，【出生日期】格式错误！日期应该形如："+DateUtils.DATE_FORMAT_DATEONLY);
					}
				}
				
				//籍贯
				if(StringUtils.isBlank(fileToList.get(i)[nativerIndex])){
					if(schoolType == TeacherTypeEnum.MIDDLE_SCHOOL.getValue() || schoolType == TeacherTypeEnum.KINDERGARTEN.getValue()){
						teacher.setNativer(null);
					}else{
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(nativerIndex+1)+"】列，【籍贯】不能为空！");
					}
				}else{
					teacher.setNativer(fileToList.get(i)[nativerIndex]);
				}
				//出生地
				teacher.setBirthPlace(fileToList.get(i)[birthPlaceIndex]);
				
				//民族
				if(StringUtils.isBlank(fileToList.get(i)[nationIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(nationIndex+1)+"】列，【民族】不能为空！");
				}else{
					Long nation = nationMap.get(fileToList.get(i)[nationIndex]);
					if(nation==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(nationIndex+1)+"】列，【民族】名称错误，找不到对应的类别名，请参照查询条件中的【民族】下拉框！");
					}else{
						teacher.setNation(nation);
					}
				}
				
				//政治面貌
				if(StringUtils.isBlank(fileToList.get(i)[politicTypeIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(politicTypeIndex+1)+"】列，【政治面貌】不能为空！");
				}else{
					String politicType = politicTypeMap.get(fileToList.get(i)[politicTypeIndex]).toString();
					if(politicType==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(politicTypeIndex+1)+"】列，【政治面貌】名称错误，找不到对应的类别名，请参照查询条件中的【政治面貌】下拉框！");
					}else{
						teacher.setPoliticType(politicType);
					}
				}
				
				//婚姻状况
				if(StringUtils.isBlank(fileToList.get(i)[marryIndex])){
					teacher.setMarry(null);
				}else{
					Long marry = marryMap.get(fileToList.get(i)[marryIndex]);
					if(marry==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(marryIndex+1)+"】列，【婚姻状况】名称错误，找不到对应的类别名，请参照查询条件中的【婚姻状况】下拉框！");
					}else{
						teacher.setMarry(marry);
					}
				}
				
				//健康状况
				if(StringUtils.isBlank(fileToList.get(i)[healthIndex])){
					if(schoolType == TeacherTypeEnum.MIDDLE_SCHOOL.getValue() || schoolType == TeacherTypeEnum.KINDERGARTEN.getValue()){
						teacher.setHealth(null);
					}else{
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(healthIndex+1)+"】列，【健康状况】不能为空！");
					}
				}else{
					Long health = healthMap.get(fileToList.get(i)[healthIndex]);
					if(health==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(healthIndex+1)+"】列，【健康状况】名称错误，找不到对应的类别名，请参照查询条件中的【健康状况】下拉框！");
					}else{
						teacher.setHealth(health);
					}
				}

				//参加工作年月
				if(StringUtils.isBlank(fileToList.get(i)[workDayIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(workDayIndex+1)+"】列，【参加工作年月】不能为空！");
				}else{
					try {
						Date workDay = DateUtils.parseDate(fileToList.get(i)[workDayIndex], DateUtils.DATE_FORMAT_DATEONLY);
						teacher.setWorkDay(workDay);
					} catch (ParseException e) {
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(workDayIndex+1)+"】列，【参加工作年月】格式错误！日期应该形如："+DateUtils.DATE_FORMAT_DATEONLY);
					}
				}
				
				if(schoolType == TeacherTypeEnum.SPECIAL_SCHOOL.getValue()){
					//从事特教起始年月
					if(StringUtils.isBlank(fileToList.get(i)[specStartDayIndex])){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(specStartDayIndex+1)+"】列，【从事特教起始年月】不能为空！");
					}else{
						try {
							Date specStartDay = DateUtils.parseDate(fileToList.get(i)[specStartDayIndex], DateUtils.DATE_FORMAT_DATEONLY);
							teacher.setSpecStartDate(specStartDay);
						} catch (ParseException e) {
							errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(specStartDayIndex+1)+"】列，【从事特教起始年月】格式错误！日期应该形如："+DateUtils.DATE_FORMAT_DATEONLY);
						}
					}
				}
				
				//进本校年月
				if(StringUtils.isBlank(fileToList.get(i)[joinSchoolDayIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(joinSchoolDayIndex+1)+"】列，【进本校年月】不能为空！");
				}else{
					try {
						Date joinSchoolDay = DateUtils.parseDate(fileToList.get(i)[joinSchoolDayIndex], DateUtils.DATE_FORMAT_DATEONLY);
						teacher.setJoinSchoolDay(joinSchoolDay);
					} catch (ParseException e) {
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(joinSchoolDayIndex+1)+"】列，【进本校年月】格式错误！日期应该形如："+DateUtils.DATE_FORMAT_DATEONLY);
					}
				}
				
				//教职工来源
				if(StringUtils.isBlank(fileToList.get(i)[workerFrom1Index])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(workerFrom1Index+1)+"】列，【教职工来源】不能为空！");
				}else{
					Long workerFrom1 = workerFrom1Map.get(fileToList.get(i)[workerFrom1Index]);
					if(workerFrom1==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(workerFrom1Index+1)+"】列，【教职工来源】名称错误，找不到对应的类别名，请参照查询条件中的【教职工来源】下拉框！");
					}else{
						if (schoolType == TeacherTypeEnum.KINDERGARTEN.getValue()) {
							teacher.setWorkerFrom2(workerFrom1);
						}else{
							teacher.setWorkerFrom1(workerFrom1);
						}
					}
				}
				
				//教职工类别
				if(StringUtils.isBlank(fileToList.get(i)[workerType1Index])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(workerType1Index+1)+"】列，【教职工类别】不能为空！");
				}else{
					Long workerType1 = workerType1Map.get(fileToList.get(i)[workerType1Index]);
					if(workerType1==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(workerType1Index+1)+"】列，【教职工类别】名称错误，找不到对应的类别名，请参照查询条件中的【教职工类别】下拉框！");
					}else{
						if(schoolType == TeacherTypeEnum.MIDDLE_SCHOOL.getValue()){
							teacher.setWorkerType1(workerType1);
						}else if (schoolType == TeacherTypeEnum.TECHNICAL_SCHOOL.getValue()){
							teacher.setWorkerType2(workerType1);
						}else if (schoolType == TeacherTypeEnum.SPECIAL_SCHOOL.getValue()){
							teacher.setWorkerType3(workerType1);
						}else if (schoolType == TeacherTypeEnum.KINDERGARTEN.getValue()){
							teacher.setWorkerType4(workerType1);
						}
					}
				}			
				//是否在编
				if(StringUtils.isBlank(fileToList.get(i)[atSchoolIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(atSchoolIndex+1)+"】列，【是否在编】不能为空！");
				}else{
					Long atSchool = isFlagMap.get(fileToList.get(i)[atSchoolIndex]);
					if(atSchool==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(atSchoolIndex+1)+"】列，【是否在编】名称错误，找不到对应的类别名，请参照查询条件中的【是否在编】下拉框！");
					}else{
						teacher.setAtSchool(atSchool);
					}
				}
				//用人形式
				if(("0-否").equals(fileToList.get(i)[atSchoolIndex])){
				if(StringUtils.isBlank(fileToList.get(i)[usePersonTypeIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(usePersonTypeIndex+1)+"】列，【用人形式】不能为空！");
				}else{
					Long usePersonType = usePersonTypeMap.get(fileToList.get(i)[usePersonTypeIndex]);
					if(usePersonType==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(usePersonTypeIndex+1)+"】列，【用人形式】名称错误，找不到对应的类别名，请参照查询条件中的【用人形式】下拉框！");
					}else{
						teacher.setUsePersonType(usePersonType);
					}
				}
				}else {
					teacher.setUsePersonType(null);
				}
				//签订合同情况
				if(StringUtils.isBlank(fileToList.get(i)[signContractIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(signContractIndex+1)+"】列，【签订合同情况】不能为空！");
				}else{
					Long signContract = signContractMap.get(fileToList.get(i)[signContractIndex]);
					if(signContract==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(signContractIndex+1)+"】列，【签订合同情况】名称错误，找不到对应的类别名，请参照查询条件中的【签订合同情况】下拉框！");
					}else{
						teacher.setSignContract(signContract);
					}
				}
				
				if(schoolType == TeacherTypeEnum.MIDDLE_SCHOOL.getValue() || schoolType == TeacherTypeEnum.SPECIAL_SCHOOL.getValue() || schoolType == TeacherTypeEnum.KINDERGARTEN.getValue()){
				//是否全日制师范类专业毕业
				if(StringUtils.isBlank(fileToList.get(i)[isQrzIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(isQrzIndex+1)+"】列，【是否全日制师范类专业毕业】不能为空！");
				}else{
					Long isQrz = isFlagMap.get(fileToList.get(i)[isQrzIndex]);
					if(isQrz==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(isQrzIndex+1)+"】列，【是否全日制师范类专业毕业】名称错误，找不到对应的类别名，请参照查询条件中的【是否全日制师范类专业毕业】下拉框！");
					}else{
						teacher.setIsQrz(isQrz);
					}
				}
				if(schoolType == TeacherTypeEnum.SPECIAL_SCHOOL.getValue()){
					//是否全日制特殊教育专业毕业
					if(StringUtils.isBlank(fileToList.get(i)[isTjzyIndex])){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(isTjzyIndex+1)+"】列，【是否全日制特殊教育专业毕业】不能为空！");
					}else{
						Long isTjzy = isFlagMap.get(fileToList.get(i)[isTjzyIndex]);
						if(isTjzy==null){
							errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(isTjzyIndex+1)+"】列，【是否全日制特殊教育专业毕业】名称错误，找不到对应的类别名，请参照查询条件中的【是否全日制特殊教育专业毕业】下拉框！");
						}else{
							//teacher.setIsTjpx(isTjzy);
						}
					}
				}
				
				if(schoolType == TeacherTypeEnum.MIDDLE_SCHOOL.getValue() || schoolType == TeacherTypeEnum.SPECIAL_SCHOOL.getValue()){
				//是否受过特教专业培养培训
				if(StringUtils.isBlank(fileToList.get(i)[isTjpxIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(isTjpxIndex+1)+"】列，【是否受过特教专业培养培训】不能为空！");
				}else{
					Long isTjpx = isFlagMap.get(fileToList.get(i)[isTjpxIndex]);
					if(isTjpx==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(isTjpxIndex+1)+"】列，【是否受过特教专业培养培训】名称错误，找不到对应的类别名，请参照查询条件中的【是否受过特教专业培养培训】下拉框！");
					}else{
						teacher.setIsTjpx(isTjpx);
					}
				}
				//是否有特殊教育从业证书
				if(StringUtils.isBlank(fileToList.get(i)[isTszsIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(isTszsIndex+1)+"】列，【是否有特殊教育从业证书】不能为空！");
				}else{
					Long isTszs = isFlagMap.get(fileToList.get(i)[isTszsIndex]);
					if(isTszs==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(isTszsIndex+1)+"】列，【是否有特殊教育从业证书】名称错误，找不到对应的类别名，请参照查询条件中的【是否有特殊教育从业证书】下拉框！");
					}else{
						teacher.setIsTszs(isTszs);
					}
				}
				}
				
				if(schoolType == TeacherTypeEnum.KINDERGARTEN.getValue()){
					//是否全日制学前教育专业毕业
					if(StringUtils.isBlank(fileToList.get(i)[isPreTchIndex])){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(isPreTchIndex+1)+"】列，【是否全日制学前教育专业毕业】不能为空！");
					}else{
						Long isPreTch = isFlagMap.get(fileToList.get(i)[isPreTchIndex]);
						if(isPreTch==null){
							errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(isPreTchIndex+1)+"】列，【是否全日制学前教育专业毕业】名称错误，找不到对应的类别名，请参照查询条件中的【是否全日制学前教育专业毕业】下拉框！");
						}else{
							teacher.setIsPreTch(isPreTch);
						}
					}
					//是否受过学前教育专业培养培训
					if(StringUtils.isBlank(fileToList.get(i)[isPreTrainIndex])){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(isPreTrainIndex+1)+"】列，【是否受过学前教育专业培养培训】不能为空！");
					}else{
						Long isPreTrain = isFlagMap.get(fileToList.get(i)[isPreTrainIndex]);
						if(isPreTrain==null){
							errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(isPreTrainIndex+1)+"】列，【是否受过学前教育专业培养培训】名称错误，找不到对应的类别名，请参照查询条件中的【是否受过学前教育专业培养培训】下拉框！");
						}else{
							teacher.setIsPreTrain(isPreTrain);
						}
					}
				}
				}
				//信息技术应用能力
				if(StringUtils.isBlank(fileToList.get(i)[computerAbilityIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(computerAbilityIndex+1)+"】列，【信息技术应用能力】不能为空！");
				}else{
					Long computerAbility = computerAbilityMap.get(fileToList.get(i)[computerAbilityIndex]);
					if(computerAbility==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(computerAbilityIndex+1)+"】列，【信息技术应用能力】名称错误，找不到对应的类别名，请参照查询条件中的【信息技术应用能力】下拉框！");
					}else{
						teacher.setComputerAbility(computerAbility);
					}
				}
				if(schoolType == TeacherTypeEnum.MIDDLE_SCHOOL.getValue() || schoolType == TeacherTypeEnum.SPECIAL_SCHOOL.getValue() || schoolType == TeacherTypeEnum.KINDERGARTEN.getValue()){
				//是否属于免费（公费）师范生
				if(StringUtils.isBlank(fileToList.get(i)[isMianIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(isMianIndex+1)+"】列，【是否属于免费（公费）师范生】不能为空！");
					teacher.setIsMian(null);
				}else{
					Long isMian = isMianMap.get(fileToList.get(i)[isMianIndex]);
					if(isMian==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(isMianIndex+1)+"】列，【是否属于免费（公费）师范生】名称错误，找不到对应的类别名，请参照查询条件中的【是否属于免费（公费）师范生】下拉框！");
					}else{
						teacher.setIsMian(isMian);
					}
				}
				//是否参加基层服务项目
				if(StringUtils.isBlank(fileToList.get(i)[isJoinBaseIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(isJoinBaseIndex+1)+"】列，【是否参加基层服务项目】不能为空！");
				}else{
					Long isJoinBase = isJoinBaseMap.get(fileToList.get(i)[isJoinBaseIndex]);
					if(isJoinBase==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(isJoinBaseIndex+1)+"】列，【是否参加基层服务项目】名称错误，找不到对应的类别名，请参照查询条件中的【是否参加基层服务项目】下拉框！");
					}else{
						teacher.setIsJoinBase(isJoinBase);
					}
				}
				//参加基层服务项目起始年月
				if(StringUtils.isBlank(fileToList.get(i)[joinBaseStartIndex])){
					//errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(joinBaseStartIndex+1)+"】列，【参加基层服务项目起始年月】不能为空！");
				}else{
					try {
						Date joinBaseStartDay = DateUtils.parseDate(fileToList.get(i)[joinBaseStartIndex], DateUtils.DATE_FORMAT_DATEONLY);
						teacher.setJoinBaseStart(joinBaseStartDay);
					} catch (ParseException e) {
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(joinBaseStartIndex+1)+"】列，【参加基层服务项目起始年月】格式错误！日期应该形如："+DateUtils.DATE_FORMAT_DATEONLY);
					}
				}
				//参加基层服务项目结束年月	
				if(StringUtils.isBlank(fileToList.get(i)[joinBaseEndIndex])){//出生日期
					//errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(joinBaseEndIndex+1)+"】列，【参加基层服务项目结束年月】不能为空！");
				}else{
					try {
						Date joinBaseEndDay = DateUtils.parseDate(fileToList.get(i)[joinBaseEndIndex], DateUtils.DATE_FORMAT_DATEONLY);
						teacher.setJoinBaseEnd(joinBaseEndDay);
					} catch (ParseException e) {
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(joinBaseEndIndex+1)+"】列，【参加基层服务项目结束年月】格式错误！日期应该形如："+DateUtils.DATE_FORMAT_DATEONLY);
					}
				}
				}
				//是否是特级教师	
				if(StringUtils.isBlank(fileToList.get(i)[isTjIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(isTjIndex+1)+"】列，【是否是特级教师】不能为空！");
				}else{
					Long isTj = isFlagMap.get(fileToList.get(i)[isTjIndex]);
					if(isTj==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(isTjIndex+1)+"】列，【是否是特级教师】名称错误，找不到对应的类别名，请参照查询条件中的【是否是特级教师】下拉框！");
					}else{
						teacher.setIsTj(isTj);
					}
				}
				
				if(schoolType == TeacherTypeEnum.MIDDLE_SCHOOL.getValue() || schoolType == TeacherTypeEnum.SPECIAL_SCHOOL.getValue() || schoolType == TeacherTypeEnum.KINDERGARTEN.getValue()){
				//是否是县级及以上骨干教师	
				if(StringUtils.isBlank(fileToList.get(i)[isTownUpBoneIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(isTownUpBoneIndex+1)+"】列，【是否是县级及以上骨干教师】不能为空！");
				}else{
					Long isTownUpBone = isFlagMap.get(fileToList.get(i)[isTownUpBoneIndex]);
					if(isTownUpBone==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(isTownUpBoneIndex+1)+"】列，【是否是县级及以上骨干教师】名称错误，找不到对应的类别名，请参照查询条件中的【是否是县级及以上骨干教师】下拉框！");
					}else{
						teacher.setIsTownUpBone(isTownUpBone);
					}
				}
				//是否心里健康教育教师	
				if(StringUtils.isBlank(fileToList.get(i)[isHealthIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(isHealthIndex+1)+"】列，【是否心里健康教育教师】不能为空！");
				}else{
					Long isHealth = isHealthMap.get(fileToList.get(i)[isHealthIndex]);
					if(isHealth==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(isHealthIndex+1)+"】列，【是否心里健康教育教师】名称错误，找不到对应的类别名，请参照查询条件中的【是否心里健康教育教师】下拉框！");
					}else{
						teacher.setIsHealth(isHealth);
					}
				}
				}
				
				if(schoolType == TeacherTypeEnum.TECHNICAL_SCHOOL.getValue()){
					//是否“双师型”教师	
					if(StringUtils.isBlank(fileToList.get(i)[isDoubleTchIndex])){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(isDoubleTchIndex+1)+"】列，【是否“双师型”教师】不能为空！");
					}else{
						Long isDoubleTch = isFlagMap.get(fileToList.get(i)[isDoubleTchIndex]);
						if(isDoubleTch==null){
							errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(isDoubleTchIndex+1)+"】列，【是否“双师型”教师】名称错误，找不到对应的类别名，请参照查询条件中的【是否“双师型”教师】下拉框！");
						}else{
							teacher.setIsDoubleTch(isDoubleTch);
						}
					}
					//是否具备职业技能等级证书	
					if(StringUtils.isBlank(fileToList.get(i)[isProfessCardIndex])){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(isProfessCardIndex+1)+"】列，【是否具备职业技能等级证书】不能为空！");
					}else{
						Long isProfessCard = isFlagMap.get(fileToList.get(i)[isProfessCardIndex]);
						if(isProfessCard==null){
							errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(isProfessCardIndex+1)+"】列，【是否具备职业技能等级证书】名称错误，找不到对应的类别名，请参照查询条件中的【是否具备职业技能等级证书】下拉框！");
						}else{
							teacher.setIsProfessCard(isProfessCard);
						}
					}
					//企业工作(实践)时长	
					if(StringUtils.isBlank(fileToList.get(i)[workDateTimerIndex])){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(workDateTimerIndex+1)+"】列，【企业工作(实践)时长】不能为空！");
					}else{
						Long workDateTimer = workDateTimerMap.get(fileToList.get(i)[workDateTimerIndex]);
						if(workDateTimer==null){
							errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(workDateTimerIndex+1)+"】列，【企业工作(实践)时长】名称错误，找不到对应的类别名，请参照查询条件中的【企业工作(实践)时长】下拉框！");
						}else{
							teacher.setWorkDateTimer(workDateTimer);
						}
					}
				}
				
				//人员状态	
				if(StringUtils.isBlank(fileToList.get(i)[personStatusIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(personStatusIndex+1)+"】列，【人员状态】不能为空！");
				}else{
					Long personStatus = personStatusMap.get(fileToList.get(i)[personStatusIndex]);
					if(personStatus==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(personStatusIndex+1)+"】列，【人员状态】名称错误，找不到对应的类别名，请参照查询条件中的【人员状态】下拉框！");
					}else{
						teacher.setPersonStatus(personStatus);
					}
				}
				
				//备注
				teacher.setMemo(fileToList.get(i)[memoIndex]);
				
//				teacher.setTel(fileToList.get(i)[telIndex]);//办公电话
//				teacher.setMobile(fileToList.get(i)[mobileIndex]);//手机
//				teacher.setEmail(fileToList.get(i)[emailIndex]);//Email
				
				/* *人员类型	行政职务	职称	 *教师资格	
				骨干类型	非专任岗位	*政治面貌	参加党派时间	/
				*/
				
		/*		Map<String, Long> personTypeMap = dictCatlogService.getSelectItemsMap("PERSON_TYPE");//人员类型
				if(StringUtils.isBlank(fileToList.get(i)[personTypeIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(personTypeIndex+1)+"】列，【人员类型】不能为空！");
				}else{
					Long personType = personTypeMap.get(fileToList.get(i)[personTypeIndex]);
					if(personType==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(personTypeIndex+1)+"】列，【人员类型】名称错误，找不到对应的类型名，请参照查询条件中的【人员类型】下拉框！");
					}else{
						teacher.setPersonType(personType);
					}
				}
				
				Map<String, Long> dutyTypeMap = dictCatlogService.getSelectItemsMap("DUTY_TYPE");//行政职务
				if(StringUtils.isBlank(fileToList.get(i)[dutyIndex])){
					//errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(dutyIndex+1)+"】列，【行政职务】不能为空！");
				}else{
					Long dutyType = dutyTypeMap.get(fileToList.get(i)[dutyIndex]);
					if(dutyType==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(dutyIndex+1)+"】列，【行政职务】名称错误，找不到对应的名称，请参照查询条件中的【行政职务】下拉框！");
					}else{
						teacher.setDuty(dutyType);
					}
				}
				
				Map<String, Long> titleMap = dictCatlogService.getSelectItemsMap("TITLE_TYPE");//职称
				if(StringUtils.isBlank(fileToList.get(i)[titleIndex])){
					//errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(titleIndex+1)+"】列，【职称】不能为空！");
				}else{
					Long title = titleMap.get(fileToList.get(i)[titleIndex]);
					if(title==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(titleIndex+1)+"】列，【职称】名称错误，找不到对应的名称，请参照查询条件中的【职称】下拉框！");
					}else{
						teacher.setTitle(title);
					}
				}
				
				Map<String, Long> qualifyMap = dictCatlogService.getSelectItemsMap("QUALIFY_TYPE");//教师资格
				if(StringUtils.isBlank(fileToList.get(i)[qualifyIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(qualifyIndex+1)+"】列，【教师资格】不能为空！");
				}else{
					Long qualify = qualifyMap.get(fileToList.get(i)[qualifyIndex]);
					if(qualify==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(qualifyIndex+1)+"】列，【教师资格】名称错误，找不到对应的名称，请参照查询条件中的【教师资格】下拉框！");
					}else{
						teacher.setQualify(qualify);
					}
				}
				
				Map<String, Long> boneMap = dictCatlogService.getSelectItemsMap("BONE_TYPE");//骨干类型
				if(StringUtils.isBlank(fileToList.get(i)[boneTypeIndex])){
				//	errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(boneTypeIndex+1)+"】列，【骨干类型】不能为空！");
				}else{
					Long bone = boneMap.get(fileToList.get(i)[boneTypeIndex]);
					if(bone==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(boneTypeIndex+1)+"】列，【骨干类型】名称错误，找不到对应的名称，请参照查询条件中的【骨干类型】下拉框！");
					}else{
						teacher.setBoneType(bone);
					}
				}
				
				if (query.getType().equals("0")) {
					Map<String, Long> jobLevelMap = dictCatlogService.getSelectItemsMap("JOB_LEVEL");//非专任岗位
					if(StringUtils.isBlank(fileToList.get(i)[jobLevelIndex])){
						//errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(jobLevelIndex+1)+"】列，【非专任岗位】不能为空！");
					}else{
						Long jobLevel = jobLevelMap.get(fileToList.get(i)[jobLevelIndex]);
						if(jobLevel==null){
							errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(jobLevelIndex+1)+"】列，【非专任岗位】名称错误，找不到对应的名称，请参照查询条件中的【非专任岗位】下拉框！");
						}else{
							teacher.setJobLevel(jobLevel);
						}
					}
				}else{
					if(StringUtils.isBlank(fileToList.get(i)[joinCadresIndex])){
					
					}else{
						try {
							Date joinCadresDay = DateUtils.parseDate(fileToList.get(i)[joinCadresIndex], DateUtils.DATE_FORMAT_DATEONLY);
							teacher.setJoinCadresDay(joinCadresDay);
						} catch (ParseException e) {
							errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(joinCadresIndex+1)+"】列，【任校级干部时间】格式错误！日期应该形如："+DateUtils.DATE_FORMAT_DATEONLY);
						}
					}
				}
				Map<String, Long> politicMap = dictCatlogService.getSelectItemsMap("POLITIC_TYPE");//政治面貌
				if(StringUtils.isBlank(fileToList.get(i)[politicIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(politicIndex+1)+"】列，【政治面貌】不能为空！");
				}else{
					Long politic = politicMap.get(fileToList.get(i)[politicIndex]);
					if(politic==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(politicIndex+1)+"】列，【政治面貌】名称错误，找不到对应的名称，请参照查询条件中的【政治面貌】下拉框！");
					}else{
						teacher.setPolitic(politic);
					}
				}
				
				if(StringUtils.isBlank(fileToList.get(i)[joinDayIndex])){//参加党派时间
					//errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(joinDayIndex+1)+"】列，【参加党派时间】不能为空！");
				}else{
					try {
						Date joinDay = DateUtils.parseDate(fileToList.get(i)[joinDayIndex], DateUtils.DATE_FORMAT_DATEONLY);
						teacher.setJoinDay(joinDay);
					} catch (ParseException e) {
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(joinDayIndex+1)+"】列，【参加党派时间】格式错误！日期应该形如："+DateUtils.DATE_FORMAT_DATEONLY);
					}
				}
				
				 * *学历	*所学专业	*毕业学校	从教时间	参加工作时间	学段	学科	年级 在岗状态
				 
				Map<String, Long> degreeMap = dictCatlogService.getSelectItemsMap("DEGREE");//学历
				if(StringUtils.isBlank(fileToList.get(i)[degreeIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(degreeIndex+1)+"】列，【学历】不能为空！");
				}else{
					Long degree = degreeMap.get(fileToList.get(i)[degreeIndex]);
					if(degree==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(degreeIndex+1)+"】列，【学历】名称错误，找不到对应的名称，请参照查询条件中的【学历】下拉框！");
					}else{
						teacher.setDegree(degree);
					}
				}
				
				if(StringUtils.isBlank(fileToList.get(i)[majorIndex])){//所学专业
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(majorIndex+1)+"】列，【所学专业】不能为空！");
				}else{
					teacher.setMajor(fileToList.get(i)[majorIndex]);
				}
				
				if(StringUtils.isBlank(fileToList.get(i)[univIndex])){//毕业学校
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(univIndex+1)+"】列，【毕业学校】不能为空！");
				}else{
					teacher.setUniv(fileToList.get(i)[univIndex]);
				}
				
				if(StringUtils.isBlank(fileToList.get(i)[teachedDayIndex])){//从教时间
					//errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(teachedDayIndex+1)+"】列，【从教时间】不能为空！");
				}else{
					try {
						Date teachedDay = DateUtils.parseDate(fileToList.get(i)[teachedDayIndex], DateUtils.DATE_FORMAT_DATEONLY);
						teacher.setTeachedDay(teachedDay);
					} catch (ParseException e) {
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(teachedDayIndex+1)+"】列，【从教时间】格式错误！日期应该形如："+DateUtils.DATE_FORMAT_DATEONLY);
					}
				}
				
				if(StringUtils.isBlank(fileToList.get(i)[workedDayIndex])){//参加工作时间
					//errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(workedDayIndex+1)+"】列，【参加工作时间】不能为空！");
				}else{
					try {
						Date workedDay = DateUtils.parseDate(fileToList.get(i)[workedDayIndex], DateUtils.DATE_FORMAT_DATEONLY);
						teacher.setWorkedDay(workedDay);
					} catch (ParseException e) {
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(workedDayIndex+1)+"】列，【参加工作时间】格式错误！日期应该形如："+DateUtils.DATE_FORMAT_DATEONLY);
					}
				}
				
				Long stage = 0l;
				Map<String, Long> stageMap = stageService.stageMap();//学段     map
				if(StringUtils.isBlank(fileToList.get(i)[stageIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(stageIndex+1)+"】列，【学段   】不能为空！");
				}else{
					stage = stageMap.get(fileToList.get(i)[stageIndex]);
					if(stage==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(stageIndex+1)+"】列，【学段   】名称错误，找不到对应的名称，请参照查询条件中的【学段   】下拉框！");
					}else{
						teacher.setStage(stage);
						
							*//**关联学科*//*
							Map<String, Long> courseMap = courseService.courseMap(stage);//学科     map
							if(StringUtils.isBlank(fileToList.get(i)[courseIndex])){
								errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(courseIndex+1)+"】列，【学科  】不能为空！");
							}else{
								Long course = courseMap.get(fileToList.get(i)[courseIndex]);
								if(course==null){
									errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(courseIndex+1)+"】列，【学科   】名称错误，找不到对应的名称，请参照查询条件中的【学科   】下拉框！");
								}else{
									teacher.setCourse(course);
								}
							}
							
							*//**关联年级*//*
							Map<String, Long> gradeMap = gradeService.gradeMap(stage);//年级     map
							if(StringUtils.isBlank(fileToList.get(i)[gradeIndex])){
							//	errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(gradeIndex+1)+"】列，【年级  】不能为空！");
							}else{
								Long grade = gradeMap.get(fileToList.get(i)[gradeIndex]);
								if(grade==null){
									errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(gradeIndex+1)+"】列，【年级   】名称错误，找不到对应的名称，请参照查询条件中的【年级   】下拉框！");
								}else{
									teacher.setGrade(grade);
								}
							}
					}
				}
				
				if(StringUtils.isBlank(fileToList.get(i)[jobStatusIndex])){
					//errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(jobStatusIndex+1)+"】列，【在岗状态】不能为空！");
				}else{
					Map<String, Long> jboStatusMap = dictCatlogService.getSelectItemsMap("JOB_STATUS");//在岗状态
					Long jobStatus = jboStatusMap.get(fileToList.get(i)[jobStatusIndex]);
					if(jobStatus==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(jobStatusIndex+1)+"】列，【在岗状态】名称错误，找不到对应的名称，请参照查询条件中的【在岗状态】下拉框！");
					}else{
						teacher.setDegree(jobStatus);
					}
				}
				
				if(query.getType().equals("0")){
					if(StringUtils.isBlank(fileToList.get(i)[headTeacherIndex])){//班主任
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(headTeacherIndex+1)+"】列，【班主任】不能为空！");
					}else{
						if(fileToList.get(i)[headTeacherIndex].equals("是")){
							teacher.setHeadTeacher(1);
						}else if(fileToList.get(i)[headTeacherIndex].equals("否")){
							teacher.setHeadTeacher(0);
						}else{
							errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(headTeacherIndex+1)+"】列，【班主任】只能为是或否！");
						}
					}
				}*/
				teacherMap.put( i+2,teacher);
//				teacherArchivalMap.put( i+2,teacherArchival);
//				teacherEduMap.put( i+2,teacherEdu);
				
				
				
			}
		}
		
		//*********************校验Excel表重复的身份证
		validateExcelIdCardName(teacherMap, errorList);
		
		//************************最后一步，hibernate validate校验****************************************************
		validateHibernateT(teacherMap, errorList);
//		validateHibernateTA(teacherArchivalMap, errorList);
//		validateHibernateTE(teacherEduMap, errorList);
		
		//字符串含有数字的排序
		List<OrderWrapper> chinesesOrderList = new ArrayList<OrderWrapper>();
		for(String error:errorList){
			chinesesOrderList.add(new OrderWrapper(error));
		}
		Collections.sort(chinesesOrderList);
		
		//排序后转换
		List<String> sortErrorList = Lists.newArrayList();
		for(OrderWrapper ow:chinesesOrderList){
			sortErrorList.add(ow.getName());
		}
		
		return sortErrorList;
		
	}
	

	/**
	 * hibernate validate校验
	 * @param teacherMap
	 * @param errorList
	 */
	private void validateHibernateT(Map<Integer, Teacher> teacherMap,
			List<String> errorList) {
		if(errorList.size()==0){
			Iterator<Map.Entry<Integer, Teacher>> entries = teacherMap.entrySet().iterator();
			while (entries.hasNext()) {
			    Map.Entry<Integer, Teacher> entry = entries.next();
			    Integer key = entry.getKey();
			    Teacher value = entry.getValue();
				
			    ValidatorResult validateResult = BeanValidator.validateResult(value, Insert.class);
				if(!validateResult.getFlag()){
					List<ValidatorBean> errorObjs = validateResult.getErrorObjs();
					for(ValidatorBean error:errorObjs){
						errorList.add("第【"+key+"】行,错误信息：数据表字段名("+ error.getFiled()+"):" + error.getErrorMsg());
					}
				}
				if(!checkIdCardExit(value)){
					errorList.add("第【"+key+"】行，身份证号为【"+value.getIdCard()+"】,在数据库里已经存在");
				}
			}
		}
		
	}
	
	/**
	 * hibernate validate校验
	 * @param teacherArchivalMap
	 * @param errorList
	 */
	@SuppressWarnings("unused")
	private void validateHibernateTA(
			Map<Integer, TeacherArchival> teacherArchivalMap,
			List<String> errorList) {
		if(errorList.size()==0){
			Iterator<Map.Entry<Integer, TeacherArchival>> entries = teacherArchivalMap.entrySet().iterator();
			while (entries.hasNext()) {
			    Map.Entry<Integer, TeacherArchival> entry = entries.next();
			    Integer key = entry.getKey();
			    TeacherArchival value = entry.getValue();
				
			    ValidatorResult validateResult = BeanValidator.validateResult(value, Insert.class);
				if(!validateResult.getFlag()){
					List<ValidatorBean> errorObjs = validateResult.getErrorObjs();
					for(ValidatorBean error:errorObjs){
						errorList.add("第【"+key+"】行,错误信息：数据表字段名("+ error.getFiled()+"):" + error.getErrorMsg());
					}
				}
			}
		}
	}
	
	/**
	 * hibernate validate校验
	 * @param teacherEduMap
	 * @param errorList
	 */
	@SuppressWarnings("unused")
	private void validateHibernateTE(Map<Integer, TeacherEdu> teacherEduMap,
			List<String> errorList) {
		if(errorList.size()==0){
			Iterator<Map.Entry<Integer, TeacherEdu>> entries = teacherEduMap.entrySet().iterator();
			while (entries.hasNext()) {
			    Map.Entry<Integer, TeacherEdu> entry = entries.next();
			    Integer key = entry.getKey();
			    TeacherEdu value = entry.getValue();
				
			    ValidatorResult validateResult = BeanValidator.validateResult(value, Insert.class);
				if(!validateResult.getFlag()){
					List<ValidatorBean> errorObjs = validateResult.getErrorObjs();
					for(ValidatorBean error:errorObjs){
						errorList.add("第【"+key+"】行,错误信息：数据表字段名("+ error.getFiled()+"):" + error.getErrorMsg());
					}
				}
			}
		}
	}

	/**
	 * 校验Excel表重复的 身份证号
	 * @param teacherMap
	 * @param errorList
	 */
	private void validateExcelIdCardName(Map<Integer, Teacher> teacherMap,
			List<String> errorList) {
		List<String> idCardList = Lists.newArrayList();//Excel（市+学校）的字符串集合
		if(errorList.size()==0){
			Iterator<Map.Entry<Integer, Teacher>> entries = teacherMap.entrySet().iterator();
			while (entries.hasNext()) {
			    Map.Entry<Integer, Teacher> entry = entries.next();
			    Teacher value = entry.getValue();
				//Excel重复值校验
				String idcard = value.getIdCard();//idcard判断唯一
				idCardList.add(idcard);
			}
			
			Iterator<Map.Entry<Integer, Teacher>> idcardEntries = teacherMap.entrySet().iterator();
			while (idcardEntries.hasNext()) {
			    Map.Entry<Integer, Teacher> entry = idcardEntries.next();
			    Integer key = entry.getKey();
			    Teacher value = entry.getValue();
			    String idcard = value.getIdCard();
				//统计idcard出现的次数
				int count = Collections.frequency(idCardList, idcard);
				if(count>1){
					errorList.add("第【"+key+"】行，身份证为【"+value.getIdCard()+"】,在Excel表中存在相同的身份证号，请修改！");
				}
			}
			
		}
	}

	/**
	 * 从Excel中导入
	 * @param file
	 * @throws ParseException 
	 * @throws Exception
	 */
	@Override
	@Transactional
	public Integer importExcel(MultipartFile file,TeacherQuery query) throws ParseException{
		List<String[]> fileToList = ExcelImportUtils.fileToList(file);
		String[] head = fileToList.get(0);//表头
		Map<String,Integer> headMap = Maps.newHashMap();
		Pattern p = Pattern.compile("\\*|\t|\r|\n");
		for(int i=0;i<head.length;i++){
			Matcher m = p.matcher(head[i]);
			String dest = m.replaceAll("");
			//headMap.put(head[i].replaceAll("\\*", ""), i);//获取表头名字，并去掉*，用于根据表头名匹配数据
			headMap.put(dest, i);
			//System.out.println("head["+i+"]"+head[i]+"--"+dest);
		}
		
		fileToList.remove(0);//删除表头数据，剩下的都是实际数据
		
		Map<String, Integer> provinceMap = provinceService.provinceMap();//省map缓存
		
		//学校类型  0：中小学  1：中等职业学校  2：特教  3：幼儿园
		int schoolType = Integer.parseInt(query.getSchoolType());
		
		//性别
		Map<String, Long> sexMap = dictCatlogService.getSelectItemsMap("GENDER");
		//国籍/地区
		Map<String, Long> nationalityMap = dictCatlogService.getSelectItemsMap("COUNTRY_TYPE");
		//身份证件类型
		Map<String, Long> paperTypeMap = dictCatlogService.getSelectItemsMap("PAPER_TYPE");
		//民族
		Map<String, Long> nationMap = dictCatlogService.getSelectItemsMap("NATION");
		//政治面貌
		Map<String, Long> politicTypeMap = dictCatlogService.getSelectItemsMap("POLITIC_TYPE");
		//婚姻状况
		Map<String, Long> marryMap = dictCatlogService.getSelectItemsMap("MARRY");
		//健康状况
		Map<String, Long> healthMap = dictCatlogService.getSelectItemsMap("HEALTH_CONDITION");
		//教职工来源
		Map<String, Long> workerFrom1Map = null;
		if (schoolType == TeacherTypeEnum.KINDERGARTEN.getValue()) {
			workerFrom1Map = dictCatlogService.getSelectItemsMap("WORKER_FROM_2");
		}else{
			workerFrom1Map = dictCatlogService.getSelectItemsMap("WORKER_FROM_1");
		}
		//教职工类别
		Map<String, Long> workerType1Map = null;
		if(schoolType == TeacherTypeEnum.MIDDLE_SCHOOL.getValue()){
			workerType1Map = dictCatlogService.getSelectItemsMap("WORKER_TYPE_1");
		}else if (schoolType == TeacherTypeEnum.TECHNICAL_SCHOOL.getValue()){
			workerType1Map = dictCatlogService.getSelectItemsMap("WORKER_TYPE_2");
		}else if (schoolType == TeacherTypeEnum.SPECIAL_SCHOOL.getValue()){
			workerType1Map = dictCatlogService.getSelectItemsMap("WORKER_TYPE_3");
		}else if (schoolType == TeacherTypeEnum.KINDERGARTEN.getValue()){
			workerType1Map = dictCatlogService.getSelectItemsMap("WORKER_TYPE_4");
		}
		//是否
		Map<String, Long> isFlagMap = dictCatlogService.getSelectItemsMap("IS_FLAG");
		//用人形式
		Map<String, Long> usePersonTypeMap = dictCatlogService.getSelectItemsMap("USE_PERSON_TYPE");
		//签订合同情况
		Map<String, Long> signContractMap = dictCatlogService.getSelectItemsMap("SIGN_CONTRACT");
		//信息技术应用能力
		Map<String, Long> computerAbilityMap = dictCatlogService.getSelectItemsMap("COMPUTER_ABILITY");
		//是否属于免费（公费）师范生
		Map<String, Long> isMianMap = dictCatlogService.getSelectItemsMap("IS_MAIN_FLAG");
		//是否参加基层服务项目
		Map<String, Long> isJoinBaseMap = dictCatlogService.getSelectItemsMap("IS_JOIN_BASE");
		//是否心里健康教育教师
		Map<String, Long> isHealthMap = dictCatlogService.getSelectItemsMap("IS_HEALTH_FLAG");
		//企业工作(实践)时长
		Map<String, Long> workDateTimerMap = dictCatlogService.getSelectItemsMap("WORK_DATE_TIMER");
		//人员状态
		Map<String, Long> personStatusMap = dictCatlogService.getSelectItemsMap("PERSON_STATUS");
		
		for(String[] row:fileToList){
			Teacher teacher = new Teacher();
			teacher.setType(Integer.parseInt(query.getType()));//设置教师、校长类型
//			TeacherArchival teacherArchival = new TeacherArchival();
//			TeacherEdu teacherEdu = new TeacherEdu();
			
			Integer province = 0;
			if(query.getProvince()==0){
				String str = row[headMap.get("省")];
				province = provinceMap.get(str.replaceAll("　", ""));
			}else{
				province = query.getProvince();
			}
			
			Integer city = 0;
			Map<String, Integer> cityMap = cityService.cityMap(province);//市map缓存
			if(query.getCity()==0){
				String str = row[headMap.get("市")];
				city = cityMap.get(str.replaceAll("　", ""));
			}else{
				city = query.getCity();
			}
			
			Integer town = 0;
			Map<String, Integer> townMap = townService.townMap(city);//区县map缓存
			if(query.getTown()==0){
				String str = row[headMap.get("区县")];
				town = townMap.get(str.replaceAll("　", ""));
			}else{
				town = query.getTown();
			}
			
			Long school = 0l;
			Map<String, Long> schoolMap = schoolService.schoolMap(town);//学校map
			if(query.getSchool()==0){
				String str = row[headMap.get("学校名称")];
				school = schoolMap.get(str.replaceAll("　", ""));
			}else{
				school = query.getSchool();
			}
			
			//省市县
			teacher.setProvince(province);
			teacher.setCity(city);
			teacher.setTown(town);
			teacher.setSchool(school);
			
			teacher.setName(row[headMap.get("姓名")]);
			
			teacher.setOldName(row[headMap.get("曾用名")]);//曾用名
			
			teacher.setGender(sexMap.get(row[headMap.get("性别")]));

			if(StringUtils.isBlank(row[headMap.get("教职工号")])){
				teacher.setTchWorkNum("0");
			}else{
				teacher.setTchWorkNum(row[headMap.get("教职工号")]);
			}
			
			//国籍/地区
			teacher.setNationality(nationalityMap.get(row[headMap.get("国籍/地区")]));
			
			//身份证件类型
			teacher.setPaperType(paperTypeMap.get(row[headMap.get("身份证件类型")]));

			//身份证件号
			teacher.setIdCard(row[headMap.get("身份证件号")]);
			
			//出生日期
			teacher.setBirthday(DateUtils.parseDate(row[headMap.get("出生日期(yyyy-MM-dd)")], DateUtils.DATE_FORMAT_DATEONLY));
			
			//籍贯
			teacher.setNativer(row[headMap.get("籍贯")]);
			//出生地
			teacher.setBirthPlace(row[headMap.get("出生地")]);
			
			//民族
			teacher.setNation(nationMap.get(row[headMap.get("民族")]));
			
			//政治面貌
			teacher.setPoliticType(politicTypeMap.get(row[headMap.get("政治面貌")]).toString());
			
			//婚姻状况
			teacher.setMarry(marryMap.get(row[headMap.get("婚姻状况")]));
			
			//健康状况
			teacher.setHealth(healthMap.get(row[headMap.get("健康状况")]));

			//参加工作年月
			teacher.setWorkDay(DateUtils.parseDate(row[headMap.get("参加工作年月(yyyy-MM-dd)")], DateUtils.DATE_FORMAT_DATEONLY));

			if(schoolType == TeacherTypeEnum.SPECIAL_SCHOOL.getValue()){
				//从事特教起始年月
				teacher.setSpecStartDate(DateUtils.parseDate(row[headMap.get("从事特教起始年月(yyyy-MM-dd)")], DateUtils.DATE_FORMAT_DATEONLY));
			}
			
			//进本校年月
			if(schoolType == TeacherTypeEnum.KINDERGARTEN.getValue()){
				teacher.setJoinSchoolDay(DateUtils.parseDate(row[headMap.get("进本园年月(yyyy-MM-dd)")], DateUtils.DATE_FORMAT_DATEONLY));
			}else{
				teacher.setJoinSchoolDay(DateUtils.parseDate(row[headMap.get("进本校年月(yyyy-MM-dd)")], DateUtils.DATE_FORMAT_DATEONLY));
			}
			//教职工来源
			if (schoolType == TeacherTypeEnum.KINDERGARTEN.getValue()) {
				teacher.setWorkerFrom2(workerFrom1Map.get(row[headMap.get("教职工来源")]));
			}else{
				teacher.setWorkerFrom1(workerFrom1Map.get(row[headMap.get("教职工来源")]));
			}

			//教职工类别
			if(schoolType == TeacherTypeEnum.MIDDLE_SCHOOL.getValue()){
				teacher.setWorkerType1(workerType1Map.get(row[headMap.get("教职工类别")]));
			}else if (schoolType == TeacherTypeEnum.TECHNICAL_SCHOOL.getValue()){
				teacher.setWorkerType2(workerType1Map.get(row[headMap.get("教职工类别")]));
			}else if (schoolType == TeacherTypeEnum.SPECIAL_SCHOOL.getValue()){
				teacher.setWorkerType3(workerType1Map.get(row[headMap.get("教职工类别")]));
			}else if (schoolType == TeacherTypeEnum.KINDERGARTEN.getValue()){
				teacher.setWorkerType4(workerType1Map.get(row[headMap.get("教职工类别")]));
			}
						
			
			//是否在编
			teacher.setAtSchool(isFlagMap.get(row[headMap.get("是否在编")]));
			
			//用人形式
			if(("0-否").equals(row[headMap.get("是否在编")])){
				teacher.setUsePersonType(usePersonTypeMap.get(row[headMap.get("用人形式")]));
			}else{
				teacher.setUsePersonType(null);
			}
			//签订合同情况
			teacher.setSignContract(signContractMap.get(row[headMap.get("签订合同情况")]));
			
			if(schoolType == TeacherTypeEnum.MIDDLE_SCHOOL.getValue() || schoolType == TeacherTypeEnum.SPECIAL_SCHOOL.getValue() || schoolType == TeacherTypeEnum.KINDERGARTEN.getValue()){
				//是否全日制师范类专业毕业
				teacher.setIsQrz(isFlagMap.get(row[headMap.get("是否全日制师范类专业毕业")]));

				if(schoolType == TeacherTypeEnum.SPECIAL_SCHOOL.getValue()){
					//是否全日制特殊教育专业毕业
				}
				if(schoolType == TeacherTypeEnum.MIDDLE_SCHOOL.getValue() || schoolType == TeacherTypeEnum.SPECIAL_SCHOOL.getValue()){
					//是否受过特教专业培养培训
					teacher.setIsTjpx(isFlagMap.get(row[headMap.get("是否受过特教专业培养培训")]));

					//是否有特殊教育从业证书
					teacher.setIsTszs(isFlagMap.get(row[headMap.get("是否有特殊教育从业证书")]));
				}
				if(schoolType == TeacherTypeEnum.KINDERGARTEN.getValue()){
					//是否全日制学前教育专业毕业
					teacher.setIsPreTch(isFlagMap.get(row[headMap.get("是否全日制学前教育专业毕业")]));
					
					//是否受过学前教育专业培养培训
					teacher.setIsPreTrain(isFlagMap.get(row[headMap.get("是否受过学前教育专业培养培训")]));
				}
			}
			//信息技术应用能力
			teacher.setComputerAbility(computerAbilityMap.get(row[headMap.get("信息技术应用能力")]));
			
			if(schoolType == TeacherTypeEnum.MIDDLE_SCHOOL.getValue() || schoolType == TeacherTypeEnum.SPECIAL_SCHOOL.getValue() || schoolType == TeacherTypeEnum.KINDERGARTEN.getValue()){
				//是否属于免费（公费）师范生
				teacher.setIsMian(isMianMap.get(row[headMap.get("是否属于免费（公费）师范生")]));

				//是否参加基层服务项目
				teacher.setIsJoinBase(isJoinBaseMap.get(row[headMap.get("是否参加基层服务项目")]));

				//参加基层服务项目起始年月
				if(row[headMap.get("参加基层服务项目起始年月(yyyy-MM-dd)")] != null && !("").equals(row[headMap.get("参加基层服务项目起始年月(yyyy-MM-dd)")])){
					teacher.setJoinBaseStart(DateUtils.parseDate(row[headMap.get("参加基层服务项目起始年月(yyyy-MM-dd)")], DateUtils.DATE_FORMAT_DATEONLY));
				}
				//参加基层服务项目结束年月
				if(row[headMap.get("参加基层服务项目结束年月(yyyy-MM-dd)")] != null && !("").equals(row[headMap.get("参加基层服务项目结束年月(yyyy-MM-dd)")])){
					teacher.setJoinBaseEnd(DateUtils.parseDate(row[headMap.get("参加基层服务项目结束年月(yyyy-MM-dd)")], DateUtils.DATE_FORMAT_DATEONLY));
				}
			}
			
			//是否是特级教师	
			teacher.setIsTj(isFlagMap.get(row[headMap.get("是否是特级教师")]));

			if(schoolType == TeacherTypeEnum.MIDDLE_SCHOOL.getValue() || schoolType == TeacherTypeEnum.SPECIAL_SCHOOL.getValue() || schoolType == TeacherTypeEnum.KINDERGARTEN.getValue()){
				//是否是县级及以上骨干教师	
				teacher.setIsTownUpBone(isFlagMap.get(row[headMap.get("是否是县级及以上骨干教师")]));

				//是否心里健康教育教师	
				teacher.setIsHealth(isHealthMap.get(row[headMap.get("是否心里健康教育教师")]));
			}
			if(schoolType == TeacherTypeEnum.TECHNICAL_SCHOOL.getValue()){
				//是否“双师型”教师	
				teacher.setIsDoubleTch(isFlagMap.get(row[headMap.get("是否“双师型”教师")]));

				//是否具备职业技能等级证书	
				teacher.setIsProfessCard(isFlagMap.get(row[headMap.get("是否具备职业技能等级证书")]));

				//企业工作(实践)时长	
				teacher.setWorkDateTimer(workDateTimerMap.get(row[headMap.get("企业工作(实践)时长")]));
			}
			
			//人员状态	
			teacher.setPersonStatus(personStatusMap.get(row[headMap.get("人员状态")]));

			//备注
			teacher.setMemo(row[headMap.get("备注")]);
			
//			teacher.setName(row[headMap.get("姓名")]);
//			teacher.setUsedName(row[headMap.get("曾用名")]);
//			if(row[headMap.get("性别")].equals("男")){
//				teacher.setSex(1);
//			}else{
//				teacher.setSex(0);
//			}
			
//			teacher.setBirthday(DateUtils.SHORT_DATE_DASH_FORMAT.parse(row[headMap.get("出生日期")]));
			
//			teacher.setNativer(row[headMap.get("籍贯")]);
			
//			Map<String, Long> nationMap = dictCatlogService.getSelectItemsMap("NATION");
//			teacher.setNation(nationMap.get(row[headMap.get("民族")]));
//			
//			teacher.setIdCard(row[headMap.get("身份证")]);
		/*	teacher.setTel(row[headMap.get("办公电话")]);
			teacher.setMobile(row[headMap.get("手机号")]);
			teacher.setEmail(row[headMap.get("邮箱")]);*/
			
	//		teacher.setStatus(TeacherStatusEnum.UNCONFIRMED.getValue());//状态
			
			/*****************人员类型	行政职务	职称	*教师资格	骨干类型	非专任岗位	*政治面貌	参加党派时间****/
			
			/*Map<String, Long> personTypeMap = dictCatlogService.getSelectItemsMap("PERSON_TYPE");
			teacher.setPersonType(personTypeMap.get(row[headMap.get("人员类型")]));
			
			if(StringUtils.isNotBlank(row[headMap.get("行政职务")])){
				Map<String, Long> dutyMap = dictCatlogService.getSelectItemsMap("DUTY_TYPE");
				teacher.setDuty(dutyMap.get(row[headMap.get("行政职务")]));
			}
			
			if(StringUtils.isNotBlank(row[headMap.get("职称")])){
				Map<String, Long> titleMap = dictCatlogService.getSelectItemsMap("TITLE_TYPE");
				teacher.setTitle(titleMap.get(row[headMap.get("职称")]));
			}
			
			Map<String, Long> qualifyMap = dictCatlogService.getSelectItemsMap("QUALIFY_TYPE");
			teacher.setQualify(qualifyMap.get(row[headMap.get("教师资格")]));
			
			if(StringUtils.isNotBlank(row[headMap.get("骨干类型")])){
				Map<String, Long> boneMap = dictCatlogService.getSelectItemsMap("BONE_TYPE");
				teacher.setBoneType(boneMap.get(row[headMap.get("骨干类型")]));
			}
			
			if(query.getType().equals("0")){
				if(StringUtils.isNotBlank(row[headMap.get("非专任岗位")])){
					Map<String, Long> jobLevelMap = dictCatlogService.getSelectItemsMap("JOB_LEVEL");
					teacher.setJobLevel(jobLevelMap.get(row[headMap.get("非专任岗位")]));
				}
			}else{
				if(StringUtils.isNotBlank(row[headMap.get("任校级干部时间")])){
					teacher.setJoinCadresDay(DateUtils.SHORT_DATE_DASH_FORMAT.parse(row[headMap.get("任校级干部时间")]));
				}
			}
			
			Map<String, Long> politicMap = dictCatlogService.getSelectItemsMap("POLITIC_TYPE");
			teacher.setPolitic(politicMap.get(row[headMap.get("政治面貌")]));
			
			if(StringUtils.isNotBlank(row[headMap.get("参加党派时间")])){
				teacher.setJoinDay(DateUtils.SHORT_DATE_DASH_FORMAT.parse(row[headMap.get("参加党派时间")]));
			}

			*//***************学历	*所学专业	*毕业学校	从教日期	任教日期	*学段	*学科	年级 在岗状态****//*
			
			Map<String, Long> dereeMap = dictCatlogService.getSelectItemsMap("DEGREE");
			teacher.setDegree(dereeMap.get(row[headMap.get("学历")]));
			
			teacher.setMajor(row[headMap.get("所学专业")]);
			teacher.setUniv(row[headMap.get("毕业学校")]);
			
			if(StringUtils.isNotBlank(row[headMap.get("从教时间")])){
				teacher.setTeachedDay(DateUtils.SHORT_DATE_DASH_FORMAT.parse(row[headMap.get("从教时间")]));
			}
			
			if(StringUtils.isNotBlank(row[headMap.get("参加工作时间")])){
				teacher.setWorkedDay(DateUtils.SHORT_DATE_DASH_FORMAT.parse(row[headMap.get("参加工作时间")]));
			}
			
			Map<String, Long> stageMap = stageService.stageMap();
			teacher.setStage(stageMap.get(row[headMap.get("学段")]));
			
			Map<String, Long> courseMap = courseService.courseMap(stageMap.get(row[headMap.get("学段")]));
			teacher.setCourse(courseMap.get(row[headMap.get("学科")]));
			
			if(StringUtils.isNotBlank(row[headMap.get("年级")])){
				Map<String, Long> gradeMap = gradeService.gradeMap(stageMap.get(row[headMap.get("学段")]));
				teacher.setGrade(gradeMap.get(row[headMap.get("年级")]));
			}
			
			if(StringUtils.isNotBlank(row[headMap.get("在岗状态")])){
				Map<String, Long> jobStatusMap = dictCatlogService.getSelectItemsMap("JOB_STATUS");
				teacher.setJobStatus(jobStatusMap.get(row[headMap.get("在岗状态")]));
			}
			
			if(query.getType().equals("0")){
				if(StringUtils.isNotBlank(row[headMap.get("班主任")])){
					if(row[headMap.get("班主任")].equals("是")){
						teacher.setHeadTeacher(1);
					}else{
						teacher.setHeadTeacher(0);
					}
				}
			}*/
			
			this.saveImpTeacher(teacher);//由于需要获取教师编号，所以，只能单条循环插入
			
		}
		
		return fileToList.size();
	}
	
	/**
	 * excel导入的保存方法，不用再校验了，前面已经校验完成了
	 */
	@Override
	@Transactional
	public Long saveImpTeacher(Teacher entity) {
		//教师编号
		String tno = "L"+entity.getTown()+""+AppStringUtils.addZero(12,this.dao.nextSequenceVal());       // ;
		entity.setTno(tno);
		this.dao.insert(entity);
		/**
		 * 生成user账号
		 */
		userService.createTeacherUser(entity);
		return entity.getId();
		
	}
	/************************excel end*******************************/

	@Override
	public YanxiuUserRegister takeRegisterInfo(Teacher teacher) {
		YanxiuUserRegister register = new YanxiuUserRegister();
		return register;
	}

	@Override
	public YanxiuUserSync takeSyncInfo(Teacher teacher) {
		YanxiuUserSync sync = new YanxiuUserSync();
		return sync;
	}
	
	@Override
	public Teacher selectViewInfo(Long id){
		return this.dao.selectViewInfo(id);
	}
	
	@Override
	public Long removeByIds(List<Long> ids){
		Long count = 0l;
		for(Long id:ids){
			Teacher teacher = this.selectById(id);
			if(teacher!=null && teacher.getIdCard()!=null){
				userService.removeByLoginName(teacher.getIdCard());
			}
			count++;
			
			//this.deleteById(id);
			this.dao.removeById(id);
			//teacherArchivalService.deleteByTid(id);
		}
		return count;
	}
	
}