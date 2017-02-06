package cn.gilight.product.card.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.code.Code;
import cn.gilight.product.card.dao.JobCardDao;

@Repository("jobCardDao")
public class JobCardDaoImpl implements JobCardDao {
	@Autowired
	private BaseDao baseDao;

	@Override
	public Map<String, Integer> updateCardUsed(String yearMonth) {
		Map<String,Integer> map =new HashMap<String, Integer>();
		
		String delSql="delete tl_card_use_stu_month where year_month= to_date(?,'yyyy-mm') ";
		int delNum=baseDao.getJdbcTemplate().update(delSql,new Object[]{yearMonth});
		map.put("delNum", delNum);	
		
		String sql="insert into tl_card_use_stu_month                                     "+
				"select to_date(?,'yyyy-mm') year_month,             "+
				"nvl(count(cp.id),0) pay_count,                                                      "+
				"nvl(sum(cp.pay_money),0) pay_money,                                             "+
				"nvl(count(distinct substr(cp.time_, 9, 2)),0) pay_days, "+
				"s.no_ stu_id,                                                            "+
				"s.name_ stu_name,                                                        "+
				"s.sex_code,                                                              "+
				"tc.name_ sex_name,                                                       "+
				"s.major_id,                                                              "+
				"cdt.name_ major_name,                                                    "+
				"s.dept_id,                                                               "+
				"cd.name_ dept_name,                                                      "+
				"s.class_id,                                                               "+
				"cl.name_ class_name,                                                      "+
				"s.edu_id,                                                                "+
				"ce.name_ edu_name                                                        "+
				"from  t_stu s  "+
				"left join t_card c on s.no_=c.people_id "+
				"left join t_card_pay cp on ( c.no_=cp.card_id and substr(cp.time_, 0, 7)=? ) "+
				" left join t_code_dept_teach cd on s.dept_id =cd.id                      "+
				" left join t_code_dept_teach cdt on s.major_id=cdt.id                    "+
				" left join t_code_education ce on s.edu_id=ce.id                         "+
				" left join t_code tc on s.sex_code=tc.code_ and tc.code_type='SEX_CODE'  "+
				" left join t_classes cl on s.class_id=cl.no_							  "+
				"where ? >= substr(s.enroll_date,0,7) and  ? "+
				"<to_char(add_months(to_date(s.enroll_date,'yyyy-mm-dd'), "+
				"s.length_schooling*12),'yyyy-mm')    "+
				"group by substr(cp.time_, 0, 7),                                        "+
				"s.no_,                                                                   "+
				"s.name_,                                                                 "+
				"s.sex_code,                                                              "+
				"tc.name_ ,                                                               "+
				"s.major_id,                                                              "+
				"cdt.name_ ,                                                              "+
				"s.dept_id,                                                               "+
				"cd.name_ ,                                                               "+
				"s.class_id,                                                               "+
				"cl.name_ ,                                                               "+
				"s.edu_id,                                                                "+
				"ce.name_ ";
		int addNum=baseDao.getJdbcTemplate().update(sql,new Object[]{yearMonth,yearMonth,yearMonth,yearMonth});
		map.put("addNum", addNum);
		
		return map;
	}

	@Override
	public Map<String, Integer> updateRecharge(String yearMonth) {
		Map<String,Integer> map =new HashMap<String, Integer>();
		
		String delSql="delete tl_card_recharge_stu_detil where year_month= to_date(?,'yyyy-mm') ";
		int delNum=baseDao.getJdbcTemplate().update(delSql,new Object[]{yearMonth});
		map.put("delNum", delNum);	
		
		String sql="insert into  tl_card_recharge_stu_detil                                 "+
				"select to_date(substr(cr.time_, 0, 7),'yyyy-mm') year_month,            "+
				"substr(cr.time_, 12, 2) hour_,                                          "+
				"cr.time_ up_time,                                                       "+
				"cr.money_ up_money,                                                     "+
				"cr.old_money old_money,                                                 "+
				"s.no_ stu_id,                                                           "+
				"s.name_ stu_name,                                                       "+
				"cr.card_deal_id,                                                        "+
				"ccd.name_ card_deal_name,                                               "+
				"s.sex_code,                                                             "+
				"tc.name_ sex_name,                                                      "+
				"s.major_id,                                                             "+
				"cdt.name_ major_name,                                                   "+
				"s.dept_id,                                                              "+
				"cd.name_ dept_name,                                                     "+
				"s.edu_id,                                                               "+
				"ce.name_ edu_name                                                       "+
				"from t_card_recharge cr                                                 "+
				"inner join t_card c on cr.card_id = c.no_                               "+
				" inner join t_stu s on c.people_id = s.no_                              "+
				" left join T_CODE_CARD_DEAL ccd on cr.card_deal_id=ccd.id               "+
				" left join t_code_dept_teach cd on s.dept_id =cd.id                     "+
				" left join t_code_dept_teach cdt on s.major_id=cdt.id                   "+
				" left join t_code_education ce on s.edu_id=ce.id                        "+
				" left join t_code tc on s.sex_code=tc.code_ and tc.code_type='SEX_CODE' "+
				"where substr(cr.time_, 0, 7)=? ";
		int addNum=baseDao.getJdbcTemplate().update(sql,new Object[]{yearMonth});
		map.put("addNum", addNum);
		
		return map;
	}

