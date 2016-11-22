package cn.gilight.framework.uitl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**   
* @Description: 导出工具类
* @author Sunwg
* @date 2016年8月29日 下午5:53:47   
*/
public class ExportUtil {
	/** 
	* @Description: 导出excel
	* @param request 
	* @param response
	* @param fileName 文件名字
	* @param heads 标题集合
	* @param fields 字段集合
	* @param resultList 数据集
	*/
	public static void downloadExcel(HttpServletRequest request,HttpServletResponse response,String fileName,List<String> heads,List<String> fields,List<Map<String, Object>> resultList){
		  response.reset();
		  response.setContentType("application/octet-stream");
		  try {
			response.setHeader("Content-Disposition","attachment; filename="+URLEncoder.encode(fileName+".xls","utf-8"));
		    ByteArrayOutputStream stream = new ByteArrayOutputStream();
			createExcel(heads,fields,resultList).write(stream);
			response.setContentLength(stream.size());
			stream.writeTo(response.getOutputStream());
			stream.close();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** 
	* @Description: 创建excel
	* @param titles 标题
	* @param fields 字段
	* @param resultList 数据集
	* @return: HSSFWorkbook
	*/
	public static HSSFWorkbook createExcel(List<String> titles,List<String> fields,List<Map<String, Object>> resultList){
		HSSFWorkbook wb = new HSSFWorkbook();
		  
	    // 创建Excel的工作sheet,对应到一个excel文档的tab  
	    HSSFSheet sheet = wb.createSheet("sheet1");  
	    // 创建单元格样式  
	    HSSFCellStyle style = wb.createCellStyle();  
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
	    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
	    style.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);  
	    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
	    // 设置边框  
	    style.setBottomBorderColor(HSSFColor.GREY_40_PERCENT.index);
	    style.setRightBorderColor(HSSFColor.GREY_40_PERCENT.index);
	    style.setLeftBorderColor(HSSFColor.GREY_40_PERCENT.index);
	    style.setTopBorderColor(HSSFColor.GREY_40_PERCENT.index);
	    style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
	    style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
	    style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
	    style.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    
	    // 创建字体样式  
	    HSSFFont font = wb.createFont();  
	    font.setFontName("Verdana");  
	    font.setBoldweight((short) 8000);  
	    font.setFontHeight((short) 250);
	    font.setColor(HSSFColor.DARK_GREEN.index);
	    // 设置字体  
	    style.setFont(font);
	  
	    // 创建Excel的sheet的一行  
	    HSSFRow row = sheet.createRow(0);  
	    row.setHeight((short) 400);// 设定行的高度  
	    HSSFCell cell = null;
	    int[] width = new int[titles.size()];
	    for (int i = 0; i < titles.size(); i++) {
	    	// 创建一个Excel的单元格  
			cell = row.createCell(i);  
			// 给Excel的单元格设置样式和赋值  
			cell.setCellStyle(style);  
			cell.setCellValue(titles.get(i)); 
			width[i] = 2*titles.get(i).length();
		}
	    HSSFFont font1 = wb.createFont();  
	    font1.setFontName("Verdana");  
	    font1.setBoldweight((short) 100);  
	    font1.setFontHeight((short) 210);  
	    font1.setColor(HSSFColor.GREY_80_PERCENT.index);  
	    // 设置单元格内容格式  
	    HSSFCellStyle style1 = wb.createCellStyle();
	    style1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
	    style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
	    // 设置边框  
	    style1.setBottomBorderColor(HSSFColor.GREY_40_PERCENT.index);
	    style1.setRightBorderColor(HSSFColor.GREY_40_PERCENT.index);
	    style1.setLeftBorderColor(HSSFColor.GREY_40_PERCENT.index);
	    style1.setTopBorderColor(HSSFColor.GREY_40_PERCENT.index);
	    style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
	    style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
	    style1.setBorderRight(HSSFCellStyle.BORDER_THIN);  
	    style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
	    style1.setFont(font1);

	    for (int i = 0; i < resultList.size(); i++) {
	    	row = sheet.createRow(i+1);
	    	row.setHeight((short) 300);
	    	Map<String, Object> item = resultList.get(i);
	    	for (int j = 0; j < fields.size(); j++) {
	    		cell = row.createCell(j);
				// 给Excel的单元格设置样式和赋值  
				cell.setCellStyle(style1); 
				String value = (String)item.get(fields.get(j));
				cell.setCellValue(value);
				if (value != null && value.length() > width[j]) {
					 width[j] = value.length();
				}
			}
	    }
	    for (int i = 0; i < fields.size(); i++) {
	    	sheet.setColumnWidth(i, 2*256*(width[i] > 50 ? 50 : width[i]));
		}
	    return wb;
	}
	
	/** 
	* @Description: 下载word
	* @param request
	* @param response
	* @param fileName
	* @param content 
	*/
	public static void downloadWord(HttpServletRequest request,HttpServletResponse response,String fileName,String content){
		  response.reset();
		  response.setContentType("application/octet-stream");
		  try {
			response.setHeader("Content-Disposition","attachment; filename="+URLEncoder.encode(fileName+".doc","utf-8"));
		    ByteArrayOutputStream stream = new ByteArrayOutputStream();
			createWordFile(fileName, content).writeFilesystem(stream);
			response.setContentLength(stream.size());
			stream.writeTo(response.getOutputStream());
			stream.close();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** 
	* @Description: 创建word文档
	* @return: POIFSFileSystem
	* @param title
	* @param content
	* @return
	* @throws IOException 
	*/
	public static POIFSFileSystem createWordFile(String title,String content) throws IOException{
		ByteArrayInputStream bais = null;
		POIFSFileSystem poifs = null;
      try {     
          //html拼接出word内容 ,拼接注意加上<html>  
			content = "<html><div style=\"text-align: center\">" +
					"<span style=\"font-size: 24px;font-family: 黑体;font-weight:bold;\">"
					+ title + "</span></div><br />" + content;
			content += "</html>";
			byte b[] = content.getBytes();
			bais = new ByteArrayInputStream(b);
			poifs = new POIFSFileSystem();
			DirectoryEntry directory = poifs.getRoot();
			@SuppressWarnings("unused")
			DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bais != null)
				bais.close();
      }
      return poifs;
	}
}
