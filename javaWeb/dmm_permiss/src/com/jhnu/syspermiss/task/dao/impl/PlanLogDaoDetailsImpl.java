package com.jhnu.syspermiss.task.dao.impl;

import java.util.Map;

import com.jhnu.syspermiss.task.dao.PlanLogDetailsDao;
import com.jhnu.syspermiss.task.entity.PlanLogDetails;
import com.jhnu.syspermiss.util.PasswordHelperUtil;
import com.jhnu.syspermiss.util.jdbcUtil.BaseDao;

public class PlanLogDaoDetailsImpl  implements PlanLogDetailsDao {
	
	private PlanLogDaoDetailsImpl() {
		
	}  
    private static PlanLogDaoDetailsImpl planLogDaoDetailsImpl=null;
	
	public static PlanLogDaoDetailsImpl getInstance() {
		if (planLogDaoDetailsImpl == null){
			synchronized (new String()) {
				if (planLogDaoDetailsImpl == null){
					planLogDaoDetailsImpl = new PlanLogDaoDetailsImpl();
				}
			}
		}
		return planLogDaoDetailsImpl;
	}

	private BaseDao baseDao=BaseDao.getInstance();
	
	@Override
	public void updateOrInsert(PlanLogDetails planLogDetails) {
		String sql="update t_sys_schedule_plan_log_detail set isyes=?,result_desc=?,endtime=? where id=?";
		baseDao.excute(sql,new Object[]{planLogDetails.getIsYes(),planLogDetails.getResultDesc(),planLogDetails.getEndTime(),planLogDetails.getId()});
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isCheck(String id) {
		String sql = "select * from t_sys_schedule_plan_log_detail t where t.id = ?";
		Map<String,String> planLogDetails=(Map<String, String>) baseDao.queryForList(sql.toString(),new Object[]{id}).get(0);
		String check=PasswordHelperUtil.simpleEncryptPassword(planLogDetails.get("STARTTIME"),id);
		if(check.equalsIgnoreCase(planLogDetails.get("CHECK_"))){
			 sql="update t_sys_schedule_plan_log_detail set check_='' where id=?";
			baseDao.excute(sql,new Object[]{id});
			return true;
		}
		return false;
	}
	
}
