package com.jhnu.system.task.jobs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.framework.entity.JobResultBean;
import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.system.permiss.service.PasswordHelper;
import com.jhnu.system.task.dao.impl.PlanLogDaoDetailsImpl;
import com.jhnu.system.task.entity.Group;
import com.jhnu.system.task.entity.Plan;
import com.jhnu.system.task.entity.PlanLog;
import com.jhnu.system.task.entity.PlanLogDetails;
import com.jhnu.system.task.entity.PlanWork;
import com.jhnu.system.task.entity.Verify;
import com.jhnu.system.task.entity.Work;
import com.jhnu.system.task.entity.WorkVerify;
import com.jhnu.system.task.service.GroupService;
import com.jhnu.system.task.service.PlanLogDetailsService;
import com.jhnu.system.task.service.PlanLogService;
import com.jhnu.system.task.service.PlanService;
import com.jhnu.system.task.service.PlanWorkService;
import com.jhnu.system.task.service.VerifyService;
import com.jhnu.system.task.service.WorkService;
import com.jhnu.system.task.service.WorkVerifyService;
import com.jhnu.util.common.HttpUtil;

@DisallowConcurrentExecution
public class PublicJob implements Job {
	@Autowired
	private PlanLogService PlanLogService;
	@Autowired
	private PlanLogDetailsService PlanLogDetailsService;
	@Autowired
	private PlanService PlanService;
	@Autowired
	private PlanWorkService PlanWorkService;
	@Autowired
	private VerifyService VerifyService;
	@Autowired
	private WorkService WorkService;
	@Autowired
	private GroupService GroupService;
	@Autowired
	private WorkVerifyService WorkVerifyService;
	@Autowired
	private PasswordHelper passwordHelper;
	@Autowired
	private PlanLogDaoDetailsImpl planLogDetailsDao;
	@Override
	public void execute(JobExecutionContext context) {
		Plan plan = (Plan) context.getMergedJobDataMap().get("plan");
		String planId=plan.getId();
		//  先插入开始执行的日志，日志名字就是jihuaName，插入完后返回保存日志的ID
		PlanLog planLog=new PlanLog();
		planLog.setPlanId(planId);planLog.setStartTime(getDate());
		planLog.setEndTime("");
		planLog.setIsYes(2);
		String planLogId="";
		boolean planIsTrue=true;
		try {
			planLogId=PlanLogService.insertReturnId(planLog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//  通过计划ID查询业务
		try{
			List<PlanWork> planWorks=PlanWorkService.getPlanWorksByPlanId(planId);
			for(int i=0;i<planWorks.size();i++){
				Work work=WorkService.getWorkById(planWorks.get(i).getWorkId());
				if(work.getIsTrue()==1){
					PlanLogDetails planLogDetailsWork=new PlanLogDetails();
					planLogDetailsWork.setStartTime(getDate());
					planLogDetailsWork.setLogType("work");
					planLogDetailsWork.setLogTypeId(work.getId());
					planLogDetailsWork.setPlanLogId(planLogId);
					//  通过业务ID，获取业务验证。
					List<WorkVerify> workVerifys=WorkVerifyService.getWorkVerifysByWorkId(work.getId());
					boolean workVerify=true;
					for(int j=0;j<workVerifys.size();j++){
						Verify verify=VerifyService.getVerifyById(workVerifys.get(j).getVerifyId());
						if(verify.getIsTrue()==1){
							PlanLogDetails planLogDetailsVerify=new PlanLogDetails();
							planLogDetailsVerify.setStartTime(getDate());
							planLogDetailsVerify.setLogType("verify");
							planLogDetailsVerify.setLogTypeId(verify.getId());
							planLogDetailsVerify.setPlanLogId(planLogId);
							planLogDetailsVerify.setIsYes(3);
							planLogDetailsVerify.setEndTime("");
							planLogDetailsVerify.setId(planLogDetailsDao.getAId());
							planLogDetailsVerify.setCheck(passwordHelper.simpleEncryptPassword(planLogDetailsVerify.getStartTime(), planLogDetailsVerify.getId()));
							planLogDetailsVerify.setResultDesc("正在进入该验证系统...");
							String planLogDetailId=PlanLogDetailsService.insertReturnId(planLogDetailsVerify);
							
							//  通过验证类型，获取验证服务的URL。
							//  通过验证中的验证代码serverName?monhedName去映射，验证有返回内容。
							try{
								forHttp(GroupService.getGroupById(verify.getGroup_()),verify.getService(),planLogDetailId);
							}catch(Exception e){
								e.printStackTrace();
							}finally{
								planLogDetailsVerify=PlanLogDetailsService.getPlanLogDetailsById(planLogDetailId);
								if(planLogDetailsVerify.getIsYes()==3){
									planLogDetailsVerify.setResultDesc("失败原因：找不到对应的验证服务");
									planLogDetailsVerify.setIsYes(0);
									planLogDetailsVerify.setEndTime(getDate());
									PlanLogDetailsService.updateOrInsert(planLogDetailsVerify);
									workVerify=false;
								}else if(planLogDetailsVerify.getIsYes()==0){
									workVerify=false;
								}else if(planLogDetailsVerify.getIsYes()==2){
									planLogDetailsVerify.setResultDesc("失败原因：验证过程中被关闭");
									planLogDetailsVerify.setIsYes(0);
									planLogDetailsVerify.setEndTime(getDate());
									PlanLogDetailsService.updateOrInsert(planLogDetailsVerify);
									workVerify=false;
								}
								if(workVerifys.get(j).getRule()==0){
									workVerify=true;
								}
							}
						}
					}
					
					if(workVerify){
						//  通过业务类型，获取业务服务的URL。
						//  通过业务中的业务代码serverName?monhedName去映射。
						planLogDetailsWork.setIsYes(3);
						planLogDetailsWork.setId(planLogDetailsDao.getAId());
						planLogDetailsWork.setCheck(passwordHelper.simpleEncryptPassword(planLogDetailsWork.getStartTime(), planLogDetailsWork.getId()));
						planLogDetailsWork.setResultDesc("正在进入该业务系统...");
						planLogDetailsWork.setEndTime("");
						String planLogDetailId=PlanLogDetailsService.insertReturnId(planLogDetailsWork);
						try{
							forHttp(GroupService.getGroupById(work.getGroup_()),work.getService(),planLogDetailId);
						}catch(Exception e){
							e.printStackTrace();
							
						}finally{
							planLogDetailsWork=PlanLogDetailsService.getPlanLogDetailsById(planLogDetailId);
							if(planLogDetailsWork.getIsYes()==3){
								planLogDetailsWork.setResultDesc("失败原因：找不到对应的业务服务");
								planLogDetailsWork.setIsYes(0);
								planLogDetailsWork.setEndTime(getDate());
								PlanLogDetailsService.updateOrInsert(planLogDetailsWork);
								planIsTrue=false;
							}else if(planLogDetailsWork.getIsYes()==0){
								planIsTrue=false;
							}else if(planLogDetailsWork.getIsYes()==2){
								planLogDetailsWork.setResultDesc("失败原因：业务系统执行中关闭");
								planLogDetailsWork.setIsYes(0);
								planLogDetailsWork.setEndTime(getDate());
								PlanLogDetailsService.updateOrInsert(planLogDetailsWork);
								planIsTrue=false;
							}
						}
					}else{
						planLogDetailsWork.setResultDesc("失败原因：验证错误停止运行");
						planLogDetailsWork.setIsYes(0);
						planLogDetailsWork.setEndTime(getDate());
						PlanLogDetailsService.updateOrInsert(planLogDetailsWork);
						planIsTrue=false;
					}
					
				}
			}
			if(planIsTrue){
				planLog.setEndTime(getDate());
				planLog.setIsYes(1);
			}else{
				planLog.setEndTime(getDate());
				planLog.setIsYes(0);
			}
		}catch(Exception e){
			e.printStackTrace();
			planLog.setEndTime(getDate());
			planLog.setIsYes(0);
		}
		try {
			PlanLogService.updateOrInsert(planLog);
		} catch (ParamException e1) {
			e1.printStackTrace();
		}
	}
	
	public String getDate(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		return df.format(new Date());// new Date()为获取当前系统时间
		
	}
	public JobResultBean forHttp(Group group,String service,String planLogDetailId){
		String services[]=service.split("\\?");
		JobResultBean jobResultBean=new JobResultBean();
		//此处不再获取JOB的执行结果。	
		HttpUtil.rpcJobPost(group.getUrl_(),services[0],services[1],planLogDetailId);
		
	//	 jobResultBean=(JobResultBean) BeanMapUtil.toBean(JobResultBean.class, (Map<?,?>)HttpUtil.rpcJobPost(group.getUrl_(),services[0],services[1],planLogDetailId) );
		return jobResultBean;
	}
	
}
