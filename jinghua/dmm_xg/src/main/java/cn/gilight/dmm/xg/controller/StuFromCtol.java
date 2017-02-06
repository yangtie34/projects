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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.PageUtils;
import cn.gilight.dmm.xg.service.StuFromService;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.ExcelUtils;

@Controller
@RequestMapping("stuFrom")
public class StuFromCtol {
	@Resource
	private StuFromService stuFromService;
	@RequestMapping()
	public String init() {
		return "stuFrom";
	}
	@RequestMapping("getMinGrade")
	@ResponseBody
	public Map<String, Object> getMinGrade(String param) {
		return stuFromService.getMinGrade();
	}
	@RequestMapping("getStuEdu")
	@ResponseBody
	public List<Map<String, Object>> getStuEdu(String param) {
		return stuFromService.getStuEdu();
	}
	@RequestMapping("getStuFrom")
	@ResponseBody
	public Map<String, Object> getStuFrom(String param, String from, String to,
			String edu, String divid,Boolean updown,int pagesize,int index) {
		return stuFromService.getStuFrom(AdvancedUtil.converAdvancedList(param), from, to, edu, divid,updown,pagesize,index);
	}
	@RequestMapping("getCountLine")
	@ResponseBody
	public Map<String, Object> getCountLine(String param, String from, String to,
			String edu, String divid) {
		return stuFromService.getCountLine(AdvancedUtil.converAdvancedList(param), from,to, edu, divid);
	}
	@RequestMapping("getSchoolTag")
	@ResponseBody
	public Map<String, Object> getSchoolTag(String param, String from, String to,
			String edu, String divid,Boolean updown,int pagesize,int index,Boolean Order) {
		return stuFromService.getSchoolTag(AdvancedUtil.converAdvancedList(param), from,to, edu, divid,updown,pagesize,index,Order);
	}
	@RequestMapping("getGrowth")
	@ResponseBody
	public Map<String, Object> getGrowth(String param, String from, String to,
			String edu, String divid,Boolean updown) {
		return stuFromService.getGrowth(AdvancedUtil.converAdvancedList(param), from,to, edu, divid,updown);
	}
	@RequestMapping("getStuFromTab")
	@ResponseBody
	public Map<String, Object> getStuFromTab(String param, String from, String to,
			String edu, String divid,Boolean updown,int pagesize,int index) {
		return stuFromService.getStuFromTab(AdvancedUtil.converAdvancedList(param), from,to, edu, divid,updown,pagesize,index);
	}
	@RequestMapping("getCountAndLv")
	@ResponseBody
	public Map<String, Object> getCountAndLv(String param,String from,String to,
			String edu, String divid) {
		return stuFromService.getCountAndLv(AdvancedUtil.converAdvancedList(param),from,to,edu,divid);
	}
	
}
