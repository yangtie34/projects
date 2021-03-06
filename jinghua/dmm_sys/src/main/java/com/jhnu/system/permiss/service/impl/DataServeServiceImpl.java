package com.jhnu.system.permiss.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.framework.data.base.BaseDao;
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
	private BaseDao baseDao;
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
		return dataServeDao.getDataServe(username, shiroTag);
		}

	@Override
	public DataServe findDataServeById(Long id) {
		return dataServeDao.findById(id);
	}

	@Override
	public List getDataServeSqlbyUserIdShrio(String username, String shiroTag) {
		return getSqlbyDataServe(getDataServe(username, shiroTag));
	}	
	
	@Override
	public List getSqlbyDataServe(List<DataServe> datas) {
		Object o;
		Method m;
		StringBuffer sql=new StringBuffer("");
		List<String> list=new ArrayList();
		for(DataServe data:datas){
			String servicename=data.getServicename();
			String classBean=servicename.substring(0, servicename.indexOf("?") );
			String method=servicename.substring(servicename.indexOf("?")+1,servicename.length()  );
			o=ApplicationComponentStaticRetriever.getComponentByItsName(classBean);
			try {
				m=o.getClass().getMethod(method, DataServe.class);
				String rsql=(String) m.invoke(o, data);
				List<Map<String,Object>> l=baseDao.getBaseDao().getJdbcTemplate().queryForList(rsql);
				for(int i=0;i<l.size();i++){
					int j=MapUtils.getIntValue(l.get(i), "LEVEL_");
					for(int k=list.size();k<=j;k++){
					list.add(k,"''");
					}
					String id="'"+l.get(i).get("ID")+"'";
					id=id.replaceAll(",", "','");
					if(list.get(j)=="''"){
						list.add(j, id);
					}else{
						String ids=id+","+list.get(j);
						list.add(j, ids);
					}
				}
				/*if(StringUtils.hasLength(rsql)){
					if("".equals(sql.toString())){
						sql.append(rsql);
					}else{
						sql.append(","+rsql);
					}
				}*/
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
		for(int i=list.size();i<4;i++){
			list.add(i,"''");
		}
		//String[] d=sql.toString().split(",");
		return list;
	}
	//去除数组中重复的记录
	public String array_unique(String[] a) {
	    // array_unique
	    List<String> list = new LinkedList<String>();
	    for(int i = 0; i < a.length; i++) {
	        if(!list.contains(a[i])) {
	            list.add(a[i]);
	        }
	    }
	    String a1=list.get(0);
	    for(int i = 1; i < list.size(); i++){
	    	a1+=","+list.get(i);
	    }
	    return a1;
	}


	@Override
	public boolean hasThisOnePerm(String thisOneLoginName, List<DataServe> datas) {
		String role=userService.getUserRootRole(thisOneLoginName);
		if("student".equals(role)){
			List list=getSqlbyDataServe(datas);
			return stuService.isInClassForStudent(thisOneLoginName, list);
		}else if("teacher".equals(role)){
			List list=getSqlbyDataServe(datas);
			return teacherService.isInDeptForTeacher(thisOneLoginName, list);
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
