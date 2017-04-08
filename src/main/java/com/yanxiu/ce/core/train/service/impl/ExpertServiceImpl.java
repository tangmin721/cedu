package com.yanxiu.ce.core.train.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yanxiu.ce.common.constant.AppConstant;
import com.yanxiu.ce.common.core.dao.BaseDao;
import com.yanxiu.ce.common.core.entity.Pagination;
import com.yanxiu.ce.common.core.service.impl.BaseServiceImpl;
import com.yanxiu.ce.common.exception.ValidateOtherException;
import com.yanxiu.ce.common.utils.DateUtils;
import com.yanxiu.ce.common.utils.OrderWrapper;
import com.yanxiu.ce.common.utils.excel.ExcelImportUtils;
import com.yanxiu.ce.core.basic.entity.School;
import com.yanxiu.ce.core.basic.entity.SchoolQuery;
import com.yanxiu.ce.core.basic.entity.Teacher;
import com.yanxiu.ce.core.basic.entity.TeacherQuery;
import com.yanxiu.ce.core.basic.enums.ExcelColEnum;
import com.yanxiu.ce.core.train.dao.ExpertDao;
import com.yanxiu.ce.core.train.entity.Expert;
import com.yanxiu.ce.core.train.entity.ExpertQuery;
import com.yanxiu.ce.core.train.service.ExpertService;

/**
 * 专家信息管理
 * @author tangmin
 * @date 2016-07-29 15:49:04
 */
@Service("expertService")
public class ExpertServiceImpl extends BaseServiceImpl<Expert, ExpertQuery> implements ExpertService{
	@Autowired
	private ExpertDao dao;

