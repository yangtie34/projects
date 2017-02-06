package cn.gilight.product.net.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.code.Code;
import cn.gilight.product.net.dao.JobNetDao;

@Repository("jobNetDao")
public class JobNetDaoImpl implements JobNetDao {
	@Autowired
	private BaseDao baseDao;

	@Override
	public Map<String, Integer> updateNetStuMonth(String yearMonth) {
		Map<String, Integer> map = new HashMap<String, Integer>();

		String delSql = "delete tl_net_stu_month where year_month= to_date('"+yearMonth+"','yyyy-mm') ";
		int delNum = baseDao.getJdbcTemplate().update(delSql);
		map.put("delNum", delNum);

		String sql =" insert into tl_net_stu_month                                                        "+
				" select to_date('"+yearMonth+"','yyyy-mm') year_month,                                     "+
				" 		nvl(sum(cp.use_time),0) ON_TIME,                                              "+
				"       	nvl(sum(cp.use_flow),0) US_FLOW,                                          "+
				"         nvl(sum(cp.use_money),0) MONEY,                                             "+
				" 	    count(cp.id) IN_COUNTS   ,                                                    "+
				"        s.no_ NO_,                                                                   "+
				"        s.name_ NAME_,                                                               "+
				"         s.dept_id,                                                                  "+
				"        cd.name_ dept_name,                                                          "+
				"        s.major_id,                                                                  "+
				"        cdt.name_ major_name,                                                        "+
				" 	   	s.class_id,                                                                   "+
				"     	cl.name_ class_name,                                                          "+
				"     	s.sex_code,                                                                   "+
				"        tc.name_ sex_name,                                                           "+
				"        s.edu_id,                                                                    "+
				"        ce.name_ edu_name,                                                           "+
				" 		s.nation_code ,                                                        "+
				" 		tc2.name_ nation_name ,                                                       "+
				" 		cp.on_type ON_TYPE_CODE,                                                      "+
				"        ntcode.name_ ON_TYPE_NAME                                                    "+
				"    from t_net_record CP                                                             "+
				"   left join t_net_user c on CP.net_id = c.id                                        "+
				"   left join t_stu s on s.no_ = c.people_id                                          "+
				"  left join t_code_dept_teach cd on s.dept_id=cd.id                                  "+
				"  left join t_code_dept_teach cdt on s.major_id=cdt.id                               "+
				"  left join t_code_education ce on s.edu_id=ce.id                                    "+
				"  left join t_code tc on s.sex_code=tc.code_ and tc.code_type='SEX_CODE'             "+
				"  left join t_classes cl on s.class_id=cl.no_                                        "+
				"  left join t_code tc2 on tc2.code_ = s.nation_code  and tc2.code_type='NATION_CODE' "+
				"  left join t_code ntcode                                                            "+
				"     on cp.on_type = ntcode.code_                                                    "+
				"    and ntcode.code_type = 'NET_TYPE_CODE'                                           "+
				"  where  substr(cp.on_time, 0, 7)='"+yearMonth+"' and s.no_ is not null              "+
				"  group by                                                 						  "+
				"        s.no_,                                                                       "+
				"        s.name_,                                                                     "+
				"        s.sex_code,                                                                  "+
				"        tc.name_ ,                                                                   "+
				"        s.major_id,                                                                  "+
				"        cdt.name_ ,                                                                  "+
				"        s.dept_id,                                                                   "+
				"        cd.name_ ,                                                                   "+
				"      s.class_id,                                                                    "+
				"      cl.name_ ,                                                                     "+
				"        s.edu_id,                                                                    "+
				"        ce.name_ ,                                                                   "+
				"        s.nation_code,															      "+
				" 	 tc2.name_	,                                                                     "+
				" 	 cp.on_type,                                                                      "+
				"      ntcode.name_                                                                  ";
		int addNum = baseDao.getJdbcTemplate().update(sql);
		map.put("addNum", addNum);

		return map;
	}

