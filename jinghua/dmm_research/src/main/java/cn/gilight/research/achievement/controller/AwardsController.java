package cn.gilight.research.achievement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;

import cn.gilight.business.model.SelfDefinedYear;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.ExportUtil;
import cn.gilight.framework.uitl.HttpResult;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.research.achievement.serivice.AwardsService;

@Controller
@RequestMapping("/achievement/awards")
public class AwardsController {
	private Logger log = Logger.getLogger(AwardsController.class);

	@Resource
	private AwardsService awardsService;
	
	@RequestMapping("/nums")
	@ResponseBody
	public HttpResult nums(String xkmlid,String startYear,String endYear,String zzjgid) {
		String shiroTag = "research:achievement:awards:nums";
		log.debug("获奖成果数");
		Map<String, Object> data = new HashMap<String, Object>();
		HttpResult result = new HttpResult();
		SelfDefinedYear year = new SelfDefinedYear("",startYear,endYear);
		SelfDefinedYear last = SelfDefinedYear.getLastInterval(year);
		try {
			int currentNums = awardsService.queryAwardsNums(xkmlid, startYear, endYear, zzjgid,shiroTag);
			int lastNums = awardsService.queryAwardsNums(xkmlid, last.getStart(), last.getEnd(), zzjgid,shiroTag);
			int growth = currentNums - lastNums;
			String increase = "";
			if(lastNums != 0 && currentNums != 0){
				increase = MathUtils.getPercent(growth, lastNums);
			}else if(lastNums != 0 && currentNums == 0){
				increase = "0%";
			}else{
				increase = "—";
			}
			data.put("current", currentNums);
			data.put("growth", growth);
			data.put("increase", increase);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		result.setResult(data);
		return result;
	}
	

	@RequestMapping("/code")
	@ResponseBody
	public HttpResult queryAwardsCode() {
		log.debug("获取获奖成果查询条件的code");
		HttpResult result = new HttpResult();
		try {
			result.setResult(awardsService.queryAwardsCode());
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/change")
	@ResponseBody
	public HttpResult change(String xkmlid,String startYear,String endYear,String zzjgid) {
		String shiroTag = "research:achievement:awards:change";
		log.debug("获奖成果变化趋势数");
		HttpResult result = new HttpResult();
		try {
			result.setResult(awardsService.queryAwardsChange(xkmlid, startYear, endYear, zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/queryDetailList")
	@ResponseBody
	public HttpResult queryDetailList(Page page,String xkmlid,String startYear,String endYear,String zzjgid,String name,String flag) {
		String shiroTag = "research:achievement:awards:queryDetailList";
		log.debug("鉴定成果变化趋势数点击下钻事件");
		HttpResult result = new HttpResult();
		try {
			result.setResult(awardsService.queryDetailList(page,xkmlid, startYear, endYear, zzjgid,name,flag,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/level")
	@ResponseBody
	public HttpResult level(String xkmlid,String startYear,String endYear,String zzjgid) {
		String shiroTag = "research:achievement:awards:level";
		log.debug("成果获奖级别");
		HttpResult result = new HttpResult();
		try {
			result.setResult(awardsService.queryAwardsLevel(xkmlid, startYear, endYear, zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/type")
	@ResponseBody
	public HttpResult type(String xkmlid,String startYear,String endYear,String zzjgid) {
		String shiroTag = "research:achievement:awards:type";
		log.debug("成果获奖类别");
		HttpResult result = new HttpResult();
		try {
			result.setResult(awardsService.queryAwardsType(xkmlid, startYear, endYear, zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/peopleDept")
	@ResponseBody
	public HttpResult peopleDept(String xkmlid,String startYear,String endYear,String zzjgid) {
		String shiroTag = "research:achievement:awards:peopleDept";
		log.debug("获奖人单位");
		HttpResult result = new HttpResult();
		try {
			result.setResult(awardsService.queryPeopleDept(xkmlid, startYear, endYear, zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/peopleRole")
	@ResponseBody
	public HttpResult peopleRole(String xkmlid,String startYear,String endYear,String zzjgid,String role) {
		String shiroTag = "research:achievement:awards:peopleRole";
		log.debug("获奖人承担角色");
		HttpResult result = new HttpResult();
		try {
			result.setResult(awardsService.queryAwardsPeopleRole(xkmlid, startYear, endYear, zzjgid,role,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public HttpResult list(Page page,String xkmlid,String startYear,String endYear,String zzjgid,String param,String level) {
		String shiroTag = "research:achievement:awards:list";
		log.debug("获奖成果列表");
		HttpResult result = new HttpResult();
		try {
			result.setResult(awardsService.queryList(xkmlid, startYear, endYear, zzjgid,param,page,level,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	/** 
	 * 科研成果列表
	 */
	@RequestMapping("/list/export")
	public void exportProjectList(HttpServletRequest request,HttpServletResponse response,String filename,Page page,String name,String flag,
			String startYear, String endYear,String xkmlid, String zzjgid,String heads,String fields){
		String shiroTag = "research:achievement:awards:export";
		log.debug("导出科研获奖成果列表！");
		List<String> headsList = JSONArray.parseArray(heads, String.class);
		List<String> fieldsList = JSONArray.parseArray(fields, String.class);
		ExportUtil.downloadExcel(request, response, filename, headsList, fieldsList,(List<Map<String, Object>>) awardsService.queryDetailList(page,xkmlid, startYear, endYear, zzjgid,name,flag,shiroTag).get("rows"));
	}
}