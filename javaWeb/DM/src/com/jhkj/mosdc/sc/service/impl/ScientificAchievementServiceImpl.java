package com.jhkj.mosdc.sc.service.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.util.ExportUtil;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.Utils4Service;
import com.jhkj.mosdc.sc.service.ScientificAchievementService;

public class ScientificAchievementServiceImpl implements ScientificAchievementService{

	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	private String jdsql = " from t_res_outcome_appraisal t  left join t_res_outcome_appraisal_auth aa on t.id = aa.appraisal_id"
					+ " inner join tb_jzgxx jzg on jzg.zgh = aa.people_id"
					+ " left join tb_xzzzjg jg on jzg.ks_id = jg.id ";
	private String hjsql = "  from t_res_outcome_award t left join t_res_outcome_award_auth aa on t.id = aa.outcome_award_id"
					+ " inner join tb_jzgxx jzg on jzg.zgh = aa.people_id "
					+ " left join tb_xzzzjg jg on jzg.ks_id = jg.id ";
	private String zlsql = "  from t_res_patent t left join t_res_patent_auth pa on t.id = pa.patent_id"
					+ " inner join tb_jzgxx jzg on jzg.zgh = pa.people_id"
					+ " left join tb_xzzzjg jg on jg.id = jzg.ks_id ";
	private String zzsql = " from t_res_work t left join t_res_work_author wa on wa.work_id = t.id"
					+ " inner join tb_jzgxx jzg on jzg.zgh = wa.people_id"
					+ " left join tb_xzzzjg jg on jg.id = jzg.ks_id ";
	private String xmsql = " from t_res_project t"
					+ " left join t_res_project_auth pa on pa.pro_id = t.id"
					+ " inner join tb_jzgxx jzg on jzg.zgh = pa.people_id"
					+ " left join tb_xzzzjg jg on jg.id = jzg.ks_id ";
	private String fblwsql = " from t_res_thesis t "
					+ " left join t_res_thesis_author ta on ta.thesis_id = t.id"
					+ " inner join tb_jzgxx jzg on jzg.zgh = ta.people_id"
					+ " left join tb_xzzzjg jg on jg.id = jzg.ks_id ";
	private String sllwsql = " from t_res_thesis_in t"
					+ " left join t_res_thesis_author ta on ta.thesis_id = t.thesis_id"
					+ " left join t_res_thesis rt on rt.id = t.thesis_id"
					+ " inner join tb_jzgxx jzg on jzg.zgh = ta.people_id"
					+ " left join tb_xzzzjg jg on jg.id = jzg.ks_id ";
	private String hjlwsql = " from t_res_thesis_award t"
					+ " left join t_res_thesis_author ta on ta.thesis_id = t.thesis_id"
					+ " left join t_res_thesis rt on rt.id = t.thesis_id"
					+ " inner join tb_jzgxx jzg on jzg.zgh = ta.people_id"
					+ " left join tb_xzzzjg jg on jg.id = jzg.ks_id";
	
	private String zc1 = " select nvl(co.name_,'未维护')  zyjszw_name ,r.nums from t_code_zyjszw co right join ("
			+ " select substr(zy.path_,0,3) path_ ,count(*) nums from ("
			+ " select distinct jzg.zgh,jzg.zyjszw_id ";
	private String zc2 = " left join t_code_zyjszw zy on zy.code_ = s.zyjszw_id"
			+ " group by substr(zy.path_,0,3) ) r on r.path_ = co.path_ ";
	private String whcd1 = "select nvl(co.name_,'未维护')  whcd_name ,r.nums from t_code_education co right join ("
			+ " select substr(ce.path_,0,6) path_ ,count(*) nums from ("
			+ " select distinct jzg.zgh,jzg.whc_id  ";
	private String whcd2 = " left join t_code_education ce on to_number(ce.code_) = s.whc_id"
			+ " group by substr(ce.path_,0,6) ) r on r.path_ = co.path_";
	private String xw1 = " select nvl(co.name_,'未维护')  xw_name ,r.nums from t_code_degree co right join ("
			+ " select substr(cd.path_,0,6) path_ ,count(*) nums from ("
			+ " select distinct jzg.zgh,jzg.xwd_id ";
	private String xw2 = " left join t_code_degree cd on to_number(cd.code_) = s.xwd_id"
			+ " group by substr(cd.path_,0,6) ) r on r.path_ = co.path_ ";
	private String zcmx1 = "select nvl(co.name_,'未维护') zyjszw_name,count(*) nums from t_code_zyjszw co"
			+ " right join ( select distinct jzg.zgh,jzg.zyjszw_id ";
	
	@Override
	public String getTable(String params) {
		String sql = getTable1Sql(params);
        Map paramsMap = Utils4Service.packageParams(params);
		Map result = baseDao.queryTableContentBySQL(sql,paramsMap);
		Map backval = new HashMap();
		backval.put("success", true);
		backval.put("data", result.get("queryList"));
		backval.put("count", result.get("count"));
		return Struts2Utils.map2json(backval);
	}
	
