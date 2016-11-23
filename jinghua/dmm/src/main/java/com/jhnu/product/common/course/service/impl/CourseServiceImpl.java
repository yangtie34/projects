package com.jhnu.product.common.course.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhnu.product.common.course.dao.CourseDao;
import com.jhnu.product.common.course.service.CourseService;
import com.jhnu.util.common.MapUtils;

@Service("courseService")
public class CourseServiceImpl implements CourseService{

	@Autowired
	private CourseDao Coursedao;
	
	@Override
	@Transactional
	public void initCourseWeekJob() {
		List<Map<String,Object>> list=Coursedao.getCourseWeek();
		List<Map<String,Object>> zcList=new ArrayList<Map<String,Object>>();
		for (int i = 0; i < list.size(); i++) {
			Map<String,Object> map=new HashMap<String, Object>();
			String zcs= getWeek(MapUtils.getString(list.get(i), "WEEK_START_END"));
			map.put("WEEKS", zcs);
			map.put("COURSE_ARRANGEMENT_ID", MapUtils.getString(list.get(i), "ID"));
			zcList.add(map);
		}
		Coursedao.updateCourseWeek(zcList);
	}

	private String getWeek(String week_start_end){
		String weeks[]=week_start_end.split(",");
		String zcs=",";
		for(String week : weeks){
			if(week.contains("-")){
				String[] a = week.split("-");
				int start = Integer.parseInt(a[0]);
				int end =Integer.parseInt( a[1]);
				for(int i=start;i<=end;i++){
					zcs+=i+",";
				}
			}else{
				zcs+=week+",";
			}
		}
		return zcs;
	}
}
