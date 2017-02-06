package cn.gilight.dmm.teaching.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.teaching.service.FailExaminationService;

/**
 * 挂科补考分析
 * @author lijun
 *
 */
@Controller
@RequestMapping("failExamination")
public class FailExaminationCtol {

	@Resource
	private FailExaminationService  failExaminationService;
	@RequestMapping()
	public String init(){
		return "failExamination";
	}
	/**
	 * 得到下拉框内容 学年 本专科 
	 * @return
	 */
	@RequestMapping("querySelectType")
	@ResponseBody
	public Map<String, Object> querySelectType(){
		
		return failExaminationService.querySelectType();
	}
	/**
	 * 挂科基本信息(挂科人数 挂科率 环比变化  平均挂科数)
	 * @param param 高级搜索
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param edu 本专科
	 * @return
	 */
	@RequestMapping("getGkInfo")
	@ResponseBody
	public Map<String, Object> getGkInfo(String param, String schoolYear, String termCode, String edu){
		return failExaminationService.getGkInfo(AdvancedUtil.converAdvancedList(param), schoolYear, termCode, edu);
	}
	/**
	 * 挂科分类信息(学生类别 人数 挂科率 变化)
	 * @param param 高级搜索
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param edu 本专科
	 * @return
	 */
	@RequestMapping("getGkflInfo")
	@ResponseBody
	public List<Map<String, Object>> getGkflInfo(String param, String schoolYear, String termCode, String edu){
		return failExaminationService.getGkflInfo(AdvancedUtil.converAdvancedList(param), schoolYear, termCode, edu);
	}
	/**
	 * 各年级挂科分布(人数 人均挂科数)
	 * @param param 高级搜索
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param edu 本专科
	 * @return
	 */
	@RequestMapping("getNjGkInfo")
	@ResponseBody
	public List<Map<String, Object>> getNjGkInfo(String param, String schoolYear, String termCode, String edu){
		return failExaminationService.getNjGkInfo(AdvancedUtil.converAdvancedList(param), schoolYear, termCode, edu);
	}
	/**
	 * 男女生挂科分布(人数 人均挂科数)
	 * @param param 高级搜索
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param edu 本专科
	 * @return
	 */
	@RequestMapping("getXbGkInfo")
	@ResponseBody
	public List<Map<String, Object>> getXbGkInfo(String param, String schoolYear, String termCode, String edu){
		return failExaminationService.getXbGkInfo(AdvancedUtil.converAdvancedList(param), schoolYear, termCode, edu);
	}
	/**
	 * 挂科课程分布--公共课/专业课(人数 人均挂科数)
	 * @param param 高级搜索
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param edu 本专科
	 * @return
	 */
	@RequestMapping("getNatKcGkInfo")
	@ResponseBody
	public List<Map<String, Object>> getNatKcGkInfo(String param, String schoolYear, String termCode, String edu){
		return failExaminationService.getNatKcGkInfo(AdvancedUtil.converAdvancedList(param), schoolYear, termCode, edu);
	}
	/**
	 * 挂科课程分布--必修课/选修课(人数 人均挂科数)
	 * @param param 高级搜索
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param edu 本专科
	 * @return
	 */
	@RequestMapping("getAttrKcGkInfo")
	@ResponseBody
	public List<Map<String, Object>> getAttrKcGkInfo(String param, String schoolYear, String termCode, String edu){
		return failExaminationService.getAttrKcGkInfo(AdvancedUtil.converAdvancedList(param), schoolYear, termCode, edu);
	}
	/**
	 * 各机构挂科分布(人数 人均挂科数)
	 * @param param 高级搜索
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param edu 本专科
	 * @return
	 */
	@RequestMapping("getJgGkInfo")
	@ResponseBody
	public Map<String, Object> getJgGkInfo(String param, String schoolYear, String termCode, String edu){
		return failExaminationService.getJgGkInfo(AdvancedUtil.converAdvancedList(param), schoolYear, termCode, edu);
	}
	/**
	 * 挂科top(人数 人均挂科数)
	 * @param param 高级搜索
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param edu 本专科
	 * @param lx  (课程 教师 专业 班级)
	 * @return
	 */
	@RequestMapping("getTopGkInfo")
	@ResponseBody
	public List<Map<String, Object>> getTopGkInfo(String param, String schoolYear, String termCode, String edu,String lx,String gkSort,String turnPage){
		return failExaminationService.getTopGkInfo(AdvancedUtil.converAdvancedList(param), schoolYear, termCode, edu,lx,gkSort,turnPage);
	}
	/**
	 * 学生挂科top(人数 人均挂科数)
	 * @param param 高级搜索
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param edu 本专科
	 * @param lx  (课程 教师 专业 班级)
	 * @return
	 */
	@RequestMapping("getStuTopGkInfo")
	@ResponseBody
	public List<Map<String, Object>> getStuTopGkInfo(String param, String schoolYear, String termCode, String edu,String gkStuSort,String turnStuPage){
		return failExaminationService.getStuTopGkInfo(AdvancedUtil.converAdvancedList(param), schoolYear, termCode, edu,gkStuSort,turnStuPage);
	}
	/**
	 * 人均补考top(人数 人均挂科数)
	 * @param param 高级搜索
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param edu 本专科
	 * @param lx  (课程 任课教师 专业)
	 * @return
	 */
	@RequestMapping("getTopbkInfo")
	@ResponseBody
	public List<Map<String, Object>> getTopbkInfo(String param, String schoolYear, String termCode, String edu,String lx,String bkTopSort,String bkturnPage){
		return failExaminationService.getTopbkInfo(AdvancedUtil.converAdvancedList(param), schoolYear, termCode, edu,lx, bkTopSort,bkturnPage);
	}	
}