	@Override
	public String getTable2(String params) {
		String sql = getTable2Sql(params);
        Map paramsMap = Utils4Service.packageParams(params);
		Map result = baseDao.queryTableContentBySQL(sql,paramsMap);
		Map backval = new HashMap();
		backval.put("success", true);
		backval.put("data", result.get("queryList"));
		backval.put("count", result.get("count"));
		return Struts2Utils.map2json(backval);
	}
	private String getTable2Sql(String params){
		JSONObject json = JSONObject.fromObject(params);
		String nowyear = getNowyear();
		String startDate = json.containsKey("from")?json.getString("from"):nowyear;	
		String endDate = json.containsKey("to")?json.getString("to"):nowyear;	
		String type = json.containsKey("type")?json.getString("type"):"鉴定成果";
		String cgname = json.containsKey("cgname")?json.getString("cgname"):"";
		String zgh = json.getString("zgh");
		
		String sql = "";
		if("鉴定成果".equals(type)){
			sql = "select jzg.zgh,jzg.xm jzg_name,jg.mc dept_name,t.name_ title_name,t.authors,t.appraisal_dept ,"
					+ " co.name_ identifymode,c.name_ identifylevel,t.time_,t.identify_no,t.identify_regist_no,"
					+ " sub.name_ project_name,t.order_ from t_res_outcome_appraisal t  left join t_res_outcome_appraisal_auth aa on t.id = aa.appraisal_id"
					+ " inner join tb_jzgxx jzg on jzg.zgh = aa.people_id"
					+ " left join tb_xzzzjg jg on jzg.ks_id = jg.id"
					+ " left join t_code co on co.code_type = 'RES_IDENTIFYMODE_CODE' and co.code_ = t.identifymode_code "
					+ " left join t_code c on c.code_type = 'RES_IDENTIFYLEVEL_CODE' and c.code_ = t.identifylevel_code"
					+ " left join t_code_subject sub on sub.code_ = t.project_id"
					+ " where substr(t.time_,0,4) between '"+ startDate +"' and '"+ endDate +"' and zgh = '"+ zgh +"' and t.name_ like '%"+cgname+"%'";
		}else if("获奖成果".equals(type)){
			sql = " select jzg.zgh,jzg.xm jzg_name,jg.mc dept_name,t.name_ outcome_name,t.prizewinner,t.award_name ,"
					+ " co.name_ level_name,c.name_ category_name ,cd.name_ rank_name,sub.name_ project_name, t.award_dept,t.award_time,t.certificate_no from t_res_outcome_award t "
					+ " left join t_res_outcome_award_auth aa on aa.outcome_award_id = t.id"
					+ " inner join tb_jzgxx jzg on jzg.zgh = aa.people_id"
					+ " left join tb_xzzzjg jg on jg.id = jzg.ks_id "
					+ " left join t_code co on co.code_type = 'RES_AWARD_LEVEL_CODE' and co.code_ = t.level_code"
					+ " left join t_code c on c.code_type = 'RES_AWARD_CATEGORY_CODE' and c.code_ = t.category_code"
					+ " left join t_code cd on cd.code_type = 'RES_AWARD_RANK_CODE' and cd.code_ = t.rank_code"
					+ " left join t_code_subject sub on sub.code_ = t.project_id"
					+ " where substr(t.award_time,0,4) between '"+ startDate +"' and '"+ endDate +"' and zgh = '"+ zgh +"' and t.name_ like '%"+cgname+"%'";
		}else if("专利".equals(type)){
			sql = "select jzg.zgh ,jzg.xm jzg_name,jg.mc dept_name,t.name_ patent_name,t.inventors,"
					+ " t.patent_dept,t.accredit_time,co.name_ patent_type,c.name_ patent_state,sub.name_ project_name, t.patent_no,t.certificate_no from t_res_patent t "
					+ " left join t_res_patent_auth pa on pa.patent_id = t.id"
					+ " inner join tb_jzgxx jzg on jzg.zgh = pa.people_id"
					+ " left join tb_xzzzjg jg on jg.id = jzg.ks_id"
					+ " left join t_code co on co.code_type = 'RES_PATENT_TYPE_CODE' and co.code_ = t.patent_type_code"
					+ " left join t_code c on c.code_type = 'RES_PATENT_STATE_CODE' and co.code_ = t.patent_state_code"
					+ " left join t_code_subject sub on sub.code_ = t.project_id"
					+ " where substr(t.accredit_time,0,4) between '"+startDate+"' and '"+endDate+"' and zgh = '"+zgh+"' and t.name_ like '%"+cgname+"%'";
		}else if("著作".equals(type)){
			sql ="select jzg.zgh,jzg.xm jzg_name,jg.mc dept_name,t.title_ work_name,t.authors,t.work_no,t.press_name,"
					+ " t.press_time,t.number_,sub.name_ project_name,t.remark  from t_res_work t "
					+ " left join t_res_work_author wa on wa.work_id = t.id"
					+ " inner join tb_jzgxx jzg on jzg.id = wa.people_id"
					+ " left join tb_xzzzjg jg on jg.id = jzg.ks_id"
					+ " left join t_code_subject sub on sub.code_ = t.project_id"
					+ " where substr(t.press_time,0,4) between '"+startDate+"' and '"+endDate+"' and zgh = '"+zgh+"'  and t.title_ like '%"+cgname+"%'";
		}else if("科研项目".equals(type)){
			sql = " select jzg.zgh,jzg.xm jzg_name,jg.mc dept_name,t.name_ project_title,t.compere,zzjg.mc project_dept,"
					+ " t.pro_no,t.category,co.name_ level_name,c.name_ rank_name,cd.name_ state_name,sub.name_ project_name,"
					+ " t.issued_dept,t.teamwork_dept,t.start_time,t.end_time,t.fund,t.setup_year from t_res_project t "
					+ " left join t_res_project_auth pa on pa.pro_id = t.id"
					+ " inner join tb_jzgxx jzg on jzg.zgh = pa.people_id"
					+ " left join tb_xzzzjg jg on jg.id = jzg.ks_id"
					+ " left join tb_xzzzjg zzjg on zzjg.id = t.dept_id"
					+ " left join t_code co on co.code_type = 'RES_PROJECT_LEVEL_CODE' and co.code_ = t.level_code"
					+ " left join t_code c on c.code_type = 'RES_PROJECT_RANK_CODE' and c.code_ = t.rank_code"
					+ " left join t_code cd on cd.code_type = 'RES_PROJECT_STATE_CODE' and cd.code_ = t.state_code"
					+ " left join t_code_subject sub on sub.code_ = t.project_id "
					+ " where substr(t.start_time,0,4) between '"+ startDate +"' and '"+ endDate +"'  and zgh = '"+ zgh +"' and t.name_ like '%"+cgname+"%'";
			System.out.println(sql);
		}else if("发表论文".equals(type)){
			sql ="select jzg.zgh,jzg.xm jzg_name,jg.mc dept_name,t.title_ thesis_title,t.authors,zzjg.mc thesis_dept,t.periodical,t.year_,t.njqy,"
					+ " co.name_ periodical_type,sub.name_ project_name,t.order_ from t_res_thesis t "
					+ " left join t_res_thesis_author ta on ta.thesis_id = t.id"
					+ " inner join tb_jzgxx jzg on jzg.zgh = ta.people_id"
					+ " left join tb_xzzzjg jg on jg.id = jzg.ks_id"
					+ " left join tb_xzzzjg zzjg on zzjg.id = t.dept_id "
					+ " left join t_code co on co.code_ = t.periodical_type_code and co.code_type='RES_PERIODICAL_TYPE_CODE'"
					+ " left join t_code_subject sub on sub.code_ = t.project_id"
					+ " where substr(t.year_,0,4) between '"+ startDate +"' and '"+ endDate+"' and zgh = '"+ zgh +"'  and t.title_ like '%"+cgname+"%'";
		}else if("收录论文".equals(type)){
			sql = "select jzg.zgh,jzg.xm jzg_name,jg.mc dept_name,rt.title_ thesis_title,xz.mc thesis_dept,rt.periodical,"
					+ " t.year_,t.njqy,t.issn,t.impact_factor,c.name_ sci_zone,co.name_ periodical_type"
					+ " from t_res_thesis_in t "
					+ " left join t_res_thesis_author ta on ta.thesis_id = t.thesis_id"
					+ " left join t_res_thesis rt on rt.id = t.thesis_id"
					+ " left join tb_xzzzjg xz on xz.id = rt.dept_id"
					+ " inner join tb_jzgxx jzg on jzg.zgh = ta.people_id"
					+ " left join tb_xzzzjg jg on jg.id = jzg.ks_id"
					+ " left join t_code c on c.code_ = t.sci_zone and c.code_type = 'THESIS_IN_SCI_CODE' "
					+ " left join t_code co on co.code_ = t.periodical_type_code and co.code_type = 'RES_PERIODICAL_TYPE_CODE'"
					+ " where substr(t.year_,0,4) between '"+ startDate +"' and '"+ endDate+"' and zgh = '"+ zgh +"'  and rt.title_ like '%"+cgname+"%'";
		}else if("获奖论文".equals(type)){
			sql = "select jzg.zgh,jzg.xm jzg_name,jg.mc dept_name,rt.title_ thesis_title,xz.mc thesis_dept,t.award_name,"
					+ " co.name_ rank_name,t.award_dept,t.award_time,t.certificate_no"
					+ " from t_res_thesis_award t "
					+ " left join t_res_thesis_author ta on ta.thesis_id = t.thesis_id"
					+ " left join t_res_thesis rt on rt.id = t.thesis_id"
					+ " left join tb_xzzzjg xz on xz.id = rt.dept_id"
					+ " inner join tb_jzgxx jzg on jzg.zgh = ta.people_id"
					+ " left join tb_xzzzjg jg on jg.id = jzg.ks_id"
					+ " left join t_code co on co.code_type = 'RES_THESIS_RANK_CODE' and co.code_ = t.award_rank_code"
					+ " where substr(t.award_time,0,4) between '"+ startDate +"' and '"+ endDate+"'  and zgh = '"+ zgh +"' and rt.title_ like '%"+cgname+"%'";
		}else if("全部".equals(type)){
			sql = "select r.* from ( select zgh,jzg_name,cg_id,title,ky_type from ("
					+ " select  j.zgh,j.xm jzg_name,oa.id cg_id,oa.name_ title,'鉴定成果' ky_type from tb_jzgxx j "
					+ " inner join t_res_outcome_appraisal_auth aa on aa.people_id = j.zgh and aa.people_id = '"+zgh+"'"
					+ " inner join t_res_outcome_appraisal oa on oa.id = aa.appraisal_id and substr(oa.time_,0,4) between '"+ startDate +"' and '"+ endDate+"' "
					+ " inner join tb_xzzzjg jg on jg.id = j.ks_id  )"
					+ " UNION  ALL("
					+ " select  j.zgh,j.xm jzg_name,rt.id cg_id,rt.title_ title ,'发表论文' ky_type from tb_jzgxx j "
					+ " inner join t_res_thesis_author ta on ta.people_id = j.zgh and ta.people_id = '"+zgh+"'"
					+ " inner join t_res_thesis rt on rt.id = ta.thesis_id and substr(rt.year_,0,4) between '"+ startDate +"' and '"+ endDate+"'"
					+ " inner join tb_xzzzjg jg on jg.id = j.ks_id ) "
					+ " UNION  ALL("
					+ " select j.zgh ,j.xm jzg_name,roa.id cg_id,roa.name_ title,'获奖成果' ky_type from tb_jzgxx j" 
					+ " inner join t_res_outcome_award_auth oaa on oaa.people_id = j.zgh and oaa.people_id = '"+zgh+"'"
					+ " inner join t_res_outcome_award roa on roa.id = oaa.outcome_award_id and substr(roa.award_time,0,4) between '"+ startDate +"' and '"+ endDate+"' "
					+ " inner join tb_xzzzjg jg on jg.id = j.ks_id )"
					+ " UNION  ALL("
					+ " select  j.zgh,j.xm jzg_name,rp.id cg_id,rp.name_ title,'专利' ky_type from tb_jzgxx j" 
					+ " inner join t_res_patent_auth pa on pa.people_id = j.zgh and pa.people_id = '"+zgh+"'"
					+ " inner join t_res_patent rp on rp.id = pa.patent_id and substr(rp.accredit_time,0,4) between '"+ startDate +"' and '"+ endDate+"' "
					+ " inner join tb_xzzzjg jg on jg.id = j.ks_id ) "
					+ " UNION  ALL("
					+ " select  j.zgh,j.xm jzg_name,pro.id cg_id,pro.name_ title,'科研项目' ky_type from tb_jzgxx j "
					+ " inner join t_res_project_auth rpa on rpa.people_id = j.zgh and rpa.people_id = '"+zgh+"'"
					+ " inner join t_res_project pro on pro.id = rpa.pro_id and substr(pro.start_time,0,4) between '"+ startDate +"' and '"+ endDate+"'"
					+ " inner join tb_xzzzjg jg on jg.id = j.ks_id )"
					+ " UNION  ALL("
					+ " select  j.zgh,j.xm jzg_name,t.id cg_id,t.title_ title,'收录论文' ky_type from tb_jzgxx j "
					+ " inner join t_res_thesis_author ta on ta.people_id = j.zgh and ta.people_id = '"+zgh+"'"
					+ " inner join t_res_thesis_in ti on ti.thesis_id = ta.thesis_id and substr(ti.year_,0,4) between '"+ startDate +"' and '"+ endDate+"'"
					+ " inner join t_res_thesis t on t.id = ti.thesis_id"
					+ " inner join tb_xzzzjg jg on jg.id = j.ks_id )"
					+ " UNION  ALL("
					+ " select  j.zgh,j.xm jzg_name,t.id cg_id,t.title_ title,'获奖论文' ky_type from tb_jzgxx j "
					+ " inner join t_res_thesis_author raa on raa.people_id = j.zgh and raa.people_id = '"+zgh+"'"
					+ " inner join t_res_thesis_award rta on rta.thesis_id = raa.thesis_id and substr(rta.award_time,0,4) between '"+ startDate +"' and '"+ endDate+"'"
					+ " inner join t_res_thesis t on t.id = rta.thesis_id"
					+ " inner join tb_xzzzjg jg on jg.id = j.ks_id ) "
					+ " UNION  ALL("
					+ " select  j.zgh,j.xm jzg_name,wo.id cg_id,wo.title_ title,'著作' ky_type from tb_jzgxx j "
					+ " inner join t_res_work_author wa on wa.people_id = j.zgh and wa.people_id = '"+zgh+"'"
					+ " inner join t_res_work wo on wo.id = wa.work_id and substr(wo.press_time,0,4) between '"+ startDate +"' and '"+ endDate+"'"
					+ " inner join tb_xzzzjg jg on jg.id = j.ks_id ) ) r where r.title like '%"+cgname+"%'";
		}
		return sql;
	}


