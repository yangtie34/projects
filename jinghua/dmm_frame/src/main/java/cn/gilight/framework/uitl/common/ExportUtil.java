package cn.gilight.framework.uitl.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import cn.gilight.framework.entity.TsStsx;

import com.alibaba.fastjson.JSONObject;


/**
 * Author: LJH Date: 2012-4-13
 */
public class ExportUtil {
	private static final int BUFFER = 1024;
	
	/**
	 * 创建路径下的文件，并返回文件
	 * @param fileName
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static File createFile(String fileName,String path)  throws IOException {
		File file = new File(path+fileName);		
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		return file;
	}
	/**
	 * 使用默认路径，创建文件
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static File createFile(String fileName) throws IOException {
		File file = new File(fileName);		
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		return file;
	}

	public static void writeWorkbookToZipFile(HSSFWorkbook workbook,
			String fileName, File archiveFile, String comment) throws Throwable {
		File file = new File(fileName);
		file.createNewFile();
		FileOutputStream fo = new FileOutputStream(file);
		workbook.write(fo);
		fo.flush();
		fo.close();
		// ----压缩文件：
		FileOutputStream f = new FileOutputStream(archiveFile);
		// 使用指定校验和创建输出流
		CheckedOutputStream csum = new CheckedOutputStream(f, new CRC32());

		ZipOutputStream zos = new ZipOutputStream(csum);
		compressFile(file, zos);
		file.delete();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void writeWorkbookListToZipFile(
			List<HSSFWorkbook> workbookList, File archiveFile, String comment)
			throws Throwable {
		List fileList = new ArrayList();
		for (HSSFWorkbook book : workbookList) {
			String title = book.getSheetName(0);
			File file = new File(title + ".xls");
			file.createNewFile();
			FileOutputStream fo = new FileOutputStream(file);
			book.write(fo);
			fo.flush();
			fo.close();
			fileList.add(file);
		}

		FileOutputStream f = new FileOutputStream(archiveFile);
		// 使用指定校验和创建输出流
		CheckedOutputStream csum = new CheckedOutputStream(f, new CRC32());

		ZipOutputStream zos = new ZipOutputStream(csum);
		compressFile(fileList,zos);
		zos.close();
	}

	private static void compressFile(List<File> fileList, ZipOutputStream zos)
			throws Throwable {
		for (File file : fileList) {
			ZipEntry entry = new ZipEntry(file.getName());
			zos.putNextEntry(entry);
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(file));
			int count;
			byte data[] = new byte[BUFFER];
			while ((count = bis.read(data, 0, BUFFER)) != -1) {
				zos.write(data, 0, count);
			}
			bis.close();
			zos.closeEntry();
		}		
		
	}

	private static void compressFile(File file, ZipOutputStream zos)
			throws Exception {

		/**
		 * 压缩包内文件名定义
		 * 
		 * <pre>
		 * 如果有多级目录，那么这里就需要给出包含目录的文件名 
		 * 如果用WinRAR打开压缩包，中文名将显示为乱码
		 * </pre>
		 */
		ZipEntry entry = new ZipEntry(file.getName());

