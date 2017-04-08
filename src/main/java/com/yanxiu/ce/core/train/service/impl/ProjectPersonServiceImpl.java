package com.yanxiu.ce.core.train.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yanxiu.ce.common.constant.AppConstant;
import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.redis.JedisClient;
import com.yanxiu.ce.common.utils.OrderWrapper;
import com.yanxiu.ce.common.utils.excel.ExcelImportUtils;
import com.yanxiu.ce.core.basic.entity.Teacher;
import com.yanxiu.ce.core.basic.entity.TeacherQuery;
import com.yanxiu.ce.core.basic.enums.ExcelColEnum;
import com.yanxiu.ce.core.basic.service.SchoolService;
import com.yanxiu.ce.core.basic.service.TeacherService;
import com.yanxiu.ce.core.score.entity.Score;
import com.yanxiu.ce.core.score.enums.ScoreStatusEnum;
import com.yanxiu.ce.core.score.enums.ScoreTypeEnum;
import com.yanxiu.ce.core.score.service.ScoreService;
import com.yanxiu.ce.core.train.dao.ProjectPersonDao;
import com.yanxiu.ce.core.train.entity.Project;
import com.yanxiu.ce.core.train.entity.ProjectPerson;
import com.yanxiu.ce.core.train.entity.ProjectPersonQuery;
import com.yanxiu.ce.core.train.enums.ScoreCreateTypeEnum;
import com.yanxiu.ce.core.train.service.ProjectPersonService;
import com.yanxiu.ce.core.train.service.ProjectService;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.service.CityService;
import com.yanxiu.ce.system.service.DictCatlogService;
import com.yanxiu.ce.system.service.DictItemService;
import com.yanxiu.ce.system.service.ProvinceService;
import com.yanxiu.ce.system.service.TownService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * 参培人名单管理
 * @author tangmin
 * @date 2016-10-24 16:32:04
 */
@Service("projectPersonService")
public class ProjectPersonServiceImpl extends BaseServiceImpl<ProjectPerson, ProjectPersonQuery> implements ProjectPersonService{
	@Autowired
	private ProjectPersonDao dao;

	@Override
	protected BaseDao<ProjectPerson, ProjectPersonQuery> dao() {
		return this.dao;
	}

	@Autowired
	private JedisClient jedisClient;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private DictItemService dictItemService;

	@Autowired
	private ScoreService scoreService;
	
	@Autowired
	private DictCatlogService dictCatlogService;


	@Autowired
	private ProvinceService provinceService;

	@Autowired
	private CityService cityService;

	@Autowired
	private TownService townService;

	@Autowired
	private SchoolService schoolService;

	private boolean running = false;
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
	public Boolean checkNameExit(ProjectPerson entity) {

		return true;
	}

	@Override
	public Boolean checkPidTidExit(ProjectPerson entity) {
		Long count = this.dao.selectCheckPidTidExit(entity.getPid(), entity.getTid());
		if(count>0){
			return true;
		}else {
			return false;
		}
	}

	/**
	 * 新增or修改
	 */
	@Override
	@Transactional
	public String saveOrUpdate(ProjectPerson entity) {
		String msg = "";
		if(entity.getId()==null){
			this.insert(entity);
			msg = "添加成功！";
		}else {
			this.update(entity);
				msg = "编辑成功！";
		}
		return msg;
	}

