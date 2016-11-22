package com.jhnu.product.wechat.parent.check.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhnu.product.wechat.parent.check.dao.WechatCheckDao;
import com.jhnu.product.wechat.parent.check.service.WechatCheckService;

@Service("checkService")
public class WechatCheckServiceImpl implements WechatCheckService{
	@Autowired
	private WechatCheckDao checkDao;
	
	@Override
	public List<Map<String, Object>> getCheckLog(String stuId) {
		return checkDao.getCheckLog(stuId);
	}

	@Override
	@Transactional
	public void saveCheckLog() {
		List<Map<String,Object>> checkList = checkDao.getCheck();
		List<Map<String,Object>> classNumber = checkDao.getClasses();
		List<Map<String,Object>> classes = new ArrayList<Map<String,Object>>();
		if(classNumber != null){
			for(Map<String,Object> map : classNumber){
				String week_start_end = (String) map.get("WEEK_START_END");
				if(week_start_end != null){
					int sums = getSums(week_start_end);
					map.remove("WEEK_START_END");
					map.put("CLASS_NUMBER", sums);
				}else{
					map.remove("WEEK_START_END");
					map.put("CLASS_NUMBER", 0);
				}
			}
			int n = 0;
			for(int i = 0;i<classNumber.size();i++){
				if(i==0){
					classes.add(classNumber.get(0));
					n += 1;
				}else{
					if(classNumber.get(i).get("SCHOOL_YEAR").equals(classes.get(n-1).get("SCHOOL_YEAR")) && classNumber.get(i).get("TERM_CODE").equals(classes.get(n-1).get("TERM_CODE")) && classNumber.get(i).get("STU_ID").equals(classes.get(n-1).get("STU_ID"))){
						int c = (int) classNumber.get(i).get("CLASS_NUMBER") + (int) classes.get(n-1).get("CLASS_NUMBER");
						Map<String,Object> map = classes.get(n-1);
						map.remove("CLASS_NUMBER");
						map.put("CLASS_NUMBER", c);
					}else{
						classes.add(classNumber.get(i));
						n += 1;
					} 
				}
			}
		}
		
		for(Map<String,Object> map : classes){
			for(Map<String,Object> m : checkList){
				if(map.get("STU_ID").equals(m.get("STU_ID")) && map.get("SCHOOL_YEAR").equals(m.get("SCHOOL_YEAR"))&& map.get("TERM_CODE").equals(m.get("TERM_CODE"))){
					map.put("CLASS_LEAVE", m.get("CLASS_LEAVE"));
					map.put("CUT_CLASS", m.get("CUT_CLASS"));
					map.put("CLASS_EARLY", m.get("CLASS_EARLY"));
					map.put("CLASS_LATE", m.get("CLASS_LATE"));
				}
			}
			
		}
		
		checkDao.saveCheckLog(classes);
	}

	private int getSums(String week_start_end) {
		String[] strs = week_start_end.split(",");
		int sums = 0;
		for(String str : strs){
			if(str.contains("-")){
				String[] s = str.split("-");
				String start = s[0];
				String end = s[1];
				sums += (Integer.parseInt(end) -Integer.parseInt(start)+1);
			}else{
				sums += 1;
			}
		}
		return sums*2;
	}

	@Override
	@Transactional
	public void saveStuCutClassLog() {
		List<Map<String,Object>> lateList = checkDao.getLateClassCourse();
		List<Map<String,Object>> cutList = checkDao.getCutClassCourse();
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		if(lateList.size()>0){
			for(Map<String,Object> map : lateList){
				if(resultList.size()>0){
					Map<String,Object> myMap = map;
					for(Map<String,Object> m : resultList){
						if(map.get("stu_id").equals(m.get("stu_id")) && map.get("school_year").equals(m.get("school_year")) && map.get("term_code").equals(m.get("term_code"))){
							myMap = m;
						}
					}
					if(!resultList.contains(myMap)){
						resultList.add(myMap);
					}
				}else{
					resultList.add(map);
				}
			}
		}
		if(cutList.size()>0){
			if(resultList.size()>0){
				for(Map<String,Object> map : cutList){
					Map<String,Object> myMap = null;
					for(Map<String,Object> m : resultList){
						if(map.get("stu_id").equals(m.get("stu_id")) && map.get("school_year").equals(m.get("school_year")) && map.get("term_code").equals(m.get("term_code"))){
							myMap = m;
						}
					}
					if(resultList.contains(myMap)){
						if(!myMap.containsKey("often_cut_class")){
							resultList.remove(myMap);
							myMap.put("often_cut_class", map.get("often_cut_class"));
							resultList.add(myMap);
						}
					}else{
						resultList.add(map);
					}
				}
			}else{
				for(Map<String,Object> map : cutList){
					resultList.add(map);
				}
			}
		}
		checkDao.saveStuCutClassLog(resultList);
	}

	@Override
	public List<Map<String, Object>> getStuCutClassLog(String stuId) {
		return checkDao.getStuCutClassLog(stuId);
	}

}