	@Override
	public Map<String, Integer> updateCardTrend() {
		Map<String,Integer> map =new HashMap<String, Integer>();
		String delSql="DELETE TL_CARD_TREND ";
		int delNum=baseDao.getJdbcTemplate().update(delSql);
		map.put("delNum", delNum);	
		int addNum=0;
		String sql="insert into TL_CARD_TREND "+
				"select b.year_month,b.dept_id,b.major_id,'all' type_code,'总体' type_name,'use_all' FLAG,'' ALL_COUNT, "+
				"'' ALL_MONEY,nvl(a.nums,0) use_people,b.nums all_people,'' HOUR_  from "+
				"(select count(*) nums,t.year_month,t.dept_id,t.major_id from tl_card_use_stu_month t  "+
				"where  t.pay_count >= "+Code.getKey("card.uesd")+" group by t.year_month,t.dept_id,t.major_id ) a "+
				"right join  "+
				"(select sum(t.people_num) nums ,t.year_month,t.dept_id,t.major_id from tl_card_people_month t  "+
				"		group by t.year_month,t.dept_id,t.major_id ) b "+
				"on (a.year_month=b.year_month and a.major_id=b.major_id ) "+
				"order by a.year_month ";
		addNum+=baseDao.getJdbcTemplate().update(sql);
		sql="insert into TL_CARD_TREND                                                                                    "+
				"select b.year_month,b.dept_id,b.major_id,b.SEX_CODE type_code,b.SEX_NAME type_name,'use_sex' FLAG,'' ALL_COUNT,    "+
				"'' ALL_MONEY,nvl(a.nums,0) use_people,b.nums all_people,'' HOUR_ from                                        "+
				"(select count(*) nums,t.year_month,t.sex_code,t.sex_name,t.dept_id,t.major_id from tl_card_use_stu_month t   "+
				"where  t.pay_count >= "+Code.getKey("card.uesd")+" group by t.year_month,t.sex_code,t.sex_name,t.dept_id,t.major_id ) a                "+
				"right join                                                                                                   "+
				"(select sum(t.people_num) nums ,t.year_month,t.sex_code,t.sex_name,t.dept_id,t.major_id from                 "+
				"tl_card_people_month t group by t.year_month,t.sex_code,t.sex_name,t.dept_id,t.major_id ) b                  "+
				"on (a.year_month=b.year_month and a.sex_code=b.sex_code and a.major_id=b.major_id)                           "+
				"order by a.year_month ";
		addNum+=baseDao.getJdbcTemplate().update(sql);
		sql="insert into TL_CARD_TREND                                                                                    "+
				"select b.year_month,b.dept_id,b.major_id,b.EDU_ID type_code,b.EDU_NAME type_name,'use_edu' FLAG,'' ALL_COUNT,      "+
				"'' ALL_MONEY,nvl(a.nums,0) use_people,b.nums all_people,'' HOUR_ from                                        "+
				"(select count(*) nums,t.year_month,t.edu_id,t.edu_name,t.dept_id,t.major_id from tl_card_use_stu_month t     "+
				"where  t.pay_count >= "+Code.getKey("card.uesd")+" group by t.year_month,t.edu_id,t.edu_name,t.dept_id,t.major_id ) a                  "+
				"right join                                                                                                   "+
				"(select sum(t.people_num) nums ,t.year_month,t.edu_id,t.edu_name,t.dept_id,t.major_id from                   "+
				"tl_card_people_month t group by t.year_month,t.edu_id,t.edu_name,t.dept_id,t.major_id ) b                    "+
				"on (a.year_month=b.year_month and a.edu_id=b.edu_id and a.major_id=b.major_id)                               "+
				"order by a.year_month ";
		addNum+=baseDao.getJdbcTemplate().update(sql);
		sql="insert into TL_CARD_TREND                                                                                    "+
				"select t.year_month,t.dept_id,t.major_id,'all' type_code,'总体' type_name,'recharge' FLAG,                          "+
				"count(*) all_count,sum(t.up_money) all_money,count(distinct t.stu_id) USE_PEOPLE, '' all_people,'' HOUR_     "+
				"from tl_card_recharge_stu_detil t group by t.year_month,t.dept_id,t.major_id";
		addNum+=baseDao.getJdbcTemplate().update(sql);
		sql="insert into TL_CARD_TREND                                                                                    "+
				"select t.year_month,t.dept_id,t.major_id,t.card_deal_id type_code,                                           "+
				"t.card_deal_name type_name ,'pay_type' FLAG,sum(t.pay_count) all_count,sum(t.pay_money) all_money,                  "+
				"'' USE_PEOPLE,'' all_people,'' HOUR_ from tl_card_dept_hour t                                            "+
				"group by t.year_month,t.card_deal_id, t.card_deal_name ,t.dept_id,t.major_id";
		addNum+=baseDao.getJdbcTemplate().update(sql);
		sql="insert into TL_CARD_TREND                                                                                    "+
				"select a.year_month,a.dept_id,a.major_id,'all' type_code,'总体' type_name,'pay_all' flag,a.all_count,              "+
				"a.all_money,b.nums use_people,'' all_people,'' HOUR_ from                                                    "+
				"(select sum(t.pay_money) all_money ,sum(t.pay_count) all_count,t.year_month,t.dept_id,t.major_id             "+
				"from tl_card_stu_deal t                                 "+
				" group by t.year_month ,t.dept_id,t.major_id )a                                                              "+
				"left join                                                                                                    "+
				"(select count(distinct t.stu_id) nums ,t.year_month,t.dept_id,t.major_id from                                "+
				"tl_card_use_stu_month t                                     "+
				" group by t.year_month,t.dept_id,t.major_id) b                  "+
				"on a.year_month=b.year_month and a.major_id=b.major_id";
		addNum+=baseDao.getJdbcTemplate().update(sql);
		sql="insert into TL_CARD_TREND                                                                                    "+
				"select a.year_month,a.dept_id,a.major_id,a.type_code,a.type_name,'pay_sex' flag,a.all_count,                       "+
				"a.all_money,b.nums use_people,'' all_people,'' HOUR_ from                                                    "+
				"(select sum(t.pay_money) all_money ,sum(t.pay_count) all_count,t.year_month,t.dept_id,t.major_id ,           "+
				"t.sex_code type_code,t.sex_name type_name                                                                    "+
				"from tl_card_stu_deal t                                                                                 "+
				" group by t.year_month ,t.dept_id,t.major_id,t.sex_code,t.sex_name )a                                        "+
				"left join                                                                                                    "+
				"(select count(distinct t.stu_id) nums ,t.year_month,t.dept_id,t.major_id,t.sex_code type_code from           "+
				"tl_card_use_stu_month t  group by t.year_month,t.dept_id,t.major_id,t.sex_code) b                            "+
				"on a.year_month=b.year_month and a.major_id=b.major_id and a.type_code=b.type_code";
		addNum+=baseDao.getJdbcTemplate().update(sql);
		sql="insert into TL_CARD_TREND                                                                                    "+
				"select a.year_month,a.dept_id,a.major_id,a.type_code,a.type_name,'pay_edu' flag,a.all_count,                       "+
				"a.all_money,b.nums use_people,'' all_people,'' HOUR_ from                                                    "+
				"(select sum(t.pay_money) all_money ,sum(t.pay_count) all_count,t.year_month,t.dept_id,t.major_id ,           "+
				"t.edu_id type_code,t.edu_name type_name                                                                      "+
				"from tl_card_stu_deal t                                                                                 "+
				" group by t.year_month ,t.dept_id,t.major_id,t.edu_id,t.edu_name )a                                          "+
				"left join                                                                                                    "+
				"(select count(distinct t.stu_id) nums ,t.year_month,t.dept_id,t.major_id,t.edu_id type_code from             "+
				"tl_card_use_stu_month t  group by t.year_month,t.dept_id,t.major_id,t.edu_id) b                              "+
				"on a.year_month=b.year_month and a.major_id=b.major_id and a.type_code=b.type_code";
		addNum+=baseDao.getJdbcTemplate().update(sql);
		sql="insert into TL_CARD_TREND                                                                                    "+
				"select t.year_month,'' dept_id,'' major_id,t.card_dept_pid type_code,t.card_dept_pname type_name,'eat_room'         "+
				"flag,sum(t.pay_count) all_count,sum(t.pay_money) all_money,'' use_people,'' all_people,t.hour_               "+
				"from tl_card_dept_hour t where t.card_deal_id='"+Code.getKey("card.ct")+"'                                                      "+
				"group by t.year_month,t.card_dept_pid,t.card_dept_pname,t.hour_   order by year_month ";
		addNum+=baseDao.getJdbcTemplate().update(sql);
		sql="insert into TL_CARD_TREND                                                                                    "+
				"select t.year_month,'' dept_id,'' major_id,t.card_dept_id type_code,t.card_dept_name type_name,'eat_port'          "+
				"flag,sum(t.pay_count) all_count,sum(t.pay_money) all_money ,                                                 "+
				"'' use_people,'' all_people,t.hour_                                                                          "+
				"from tl_card_dept_hour t where t.card_deal_id='"+Code.getKey("card.ct")+"'                                                      "+
				"group by t.year_month,t.card_dept_id,t.card_dept_name,t.hour_   order by year_month";
		addNum+=baseDao.getJdbcTemplate().update(sql);
		
		sql="insert into TL_CARD_TREND                                                                                    "+
				"select to_date(substr(t.up_time,0,4)||'-01','yyyy-mm') year,t.dept_id,t.major_id,    "+
				"t.card_deal_id type_code,t.card_deal_name type_name,    "+
				"'recha_year' flag,count(*) all_count,sum(t.up_money) all_money ,'' USE_PEOPLE, '' all_people,'' HOUR_    "+
				"from tl_card_recharge_stu_detil t group by substr(t.up_time,0,4),    "+
				"t.card_deal_id,t.card_deal_name,t.dept_id,t.major_id order by substr(t.up_time,0,4) " ;
		addNum+=baseDao.getJdbcTemplate().update(sql);
		
		sql="insert into TL_CARD_TREND                                                                                    "+
				"select to_date(a.years||'-01','yyyy-mm') year,dept_id,major_id ,'all' type_code,'总体' type_name,'pay_year' FLAG, "+
				"'' all_count,sum(pay_money) all_money,count(distinct stu_id) USE_PEOPLE,'' all_people,'' HOUR_  from  "+
				"(select to_char(year_month,'yyyy') years,stu_id,sum(pay_money) pay_money,dept_id,major_id  "+
				"from tl_card_use_stu_month group by to_char(year_month,'yyyy') ,stu_id,stu_name,dept_id,major_id "+
				")a group by a.years,dept_id,major_id " ;
		addNum+=baseDao.getJdbcTemplate().update(sql);
		
		map.put("addNum", addNum);
		return map;
	}

