package cn.gilight.dmm.xg.dao.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.xg.dao.WorkerDao;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.ListUtils;

/**
 * 学生工作者数据查询
 * 
 * @author xuebl
 * @date 2016年4月19日 下午5:14:24
 */
@Repository("workerDao")
public class WorkerDaoImpl implements WorkerDao {

	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessDao businessDao;
	
	@Override
	public List<Map<String, Object>> queryWorkerCountList(String start_date, String end_date, 
			List<String> deptList, List<AdvancedParam> teaAdvancedList){
		String pmsSql = getWorkerMatchingRecordIdSql(start_date, end_date, deptList, null),
			   sql = "select '" +Constant.Worker_Name+ "' name, count(distinct t.tea_id) as value, '" +Constant.CODE_All+ "' code"
				+ " from t_tea_stu_worker t,("+pmsSql+")wk where t.id=wk.id"
				+ " union all select * from (select cd.name_ name, t.count as value, cd.code_ code from t_code cd"
				+ " left join (select count(distinct(t.tea_id)) count, t.stu_worker_code"
				+ " from t_tea_stu_worker t,("+pmsSql+")wk where t.id=wk.id group by t.stu_worker_code) t"
				+ " on t.stu_worker_code=cd.code_"
				+ " where cd.code_type='"+Constant.CODE_STU_WORKER_CODE+"' order by cd.order_,cd.code_)";
		return ListUtils.replaceNullToValue(baseDao.queryListInLowerKey(sql), "value", 0);
	}
	
	@Override
	public List<String> queryInstructorsList(List<String> deptList, List<List<String>> xnxqList, boolean isFullTime){
		// 学年学期解析sql
		String xnxqSql = "";
		for(List<String> li : xnxqList){
			if(!"".equals(xnxqSql)) xnxqSql += " or ";
			xnxqSql += "( substr(t.school_year, 1, 4)='"+li.get(0)+"' and t.term_code ='"+li.get(1)+"')";
		}
		xnxqSql = xnxqSql.length()==0 ? "" : (" and ("+xnxqSql+ ")");
		// 权限班级解析sql
		String classDeptSql = businessDao.getClassesIdSqlByDeptList(deptList),
			   sql = "select distinct(t.tea_id) from t_classes_instructor t, ("+classDeptSql+")c"
				+ " where t.class_id=c.no and " + (isFullTime ? "t.isFullTime=1" : "t.isFullTime!=1") +xnxqSql;
		return baseDao.queryForListString(sql);
	}
	
	@Override
	public List<String> queryInstructorsList(List<String> deptList, String start_date, String end_date, boolean isFullTime){
		String stu_worker_code = isFullTime ? Constant.CODE_STU_WORKER_CODE_2 : Constant.CODE_STU_WORKER_CODE_3,
			   sql = getWorkerMatchingTeacherIdSql(start_date, end_date, deptList, stu_worker_code);
		return baseDao.queryForListString(sql);
	}
	
	@Override
	public String getInstructorsSql(List<String> deptList, String start_date, String end_date, boolean isFullTime){
		String stu_worker_code = isFullTime ? Constant.CODE_STU_WORKER_CODE_2 : Constant.CODE_STU_WORKER_CODE_3,
			   sql = getWorkerMatchingTeacherIdSql(start_date, end_date, deptList, stu_worker_code);
		return "select t.tea_id,tea.dept_id from ("+sql+")t left join t_tea tea on t.tea_id=tea.tea_no";
	}
	
	/**
	 * 获取学生工作者表记录 ID sql 【可以用这个sql 再次关联 t_tea_stu_worker 表】
	 * @param start_date
	 * @param end_date
	 * @return String "select t.id from t_tea_stu_worker t where ..."
	 */
	@Override
	public String getWorkerMatchingRecordIdSql(String start_date, String end_date, List<String> deptList, String stu_worker_code){
		// 日期匹配sql、权限匹配sql
		String dateMatchingRecordIdSql = "select t.id, t.tea_id from t_tea_stu_worker t where "
									+ (stu_worker_code==null ? "" : (" t.stu_worker_code = '"+stu_worker_code+"' and ") )
									+ " (" +getWorkerMatchingDateSql(start_date, end_date)+ ")",
				deptIdSql   = businessDao.getDeptAllIdSqlByDeptList(deptList),
				getTeaSql   = businessDao.getTeaSql(deptList, new ArrayList<AdvancedParam>()),
				matchingSql = "select distinct(wk.id) from (" +dateMatchingRecordIdSql+ ") wk, (" +getTeaSql+ ") tea, (" +deptIdSql+ ") dept where wk.tea_id=tea.tea_no and tea.dept_id=dept.id";
		return matchingSql;
	}
	/**
	 * 获取学生工作者教职工 ID sql 【一定不能用这个sql 再次关联 t_tea_stu_worker 表】
	 * @param start_date
	 * @param end_date
	 * @return String "select t.tea_id from t where ..."
	 */
	@Override
	public String getWorkerMatchingTeacherIdSql(String start_date, String end_date, List<String> deptList, String stu_worker_code){
		// 日期匹配sql、权限匹配sql
		String dateMatchingSql = "select distinct(t.tea_id) tea_id from t_tea_stu_worker t where "
								+ (stu_worker_code==null ? "" : (" t.stu_worker_code = '"+stu_worker_code+"' and ") )
								+ " (" +getWorkerMatchingDateSql(start_date, end_date)+ ")",
			   deptIdSql   = businessDao.getDeptAllIdSqlByDeptList(deptList),
					   getTeaSql   = businessDao.getTeaSql(deptList, new ArrayList<AdvancedParam>()),
			   matchingSql = "select wk.tea_id from (" +dateMatchingSql+ ") wk,  (" +getTeaSql+ ") tea, (" +deptIdSql+ ") dept where wk.tea_id=tea.tea_no and tea.dept_id=dept.id";
		return matchingSql;
	}
	/**
	 * 获取学生工作者人员匹配sql（日期）
	 * @param start_date
	 * @param end_date
	 * @return String
	 */
	private String getWorkerMatchingDateSql(String start_date, String end_date){
		String s = end_date==null ?
				// 开始时间 >start_date || 或结束时间>start_date  || 结束时间 is null
				("t.start_date >= '" +start_date+ "' or t.end_date >= '" +start_date+ "' or t.end_date is null")
				: ("(t.start_date <= '" +start_date+ "' and(t.end_date >= '" +start_date+ "' or t.end_date is null) )"
					+ " or (t.start_date >= '" +start_date+ "' and t.start_date <= '" +end_date+ "')");
		return s;
	}
	
