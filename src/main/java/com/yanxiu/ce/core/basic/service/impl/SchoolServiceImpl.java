package com.yanxiu.ce.core.basic.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yanxiu.ce.common.constant.AppConstant;
import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.exception.ValidateOtherException;
import com.yanxiu.ce.common.redis.JedisClient;
import com.yanxiu.ce.common.utils.AppStringUtils;
import com.yanxiu.ce.common.utils.OrderWrapper;
import com.yanxiu.ce.common.utils.excel.ExcelImportUtils;
import com.yanxiu.ce.common.validate.BeanValidator;
import com.yanxiu.ce.common.validate.Insert;
import com.yanxiu.ce.common.validate.ValidatorBean;
import com.yanxiu.ce.common.validate.ValidatorResult;
import com.yanxiu.ce.core.basic.dao.SchoolDao;
import com.yanxiu.ce.core.basic.entity.School;
import com.yanxiu.ce.core.basic.entity.SchoolQuery;
import com.yanxiu.ce.core.basic.enums.ExcelColEnum;
import com.yanxiu.ce.core.basic.enums.TeacherTypeEnum;
import com.yanxiu.ce.core.basic.service.SchoolService;
import com.yanxiu.ce.core.basic.service.TeacherService;
import com.yanxiu.ce.system.service.CityService;
import com.yanxiu.ce.system.service.DictCatlogService;
import com.yanxiu.ce.system.service.ProvinceService;
import com.yanxiu.ce.system.service.TownService;
import com.yanxiu.ce.system.service.UserService;

/**
 * 学校管理
 * @author tangmin
 * @date 2016-03-22 19:04:55
 */
@Service("schoolService")
public class SchoolServiceImpl extends BaseServiceImpl<School, SchoolQuery> implements SchoolService{
	@Autowired
	private SchoolDao dao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private ProvinceService provinceService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private TownService townService;
	
	@Autowired
	private DictCatlogService dictCatlogService;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${SCHOOL_KEY}")
	private String SCHOOL_KEY;
	
	@Value("${COMMON_EXPIRE}")
	private Integer COMMON_EXPIRE;
	
