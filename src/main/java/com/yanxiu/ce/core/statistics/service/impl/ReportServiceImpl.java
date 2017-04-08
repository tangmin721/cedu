package com.yanxiu.ce.core.statistics.service.impl;

import com.yanxiu.ce.common.utils.AppStringUtils;
import com.yanxiu.ce.core.basic.entity.*;
import com.yanxiu.ce.core.basic.enums.TeacherTypeEnum;
import com.yanxiu.ce.core.basic.service.*;
import com.yanxiu.ce.core.statistics.service.ReportService;
import com.yanxiu.ce.core.statistics.service.ReportTeacherService;
import com.yanxiu.ce.system.entity.DictItem;
import com.yanxiu.ce.system.service.DictCatlogService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service("reportService")
public class ReportServiceImpl implements ReportService{
	
	@Autowired
	private ReportTeacherService reportTeacherService;

	@Override
	public String process() {
		
		reportTeacherService.processTeacher(TeacherTypeEnum.MIDDLE_SCHOOL.getValue());
		//reportTeacherService.processTeacher(TeacherTypeEnum.TECHNICAL_SCHOOL.getValue());
		
		return "执行成功";
	}

	
	
	@Autowired
	private TeacherService teacherService;
	
	private static SimpleDateFormat dfIdcard = new SimpleDateFormat("yyyyMMdd");
	
	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private DictCatlogService dictService;
	
	@Autowired
	private StageService stageService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private GradeService gradeService;
	
	
	/**
	 * 生成测试数据
	 * @param startNumber
	 * @param totalNumber
	 */
	public void addTeachers (Integer startNumber,Integer totalNumber){
		
		for(int i=startNumber;i<totalNumber+startNumber;i++){
			Teacher teacher = new Teacher();
			Random r = new Random();
			Integer schoolId = 1+r.nextInt(38);
			School school = schoolService.selectById( Long.valueOf(schoolId));
			teacher.setProvince(school.getProvince());
			teacher.setCity(school.getCity());
			teacher.setTown(school.getTown());
			teacher.setSchool(school.getId());
			
			teacher.setName("测试"+i);
			Date birthDay = randomDate(1950,50);
			teacher.setBirthday(birthDay);
//			if(i%2==0){
//				teacher.setSex(0);
//			}else{
//				teacher.setSex(1);
//			}
/*
			if(i%2==0){
				teacher.setHeadTeacher(0);
			}else{
				teacher.setHeadTeacher(1);
			}

			teacher.setMajor("计算机专业");*/
			
			//名族
			List<DictItem> nations = dictService.getSelectItems("NATION");
			DictItem nation = nations.get(r.nextInt(nations.size()));
			teacher.setNation(nation.getId());
			
			//身份证431121 19890909 4121
			String idCard="4"+AppStringUtils.addZero(5,r.nextInt(9999))+dfIdcard.format(birthDay)+AppStringUtils.addZero(4,r.nextInt(9999));
			teacher.setIdCard(idCard);
			
			teacher.setType(0);
			
		/*	teacher.setMobile("13901011234");
			teacher.setEmail("1923131@qq.com");
			*/
			
			/*List<DictItem> PERSON_TYPEs = dictService.getSelectItems("PERSON_TYPE");
			DictItem personType = PERSON_TYPEs.get(r.nextInt(PERSON_TYPEs.size()));
			teacher.setPersonType(personType.getId());
			
			List<DictItem> DUTY_TYPEs = dictService.getSelectItems("DUTY_TYPE");
			DictItem duty = DUTY_TYPEs.get(r.nextInt(DUTY_TYPEs.size()));
			teacher.setDuty(duty.getId());
			
			List<DictItem> TITLE_TYPEs = dictService.getSelectItems("TITLE_TYPE");
			DictItem title = TITLE_TYPEs.get(r.nextInt(TITLE_TYPEs.size()));
			teacher.setTitle(title.getId());
			
			
			List<DictItem> QUALIFY_TYPE = dictService.getSelectItems("QUALIFY_TYPE");
			DictItem qualify = QUALIFY_TYPE.get(r.nextInt(QUALIFY_TYPE.size()));
			teacher.setQualify(qualify.getId());
			
			
			List<DictItem> BONE_TYPE = dictService.getSelectItems("BONE_TYPE");
			DictItem bone = BONE_TYPE.get(r.nextInt(BONE_TYPE.size()));
			teacher.setBoneType(bone.getId());
			
			List<DictItem> JOB_LEVEL = dictService.getSelectItems("JOB_LEVEL");
			DictItem jobLevel = JOB_LEVEL.get(r.nextInt(JOB_LEVEL.size()));
			teacher.setJobLevel(jobLevel.getId());
			
			List<DictItem> POLITIC_TYPE = dictService.getSelectItems("POLITIC_TYPE");
			DictItem politic = POLITIC_TYPE.get(r.nextInt(POLITIC_TYPE.size()));
			teacher.setPolitic(politic.getId());
			
			List<DictItem> DEGREE = dictService.getSelectItems("DEGREE");
			DictItem degree = DEGREE.get(r.nextInt(DEGREE.size()));
			teacher.setDegree(degree.getId());
			
			teacher.setTeachedDay(randomDate(1975,40));
			
			List<Stage> stages = stageService.selectStages();
			Long stageId = stages.get(r.nextInt(stages.size())).getId();
			teacher.setStage(stageId);
			
			CourseQuery courseQuery = new CourseQuery();
			courseQuery.setFields("id,name");
			courseQuery.setStageIdLike(false);
			courseQuery.setStageId(stageId.toString());
			List<Course> courses = courseService.selectList(courseQuery);
			if(courses.size()>0){
				Long courseId = courses.get(r.nextInt(courses.size())).getId();
				teacher.setCourse(courseId);
			}
			
			GradeQuery gradeQuery = new GradeQuery();
			gradeQuery.setFields("id,name");
			gradeQuery.setStageIdLike(false);
			gradeQuery.setStageId(stageId.toString());
			List<Grade> grades = gradeService.selectList(gradeQuery);
			if(grades.size()>0){
				Long gradeId = grades.get(r.nextInt(grades.size())).getId();
				teacher.setGrade(gradeId);
			}
			
			
			if(i%3==0){
				teacher.setUniv("清华大学");
			}else{
				teacher.setUniv("北京大学");
			}*/
			System.out.println(teacher);
			
			teacherService.saveImpTeacher(teacher);
		}
		
	}
	
	@Test
	public void randomYear(){
		Random r = new Random();
		int nextInt = 50+r.nextInt(49);
		System.out.println(nextInt);
	}
	
	/**
	 * 随机生成年月日
	 * @param minYear  1950最小年
	 * @param score   范围 20年之间浮动
	 */
	private static Date randomDate(Integer minYear,int score){
		String dateStr = "";
		Random r = new Random();
		int nextInt = minYear+r.nextInt(score);
		int mth = 1+r.nextInt(9);
		int day = 10+r.nextInt(16);
		dateStr = nextInt +"-0"+mth+"-"+day;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date parse = null;
		try {
			parse = df.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parse;
	}
	
}
