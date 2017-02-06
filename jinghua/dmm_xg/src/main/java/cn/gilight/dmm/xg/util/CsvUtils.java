package cn.gilight.dmm.xg.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
	  * @param args
	  */

	 public static void main(String[] args) throws IOException {
	  CSVWriter writer = new CSVWriter(new FileWriter(new File("./1.csv")),',');
	  List<String[]> alList=new ArrayList<String[]>();
	  List<String> list=new ArrayList<String>();
	  list.add("aa");
	  list.add("x1");
	  list.add("y");
	  alList.add(list.toArray(new String[list.size()]));
	    
	  list=new ArrayList<String>();
	  list.add("dd");
	  list.add("2");
	  list.add("2");
	  alList.add(list.toArray(new String[list.size()]));
	  
	  writer.writeAll(alList);
	  writer.close();
	  System.out.println(writer.toString());

	 }
}
