package cn.gilight.dmm.teaching.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.teaching.dao.TeachingFundsDao;
import cn.gilight.framework.base.dao.BaseDao;
/**
 * 教学经费
 *
 */
@Repository("teachingFundsDao")
public class TeachingFundsDaoImpl  implements TeachingFundsDao{
	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessDao businessDao;
	@Override
	public List<Map<String, Object>> queryFundsBySty(int year,String deptId) {
		String sql = " (select t.FUND_CODE as code,OUTLAY_VAL value,DEPT_ID from t_teaching_funds t where year_="
				+ year + " and dept_id in ("+deptId+") ) funds ";// 查询当年所有经费
		String sql2 = getSqlForName();// 查询经费名称
		String sql3 = " select funds2.name as name,funds.code as code, (funds.value/10000) as value from " + sql + " left join "
				+ sql2 + " on funds.code=funds2.code ";
		List<Map<String, Object>> list = baseDao.queryListInLowerKey(sql3);// 得到数据
		return list;
	}

	@Override
	public Map<String, Object> queryFundsByYear(int year,String code,String deptId) {
		String sql = " (select fund_code as code,outlay_val as value,year_ as year from t_teaching_funds where year_= "
				+ year + " and dept_id in( "+deptId+") order by code ) funds ";
		String sql2= getSqlForName();
		String finsql="select funds2.name as name," + code + " as id , (funds.value/10000) as value from " + sql
				+ " left join " + sql2 + " on funds.code=funds2.code " + "where funds.code="+code;
		Map<String, Object> map = baseDao.queryMapInLowerKey(finsql);
		if(map==null){
			String mapNull="select funds2.name as name, " + code + " as id ,'0' as value from "
					+ "(select t.code_ as code,t.name_ as name from t_code t where code_type='FUND_CODE') funds2 "
					+ " where funds2.code="+code;
			map = baseDao.queryMapInLowerKey(mapNull);
		}
		
		return map;
	}
	
	@Override
	public Map<String, Object> queryFundsByAll(int year,String deptId) {
		int schoolYear=year-1;
		String stuSql="( select count(*) as count from "+
					  "( select t.* from t_stu t  where t.CLASS_ID in(select distinct(t.no_) no " +
					  " from t_classes t,(select * from(select id from (select * from T_Code_Dept t , "+
					  "(select t.path_ from T_Code_Dept t where t.id in("+deptId+")) x where t.istrue=1 and substr(t.path_,1,length(x.path_))=x.path_ "+
					  "order by t.level_,t.pid,t.order_,t.code_) union select id from (select * from T_Code_Dept_Teach t , "+
					  "(select t.path_ from T_Code_Dept_Teach t where t.id in("+deptId+")) x where t.istrue=1 and substr(t.path_,1,length(x.path_))=x.path_ "+ 
					  "order by t.level_,t.pid,t.order_,t.code_)))dept where t.teach_dept_id = dept.id) and (t.ENROLL_GRADE <= "+schoolYear+ 
					  "and (t.ENROLL_GRADE+t.LENGTH_SCHOOLING) > "+schoolYear+"))stu  where  stu.stu_roll_code = '1') stu "  ;
		
		String sqln = "select funds.name as name,(funds.value/10000) as value, round((funds.value/stu.count),2) as avg from "
					 + "(select '全部经费' as name,sum(outlay_val) as value from t_Teaching_Funds where year_=" + year
				     + " and dept_id in ("+deptId+") ) funds , " +stuSql;
		Map<String,Object> map=baseDao.queryMapInLowerKey(sqln);
		if(map.get("value").equals("")){
			map.put("value", 0);
			map.put("avg",0);
		}
		return map;
	}
	@Override
	public Map<String, Object> queryFundsAvg(int year,String deptId) {
		int schoolYear = year - 1;
		String stuSql=" select count(*) as count from "+
				  "( select t.* from t_stu t  where t.CLASS_ID in(select distinct(t.no_) no " +
				  " from t_classes t,(select * from(select id from (select * from T_Code_Dept t , "+
				  "(select t.path_ from T_Code_Dept t where t.id in("+deptId+")) x where t.istrue=1 and substr(t.path_,1,length(x.path_))=x.path_ "+
				  "order by t.level_,t.pid,t.order_,t.code_) union select id from (select * from T_Code_Dept_Teach t , "+
				  "(select t.path_ from T_Code_Dept_Teach t where t.id in("+deptId+")) x where t.istrue=1 and substr(t.path_,1,length(x.path_))=x.path_ "+ 
				  "order by t.level_,t.pid,t.order_,t.code_)))dept where t.teach_dept_id = dept.id) and (t.ENROLL_GRADE <= "+schoolYear+ 
				  "and (t.ENROLL_GRADE+t.LENGTH_SCHOOLING) > "+schoolYear+"))stu  where  stu.stu_roll_code = '1' "  ;//获取学生总数
		String fundsSql =  "select sum(outlay_val) as value from t_Teaching_Funds where year_=" + year
			     + " and dept_id in ("+deptId+") "; //获取经费总额
		String tag=baseDao.queryForString(fundsSql);
		if(tag==null || tag.equals("0")){
			fundsSql =  "select '0' as value from t_Teaching_Funds where year_=" + year
				     + " and dept_id in ("+deptId+") ";
		}
		String tag2=baseDao.queryForString(stuSql);
		String sqln="select round((funds.value/stu.count),2) as avg from "+"( "+fundsSql+" ) funds , "+" ( "+stuSql+" ) stu";
		Map<String,Object> map=new HashMap<String ,Object>();
		if(tag2==null || tag2.equals("0")){
			map.put("avg", 0);
		}else{
			map=baseDao.queryMapInLowerKey(sqln);
		}	
		if(map==null ){
			String mapNull = "select '0' as avg from "
					 + "(select sum(outlay_val) as value from t_Teaching_Funds where year_=" + year
				     + " and dept_id in ("+deptId+") ) funds , " +" ( "+stuSql+" ) stu";
			map = baseDao.queryMapInLowerKey(mapNull);
		}
		return map;
	}

