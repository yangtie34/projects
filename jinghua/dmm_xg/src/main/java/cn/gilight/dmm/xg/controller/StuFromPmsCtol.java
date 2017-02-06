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
import cn.gilight.dmm.xg.service.StuFromService;
import cn.gilight.framework.uitl.common.ExcelUtils;

@Controller
@RequestMapping("pmsStuFrom")
public class StuFromPmsCtol {
	@Resource
	private StuFromService stuFromService;
	
	@RequestMapping("getStuList")
	@ResponseBody
	public Map<String, Object> getStuList(String param,String page, String values, String fields) {
		return stuFromService.getStuList(AdvancedUtil.converAdvancedList(param), PageUtils.converPage(page),JSONObject.parseObject(values, HashMap.class), JSONObject.parseArray(fields, String.class));
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("down")
	@ResponseBody
	public void down(String param,String page,String values, String fields,String headers,String divid, String updown, String from, String to,
			String edu,
			String fileName, HttpServletRequest request, HttpServletResponse response){
//		response.setContentType("multipart/form-data");
//		response.setContentType("text/html;charset=UTF-8");
//		response.setContentType("application/x-execl"); 
		response.setContentType("application/octet-stream; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		if(fileName == null) fileName = "下载文件";
		try {
			response.setHeader("Content-Disposition", "attachment;fileName=" +new String((fileName+".xls").getBytes(),"iso-8859-1"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
        
		List<String> fields2 = JSONObject.parseArray(fields, String.class),
					 headers2 = JSONObject.parseArray(headers, String.class);
		List<Map<String, Object>> list = stuFromService.getStuDetail(AdvancedUtil.converAdvancedList(param), JSONObject.parseObject(values, HashMap.class), JSONObject.parseArray(fields, String.class)/*, divid, Boolean.parseBoolean(updown), from, to, edu*/);
		HSSFWorkbook workBook = ExcelUtils.createExcel(list, fields2, headers2, fileName);
		try {
			OutputStream os = response.getOutputStream();
			workBook.write(os);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/getExportData")
	@ResponseBody
	public void getExportData(String param,String from,String to,String edu,
			String divid, Boolean updown,String bs,String fields,String headers,
			String fileName, HttpServletRequest request, HttpServletResponse response){
//		response.setContentType("multipart/form-data");
//		response.setContentType("text/html;charset=UTF-8");
//		response.setContentType("application/x-execl"); 
		response.setContentType("application/octet-stream; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		if(fileName == null) fileName = "下载文件";
		try {
			response.setHeader("Content-Disposition", "attachment;fileName=" +new String((fileName+".xls").getBytes(),"iso-8859-1"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
        
		List<String> fields2 = JSONObject.parseArray(fields, String.class),
					 headers2 = JSONObject.parseArray(headers, String.class);
		List<Map<String, Object>> list = stuFromService.getExportData(AdvancedUtil.converAdvancedList(param), from, to, edu, divid, updown, bs);
		HSSFWorkbook workBook = ExcelUtils.createExcel(list, fields2, headers2, fileName);
		try {
			OutputStream os = response.getOutputStream();
			workBook.write(os);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
