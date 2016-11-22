package com.jhnu.edu.dao.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.edu.dao.SchoolListDao;
import com.jhnu.edu.entity.TEDUCODE;
import com.jhnu.edu.entity.TEDUSCHOOLS;
import com.jhnu.edu.util.SqlByEdu;
import com.jhnu.framework.base.dao.BaseDao;
import com.jhnu.framework.page.Page;

@Repository("schoolListDao")
public class SchoolListDaoImpl implements SchoolListDao {

	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Map<String, Object>> getFilter(String codeType) {
		TEDUCODE educode=new TEDUCODE();
		educode.setCodeType(codeType);
		String sql=SqlByEdu.getQuerySql(educode);
		List<Map<String, Object>> bxxz= baseDao.queryForList(sql);
		return bxxz;
	}

	@Override
	public Page getSchools(Map<String, Object> mapFilter,int currentPage, int numPerPage) {
		String schoolname=(String) mapFilter.get("schoolName");
		List list= (List) mapFilter.get("list");
		String ts="",tct="";
		  for (int i=0;i<list.size();i++) { 
			  Map m=(Map) list.get(i);
			  ts+=" or ts.value_ ='"+m.get("id")+"'";
			  tct+="or tct.code_type='"+m.get("codeType")+"'";
		  }
		  if(!ts.equalsIgnoreCase("")){
			  ts=" and "+ts.replace("or","");
			  tct=" and "+tct.replace("or","");
		  }
		String sql=SqlByEdu.getQuerySql(new TEDUSCHOOLS());
		sql="select  distinct t.* from t_edu_schools t "
				+ " left join T_EDU_SCHOOL_DETAILS ts on ts.SCHOOLId=t.id "
				+ " left join t_edu_code_title tct on tct.code_=ts.titleid "
				+ " where t.ch_name like'%"+schoolname+"%' "
						+ ts+tct;
		return  new Page(sql.toString(), currentPage, numPerPage, baseDao.getJdbcTemplate()
				, null);
	}
	
}
