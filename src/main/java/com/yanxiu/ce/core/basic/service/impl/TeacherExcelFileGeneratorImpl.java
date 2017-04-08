package com.yanxiu.ce.core.basic.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yanxiu.ce.common.utils.DateUtils;
import com.yanxiu.ce.common.utils.excel.FileZip;
import com.yanxiu.ce.core.basic.entity.Teacher;
import com.yanxiu.ce.core.basic.entity.TeacherQuery;
import com.yanxiu.ce.core.basic.service.TeacherExcelFileGenerator;
import com.yanxiu.ce.core.basic.service.TeacherService;

/**
 * 系统数据导出Excel 生成器,支持大数据量分页导出
 * @author tangmin
 * @date 2016年3月29日
 */
@Component
public class TeacherExcelFileGeneratorImpl implements TeacherExcelFileGenerator{
	
	@Autowired
	private TeacherService teacherService;
	
	private final int SPLIT_DATABASE_COUNT = 10000; //一次从数据库里读取多少条数据

	private final int SPLIT_COUNT = 10000; //Excel每个工作簿的行数

	
	/**
	 * 创建HSSFWorkbook对象
	 * @return HSSFWorkbook
	 */
	public HSSFWorkbook createWorkbook(TeacherQuery query,List<String> fieldName, Map<String, String> sercherNameMap) {
		int pagesheetNum = 1;
		HSSFWorkbook workBook = new HSSFWorkbook();//创建一个工作薄对象
		//1.1教师表总记录条数
		int total = teacherService.selectListTotal(query).intValue();
		//1.2 一次从数据库里查询多少条数据，直到全部查询出来
		int page = 0;//指定数据库的第几页
		if (total % SPLIT_COUNT == 0) {
			page = total / SPLIT_DATABASE_COUNT;
		} else {
			page = total / SPLIT_DATABASE_COUNT + 1;
		}
		List<List<String>> fieldData = Lists.newArrayList();
		for (int p = 1; p <= page; p++){
			query.setPageSize(Long.parseLong(String.valueOf(SPLIT_DATABASE_COUNT)));
			query.setPageCurrent(Long.parseLong(String.valueOf(p)));
			fieldData = teacherService.getExcelDatasByQuery(query);
			int pageNo = spitDataToExcel(fieldData,fieldName,workBook,pagesheetNum, sercherNameMap);
			pagesheetNum = pageNo;
		}
		
		return workBook;
	}

