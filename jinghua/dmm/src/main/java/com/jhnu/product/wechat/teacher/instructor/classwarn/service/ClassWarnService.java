package com.jhnu.product.wechat.teacher.instructor.classwarn.service;

import java.util.List;
import java.util.Map;

public interface ClassWarnService {
	
	/**
	 * 获取辅导员所管辖班级list的晚寝晚归人数
	 * @param teacherId 辅导员ID
	 * @param action_date 晚寝晚归日期
	 * @return
	 */
	public List<Map<String,Object>> getClassLateDormByTeacherId(String teacherId,String action_date);
	
}