	@Override
	public Map<String, Object> queryFundsBycollege(int year,String code,String deptId) {
		String sql = " (select fund_code as code,outlay_val as value,year_ as year from t_teaching_funds where year_= "
				+ year +" and dept_id="+deptId+" order by code ) funds ";//获取传入年经费
		String sql2= getSqlForName();
		String finsql="select funds2.name as name," + code + " as id , (funds.value/10000) as value from " + sql
				+ " left join " + sql2 + " on funds.code=funds2.code " + "where funds.code="+code+" ";
		Map<String, Object> map = baseDao.queryMapInLowerKey(finsql);
		if(map==null){
			String mapNull="select funds2.name as name, " + code + " as id ,'0' as value from "
					+ "(select t.code_ as code,t.name_ as name from t_code t where code_type='FUND_CODE') funds2 "
					+ " where funds2.code="+code;
			map = baseDao.queryMapInLowerKey(mapNull);
		}
		return map;
	}
	
	//获取当年各学院
	public List<Map<String,Object>> queryFundsBydeptId(int year,String deptList){
		String sql2="select name_ as name,id as id from t_code_dept_teach where pid in ( "+deptList+" ) and istrue=1 and level_type !='JXDW' order by order_";//更新sql
		List<Map<String,Object>> list=baseDao.queryListInLowerKey(sql2);
		return list;
	}
	
	// 获取经费编码表
	@Override
	public List<Map<String,Object>>  queryFundsByCode(){
		String sql=" select t.code_ as code,t.name_ as name from t_code t where code_type='FUND_CODE' ";
		return baseDao.queryListInLowerKey(sql);
	}
	private String getSqlForName(){
		String sql = " (select t.code_ as code,t.name_ as name from t_code t where code_type='FUND_CODE') funds2 ";
		return sql;
	}

	@Override
	public List<Map<String, Object>> queryFundsYear() {
		String sql="select id||'年' as mc,id from "+ 
					"(select distinct(year_) as id from t_teaching_funds order by id desc)";
		List<Map<String,Object>> list =baseDao.queryListInLowerKey(sql);
		return list;
	}

	

}
