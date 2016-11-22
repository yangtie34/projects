package cn.gilight.personal.teacher.warning.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.ContextHolderUtils;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.personal.teacher.warning.service.TeacherWarningService;
 
/**   
* @Description: TODO 学生预警管理
* @author Sunwg  
* @date 2016年4月1日 上午10:02:05   
*/
@Controller("teacherWarningController")
@RequestMapping("/teacher/warning")
public class TeacherWarningController {
	private Logger log = Logger.getLogger(TeacherWarningController.class);
	
	@Resource
	private BaseDao baseDao;
	
	@Resource
	private TeacherWarningService warningService;
	
	/** 
	* @Description: TODO 查询传入月数内的消费异常人数
	* @param @param months 月数
	* @return Map<String,Object>
	*/
	@RequestMapping("/queryConsumeNums")
	@ResponseBody
	public Map<String, Object> queryConsumeNums(int months) {
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		return warningService.queryConsumeNums(username,months);
	}
	
	/** 
	* @Description: 查询学业异常人数
	* @param @param xn
	* @param @param xq
	* @return List<Map<String,Object>>
	*/
	@RequestMapping("/queryStudyNums")
	@ResponseBody
	public List<Map<String, Object>> queryStudyNums(String xn,String xq){
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		List<Map<String, Object>> result = warningService.queryStudyNums(username,xn,xq);
		return result;
	}
	
	/*==================学生消费处理=====================*/
	
	/** 
	* @Description: 查询男女生日均消费值
	* @return Map<String,Object>
	*/
	@RequestMapping("/queryAvgDayConsume")
	@ResponseBody
	public Map<String,Object> queryAvgDayConsume(){
		return warningService.queryAvgDayConsume();
	}
	
	
	/** 
	* @Description: 查询指定月数内的高消费|低消费学生列表
	* @param @param months 月数
	* @param @param type ： high|low 高消费|低消费
 	* @return List<Map<String,Object>>
	*/
	@RequestMapping("/queryXfycStudentsList")
	@ResponseBody
	public List<Map<String, Object>> queryXfycStudentsList(int months,String type){
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		if(type.equals("high")){
			result = warningService.queryXfycStudentsList(username,months,0);
		}else if(type.equals("low")){
			result = warningService.queryXfycStudentsList(username,months,0);
		}
		return result;
	}
	
	/*==================住宿异常页面===================*/
	
	/** 
	* @Description: 查询住宿异常人数
	* @return Map<String,Object>
	*/
	@RequestMapping("/queryStayNums")
	@ResponseBody
	public Map<String,Object> queryStayNums(){
		log.debug("查询学生住宿异常人数，晚归和疑似不在校");
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		String date = DateUtils.getYesterday();
		Map<String, Object> result = warningService.queryStayNums(username,date);
		return result;
	}
	
	/** 
	* @Description: 查询晚归学生名单
	* @return List<Map<String,Object>>
	*/
	@RequestMapping("/queryLateStudentsList")
	@ResponseBody
	public List<Map<String, Object>>queryLateStudentsList(){
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		String date = DateUtils.getYesterday();
		List<Map<String, Object>> result = warningService.queryLateStudentsList(username,date);
		return result; 
    }
	
	/** 
	* @Description: 查询疑似不在校学生名单
	* @return List<Map<String,Object>>
	*/
    @RequestMapping("/queryOutStudentList")
	@ResponseBody
	public List<Map<String, Object>>queryOutStudentList(){
    	AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		String date = DateUtils.getYesterday();
		List<Map<String, Object>> result = warningService.queryOutStudentList(username, date);
		return result; 
    }

    /** 
     * @Description: 查询成绩异常学生名单
     * @return List<Map<String,Object>>
     */
    @RequestMapping("/queryCourseFailStudents")
    @ResponseBody
    public List<Map<String, Object>>queryCourseFailStudents(String xn,String xq,String bjid){
    	List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
    	result = warningService.queryCourseFailStudents(xn, xq, bjid);
    	return result; 
    }
}