package cn.gilight.research.writing.controller;

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
import cn.gilight.research.writing.service.WorkService;

@Controller
@RequestMapping("/writing/work")
public class WorkController {
	
	private Logger log = Logger.getLogger(WorkController.class);

	@Resource
	private WorkService workService;
	
	
	
	@RequestMapping("/nums")
	@ResponseBody
	public HttpResult nums(String xkmlid,String startYear,String endYear,String zzjgid) {
		String shiroTag = "research:writing:work:nums";
		log.debug("查询著作数量");
		Map<String, Object> data = new HashMap<String, Object>();
		HttpResult result = new HttpResult();
		SelfDefinedYear year = new SelfDefinedYear("",startYear,endYear);
		SelfDefinedYear last = SelfDefinedYear.getLastInterval(year);
		try {
			int currentNums = workService.queryWorkNums(xkmlid, startYear, endYear, zzjgid,shiroTag);
			int lastNums = workService.queryWorkNums(xkmlid, last.getStart(), last.getEnd(), zzjgid,shiroTag);
			int growth = currentNums - lastNums;
			String increase = "";
			if(lastNums != 0 && currentNums != 0){
				increase = MathUtils.getPercent(growth, lastNums);
			}else if(lastNums != 0 && currentNums == 0 ){
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
	
	@RequestMapping("/authorRole")
	@ResponseBody
	public HttpResult queryAuthorRole(String xkmlid,String startYear,String endYear,String zzjgid) {
		String shiroTag = "research:writing:work:authorRole";
		log.debug("获取著作作者承担角色");
		HttpResult result = new HttpResult();
		try {
			result.setResult(workService.queryWorkAuthorRole(xkmlid, startYear, endYear, zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/numsChange")
	@ResponseBody
	public HttpResult queryWorkNumsChange(String xkmlid,String startYear,String endYear,String zzjgid) {
		String shiroTag = "research:writing:work:numsChange";
		log.debug("获取著作数量历年变化");
		HttpResult result = new HttpResult();
		try {
			result.setResult(workService.queryWorkNumsChange(xkmlid, startYear, endYear, zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/dept")
	@ResponseBody
	public HttpResult queryWorkDept(String xkmlid,String startYear,String endYear,String zzjgid) {
		String shiroTag = "research:writing:work:dept";
		log.debug("获取著作单位分布");
		HttpResult result = new HttpResult();
		try {
			result.setResult(workService.queryWorkDept(xkmlid, startYear, endYear, zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public HttpResult queryWorkList(Page page,String xkmlid,String startYear,String endYear,String zzjgid,String param) {
		String shiroTag = "research:writing:work:list";
		log.debug("获取著作列表");
		HttpResult result = new HttpResult();
		try {
			result.setResult(workService.queryWorkList(page,xkmlid, startYear, endYear, zzjgid,param,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/clickDetail")
	@ResponseBody
	public HttpResult clickDetail(Page page,String xkmlid,String startYear,String endYear,String zzjgid,String name,String flag) {
		String shiroTag = "research:writing:work:clickDetail";
		log.debug("鉴定成果变化趋势数点击下钻事件");
		HttpResult result = new HttpResult();
		try {
			result.setResult(workService.queryClickDetail(page,xkmlid, startYear, endYear, zzjgid,name,flag,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	/** 
	 * 著作列表
	 */
	@RequestMapping("/clickDetail/export")
	public void exportProjectList(HttpServletRequest request,HttpServletResponse response,String filename,Page page,String name,String flag,
			String startYear, String endYear,String xkmlid, String zzjgid,String heads,String fields){
		String shiroTag = "research:writing:work:export	";
		log.debug("导出著作列表！");
		List<String> headsList = JSONArray.parseArray(heads, String.class);
		List<String> fieldsList = JSONArray.parseArray(fields, String.class);
		ExportUtil.downloadExcel(request, response, filename, headsList, fieldsList,(List<Map<String, Object>>) workService.queryClickDetail(page,xkmlid, startYear, endYear, zzjgid,name,flag,shiroTag).get("rows"));
	}
	
	
}