	@Override
	public Map<String, Integer> updateStuPay(String yearMonth) {
		Map<String,Integer> map =new HashMap<String, Integer>();
		String delSql="delete tl_card_pay_stu_month where year_month= to_date(?,'yyyy-mm') ";
		int delNum=baseDao.getJdbcTemplate().update(delSql,new Object[]{yearMonth});
		map.put("delNum", delNum);	
		String sql="insert into  tl_card_pay_stu_month                                      "+
				"select to_date(substr(cp.time_, 0, 7),'yyyy-mm') year_month,            "+
				"substr(cp.time_, 12, 2) hours,                                          "+
				"sum(cp.pay_money) pay_money,                                            "+
				"count(0) pay_count,                                                     "+
				"cde.id card_DEPT_id,                                                        "+
				"cde.name_ card_DEPT_name,                                               "+
				"pcde.id card_DEPT_pid,                                                  "+
				"pcde.name_ card_DEPT_pname,                                             "+
				"cp.card_deal_id,                                                        "+
				"ccd.name_ card_deal_name,                                               "+
				"s.sex_code,                                                             "+
				"tc.name_ sex_name,                                                      "+
				"s.major_id,                                                             "+
				"cdt.name_ major_name,                                                   "+
				"s.dept_id,                                                              "+
				"cd.name_ dept_name,                                                     "+
				"s.edu_id,                                                               "+
				"ce.name_ edu_name,                                                      "+

				"cde.DEPT_TYPE CARD_DEPT_TYPE                                            "+
				
				"  from t_card_pay cp                                                    "+
				" inner join t_card c on cp.card_id = c.no_                              "+
				" inner join t_stu s on c.people_id = s.no_                              "+
				" left join T_CODE_CARD_DEAL ccd on cp.card_deal_id=ccd.id               "+
				" left join T_CARD_PORT TCP on cp.card_port_id=TCP.NO_                    "+
				" left join T_CARD_DEPT cde on TCP.card_DEPT_id=cde.id                    "+
				" left join T_CARD_DEPT pcde on cde.pid=pcde.id                          "+
				" left join t_code_dept_teach cd on s.dept_id =cd.id                     "+
				" left join t_code_dept_teach cdt on s.major_id=cdt.id                   "+
				" left join t_code_education ce on s.edu_id=ce.id                        "+
				" left join t_code tc on s.sex_code=tc.code_ and tc.code_type='SEX_CODE' "+
				"where substr(cp.time_, 0, 7)=? "+
				" group by substr(cp.time_, 0, 7),                                       "+
				"cde.id,                                                        "+
				"cde.name_ ,                                                             "+
				"cp.card_deal_id,                                                        "+
				"ccd.name_ ,                                                             "+
				"pcde.id ,                                                               "+
				"pcde.name_ ,                                                            "+
				"substr(cp.time_, 12, 2) ,                                               "+
				"s.sex_code,                                                             "+
				"tc.name_ ,                                                              "+
				"s.major_id,                                                             "+
				"cdt.name_ ,                                                             "+
				"s.dept_id,                                                              "+
				"cd.name_ ,                                                              "+
				"s.edu_id,                                                               "+
				"ce.name_,cde.DEPT_TYPE                                                                ";
		int addNum=baseDao.getJdbcTemplate().update(sql,new Object[]{yearMonth});
		map.put("addNum", addNum);
		
		return map;
	}