	@Override
	public Map<String, Integer> updateNetTypeMonth(String yearMonth) {
		Map<String, Integer> map = new HashMap<String, Integer>();

		String delSql = "delete tl_net_type_month where year_month= to_date('"+yearMonth+"','yyyy-mm') ";
		int delNum = baseDao.getJdbcTemplate().update(delSql);
		map.put("delNum", delNum);

		String sql=" insert into tl_net_type_month                                          "+
				" select to_date('"+yearMonth+"', 'yyyy-mm') year_month,                       "+
				"        TH.HOUR_,                                                       "+
				"        count(case when substr(t.ON_time, 12, 2) = TH.HOUR_ then        "+
				"                 '1' else null end) ON_COUNTS,                          "+
				"        count(case when substr(t.off_time, 12, 2) = TH.HOUR_ then       "+
				"                 '1' else null end) OUT_COUNTS,                         "+
				"        nvl(count(distinct t.net_id), 0) IN_COUNTS,                     "+
				"        nvl(sum(case when substr(t.off_time, 12, 2) = TH.HOUR_ then     "+
				"                 t.use_time else  null end),0) use_time,                "+
				"        nvl(sum(case when substr(t.off_time, 12, 2) = TH.HOUR_ then     "+
				"             t.use_flow else null end),0) use_flow,                     "+
				"        nvl(sum(case when substr(t.off_time, 12, 2) = TH.HOUR_ then     "+
				"        t.use_money  else null end),0) use_money,                       "+
				"        s.dept_id,                                                      "+
				"        cd.name_ dept_name,                                             "+
				"        s.major_id,                                                     "+
				"        cdt.name_ major_name,                                           "+
				"        s.class_id,                                                     "+
				"        cl.name_ class_name,                                            "+
				"        s.sex_code,                                                     "+
				"        tc.name_ sex_name,                                              "+
				"        s.edu_id,                                                       "+
				"        ce.name_ edu_name,                                              "+
				"        s.nation_code,                                          "+
				"        tc2.name_ nation_name,                                          "+
				"        t.on_type ON_TYPE_CODE,                                         "+
				"        ntcode.name_ ON_TYPE_NAME                                       "+
				"   from t_net_record t                                                  "+
				"   left join t_net_user c                                               "+
				"     on t.net_id = c.id                                                 "+
				"   left join t_stu s                                                    "+
				"     on s.no_ = c.people_id                                             "+
				"   left join t_code_dept_teach cd                                       "+
				"     on s.dept_id = cd.id                                               "+
				"   left join t_code_dept_teach cdt                                      "+
				"     on s.major_id = cdt.id                                             "+
				"   left join t_code_education ce                                        "+
				"     on s.edu_id = ce.id                                                "+
				"   left join t_code tc                                                  "+
				"     on s.sex_code = tc.code_                                           "+
				"    and tc.code_type = 'SEX_CODE'                                       "+
				"   left join t_classes cl                                               "+
				"     on s.class_id = cl.no_                                             "+
				"   left join t_code tc2                                                 "+
				"     on tc2.code_ = s.nation_code                                       "+
				"    and tc2.code_type = 'NATION_CODE'                                   "+
				"   left join t_code ntcode                                              "+
				"     on t.on_type = ntcode.code_                                        "+
				"    and ntcode.code_type = 'NET_TYPE_CODE'                              "+
				"   LEFT JOIN T_HOUR TH                                                  "+
				"     ON ((substr(t.on_time, 12, 2) <= TH.HOUR_ AND                      "+
				"        substr(t.off_time, 12, 2) >= TH.HOUR_ AND                       "+
				"        substr(t.on_time, 9, 2) = substr(t.oFF_time, 9, 2)) OR          "+
				"        (((substr(t.on_time, 12, 2) <= TH.HOUR_ AND TH.HOUR_ <= 23) OR  "+
				"        (00 <= TH.HOUR_ AND TH.HOUR_ <= substr(t.off_time, 12, 2))) AND "+
				"        (substr(t.oFF_time, 9, 2) - substr(t.on_time, 9, 2)) = 1) OR    "+
				"        (00 <= TH.HOUR_ AND TH.HOUR_ >= 23 AND                          "+
				"        (substr(t.oFF_time, 9, 2) - substr(t.on_time, 9, 2)) > 1))      "+
				"  where 1 = 1                                                           "+
				"    and substr(t.on_time, 0, 7) = '"+yearMonth+"' and s.no_ is not null "+
				"  group by TH.HOUR_,                                                    "+
				"           s.sex_code,                                                  "+
				"           tc.name_,                                                    "+
				"           s.dept_id,                                                   "+
				"           cd.name_,                                                    "+
				"           s.major_id,                                                  "+
				"           cdt.name_,                                                   "+
				"           s.class_id,                                                  "+
				"           cl.name_,                                                    "+
				"           s.edu_id,                                                    "+
				"           ce.name_,                                                    "+
				"           s.nation_code,                                                   "+
				"           tc2.name_,                                                   "+
				"           t.on_type,                                                   "+
				"           ntcode.name_                                                ";

		int addNum = baseDao.getJdbcTemplate().update(sql);
		map.put("addNum", addNum);

		return map;
	}

