package cn.gilight.dmm.business.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import cn.gilight.framework.uitl.common.DateUtils;

/**
 * 
 * 
 * @author xuebl
 * @date 2017年1月19日 下午5:39:25
 */
public class WriteLog {


	/**
	 * 写入日志
	 * @param filePath	文件路径
	 * @param data	数据
	 * @param text  模块信息  例：编学号
	 */
	public static void log(String filePath, List<Object> data, String text){

		File tempFile = new File(filePath);
		if(!tempFile.exists()){
			try {
				tempFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		BufferedOutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(tempFile));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
    
    	try {
    		out.write(("\r\n\r\n----Log----"+text+"----"+DateUtils.getNowDate2()+"----\r\n").getBytes());
			for(int i=0,len=data.size(); i<len; i++){
				out.write((data.get(i).toString()+"\r\n").getBytes());
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			System.out.println(text+"写入日志出错！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 写入日志
	 * @param filePath	文件路径
	 * @param data	数据
	 * @param text  模块信息  例：编学号
	 * @param add   是否追加   true:追加
	 */
	public static void log(String filePath, List<Object> data, String text, boolean add){
		try {
			File file = new File(filePath);
			if(!file.exists()){
				file.createNewFile();
			}
			FileWriter writer = new FileWriter(file, add);
			writer.write("\r\n\r\n----Log----"+text+"----"+DateUtils.getNowDate2()+"----\r\n");
			for(int i=0,len=data.size(); i<len; i++){
				writer.write(data.get(i)+"\r\n");
			}
			writer.flush();
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}    
		
	};
	
}
