package com.jhnu.product.manager.scientific.excuteUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;


public class ExcelUtil {
	/**
     * 读取xls文件内容
	 * @throws Exception 
     */
    public static List<Map> readXls(MultipartFile file) throws Exception {
    	InputStream is = file.getInputStream();
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
            //循环标题第一行
            HSSFRow hssfRow0 = hssfSheet.getRow(0);
            int i=0;
            String keys="";
           while(hssfRow0.getCell(i)!=null){
        	   keys+=hssfRow0.getCell(i)+",";
        	   i++;
           }
           String key[]=keys.split(",");
            // 循环数据行Row
           List<List> list=new LinkedList();
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                List l=new LinkedList();
                 for(int k=0;k<key.length;k++){
             	   l.add(hssfRow.getCell(k)+"");
                }
                list.add(l);
            }
           
            List listall=new LinkedList();
            for(int j=0;j<list.size();j++){
            	Map map=new LinkedHashMap();
            	for(int k=0;k<key.length;k++){
            		map.put(key[k], list.get(j).get(k));
            	}
            	listall.add(map);
            }
            is.close(); 
        return listall;
    }
    /**
     * 
     */
    public static HSSFWorkbook getXls(String titles,String sheetName)  {
    	String title[]=titles.split(",");
     	//第一步创建一个工作薄对象 workbook ,对应一个Excel文件
     	HSSFWorkbook workBook = new HSSFWorkbook();
     	//第二步，在workBook中添 添加一个sheet 对应的Excel 文件中的sheet
     	HSSFSheet sheet = workBook.createSheet(sheetName);
     	//第三步，在sheet中添加表头 第 0 行（从 0 开始的），老版本的poi 对Excel的行数列数有限制 short
     	HSSFRow row = sheet.createRow(0);
     	//第四步，创建单元格，并设置表头居中
     	HSSFCellStyle style =  workBook.createCellStyle();
     	HSSFDataFormat format = workBook.createDataFormat(); 
     	style.setDataFormat(format.getFormat("@"));   
     	style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
     	HSSFCell cell = null;
    	 for(int i=0;i<title.length;i++){
    		cell = row.createCell(i);
 			cell.setCellValue(title[i]);
 			cell.setCellStyle(style);
 			cell.setCellType(HSSFCell.CELL_TYPE_STRING);   
 			
     	}
		return workBook;
    	
    }
}
