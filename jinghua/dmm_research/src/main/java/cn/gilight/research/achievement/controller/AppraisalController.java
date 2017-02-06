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
import cn.gilight.research.achievement.serivice.AppraisalService;

@Controller
@RequestMapping("/achievement/appraisal")
public class AppraisalController {
	private Logger log = Logger.getLogger(AppraisalController.class);

	@Resource
	private AppraisalService appraisalService;
	
	
	@RequestMapping("/code")
	@ResponseBody
	public HttpResult queryAchievementCode() {
		log.debug("获取鉴定成果查询条件的code");
		HttpResult result = new HttpResult();
		try {
			result.setResult(appraisalService.queryAchievementCode());
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/nums")
	@ResponseBody
	public HttpResult nums(String xkmlid,String startYear,String endYear,String zzjgid) {
		String shiroTag = "research:achievement:appraisal:nums";
		log.debug("鉴定成果数");
		Map<String, Object> data = new HashMap<String, Object>();
		HttpResult result = new HttpResult();
		SelfDefinedYear year = new SelfDefinedYear("",startYear,endYear);
		SelfDefinedYear last = SelfDefinedYear.getLastInterval(year);
		try {
			int currentNums = appraisalService.queryNums(xkmlid, startYear, endYear, zzjgid,shiroTag);
			int lastNums = appraisalService.queryNums(xkmlid, last.getStart(), last.getEnd(), zzjgid,shiroTag);
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
	
	@RequestMapping("/change")
	@ResponseBody
	public HttpResult change(String xkmlid,String startYear,String endYear,String zzjgid) {
		String shiroTag = "research:achievement:appraisal:change";
		log.debug("鉴定成果变化趋势数");
		HttpResult result = new HttpResult();
		try {
			result.setResult(appraisalService.queryChange(xkmlid, startYear, endYear, zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	@RequestMapping("/changeClick")
	@ResponseBody
	public HttpResult changeClick(Page page,String xkmlid,String startYear,String endYear,String zzjgid,String name,String flag) {
		String shiroTag = "research:achievement:appraisal:changeClick";
		log.debug("鉴定成果变化趋势数点击下钻事件");
		HttpResult result = new HttpResult();
		try {
			result.setResult(appraisalService.queryChangeClick(page,xkmlid, startYear, endYear, zzjgid,name,flag,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/grade")
	@ResponseBody
	public HttpResult grade(String xkmlid,String startYear,String endYear,String zzjgid) {
		String shiroTag = "research:achievement:appraisal:grade";
		log.debug("成果鉴定级别");
		HttpResult result = new HttpResult();
		try {
			result.setResult(appraisalService.queryGrade(xkmlid, startYear, endYear, zzjgid,shiroTag));
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
		String shiroTag = "research:achievement:appraisal:level";
		log.debug("鉴定成果水平");
		HttpResult result = new HttpResult();
		try {
			result.setResult(appraisalService.queryLevel(xkmlid, startYear, endYear, zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	@RequestMapping("/mode")
	@ResponseBody
	public HttpResult mode(String xkmlid,String startYear,String endYear,String zzjgid) {
		String shiroTag = "research:achievement:appraisal:mode";
		log.debug("鉴定形式");
		HttpResult result = new HttpResult();
		try {
			result.setResult(appraisalService.queryMode(xkmlid, startYear, endYear, zzjgid,shiroTag));
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
		String shiroTag = "research:achievement:appraisal:peopleRole";
		log.debug("完成人承担角色");
		HttpResult result = new HttpResult();
		try {
			result.setResult(appraisalService.queryPeopleRole(xkmlid, startYear, endYear, zzjgid,role,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}

	
	@RequestMapping("/list")
	@ResponseBody
	public HttpResult list(Page page,String xkmlid,String startYear,String endYear,String zzjgid,String param,String grade,String level,String mode) {
		String shiroTag = "research:achievement:appraisal:list";
		log.debug("鉴定成果列表");
		HttpResult result = new HttpResult();
		try {
			result.setResult(appraisalService.queryList(xkmlid, startYear, endYear, zzjgid,param,page,grade,level,mode,shiroTag));
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
		String shiroTag = "research:achievement:appraisal:export";
		log.debug("导出科研鉴定成果列表！");
		List<String> headsList = JSONArray.parseArray(heads, String.class);
		List<String> fieldsList = JSONArray.parseArray(fields, String.class);
		ExportUtil.downloadExcel(request, response, filename, headsList, fieldsList,(List<Map<String, Object>>) appraisalService.queryChangeClick(page,xkmlid, startYear, endYear, zzjgid,name,flag,shiroTag).get("rows"));
	}
	
}