	@Override
	public Map<String, Integer> updateStuEat(String yearMonth) {
		Map<String,Integer> map =new HashMap<String, Integer>();
		
		String delSql="delete tl_card_stu_eat where year_month= to_date(?,'yyyy-mm') ";
		int delNum=baseDao.getJdbcTemplate().update(delSql,new Object[]{yearMonth});
		map.put("delNum", delNum);	
		
		String sql="insert into tl_card_stu_eat                                                         "+
				"select round(sum(eat)/count(*)) eat,year_month,stu_id,stu_name,                     "+
				"	sex_code,sex_name,major_id,major_name,dept_id,dept_name,edu_id,edu_name from (   "+
				" select count(distinct eat) eat,days,year_month,stu_id,stu_name,                    "+
				"	sex_code,sex_name,major_id,major_name,dept_id,dept_name,edu_id,edu_name          "+
				" from (select to_date(substr(cp.time_, 0, 7),'yyyy-mm') year_month,                 "+
				" substr(cp.time_, 0, 10) days,                                                      "+
				"case when substr(cp.time_, 12, 2) <='09' then '早'                                  "+
				"when substr(cp.time_, 12, 2) >'09' and substr(cp.time_, 12, 2) <='14' then '中'     "+
				"else  '晚' end eat,                                                                 "+
				"s.no_ stu_id,                                                                       "+
				"s.name_ stu_name,                                                                   "+
				"s.sex_code,                                                                         "+
				"tc.name_ sex_name,                                                                  "+
				"s.major_id,                                                                         "+
				"cdt.name_ major_name,                                                               "+
				"s.dept_id,                                                                          "+
				"cd.name_ dept_name,                                                                 "+
				"s.edu_id,                                                                           "+
				"ce.name_ edu_name                                                                   "+
				"  from t_card_pay cp                                                                "+
				" inner join t_card c on cp.card_id = c.no_                                          "+
				" inner join t_stu s on c.people_id = s.no_                                          "+
				" left join t_code_dept_teach cd on s.dept_id =cd.id                                 "+
				" left join t_code_dept_teach cdt on s.major_id=cdt.id                               "+
				" left join t_code_education ce on s.edu_id=ce.id                                    "+
				" left join t_code tc on s.sex_code=tc.code_ and tc.code_type='SEX_CODE'             "+
				" where cp.card_deal_id='"+Code.getKey("card.ct")+"'  and substr(cp.time_, 0, 7)=? "+
				" group by substr(cp.time_, 0, 7),                                                   "+
				" substr(cp.time_, 0, 10),                                                           "+
				" substr(cp.time_, 12, 2),                                                           "+
				"s.no_,                                                                              "+
				"s.name_,                                                                            "+
				"s.sex_code,                                                                         "+
				"tc.name_ ,                                                                          "+
				"s.major_id,                                                                         "+
				"cdt.name_ ,                                                                         "+
				"s.dept_id,                                                                          "+
				"cd.name_ ,                                                                          "+
				"s.edu_id,                                                                           "+
				"ce.name_                                                                            "+
				" )  group by days,year_month,stu_id,stu_name,sex_code,                              "+
				"	sex_name,major_id,major_name,dept_id,dept_name,edu_id,edu_name                   "+
				") group by year_month,stu_id,stu_name,sex_code,                                     "+
				"	sex_name,major_id,major_name,dept_id,dept_name,edu_id,edu_name                   ";
		int addNum=baseDao.getJdbcTemplate().update(sql,new Object[]{yearMonth});
		map.put("addNum", addNum);
		
		return map;
	}