    @Override
    public List<String> checkExcel(MultipartFile file,Long pid) {
		List<String[]> fileToList = ExcelImportUtils.fileToList(file);
		String[] head = fileToList.get(0);//表头
		Map<String,Integer> headMap = Maps.newHashMap();
		for(int i=0;i<head.length;i++){
			headMap.put(head[i].replaceAll("\\*", ""), i);//获取表头名字，并去掉*，用于根据表头名匹配数据
		}
		fileToList.remove(0);//删除表头数据，剩下的都是实际数据

		Map<Integer,ProjectPerson> personMap = Maps.newHashMap();//teacher + 索引位置,用于记录行号，输出 出错的行号

		Map<String, Integer> provinceMap = provinceService.provinceMap();//省map缓存
		List<String> errorList = Lists.newArrayList();

		if(fileToList.size()==0){
			errorList.add("Excel里无数据，请检查！");
		}

		Integer provinceIndex = headMap.get("省");
		if(provinceIndex==null){
			errorList.add("找不到【省】列，请检查Excel模板是否正确！");
		}

		Integer cityIndex = headMap.get("市");
		if(cityIndex==null){
			errorList.add("找不到【市】列，请检查Excel模板是否正确！");
		}

		Integer townIndex = headMap.get("区县");
		if(townIndex==null){
			errorList.add("找不到【区县】列，请检查Excel模板是否正确！");
		}

		Integer schoolIndex = headMap.get("学校名称");
		if(schoolIndex==null){
			errorList.add("找不到【学校名称】列，请检查Excel模板是否正确！");
		}

		Integer nameIndex = headMap.get("姓名");
		if(nameIndex==null){
			errorList.add("找不到【姓名】列，请检查Excel模板是否正确！");
		}

		Integer sexIndex = headMap.get("性别");
		if(sexIndex==null){
			errorList.add("找不到【性别】列，请检查Excel模板是否正确！");
		}

		Integer idCardIndex = headMap.get("身份证");
		if(idCardIndex==null){
			errorList.add("找不到【身份证】列，请检查Excel模板是否正确！");
		}

		Integer mobileIndex = headMap.get("手机号");
		if(mobileIndex==null){
			errorList.add("找不到【手机号】列，请检查Excel模板是否正确！");
		}

		Integer scoreIndex = headMap.get("成绩");
		if(scoreIndex==null){
			errorList.add("找不到【成绩】列，请检查Excel模板是否正确！");
		}

		if(errorList.size()==0){
			//逐条判断
			for(int i=0;i<fileToList.size();i++){

				ProjectPerson person = new ProjectPerson();
				person.setPid(pid);//把传入的pid存进来

				if(StringUtils.isBlank(fileToList.get(i)[nameIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(nameIndex+1)+"】列，【姓名】不能为空！");
				}else{
					person.setName(fileToList.get(i)[nameIndex]);
				}

				if(StringUtils.isBlank(fileToList.get(i)[scoreIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(scoreIndex+1)+"】列，【成绩】不能为空！");
				}else{
					if("优秀".equals(fileToList.get(i)[scoreIndex])){
						person.setPass(1);
					}else if("合格".equals(fileToList.get(i)[scoreIndex])){
						person.setPass(2);
					}else if("不合格".equals(fileToList.get(i)[scoreIndex])){
						person.setPass(0);
					}else{
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(scoreIndex+1)+"】列，【成绩】只能填入 优秀 、合格 或 不合格！");
					}
				}

				if(StringUtils.isBlank(fileToList.get(i)[idCardIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(idCardIndex+1)+"】列，【身份证】不能为空！");
				}else{
					person.setIdCard(fileToList.get(i)[idCardIndex]);
				}
				personMap.put( i+2,person);
			}
		}

		//*********************校验Excel表重复的身份证
		validateExcelIdCardName(personMap, errorList);

		//************************最后一步，hibernate validate,数据库校验****************************************************
		validateHibernateT(personMap, errorList);

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

    @Override
    public Integer selectMakeScoreTotalNum(Long pid) {
        return this.dao.selectMakeScoreTotalNum(pid);
    }

	@Override
	@Transactional
	public Map<String, String> startMakeScore(Long pid) {
		Integer totalNum = this.selectMakeScoreTotalNum(pid);

		String redisKey = UUID.randomUUID().toString().replaceAll("-","");//生成一个uuid作为redis的key，用来当前插入记录条
		//把一共的记录条数，当前条放到map中
		Map<String,String> map = Maps.newHashMap();
		map.put("totalNum",totalNum.toString());
		map.put("redisKey", redisKey);
		jedisClient.set(redisKey,"0");//存一个初始值0
		jedisClient.expire(redisKey,3600);//设置过期时间

		return  map;
	}

	@Override
	@Transactional
	public void makeScore(Long pid,String redisKey) {

		List<ProjectPerson> projectPersons = this.selectMakeScoreProjectPerson(pid);
		for(ProjectPerson projectPerson:projectPersons){
			insertScoreFromProjectPerson(projectPerson);
			//更新redisKey的计数
			Integer oldValue = Integer.valueOf(jedisClient.get(redisKey));

			//中断操作
			if(oldValue==-1){
				this.deleteScoreByPid(pid);
				break;
			}

			Integer newValeu = oldValue+1;
			jedisClient.set(redisKey,newValeu.toString());//+1 redis里值更新
			jedisClient.expire(redisKey,3600);
			//模拟延时
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
	}

	@Override
	public void makeScoreOnly(Long pid) {
		List<ProjectPerson> projectPersons = this.selectMakeScoreProjectPerson(pid);
		for(ProjectPerson projectPerson:projectPersons) {
			insertScoreFromProjectPerson(projectPerson);
		}
	}

	@Override
	@Transactional
	public void cancelMakeScore(Long pid, String redisKey) {
		if(StringUtils.isNotBlank(redisKey)){
			jedisClient.set(redisKey,"-1");//+1 redis里值更新
			jedisClient.expire(redisKey,3600);
		}
		this.deleteScoreByPid(pid);
	}

	@Override
	@Transactional
	public Long deleteScoreByPid(Long pid) {
		return this.dao.deleteScoreByPid(pid);
	}

	@Override
	@Transactional
	public Long deleteScoreByTidPid(Long tid, Long pid) {
		return this.dao.deleteScoreByTidPid(tid,pid);
	}

	@Override
	@Transactional
	public List<ProjectPerson> selectMakeScoreProjectPerson(Long pid) {
		return this.dao.selectMakeScoreProjectPerson(pid);
	}

	@Override
	public Boolean checkScoreExist(Long tid, Long pid) {
		Integer count = this.dao.selectCheckScoreExist(tid, pid);
		if(count>0){
			return true;  //如果存在，返回true
		}else {
			return false;
		}
	}

	/**
	 * 导出的excel表头
	 */
	@Override
	public List<String> getExcelFieldName() {
		String[] fieldNameArray = { "省", "市", "区县", "学校名称","姓名","*标识码","身份证","学段","学科","成绩","学时"};
		return Arrays.asList(fieldNameArray);
	}

	/**
	 * 导出的excel数据  根据query
	 */
	@Override
	public List<List<String>> getExcelDatasByQuery(ProjectPersonQuery query) {
		query.setPageSize(AppConstant.MAX_PAGE_SIZE);
		Pagination<ProjectPerson> page = this.selectListPagination(query);
		return changeObjToStringList(page.getList());
	}

	/**
	 * 导入Excel文件
	 */
	@Override
	@Transactional
	public Integer importExcel(MultipartFile file,Long pid,Long oid){
		List<String[]> fileToList = ExcelImportUtils.fileToList(file);
		String[] head = fileToList.get(0);//表头
		Map<String,Integer> headMap = Maps.newHashMap();
		for(int i=0;i<head.length;i++){
			headMap.put(head[i].replaceAll("\\*", ""), i);
		}
		fileToList.remove(0);//删除表头数据，剩下的都是实际数据

		for(String[] row:fileToList){
			ProjectPerson person = new ProjectPerson();
			person.setPid(pid);
			person.setOid(oid);

			person.setName(row[headMap.get("姓名")]);
			person.setIdCard(row[headMap.get("身份证")]);

			String strPass = row[headMap.get("成绩")];
			if("优秀".equals(strPass)){
				person.setPass(1);
			}else if("合格".equals(strPass)){
				person.setPass(2);
			}else if("不合格".equals(strPass)){
				person.setPass(0);
			}

			TeacherQuery query = new TeacherQuery();
			query.setName(person.getName());
			query.setNameLike(false);
			query.setIdCard(person.getIdCard());
			query.setIdCardLike(false);
			List<Teacher> teachers = teacherService.selectList(query);
			person.setTid(teachers.get(0).getId());

			this.dao.insert(person);
		}

		//判断是否是自动生成学时
		Project project = projectService.selectById(pid);
		Integer scoreCreateType = project.getScoreCreateType();
		if(scoreCreateType== ScoreCreateTypeEnum.ORG_CREATE.getValue()){
			this.makeScoreOnly(pid);
		}

		return fileToList.size();
	}

	/**
	 * 导出 转换为excel数据
	 * @param projectPersons
	 * @return
	 */
	private List<List<String>> changeObjToStringList(List<ProjectPerson> projectPersons) {
		List<List<String>> result = Lists.newArrayList();
		for(ProjectPerson projectPerson:projectPersons){
			List<String> ls = Lists.newArrayList();
			ls.add(projectPerson.getProvinceName());
			ls.add(projectPerson.getCityName());
			ls.add(projectPerson.getTownName());
			ls.add(projectPerson.getName());
			ls.add(projectPerson.getSchoolName());
			ls.add(projectPerson.getTno());
			ls.add(projectPerson.getIdCard());
			ls.add(projectPerson.getStageName());
			ls.add(projectPerson.getCourseName());

			String passStr = "";
			String score = "0";
			if(projectPerson.getPass()!=null){
				if(projectPerson.getPass()==1){
					passStr = "优秀";
					if(projectPerson.getGoodScore()!=null){
						score = projectPerson.getGoodScore().toString();
					}
				}else if (projectPerson.getPass()==2) {
					passStr = "合格";
					if(projectPerson.getPassScore()!=null){
						score = projectPerson.getPassScore().toString();
					}
				}else if (projectPerson.getPass()==0) {
					passStr = "不合格";
				}
			}else {
				passStr = "--";
			}
			ls.add(passStr);
			ls.add(score);


			result.add(ls);
		}
		return result;
	}


	/**
	 * 根据projectPerson生成项目认证学时
	 * @param projectPerson
	 */
	private void insertScoreFromProjectPerson(ProjectPerson projectPerson) {
		Teacher teacher = teacherService.selectById(projectPerson.getTid());
		Project project = projectService.selectById(projectPerson.getPid());
		Score score = new Score();
		score.setTid(projectPerson.getTid());
		score.setProvince(teacher.getProvince());
		score.setCity(teacher.getCity());
		score.setTown(teacher.getTown());
		score.setSchool(teacher.getSchool());
		score.setTname(teacher.getName());
		score.setTno(teacher.getTno().toString());
		score.setPid(projectPerson.getPid());
		if (project != null) {
			Long trainTypeId = project.getTrainType();
			DictItem trainType = dictItemService.selectById(trainTypeId);
			String name = trainType.getName() + "(" + project.getName() + ")";
			score.setName(name);
			score.setYear(project.getSchoolYear());
			score.setPno(project.getPno());
			score.setLevel(project.getTrainLevel());
				
			if(projectPerson.getPass()==1){
				score.setScore(project.getGoodScore().intValue());
			}else if(projectPerson.getPass()==2){
				score.setScore(project.getPassScore().intValue());
			}
			
			Long schoolItemId = 0l;
			List<DictItem> allLevels= dictCatlogService.getSelectItems("TRAIN_LEVEL");
			for(DictItem level:allLevels){
				int indexOfSchool = level.getName().indexOf("校");
				if(indexOfSchool!=-1){
					schoolItemId = level.getId();
				}
			}
			if(project.getTrainLevel().longValue()==schoolItemId){
				//设置学时类型
				score.setType(1);//非集中培训
				score.setScoreType(ScoreTypeEnum.XIAOBEN.getValue());
			}else{
				//设置学时类型
				score.setType(0);//集中培训
				score.setScoreType(ScoreTypeEnum.XIANGMU.getValue());
			}
			
		}
		//设置状态学时认证完成
		score.setStatus(ScoreStatusEnum.CHECK_END.getValue());
		score.setCheckDate(new Date());

		

		//如果已经存在，先删除，再生成
		if(checkScoreExist(score.getTid(),score.getPid())){
			this.deleteScoreByTidPid(score.getTid(),score.getPid());
		}
		scoreService.insert(score);

	}


	/**
	 * 校验Excel表重复的 身份证号
	 * @param personMap
	 * @param errorList
	 */
	private void validateExcelIdCardName(Map<Integer, ProjectPerson> personMap,
										 List<String> errorList) {
		List<String> idCardList = Lists.newArrayList();
		if(errorList.size()==0){
			//第一次遍历，把所有的idcard放到idCardList
			Iterator<Map.Entry<Integer, ProjectPerson>> entries = personMap.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry<Integer, ProjectPerson> entry = entries.next();
				ProjectPerson value = entry.getValue();
				//Excel重复值校验
				String idcard = value.getIdCard();//idcard判断唯一
				idCardList.add(idcard);
			}

			//第二次遍历，找出出现次数>1的值，并记录索引位置
			Iterator<Map.Entry<Integer, ProjectPerson>> idcardEntries = personMap.entrySet().iterator();
			while (idcardEntries.hasNext()) {
				Map.Entry<Integer, ProjectPerson> entry = idcardEntries.next();
				Integer key = entry.getKey();
				ProjectPerson value = entry.getValue();
				String idcard = value.getIdCard();
				//用集合的工具方法，统计idcard出现的次数
				int count = Collections.frequency(idCardList, idcard);
				if(count>1){
					errorList.add("第【"+key+"】行，身份证为【"+value.getIdCard()+"】,在Excel表中存在相同的身份证号，请修改！");
				}
			}

		}
	}

	/**
	 * hibernate validate校验,并数据库校验是否已经存在
	 * @param personMap
	 * @param errorList
	 */
	private void validateHibernateT(Map<Integer, ProjectPerson> personMap,
									List<String> errorList) {
		if(errorList.size()==0){
			Iterator<Map.Entry<Integer, ProjectPerson>> entries = personMap.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry<Integer, ProjectPerson> entry = entries.next();
				Integer key = entry.getKey();
				ProjectPerson value = entry.getValue();

//				ValidatorResult validateResult = BeanValidator.validateResult(value, Insert.class);
//				if(!validateResult.getFlag()){
//					List<ValidatorBean> errorObjs = validateResult.getErrorObjs();
//					for(ValidatorBean error:errorObjs){
//						errorList.add("第【"+key+"】行,错误信息：数据表字段名("+ error.getFiled()+"):" + error.getErrorMsg());
//					}
//				}

				if(!checkHasPerson(value)){
					errorList.add("第【"+key+"】行，身份证号为【"+value.getIdCard()+"】,根据姓名+身份证号，找不到对应的教师，请检查信息是否准确。");
				}else {
					if(checkPidTidExit(value)){
						errorList.add("第【"+key+"】行，身份证号为【"+value.getIdCard()+"】,本项目列表成绩单里，已经存在该老师的成绩。");
					}
				}
			}
		}

	}



	/**
	 * 校验身份证号在数据库是否存在，并校验本教师是否已经有本项目的成绩了,
	 * @param projectPerson
	 * @return
	 */
	private boolean checkHasPerson(ProjectPerson projectPerson) {
		TeacherQuery query = new TeacherQuery();
		query.setName(projectPerson.getName());
		query.setNameLike(false);
		query.setIdCard(projectPerson.getIdCard());
		query.setIdCardLike(false);
		List<Teacher> teachers = teacherService.selectList(query);
		int size = teachers.size();
		if(size>0){
			//如果有，则，赋值tid，以便下一步的校验
			projectPerson.setTid(teachers.get(0).getId());
			return true;
		}else {
			return false;
		}
	}

}