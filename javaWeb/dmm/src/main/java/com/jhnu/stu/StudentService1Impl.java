package com.jhnu.stu;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service("studentService1")
public class StudentService1Impl implements StudentService1 {
	@Autowired
	private StudentDao1 studentDao1;
	@Override
	public  List<Map<String, Object>> querySchoolName(String pid){
		return studentDao1.getSchoolName(pid);
	}
	@Override
	public  List<Map<String, Object>> queryYxdName(String pid){
		return studentDao1.getYxdName(pid);
	}
	@Override
	public  List<Map<String, Object>> queryXl(String pid){
		return studentDao1.getXl(pid);
		
	}
	@Override
	public  List<Map<String, Object>> queryRs(String pid){
		return studentDao1.getRs(pid);
	}
	@Override
	public  List<Map<String, Object>> querySyd(String pid){
		return studentDao1.getSyd(pid);
	}
	@Override
	public List<Map<String, Object>> queryNl(String pid){
		return studentDao1.getNl(pid);
	}
	@Override
	public List<Map<String, Object>> queryRydb(String pid){
		return studentDao1.getRydb(pid);
	}
	@Override
	public List<Map<String, Object>> queryZzmm(String pid){
		return studentDao1.getZzmm(pid);
	}
	@Override
	public List<Map<String, Object>> queryMz(String pid){
		return studentDao1.getMz(pid);
	}
	@Override
	public List<Map<String, Object>> queryMzCount(String pid){
		return studentDao1.getMzCount(pid);
	}
	public List<Map<String, Object>> queryZzmm1(String pid){
		return studentDao1.getZzmm1(pid);
	}
	
}
