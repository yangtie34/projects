package cn.gilight.research.thesis.controller;

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
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.research.thesis.service.ThesisGyxlqkInService;

/**   
* @Description: 高影响力期刊论文收录
* @author Sunwg  
* @date 2016年6月6日 下午3:42:30   
*/
@Controller
@RequestMapping("/thesis/gyxlqk/in")
public class ThesisGyxlqkInController {
	private Logger log = Logger.getLogger(this.getClass());

	@Resource
	private ThesisGyxlqkInService service;
	
	@RequestMapping("/types")
	@ResponseBody
	public HttpResult total(String xkmlid,String startYear,String endYear,String zzjgid) {
		log.debug("查询各类期刊的论文收录量！");
		String shiroTag = "research:thesis:include:types";
		
		HttpResult result = new HttpResult();
		SelfDefinedYear year = new SelfDefinedYear("",startYear,endYear);
		SelfDefinedYear last = SelfDefinedYear.getLastInterval(year);
		try {		
			List<Map<String, Object>> list = service.getFamousTypes();
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> data = list.get(i);
				String periodicalType = MapUtils.getString(data, "code");
				data.put("current", service.getIncludeNumsOfPeriodical(periodicalType, xkmlid, startYear, endYear,zzjgid,shiroTag));
				data.put("last", service.getIncludeNumsOfPeriodical(periodicalType, xkmlid, last.getStart(), last.getEnd(),zzjgid,shiroTag));
			}
			result.setResult(list);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	//论文所属单位分布
	@RequestMapping("/depts")
	@ResponseBody
	public HttpResult dept(String periodicalType,String xkmlid,String qklbdm,String startYear,String endYear,String zzjgid) {
		String shiroTag = "research:thesis:include:deptnums";
		HttpResult result = new HttpResult();
		try {		
			result.setResult(service.getIncludeNumsOfPeriodicalByDept(periodicalType, xkmlid, startYear, endYear,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	//收录论文发表时间分布
	@RequestMapping("/years")
	@ResponseBody
	public HttpResult years(String periodicalType,String xkmlid,String qklbdm,String startYear,String endYear,String zzjgid) {
		String shiroTag = "research:thesis:include:yearnums";
		HttpResult result = new HttpResult();
		try {		
			result.setResult(service.getIncludeNumsOfPeriodicalByYear(periodicalType, xkmlid, startYear, endYear,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	//SCI论文收录期刊影响因子分布
	@RequestMapping("/sci/impact")
	@ResponseBody
	public HttpResult sciImpact(String periodicalType,String xkmlid,String qklbdm,String startYear,String endYear,String zzjgid) {
		String shiroTag = "research:thesis:include:sciimpact";
		HttpResult result = new HttpResult();
		try {		
			result.setResult(service.getSCIImpactFactor(periodicalType, xkmlid, startYear, endYear,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	//SCI收录论文学科分区
	@RequestMapping("/sci/zone")
	@ResponseBody
	public HttpResult sciZone(String periodicalType,String xkmlid,String qklbdm,String startYear,String endYear,String zzjgid) {
		String shiroTag = "research:thesis:include:scizone";
		HttpResult result = new HttpResult();
		try {		
			result.setResult(service.getSCIZone(periodicalType, xkmlid, startYear, endYear,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	//详细列表
	@RequestMapping("/list")
	@ResponseBody
	public HttpResult list(Page page,String periodicalType,String xkmlid,String qklbdm,String startYear,String endYear,String zzjgid,String zzjgmc,String pubYear,String thesisInSci) {
		String shiroTag = "research:thesis:include:list";
		HttpResult result = new HttpResult();
		try {		
			result.setResult(service.queryIncludeListOfPeriodical(page, periodicalType, xkmlid, startYear, endYear,zzjgid,shiroTag, zzjgmc, pubYear,thesisInSci));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	//详细列表导出
	@RequestMapping("/list/export")
	public void exportProjectList(HttpServletRequest request,HttpServletResponse response,String filename,Page page,String heads,String fields,
			String periodicalType,String xkmlid,String qklbdm,String startYear,String endYear,String zzjgid,String zzjgmc,String pubYear,String thesisInSci){
		log.debug("导出科研项目列表！");
		String shiroTag = "research:thesis:include:exoprt";
		List<String> headsList = JSONArray.parseArray(heads, String.class);
		List<String> fieldsList = JSONArray.parseArray(fields, String.class);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> dataList = (List<Map<String, Object>>) service.queryIncludeListOfPeriodical(page, periodicalType, xkmlid, startYear, endYear,zzjgid,shiroTag, zzjgmc, pubYear,thesisInSci).get("rows");
		ExportUtil.downloadExcel(request, response, filename, headsList, fieldsList,dataList);
	}
}