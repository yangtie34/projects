package cn.gilight.research.project.controller;

import java.util.ArrayList;
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

import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.ExportUtil;
import cn.gilight.framework.uitl.HttpResult;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.research.project.serivice.ProjectCompereService;

/**   
* @Description: 主持人分析
* @author Sunwg  
* @date 2016年6月6日 下午3:42:30   
*/
@Controller
@RequestMapping("/project/compere")
public class ProjectCompereController {
	private Logger log = Logger.getLogger(this.getClass());

	@Resource
	private ProjectCompereService service;
	
	/** 
	* @Description: 查询主持人人数
	* @param xkmlid
	* @param startYear
	* @param endYear
	* @param zzjgid
	* @return: HttpResult
	*/
	@RequestMapping("/nums")
	@ResponseBody
	public HttpResult total(String startYear,String endYear,String zzjgid) {
		log.debug("查询主持人人数！");
		String shiroTag = "research:project:compere:total";
		HttpResult result = new HttpResult();
		try {		
			result.setSuccess(true);
			result.setResult(service.queryTotalNums(startYear, endYear, zzjgid,shiroTag));
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}
	
	/** 
	 * @Description: 按照性别查询人数
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: HttpResult
	 */
	@RequestMapping("/nums/gender")
	@ResponseBody
	public HttpResult totalByGender(String startYear,String endYear,String zzjgid) {
		log.debug("按性别查询主持人人数！");
		String shiroTag = "research:project:compere:gender";
		HttpResult result = new HttpResult();
		try {		
			result.setSuccess(true);
			int male = 0,
				female = 0,
				unknown = 0;
			List<Map<String, Object>> temp = 	service.queryAuthorNumsByGender(startYear, endYear, zzjgid,shiroTag);
			for (int i = 0; i < temp.size(); i++) {
				Map<String, Object> it = temp.get(i);
				String gender = MapUtils.getString(it, "name");
				if(gender.equals("男"))male= MapUtils.getIntValue(it, "value");
				else if(gender.equals("女"))female= MapUtils.getIntValue(it, "value");
				else if(gender.equals("未说明"))unknown= MapUtils.getIntValue(it, "value");
			}
			Map<String, Object> m1 = new HashMap<String, Object>();
			m1.put("name", "男");
			m1.put("value", male);
			Map<String, Object> m2 = new HashMap<String, Object>();
			m2.put("name", "女");
			m2.put("value", female);
			Map<String, Object> m3 = new HashMap<String, Object>();
			m3.put("name", "未说明");
			m3.put("value", unknown);
			List<Map<String, Object>> rs = new ArrayList<Map<String,Object>>();
			rs.add(m1);
			rs.add(m2);
			rs.add(m3);
			result.setResult(rs);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}
	
	/** 
	 * @Description: 按照类别查询主持人人数
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: HttpResult
	 */
	@RequestMapping("/nums/level")
	@ResponseBody
	public HttpResult totalByProjectLevel(String startYear,String endYear,String zzjgid) {
		log.debug(" 按照类别查询主持人人数！");
		String shiroTag = "research:project:compere:type";
		HttpResult result = new HttpResult();
		try {		
			result.setSuccess(true);
			result.setResult(service.queryCompereNumsByProjectLevel(startYear, endYear, zzjgid,shiroTag));
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}
	
	
	/** 
	 * @Description: 查询学历分布
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: HttpResult
	 */
	@RequestMapping("/edu")
	@ResponseBody
	public HttpResult edu(String startYear,String endYear,String zzjgid) {
		log.debug("查询学历分布！");
		String shiroTag = "research:project:compere:edu";
		HttpResult result = new HttpResult();
		try {		
			result.setSuccess(true);
			result.setResult(service.queryAuthorNumsByEducation(startYear, endYear, zzjgid,shiroTag));
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}
	
	/** 
	 * @Description: 查询主持人年龄分布
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: HttpResult
	 */
	@RequestMapping("/age")
	@ResponseBody
	public HttpResult age(String startYear,String endYear,String zzjgid) {
		log.debug("查询主持人年龄分布！");
		String shiroTag = "research:project:compere:age";
		HttpResult result = new HttpResult();
		try {		
			result.setSuccess(true);
			List<Map<String, Object>> templist = service.queryAuthorNumsByAge(startYear, endYear, zzjgid,shiroTag);
			int wfjx = 0, //无法解析  0
				qnjs = 0, //青年教师 35以下
				znjs = 0, //中年教师 35-50
				lnjs = 0; //老年教师 50以上
			for (int i = 0; i < templist.size(); i++) {
				Map<String, Object> it = templist.get(i);
				int age = MapUtils.getIntValue(it, "age");
				if (age == 0) wfjx ++;
				else if(age < 35) qnjs++;
				else if(age <= 50) znjs++;
				else if(age > 50) lnjs++;
			}
			Map<String, Object> m1 = new HashMap<String, Object>();
			m1.put("name", "无法解析");
			m1.put("value", wfjx);
			Map<String, Object> m2 = new HashMap<String, Object>();
			m2.put("name", "青年教师");
			m2.put("value", qnjs);
			Map<String, Object> m3 = new HashMap<String, Object>();
			m3.put("name", "中年教师");
			m3.put("value", znjs);
			Map<String, Object> m4 = new HashMap<String, Object>();
			m4.put("name", "老年教师");
			m4.put("value", lnjs);
			List<Map<String, Object>> rs = new ArrayList<Map<String,Object>>();
			rs.add(m1);
			rs.add(m2);
			rs.add(m3);
			rs.add(m4);
			result.setResult(rs);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}
	
	/** 
	 * @Description: 主持人职称级别
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: HttpResult
	 */
	@RequestMapping("/zyjszwjb")
	@ResponseBody
	public HttpResult zyjszwjb(String startYear,String endYear,String zzjgid) {
		log.debug("主持人职称级别！");
		String shiroTag = "research:project:compere:zyjszwjb";
		HttpResult result = new HttpResult();
		try {		
			result.setSuccess(true);
			result.setResult(service.queryAuthorNumsByZyjszwJb(startYear, endYear, zzjgid,shiroTag));
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}
	
	/** 
	 * @Description: 主持人职称
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: HttpResult
	 */
	@RequestMapping("/zyjszw")
	@ResponseBody
	public HttpResult zyjszw(String startYear,String endYear,String zzjgid) {
		log.debug("主持人职称！");
		String shiroTag = "research:project:compere:zyjszw";
		HttpResult result = new HttpResult();
		try {		
			result.setSuccess(true);
			result.setResult(service.queryAuthorNumsByZyjszw(startYear, endYear, zzjgid,shiroTag));
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}
	
	/** 
	 * @Description: 人员类别
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: HttpResult
	 */
	@RequestMapping("/rylb")
	@ResponseBody
	public HttpResult rylb(String startYear,String endYear,String zzjgid) {
		log.debug("人员类别！");
		String shiroTag = "research:project:compere:rylb";
		HttpResult result = new HttpResult();
		try {		
			result.setSuccess(true);
			result.setResult(service.queryAuthorNumsByRylb(startYear, endYear, zzjgid,shiroTag));
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}
	
	/** 
	 * @Description: 教职工来源
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: HttpResult
	 */
	@RequestMapping("/source")
	@ResponseBody
	public HttpResult source(String startYear,String endYear,String zzjgid) {
		log.debug("教职工来源！");
		String shiroTag = "research:project:compere:source";
		HttpResult result = new HttpResult();
		try {		
			result.setSuccess(true);
			result.setResult(service.queryAuthorNumsByTeaSource(startYear, endYear, zzjgid,shiroTag));
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}
	

	//详细列表
	@RequestMapping("/list")
	@ResponseBody
	public HttpResult list(Page page,String periodicalType,String xkmlid,String qklbdm,String startYear,String endYear,String zzjgid,
			String gender,String education,String age,String zcjb,String zc,String rylb,String source) {
		HttpResult result = new HttpResult();
		String shiroTag = "research:project:compere:list";
		try {		
			result.setResult(service.queryCompereListByPage(page, startYear, endYear, zzjgid,shiroTag,
					gender, education, age, zcjb, zc, rylb, source));
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
			String xkmlid,String qklbdm,String startYear,String endYear,String zzjgid,
			String gender,String education,String age,String zcjb,String zc,String rylb,String source){
		log.debug("导出科研项目列表！");
		String shiroTag = "research:project:compere:listexport";
		List<String> headsList = JSONArray.parseArray(heads, String.class);
		List<String> fieldsList = JSONArray.parseArray(fields, String.class);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> dataList = (List<Map<String, Object>>) service.
				queryCompereListByPage(page, startYear, endYear, zzjgid,shiroTag,
						gender, education, age, zcjb, zc, rylb, source).get("rows");
		ExportUtil.downloadExcel(request, response, filename, headsList, fieldsList,dataList);
	}
}