	@Override
	protected BaseDao<School, SchoolQuery> dao() {
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
	 * 校验entity是否可修改（name是否存在）
	 */
	@Override
	public Boolean checkNameExit(School entity) {
		Long count = this.dao.selectCheckNameExit(entity.getName(),entity.getTown(), entity.getId());
		if(count>0){
			return false;
		}
		return true;
	}
	
	@Override
	public Long deleteById(Long id) {
		Long deleteById = this.dao().deleteById(id);
		//同步缓存
		try {
			School entity = this.selectById(id);
			syncRedisSchoolMap(entity.getTown());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return deleteById;
	}

	/**
	 * 覆写删除方法，删除学校同时删除账户，并同步缓存下拉框
	 */
	@Override
	@Transactional
	public Long deleteByIds(List<Long> ids) {
		Long count = 0l;
		for(Long id:ids){
			School school = this.selectById(id);
			if(school!=null && school.getSchoolNo()!=null){
				userService.deleteByLoginName(school.getSchoolNo().toString());
			}
			count++;
			
			this.deleteById(id);
		}
		
		
		//同步缓存
		try {
			for(Long id:ids){
				School entity = this.selectById(id);
				syncRedisSchoolMap(entity.getTown());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
	/**
	 * 新增or修改
	 */
	@Override
	@Transactional
	public String saveOrUpdate(School entity) {
		if(!checkNameExit(entity)){
			if(entity.getId()==null){
				throw new ValidateOtherException(ValidateOtherException.INSERT_FAILD,"学校名称已经存在，新增失败");
			}else {
				throw new ValidateOtherException(ValidateOtherException.UPDATE_FAILD,"学校名称已经存在，修改失败");
			}
		}
		String msg = "";
		if(entity.getId()==null){
			//学校编号
			String schoolNo = entity.getTown()+""+AppStringUtils.addZero(4,this.dao.nextSequenceVal());
			entity.setSchoolNo(Long.parseLong(schoolNo));
			this.insert(entity);
			msg = "添加成功！";
		}else {
			School old = this.selectById(entity.getId());
			//修改学校的省市县，则需要同时修改其下属的教师省市县。
			if(old.getProvince()!=entity.getProvince()||old.getCity()!=entity.getCity()||old.getTown()!=entity.getTown()){
				teacherService.updateBatchSchool(entity.getProvince(),entity.getCity(),entity.getTown(),entity.getId());
			}
			this.update(entity);
				msg = "编辑成功！";
				
			entity.setSchoolNo(old.getSchoolNo());
		}
		
		//同步缓存
		try {
			syncRedisSchoolMap(entity.getTown());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//生成学校账号
		userService.createSchoolUser(entity);
		
		return msg;
	}
	
	
	/**
	 * excel导入的保存方法，不用再校验了，前面已经校验完成了
	 */
	@Override
	@Transactional
	public void saveImpSchool(School entity) {
			//学校编号
		String schoolNo = entity.getTown()+""+AppStringUtils.addZero(4,this.dao.nextSequenceVal()); 
		entity.setSchoolNo(Long.parseLong(schoolNo));
		this.dao.insert(entity);
		
		userService.createSchoolUser(entity);
		//同步缓存
		try {
			syncRedisSchoolMap(entity.getTown());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<School> schools(Integer town) {
		SchoolQuery query = new SchoolQuery();
		query.setTown(town);
		query.setFields("id,name");
		//禁用掉查询全部
		if(query.getTown()==0){
			query.setTown(-1);
		}
		return this.selectList(query);
	}

	/**
	 * 导出的excel表头
	 */
	@Override
	public List<String> getExcelFieldName() {
		String[] fieldNameArray = { "*省", "*市", "*区县", "*学校名称","*类别","*大类型","*类型","学校地址","邮编","*校长","*校办电话","负责人","负责人身份证","负责人电话","负责人手机","负责人邮箱"};
		return Arrays.asList(fieldNameArray);
	}
	
	/**
	 * 导出 转换为excel数据
	 * @param schools
	 * @return
	 */
	private List<List<String>> changeObjToStringList(List<School> schools) {
		List<List<String>> result = Lists.newArrayList();
		for(School school:schools){
			List<String> ls = Lists.newArrayList();
			ls.add(school.getProvinceName());
			ls.add(school.getCityName());
			ls.add(school.getTownName());
			ls.add(school.getName());
			ls.add(school.getCategoryName());
			String stypeName = "";
			if(school.getSchoolType()== TeacherTypeEnum.MIDDLE_SCHOOL.getValue()){
				stypeName =  TeacherTypeEnum.MIDDLE_SCHOOL.getDesc();
			}else if(school.getSchoolType()== TeacherTypeEnum.TECHNICAL_SCHOOL.getValue()){
				stypeName =  TeacherTypeEnum.TECHNICAL_SCHOOL.getDesc();
			}else if(school.getSchoolType()== TeacherTypeEnum.SPECIAL_SCHOOL.getValue()){
				stypeName =  TeacherTypeEnum.SPECIAL_SCHOOL.getDesc();
			}else if(school.getSchoolType()== TeacherTypeEnum.KINDERGARTEN.getValue()){
				stypeName =  TeacherTypeEnum.KINDERGARTEN.getDesc();
			}
			ls.add(stypeName);
			ls.add(school.getTypeName());
			ls.add(school.getAddress());
			ls.add(school.getPostCode());
			ls.add(school.getMaster());
			ls.add(school.getTel());
			ls.add(school.getDirector());
			ls.add(school.getIdCard());
			ls.add(school.getTheTel());
			ls.add(school.getPhone());
			ls.add(school.getEmail());
			result.add(ls);
		}
		return result;
	}

	/**
	 * 导出的excel数据  根据query
	 */
	@Override
	public List<List<String>> getExcelDatasByQuery(SchoolQuery query) {
		query.setPageSize(AppConstant.MAX_PAGE_SIZE);
		Pagination<School> page = this.selectListPagination(query);
		return changeObjToStringList(page.getList());
	}
	
	/**
	 * 导出的excel数据  根据ids
	 */
	@Override
	public List<List<String>> getExcelDatasByIds(List<Long> ids) {
		return changeObjToStringList(this.selectByIds(ids));
	}

	/**
	 * 验证Excel
	 */
	@Override
	public List<String> checkExcel(MultipartFile file,SchoolQuery query) {
		List<String[]> fileToList = ExcelImportUtils.fileToList(file);
		String[] head = fileToList.get(0);//表头
		Map<String,Integer> headMap = Maps.newHashMap();
		for(int i=0;i<head.length;i++){
			headMap.put(head[i].replaceAll("\\*", ""), i);
		}
		fileToList.remove(0);//删除表头数据，剩下的都是实际数据
		
		Map<Integer,School> schoolMap = Maps.newHashMap();//school + 索引位置，用于记录行号，输出 出错的行号
		
		Map<String, Integer> provinceMap = provinceService.provinceMap();//省map缓存
		List<String> errorList = Lists.newArrayList();
		
		if(query.getProvince()==0){
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
		
		Integer schoolIndex = headMap.get("学校名称");
		if(schoolIndex==null){
			errorList.add("找不到【学校名称】列，请检查Excel模板是否正确！");
		}
		
		Integer categoryIndex = headMap.get("类别");
		if(categoryIndex==null){
			errorList.add("找不到【类别】列，请检查Excel模板是否正确！");
		}
		
		Integer schoolTypeIndex = headMap.get("大类型");
		if(schoolTypeIndex==null){
			errorList.add("找不到【大类型】列，请检查Excel模板是否正确！");
		}
		
		Integer typeIndex = headMap.get("类型");
		if(typeIndex==null){
			errorList.add("找不到【类型】列，请检查Excel模板是否正确！");
		}
		
		Integer masterIndex = headMap.get("校长");
		if(masterIndex==null){
			errorList.add("找不到【校长】列，请检查Excel模板是否正确！");
		}
		
		Integer telIndex = headMap.get("校办电话");
		if(telIndex==null){
			errorList.add("找不到【校办电话】列，请检查Excel模板是否正确！");
		}
		
		Integer addressIndex = headMap.get("学校地址");
		if(addressIndex==null){
			errorList.add("找不到【学校地址】列，请检查Excel模板是否正确！");
		}
		
		Integer postIndex = headMap.get("邮编");
		if(postIndex==null){
			errorList.add("找不到【邮编】列，请检查Excel模板是否正确！");
		}
		
		Integer dictorIndex = headMap.get("负责人");
		if(dictorIndex==null){
			errorList.add("找不到【负责人】列，请检查Excel模板是否正确！");
		}
		
		Integer theTelIndex = headMap.get("负责人电话");
		if(theTelIndex==null){
			errorList.add("找不到【负责人电话】列，请检查Excel模板是否正确！");
		}
		
		Integer cardIndex = headMap.get("负责人身份证");
		if(cardIndex==null){
			errorList.add("找不到【负责人身份证】列，请检查Excel模板是否正确！");
		}
		
		Integer mobileIndex = headMap.get("负责人手机");
		if(mobileIndex==null){
			errorList.add("找不到【负责人手机】列，请检查Excel模板是否正确！");
		}
		
		Integer emailIndex = headMap.get("负责人邮箱");
		if(emailIndex==null){
			errorList.add("找不到【负责人邮箱】列，请检查Excel模板是否正确！");
		}
		
		if(errorList.size()==0){
			//逐条判断
			for(int i=0;i<fileToList.size();i++){
				
				School school = new School();
				
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
				school.setProvince(province);
				
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
				
				school.setCity(city);
				
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
				school.setTown(town);
				
				if(StringUtils.isBlank(fileToList.get(i)[schoolIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(schoolIndex+1)+"】列，【学校名称】不能为空！");
				}else{
					school.setName(fileToList.get(i)[schoolIndex]);
				}
				
				Map<String, Long> categoryMap = dictCatlogService.getSelectItemsMap("SCHOOL_CATEGORY");//字典学校类别
				if(StringUtils.isBlank(fileToList.get(i)[categoryIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(categoryIndex+1)+"】列，【类别】不能为空！");
				}else{
					Long category = categoryMap.get(fileToList.get(i)[categoryIndex]);
					if(category==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(categoryIndex+1)+"】列，【类别】名称错误，找不到对应的类别名，请参照查询条件中的【类别下拉框】！");
					}else{
						school.setCategory(category);
					}
				}
				
				Map<String, Integer> schoolTypeMap = TeacherTypeEnum.toSimpleMap();//学校大类型
				if(StringUtils.isBlank(fileToList.get(i)[schoolTypeIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(schoolTypeIndex+1)+"】列，【大类型】不能为空！");
				}else{
					Integer schoolType = schoolTypeMap.get(fileToList.get(i)[schoolTypeIndex]);
					if(schoolType==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(schoolTypeIndex+1)+"】列，【大类型】名称错误，找不到对应的类型名，请参照查询条件中的【大类型下拉框】！");
					}else{
						school.setSchoolType(schoolType);
					}
				}
				
				Map<String, Long> typeMap = dictCatlogService.getSelectItemsMap("SCHOOL_TYPE");//字典学校类型
				if(StringUtils.isBlank(fileToList.get(i)[typeIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(typeIndex+1)+"】列，【类型】不能为空！");
				}else{
					Long type = typeMap.get(fileToList.get(i)[typeIndex]);
					if(type==null){
						errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(typeIndex+1)+"】列，【类型】名称错误，找不到对应的类型名，请参照查询条件中的【类型下拉框】！");
					}else{
						school.setType(type);
					}
				}
				
				if(StringUtils.isBlank(fileToList.get(i)[masterIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(masterIndex+1)+"】列，【校长】不能为空！");
				}else{
					school.setMaster(fileToList.get(i)[masterIndex]);
				}
				
				if(StringUtils.isBlank(fileToList.get(i)[telIndex])){
					errorList.add("第【"+(i+2)+"】行,第【"+ExcelColEnum.getName(telIndex+1)+"】列，【校办电话】不能为空！");
				}else{
					school.setTel(fileToList.get(i)[telIndex]);
				}
				school.setAddress(fileToList.get(i)[addressIndex]);
				school.setPostCode(fileToList.get(i)[postIndex]);
				school.setDirector(fileToList.get(i)[dictorIndex]);
				school.setTheTel(fileToList.get(i)[theTelIndex]);
				school.setIdCard(fileToList.get(i)[cardIndex]);
				school.setPhone(fileToList.get(i)[mobileIndex]);
				school.setEmail(fileToList.get(i)[emailIndex]);
				schoolMap.put( i+2,school);
				
				
				
			}
		}
		
		
		//*********************校验Excel表重复的 学校名称
		validateExcelSchoolName(schoolMap, errorList);
		
		//************************最后一步，hibernate validate校验****************************************************
		validateHibernate(schoolMap, errorList);
		
		
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
	 * 校验Excel表重复的 学校名称
	 * @param schoolMap
	 * @param errorList
	 */
	private void validateExcelSchoolName(Map<Integer, School> schoolMap,
			List<String> errorList) {
		List<String> schoolNameList = Lists.newArrayList();//Excel（市+学校）的字符串集合
		if(errorList.size()==0){
			Iterator<Map.Entry<Integer, School>> entries = schoolMap.entrySet().iterator();
			while (entries.hasNext()) {
			    Map.Entry<Integer, School> entry = entries.next();
				School value = entry.getValue();
				//Excel重复值校验
				String nameAndTown = value.getTown()+value.getName();//根据市，学校名判断唯一
				schoolNameList.add(nameAndTown);
			}
			
			Iterator<Map.Entry<Integer, School>> nameEntries = schoolMap.entrySet().iterator();
			while (nameEntries.hasNext()) {
			    Map.Entry<Integer, School> entry = nameEntries.next();
			    Integer key = entry.getKey();
				School value = entry.getValue();
				String nameAndTown = value.getTown()+value.getName();//根据市，学校名判断唯一
				//统计出现的次数
				int count = Collections.frequency(schoolNameList, nameAndTown);
				if(count>1){
					errorList.add("第【"+key+"】行，学校名称为【"+value.getName()+"】,在Excel表中存在相同的名称，请修改！");
				}
			}
			
		}
	}

	
	/**
	 * hibernate validate校验
	 * @param schoolMap
	 * @param errorList
	 */
	private void validateHibernate(Map<Integer, School> schoolMap,
			List<String> errorList) {
		if(errorList.size()==0){
			Iterator<Map.Entry<Integer, School>> entries = schoolMap.entrySet().iterator();
			while (entries.hasNext()) {
			    Map.Entry<Integer, School> entry = entries.next();
			    Integer key = entry.getKey();
				School value = entry.getValue();
				
			    ValidatorResult validateResult = BeanValidator.validateResult(value, Insert.class);
				if(!validateResult.getFlag()){
					List<ValidatorBean> errorObjs = validateResult.getErrorObjs();
					for(ValidatorBean error:errorObjs){
						errorList.add("第【"+key+"】行,错误信息：数据表字段名("+ error.getFiled()+"):" + error.getErrorMsg());
					}
				}
				if(!checkNameExit(value)){
					errorList.add("第【"+key+"】行，学校名称为【"+value.getName()+"】,已经存在");
				}
			}
		}
	}
	
	/**
	 * 导入Excel文件
	 */
	@Override
	@Transactional
	public Integer importExcel(MultipartFile file,SchoolQuery query){
		List<String[]> fileToList = ExcelImportUtils.fileToList(file);
		String[] head = fileToList.get(0);//表头
		Map<String,Integer> headMap = Maps.newHashMap();
		for(int i=0;i<head.length;i++){
			headMap.put(head[i].replaceAll("\\*", ""), i);
		}
		fileToList.remove(0);//删除表头数据，剩下的都是实际数据
		
		Map<String, Integer> provinceMap = provinceService.provinceMap();//省map缓存
		
		for(String[] row:fileToList){
			School school = new School();
			
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
			
			//省市县
			school.setProvince(province);
			school.setCity(city);
			school.setTown(town);
			
			school.setName(row[headMap.get("学校名称")]);
			
			Map<String, Long> categoryMap = dictCatlogService.getSelectItemsMap("SCHOOL_CATEGORY");//字典学校类别
			school.setCategory(categoryMap.get(row[headMap.get("类别")]));
			
			Map<String, Integer> schoolTypeMap = TeacherTypeEnum.toSimpleMap();//学校大类型
			school.setSchoolType(schoolTypeMap.get(row[headMap.get("大类型")]));
			
			Map<String, Long> typeMap = dictCatlogService.getSelectItemsMap("SCHOOL_TYPE");//字典学校类型
			school.setType(typeMap.get(row[headMap.get("类型")]));
			
			school.setMaster(row[headMap.get("校长")]);
			school.setTel(row[headMap.get("校办电话")]);
			
			
			school.setAddress(row[headMap.get("学校地址")]);
			school.setPostCode(row[headMap.get("邮编")]);
			school.setDirector(row[headMap.get("负责人")]);
			school.setIdCard(row[headMap.get("负责人身份证")]);
			school.setTheTel(row[headMap.get("负责人电话")]);
			school.setPhone(row[headMap.get("负责人手机")]);
			school.setEmail(row[headMap.get("负责人邮箱")]);
			
			//状态
			//school.setStatus(SchoolStatusEnum.UNCONFIRMED.getValue());
			
			this.saveImpSchool(school);//由于需要获取学校编号，所以，只能单条循环插入
		}
		
		return fileToList.size();
	}

	@Override
	public Map<String, Long> schoolMap(Integer townNo) {
		//得到结果之前从redis中获取数据
		try {
			String json = jedisClient.get(SCHOOL_KEY+"_MAP:"+townNo);
			if(StringUtils.isNoneBlank(json)){
				Map parseObject = JSON.parseObject(json);
				Map<String, Long> map = Maps.newHashMap();
				for (Object o : parseObject.entrySet()) { 
				      @SuppressWarnings("unchecked")
				      Map.Entry<String,Integer> entry = (Map.Entry<String,Integer>)o; 
				      map.put(entry.getKey(), Long.parseLong(entry.getValue().toString()));
				    } 
				return map;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		Map<String, Long> map = Maps.newHashMap();
		List<School> schools = this.schools(townNo);
		for(School school:schools){
			map.put(school.getName().replaceAll("　", ""), school.getId());
		}
		
		//返回结果之前向redis中添加数据
		try {
			jedisClient.set(SCHOOL_KEY+"_MAP:"+townNo, JSON.toJSONString(map));
			jedisClient.expire(SCHOOL_KEY+"_MAP:"+townNo, COMMON_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
		
	}

	@Override
	public Long syncRedisSchoolMap(Integer townNo) {
		return jedisClient.del(SCHOOL_KEY+"_MAP:"+townNo);
	}

	

	
}