	@Override
	public Map<String, Integer> updateCardPeople(String yearMonth) {
		// TODO 当前仅为学生
		Map<String,Integer> map =new HashMap<String, Integer>();
		
		String delSql="delete tl_card_people_month where year_month= to_date(?,'yyyy-mm') ";
		int delNum=baseDao.getJdbcTemplate().update(delSql,new Object[]{yearMonth});
		map.put("delNum", delNum);	
		
		String sql="insert into tl_card_people_month     "+
				"select to_date(?,'yyyy-mm') year_month,    "+
				"count(*) people_num,s.sex_code,      "+
				"case when s.edu_id='31' then '10'    "+
				"when s.edu_id='21' then '09'  "+
				"else '' end people_code,      "+
				"case when s.edu_id='31' then '专科'  "+
				"when s.edu_id='21' then '本科' "+
				"else '未维护' end people_name, "+
				"'P1' people_par_code,  "+
				"'学生' people_par_name, "+
				"tc.name_ sex_name,     "+
				"s.major_id,     "+
				"cdt.name_ major_name,  "+
				"s.dept_id,      "+
				"cd.name_ dept_name,    "+
				"s.edu_id, "+
				"ce.name_ edu_name  from t_stu s      "+
				" left join t_code_dept_teach cd on s.dept_id =cd.id "+
				" left join t_code_dept_teach cdt on s.major_id=cdt.id     "+
				" left join t_code_education ce on s.edu_id=ce.id   "+
				" left join t_code tc on s.sex_code=tc.code_ and tc.code_type='SEX_CODE' "+
				"where ? >= substr(s.enroll_date,0,7) and  ? "+
				"<to_char(add_months(to_date(s.enroll_date,'yyyy-mm-dd'), "+
				"s.length_schooling*12),'yyyy-mm')    "+
				"group by s.sex_code,   "+
				"tc.name_ ,      "+
				"s.major_id,     "+
				"cdt.name_ ,     "+
				"s.dept_id,      "+
				"cd.name_ ,      "+
				"s.edu_id, "+
				"ce.name_ ";
		int addNum=baseDao.getJdbcTemplate().update(sql,new Object[]{yearMonth,yearMonth,yearMonth});
		map.put("addNum", addNum);
		return map;
	}

