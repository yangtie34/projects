package cn.gilight.dmm.teaching.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVWriter;

public class CsvUtils {
	/**
	 * 输出csv文件
	 * @param filename .csv文件存储位置
	 * @param userlist 要存储的数据(数据格式为List<Map<String,Object>>)
	 * @throws IOException
	 */
	public static void createCsvFile(String filename,List<Map<String,Object>> userlist){
		CSVWriter writer;
		try {
			writer = new CSVWriter(new FileWriter(new File(filename)),',');
			List<String[]> alList=new ArrayList<String[]>();
			List<String> list=new ArrayList<String>();
			  list.add("stu_id");list.add("x1");list.add("y");
			  alList.add(list.toArray(new String[list.size()]));
			for(Map<String,Object> map:userlist){
				list=new ArrayList<String>();
				list.add((String) map.get("CL01"));
				list.add((String) map.get("CL02").toString());
				list.add((String) map.get("CL03").toString());
				alList.add(list.toArray(new String[list.size()]));
			}
			  writer.writeAll(alList);
			  writer.close();
			  System.out.println(writer.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 生成CSV文件
	 * @param filename CSV文件的位置保持存储的CSV文件的名称（例如：C:\test\test.csv)
	 * @param titles   CSV文件首行的列名
	 * @param userlist CSV文件的数据
	 */
	public static void createCsvFile(String filename,List<String> titles,List<Map<String,Object>> userlist){
		CSVWriter writer;
		try {
			writer = new CSVWriter(new FileWriter(new File(filename)),',');
			List<String[]> alList=new ArrayList<String[]>();
			  alList.add(titles.toArray(new String[titles.size()]));
			for(Map<String,Object> map:userlist){
				List<String> list = new ArrayList<String>();
				for(String title:titles){
				list.add((String) map.get(title));
				}
				alList.add(list.toArray(new String[list.size()]));
			}
			  writer.writeAll(alList);
			  writer.close();
			  System.out.println(writer.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 生成CSV文件（传入List<List<String>>）
	 * @param filename CSV文件的位置保持存储的CSV文件的名称（例如：C:\test\test.csv)
	 * @param titles   CSV文件首行的列名
	 * @param userlist CSV文件的数据
	 */
	public static void createCsvFileByList(String filename,List<String> titles,List<List<String>> userlist){
		CSVWriter writer;
		try {
			writer = new CSVWriter(new FileWriter(new File(filename)),',');
			List<String[]> alList=new ArrayList<String[]>();
			alList.add(titles.toArray(new String[titles.size()]));
			for(List<String> list:userlist){
				alList.add(list.toArray(new String[list.size()]));
			}
			writer.writeAll(alList);
			writer.close();
			System.out.println(writer.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 /**
	  * @param args
	  */

	 public static void main(String[] args) throws IOException {
			String filename="E:\\test\\test.csv";//+grades[2]+".csv";//
			List<String> titles=new ArrayList<>();
			List<List<String>> list1=new ArrayList<>();
			list1.add(titles);
			titles.add("studentid");titles.add("a");titles.add("b");
			List<Map<String,Object>> dataList=new ArrayList<>();
			for(int i=0;i<10;i++){
			Map<String,Object> map=new HashMap<>();
			map.put("studentid", i+"");
			map.put("a", i+"");
			map.put("b", i+"");
			dataList.add(map);
			}
//			String filename=System.getProperty("webapp.root").toString()+"static\\csv\\";//+grades[2]+".csv";//
//		 CsvUtils.createCsvFile(filename, titles, dataList);
		 CsvUtils.createCsvFileByList(filename, titles, list1);
//	  CSVWriter writer = new CSVWriter(new FileWriter(new File("./1.csv")),',');
//	  List<String[]> alList=new ArrayList<String[]>();
//	  List<String> list=new ArrayList<String>();
//	  list.add("aa");
//	  list.add("x1");
//	  list.add("y");
//	  alList.add(list.toArray(new String[list.size()]));
//	    
//	  list=new ArrayList<String>();
//	  list.add("dd");
//	  list.add("2");
//	  list.add("2");
//	  alList.add(list.toArray(new String[list.size()]));
//	  
//	  writer.writeAll(alList);
//	  writer.close();
//	  System.out.println(writer.toString());

	 }
}
