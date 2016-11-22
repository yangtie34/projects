package cn.gilight.product.bookRke.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.SqlUtil;
import cn.gilight.product.bookRke.dao.StuBookRkeDao;
@Repository("stuBookRkeDao")
public class StuBookRkeDaoImpl implements StuBookRkeDao{
	@Autowired
	private BaseDao baseDao;
	
	@Autowired
	private LibraryRkeDaoImpl libraryRkeDao;
	private int rank=50;
	@Override
	public int changeRank(int rank){
		this.rank=rank;
		return this.rank;
	}
	private String getliveSql(String tj){
		String sql=" select * from (              "+
				" select t.NO_ ,               "+
				"   t.NAME_     ,  	           "+
				"   t.DEPT_ID    ,	           "+
				"   t.DEPT_NAME  ,	           "+
				"   t.MAJOR_ID   ,             "+
				"   t.MAJOR_NAME ,             "+
				"   t.CLASS_ID   ,             "+
				"   t.CLASS_NAME,  	           "+
				"   t.SEX_CODE  ,              "+
				"   t.SEX_NAME  ,              "+
				"   t.EDU_ID    ,              "+
				"   t.EDU_NAME   ,             "+
				"   t.NATION_CODE	,          "+
				"   t.NATION_NAME	,          "+
				"   sum(t.count_) count_  ,    "+
				"   dense_rank() over(order by sum(t.count_) desc) rank_ "+
				" from tl_stu_rke_book_month t "+
				" where t.count_>0             "+tj+
				" group by t.NO_ ,             "+
				"   t.NAME_     ,  	           "+
				"   t.DEPT_ID    ,	           "+
				"   t.DEPT_NAME  ,	           "+
				"   t.MAJOR_ID   ,             "+
				"   t.MAJOR_NAME ,             "+
				"   t.CLASS_ID   ,             "+
				"   t.CLASS_NAME,  	           "+
				"   t.SEX_CODE  ,              "+
				"   t.SEX_NAME  ,              "+
				"   t.EDU_ID    ,              "+
				"   t.EDU_NAME   ,             "+
				"   t.NATION_CODE	,          "+
				"   t.NATION_NAME	           "+
				"   )t where t.rank_<="+rank;
		return sql;
	}
	@Override
	public Page getRankLively(int currentPage,int numPerPage,int totalRow,String startDate,
			String endDate, Map<String, String> deptTeach) {
		String tj=libraryRkeDao.getWhere(startDate, endDate, deptTeach);
		String sql=getliveSql(tj);
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public List<Map<String, Object>> getBlByLB(String startDate,
			String endDate, Map<String, String> deptTeach, String lb) {
		String tj=libraryRkeDao.getWhere(startDate, endDate, deptTeach);
		String[] zdlb=libraryRkeDao.getZDBylb(lb);
		String sql=" select a.code,a.name,                                                                  "+
				"       round(count(b.no_) / count(a.no_) * 10000, 2) inrate,                       "+
				"       count(b.no_) count_                                                       "+
				"  from (select t.NO_, "+zdlb[0]+"                                                 "+
				"          from tl_stu_rke_book_month t                                           "+
				"         where 1 = 1                                                             "+
				tj+
				"         group by t.NO_, "+zdlb[1]+") a                                           "+
				"  left join (select *                                                            "+
				"               from (select t.NO_,                                               "+
				"                            "+zdlb[0]+",                                          "+
				"                            dense_rank() over(order by sum(t.count_) desc) rank_ "+
				"                       from tl_stu_rke_book_month t                              "+
				"                      where t.count_ > 0                                         "+
				tj+
				"                      group by t.NO_,"+zdlb[1]+") t                              "+
				"              where t.rank_ <= "+rank+") b on a.no_ = b.no_                           "+
				" group by a.code,a.name                                                                 ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	private String getsqlDept(String startDate,
			String endDate, Map<String, String> deptTeach,boolean alive){
		String tj=libraryRkeDao.getWhere(startDate, endDate, deptTeach);
		String querySql1="select * from tl_stu_rke_book_month t where 1=1 "+tj;
		String sqldept1=SqlUtil.getDeptTeachGroup(deptTeach,querySql1,false,"inner join");
		String querySql2=getliveSql(tj);
		if(alive==false){
			querySql2=querySql2.replace("desc", "");
		}
		String sqldept2=SqlUtil.getDeptTeachGroup(deptTeach,querySql2,false,"inner join");
		String sql=" select a.next_dept_code dept_code,a.next_dept_name dept_name,round(count(b.no_)/count(a.no_)*100, 2) inrate,count(b.no_) count_ "+
					  " from ("+sqldept1+") a "+
					  " left join ("+sqldept2+") b  "+
					  " on a.no_ = b.no_ "
					  + " group by a.next_dept_code, a.next_dept_name order by a.next_dept_code";
		return sql;
	}
	@Override
	public List<Map<String, Object>> getCountsByDeptLively(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String sql=getsqlDept(startDate, endDate, deptTeach, true);
		
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getCountsByDeptNoLively(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String sql=getsqlDept(startDate, endDate, deptTeach, false);
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getStuRkeTrend(String stuId) {
		String sql=" select                                      "+
				" to_char(t.year_month,'yyyy-mm' ) YEAR_MONTH,"+
				" sum(t.count_) all_count                     "+
				" from tl_stu_rke_book_month t                "+
				" where t.no_='"+stuId+"'                     "+
				" group by to_char(t.year_month,'yyyy-mm' )   "+
				" order by YEAR_MONTH                         ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
}
