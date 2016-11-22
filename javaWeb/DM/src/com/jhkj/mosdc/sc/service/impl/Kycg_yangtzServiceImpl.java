package com.jhkj.mosdc.sc.service.impl;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;
import com.jhkj.mosdc.framework.util.ExportUtil;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.Utils4Service;
import com.jhkj.mosdc.sc.service.Kycg_yangtzService;

public class Kycg_yangtzServiceImpl extends BaseServiceImpl implements
		Kycg_yangtzService {
	String zzTable = "T_RES_WORK";
	String kycgTable = "T_RES_OUTCOME_AWARD";
	String cgjdTable = "T_RES_OUTCOME_APPRAISAL";
	String zlTable = "T_RES_PATENT";

	String zzdept_id = "dept_id";
	String kycgdept_id = "dept_id";
	String cgjddept_id = "dept_id";
	String zldept_id = "INVENTOR_DEPT_ID";

	String zzTime = "press_time";
	String kycgTime = "AWARD_TIME";
	String cgjdTime = "TIME_";
	String zlTime = "ACCREDIT_TIME";
	public String formatkxSql(String json){
		JSONObject params = JSONObject.fromObject(json);
		String kx = params.getString("kx");
		
		
		String casekx="";	
		switch(kx){
		case "01":
			casekx=" and work.PROJECT_ID like '%110-180%' ";
			break;
		case "10":
			casekx=" and work.PROJECT_ID  like '%710-910%' ";
			break;
		case "11":
			casekx=" and (work.PROJECT_ID like '%110-180%'  or work.PROJECT_ID  like '%710-910%') ";
			break;
		}		
		System.out.println(casekx);
		return casekx;
	}
	@Override
	public String cggk1(String json) {
		String kx=formatkxSql(json);
		JSONObject params = JSONObject.fromObject(json);
		System.out.println(params);
		String from = params.getString("from");
		String to = params.getString("to");
		String qxm = params.getString("qxm");
		String sql = "select t.field field,sum(t.counts) counts from("
				+ " select jxz.qxm, count(*) counts,'科研获奖成果' field from T_RES_OUTCOME_AWARD work"
				+ " left join tb_xzzzjg jxz on jxz.dm=work.DEPT_ID"
				+ " where work.AWARD_TIME between '"
				+ from
				+ "' and '"
				+ to
				+ "'"
				+kx
				+ " group by jxz.qxm"
				+ " union all "
				+ " select jxz.qxm,count(*) counts,'著作/教材' field from T_RES_WORK work"
				+ " left join tb_xzzzjg jxz on jxz.dm=work.dept_id"
				+ " where work.press_time between '"
				+ from
				+ "' and '"
				+ to
				+ "'"
					+kx
				+ " group by jxz.qxm"
				+ " union all "
				+ " select jxz.qxm, count(*) counts,'成果鉴定' field from T_RES_OUTCOME_APPRAISAL work"
				+ " left join tb_xzzzjg jxz on jxz.dm=work.DEPT_ID"
				+ " where work.TIME_ between '"
				+ from
				+ "' and '"
				+ to
				+ "'"
					+kx
				+ " group by jxz.qxm"
				+ " union all "
				+ " select jxz.qxm,count(*) counts,'专利' field from T_RES_PATENT work"
				+ " left join tb_xzzzjg jxz on jxz.dm=work.INVENTOR_DEPT_ID"
				+ " where work.ACCREDIT_TIME between '"
				+ from
				+ "' and '"
				+ to
				+ "'"	+kx
				+ " group by jxz.qxm"
				+ " )t"
				+ " where qxm like'"
				+ qxm
				+ "%'" + " group  by t.field";
		String a=Struts2Utils.list2json(baseDao.queryListMapInLowerKeyBySql(sql));
System.out.println(a);
		return a;
	}

	@Override
	public String cggk2(String json) {
		String kx=formatkxSql(json);
		JSONObject params = JSONObject.fromObject(json);
		String from = params.getString("from");
		String to = params.getString("to");
		String qxm = params.getString("qxm");
		String sql = " select t.field field,sum(counts) counts,name from("
				+
				// "--著作，部门，数量"+
				" select jxz.dm,nvl(jxz.mc,'未维护') field,'著作/教材'  name,count(work.id)counts "
				+ " from T_RES_WORK work"
				+ " left join tb_xzzzjg jxz on jxz.dm=work.dept_id"
				+ " where work.press_time between '"
				+ from
				+ "' and '"
				+ to
				+ "' and jxz.qxm like '"
				+ qxm
				+ "%'"+kx
				+ " group by jxz.mc,jxz.dm "
				+ " union all"
				+
				// "--专利"+
				" select jxz.dm,nvl(jxz.mc,'未维护') field,'专利'  name,count(work.id)counts "
				+ " from T_RES_PATENT work"
				+ " left join tb_xzzzjg jxz on jxz.dm=work.INVENTOR_DEPT_ID"
				+ " where work.ACCREDIT_TIME between '"
				+ from
				+ "' and '"
				+ to
				+ "' and jxz.qxm like '"
				+ qxm
				+ "%'"+kx
				+ " group by jxz.mc,jxz.dm "
				+ " union all"
				+
				// "--科研获奖成果"+
				" select jxz.dm,nvl(jxz.mc,'未维护') field,'科研获奖成果'  name,count(work.id)counts "
				+ " from T_RES_OUTCOME_AWARD work"
				+ " left join tb_xzzzjg jxz on jxz.dm=work.DEPT_ID"
				+ " where work.AWARD_TIME between '"
				+ from
				+ "' and '"
				+ to
				+ "' and jxz.qxm like '"
				+ qxm
				+ "%'"+kx
				+ " group by jxz.mc,jxz.dm "
				+ " union all"
				+
				// "--成果鉴定"+
				" select jxz.dm,nvl(jxz.mc,'未维护') field,'成果鉴定'  name,count(work.id)counts "
				+ " from T_RES_OUTCOME_APPRAISAL work"
				+ " left join tb_xzzzjg jxz on jxz.dm=work.DEPT_ID"
				+ " where work.TIME_ between '"
				+ from
				+ "' and '"
				+ to
				+ "' and jxz.qxm like '"
				+ qxm
				+ "%'"+kx
				+ "group by jxz.mc,jxz.dm "
				+ ") t"
				+ " group by name,field"
				+ " order by t.name";
		String a = Struts2Utils.list2json(baseDao
				.queryListMapInLowerKeyBySql(sql));
		System.out.println(a);
		return a;
	}

	@Override
	public String cgqs(String json) {
		String kx=formatkxSql(json);
		JSONObject params = JSONObject.fromObject(json);
		String from = params.getString("from");
		String to = params.getString("to");
		String qxm = params.getString("qxm");
		String sql = "select substr(field,0,4) field,name,sum(counts) counts from( "
				+
				// "--著作，部门，数量 "+
				"select work.press_time field,'著作/教材' name,1 counts  "
				+ "from T_RES_WORK work "
				+ "left join tb_xzzzjg jxz on jxz.dm=work.dept_id "
				+ "where work.press_time between '"
				+ from
				+ "' and '"
				+ to
				+ "' and jxz.qxm like '"
				+ qxm
				+ "%' "+kx
				+ "union all "
				+
				// "--专利 "+
				"select work.ACCREDIT_TIME field,'专利' name,1 counts  "
				+ "from T_RES_PATENT work "
				+ "left join tb_xzzzjg jxz on jxz.dm=work.INVENTOR_DEPT_ID "
				+ "where work.ACCREDIT_TIME between '"
				+ from
				+ "' and '"
				+ to
				+ "' and jxz.qxm like '"
				+ qxm
				+ "%' "+kx
				+ "union all "
				+
				// "--科研获奖成果 "+
				"select work.AWARD_TIME field,'科研获奖成果' name,1 counts  "
				+ "from T_RES_OUTCOME_AWARD work "
				+ "left join tb_xzzzjg jxz on jxz.dm=work.DEPT_ID "
				+ "where work.AWARD_TIME between '"
				+ from
				+ "' and '"
				+ to
				+ "' and jxz.qxm like '"
				+ qxm
				+ "%' "+kx
				+ "union all "
				+
				// "--成果鉴定 "+
				"select work.TIME_ field,'成果鉴定' name,1 counts  "
				+ "from T_RES_OUTCOME_APPRAISAL work "
				+ "left join tb_xzzzjg jxz on jxz.dm=work.DEPT_ID "
				+ "where work.TIME_ between '"
				+ from
				+ "' and '"
				+ to
				+ "' and jxz.qxm like '"
				+ qxm
				+ "%' "+kx
				+ ") t "
				+ "group by name,substr(field,0,4) order by substr(field,0,4) ";
		return Struts2Utils.list2json(baseDao.queryListMapInLowerKeyBySql(sql));
	}

	@Override
	public String lbxz(String json) {
		String kx=formatkxSql(json);
		kx=kx.replace("work", "patent");
		JSONObject params = JSONObject.fromObject(json);
		String from = params.getString("from");
		String to = params.getString("to");
		String qxm = params.getString("qxm");
		String lbname = params.getString("lbname");

		String sql = "";
		switch (lbname) {
		case "专利":
			sql = "select code.name_ field,count(patent.id) counts from T_RES_PATENT patent "
					+ "left join t_code code on patent.patent_type_code=code.code_ "
					+ " left join tb_xzzzjg jxz on jxz.dm=patent.INVENTOR_DEPT_ID "
					+ "where code.code_type like '%RES_PATENT%' "
					+ "and patent.ACCREDIT_TIME between '"
					+ from
					+ "' and '"
					+ to
					+ "'"
					+ "and jxz.qxm like '"
					+ qxm
					+ "%' "+kx
					+ "group by code.name_";
			break;
		case "科研获奖成果":
			sql = "select '内' cc, "
					+ "code.name_ field,count(patent.id) counts from T_RES_OUTCOME_AWARD patent "
					+ "left join t_code code on patent.level_code=code.code_ "
					+ " left join tb_xzzzjg jxz on jxz.dm=patent.DEPT_ID "
					+ "where code.code_type like '%RES_AWARD_LEVEL_CODE%' " +
					// "and substr(patent.AWARD_TIME,0,4)='"+nf+"' "+
					"and patent.AWARD_TIME between '"
					+ from
					+ "' and '"
					+ to
					+ "'"
					+ "and jxz.qxm like '"
					+ qxm
					+ "%' "+kx
					+ "group by code.name_ "
					+ "union all "
					+ "select '外' cc, "
					+ " code.name_ field,count(patent.id) counts from T_RES_OUTCOME_AWARD patent "
					+ "left join t_code code on patent.rank_code=code.code_ "
					+ " left join tb_xzzzjg jxz on jxz.dm=patent.DEPT_ID "
					+ "where code.code_type like '%RES_AWARD_RANK_CODE%' "
					+
					// "and substr(patent.AWARD_TIME,0,4)='"+nf+"' "+
					"and patent.AWARD_TIME between '"
					+ from
					+ "' and '"
					+ to
					+ "'"
					+ "and jxz.qxm like '"
					+ qxm
					+ "%' "+kx
					+ "group by code.name_  " ;
			break;
		case "著作/教材":

			break;
		case "成果鉴定":
			sql = "select code.name_ field,count(patent.id) counts from T_RES_OUTCOME_APPRAISAL patent "
					+ "left join t_code code on patent.IDENTIFYLEVEL_CODE=code.code_ "
					+ " left join tb_xzzzjg jxz on jxz.dm=patent.DEPT_ID "
					+ "where code.code_type like '%IDENTIFYLEVEL_CODE%' "
					+ "and patent.TIME_ between '"
					+ from
					+ "' and '"
					+ to
					+ "'"
					+ "and jxz.qxm like '"
					+ qxm
					+ "%' "+kx
					+ "group by code.name_";
			break;
		}
		;
sql="select t.* from ("+sql+") t order by decode(substr(t.field,2,1),'一',1,'二',2,'三',3,'四',4,'五',5,'六',6,'七',7,'八',8,'九',9),t.field ";
		return Struts2Utils.list2json(baseDao.queryListMapInLowerKeyBySql(sql));
	}
	@Override
	public HSSFWorkbook xqxzexcel(String json) {
		return  xqxzlb(json);
		
	}
	@Override
	public String xqxz(String json) {
		  String a=xqxzlb(json).getSheetAt(0).getRow(0).getCell(0).getStringCellValue();
		 
		  return a;
	}
	public  HSSFWorkbook xqxzlb(String json) {
		String kx=formatkxSql(JSONObject.fromObject(json).getString("params"));kx=kx.replace("work", "patent");
		//<-->
		try {
			if(json.equals(new String(json.getBytes("ISO-8859-1"), "ISO-8859-1"))){
			json=  new String(json.getBytes("ISO-8859-1"),"UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        //System.out.println(getEncoding(serviceAndParams));
        System.out.println(json);
    	//<-->
		JSONObject json1 = JSONObject.fromObject(json);
		JSONObject params = JSONObject.fromObject(json1.get("params"));
		System.out.println(json1);
		JSONObject chaxun = JSONObject.fromObject(json1.get("chaxun"));
		// var chaxun={name:'',lx:'',xk:''};
		String name = chaxun.getString("name");// 名称
		String lx = chaxun.getString("lx");// 类型
		// String xk = chaxun.getString("xk");//学科

		String from = params.getString("from");
		String to = params.getString("to");
		String qxm = params.getString("qxm");
		String lbname = params.getString("lbname");
		String bm = params.getString("bm");
		String sql = "";
		// String field=null;
		System.out.println(lbname);
		String cx = "";
		switch (lbname) {
		case "专利":
			cx = " and patent.name_ like '%" + name
					+ "%' and code.name_ like '%" + lx + "%'"+kx;// and 查询 //专利类型
			// field="单位代码,发明人单位,发明人,专利名称,专利类型,专利编号,专利实施,专利号,申请日,授权日,证书编号,备注";
			sql = "select jxz.dm  l02,jxz.mc l03,patent.inventors l04,patent.name_  l05,code.name_ l06,patent.id l07,code1.name_ l08,patent.PATENT_NO l09, patent.ACCEPT_TIME l10,patent.ACCREDIT_TIME l11,patent.CERTIFICATE_NO l12,patent.REMARK l13 "
					+ "from T_RES_PATENT patent "
					+ "left join tb_xzzzjg jxz on jxz.dm=patent.INVENTOR_DEPT_ID  "
					+ "left join (select * from t_code where code_type like'%RES_PATENT_TYPE_CODE%') code on patent.patent_type_code=code.code_  "
					+ "left join (select * from t_code where code_type like'%RES_PATENT_STATE_CODE%') code1 on patent.PATENT_STATE_CODE=code1.code_  "
					+ "where patent.ACCREDIT_TIME between '"
					+ from
					+ "' and '"
					+ to + "'" + "and jxz.mc like '" + bm + "%'" + cx;
			break;
		case "科研获奖成果":
			cx = " and patent.name_ like '%" + name // 成果名称
					+ "%' and code.name_ like '%" + lx + "%'"+kx;// and 查询
																		// //授奖单位
			// field="单位代码,获奖单位,成果名称,成果编号,获奖名称,获奖等级,授奖单位,证书编号,评价等级,备注";
			sql = "select jxz.dm  l02,jxz.mc l03,patent.NAME_ l04,patent.ID  l05,patent.AWARD_NAME l06,code.name_ l07,patent.AWARD_DEPT l08,patent.CERTIFICATE_NO l09, code1.name_ l10 "
					+ "from T_RES_OUTCOME_AWARD patent "
					+ "left join tb_xzzzjg jxz on jxz.dm=patent.DEPT_ID  "
					+ "left join (select * from t_code where code_type like'%RES_AWARD_RANK_CODE%') code on patent.RANK_CODE=code.code_  "
					+ "left join (select * from t_code where code_type like'%RES_IDENTIFYLEVEL_CODE%') code1 on patent.LEVEL_CODE=code1.code_  "
					+ "where patent.AWARD_TIME between '"
					+ from
					+ "' and '"
					+ to + "'" + "and jxz.mc like '" + bm + "%'" + cx;
			break;
		case "著作/教材":
			cx = " and patent.TITLE_ like '%" + name // 成果名称
					+ "%' and patent.AUTHORS like '%" + lx + "%'"+kx;// and 查询
																	// //作者名称
			// field="单位代码,作者单位,作者姓名,著作名称,著作编号,出版单位,出版时间,著作字数,著作书号,备注";
			sql = "select jxz.dm  l02,jxz.mc l03,patent.AUTHORS l04,patent.TITLE_  l05,patent.ID l06,patent.PRESS_NAME l07,patent.PRESS_TIME l08,patent.NUMBER_ l09, patent.WORK_NO l10, patent.REMARK l11 "
					+ "from T_RES_WORK patent "
					+ "left join tb_xzzzjg jxz on jxz.dm=patent.DEPT_ID  "
					+ "where patent.PRESS_TIME between '"
					+ from
					+ "' and '"
					+ to + "'" + "and jxz.mc like '" + bm + "%'" + cx;
			break;
		case "成果鉴定":
			cx = " and patent.NAME_ like '%" + name // 成果名称
					+ "%' "+kx;// and patent.AUTHORS like '%"+lx+"%'";//and 查询
							// //作者名称
			// field="单位代码,完成人单位,完成人,成果名称,成果编号,鉴定单位,鉴定级别,鉴定形式,鉴定水平,鉴定证号,鉴定时间,我校名次,备注";
			sql = "select jxz.dm  l02,jxz.mc l03,patent.AUTHORS l04,patent.NAME_  l05,patent.ID l06,patent.APPRAISAL_DEPT l07,patent.ORDER_ l08,code.name_ l09, code1.name_ l10, patent.IDENTIFY_NO l11, patent.TIME_ l12, patent.ORDER_ l13, patent.REMARK l14 "
					+ "from T_RES_OUTCOME_APPRAISAL patent "
					+ "left join tb_xzzzjg jxz on jxz.dm=patent.DEPT_ID  "
					+ "left join (select * from t_code where code_type like'%IDENTIFYMODE_CODE%') code on patent.IDENTIFYMODE_CODE=code.code_  "
					+ "left join (select * from t_code where code_type like'%IDENTIFYLEVEL_CODE%') code1 on patent.IDENTIFYLEVEL_CODE=code1.code_  "
					+ "where patent.TIME_ between '"
					+ from
					+ "' and '"
					+ to
					+ "'" + "and jxz.mc like '" + bm + "%'" + cx;
			break;
		}
		;
		sql = "select t1.* from (" + sql + ") t1 ";
		// + "where rownum<20";
		////////
		HSSFWorkbook workbook = new HSSFWorkbook();
		if(!json1.containsKey("start")){
			String[] fields=json1.getString("fields").split(",");
			List<Map<String,Object>> xsList = baseDao.querySqlList(sql);
			
			
			 workbook = ExportUtil.getHSSFWorkbookByMap(fields, json1.getString("title"), xsList);
			return workbook;
		}else{
		/////////////
		/////////
		Map paramsMap = Utils4Service.packageParams(json);
		Map result = baseDao.queryTableContentBySQL(sql, paramsMap);
		Map backval = new LinkedHashMap();
		backval.put("success", true);
		backval.put("data", result.get("queryList"));
		backval.put("count", result.get("count"));
		String a=Struts2Utils.map2json(backval);
		workbook.createSheet().createRow(0).createCell((short)0).setCellValue(a); 
		//workbook.setSheetName(0, Struts2Utils.map2json(backval));
		  return workbook;
		}
		}
	

	@Override
	public String qsxz(String json) {
		String kx=formatkxSql(json);kx=kx.replace("work", "patent");
		String table = null;
		String dep = "dept_id";
		String time = null;
		String codetype = null;
		String tabletype = null;

		JSONObject params = JSONObject.fromObject(json);
		System.out.println(params);
		String from = params.getString("from");
		String to = params.getString("to");
		String qxm = params.getString("qxm");
		String lbname = params.getString("lbname");

		String fl = params.getString("fl");

		String sql = "";
		switch (lbname) {
		case "专利":
			dep = "INVENTOR_DEPT_ID";
			table = "T_RES_PATENT";
			time = "ACCREDIT_TIME";

			switch (fl) {
			case "分类":
				tabletype = "patent_type_code";
				codetype = "RES_PATENT_TYPE_CODE";
				break;
			case "状态":
				tabletype = "PATENT_STATE_CODE";
				codetype = "RES_PATENT_STATE_CODE";
				break;
			}
			;

			// RES_PATENT_TYPE_CODE
			// patent.patent_type_code
			break;
		case "科研获奖成果":
			table = "T_RES_OUTCOME_AWARD";
			time = "AWARD_TIME";
			tabletype = "RANK_CODE";
			codetype = tabletype;

			break;
		case "著作/教材":
			table = "T_RES_WORK";
			time = "press_time";
			tabletype = "PRESS_NAME";
			codetype = "";
			break;
		case "成果鉴定":
			time = "TIME_";
			table = "T_RES_OUTCOME_APPRAISAL";
			switch (fl) {
			case "级别":
				tabletype = "IDENTIFYLEVEL_CODE";
				codetype = tabletype;
				break;
			case "鉴定类型":
				tabletype = "IDENTIFYMODE_CODE";
				codetype = tabletype;
				break;
			case "领先水平":
				tabletype = "IDENTIFYLEVEL_CODE";
				codetype = tabletype;
				break;
			}
			;
			break;
		}
		;

		sql = "select nvl(substr(field,0,4),'未维护') field,name,sum(counts) counts from( "
				+ "select 1 counts,patent."
				+ time
				+ " field,code.name_ name from "
				+ table
				+ " patent  "
				+ " left join tb_xzzzjg jxz on jxz.dm=patent."
				+ dep
				+ " "
				+ "left join t_code code on patent."
				+ tabletype
				+ "=code.code_   "
				+ "where code_type like'%"
				+ codetype
				+ "%' "
				+ "and patent."
				+ time
				+ " between '"
				+ from
				+ "' and '"
				+ to
				+ "'"+kx
				+ "and jxz.qxm like '"
				+ qxm
				+ "%' "
				+ ") group by substr(field,0,4),name "
				+ "order by substr(field,0,4) ";
		String a = Struts2Utils.list2json(baseDao
				.queryListMapInLowerKeyBySql(sql));
		System.out.println(a);
		return a;
	}

}
