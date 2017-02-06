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
import cn.gilight.research.achievement.serivice.PatentService;

@Controller
@RequestMapping("/achievement/patent")
public class PatentController {
	private Logger log = Logger.getLogger(PatentController.class);

	@Resource
	private PatentService patentService;
	
	@RequestMapping("/nums")
	@ResponseBody
	public HttpResult nums(String xkmlid,String startYear,String endYear,String zzjgid) {
		String shiroTag = "research:achievement:patent:nums";
		log.debug("专利数");
		Map<String, Object> data = new HashMap<String, Object>();
		HttpResult result = new HttpResult();
		SelfDefinedYear year = new SelfDefinedYear("",startYear,endYear);
		SelfDefinedYear last = SelfDefinedYear.getLastInterval(year);
		try {
			int currentNums = patentService.queryPatentNums(xkmlid, startYear, endYear, zzjgid,shiroTag);
			int lastNums = patentService.queryPatentNums(xkmlid, last.getStart(), last.getEnd(), zzjgid,shiroTag);
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
	
	@RequestMapping("/queryPatentDetail")
	@ResponseBody
	public HttpResult queryPatentDetail(Page page,String xkmlid,String startYear,String endYear,String zzjgid,String name,String flag) {
		String shiroTag = "research:achievement:patent:queryPatentDetail";
		log.debug("鉴定成果变化趋势数点击下钻事件");
		HttpResult result = new HttpResult();
		try {
			result.setResult(patentService.queryPatentDetail(page,xkmlid, startYear, endYear, zzjgid,name,flag,shiroTag));
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
		String shiroTag = "research:achievement:patent:type";
		log.debug("专利类型");
		HttpResult result = new HttpResult();
		try {
			result.setResult(patentService.queryPatentType(xkmlid, startYear, endYear, zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/state")
	@ResponseBody
	public HttpResult state(String xkmlid,String startYear,String endYear,String zzjgid) {
		String shiroTag = "research:achievement:patent:state";
		log.debug("专利实施状态");
		HttpResult result = new HttpResult();
		try {
			result.setResult(patentService.queryPatentState(xkmlid, startYear, endYear, zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/change")
	@ResponseBody
	public HttpResult change(String xkmlid,String startYear,String endYear,String zzjgid,String param) {
		String shiroTag = "research:achievement:patent:change";
		log.debug("专利变化趋势");
		HttpResult result = new HttpResult();
		try {
			result.setResult(patentService.queryPatentChange(xkmlid, startYear, endYear, zzjgid,param,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/dept")
	@ResponseBody
	public HttpResult dept(String xkmlid,String startYear,String endYear,String zzjgid,String param) {
		String shiroTag = "research:achievement:patent:dept";
		log.debug("专利单位分布");
		HttpResult result = new HttpResult();
		try {
			result.setResult(patentService.queryPatentDept(xkmlid, startYear, endYear, zzjgid,param,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/deptMax")
	@ResponseBody
	public HttpResult deptMax(String xkmlid,String startYear,String endYear,String zzjgid) {
		String shiroTag = "research:achievement:patent:deptMax";
		log.debug("专利贡献度最高的单位");
		HttpResult result = new HttpResult();
		try {
			result.setResult(patentService.queryConDept(xkmlid, startYear, endYear, zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	/** 
	 * 科研专利列表
	 */
	@RequestMapping("/queryPatentDetail/export")
	public void exportProjectList(HttpServletRequest request,HttpServletResponse response,String filename,Page page,String name,String flag,
			String startYear, String endYear,String xkmlid, String zzjgid,String heads,String fields){
		String shiroTag = "research:achievement:patent:export";
		log.debug("导出科研专利列表！");
		List<String> headsList = JSONArray.parseArray(heads, String.class);
		List<String> fieldsList = JSONArray.parseArray(fields, String.class);
		ExportUtil.downloadExcel(request, response, filename, headsList, fieldsList,(List<Map<String, Object>>) patentService.queryPatentDetail(page,xkmlid, startYear, endYear, zzjgid,name,flag,shiroTag).get("rows"));
	}
	
	
}