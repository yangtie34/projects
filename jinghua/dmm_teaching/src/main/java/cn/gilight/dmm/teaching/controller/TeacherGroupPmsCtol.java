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
import cn.gilight.dmm.teaching.service.TeacherGroupService;
import cn.gilight.framework.uitl.common.ExcelUtils;

/**
 * 师资力量
 * @author xuebl
 * @date 2016年6月28日 14:08
 */
@Controller
@RequestMapping("pmsTeacherGroup")
public class TeacherGroupPmsCtol {
	
	@Resource
	private TeacherGroupService teacherGroupService;
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("getTeaDetail")
	@ResponseBody
	public Map<String, Object> getTeaDetail(String param, String page, String values, String fields){
		return teacherGroupService.getTeaDetail(AdvancedUtil.converAdvancedList(param), PageUtils.converPage(page), 
				JSONObject.parseObject(values, HashMap.class), JSONObject.parseArray(fields, String.class));
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("down")
	public void down(String param, String page, String values, String fields, String headers,
			String fileName, HttpServletRequest request, HttpServletResponse response){
//		response.setContentType("multipart/form-data"); // 上传
//		response.setContentType("text/html;charset=UTF-8"); 
//		response.setContentType("application/x-execl"); 
		response.setContentType("application/octet-stream; charset=utf-8"); 
		response.setCharacterEncoding("utf-8");
		if(fileName == null) fileName = "下载文件";
		//这里设置一下让浏览器弹出下载提示框，而不是直接在浏览器中打开
//		response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+"\"");
		/*final String userAgent = request.getHeader("USER-AGENT");
        try {
            if(StringUtils.contains(userAgent, "MSIE")){ //IE浏览器
                fileName = URLEncoder.encode(fileName,"UTF8");
            }else if(StringUtils.contains(userAgent, "Mozilla")){ //google,火狐浏览器
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            }else{
                fileName = URLEncoder.encode(fileName,"UTF8"); //其他浏览器
            }
        } catch (UnsupportedEncodingException e) {
        }*/
		try {
			response.setHeader("Content-Disposition", "attachment;fileName=" +new String((fileName+".xls").getBytes(),"iso-8859-1"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
        
		List<String> fields2 = JSONObject.parseArray(fields, String.class),
					 headers2 = JSONObject.parseArray(headers, String.class);
		List<Map<String, Object>> list = teacherGroupService.getTeaDetailAll(AdvancedUtil.converAdvancedList(param), PageUtils.converPage(page),
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
