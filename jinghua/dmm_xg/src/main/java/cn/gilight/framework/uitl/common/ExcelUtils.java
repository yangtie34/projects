package cn.gilight.framework.uitl.common;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import cn.gilight.framework.entity.ExcelParam;


/**
 * POI导出excel表工具类
 * @author lijun
 *
 */
public class ExcelUtils {
	
	
	/**
	 * 根据表格数据 创建excel
	 * @param list 数据列表 <br>
	 * [ {name:'',sex:''},{} ]
	 * @param fields 字段名 <br>
	 * [ name, sex ]
	 * @param headers 表头 <br>
	 * [ '姓名', '性别' ]
	 * @param title 文件标题
	 * @return HSSFWorkbook
	 */
	public static HSSFWorkbook createExcel(List<Map<String, Object>> list, List<String> fields, List<String> headers, String title){
		//1、创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		//1.1、创建合并单元格对象
		// 分析表头需要几列合并
		int column = headers!=null ? headers.size() : 1;
		if(list != null && list.size() > 0){
			int column2 = list.get(0).entrySet().size();
			column = column2>column ? column2 : column;
		}
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 1, 0, column);//起始行号，结束行号，起始列号，结束列号
		int thisRow = 2;
		//1.2、头标题样式
		HSSFCellStyle style1 = createCellStyle(workbook, (short)15);
		//1.3、列标题样式
		HSSFCellStyle style2 = createCellStyle(workbook, (short)12);

		//2、创建工作表
		HSSFSheet sheet = workbook.createSheet(title);
		//2.1、加载合并单元格对象
		sheet.addMergedRegion(cellRangeAddress);
		//设置默认列宽
		sheet.setDefaultColumnWidth(25);

		//3、创建行
		//3.1、创建头标题行；并且设置头标题
		HSSFRow row1 = sheet.createRow(0); // 一行
		HSSFCell cell1 = row1.createCell(0); // 一个单元格
		//加载表头样式
		cell1.setCellStyle(style1);
		cell1.setCellValue(title);
		
		//3.2、创建列标题行；并且设置列标题
		HSSFRow row2 = sheet.createRow(thisRow++);
		for(int i = 0; i < headers.size(); i++){
			HSSFCell cell2 = row2.createCell(i);
			cell2.setCellStyle(style2); //加载单元格样式
			cell2.setCellValue(headers.get(i)); // 名称
		}

