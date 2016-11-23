package com.jhnu.system.task;


/*
@Service
public class InitTask implements InitializingBean, ServletContextAware{

	@Autowired
	private PlanService PlanService;
	@Autowired
	TaskBaseService taskBaseService;
	
	private static final Logger logger = Logger.getLogger(InitTask.class);
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		logger.info("===============初始化任务===================");
		 List<Plan> plans=taskBaseService.getPlans();
		for(int i=0;i<plans.size();i++){
			try {
				taskBaseService.addJob(plans.get(i));
			} catch (SchedulerException  e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
	}
	
} */
public class InitTask {
	
}
