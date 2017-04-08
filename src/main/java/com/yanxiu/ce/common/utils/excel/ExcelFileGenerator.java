package com.yanxiu.ce.common.utils.excel;

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

import com.google.common.collect.Maps;
import com.yanxiu.ce.common.utils.DateUtils;

/**
 * 系统数据导出Excel 生成器,支持大数据量分页导出
 * @author tangmin
 * @date 2016年3月29日
 */
public class ExcelFileGenerator {

	private final int SPLIT_COUNT = 10000; //Excel每个工作簿的行数

	private List<String> fieldName = null; //excel标题数据集

	private List<List<String>> fieldData = null; //excel数据内容	

	private HSSFWorkbook workBook = null;

	/**
	 * 构造器
	 * @param fieldName 结果集的字段名
	 * @param data
	 */
	public ExcelFileGenerator(List<String> fieldName, List<List<String>> fieldData) {

		this.fieldName = fieldName;
		this.fieldData = fieldData;
	}

	/**
	 * 创建HSSFWorkbook对象
	 * @return HSSFWorkbook
	 */
	public HSSFWorkbook createWorkbook() {

		workBook = new HSSFWorkbook();//创建一个工作薄对象
		int rows = fieldData.size();//总的记录数
		int sheetNum = 0;           //指定sheet的页数

		if (rows % SPLIT_COUNT == 0) {
			sheetNum = rows / SPLIT_COUNT;
		} else {
			sheetNum = rows / SPLIT_COUNT + 1;
		}

		
		for (int i = 1; i <= sheetNum; i++) {//循环2个sheet的值
			HSSFSheet sheet = workBook.createSheet("Page " + i);//使用workbook对象创建sheet对象
			HSSFRow headRow = sheet.createRow((short) 0); //创建行，0表示第一行（本例是excel的标题）
			
			
			//列宽map
			Map<Integer,Integer> colWidthMap = Maps.newHashMap();
			for(int n=0;n<fieldName.size();n++){
				colWidthMap.put(n, 1);//初始化map
			}
			
			//分页处理excel的数据，遍历所有的结果
			for (int k = 0; k < (rows < SPLIT_COUNT ? rows : SPLIT_COUNT); k++) {
				if (((i - 1) * SPLIT_COUNT + k) >= rows)//如果数据超出总的记录数的时候，就退出循环
					break;
				HSSFRow row = sheet.createRow((short) (k + 1));//创建1行
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
				sheet.setColumnWidth(j,(colWidthMap.get(j)+2) * 256);
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
			
		}
		
		return workBook;
	}

	public void expordExcel(OutputStream os) throws Exception {
		try{
			workBook = createWorkbook();
			workBook.write(os);//将excel中的数据写到输出流中，用于文件的输出
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			os.flush();
			os.close();
		}
	}

	
	public static void excelExportDownload(HttpServletResponse response,
			List<String> fieldName, List<List<String>> fieldDatas,String fileName)
			throws UnsupportedEncodingException, IOException, Exception {
		ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator(fieldName, fieldDatas);
		
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
		excelFileGenerator.expordExcel(os);//使用输出流，导出
	}
	
}
