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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gilight.dmm.business.util.PageUtils;
import cn.gilight.dmm.teaching.service.StudentsQualityService;
import cn.gilight.framework.uitl.common.ExcelUtils;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("studentsQuality")
public class StudentsQualityCtol {

	@Resource
	private StudentsQualityService studentsQualityService;
	@RequestMapping()
	public String init(){
		return "studentsQuality";
	}
	/**
	 * 
	* @Description: 页面查询
	* @return: Map<String,Object>
	* @return
	 */
	@RequestMapping("querySelectType")
	@ResponseBody
	public Map<String, Object> querySelectType(){
		return studentsQualityService.querySelectType();
	}
	/**
	 * 
	* @Description: 查询本科生各项数据
	* @return: Map<String,Object>
	* @param year 查询的年份
	* @return
	 */
	@RequestMapping("queryStudents")
	@ResponseBody
	public Map<String, Object> queryStudents(String year) {
		return studentsQualityService.queryStudents(year);
	}
	/**
	 * 
	* @Description: 查询表中所有科类
	* @return: List<Map<String,Object>>
	* @param year  查询的年份
	* @return
	 */
	@RequestMapping("queryAllSub")
	@ResponseBody
	public List<Map<String, Object>> queryAllSub(String year){
		return studentsQualityService.queryAllSub(year);
	}
	/**
	 * 
	* @Description: 选择某科类时,查询科类对应数据 sub:科类ID
	* @return: List<Map<String,Object>>
	* @param year 查询的年份
	* @param sub 科类ID
	* @return
	 */
	@RequestMapping("queryStudentsSub")
	@ResponseBody
	public List<Map<String, Object>> queryStudentsSub(String year,  @RequestParam("sub[]") List<String> sub) {
		return studentsQualityService.queryStudentsSub(year, sub);
	}
	/**
	 * 
	* @Description: 查询超出分数线20分的专业 及其各项数据
	* @return: Map<String,Object>
	* @param page 分页参数
	* @param year 查询的年份
	* @param point 筛选条件
	* @param flag 样式变化标志参数
	* @return
	 */
	@RequestMapping("queryStudentsScore")
	@ResponseBody
	public Map<String, Object> queryStudentsScore(String page,String year,String point,String flag) {
		return studentsQualityService.queryStudentsScore(PageUtils.converPage(page),year,point,flag);
	}
	/**
	 * 
	* @Description: 调剂率
	* @return: Map<String,Object>
	* @param page 分页参数
	* @param year 查询的年份
	* @param flag 样式变化标志参数
	* @return
	 */
	@RequestMapping("queryStudentsAdjust")
	@ResponseBody
	public Map<String, Object> queryStudentsAdjust(String page,String year,String flag) {
		return studentsQualityService.queryStudentsAdjust(PageUtils.converPage(page),year,flag);
	}
	/**
	 * 
	* @Description: 自主招生录取率
	* @return: Map<String,Object>
	* @param page 分页参数
	* @param year 查询的年份
	* @param flag 样式变化标志参数
	* @return
	 */
	@RequestMapping("queryStudentsEnroll")
	@ResponseBody
	public Map<String, Object> queryStudentsEnroll(String page,String year,String flag) {
		return studentsQualityService.queryStudentsEnroll(PageUtils.converPage(page),year,flag);
	}
	/**
	 * 
	* @Description: 未报到人数
	* @return: Map<String,Object>
	* @param year 查询的年份
	* @return
	 */
	@RequestMapping("queryStudentsNotReport")
	@ResponseBody
	public Map<String, Object> queryStudentsNotReport(String year) {
		return studentsQualityService.queryStudentsNotReport(year);
	}
	/**
	 * 
	* @Description: 学生未报到地理分布
	* @return: Map<String,Object>
	* @param year 查询的年份
	* @param xzqh 节点代码
	* @param updown Boolean值,用于判断向上或者向下取节点
	* @return
	 */
	@RequestMapping("queryStudentsNotReportByLocal")
	@ResponseBody
	public Map<String, Object> queryStudentsNotReportByLocal(String year,String xzqh,Boolean updown) {
		return studentsQualityService.queryStudentsNotReportByLocal(year,xzqh,updown);
	}
	/**
	 * 
	* @Description: 未报到原因分布
	* @return: Map<String,Object>
	* @param year 查询的年份
	* @return
	 */
	@RequestMapping("queryStudentsNotReportReason")
	@ResponseBody
	public Map<String, Object> queryStudentsNotReportReason(String year) {
		return studentsQualityService.queryStudentsNotReportReason(year);
	}
	/**
	 * 
	* @Description: 各省分数线详情
	* @return: Map<String,Object>
	* @param page 分页参数
	* @param flag 样式改变 标志参数
	* @param year 查询的年份
	* @param majorId 专业ID
	* @return
	 */
	@RequestMapping("queryScoreLineByPro")
	@ResponseBody
	public Map<String, Object> queryScoreLineByPro(String page,String flag,String year, String majorId){
		return studentsQualityService.queryScoreLineByPro(PageUtils.converPage(page),flag,year, majorId);
	}
	/**
	 * 
	* @Description: 未报到学生详情
	* @return: Map<String,Object>
	* @param page 分页参数
	* @param pid 父节点代码
	* @param year 查询的年份
	* @param fields 需获取字段的代码
	* @return
	 */
	@RequestMapping("queryWbdDetail")
	@ResponseBody
	public Map<String, Object> queryWbdDetail(String page, String pid, String year,String fields){
		return studentsQualityService.queryWbdDetail(PageUtils.converPage(page), pid, year, JSONObject.parseArray(fields, String.class));
	}
	/**
	 * 
	* @Description: 获取学生未报到原因
	* @return: Map<String,Object>
	* @param page 分页参数
	* @param year 查询的年份
	* @param values 未报到原因ID
	* @param fields 需获取字段的代码
	* @return
	 */
	@RequestMapping("getNotReportReason")
	@ResponseBody
	public Map<String, Object> getNotReportReason(String page,String year,String values,String fields){
		return studentsQualityService.getNotReportReason(PageUtils.converPage(page), year, values, JSONObject.parseArray(fields, String.class));
	}
	/**
	* @Description: 下载
	* @return: void
	* @param page 分页参数
	* @param pid 父节点代码
	* @param year 查询的年份
	* @param values 未报到原因ID
	* @param fields 需获取的字段代码
	* @param flag 样式改变标志代码
	* @param point 分数值代码
	* @param majorId 专业ID
	* @param headers 
	* @param fileName 
	* @param request 
	* @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("down")
	public void down(String page,String pid,String year,String values, String fields,String headers,
			String fileName,String flag,String point,String majorId, HttpServletRequest request, HttpServletResponse response){
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
		Map<String, Object> map = new HashMap<String, Object>();
		if(flag.equals("1")){
			map = studentsQualityService.queryScoreLineByPro(PageUtils.converPage(page), flag, year, majorId);
		}
		if(flag.equals("2")){
			map = studentsQualityService.queryStudentsScore(PageUtils.converPage(page), year, point, flag);
		}
		if(flag.equals("3")){
			map = studentsQualityService.queryStudentsAdjust(PageUtils.converPage(page), year, flag);
		}
		if(flag.equals("4")){
			map = studentsQualityService.queryStudentsEnroll(PageUtils.converPage(page), year, flag);
		}
		if(flag.equals("5")){
			map = studentsQualityService.queryWbdDetail(PageUtils.converPage(page), pid, year, fields2);
		}
		if(flag.equals("6")){
			map = studentsQualityService.getNotReportReason(PageUtils.converPage(page), year, values, fields2);
		}
		List<Map<String,Object>> list = (List<Map<String, Object>>) map.get("rows");
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