	@Override
	protected BaseDao<Expert, ExpertQuery> dao() {
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
	public Boolean checkNameExit(Expert entity) {
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
	public String saveOrUpdate(Expert entity) {
//		if(!checkNameExit(entity)){
//			if(entity.getId()==null){
//				throw new ValidateOtherException(ValidateOtherException.INSERT_FAILD,"名称已经存在，新增失败");
//			}else {
//				throw new ValidateOtherException(ValidateOtherException.UPDATE_FAILD,"名称已经存在，修改失败");
//			}
//		}
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
	
	/**
	 * 验证excel
	 * @param file
	 * @throws Exception
	 */
	@Override
	public List<String> checkExcel(MultipartFile file){
		List<String[]> fileToList = ExcelImportUtils.fileDataToList(file,2,1,10);
		
//		for(int k=0;k<fileToList.size();k++){
//			System.out.println("1--"+fileToList.get(k)[0]);
//			System.out.println("2--"+fileToList.get(k)[1]);
//			System.out.println("3--"+fileToList.get(k)[2]);
//			System.out.println("4--"+fileToList.get(k)[3]);
//			System.out.println("5--"+fileToList.get(k)[4]);
//			System.out.println("6--"+fileToList.get(k)[5]);
//			System.out.println("7--"+fileToList.get(k)[6]);
//			System.out.println("8--"+fileToList.get(k)[7]);
//			System.out.println("9--"+fileToList.get(k)[8]);
//			System.out.println("10--"+fileToList.get(k)[9]);
//		}
		
		Map<Integer,Expert> expertMap = Maps.newHashMap();//expert + 索引位置,用于记录行号，输出 出错的行号

		List<String> errorList = Lists.newArrayList();
		
		if(errorList.size()==0){
			//逐条判断
			for(int i=0;i<fileToList.size();i++){
				
				Expert expert = new Expert();
				
				expert.setCourseName(fileToList.get(i)[0]);
				
				if(StringUtils.isBlank(fileToList.get(i)[1])){
					errorList.add("第【"+(i+3)+"】行,第【"+ExcelColEnum.getName(1+2)+"】列，【名称】不能为空！");
				}else{
					expert.setName(fileToList.get(i)[1]);
				}
				expert.setIdCard(fileToList.get(i)[2]);
				expert.setDept(fileToList.get(i)[3]);
				expert.setTitle(fileToList.get(i)[4]);
				expert.setMobile(fileToList.get(i)[5]);//手机
				expert.setEmail(fileToList.get(i)[6]);//Email
				expert.setDirection(fileToList.get(i)[7]);
				if(StringUtils.isBlank(fileToList.get(i)[8])){
					
				}else{
					try {
						Date time = DateUtils.parseDate(fileToList.get(i)[8], DateUtils.DATE_FORMAT_DATEONLY);
						expert.setTime(time);
					} catch (ParseException e) {
						errorList.add("第【"+(i+3)+"】行,第【"+ExcelColEnum.getName(8+2)+"】列，【授课时间】格式错误！日期应该形如："+DateUtils.DATE_FORMAT_DATEONLY);
					}
				}
				expert.setGoodRate(fileToList.get(i)[9]);

				expertMap.put( i,expert);		
			}
		}
				
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
	 * 从Excel中导入
	 * @param file
	 * @throws Exception
	 */
	@Override
	@Transactional
	public Long importExcel(MultipartFile file) throws ParseException{
		List<String[]> fileToList = ExcelImportUtils.fileDataToList(file,2,1,10);
		
		List<Expert> expertList = new ArrayList<Expert>();
		for(String[] row:fileToList){
			
			System.out.println("1--"+row[0]);
			System.out.println("2--"+row[1]);
			System.out.println("3--"+row[2]);
			System.out.println("4--"+row[3]);
			System.out.println("5--"+row[4]);
			System.out.println("6--"+row[5]);
			System.out.println("7--"+row[6]);
			System.out.println("8--"+row[7]);
			System.out.println("9--"+row[8]);
			System.out.println("10--"+row[9]);
			
			Expert expert = new Expert();
			
			expert.setCourseName(row[0]);
			expert.setName(row[1]);
			expert.setIdCard(row[2]);
			expert.setDept(row[3]);
			expert.setTitle(row[4]);
			expert.setMobile(row[5]);//手机
			expert.setEmail(row[6]);//Email
			expert.setDirection(row[7]);
			if(row[8] != null && !row[8].equals("")){
				expert.setTime(DateUtils.parseDate(row[8], DateUtils.DATE_FORMAT_DATEONLY));
			}else{
				expert.setTime(null);
			}
			expert.setGoodRate(row[9]);
			expertList.add(expert);
		}
		
		Long rows = this.insertBatch(expertList);
		return rows;
	}
	
	public List<String> getExcelFieldName(){
		String[] fieldNameArray = {"*姓名","身份证","单位","职称","联系电话","电子邮件","研究方向","授课时间","优良率","课程名称"};
		return Arrays.asList(fieldNameArray);
	}
	
	/**
	 * 导出 转换为excel数据
	 * @param schools
	 * @return
	 */
	private List<List<String>> changeObjToStringList(List<Expert> experts) {
		List<List<String>> result = Lists.newArrayList();
		for(Expert expertt:experts){
			List<String> ls = Lists.newArrayList();
			ls.add(expertt.getName());
			ls.add(expertt.getIdCard());
			ls.add(expertt.getDept());
			ls.add(expertt.getTitle());
			ls.add(expertt.getMobile());
			ls.add(expertt.getEmail());
			ls.add(expertt.getDirection());
			if(expertt.getTime() != null){
				ls.add(DateUtils.sdfDateOnly.format(expertt.getTime()));
			}else{
				ls.add("");
			}
			ls.add(expertt.getGoodRate());
			ls.add(expertt.getCourseName());
			result.add(ls);
		}
		return result;
	}
	
	/**
	 * 导出的excel数据  根据query
	 */
	@Override
	public List<List<String>> getExcelDatasByQuery(ExpertQuery query) {
		query.setPageSize(AppConstant.MAX_PAGE_SIZE);
		Pagination<Expert> page = this.selectListPagination(query);
		return changeObjToStringList(page.getList());
	}

}