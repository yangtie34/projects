package com.jhkj.mosdc.sc.job.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.newoutput.util.MapUtils;
import com.jhkj.mosdc.sc.job.InitPathService;

public class InitPathServiceImpl implements InitPathService{

	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void saveInitPath(Map<String,Object> data) {
		String tableName=MapUtils.getString(data, "TableName");
		String tablePid=MapUtils.getString(data, "Pid")!=null?MapUtils.getString(data, "Pid"):"pid";
		String tableId=MapUtils.getString(data, "Id")!=null?MapUtils.getString(data, "Id"):"id";
		String tablePidStart=MapUtils.getString(data, "PidStart")!=null?MapUtils.getString(data, "PidStart"):"-1";
		int tablePathLength=MapUtils.getString(data, "PathLength")!=null?MapUtils.getIntValue(data, "PathLength"):5;
		
		String sql="select * from "+tableName+"  start with "+tablePid+"='"+tablePidStart+"' connect by prior "+tableId+"= "+tablePid;
		List<Map> list=baseDao.querySqlList(sql);
		
		List<Map<String,Object>> saveList=new ArrayList<Map<String,Object>>();
		List<String> levelCodeId=new ArrayList<String>();
		List<String> levelCodePath=new ArrayList<String>();
		Map<String, Object> saveMap=null;
		String pid="", path="";
		levelCodeId.add(tablePidStart);
		levelCodePath.add("");
		for(int i=0;i<list.size();i++){
			saveMap=new HashMap<String, Object>();
			String id=MapUtils.getString(list.get(i), "ID");
			String thisPid=MapUtils.getString(list.get(i), "PID");
			String thisPath=path+fillNum(tablePathLength,i+1);
			saveMap.put("id", id);
			saveMap.put("path", thisPath);
			saveList.add(saveMap);
			
			if(thisPid.equals(pid)){
				if(i+1<list.size()){
					String nextPid=MapUtils.getString(list.get(i+1), "PID");
					if(!thisPid.equals(nextPid)){
						int inList=isListIndexOf(nextPid,levelCodeId);
						if(inList>=0){
							path=levelCodePath.get(inList);
						}
					}
				}
			}else{
				levelCodeId.add(id);
				levelCodePath.add(thisPath);
				if(i+1<list.size()){
					String nextPid=MapUtils.getString(list.get(i+1), "PID");
					if(!thisPid.equals(nextPid)){
						int inList=isListIndexOf(nextPid,levelCodeId);
						if(inList>=0){
							path=levelCodePath.get(inList);
						}else{
							pid=id;
							path=thisPath;
						}
					}
				}
			}
		}
		updatePath(data,saveList);
	}
	
	//将数字类型转换成字符串并补全长度
	private String fillNum(int fillLength,int num){
		String numS=num+"";
		int numSLength=numS.length();
		for(int i=0;i<fillLength-numSLength;i++){
			numS="0"+numS;
		}
		return numS;
	}
	//得出字符串第一次出现在list中的下标
	private int isListIndexOf(String isInStr,List<String> lists){
		for (int i=0;i<lists.size();i++) {
			if(isInStr.equals(lists.get(i))){
				return i;
			}
		}
		return -1;
	}
	
	//批量更新数据
	private void updatePath(Map<String,Object> data,List<Map<String, Object>> list){
		String tableName=MapUtils.getString(data, "TableName");
		String tableId=MapUtils.getString(data, "Id")!=null?MapUtils.getString(data, "Id"):"id";
		String tablePath=MapUtils.getString(data, "Path")!=null?MapUtils.getString(data, "Path"):"path_";
		final int COUNT = list.size();
		final List<Map<String,Object>> LIST=list;
		baseDao.getJdbcTemplate().batchUpdate(
				"update "+tableName+" set "+tablePath+"=? where "+tableId+"=? ",
				new BatchPreparedStatementSetter() {    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
		                ps.setString(1, MapUtils.getString(LIST.get(i),"path"));    
		                ps.setString(2, MapUtils.getString(LIST.get(i),"id"));    
		              }    
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 }); 
	}
	
}
