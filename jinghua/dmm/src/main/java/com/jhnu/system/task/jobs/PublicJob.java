package com.jhnu.system.task.jobs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.product.four.common.entity.JobResultBean;
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
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("任务成功运行test1Job");
		Plan plan = (Plan) context.getMergedJobDataMap().get("plan");
		String planId=plan.getId();
		String planName=plan.getName_();
		System.out.println("任务:"+planName+"成功运行");
		// TODO 先插入开始执行的日志，日志名字就是jihuaName，插入完后返回保存日志的ID
		PlanLog planLog=new PlanLog();
		planLog.setPlanId(planId);planLog.setStartTime(getDate());
		planLog.setIsYes(0);
		String planLogId=PlanLogService.insertReturnId(planLog);
		// TODO 通过计划ID查询业务
		List<PlanWork> planWorks=PlanWorkService.getPlanWorksByPlanId(planId);
		for(int i=0;i<planWorks.size();i++){
			Work work=WorkService.getWorkById(planWorks.get(i).getWorkId());
			PlanLogDetails planLogDetailsWork=new PlanLogDetails();
			planLogDetailsWork.setStartTime(getDate());
			planLogDetailsWork.setLogType("work");
			planLogDetailsWork.setLogTypeId(work.getId());
			planLogDetailsWork.setPlanLogId(planLogId);
		// TODO 通过业务ID，获取业务验证。
			List<WorkVerify> workVerifys=WorkVerifyService.getWorkVerifysByWorkId(work.getId());
			boolean workVerify=true;
			for(int j=0;j<workVerifys.size();j++){
				Verify verify=VerifyService.getVerifyById(workVerifys.get(j).getVerifyId());
				PlanLogDetails planLogDetailsVerify=new PlanLogDetails();
				planLogDetailsVerify.setStartTime(getDate());
				planLogDetailsVerify.setLogType("verify");
				planLogDetailsVerify.setLogTypeId(verify.getId());
				planLogDetailsVerify.setPlanLogId(planLogId);
		// TODO 通过验证类型，获取验证服务的URL。
		// TODO 通过验证中的验证代码serverName?monhedName去映射，验证有返回内容。
				JobResultBean jobResultBeanVerify=forHttp(GroupService.getGroupById(verify.getGroup_()),verify.getService());
				if(workVerifys.get(j).getRule()==1)workVerify=jobResultBeanVerify.getIsTrue();
				planLogDetailsVerify.setResultDesc(jobResultBeanVerify.getMsg());
				planLogDetailsVerify.setIsYes(jobResultBeanVerify.getIsTrue()?1:0);
				planLogDetailsVerify.setEndTime(getDate());
				planLogDetailsVerify.setIsYes(1);
					try {
						PlanLogDetailsService.updateOrInsert(planLogDetailsVerify);
					} catch (ParamException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
			}
			planLogDetailsWork.setIsYes(0);
			if(workVerify){
			// TODO 通过业务类型，获取业务服务的URL。
			// TODO 通过业务中的业务代码serverName?monhedName去映射。
				JobResultBean jobResultBeanWork=forHttp(GroupService.getGroupById(work.getGroup_()),work.getService());
				planLogDetailsWork.setIsYes(jobResultBeanWork.getIsTrue()?1:0);
				planLogDetailsWork.setResultDesc(jobResultBeanWork.getMsg());
			}
			// TODO 通过将上面的代码，进行捕获异常。异常捕获后，在最后通过上面的日志ID更新日志
			planLogDetailsWork.setEndTime(getDate());
				try {
					PlanLogDetailsService.updateOrInsert(planLogDetailsWork);
				} catch (ParamException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		}
		planLog.setEndTime(getDate());
		planLog.setIsYes(1);
		try {
			PlanLogService.updateOrInsert(planLog);
		} catch (ParamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("任务:"+planName+"运行结束");
	}
	
	public String getDate(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		return df.format(new Date());// new Date()为获取当前系统时间
		
	}
	public JobResultBean forHttp(Group group,String service){
		String services[]=service.split("\\?");
		JobResultBean jobResultBean=new JobResultBean();
		 jobResultBean=(JobResultBean) HttpUtil.rpcPost(group.getId(),services[0],services[1],null);
		return jobResultBean;
	}
	
}
