package com.jhnu.product.manager.scientific.dao.impl;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.product.manager.scientific.dao.ScientificInputDao;
import com.jhnu.product.manager.scientific.entity.APage;
import com.jhnu.product.manager.scientific.entity.Dept;
import com.jhnu.product.manager.scientific.entity.FindList;
import com.jhnu.product.manager.scientific.entity.TCodeSubject;
import com.jhnu.product.manager.scientific.entity.TResKylrTemp;
import com.jhnu.product.manager.scientific.excuteUtil.Excute;
/**
 * @title 科研录入Dao实现类
 * @description 科研信息录入
 * @author Administrator
 * @date 2015/10/13 18:12
 */

@Repository("scientificInputDao")
public class ScientificInputDaoImpl implements ScientificInputDao {
	@Autowired
	private Excute excute;
	@Override
	public List<Dept> getDept() {
	///	System.out.println(baseDao.hashCode());
		String sql=" select code_ code,name_ name from t_code_dept_teach t where t.pid=0";
		return excute.query(sql, new Dept());
	}

	@Override
	public List<TCodeSubject> getXKML() {
		String sql=" select * from T_CODE_SUBJECT ";
		return excute.query(sql, new TCodeSubject());
	}

	@Override
	public List<FindList> getkyId(String name, String cls) {
		String[] kyTable=KyArray.kyTable;
		String[] kyTableTitle=KyArray.kyTableTitle;
		String nameClumn=null;
		String clumn=null;
		for(int i=0;i<kyTable.length;i++){
			if(cls.equalsIgnoreCase(kyTable[i])){
				nameClumn=kyTableTitle[i];
				break;
			}
		}
		  Class cla = null;
		try {
			cla = Class.forName("com.jhnu.product.manager.scientific.entity."+cls);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  //获取表名
		  Table table=(Table) cla.getAnnotation(Table.class);
		   String tablename=table.name();
		  //获取字段名及方法
		  Method[] methods=cla.getMethods();
		  for(int i=0;i<methods.length;i++){
			  String methodname=methods[i].getName();
			  if(methodname.substring(3, methodname.length()).equalsIgnoreCase(nameClumn)&&methodname.substring(0, 3).equalsIgnoreCase("get")){
				  clumn=methods[i].getAnnotation(Column.class).name();  
				  break;
			  }
		  }
		String sql=" select id,"+clumn+" name from "+tablename+" where "+clumn+" like '%"+name+ "%'";
		return excute.query(sql, new FindList());
	}
	@Override
	public List<FindList> getAuthorInfo(String name, String lb) {
		String sql=null;
		
		switch(lb){
		case "学生":
			 sql=" select no_ id,name_ name from t_stu where name_ like '"+name+ "'";
			break;
		case "教师":
			sql=" select tea_no id,name_ name from t_tea where name_ like '%"+name+ "%'";
			break;
		}
		return excute.query(sql, new FindList());
	}
	@Override
	public String getTheName(String id, String lb) {
		String sql=null;
		
		switch(lb){
		case "学生":
			 sql=" select name_ name from t_stu where no_ ='"+id+ "'";
			break;
		case "教师":
			 sql=" select name_ name from t_tea where tea_no = '"+id+ "'";
			break;
		case "项目":
			sql=" select name_ name from T_RES_PROJECT where id= '"+id+ "'";
			break;
		}
		List<Map<String, Object>> results = excute.getBaseDao().getBaseDao().getJdbcTemplate().queryForList(sql);
		String str="";
		if(results.size()>0&&results.get(0).get("NAME")!=null) 
			str=results.get(0).get("NAME").toString();
		return str;
	}
	@Override
	public boolean insert(List list) {
		return excute.insert(list);
	}
	@Override
	public boolean delete(List list) {
		return excute.delete(list);
	}
	@Override
	public boolean update(List list) {
		return excute.update(list);
	}

	@Override
	public List getUnaudited(String ids,Object ky) {
		if(ids.equalsIgnoreCase("")){
			ids="''";
		}
		String tableName=ky.getClass().getAnnotation(Table.class).name();
		String sql=" select * from "+tableName+" where id in("+ids+ ")";
		return excute.query(sql,ky);
	}

	@Override
	public List getAuthorInfo(String kyid,Object kyzz) {
		String tableName=kyzz.getClass().getAnnotation(Table.class).name();
		String kyidClounm=kyzz.getClass().getDeclaredFields()[1].getName();
		try {
			kyidClounm=kyzz.getClass().getDeclaredMethod(upfirst(kyidClounm)).getAnnotation(Column.class).name();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sql=" select * from "+tableName+" where "+kyidClounm+ "='"+kyid+"'";
		return excute.query(sql,kyzz);
	}
	public List getAuthorInfo(String[] kyid,Object kyzz) {
		String tableName=kyzz.getClass().getAnnotation(Table.class).name();
		String kyidClounm=kyzz.getClass().getDeclaredFields()[1].getName();
		try {
			kyidClounm=kyzz.getClass().getDeclaredMethod(upfirst(kyidClounm)).getAnnotation(Column.class).name();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String kyids=Arrays.toString(kyid);
		kyids=kyids.replace(", ", ",");
		kyids=kyids.replace("[", "('");
		kyids=kyids.replace("]", "')");
		kyids=kyids.replace(",", "','");
		String sql=" select * from "+tableName+" where "+kyidClounm+ " in "+kyids;
		return excute.query(sql,kyzz);
	}
	public String upfirst(String str){
		return "get"+str.substring(0, 1).toUpperCase()+str.substring(1,str.length());
	}
	@Override//根据用户id或部门id获取录入科研信息
	public APage getKyInfo(APage apage,Object ky,TResKylrTemp temp,Map authMap) {
		String lb=getTempKyCode(ky);
		String idwhere="";
		/*if(temp.getDeptId()==null){
			idwhere=" and PEOPLE_ID='"+temp.getPeopleId()+"' ";
		}else{
			idwhere=" and DEPT_ID='"+temp.getDeptId()+"' ";
		}*/
		String flagwhere="";
		if(temp.getFlagCode()==null){
			flagwhere=" and FLAG_CODE LIKE '%%' ";
		}else{
			flagwhere=" and FLAG_CODE LIKE '%"+temp.getFlagCode()+"%' ";
		}
		String sql=" select * from T_RES_KYLR_TEMP where KYLB_CODE='"+lb+"'"+idwhere+flagwhere;
		String countssql=" select count(*) counts from T_RES_KYLR_TEMP where KYLB_CODE='"+lb+"'"+idwhere+flagwhere;
		List<Map<String, Object>> results = excute.getBaseDao().getBaseDao().getJdbcTemplate().queryForList(sql);
		String counts="";
		 if(results.size()>0){counts=(String) results.get(0).get("counts");}else{counts="0";}
		/* int pagecounts=Integer.parseInt(apage.getPageCount());
		 int begin=(Integer.parseInt(apage.getPageNumber())-1)*pagecounts+1;
		 int end=begin+pagecounts;
		 sql="select * from ("+sql+") where rownum between "+ begin+" and "+end;*/
		List<Map> tempList=excute.query(sql,new TResKylrTemp()); 
		String ids="";
		for(int i=0;i<tempList.size();i++){
			if(i==0){
				ids=(String) tempList.get(i).get("kyId");
			}else{
			ids+=","+(String)tempList.get(i).get("kyId");
			}
		}
		List tempKY=getUnaudited(ids, ky);
		String authtablename=(String) authMap.get(ky.getClass().getSimpleName());
		List authKY = null;
		try {
			try {
				authKY = getAuthorInfo(ids.split(","),Class.forName("com.jhnu.product.manager.scientific.entity."+authtablename).newInstance());
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		APage page =new APage();
		Map map=new LinkedHashMap();
		map.put("ky", tempKY);
		map.put("auth", authKY);
		map.put("temp", tempList);
		/*page.setCounts(counts);
		page.setLines(apage.getLines());
		page.setPageCount( (int)Math.floor(Integer.parseInt(counts)/pagecounts)+"");
		page.setPageNumber(apage.getPageNumber());*/
		page.setMap(map);
		return page;
	}

	@Override
	public String getTempKyCode(Object ky) {
		String[] tableClass=KyArray.tableClass;
		String lb="";
		String tableName=ky.getClass().getSimpleName();
		for(int i=0;i<tableClass.length;i++){
			String[] tcla=tableClass[i].split(",");
			for(int j=0;j<tcla.length;j++){
				if(tableName.equalsIgnoreCase(tcla[j])){
					lb+=(i+1)+""+j;
				}
			}
		}
		return lb;
	}
	@Override
	public String selectAId() {
		String sql="select sequence_ky.nextval id from dual";
		List<Map<String, Object>> results = excute.getBaseDao().getBaseDao().getJdbcTemplate().queryForList(sql);
		return  results.get(0).get("id").toString();
	}
	@Override
	public void excutexmcc(String xmid,String kyid,String clas) {
		String sql="insert into  T_RES_PROJECT_THESIS_PATENT(ID,PROJECT_ID,PATENT_ID,THESIS_ID) values";
		switch(clas){
		case "TResPatent":sql+="('"+selectAId()+"','"+xmid+"','"+kyid+"',NULL)";
			break;
		case "TResThesis":sql+="('"+selectAId()+"','"+xmid+"',NULL,'"+kyid+"')";
			break;
		}
		excute.getBaseDao().getBaseDao().getJdbcTemplate().execute(sql);
	}
	@Override
	public String selectxmccId(String kyid,String clas) {
		String sql="select PROJECT_ID id from T_RES_PROJECT_THESIS_PATENT ";
		switch(clas){
		case "TResPatent":sql+="where PATENT_ID='"+kyid+"'";
			break;
		case "TResThesis":sql+="where THESIS_ID='"+kyid+"'";
			break;
		}
		List<Map<String, Object>> results = excute.getBaseDao().getBaseDao().getJdbcTemplate().queryForList(sql);
		String id=null;
		if(results.size()>0&&results.get(0).get("ID")!=null) id=results.get(0).get("ID").toString();
		return  id;
	}
}
