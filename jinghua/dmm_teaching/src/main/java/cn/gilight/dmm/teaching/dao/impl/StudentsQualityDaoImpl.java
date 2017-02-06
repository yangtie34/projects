package cn.gilight.dmm.teaching.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.teaching.dao.StudentsQualityDao;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;

@Repository("studentsQualityDao")
public class StudentsQualityDaoImpl implements StudentsQualityDao{

	@Resource
	private BaseDao baseDao;
	
	public static final String FROMSQL = "select div.id as id,div.name_ as name,div.pid,div.level_ as cc from t_code_admini_div div where ";
	//查询本科生各项数据
	@Override
	public Map<String, Object> queryStudents(String year) {
		String sql1="SELECT COUNT(*)SL,NVL(TRUNC(AVG(T.SCORE),1),0)PJF,NVL(MAX(T.SCORE),0)ZGF,NVL(MIN(T.SCORE),0)ZDF"
                +" FROM T_STU_ENCRUIT T WHERE T.ENROLL_BATCH_ID IN ('100','101','102','103') AND T.YEAR='"+year+"'";
		String sql2="SELECT COUNT(*)SL,NVL(TRUNC(AVG(T.SCORE),1),0)PJF,NVL(MAX(T.SCORE),0)ZGF,NVL(MIN(T.SCORE),0)ZDF"
                +" FROM T_STU_ENCRUIT T WHERE T.ENROLL_BATCH_ID IN ('100','101','102','103') AND T.YEAR='"+year+"'-1";
		Map<String,Object> map1 = baseDao.queryMapInLowerKey(sql1);
		Map<String,Object> map2 = baseDao.queryMapInLowerKey(sql2);
		Map<String,Object> map = new HashMap<String, Object>();
		for (int i = 0; i < map1.size() && i<map2.size(); i++) {
			int a = MapUtils.getIntValue(map1, "sl") - MapUtils.getIntValue(map2, "sl");
			int b = MapUtils.getIntValue(map1, "pjf") - MapUtils.getIntValue(map2, "pjf");
			int c = MapUtils.getIntValue(map1, "zgf") - MapUtils.getIntValue(map2, "zgf");
			int d = MapUtils.getIntValue(map1, "zdf") - MapUtils.getIntValue(map2, "zdf");
			map.put("rsc", a);
			map.put("pjfc", b);
			map.put("zgfc", c);
			map.put("zdfc", d);
		}
		Map<String,Object> map3 = new HashMap<String, Object>();
		map3.put("lasYear",map);
		map3.put("nowYear",map1);
		return map3;
	}
	//查询数据中所有科类
	@Override
	public List<Map<String,Object>> queryAllSub(String year){
		String sql1="SELECT ROWNUM RN ,TT.* FROM (SELECT DISTINCT TA.SUBJECT_KIND_ID SUBID,TB.NAME_ SUBMC FROM T_STU_ENCRUIT TA LEFT JOIN "
				+" T_CODE_SUBJECT_KIND TB ON TA.SUBJECT_KIND_ID=TB.CODE_ WHERE TA.YEAR='"+year+"')TT";
		List<Map<String,Object>> list1 = baseDao.queryListInLowerKey(sql1);
		return list1;
	}
	//选择某科类时,查询科类对应数据 sub:科类ID
	@Override
	public List<Map<String, Object>> queryStudentsSub(String year,List<String> sub) {
		String subject = "";//用来存放前台传回的sub参数
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		if(sub.size()!=0){//判断传回的sub是否为空
			for (int i = 0; i < sub.size(); i++) {//循环取出sub参数
				subject = sub.get(i);
				if(subject !=""){//判断subject为空时，取下一个sub参数，不为空时执行查询。防止传回空字符串
					String sql="SELECT A.MC,A.SL,A.PJS,A.ZDS,A.ZXS,NVL((A.SL-B.SL),0)SLC,NVL((A.PJS-B.PJS),0)PJSC,"
					   +" NVL((A.ZDS-B.ZDS),0)ZDSC,NVL((A.ZXS-B.ZXS),0)ZXSC FROM("
		               +" SELECT TB.NAME_ MC,TB.CODE_ ID_,COUNT(TB.NAME_)SL,TRUNC(AVG(TA.SCORE),1)PJS,MAX(TA.SCORE)ZDS,MIN(TA.SCORE)ZXS "
		               +" FROM T_STU_ENCRUIT TA LEFT JOIN T_CODE_SUBJECT_KIND TB ON TA.SUBJECT_KIND_ID=TB.CODE_ WHERE TB.CODE_ IN '"+subject+"' AND "
		               +" TA.YEAR='"+year+"' GROUP BY TB.NAME_,TB.CODE_)A LEFT JOIN(SELECT TB.NAME_ MC,TB.CODE_ ID_,COUNT(TB.NAME_)SL,"
		               +" TRUNC(AVG(TA.SCORE),1)PJS,MAX(TA.SCORE)ZDS,MIN(TA.SCORE)ZXS FROM T_STU_ENCRUIT TA LEFT JOIN T_CODE_SUBJECT_KIND" 
		               +" TB ON TA.SUBJECT_KIND_ID=TB.CODE_ WHERE TB.CODE_ IN '"+subject+"' AND TA.YEAR='"+year+"'-1 "
		               +" GROUP BY TB.NAME_,TB.CODE_)B ON A.ID_=B.ID_";
					Map<String, Object> map1 = baseDao.queryMapInLowerKey(sql);
					if(map1 == null){
						String sql2 = "select t.code_ id,t.name_ mc from t_code_subject_kind t where t.code_='"+subject+"'";
						map1 = baseDao.queryMapInLowerKey(sql2);
						map1.put("sl", "--");
						map1.put("pjs", "--");
						map1.put("zds", "--");
						map1.put("zxs", "--");
						map1.put("slc", "--");
						map1.put("pjsc", "--");
						map1.put("zdsc", "--");
						map1.put("zxsc", "--");
					}
					list.add(map1);//每执行一次循环查出的结果放入list中
				}
			}
		}
		//先判断是否有数据,再执行以下
		if(list!=null){
			for(Map<String,Object> m : list){
				boolean slcFlag = false;
				boolean pjscFlag = false;
				boolean zdscFlag = false;
				boolean zxscFlag = false;
				if(!("--").equals(MapUtils.getString(m, "slc"))){
					if(MapUtils.getInteger(m, "slc") >= 0){
						slcFlag = true;
					};
				}
				if(!("--").equals(MapUtils.getString(m, "pjsc"))){
					if(MapUtils.getFloat(m, "pjsc") >= 0 ){
						pjscFlag = true;
					};
				}
				if(!("--").equals(MapUtils.getString(m, "zdsc"))){
					if(MapUtils.getFloat(m, "zdsc") >= 0 ){
						zdscFlag = true;
					};				
				}
				if(!("--").equals(MapUtils.getString(m, "zxsc"))){
					if(MapUtils.getFloat(m, "zxsc") >= 0 ){
						zxscFlag = true;
					}
				}
				m.put("slcFlag", slcFlag);
				m.put("pjscFlag", pjscFlag);
				m.put("zdscFlag", zdscFlag);
				m.put("zxscFlag", zxscFlag);
			}
		}
		return list;
	}
	//查询各省分数线
	@Override
	public Map<String,Object> queryScoreLineByPro(Page page,String flag,String year,String majorId){
		String str = " order by t.enroll_score desc";
		if("lineup".equals(flag)){//根据各省分数排序
			str = " order by t.enroll_score asc";
		}else if("linedown".equals(flag)){
			str = " order by t.enroll_score desc";
		}
		String sql1 = "select ts.name_ zymc,t.province_id,tt.name_,t.enroll_score from t_stu_encruit_plan t"
                  +" left join t_code_dept_teach ts on t.major_id=ts.code_ "
                  +" left join t_code_admini_div tt on tt.code_=t.province_id"
                  +" where t.year='"+year+"' and t.major_id='"+majorId+"'"+str;
		Map<String, Object> map = baseDao.createPageQueryInLowerKey(sql1, page);
		return map;
	}
	//查询超出分数线20分的专业 及其各项数据
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryStudentsScore(Page page,String year,String point,String flag) {
		//判断排序标志
		String str1 = " order by ta.rs desc";
		if("rsup".equals(flag)){//根据人数排序
			str1 = " order by ta.rs asc";
		}else if("rsdown".equals(flag)){
			str1 = " order by ta.rs desc";
		}else if("avgup".equals(flag)){//根据平均分排序
			str1 = " order by ta.pjf asc";
		}else if("avgdown".equals(flag)){
			str1 = " order by ta.pjf desc";
		}else if("maxup".equals(flag)){//根据最高分排序
			str1 = " order by ta.zgf asc";
		}else if("maxdown".equals(flag)){
			str1 = " order by ta.zgf desc";
		}else if("minup".equals(flag)){//根据最低分排序
			str1 = " order by ta.zdf asc";
		}else if("mindown".equals(flag)){
			str1 = " order by ta.zdf desc";
	    }else if("moreup".equals(flag)){//根据高出分数线分数排序
			str1 = " order by tb.fc asc";
		}else if("moredown".equals(flag)){
			str1 = " order by tb.fc desc";
		};
		//根据分差计算今年与去年相应条件下专业数量
		String sql1 =" select count(*) jnsl from (select distinct ts.major_id from "
                   +" t_stu_encruit_plan ts where (ts.enroll_score - ts.plan_score) >='"+point+"'"
                   +" and ts.year = '"+year+"')";
		String sql2 =" select count(*) qnsl from (select distinct ts.major_id from "
                   +" t_stu_encruit_plan ts where (ts.enroll_score - ts.plan_score) >='"+point+"'"
                   +" and ts.year ='"+year+"'-1 )";
		Map<String,Object> map1 = baseDao.queryMapInLowerKey(sql1);
		Map<String,Object> map2 = baseDao.queryMapInLowerKey(sql2);
		int def = MapUtils.getIntValue(map1, "jnsl") - MapUtils.getIntValue(map2, "qnsl");
		map1.put("slc", def);
		map1.put("fs", point);
		/**
		 * 各专业详细数据
		 */
		//今年超出分数线'point'分的专业
		String sql4 = "(select distinct ts.major_id mid, ts.year, ts.enroll_score from t_stu_encruit_plan ts"
                   +" where (ts.enroll_score - ts.plan_score) >= '"+point+"' and ts.year = '"+year+"')";
		//查询去年的平均分
		String sql5 = "select ba.mid, nvl(trunc(avg(bb.score), 1),0) pjf from"+sql4+"ba left join t_stu_encruit bb"
                   +" on ba.mid = bb.major_id where bb.year = '"+year+"'-1 group by ba.mid";
		//高出分数线最多(分)
		String sql6 = "select ac.mid, ac.year, max(ac.fc)fc from (select aa.mid,aa.year,aa.enroll_score,(max(ab.score) - aa.enroll_score) fc"
                   +" from"+sql4+" aa left join t_stu_encruit ab on aa.mid = ab.major_id group by aa.mid, aa.year, aa.enroll_score) ac"
                   +" group by ac.mid, ac.year";
		//基本值
		String sql7 = "select b.name_,a.mid,count(c.examinee_no) rs,nvl(trunc(avg(c.score), 1),0) pjf,max(c.score) zgf,min(c.score) zdf"
                   +" from "+sql4+"a left join t_code_dept_teach b on a.mid = b.code_ left join t_stu_encruit c on a.mid = c.major_id"
                   +" left join t_stu_encruit_plan d on a.mid = d.major_id and c.year = d.year group by b.name_, a.mid";
		//综合查询
		String sql3 = "select * from(select rownum rn,td.* from("
				+ "select ta.name_,ta.mid,ta.rs,ta.pjf,ta.zgf,ta.zdf,tb.fc,(ta.pjf-tc.pjf)pjfc from ("+sql7+")ta "
				+" left join("+sql6+")tb on ta.mid=tb.mid"
				+" left join("+sql5+")tc on ta.mid=tc.mid"+str1+")td)te ";
		Map<String,Object> map3 = baseDao.createPageQueryInLowerKey(sql3, page);
		List<Map<String, Object >> list = (List<Map<String, Object>>) map3.get("rows");
		String a = "";
		for (Map<String, Object> it : list) {
			a = MapUtils.getString(it, "pjfc");
			if(a!=""){
				
			}else{
				it.put("pjfc", "--");
			}
		}
		map3.put("sl", map1);
		return map3;
	}
	//调剂率
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryStudentsAdjust(Page page,String year,String flag) {
		String str = " order by TT.ZRS desc";
		if("rsup".equals(flag)){
			str = " ORDER BY TT.ZRS ASC";
		}else if("rsdown".equals(flag)){
			str = " ORDER BY TT.ZRS DESC";
		}else if("adjup".equals(flag)){
			str = " ORDER BY TT.TJL ASC";
		}else if("adjdown".equals(flag)){
			str = " ORDER BY TT.TJL DESC";
		}else if("lastup".equals(flag)){
			str = " ORDER BY TJLC ASC";
		}else if("lastdown".equals(flag)){
			str = " ORDER BY TJLC DESC";
		}
		//各专业调剂情况
		String sql = "";
		//当前选择的年
		String sql1 = "SELECT TA.MAJOR_ID,TB.ZRS,"
                +" TRUNC(TA.TJRS/TB.ZRS,3)TJL FROM(SELECT T.MAJOR_ID,COUNT(*)TJRS FROM T_STU_ENCRUIT T WHERE "
                +" T.IS_ADJUST='1' AND T.YEAR='"+year+"' GROUP BY T.MAJOR_ID)TA LEFT JOIN(SELECT T.MAJOR_ID, COUNT(*)ZRS"
                +" FROM T_STU_ENCRUIT T WHERE T.YEAR='"+year+"' GROUP BY T.MAJOR_ID)TB ON TA.MAJOR_ID=TB.MAJOR_ID";
		//当前选择年的上一年
		String sql2 = "SELECT TA.MAJOR_ID,TB.ZRS,TRUNC(TA.TJRS/TB.ZRS,3)TJL FROM(SELECT T.MAJOR_ID,COUNT(*)TJRS"
                +" FROM T_STU_ENCRUIT T WHERE T.IS_ADJUST='1' AND T.YEAR='"+year+"'-1 GROUP BY T.MAJOR_ID)TA LEFT JOIN"
                +" (SELECT T.MAJOR_ID,COUNT(*)ZRS FROM T_STU_ENCRUIT T WHERE T.YEAR='"+year+"'-1 GROUP BY T.MAJOR_ID)TB "
                +" ON TA.MAJOR_ID=TB.MAJOR_ID";
			sql = "SELECT * FROM(SELECT ROWNUM RN,TA.* FROM("
			    +" SELECT CD.NAME_,TT.ZRS,TT.TJL,(TT.TJL-TS.TJL)TJLC FROM("+sql1+")TT "
                +" LEFT JOIN("+sql2+")TS ON TS.MAJOR_ID=TT.MAJOR_ID LEFT JOIN T_CODE_DEPT_TEACH CD "
                +" ON CD.CODE_=TT.MAJOR_ID "+str+")TA)TB";
		Map<String, Object> map1 = baseDao.createPageQueryInLowerKey(sql, page);
		List<Map<String, Object >> list = new ArrayList<Map<String,Object>>();
		list = (List<Map<String, Object>>) map1.get("rows");
		String a = "";
		float tjl = 0,tjlc = 0;
		for (Map<String, Object> it : list) {
			tjl = MapUtils.getFloatValue(it, "tjl");
			it.put("tjl", Math.round(tjl*10000)/100.0 + "%");
			a = MapUtils.getString(it, "tjlc");
			if(a!=""){//判断当前年与之前年差值是否为空
				tjlc = MapUtils.getFloatValue(it, "tjlc");
				it.put("tjlc", Math.round(tjlc*10000)/100.0 + "%");
			}else{
				it.put("tjlc", "--");
			}
		}
		map1.put("rows", list);
		//表头填充的数据
		String str1 = "SELECT trunc(AVG(A.TJL),3)PJTJL FROM ("+sql+") A";
		String str2 = sql + " ORDER BY TB.TJL DESC";
		List<Map<String, Object>> list2 = baseDao.queryListInLowerKey(str2);
		Map<String, Object> map2 = baseDao.queryMapInLowerKey(str1);
		if(list2.size()!=0){
			float pjtjl = MapUtils.getFloatValue(map2, "pjtjl");
			float zgtjl = MapUtils.getFloatValue(list2.get(0), "tjl");
			map2.put("zymc", MapUtils.getString(list2.get(0), "name_"));
			map2.put("pjtjl", Math.round(pjtjl*10000)/100.0+"%");
			map2.put("zgtjl", Math.round(zgtjl*10000)/100.0+"%");
		}else{
			map2.put("zymc", "--");
			map2.put("pjtjl", "--");
			map2.put("zgtjl", "--");
		}
		map1.put("sl", map2);
		return map1;
	}
	//自主招生录取率
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryStudentsEnroll(Page page,String year,String flag) {
		String str = " order by a.zyrs desc";
		if("rsup".equals(flag)){
			str = " order by a.zyrs asc";
		}else if("rsdown".equals(flag)){
			str = " order by a.zyrs desc";
		}else if("lqlup".equals(flag)){
			str = " order by a.lql asc";
		}else if("lqldown".equals(flag)){
			str = " order by a.lql desc";
		}else if("lastup".equals(flag)){
			str = " order by lqlc asc";
		}else if("lastdown".equals(flag)){
			str = " order by lqlc desc";
		}
		//各专业自主招生录取详情,该专业当前年前一年的录取人数
		String str1 = "(select t.major_id, count(t.major_id) zrs from t_stu_encruit t where t.enroll_category_id = '04' and t.year = '"+year+"'-1 group by t.major_id) tt";
		//该专业当前年前一年的总人数
		String str2 = "(select t.major_id, count(t.major_id) lqrs from t_stu_encruit t where t.enroll_category_id = '04' and t.is_retreat_archive = '0'"
				+" and t.year = '"+year+"'-1 group by t.major_id) ts";
		String str3 = "select tt.major_id zyid,trunc(ts.lqrs / tt.zrs, 3) lql from "+str1+" left join "+str2+" on ts.major_id = tt.major_id";
		//该专业当前年总人数
		String str4 = "(select t.major_id,count(t.major_id) zrs from t_stu_encruit t where t.enroll_category_id = '04' and t.year = '"+year+"' group by t.major_id) ta";
		//该专业当前年录取人数
		String str5 = "(select t.major_id, count(t.major_id) lqrs from t_stu_encruit t where t.enroll_category_id = '04' and t.is_retreat_archive = '0'"
				+" and t.year = '"+year+"' group by t.major_id) tb";
		//该专业当前年录取人数及录取率
		String str6 = "select ta.major_id,tc.name_ zymc,count(td.examinee_no) zyrs,trunc(tb.lqrs / ta.zrs, 3) lql from "+str4
				+" left join "+str5+" on ta.major_id = tb.major_id  left join t_code_dept_teach tc on ta.major_id = tc.code_ left join t_stu_encruit "
				+" td on td.major_id = ta.major_id where td.year = '"+year+"' group by ta.major_id, tc.name_, trunc(tb.lqrs / ta.zrs, 3)";
		String sql = "select * from(select rownum rn,ta.* from(select a.zymc,a.zyrs,a.lql,(a.lql - b.lql) as lqlc from "
					+ "("+str6+") a left join ("+str3+") b on a.major_id = b.zyid "+str+")ta)tb";
		Map<String,Object> map1 = baseDao.createPageQueryInLowerKey(sql, page);
		List<Map<String, Object >> list = (List<Map<String, Object>>) map1.get("rows");
		String a = "";
		float lql =0, lqlc = 0;
		for (Map<String, Object> it : list) {
			lql = MapUtils.getFloatValue(it, "lql");
			it.put("lql", Math.round(lql*10000)/100.0 + "%");
			a = MapUtils.getString(it, "lqlc");
			if(a!=""){//判断当前年与之前年差值是否为空
				lqlc = MapUtils.getFloatValue(it, "lqlc");
				it.put("lqlc", Math.round(lqlc*10000)/100.0 + "%");
			}else{
				it.put("lqlc", "--");
			}
		}
		map1.put("rows", list);
		//表头数据填充
		String sql2 = "SELECT TRUNC(AVG(DD.LQL),3)PJLQL FROM("+sql+")DD";
		Map<String, Object> map2 = baseDao.queryMapInLowerKey(sql2);
		String sql3 = sql +" order by tb.lql asc";
		List<Map<String, Object>> list2 = baseDao.queryListInLowerKey(sql3);
		if(list2.size()!=0){
			float pjlql = MapUtils.getFloatValue(map2, "pjlql");
			map2.put("pjlql", Math.round(pjlql*10000)/100.0+"%");
			map2.put("zymc",  MapUtils.getString(list2.get(0), "zymc"));
			float zdlql = MapUtils.getFloatValue(list2.get(0), "lql");
			map2.put("zdlql", Math.round(zdlql*10000)/100.0+"%");
		}else{
			map2.put("pjlql", "--");
			map2.put("zymc", "--");
			map2.put("zdlql", "--");
		}
		map1.put("sl", map2);
		return map1;
	}
	//新生报到率和未报到人数
	@Override
	public Map<String, Object> queryStudentsNotReport(String year) {
		String sql1="SELECT TRUNC((TS.ZRS-TT.RS)/TS.ZRS,3)BDL,TT.RS FROM ( SELECT TA.YEAR,COUNT(*)RS FROM "
			   +" T_STU_ENCRUIT TA WHERE TA.IS_REPORT='0'AND TA.YEAR='"+year+"' GROUP BY TA.YEAR)TT LEFT JOIN ("
               +" SELECT TB.YEAR,COUNT(*)ZRS FROM T_STU_ENCRUIT TB WHERE TB.YEAR='"+year+"' GROUP BY TB.YEAR)TS "
               +" ON TS.YEAR=TT.YEAR";
		Map<String, Object> map1 = baseDao.queryMapInLowerKey(sql1);
		if(map1!=null){
			float bdl = MapUtils.getFloatValue(map1, "bdl");
			map1.put("bdl", Math.round(bdl*100)+"%");
			return map1;
		}else{
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("bdl", "--");
			map2.put("rs", "--");
			return map2;
		}
	}
	//未报到原因分布
	@Override
	public Map<String, Object> queryStudentsNotReportReason(String year) {
		String sql="select rownum as rn,tt.* from("
			+" select ta.not_report_reason_id code,tb.name_ name,count(*) value from t_stu_encruit ta left join "
			+" t_code_not_report_reason tb on ta.not_report_reason_id=tb.code_ where ta.is_report='0' "
			+" and ta.year='"+year+"' group by ta.not_report_reason_id,tb.name_)tt";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		Map<String,Object> map = DevUtils.MAP();
		map.put("rea", list);
		return map;
	}
	//学生未报到地理分布
	@Override
	public List<Map<String, Object>> queryStudentsNotReportByLocal(String year,String xzqh,Boolean updown) {
		Map<String, String> map1 = queryCode(xzqh,updown);//查询出来应该截取的学生生源地id
		String pid = map1.get("pid");
		String str1 = map1.get("str1");
		String str2 = map1.get("str2");
		String sql = "select ta.id, ta.name, ta.pid, ta.cc, count(tb.examinee_no) value from ("+str2+") ta left join "
			    +"(select * from t_stu_encruit ta where ta.year = '"+year+"' and ta.is_report = '0') "
			    +" tb on ta.id = "+str1+" group by ta.id, ta.name, ta.pid, ta.cc";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {//补全各地区的信息
			Map<String, Object> map2 = list.get(i);
			String code = MapUtils.getString(map2, "id");
			if (list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					Map<String, Object> map3 = list.get(j);
					String code1 = MapUtils.getString(map3, "id");
					if (code != null && code1 != null && code1.equals(code)) {
						result.add(map3);
					}
				}
			}else{
				map2.put("value", 0);
				result.add(map2);
			}
			if (result.size() < i + 1) {
				map2.put("value", 0);
				result.add(map2);
			}
		}
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("maptype",MapUtils.getString(baseDao.queryMapInLowerKey(queryNowDiv(pid)), "name"));//获取当前展示的地图的类型以及ID放到list的第一位
		map3.put("id", 0);
		result.add(0,map3);
		return result;
	}
	/** 地图下钻代码 */
	/**
	 * 获取下层地区节点信息
	 */
	private String queryNextDiv(String Id) {
		String sql = FROMSQL + " div.pid ='" + Id + "'";
		return sql;
	}
	/**
	 * 获取当前节点信息
	 */
	private String queryNowDiv(String Id) {
		String sql = FROMSQL + " div.id ='" + Id + "'";
		return sql;
	}
	/**
	 * 获取上层地区节点信息
	 */
	private String queryLastDiv(String Id) {
		String sql1 = queryNowDiv(Id);
		Map<String, Object> map = baseDao.queryMapInLowerKey(sql1);
		String code = map == null ? "0" : MapUtils.getString(map, "pid");
		String sql = FROMSQL + " div.pid ='0'";
		if (code.equals("0")) {
		} else {
			String sql2 = FROMSQL + " div.id = " + code;
			Map<String, Object> map1 = baseDao.queryMapInLowerKey(sql2);
			String dm = MapUtils.getString(map1, "pid");
			sql = FROMSQL + " div.pid = " + dm;
		}
		return sql;
	}
	/**
	 * 获取要展示的节点层次以及生源地id以及节点的信息查询sql
	 */
	private Map<String, String> queryCode(String Id,Boolean updown) {
		String str2 = queryNextDiv(Id);
		if (!updown || baseDao.queryListInLowerKey(queryNextDiv(Id)).size() == 0) {
			str2 = queryLastDiv(Id);
		}
		List<Map<String, Object>> From = baseDao.queryListInLowerKey(str2);
		String code = From.size() == 0 ? "0" : MapUtils.getString(From.get(0), "cc");
		String pid = From.size() == 0 ? "0" : MapUtils.getString(From.get(0), "pid");
		Map<String, String> map = new HashMap<String, String>();
		String str1 = "tb.sf_id";
		if (code.equals("1")) {
			str1 = " tb.sf_id";
		} else if (code.equals("2")) {
			str1 = " tb.s_id";
		}else if(code.equals("3")){
			str1 = " tb.x_id";
		}
		map.put("str1", str1);
		map.put("str2", str2);
		map.put("pid", pid);
		return map;
	}
}
