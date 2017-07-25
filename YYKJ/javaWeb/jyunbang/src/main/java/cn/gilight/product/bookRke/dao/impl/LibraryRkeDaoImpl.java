package cn.gilight.product.bookRke.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.code.Code;
import cn.gilight.framework.enums.ShiroTagEnum;
import cn.gilight.framework.uitl.common.SqlUtil;
import cn.gilight.product.bookRke.dao.LibraryRkeDao;
@Repository("libraryRkeDao")
public class LibraryRkeDaoImpl implements LibraryRkeDao{
	@Autowired
	private BaseDao baseDao;
	
	public String getWhere(String startDate,
			String endDate, Map<String, String> deptTeach){
		return SqlUtil.getWhere(startDate,endDate,deptTeach, ShiroTagEnum.BOOK_LR.getCode());
	}
	public String[] getZDBylb(String lb){
		return SqlUtil.getZDBylb(lb);
	}
	@Override
	public List<Map<String, Object>> getCounts(String startDate,
			String endDate, Map<String, String> deptTeach, String lb) {
		String tj=getWhere(startDate, endDate, deptTeach);
		String[] zdlb=getZDBylb(lb);
		String sql="select "+zdlb[0]+", sum(t.count_) all_count "
				+ " from tl_stu_rke_book_month t"
				+ " where 1=1  "+tj
				+" group by  "+zdlb[1];
		if(lb.equalsIgnoreCase("mz")){
			sql="select t.code,t.name, "+
					" sum(t.all_count) all_count "+
					" from( "+
					sql+
					") t group by t.code,t.name ";
		}
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getAvgCounts(String startDate,
			String endDate, Map<String, String> deptTeach, String lb) {
		String tj=getWhere(startDate, endDate, deptTeach);
		String[] zdlb=getZDBylb(lb);
		String sql="select t.code,t.name,round(sum(t.count_)/count(t.no_),2) avg_count from "
				+ "(select t.no_,sum(t.count_) count_ ,"+zdlb[0]
				+ " from tl_stu_rke_book_month t "
				+ " where 1=1  "+tj
				+ " group by t.no_,"+zdlb[1]+")t "
				+ " group by t.code,t.name";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getInRate(String startDate,
			String endDate, Map<String, String> deptTeach, String lb) {
		String tj=getWhere(startDate, endDate, deptTeach);
		String[] zdlb=getZDBylb(lb);
		String sql="select t.code,t.name,round(sum(t.count_)/count(t.count_)*100,2) inrate from "
				+ "(select t.no_,case when  sum(t.count_) >0 then 1 else 0 end count_,"+zdlb[0]
				+ " from tl_stu_rke_book_month t "
				+ " where 1=1  "+tj
				+ " group by t.no_,"+zdlb[1]+")t "
				+ " group by t.code,t.name";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getCountsByGroup(String startDate,
			String endDate, Map<String, String> deptTeach, String lb, String fieldlb) {
		String tj=getWhere(startDate, endDate, deptTeach);
		String[] zdlb=getZDBylb(lb);
		String[] fieldzdlb=getZDBylb(fieldlb);
		String sql="select "+zdlb[0]+","+fieldzdlb[0]+",sum(t.count_) count_  "
				+ " from tl_book_rke_stu_month t"
				+ " where 1=1  "+tj
				+ " group by "+zdlb[1]+","+fieldzdlb[1];
		if(lb.equalsIgnoreCase("mz")){
			sql="select t.code,t.name,t.fieldcode,t.fieldname, "+
					" sum(t.count_) count_ "+
					" from( "+
					sql+
					") t group by t.code,t.name,t.fieldcode,t.fieldname ";
		}
		sql="select * from ("+sql+") order by  fieldcode";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getQjByGroup(String startDate,
			String endDate, Map<String, String> deptTeach, String lb) {
		String tj=getWhere(startDate, endDate, deptTeach);
		String[] zdlb=getZDBylb(lb);
	 String sql=" select t.code,t.name,t.field,sum(t.count_) count_ from(           "+
				" select t.code,t.name,                                      "+
				" case when  t.count_ <5 then '1'               "+
				" when t.count_ >=5  and t.count_ <10 then '2'   "+
				
				" when t.count_ >=10 and t.count_ <20 then '3'  "+ 
				" when t.count_ >=20 and t.count_ <30 then '4'  "+
				" when t.count_ >=30 and t.count_ <50 then '5'  "+
				" else '6' end fieldcode ,                         "+
				" case when  t.count_ <5 then '5次以下'               "+
				" when t.count_ >=5  and t.count_ <10 then '5-10次'   "+
				
				" when t.count_ >=10 and t.count_ <20 then '10-20次'  "+ 
				" when t.count_ >=20 and t.count_ <30 then '20-30次'  "+
				" when t.count_ >=30 and t.count_ <50 then '30-50次'  "+
				" else '50次以上' end field ,                         "+
				" count(t.no_) count_                                 "+
				" from (                                              "+
				" select"+zdlb[0]+",t.no_,sum(t.count_) count_   "+
				" from tl_stu_rke_book_month t                        "+
				" where 1=1  "+tj+
				" group by "+zdlb[1]+",t.no_                           "+
				" ) t                                                 "+
				" group by t.code,t.name,t.count_                            "+
				" ) t                                                 "+
				" group by t.code,t.name,t.field ,t.fieldcode  order by t.fieldcode                          ";
	 return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getCountsByDept(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String tj=getWhere(startDate, endDate, deptTeach);
		String querySql="select * from tl_stu_rke_book_month t where 1=1 "+tj;

		String sqldept=SqlUtil.getDeptTeachGroup(deptTeach,querySql,false,"inner join");
		String pubsql=",t.next_dept_code, t.next_dept_name from ("+sqldept+") t  "+
					"group by t.next_dept_code, t.next_dept_name ";
		String sql="select a.next_dept_code dept_code,a.next_dept_name dept_name,a.all_count,b.avg_count,c.inrate from "
				+ "(select sum(t.count_) all_count"+pubsql+"   "
						+ ")a "
						+ " left join "
				+ "(select round(sum(t.count_)/count(t.no_),2) avg_count,t.next_dept_code, t.next_dept_name from("
				+ "select t.no_,sum(t.count_) count_"+pubsql+", t.no_ )t group by t.next_dept_code, t.next_dept_name  "
						+ ")b on a.next_dept_code=b.next_dept_code "
						+ " left join "
				+ "(select round(sum(t.count_)/count(t.count_)*100,2) inrate,t.next_dept_code, t.next_dept_name from ("
				+ "select t.no_,case when  sum(t.count_) >0 then 1 else 0 end count_"+pubsql+",t.no_)t group by t.next_dept_code, t.next_dept_name  "
						+ ")c on c.next_dept_code=b.next_dept_code order by a.next_dept_code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getCountsForYears(
			Map<String, String> deptTeach) {
		String sql="select t.year,sum(t.all_count) all_count,round(sum(t.all_count)/count(t.stu_count),2) avg_count from("
				+ "select to_char(t.year_month,'yyyy' ) year,sum(t.count_) all_count,"
				+ " t.no_ stu_count from tl_stu_rke_book_month t "
				+ " where 1=1 "
				+SqlUtil.getDeptTeachTj(deptTeach,ShiroTagEnum.BOOK_LR.getCode(),"t")
				+ " group by to_char(t.year_month,'yyyy' ),t.no_)t"
				+ " group by t.year order by year ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	@Override
	public List<Map<String, Object>> getStuRkeTrend(
			Map<String, String> deptTeach, String flag, String flagCode) {
		String whe=null;
		switch(flag){
		case "all":whe="";
			break;
		case "xb":whe="and t.sex_code='"+flagCode+"' ";
			break;
		case "xl":whe="and t.edu_id='"+flagCode+"' ";
			break;
		case "mz":whe="and t.nation_code='"+flagCode+"' ";
				if(flagCode=="null"){
					String hz=Code.getKey("mz.hz");String zz=Code.getKey("mz.zz.code");
					whe="and t.nation_code!='"+hz+"' and t.nation_code!='"+zz+"' ";
				}
			break;	
		}
		String sql="select t.YEAR_MONTH,sum(t.all_count) all_count,round(sum(t.all_count)/count(t.stu_count),2) avg_count,round(sum(t.count_)/count(t.count_)*100,2) inrate from("
				+ "select to_char(t.year_month,'yyyy-mm' ) YEAR_MONTH,sum(t.count_) all_count,case when  sum(t.count_) >0 then 1 else 0 end count_,"
				+ " t.no_ stu_count from tl_stu_rke_book_month t "
				+ " where 1=1 "
				+whe
				+SqlUtil.getDeptTeachTj(deptTeach,ShiroTagEnum.BOOK_LR.getCode(),"t")
				+ " group by to_char(t.year_month,'yyyy-mm' ),t.no_)t"
				+ " group by t.YEAR_MONTH order by t.YEAR_MONTH ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
}