	@Override
	public Map<String, Integer> updateStuNumMonth(String yearMonth) {
		Map<String, Integer> map = new HashMap<String, Integer>();

		String delSql = "delete TL_STU_NUM_MONTH where year_month= to_date('"+yearMonth+"','yyyy-mm') ";
		int delNum = baseDao.getJdbcTemplate().update(delSql);
		map.put("delNum", delNum);
		String sql="insert into TL_STU_NUM_MONTH  "+
				"select to_date('"+yearMonth+"', 'yyyy-mm') year_month,                             "+
				"       s.dept_id,                                                                     "+
				"       cd.name_ dept_name,                                                            "+
				"       s.major_id,                                                                    "+
				"       cdt.name_ major_name,                                                          "+
				"       s.class_id,                                                                    "+
				"       cl.name_ class_name,                                                           "+
				"       s.sex_code,                                                                    "+
				"       tc.name_ sex_name,                                                             "+
				"       s.edu_id,                                                                      "+
				"       ce.name_ edu_name,                                                             "+
				"       s.nation_code,                                                                 "+
				"       tc2.name_ nation_name,                                                         "+
				"       count(*) all_stu                                                               "+
				"  from t_stu s                                                                        "+
				"  left join t_code_dept_teach cd on s.dept_id=cd.id                                   "+
				"  left join t_code_dept_teach cdt on s.major_id=cdt.id                                "+
				"  left join t_code_education ce on s.edu_id=ce.id                                     "+
				"  left join t_code tc on s.sex_code=tc.code_ and tc.code_type='SEX_CODE'              "+
				"  left join t_classes cl on s.class_id=cl.no_                                         "+
				"  left join t_code tc2 on tc2.code_ = s.nation_code  and tc2.code_type='NATION_CODE'  "+
				" where '"+yearMonth+"' >= substr(s.enroll_date, 0, 7)                                 "+
				"   and '"+yearMonth+"' < to_char(add_months(to_date(s.enroll_date, 'yyyy-mm-dd'),    "+
				"   s.length_schooling * 12),'yyyy-mm')                                                "+
				" group by s.dept_id,                                                                  "+
				"          cd.name_,                                                                   "+
				"          s.major_id,                                                                 "+
				"          cdt.name_,                                                                  "+
				"          s.class_id,                                                                 "+
				"          cl.name_,                                                                   "+
				"          s.sex_code,                                                                 "+
				"          tc.name_ ,                                                                  "+
				"          s.edu_id,                                                                   "+
				"          ce.name_,                                                                   "+
				"          s.nation_code,                                                              "+
				"          tc2.name_                                                                   ";
		int addNum = baseDao.getJdbcTemplate().update(sql);
		map.put("addNum", addNum);
		return map;
	}

