package cn.gilight.research.thesis.controller;

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
import cn.gilight.research.thesis.service.ThesisAuthorService;

/**   
* @Description: 论文作者分析
* @author Sunwg  
* @date 2016年6月6日 下午3:42:30   
*/
@Controller
@RequestMapping("/thesis/author")
public class ThesisAuthorController {
	private Logger log = Logger.getLogger(this.getClass());

	@Resource
	private ThesisAuthorService service;
	
	/** 
	* @Description: 查询作者人数
	* @param xkmlid
	* @param startYear
	* @param endYear
	* @param zzjgid
	* @return: HttpResult
	*/
	@RequestMapping("/nums")
	@ResponseBody
	public HttpResult total(String xkmlid,String startYear,String endYear,String zzjgid) {
		String shiroTag = "research:thesis:author:total";
		log.debug("查询作者人数！");
		HttpResult result = new HttpResult();
		try {		
			result.setSuccess(true);
			result.setResult(service.queryTotalNums(xkmlid, startYear, endYear,zzjgid,shiroTag));
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
	public HttpResult totalByGender(String xkmlid,String startYear,String endYear,String zzjgid) {
		log.debug("按性别查询作者人数！");
		String shiroTag = "research:thesis:author:gender";
		HttpResult result = new HttpResult();
		try {		
			result.setSuccess(true);
			int male = 0,
				female = 0,
				unknown = 0;
			List<Map<String, Object>> temp = 	service.queryAuthorNumsByGender(xkmlid, startYear, endYear,zzjgid,shiroTag);
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
	 * @Description: 按照类别查询作者人数
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: HttpResult
	 */
	@RequestMapping("/nums/type")
	@ResponseBody
	public HttpResult totalByType(String xkmlid,String startYear,String endYear,String zzjgid) {
		log.debug(" 按照类别查询作者人数！");
		String shiroTag = "research:thesis:author:type";
		HttpResult result = new HttpResult();
		try {		
			result.setSuccess(true);
			result.setResult(service.queryAuthorNumsByType(xkmlid, startYear, endYear,zzjgid,shiroTag));
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}
	
	/** 
	 * @Description: 按作者产量查询作者人数
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: HttpResult
	 */
	@RequestMapping("/nums/output")
	@ResponseBody
	public HttpResult totalByThesisNums(String xkmlid,String startYear,String endYear,String zzjgid) {
		log.debug("按作者产量查询作者人数！");
		String shiroTag = "research:thesis:author:output";
		HttpResult result = new HttpResult();
		try {
			int v1=0,v2=0,v3=0;
			result.setSuccess(true);
			List<Map<String, Object>> temp = service.queryAuthorNumsByOutput(xkmlid, startYear, endYear,zzjgid,shiroTag);
			for (int i = 0; i < temp.size(); i++) {
				Map<String, Object> it = temp.get(i);
				int itnum = MapUtils.getIntValue(it, "value");
				if(itnum == 1)v1 ++;
				else if(itnum == 2) v2++;
				else if(itnum > 2)v3++;
			}
			Map<String, Object> m1 = new HashMap<String, Object>();
			m1.put("name", "年产1篇");
			m1.put("value", v1);
			Map<String, Object> m2 = new HashMap<String, Object>();
			m2.put("name", "年产2篇");
			m2.put("value", v2);
			Map<String, Object> m3 = new HashMap<String, Object>();
			m3.put("name", "高产作者");
			m3.put("value", v3);
			List<Map<String, Object>> rs = new ArrayList<Map<String,Object>>();
			rs.add(m3);
			rs.add(m2);
			rs.add(m1);
			result.setResult(rs);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
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
	public HttpResult edu(String xkmlid,String startYear,String endYear,String zzjgid) {
		log.debug("查询学历分布！");
		String shiroTag = "research:thesis:author:edu";
		HttpResult result = new HttpResult();
		try {		
			result.setSuccess(true);
			result.setResult(service.queryAuthorNumsByEducation(xkmlid, startYear, endYear,zzjgid,shiroTag));
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}
	
	/** 
	 * @Description: 查询作者年龄分布
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: HttpResult
	 */
	@RequestMapping("/age")
	@ResponseBody
	public HttpResult age(String xkmlid,String startYear,String endYear,String zzjgid) {
		log.debug("查询作者年龄分布！");
		String shiroTag = "research:thesis:author:age";
		HttpResult result = new HttpResult();
		try {		
			result.setSuccess(true);
			List<Map<String, Object>> templist = service.queryAuthorNumsByAge(xkmlid, startYear, endYear,zzjgid,shiroTag);
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
	 * @Description: 作者职称级别
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: HttpResult
	 */
	@RequestMapping("/zyjszwjb")
	@ResponseBody
	public HttpResult zyjszwjb(String xkmlid,String startYear,String endYear,String zzjgid) {
		log.debug("作者职称级别！");
		String shiroTag = "research:thesis:author:zyjszwjb";
		HttpResult result = new HttpResult();
		try {		
			result.setSuccess(true);
			result.setResult(service.queryAuthorNumsByZyjszwJb(xkmlid, startYear, endYear,zzjgid,shiroTag));
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setErrmsg(e.getMessage());
		}
		return result;
	}
	
	/** 
	 * @Description: 作者职称
	 * @param xkmlid
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: HttpResult
	 */
	@RequestMapping("/zyjszw")
	@ResponseBody
	public HttpResult zyjszw(String xkmlid,String startYear,String endYear,String zzjgid) {
		log.debug("作者职称！");
		String shiroTag = "research:thesis:author:zyjszw";
		HttpResult result = new HttpResult();
		try {		
			result.setSuccess(true);
			result.setResult(service.queryAuthorNumsByZyjszw(xkmlid, startYear, endYear,zzjgid,shiroTag));
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
	public HttpResult rylb(String xkmlid,String startYear,String endYear,String zzjgid) {
		log.debug("人员类别！");
		String shiroTag = "research:thesis:author:rylb";
		HttpResult result = new HttpResult();
		try {		
			result.setSuccess(true);
			result.setResult(service.queryAuthorNumsByRylb(xkmlid, startYear, endYear,zzjgid,shiroTag));
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
	public HttpResult source(String xkmlid,String startYear,String endYear,String zzjgid) {
		log.debug("教职工来源！");
		String shiroTag = "research:thesis:author:source";
		HttpResult result = new HttpResult();
		try {		
			result.setSuccess(true);
			result.setResult(service.queryAuthorNumsByTeaSource(xkmlid, startYear, endYear,zzjgid,shiroTag));
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
			String gender,String value,String education,String age,String zcjb,String zc,String rylb,String source) {
		String shiroTag = "research:thesis:author:list";
		HttpResult result = new HttpResult();
		try {		
			result.setResult(service.queryAuthorListByPage(page, xkmlid, startYear, endYear,zzjgid,shiroTag,
					gender,value, education, age, zcjb, zc, rylb, source));
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
			String gender,String value,String education,String age,String zcjb,String zc,String rylb,String source){
		String shiroTag = "research:thesis:author:listexoprt";
		log.debug("导出科研项目列表！");
		List<String> headsList = JSONArray.parseArray(heads, String.class);
		List<String> fieldsList = JSONArray.parseArray(fields, String.class);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> dataList = (List<Map<String, Object>>) service.queryAuthorListByPage(page, xkmlid, startYear, endYear,zzjgid,shiroTag,
				gender,value, education, age, zcjb, zc, rylb, source).get("rows");
		ExportUtil.downloadExcel(request, response, filename, headsList, fieldsList,dataList);
	}
}