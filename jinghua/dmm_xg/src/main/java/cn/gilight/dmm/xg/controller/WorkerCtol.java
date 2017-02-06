package cn.gilight.dmm.xg.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.xg.service.WorkerService;

/**
 * 学生工作者
 * 
 * @author xuebl
 * @date 2016年4月18日 下午5:10:48
 */
@Controller
@RequestMapping("worker")
public class WorkerCtol {
	
	@Resource
	private WorkerService workerService;
	
	@RequestMapping()
	public String init(){
		return "worker";
	}

	@RequestMapping("getWorker")
	@ResponseBody
	public Map<String, Object> getWorker(String param){
		return workerService.getWorker(AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getWorkerDistribute")
	@ResponseBody
	public Map<String, Object> getWorkerDistribute(String param){
		return workerService.getWorkerDistribute(AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getInstructorsDistribute")
	@ResponseBody
	public Map<String, Object> getInstructorsDistribute(String param){
		return workerService.getInstructorsDistribute(AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getOrganizationInstructorsStuRatio")
	@ResponseBody
	public List<Map<String, Object>> getOrganizationInstructorsStuRatio(String param, Integer schoolYear){
		return workerService.getOrganizationInstructorsStuRatio(AdvancedUtil.converAdvancedList(param), schoolYear);
	}
	
	private final static String MV_TEST = "/test";
	@RequestMapping(MV_TEST)
	public String Test(){
		DevUtils.p(" test xg ");
		return MV_TEST;
	}
	
}
