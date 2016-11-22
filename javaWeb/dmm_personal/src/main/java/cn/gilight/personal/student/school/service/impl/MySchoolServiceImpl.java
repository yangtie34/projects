package cn.gilight.personal.student.school.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.test1.util.MapUtils;
import cn.gilight.framework.uitl.SysConfig;
import cn.gilight.personal.student.school.dao.MySchoolDao;
import cn.gilight.personal.student.school.service.MySchoolService;

@Service("mySchoolService")
public class MySchoolServiceImpl implements MySchoolService{
	
	@Autowired
	private MySchoolDao mySchoolDao;

	@Override
	public Map<String, Object> getCounts() {
		Map<String,Object> map =  new HashMap<String,Object>();
		
		Map<String,Object> teachingbuilding = mySchoolDao.getTeachingBuildingCounts();
		if(MapUtils.getIntValue(teachingbuilding, "teachingbuilding") == 0){
			teachingbuilding.remove("teachingbuilding");
			teachingbuilding.put("teachingbuilding", "未维护");
		}
		map.putAll(teachingbuilding);
		
		Map<String,Object> dormitorybuilding = mySchoolDao.getDormitoryBuildingCounts();
		if(MapUtils.getIntValue(dormitorybuilding, "dormitorybuilding") == 0){
			dormitorybuilding.remove("dormitorybuilding");
			dormitorybuilding.put("dormitorybuilding", "未维护");
		}
		map.putAll(dormitorybuilding);
		
		Map<String,Object> restaurant = mySchoolDao.getRestaurantCounts();
		if(MapUtils.getIntValue(restaurant, "restaurant") == 0){
			restaurant.remove("restaurant");
			restaurant.put("restaurant", "未维护");
		}
		map.putAll(restaurant);
		
		Map<String,Object> shop = mySchoolDao.getShopCounts();
		if(MapUtils.getIntValue(shop, "shop") == 0){
			shop.remove("shop");
			shop.put("shop", "未维护");
		}
		map.putAll(shop);
		
		Map<String,Object> book = mySchoolDao.getBookCounts();
		if(MapUtils.getIntValue(book, "book") == 0){
			book.remove("book");
			book.put("book", "未维护");
		}
		map.putAll(book);
		
		Map<String,Object> teacher = mySchoolDao.getTeacherCounts();
		if(MapUtils.getIntValue(teacher, "teacher") == 0){
			teacher.remove("teacher");
			teacher.put("teacher", "未维护");
		}
		map.putAll(teacher);
		return map;
		
	}

	@Override
	public Map<String, Object> getPeopleCounts() {
		Map<String,Object> map =  new HashMap<String,Object>();
		Map<String,Object> stuMap = mySchoolDao.getStudntCounts();
		int stuCounts = MapUtils.getIntValue(stuMap, "student");
		map.put("stuCounts", stuCounts);
		return map;
	}

	@Override
	public Map<String, Object> getSchool() {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("schoolName", SysConfig.instance().getSchoolName());
		result.put("schoolYear", SysConfig.instance().getSchoolYear());
		result.put("school_motto", SysConfig.instance().getSchool_motto());
		result.put("school_img", SysConfig.instance().getSchool_img());
		result.put("school_baike_url", SysConfig.instance().getSchool_baike_url());
		return result;
	}

}