	@Override
	public Map<String, Integer> updateStuPayDetil(String yearMonth) {
		
		Map<String,Integer> map =new HashMap<String, Integer>();
		
		String delSql="delete tl_card_pay_stu_detil where year_month= to_date(?,'yyyy-mm') ";
		int delNum=baseDao.getJdbcTemplate().update(delSql,new Object[]{yearMonth});
		map.put("delNum", delNum);	
		
		String sql="insert into tl_card_pay_stu_detil     "+
				"select to_date(substr(cp.time_, 0, 7),'yyyy-mm') year_month,      "+
				"cp.time_ pay_time,      "+
				"cp.pay_money pay_money, "+
				"cde.id card_DEPT_id,                                                        "+
				"cde.name_ card_DEPT_name,                                               "+
				"pcde.id card_DEPT_pid,                                                  "+
				"pcde.name_ card_DEPT_pname,                                             "+
				"cp.card_deal_id, "+
				"ccd.name_ card_deal_name,      "+
				"s.no_ stu_no,    "+
				"s.name_ stu_name, "+
				"s.sex_code,      "+
				"tc.name_ sex_name,      "+
				"s.major_id,      "+
				"cdt.name_ major_name,   "+
				"s.dept_id, "+
				"cd.name_ dept_name,     "+
				"s.edu_id, "+
				"ce.name_ edu_name "+
				"from t_card_pay cp      "+
				" inner join t_card c on cp.card_id = c.no_   "+
				" inner join t_stu s on c.people_id = s.no_   "+
				" left join T_CODE_CARD_DEAL ccd on cp.card_deal_id=ccd.id  "+
				" left join T_CARD_PORT TCP on cp.card_port_id=TCP.NO_                    "+
				" left join T_CARD_DEPT cde on TCP.card_DEPT_id=cde.id                    "+
				" left join T_CARD_DEPT pcde on cde.pid=pcde.id                          "+
				" left join t_code_dept_teach cd on s.dept_id =cd.id "+
				" left join t_code_dept_teach cdt on s.major_id=cdt.id      "+
				" left join t_code_education ce on s.edu_id=ce.id    "+
				" left join t_code tc on s.sex_code=tc.code_ and tc.code_type='SEX_CODE'  "+
				"where substr(cp.time_, 0, 7)=? ";
		int addNum=baseDao.getJdbcTemplate().update(sql,new Object[]{yearMonth,});
		map.put("addNum", addNum);
		return map;
	}

	@Override
	public Map<String, Integer> updateOriginMonth(String yearMonth) {
		Map<String,Integer> map =new HashMap<String, Integer>();
		
		String delSql="delete tl_card_pay_origin_hours where year_month= to_date(?,'yyyy-mm') ";
		int delNum=baseDao.getJdbcTemplate().update(delSql,new Object[]{yearMonth});
		map.put("delNum", delNum);	
		String area_id=Code.getKey("area.xz.code");
		String sql="insert into tl_card_pay_origin_hours                                     "+
				" select to_date(?,'yyyy-mm') year_month,                   "+
				" substr(cp.time_, 12, 2) hours,                                                 "+
				" sum(cp.pay_money) pay_money,                                                   "+
				" count(0) pay_count,                                                            "+
				" cp.card_deal_id,                                                               "+
				" ccd.name_ card_deal_name,                                                      "+
				" s.major_id,                                                                    "+
				" cdt.name_ major_name,                                                          "+
				" s.dept_id,                                                                     "+
				" cd.name_ dept_name,                                                            "+
				" s.origin_id,                                                                   "+
				" s.origin_name                                                                  "+
				"  from t_card_pay cp                                                            "+
				"  inner join t_card c on cp.card_id = c.no_                                     "+
				"  inner join (                                                                  "+
				"select s.*, "+
			    "case when substr(s.stu_origin_id,0,2)||'0000'='"+area_id+"' and s.nation_code<>'01' then '"+area_id+"' else 'other' end origin_id,  "+
			    "case when substr(s.stu_origin_id,0,2)||'0000'='"+area_id+"' and s.nation_code<>'01' then '特殊人群' else '其他' end origin_name from "+
			    "t_stu s "+
				"  ) s on c.people_id = s.no_                                                    "+
				"  left join T_CODE_CARD_DEAL ccd on cp.card_deal_id=ccd.id                      "+
				"  left join t_code_dept_teach cd on s.dept_id =cd.id                            "+
				"  left join t_code_dept_teach cdt on s.major_id=cdt.id                          "+
				"  where substr(cp.time_, 0, 7)=?                                        "+
				"  group by cp.card_deal_id,                                                    "+
				" ccd.name_ ,                                                                    "+
				" substr(cp.time_, 12, 2) ,                                                      "+
				" s.major_id,                                                                    "+
				" cdt.name_ ,                                                                    "+
				" s.dept_id,                                                                     "+
				" cd.name_ ,                                                                     "+
				" s.origin_id,                                                                   "+
				" s.origin_name                                                                  ";
		int addNum=baseDao.getJdbcTemplate().update(sql,new Object[]{yearMonth,yearMonth});
		map.put("addNum", addNum);
		
		return map;
	}

