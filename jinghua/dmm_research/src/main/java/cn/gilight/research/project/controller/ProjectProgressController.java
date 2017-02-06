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
import cn.gilight.research.project.serivice.ProjectProgressService;

/**
 * @Description: 项目进度
 * @author Sunwg
 * @date 2016年6月27日 下午2:48:24
 */
@Controller
@RequestMapping("/project/progress")
public class ProjectProgressController {
	private Logger log = Logger.getLogger(this.getClass());

	@Resource
	private ProjectProgressService service;

	/** 
	* @Description: 查询在研项目数
	* @param level
	* @param startYear
	* @param endYear
	* @param zzjgid
	* @return: HttpResult
	*/
	@RequestMapping("/goingon/nums")
	@ResponseBody
	public HttpResult total(String level, String startYear, String endYear,
			String zzjgid) {
		log.debug("查询在研项目数！");
		String shiroTag = "research:project:progress:nums";
		HttpResult result = new HttpResult();
		try {
			SelfDefinedYear year = new SelfDefinedYear("",startYear,endYear);
			SelfDefinedYear last = SelfDefinedYear.getLastInterval(year);
			Map<String, Object> mp = new HashMap<String, Object>();
			mp.put("current", service.queryGoingOnProjectNums(level, startYear,endYear,zzjgid,shiroTag));
			mp.put("last",	service.queryGoingOnProjectNums(level, last.getStart(), last.getEnd(),zzjgid,shiroTag));
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
	 * @Description: 查询各单位不同状态的项目数
	 * @param level
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String,Object>>
	 */
	@RequestMapping("/depts/state")
	@ResponseBody
	public HttpResult queryProjectNumsByDeptAndState(String level,
			String startYear, String endYear, String zzjgid) {
		log.debug("查询各单位不同状态的项目数！");
		String shiroTag = "research:project:progress:deptnums";
		HttpResult result = new HttpResult();
		try {
			result.setSuccess(true);
			result.setResult(service.queryProjectNumsByDeptAndState(level,startYear, endYear,zzjgid,shiroTag));
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}

	/**
	 * @Description: 科研项目超期时长分布
	 * @param level
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String,Object>>
	 */
	@RequestMapping("/timeout")
	@ResponseBody
	public HttpResult queryTimeoutProjectNums(String level, String startYear,
			String endYear, String zzjgid) {
		log.debug("科研项目超期时长分布！");
		String shiroTag = "research:project:progress:timeout";
		HttpResult result = new HttpResult();
		try {
			result.setSuccess(true);
			result.setResult(service.queryTimeoutProjectNums(level, startYear,
					endYear,zzjgid,shiroTag));
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}

	/**
	 * @Description: 科研项目状态组成
	 * @param level
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String,Object>>
	 */
	@RequestMapping("/state")
	@ResponseBody
	public HttpResult queryProjectNumsByState(String level, String startYear,
			String endYear, String zzjgid) {
		log.debug("科研项目状态组成！");
		String shiroTag = "research:project:progress:state";
		HttpResult result = new HttpResult();
		try {
			result.setSuccess(true);
			result.setResult(service.queryProjectNumsByState(level, startYear,
					endYear,zzjgid,shiroTag));
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}

	/**
	 * @Description: 各单位科研项目到期完成率排名
	 * @param page
	 * @param level
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String,Object>>
	 */
	@RequestMapping("/order/dept")
	@ResponseBody
	public HttpResult queryOrderByDept(Page page,String level, String startYear,
			String endYear, String zzjgid) {
		log.debug("各单位科研项目到期完成率排名！");
		String shiroTag = "research:project:progress:deptrank";
		HttpResult result = new HttpResult();
		try {
			result.setResult(service.queryOrderByDept(page,level, startYear,
					endYear,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}

	/**
	 * @Description: 单位 主持人完成率排名
	 * @param page
	 * @param level
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String,Object>>
	 */
	@RequestMapping("/order/compere")
	@ResponseBody
	public HttpResult queryOrderByCompere(Page page,String level, String startYear,
			String endYear, String zzjgid) {
		log.debug("单位 主持人完成率排名");
		String shiroTag = "research:project:progress:compererank";
		HttpResult result = new HttpResult();
		try {
			result.setResult(service.queryOrderByCompere(page,level, startYear,
					endYear,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}
	
	/**
	 * @Description: 超期项目列表
	 * @param page
	 * @param level
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String,Object>>
	 */
	@RequestMapping("/timeout/projectlist")
	@ResponseBody
	public HttpResult queryTimeOutProjectList(Page page,String level, String startYear,
			String endYear, String zzjgid) {
		log.debug("超期项目列表");
		String shiroTag = "research:project:progress:timeoutlist";
		HttpResult result = new HttpResult();
		try {
			result.setResult(service.queryTimeOutProjectList(page,level, startYear,
					endYear,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("/queryProgressDetail")
	@ResponseBody
	public HttpResult queryProgressDetail(Page page,String level, String startYear,
			String endYear, String zzjgid,String name,String flag) {
		log.debug("项目列表明细");
		String shiroTag = "research:project:progress:queryProgressDetail";
		HttpResult result = new HttpResult();
		try {
			result.setResult(service.queryProgressDetail(page,level, startYear,
					endYear,zzjgid,shiroTag,name,flag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}
	
	/** 
	 * 科研项目进度列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryProgressDetail/export")
	public void exportProjectList(HttpServletRequest request,HttpServletResponse response,String filename,Page page,String name,String flag,
			String startYear, String endYear,String level, String zzjgid,String heads,String fields){
		log.debug("导出科研项目进度列表！");
		String shiroTag = "research:project:progress:listexport";
		List<String> headsList = JSONArray.parseArray(heads, String.class);
		List<String> fieldsList = JSONArray.parseArray(fields, String.class);
		ExportUtil.downloadExcel(request, response, filename, headsList, fieldsList,(List<Map<String, Object>>) service.queryProgressDetail(page,level, startYear, endYear,zzjgid,shiroTag,name,flag).get("rows"));
	}
}