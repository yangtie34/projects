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
import cn.gilight.dmm.xg.service.NewStuService;
import cn.gilight.framework.uitl.common.ExcelUtils;

@Controller
@RequestMapping("newStu")
public class NewStuCtol {
	@Resource
	private NewStuService newStuService;
	@RequestMapping()
	public String init(){
		return "newStu";
	}
	@RequestMapping("getIsRegisterCount")
	@ResponseBody
	public Map<String,Object> getIsRegisterCount(String param){
		return newStuService.getIsRegisterCount(AdvancedUtil.converAdvancedList(param));
	}
	@RequestMapping("getCountAndLv")
	@ResponseBody
	public Map<String,Object> getCountAndLv(String param){
		return newStuService.getCountAndLv(AdvancedUtil.converAdvancedList(param));
	}
	@RequestMapping("getDeptAvgLv")
	@ResponseBody
	public Map<String,Object> getDeptAvgLv(String param){
		return newStuService.getDeptAvgLv(AdvancedUtil.converAdvancedList(param));
	}
	@RequestMapping("getPoorCount")
	@ResponseBody
	public Map<String,Object> getPoorCount(String param,String year){
		return newStuService.getPoorCount(AdvancedUtil.converAdvancedList(param),year);
	}
	@RequestMapping("getLoanCount")
	@ResponseBody
	public Map<String,Object> getLoanCount(String param,String year){
		return newStuService.getLoanCount(AdvancedUtil.converAdvancedList(param),year);
	}
	@RequestMapping("getJmCount")
	@ResponseBody
	public Map<String,Object> getJmCount(String param,String year){
		return newStuService.getJmCount(AdvancedUtil.converAdvancedList(param),year);
	}
//	@RequestMapping("getPoorPie")
//	@ResponseBody
//	public Map<String,Object> getPoorPie(String param,String year){
//		return newStuService.getPoorPie(AdvancedUtil.converAdvancedList(param),year);
//	}
//	@RequestMapping("getLoanPie")
//	@ResponseBody
//	public Map<String,Object> getLoanPie(String param,String year){
//		return newStuService.getLoanPie(AdvancedUtil.converAdvancedList(param),year);
//	}
//	@RequestMapping("getJmPie")
//	@ResponseBody
//	public Map<String,Object> getJmPie(String param,String year){
//		return newStuService.getJmPie(AdvancedUtil.converAdvancedList(param),year);
//	}
}
