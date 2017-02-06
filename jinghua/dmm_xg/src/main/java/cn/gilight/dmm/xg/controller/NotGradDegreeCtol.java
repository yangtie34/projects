package cn.gilight.dmm.xg.controller;

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
import cn.gilight.dmm.xg.service.NotGradDegreeService;
import cn.gilight.framework.uitl.common.ExcelUtils;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("notGradDegree")
public class NotGradDegreeCtol {
	@Resource
	private NotGradDegreeService  notGradDegreeService;
	@RequestMapping()
	public String init(){
		return "notGradDegree";
	}
	/**
	 * 得到在籍无法毕业人数和无学位人数
	 */
	@RequestMapping("queryXwInfo")
	@ResponseBody
	public Map<String, Object> queryXwInfo(String param) {
		return notGradDegreeService.queryXwInfo(AdvancedUtil.converAdvancedList(param));
	}
	/**
	 * 得到在籍无法毕业人数和无学位人数
	 */
	@RequestMapping("queryXwfbAndRatio")
	@ResponseBody
	public Map<String, Object> queryXwfbAndRatio(String fb,String param) {
		return notGradDegreeService.queryXwfbAndRatio(fb,AdvancedUtil.converAdvancedList(param));
	}
	/**
	 * 得到在籍无法毕业和无学位类型
	 */
	@RequestMapping("getNoDegreeType")
	@ResponseBody
	public List<Map<String, Object>> getNoDegreeType() {
		return notGradDegreeService.getNoDegreeType();
	}
	/**
	 * 无学位学生学科分布
	 */
	@RequestMapping("queryXkfb")
	@ResponseBody
	public List<Map<String, Object>> queryXkfb(String param) {
		return notGradDegreeService.queryXkfb(AdvancedUtil.converAdvancedList(param));
	}
	/**
	 * 无学位学生年级分布
	 */
	@RequestMapping("queryNjfb")
	@ResponseBody
	public List<Map<String, Object>> queryNjfb(String param) {
		return notGradDegreeService.queryNjfb(AdvancedUtil.converAdvancedList(param));
	}
	/**
	 * 无学位学生性别分布
	 */
	@RequestMapping("queryXbfb")
	@ResponseBody
	public List<Map<String, Object>> queryXbfb(String param) {
		return notGradDegreeService.queryXbfb(AdvancedUtil.converAdvancedList(param));
	}
	/**
	 * 无学位学生院系分布
	 */
	@RequestMapping("queryYyfb")
	@ResponseBody
	public List<Map<String, Object>> queryYyfb(String param) {
		return notGradDegreeService.queryYyfb(AdvancedUtil.converAdvancedList(param));
	}
	/**
	 * 最近几年无学位学生原因分布
	 */
	@RequestMapping("queryStatefbByYear")
	@ResponseBody
	public Map<String, Object> queryStatefbByYear(String param) {
		return notGradDegreeService.queryStatefbByYear(AdvancedUtil.converAdvancedList(param));
	}
	/**
	 * 最近几年无学位学生学科分布
	 */
	@RequestMapping("queryXkfbByYear")
	@ResponseBody
	public Map<String, Object> queryXkfbByYear(String param) {
		return notGradDegreeService.queryXkfbByYear(AdvancedUtil.converAdvancedList(param));
	}
	/**
	 * 最近几年无学位学生年级分布
	 */
	@RequestMapping("queryNjfbByYear")
	@ResponseBody
	public Map<String, Object> queryNjfbByYear(String param) {
		return notGradDegreeService.queryNjfbByYear(AdvancedUtil.converAdvancedList(param));
	}
	/**
	 * 最近几年无学位学生性别分布
	 */
	@RequestMapping("queryXbfbByYear")
	@ResponseBody
	public Map<String, Object> queryXbfbByYear(String param) {
		return notGradDegreeService.queryXbfbByYear(AdvancedUtil.converAdvancedList(param));
	}
	
}
