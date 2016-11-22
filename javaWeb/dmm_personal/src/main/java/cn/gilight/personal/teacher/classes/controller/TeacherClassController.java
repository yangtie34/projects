package cn.gilight.personal.teacher.classes.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jhnu.syspermiss.util.ContextHolderUtils;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.personal.teacher.classes.service.TeacherClassService;
 
/**   
* @Description: TODO 教职工班级管理
* @author Sunwg  
* @date 2016年3月30日 上午9:52:20   
*/
@Controller("teacherClassController")
@RequestMapping("/teacher/classes")
public class TeacherClassController {
	private Logger log = Logger.getLogger(TeacherClassController.class);
	
	@Resource
	private BaseDao baseDao;
	
	@Resource
	private TeacherClassService classService;
	
	@RequestMapping("/queryClassesTotalInfo")
	@ResponseBody
	public Map<String, Object> queryClassesTotalInfo() {
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username);
		return classService.queryClassesTotalInfo(username);
	}
	
	@RequestMapping("/queryClassList")
	@ResponseBody
	public List<Map<String, Object>> queryClassList() {
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		return classService.queryClassList(username);
	}
	
	@RequestMapping("/queryStudentsList")
	@ResponseBody
	public List<Map<String, Object>> queryStudentsList() {
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		return classService.queryStudentsList(username);
	}
	
	@RequestMapping("/queryClassInfo")
	@ResponseBody
	public Map<String, Object> queryClassInfo(String classId) {
		return classService.queryClassInfo(classId);
	}
	
	@RequestMapping("/queryStudentsListOfClass")
	@ResponseBody
	public List<Map<String, Object>> queryStudentsListOfClass(String classId) {
		return classService.queryStudentsListOfClass(classId);
	}
}