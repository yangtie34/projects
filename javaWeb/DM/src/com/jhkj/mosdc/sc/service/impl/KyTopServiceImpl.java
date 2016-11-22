package com.jhkj.mosdc.sc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.util.DateUtils;
import com.jhkj.mosdc.framework.util.ExportUtil;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.Utils4Service;
import com.jhkj.mosdc.newoutput.util.MapUtils;
import com.jhkj.mosdc.sc.service.KyTopService;

public class KyTopServiceImpl implements KyTopService {
	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public void saveInitData() {
		for(int i=1990 ;i<2016 ;i++) {
			saveNum(i);
		}
		for(int i=1990 ;i<2016 ;i++) {
			saveUpNum(i);
		}
		
	}
	
	@Override
	public void saveInitDataByYear() {
		int i=Integer.valueOf(DateUtils.YEAR.format(new Date()));
		saveNum(i);
		saveUpNum(i);
	}
	
	
	private void saveUpNum(int year){
		String sql="insert into   tl_res_up_num "+
				"select tea_no,tea_name,dept_id,dept_name,'"+year+"' year_,sum(decode(year_, '"+year+"', cgs,0))-sum(decode(year_, '"+(year-1)+"', cgs,0)) cgzs , "+
				"sum(decode(year_, '"+year+"', lws,0))-sum(decode(year_, '"+(year-1)+"', lws,0)) lwzs , "+
				"sum(decode(year_, '"+year+"', xms,0))-sum(decode(year_, '"+(year-1)+"', xms,0)) xmzs , "+
				"sum(decode(year_, '"+year+"', zzs,0))-sum(decode(year_, '"+(year-1)+"', zzs,0)) zzzs , "+
				"sum(decode(year_, '"+year+"', zls,0))-sum(decode(year_, '"+(year-1)+"', zls,0)) zlzs  "+
				"from tl_res_num where year_ between '"+(year-1)+"' and '"+year+"' group by tea_no,tea_name,dept_id,dept_name ";
		baseDao.insert(sql);
	}
	
	private void saveNum(int year){
		String sql="insert into  tl_res_num              "+
				"select b.tea_no,j.xm tea_name,x.id dept_id,x.mc dept_name,'"+year+"' year_,b.cgs,b.xms,b.lws,b.zzs,b.zls from         "+
				"(select t.tea_no,sum(t.su) as cgs,sum(decode(t.ky, 'xms', su,0)) as xms,               "+
				"    sum(decode(t.ky, 'lws', su,0)) as lws,sum(decode(t.ky, 'zzs', su,0)) as zzs,       "+
				"    sum(decode(t.ky, 'zls', su,0)) as zls                     "+
				"from (      "+
				"select b.people_id tea_no,'xms' ky ,count(*) su from T_RES_PROJECT a inner join T_RES_PROJECT_AUTH b on a.id=b.pro_id     "+
				"where substr(a.start_time,0,4)='"+year+"' and b.people_id is not null group by b.people_id       "+
				"UNION ALL                            "+
				"select b.people_id tea_no,'lws' ky ,count(*) su from T_RES_THESIS a inner join T_RES_THESIS_AUTHOR b on a.id=b.THESIS_ID  "+
				"where a.year_='"+year+"' and b.people_id is not null group by b.people_id                        "+
				"UNION ALL                            "+
				"select b.people_id tea_no,'zzs' ky ,count(*) su from T_RES_WORK a inner join T_RES_WORK_AUTHOR b on a.id=b.WORK_ID        "+
				"where substr(a.PRESS_TIME,0,4)='"+year+"' and b.people_id is not null group by b.people_id       "+
				"UNION ALL                            "+
				"select b.people_id tea_no,'zls' ky ,count(*) su from T_RES_PATENT a inner join T_RES_PATENT_AUTH b on a.id=b.PATENT_ID    "+
				"where substr(a.ACCREDIT_TIME,0,4)='"+year+"' and b.people_id is not null group by b.people_id    "+
				")t group by t.tea_no ) b             "+
				"inner join tb_jzgxx j on b.tea_no=j.zgh                       "+
				"left join tb_xzzzjg x on j.yx_id=x.id                         "+
				"order by cgs desc ";
		baseDao.insert(sql);
	}
	
