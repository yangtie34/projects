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
 * 挂科预警
 * @author lijun
 *
 */
@Controller
@RequestMapping("failExamPredict")
public class FailExamPredictCtol {

	@Resource
	private FailExamPredictService failExamPredictService;
	@RequestMapping()
	public String init(){
		return "failExamPredict";
	}
	/**
	 * 得到挂科预测学生期末考试人数和预测挂科概率
	 * @param param
	 * @return
	 */
	@RequestMapping("getYearAndTerm")
	@ResponseBody
	Map<String, Object> getYearAndTerm(String param){
		return failExamPredictService.getYearAndTerm();
	}
	/**
	 * 得到挂科预测学生期末考试人数和预测挂科概率
	 * @param param
	 * @return
	 */
	@RequestMapping("queryGkInfo")
	@ResponseBody
	Map<String, Object> queryGkInfo(String param){
		return failExamPredictService.queryGkInfo(AdvancedUtil.converAdvancedList(param));
	}
	/**
	 * 得到挂科预测学生性别分布情况
	 * @return
	 */
	@RequestMapping("queryGklxfb")
	@ResponseBody
	List<Map<String, Object>> queryGklxfb(String param){
		return failExamPredictService.queryGklxfb(AdvancedUtil.converAdvancedList(param));
	}
	/**
	 * 得到挂科学生年级分布情况
	 * @return
	 */
	@RequestMapping("queryXslxfb")
	@ResponseBody
	List<Map<String, Object>> queryXslxfb(String param){
		return failExamPredictService.queryXslxfb(AdvancedUtil.converAdvancedList(param));
	}
	/**
	 * 各院系挂科预测学生人数和比例
	 * @return
	 */
	@RequestMapping("queryGkrsAndRadio")
	@ResponseBody
	Map<String, Object> queryGkrsAndRadio(String param) {
		return failExamPredictService.queryGkrsAndRadio(AdvancedUtil.converAdvancedList(param));
	}
	/**
	 * 得到挂科预测学生详细信息(院系 预测挂科人数 预测挂科门数 占比 金额)
	 * @return
	 */
	@RequestMapping("queryGkxxInfo")
	@ResponseBody
	Map<String, Object> queryGkxxInfo(String param){
		return failExamPredictService.queryGkxxInfo(AdvancedUtil.converAdvancedList(param));
	}
	/**
	 * 发送邮件
	 * @return
	 */
	@RequestMapping("sendGkInfo")
	@ResponseBody
	Map<String, Object> sendGkInfo(String sendType,String pid, String list,HttpServletRequest request){
//		System.out.println(request.getSession().getServletContext().getRealPath("/"));
		return failExamPredictService.sendGkInfo(sendType,pid,list,request);
	}
	/**
	 * 全部发送
	 * @return
	 */
	@RequestMapping("sendAll")
	@ResponseBody
	Map<String, Object> sendAll(HttpServletRequest request){
		return failExamPredictService.sendAll(request);
	}
	/**
	 * 导出excel
	 * @return
	 */
	@RequestMapping("excelGkInfo")
	@ResponseBody
	void excelGkInfo(String pid, String list,HttpServletResponse response ){
		 failExamPredictService.exportGkInfo(pid, list, response);
	}
	/**
	 * 导出excel
	 * @return
	 */
	@RequestMapping("excelGkAll")
	@ResponseBody
	void excelGkAll(HttpServletRequest request,HttpServletResponse response ){
		failExamPredictService.excelGkAll(request, response);
	}
}
