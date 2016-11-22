package cn.gilight.personal.student.main.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jhnu.syspermiss.util.ContextHolderUtils;
import cn.gilight.personal.po.TStuComm;
import cn.gilight.personal.student.main.service.StudentInfoService;

/**   
* @Description: 学生个人信息服务
* @author Sunwg  
* @date 2016年4月26日 下午3:39:54   
*/
@Controller("studentMainController")
@RequestMapping("/student/main")
public class StudentMainController {
	private Logger log = Logger.getLogger(StudentMainController.class);
	
	@Resource
	private StudentInfoService studentService;
	
	/** 
	* @Description: 获取当前登录学生基础信息 
	* @return Map<String,Object>
	*/
	@RequestMapping("/getSelfInfo")
	@ResponseBody
	public Map<String, Object> getSelfInfo() {
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug("学生" + username +"登录了系统" );
		return studentService.getStudentSimpleInfo(username);
	}
	
	/** 
	* @Description: 获取当前登录学生详细信息
	* @return Map<String,Object>
	*/
	@RequestMapping("/getSelfInfoDetial")
	@ResponseBody
	public Map<String, Object> getSelfInfoDetial(){
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		//详细信息
		return studentService.getStudentDetailInfo(username);
	}
	
	/** 
	 * @Description: 更新当前登录学生的手机号
	 * @return boolean
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	*/
	@RequestMapping("/updateTelOfStudent")
	@ResponseBody
	@Transactional
	public boolean updateTelOfStudent(String tel) throws SecurityException, NoSuchFieldException{
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		boolean result = studentService.saveOrUpdateStudentTel(username, tel);
		return result;
	}
}