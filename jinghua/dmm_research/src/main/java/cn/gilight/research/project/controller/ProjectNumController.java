package cn.gilight.research.project.controller;

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
import cn.gilight.research.project.serivice.ProjectNumsService;

/**   
* 科研项目数量
* @author Sunwg  
* @date 2016年6月6日 下午3:42:30   
*/
@Controller
@RequestMapping("/project/nums")
public class ProjectNumController {
	private Logger log = Logger.getLogger(this.getClass());

	@Resource
	private ProjectNumsService service;

	/** 
	 * 查询科研项目总数
	 */
	@RequestMapping("/total")
	@ResponseBody
	public HttpResult queryTotalNums(String startYear, String endYear,String zzjgid){
		log.debug("查询科研项目总数！");
		String shiroTag = "research:project:total:total";
		HttpResult result = new HttpResult();
		try {		
			SelfDefinedYear year = new SelfDefinedYear("",startYear,endYear);
			SelfDefinedYear last = SelfDefinedYear.getLastInterval(year);
			Map<String, Object> mp = new HashMap<String, Object>();
			mp.put("current", service.queryTotalNums(startYear, endYear,zzjgid,shiroTag));
			mp.put("last",	service.queryTotalNums(last.getStart(), last.getEnd(),zzjgid,shiroTag));
			result.setResult(mp);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}

	/** 
	 * 查询每年科研项目数量
	 */
	@RequestMapping("/years")
	@ResponseBody
	public HttpResult queryProjectNumsOfYears(String startYear, String endYear, String zzjgid){
		log.debug("查询每年科研项目数量！");
		String shiroTag = "research:project:total:yearnums";
		HttpResult result = new HttpResult();
		try {		
			result.setSuccess(true);
			result.setResult(service.queryProjectNumsOfYears(startYear, endYear,zzjgid,shiroTag) );
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}

	/** 
	 * 各单位各级别项目总数
	 */
	@RequestMapping("/dept/level")
	@ResponseBody
	public HttpResult queryProjectNumsOfDeptAndLevel(
			String startYear, String endYear, String zzjgid){
		log.debug("各单位各级别项目总数！");
		String shiroTag = "research:project:total:deptnums";
		HttpResult result = new HttpResult();
		try {		
			result.setSuccess(true);
			result.setResult(service.queryProjectNumsOfDeptAndLevel(startYear, endYear,zzjgid,shiroTag) );
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}

	/** 
	 * 项目级别分布
	 */
	@RequestMapping("/level")
	@ResponseBody
	public HttpResult queryProjectNumsOfLevel(
			String startYear, String endYear, String zzjgid){
		log.debug("项目级别分布！");
		String shiroTag = "research:project:total:level";
		HttpResult result = new HttpResult();
		try {		
			result.setSuccess(true);
			result.setResult(service.queryProjectNumsOfLevel(startYear, endYear,zzjgid,shiroTag) );
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}

	/** 
	 * 各级项目下达部门分布
	 */
	@RequestMapping("/level/issueddept")
	@ResponseBody
	public HttpResult queryIssuedDeptNumsOfProjectLevel(
			String startYear, String endYear, String zzjgid){
		log.debug("各级项目下达部门分布！");
		String shiroTag = "research:project:total:issueddept";
		HttpResult result = new HttpResult();
		try {		
			result.setSuccess(true);
			result.setResult(service.queryIssuedDeptNumsOfProjectLevel(startYear, endYear,zzjgid,shiroTag) );
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}

	/** 
	 * 项目等级分布
	 */
	@RequestMapping("/level/rank")
	@ResponseBody
	public HttpResult queryProjectRankNumsOfProjectLevel(
			String startYear, String endYear, String zzjgid){
		log.debug("项目等级分布！");
		String shiroTag = "research:project:total:levelrank";
		HttpResult result = new HttpResult();
		try {		
			result.setSuccess(true);
			result.setResult(service.queryProjectRankNumsOfProjectLevel(startYear, endYear,zzjgid,shiroTag) );
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}

	/** 
	 * 科研项目列表
	 */
	@RequestMapping("/list")
	@ResponseBody
	public HttpResult queryProjectList(Page page,String queryString,
			String startYear, String endYear, String zzjgid,
			String setupYear,String dept,String level,String issuedDept){
		log.debug("科研项目列表！");
		String shiroTag = "research:project:total:list";
		HttpResult result = new HttpResult();
		try {		
			result.setSuccess(true);
			result.setResult(service.queryProjectList(page,queryString,startYear, endYear,zzjgid,shiroTag,setupYear,dept,level,issuedDept));
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}
	
	/** 
	 * 科研项目列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/list/export")
	public void exportProjectList(HttpServletRequest request,HttpServletResponse response,String filename,Page page,String heads,String fields
			,String queryString,String startYear, String endYear, String zzjgid,
			String setupYear,String dept,String level,String issuedDept){
		log.debug("导出科研项目列表！");
		String shiroTag = "research:project:total:listexport";
		List<String> headsList = JSONArray.parseArray(heads, String.class);
		List<String> fieldsList = JSONArray.parseArray(fields, String.class);
		ExportUtil.downloadExcel(request, response, filename, headsList, fieldsList,(List<Map<String, Object>>) service.queryProjectList(page,queryString,startYear, endYear,zzjgid,shiroTag,setupYear,dept,level,issuedDept).get("rows"));
	}
}