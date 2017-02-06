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
import cn.gilight.research.achievement.serivice.SoftService;

@Controller
@RequestMapping("/achievement/soft")
public class SoftController {
	private Logger log = Logger.getLogger(SoftController.class);

	@Resource
	private SoftService softService;
	
	@RequestMapping("/nums")
	@ResponseBody
	public HttpResult nums(String xkmlid,String startYear,String endYear,String zzjgid) {
		String shiroTag = "research:achievement:soft:nums";
		log.debug("计算机著作权数");
		Map<String, Object> data = new HashMap<String, Object>();
		HttpResult result = new HttpResult();
		SelfDefinedYear year = new SelfDefinedYear("",startYear,endYear);
		SelfDefinedYear last = SelfDefinedYear.getLastInterval(year);
		try {
			int currentNums = softService.querySoftNums(xkmlid, startYear, endYear, zzjgid,shiroTag);
			int lastNums = softService.querySoftNums(xkmlid, last.getStart(), last.getEnd(), zzjgid,shiroTag);
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
	public HttpResult querySoftCode() {
		log.debug("获取获奖成果查询条件的code");
		HttpResult result = new HttpResult();
		try {
			result.setResult(softService.querySoftCode());
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
		String shiroTag = "	research:achievement:soft:change";
		log.debug("计算机著作权数变化趋势数");
		HttpResult result = new HttpResult();
		try {
			result.setResult(softService.querySoftChange(xkmlid, startYear, endYear, zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/querySoftDetail")
	@ResponseBody
	public HttpResult querySoftDetail(Page page,String xkmlid,String startYear,String endYear,String zzjgid,String name,String flag) {
		String shiroTag = "research:achievement:soft:querySoftDetail";
		log.debug("鉴定成果变化趋势数点击下钻事件");
		HttpResult result = new HttpResult();
		try {
			result.setResult(softService.querySoftDetail(page,xkmlid, startYear, endYear, zzjgid,name,flag,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/get")
	@ResponseBody
	public HttpResult get(String xkmlid,String startYear,String endYear,String zzjgid) {
		String shiroTag = "research:achievement:soft:get";
		log.debug("计算机著作权取得方式");
		HttpResult result = new HttpResult();
		try {
			result.setResult(softService.querySoftGet(xkmlid, startYear, endYear, zzjgid,shiroTag));
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
		String shiroTag = "research:achievement:soft:peopleDept";
		log.debug("计算机著作权人员单位分布");
		HttpResult result = new HttpResult();
		try {
			result.setResult(softService.querySoftPeopleDept(xkmlid, startYear, endYear, zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/author")
	@ResponseBody
	public HttpResult author(String xkmlid,String startYear,String endYear,String zzjgid) {
		String shiroTag = "research:achievement:soft:author";
		log.debug("活跃软件开发者");
		HttpResult result = new HttpResult();
		try {
			result.setResult(softService.querySoftAuthor(xkmlid, startYear, endYear, zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public HttpResult list(Page page,String xkmlid,String startYear,String endYear,String zzjgid,String param,String copyright,String getcode) {
		String shiroTag = "research:achievement:soft:list";
		log.debug("计算机著作权列表");
		HttpResult result = new HttpResult();
		try {
			result.setResult(softService.queryList(xkmlid, startYear, endYear, zzjgid,param,page,copyright,getcode,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	/** 
	 * 计算机著作权列表
	 */
	@RequestMapping("/list/export")
	public void exportProjectList(HttpServletRequest request,HttpServletResponse response,String filename,Page page,String name,String flag,
			String startYear, String endYear,String xkmlid, String zzjgid,String heads,String fields){
		String shiroTag = "research:achievement:soft:export";
		log.debug("导出科研计算机著作权列表！");
		List<String> headsList = JSONArray.parseArray(heads, String.class);
		List<String> fieldsList = JSONArray.parseArray(fields, String.class);
		ExportUtil.downloadExcel(request, response, filename, headsList, fieldsList,(List<Map<String, Object>>) softService.querySoftDetail(page,xkmlid, startYear, endYear, zzjgid,name,flag,shiroTag).get("rows"));
	}
}