package com.yanxiu.ce.common.utils.excel;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.yanxiu.ce.common.exception.BizException;
import com.yanxiu.ce.common.utils.DateUtils;

/**
 * Excel导入工具类
 * @author tangmin
 * @date 2016年3月29日
 */
public class ExcelImportUtils {

	public static final int introwaccess = 200;// 内存中缓存记录行数

	/**
	 * 根据文件，转换成一个List<String[]>
	 * @return
	 * @throws Exception
	 */
	public static List<String[]> fileToList(MultipartFile formFile){
		InputStream in = null;
		HSSFWorkbook hssfwb = null;
		XSSFWorkbook xsswb = null;// 大数据量的数据导入，只支持xlsx
		List<String[]> list = new ArrayList<String[]>();

		try {
			if (formFile == null) {
				throw new BizException("文件为空！");
			}
			String fileName = formFile.getOriginalFilename();
			String[] split = fileName.split("\\.");
			if (split.length == 2) {
				if (split[1].equals("xls")) {
					in = formFile.getInputStream();
					// 读取excel文件分析Excel文件中的数据
					hssfwb = new HSSFWorkbook(in);
					// 读取第一页的内容
					HSSFSheet sheet = hssfwb.getSheetAt(0);

					// 一共多少行有数据
					// System.out.println(sheet.getLastRowNum());

					int arrayLong = 0;
					
					//读取标题字段，识别字段个数，如果有标题为空的，则返回最大列的值为字段个数
					HSSFRow rowTitle = sheet.getRow(0);
					for (int i = 0; i < 100; i++) {
						if (StringUtils.isEmpty(getStringValue(rowTitle, i))) {
							arrayLong = i;
							break;
						}
					}
					//System.out.println(arrayLong);
					for (int i = 0; i <= sheet.getLastRowNum(); i++) {
						HSSFRow row = sheet.getRow(i);
						String[] data = new String[arrayLong];
						// 读取数据 放在data里
						for (int j = 0; j < arrayLong; j++) {
							data[j] = getStringValue(row, j);
						}
						// 存入list
						list.add(data);
					}

				} else if (split[1].equals("xlsx")) {
					in = formFile.getInputStream();
					// 缓存200行
					xsswb = new XSSFWorkbook(in);
					// 读取第一页的内容
					XSSFSheet sheet = xsswb.getSheetAt(0);

					// 一共多少行有数据
					// System.out.println(sheet.getLastRowNum());

					int arrayLong = 0;
					
					//读取标题字段，识别字段个数，如果有标题为空的，则返回最大列的值为字段个数
					XSSFRow rowTitle = sheet.getRow(0);
					for (int i = 0; i < 100; i++) {
						if (StringUtils
								.isEmpty(getXSSFStringValue(rowTitle, i))) {
							arrayLong = i;
							break;
						}
					}
					System.out.println(arrayLong);
					for (int i = 0; i <= sheet.getLastRowNum(); i++) {
						XSSFRow row = sheet.getRow(i);
						String[] data = new String[arrayLong];
						// 读取数据 放在data里
						for (int j = 0; j < arrayLong; j++) {
							data[j] = getXSSFStringValue(row, j);
						}
						// 存入list
						list.add(data);
					}
				} else {
					throw new BizException("文件类型不能识别,只能是.xls或.xlsx！");
				}
			} else {
				throw new BizException("文件类型不能识别,只能是.xls或.xlsx！");
			}

			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(e);
		} finally {
			if (hssfwb != null) {
				try {
					hssfwb.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (xsswb != null) {
				try {
					xsswb.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 根据文件，转换成一个List<String[]>,不读取文件标题
	 * @return
	 * @throws Exception
	 */
	public static List<String[]> fileDataToList(MultipartFile formFile,Integer rowStart,Integer columnStart, Integer columnEnd){
		InputStream in = null;
		HSSFWorkbook hssfwb = null;
		XSSFWorkbook xsswb = null;// 大数据量的数据导入，只支持xlsx
		List<String[]> list = new ArrayList<String[]>();

		try {
			if (formFile == null) {
				throw new BizException("文件为空！");
			}
			String fileName = formFile.getOriginalFilename();
			String[] split = fileName.split("\\.");
			if (split.length == 2) {
				if (split[1].equals("xls")) {
					in = formFile.getInputStream();
					// 读取excel文件分析Excel文件中的数据
					hssfwb = new HSSFWorkbook(in);
					// 读取第一页的内容
					HSSFSheet sheet = hssfwb.getSheetAt(0);

					//System.out.println(arrayLong);
					for (int i = rowStart; i <= sheet.getLastRowNum(); i++) {
						HSSFRow row = sheet.getRow(i);
						String[] data = new String[columnEnd-columnStart+1];
						// 读取数据 放在data里
						for (int j = columnStart; j <= columnEnd; j++) {
							data[j-columnStart] = getStringValue(row, j);
						}
						// 存入list
						list.add(data);
					}

				} else if (split[1].equals("xlsx")) {
					in = formFile.getInputStream();
					// 缓存200行
					xsswb = new XSSFWorkbook(in);
					// 读取第一页的内容
					XSSFSheet sheet = xsswb.getSheetAt(0);
					for (int i = rowStart; i <= sheet.getLastRowNum(); i++) {
						XSSFRow row = sheet.getRow(i);
						String[] data = new String[columnEnd-columnStart+1];
						// 读取数据 放在data里
						for (int j = columnStart; j <= columnEnd; j++) {
							data[j-columnStart] = getXSSFStringValue(row, j);
						}
						// 存入list
						list.add(data);
					}
				} else {
					throw new BizException("文件类型不能识别,只能是.xls或.xlsx！");
				}
			} else {
				throw new BizException("文件类型不能识别,只能是.xls或.xlsx！");
			}

			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(e);
		} finally {
			if (hssfwb != null) {
				try {
					hssfwb.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (xsswb != null) {
				try {
					xsswb.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 取索引位置的 数值类型 数据
	 * 
	 * @param row
	 * @param index
	 * @return
	 */
	@SuppressWarnings("unused")
	private static int getIntCellValue(HSSFRow row, int index) {
		int rtn = 0;
		try {
			@SuppressWarnings("deprecation")
			HSSFCell cell = row.getCell((short) index);
			rtn = (int) cell.getNumericCellValue();
		} catch (RuntimeException e) {
		}
		return rtn;
	}

	/**
	 * 取索引位置的 字符串类型 数据
	 * 
	 * @param row
	 * @param index
	 * @return
	 */
	private static String getStringValue(HSSFRow row, int index) {
		String rtn = "";
		try {
			@SuppressWarnings("deprecation")
			HSSFCell cell = row.getCell((short) index);
			// rtn = cell.getRichStringCellValue().getString();
			rtn = getCellStringValue(cell);
		} catch (RuntimeException e) {
		}
		return rtn.trim();//去首尾空格
	}

	/**
	 * 取索引位置的 数值型 数据
	 * 
	 * @param row
	 * @param index
	 * @return
	 */
	@SuppressWarnings("unused")
	private static int getXSSFIntCellValue(XSSFRow row, int index) {
		int rtn = 0;
		try {
			XSSFCell cell = row.getCell((short) index);
			rtn = (int) cell.getNumericCellValue();
		} catch (RuntimeException e) {
		}
		return rtn;
	}

	/**
	 * 取索引位置的 字符串类型 数据
	 * 
	 * @param row
	 * @param index
	 * @return
	 */
	private static String getXSSFStringValue(XSSFRow row, int index) {
		String rtn = "";
		try {
			XSSFCell cell = row.getCell((short) index);
			rtn = getXSSFCellStringValue(cell);
		} catch (RuntimeException e) {
		}
		return rtn.trim();//去首尾空格
	}

	/**
	 * 根据单元格不同属性返回字符串数值
	 * 
	 * @param cell
	 * @return
	 */
	private static String getCellStringValue(HSSFCell cell) {
		String cellValue = "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:// 字符串类型
			cellValue = cell.getStringCellValue();
			if (cellValue.trim().equals("") || cellValue.trim().length() <= 0)
				cellValue = " ";
			break;
		case HSSFCell.CELL_TYPE_NUMERIC: // 数值类型
			if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
				Date date = cell.getDateCellValue();
				if (date != null) {
					cellValue = DateUtils.SHORTDATEFORMAT.format(date);
				}else{
					cellValue="";
				}
			} else {
				double value = cell.getNumericCellValue();
				CellStyle style = cell.getCellStyle();
				DecimalFormat format = new DecimalFormat();
				String temp = style.getDataFormatString();
				// 单元格设置成常规
				if (temp.equals("General")) {
					format.applyPattern("#");
				}
				cellValue = format.format(value);
			}
			break;
		case HSSFCell.CELL_TYPE_FORMULA: // 公式
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cellValue = String.valueOf(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			cellValue = " ";
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			break;
		default:
			break;
		}
		return cellValue;
	}

	/**
	 * 根据单元格不同属性返回字符串数值
	 * 
	 * @param cell
	 * @return
	 */
	private static String getXSSFCellStringValue(XSSFCell cell) {
		String cellValue = "";
		switch (cell.getCellType()) {
		case XSSFCell.CELL_TYPE_STRING:// 字符串类型
			cellValue = cell.getStringCellValue();
			if (cellValue.trim().equals("") || cellValue.trim().length() <= 0)
				cellValue = " ";
			break;
		case XSSFCell.CELL_TYPE_NUMERIC: // 数值类型
			if (DateUtil.isCellDateFormatted(cell)) {
				Date date = cell.getDateCellValue();
				if (date != null) {
					cellValue = DateUtils.SHORT_DATE_DOT_FORMAT.format(date);
				}else{
					cellValue="";
				}
			} else {
				cellValue = String.valueOf(cell.getNumericCellValue());
			}
			break;
		case XSSFCell.CELL_TYPE_FORMULA: // 公式
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cellValue = String.valueOf(cell.getNumericCellValue());
			break;
		case XSSFCell.CELL_TYPE_BLANK:
			cellValue = " ";
			break;
		case XSSFCell.CELL_TYPE_BOOLEAN:
			break;
		case XSSFCell.CELL_TYPE_ERROR:
			break;
		default:
			break;
		}
		return cellValue;
	}

}
