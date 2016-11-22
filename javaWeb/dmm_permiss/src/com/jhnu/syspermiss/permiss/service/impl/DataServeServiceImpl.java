package com.jhnu.syspermiss.permiss.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

import com.jhnu.syspermiss.permiss.dao.DataServeDao;
import com.jhnu.syspermiss.permiss.dao.impl.DataServeDaoImpl;
import com.jhnu.syspermiss.permiss.entity.DataServe;
import com.jhnu.syspermiss.permiss.service.DataServeService;
import com.jhnu.syspermiss.permiss.service.UserService;
import com.jhnu.syspermiss.school.service.impl.DeptPermissionServiceImpl;
import com.jhnu.syspermiss.util.StringUtils;
import com.jhnu.syspermiss.util.jdbcUtil.BaseDao;

public class DataServeServiceImpl implements DataServeService{
	private DataServeServiceImpl() {
		
	}  
    private static DataServeServiceImpl DataServeServiceImpl=null;
	
	public static DataServeServiceImpl getInstance() {
		if (DataServeServiceImpl == null){
			synchronized (new String()) {
				if (DataServeServiceImpl == null){
					DataServeServiceImpl = new DataServeServiceImpl();
				}
			}
		}
		return DataServeServiceImpl;
	}
	private DataServeDao dataServeDao= DataServeDaoImpl.getInstance();
	private UserService userService= UserServiceImpl.getInstance();
	
	@Override
	public List<DataServe> getDataServe(String username, String shiroTag) {
		return dataServeDao.getDataServe(username, shiroTag);
	}

	@Override
	public DataServe findDataServeById(Long id) {
		return dataServeDao.findById(id);
	}

	@Override
	public List<String> getDataServeSqlbyUserIdShrio(String username, String shiroTag) {
		return getSqlbyDataServe(getDataServe(username, shiroTag));
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public List<String> getSqlbyDataServe(List<DataServe> datas) {
		Object o;
		Method m;
		List<String> list=new ArrayList<String>();
		for(DataServe data:datas){
			String servicename=data.getServicename();
			String classBean=servicename.substring(0, servicename.indexOf("?") );
			String method=servicename.substring(servicename.indexOf("?")+1,servicename.length()  );
			o= DeptPermissionServiceImpl.getInstance();
			try {
				m=o.getClass().getMethod(method, DataServe.class);
				String rsql=(String) m.invoke(o, data);
				List<Map<String,Object>> l=BaseDao.getInstance().queryForList(rsql);
				for(int i=0;i<l.size();i++){
					int j=MapUtils.getIntValue(l.get(i), "LEVEL_");
					for(int k=list.size();k<=j;k++){
					list.add(k,"''");
					}
					String id="'"+l.get(i).get("ID")+"'";
					id=id.replaceAll(",", "','");
					if(list.get(j)=="''"){
						list.set(j, id);
					}else{
						String ids=id+","+list.get(j);
						list.set(j, ids);
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
		for(int i=list.size();i<5;i++){
			list.add(i,"''");
		}
		//String[] d=sql.toString().split(",");
		for(int i=0;i<list.size();i++){
			String a=array_unique(list.get(i).split(","));
			list.set(i, a);
		}
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
			List<String> list=getSqlbyDataServe(datas);
			return dataServeDao.isInClassForStudent(thisOneLoginName, list);
		}else if("teacher".equals(role)){
			List<String> list=getSqlbyDataServe(datas);
			return dataServeDao.isInDeptForTeacher(thisOneLoginName, list);
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

	@Override
	public List<String> getDataByDeptAndData(String type, String deptId,
			List<String> datas) {
		String qids="";
		for (int i = 0; i < datas.size(); i++) {
			if(!"''".equals(datas.get(i))){
				qids+=datas.get(i)+",";
			}
		}
		if(!StringUtils.hasText(qids)){
			qids="'',";
		}
		qids=qids.substring(0,qids.length()-1);
		List<Map<String,Object>> l=new ArrayList<Map<String,Object>>(); 
		List<String> list=new ArrayList<String>();
		if("dept".equals(type)){
			l=dataServeDao.getDeptDataByDeptAndData(deptId, qids);
		}else if("deptTeach".equals(type)){
			l=dataServeDao.getDeptTeachDataByDeptAndData(deptId, qids);
		}
			for(int i=0;i<l.size();i++){
				int j=MapUtils.getIntValue(l.get(i), "LEVEL_");
				for(int k=list.size();k<=j;k++){
				list.add(k,"''");
				}
				String id="'"+l.get(i).get("ID")+"'";
				id=id.replaceAll(",", "','");
				if(list.get(j)=="''"){
					list.set(j, id);
				}else{
					String ids=id+","+list.get(j);
					list.set(j, ids);
				}
			}
			for (int i = list.size(); i < 5; i++) {
				list.add("''");
			}
		return list;
	}
	

}