	@Override
	public List<Map<String, Object>> queryWorkerZcCountList(String start_date, String end_date, List<String> deptList, String stu_worker_code){
		String pmsSql = getWorkerMatchingTeacherIdSql(start_date, end_date, deptList, stu_worker_code),
		sql = "select t.value, t.code, case when jbCode.name_ is null then '" +Constant.CODE_Unknown_Name+ "' else jbCode.name_ end as name"
				+ " from (select zyjszw.zyjszw_jb_code code, count(0) value"
				+ "  from (" +pmsSql+ ") wk, t_tea tea"
				+ "  left join T_CODE_ZYJSZW zyjszw on tea.zyjszw_id = zyjszw.id"
				+ "  where wk.tea_id = tea.tea_no group by zyjszw.zyjszw_jb_code)t"
				+ " left join t_code jbCode on t.code=jbCode.Code_ and jbCode.Code_Type='"+Constant.CODE_ZYJSZW_JB_CODE+"'"
				+ " order by jbCode.order_, jbCode.code_";
		return baseDao.queryListInLowerKey(sql, Types.NUMERIC, Types.VARCHAR, Types.VARCHAR);
	}
	
	@Override
	public List<Map<String, Object>> queryWorkerDegreeCountList(String start_date, String end_date, List<String> deptList, String stu_worker_code){
		String pmsSql = getWorkerMatchingTeacherIdSql(start_date, end_date, deptList, stu_worker_code),
		sql = "select case when deg.name_ is null then '" +Constant.CODE_Unknown_Name+ "' else deg.name_ end as name, x.count as value, deg.id as degree_id_pid from"
				+ " (select z.pid, sum(z.count) count from"
				+ " (select t.count, deg.pid from"
				+ " (select count(0) count, t.degree_id from t_tea t,(" +pmsSql+ ")wk where t.tea_no=wk.tea_id group by t.degree_id) t"
				+ " left join (select t.id,tp.id pid from t_code_degree t, t_code_degree tp where substr(t.path_,1,4)=tp.path_)deg"
				+ " on t.degree_id=deg.id) z group by pid)x"
				+ " left join t_code_degree deg on x.pid=deg.id order by deg.order_,deg.code_";
		return baseDao.queryListInLowerKey(sql, Types.VARCHAR, Types.NUMERIC, Types.VARCHAR);
	}
	
	@Override
	public List<String> queryWorkerBirthdayList(String start_date, String end_date, List<String> deptList, String stu_worker_code){
		String pmsSql = getWorkerMatchingTeacherIdSql(start_date, end_date, deptList, stu_worker_code),
				sql = "select tea.birthday from t_tea tea, (" +pmsSql+ ") wk where tea.tea_no=wk.tea_id ";
		return baseDao.queryForListString(sql);
	}
	
	@Override
	public List<Map<String, Object>> queryWorkerSexCountList(String start_date, String end_date, List<String> deptList, String stu_worker_code){
		String pmsSql = getWorkerMatchingTeacherIdSql(start_date, end_date, deptList, stu_worker_code),
		sql = "select case when sex.name_ is null then '" +Constant.CODE_Unknown_Name+ "' else sex.name_ end as name,sex.code_ as code, x.count as value"
				+ " from (select count(0) count, tea.sex_code from (" +pmsSql+ ") wk, t_tea tea"
				+ " where tea.tea_no=wk.tea_id group by tea.sex_code) x"
				+ " left join t_code sex on x.sex_code=sex.code_ and sex.code_type = '"+Constant.CODE_SEX_CODE+"' order by sex.order_,sex.code_";
		return baseDao.queryListInLowerKey(sql, Types.VARCHAR, Types.NUMERIC);
	}
	
}
