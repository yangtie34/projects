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
import cn.gilight.dmm.xg.service.FailExamPredictService;
import cn.gilight.framework.uitl.common.ExcelUtils;

/**
 * 挂科预警下钻详情
 * @author lijun
 *
 */
@Controller
@RequestMapping("pmsFailExamPredict")
public class FailExamPredictPmsCtol {

	@Resource
	private FailExamPredictService failExamPredictService;
	@SuppressWarnings("unchecked")
	@RequestMapping("getStuDetail")
	@ResponseBody
	public Map<String, Object> getStuDetail(String param, String page, String values, String fields){
		return failExamPredictService.getTeaDetail(AdvancedUtil.converAdvancedList(param), PageUtils.converPage(page), 
				JSONObject.parseObject(values, HashMap.class), JSONObject.parseArray(fields, String.class));
	}
	@SuppressWarnings("unchecked")
	@RequestMapping("down")
	public void down(String param,String page, String values, String fields, String headers,
			String fileName, HttpServletRequest request, HttpServletResponse response){
//		response.setContentType("multipart/form-data");
//		response.setContentType("text/html;charset=UTF-8");
//		response.setContentType("application/x-execl"); 
		response.setCharacterEncoding("utf-8");
		if(fileName == null) fileName = "下载文件";
		try {
			response.setHeader("Content-Disposition", "attachment;fileName=" +new String((fileName+".xls").getBytes(),"iso-8859-1"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setContentType("application/octet-stream; charset=utf-8"); 
        
		List<String> fields2 = JSONObject.parseArray(fields, String.class),
					 headers2 = JSONObject.parseArray(headers, String.class);
		List<Map<String, Object>> list = failExamPredictService.getStuDetailAll(AdvancedUtil.converAdvancedList(param), PageUtils.converPage(page), 
				JSONObject.parseObject(values, HashMap.class), fields2);
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