	@Override
	public Map<String, Integer> updateCardDeptHour(String yearMonth) {
		Map<String,Integer> map =new HashMap<String, Integer>();
		
		String delSql="delete tl_card_dept_hour where year_month= to_date(?,'yyyy-mm') ";
		int delNum=baseDao.getJdbcTemplate().update(delSql,new Object[]{yearMonth});
		map.put("delNum", delNum);	
		
		String sql="insert into  tl_card_dept_hour                                    "+
				"select to_date(?,'yyyy-mm') year_month,      "+
				"        substr(cp.time_, 12, 2) hour_,                            "+
				"        s.dept_id,                                                "+ 
				"        cd.name_ dept_name,                                       "+
				"        s.major_id,                                               "+ 
				"        cdt.name_ major_name,                                     "+
				"        sum(cp.pay_money) pay_money,                              "+ 
				"        count(0) pay_count,                                       "+ 
				"        cde.id card_DEPT_id,                                      "+     
				"        cde.name_ card_DEPT_name,                                 "+ 
				"        pcde.id card_DEPT_pid,                                    "+ 
				"        pcde.name_ card_DEPT_pname,                               "+ 
				"        cp.card_deal_id,                                          "+ 
				"        ccd.name_ card_deal_name,                                 "+ 
				"        cde.DEPT_TYPE CARD_DEPT_TYPE                              "+ 
				"         from t_card_pay cp                                       "+
				"         inner join t_card c on cp.card_id = c.no_                "+ 
				"         inner join t_stu s on c.people_id = s.no_                "+ 
				"         left join T_CODE_CARD_DEAL ccd on cp.card_deal_id=ccd.id "+ 
				"         left join T_CARD_PORT TCP on cp.card_port_id=TCP.NO_     "+  
				"         left join T_CARD_DEPT cde on TCP.card_DEPT_id=cde.id     "+  
				"         left join T_CARD_DEPT pcde on cde.pid=pcde.id            "+ 
				"         left join t_code_dept_teach cd on s.dept_id =cd.id       "+ 
				"         left join t_code_dept_teach cdt on s.major_id=cdt.id     "+ 
				"        where substr(cp.time_, 0, 7)=?                            "+
				"        group by substr(cp.time_, 12, 2) ,                        "+
				"        cde.id,                                                   "+
				"        cde.name_ ,                                               "+ 
				"        cp.card_deal_id,                                          "+ 
				"        ccd.name_ ,                                               "+ 
				"        pcde.id ,                                                 "+ 
				"        pcde.name_ ,                                              "+ 
				"        s.major_id,                                               "+ 
				"        cdt.name_ ,                                               "+ 
				"        s.dept_id,                                                "+ 
				"        cd.name_ ,                                                "+ 
				"        cde.DEPT_TYPE                                             ";
		int addNum=baseDao.getJdbcTemplate().update(sql,new Object[]{yearMonth,yearMonth});
		map.put("addNum", addNum);
		
		return map;
	}

	@Override
	public Map<String, Integer> updateCardStuHour(String yearMonth) {
		Map<String,Integer> map =new HashMap<String, Integer>();
		
		String delSql="delete tl_card_stu_hour where year_month= to_date(?,'yyyy-mm') ";
		int delNum=baseDao.getJdbcTemplate().update(delSql,new Object[]{yearMonth});
		map.put("delNum", delNum);	
		
		String sql="insert into  tl_card_stu_hour                                                            "+
				"select to_date(?,'yyyy-mm') year_month,                             "+
				"        substr(cp.time_, 12, 2) hour_,                                                   "+
				"        sum(cp.pay_money) pay_money,                                                     "+
				"        count(0) pay_count,                                                              "+
				"        s.no_ stu_id,                                                                    "+
				"        s.name_ stu_name,                                                                "+
				"        s.dept_id,                                                                       "+
				"        cd.name_ dept_name,                                                              "+
				"        s.major_id,                                                                      "+
				"        cdt.name_ major_name,                                                            "+
				"        s.class_id,                                                                      "+
				"		 cl.name_ class_name,                                                             "+
				"        s.sex_code,                                                                      "+
				"        tc.name_ sex_name,                                                               "+
				"        s.edu_id,                                                                        "+
				"        ce.name_ edu_name ,                                                              "+
				"        s.nation_code,                                                                   "+
				"        tcn.name_ nation_name,                                                           "+
				"        s.stu_origin_id origin_id,                                                       "+
				"        cad.id PROVINCE_ID,                                                              "+
				"        cad.name_ PROVINCE_NAME                                                          "+
				"         from t_card_pay cp                                                              "+
				"         inner join t_card c on cp.card_id = c.no_                                       "+
				"         inner join t_stu s on c.people_id = s.no_                                       "+
				"         left join t_code_dept_teach cd on s.dept_id =cd.id                              "+
				"         left join t_code_dept_teach cdt on s.major_id=cdt.id                            "+
				"         left join t_classes cl on s.class_id=cl.no_                                     "+
				"         left join t_code_education ce on s.edu_id=ce.id                                 "+
				"         left join t_code tc on s.sex_code=tc.code_ and tc.code_type='SEX_CODE'          "+
				"         left join t_code tcn on s.nation_code=tcn.code_ and tcn.code_type='NATION_CODE' "+
				"         left join T_CODE_ADMINI_DIV CAD on substr(s.stu_origin_id,0,2)||'0000'=CAD.ID   "+
				"        where substr(cp.time_, 0, 7)=?                                                   "+
				"        group by substr(cp.time_, 12, 2) ,                                               "+
				"        s.no_,                                                                           "+
				"        s.name_,                                                                         "+
				"        s.sex_code,                                                                      "+
				"        tc.name_ ,                                                                       "+
				"        s.major_id,                                                                      "+
				"        cdt.name_ ,                                                                      "+
				"        s.dept_id,                                                                       "+
				"        cd.name_ ,                                                                       "+
				"        s.class_id,                                                                      "+
				"		 cl.name_ ,                                                                       "+
				"        s.edu_id ,                                                                       "+
				"        ce.name_  ,                                                                      "+
				"        s.nation_code,                                                                   "+
				"        tcn.name_,                                                                       "+
				"        s.stu_origin_id ,                                                                "+
				"        CAD.ID,                                                                          "+
				"        cad.name_                                                                        ";
		int addNum=baseDao.getJdbcTemplate().update(sql,new Object[]{yearMonth,yearMonth});
		map.put("addNum", addNum);
		
		return map;
	}