		//4、操作单元格；将用户列表写入excel
		if(list != null){
			Object value = null;
			for(int i=0,len=list.size(); i<len; i++){
				HSSFRow row = sheet.createRow(i+thisRow);
				Map<String, Object> map = list.get(i);
				if(fields != null){
					for(int j=0, jLen=fields.size(); j<jLen; j++){
						value = MapUtils.getString(map, fields.get(j));
						row.createCell(j).setCellValue(value==null ? null : String.valueOf(value));
					}
				}else{
					int j=0;
					for(Map.Entry<String, Object> entry : map.entrySet()){
						value = entry.getValue();
						row.createCell(j++).setCellValue(value==null ? null : String.valueOf(value));
					}
				}
			}
		}
		//输出到硬盘
//		FileOutputStream outputStream = new FileOutputStream(new String(filename.getBytes("utf-8"),"utf-8"));
		//5、输出
//		workbook.write(outputStream);
//		workbook.close();
		return workbook;
	}
	
	/**
	 * 导出用户的所有数据到指定的路径
	 */
	public static void exportUserExcel(String title,List<ExcelParam> userList,String filename) {
		try {

			//1、创建工作簿
			HSSFWorkbook workbook = new HSSFWorkbook();
			//1.1、创建合并单元格对象
			CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 1, 0, 4);//起始行号，结束行号，起始列号，结束列号
			
			//1.2、头标题样式
			HSSFCellStyle style1 = createCellStyle(workbook, (short)13);
			
			//1.3、列标题样式
			HSSFCellStyle style2 = createCellStyle(workbook, (short)10);
			
			//2、创建工作表
			HSSFSheet sheet = workbook.createSheet("导出数据");
			//2.1、加载合并单元格对象
			sheet.addMergedRegion(cellRangeAddress);
			//设置默认列宽
			sheet.setDefaultColumnWidth(25);
			
			//3、创建行
			//3.1、创建头标题行；并且设置头标题
			HSSFRow row1 = sheet.createRow(0);
			HSSFCell cell1 = row1.createCell(0);
			//加载单元格样式
			cell1.setCellStyle(style1);
			cell1.setCellValue(title);
			HSSFRow row6 = sheet.createRow(2);
			HSSFCell cell6 = row6.createCell(0);
			//加载单元格样式
//			cell6.setCellStyle(style1);
			cell6.setCellValue("学生名单");
			
			//3.2、创建列标题行；并且设置列标题
			HSSFRow row2 = sheet.createRow(3);
			String[] titles = {"学号","姓名", "性别", "专业", "班级"};
			for(int i = 0; i < titles.length; i++){
				HSSFCell cell2 = row2.createCell(i);
				//加载单元格样式
				cell2.setCellStyle(style2);
				cell2.setCellValue(titles[i]);
			}
			
			//4、操作单元格；将用户列表写入excel
			if(userList != null){
				for(int j = 0; j < userList.size(); j++){
					HSSFRow row = sheet.createRow(j+4);
					HSSFCell cell11 = row.createCell(0);
					cell11.setCellValue(userList.get(j).getCL1());
					HSSFCell cell12 = row.createCell(1);
					cell12.setCellValue(userList.get(j).getCL2());
					HSSFCell cell13 = row.createCell(2);
					cell13.setCellValue(userList.get(j).getCL3());
					HSSFCell cell14 = row.createCell(3);
					cell14.setCellValue(userList.get(j).getCL4());
					HSSFCell cell15 = row.createCell(4);
					cell15.setCellValue(userList.get(j).getCL5());
				}
			}
			//输出到硬盘
			FileOutputStream outputStream = new FileOutputStream(new String(filename.getBytes("utf-8"),"utf-8"));
			//5、输出
			workbook.write(outputStream);
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 导出用户的所有数据到指定的路径
	 */
	public static void exportUserExcel(String title,List<ExcelParam> userList,ServletOutputStream outputStream) {
		try {

			//1、创建工作簿
			HSSFWorkbook workbook = new HSSFWorkbook();
			//1.1、创建合并单元格对象
			CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 1, 0, 4);//起始行号，结束行号，起始列号，结束列号
			
			//1.2、头标题样式
			HSSFCellStyle style1 = createCellStyle(workbook, (short)13);
			
			//1.3、列标题样式
			HSSFCellStyle style2 = createCellStyle(workbook, (short)10);
			
			//2、创建工作表
			HSSFSheet sheet = workbook.createSheet("导出数据");
			//2.1、加载合并单元格对象
			sheet.addMergedRegion(cellRangeAddress);
			//设置默认列宽
			sheet.setDefaultColumnWidth(25);
			
			//3、创建行
			//3.1、创建头标题行；并且设置头标题
			HSSFRow row1 = sheet.createRow(0);
			HSSFCell cell1 = row1.createCell(0);
			//加载单元格样式
			cell1.setCellStyle(style1);
			cell1.setCellValue(title);
			HSSFRow row6 = sheet.createRow(2);
			HSSFCell cell6 = row6.createCell(0);
			//加载单元格样式
//			cell6.setCellStyle(style1);
			cell6.setCellValue("学生名单");
			
			//3.2、创建列标题行；并且设置列标题
			HSSFRow row2 = sheet.createRow(3);
			String[] titles = {"学号","姓名", "性别", "专业", "班级"};
			for(int i = 0; i < titles.length; i++){
				HSSFCell cell2 = row2.createCell(i);
				//加载单元格样式
				cell2.setCellStyle(style2);
				cell2.setCellValue(titles[i]);
			}
			
			//4、操作单元格；将用户列表写入excel
			if(userList != null){
				for(int j = 0; j < userList.size(); j++){
					HSSFRow row = sheet.createRow(j+4);
					HSSFCell cell11 = row.createCell(0);
					cell11.setCellValue(userList.get(j).getCL1());
					HSSFCell cell12 = row.createCell(1);
					cell12.setCellValue(userList.get(j).getCL2());
					HSSFCell cell13 = row.createCell(2);
					cell13.setCellValue(userList.get(j).getCL3());
					HSSFCell cell14 = row.createCell(3);
					cell14.setCellValue(userList.get(j).getCL4());
					HSSFCell cell15 = row.createCell(4);
					cell15.setCellValue(userList.get(j).getCL5());
				}
			}
			//5、输出
			workbook.write(outputStream);
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 创建单元格样式
	 * @param workbook 工作簿
	 * @param fontSize 字体大小
	 * @return 单元格样式
	 */
	private static HSSFCellStyle createCellStyle(HSSFWorkbook workbook, short fontSize) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);//水平居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		//创建字体
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗字体
		font.setFontHeightInPoints(fontSize);
		//加载字体
		style.setFont(font);
		return style;
	}
	//测试poi导出excel
	public static void main(String[] args) {
		List<ExcelParam> userList =new ArrayList<ExcelParam>();
		ExcelParam user=new ExcelParam();
		user.setCL1("文学院");
		user.setCL2("5132");
		user.setCL3("30");
		user.setCL4("0.1");
		user.setCL5("60400");
		userList.add(user);
		ExcelUtils.exportUserExcel("基本信息",userList,"E:\\测试.xls");
	}
}
