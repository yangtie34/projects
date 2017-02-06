package cn.gilight.research.xkjs.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.gilight.framework.uitl.HttpResult;
import cn.gilight.research.xkjs.service.XkjsService;

@Controller
@RequestMapping("/xkjs")
public class XkjsController {
	private Logger log = Logger.getLogger(XkjsController.class);

	@Resource
	private XkjsService xkjsService;
	
	@RequestMapping("/query/xkryzc")
	@ResponseBody
	public HttpResult queryXkryzc(String year){
		log.debug("学科人员组成");
		HttpResult result = new HttpResult();
		try {
			result.setResult(xkjsService.queryXkryzc(year));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}

	@RequestMapping("/query/zblist")
	@ResponseBody
	public HttpResult queryAllzb(){
		log.debug("学科业绩指标进展");
		HttpResult result = new HttpResult();
		try {
			result.setResult(xkjsService.queryZblist());
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}

	@RequestMapping("/query/zbjz")
	@ResponseBody
	public HttpResult queryZbjz(String year,String zbid){
		log.debug("学科业绩指标进展");
		HttpResult result = new HttpResult();
		try {
			result.setResult(xkjsService.queryZbjzOfYear(year));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/query/gxkzbjz")
	@ResponseBody
	public HttpResult queryGxkzbjz(String year,String zbid){
		log.debug("各学科分项指标建设进展");
		HttpResult result = new HttpResult();
		try {
			result.setResult(xkjsService.queryGxkzbjzOfYearAndZb(year,zbid));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/query/ywczb")
	@ResponseBody
	public HttpResult queryYwczbs(String year){
		log.debug("根据年份查询学科建设应完成指标明细");
		HttpResult result = new HttpResult();
		try {
			result.setResult(xkjsService.queryYwczbOfYear(year));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	@RequestMapping("/query/zbwcl")
	@ResponseBody
	public HttpResult queryZbwcl(String year){
		log.debug("根据年份查询学科建设指标完成率");
		HttpResult result = new HttpResult();
		try {
			result.setResult(xkjsService.queryZbwclOfYear(year));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/data/xkryxx")
	@ResponseBody
	public HttpResult countXkryxx(){
		log.debug("计算学科人员组成");
		HttpResult result = new HttpResult();
		try {
			xkjsService.calculateXkryzc();
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/data/zbwcsj")
	@ResponseBody
	public HttpResult countZbwcsj(){
		log.debug("计算指标完成数据");
		HttpResult result = new HttpResult();
		try {
			xkjsService.calculateZbwcsj();
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	//查询带头人指标完成率
	@RequestMapping("/query/grzbwcl")
	@ResponseBody
	public HttpResult queryGrzbwcl(String year,String zgh,String xkid){
		log.debug("个人指标完成率");
		HttpResult result = new HttpResult();
		try {
			result.setResult(xkjsService.queryGrzbwcl(year, zgh, xkid));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	//查询带头人职工号
	@RequestMapping("/query/grzgh")
	@ResponseBody
	public HttpResult queryGrzgh(String xkid){
		log.debug("查询带头人职工号");
		HttpResult result = new HttpResult();
		try {
			result.setResult(xkjsService.queryGrzgh(xkid));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	//查询学科名称
	@RequestMapping("/query/xkmc")
	@ResponseBody
	public HttpResult queryXkmc(){
		log.debug("查询学科名称");
		HttpResult result = new HttpResult();
		try {
			result.setResult(xkjsService.queryXkmc());
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
}