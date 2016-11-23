package com.jhkj.mosdc.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class JxlUtil {
	
	public static WritableWorkbook createWorkbookFromTplFile(File tplFile,File goalFile) throws Throwable{
		FileInputStream fis=new FileInputStream(tplFile);
		Workbook wb = Workbook.getWorkbook(fis);		
		fis.close();
		return wb.createWorkbook(goalFile);
	}
	
	public static WritableWorkbook createWorkbookFromTplFile(File tplFile,OutputStream os) throws Throwable{
		FileInputStream fis=new FileInputStream(tplFile);
		Workbook wb = Workbook.getWorkbook(fis);		
		fis.close();
		WorkbookSettings ws;
		return wb.createWorkbook(os);
	}

	/**
	 * 从excel模板中，获取工作薄
	 * @param file
	 * @return
	 * @throws Throwable
	 */
	public static Workbook getWorkBookOfTplFile(File file) throws Throwable{
		FileInputStream fis=new FileInputStream(file);
		Workbook wb = Workbook.getWorkbook(fis);		
		fis.close();		
		return wb;
	}
	/**
	 * 生成工作薄的一个copy
	 * @param fileName
	 * @param in
	 * @return
	 * @throws Throwable
	 */
	public static WritableWorkbook cloneWorkbook(String fileName,Workbook in) throws Throwable{
		File f=new File(fileName);
		WritableWorkbook w=Workbook.createWorkbook(f, in);		
		return w;		
	}
	
	/**
	 * 将数据写入工作表格的合适位置
	 * @param data
	 * @param row
	 * @param col
	 * @param sheet
	 * @throws Throwable
	 */
	public static void writeDataToSheet(String data,int row,int col,WritableSheet sheet) throws Throwable{
		sheet.addCell(new Label(col ,  row ,  data ));
	}
	
	/**
	 * 新生成一个工作薄
	 * @param fileName
	 * @return
	 * @throws Throwable
	 */
	public static WritableWorkbook createWorkBook(String fileName) throws Throwable{
		File f=new File(fileName);
		WritableWorkbook w=Workbook.createWorkbook(f);
		return w;
	}

}
