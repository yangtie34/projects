package com.jhnu.stu;

import java.util.List;
import java.util.Map;

public interface StudentDao1 {
	
	public List<Map<String, Object>> getSchoolName(String pid);
	public List<Map<String, Object>> getRs(String Id);
	public List<Map<String, Object>> getXl(String Id);
	public List<Map<String, Object>> getYxdName(String pid);
	public List<Map<String, Object>> getSyd(String Id);
	public List<Map<String, Object>> getNl(String Id);
	public List<Map<String, Object>> getRydb(String Id);
	public List<Map<String, Object>> getZzmm(String Id);
	public List<Map<String, Object>> getMz(String Id);
	public List<Map<String, Object>> getMzCount(String Id);
	public List<Map<String, Object>> getZzmm1(String Id);

}
