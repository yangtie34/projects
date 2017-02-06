package cn.gilight.dmm.teaching.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.PageUtils;
import cn.gilight.dmm.teaching.service.ScorePredictService;
import cn.gilight.framework.uitl.common.ExcelUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 成绩预测（辅导员）
 * @author lijun
 *
 */
@Controller
@RequestMapping("scorePredict")
public class scorePredictCtol {

	@Resource
	private ScorePredictService scorePredictService;
	@RequestMapping()
	public String init(){
		return "scorePredict";
	}
	/**
	 * 得到学年学期及本专科
	 * @param param
	 * @return
	 */
	@RequestMapping("getYearAndTerm")
	@ResponseBody
	Map<String, Object> getYearAndTerm(String param){
		return scorePredictService.getYearAndTerm();
	}
	/**
	 * 得到筛选课程
	 * @param param
	 * @return
	 */
	@RequestMapping("queryCourseByType")
	@ResponseBody
	Map<String, Object> queryCourseByType(String id,String schoolYear, String termCode ){
		return scorePredictService.queryCourseByType(id,schoolYear,termCode);
	}
	/**
	 * 得到辅导员班级 班级人数以及专业等信息
	 * @param param
	 * @return
	 */
	@RequestMapping("queryGkInfo")
	@ResponseBody
	List<Map<String, Object>> queryGkInfo(String param, String schoolYear, String termCode, String edu,String majorType){
		return scorePredictService.queryGkInfo(AdvancedUtil.converAdvancedList(param),schoolYear,termCode,edu,majorType);
	}
	/**
	 * 成绩预测概况
	 * @return
	 */
	@RequestMapping("queryScoreInfo")
	@ResponseBody
	List<Map<String, Object>> queryScoreInfo(String param,String schoolYear, String termCode, String edu,String courseType,String courseid,String majorType){
		return scorePredictService.queryScoreInfo(AdvancedUtil.converAdvancedList(param),schoolYear,termCode,edu,courseType,courseid,majorType);
	}
	/**
	 * 成绩分布(按课程)
	 * @return
	 */
	@RequestMapping("queryScorefb")
	@ResponseBody
	List<Map<String, Object>> queryScorefb(String param,String schoolYear, String termCode, String edu,String majorType){
		return scorePredictService.queryScorefb(AdvancedUtil.converAdvancedList(param),schoolYear,termCode,edu,majorType);
	}
	/**
	 * 成绩分布(按课程性质)
	 * @return
	 */
	@RequestMapping("queryScorefbByNature")
	@ResponseBody
	List<Map<String, Object>> queryScorefbByNature(String param,String schoolYear, String termCode, String edu,String majorType){
		return scorePredictService.queryScorefbByNature(AdvancedUtil.converAdvancedList(param),schoolYear,termCode,edu,majorType);
	}
}