		zos.putNextEntry(entry);

		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
				file));

		int count;
		byte data[] = new byte[BUFFER];
		while ((count = bis.read(data, 0, BUFFER)) != -1) {
			zos.write(data, 0, count);
		}
		bis.close();
		zos.closeEntry();
		zos.close();
	}

	/**
	 * excel 头样式
	 * 
	 * @param workbook
	 * @return
	 */
	public static HSSFCellStyle createHeaderStyle(HSSFWorkbook workbook) {

		HSSFCellStyle style = workbook.createCellStyle();
		HSSFPalette palette = workbook.getCustomPalette();
		palette.setColorAtIndex((short) 11, (byte) (182), (byte) (221),
				(byte) (232));

		style.setFillForegroundColor((short) 11);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);

		return style;
	}

	/**
	 * excel 内容样式
	 * 
	 * @param workbook
	 * @return
	 */
	public static HSSFCellStyle createContentStyle(HSSFWorkbook workbook) {
		// 生成并设置另一个样式 用于内容　
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.WHITE.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		return style2;
	}

	/**
	 * excel 空白样式（合并单元格）
	 * 
	 * @param workbook
	 * @return
	 */
	private static HSSFCellStyle createBlankStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.WHITE.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font.setFontHeightInPoints((short) 30);
		font.setFontName("微软雅黑");
		// 把字体应用到当前的样式
		style2.setFont(font);

		return style2;
	}

	/**
	 * 创建空白行
	 * 
	 * @param workbook
	 * @param sheet
	 * @param rowNum
	 * @param columnNum
	 * @param title
	 * @return
	 */
	private static int createBlankRow(HSSFWorkbook workbook, HSSFSheet sheet,
			int rowNum, int columnNum, String title) {
		int i = 0;
		for (; i < rowNum; i++) {
			HSSFRow blankRow = sheet.createRow(i);
			blankRow.setHeightInPoints(23);
			for (int j = 0; j < columnNum; j++) {
				HSSFCell cell = blankRow.createCell(j);
				cell.setCellStyle(createBlankStyle(workbook));

				HSSFRichTextString text = new HSSFRichTextString(title);
				cell.setCellValue(text);
			}

		}
		return i;
	}

	/**
	 * 创建excel表格的表头，
	 * 
	 * @param workbook
	 * @param sheet
	 * @param startIndex
	 * @param headers
	 *            <Map> map(key,mc,index)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void createExcelHeaderRow(HSSFWorkbook workbook,
			HSSFSheet sheet, int startRowIndex, List<Map> headers) {
		HSSFCellStyle style = createHeaderStyle(workbook);

		// 产生表格标题行
		HSSFRow rowHeader = sheet.createRow(startRowIndex);
		rowHeader.setHeightInPoints(23);
		for (int i = 0; i < headers.size(); i++) {
			HSSFCell cell = rowHeader.createCell(i);
			HSSFFont newFont = workbook.createFont();
			cell.setCellStyle(style);
			Map map = headers.get(i);
			HSSFRichTextString text = new HSSFRichTextString(String.valueOf(map
					.get("mc")));
			text.applyFont(newFont);
			cell.setCellValue(text);
			map.put("index", i);// 这里加序号
		}
	}

	/**
	 * headers中key的值，是dataMap的key
	 * 
	 * @param workbook
	 * @param sheet
	 * @param startRowIndex
	 * @param headers
	 * @param dataMap
	 */
	@SuppressWarnings({ "rawtypes" })
	public static void createExcelContentRow(HSSFWorkbook workbook,
			HSSFSheet sheet, int startRowIndex, List<Map> headers, Map dataMap) {
		HSSFCellStyle style2 = createContentStyle(workbook);
		// 插入具体内容
		HSSFRow row = sheet.createRow(startRowIndex);
		row.setHeightInPoints(23);
		for (Map header : headers) {
			Object key = header.get("key");
			Object value = dataMap.get(key);
			Integer index = (Integer) header.get("index");
			HSSFCell cell = null;
			cell = row.createCell(index);
			cell.setCellStyle(style2);
			try {
				if (value != null) {
					cell.setCellValue(String.valueOf(value));
				}

			} catch (NumberFormatException e) {
				// cell.setCellValue(String.valueOf(value));
			}
		}
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 
	 * @param workbook
	 * @param sheet
	 * @param startRowIndex
	 * @param headers
	 *            headers<Map> map(key,mc,index)
	 * @param dataList
	 *            (dataMap)
	 */
	@SuppressWarnings("rawtypes")
	public static void createExcelContent(HSSFWorkbook workbook,
			HSSFSheet sheet, int startRowIndex, List<Map> headers,
			List<Map> dataList) {
		for (int i = 0; i < dataList.size(); i++) {
			Map dataMap = dataList.get(i);
			int rowIndex = startRowIndex + i;
			createExcelContentRow(workbook, sheet, rowIndex, headers, dataMap);
		}
	}

	/**
	 * 创建excel头
	 * 
	 * @param workbook
	 * @param sheet
	 * @param startIndex
	 * @param headers
	 */
	public static void createHeaderRow(HSSFWorkbook workbook, HSSFSheet sheet,
			int startIndex, List<TsStsx> headers) {
		HSSFCellStyle style = createHeaderStyle(workbook);

		// 产生表格标题行
		HSSFRow rowHeader = sheet.createRow(startIndex);
		rowHeader.setHeightInPoints(23);
		for (int i = 0; i <= headers.size(); i++) {
			HSSFCell cell = rowHeader.createCell(i);
			HSSFFont newFont = workbook.createFont();
			cell.setCellStyle(style);
			if (0 == i) {
				HSSFRichTextString text = new HSSFRichTextString("序号");
				cell.setCellValue(text);
			} else {
				if (headers.get(i - 1).getSffk() == null
						|| headers.get(i - 1).getSffk() == 0L) {
					newFont.setColor(HSSFFont.COLOR_RED);
				}
				HSSFRichTextString text = new HSSFRichTextString(headers.get(
						i - 1).getSxzwm());
				text.applyFont(newFont);
				cell.setCellValue(text);
			}
		}
	}

	/**
	 * 插入excel正文
	 * 
	 * @param workbook
	 * @param sheet
	 * @param startIndex
	 * @param headers
	 * @param list
	 */
	@SuppressWarnings("rawtypes")
	public static void createContentRow(HSSFWorkbook workbook, HSSFSheet sheet,
			int startIndex, List<TsStsx> headers, List list) {
		HSSFCellStyle style2 = createContentStyle(workbook);
		// 插入具体内容
		for (int i = 0, j = startIndex + 1; i < list.size(); i++, j++) {
			JSONObject jsonObj = JSONObject.parseObject((String) list.get(i));
			HSSFRow row = sheet.createRow(j);
			row.setHeightInPoints(23);
			for (int columnIndex = 0; columnIndex <= headers.size(); columnIndex++) {
				HSSFCell cell = row.createCell(columnIndex);
				cell.setCellStyle(style2);
				if (0 == columnIndex) {
					cell.setCellValue(i + 1);
				} else {
					TsStsx tsStsx = headers.get(columnIndex - 1);
					String value = jsonObj.containsKey(tsStsx.getSx()) ? jsonObj
							.getString(tsStsx.getSx()) : "";
					if (value == "null")
						value = "";
					if (tsStsx.getSxzjlx().equals("combobox")
							|| tsStsx.getSxzjlx().equals("treecombobox")
							|| tsStsx.getSxzjlx().equals("tree")) {// 判断
																	// 当前字段是否为combobox,treecombobox,tree
						Map map = (Map) tsStsx.getBmsj();
						if (value == null || value.equals("")
								|| tsStsx.getBmsj() == null) {// 判断ID是否为空，所查的下拉数据是否为null
							value = "";
						} else {
							try {
								value = (String) map.get(Long.valueOf(value));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else if (tsStsx.getSxzjlx().equals("checkbox")) {
						if (value.equals("1")) {
							value = "是";
						} else {
							value = "否";
						}
					}
					try {
						/*
						 * if (value.length() < 12) { Long valNum =
						 * Long.valueOf(value); cell.setCellValue(valNum); }
						 * else {
						 */
						cell.setCellValue(value);
						// }

					} catch (NumberFormatException e) {
						cell.setCellValue(value);
						// e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 拼接excel标题
	 * 
	 * @param base
	 * @return
	 */
	private static String genTableTitle(String base) {
		Calendar c = Calendar.getInstance();

		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		String month_ = new String("" + month);
		if (month < 10) {
			month_ = "0" + month;
		}
		int day = c.get(Calendar.DAY_OF_MONTH);
		String day_ = new String("" + day);
		if (day < 10) {
			day_ = "0" + day;
		}
		int hour = c.get(Calendar.HOUR_OF_DAY);
		String hour_ = new String("" + hour);
		if (hour < 10) {
			hour_ = "0" + hour;
		}

		int minute = c.get(Calendar.MINUTE);
		String minute_ = new String("" + minute);
		if (minute < 10) {
			minute_ = "0" + minute;
		}

		String title = base + "  (" + year + "." + month_ + "." + day_ + "  "
				+ hour_ + ":" + minute_ + ") ";

		return title;
	}

	/**
	 * 创建一个Excel工作薄
	 * 
	 * @param title
	 * @param startRowIndex
	 * @param columns
	 * @param defaultColumnWidth
	 * @return
	 */
	public static HSSFWorkbook createExcelWorkbook(String title,
			Integer startRowIndex, Integer columns, Integer defaultColumnWidth) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);

		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(13);
		createBlankStyle(workbook);
		// createBlankRow(workbook, sheet, 3, columns, title);
		return workbook;
	}
	/**
	 * 取得工作簿对象。
	 * @param strs
	 * @param sheetName
	 * @param values
	 * @return
	 */
	public static HSSFWorkbook getHSSFWorkbook(String[] strs,String sheetName,List<Object> values){
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle headerStyle = ExportUtil.createContentStyle(workbook);
		HSSFCellStyle contentStyle = ExportUtil.createContentStyle(workbook);
		contentStyle.setWrapText(true);// 自动换行
		HSSFSheet sheet = workbook.createSheet(sheetName);
	
		// 创建表标题
		HSSFRow header = sheet.createRow(0);
		for (int i = 0; i < strs.length; i++) {
			HSSFCell cell = header.createCell(i+1);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(strs[i]);
		}
		sheet.setColumnWidth(0, 200);
		for(int i=1;i<=strs.length;i++){
			sheet.setColumnWidth(i, 5600);
		}

		int lineNumber = 1;
    	for(Object obj : values){
    		HSSFRow contentRow = sheet.createRow(lineNumber++);
    		contentRow.setHeight((short) 300);   // 设置行高
    		
    		/**/
    		Field[] fields = obj.getClass().getDeclaredFields();
    		for (int i = 0; i < fields.length; i++) {
    			Field field = fields[i];
    			HSSFCell cell = contentRow.createCell(i+1);
    			cell.setCellStyle(contentStyle);
    			// 反射调用返回该字段值的字符串形式。
    			String strValue = Utils4Service.getValueByField(obj, field);
    			cell.setCellValue(strValue);
    		}
    	}
    	return workbook;
	}
	
	/**
	 * 取得工作簿对象。
	 * @param strs
	 * @param sheetName
	 * @param values
	 * @return
	 */
	public static HSSFWorkbook getHSSFWorkbookByMap(String[] strs,String sheetName,List<Map<String,Object>> values){
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle headerStyle = ExportUtil.createContentStyle(workbook);
		HSSFCellStyle contentStyle = ExportUtil.createContentStyle(workbook);
		contentStyle.setWrapText(true);// 自动换行
		HSSFSheet sheet = workbook.createSheet(sheetName);
	
		// 创建表标题
		HSSFRow header = sheet.createRow(0);
		for (int i = 0; i < strs.length; i++) {
			HSSFCell cell = header.createCell(i);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(strs[i]);
		}
		sheet.setColumnWidth(0, 200);
		for(int i=0;i<strs.length;i++){
			sheet.setColumnWidth(i, 5600);
		}

		int lineNumber = 1;
    	for(Map<String,Object> map : values){
    		HSSFRow contentRow = sheet.createRow(lineNumber++);
    		contentRow.setHeight((short) 900);   // 设置行高
    		int i=0;
    		for (String key : map.keySet()) {
    			HSSFCell cell = contentRow.createCell(i);
    			cell.setCellStyle(contentStyle);
    			// 反射调用返回该字段值的字符串形式。
    			cell.setCellValue(MapUtils.getString(map, key));
    			i++;
    		}
    	}
    	return workbook;
	}
	
	/**
	 * 取得工作簿对象。
	 * @param strs
	 * @param sheetName
	 * @param values
	 * @return
	 */
	public static HSSFWorkbook getHSSFWorkbookByCodes(List<String> strs,List<String> codes,String sheetName,List<Map<String,Object>> values){
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle headerStyle = ExportUtil.createContentStyle(workbook);
		HSSFCellStyle contentStyle = ExportUtil.createContentStyle(workbook);
		contentStyle.setWrapText(true);// 自动换行
		HSSFSheet sheet = workbook.createSheet(sheetName);
	
		// 创建表标题
		HSSFRow header = sheet.createRow(0);
		for (int i = 0; i < strs.size(); i++) {
			HSSFCell cell = header.createCell(i);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(strs.get(i));
		}
		sheet.setColumnWidth(0, 200);
		for(int i=0;i<strs.size();i++){
			sheet.setColumnWidth(i, 5600);
		}

		int lineNumber = 1;
    	for(Map<String,Object> map : values){
    		HSSFRow contentRow = sheet.createRow(lineNumber++);
    		contentRow.setHeight((short) 900);   // 设置行高
    		int i=0;
    		for (int j = 0; j < codes.size(); j++) {
    			String key=codes.get(j);
    			HSSFCell cell = contentRow.createCell(i);
    			cell.setCellStyle(contentStyle);
    			// 反射调用返回该字段值的字符串形式。
    			cell.setCellValue(MapUtils.getString(map, key));
    			i++;
    		}
    	}
    	return workbook;
	}
	
	/**
	 * 导出方法
	 * 
	 * @param title
	 * @param headers
	 * @param list
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static HSSFWorkbook exportExcel(String title, List<TsStsx> headers,
			List list) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);

		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(13);
		/*
		 * for(int i=0;i<headers.length+1;i++){ sheet.autoSizeColumn(i); }
		 */

		String tableHeader = genTableTitle(title);

		int startIndex = createBlankRow(workbook, sheet, 3, headers.size() + 1,
				tableHeader);
		createHeaderRow(workbook, sheet, startIndex, headers);
		if (null != list) {
			createContentRow(workbook, sheet, startIndex, headers, list);
		}

		sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, headers.size()));
		sheet.autoSizeColumn(0);
		return workbook;

	}
}
