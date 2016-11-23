package com.jhnu.edu.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.edu.dao.SchoolListDao;
import com.jhnu.edu.entity.TEDUCODE;
import com.jhnu.edu.entity.TEDUSCHOOLS;
import com.jhnu.edu.service.SchoolListService;
import com.jhnu.edu.util.DataCovert;
import com.jhnu.framework.page.Page;


@Repository("schoolListService")
public class SchoolListServiceImpl implements SchoolListService {

	@Autowired
	private SchoolListDao schoolListDao;

	@Override
	public List<Map<String, Object>> getFilter() {
		
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		Map<String, Object> filterMap=new HashMap<String, Object>();
		Map<String, String> covMap=new HashMap<String, String>();
		//办学性质 t_edu_bb
		filterMap.clear();covMap.clear();
		covMap.put("code", "id");
		covMap.put("name", "mc");
		covMap.put("codeType", "codeType");
		List<Map<String, Object>> bxxz=DataCovert.coverList(schoolListDao.getFilter("t_edu_bb"), new TEDUCODE(), covMap);
		filterMap.put("queryName", "办学性质");
		filterMap.put("queryCode", "comboList");
		filterMap.put("queryType", "comboList");
		filterMap.put("items", bxxz);
		list.add(filterMap);
		//院校特色
		return list;
	}

	@Override
	public Page getSchools(Map<String, Object> mapFilter,int currentPage, int numPerPage){
		Page page=schoolListDao.getSchools(mapFilter, currentPage, numPerPage);
		page.setResultList(DataCovert.coverList(page.getResultList(),new TEDUSCHOOLS()));
		return page;
	}
}