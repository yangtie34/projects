package com.jhnu.system.permiss.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.framework.spring.ApplicationComponentStaticRetriever;
import com.jhnu.product.common.stu.service.StuService;
import com.jhnu.product.common.teacher.service.TeacherService;
import com.jhnu.system.permiss.dao.DataServeDao;
import com.jhnu.system.permiss.entity.DataServe;
import com.jhnu.system.permiss.service.DataServeService;
import com.jhnu.system.permiss.service.UserService;
import com.jhnu.util.common.StringUtils;

@Service("dataServeService")
public class DataServeServiceImpl implements DataServeService{
	
	@Autowired
	private DataServeDao dataServeDao;
	@Autowired
	private UserService userService;
	@Autowired
	private StuService stuService;
	@Autowired
	private TeacherService teacherService;
	
	@Override
	public List<DataServe> getDataServe(String username, String shiroTag) {
		String checkString="";
		while(shiroTag.indexOf(":")>=0){
			  shiroTag=shiroTag.substring(0, shiroTag.lastIndexOf(":"));
			  checkString+="'"+shiroTag+":*',";
		  } 
		checkString=checkString.substring(0,checkString.length()-1);
		return dataServeDao.getDataServe(username, checkString);
	}

	@Override
	public DataServe findDataServeById(Long id) {
		return dataServeDao.findById(id);
	}

	@Override
	public String getDataServeSqlbyUserIdShrio(String username, String shiroTag) {
		return getSqlbyDataServe(getDataServe(username, shiroTag));
	}
	
	@Override
	public String getSqlbyDataServe(List<DataServe> datas) {
		Object o;
		Method m;
		StringBuffer sql=new StringBuffer("");
		for(DataServe data:datas){
			String servicename=data.getServicename();
			String classBean=servicename.substring(0, servicename.indexOf("?") );
			String method=servicename.substring(servicename.indexOf("?")+1,servicename.length()  );
			o=ApplicationComponentStaticRetriever.getComponentByItsName(classBean);
			try {
				m=o.getClass().getMethod(method, DataServe.class);
				String rsql=(String) m.invoke(o, data);
				if(StringUtils.hasOtherText(rsql)){
					if("".equals(sql.toString())){
						sql.append(rsql);
					}else{
						sql.append(" union all "+rsql);
					}
				}
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
		}
		sql.insert(0, "select distinct id from ( ");
		sql.append(" )");
		return sql.toString();
	}

	@Override
	public boolean hasThisOnePerm(String thisOneLoginName, List<DataServe> datas) {
		String role=userService.getUserRootRole(thisOneLoginName);
		if("student".equals(role)){
			String sql=getSqlbyDataServe(datas);
			return stuService.isInClassForStudent(thisOneLoginName, sql);
		}else if("teacher".equals(role)){
			String sql=getSqlbyDataServe(datas);
			return teacherService.isInDeptForTeacher(thisOneLoginName, sql);
		}
		return false;
	}

	@Override
	public List<Map<String, Object>> findAll() {
		return dataServeDao.findAll();
	}

	@Override
	public boolean hasThisOnePerm(String thisOneLoginName, String username,
			String shiroTag) {
		return hasThisOnePerm(thisOneLoginName,getDataServe(username, shiroTag));
	}

}
