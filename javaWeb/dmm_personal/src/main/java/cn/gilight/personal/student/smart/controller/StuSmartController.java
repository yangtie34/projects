package cn.gilight.personal.student.smart.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jhnu.syspermiss.util.ContextHolderUtils;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.personal.po.TStu;
import cn.gilight.personal.student.smart.service.StuSmartService;

@Controller("stuSmartController")
@RequestMapping("/student/smart")
public class StuSmartController {
	
	@Resource
	private StuSmartService stuService;
	
	@Resource 
	private HibernateDao hibernate;
	
	/** 
	* @Description: 对比学生和学霸君的成绩对比（取同专业且同年级学霸）
	* @Return: Map<String,Object>
	*/
	@RequestMapping("/compareScore")
	@ResponseBody
	@Transactional
	public List<Map<String, Object>> compareScore(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		TStu student = hibernate.getById(username, TStu.class);
		List<Map<String, Object>> myScoreList = stuService.queryScoreOfStudent(username);
		List<Map<String, Object>> smartScoreList = stuService.queryScoreOfSmartStudent(student.getMajorId(), student.getEnrollGrade());
		for (int i = 0; i < myScoreList.size(); i++) {
			Map<String, Object> myScore = myScoreList.get(i);
			String cno = MapUtils.getString(myScore, "cno");
			for (int j = 0; j < smartScoreList.size(); j++) {
				Map<String, Object> sScore = smartScoreList.get(j);
				if(MapUtils.getString(sScore, "cno").equals(cno)){
					myScore.put("smart_avg",MapUtils.getString(sScore, "avg_score"));
					myScore.put("smart_max",MapUtils.getString(sScore, "max_score"));
				}
			}
		}
		return myScoreList;
	}
	
	/** 
	 * @Description: 对比学生和学霸君的消费对比（取同年级学霸）
	 * @Return: Map<String,Object>
	 */
	@RequestMapping("/compareConsume")
	@Transactional
	@ResponseBody
	public Map<String,Object> compareConsume(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		TStu student = hibernate.getById(username, TStu.class);
		Map<String,Object> myConsume = stuService.queryConsumeOfStudent(username);
		Map<String,Object> smartConsume = stuService.queryConsumeOfSmartStudent(student.getEnrollGrade(), student.getSexCode());
		Map<String, Object> result = new  HashMap<String, Object>();
		result.put("me", myConsume);
		result.put("smart", smartConsume);
		return result;
	}
	
	/** 
	* @Description: 对比学生和学霸君的用餐对比（取同年级学霸）
	* @Return: Map<String,Object>
	*/
	@RequestMapping("/compareDinner")
	@Transactional
	@ResponseBody
	public Map<String,Object> compareDinner(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		TStu student = hibernate.getById(username, TStu.class);
		Map<String,Object> myDinner = stuService.queryDinnerOfStudent(username);
		Map<String,Object> smartDinner = stuService.queryDinnerOfSmartStudent(student.getEnrollGrade(), student.getSexCode());
		Map<String, Object> result = new  HashMap<String, Object>();
		result.put("me", myDinner);
		result.put("smart", smartDinner);
		return result;
	}
	
	/** 
	* @Description: 对比学生和学霸君的借阅对比（取同专业且同年级学霸）
	* @Return: Map<String,Object>
	*/
	@RequestMapping("/compareBook")
	@Transactional
	@ResponseBody
	public Map<String,Object> compareBook(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		TStu student = hibernate.getById(username, TStu.class);
		Map<String,Object> myBook = stuService.queryBookOfStudent(username);
		Map<String,Object> tzyBook = stuService.queryBookOfSmartStudent(student.getMajorId(),student.getEnrollGrade());
		Map<String,Object> tnjBook = stuService.queryBookOfSmartStudent(student.getEnrollGrade());
		Map<String, Object> result = new  HashMap<String, Object>();
		result.put("me", myBook);
		result.put("major", tzyBook);
		result.put("grade", tnjBook);
		return result;
	}
	
	/** 
	* @Description: 对比学生和学霸君的借阅偏好对比（取同专业且同年级学霸）
	* @Return: Map<String,Object>
	*/
	/*@RequestMapping("/compareBookType")
	@Transactional
	@ResponseBody
	public Map<String,Object> compareBookType(){
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		TStu student = hibernate.getById(username, TStu.class);
		Map<String,Object> myBookType = stuService.queryBookTypeOfStudent(username);
		Map<String,Object> tzyBookType = stuService.queryBookTypeOfSmartStudent(student.getMajorId(),student.getEnrollGrade());
		Map<String,Object> tnjBookType = stuService.queryBookTypeOfSmartStudent(student.getEnrollGrade());
		Map<String, Object> result = new  HashMap<String, Object>();
		result.put("me", myBookType);
		result.put("major", tzyBookType);
		result.put("grade", tnjBookType);
		return result;
	}*/
}