	/**
	 *  把数据库的data分配写入到excel
	 * @param fieldData
	 * @param fieldName
	 * @param workBook
	 * @param p  
	 */
	private int spitDataToExcel(List<List<String>> fieldData,List<String> fieldName,HSSFWorkbook workBook,int pagesheetNum, Map<String, String> sercherNameMap) {
		
		int rows = fieldData.size();//总的记录数
		int sheetNum = 1;           //指定sheet的页数

		if (rows % SPLIT_COUNT == 0) {
			sheetNum = rows / SPLIT_COUNT;
		} else {
			sheetNum = rows / SPLIT_COUNT + 1;
		}

		
		for (int i = 1; i <= sheetNum; i++) {//循环2个sheet的值
			HSSFSheet sheet = workBook.createSheet("Page " + pagesheetNum);//使用workbook对象创建sheet对象
			pagesheetNum = pagesheetNum+1;
			
			HSSFRow sercherRowTitle = sheet.createRow((short) 0); //创建行，0表示第一行（本例是excel的标题）
			HSSFCell cellSercherTitle = sercherRowTitle.createCell( 0);//使用行对象创建列对象，0表示第1列
			HSSFCellStyle cellStyleHeadTitle = workBook.createCellStyle();//创建列的样式对象
			HSSFFont fontHeadTitle = workBook.createFont();//创建字体对象
			//字体加粗
			fontHeadTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			//字体颜色变红
			fontHeadTitle.setColor(HSSFColor.BLACK.index);
			cellStyleHeadTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平布局：居中
			//如果font中存在设置后的字体，并放置到cellStyle对象中，此时该单元格中就具有了样式字体
			cellStyleHeadTitle.setFont(fontHeadTitle);
			
			//添加样式
			cellSercherTitle.setCellType(HSSFCell.CELL_TYPE_STRING);
			cellSercherTitle.setCellValue("查询条件");//为标题中的单元格设置值
			
			HSSFRow sercherRow = sheet.createRow((short) 1); 
			
			HSSFRow headRowTitle = sheet.createRow((short) 2); 
			HSSFCell cellHeadTitle = headRowTitle.createCell( 0);
			//添加样式
			cellHeadTitle.setCellType(HSSFCell.CELL_TYPE_STRING);
			cellHeadTitle.setCellValue("教师信息");//为标题中的单元格设置值
			
			HSSFRow headRow = sheet.createRow((short) 3); //创建行，0表示第一行（本例是excel的标题）
			
			//列宽map
			Map<Integer,Integer> colWidthMap = Maps.newHashMap();
			for(int n=0;n<fieldName.size();n++){
				colWidthMap.put(n, 1);//初始化map
			}
			
			//分页处理excel的数据，遍历所有的结果
			for (int k = 0; k < (rows < SPLIT_COUNT ? rows : SPLIT_COUNT); k++) {
				if (((i - 1) * SPLIT_COUNT + k) >= rows)//如果数据超出总的记录数的时候，就退出循环
					break;
				HSSFRow row = sheet.createRow((short) (k + 4));//创建1行
				//分页处理，获取每页的结果集，并将数据内容放入excel单元格
				List<String> rowList = (List<String>) fieldData.get((i - 1) * SPLIT_COUNT + k);
				
				
				
				for (int n = 0; n < rowList.size(); n++) {//遍历某一行的结果
					HSSFCell cell = row.createCell( n);//使用行创建列对象
					if(rowList.get(n) != null){
						String cellStr = (String) rowList.get(n).toString();
						int length = cellStr.getBytes().length;
						if(length>colWidthMap.get(n)){
							colWidthMap.put(n, length);
						}
						cell.setCellValue(cellStr);
					}else{
						cell.setCellValue("");
					}
				}
			}
			
			for (int j = 0; j < fieldName.size(); j++) {//循环excel的标题
				HSSFCell cell = headRow.createCell( j);//使用行对象创建列对象，0表示第1列
				
				
				/**************对标题添加样式begin********************/
				//设置列的自动宽度
				int length = colWidthMap.get(j);
				if(length < fieldName.get(j).getBytes().length){
					length = fieldName.get(j).getBytes().length;
					colWidthMap.put(j, length);
				}
				sheet.setColumnWidth(j,(length + 2) * 256);
				//sheet.setColumnWidth(j, 6000);//设置列的固定宽度
				HSSFCellStyle cellStyle = workBook.createCellStyle();//创建列的样式对象
				HSSFFont font = workBook.createFont();//创建字体对象
				//字体加粗
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				//字体颜色变红
				font.setColor(HSSFColor.RED.index);
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平布局：居中
				//如果font中存在设置后的字体，并放置到cellStyle对象中，此时该单元格中就具有了样式字体
				cellStyle.setFont(font);
				
				/**************对标题添加样式end********************/
				
				//添加样式
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				if(fieldName.get(j) != null){
					//将创建好的样式放置到对应的单元格中
					cell.setCellStyle(cellStyle);
					cell.setCellValue((String) fieldName.get(j));//为标题中的单元格设置值
				}else{
					cell.setCellValue("-");
				}
			}
			
			int sercherTotal = 0;
			for(Map.Entry<String, String> entry : sercherNameMap.entrySet()){
				
				HSSFCell cellKey = sercherRow.createCell( 2 * sercherTotal);//使用行对象创建列对象，0表示第1列
				HSSFCell cellValue = sercherRow.createCell( 2 * sercherTotal + 1);
								
				String key = entry.getKey();
				String value = entry.getValue();
				
				/**************对标题添加样式begin********************/
				//设置列的自动宽度
				sheet.setColumnWidth(sercherTotal,(colWidthMap.get(sercherTotal) + 2) * 256);
				//sheet.setColumnWidth(j, 6000);//设置列的固定宽度
				HSSFCellStyle cellStyle = workBook.createCellStyle();//创建列的样式对象
				HSSFFont font = workBook.createFont();//创建字体对象
				//字体加粗
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				//字体颜色变红
				font.setColor(HSSFColor.RED.index);
				cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平布局：居中
				//如果font中存在设置后的字体，并放置到cellStyle对象中，此时该单元格中就具有了样式字体
				cellStyle.setFont(font);
				
				/**************对标题添加样式end********************/
				
				
				//添加样式
				cellKey.setCellType(HSSFCell.CELL_TYPE_STRING);
				if(key != null && !("").equals(key)){
					//将创建好的样式放置到对应的单元格中
					cellKey.setCellStyle(cellStyle);
					cellKey.setCellValue(key);//为标题中的单元格设置值
				}else{
					cellKey.setCellValue("-");
				}
				if(value != null && !("").equals(value)){
					cellValue.setCellValue(value);
				}else{
					cellValue.setCellValue("");
				}
				
				sercherTotal ++;
			}
		}
		
		return pagesheetNum;
	}

	public void expordExcel(OutputStream os,TeacherQuery query,List<String> fieldName, Map<String, String> sercherNameMap) throws Exception {
		try{
			HSSFWorkbook workBook = createWorkbook(query,fieldName, sercherNameMap);
			workBook.write(os);//将excel中的数据写到输出流中，用于文件的输出
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			os.flush();
			os.close();
		}
	}

	@Override
	public void excelExportDownload(HttpServletResponse response,
			List<String> fieldName, TeacherQuery query,String fileName, Map<String, String> sercherNameMap)
			throws UnsupportedEncodingException, IOException, Exception {
		
		/**导出报表的文件名*/
		fileName = new String(fileName.getBytes("gbk"),"iso-8859-1");
		String filename = fileName+"("+DateUtils.LONGDATEFORMAT.format(new Date())+").xls";
		//处理乱码
		/**response中进行设置，总结下载，导出，需要io流和头*/
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		
		response.addHeader("Content-disposition", "attachment; filename="+ filename);
		response.setBufferSize(1024);
		
		//获取输出流
		OutputStream os = response.getOutputStream();
		this.expordExcel(os,query,fieldName, sercherNameMap);//使用输出流，导出
	}
	
}
