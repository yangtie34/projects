package cn.gilight.research.kyjl.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.jhnu.syspermiss.util.ContextHolderUtils;

import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.ExportUtil;
import cn.gilight.framework.uitl.HttpResult;
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.research.kyjl.service.KyjlService;

@Controller
@RequestMapping("/kyjl")
public class KyjlController {
	private Logger log = Logger.getLogger(KyjlController.class);

	@Resource
	private KyjlService kyjlService;
	
	@RequestMapping("/data/refreshresult")
	@ResponseBody
	public HttpResult refreshResult(String year){
		log.debug("刷新奖励结果");
		HttpResult result = new HttpResult();
		try {
			kyjlService.refreshResult(year);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/detail/detail")
	@ResponseBody
	public HttpResult SetupDetail(Page page,String year,String xkmlid,String zzjgid,String name,String paramName,String paramValue) {
		String shiroTag = "research:kyjl:detail:detail";
		log.debug("查询奖励结果下钻明细");
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryDetail(page,year,xkmlid,zzjgid,name,paramName,paramValue,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/detail/detail/export")
	public void exportSetupDetail(HttpServletRequest request,HttpServletResponse response,String filename,Page page,String year,String xkmlid,String zzjgid,String name,String paramName,String paramValue,String heads,String fields){
		String shiroTag = "research:kyjl:detail:export";
		log.debug("导出奖励结果下钻明细！");
		List<String> headsList = JSONArray.parseArray(heads, String.class);
		List<String> fieldsList = JSONArray.parseArray(fields, String.class);
		ExportUtil.downloadExcel(request, response, filename, headsList, fieldsList,(List<Map<String, Object>>) kyjlService.queryDetail(page,year,xkmlid,zzjgid,name,paramName,paramValue,shiroTag).get("rows"));
	}
	
	@RequestMapping("/award/detail")
	@ResponseBody
	public HttpResult awardDetail(Page page,String year,String xkmlid,String zzjgid,String paramName,String paramValue) {
		String shiroTag = "research:kyjl:award:detail";
		log.debug("查询奖励结果下钻明细");
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryAwardDetail(page,year,xkmlid,zzjgid,paramName,paramValue,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	
	@RequestMapping("/award/detail/export")
	public void exportAwardDetail(HttpServletRequest request,HttpServletResponse response,String filename,Page page,String year,String xkmlid,String zzjgid,String paramName,String paramValue,String heads,String fields){
		String shiroTag = "research:kyjl:award:export";
		log.debug("导出奖励结果下钻明细！");
		List<String> headsList = JSONArray.parseArray(heads, String.class);
		List<String> fieldsList = JSONArray.parseArray(fields, String.class);
		ExportUtil.downloadExcel(request, response, filename, headsList, fieldsList,(List<Map<String, Object>>) kyjlService.queryAwardDetail(page,year,xkmlid,zzjgid,paramName,paramValue,shiroTag).get("rows"));
	}
	
	@RequestMapping("/award/fund")
	@ResponseBody
	public HttpResult queryFund(String year,String xkmlid,String zzjgid){
		String shiroTag = "research:kyjl:award:fund";
		log.debug("查询奖励总金额");
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryFund(year,xkmlid,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/award/pie")
	@ResponseBody
	public HttpResult queryAwardPie(String year,String xkmlid,String zzjgid){
		String shiroTag = "research:kyjl:award:pie";
		log.debug("查询各奖项奖励金额分布");
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryAwardPie(year,xkmlid,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/award/dept")
	@ResponseBody
	public HttpResult queryAwardDept(String year,String xkmlid,String zzjgid){
		String shiroTag = "research:kyjl:award:dept";
		log.debug("查询获奖人员单位分布");
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryAwardDept(year,xkmlid,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/award/people")
	@ResponseBody
	public HttpResult queryAwardPeople(Page page,String year,String xkmlid,String zzjgid,String param){
		String shiroTag = "research:kyjl:award:people";
		log.debug("查询获奖人员排名");
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryAwardPeople(year,xkmlid,zzjgid,page,param,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/detail/setup")
	@ResponseBody
	public HttpResult querySetupList(Page page,String year,String xkmlid,String zzjgid){
		String shiroTag = "research:kyjl:detail:setuplist";
		log.debug("查询立项奖奖励名单");
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.querySetupList(page,year,xkmlid,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/detail/level")
	@ResponseBody
	public HttpResult querySetupLevel(String year,String xkmlid,String zzjgid){
		String shiroTag = "research:kyjl:detail:level";
		log.debug("查询立项奖金额奖励级别分布");
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.querySetupLevel(year,xkmlid,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/detail/rank")
	@ResponseBody
	public HttpResult querySetupRank(String year,String xkmlid,String zzjgid){
		String shiroTag = "research:kyjl:detail:rank";
		log.debug("查询立项奖金额奖励级别分布");
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.querySetupRank(year,xkmlid,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/detail/setupdept")
	@ResponseBody
	public HttpResult querySetupDept(String year,String xkmlid,String zzjgid){
		String shiroTag = "research:kyjl:detail:setupdept";
		log.debug("查询立项奖获奖人部门分布");
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.querySetupDept(year,xkmlid,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/detail/end")
	@ResponseBody
	public HttpResult queryEndList(Page page,String year,String xkmlid,String zzjgid){
		String shiroTag = "research:kyjl:detail:endlist";
		log.debug("查询结项奖奖励名单");
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryEndList(page,year,xkmlid,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/detail/enddept")
	@ResponseBody
	public HttpResult queryEndDept(String year,String xkmlid,String zzjgid){
		String shiroTag = "research:kyjl:detail:enddept";
		log.debug("查询结项奖获奖人部门分布");
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryEndDept(year,xkmlid,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/detail/inlist")
	@ResponseBody
	public HttpResult queryThesisInList(Page page,String year,String xkmlid,String zzjgid){
		String shiroTag = "research:kyjl:detail:inlist";
		log.debug("查询论文收录获奖名单");
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryThesisInList(page,year,xkmlid,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/detail/reshiplist")
	@ResponseBody
	public HttpResult queryThesisReshipList(Page page,String year,String xkmlid,String zzjgid){
		String shiroTag = "research:kyjl:detail:reshiplist";
		log.debug("查询论文转载获奖名单");
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryThesisReshipList(page,year,xkmlid,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/detail/in")
	@ResponseBody
	public HttpResult queryThesisIn(String year,String xkmlid,String zzjgid){
		String shiroTag = "research:kyjl:detail:in";
		log.debug("查询论文收录期刊分布");
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryThesisIn(year,xkmlid,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/detail/reship")
	@ResponseBody
	public HttpResult queryThesisReship(String year,String xkmlid,String zzjgid){
		String shiroTag = "research:kyjl:detail:reship";
		log.debug("查询论文转载期刊分布分布");
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryThesisReship(year,xkmlid,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/detail/thesisdept")
	@ResponseBody
	public HttpResult queryThesisDept(String year,String xkmlid,String zzjgid){
		String shiroTag = "research:kyjl:detail:thesisdept";
		log.debug("查询论文获奖人员单位分布");
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryThesisDept(year,xkmlid,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/detail/patentlist")
	@ResponseBody
	public HttpResult queryPatentList(Page page,String year,String xkmlid,String zzjgid){
		String shiroTag = "research:kyjl:detail:patentlist";
		log.debug("查询发明专利奖励名单");
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryPatentList(page,year,xkmlid,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/detail/patenttype")
	@ResponseBody
	public HttpResult queryPatentType(String year,String xkmlid,String zzjgid){
		String shiroTag = "research:kyjl:detail:patenttype";
		log.debug("查询专利类别分布");
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryPatentType(year,xkmlid,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/detail/patentdept")
	@ResponseBody
	public HttpResult queryPatentDept(String year,String xkmlid,String zzjgid){
		String shiroTag = "research:kyjl:detail:patentdept";
		log.debug("查询发明人单位分布");
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryPatentDept(year,xkmlid,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/detail/fundlist")
	@ResponseBody
	public HttpResult queryFundList(Page page,String year,String xkmlid,String zzjgid){
		String shiroTag = "research:kyjl:detail:fundlist";
		log.debug("查询科研经费奖奖励名单");
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryFundList(page,year,xkmlid,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/detail/funddept")
	@ResponseBody
	public HttpResult queryFundDept(String year,String xkmlid,String zzjgid){
		String shiroTag = "research:kyjl:detail:funddept";
		log.debug("查询科研经费奖单位分布");
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryFundDept(year,xkmlid,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/detail/transformlist")
	@ResponseBody
	public HttpResult queryTransformList(Page page,String year,String xkmlid,String zzjgid){
		String shiroTag = "research:kyjl:detail:transformlist";
		log.debug("查询科研成果转化奖奖励名单");
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryTransformList(page,year,xkmlid,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/detail/transformdept")
	@ResponseBody
	public HttpResult queryTransformDept(String year,String xkmlid,String zzjgid){
		String shiroTag = "research:kyjl:detail:transformdept";
		log.debug("查询科研成果转化奖单位分布");
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryTransformDept(year,xkmlid,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/detail/achievelist")
	@ResponseBody
	public HttpResult queryAchievementList(Page page,String year,String xkmlid,String zzjgid){
		String shiroTag = "research:kyjl:detail:achievelist";
		log.debug("查询获奖成果奖励名单");
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryAchievementList(page,year,xkmlid,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/detail/achievementlist")
	@ResponseBody
	public HttpResult queryAchievementList2(Page page,String year,String xkmlid,String zzjgid){
		String shiroTag = "research:kyjl:detail:achievementlist";
		log.debug("查询获奖成果参与奖励名单");
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryAchievementList2(page,year,xkmlid,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/detail/achievedept")
	@ResponseBody
	public HttpResult queryAchievementDept(String year,String xkmlid,String zzjgid){
		String shiroTag = "research:kyjl:detail:achievedept";
		log.debug("查询获奖成果单位分布");
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryAchievementDept(year,xkmlid,zzjgid,shiroTag));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/personal/total")
	@ResponseBody
	public HttpResult queryAwardTotal(String teaId,String year){
		log.debug("查询个人获得奖励总金额");
		if(!StringUtils.hasText(teaId)){
			// 获取cas中登陆者的登陆名称
			AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
			teaId = principal.getName();
		}
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryPersonalFundTotal(year, teaId));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/personal/award")
	@ResponseBody
	public HttpResult queryPersonalAward(String teaId,String year){
		log.debug("查询个人各项奖励分布");
		if(!StringUtils.hasText(teaId)){
			// 获取cas中登陆者的登陆名称
			AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
			teaId = principal.getName();
		}
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryPersonalAward(year, teaId));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/personal/setup")
	@ResponseBody
	public HttpResult queryPersonalSetup(Page page,String year,String teaId){
		log.debug("查询个人立项奖");
		if(!StringUtils.hasText(teaId)){
			// 获取cas中登陆者的登陆名称
			AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
			teaId = principal.getName();
		}
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryPersonalSetup(page, year, teaId));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/personal/end")
	@ResponseBody
	public HttpResult queryPersonalEnd(Page page,String year,String teaId){
		log.debug("查询个人结项奖");
		if(!StringUtils.hasText(teaId)){
			// 获取cas中登陆者的登陆名称
			AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
			teaId = principal.getName();
		}
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryPersonalEnd(page, year, teaId));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/personal/achievement")
	@ResponseBody
	public HttpResult queryPersonalAchievement(Page page,String year,String teaId){
		log.debug("查询个人获奖成果奖奖励");
		if(!StringUtils.hasText(teaId)){
			// 获取cas中登陆者的登陆名称
			AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
			teaId = principal.getName();
		}
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryPersonalAchievement(page, year, teaId));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/personal/thesis")
	@ResponseBody
	public HttpResult queryPersonalThesis(Page page,String year,String teaId){
		log.debug("查询个人论文奖奖励");
		if(!StringUtils.hasText(teaId)){
			// 获取cas中登陆者的登陆名称
			AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
			teaId = principal.getName();
		}
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryPersonalThesis(page, year, teaId));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/personal/patent")
	@ResponseBody
	public HttpResult queryPersonalPatent(Page page,String year,String teaId){
		log.debug("查询个人发明专利奖奖励");
		if(!StringUtils.hasText(teaId)){
			// 获取cas中登陆者的登陆名称
			AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
			teaId = principal.getName();
		}
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryPersonalPatent(page, year, teaId));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/personal/awardfund")
	@ResponseBody
	public HttpResult queryPersonalAwardFund(Page page,String year,String teaId){
		log.debug("查询个人科研经费奖奖励");
		if(!StringUtils.hasText(teaId)){
			// 获取cas中登陆者的登陆名称
			AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
			teaId = principal.getName();
		}
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryPersonalFund(page, year, teaId));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/personal/transform")
	@ResponseBody
	public HttpResult queryPersonalTransform(Page page,String year,String teaId){
		log.debug("查询个人成果转化奖奖励");
		if(!StringUtils.hasText(teaId)){
			// 获取cas中登陆者的登陆名称
			AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
			teaId = principal.getName();
		}
		HttpResult result = new HttpResult();
		try {
			result.setResult(kyjlService.queryPersonalTransform(page, year, teaId));
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}
	
	
}