	@Override
	public Map<String, Integer> updateNetTrend() {
		Map<String, Integer> map = new HashMap<String, Integer>();

		String delSql = "delete TL_NET_TREND ";
		int delNum = baseDao.getJdbcTemplate().update(delSql);
		map.put("delNum", delNum);
		String sql="insert into TL_NET_TREND "+
				"select b.Year_Month,b.DEPT_ID,b.MAJOR_ID,b.CLASS_ID,b.SEX_CODE,b.SEX_NAME,        "+
				"b.EDU_ID,b.EDU_NAME,b.NATION_CODE,b.NATION_NAME,b.ON_TYPE_CODE,b.ON_TYPE_NAME,    "+
				"nvl(b.all_time,0) all_time ,nvl(b.all_flow,0) all_flow,nvl(b.all_counts,0)        "+
				"all_counts,nvl(b.use_stu,0) use_stu,a.all_stu from                                "+
				"( SELECT * FROM TL_STU_NUM_MONTH ) a left join                                    "+
				"( SELECT t.Year_Month,T.DEPT_ID,T.MAJOR_ID,T.CLASS_ID,T.SEX_CODE,T.SEX_NAME,      "+
				"T.EDU_ID,T.EDU_NAME,T.NATION_CODE,T.NATION_NAME,T.ON_TYPE_CODE,T.ON_TYPE_NAME,    "+
				"sum(t.use_time) all_time,                                                         "+
				"sum(t.use_flow) all_flow,                                                         "+
				"sum(t.in_counts) all_counts,                                                      "+
				"count(distinct t.stu_id) use_stu                                                  "+
				"FROM tl_net_stu_month t where t.stu_id is not null                                "+
				"group by t.Year_Month,T.DEPT_ID,T.MAJOR_ID,T.CLASS_ID,T.SEX_CODE,T.SEX_NAME,      "+
				"T.EDU_ID,T.EDU_NAME,T.NATION_CODE,T.NATION_NAME,T.ON_TYPE_CODE,T.ON_TYPE_NAME ) b "+
				"on a.year_month=b.year_month and a.dept_id=b.dept_id                              "+
				"and a.major_id=b.major_id and a.class_id=b.class_id                               "+
				"and a.sex_code=b.sex_code and a.edu_id=b.edu_id and a.nation_code=b.nation_code   "+
				"where b.year_month is not null                                                    ";
		int addNum = baseDao.getJdbcTemplate().update(sql);
		map.put("addNum", addNum);
		return map;
	}
	@Override
	public Map<String, Integer> updateNetTeaWarn(String yearMonth) {
		Map<String, Integer> map = new HashMap<String, Integer>();

		String delSql = "delete tl_net_tea_warn where year_month= to_date('"+yearMonth+"','yyyy-mm') ";
		int delNum = baseDao.getJdbcTemplate().update(delSql);
		map.put("delNum", delNum);

		String sql =" insert into tl_net_tea_warn                                                "+
				" select to_date('"+yearMonth+"','yyyy_mm') year_month,a.people_id,a.on_ip,a.ON_WORK_NUM,a.OUT_WORK_NUM, "+
				" nvl(b.num,0) stu_num,a.people_name,a.sex_code,a.sex_name ,a.dept_id,a.dept_name  from ( "+
				" select on_ip, u.people_id, tea.name_ people_name, "+
		        " tea.sex_code, tc.name_ sex_name,   "+
		        " tea.dept_id, cd.name_ dept_name,                                                            "+
				"        SUM(CASE WHEN SUBSTR(T.ON_TIME, 12, 2) >= "+Code.getKey("net.work.start")+" AND "+
				"                   SUBSTR(T.ON_TIME, 12, 2) < "+Code.getKey("net.work.end")+" AND "+
				"                   to_number(to_char(to_date(ON_TIME, 'YYYY-MM-DD HH24:MI:SS'), "+
				"                                     'D')) <> 1 AND                             "+
				"                   to_number(to_char(to_date(ON_TIME, 'YYYY-MM-DD HH24:MI:SS'), "+
				"                                     'D')) <> 7 THEN 1 ELSE 0 END) ON_WORK_NUM, "+
				"        SUM(CASE WHEN SUBSTR(T.ON_TIME, 12, 2) >= "+Code.getKey("net.work.start")+" AND "+
				"                   SUBSTR(T.ON_TIME, 12, 2) < "+Code.getKey("net.work.end")+" AND "+
				"                   to_number(to_char(to_date(ON_TIME, 'YYYY-MM-DD HH24:MI:SS'), "+
				"                                     'D')) <> 1 AND                             "+
				"                   to_number(to_char(to_date(ON_TIME, 'YYYY-MM-DD HH24:MI:SS'), "+
				"                                     'D')) <> 7 THEN 0 ELSE 1 END) OUT_WORK_NUM "+
				"   from t_net_record t                                                          "+
				"   inner join t_net_user u on t.net_id = u.id                                   "+
				"   inner join t_tea tea on u.people_id = tea.tea_no                             "+
				"   left join t_code_dept cd on tea.dept_id=cd.id "+
				"   left join t_code tc on tea.sex_code=tc.code_ and tc.code_type='SEX_CODE' "+
				"   where substr(t.on_time,0,7) = '"+yearMonth+"'                                "+
				"   group by on_ip, u.people_id,tea.name_ ,tea.sex_code,tc.name_,tea.dept_id,cd.name_  "+
				"  ) a left join                                                                 "+
				" (select on_ip,count(*) num from t_net_record t                                 "+
				"   inner join t_net_user u on t.net_id =u.id                                    "+
				"   inner join t_stu stu on u.people_id = stu.no_                                "+
				"   where substr(t.on_time,0,7) = '"+yearMonth+"'                                      "+
				"   group by on_ip                                                               "+
				" ) b on a.on_ip=b.on_ip                                                         "+
				" order by a.people_id,a.on_ip                                                   ";
		int addNum = baseDao.getJdbcTemplate().update(sql);
		map.put("addNum", addNum);

		return map;
	}

