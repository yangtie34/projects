package com.jhkj.mosdc.sc.service;

import java.util.Map;


/**
 * 不在校学生名单service
 * @author Administrator
 *
 */
public interface LeaveSchoolService {
	
	public void saveStudent(Map<String,Object> map);
	
	public boolean haveCode(String code);

	public String queryGridContent(String params);
	
	public void deleteStudent(String id);
	
}
