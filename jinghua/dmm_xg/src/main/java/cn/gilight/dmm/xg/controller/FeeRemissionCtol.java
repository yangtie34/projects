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

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.PageUtils;
import cn.gilight.dmm.xg.service.FeeRemissionService;
import cn.gilight.framework.uitl.common.ExcelUtils;

@Controller
@RequestMapping("feeRemission")
public class FeeRemissionCtol {

	@Resource
	private FeeRemissionService  feeRemissionService;
	@RequestMapping()
	public String init(){
		return "feeRemission";
	}
	/**
	 * 得到学年
	 * @return
	 */
	@RequestMapping("querySchoolYear")
	@ResponseBody
	public List<Map<String , Object>> querySchoolYear() {
		return feeRemissionService.querySchoolYear();
	}
	/**
	 * 得到总的学费减免人数以及金额
	 */
	@RequestMapping("queryJmInfo")
	@ResponseBody
	public List<Map<String, Object>> queryJmInfo(String schoolYear, String edu,String param) {
		return feeRemissionService.queryJmInfo(schoolYear, edu,AdvancedUtil.converAdvancedList(param));
	}
	/**
	 * 得到学费减免分布
	 */
	@RequestMapping("queryJmfb")
	@ResponseBody
	public Map<String, Object> queryJmfb(String schoolYear, String edu,String fb,String param) {
		return feeRemissionService.queryJmfb(schoolYear, edu,fb,AdvancedUtil.converAdvancedList(param));
	}

	/**
	 * 得到历年学费减免变化
	 */
	@RequestMapping("queryYearJmfb")
	@ResponseBody
	public Map<String, Object> queryYearJmfb(String bh,String param,String edu) {
		return feeRemissionService.queryYearJmfb(bh,AdvancedUtil.converAdvancedList(param),edu);//修改此处
	}
}
