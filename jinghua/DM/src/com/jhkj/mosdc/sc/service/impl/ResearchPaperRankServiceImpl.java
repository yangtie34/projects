package com.jhkj.mosdc.sc.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.Utils4Service;
import com.jhkj.mosdc.sc.service.ResearchPaperRankService;

public class ResearchPaperRankServiceImpl implements ResearchPaperRankService{

	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	@Override
	public String getResearchDeptRank(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String lastYear = getLastYear();
		String year = json.containsKey("year")?json.getString("year"):lastYear;	
		String sql = "select rt.dept_id,xzzzjg.mc dept_name,round(sum(t.impact_factor)/count(distinct t.thesis_id),3) impact_factor_avg  from t_res_thesis_in t "
				+ " left join t_res_thesis rt on rt.id = t.thesis_id "
				+ " left join tb_xzzzjg xzzzjg on xzzzjg.dm = rt.dept_id"
				+ " where t.year_ = '"+ year +"' and t.impact_factor is not null group by rt.dept_id,xzzzjg.mc order by impact_factor_avg desc";
		
		List<Map> result = baseDao.querySqlList(sql);
		return Struts2Utils.list2json(result);
	}
	@Override
	public String getResearchDeptAvg(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String lastYear = getLastYear();
		String year = json.containsKey("year")?json.getString("year"):lastYear;	
		String sql = "select round(sum(t.impact_factor)/count(distinct t.thesis_id),3) impact_factor_avg from t_res_thesis_in t where t.year_ = '"+year+"' and t.impact_factor is not null";
		List<Map> result = baseDao.querySqlList(sql);
		return Struts2Utils.list2json(result);
	}
	@Override
	public String getResearchPaperRank(String params) {
		JSONObject json = JSONObject.fromObject(params);
		Map paramsMap = Utils4Service.packageParams(params);
		System.out.println(json.toString());
		String lastYear = getLastYear();
		String year = json.containsKey("year")?json.getString("year"):lastYear;
		String authors = json.containsKey("authors")?json.getString("authors"):"";
		String thesisTitle = json.containsKey("thesisTitle")?json.getString("thesisTitle"):"";
		String deptId = json.containsKey("deptId")?json.getString("deptId"):"0";
		String sql2 = "";
		if(deptId != null && !"".equals(deptId) && !"0".equals(deptId)){
			sql2 = "and rt.dept_id in ("+ deptId +")";
		}
		String  sql = "select r.* from (select s.*,rownum rm from(select rt.id thesis_id,rt.title_ thesis_title,rt.authors authors,xzzzjg.mc dept_name,rt.periodical"
				+ " ,rt.year_ thesis_year,t.year_ in_year,rt.njqy,co.name_ periodical_name,rt.order_ school_order,t.impact_factor,t.year_  from t_res_thesis_in t"
				+ " left join t_res_thesis rt on rt.id = t.thesis_id"
				+ " left join tb_xzzzjg xzzzjg on xzzzjg.dm = rt.dept_id"
				+ " left join t_code co on co.code_type='RES_PERIODICAL_TYPE_CODE' and co.code_ = t.periodical_type_code"
				+ " where t.year_ = '"+year+"' and t.impact_factor is not null "+ sql2 +" order by t.impact_factor desc) s )r where r.authors like '%"+authors+"%' and r.thesis_title like '%"+thesisTitle+"%'";
		Map backval = new HashMap();
		Map result = baseDao.queryTableContentBySQL(sql,paramsMap);
		backval.put("success", true);
		backval.put("data", result.get("queryList"));
		backval.put("count", result.get("count"));
		return Struts2Utils.map2json(backval);
	}
	@Override
	public String getPast(String params) {
		
		String deptsql ="select s.dept_id,nums,rownum rm from ("
	         +" select th.dept_id,count(*) nums from t_res_thesis_in t "
	         +" left join t_res_thesis th on th.id = t.thesis_id"
	         +" group by th.dept_id order by nums desc)s";
		List<Map> alldept = baseDao.querySqlList(deptsql);
		String dept = "";
		if(alldept != null && alldept.size()>0){
			for(int i=0;i<alldept.size();i++){
				if(i==0){
					dept = (String) alldept.get(i).get("dept_id");
				}else{
					dept = dept + ","+ alldept.get(i).get("dept_id");
				}
			}
		}
		JSONObject json = JSONObject.fromObject(params);
		String sql = "select rti.year_ inyear,th.dept_id dept_id,xzzzjg.mc dept_name,count(distinct rti.thesis_id) sums from t_res_thesis_in rti "
				+ " left join t_res_thesis th on th.id = rti.thesis_id"
				+ " left join tb_xzzzjg xzzzjg on xzzzjg.dm = th.dept_id where dept_id in ("+dept+")"
				+ " group by rti.year_,th.dept_id,xzzzjg.mc order by inyear,dept_id ";
		String sql2 = "select jg.dm dept_id,jg.mc dept_name from tb_xzzzjg jg where jg.dm in ("+ dept +") ";
		
		List<Map> result = baseDao.querySqlList(sql);
		List<Map> depts = baseDao.querySqlList(sql2);
		List<Map> rs = new ArrayList<Map>();
		List<Map> myresult = new ArrayList<Map>();
		if(result != null && result.size()>0 && depts!=null && depts.size()>0){
			int first = Integer.parseInt((String) result.get(0).get("inyear"));
			int last = Integer.parseInt((String) result.get(result.size()-1).get("inyear"));
			List<String> years = new ArrayList<String>();
			years.add(String.valueOf(first));
			for(int i=0;i<(last-first);i++){
				int yr = first + i +1;
				years.add(String.valueOf(yr));
			}
			for(int i=0;i<years.size();i++){
				for(int j=0;j<depts.size();j++){
					Map map = new HashMap();
					for(int x=0;x<result.size();x++){
						if(result.get(x).get("inyear").equals(years.get(i)) && result.get(x).get("dept_name").equals(depts.get(j).get("dept_name"))){
							map = result.get(x);
						}
					}
					if(map.size()==0){
						map.put("INYEAR", years.get(i));
						map.put("DEPT_ID", depts.get(j).get("dept_id"));
						map.put("DEPT_NAME", depts.get(j).get("dept_name"));
						map.put("SUMS", "0");
					}
					myresult.add(map);
				}
			}
		}
 		return Struts2Utils.list2json(myresult);
	}
	
	@Override
	public String getAllDept(String params) {
		String sql = "select jg.dm dept_id,jg.mc dept_name from tb_xzzzjg jg where jg.cc = '1'";
		List<Map> result = baseDao.querySqlList(sql);
		return Struts2Utils.list2json(result);
	}
	/*
	 * 获取去年的年
	 */
	private String getLastYear() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, -1);
		SimpleDateFormat year = new SimpleDateFormat("yyyy");
		return year.format(calendar.getTime());
	}
	
	


}
