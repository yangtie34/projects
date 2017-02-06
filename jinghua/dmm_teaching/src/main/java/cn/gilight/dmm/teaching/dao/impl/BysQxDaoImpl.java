package cn.gilight.dmm.teaching.dao.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.teaching.dao.BysQxDao;
import cn.gilight.framework.base.dao.BaseDao;

@Repository("bysQxDao")
public class BysQxDaoImpl implements BysQxDao{

	@Resource
	private BusinessDao businessDao;
	@Resource
	private BusinessService businessService;
	@Resource
	private BaseDao baseDao;

	@Override
	public String getBysSql(String schoolYear,String stuSql,Boolean graduated,Boolean isDegree){
		String gra = "",deg="";
		if(graduated != null && graduated){
			gra = " and t.GRADUATED = 1 ";
		}else if(graduated != null && !graduated){
			gra = " and t.GRADUATED = 0 ";
		}
		if(isDegree != null && isDegree ){
			deg = " and t.DEGREE_GRANT = 1 ";
		}else if(isDegree != null && !isDegree){
			deg = " and t.DEGREE_GRANT = 0 ";
		}
		String sql = "select t.*,stu.no_,stu.dept_id,stu.major_id,stu.class_id from t_stu_graduate t,("+stuSql+") stu where t.school_year = '"+schoolYear+"'"
				+ gra+deg+" and t.stu_id = stu.no_ ";
		return sql;
	}
	@Override
	public List<Map<String,Object>> queryBysFb(String schoolYear,String stuSql,int scale,Boolean isScale){
		String bysSql = getBysSql(schoolYear, stuSql, true, null);
		String str = isScale ? " case when "+scale+" =0 then 0 else "
				+ " round(count(0)*100/"+scale+",0) end as value " : " count(0) as value ";
		String codeSql ="select * from t_code_graduate_direction where level_ = 1 ";
		String sql = "select code1.id as id,code1.name_ as name,"+str+" from t_stu_graduate_direction dir,("+bysSql+") bys,"
				+ " t_code_graduate_direction code,("+codeSql+") code1 where "
			    + " dir.stu_id = bys.stu_id and dir.direction_id = code.id and "
			    + " substr(code.path_,0,4) = code1.path_ group by code1.id,code1.name_ order by code1.id ";
		return baseDao.queryListInLowerKey(sql);
	}
	@Override
	public List<Map<String,Object>> queryBysSzFb(String schoolYear,String stuSql,int scale,Boolean isScale){
		String bysSql = getBysSql(schoolYear, stuSql, true, null);
		String str = isScale ? " case when "+scale+" =0 then 0 else "
				+ " round(count(0)*100/"+scale+",0) end as value " : " count(0) as value ";
		String codeSql ="select * from t_code_graduate_direction where pid = '"+Constant.CODE_GRADUATE_DIRECTION_ID_02+"' ";
		String sql = "select code1.id as id,code1.name_ as name,"+str+" from t_stu_graduate_direction dir,("+bysSql+") bys,"
				+ " t_code_graduate_direction code,("+codeSql+") code1 where "
			    + " dir.stu_id = bys.stu_id and dir.direction_id = code.id and "
			    + " substr(code.path_,0,8) = code1.path_ group by code1.id,code1.name_ order by code1.id ";
		return baseDao.queryListInLowerKey(sql);
	}
	@Override
	public List<Map<String,Object>> queryBysWjyyy(String schoolYear,String stuSql){
		String bysSql = getBysSql(schoolYear, stuSql, true, null);
		String codeSql ="select * from t_code where code_type = 'UNOCCUPIED_CODE' ";
		String str = "select count(0) as value from t_stu_graduate_direction dir,("+bysSql+") bys,"
				+ " ("+codeSql+") code where dir.stu_id = bys.stu_id and dir.UNOCCUPIED_CODE = code.code_ ";
		int count = baseDao.queryForInt(str);
		String sql = "select code.CODE_ as id,code.name_ as name,case when "+count+" = 0 then 0 else "
				+ " round(count(0)*100/"+count+",0) end as value from t_stu_graduate_direction dir,("+bysSql+") bys,"
				+ " ("+codeSql+") code where dir.stu_id = bys.stu_id and dir.UNOCCUPIED_CODE = code.code_ group by code.CODE_ "
						+ " ,code.name_ order by code.CODE_ ";
		return baseDao.queryListInLowerKey(sql);
	}
	@Override
	public int queryCountFb(String schoolYear,String stuSql){
		String bysSql = getBysSql(schoolYear, stuSql, true, null);
		String sql = "select count(0) as value from t_stu_graduate_direction dir,("+bysSql+") bys,"
				+ " t_code_graduate_direction code where "
			    + " dir.stu_id = bys.stu_id and dir.direction_id = code.id ";
		return baseDao.queryForInt(sql);
	}
	@Override
	public List<Map<String,Object>> queryCountFbByDept(String schoolYear,List<String> deptList,List<AdvancedParam> advancedList){
		String stuSql = businessDao.getStuSql(deptList, advancedList);
		int year = Integer.parseInt(schoolYear.substring(0, 4));
		String bysSql = getBysSql(schoolYear, stuSql, true, null);
		Integer grade = AdvancedUtil.getStuGrade(advancedList); 
		String sql = "select bys.dept_id||','||bys.major_id||','||bys.class_id as id,bys.dept_id,bys.major_id,bys.class_id,count(0) as value from t_stu_graduate_direction dir,("+bysSql+") bys "
				+ "  where dir.stu_id = bys.stu_id group by bys.dept_id,bys.major_id,bys.class_id ";
		String sql1 = " select bys.dept_id||','||bys.major_id||','||bys.class_id as id,bys.dept_id,bys.major_id,bys.class_id,code1.id as code,"
				+ " code1.name_ as name,count(0) as value "
				+ " from t_stu_graduate_direction dir,("+bysSql+") bys,"
				+ " t_code_graduate_direction code,"
				+ " (select * from t_code_graduate_direction where level_ = 1) code1 "
				+ " where  dir.stu_id = bys.stu_id and dir.direction_id = code.id "
				+ " and substr(code.path_,0,4) = code1.path_ "
				+ " group by bys.dept_id,bys.major_id,bys.class_id,code1.id,code1.name_ ";
		sql = " select a.dept_id,a.major_id,a.class_id,a.value as \"all\",b.value as lxcount,"
				+ " b.name,b.code from ("+sql+") a,("+sql1+") b"
				+ "  where a.id = b.id ";
		String deptSql = businessService.getNextLevelDataSqlByDeptDataAndPid(deptList, AdvancedUtil.getPid(advancedList), sql, false, true, false, false, year, grade);
		String orderSql = businessService.getNextLevelOrderSqlByDeptDataAndPid(deptList, AdvancedUtil.getPid(advancedList), sql, false, true, false, false, year, grade);
	    String reSql = "select  a.id as code, a.name_ as field,dept.code as id,dept.name,case when sum(dept.\"all\")=0 then 0 else "
	    		+ " round(sum(dept.lxcount)*100/sum(dept.\"all\"),2) end as value from ("+deptSql+") dept "
				+ " ,("+orderSql+") a where  dept.next_dept_code = a.id group by  a.id,a.name_,a.next_dept_order,dept.code,dept.name "
				+ " order by a.next_dept_order ";
	    return baseDao.queryListInLowerKey(reSql);
	}
	@Override
	public int queryCountSzFb(String schoolYear,String stuSql){
		String bysSql = getBysSql(schoolYear, stuSql, true, null);
		String codeSql ="select * from t_code_graduate_direction where pid = '"+Constant.CODE_GRADUATE_DIRECTION_ID_02+"' ";
		String sql = "select count(0) as value from t_stu_graduate_direction dir,("+bysSql+") bys,"
				+ " t_code_graduate_direction code,("+codeSql+") code1 where "
			    + " dir.stu_id = bys.stu_id and dir.direction_id = code.id and "
			    + " substr(code.path_,0,8) = code1.path_ ";
		return baseDao.queryForInt(sql);
	}
	@Override
	public String getPath(String code){
		String sql = "select path_  from t_code_graduate_direction where id = '"+code+"'";
		return baseDao.queryForString(sql);
	}
}
