package com.yanxiu.ce.core.statistics.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.core.basic.enums.TeacherTypeEnum;
import com.yanxiu.ce.core.statistics.dao.ReportTeacherDao;
import com.yanxiu.ce.core.statistics.dto.NameValue;
import com.yanxiu.ce.core.statistics.entity.ReportTeacher;
import com.yanxiu.ce.core.statistics.entity.ReportTeacherQuery;
import com.yanxiu.ce.core.statistics.enums.ReportTeacherWayEnum;
import com.yanxiu.ce.core.statistics.service.ReportTeacherService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 消息管理
 * @author tangmin
 * @date 2016-08-16 11:56:03
 */
@Service("reportTeacherService")
public class ReportTeacherServiceImpl extends BaseServiceImpl<ReportTeacher, ReportTeacherQuery> implements ReportTeacherService{
	private static final String NULL = "未填";

	@Autowired
	private ReportTeacherDao dao;
	
	@Value("${SYS.PROVINCE}")
	private Integer SYS_PROVINCE;

	@Value("${renzhi_dict_id}")
	private String renzhi_dict_id;

	@Value("${tigao_dict_id}")
	private String tigao_dict_id;


	@Override
	protected BaseDao<ReportTeacher, ReportTeacherQuery> dao() {
		return this.dao;
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
	public Boolean checkNameExit(ReportTeacher entity) {
//		Long count = this.dao.selectCheckNameExit(entity.getName(), entity.getId());
//		if(count>0){
//			return false;
//		}
		return true;
	}
	
	/**
	 * 新增or修改
	 */
	@Override
	@Transactional
	public String saveOrUpdate(ReportTeacher entity) {
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
	public String processTeacherTotal() {
		// 清空此表
		this.truncate();
		
		//统计 教师
		processTeacher(TeacherTypeEnum.MIDDLE_SCHOOL.getValue());
		
		//统计校长
		//processTeacher(TeacherTypeEnum.TECHNICAL_SCHOOL.getValue());
		return "执行成功";
	}
	
	@Override
	public String processTeacher(Integer tchtype) {
		
		//0、教师人数统计
		this.processTeacherNum(tchtype);
		//1 根据各属性统计
		this.processProperty(tchtype);
		//2 根据年龄段统计
		this.processAge(tchtype);
		//3 根据教龄段统计
		//this.processJoinday(tchtype);
		

		//4、学科统计
		//this.processCourse(tchtype);

		//5、统计有任职证书，提高证书的教师，校长,人数
		this.processCredentNum(tchtype);
		
		return "执行成功";
	}

	/**
	 * 5、统计有任职证书，提高证书的教师，校长,人数
	 * @param tchtype
	 */
	private void processCredentNum(Integer tchtype) {

		//1  先统计省的总体
		groupCredentByCityProcess(tchtype);
//		//2 执行 市、区县 的统计
		groupCredentByTownProcess(tchtype);

	}

	private void groupCredentByTownProcess(Integer tchtype) {
		//先统计任职证书的
		renzhiTownProcess(renzhi_dict_id,tchtype);
		renzhiTownProcess(tigao_dict_id,tchtype);
	}

	private void renzhiTownProcess(String dictId, Integer tchtype) {
		List<String> citys = this.distinctByType("city", SYS_PROVINCE, null, null, null, tchtype);

		for (String cityStr : citys) {
			Integer city = Integer.parseInt(cityStr);
			List<NameValue> nameValues = this.groupCredentByPct("city", city, "town", dictId, tchtype);
			ReportTeacher reportTeacher = new ReportTeacher();
			List<NameValue> nvs = Lists.newArrayList();
			List<String> titils = Lists.newArrayList();

			for (NameValue nv : nameValues) {
				if (StringUtils.isBlank(nv.getName())) {
					nv.setName(NULL);
				}
				if(Integer.valueOf(nv.getValue())!=0){
					nvs.add(nv);
					titils.add(nv.getName());
				}
			}

			if (dictId.equals(renzhi_dict_id)) {
//				reportTeacher.setWay(ReportTeacherWayEnum.CREDENT_RE.getValue());
			} else if (dictId.equals(tigao_dict_id)) {
//				reportTeacher.setWay(ReportTeacherWayEnum.CREDENT_UP.getValue());
			}
			reportTeacher.setTchtype(tchtype);
			reportTeacher.setProvince(SYS_PROVINCE);
			reportTeacher.setCity(city);
			reportTeacher.setTown(0);
			reportTeacher.setSchool(0l);

			reportTeacher.setTitle(JSONObject.toJSONString(titils));
			reportTeacher.setData(JSONObject.toJSONString(nvs));

			if(nvs.size()>0){
				this.saveOrUpdate(reportTeacher);
			}

			//统计本区县 下的 各学校
			groupCredentBySchoolProcess(city, tchtype);
		}
	}

	private void groupCredentBySchoolProcess(Integer city, Integer tchtype) {
		//先统计任职证书的
		renzhiSchoolProcess(city,renzhi_dict_id,tchtype);
		renzhiSchoolProcess(city,tigao_dict_id,tchtype);
	}

	private void renzhiSchoolProcess(Integer city,String dictId, Integer tchtype) {
		List<String> towns = this.distinctByType("town",SYS_PROVINCE,city,null,null,tchtype);

		for(String townStr:towns){
			Integer town = Integer.parseInt(townStr);
			List<NameValue> nameValues = this.groupCredentBySchool(town,dictId, tchtype);
			ReportTeacher reportTeacher = new ReportTeacher();
			List<NameValue> nvs = Lists.newArrayList();
			List<String> titils = Lists.newArrayList();

			for(NameValue nv:nameValues){
				if(StringUtils.isBlank(nv.getName())){
					nv.setName(NULL);
				}
				if(Integer.valueOf(nv.getValue())!=0){
					nvs.add(nv);
					titils.add(nv.getName());
				}
			}

			if(dictId.equals(renzhi_dict_id)){
//				reportTeacher.setWay(ReportTeacherWayEnum.CREDENT_RE.getValue());
			}else if(dictId.equals(tigao_dict_id)){
//				reportTeacher.setWay(ReportTeacherWayEnum.CREDENT_UP.getValue());
			}
			reportTeacher.setTchtype(tchtype);
			reportTeacher.setProvince(SYS_PROVINCE);
			reportTeacher.setCity(city);
			reportTeacher.setTown(town);
			reportTeacher.setSchool(0l);

			reportTeacher.setTitle(JSONObject.toJSONString(titils));
			reportTeacher.setData(JSONObject.toJSONString(nvs));

			if(nvs.size()>0){
				this.saveOrUpdate(reportTeacher);
			}

			countCredentSchoolProcess(city, town,tchtype);

		}
	}

	private void countCredentSchoolProcess(Integer city, Integer town, Integer tchtype) {
		//先统计任职证书的
		renzhiCountSchoolProcess(city,town,renzhi_dict_id,tchtype);
		renzhiCountSchoolProcess(city,town,tigao_dict_id,tchtype);
	}

	private void renzhiCountSchoolProcess(Integer city, Integer town, String dictId, Integer tchtype) {
		List<String> schools = this.distinctByType("school", SYS_PROVINCE, city, town, null,tchtype);
		for(String schoolStr:schools){
			Long school = Long.parseLong(schoolStr);

			List<NameValue> nameValues = this.countCredentBySchool(school,dictId,tchtype);
			ReportTeacher reportTeacher = new ReportTeacher();
			List<NameValue> nvs = Lists.newArrayList();
			List<String> titils = Lists.newArrayList();

			for(NameValue nv:nameValues){
				if(StringUtils.isBlank(nv.getName())){
					nv.setName(NULL);
				}

				if(Integer.valueOf(nv.getValue())!=0){
					nvs.add(nv);
					titils.add(nv.getName());
				}

			}

			if(dictId.equals(renzhi_dict_id)){
//				reportTeacher.setWay(ReportTeacherWayEnum.CREDENT_RE.getValue());
			}else if(dictId.equals(tigao_dict_id)){
//				reportTeacher.setWay(ReportTeacherWayEnum.CREDENT_UP.getValue());
			}
			reportTeacher.setTchtype(tchtype);
			reportTeacher.setProvince(SYS_PROVINCE);
			reportTeacher.setCity(city);
			reportTeacher.setTown(town);
			reportTeacher.setSchool(school);

			reportTeacher.setTitle(JSONObject.toJSONString(titils));
			reportTeacher.setData(JSONObject.toJSONString(nvs));
			if(nvs.size()>0){
				this.saveOrUpdate(reportTeacher);
			}

		}
	}

	private void groupCredentByCityProcess(Integer tchtype) {
		//先统计任职证书的
		renzhiCityProcess(renzhi_dict_id,tchtype);
		renzhiCityProcess(tigao_dict_id,tchtype);
	}

	private void renzhiCityProcess(String dictId,Integer tchtype) {
		List<NameValue> nameValues = this.groupCredentByPct("province",SYS_PROVINCE,"city",dictId,tchtype);

		ReportTeacher reportTeacher = new ReportTeacher();

		List<NameValue> nvs = Lists.newArrayList();
		List<String> titils = Lists.newArrayList();

		for(NameValue nv:nameValues){
			if(StringUtils.isBlank(nv.getName())){
				nv.setName(NULL);
			}
			if(Integer.valueOf(nv.getValue())!=0){
				nvs.add(nv);
				titils.add(nv.getName());
			}
		}

		if(dictId.equals(renzhi_dict_id)){
//			reportTeacher.setWay(ReportTeacherWayEnum.CREDENT_RE.getValue());
		}else if(dictId.equals(tigao_dict_id)){
//			reportTeacher.setWay(ReportTeacherWayEnum.CREDENT_UP.getValue());
		}
		reportTeacher.setTchtype(tchtype);
		reportTeacher.setProvince(SYS_PROVINCE);
		reportTeacher.setCity(0);
		reportTeacher.setTown(0);
		reportTeacher.setSchool(0l);

		reportTeacher.setTitle(JSONObject.toJSONString(titils));
		reportTeacher.setData(JSONObject.toJSONString(nvs));

		if(nvs.size()>0){
			this.saveOrUpdate(reportTeacher);
		}
	}

	/**
	 * 学科统计
	 * @param tchtype
	 */
	private void processCourse(Integer tchtype) {
		
	}

	/**
	 * 0.人数统计 统计本市  各区县（下层调用 各区下的各学校统计）
	 */
	private void groupByTownProcess(Integer tchtype) {
		List<String> citys = this.distinctByType("city",SYS_PROVINCE,null,null,null,tchtype);
		
		for(String cityStr:citys){
			Integer city = Integer.parseInt(cityStr);
			List<NameValue> nameValues = this.groupByTown(SYS_PROVINCE, city,tchtype);
			ReportTeacher reportTeacher = new ReportTeacher();
			List<NameValue> nvs = Lists.newArrayList();
			List<String> titils = Lists.newArrayList();
			
			for(NameValue nv:nameValues){
				if(StringUtils.isBlank(nv.getName())){
					nv.setName(NULL);
				}
				nvs.add(nv);
				titils.add(nv.getName());
			}
			
			reportTeacher.setWay(ReportTeacherWayEnum.NUMBERS.getValue());
			reportTeacher.setTchtype(tchtype);
			reportTeacher.setProvince(SYS_PROVINCE);
			reportTeacher.setCity(city);
			reportTeacher.setTown(0);
			reportTeacher.setSchool(0l);
			
			reportTeacher.setTitle(JSONObject.toJSONString(titils));
			reportTeacher.setData(JSONObject.toJSONString(nvs));
			
			this.saveOrUpdate(reportTeacher);
			
			//统计本区县 下的 各学校
			groupBySchoolProcess(city,tchtype);
		}
	}

	/**
	 * 0.人数统计 统计本区县 下的 各学校
	 * @param city
	 */
	private void groupBySchoolProcess(Integer city,Integer tchtype) {
		List<String> towns = this.distinctByType("town",SYS_PROVINCE,city,null,null,tchtype);
		
		for(String townStr:towns){
			Integer town = Integer.parseInt(townStr);
			List<NameValue> nameValues = this.groupBySchool(SYS_PROVINCE, city,town,tchtype);
			ReportTeacher reportTeacher = new ReportTeacher();
			List<NameValue> nvs = Lists.newArrayList();
			List<String> titils = Lists.newArrayList();
			
			for(NameValue nv:nameValues){
				if(StringUtils.isBlank(nv.getName())){
					nv.setName(NULL);
				}
				nvs.add(nv);
				titils.add(nv.getName());
			}
			
			reportTeacher.setWay(ReportTeacherWayEnum.NUMBERS.getValue());
			reportTeacher.setTchtype(tchtype);
			reportTeacher.setProvince(SYS_PROVINCE);
			reportTeacher.setCity(city);
			reportTeacher.setTown(town);
			reportTeacher.setSchool(0l);
			
			reportTeacher.setTitle(JSONObject.toJSONString(titils));
			reportTeacher.setData(JSONObject.toJSONString(nvs));
			
			this.saveOrUpdate(reportTeacher);
			
			countSchoolProcess(city, town,tchtype);
			
		}
	}

	/**
	 * 0.人数统计 统计学校
	 * @param city
	 * @param town
	 */
	private void countSchoolProcess(Integer city, Integer town,Integer tchtype) {
		List<String> schools = this.distinctByType("school", SYS_PROVINCE, city, town, null,tchtype);
		for(String schoolStr:schools){
			Long school = Long.parseLong(schoolStr);
			
			List<NameValue> nameValues = this.countSchool(school,tchtype);
			ReportTeacher reportTeacher = new ReportTeacher();
			List<NameValue> nvs = Lists.newArrayList();
			List<String> titils = Lists.newArrayList();
			
			for(NameValue nv:nameValues){
				if(StringUtils.isBlank(nv.getName())){
					nv.setName(NULL);
				}
				nvs.add(nv);
				titils.add(nv.getName());
			}
			
			reportTeacher.setWay(ReportTeacherWayEnum.NUMBERS.getValue());
			reportTeacher.setTchtype(tchtype);
			reportTeacher.setProvince(SYS_PROVINCE);
			reportTeacher.setCity(city);
			reportTeacher.setTown(town);
			reportTeacher.setSchool(school);
			
			reportTeacher.setTitle(JSONObject.toJSONString(titils));
			reportTeacher.setData(JSONObject.toJSONString(nvs));
			
			this.saveOrUpdate(reportTeacher);
			
		}
	}

	/**
	 * 0.人数统计 统计本省  各市
	 */
	private void groupByCityProcess(Integer tchtype) {
		List<NameValue> nameValues = this.groupByCity(SYS_PROVINCE,tchtype);
		
		ReportTeacher reportTeacher = new ReportTeacher();
		
		List<NameValue> nvs = Lists.newArrayList();
		List<String> titils = Lists.newArrayList();
		
		for(NameValue nv:nameValues){
			if(StringUtils.isBlank(nv.getName())){
				nv.setName(NULL);
			}
			nvs.add(nv);
			titils.add(nv.getName());
		}
		
		reportTeacher.setWay(ReportTeacherWayEnum.NUMBERS.getValue());
		reportTeacher.setTchtype(tchtype);
		reportTeacher.setProvince(SYS_PROVINCE);
		reportTeacher.setCity(0);
		reportTeacher.setTown(0);
		reportTeacher.setSchool(0l);
		
		reportTeacher.setTitle(JSONObject.toJSONString(titils));
		reportTeacher.setData(JSONObject.toJSONString(nvs));
		
		this.saveOrUpdate(reportTeacher);
	}

	@Override
	public List<NameValue> groupByCity(Integer province,Integer tchtype) {
		return this.dao.groupByCity(province,tchtype);
	}

	@Override
	public void truncate() {
		this.dao.truncate();
	}

	@Override
	public List<String> distinctByType(String type,Integer province,Integer city,Integer town,Long school,Integer tchtype) {
		return this.dao.distinctByType(type,SYS_PROVINCE,city,town, school,tchtype);
	}

	@Override
	public List<NameValue> groupByTown(Integer province, Integer city,Integer tchtype) {
		return this.dao.groupByTown(province, city,tchtype);
	}

	@Override
	public List<NameValue> groupBySchool(Integer province, Integer city,
			Integer town,Integer tchtype) {
		return this.dao.groupBySchool(province, city, town,tchtype);
	}

	@Override
	public List<NameValue> countSchool(Long school,Integer tchtype) {
		return this.dao.countSchool(school,tchtype);
	}

	

	@Override
	public List<NameValue> groupByProperty(String property, String tableName,
			Integer province, Integer city, Integer town, Long school,Integer tchtype) {
		return this.dao.groupByProperty(property, tableName, SYS_PROVINCE, city, town, school,tchtype);
	}
	
	
	/**
	 * 0.人数统计
	 */
	@Override
	public String processTeacherNum(Integer tchtype) {
		
		//1  先统计省的总体
		groupByCityProcess(tchtype);
		//2 执行 市、区县 的统计
		groupByTownProcess(tchtype);
		return "执行成功";
	}
	
	/**
	 * 1.学历（学段、学科、职称、行政职务、骨干类型）
	 * DEGREE("学历", 1), 
	TITLE("职称",2),
	STAGE("学段", 3), 
	COURSE("学科",4),
	XINGZHENGZHIWU("行政职务", 5), 
	GUGANLEIX("骨干类型",6),
	 */
	@Override
	public String processProperty(Integer tchtype) {
		//1、统计 学历
	//	provinceProperty("degree", "sys_dict_item",ReportTeacherWayEnum.DEGREE.getValue(),tchtype);
		
		//2、统计 职称
	//	provinceProperty("title", "sys_dict_item",ReportTeacherWayEnum.TITLE.getValue(),tchtype);

		//3、统计 学段
	//	provinceProperty("stage", "t_conf_stage",ReportTeacherWayEnum.STAGE.getValue(),tchtype);
		
		//4、统计 学科
	//	provinceProperty("course", "t_conf_course",ReportTeacherWayEnum.COURSE.getValue(),tchtype);
		
		//5、统计 行政职务
	//	provinceProperty("duty", "sys_dict_item",ReportTeacherWayEnum.DUTY.getValue(),tchtype);
		
		//6、统计 骨干类型
	//	provinceProperty("bonetype", "sys_dict_item",ReportTeacherWayEnum.BONETYPE.getValue(),tchtype);
		
		//9、统计 名族
		provinceProperty("nation", "sys_dict_item",ReportTeacherWayEnum.NATION.getValue(),tchtype);
		return "执行成功";
	}

	/**1 省 学历（学段、学科、职称、行政职务、骨干类型）**/
	private void provinceProperty(String property, String tableName,Integer way,Integer tchtype) {
		List<NameValue> nameValues = this.groupByProperty(property, tableName, SYS_PROVINCE, null, null, null,tchtype);
		ReportTeacher reportTeacher = new ReportTeacher();
		
		List<NameValue> nvs = Lists.newArrayList();
		List<String> titils = Lists.newArrayList();
		
		for(NameValue nv:nameValues){
			if(StringUtils.isBlank(nv.getName())){
				nv.setName(NULL);
			}
			nvs.add(nv);
			titils.add(nv.getName());
		}
		
		reportTeacher.setTchtype(tchtype);
		reportTeacher.setWay(way);
		reportTeacher.setProvince(SYS_PROVINCE);
		reportTeacher.setCity(0);
		reportTeacher.setTown(0);
		reportTeacher.setSchool(0l);
		
		reportTeacher.setTitle(JSONObject.toJSONString(titils));
		reportTeacher.setData(JSONObject.toJSONString(nvs));
		
		this.saveOrUpdate(reportTeacher);
		
		cityProperty(property,tableName,way,tchtype);
	}

	/**
	 * 1 市 学历（学段、学科、职称、行政职务、骨干类型）
	 * @param property
	 * @param tableName
	 */
	private void cityProperty(String property, String tableName,Integer way,Integer tchtype) {
		List<String> citys = this.distinctByType("city",SYS_PROVINCE,null,null,null,tchtype);
		for(String cityStr:citys){
			Integer city = Integer.parseInt(cityStr);
			List<NameValue> nameValues = this.groupByProperty(property, tableName, SYS_PROVINCE, city, null, null,tchtype);
			ReportTeacher reportTeacher = new ReportTeacher();
			
			List<NameValue> nvs = Lists.newArrayList();
			List<String> titils = Lists.newArrayList();
			
			for(NameValue nv:nameValues){
				if(StringUtils.isBlank(nv.getName())){
					nv.setName(NULL);
				}
				nvs.add(nv);
				titils.add(nv.getName());
			}
			
			reportTeacher.setWay(way);
			reportTeacher.setTchtype(tchtype);
			reportTeacher.setProvince(SYS_PROVINCE);
			reportTeacher.setCity(city);
			reportTeacher.setTown(0);
			reportTeacher.setSchool(0l);
			
			reportTeacher.setTitle(JSONObject.toJSONString(titils));
			reportTeacher.setData(JSONObject.toJSONString(nvs));
			
			this.saveOrUpdate(reportTeacher);
			
			
			townProperty(property, tableName, way, city,tchtype);
		}
	}

	/**
	 * 2 区县 学历（学段、学科、职称、行政职务、骨干类型）
	 * @param property
	 * @param tableName
	 * @param way
	 * @param city
	 */
	private void townProperty(String property, String tableName, Integer way,
			Integer city,Integer tchtype) {
		List<String> towns = this.distinctByType("town",SYS_PROVINCE,city,null,null,tchtype);
		
		for(String townStr:towns){
			Integer town = Integer.parseInt(townStr);
			List<NameValue> nameValues =  this.groupByProperty(property, tableName, SYS_PROVINCE, city, town, null,tchtype);
			ReportTeacher reportTeacher = new ReportTeacher();
			List<NameValue> nvs = Lists.newArrayList();
			List<String> titils = Lists.newArrayList();
			
			for(NameValue nv:nameValues){
				if(StringUtils.isBlank(nv.getName())){
					nv.setName(NULL);
				}
				nvs.add(nv);
				titils.add(nv.getName());
			}
			
			reportTeacher.setWay(way);
			reportTeacher.setTchtype(tchtype);
			reportTeacher.setProvince(SYS_PROVINCE);
			reportTeacher.setCity(city);
			reportTeacher.setTown(town);
			reportTeacher.setSchool(0l);
			
			reportTeacher.setTitle(JSONObject.toJSONString(titils));
			reportTeacher.setData(JSONObject.toJSONString(nvs));
			
			this.saveOrUpdate(reportTeacher);
			
			schoolProperty(property, tableName, way, city, town,tchtype);
			
		}
	}

	/**
	 * 3 学校 学历（学段、学科、职称、行政职务、骨干类型）
	 * @param property
	 * @param tableName
	 * @param way
	 * @param city
	 * @param town
	 */
	private void schoolProperty(String property, String tableName, Integer way,
			Integer city, Integer town,Integer tchtype) {
		List<String> schools = this.distinctByType("school", SYS_PROVINCE, city, town, null,tchtype);
		for(String schoolStr:schools){
			Long school = Long.parseLong(schoolStr);
			
			List<NameValue> nameValues = this.groupByProperty(property, tableName, SYS_PROVINCE, city, town, school,tchtype);
			ReportTeacher reportTeacher = new ReportTeacher();
			List<NameValue> nvs = Lists.newArrayList();
			List<String> titils = Lists.newArrayList();
			
			for(NameValue nv:nameValues){
				if(StringUtils.isBlank(nv.getName())){
					nv.setName(NULL);
				}
				nvs.add(nv);
				titils.add(nv.getName());
			}
			
			reportTeacher.setWay(way);
			reportTeacher.setTchtype(tchtype);
			reportTeacher.setProvince(SYS_PROVINCE);
			reportTeacher.setCity(city);
			reportTeacher.setTown(town);
			reportTeacher.setSchool(school);
			
			reportTeacher.setTitle(JSONObject.toJSONString(titils));
			reportTeacher.setData(JSONObject.toJSONString(nvs));
			
			this.saveOrUpdate(reportTeacher);
			
		}
	}

	/**
	 * 根据年龄统计
	 */
	@Override
	public String processAge(Integer tchtype) {
		provinceAge(tchtype);
		return "执行成功";
	}

	/**1 省 年龄 **/
	private void provinceAge(Integer tchtype) {
		List<NameValue> nameValues = this.groupByAge(SYS_PROVINCE, null, null, null,tchtype);
		ReportTeacher reportTeacher = new ReportTeacher();
		
		List<NameValue> nvs = Lists.newArrayList();
		List<String> titils = Lists.newArrayList();
		
		for(NameValue nv:nameValues){
			if(StringUtils.isBlank(nv.getName())){
				nv.setName(NULL);
			}
			nvs.add(nv);
			titils.add(nv.getName());
		}
		
		reportTeacher.setTchtype(tchtype);
		reportTeacher.setWay(ReportTeacherWayEnum.AGE.getValue());
		reportTeacher.setProvince(SYS_PROVINCE);
		reportTeacher.setCity(0);
		reportTeacher.setTown(0);
		reportTeacher.setSchool(0l);
		
		reportTeacher.setTitle(JSONObject.toJSONString(titils));
		reportTeacher.setData(JSONObject.toJSONString(nvs));
		
		this.saveOrUpdate(reportTeacher);
		
		cityAge(tchtype);
	}
	
	/**
	 * 2 市 年龄
	 */
	private void cityAge(Integer tchtype) {
		List<String> citys = this.distinctByType("city",SYS_PROVINCE,null,null,null,tchtype);
		for(String cityStr:citys){
			Integer city = Integer.parseInt(cityStr);
			List<NameValue> nameValues = this.groupByAge(SYS_PROVINCE, city, null, null,tchtype);
			ReportTeacher reportTeacher = new ReportTeacher();
			
			List<NameValue> nvs = Lists.newArrayList();
			List<String> titils = Lists.newArrayList();
			
			for(NameValue nv:nameValues){
				if(StringUtils.isBlank(nv.getName())){
					nv.setName(NULL);
				}
				nvs.add(nv);
				titils.add(nv.getName());
			}
			
			reportTeacher.setWay(ReportTeacherWayEnum.AGE.getValue());
			reportTeacher.setTchtype(tchtype);
			reportTeacher.setProvince(SYS_PROVINCE);
			reportTeacher.setCity(city);
			reportTeacher.setTown(0);
			reportTeacher.setSchool(0l);
			
			reportTeacher.setTitle(JSONObject.toJSONString(titils));
			reportTeacher.setData(JSONObject.toJSONString(nvs));
			
			this.saveOrUpdate(reportTeacher);
			
			townAge(city,tchtype);
		}
	}
	
	/**
	 * 2 区县 年龄
	 * @param city
	 */
	private void townAge(Integer city,Integer tchtype) {
		List<String> towns = this.distinctByType("town",SYS_PROVINCE,city,null,null,tchtype);
		
		for(String townStr:towns){
			Integer town = Integer.parseInt(townStr);
			List<NameValue> nameValues =  this.groupByAge(SYS_PROVINCE, city, town, null,tchtype);
			ReportTeacher reportTeacher = new ReportTeacher();
			List<NameValue> nvs = Lists.newArrayList();
			List<String> titils = Lists.newArrayList();
			
			for(NameValue nv:nameValues){
				if(StringUtils.isBlank(nv.getName())){
					nv.setName(NULL);
				}
				nvs.add(nv);
				titils.add(nv.getName());
			}
			
			reportTeacher.setWay(ReportTeacherWayEnum.AGE.getValue());
			reportTeacher.setTchtype(tchtype);
			reportTeacher.setProvince(SYS_PROVINCE);
			reportTeacher.setCity(city);
			reportTeacher.setTown(town);
			reportTeacher.setSchool(0l);
			
			reportTeacher.setTitle(JSONObject.toJSONString(titils));
			reportTeacher.setData(JSONObject.toJSONString(nvs));
			
			this.saveOrUpdate(reportTeacher);
			
			schoolAge(city, town,tchtype);
			
		}
	}

	/**
	 * 3 学校 年龄
	 * @param city
	 * @param town
	 */
	private void schoolAge(Integer city, Integer town,Integer tchtype) {
		List<String> schools = this.distinctByType("school", SYS_PROVINCE, city, town, null,tchtype);
		for(String schoolStr:schools){
			Long school = Long.parseLong(schoolStr);
			
			List<NameValue> nameValues = this.groupByAge(SYS_PROVINCE, city, town, school,tchtype);
			ReportTeacher reportTeacher = new ReportTeacher();
			List<NameValue> nvs = Lists.newArrayList();
			List<String> titils = Lists.newArrayList();
			
			for(NameValue nv:nameValues){
				if(StringUtils.isBlank(nv.getName())){
					nv.setName(NULL);
				}
				nvs.add(nv);
				titils.add(nv.getName());
			}
			
			reportTeacher.setWay(ReportTeacherWayEnum.AGE.getValue());
			reportTeacher.setTchtype(tchtype);
			reportTeacher.setProvince(SYS_PROVINCE);
			reportTeacher.setCity(city);
			reportTeacher.setTown(town);
			reportTeacher.setSchool(school);
			
			reportTeacher.setTitle(JSONObject.toJSONString(titils));
			reportTeacher.setData(JSONObject.toJSONString(nvs));
			
			this.saveOrUpdate(reportTeacher);
			
		}
	}
	
	@Override
	public List<NameValue> groupByAge(Integer province, Integer city,
			Integer town, Long school, Integer tchtype) {
		return this.dao.groupByAge(province, city, town, school, tchtype);
	}
	
	/**
	 * 根据教龄统计
	 */
	@Override
	public String processJoinday(Integer tchtype) {
		provinceJoinday(tchtype);
		return "执行成功";
	}

	/**1 省 教龄 **/
	private void provinceJoinday(Integer tchtype) {
		List<NameValue> nameValues = this.groupByJoinday(SYS_PROVINCE, null, null, null,tchtype);
		ReportTeacher reportTeacher = new ReportTeacher();
		
		List<NameValue> nvs = Lists.newArrayList();
		List<String> titils = Lists.newArrayList();
		
		for(NameValue nv:nameValues){
			if(StringUtils.isBlank(nv.getName())){
				nv.setName(NULL);
			}
			nvs.add(nv);
			titils.add(nv.getName());
		}
		
		reportTeacher.setTchtype(tchtype);
//		reportTeacher.setWay(ReportTeacherWayEnum.JOINYEAR.getValue());
		reportTeacher.setProvince(SYS_PROVINCE);
		reportTeacher.setCity(0);
		reportTeacher.setTown(0);
		reportTeacher.setSchool(0l);
		
		reportTeacher.setTitle(JSONObject.toJSONString(titils));
		reportTeacher.setData(JSONObject.toJSONString(nvs));
		
		this.saveOrUpdate(reportTeacher);
		
		cityJoinday(tchtype);
	}
	
	/**
	 * 2 市 教龄
	 */
	private void cityJoinday(Integer tchtype) {
		List<String> citys = this.distinctByType("city",SYS_PROVINCE,null,null,null,tchtype);
		for(String cityStr:citys){
			Integer city = Integer.parseInt(cityStr);
			List<NameValue> nameValues = this.groupByJoinday(SYS_PROVINCE, city, null, null,tchtype);
			ReportTeacher reportTeacher = new ReportTeacher();
			
			List<NameValue> nvs = Lists.newArrayList();
			List<String> titils = Lists.newArrayList();
			
			for(NameValue nv:nameValues){
				if(StringUtils.isBlank(nv.getName())){
					nv.setName(NULL);
				}
				nvs.add(nv);
				titils.add(nv.getName());
			}
			
//			reportTeacher.setWay(ReportTeacherWayEnum.JOINYEAR.getValue());
			reportTeacher.setTchtype(tchtype);
			reportTeacher.setProvince(SYS_PROVINCE);
			reportTeacher.setCity(city);
			reportTeacher.setTown(0);
			reportTeacher.setSchool(0l);
			
			reportTeacher.setTitle(JSONObject.toJSONString(titils));
			reportTeacher.setData(JSONObject.toJSONString(nvs));
			
			this.saveOrUpdate(reportTeacher);
			
			townJoinday(city,tchtype);
		}
	}
	
	/**
	 * 2 区县 教龄
	 * @param city
	 */
	private void townJoinday(Integer city,Integer tchtype) {
		List<String> towns = this.distinctByType("town",SYS_PROVINCE,city,null,null,tchtype);
		
		for(String townStr:towns){
			Integer town = Integer.parseInt(townStr);
			List<NameValue> nameValues =  this.groupByJoinday(SYS_PROVINCE, city, town, null,tchtype);
			ReportTeacher reportTeacher = new ReportTeacher();
			List<NameValue> nvs = Lists.newArrayList();
			List<String> titils = Lists.newArrayList();
			
			for(NameValue nv:nameValues){
				if(StringUtils.isBlank(nv.getName())){
					nv.setName(NULL);
				}
				nvs.add(nv);
				titils.add(nv.getName());
			}
			
//			reportTeacher.setWay(ReportTeacherWayEnum.JOINYEAR.getValue());
			reportTeacher.setTchtype(tchtype);
			reportTeacher.setProvince(SYS_PROVINCE);
			reportTeacher.setCity(city);
			reportTeacher.setTown(town);
			reportTeacher.setSchool(0l);
			
			reportTeacher.setTitle(JSONObject.toJSONString(titils));
			reportTeacher.setData(JSONObject.toJSONString(nvs));
			
			this.saveOrUpdate(reportTeacher);
			
			schoolJoinday(city, town,tchtype);
			
		}
	}

	/**
	 * 3 学校 教龄
	 * @param city
	 * @param town
	 */
	private void schoolJoinday(Integer city, Integer town,Integer tchtype) {
		List<String> schools = this.distinctByType("school", SYS_PROVINCE, city, town, null,tchtype);
		for(String schoolStr:schools){
			Long school = Long.parseLong(schoolStr);
			
			List<NameValue> nameValues = this.groupByJoinday(SYS_PROVINCE, city, town, school,tchtype);
			ReportTeacher reportTeacher = new ReportTeacher();
			List<NameValue> nvs = Lists.newArrayList();
			List<String> titils = Lists.newArrayList();
			
			for(NameValue nv:nameValues){
				if(StringUtils.isBlank(nv.getName())){
					nv.setName(NULL);
				}
				nvs.add(nv);
				titils.add(nv.getName());
			}
			
	//		reportTeacher.setWay(ReportTeacherWayEnum.JOINYEAR.getValue());
			reportTeacher.setTchtype(tchtype);
			reportTeacher.setProvince(SYS_PROVINCE);
			reportTeacher.setCity(city);
			reportTeacher.setTown(town);
			reportTeacher.setSchool(school);
			
			reportTeacher.setTitle(JSONObject.toJSONString(titils));
			reportTeacher.setData(JSONObject.toJSONString(nvs));
			
			this.saveOrUpdate(reportTeacher);
			
		}
	}
	
	@Override
	public List<NameValue> groupByJoinday(Integer province, Integer city,
			Integer town, Long school, Integer tchtype) {
		return this.dao.groupByJoinday(province, city, town, school, tchtype);
	}

	@Override
	public List<NameValue> groupCredentByPct(String uppct, Integer uppctValue, String pct, String credentType, Integer tchtype) {
		return this.dao.groupCredentByPct(uppct,uppctValue,pct,credentType,tchtype);
	}

	@Override
	public List<NameValue> groupCredentBySchool(Integer town, String credentType, Integer tchtype) {
		return this.dao.groupCredentBySchool(town,credentType,tchtype);
	}

	@Override
	public List<NameValue> countCredentBySchool(Long school, String credentType, Integer tchtype) {
		return this.dao.countCredentBySchool(school,credentType,tchtype);
	}


}