	private String getNum(String params){
		JSONObject paramObj = JSONObject.fromObject(params);
		String zzjgId = paramObj.containsKey("zzjgId")?paramObj.get("zzjgId").toString():"0";
        String kyt = paramObj.containsKey("kyt")?paramObj.getString("kyt"):"cgs";
        int rank = paramObj.containsKey("rank")?paramObj.getInt("rank"):10;
        int from = paramObj.containsKey("from")?paramObj.getInt("from"):2011;
        int to = paramObj.containsKey("to")?paramObj.getInt("to"):2015;
        int nf=to-from==0?1:to-from;
		String tj=zzjgId.equals("0")?"":" and dept_id="+zzjgId+" ";
		String sql="select rank_,tea_no,tea_name,dept_id,dept_name,cgs,cgps,lws,lwps,xms,xmps,zls,zlps,zzs,zzps from ( select rank() over (order by t."+kyt+" desc) rank_,t.* from ( "+
				"select tea_no,tea_name,dept_id,dept_name ,sum(cgs) cgs,round(sum(cgs)/"+nf+",2) cgps, "+
				"sum(lws) lws,round(sum(lws)/"+nf+",2) lwps, "+
				"sum(xms) xms,round(sum(xms)/"+nf+",2) xmps, "+
				"sum(zls) zls,round(sum(zls)/"+nf+",2) zlps, "+
				"sum(zzs) zzs,round(sum(zzs)/"+nf+",2) zzps from tl_res_num  "+
				"where year_ between '"+from+"' and '"+to+"' "+tj+" group by tea_no,tea_name,dept_id,dept_name "+
				") t ) where rank_ < = "+rank ;
		return sql;
	}
	
