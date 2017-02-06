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
import cn.gilight.dmm.xg.service.StuEnrollService;
import cn.gilight.framework.uitl.common.ExcelUtils;

@Controller
@RequestMapping("stuEnroll")
public class StuEnrollCtol {
	@Resource
	private StuEnrollService stuEnrollService;
	@RequestMapping()
	public String init(){
		return "stuEnroll";
	}
	@RequestMapping("getStuCountByDept")
	@ResponseBody
	public Map<String, Object> getStuCountByDept(String param){
		return stuEnrollService.getStuCountByDept(AdvancedUtil.converAdvancedList(param));
	}
	@RequestMapping("getStuCountBySex")
	@ResponseBody
	public Map<String, Object> getStuCountBySex(String param){
		return stuEnrollService.getStuCountBySex(AdvancedUtil.converAdvancedList(param));
	}
	@RequestMapping("getStuCountByAge")
	@ResponseBody
	public Map<String, Object> getStuCountByAge(String param){
		return stuEnrollService.getStuCountByAge(AdvancedUtil.converAdvancedList(param));
	}
	@RequestMapping("getContrastByDept")
	@ResponseBody
	public Map<String, Object> getContrastByDept(String param){
		return stuEnrollService.getContrastByDept(AdvancedUtil.converAdvancedList(param));
	}
	@RequestMapping("getContrastByPolitics")
	@ResponseBody
	public Map<String, Object> getContrastByPolitics(String param){
		return stuEnrollService.getContrastByPolitics(AdvancedUtil.converAdvancedList(param));
	}
	@RequestMapping("getStuFrom")
	@ResponseBody
	public Map<String, Object> getStuFrom(String param){
		return stuEnrollService.getStuFrom(AdvancedUtil.converAdvancedList(param));
	}
	@RequestMapping("getStuFromLine")
	@ResponseBody
	public Map<String, Object> getStuFromLine(String param){
		return stuEnrollService.getStuFromLine(AdvancedUtil.converAdvancedList(param));
	}
	@RequestMapping("getStuCountLine")
	@ResponseBody
	public Map<String, Object> getStuCountLine(String param){
		return stuEnrollService.getStuCountLine(AdvancedUtil.converAdvancedList(param));
	}
	@RequestMapping("getStuNationLine")
	@ResponseBody
	public Map<String, Object> getStuNationLine(String param){
		return stuEnrollService.getStuNationLine(AdvancedUtil.converAdvancedList(param));
	}
	@RequestMapping("getStuSexLine")
	@ResponseBody
	public Map<String, Object> getStuSexLine(String param){
		return stuEnrollService.getStuSexLine(AdvancedUtil.converAdvancedList(param));
	}
	@RequestMapping("getStuAgeLine")
	@ResponseBody
	public Map<String, Object> getStuAgeLine(String param){
		return stuEnrollService.getStuAgeLine(AdvancedUtil.converAdvancedList(param));
	}
	@RequestMapping("getPoliticsLine")
	@ResponseBody
	public Map<String, Object> getPoliticsLine(String param){
		return stuEnrollService.getPoliticsLine(AdvancedUtil.converAdvancedList(param));
	}
	@RequestMapping("getDeptLine")
	@ResponseBody
	public Map<String, Object> getDeptLine(String param){
		return stuEnrollService.getDeptLine(AdvancedUtil.converAdvancedList(param));
	}
	@RequestMapping("getStyleLine")
	@ResponseBody
	public Map<String, Object> getStyleLine(String param){
		return stuEnrollService.getStyleLine(AdvancedUtil.converAdvancedList(param));
	}
	@RequestMapping("getFormLine")
	@ResponseBody
	public Map<String, Object> getFormLine(String param){
		return stuEnrollService.getFormLine(AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getGraduateStuCount")
	@ResponseBody
	public Map<String, Object> getGraduateStuCount(String param){
		return stuEnrollService.getGraduateStuCount(AdvancedUtil.converAdvancedList(param));
	}
	
	@RequestMapping("getBsCount")
	@ResponseBody
	public Map<String, Object> getBsCount(String param){
		return stuEnrollService.getBsCount(AdvancedUtil.converAdvancedList(param));
	}
}