	private String getTable1Sql(String params){
		JSONObject json = JSONObject.fromObject(params);
		String nowyear = getNowyear();
		String startDate = json.containsKey("from")?json.getString("from"):nowyear;	
		String endDate = json.containsKey("to")?json.getString("to"):nowyear;	
		String type = json.containsKey("type")?json.getString("type"):"鉴定成果";
		String deptId = json.containsKey("zzjgId")?json.getString("zzjgId"):"0";
		String jzgname = json.containsKey("jzgname")?json.getString("jzgname"):"";
		String typeName = json.getString("typeName");
		String typecode = json.getString("typecode");
		String sql2 = "";
		if(!"0".equals(deptId)){
			sql2 = " and jg.id in ("+deptId+")";
		}
		String sql = "";
		if("鉴定成果".equals(type)){
			String jd = "select s.* from ( select r.zgh,r.jzg_name,r.dept_name ,r.name_ type_name , count(*) nums from ("
					    + " select jzg.zgh,zy.name_,jzg.xm jzg_name,jg.mc dept_name "
						+  jdsql;
			if("zc".equals(typecode)){
				String mysql = "  and ( zy.pid in (select de.id from  t_code_zyjszw de where de.name_ = '"+ typeName +"') or zy.name_ = '"+ typeName +"' ) ";
				if("未维护".equals(typeName)){
					mysql = " and name_ is null ";
				}
				sql =  "select r.* from ("+jd + " left join t_code_zyjszw zy on zy.code_ = jzg.zyjszw_id "
						+ " where substr(t.time_,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2  + mysql +") r "
						+ " group by r.zgh,r.jzg_name,r.dept_name,r.name_  order by nums desc  ) s )r where r.jzg_name like '%"+jzgname+"%'";
			}else if("whcd".equals(typecode)){
				String mysql = "  and ( zy.pid in (select de.id from  t_code_education de where de.name_ = '"+ typeName +"') or zy.name_ = '"+ typeName +"' )  ";
				if("未维护".equals(typeName)){
					mysql = " and name_ is null ";
				}
				sql = "select r.* from ("+ jd + " left join t_code_education zy on to_number(zy.code_) = jzg.whc_id "
						+ " where substr(t.time_,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 + mysql +") r "
						+ " group by r.zgh,r.jzg_name,r.dept_name,r.name_ order by nums desc ) s )r where r.jzg_name like '%"+jzgname+"%'";
			}else if("xw".equals(typecode)){
				String mysql = "  and ( zy.pid in (select de.id from  t_code_degree de where de.name_ = '"+ typeName +"') or zy.name_ = '"+ typeName +"' )  ";
				if("未维护".equals(typeName)){
					mysql = " and name_ is null ";
				}
				sql =  "select r.* from ("+ jd+ " left join t_code_degree zy on to_number(zy.code_) = jzg.xwd_id  "
						+ " where substr(t.time_,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 + mysql + ") r "
						+ " group by r.zgh,r.jzg_name,r.dept_name,r.name_ order by nums desc ) s )r where r.jzg_name like '%"+jzgname+"%'" ;       
						             
			}else if("dw".equals(typecode)){
				sql = "select s.* from (select  r.zgh,r.jzg_name,r.dept_name ,'' type_name,count(*) nums from"
						+ " (select  jzg.zgh,jzg.xm jzg_name,jg.mc dept_name "
						+ jdsql +"  where substr(t.time_,0,4) between '"+ startDate +"' and '"+ endDate +"' and ks_id = "
						+ " (select j.id from tb_xzzzjg j where j.mc = '"+typeName+"')) r group by r.zgh,r.jzg_name,r.dept_name order by nums desc) s where s.jzg_name like '%"+jzgname+"%' ";
			}
		}else if("获奖成果".equals(type)){
			String hj = "select s.* from ( select r.zgh,r.jzg_name,r.dept_name ,r.name_ type_name , count(*) nums from ("
				    + " select jzg.zgh,zy.name_,jzg.xm jzg_name,jg.mc dept_name "
					+  hjsql;
			if("zc".equals(typecode)){
				String mysql = "  and ( zy.pid in (select de.id from  t_code_zyjszw de where de.name_ = '"+ typeName +"') or zy.name_ = '"+ typeName +"' ) ";
				if("未维护".equals(typeName)){
					mysql = " and name_ is null ";
				}
				sql =  hj + " left join t_code_zyjszw zy on zy.code_ = jzg.zyjszw_id "
						+ " where substr(t.award_time,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2  + mysql +") r "
						+ " group by r.zgh,r.jzg_name,r.dept_name,r.name_  order by nums desc  ) s where s.jzg_name like '%"+jzgname+"%'";
			}else if("whcd".equals(typecode)){
				String mysql = "  and ( zy.pid in (select de.id from  t_code_education de where de.name_ = '"+ typeName +"') or zy.name_ = '"+ typeName +"' )  ";
				if("未维护".equals(typeName)){
					mysql = " and name_ is null ";
				}
				sql = hj + " left join t_code_education zy on to_number(zy.code_) = jzg.whc_id "
						+ " where substr(t.award_time,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 + mysql +") r "
						+ " group by r.zgh,r.jzg_name,r.dept_name,r.name_ order by nums desc ) s where s.jzg_name like '%"+jzgname+"%'";
			}else if("xw".equals(typecode)){
				String mysql = "  and ( zy.pid in (select de.id from  t_code_degree de where de.name_ = '"+ typeName +"') or zy.name_ = '"+ typeName +"' )  ";
				if("未维护".equals(typeName)){
					mysql = " and name_ is null ";
				}
				sql =   hj+ " left join t_code_degree zy on to_number(zy.code_) = jzg.xwd_id  "
						+ " where substr(t.award_time,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 + mysql + ") r "
						+ " group by r.zgh,r.jzg_name,r.dept_name,r.name_ order by nums desc ) s where s.jzg_name like '%"+jzgname+"%'" ;       
						             
			}else if("dw".equals(typecode)){
				sql = "select s.* from (select  r.zgh,r.jzg_name,r.dept_name ,'' type_name,count(*) nums from"
						+ " (select  jzg.zgh,jzg.xm jzg_name,jg.mc dept_name "
						+ hjsql +"  where substr(t.award_time,0,4) between '"+ startDate +"' and '"+ endDate +"' and ks_id = "
						+ " (select j.id from tb_xzzzjg j where j.mc = '"+typeName+"')) r group by r.zgh,r.jzg_name,r.dept_name order by nums desc) s "
								+ " where s.jzg_name like '%"+jzgname+"%'";
			}
		}else if("专利".equals(type)){
			String zl = "select s.* from ( select r.zgh,r.jzg_name,r.dept_name ,r.name_ type_name , count(*) nums from ("
				    + " select jzg.zgh,zy.name_,jzg.xm jzg_name,jg.mc dept_name "
					+  zlsql;
			if("zc".equals(typecode)){
				String mysql = "  and ( zy.pid in (select de.id from  t_code_zyjszw de where de.name_ = '"+ typeName +"') or zy.name_ = '"+ typeName +"' ) ";
				if("未维护".equals(typeName)){
					mysql = " and name_ is null ";
				}
				sql =  zl + " left join t_code_zyjszw zy on zy.code_ = jzg.zyjszw_id "
						+ " where substr(t.accredit_time,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2  + mysql +") r "
						+ " group by r.zgh,r.jzg_name,r.dept_name,r.name_  order by nums desc  ) s  where s.jzg_name like '%"+jzgname+"%'";
			}else if("whcd".equals(typecode)){
				String mysql = "  and ( zy.pid in (select de.id from  t_code_education de where de.name_ = '"+ typeName +"') or zy.name_ = '"+ typeName +"' )  ";
				if("未维护".equals(typeName)){
					mysql = " and name_ is null ";
				}
				sql = zl + " left join t_code_education zy on to_number(zy.code_) = jzg.whc_id "
						+ " where substr(t.accredit_time,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 + mysql +") r "
						+ " group by r.zgh,r.jzg_name,r.dept_name,r.name_ order by nums desc ) s  where s.jzg_name like '%"+jzgname+"%'";
			}else if("xw".equals(typecode)){
				String mysql = "  and ( zy.pid in (select de.id from  t_code_degree de where de.name_ = '"+ typeName +"') or zy.name_ = '"+ typeName +"' )  ";
				if("未维护".equals(typeName)){
					mysql = " and name_ is null ";
				}
				sql =   zl+ " left join t_code_degree zy on to_number(zy.code_) = jzg.xwd_id  "
						+ " where substr(t.accredit_time,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 + mysql + ") r "
						+ " group by r.zgh,r.jzg_name,r.dept_name,r.name_ order by nums desc ) s  where s.jzg_name like '%"+jzgname+"%'" ;       
						             
			}else if("dw".equals(typecode)){
				sql = "select s.* from (select  r.zgh,r.jzg_name,r.dept_name ,'' type_name,count(*) nums from"
						+ " (select  jzg.zgh,jzg.xm jzg_name,jg.mc dept_name "
						+ zlsql +"  where substr(t.accredit_time,0,4) between '"+ startDate +"' and '"+ endDate +"' and ks_id = "
						+ " (select j.id from tb_xzzzjg j where j.mc = '"+typeName+"')) r group by r.zgh,r.jzg_name,r.dept_name order by nums desc) s "
								+ " where s.jzg_name like '%"+jzgname+"%'";
			}
		}else if("著作".equals(type)){
			String zz = "select s.* from ( select r.zgh,r.jzg_name,r.dept_name ,r.name_ type_name , count(*) nums from ("
				    + " select jzg.zgh,zy.name_,jzg.xm jzg_name,jg.mc dept_name "
					+  zzsql;
			if("zc".equals(typecode)){
				String mysql = "  and ( zy.pid in (select de.id from  t_code_zyjszw de where de.name_ = '"+ typeName +"') or zy.name_ = '"+ typeName +"' ) ";
				if("未维护".equals(typeName)){
					mysql = " and name_ is null ";
				}
				sql =  zz + " left join t_code_zyjszw zy on zy.code_ = jzg.zyjszw_id "
						+ " where substr(t.press_time,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2  + mysql +") r "
						+ " group by r.zgh,r.jzg_name,r.dept_name,r.name_  order by nums desc  ) s  where s.jzg_name like '%"+jzgname+"%'";
			}else if("whcd".equals(typecode)){
				String mysql = "  and ( zy.pid in (select de.id from  t_code_education de where de.name_ = '"+ typeName +"') or zy.name_ = '"+ typeName +"' )  ";
				if("未维护".equals(typeName)){
					mysql = " and name_ is null ";
				}
				sql = zz + " left join t_code_education zy on to_number(zy.code_) = jzg.whc_id "
						+ " where substr(t.press_time,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 + mysql +") r "
						+ " group by r.zgh,r.jzg_name,r.dept_name,r.name_ order by nums desc ) s  where s.jzg_name like '%"+jzgname+"%'";
			}else if("xw".equals(typecode)){
				String mysql = "  and ( zy.pid in (select de.id from  t_code_degree de where de.name_ = '"+ typeName +"') or zy.name_ = '"+ typeName +"' )  ";
				if("未维护".equals(typeName)){
					mysql = " and name_ is null ";
				}
				sql =   zz+ " left join t_code_degree zy on to_number(zy.code_) = jzg.xwd_id  "
						+ " where substr(t.press_time,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 + mysql + ") r "
						+ " group by r.zgh,r.jzg_name,r.dept_name,r.name_ order by nums desc ) s  where s.jzg_name like '%"+jzgname+"%'" ;       
						             
			}else if("dw".equals(typecode)){
				sql = "select s.* from (select  r.zgh,r.jzg_name,r.dept_name ,'' type_name,count(*) nums from"
						+ " (select  jzg.zgh,jzg.xm jzg_name,jg.mc dept_name "
						+ zzsql +"  where substr(t.press_time,0,4) between '"+ startDate +"' and '"+ endDate +"' and ks_id = "
						+ " (select j.id from tb_xzzzjg j where j.mc = '"+typeName+"')) r group by r.zgh,r.jzg_name,r.dept_name order by nums desc) s "
								+ " where s.jzg_name like '%"+jzgname+"%'";
			}
		}else if("科研项目".equals(type)){
			String xm = "select s.* from ( select r.zgh,r.jzg_name,r.dept_name ,r.name_ type_name , count(*) nums from ("
				    + " select jzg.zgh,zy.name_,jzg.xm jzg_name,jg.mc dept_name "
					+  xmsql;
			if("zc".equals(typecode)){
				String mysql = "  and ( zy.pid in (select de.id from  t_code_zyjszw de where de.name_ = '"+ typeName +"') or zy.name_ = '"+ typeName +"' ) ";
				if("未维护".equals(typeName)){
					mysql = " and name_ is null ";
				}
				sql =  xm + " left join t_code_zyjszw zy on zy.code_ = jzg.zyjszw_id "
						+ " where substr(t.start_time,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2  + mysql +") r "
						+ " group by r.zgh,r.jzg_name,r.dept_name,r.name_  order by nums desc  ) s  where s.jzg_name like '%"+jzgname+"%'";
			}else if("whcd".equals(typecode)){
				String mysql = "  and ( zy.pid in (select de.id from  t_code_education de where de.name_ = '"+ typeName +"') or zy.name_ = '"+ typeName +"' )  ";
				if("未维护".equals(typeName)){
					mysql = " and name_ is null ";
				}
				sql = xm + " left join t_code_education zy on to_number(zy.code_) = jzg.whc_id "
						+ " where substr(t.start_time,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 + mysql +") r "
						+ " group by r.zgh,r.jzg_name,r.dept_name,r.name_ order by nums desc ) s  where s.jzg_name like '%"+jzgname+"%'";
			}else if("xw".equals(typecode)){
				String mysql = "  and ( zy.pid in (select de.id from  t_code_degree de where de.name_ = '"+ typeName +"') or zy.name_ = '"+ typeName +"' )  ";
				if("未维护".equals(typeName)){
					mysql = " and name_ is null ";
				}
				sql =   xm+ " left join t_code_degree zy on to_number(zy.code_) = jzg.xwd_id  "
						+ " where substr(t.start_time,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 + mysql + ") r "
						+ " group by r.zgh,r.jzg_name,r.dept_name,r.name_ order by nums desc ) s  where s.jzg_name like '%"+jzgname+"%'" ;       
						             
			}else if("dw".equals(typecode)){
				sql = "select s.* from (select  r.zgh,r.jzg_name,r.dept_name ,'' type_name,count(*) nums from"
						+ " (select  jzg.zgh,jzg.xm jzg_name,jg.mc dept_name "
						+ xmsql +"  where substr(t.start_time,0,4) between '"+ startDate +"' and '"+ endDate +"' and ks_id = "
						+ " (select j.id from tb_xzzzjg j where j.mc = '"+typeName+"')) r group by r.zgh,r.jzg_name,r.dept_name order by nums desc) s "
								+ " where s.jzg_name like '%"+jzgname+"%'";
			}
		}else if("发表论文".equals(type)){
			String fblw = "select s.* from ( select r.zgh,r.jzg_name,r.dept_name ,r.name_ type_name , count(*) nums from ("
				    + " select jzg.zgh,zy.name_,jzg.xm jzg_name,jg.mc dept_name "
					+  fblwsql;
			if("zc".equals(typecode)){
				String mysql = "  and ( zy.pid in (select de.id from  t_code_zyjszw de where de.name_ = '"+ typeName +"') or zy.name_ = '"+ typeName +"' ) ";
				if("未维护".equals(typeName)){
					mysql = " and name_ is null ";
				}
				sql =  fblw + " left join t_code_zyjszw zy on zy.code_ = jzg.zyjszw_id "
						+ " where substr(t.year_,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2  + mysql +") r "
						+ " group by r.zgh,r.jzg_name,r.dept_name,r.name_  order by nums desc  ) s  where s.jzg_name like '%"+jzgname+"%'";
			}else if("whcd".equals(typecode)){
				String mysql = "  and ( zy.pid in (select de.id from  t_code_education de where de.name_ = '"+ typeName +"') or zy.name_ = '"+ typeName +"' )  ";
				if("未维护".equals(typeName)){
					mysql = " and name_ is null ";
				}
				sql = fblw + " left join t_code_education zy on to_number(zy.code_) = jzg.whc_id "
						+ " where substr(t.year_,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 + mysql +") r "
						+ " group by r.zgh,r.jzg_name,r.dept_name,r.name_ order by nums desc ) s  where s.jzg_name like '%"+jzgname+"%'";
			}else if("xw".equals(typecode)){
				String mysql = "  and ( zy.pid in (select de.id from  t_code_degree de where de.name_ = '"+ typeName +"') or zy.name_ = '"+ typeName +"' )  ";
				if("未维护".equals(typeName)){
					mysql = " and name_ is null ";
				}
				sql =   fblw+ " left join t_code_degree zy on to_number(zy.code_) = jzg.xwd_id  "
						+ " where substr(t.year_,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 + mysql + ") r "
						+ " group by r.zgh,r.jzg_name,r.dept_name,r.name_ order by nums desc ) s  where s.jzg_name like '%"+jzgname+"%'" ;       
						             
			}else if("dw".equals(typecode)){
				sql = "select s.* from (select  r.zgh,r.jzg_name,r.dept_name ,'' type_name,count(*) nums from"
						+ " (select  jzg.zgh,jzg.xm jzg_name,jg.mc dept_name "
						+ fblwsql +"  where substr(t.year_,0,4) between '"+ startDate +"' and '"+ endDate +"' and ks_id = "
						+ " (select j.id from tb_xzzzjg j where j.mc = '"+typeName+"')) r group by r.zgh,r.jzg_name,r.dept_name order by nums desc) s "
								+ " where s.jzg_name like '%"+jzgname+"%'";
			}
		}else if("收录论文".equals(type)){
			String sllw = "select s.* from ( select r.zgh,r.jzg_name,r.dept_name ,r.name_ type_name , count(*) nums from ("
				    + " select jzg.zgh,zy.name_,jzg.xm jzg_name,jg.mc dept_name "
					+  sllwsql;
			if("zc".equals(typecode)){
				String mysql = "  and ( zy.pid in (select de.id from  t_code_zyjszw de where de.name_ = '"+ typeName +"') or zy.name_ = '"+ typeName +"' ) ";
				if("未维护".equals(typeName)){
					mysql = " and name_ is null ";
				}
				sql =  sllw + " left join t_code_zyjszw zy on zy.code_ = jzg.zyjszw_id "
						+ " where substr(t.year_,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2  + mysql +") r "
						+ " group by r.zgh,r.jzg_name,r.dept_name,r.name_  order by nums desc  ) s  where s.jzg_name like '%"+jzgname+"%'";
			}else if("whcd".equals(typecode)){
				String mysql = "  and ( zy.pid in (select de.id from  t_code_education de where de.name_ = '"+ typeName +"') or zy.name_ = '"+ typeName +"' )  ";
				if("未维护".equals(typeName)){
					mysql = " and name_ is null ";
				}
				sql = sllw + " left join t_code_education zy on to_number(zy.code_) = jzg.whc_id "
						+ " where substr(t.year_,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 + mysql +") r "
						+ " group by r.zgh,r.jzg_name,r.dept_name,r.name_ order by nums desc ) s  where s.jzg_name like '%"+jzgname+"%'";
			}else if("xw".equals(typecode)){
				String mysql = "  and ( zy.pid in (select de.id from  t_code_degree de where de.name_ = '"+ typeName +"') or zy.name_ = '"+ typeName +"' )  ";
				if("未维护".equals(typeName)){
					mysql = " and name_ is null ";
				}
				sql =   sllw+ " left join t_code_degree zy on to_number(zy.code_) = jzg.xwd_id  "
						+ " where substr(t.year_,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 + mysql + ") r "
						+ " group by r.zgh,r.jzg_name,r.dept_name,r.name_ order by nums desc ) s  where s.jzg_name like '%"+jzgname+"%'" ;       
						             
			}else if("dw".equals(typecode)){
				sql = "select s.* from (select  r.zgh,r.jzg_name,r.dept_name ,'' type_name,count(*) nums from"
						+ " (select  jzg.zgh,jzg.xm jzg_name,jg.mc dept_name "
						+ sllwsql +"  where substr(t.year_,0,4) between '"+ startDate +"' and '"+ endDate +"' and ks_id = "
						+ " (select j.id from tb_xzzzjg j where j.mc = '"+typeName+"')) r group by r.zgh,r.jzg_name,r.dept_name order by nums desc) s "
								+ " where s.jzg_name like '%"+jzgname+"%'";
			}
		}else if("获奖论文".equals(type)){
			String hjlw = "select s.* from ( select r.zgh,r.jzg_name,r.dept_name ,r.name_ type_name , count(*) nums from ("
				    + " select jzg.zgh,zy.name_,jzg.xm jzg_name,jg.mc dept_name "
					+  hjlwsql;
			if("zc".equals(typecode)){
				String mysql = "  and ( zy.pid in (select de.id from  t_code_zyjszw de where de.name_ = '"+ typeName +"') or zy.name_ = '"+ typeName +"' ) ";
				if("未维护".equals(typeName)){
					mysql = " and name_ is null ";
				}
				sql =  hjlw + " left join t_code_zyjszw zy on zy.code_ = jzg.zyjszw_id "
						+ " where substr(t.award_time,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2  + mysql +") r "
						+ " group by r.zgh,r.jzg_name,r.dept_name,r.name_  order by nums desc  ) s  where s.jzg_name like '%"+jzgname+"%'";
			}else if("whcd".equals(typecode)){
				String mysql = "  and ( zy.pid in (select de.id from  t_code_education de where de.name_ = '"+ typeName +"') or zy.name_ = '"+ typeName +"' )  ";
				if("未维护".equals(typeName)){
					mysql = " and name_ is null ";
				}
				sql = hjlw + " left join t_code_education zy on to_number(zy.code_) = jzg.whc_id "
						+ " where substr(t.award_time,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 + mysql +") r "
						+ " group by r.zgh,r.jzg_name,r.dept_name,r.name_ order by nums desc ) s  where s.jzg_name like '%"+jzgname+"%'";
			}else if("xw".equals(typecode)){
				String mysql = "  and ( zy.pid in (select de.id from  t_code_degree de where de.name_ = '"+ typeName +"') or zy.name_ = '"+ typeName +"' )  ";
				if("未维护".equals(typeName)){
					mysql = " and name_ is null ";
				}
				sql =   hjlw+ " left join t_code_degree zy on to_number(zy.code_) = jzg.xwd_id  "
						+ " where substr(t.award_time,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 + mysql + ") r "
						+ " group by r.zgh,r.jzg_name,r.dept_name,r.name_ order by nums desc ) s  where s.jzg_name like '%"+jzgname+"%'" ;       
						             
			}else if("dw".equals(typecode)){
				sql = "select s.* from (select  r.zgh,r.jzg_name,r.dept_name ,'' type_name,count(*) nums from"
						+ " (select  jzg.zgh,jzg.xm jzg_name,jg.mc dept_name "
						+ hjlwsql +"  where substr(t.award_time,0,4) between '"+ startDate +"' and '"+ endDate +"' and ks_id = "
						+ " (select j.id from tb_xzzzjg j where j.mc = '"+typeName+"')) r group by r.zgh,r.jzg_name,r.dept_name order by nums desc) s "
								+ " where s.jzg_name like '%"+jzgname+"%'";
			}
		}else if("全部".equals(type)){
			String qb = " select zgh,count(*) nums from ("
					+ " select zgh from ("
					+ " select  j.zgh from tb_jzgxx j "
					+ " inner join t_res_outcome_appraisal_auth aa on aa.people_id = j.zgh"
					+ " inner join t_res_outcome_appraisal oa on oa.id = aa.appraisal_id and substr(oa.time_,0,4) between '"+startDate+"' and '"+endDate+"'"
					+ " inner join tb_xzzzjg jg on jg.id = j.ks_id  )"
					+ " UNION  ALL("
					+ " select  j.zgh from tb_jzgxx j "
					+ " inner join t_res_thesis_author ta on ta.people_id = j.zgh"
					+ " inner join t_res_thesis rt on rt.id = ta.thesis_id and substr(rt.year_,0,4) between '"+startDate+"' and '"+endDate+"'"
					+ " inner join tb_xzzzjg jg on jg.id = j.ks_id) "
					+ " UNION  ALL("
					+ " select  j.zgh from tb_jzgxx j "
					+ " inner join t_res_outcome_award_auth oaa on oaa.people_id = j.zgh"
					+ " inner join t_res_outcome_award roa on roa.id = oaa.outcome_award_id and substr(roa.award_time,0,4) between '"+startDate+"' and '"+endDate+"' "
					+ " inner join tb_xzzzjg jg on jg.id = j.ks_id )"
					+ " UNION  ALL("
					+ " select  j.zgh from tb_jzgxx j "
					+ " inner join t_res_thesis_author ta on ta.people_id = j.zgh "
					+ " inner join t_res_thesis_in ti on ti.thesis_id = ta.thesis_id and substr(ti.year_,0,4) between '"+startDate+"' and '"+endDate+"'"
					+ " inner join t_res_thesis t on t.id = ti.thesis_id"
					+ " inner join tb_xzzzjg jg on jg.id = j.ks_id )"
					+ " UNION  ALL("
					+ " select   j.zgh from tb_jzgxx j "
					+ " inner join t_res_thesis_author raa on raa.people_id = j.zgh"
					+ " inner join t_res_thesis_award rta on rta.thesis_id = raa.thesis_id and substr(rta.award_time,0,4) between '"+startDate+"' and '"+endDate+"'"
					+ " inner join t_res_thesis t on t.id = rta.thesis_id"
					+ " inner join tb_xzzzjg jg on jg.id = j.ks_id ) "
					+ " UNION  ALL("
					+ " select  j.zgh from tb_jzgxx j "
					+ " inner join t_res_patent_auth pa on pa.people_id = j.zgh"
					+ " inner join t_res_patent rp on rp.id = pa.patent_id and substr(rp.accredit_time,0,4) between '"+startDate+"' and '"+endDate+"'"
					+ " inner join tb_xzzzjg jg on jg.id = j.ks_id ) "
					+ " UNION  ALL("
					+ " select  j.zgh from tb_jzgxx j "
					+ " inner join t_res_project_auth rpa on rpa.people_id = j.zgh"
					+ " inner join t_res_project pro on pro.id = rpa.pro_id and substr(pro.start_time,0,4) between '"+startDate+"' and '"+endDate+"'"
					+ " inner join tb_xzzzjg jg on jg.id = j.ks_id) "
					+ " UNION  ALL("
					+ " select  j.zgh from tb_jzgxx j "
					+ " inner join t_res_work_author wa on wa.people_id = j.zgh"
					+ " inner join t_res_work wo on wo.id = wa.work_id and substr(wo.press_time,0,4) between '"+startDate+"' and '"+endDate+"'"
					+ " inner join tb_xzzzjg jg on jg.id = j.ks_id )) group by zgh";
			String sql3 = "";
			if(!"0".equals(deptId)){
				sql3 = " and zzjg.id in ("+deptId+")";
			}
			if("zc".equals(typecode)){
				String mysql = "where (zc.pid in (select de.id from  t_code_zyjszw de where de.name_ = '"+ typeName +"') or zc.name_ = '"+ typeName +"') ";
				if("未维护".equals(typeName)){
					mysql = " where zc.name_ is null ";
				}
				sql = "select s.* from (select jzg.zgh,jzg.xm jzg_name,zzjg.mc dept_name,zc.name_ type_name,r.nums from tb_jzgxx jzg inner join ("
						+ qb + " ) r on r.zgh = jzg.zgh left join tb_xzzzjg zzjg on zzjg.id = jzg.ks_id"
						+ " left join t_code_zyjszw zc on zc.code_ = jzg.zyjszw_id  "+ mysql + sql3 +" order by nums desc) s where s.jzg_name like '%"+jzgname+"%'";
			}else if("whcd".equals(typecode)){
				String mysql = " where (zc.pid in (select de.id from  t_code_education de where de.name_ = '"+ typeName +"') or zc.name_ = '"+ typeName +"')";
				if("未维护".equals(typeName)){
					mysql = " where zc.name_ is null  ";
				}
				sql = "select s.* from (select jzg.zgh,jzg.xm jzg_name,zzjg.mc dept_name,zc.name_ type_name,r.nums from tb_jzgxx jzg inner join ("
						+ qb + " ) r on r.zgh = jzg.zgh left join tb_xzzzjg zzjg on zzjg.id = jzg.ks_id"
						+ " left join t_code_education zc on zc.code_ = jzg.whc_id "+ mysql + sql3 +" order by nums desc) s where s.jzg_name like '%"+jzgname+"%'";
			}else if("xw".equals(typecode)){
				String mysql = "where (zc.pid in (select de.id from  t_code_degree de where de.name_ = '"+ typeName +"') or zc.name_ = '"+ typeName +"' ) ";
				if("未维护".equals(typeName)){
					mysql = " where zc.name_ is null  ";
				}
				sql = "select s.* from (select jzg.zgh,jzg.xm jzg_name,zzjg.mc dept_name,zc.name_ type_name,r.nums from tb_jzgxx jzg inner join ("
						+ qb + " ) r on r.zgh = jzg.zgh left join tb_xzzzjg zzjg on zzjg.id = jzg.ks_id"
						+ " left join t_code_degree zc on zc.code_ = jzg.xwd_id  "+ mysql + sql3 +" order by nums desc) s where s.jzg_name like '%"+jzgname+"%'";      
						             
			}else if("dw".equals(typecode)){
				sql = "select s.* from (select jzg.zgh,jzg.xm jzg_name,zzjg.mc dept_name,'' type_name,r.nums from tb_jzgxx jzg inner join ("
						+ qb + " ) r on r.zgh = jzg.zgh left join tb_xzzzjg zzjg on zzjg.id = jzg.ks_id "
						+ " where zzjg.mc = '"+typeName+"' order by nums desc) s where s.jzg_name like '%"+jzgname+"%'";      
			}
		}
		return sql;
	}
	
	@Override
	public String getZcFb(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String nowyear = getNowyear();
		String startDate = json.containsKey("from")?json.getString("from"):nowyear;	
		String endDate = json.containsKey("to")?json.getString("to"):nowyear;	
		String type = json.containsKey("type")?json.getString("type"):"鉴定成果";
		String deptId = json.containsKey("zzjgId")?json.getString("zzjgId"):"0";
		String sql2 = "";
		if(!"0".equals(deptId)){
			sql2 = " and jg.id in ("+deptId+")";
		}
		List<Map> result = new ArrayList<Map>();
		if("鉴定成果".equals(type)){
			String sql = zc1 + jdsql + " where substr(t.time_,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2+ ") s " + zc2 +" order by nums";
			result = baseDao.querySqlList(sql);
		}else if("获奖成果".equals(type)){
			String sql = zc1 + hjsql + " where substr(t.award_time,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 +") s " + zc2 +"order by nums";
			result = baseDao.querySqlList(sql);
		}else if("专利".equals(type)){
			String sql = zc1 + zlsql + " where substr(t.accredit_time,0,4) between '"+ startDate+"' and '"+ endDate +"' "+ sql2 +") s " + zc2 +"order by nums";
			result = baseDao.querySqlList(sql);
		}else if("著作".equals(type)){
			String sql =zc1 + zzsql + " where substr(t.press_time,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 +") s " + zc2 +"order by nums";
			result = baseDao.querySqlList(sql);
		}else if("科研项目".equals(type)){
			String sql = zc1 + xmsql + " where substr(t.start_time,0,4) between '"+ startDate+"' and '"+ endDate +"' "+ sql2 +") s " +zc2 +"order by nums";
			result = baseDao.querySqlList(sql);
		}else if("发表论文".equals(type)){
			String sql =zc1 + fblwsql + " where substr(t.year_,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 +" ) s " + zc2 +"order by nums";
			result = baseDao.querySqlList(sql);
		}else if("收录论文".equals(type)){
			String sql = zc1 + sllwsql + " where substr(t.year_,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 +" )s" + zc2 +"order by nums";
			result = baseDao.querySqlList(sql);
		}else if("获奖论文".equals(type)){
			String sql = zc1 + hjlwsql + " where substr(t.award_time,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 +") s "+ zc2 +"order by nums";
			result = baseDao.querySqlList(sql);
		}else if("全部".equals(type)){
			String allsql = getAllSql(startDate, endDate, deptId);
			String sql = zc1+" from tb_jzgxx jzg inner join ( "+allsql+ ") r on r.zgh = jzg.zgh ) s " + zc2 +"order by nums";
			result = baseDao.querySqlList(sql);
		}
		return Struts2Utils.list2json(result);
	}
	
	@Override
	public String getZcMxFb(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String nowyear = getNowyear();
		String startDate = json.containsKey("from")?json.getString("from"):nowyear;	
		String endDate = json.containsKey("to")?json.getString("to"):nowyear;	
		String type = json.containsKey("type")?json.getString("type"):"鉴定成果";
		String deptId = json.containsKey("zzjgId")?json.getString("zzjgId"):"0";
		String zc = json.containsKey("zc")?json.getString("zc"):"高等学校教师";
		String sql2 = "";
		if(!"0".equals(deptId)){
			sql2 = " and jg.id in ("+deptId+")";
		}
		List<Map> result = new ArrayList<Map>();
		if("鉴定成果".equals(type)){
			String sql = zcmx1 + jdsql + " where substr(t.time_,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2+ ") s "
					+ " on s.zyjszw_id =  co.code_ where co.pid = (select zw.id from t_code_zyjszw zw where zw.name_ like '"+ zc +"') group by co.name_";
			result = baseDao.querySqlList(sql);
		}else if("获奖成果".equals(type)){
			String sql = zcmx1 + hjsql + " where substr(t.award_time,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 +") s "
					+ " on s.zyjszw_id =  co.code_ where co.pid = (select zw.id from t_code_zyjszw zw where zw.name_ like '"+ zc +"') group by co.name_";
			result = baseDao.querySqlList(sql);
		}else if("专利".equals(type)){
			String sql = zcmx1 + zlsql + " where substr(t.accredit_time,0,4) between '"+ startDate+"' and '"+ endDate +"' "+ sql2 +") s "
					+ " on s.zyjszw_id =  co.code_ where co.pid = (select zw.id from t_code_zyjszw zw where zw.name_ like '"+ zc +"') group by co.name_";
			result = baseDao.querySqlList(sql);
		}else if("著作".equals(type)){
			String sql =zcmx1 + zzsql + " where substr(t.press_time,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 +") s "
					+ " on s.zyjszw_id =  co.code_ where co.pid = (select zw.id from t_code_zyjszw zw where zw.name_ like '"+ zc +"') group by co.name_";
			result = baseDao.querySqlList(sql);
		}else if("科研项目".equals(type)){
			String sql = zcmx1 + xmsql + " where substr(t.start_time,0,4) between '"+ startDate+"' and '"+ endDate +"' "+ sql2 +") s "
					+ " on s.zyjszw_id =  co.code_ where co.pid = (select zw.id from t_code_zyjszw zw where zw.name_ like '"+ zc +"') group by co.name_" ;
			result = baseDao.querySqlList(sql);
		}else if("发表论文".equals(type)){
			String sql =zcmx1 + fblwsql + " where substr(t.year_,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 +" ) s "
					+ " on s.zyjszw_id =  co.code_ where co.pid = (select zw.id from t_code_zyjszw zw where zw.name_ like '"+ zc +"') group by co.name_" ;
			result = baseDao.querySqlList(sql);
		}else if("收录论文".equals(type)){
			String sql = zcmx1 + sllwsql + " where substr(t.year_,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 +" )s "
					+ " on s.zyjszw_id =  co.code_ where co.pid = (select zw.id from t_code_zyjszw zw where zw.name_ like '"+ zc +"') group by co.name_" ;
			result = baseDao.querySqlList(sql);
		}else if("获奖论文".equals(type)){
			String sql = zcmx1 + hjlwsql + " where substr(t.award_time,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 +") s "
					+ " on s.zyjszw_id =  co.code_ where co.pid = (select zw.id from t_code_zyjszw zw where zw.name_ like '"+ zc +"') group by co.name_";
			result = baseDao.querySqlList(sql);
		}else if("全部".equals(type)){
			String allsql = getAllSql(startDate, endDate, deptId);
			String sql = zcmx1+" from tb_jzgxx jzg inner join ( "+allsql+ ") r on r.zgh = jzg.zgh ) s "
					+ " on s.zyjszw_id =  co.code_ where co.pid = (select zw.id from t_code_zyjszw zw where zw.name_ like '"+ zc +"') group by co.name_";
			result = baseDao.querySqlList(sql);
		}
		return Struts2Utils.list2json(result);
	}
	
	@Override
	public String getWhcdFb(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String nowyear = getNowyear();
		String startDate = json.containsKey("from")?json.getString("from"):nowyear;	
		String endDate = json.containsKey("to")?json.getString("to"):nowyear;	
		String type = json.containsKey("type")?json.getString("type"):"鉴定成果";
		String deptId = json.containsKey("zzjgId")?json.getString("zzjgId"):"0";
		String sql2 = "";
		if(!"0".equals(deptId)){
			sql2 = " and jg.id in ("+deptId+")";
		}
		List<Map> result = new ArrayList<Map>();
		
		if("鉴定成果".equals(type)){
			String sql = whcd1 + jdsql + " where substr(t.time_,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2+ ") s" + whcd2;
			result = baseDao.querySqlList(sql);
		}else if("获奖成果".equals(type)){
			String sql = whcd1 + hjsql + " where substr(t.award_time,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 +") s" + whcd2;
			result = baseDao.querySqlList(sql);
		}else if("专利".equals(type)){
			String sql = whcd1 + zlsql + " where substr(t.accredit_time,0,4) between '"+ startDate+"' and '"+ endDate +"' "+ sql2 +") s" + whcd2;
			result = baseDao.querySqlList(sql);
		}else if("著作".equals(type)){
			String sql =whcd1 + zzsql + " where substr(t.press_time,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 +") s" + whcd2;
			result = baseDao.querySqlList(sql);
		}else if("科研项目".equals(type)){
			String sql = whcd1 + xmsql + " where substr(t.start_time,0,4) between '"+ startDate+"' and '"+ endDate +"' "+ sql2 +") s" + whcd2 ;
			result = baseDao.querySqlList(sql);
		}else if("发表论文".equals(type)){
			String sql =whcd1 + fblwsql + " where substr(t.year_,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 +" ) s " + whcd2;
			result = baseDao.querySqlList(sql);
		}else if("收录论文".equals(type)){
			String sql = whcd1 + sllwsql + " where substr(t.year_,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 +" )s" + whcd2;
			result = baseDao.querySqlList(sql);
		}else if("获奖论文".equals(type)){
			String sql = whcd1 + hjlwsql + " where substr(t.award_time,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 +") s "+ whcd2;
			result = baseDao.querySqlList(sql);
		}else if("全部".equals(type)){
			String allsql = getAllSql(startDate, endDate, deptId);
			String sql = whcd1+" from tb_jzgxx jzg inner join ( "+allsql+ ") r on r.zgh = jzg.zgh ) s " + whcd2;
			result = baseDao.querySqlList(sql);
		}
		return Struts2Utils.list2json(result);
	}
	
	@Override
	public String getXwFb(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String nowyear = getNowyear();
		String startDate = json.containsKey("from")?json.getString("from"):nowyear;	
		String endDate = json.containsKey("to")?json.getString("to"):nowyear;	
		String type = json.containsKey("type")?json.getString("type"):"鉴定成果";
		String deptId = json.containsKey("zzjgId")?json.getString("zzjgId"):"0";
		String sql2 = "";
		if(!"0".equals(deptId)){
			sql2 = " and jg.id in ("+deptId+")";
		}
		List<Map> result = new ArrayList<Map>();
		
		if("鉴定成果".equals(type)){
			String sql = xw1 + jdsql + " where substr(t.time_,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2+ ") s " + xw2;
			result = baseDao.querySqlList(sql);
		}else if("获奖成果".equals(type)){
			String sql = xw1 + hjsql + " where substr(t.award_time,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 +") s " + xw2;
			result = baseDao.querySqlList(sql);
		}else if("专利".equals(type)){
			String sql = xw1 + zlsql + " where substr(t.accredit_time,0,4) between '"+ startDate+"' and '"+ endDate +"' "+ sql2 +") s " + xw2;
			result = baseDao.querySqlList(sql);
		}else if("著作".equals(type)){
			String sql =xw1 + zzsql + " where substr(t.press_time,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 +") s " + xw2;
			result = baseDao.querySqlList(sql);
		}else if("科研项目".equals(type)){
			String sql = xw1 + xmsql + " where substr(t.start_time,0,4) between '"+ startDate+"' and '"+ endDate +"' "+ sql2 +") s " + xw2 ;
			result = baseDao.querySqlList(sql);
		}else if("发表论文".equals(type)){
			String sql = xw1 + fblwsql + " where substr(t.year_,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 +" ) s " + xw2;
			result = baseDao.querySqlList(sql);
		}else if("收录论文".equals(type)){
			String sql = xw1 + sllwsql + " where substr(t.year_,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 +" ) s " + xw2;
			result = baseDao.querySqlList(sql);
		}else if("获奖论文".equals(type)){
			String sql = xw1 + hjlwsql + " where substr(t.award_time,0,4) between '"+ startDate +"' and '"+ endDate +"' "+ sql2 +") s "+ xw2;
			result = baseDao.querySqlList(sql);
		}else if("全部".equals(type)){
			String allsql = getAllSql(startDate, endDate, deptId);
			String sql = xw1+" from tb_jzgxx jzg inner join ( "+allsql+ ") r on r.zgh = jzg.zgh ) s " + xw2;
			result = baseDao.querySqlList(sql);
		}
		return Struts2Utils.list2json(result);
	}
	
	@Override
	public String getDwFb(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String nowyear = getNowyear();
		String startDate = json.containsKey("from")?json.getString("from"):nowyear;	
		String endDate = json.containsKey("to")?json.getString("to"):nowyear;	
		String type = json.containsKey("type")?json.getString("type"):"鉴定成果";
		List<Map> result = new ArrayList<Map>();
		
		if("鉴定成果".equals(type)){
			String sql = "select jg.mc dept_name,count(*) nums from tb_xzzzjg jg inner join ("
					+ " select distinct jzg.zgh,jzg.ks_id"
					+ jdsql+ "  where substr(t.time_,0,4) between '"+ startDate +"' and '"+ endDate +"' ) r on r.ks_id = jg.id group by jg.mc";
			result = baseDao.querySqlList(sql);
		}else if("获奖成果".equals(type)){
			String sql = "select jg.mc dept_name,count(*) nums from tb_xzzzjg jg inner join ("
					+ " select distinct jzg.zgh,jzg.ks_id"
					+ hjsql+ "  where substr(t.award_time,0,4) between '"+ startDate +"' and '"+ endDate +"' ) r on r.ks_id = jg.id group by jg.mc";
			result = baseDao.querySqlList(sql);
		}else if("专利".equals(type)){
			String sql = "select jg.mc dept_name,count(*) nums from tb_xzzzjg jg inner join ("
					+ " select distinct jzg.zgh,jzg.ks_id"
					+ zlsql+ "  where substr(t.accredit_time,0,4) between '"+ startDate +"' and '"+ endDate +"' ) r on r.ks_id = jg.id group by jg.mc";
			result = baseDao.querySqlList(sql);
		}else if("著作".equals(type)){
			String sql = "select jg.mc dept_name,count(*) nums from tb_xzzzjg jg inner join ("
					+ " select distinct jzg.zgh,jzg.ks_id"
					+ zzsql+ "  where substr(t.press_time,0,4) between '"+ startDate +"' and '"+ endDate +"' ) r on r.ks_id = jg.id group by jg.mc";
			result = baseDao.querySqlList(sql);
		}else if("科研项目".equals(type)){
			String sql = "select jg.mc dept_name,count(*) nums from tb_xzzzjg jg inner join ("
					+ " select distinct jzg.zgh,jzg.ks_id"
					+ xmsql+ "  where substr(t.start_time,0,4) between '"+ startDate +"' and '"+ endDate +"' ) r on r.ks_id = jg.id group by jg.mc";
			result = baseDao.querySqlList(sql);
		}else if("发表论文".equals(type)){
			String sql = "select jg.mc dept_name,count(*) nums from tb_xzzzjg jg inner join ("
					+ " select distinct jzg.zgh,jzg.ks_id"
					+ fblwsql+ "  where substr(t.year_,0,4) between '"+ startDate +"' and '"+ endDate +"' ) r on r.ks_id = jg.id group by jg.mc";
			result = baseDao.querySqlList(sql);
		}else if("收录论文".equals(type)){
			String sql = "select jg.mc dept_name,count(*) nums from tb_xzzzjg jg inner join ("
					+ " select distinct jzg.zgh,jzg.ks_id"
					+ sllwsql+ "  where substr(t.year_,0,4) between '"+ startDate +"' and '"+ endDate +"' ) r on r.ks_id = jg.id group by jg.mc";
			result = baseDao.querySqlList(sql);
		}else if("获奖论文".equals(type)){
			String sql = "select jg.mc dept_name,count(*) nums from tb_xzzzjg jg inner join ("
					+ " select distinct jzg.zgh,jzg.ks_id"
					+ hjlwsql+ "  where substr(t.award_time,0,4) between '"+ startDate +"' and '"+ endDate +"' ) r on r.ks_id = jg.id group by jg.mc";
			result = baseDao.querySqlList(sql);
		}else if("全部".equals(type)){
			String allsql = getAllSql(startDate, endDate, "0");
			String sql = "select zzjg.mc dept_name,count(*) nums from tb_jzgxx jzg inner join ( select zgh from ("
					+ allsql + " ) a group by a.zgh ) r on r.zgh = jzg.zgh "
							+ " inner join tb_xzzzjg zzjg on zzjg.id = jzg.ks_id group by zzjg.mc";
			result = baseDao.querySqlList(sql);
		}
		
		return Struts2Utils.list2json(result);
	}
	
	private String getAllSql(String startDate,String endDate,String deptId){
		String sql2 = "";
		if(!"0".equals(deptId)){
			sql2 = " and jg.id in ("+deptId+")";
		}
		String sql = "select distinct zgh from ("
				+ " select distinct j.zgh from tb_jzgxx j "
				+ " inner join t_res_outcome_appraisal_auth aa on aa.people_id = j.zgh"
				+ " inner join t_res_outcome_appraisal oa on oa.id = aa.appraisal_id and substr(oa.time_,0,4) between '"+ startDate +"'  and '"+ endDate +"' "
				+ " inner join tb_xzzzjg jg on jg.id = j.ks_id "+ sql2 +" )"
				+ " UNION  ALL("
				+ " select distinct j.zgh from tb_jzgxx j "
				+ " inner join t_res_thesis_author ta on ta.people_id = j.zgh"
				+ " inner join t_res_thesis rt on rt.id = ta.thesis_id and substr(rt.year_,0,4) between '"+ startDate +"' and '"+ endDate +"'"
				+ " inner join tb_xzzzjg jg on jg.id = j.ks_id "+ sql2 +") "
				+ " UNION  ALL("
				+ " select  distinct j.zgh from tb_jzgxx j "
				+ " inner join t_res_thesis_author ta on ta.people_id = j.zgh "
				+ " inner join t_res_thesis_in ti on ti.thesis_id = ta.thesis_id and substr(ti.year_,0,4) between '"+ startDate +"' and '"+ endDate +"'"
				+ " inner join t_res_thesis t on t.id = ti.thesis_id"
				+ " inner join tb_xzzzjg jg on jg.id = j.ks_id  "+ sql2 +")"
				+ " UNION  ALL("
				+ " select  distinct j.zgh from tb_jzgxx j "
				+ " inner join t_res_thesis_author raa on raa.people_id = j.zgh "
				+ " inner join t_res_thesis_award rta on rta.thesis_id = raa.thesis_id and substr(rta.award_time,0,4) between  '"+ startDate +"' and '"+ endDate +"'"
				+ " inner join t_res_thesis t on t.id = rta.thesis_id"
				+ " inner join tb_xzzzjg jg on jg.id = j.ks_id  "+ sql2 +") "
				+ " UNION  ALL("
				+ " select distinct j.zgh from tb_jzgxx j "
				+ " inner join t_res_outcome_award_auth oaa on oaa.people_id = j.zgh"
				+ " inner join t_res_outcome_award roa on roa.id = oaa.outcome_award_id and substr(roa.award_time,0,4) between '"+ startDate +"' and '"+ endDate +"' "
				+ " inner join tb_xzzzjg jg on jg.id = j.ks_id "+ sql2 +")"
				+ " UNION  ALL("
				+ " select distinct j.zgh from tb_jzgxx j "
				+ " inner join t_res_patent_auth pa on pa.people_id = j.zgh"
				+ " inner join t_res_patent rp on rp.id = pa.patent_id and substr(rp.accredit_time,0,4) between '"+ startDate +"' and '"+ endDate +"'"
				+ " inner join tb_xzzzjg jg on jg.id = j.ks_id "+ sql2 +") "
				+ " UNION  ALL("
				+ " select distinct j.zgh from tb_jzgxx j "
				+ " inner join t_res_project_auth rpa on rpa.people_id = j.zgh"
				+ " inner join t_res_project pro on pro.id = rpa.pro_id and substr(pro.start_time,0,4) between '"+ startDate +"' and '"+ endDate +"'"
				+ " inner join tb_xzzzjg jg on jg.id = j.ks_id "+ sql2 +") "
				+ " UNION  ALL("
				+ " select distinct j.zgh from tb_jzgxx j "
				+ " inner join t_res_work_author wa on wa.people_id = j.zgh"
				+ " inner join t_res_work wo on wo.id = wa.work_id and substr(wo.press_time,0,4) between '"+ startDate +"' and '"+ endDate +"'"
				+ " inner join tb_xzzzjg jg on jg.id = j.ks_id "+ sql2 +")";
		return sql;
	}
	
	
	private String getNowyear() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		SimpleDateFormat year = new SimpleDateFormat("yyyy");
		return year.format(calendar.getTime());
	}

	@Override
	@SuppressWarnings({ "unchecked" })
	public HSSFWorkbook getData1Export(String params) {
		String sql=getTable1Sql(params);
		System.out.println("sql:"+sql);
		List<Map<String,Object>> xsList = baseDao.querySqlList(sql);
		String str="职工号,教职工名字,单位名称,类型,成果数" ;
		HSSFWorkbook workbook = ExportUtil.getHSSFWorkbookByMap(str.split(","), "aaaa", xsList);
		return workbook;
	}
	
	@Override
	@SuppressWarnings({ "unchecked" })
	public HSSFWorkbook getData2Export(String params) {
		JSONObject paramObj = JSONObject.fromObject(params);
		String sql=getTable2Sql(params);
		System.out.println("sql:"+sql);
		List<Map<String,Object>> xsList = baseDao.querySqlList(sql);
		String show_type = paramObj.containsKey("type")?paramObj.getString("type"):"鉴定成果";
		String str="" ;
		switch (show_type) {
		case "鉴定成果":
			str="职工号,教职工名字,单位,成果名称,完成人,鉴定部门,鉴定形式,鉴定水平,鉴定时间,鉴定证号,成果登记号,学科门类,我校排名";
			break;
		case "获奖成果":
			str="职工号,教职工名称,单位,成果名称,获奖人,获奖名称,获奖级别,获奖类别,获奖等级,学科门类,获奖单位,获奖时间,证书编号";
			break;
		case "发表论文":
			str="职工号,教职工名称,单位,论文题目,作者姓名,论文所属单位,发表期刊,发表年份,年卷期页,期刊类别,所属学科门类,我校排名";
			break;
		case "收录论文":
			str="职工号,教职工名称,单位,论文题目,发表期刊,收录年份,年卷期页,ISSN,影响因子,SCI分区,收录期刊类别";
			break;
		case "获奖论文":
			str="职工号,教职工名称,单位,论文题目,获奖名称,获奖等级,授奖单位,授奖日期,证书编号";
			break;
		case "科研项目":
			str="职工号,教职工名称,单位,项目名称,主持人,承担单位,项目编号,项目类别,项目级别,项目等级,项目状态,所属学科门类,下达部门,合作单位,开始时间,结束时间,经费数额（万元）,立项年度";
			break;
		case "专利":
			str="职工号,教职工名字,单位,专利名称,发明人,专利权单位,受理日,专利类型,专利实施状态,学科门类,专利号,证书编号";
			break;
		case "著作":
			str="职工号,教职工名称,单位,著作题目,作者姓名,著作书号,出版单位,出版时间,著作字数,所属学科门类,备注";
			break;
		default:
			break;
		}
		HSSFWorkbook workbook = ExportUtil.getHSSFWorkbookByMap(str.split(","), "aaaa", xsList);
		return workbook;
	}

	
	



	
	
	
}
