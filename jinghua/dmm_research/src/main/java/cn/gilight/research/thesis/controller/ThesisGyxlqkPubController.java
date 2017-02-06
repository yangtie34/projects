package cn.gilight.research.thesis.controller;

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
import cn.gilight.research.thesis.service.ThesisGyxlqkPubService;

/**   
* @Description: 高影响力期刊论文发表
* @author Sunwg  
* @date 2016年6月6日 下午3:42:30   
*/
@Controller
@RequestMapping("/thesis/gyxlqk/pub")
public class ThesisGyxlqkPubController {
	private Logger log = Logger.getLogger(ThesisGyxlqkPubController.class);

	@Resource
	private ThesisGyxlqkPubService service;
	
	@RequestMapping("/total")
	@ResponseBody
	public HttpResult total(String xkmlid,String startYear,String endYear,String zzjgid) {
		log.debug("论文发表总量");
		String shiroTag = "research:thesis:publish:total";
		Map<String, Object> data = new HashMap<String, Object>();
		HttpResult result = new HttpResult();
		SelfDefinedYear year = new SelfDefinedYear("",startYear,endYear);
		SelfDefinedYear last = SelfDefinedYear.getLastInterval(year);
		try {
			data.put("current", service.querySCINums(xkmlid, startYear, endYear,zzjgid,shiroTag));
			data.put("last", service.querySCINums(xkmlid, last.getStart(), last.getEnd(),zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		result.setResult(data);
		return result;
	}
	
	@RequestMapping("/yearchange")
	@ResponseBody
	public HttpResult yearchange(String xkmlid,String startYear,String endYear,String zzjgid) {
		log.debug("发表量变化趋势");
		String shiroTag = "research:thesis:publish:yearchange";
		HttpResult result = new HttpResult();
		try {
			result.setResult(service.querySCINumsByYear(xkmlid, startYear, endYear,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/depts")
	@ResponseBody
	public HttpResult depts(String xkmlid,String startYear,String endYear,String zzjgid) {
		log.debug("各单位论文发表量和占比");
		String shiroTag = "research:thesis:publish:deptnums";
		HttpResult result = new HttpResult();
		try {
			result.setResult(service.querySCINumsByDept(xkmlid, startYear, endYear,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/periodical/includenums")
	@ResponseBody
	public HttpResult includenums(String xkmlid,String startYear,String endYear,String zzjgid) {
		log.debug("发文期刊的载文量分析");
		String shiroTag = "research:thesis:publish:yearnums";
		HttpResult result = new HttpResult();
		try {
			result.setResult(service. queryIncludeNumsByPeriodical(xkmlid, startYear, endYear,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public HttpResult queryGyxlqkPubList(Page page,String xkmlid,String startYear,String endYear,
			String zzjgid,String name,String flag){
		log.debug("发文期刊的载文量分析");
		String shiroTag = "research:thesis:publish:list";
		HttpResult result = new HttpResult();
		try {
			result.setResult(service.queryGyxlqkPubList(page, xkmlid, startYear, endYear,zzjgid,shiroTag, name, flag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/list/export")
	public void exportGyxlqkPubList(HttpServletRequest request,HttpServletResponse response,String filename,Page page,
			String xkmlid,String startYear,String endYear,String zzjgid,String heads,String fields,String name,String flag){
		log.debug("发文期刊的载文量分析");
		String shiroTag = "research:thesis:publish:listexport";
		List<String> headsList = JSONArray.parseArray(heads,String.class);
		List<String> filedsList = JSONArray.parseArray(fields,String.class);
		ExportUtil.downloadExcel(request, response, filename, headsList, filedsList, (List<Map<String, Object>>) service.queryGyxlqkPubList(page, xkmlid, startYear, endYear,zzjgid,shiroTag, name, flag).get("rows"));
	}
}