	private String getUpNum(String params){
		JSONObject paramObj = JSONObject.fromObject(params);
		String zzjgId = paramObj.containsKey("zzjgId")?paramObj.get("zzjgId").toString():"0";
        String kyt = paramObj.containsKey("kyt")?paramObj.getString("kyt"):"avg_cgzs";
        int rank = paramObj.containsKey("rank")?paramObj.getInt("rank"):10;
        int from = paramObj.containsKey("from")?paramObj.getInt("from"):2011;
        int to = paramObj.containsKey("to")?paramObj.getInt("to"):2015;
        int nf=to-from==0?1:to-from;
		String tj=zzjgId.equals("0")?"":" and dept_id="+zzjgId+" ";
		String sql= "select rank_,tea_no,tea_name,dept_id,dept_name,avg_cgzs,avg_lwzs,avg_xmzs,avg_zlzs,avg_zzzs from ( select rank() over (order by t."+kyt+" desc) rank_,t.* from ( "+
				    "select tea_no,tea_name,dept_id,dept_name ,round(sum(cgzs)/"+nf+",2) avg_cgzs, "+
					"round(sum(lwzs)/"+nf+",2) avg_lwzs, "+
					"round(sum(xmzs)/"+nf+",2) avg_xmzs, "+
					"round(sum(zlzs)/"+nf+",2) avg_zlzs, "+
					"round(sum(zzzs)/"+nf+",2) avg_zzzs from tl_res_up_num  "+
					"where year_ between '"+from+"' and '"+to+"' "+tj+" group by tea_no,tea_name,dept_id,dept_name "+
					") t ) where rank_ < = "+rank ;
		return sql;	
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String getTopNum(String params) {
		String sql=getNum(params);
		Map paramsMap = Utils4Service.packageParams(params);
		Map result = baseDao.queryTableContentBySQL(sql,paramsMap);
		Map backval = new HashMap();
		backval.put("success", true);
		backval.put("data", result.get("queryList"));
		backval.put("count", result.get("count"));
		return Struts2Utils.map2json(backval);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String getTopUpNum(String params) {
		String sql=getUpNum(params);
		Map paramsMap = Utils4Service.packageParams(params);
		Map result = baseDao.queryTableContentBySQL(sql,paramsMap);
		Map backval = new HashMap();
		backval.put("success", true);
		backval.put("data", result.get("queryList"));
		backval.put("count", result.get("count"));
		return Struts2Utils.map2json(backval);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String getTopGoodNum(String params) {
		String sql=getGoodNumSql(params);
        Map paramsMap = Utils4Service.packageParams(params);
		Map result = baseDao.queryTableContentBySQL(sql,paramsMap);
		Map backval = new HashMap();
		backval.put("success", true);
		backval.put("data", result.get("queryList"));
		backval.put("count", result.get("count"));
		return Struts2Utils.map2json(backval);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String getUpNumYear(String params){
		JSONObject paramObj = JSONObject.fromObject(params);
        String show_type = paramObj.containsKey("show_type")?paramObj.getString("show_type"):"lws";
        String tea_no = paramObj.containsKey("tea_no")?paramObj.getString("tea_no"):"11";
        int from = (paramObj.containsKey("from")?paramObj.getInt("from"):2011)-1;
        int to = paramObj.containsKey("to")?paramObj.getInt("to"):2015;
		String sql="select year_,"+show_type+" tnum from tl_res_num where tea_no='"+tea_no+"' and year_ between '"+from+"' and '"+to+"' order by year_ ";
		List<Map<String,Object>> list = baseDao.querySqlList(sql);
		int lastYearNum=0;
		List<Map<String,Object>> outList=new ArrayList<Map<String,Object>>();
		int j=0;
		for (int i = from; i <=to ; i++) {
			Map<String,Object> map=new HashMap<String, Object>();
			if(list.size()!=j){
				if(list.get(j).get("YEAR_").toString().equals(i+"")){
					int thisNum=MapUtils.getIntValue(list.get(j), "TNUM");
					map.put("YEAR", i+"");
					map.put("NUM",thisNum);
					if(i==from){
						map.put("UP_NUM","不计算");
					}else{
						map.put("UP_NUM",thisNum-lastYearNum);
					}
					lastYearNum=thisNum;
					j++;
				}else{
					map.put("YEAR", i+"");
					map.put("NUM",0);
					if(i==from){
						map.put("UP_NUM","不计算");
						lastYearNum=0;
					}else{
						map.put("UP_NUM",0-lastYearNum);
					}
				}
			}else {
				map.put("YEAR", i+"");
				map.put("NUM",0);
				map.put("UP_NUM",0-lastYearNum);
			}
			outList.add(map);
		}
		Map backval = new HashMap();
		backval.put("success", true);
		backval.put("data", outList);
		backval.put("count", to-from+1);
		return Struts2Utils.map2json(backval);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String getPeopleTypeByPie(String params) {
		JSONObject paramObj = JSONObject.fromObject(params);
		String people_count = paramObj.containsKey("people_count")?paramObj.getString("people_count"):"count";
		String sql=getPeopleTypeByPieSql(params);		
		if(people_count.equals("count")){
			List<Map> result = baseDao.querySqlList(sql);
			return Struts2Utils.list2json(result);
		}else{
			Map paramsMap = Utils4Service.packageParams(params);
			Map result = baseDao.queryTableContentBySQL(sql,paramsMap);
			Map backval = new HashMap();
			backval.put("success", true);
			backval.put("data", result.get("queryList"));
			backval.put("count", result.get("count"));
			return Struts2Utils.map2json(backval);
			
		}
	}
	
	
	
	private String getPeopleTypeByPieSql(String params) {
		JSONObject paramObj = JSONObject.fromObject(params);
		String people_count = paramObj.containsKey("people_count")?paramObj.getString("people_count"):"count";
		String people_list_type = paramObj.containsKey("people_list_type")?paramObj.getString("people_list_type"):"未维护";
		String people_type = paramObj.containsKey("people_type")?paramObj.getString("people_type"):"num";
		String group_type=paramObj.containsKey("group_type")?paramObj.getString("group_type"):"zyjszw";
		String sql="";
		String people_sql="";
		String left_sql="";
		String where_sql="";
		if(paramObj.containsKey("where_xm")&&(!paramObj.getString("where_xm").equals(""))){
			where_sql=where_sql+" and jzg.xm like '%"+paramObj.getString("where_xm")+"%' ";
		}
		
		
		if(people_type.equals("num")){
			people_sql=getNum(params)+" )  people left join tb_jzgxx jzg on people.tea_no= jzg.zgh ";
		}else if(people_type.equals("up_num")){
			people_sql=getUpNum(params)+" )  people left join tb_jzgxx jzg on people.tea_no= jzg.zgh ";
		}else{
			people_sql=getGoodNumSql(params)+" )  people left join tb_jzgxx jzg on people.tea_no= jzg.zgh ";
		}
		
		if(group_type.equals("zyjszw")){
			left_sql="left join t_code_zyjszw pc on jzg.zyjszw_id = pc.code_ ";
		}else if(group_type.equals("whcd")){
			left_sql="left join t_code_education pc on jzg.whc_id = to_number(pc.code_)";
		}else if(group_type.equals("xw")){
			left_sql="left join t_code_degree pc on jzg.xwd_id = to_number(pc.code_) ";
		}
		
		if(people_count.equals("count")){
			sql="select nvl(pc.name_,'未维护') name_,count(*) cou from ( "+people_sql+left_sql+"group by pc.name_ ";
			if(group_type.equals("dwfb")){
				left_sql="left join tb_xzzzjg pc on jzg.dwd_id = pc.id group by pc.mc order by count(*) desc";
				sql="select nvl(pc.mc,'未维护') name_,count(*) cou from ( " +people_sql+left_sql;
			}
			
		}else{
			if(people_list_type.equals("未维护")){
				people_list_type=" is null ";
			}else{
				people_list_type=" = '"+people_list_type+"' ";
			}
			left_sql=left_sql+"left join tb_xzzzjg xz on jzg.dwd_id = xz.id where pc.name_"+people_list_type;
			sql="select jzg.zgh,jzg.xm,xz.mc from ( "+people_sql+left_sql+where_sql;
			
			if(group_type.equals("dwfb")){
				left_sql="left join tb_xzzzjg pc on jzg.dwd_id = pc.id where pc.mc ";
				sql="select jzg.zgh,jzg.xm,pc.mc from ( " +people_sql+left_sql+people_list_type+where_sql;
			}
		}
		
		
		return sql;
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String getDataList(String params){
		String sql=getOneDataSql(params);
        Map paramsMap = Utils4Service.packageParams(params);
		Map result = baseDao.queryTableContentBySQL(sql,paramsMap);
		Map backval = new HashMap();
		backval.put("success", true);
		backval.put("data", result.get("queryList"));
		backval.put("count", result.get("count"));
		return Struts2Utils.map2json(backval);
        
	}
	
	@Override
	@SuppressWarnings({ "unchecked" })
	public HSSFWorkbook getDataMdExport(String params) {
		JSONObject paramObj = JSONObject.fromObject(params);
		String sql=getOneDataSql(params);
		List<Map<String,Object>> xsList = baseDao.querySqlList(sql);
		String show_type = paramObj.containsKey("show_type")?paramObj.getString("show_type"):"lws";
		String str="" ;
		switch (show_type) {
		case "lws":
			str="论文名称,参与人员,所属单位,发表期刊,年卷期页,期刊类别,所属学科门类,备注";
			break;
		case "xms":
			str="项目编号,项目名称,所属单位,项目类别,项目级别,项目等级,下达部门,合作单位,开始时间,结束时间,经费(万元),状态,所属学科门类,备注";
			break;
		case "zls":
			str="专利编号,专利名称,参与人员,所属单位,专利类型,专利实施状态,受理日,授权日,所属学科门类,备注";
			break;
		default:
			str="著作编号,著作名称,参与人员,所属单位,出版单位,出版时间,著作字数,所属学科门类,备注";
			break;
		}
		
		HSSFWorkbook workbook = ExportUtil.getHSSFWorkbookByMap(str.split(","), "aaaa", xsList);
		return workbook;
	}
	
	@Override
	@SuppressWarnings({ "unchecked" })
	public HSSFWorkbook getPeopleExport(String params) {
		String sql=getPeopleTypeByPieSql(params);
		List<Map<String,Object>> xsList = baseDao.querySqlList(sql);
		String str="职工号,姓名,院系" ;
		HSSFWorkbook workbook = ExportUtil.getHSSFWorkbookByMap(str.split(","), "aaaa", xsList);
		return workbook;
	}
	
	@Override
	@SuppressWarnings({ "unchecked" })
	public HSSFWorkbook getDataGMdExport(String params) {
		JSONObject paramObj = JSONObject.fromObject(params);
		String sql=getGoodNumSql(params);
		List<Map<String,Object>> xsList = baseDao.querySqlList(sql);
		String show_type = paramObj.containsKey("kyt")?paramObj.getString("kyt"):"gjxm";
		String str="" ;
		switch (show_type) {
		case "gjxm":
			str="项目编号,项目名称,所属单位,项目类别,项目级别,项目等级,下达部门,合作单位,开始时间,结束时间,经费(万元),状态,所属学科门类,备注";
			break;
		case "zdxm":
			str="项目编号,项目名称,所属单位,项目类别,项目级别,项目等级,下达部门,合作单位,开始时间,结束时间,经费(万元),状态,所属学科门类,备注";
			break;
		case "sllw":
			str="论文名称,参与人员,所属单位,收录类别,SCI分区,影响因子,收录年份,学科门类,备注";
			break;
		case "fblw":
			str="论文名称,参与人员,所属单位,发表期刊,年卷期页,期刊类别,所属学科门类,备注";
			break;
		case "hjlw":
			str="论文名称,参与人员,所属单位,获奖名称,获奖级别,获奖等级,授奖单位,授奖时间,学科门类,备注";
			break;
		case "sjcgj":
			str="成果名称,参与人员,所属单位,获奖名称,获奖级别,获奖等级,获奖类别,授奖单位,授奖时间,学科门类";
			break;
		default:
			break;
		}
		
		HSSFWorkbook workbook = ExportUtil.getHSSFWorkbookByMap(str.split(","), "aaaa", xsList);
		return workbook;
	}
	
	private String getGoodNumSql(String params){
		JSONObject paramObj = JSONObject.fromObject(params);
		String zzjgId = paramObj.containsKey("zzjgId")?paramObj.get("zzjgId").toString():"0";
		int rank = paramObj.containsKey("rank")?paramObj.getInt("rank"):10;
		String tea_no = paramObj.containsKey("tea_no")?paramObj.getString("tea_no"):"";
		boolean is_count = paramObj.containsKey("is_count")?paramObj.getBoolean("is_count"):true;
		String kyt = paramObj.containsKey("kyt")?paramObj.getString("kyt"):"gjxm";
		int from = paramObj.containsKey("from")?paramObj.getInt("from"):2011;
		int to = paramObj.containsKey("to")?paramObj.getInt("to"):2015;
		String tj=zzjgId.equals("0")?"":" and x.id='"+zzjgId+"' ";
		String sql="" ,TopSql="",EndSql="";
		String where_sql="";
		if(paramObj.containsKey("where_year")&&(!paramObj.getString("where_year").equals(""))){
			from=to=Integer.valueOf(paramObj.getString("where_year"));
		}
		//TODO 此处SQL中的CODE需要通过静态变量来修改
		switch (kyt) {
		case "gjxm":
			if(paramObj.containsKey("where_cgxm")&&(!paramObj.getString("where_cgxm").equals(""))){
    			where_sql=where_sql+" and a.name_ like '%"+paramObj.getString("where_cgxm")+"%' ";
    		}
			TopSql="select a.pro_no,a.name_,xx.mc for_dept_name,a.category,lc.name_ level_name,rc.name_ rank_name,a.issued_dept,a.teamwork_dept,a.start_time,a.end_time, "+
        			"a.fund,sc.name_ state_name,csub.name_ project_name,a.remark " ;
			sql="from T_RES_PROJECT a inner join T_RES_PROJECT_AUTH b on a.id=b.pro_id     "+
					"inner join tb_jzgxx j on b.people_id=j.zgh              "+
					"left join tb_xzzzjg x on j.yx_id=x.id                  "+
					"left join tb_xzzzjg xx on a.dept_id=xx.id             "+
					"left join t_code lc on (a.level_code=lc.code_ and lc.code_type='RES_PROJECT_LEVEL_CODE') "+
        			"left join t_code rc on (a.rank_code=rc.code_ and rc.code_type='RES_PROJECT_RANK_CODE') "+
        			"left join t_code sc on (a.state_code=sc.code_ and sc.code_type='RES_PROJECT_STATE_CODE' ) "+
        			"left join t_code_subject csub on a.project_id=csub.code_ "+
					"where substr(a.start_time,0,4) between '"+from+"' and '"+to+"'                "+
					"and a.level_code='03' " + tj;
			EndSql="and b.people_id ='"+tea_no+"' "+where_sql+" order by a.start_time  ";
			break;
		case "zdxm":
			if(paramObj.containsKey("where_cgxm")&&(!paramObj.getString("where_cgxm").equals(""))){
    			where_sql=where_sql+" and a.name_ like '%"+paramObj.getString("where_cgxm")+"%' ";
    		}
			TopSql="select a.pro_no,a.name_,xx.mc for_dept_name,a.category,lc.name_ level_name,rc.name_ rank_name,a.issued_dept,a.teamwork_dept,a.start_time,a.end_time, "+
        			"a.fund,sc.name_ state_name,csub.name_ project_name,a.remark " ;
			sql="from T_RES_PROJECT a inner join T_RES_PROJECT_AUTH b on a.id=b.pro_id     "+
					"inner join tb_jzgxx j on b.people_id=j.zgh              "+
					"left join tb_xzzzjg x on j.yx_id=x.id                  "+
					"left join tb_xzzzjg xx on a.dept_id=xx.id             "+
					"left join t_code lc on (a.level_code=lc.code_ and lc.code_type='RES_PROJECT_LEVEL_CODE') "+
        			"left join t_code rc on (a.rank_code=rc.code_ and rc.code_type='RES_PROJECT_RANK_CODE') "+
        			"left join t_code sc on (a.state_code=sc.code_ and sc.code_type='RES_PROJECT_STATE_CODE' ) "+
        			"left join t_code_subject csub on a.project_id=csub.code_ "+
					"where substr(a.start_time,0,4) between '"+from+"' and '"+to+"'                "+
					"and ( a.rank_code='03' or a.rank_code='02')  " + tj;
			EndSql="and b.people_id ='"+tea_no+"' "+where_sql+" order by a.start_time  ";
			break;
		case "sllw":
			if(paramObj.containsKey("where_cgxm")&&(!paramObj.getString("where_cgxm").equals(""))){
    			where_sql=where_sql+" and rt.title_ like '%"+paramObj.getString("where_cgxm")+"%' ";
    		}
			TopSql="select rt.title_,rt.authors,xx.mc for_dept_name,c1.name_ type_name ,rp.sci_zone,rp.impact_factor,rp.year_,csub.name_ project_name,rp.remark  ";
			sql="from T_RES_THESIS_IN rp    "+
					"inner join T_RES_THESIS rt on rp.thesis_id=rt.id       "+
					"inner join T_RES_THESIS_AUTHOR b on rp.id=b.thesis_id                       "+
					"inner join tb_jzgxx j on b.people_id=j.zgh              "+
					"left join tb_xzzzjg x on j.yx_id=x.id                 "+
					"left join tb_xzzzjg xx on rt.dept_id=xx.id             "+
					"left join t_code c1 on ( rp.periodical_type_code = c1.code_ and c1.code_type='RES_PERIODICAL_TYPE_CODE' ) "+
					"left join t_code_subject csub on rt.project_id=csub.code_  "+
					"where rp.year_ between '"+from+"' and '"+to+"'  " + tj;
			EndSql="and b.people_id ='"+tea_no+"' "+where_sql+" order by rp.year_ , rp.impact_factor desc ";
			break;
		case "fblw":
			if(paramObj.containsKey("where_cgxm")&&(!paramObj.getString("where_cgxm").equals(""))){
    			where_sql=where_sql+" and a.title_ like '%"+paramObj.getString("where_cgxm")+"%' ";
    		}
			TopSql="select a.title_,a.authors,xx.mc for_dept_name,a.periodical,a.njqy,ptc.name_ type_name,csub.name_ project_name,a.remark  ";
			sql="from  T_RES_THESIS a         "+
					"inner join T_RES_THESIS_AUTHOR b on a.id=b.thesis_id "+
					"inner join tb_jzgxx j on b.people_id=j.zgh              "+
					"left join tb_xzzzjg x on j.yx_id=x.id                 "+
					"left join tb_xzzzjg xx on a.dept_id=xx.id             "+
					"left join t_code ptc on (a.periodical_type_code=ptc.code_ and ptc.code_type='RES_PERIODICAL_TYPE_CODE') "+
        			"left join t_code_subject csub on a.project_id=csub.code_ "+
					"where a.year_ between '"+from+"' and '"+to+"'         "+
					"and a.periodical_type_code in ('006', '007', '008', '009', '027', '028')  " + tj;
			EndSql="and b.people_id ='"+tea_no+"' "+where_sql+" order by a.year_  ";
			break;
		case "hjlw":
			if(paramObj.containsKey("where_cgxm")&&(!paramObj.getString("where_cgxm").equals(""))){
    			where_sql=where_sql+" and rt.title_ like '%"+paramObj.getString("where_cgxm")+"%' ";
    		}
			TopSql="select rt.title_,rt.authors,xx.mc for_dept_name,substr(rp.award_name,0,instr(rp.award_name,'-')-1) award_name,c1.name_ level_name,c2.name_ rank_name ,rp.award_dept,rp.award_time,csub.name_ project_name,rp.remark ";
			sql="from T_RES_THESIS_AWARD rp    "+
					"inner join T_RES_THESIS rt on rp.thesis_id=rt.id       "+
					"inner join T_RES_THESIS_AUTHOR b on rp.id=b.thesis_id                       "+
					"inner join tb_jzgxx j on b.people_id=j.zgh              "+
					"left join tb_xzzzjg x on j.yx_id=x.id                 "+
					"left join tb_xzzzjg xx on rt.dept_id=xx.id             "+
					"left join t_code c1 on ( rp.award_level_code = c1.code_ and c1.code_type='RES_AWARD_LEVEL_CODE' ) "+
					"left join t_code c2 on ( rp.award_rank_code = c2.code_ and c2.code_type='RES_THESIS_RANK_CODE' ) "+
					"left join t_code_subject csub on rt.project_id=csub.code_  "+
					"where rt.year_ between '"+from+"' and '"+to+"'  " + tj;
			EndSql="and b.people_id ='"+tea_no+"' "+where_sql+" order by rt.year_  ";
			break;
		case "sjcgj":
			if(paramObj.containsKey("where_cgxm")&&(!paramObj.getString("where_cgxm").equals(""))){
    			where_sql=where_sql+" and rt.name_ like '%"+paramObj.getString("where_cgxm")+"%' ";
    		}
			TopSql="select rt.name_,rt.prizewinner,rt.award_name,c1.name_ level_name," +
					"c2.name_ rank_name,c3.name_ category_name,rt.award_dept,rt.award_time,csub.name_ project_name ";
			
			sql="from  T_RES_OUTCOME_AWARD rt  "+
					"inner join T_RES_OUTCOME_AWARD_AUTH b on rt.id=b.outcome_award_id           "+
					"inner join tb_jzgxx j on b.people_id=j.zgh              "+
					"left join tb_xzzzjg x on j.yx_id=x.id                 "+
					"left join tb_xzzzjg xx on rt.dept_id=xx.id             "+
					"left join t_code c1 on ( rt.level_code = c1.code_ and c1.code_type='RES_AWARD_LEVEL_CODE' ) "+
					"left join t_code c2 on ( rt.rank_code = c2.code_ and c2.code_type='RES_AWARD_RANK_CODE' ) "+
					"left join t_code c3 on ( rt.category_code = c3.code_ and c3.code_type='RES_AWARD_CATEGORY_CODE' ) "+
					"left join t_code_subject csub on rt.project_id=csub.code_  "+
					"where substr(rt.award_time,0,4) between '"+from+"' and '"+to+"'                 "+
					"and rt.level_code='02'  " + tj;
			EndSql="and b.people_id ='"+tea_no+"' "+where_sql+" order by rt.award_time  ";
			break;
		default:
			break;
		}
		if(is_count){
			TopSql="select tea_no,tea_name,dept_id,dept_name,count_,avg_year,rank_ from (     "+
					"select j.zgh tea_no ,j.xm tea_name,x.id dept_id,x.mc dept_name,count(*) count_, "+
					"round(count(*)/3,2) avg_year ,rank() over(order by count(*) desc) rank_         ";
			EndSql="group by j.zgh ,j.xm,x.id,x.mc                   "+
					")where rank_<= "+rank;
		}
		sql=sql.equals("")?sql:TopSql+sql+EndSql;
		return sql;
	}
	
	private String getOneDataSql(String params){
		JSONObject paramObj = JSONObject.fromObject(params);
		String tea_no = paramObj.containsKey("tea_no")?paramObj.getString("tea_no"):"";
		String show_type = paramObj.containsKey("show_type")?paramObj.getString("show_type"):"lws";
		int show_year = paramObj.containsKey("show_year")?paramObj.getInt("show_year"):0;
		int from = paramObj.containsKey("from")?paramObj.getInt("from"):2011;
		int to = paramObj.containsKey("to")?paramObj.getInt("to"):2015;
		String where_sql="";
		if(show_year!=0){
			from=show_year;
			to=show_year;
		}else if(paramObj.containsKey("where_year")&&(!paramObj.getString("where_year").equals(""))){
			from=to=Integer.valueOf(paramObj.getString("where_year"));
		}
        String sql="";
        if(show_type.equals("lws")){
        	if(paramObj.containsKey("where_cgxm")&&(!paramObj.getString("where_cgxm").equals(""))){
    			where_sql=where_sql+" and a.title_ like '%"+paramObj.getString("where_cgxm")+"%' ";
    		}
        	sql="select a.title_,a.authors,xx.mc for_dept_name,a.periodical,a.njqy,ptc.name_ type_name,csub.name_ project_name,a.remark  "+
        			"from T_RES_THESIS a inner join T_RES_THESIS_AUTHOR b on a.id=b.THESIS_ID "+
        			"left join tb_xzzzjg xx on a.dept_id=xx.id             "+
        			"left join t_code ptc on (a.periodical_type_code=ptc.code_ and ptc.code_type='RES_PERIODICAL_TYPE_CODE') "+
        			"left join t_code_subject csub on a.project_id=csub.code_ "+
        			"where a.year_ between '"+from+"' and '"+to+"' and b.people_id ='"+tea_no+"' "+where_sql+" order by a.year_  ";
        }else if(show_type.equals("xms")){
        	if(paramObj.containsKey("where_cgxm")&&(!paramObj.getString("where_cgxm").equals(""))){
    			where_sql=where_sql+" and a.name_ like '%"+paramObj.getString("where_cgxm")+"%' ";
    		}
        	sql="select a.pro_no,a.name_,xx.mc for_dept_name,a.category,lc.name_ level_name,rc.name_ rank_name,a.issued_dept,a.teamwork_dept,a.start_time,a.end_time, "+
        			"a.fund,sc.name_ state_name,csub.name_ project_name,a.remark from T_RES_PROJECT a  "+
        			"inner join T_RES_PROJECT_AUTH b on a.id=b.pro_id "+
        			"left join tb_xzzzjg xx on a.dept_id=xx.id             "+
        			"left join t_code lc on (a.level_code=lc.code_ and lc.code_type='RES_PROJECT_LEVEL_CODE') "+
        			"left join t_code rc on (a.rank_code=rc.code_ and rc.code_type='RES_PROJECT_RANK_CODE') "+
        			"left join t_code sc on (a.state_code=sc.code_ and sc.code_type='RES_PROJECT_STATE_CODE' ) "+
        			"left join t_code_subject csub on a.project_id=csub.code_ "+
        			"where  substr(a.start_time,0,4) between '"+from+"' and '"+to+"' and b.people_id ='"+tea_no+"' "+where_sql+" order by a.start_time  ";
        }else if(show_type.equals("zls")){
        	if(paramObj.containsKey("where_cgxm")&&(!paramObj.getString("where_cgxm").equals(""))){
    			where_sql=where_sql+" and a.name_ like '%"+paramObj.getString("where_cgxm")+"%' ";
    		}
        	sql="select a.patent_no,a.name_,a.inventors,xx.mc for_dept_name,tc.name_ type_name,stc.name_ state_name,a.accept_time,a.accredit_time,csub.name_ project_name, "+
		        	"a.remark from T_RES_PATENT a inner join T_RES_PATENT_AUTH b on a.id=b.PATENT_ID "+
		        	"left join tb_xzzzjg xx on a.patent_dept=xx.id             "+
		        	"left join t_code tc on (a.patent_type_code=tc.code_ and tc.code_type='RES_PATENT_TYPE_CODE') "+
		        	"left join t_code stc on (a.patent_state_code=stc.code_ and stc.code_type='RES_PATENT_STATE_CODE') "+
		        	"left join t_code_subject csub on a.project_id=csub.code_ "+
		        	"where substr(a.ACCREDIT_TIME,0,4) between '"+from+"' and '"+to+"'  and b.people_id ='"+tea_no+"' "+where_sql+" order by a.ACCREDIT_TIME  ";
        }else {
        	if(paramObj.containsKey("where_cgxm")&&(!paramObj.getString("where_cgxm").equals(""))){
    			where_sql=where_sql+" and a.title_ like '%"+paramObj.getString("where_cgxm")+"%' ";
    		}
        	sql="select a.work_no,a.title_,a.authors,xx.mc for_dept_name,a.press_name,a.press_time,a.number_,csub.name_ project_name,a.remark  "+ 
        			"from T_RES_WORK a inner join T_RES_WORK_AUTHOR b on a.id=b.WORK_ID "+
        			"left join tb_xzzzjg xx on a.dept_id=xx.id             "+
        			"left join t_code_subject csub on a.project_id=csub.code_ "+
        			"where substr(a.PRESS_TIME,0,4) between '"+from+"' and '"+to+"'  and b.people_id ='"+tea_no+"' "+where_sql+" order by a.PRESS_TIME ";
        }
        return sql;
	}

}