	@Override
	public Map<String, Integer> updateNetTeaMonth(String yearMonth) {
		Map<String, Integer> map = new HashMap<String, Integer>();

		String delSql = "delete tl_net_tea_month where year_month= to_date('"+yearMonth+"','yyyy-mm') ";
		int delNum = baseDao.getJdbcTemplate().update(delSql);
		map.put("delNum", delNum);

//		String sql =" insert into tl_net_tea_month                                      "+
//				"select to_date('"+yearMonth+"','yyyy-mm') year_month,                  "+
//				"    nvl(sum(nr.use_time),0) USE_TIME,                                  "+
//				"    nvl(sum(nr.use_flow),0) USE_FLOW,                                  "+
//				"    nvl(sum(nr.use_money),0) USE_MONEY,                                "+
//				"    count(nr.id) ALL_COUNTS,                                           "+
//				"       t.tea_no,                                                       "+
//				"       t.name_ tea_name,                                               "+
//				"       t.sex_code,                                                     "+
//				"       tc.name_ sex_name ,                                             "+
//				"       t.dept_id,                                                      "+
//				"       cd.name_ dept_name,                                             "+
//				"       t.edu_id,                                                       "+
//				"       ce.name_ edu_name,                                              "+
//				"       nr.on_type ON_TYPE_CODE,                                        "+
//				"       ntcode.name_ ON_TYPE_NAME                                       "+
//				"from t_net_record nr                                                   "+
//				"inner join t_net_user nu on nr.net_id=nu.id                            "+
//				"inner join t_tea t on nu.people_id=t.tea_no                            "+
//				"left join t_code_dept_teach cd on t.dept_id =cd.id                     "+
//				"left join t_code_education ce on t.edu_id=ce.id                        "+
//				"left join t_code tc on t.sex_code=tc.code_ and tc.code_type='SEX_CODE' "+
//				"left join t_code ntcode on nr.on_type = ntcode.code_                   "+
//				"and ntcode.code_type = 'NET_TYPE_CODE'                                 "+
//				"where  substr(nr.on_time, 0, 7)='"+yearMonth+"'                        "+
//				"group by substr(nr.on_time, 0, 7),                                     "+
//				"       t.tea_no,                                                       "+
//				"       t.name_,                                                        "+
//				"       t.sex_code,                                                     "+
//				"       tc.name_ ,                                                      "+
//				"       t.dept_id,                                                      "+
//				"       cd.name_,                                                       "+
//				"       t.edu_id,                                                       "+
//				"       ce.name_ ,                                                      "+
//				"       nr.on_type ,                                                    "+
//				"       ntcode.name_                                                    ";
		String sql =" insert into tl_net_tea_month                                      "+
				"select to_date('"+yearMonth+"', 'yyyy-mm') year_month,TH.HOUR_,               "+
				"       count(case when substr(t.ON_time, 12, 2) = TH.HOUR_ then         "+
				"                '1' else null end) ON_COUNTS,                           "+
				"       count(case when substr(t.off_time, 12, 2) = TH.HOUR_ then        "+
				"                '1' else null end) OUT_COUNTS,                          "+
				"       nvl(count(distinct t.net_id), 0) IN_COUNTS,                      "+
				"       nvl(sum(case when substr(t.off_time, 12, 2) = TH.HOUR_ then      "+
				"                t.use_time else  null end),0) use_time,                 "+
				"       nvl(sum(case when substr(t.off_time, 12, 2) = TH.HOUR_ then      "+
				"            t.use_flow else null end),0) use_flow,                      "+
				"       nvl(sum(case when substr(t.off_time, 12, 2) = TH.HOUR_ then      "+
				"       t.use_money  else null end),0) use_money,                        "+
				"       s.tea_no,                                                        "+
				"       s.name_,                                                         "+
				"       s.dept_id,                                                       "+
				"       cd.name_ dept_name,                                              "+
				"       s.sex_code,                                                      "+
				"       tc.name_ sex_name,                                               "+
				"       s.edu_id,                                                        "+
				"       ce.name_ edu_name,                                               "+
				"       s.tea_status_code status_code,                                   "+
				"       tc2.name_ status_name,                                           "+
				"       S.BZLB_CODE,                                                     "+
				"       TC3.NAME_ BZLB_NAME,                                             "+
				"       zw.zyjszw_jb_code zw_jb_code,                                    "+
				"       tc4.name_ zw_jb_name,                                            "+
				"       t.on_type ON_TYPE_CODE,                                          "+
				"       ntcode.name_ ON_TYPE_NAME                                        "+
				"  from t_net_record t                                                   "+
				"  left join t_net_user c                                                "+
				"    on t.net_id = c.id                                                  "+
				"  left join t_tea s                                                     "+
				"    on s.tea_no = c.people_id                                           "+
				"  left join t_code_dept cd                                              "+
				"    on s.dept_id = cd.id                                                "+
				"  left join t_code_education ce                                         "+
				"    on s.edu_id = ce.id                                                 "+
				"  left join t_code tc                                                   "+
				"    on s.sex_code = tc.code_                                            "+
				"   and tc.code_type = 'SEX_CODE'                                        "+
				"  left join t_code tc2                                                  "+
				"    on tc2.code_ = s.tea_status_code                                    "+
				"   and tc2.code_type = 'TEA_STATUS_CODE'                                "+
				"  left join t_code tc3                                                  "+
				"    on tc3.code_ = s.bzlb_code                                          "+
				"   and tc3.code_type = 'BZLB_CODE'                                      "+
				"  left join T_CODE_ZYJSZW zw                                            "+
				"  on s.zyjszw_id =zw.id                                                 "+
				"  left join t_code tc4                                                  "+
				"  on zw.zyjszw_jb_code=tc4.code_                                        "+
				"  and tc4.code_type='ZYJSZW_JB_CODE'                                    "+
				"  left join t_code ntcode                                               "+
				"    on t.on_type = ntcode.code_                                         "+
				"   and ntcode.code_type = 'NET_TYPE_CODE'                               "+
				"  LEFT JOIN T_HOUR TH                                                   "+
				"    ON ((substr(t.on_time, 12, 2) <= TH.HOUR_ AND                       "+
				"       substr(t.off_time, 12, 2) >= TH.HOUR_ AND                        "+
				"       substr(t.on_time, 9, 2) = substr(t.oFF_time, 9, 2)) OR           "+
				"       (((substr(t.on_time, 12, 2) <= TH.HOUR_ AND TH.HOUR_ <= 23) OR   "+
				"       (00 <= TH.HOUR_ AND TH.HOUR_ <= substr(t.off_time, 12, 2))) AND  "+
				"       (substr(t.oFF_time, 9, 2) - substr(t.on_time, 9, 2)) = 1) OR     "+
				"       (00 <= TH.HOUR_ AND TH.HOUR_ >= 23 AND                           "+
				"       (substr(t.oFF_time, 9, 2) - substr(t.on_time, 9, 2)) > 1))       "+
				" where substr(t.on_time, 0, 7) = '"+yearMonth+"' and s.tea_no is not null     "+
				" group by TH.HOUR_,                                                     "+
				"          s.tea_no,                                                     "+
				"          s.name_,                                                      "+
				"          s.sex_code,                                                   "+
				"          tc.name_,                                                     "+
				"          s.dept_id,                                                    "+
				"          cd.name_,                                                     "+
				"          s.edu_id,                                                     "+
				"          ce.name_,                                                     "+
				"          s.tea_status_code,                                            "+
				"          tc2.name_,                                                    "+
				"          S.BZLB_CODE,                                                  "+
				"          TC3.NAME_,                                                    "+
				"          zw.zyjszw_jb_code,                                            "+
				"          tc4.name_,                                                    "+
				"          t.on_type,                                                    "+
				"          ntcode.name_                                                  ";
		int addNum = baseDao.getJdbcTemplate().update(sql);
		map.put("addNum", addNum);
		return map;
	}
}
