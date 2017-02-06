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
import cn.gilight.dmm.xg.service.FeeWarningService;
import cn.gilight.framework.uitl.common.ExcelUtils;

/**
 * 学费预警
 * @author lijun
 *
 */
@Controller
@RequestMapping("feeWarning")
public class FeeWarningCtol {

	@Resource
	private FeeWarningService feeWarningService;
	@RequestMapping()
	public String init(){
		return "feeWarning";
	}
	@RequestMapping("queryXfInfo")
	@ResponseBody
	Map<String, Object> queryXfInfo(String param){
		return feeWarningService.queryXfInfo(AdvancedUtil.converAdvancedList(param));
	}
	/**
	 * 得到未缴费学费类型及金额
	 * @return
	 */
	@RequestMapping("queryXflxfb")
	@ResponseBody
	List<Map<String, Object>> queryXflxfb(String param){
		return feeWarningService.queryXflxfb(AdvancedUtil.converAdvancedList(param));
	}
	/**
	 * 得到筛选类型(本专科生 欠费类型  学生类型)
	 * @return
	 */
	@RequestMapping("querySelectType")
	@ResponseBody
	List<Map<String, Object>> querySelectType(){
		return feeWarningService.querySelectType();
	}
	/**
	 * 得到未缴费学生类型及人数
	 * @return
	 */
	@RequestMapping("queryXslxfb")
	@ResponseBody
	List<Map<String, Object>> queryXslxfb(String param){
		return feeWarningService.queryXslxfb(AdvancedUtil.converAdvancedList(param));
	}
	/**
	 * 历年未缴费金额及比例
	 * @return
	 */
	@RequestMapping("queryQfjeAndRadio")
	@ResponseBody
	Map<String, Object> queryQfjeAndRadio(String param) {
		return feeWarningService.queryQfjeAndRadio(AdvancedUtil.converAdvancedList(param));
	}
	/**
	 * 得到欠费详细信息(院系 总人数 欠费人数 占比 金额)
	 * @return
	 */
	@RequestMapping("queryQfInfo")
	@ResponseBody
	Map<String, Object> queryQfInfo(Integer year,String edu,String lx,String slx){
		return feeWarningService.queryQfInfo(year,edu,lx,slx);
	}
	/**
	 * 发送邮件
	 * @return
	 */
	@RequestMapping("sendQfInfo")
	@ResponseBody
	Map<String, Object> sendQfInfo(String sendType,String pid, String list,String year,String edu, String lx,
			String slx,HttpServletRequest request){
//		System.out.println(request.getSession().getServletContext().getRealPath("/"));
		return feeWarningService.sendQfInfo(sendType,pid,list,year,edu,lx,slx,request);
	}
	/**
	 * 全部发送
	 * @return
	 */
	@RequestMapping("sendAll")
	@ResponseBody
	Map<String, Object> sendAll(HttpServletRequest request){
		return feeWarningService.sendAll(request);
	}
	/**
	 * 导出excel
	 * @return
	 */
	@RequestMapping("excelQfInfo")
	@ResponseBody
	void excelQfInfo(String pid, String list,String year,String edu, String lx,
			String slx,HttpServletResponse response ){
		 feeWarningService.exportQfInfo(pid, list,year, edu, lx, slx, response);
	}
	/**
	 * 导出excel
	 * @return
	 */
	@RequestMapping("excelQfAll")
	@ResponseBody
	void excelQfAll(HttpServletRequest request,HttpServletResponse response ){
		feeWarningService.excelQfAll(request, response);
	}
}
