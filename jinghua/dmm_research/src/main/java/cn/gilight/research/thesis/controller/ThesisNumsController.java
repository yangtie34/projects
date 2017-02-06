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

import cn.gilight.business.model.SelfDefinedYear;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.ExportUtil;
import cn.gilight.framework.uitl.HttpResult;
import cn.gilight.research.thesis.service.ThesisNumService;

import com.alibaba.fastjson.JSONArray;

/**   
* @Description: 科研论文处理
* @author Sunwg  
* @date 2016年6月6日 下午3:42:30   
*/
@Controller
@RequestMapping("/thesis/publish")
public class ThesisNumsController {
	private Logger log = Logger.getLogger(ThesisNumsController.class);

	@Resource
	private ThesisNumService numService;
	
	@RequestMapping("/total")
	@ResponseBody
	public HttpResult total(String xkmlid,String startYear,String endYear,String zzjgid) {
		String shiroTag = "research:thesis:total:nums";
		log.debug("论文发表总量");
		Map<String, Object> data = new HashMap<String, Object>();
		HttpResult result = new HttpResult();
		SelfDefinedYear year = new SelfDefinedYear("",startYear,endYear);
		SelfDefinedYear last = SelfDefinedYear.getLastInterval(year);
		try {
			data.put("current", numService.queryTotalNums(xkmlid, startYear, endYear, zzjgid,shiroTag));
			data.put("last", numService.queryTotalNums(xkmlid, last.getStart(), last.getEnd(), zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		result.setResult(data);
		return result;
	}
	
	@RequestMapping("/fistdept")
	@ResponseBody
	public HttpResult fistdept(String xkmlid,String startYear,String endYear,String zzjgid) {
		log.debug("以我校为第一单位发表");
		String shiroTag = "research:thesis:total:firstauth";
		Map<String, Object> data = new HashMap<String, Object>();
		HttpResult result = new HttpResult();
		SelfDefinedYear year = new SelfDefinedYear("",startYear,endYear);
		SelfDefinedYear last = SelfDefinedYear.getLastInterval(year);
		try {
			data.put("isFirst", numService.firstAuthThesisNums(xkmlid, startYear, endYear, zzjgid,shiroTag,true));
			data.put("notFirst", numService.firstAuthThesisNums(xkmlid, last.getStart(), last.getEnd(), zzjgid,shiroTag,false));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		result.setResult(data);
		return result;
	}
	
	@RequestMapping("/depts")
	@ResponseBody
	public HttpResult depts(String xkmlid,String startYear,String endYear) {
		String shiroTag = "research:thesis:total:deptnums";
		log.debug("各单位论文发表量和占比");
		HttpResult result = new HttpResult();
		try {
			result.setResult(numService.queryThesisNumsByDepts(xkmlid, startYear, endYear,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/years")
	@ResponseBody
	public HttpResult years(String xkmlid,String startYear,String endYear,String zzjgid) {
		log.debug("单位毎年论文发表量和占比");
		String shiroTag = "research:thesis:total:yearnums";
		HttpResult result = new HttpResult();
		try {
			result.setResult(numService.queryThesisNumsByYears(xkmlid, startYear, endYear,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/qklbfbl")
	@ResponseBody
	public HttpResult queryGywsykLwfbl(String xkmlid,String startYear,String endYear,String zzjgid) {
		String shiroTag = "research:thesis:total:ywsyk";
		log.debug("各期刊类别发表量和占比");
		HttpResult result = new HttpResult();
		try {
			result.setResult(numService.queryGywsykLwfbl(xkmlid, startYear, endYear,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public HttpResult queryNumsList(Page page, String xkmlid, String startYear,
			String endYear, String zzjgid,String name,String flag){
		String shiroTag  ="research:thesis:total:list";
		log.debug("各部门论文发表量下钻");
		HttpResult result = new HttpResult();
		try {
			result.setResult(numService.queryNumsList(page, xkmlid, startYear, endYear, zzjgid,shiroTag,name,flag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}
	@SuppressWarnings("unchecked")
	@RequestMapping("/list/export")
	public void exportNumsList(HttpServletRequest request,HttpServletResponse response,Page page,String filename,
			String xkmlid,String startYear,String endYear,String zzjgid,String heads,String fields,String name,String flag){
		log.debug("导出各部门论文发表量");
		String shiroTag = "research:thesis:total:listexport";
		List<String> headsList = JSONArray.parseArray(heads, String.class);
		List<String> fieldsList = JSONArray.parseArray(fields, String.class);
		ExportUtil.downloadExcel(request, response, filename, headsList, fieldsList,(List<Map<String, Object>>) numService.queryNumsList(page, xkmlid, startYear, endYear, zzjgid,shiroTag,name,flag).get("rows"));
	}
	
}