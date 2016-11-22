package com.jhkj.mosdc.sc.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.service.BaseService;
import com.jhkj.mosdc.framework.util.ExportUtil;
import com.jhkj.mosdc.framework.util.Utils4Service;
import com.jhkj.mosdc.sc.service.IThesisNumsRanksService;

public class ThesisNumsRanksServiceImpl implements IThesisNumsRanksService {

	private BaseService baseService;

	public BaseService getBaseService() {
		return baseService;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	private BaseDao baseDao;

	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String eINumsRanks(String typeCode) {
		JSONObject jsonObject = JSONObject.fromObject(typeCode);
		String codeType = (String) jsonObject.get("codeType");
		String sql = "select nvl(count(*),'0') counts, trti.year_ years from t_res_thesis_in trti "
				+ "inner join t_res_thesis trt on trt.id = trti.thesis_id "
				+ "inner join t_code_subject tcs on tcs.code_=trt.project_id "
				+ "where tcs.code_='"
				+ codeType
				+ "' "
				+ "group by trti.year_ order by trti.year_";
		List<Map> results = baseDao.querySqlList(sql);
		return JSONArray.fromObject(results).toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String iSTPNumsRanks(String typeCode) {
		JSONObject jsonObject = JSONObject.fromObject(typeCode);
		String codeType = (String) jsonObject.get("codeType");
		String sql = "select nvl(count(*),'0') counts, trti.year_ years from t_res_thesis_istp trti "
				+ "inner join t_res_thesis trt on trt.id = trti.thesis_id "
				+ "inner join t_code_subject tcs on tcs.code_=trt.project_id "
				+ "where tcs.code_='"
				+ codeType
				+ "' "
				+ "group by trti.year_ order by trti.year_";
		List<Map> results = baseDao.querySqlList(sql);
		return JSONArray.fromObject(results).toString();
	}

	@Override
	public String cPCINumsRanks(String typeCode) {
		JSONObject jsonObject = JSONObject.fromObject(typeCode);
		String codeType = (String) jsonObject.get("codeType");
		String sql = "select nvl(count(*),'0') counts, trtc.year_ years from t_res_thesis_cpcis trtc "
				+ "inner join t_res_thesis trt on trt.id = trtc.thesis_id "
				+ "inner join t_code_subject tcs on tcs.code_=trt.project_id "
				+ "where tcs.code_='"
				+ codeType
				+ "' "
				+ "group by trtc.year_ order by trtc.year_";
		List<Map> results = baseDao.querySqlList(sql);
		return JSONArray.fromObject(results).toString();
	}

	@Override
	public String allRanks(String params) {
		String sql = "select trtir.year_ years, trtir.type_ includeTpes, trtir.ranks_ ranks from t_res_thesis_include_ranks trtir group by trtir.year_, trtir.type_, trtir.ranks_ order by trtir.year_, trtir.type_ desc";
		List<Map> results = baseDao.querySqlList(sql);
		return JSONArray.fromObject(results).toString();
	}

	@Override
	public String eIYears(String params) {
		String sql = "select trti.year_ years from t_res_thesis_in trti inner join t_res_thesis trt on trt.id = trti.thesis_id group by trti.year_ order by trti.year_ asc";
		List<Map> results = baseDao.querySqlList(sql);
		if (results.size() == 0) {
			Map obj = new HashMap();
			obj.put("YEARS", "2015");
			results.add(obj);
		}
		return JSONArray.fromObject(results).toString();
	}

	@Override
	public String iSYears(String params) {
		String sql = "select trti.year_ years from t_res_thesis_istp trti inner join t_res_thesis trt on trt.id = trti.thesis_id group by trti.year_ order by trti.year_ asc";
		List<Map> results = baseDao.querySqlList(sql);
		if (results.size() == 0) {
			Map obj = new HashMap();
			obj.put("YEARS", "2015");
			results.add(obj);
		}
		return JSONArray.fromObject(results).toString();
	}

	@Override
	public String cPYears(String params) {
		String sql = "select trtc.year_ years from t_res_thesis_cpcis trtc inner join t_res_thesis trt on trt.id = trtc.thesis_id group by trtc.year_ order by trtc.year_ asc";
		List<Map> results = baseDao.querySqlList(sql);
		if (results.size() == 0) {
			Map obj = new HashMap();
			obj.put("YEARS", "2015");
			results.add(obj);
		}
		return JSONArray.fromObject(results).toString();
	}

	@Override
	public String departmentThesisNums(String datas) {
		JSONObject jsonObject = JSONObject.fromObject(datas);
		String codeType = (String) jsonObject.getJSONObject("typeCulture").get(
				"codeType");
		String years = (String) jsonObject.getJSONObject("chooseYear").get(
				"years");
		String sql = "select nvl(tc.name_,'未维护') names, count(*) counts from t_res_thesis trt "
				+ "inner join t_Code_Dept tc on tc.code_ = trt.dept_id "
				+ "inner join t_code_subject tcs on tcs.code_=trt.project_id "
				+ "where trt.year_='"
				+ years
				+ "' and tcs.code_='"
				+ codeType
				+ "' " + "group by tc.name_";
		List<Map> results = baseDao.querySqlList(sql);
		return JSONArray.fromObject(results).toString();
	}

	@Override
	public String awardThesisRate(String datas) {
		JSONObject jsonObject = JSONObject.fromObject(datas);
		String codeType = (String) jsonObject.getJSONObject("typeCulture").get(
				"codeType");
		String years = (String) jsonObject.getJSONObject("chooseYear").get(
				"years");
		String sql = "select nvl(tc.name_,'未维护') names, count(*) counts from t_res_thesis t "
				+ "inner join t_code_dept tc on tc.code_ = t.dept_id "
				+ "inner join t_res_thesis_award trta on trta.thesis_id = t.id "
				+ "inner join t_code_subject tcs on tcs.code_=t.project_id "
				+ "where t.year_='"
				+ years
				+ "' and tcs.code_='"
				+ codeType
				+ "' " + "group by tc.name_";
		List<Map> results = baseDao.querySqlList(sql);
		return JSONArray.fromObject(results).toString();
	}

	@Override
	public String thesisInclude(String datas) {
		JSONObject jsonObject = JSONObject.fromObject(datas);
		String codeType = (String) jsonObject.getJSONObject("typeCulture").get(
				"codeType");
		String years = (String) jsonObject.getJSONObject("chooseYear").get(
				"years");
		String sql = "select names, sum(counts) counts from ( "
				+ "select tc.name_ names, count(*) counts from t_res_thesis trt "
				+ "inner join t_code_dept tc on tc.code_ = trt.dept_id "
				+ "inner join t_code_subject tcs on tcs.code_=trt.project_id "
				+ "inner join t_res_thesis_in trti on trti.thesis_id = trt.id "
				+ "where trt.year_='"
				+ years
				+ "' and tcs.code_='"
				+ codeType
				+ "' "
				+ "group by tc.name_ "
				+ "union all "
				+ "select tc.name_ names, count(*) counts from t_res_thesis trt "
				+ "inner join t_code_dept tc on tc.code_ = trt.dept_id "
				+ "inner join t_code_subject tcs on tcs.code_=trt.project_id "
				+ "inner join t_res_thesis_istp trtis on trtis.thesis_id = trt.id "
				+ "where trt.year_='"
				+ years
				+ "' and tcs.code_='"
				+ codeType
				+ "' "
				+ "group by tc.name_ "
				+ "union all "
				+ "select tc.name_ names, count(*) counts from t_res_thesis trt "
				+ "inner join t_code_dept tc on tc.code_ = trt.dept_id "
				+ "inner join t_code_subject tcs on tcs.code_=trt.project_id "
				+ "inner join t_res_thesis_cpcis trtc on trtc.thesis_id = trt.id "
				+ "where trt.year_='"
				+ years
				+ "' and tcs.code_='"
				+ codeType
				+ "' " + "group by tc.name_ ) " + "group by names";
		List<Map> results = baseDao.querySqlList(sql);
		if (results.size() == 0) {
			Map obj = new HashMap();
			obj.put("year", years);
			obj.put("codeType", codeType);
			results.add(obj);
		}
		for (Map obj : results) {
			obj.put("year", years);
			obj.put("codeType", codeType);
		}
		return JSONArray.fromObject(results).toString();
	}

	@Override
	public String queryAllCountsThesis(String datas) {
		String sql = "select count(1) counts from t_res_thesis";
		List<Map> results = baseDao.querySqlList(sql);
		return JSONArray.fromObject(results).toString();
	}

	@Override
	public String pulishThesis(String datas) {
		JSONObject jsonObject = JSONObject.fromObject(datas);
		String deptName = (String) jsonObject.getJSONObject("deptNames").get(
				"deptName");
		String years = (String) jsonObject.getJSONObject("years").get("year");
		String codeType = (String) jsonObject.getJSONObject("typeCodes").get(
				"typeCode");
		String sql = "select tcode.name_ periodical, count(1) counts from t_res_thesis trt "
				+ "inner join t_code_dept tc on tc.code_ = trt.dept_id "
				+ "inner join t_code_subject tcs on tcs.code_ = trt.project_id "
				+ "inner join t_code tcode on tcode.code_ = trt.periodical_type_code "
				+ "where tc.name_='"
				+ deptName
				+ "' and trt.year_='"
				+ years
				+ "' and tcs.code_='"
				+ codeType
				+ "' and tcode.code_type='RES_PERIODICAL_TYPE_CODE'"
				+ "group by tcode.name_";
		List<Map> results = baseDao.querySqlList(sql);
		if (results.size() == 0) {
			Map obj = new HashMap();
			obj.put("name", "");
			obj.put("value", "");
			obj.put("codeType", codeType);
			obj.put("deptName", deptName);
			obj.put("years", years);
			results.add(obj);
		} else {
			for (Map obj : results) {
				obj.put("name", obj.get("PERIODICAL"));
				obj.put("value", obj.get("COUNTS"));
				obj.remove("PERIODICAL");
				obj.remove("COUNTS");
				obj.put("codeType", codeType);
				obj.put("deptName", deptName);
				obj.put("years", years);
			}
		}
		return JSONArray.fromObject(results).toString();
	}

	@Override
	public String awardThesisRanks(String datas) {
		JSONObject jsonObject = JSONObject.fromObject(datas);
		String deptName = (String) jsonObject.getJSONObject("deptNames").get(
				"deptName");
		String years = (String) jsonObject.getJSONObject("years").get("year");
		String codeType = (String) jsonObject.getJSONObject("typeCodes").get(
				"typeCode");
		String sql = "select tc.name_ names, count(1) counts from t_res_thesis_award trta "
				+ "inner join t_code tc on tc.code_=trta.award_rank_code "
				+ "inner join  t_res_thesis trt on trt.id = trta.thesis_id "
				+ "inner join t_code_dept tcd on tcd.code_ = trt.dept_id "
				+ "inner join t_code_subject tcs on tcs.code_ = trt.project_id "
				+ "where tc.code_type='RES_THESIS_RANK_CODE' "
				+ "and tcd.name_='"
				+ deptName
				+ "' "
				+ "and trt.year_='"
				+ years
				+ "' "
				+ "and tcs.code_='"
				+ codeType
				+ "' group by tc.name_ " + "order by tc.name_ desc";

		List<Map> results = baseDao.querySqlList(sql);
		for (Map obj : results) {
			obj.put("deptName", deptName);
			obj.put("years", years);
			obj.put("codeType", codeType);
		}
		return JSONArray.fromObject(results).toString();
	}

	@Override
	public String eIInclude(String datas) {
		JSONObject jsonObject = JSONObject.fromObject(datas);
		String deptName = (String) jsonObject.getJSONObject("deptNames").get(
				"deptName");
		String years = (String) jsonObject.getJSONObject("years").get("year");
		String codeType = (String) jsonObject.getJSONObject("typeCodes").get(
				"typeCode");
		String sql = "select count(*) counts from t_res_thesis_in trti "
				+ "inner join t_res_thesis trt on trt.id = trti.thesis_id "
				+ "inner join t_code_dept tc on tc.code_ = trt.dept_id "
				+ "inner join t_code_subject tcs on tcs.code_ = trt.project_id "
				+ "where tc.name_='" + deptName + "' and trt.year_='" + years
				+ "' and tcs.code_='" + codeType + "'";
		List<Map> results = baseDao.querySqlList(sql);
		for (Map obj : results) {
			obj.put("name", "SCI/SCIE/EI");
			obj.put("value", obj.get("COUNTS"));
			obj.put("deptName", deptName);
			obj.put("years", years);
			obj.put("codeType", codeType);
			obj.remove("COUNTS");
		}
		return JSONArray.fromObject(results).toString();
	}

	@Override
	public String iSInclude(String datas) {
		JSONObject jsonObject = JSONObject.fromObject(datas);
		String deptName = (String) jsonObject.getJSONObject("deptNames").get(
				"deptName");
		String years = (String) jsonObject.getJSONObject("years").get("year");
		String codeType = (String) jsonObject.getJSONObject("typeCodes").get(
				"typeCode");
		String sql = "select count(*) counts from t_res_thesis_istp trti "
				+ "inner join t_res_thesis trt on trt.id = trti.thesis_id "
				+ "inner join t_code_dept tc on tc.code_ = trt.dept_id "
				+ "inner join t_code_subject tcs on tcs.code_ = trt.project_id "
				+ "where tc.name_='" + deptName + "' and trt.year_='" + years
				+ "' and tcs.code_='" + codeType + "'";
		List<Map> results = baseDao.querySqlList(sql);
		for (Map obj : results) {
			obj.put("name", "ISTP");
			obj.put("value", obj.get("COUNTS"));
			obj.remove("COUNTS");
		}
		return JSONArray.fromObject(results).toString();
	}

	@Override
	public String cPInclude(String datas) {
		JSONObject jsonObject = JSONObject.fromObject(datas);
		String deptName = (String) jsonObject.getJSONObject("deptNames").get(
				"deptName");
		String years = (String) jsonObject.getJSONObject("years").get("year");
		String codeType = (String) jsonObject.getJSONObject("typeCodes").get(
				"typeCode");
		String sql = "select count(*) counts from t_res_thesis_cpcis trti "
				+ "inner join t_res_thesis trt on trt.id = trti.thesis_id "
				+ "inner join t_code_dept tc on tc.code_ = trt.dept_id "
				+ "inner join t_code_subject tcs on tcs.code_ = trt.project_id "
				+ "where tc.name_='" + deptName + "' and trt.year_='" + years
				+ "' and tcs.code_='" + codeType + "'";
		List<Map> results = baseDao.querySqlList(sql);
		for (Map obj : results) {
			obj.put("name", "CPCI-S");
			obj.put("value", obj.get("COUNTS"));
			obj.remove("COUNTS");
		}
		return JSONArray.fromObject(results).toString();
	}

	@Override
	public String awardTrend(String datas) {
		JSONObject jsonObject = JSONObject.fromObject(datas);
		String typeCode = (String) jsonObject.get("codeType");
		String sql = "select trt.year_ years,trta.award_name names, tcode.name_ rankName, count(*) counts from t_res_thesis_award trta "
				+ "inner join  t_res_thesis trt on trt.id = trta.thesis_id "
				+ "inner join t_code_dept tc on tc.code_ = trt.dept_id "
				+ "inner join t_code_subject tcs on tcs.code_ = trt.project_id "
				+ "inner join t_code tcode on tcode.code_ = trta.award_rank_code "
				+ "where tcode.code_type='RES_THESIS_RANK_CODE' and tcs.code_='"
				+ typeCode
				+ "' "
				+ "group by tcode.name_, trta.award_name, trt.year_ "
				+ "order by trt.year_ asc";
		List<Map> results = baseDao.querySqlList(sql);
		return JSONArray.fromObject(results).toString();
	}

	@Override
	public String includeTrend(String datas) {
		JSONObject jsonObject = JSONObject.fromObject(datas);
		String typeCode = (String) jsonObject.get("codeType");
		String sql = "select trt.year_ years, tcode.name_ periodical, count(1) counts from t_res_thesis trt "
				+ "inner join t_code_dept tc on tc.code_ = trt.dept_id "
				+ "inner join t_code_subject tcs on tcs.code_ = trt.project_id "
				+ "inner join t_code tcode on tcode.code_ = trt.periodical_type_code "
				+ "where tcode.code_type='RES_PERIODICAL_TYPE_CODE' "
				+ "and tcs.code_='"
				+ typeCode
				+ "' group by tcode.name_, trt.year_ "
				+ "order by trt.year_ asc";
		List<Map> results = baseDao.querySqlList(sql);
		return JSONArray.fromObject(results).toString();
	}

	@Override
	public String includeTrendNames(String datas) {
		JSONObject jsonObject = JSONObject.fromObject(datas);
		String typeCode = (String) jsonObject.get("codeType");
		String sql = "select tcode.name_ periodical, count(1) counts from t_res_thesis trt "
				+ "inner join t_code_dept tc on tc.code_ = trt.dept_id "
				+ "inner join t_code_subject tcs on tcs.code_ = trt.project_id "
				+ "inner join t_code tcode on tcode.code_ = trt.periodical_type_code "
				+ "where tcode.code_type='RES_PERIODICAL_TYPE_CODE' "
				+ "and tcs.code_='" + typeCode + "' group by tcode.name_";
		List<Map> results = baseDao.querySqlList(sql);
		return JSONArray.fromObject(results).toString();
	}

	@Override
	public String thesisTrend(String datas) {
		JSONObject jsonObject = JSONObject.fromObject(datas);
		String typeCode = (String) jsonObject.get("codeType");
		String sql = "select years, names, sum(counts) counts from ( "
				+ "select tc.name_ names, count(*) counts, trt.year_ years from t_res_thesis trt "
				+ "inner join t_code_dept tc on tc.code_ = trt.dept_id "
				+ "inner join t_code_subject tcs on tcs.code_=trt.project_id "
				+ "inner join t_res_thesis_in trti on trti.thesis_id = trt.id "
				+ "where tcs.code_='"
				+ typeCode
				+ "' "
				+ "group by tc.name_, trt.year_ "
				+ "union all "
				+ "select tc.name_ names, count(*) counts, trt.year_ years from t_res_thesis trt "
				+ "inner join t_code_dept tc on tc.code_ = trt.dept_id "
				+ "inner join t_code_subject tcs on tcs.code_=trt.project_id "
				+ "inner join t_res_thesis_istp trtis on trtis.thesis_id = trt.id "
				+ "where tcs.code_='"
				+ typeCode
				+ "' "
				+ "group by tc.name_, trt.year_ "
				+ "union all "
				+ "select tc.name_ names, count(*) counts, trt.year_  from t_res_thesis trt "
				+ "inner join t_code_dept tc on tc.code_ = trt.dept_id "
				+ "inner join t_code_subject tcs on tcs.code_=trt.project_id "
				+ "inner join t_res_thesis_cpcis trtc on trtc.thesis_id = trt.id "
				+ "where tcs.code_='" + typeCode
				+ "' group by tc.name_, trt.year_ ) "
				+ "group by names, years order by years asc";

		List<Map> results = baseDao.querySqlList(sql);
		return JSONArray.fromObject(results).toString();
	}

	@Override
	public String awardRatesAll(String datas) {
		JSONObject jsonObject = JSONObject.fromObject(datas);
		String typeCode = (String) jsonObject.get("codeType");
		String sql = "select trt.year_ years,tc.name_ names, count(*) counts from t_res_thesis_award trta "
				+ "inner join  t_res_thesis trt on trt.id = trta.thesis_id "
				+ "inner join t_code_dept tc on tc.code_ = trt.dept_id "
				+ "inner join t_code_subject tcs on tcs.code_ = trt.project_id "
				+ "inner join t_code tcode on tcode.code_ = trta.award_rank_code "
				+ "where tcode.code_type='RES_THESIS_RANK_CODE' "
				+ "and tcs.code_='"
				+ typeCode
				+ "' "
				+ "group by trt.year_,tc.name_ " + "order by trt.year_";

		List<Map> results = baseDao.querySqlList(sql);
		return JSONArray.fromObject(results).toString();
	}

	@Override
	public String queryAllAwardCounts(String datas) {
		String sql = "select count(1) counts from t_res_thesis_award";
		List<Map> results = baseDao.querySqlList(sql);
		return JSONArray.fromObject(results).toString();
	}

	@Override
	public String queryAllIncludeCounts(String datas) {
		String sql = "select sum(allIncludeCounts.counts) counts from( "
				+ "select count(0) counts  from t_res_thesis_istp "
				+ "union all "
				+ "select count(0) counts from t_res_thesis_in "
				+ "union all "
				+ "select count(0) counts from t_res_thesis_cpcis) allIncludeCounts";
		List<Map> results = baseDao.querySqlList(sql);
		return JSONArray.fromObject(results).toString();
	}

	@Override
	public String queryThesisLimitPage(String datas) {
		JSONObject jsonObject = JSONObject.fromObject(datas);
		String years = (String) jsonObject.getJSONObject("years").get("year");
		String deptName = (String) jsonObject.getJSONObject("deptName").get(
				"dName");
		String typeCode = (String) jsonObject.getJSONObject("typeCode").get(
				"codeType");
		String booksName = (String) jsonObject.getJSONObject("bNames").get(
				"booksNames");
		Map paramsMap = Utils4Service.packageParams(datas);
		Map result = new HashMap();
		if (jsonObject.containsKey("whereNames")
				&& !(jsonObject.get("whereNames").equals(""))) {
			String conditionsWhere = (String) jsonObject.get("whereNames");
			result = baseDao.queryTableContentBySQL(
					punishConditionsThesisAll(deptName, years, typeCode,
							booksName, conditionsWhere), paramsMap);
		} else {
			result = baseDao.queryTableContentBySQL(
					punishThesisAll(deptName, years, typeCode, booksName),
					paramsMap);
		}

		List<Map> queryList = (List) result.get("queryList");
		Map backval = new HashMap();
		backval.put("count", result.get("count"));
		backval.put("data", queryList);
		backval.put("success", true);
		return JSONObject.fromObject(backval).toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public HSSFWorkbook exportExcelPilish(String datas) throws Exception {
		JSONObject jsonObject = JSONObject.fromObject(datas);
		String years = (String) jsonObject.getJSONObject("years").get("year");
		String deptName = (String) jsonObject.getJSONObject("deptName").get(
				"dName");
		String typeCode = (String) jsonObject.getJSONObject("typeCode").get(
				"codeType");
		String booksName = (String) jsonObject.getJSONObject("bNames").get(
				"booksNames");
		String str = "论文作者,院系名称,年卷期页,期刊类别,论文名称,发表年份";
		List<Map<String, Object>> results = baseDao
				.querySqlList(punishThesisAll(deptName, years, typeCode,
						booksName));
		HSSFWorkbook workbook = ExportUtil.getHSSFWorkbookByMap(str.split(","),
				"期刊论文详情", results);
		return workbook;
	}

	@Override
	public String queryAwardThesisLimitPage(String datas) {
		JSONObject jsonObject = JSONObject.fromObject(datas);
		Map paramsMap = Utils4Service.packageParams(datas);
		String deptName = (String) jsonObject.getJSONObject("deptName").get(
				"dName");
		String years = (String) jsonObject.getJSONObject("years").get("year");
		String codeType = (String) jsonObject.getJSONObject("typeCode").get(
				"codeType");
		String awardId = (String) jsonObject.getJSONObject("awradId").get(
				"awradId");
		Map result = new HashMap();
		if (jsonObject.containsKey("whereNames")
				&& !(jsonObject.get("whereNames").equals(""))) {
			String conditionsWhere = (String) jsonObject.get("whereNames");
			result = baseDao.queryTableContentBySQL(
					awardConditionsThesisAll(deptName, years, codeType,
							awardId, conditionsWhere), paramsMap);
		} else {
			result = baseDao.queryTableContentBySQL(
					awardThesisAll(deptName, years, codeType, awardId),
					paramsMap);
		}
		List<Map> queryList = (List) result.get("queryList");
		Map backval = new HashMap();
		backval.put("count", result.get("count"));
		backval.put("data", queryList);
		backval.put("success", true);
		return JSONObject.fromObject(backval).toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public HSSFWorkbook exportExcelAward(String datas) throws Exception {
		JSONObject jsonObject = JSONObject.fromObject(datas);
		String deptName = (String) jsonObject.getJSONObject("deptName").get(
				"dName");
		String years = (String) jsonObject.getJSONObject("years").get("year");
		String codeType = (String) jsonObject.getJSONObject("typeCode").get(
				"codeType");
		String awardId = (String) jsonObject.getJSONObject("awradId").get(
				"awradId");
		String str = "论文作者,院系名称,获奖名称,年卷期页,期刊类别,论文名称,发表年份";
		List<Map<String, Object>> results = baseDao
				.querySqlList(awardThesisAll(deptName, years, codeType, awardId));
		HSSFWorkbook workbook = ExportUtil.getHSSFWorkbookByMap(str.split(","),
				"获奖论文详情", results);
		return workbook;
	}

	@Override
	public String queryEIThesisLimitPage(String datas) {
		JSONObject jsonObject = JSONObject.fromObject(datas);
		String deptName = (String) jsonObject.getJSONObject("deptName").get(
				"dName");
		String years = (String) jsonObject.getJSONObject("years").get("year");
		String codeType = (String) jsonObject.getJSONObject("typeCode").get(
				"codeType");
		Map paramsMap = Utils4Service.packageParams(datas);
		Map result = new HashMap();
		if (jsonObject.containsKey("whereNames")
				&& !(jsonObject.get("whereNames").equals(""))) {
			String conditionsWhere = (String) jsonObject.get("whereNames");
			result = baseDao.queryTableContentBySQL(
					eIIncludeConditionsThesisAll(deptName, years, codeType,
							conditionsWhere), paramsMap);
		} else {
			result = baseDao.queryTableContentBySQL(
					eIIncludeThesisAll(deptName, years, codeType), paramsMap);
		}
		List<Map> queryList = (List) result.get("queryList");
		Map backval = new HashMap();
		backval.put("count", result.get("count"));
		backval.put("data", queryList);
		backval.put("success", true);
		return JSONObject.fromObject(backval).toString();
	}

	@Override
	public HSSFWorkbook exportExcelEIInclude(String datas) throws Exception {
		JSONObject jsonObject = JSONObject.fromObject(datas);
		String deptName = (String) jsonObject.getJSONObject("deptName").get(
				"dName");
		String years = (String) jsonObject.getJSONObject("years").get("year");
		String codeType = (String) jsonObject.getJSONObject("typeCode").get(
				"codeType");
		String str = "论文作者,院系名称,年卷期页,期刊类别,论文名称,发表年份";
		List<Map<String, Object>> results = baseDao
				.querySqlList(eIIncludeThesisAll(deptName, years, codeType));
		HSSFWorkbook workbook = ExportUtil.getHSSFWorkbookByMap(str.split(","),
				"被EI收录论文详情", results);
		return workbook;
	}

	@Override
	public String queryISThesisLimitPage(String datas) {
		JSONObject jsonObject = JSONObject.fromObject(datas);
		String deptName = (String) jsonObject.getJSONObject("deptName").get(
				"dName");
		String years = (String) jsonObject.getJSONObject("years").get("year");
		String codeType = (String) jsonObject.getJSONObject("typeCode").get(
				"codeType");
		Map paramsMap = Utils4Service.packageParams(datas);
		Map result = new HashMap();
		if (jsonObject.containsKey("whereNames")
				&& !(jsonObject.get("whereNames").equals(""))) {
			String conditionsWhere = (String) jsonObject.get("whereNames");
			result = baseDao.queryTableContentBySQL(
					iSIncludeConditionsThesisAll(deptName, years, codeType,
							conditionsWhere), paramsMap);
		} else {
			result = baseDao.queryTableContentBySQL(
					iSIncludeThesisAll(deptName, years, codeType), paramsMap);
		}

		List<Map> queryList = (List) result.get("queryList");
		Map backval = new HashMap();
		backval.put("count", result.get("count"));
		backval.put("data", queryList);
		backval.put("success", true);
		return JSONObject.fromObject(backval).toString();
	}

	@Override
	public HSSFWorkbook exportExcelISInclude(String datas) throws Exception {
		JSONObject jsonObject = JSONObject.fromObject(datas);
		String deptName = (String) jsonObject.getJSONObject("deptName").get(
				"dName");
		String years = (String) jsonObject.getJSONObject("years").get("year");
		String codeType = (String) jsonObject.getJSONObject("typeCode").get(
				"codeType");
		String str = "论文作者,院系名称,年卷期页,期刊类别,论文名称,发表年份";
		List<Map<String, Object>> results = baseDao
				.querySqlList(iSIncludeThesisAll(deptName, years, codeType));
		HSSFWorkbook workbook = ExportUtil.getHSSFWorkbookByMap(str.split(","),
				"被IS收录论文详情", results);
		return workbook;
	}

	@Override
	public String queryCPThesisLimitPage(String datas) {
		JSONObject jsonObject = JSONObject.fromObject(datas);
		String deptName = (String) jsonObject.getJSONObject("deptName").get(
				"dName");
		String years = (String) jsonObject.getJSONObject("years").get("year");
		String codeType = (String) jsonObject.getJSONObject("typeCode").get(
				"codeType");
		Map paramsMap = Utils4Service.packageParams(datas);
		Map result = new HashMap();
		if (jsonObject.containsKey("whereNames")
				&& !(jsonObject.get("whereNames").equals(""))) {
			String conditionsWhere = (String) jsonObject.get("whereNames");
			result = baseDao.queryTableContentBySQL(
					cPIncludeConditionsThesisAll(deptName, years, codeType,
							conditionsWhere), paramsMap);
		} else {
			result = baseDao.queryTableContentBySQL(
					cPIncludeThesisAll(deptName, years, codeType), paramsMap);
		}
		List<Map> queryList = (List) result.get("queryList");
		Map backval = new HashMap();
		backval.put("count", result.get("count"));
		backval.put("data", queryList);
		backval.put("success", true);
		return JSONObject.fromObject(backval).toString();
	}

	@Override
	public HSSFWorkbook exportExcelCPInclude(String datas) throws Exception {
		JSONObject jsonObject = JSONObject.fromObject(datas);
		String deptName = (String) jsonObject.getJSONObject("deptName").get(
				"dName");
		String years = (String) jsonObject.getJSONObject("years").get("year");
		String codeType = (String) jsonObject.getJSONObject("typeCode").get(
				"codeType");
		String str = "论文作者,院系名称,年卷期页,期刊类别,论文名称,发表年份";
		List<Map<String, Object>> results = baseDao
				.querySqlList(cPIncludeThesisAll(deptName, years, codeType));
		HSSFWorkbook workbook = ExportUtil.getHSSFWorkbookByMap(str.split(","),
				"被CPCI收录论文详情", results);
		return workbook;
	}

	@Override
	public String kindOfYearsDeptsCounts(String datas) {
		JSONObject jsonObject = JSONObject.fromObject(datas);
		String codeType = (String) jsonObject.get("codeType");
		String sql = "select nvl(tc.name_,'未维护') names,trt.year_ years, count(*) counts from t_res_thesis trt "
				+ "inner join t_Code_Dept tc on tc.code_ = trt.dept_id "
				+ "inner join t_code_subject tcs on tcs.code_=trt.project_id "
				+ "where tcs.code_='"
				+ codeType
				+ "' group by tc.name_,trt.year_";
		List<Map> results = baseDao.querySqlList(sql);
		return JSONArray.fromObject(results).toString();
	}

	/**
	 * @description 论文封装
	 * @param deptName
	 * @param years
	 * @param typeCode
	 * @param booksName
	 * @return
	 */
	public static String punishThesisAll(String deptName, String years,
			String typeCode, String booksName) {
		String sql = "select trt.authors author, tc.name_ deptName, trt.njqy njqy, trt.periodical periodical, trt.title_ title,trt.year_ years from t_res_thesis trt "
				+ "inner join t_code_dept tc on tc.code_ = trt.dept_id "
				+ "inner join t_code_subject tcs on tcs.code_ = trt.project_id "
				+ "inner join t_code tcode on tcode.code_ = trt.periodical_type_code "
				+ "where tc.name_='"
				+ deptName
				+ "' "
				+ "and trt.year_='"
				+ years
				+ "' "
				+ "and tcs.code_='"
				+ typeCode
				+ "' "
				+ "and tcode.code_type='RES_PERIODICAL_TYPE_CODE' "
				+ "and tcode.name_='" + booksName + "' ";
		return sql;
	}

	/**
	 * @description 论文条件封装
	 * @param deptName
	 * @param years
	 * @param typeCode
	 * @param booksName
	 * @param whereNames
	 * @return
	 */
	public static String punishConditionsThesisAll(String deptName,
			String years, String typeCode, String booksName, String whereNames) {
		String sql = "select trt.authors author, tc.name_ deptName, trt.njqy njqy, trt.periodical periodical, trt.title_ title,trt.year_ years from t_res_thesis trt "
				+ "inner join t_code_dept tc on tc.code_ = trt.dept_id "
				+ "inner join t_code_subject tcs on tcs.code_ = trt.project_id "
				+ "inner join t_code tcode on tcode.code_ = trt.periodical_type_code "
				+ "where tc.name_='"
				+ deptName
				+ "' "
				+ "and trt.year_='"
				+ years
				+ "' "
				+ "and tcs.code_='"
				+ typeCode
				+ "' "
				+ "and tcode.code_type='RES_PERIODICAL_TYPE_CODE' "
				+ "and tcode.name_='"
				+ booksName
				+ "' and trt.title_ like '%"
				+ whereNames + "%'";
		return sql;
	}

	/**
	 * @description 封装获奖论文
	 * @param deptName
	 * @param years
	 * @param codeType
	 * @param awardId
	 * @return
	 */
	public static String awardThesisAll(String deptName, String years,
			String codeType, String awardId) {
		String sql = "select trt.authors author, tcd.name_ deptName, trta.award_name awardName, trt.njqy njqy, trt.periodical periodical, trt.title_ title,trt.year_ years from t_res_thesis_award trta "
				+ "inner join t_code tc on tc.code_=trta.award_rank_code "
				+ "inner join  t_res_thesis trt on trt.id = trta.thesis_id "
				+ "inner join t_code_dept tcd on tcd.code_ = trt.dept_id "
				+ "inner join t_code_subject tcs on tcs.code_ = trt.project_id "
				+ "where tc.code_type='RES_THESIS_RANK_CODE' "
				+ "and tcd.name_='"
				+ deptName
				+ "' and trt.year_='"
				+ years
				+ "' and tcs.code_='"
				+ codeType
				+ "' and tc.name_='"
				+ awardId
				+ "'";
		return sql;
	}

	/**
	 * @descption 封装条件获奖论文
	 * @param deptName
	 * @param years
	 * @param codeType
	 * @param awardId
	 * @param whereNames
	 * @return
	 */
	public static String awardConditionsThesisAll(String deptName,
			String years, String codeType, String awardId, String whereNames) {
		String sql = "select trt.authors author, tcd.name_ deptName, trta.award_name awardName, trt.njqy njqy, trt.periodical periodical, trt.title_ title,trt.year_ years from t_res_thesis_award trta "
				+ "inner join t_code tc on tc.code_=trta.award_rank_code "
				+ "inner join  t_res_thesis trt on trt.id = trta.thesis_id "
				+ "inner join t_code_dept tcd on tcd.code_ = trt.dept_id "
				+ "inner join t_code_subject tcs on tcs.code_ = trt.project_id "
				+ "where tc.code_type='RES_THESIS_RANK_CODE' "
				+ "and tcd.name_='"
				+ deptName
				+ "' and trt.year_='"
				+ years
				+ "' and tcs.code_='"
				+ codeType
				+ "' and tc.name_='"
				+ awardId
				+ "'" + " and trt.title_ like '%" + whereNames + "%'";
		return sql;
	}

	/**
	 * @description 封装EI收录论文
	 * @param deptName
	 * @param years
	 * @param codeType
	 * @return
	 */
	public static String eIIncludeThesisAll(String deptName, String years,
			String codeType) {
		String sql = "select trt.authors author, tc.name_ deptName, trt.njqy njqy, trt.periodical periodical, trt.title_ title,trt.year_ years from t_res_thesis_in trti "
				+ "inner join t_res_thesis trt on trt.id = trti.thesis_id "
				+ "inner join t_code_dept tc on tc.code_ = trt.dept_id "
				+ "inner join t_code_subject tcs on tcs.code_ = trt.project_id "
				+ "where tc.name_='"
				+ deptName
				+ "' "
				+ "and trt.year_='"
				+ years + "' " + "and tcs.code_='" + codeType + "' ";
		return sql;
	}

	/**
	 * @description 封装条件EI收录论文
	 * @param deptName
	 * @param years
	 * @param codeType
	 * @param whereNames
	 * @return
	 */
	public static String eIIncludeConditionsThesisAll(String deptName,
			String years, String codeType, String whereNames) {
		String sql = "select trt.authors author, tc.name_ deptName, trt.njqy njqy, trt.periodical periodical, trt.title_ title,trt.year_ years from t_res_thesis_in trti "
				+ "inner join t_res_thesis trt on trt.id = trti.thesis_id "
				+ "inner join t_code_dept tc on tc.code_ = trt.dept_id "
				+ "inner join t_code_subject tcs on tcs.code_ = trt.project_id "
				+ "where tc.name_='"
				+ deptName
				+ "' "
				+ "and trt.year_='"
				+ years
				+ "' "
				+ "and tcs.code_='"
				+ codeType
				+ "' and trt.title_ like '%" + whereNames + "%'";
		return sql;
	}

	/**
	 * @description 封装IS收录论文
	 * @param deptName
	 * @param years
	 * @param codeType
	 * @return
	 */
	public static String iSIncludeThesisAll(String deptName, String years,
			String codeType) {
		String sql = "select trt.authors author, tc.name_ deptName, trt.njqy njqy, trt.periodical periodical, trt.title_ title,trt.year_ years from t_res_thesis_istp trti "
				+ "inner join t_res_thesis trt on trt.id = trti.thesis_id "
				+ "inner join t_code_dept tc on tc.code_ = trt.dept_id "
				+ "inner join t_code_subject tcs on tcs.code_ = trt.project_id "
				+ "where tc.name_='"
				+ deptName
				+ "' "
				+ "and trt.year_='"
				+ years + "' " + "and tcs.code_='" + codeType + "' ";
		return sql;
	}

	/**
	 * @description 封装条件IS收录论文
	 * @param deptName
	 * @param years
	 * @param codeType
	 * @param whereNames
	 * @return
	 */
	public static String iSIncludeConditionsThesisAll(String deptName,
			String years, String codeType, String whereNames) {
		String sql = "select trt.authors author, tc.name_ deptName, trt.njqy njqy, trt.periodical periodical, trt.title_ title,trt.year_ years from t_res_thesis_istp trti "
				+ "inner join t_res_thesis trt on trt.id = trti.thesis_id "
				+ "inner join t_code_dept tc on tc.code_ = trt.dept_id "
				+ "inner join t_code_subject tcs on tcs.code_ = trt.project_id "
				+ "where tc.name_='"
				+ deptName
				+ "' "
				+ "and trt.year_='"
				+ years
				+ "' "
				+ "and tcs.code_='"
				+ codeType
				+ "' and trt.title_ like '%" + whereNames + "%'";
		return sql;
	}

	/**
	 * @description 封装CP收录论文
	 * @param deptName
	 * @param years
	 * @param codeType
	 * @return
	 */
	public static String cPIncludeThesisAll(String deptName, String years,
			String codeType) {
		String sql = "select trt.authors author, tc.name_ deptName, trt.njqy njqy, trt.periodical periodical, trt.title_ title,trt.year_ years from t_res_thesis_cpcis trti "
				+ "inner join t_res_thesis trt on trt.id = trti.thesis_id "
				+ "inner join t_code_dept tc on tc.code_ = trt.dept_id "
				+ "inner join t_code_subject tcs on tcs.code_ = trt.project_id "
				+ "where tc.name_='"
				+ deptName
				+ "' "
				+ "and trt.year_='"
				+ years + "' " + "and tcs.code_='" + codeType + "' ";
		return sql;
	}

	/**
	 * @description 封装条件CP收录论文
	 * @param deptName
	 * @param years
	 * @param codeType
	 * @param whereNames
	 * @return
	 */
	public static String cPIncludeConditionsThesisAll(String deptName,
			String years, String codeType, String whereNames) {
		String sql = "select trt.authors author, tc.name_ deptName, trt.njqy njqy, trt.periodical periodical, trt.title_ title,trt.year_ years from t_res_thesis_cpcis trti "
				+ "inner join t_res_thesis trt on trt.id = trti.thesis_id "
				+ "inner join t_code_dept tc on tc.code_ = trt.dept_id "
				+ "inner join t_code_subject tcs on tcs.code_ = trt.project_id "
				+ "where tc.name_='"
				+ deptName
				+ "' "
				+ "and trt.year_='"
				+ years
				+ "' "
				+ "and tcs.code_='"
				+ codeType
				+ "' and trt.title_ like '%" + whereNames + "%'";
		return sql;
	}

}
