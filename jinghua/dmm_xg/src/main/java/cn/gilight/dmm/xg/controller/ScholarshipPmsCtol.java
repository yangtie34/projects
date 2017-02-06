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
import cn.gilight.dmm.xg.service.ScholarshipService;
import cn.gilight.framework.uitl.common.ExcelUtils;


@Controller
@RequestMapping("pmsScholarship")
public class ScholarshipPmsCtol {

	@Resource
	private ScholarshipService scholarshipService;
	
	@RequestMapping("getStuDetail")
	@ResponseBody
	public Map<String, Object> getStuDetail(String param,String page, String values, String fields){
		return scholarshipService.getStuScholarshipDetail(AdvancedUtil.converAdvancedList(param), PageUtils.converPage(page),JSONObject.parseObject(values, HashMap.class), JSONObject.parseArray(fields, String.class));
	}
	
	@RequestMapping("down")
	@ResponseBody
	public void down(String param,String page,String values,String fields,String headers,String fileName,HttpServletRequest request,HttpServletResponse response){
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
		List<Map<String, Object>> list = scholarshipService.getStuDetailList(AdvancedUtil.converAdvancedList(param),PageUtils.converPage(page), JSONObject.parseObject(values, HashMap.class), JSONObject.parseArray(fields, String.class));
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