	@Override
	public Map<String, Integer> updateCardStuDeal(String yearMonth) {
		Map<String,Integer> map =new HashMap<String, Integer>();
		
		String delSql="delete tl_card_stu_deal where year_month= to_date(?,'yyyy-mm') ";
		int delNum=baseDao.getJdbcTemplate().update(delSql,new Object[]{yearMonth});
		map.put("delNum", delNum);	
		
		String sql="insert into tl_card_stu_deal                                                             "+
				"select to_date(?,'yyyy-mm') year_month,                             "+
				"        sum(cp.pay_money) pay_money,                                                     "+
				"        count(0) pay_count,                                                              "+
				"        s.no_ stu_id,                                                                    "+
				"        s.name_ stu_name,                                                                "+
				"        cp.card_deal_id,                                                                 "+
				"        ccd.name_ card_deal_name,                                                        "+
				"        s.sex_code,                                                                      "+
				"        tc.name_ sex_name,                                                               "+
				"        s.major_id,                                                                      "+
				"        cdt.name_ major_name,                                                            "+
				"        s.class_id,                                                                      "+
				"		cl.name_ class_name,                                                              "+
				"        s.dept_id,                                                                       "+
				"        cd.name_ dept_name,                                                              "+
				"        s.edu_id,                                                                        "+
				"        ce.name_ edu_name ,                                                              "+
				"        s.nation_code,                                                                   "+
				"        tcn.name_ nation_name,                                                           "+
				"        s.stu_origin_id origin_id,                                                       "+
				"        cad.id PROVINCE_ID,                                                              "+
				"        cad.name_ PROVINCE_NAME                                                          "+
				"         from t_card_pay cp                                                              "+
				"         inner join t_card c on cp.card_id = c.no_                                       "+
				"         inner join t_stu s on c.people_id = s.no_                                       "+
				"         left join T_CODE_CARD_DEAL ccd on cp.card_deal_id=ccd.id                        "+
				"         left join t_code_dept_teach cd on s.dept_id =cd.id                              "+
				"         left join t_code_dept_teach cdt on s.major_id=cdt.id                            "+
				"         left join t_classes cl on s.class_id=cl.no_                                     "+
				"         left join t_code_education ce on s.edu_id=ce.id                                 "+
				"         left join t_code tc on s.sex_code=tc.code_ and tc.code_type='SEX_CODE'          "+
				"         left join t_code tcn on s.nation_code=tcn.code_ and tcn.code_type='NATION_CODE' "+
				"         left join T_CODE_ADMINI_DIV CAD on substr(s.stu_origin_id,0,2)||'0000'=CAD.ID   "+
				"         where substr(cp.time_, 0, 7)=?                                                  "+
				"         group by s.no_,                                                                 "+
				"        s.name_,                                                                         "+
				"        cp.card_deal_id,                                                                 "+
				"        ccd.name_ ,                                                                      "+
				"        s.sex_code,                                                                      "+
				"        tc.name_ ,                                                                       "+
				"        s.major_id,                                                                      "+
				"        cdt.name_ ,                                                                      "+
				"        s.dept_id,                                                                       "+
				"        cd.name_ ,                                                                       "+
				"        s.class_id,                                                                      "+
				"		cl.name_ ,                                                                        "+
				"        s.edu_id,                                                                        "+
				"        ce.name_ ,                                                                       "+
				"        s.nation_code,                                                                   "+
				"        tcn.name_,                                                                       "+
				"        s.stu_origin_id,                                                                 "+
				"        CAD.ID,                                                                          "+
				"        cad.name_                                                                        ";
		int addNum=baseDao.getJdbcTemplate().update(sql,new Object[]{yearMonth,yearMonth});
		map.put("addNum", addNum);
		
		return map;
	}
}
