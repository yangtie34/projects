package com.jhnu.system.task.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.dao.PlanLogDetailsDao;
import com.jhnu.system.task.entity.Plan;
import com.jhnu.system.task.entity.PlanLogDetails;
@Repository("taskPlanLogDetailsDao")
public class PlanLogDaoDetailsImpl extends TaskBaseDaoImpl implements PlanLogDetailsDao {

	@Override
	public Page getPlanLogDetailss(int currentPage, int numPerPage) {
		String sql=sqlByBean.getQuerySql(new PlanLogDetails());
		return getObjs(currentPage,numPerPage,sql, new PlanLogDetails());
	}

	@Override
	public PlanLogDetails getPlanLogDetailsById(String id) {
		PlanLogDetails plan=new PlanLogDetails();
		plan.setId(id);
		String sql=sqlByBean.getQuerySqlByIdOrder(plan);
		return (PlanLogDetails) getObjs(sql, new PlanLogDetails()).get(0);
	}

	@Override
	public Page getWorkPlanLogDetailsByPlanLogId(int currentPage, int numPerPage,String planLogId) {
		String sql="select a.ID, a.PLANLOGID, a.LOGTYPEID, a.ISYES, a.RESULT_DESC, c.name_ LOGTYPE, a.STARTTIME, a.ENDTIME "
				+ "from T_SYS_SCHEDULE_PLAN_LOG_DETAIL a "
				+ " left join T_SYS_SCHEDULE_PLAN_WORK b on (b.WORKID=a.LOGTYPEID) "
				+ " left join T_SYS_SCHEDULE_WORK c on (a.LOGTYPEID=c.id)"
				+ "where a.logtype='work' and a.PLANLOGID='"+planLogId+"' "
						+ "order by b.order_";
		return getObjs(currentPage,numPerPage,sql, new PlanLogDetails());
	}
	
	@Override
	public List getVerifyPlanLogDetailsByPlanLogId(String planLogId,String workId) {
		String sql="select a.ID, a.PLANLOGID, a.LOGTYPEID, a.ISYES, a.RESULT_DESC, c.name_ LOGTYPE, a.STARTTIME, a.ENDTIME "
				+ "from T_SYS_SCHEDULE_PLAN_LOG_DETAIL a "
				+ " left join T_SYS_SCHEDULE_WORK_VERIFY b on (b.VERIFYID=a.LOGTYPEID) "
				+ " left join T_SYS_SCHEDULE_VERIFY c on (a.LOGTYPEID=c.id)"
				+ "where a.logtype='verify' and b.WORKID='"+workId+"' and  a.PLANLOGID='"+planLogId+"'"
				+ "order by b.order_";
		return getObjs(sql, new PlanLogDetails());
	}
	@Override
	public boolean updateOrInsert(PlanLogDetails plan) {
		if(plan.getId()==null)plan.setId(getAId());
		String sql=sqlByBean.getUpdateSql(plan);
		return excute(sql);
	}

	@Override
	public boolean del(PlanLogDetails plan) {
		String sql=sqlByBean.getDelSql(plan);
		return excute(sql);
	}
	@Override
	public String insertReturnId(PlanLogDetails plan) {
		if(plan.getId()==null)plan.setId(getAId());
		updateOrInsert(plan);
		return plan.getId();
	}
}
