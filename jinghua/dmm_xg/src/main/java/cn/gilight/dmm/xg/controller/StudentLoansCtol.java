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

import com.alibaba.fastjson.JSONObject;

import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.PageUtils;
import cn.gilight.dmm.xg.service.StudentLoansService;
import cn.gilight.framework.uitl.common.ExcelUtils;

/**
 * 助学贷款
 *
 */
@Controller
@RequestMapping("studentLoans")
public class StudentLoansCtol {

	@Resource
	private StudentLoansService studentLoansService;
	@RequestMapping()
	public String init(){
		return "studentLoans";
	}
	/**
	 * 得到学年
	 * @return
	 */
	@RequestMapping("querySchoolYear")
	@ResponseBody
	public List<Map<String , Object>> querySchoolYear() {
		return studentLoansService.querySchoolYear();
	}
	/**
	 * 得到总的助学贷款人数以及金额
	 */
	@RequestMapping("queryZxInfo")
	@ResponseBody
	public List<Map<String, Object>> queryZxInfo(String schoolYear, String id,String pid) {
		return studentLoansService.queryZxInfo(schoolYear, id,pid);
	}
	/**
	 * 查询助学金贷款学生行为
	 */
	@RequestMapping("queryZxxw")
	@ResponseBody
	public Map<String, Object> queryZxxw(String schoolYear, String edu,String param) {
		return studentLoansService.queryZxxw(schoolYear, edu,AdvancedUtil.converAdvancedList(param));
	}
	/**
	 * 助学贷款分布
	 */
	@RequestMapping("queryZxfb")
	@ResponseBody
	public Map<String, Object> queryZxfb(String schoolYear, String edu,String fb,String param) {
		return studentLoansService.queryZxfb(schoolYear, edu,fb,AdvancedUtil.converAdvancedList(param));
	}

	/**
	 * 查询历年助学贷款变化
	 */
	@RequestMapping("queryYearZxfb")
	@ResponseBody
	public Map<String, Object> queryYearZxfb(String bh,String param) {
		return studentLoansService.queryYearZxfb(bh,AdvancedUtil.converAdvancedList(param));
	}
}
