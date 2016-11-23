package cn.gilight.product.dorm.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.code.Code;
import cn.gilight.framework.enums.ShiroTagEnum;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.SqlUtil;
import cn.gilight.product.card.dao.CardTjUtil;
import cn.gilight.product.dorm.dao.DormRkeUsedDao;
import cn.gilight.product.dorm.dao.DormSqlUtil;

@Repository("dormRkeUsedDao")
public class DormRkeUsedDaoImpl implements DormRkeUsedDao{

	@Autowired
	private BaseDao baseDao;
	
	@Override
	public Map<String, Object> getDormRkeUsed(String startDate, String endDate,
			Map<String, String> deptTeach) {
		String dateTj=DormSqlUtil.getDateTJ(startDate, endDate);
		String deptTj=SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.DORM_RKE_STU.getCode(),"t");
		String sql="select round(nvl(a.nums,0) / DECODE(b.nums, 0,1,b.nums) * 100, 2) use_rate, "+
					"nvl(a.nums,0) use_Num,DECODE(b.nums, 0,1,b.nums) all_Num "+
					"from (select count(*) nums,'1' flag "+
					"from (select sum(t.rke_count) rke_count,t.stu_id,t.stu_name from tl_dorm_rke_USED_stu t "+
					"where 1=1  "+dateTj+deptTj+
					"group by t.stu_id,t.stu_name ) "+
					"where rke_count >=(MONTHS_BETWEEN(to_date('"+endDate+"', 'yyyy-mm'), "+
					"to_date('"+startDate+"', 'yyyy-mm')) * "+Code.getKey("dorm.uesd")+") ) a "+
					"right join (select count(*) nums, '1' flag "+
					"from tl_dorm_stu t where t.stu_id is not null "+deptTj+" ) b "+
					"on a.flag = b.flag";
		return baseDao.getJdbcTemplate().queryForMap(sql);
	}

	@Override
	public List<Map<String, Object>> getDormRkeUsedBySex(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String sql=getDormUsedSql(startDate, endDate, deptTeach, "SEX");
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getDormRkeUsedByEdu(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String sql=getDormUsedSql(startDate, endDate, deptTeach, "EDU");
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getDormRkeUsedByMz(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String sql=getDormUsedSql(startDate, endDate, deptTeach, "MZ");
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	
	@Override
	public List<Map<String, Object>> getDormRkeUsedByLY(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String sql=getDormUsedSql(startDate, endDate, deptTeach, "LY");
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getDormRkeUsedByDept(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String sql=getDormUsedSql(startDate, endDate, deptTeach, "deptTeach");
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public Page getDormRkeUsedPage(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc, String startDate, String endDate,
			Map<String, String> deptTeach, String type, String type_code) {
		//TODO 需要看一下一卡通的使用情况在点击院系明细的时候是否有错。
		
		return null;
	}

	@Override
	public Page getNoDormRkeUsed(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			String startDate, String endDate, Map<String, String> deptTeach) {
		String dateTj=CardTjUtil.getDateTJ(startDate, endDate);
		String deptTj=SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.DORM_RKE_STU.getCode(),"t");
		String sql="select a.rke_count,b.* from "+
					"(select rke_count,stu_id "+
					"from (select sum(t.rke_count) rke_count ,t.stu_id from tl_dorm_rke_USED_stu t "+
					"where 1=1 "+dateTj+deptTj+
					"group by t.stu_id ) "+
					"where rke_count <(MONTHS_BETWEEN(to_date('"+endDate+"', 'yyyy-mm'), "+
					"to_date('"+startDate+"', 'yyyy-mm')) * "+Code.getKey("dorm.uesd")+") ) a "+
					"inner join tl_dorm_stu b on a.stu_id=b.stu_id order by rke_count";
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow,sort,isAsc);
	}
	
	private String getDormUsedSql(String startDate,String endDate, Map<String, String> deptTeach,String queryType){
		
		String dateTj=DormSqlUtil.getDateTJ(startDate, endDate);
		String deptTj=SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.DORM_RKE_STU.getCode(),"t");
		String id="",name="";
		String querySql="select * from TL_DORM_RKE_USED_STU t where 1=1 "+dateTj+deptTj ;
		String querySql2="select * from tl_dorm_stu t where t.stu_id is not null "+deptTj;
		if("deptTeach".equals(queryType)){
			querySql=SqlUtil.getDeptTeachGroup(deptTeach,querySql,true,"inner join");
			
			querySql2=SqlUtil.getDeptTeachGroup(deptTeach,querySql2,true,"inner join");
			
			id="next_dept_code";name="next_dept_name";
		}else{
			id=queryType+"_id";
			name=queryType+"_name";
		}
		
		String sql="select round(nvl(a.nums,0) / DECODE(b.nums, 0,1,b.nums) * 100, 2) use_rate, "+
					"nvl(a.nums,0) use_Num,DECODE(b.nums, 0,1,b.nums) all_Num,b."+id+" code,b."+name+" name "+
					"from (select count(*) nums, "+id+", "+name+" "+
					"from (select sum(t.rke_count) rke_count,t.stu_id,t.stu_name, "+
					"t."+id+",t."+name+" from ("+querySql+") t "+
					"group by t.stu_id,t.stu_name,t."+id+",t."+name+") "+
					"where rke_count >=(MONTHS_BETWEEN(to_date('"+endDate+"', 'yyyy-mm'), "+
					"to_date('"+startDate+"', 'yyyy-mm')) * "+Code.getKey("dorm.uesd")+") group by "+id+", "+name+") a "+
					"right join (select count(*) nums, "+id+","+name+" "+
					"from ("+querySql2+") group by "+id+","+name+") b "+
					"on a."+id+" = b."+id+"";
		return sql;
	}

}
