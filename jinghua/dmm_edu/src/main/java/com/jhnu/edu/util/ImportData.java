package com.jhnu.edu.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ImportData {
	public Map getImportData(String file) {
		int daima = 0;
		int start = 3;
		Map map=new HashMap();
		ArrayList listMap = new ArrayList();
		ArrayList listdata = new ArrayList();
		CSVFileUtil csvf = new CSVFileUtil(file);

		String line = "";
		int lineNum = 0;
		while ((line!=null && lineNum > start) || lineNum <= start) {

			line = csvf.readLine();
			if (lineNum == daima) {
				listMap = csvf.fromCSVLinetoArray(line);
			}
			if (lineNum >= start) {
				listdata.add(csvf.fromCSVLinetoArray(line));
			}
			lineNum++;
		}
		map.put("key", listMap);
		map.put("data", listdata);
		return map;
	}
}
