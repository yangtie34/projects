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
import cn.gilight.research.project.serivice.ProjectFundService;

/**   
* 科研经费
* @author Sunwg  
* @date 2016年6月24日 下午3:42:30   
*/
@Controller
@RequestMapping("/project/fund")
public class ProjectFundController {
	private Logger log = Logger.getLogger(this.getClass());

	@Resource
	private ProjectFundService service;

	/** 
	 * 查询科研经费总数
	 */
	@RequestMapping("/total")
	@ResponseBody
	public HttpResult queryTotalNums(String startYear, String endYear,String zzjgid){
		log.debug("查询科研经费总数！");
		String shiroTag = "research:project:fund:total";
		HttpResult result = new HttpResult();
		try {		
			SelfDefinedYear year = new SelfDefinedYear("",startYear,endYear);
			SelfDefinedYear last = SelfDefinedYear.getLastInterval(year);
			Map<String, Object> mp = new HashMap<String, Object>();
			mp.put("current", service.queryProjectFundTotal(startYear, endYear, zzjgid,shiroTag));
			mp.put("last",	service.queryProjectFundTotal(last.getStart(), last.getEnd(), zzjgid,shiroTag));
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
	 * 经费投入变化趋势（按年 ）
	 */
	@RequestMapping("/trend")
	@ResponseBody
	public HttpResult queryYearsTrend(String startYear, String endYear,String zzjgid){
		log.debug("经费投入变化趋势（按年 ）！");
		String shiroTag = "research:project:fund:yeartrend";
		HttpResult result = new HttpResult();
		try {		
			result.setResult(service.queryFundTotalByYears(startYear, endYear, zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}
	
	/** 
	 * 各单位各级别项目经费投入
	 */
	@RequestMapping("/dept/level")
	@ResponseBody
	public HttpResult queryTotalByDeptAndProjectLevel(String startYear, String endYear,String zzjgid){
		log.debug("各单位各级别项目经费投入！");
		String shiroTag = "research:project:fund:deptlevel";
		HttpResult result = new HttpResult();
		try {		
			result.setResult(service.queryFundTotalByDeptAndProjectLevel(startYear, endYear, zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}
	

	/** 
	 * 各级别项目经费投入
	 */
	@RequestMapping("/level")
	@ResponseBody
	public HttpResult queryTotalByProjectLevel(String startYear, String endYear,String zzjgid){
		log.debug("各级别项目经费投入！");
		String shiroTag = "research:project:fund:level";
		HttpResult result = new HttpResult();
		try {		
			result.setResult(service.queryFundTotalByProjectLevel(startYear, endYear, zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}
	
	/** 
	 * 各单位项目总数，经费总额，项目均额
	 */
	@RequestMapping("/avg")
	@ResponseBody
	public HttpResult queryAvgByDeptAndProjectLevel(String startYear, String endYear,String zzjgid){
		log.debug("各级别项目经费投入！");
		String shiroTag = "research:project:fund:deptavg";
		HttpResult result = new HttpResult();
		try {		
			result.setResult(service.queryFundAvgByDept(startYear, endYear, zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}
	
	/** 
	 * 各单位项目列表
	 */
	@RequestMapping("/queryProjectDetail")
	@ResponseBody
	public HttpResult queryProjectDetail(Page page,String startYear, String endYear,String zzjgid,String name,String flag){
		log.debug("各单位项目列表明细！");
		String shiroTag = "research:project:fund:queryProjectDetail";
		HttpResult result = new HttpResult();
		try {		
			result.setResult(service.queryProjectDetail( page, startYear,  endYear, zzjgid,shiroTag, name, flag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}
	
	/** 
	 * 科研项目经费下钻列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryProjectDetail/export")
	public void exportProjectList(HttpServletRequest request,HttpServletResponse response,String filename,Page page,String name,String flag,
			String startYear, String endYear,String level, String zzjgid,String heads,String fields){
		log.debug("导出科研项目经费下钻列表！");
		String shiroTag = "research:project:fund:listexport";
		List<String> headsList = JSONArray.parseArray(heads, String.class);
		List<String> fieldsList = JSONArray.parseArray(fields, String.class);
		ExportUtil.downloadExcel(request, response, filename, headsList, fieldsList,(List<Map<String, Object>>) service.queryProjectDetail(page, startYear, endYear, zzjgid,shiroTag,name,flag).get("rows"));
	}
	
	
	
}