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



import com.alibaba.fastjson.JSONObject;

import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.PageUtils;
import cn.gilight.dmm.teaching.service.BysQxService;
import cn.gilight.framework.uitl.common.ExcelUtils;

@Controller
@RequestMapping("bysQx")
public class BysQxCtol {
	
	@Resource
	private BysQxService bysQxService;
	
	@RequestMapping()
	public String init(){
		return "bysQx";
	}
	
	@RequestMapping("/getTimeList")
	@ResponseBody
	public List<Map<String, Object>>  getTimeList(){
		return bysQxService.getTimeList();
	}

	@RequestMapping("/getBysQxFb")
	@ResponseBody
	public List<Map<String, Object>>  getBysQxFb(String param,String schoolYear){
		return bysQxService.getBysQxFb(AdvancedUtil.converAdvancedList(param),schoolYear);
	}
	
	@RequestMapping("/getBysQxSzFb")
	@ResponseBody
	public List<Map<String, Object>>  getBysQxSzFb(String param,String schoolYear){
		return bysQxService.getBysQxSzFb(AdvancedUtil.converAdvancedList(param),schoolYear);
	}
	
	@RequestMapping("/getLnBysQxfb")
	@ResponseBody
	public Map<String, Object>  getLnBysQxfb(String param,String schoolYear){
		return bysQxService.getLnBysQxfb(AdvancedUtil.converAdvancedList(param),schoolYear);
	}
	
	@RequestMapping("/getLnBysSzQxfb")
	@ResponseBody
	public Map<String, Object>  getLnBysSzQxfb(String param,String schoolYear){
		return bysQxService.getLnBysSzQxfb(AdvancedUtil.converAdvancedList(param),schoolYear);
	}
	
	@RequestMapping("/getLnReasonfb")
	@ResponseBody
	public Map<String, Object>  getLnReasonfb(String param,String schoolYear){
		return bysQxService.getLnReasonfb(AdvancedUtil.converAdvancedList(param),schoolYear);
	}
	
	@RequestMapping("/getScaleByDept")
	@ResponseBody
	public Map<String, Object>  getScaleByDept(String param,String schoolYear){
		return bysQxService.getScaleByDept(AdvancedUtil.converAdvancedList(param),schoolYear);
	}
	
}
