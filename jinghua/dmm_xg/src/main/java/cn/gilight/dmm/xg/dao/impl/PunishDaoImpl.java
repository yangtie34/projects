package cn.gilight.dmm.xg.dao.impl;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.AgeUtils;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.PmsUtils;
import cn.gilight.dmm.xg.dao.PunishDao;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.product.EduUtils;

/**
 * 违纪处分
 * 
 * @author xuebl
 * @date 2016年5月31日 上午11:01:15
 */
@Repository("punishDao")
public class PunishDaoImpl implements PunishDao {

	@Resource
	private BusinessDao businessDao;
	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessService businessService;
	private static final String KEY_VIOLATE = "violate";
	private static final String KEY_PUNISH  = "punish";
	private static final String KEY_MONTH  = "month";
	private static final String KEY_GRADE = "grade";
	private static final String KEY_AGAIN="again";
	private static final String KEY_DEPT="dept";
	/**
     * 标准代码-汉族编码
     */
    public static final String HANZU_CODE = "01";
	@Override
	public String getViolateSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date){
		String stuSql  = businessDao.getStuSql(schoolYear, deptList, advancedList),
			   schoolYearSql = schoolYear!=null ? (" and t.school_year like '"+schoolYear+"%'") : "",
			   dateSql = start_date!=null ? (" and t.violate_date >= '" +start_date+ "'") : "";
		dateSql = end_date!=null ? (dateSql+" and t.violate_date <= '" +end_date+ "'") : dateSql;
		return "select t.* from t_stu_punish t, ("+stuSql+") stu where t.stu_id=stu.no_ " + schoolYearSql + dateSql;
	}
	@Override
	public int queryViolateCount(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date){
		String violateSql  = getViolateSql(schoolYear, deptList, advancedList, start_date, end_date);
		return baseDao.queryForCount(violateSql);
	}
	@Override
	public String getViolateStuIdSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date){
		String violateSql = getViolateSql(schoolYear, deptList, advancedList, start_date, end_date);
		return "select distinct(stu_id) no_ from (" +violateSql+ ")";
	}
	@Override
	public int queryViolateStuIdCount(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date){
		String violateStuIdSql = getViolateStuIdSql(schoolYear, deptList, advancedList, start_date, end_date);
		return baseDao.queryForCount(violateStuIdSql);
	}

	@Override
	public String getPunishSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date){
		String violateSql = getViolateSql(schoolYear, deptList, advancedList, start_date, end_date);
		return "select t.* from (" +violateSql+ ") t where t.punish_code is not null";
	}
	@Override
	public int queryPunishCount(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date){
		String punishSql  = getPunishSql(schoolYear, deptList, advancedList, start_date, end_date);
		return baseDao.queryForCount(punishSql);
	}
	@Override
	public String getPunishStuIdSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date){
		String punishSql = getPunishSql(schoolYear, deptList, advancedList, start_date, end_date);
		return "select distinct(stu_id) no_ from (" +punishSql+ ")";
	}
	@Override
	public int queryPunishStuIdCount(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date){
		String punishStuIdSql = getPunishStuIdSql(schoolYear, deptList, advancedList, start_date, end_date);
		return baseDao.queryForCount(punishStuIdSql);
	}

	
	@Override
	public List<Map<String, Object>> queryViolateTypeList(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date){
		String violateSql = getViolateSql(schoolYear, deptList, advancedList, start_date, end_date);
		String sql = "select t.count value, code.id, code.name_ name, p.pid, p.pname_ pname from "
				+ " (select t.violate_id, count(0) count from (" +violateSql+ ") t group by t.violate_id) t, t_code_violate code,"
				+ " (select v.id,v.name_,p.id pid,p.name_ pname_ from t_code_violate v, t_code_violate p"
				+ " where substr(v.path_,1,4)=p.path_)p where t.violate_id=code.id and code.id=p.id"
				+ " order by code.level_,code.pid,code.order_";
		return baseDao.queryListInLowerKey(sql, Types.NUMERIC, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR);
	}
	
	@Override
	public List<Map<String, Object>> queryPunishTypeList(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date){
		String punishSql = getPunishSql(schoolYear, deptList, advancedList, start_date, end_date);
		String sql = "select t.count value, code.code_ code, code.name_ name from "
				+ " (select t.punish_code, count(0) count from (" +punishSql+ ") t group by t.punish_code) t, t_code code"
				+ " where t.punish_code=code.code_ and code.code_type='" +Constant.CODE_PUNISH_CODE+ "'"
				+ " order by code.order_,code.code_";
		return baseDao.queryListInLowerKey(sql, Types.NUMERIC, Types.VARCHAR, Types.VARCHAR);
	}
	
	@Override
	public List<Map<String, Object>> queryViolateDeptList(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, 
			String start_date, String end_date, String violateBzdmSql){
		String violateSql = getViolateSql(schoolYear, deptList, advancedList, start_date, end_date);
		/*String sql = "select t.count value, code.id code, code.name_ name from"
				+ " (select t.violate_id id, count(0) count from (" +violateSql+ ") t group by t.violate_id) t"
				+ " right join (" +violateBzdmSql+ ")code on t.id=code.id order by code.level_,code.pid,code.order_";*/
		
		String sql="select t.*,stu.class_id,stu.major_id,stu.dept_id from ("+violateSql+") t,t_stu stu where t.stu_id=stu.no_";
		String dataSql = businessService.getNextLevelDataSqlByDeptDataAndPid(deptList, AdvancedUtil.getPid(advancedList), sql, false, true, false, false, schoolYear, null);
		sql = "select t.violate_id as code,t.next_dept_code as id,count(0) as value from ("+dataSql+") t "
				+" group by  t.violate_id,t.next_dept_code ";
		sql="select  t.code as code ,t.value as value, t.id as id,code.name_ as name from ("+sql+") t"
				+ " right join (" +violateBzdmSql+ ")code on t.code=code.id order by t.id,t.code,code.level_,code.pid,code.order_";
		return baseDao.queryListInLowerKey(sql, Types.VARCHAR, Types.NUMERIC, Types.VARCHAR,Types.VARCHAR);
	}
	
	@Override
	public List<Map<String, Object>> queryPunishDeptList(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, 
			String start_date, String end_date, String punishBzdmSql){
		String punishSql = getPunishSql(schoolYear, deptList, advancedList, start_date, end_date);
		/*String sql = "select t.count value, code.code_ code, code.name_ name from"
				+ " (select t.punish_code code, count(0) count from (" +punishSql+ ") t group by t.punish_code) t"
				+ " right join (" +punishBzdmSql+ ")code on t.code=code.code_ order by code.order_,code.code_";*/
		String sql="select t.*,stu.class_id,stu.major_id,stu.dept_id from ("+punishSql+") t,t_stu stu where t.stu_id=stu.no_";
		String dataSql = businessService.getNextLevelDataSqlByDeptDataAndPid(deptList, AdvancedUtil.getPid(advancedList), sql, false, true, false, false, schoolYear, null);
		sql = "select t.punish_code as code,t.next_dept_code as id,count(0) as value from ("+dataSql+") t "
				+" group by  t.punish_code,t.next_dept_code ";
		sql="select  t.code as code ,t.value as value, t.id as id,code.name_ as name from ("+sql+") t"
				+ " right join (" +punishBzdmSql+ ")code on t.code=code.code_ order by code.order_,code.code_";
		return baseDao.queryListInLowerKey(sql, Types.VARCHAR, Types.NUMERIC, Types.VARCHAR,Types.VARCHAR);
	}

	@Override
	public List<Map<String, Object>> queryViolateMonthList(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, 
			String start_date, String end_date){
		String violateSql = getViolateSql(schoolYear, deptList, advancedList, start_date, end_date);
		String sql = "select count(0) value, month name from"
				+ " (select substr(t.violate_date, 6, 2) as month from (" +violateSql+ ") t) group by month order by month";
		return baseDao.queryListInLowerKey(sql, Types.NUMERIC, Types.NUMERIC);
	}

	@Override
	public List<Map<String, Object>> queryPunishMonthList(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, 
			String start_date, String end_date){
		String punishSql = getPunishSql(schoolYear, deptList, advancedList, start_date, end_date);
		String sql = "select count(0) value, month name from"
				+ " (select substr(t.punish_date, 6, 2) as month from (" +punishSql+ ") t) group by month order by month";
		return baseDao.queryListInLowerKey(sql, Types.NUMERIC, Types.NUMERIC);
	}
	
	@Override
	public List<String> queryViolateBirthdayList(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, 
			String start_date, String end_date){
		String violateSql = getViolateSql(schoolYear, deptList, advancedList, start_date, end_date);
		String sql = "select stu.birthday from ("+violateSql+")t, t_stu stu where t.stu_id = stu.no_";
		return baseDao.queryForListString(sql);
	}
	
	@Override
	public List<String> queryPunishBirthdayList(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, 
			String start_date, String end_date){
		String punishSql = getPunishSql(schoolYear, deptList, advancedList, start_date, end_date);
		String sql = "select stu.birthday from ("+punishSql+")t, t_stu stu where t.stu_id = stu.no_";
		return baseDao.queryForListString(sql);
	}
	
	@Override
	public int queryPunishAgainCount(String punishStuIdSql, String end_date){
		String sql = "select count(0) count from"
				+ " (select t.stu_id, count(0) count from t_stu_punish t, (" +punishStuIdSql+ ")z"
				+ " where t.punish_date <= '" +end_date+ "' and t.stu_id=z.no_ group by t.stu_id) where count > 1";
		return baseDao.queryForInt(sql);
	}
	
	@Override
	public String getViolateBzdmSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date){
		String violateSql = getViolateSql(schoolYear, deptList, advancedList, start_date, end_date),
			   sql = "select code.* from t_code_violate code,"
				+ " (select distinct(violate_id) code from (" +violateSql+ "))t"
				+ " where t.code = code.id order by code.level_,code.pid,code.order_";
		return sql;
	}
	
	@Override
	public String getPunishBzdmSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date){
		String punishSql = getPunishSql(schoolYear, deptList, advancedList, start_date, end_date),
			   sql = "select code.* from t_code code,"
				+ " (select distinct(punish_code) code from (" +punishSql+ "))t"
				+ " where t.code = code.code_ and code.code_type='" +Constant.CODE_PUNISH_CODE+ "' order by code.order_,code.code_";
		return sql;
	}
	@SuppressWarnings("unchecked")
	@Override
	public String getStuDetailSql(Integer schoolYear,List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date,Map<String, Object> keyValue, List<String> fields){
		String stuPunishDetail=null,stuSql=null, level=null;
		for(Map.Entry<String, Object> entry : keyValue.entrySet()){
			String type = entry.getKey();
			if(type.equals("all")){
				level= (String)entry.getValue();
				if(level.equals(KEY_VIOLATE)){
					String violateSql=getViolateSql(schoolYear, deptList, advancedList, start_date, end_date);
					stuSql="select stu.*,t."+level+"_date from ("+violateSql+") t, t_stu stu where t.stu_id=stu.no_ ";
				}else{
					String punishSql=getPunishSql(schoolYear, deptList, advancedList, start_date, end_date);
					stuSql="select stu.*,t."+level+"_date from ("+punishSql+") t, t_stu stu where t.stu_id=stu.no_ ";
				}
			}
			if(type.equals(KEY_VIOLATE)){
				level=KEY_VIOLATE;
				String code = String.valueOf(entry.getValue());
				String sql="select id from T_CODE_VIOLATE where level_='1' and id='"+code+"'";
				 Map<String,Object> tag =baseDao.queryMapInLowerKey(sql);
				if(tag!=null &&!tag.equals("")){
					String violateSql=getViolateSql(schoolYear, deptList, advancedList, start_date, end_date);
					violateSql="select t.*,code.pid from ("+violateSql+") t,T_CODE_VIOLATE code where t.violate_id=code.id";
					stuSql="select stu.*,t.violate_date from ("+violateSql+") t, t_stu stu where t.stu_id=stu.no_ and t.pid='"+code+"'";
				}else{
					String violateSql=getViolateSql(schoolYear, deptList, advancedList, start_date, end_date);
					stuSql="select stu.*,t.violate_date from ("+violateSql+") t, t_stu stu where t.stu_id=stu.no_ and t.violate_id='"+code+"'";
				}
			}
			if(type.equals(KEY_PUNISH)){
				level=KEY_PUNISH;
				String code = String.valueOf(entry.getValue());
				String punishSql=getPunishSql(schoolYear, deptList, advancedList, start_date, end_date);
				stuSql="select stu.*,t.punish_date from ("+punishSql+") t,t_stu stu where t.stu_id=stu.no_ and t.punish_code='"+code+"'";
			}
			if(type.equals(KEY_DEPT)){
				Map<String,String> map=(Map<String, String>) entry.getValue();
				level=MapUtils.getString(map, "level");
				String target=MapUtils.getString(map, "target");
				int level_=baseDao.queryForInt("select level_ from t_code_dept_teach where id='"+MapUtils.getString(map, "deptId")+"'");
				deptList = PmsUtils.getDeptListByDeptAndLevel(MapUtils.getString(map, "deptId"),level_ );//type代表层次类型
				String sql="";
				if(target.equals("1")){
					if(level.equals(KEY_PUNISH)){sql=getPunishSql(schoolYear, deptList, advancedList, start_date, end_date);
					stuSql="select stu.*,t."+level+"_date from ("+sql+") t, t_stu stu where t.stu_id=stu.no_ and t."+level+"_code='"+MapUtils.getString(map, "code")+"'";
					}
					if(level.equals(KEY_VIOLATE)){sql=getViolateSql(schoolYear, deptList, advancedList, start_date, end_date);
					stuSql="select stu.*,t."+level+"_date from ("+sql+") t, t_stu stu where t.stu_id=stu.no_ and t."+level+"_id='"+MapUtils.getString(map, "code")+"'";
					}
				}
				if(target.equals("2")){
					if(level.equals(KEY_PUNISH)){
						sql=getPunishSql(schoolYear, deptList, advancedList, start_date, end_date);
						sql="select distinct(t.stu_id),wm_concat(t.punish_date) as punish_date from ("+sql+") t group by t.stu_id";
						stuSql="select stu.*,t."+level+"_date from ("+sql+") t, t_stu stu where t.stu_id=stu.no_ ";
					}
					if(level.equals(KEY_VIOLATE)){
						sql=getViolateSql(schoolYear, deptList, advancedList, start_date, end_date);
						sql="select distinct(t.stu_id),wm_concat(t.violate_date) as violate_date from ("+sql+") t group by t.stu_id";
						stuSql="select stu.*,t."+level+"_date from ("+sql+") t, t_stu stu where t.stu_id=stu.no_ ";
					}
				}
			}
			if(type.equals(KEY_MONTH)){
				Map<String,String> map=(Map<String, String>) entry.getValue();
				level=MapUtils.getString(map, "level");
				String month=MapUtils.getString(map, "month").replaceAll("[^(0-9)]", "");
				if(month.length()==1){
					month="0"+month;
				}
				if(level.equals(KEY_VIOLATE)){
					String violateSql=getViolateSql(schoolYear, deptList, advancedList, start_date, end_date);
					violateSql= "select t.stu_id,t.violate_date from  (select t.*,substr(t.violate_date, 6, 2) as month from (" +violateSql+ ") t) t where t.month='"+month+"'";
					stuSql="select stu.*,t.violate_date from ("+violateSql+") t, t_stu stu where t.stu_id=stu.no_ ";
				}
				if(level.equals(KEY_PUNISH)){
					String punishSql=getPunishSql(schoolYear, deptList, advancedList, start_date, end_date);
					punishSql= "select t.stu_id,t.punish_date from  (select t.*,substr(t.punish_date, 6, 2) as month from (" +punishSql+ ") t) t where t.month='"+month+"'";
					stuSql="select stu.*,t.punish_date from ("+punishSql+") t,t_stu stu where t.stu_id=stu.no_ ";
				}
			}
			if(type.equals(KEY_AGAIN)){
				Map<String,String> map=(Map<String, String>) entry.getValue();
				String start=MapUtils.getString(map, "start");
				int year =Integer.parseInt(start);
				level=MapUtils.getString(map, "level");
				String[] yearAll=EduUtils.getTimeQuantum(year);
				String punishSql=getPunishStuIdSql(year, deptList, advancedList, yearAll[0], yearAll[1]);
				punishSql="select stu_id,punish_date from "+
						" (select t.stu_id, count(0) as count,wm_concat(t.punish_date) as punish_date from t_stu_punish t, (" +punishSql+ ")z"
						+ " where t.punish_date <= '" +yearAll[1]+ "' and t.stu_id=z.no_ group by t.stu_id ) where count > 1";
				//punishSql
				stuSql="select stu.*,t.punish_date from ("+punishSql+") t,t_stu stu where t.stu_id=stu.no_ ";
			}
		}
		String stuDetailSql = getStuDetailSql(stuSql,level);
		if(fields!=null && !fields.isEmpty()){
			stuPunishDetail =  "select "+StringUtils.join(fields, ",")+ " from ("+stuDetailSql+") ";
		}
		return stuPunishDetail;
		
	}
	@Override
	public String getGradeOrAgeDetailSql(Integer school_year,List<String> deptList, List<AdvancedParam> advancedList, String start,
			String end,String num,String level,String type){
		String violateSql=null,stuSql=null;
		if(type.equals(KEY_GRADE)){
			if(level.equals("violate")){
				violateSql=getViolateSql(school_year, deptList, advancedList, start, end);
			}else{
				violateSql=getPunishSql(school_year, deptList, advancedList, start, end);
			}
			/*stuSql="select t.* from (select stu.*,t."+level+"_date,("+school_year+"-stu.enroll_grade+1) as grade from ("+violateSql+") t,t_stu stu where t.stu_id=stu.no_) t"
					+ " where grade='"+num+"'";//对原数据不做处理*/
			String Sql="select distinct(t.no_),wm_concat(t."+level+"_date) as "+level+"_date from (select stu.*,t."+level+"_date,("+school_year+"-stu.enroll_grade+1) as grade from ("+violateSql+") t,t_stu stu where t.stu_id=stu.no_) t"
				+ " where grade='"+num+"' group by t.no_";//对学生进行重复过滤
			stuSql="select stu.*,t."+level+"_date from ("+Sql+") t,t_stu stu where stu.no_=t.no_";
			 
		}else{
			if(level.equals("violate")){
				violateSql=getViolateSql(school_year, deptList, advancedList, start, end);
			}else{
				violateSql=getPunishSql(school_year, deptList, advancedList, start, end);
			}
			start = start.length() ==4? start : start.substring(0, 4);
			if(num.equals("18")){
				num="<=18";
			}else if(num.equals("25")){
				num=">=25";
			}else{
				num="="+num;
			}
			 stuSql="select t.* from (select stu.*,t."+level+"_date,("+start+"-substr(stu.birthday, 0, 4)) as age from ("+violateSql+") t,t_stu stu where t.stu_id=stu.no_) t"
				+ " where age "+num;
		}
		String stuDetailSql = getStuDetailSql(stuSql,level);
		return stuDetailSql;
	}
	
	//获取学生违纪处分信息 以及日期
		private String getStuDetailSql(String stuSql,String level){
			String sql = "select t.id,t.no_ no,t.name_ name, sex.name_ sexmc, sex.code_ sexid, dept.name_ deptmc, t.dept_id deptid,"
					+ " major.name_ majormc, t.major_id majorid, class.name_ classmc, t.class_id,t.enroll_grade,t.length_schooling,"
					+ " xzqh.sname shengmc, xzqh.sid shengid, shi.sname shimc, shi.sid shiid, shi.name xianmc, shi.id xianid,t."+level+"_date date_"
					+ " from ("+stuSql+")t"
					+ " left join t_code sex on t.sex_code=sex.code_ and sex.code_type='"+Constant.CODE_SEX_CODE+"'"
					+ " left join t_code_dept_teach dept on t.dept_id=dept.id"
					+ " left join t_code_dept_teach major on t.major_id=major.id"
					+ " left join t_classes class on t.class_id=class.no_"
					+ " left join (select s.id sid, s.name_ sname, q.id from t_code_admini_div s, t_code_admini_div q"
					+ " where substr(q.path_,1,4)=s.path_) xzqh on t.STU_ORIGIN_ID=xzqh.id"
					+ " left join (select s.id sid, s.name_ sname, q.id, q.name_ name from t_code_admini_div s, t_code_admini_div q"
					+ " where substr(q.path_,1,8)=s.path_) shi on t.stu_origin_id=shi.id";
			return sql;
		}
	@Override
	public Map<String,Object> getViolateOrPunishBySex(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date,String code,String type){
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedList);
		stuAdvancedList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Common_SEX_CODE, code));
		int stuCount=businessDao.queryStuCount(schoolYear, deptList, stuAdvancedList);
		String sql=null;
		if(type.equals(KEY_PUNISH)){
			/**处分男女*/
			String punishSql=getPunishSql(schoolYear, deptList, advancedList, start_date, end_date);
			sql = "select round((t.value/"+stuCount+")*100,8) as value from ("
					+ " select count(0) value, t.sex_code code from (select t.stu_id, stu.sex_code"
					+ " from (" +punishSql+ ") t, t_stu stu where t.stu_id=stu.no_)t group by t.sex_code) t"
					+ " where code='"+code+"'";
		}
		if(type.equals(KEY_VIOLATE)){
			String violateSql=getViolateSql(schoolYear, deptList, advancedList, start_date, end_date);
			/**违纪男女*/
			sql = "select round((t.value/"+stuCount+")*100,8) as value from ("
					+ " select count(0) value, t.sex_code code from (select t.stu_id, stu.sex_code"
					+ " from (" +violateSql+ ") t, t_stu stu where t.stu_id=stu.no_)t group by t.sex_code) t"
					+ " where code='"+code+"'";
		}
		Map<String,Object> map= baseDao.queryMapInLowerKey(sql);
		return map;
	}
	@Override
	public Map<String,Object> getViolateOrPunishByNation(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date,String code,String type){
		String stuSql=businessDao.getStuSql(schoolYear, deptList, advancedList);
		String sql=null;
		if(type.equals(KEY_PUNISH)){
			/**处分汉族，少数民族*/
			String punishSql=getPunishSql(schoolYear, deptList, advancedList, start_date, end_date);
			if(code.equals(HANZU_CODE)){
				stuSql="select t.* from ("+stuSql+") t where t.nation_code='"+code+"'";
				int stuCount=baseDao.queryForCount(stuSql);
				sql = "select round((t.value/"+stuCount+")*100,8) as value from ("
						+ " select count(0) value from (select t.stu_id "
						+ " from (" +punishSql+ ") t, t_stu stu where t.stu_id=stu.no_ and stu.nation_code='"+code+"' ) t ) t";
			}else{
				stuSql="select t.* from ("+stuSql+") t where t.nation_code!='"+HANZU_CODE+"'";
				int stuCount=baseDao.queryForCount(stuSql);
				sql = "select round((t.value/"+stuCount+")*100,8) as value from ("
						+ " select count(0) value from (select t.stu_id "
						+ " from (" +punishSql+ ") t, t_stu stu where t.stu_id=stu.no_ and stu.nation_code!='"+HANZU_CODE+"' ) t ) t";
			}
		}
		if(type.equals(KEY_VIOLATE)){
			String violateSql=getViolateSql(schoolYear, deptList, advancedList, start_date, end_date);
			if(code.equals(HANZU_CODE)){
				stuSql="select t.* from ("+stuSql+") t where t.nation_code='"+code+"'";
				int stuCount=baseDao.queryForCount(stuSql);
				sql = "select round((t.value/"+stuCount+")*100,8) as value from ("
						+ " select count(0) value from (select t.stu_id "
						+ " from (" +violateSql+ ") t, t_stu stu where t.stu_id=stu.no_ and stu.nation_code='"+code+"' ) t ) t";
			}else{
				stuSql="select t.* from ("+stuSql+") t where t.nation_code!='"+HANZU_CODE+"'";
				int stuCount=baseDao.queryForCount(stuSql);
				sql = "select round((t.value/"+stuCount+")*100,8) as value from ("
						+ " select count(0) value from (select t.stu_id "
						+ " from (" +violateSql+ ") t, t_stu stu where t.stu_id=stu.no_ and stu.nation_code!='"+HANZU_CODE+"' ) t ) t";
			}
		}
		Map<String,Object> map= baseDao.queryMapInLowerKey(sql);
		return map;
	}
	@Override
	public Map<String,Object> getViolateOrPunishBySubject(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date,String code,String type){
		String stuSql=businessDao.getStuSql(schoolYear, deptList, advancedList),sql="";
		stuSql="select t.* from (select t.*,tp.subject_id from ("+stuSql+") t,t_code_dept_teach tp where t.major_id=tp.id ) t where t.subject_id='"+code+"'";
		int stuCount=baseDao.queryForCount(stuSql);
		if(stuCount==0){
			sql="select '0' as value from dual ";
		}else{
			if(type.equals(KEY_VIOLATE)){
				String violateSql=getViolateSql(schoolYear, deptList, advancedList, start_date, end_date);
				 sql="select t.stu_id,t.major_id, tp.subject_id from  (select stu.major_id,t.stu_id from ("+violateSql+") t,t_stu stu where t.stu_id=stu.no_) t,t_code_dept_teach tp where t.major_id=tp.id ";//违纪学生及学科
				 sql="select round((t.value/"+stuCount+")*100,8) as value from (select count(*) value from ("+sql+") t where t.subject_id in ("+code+")) t";
			}else{
				String punishSql=getPunishSql(schoolYear, deptList, advancedList, start_date, end_date);
				sql="select t.stu_id,t.major_id, tp.subject_id from  (select stu.major_id,t.stu_id from ("+punishSql+") t,t_stu stu where t.stu_id=stu.no_) t,t_code_dept_teach tp where t.major_id=tp.id ";//处分学生及学科
				sql="select round((t.value/"+stuCount+")*100,8) as value from (select count(*) value from ("+sql+") t where t.subject_id in ("+code+")) t";
			}
		}
		Map<String,Object> map=baseDao.queryMapInLowerKey(sql);
		return map;
	}
	@Override
	public String getSexGradeSubjectNationSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date,String code,String type,String tag){
		String sql="",dataSql="";
		if(type.equals(KEY_PUNISH)){
			 dataSql=getPunishSql(schoolYear, deptList, advancedList, start_date, end_date);
		}else{
			 dataSql=getViolateSql(schoolYear, deptList, advancedList, start_date, end_date);
		}
		switch (tag){
		case "sex":
			sql = "select t.stu_id from  (select t.stu_id, stu.sex_code"
				+ " from (" +dataSql+ ") t, t_stu stu where t.stu_id=stu.no_ ) t"
				+ " where t.sex_code='"+code+"'";//性别
			break;
		case "subject":
			sql="select t.stu_id from (select t.stu_id, tp.subject_id from  (select stu.major_id,t.stu_id from ("+dataSql+") t,t_stu stu where t.stu_id=stu.no_) t,t_code_dept_teach tp where t.major_id=tp.id ) t "
				+ " where t.subject_id='"+code+"'";//违纪学生及学科
			break;
		case "grade":
			sql="select t.stu_id from (select t.stu_id,("+schoolYear+"-stu.enroll_grade+1) as grade from ("+dataSql+") t,t_stu stu where t.stu_id=stu.no_) t"
					+ " where t.grade='"+code+"'";
			break;
		case "nation":
			if(code.equals(HANZU_CODE)){
				sql="select t.stu_id from ( select t.stu_id,stu.nation_code from("+dataSql+") t,t_stu stu where t.stu_id=stu.no_) t "
						+ " where t.nation_code='"+code+"'";//违纪学生汉族
			}else{
				sql="select t.stu_id from ( select t.stu_id,stu.nation_code from("+dataSql+") t,t_stu stu where t.stu_id=stu.no_) t "
						+ " where t.nation_code!='"+HANZU_CODE+"'";//违纪学生少数民族
			}
			break;
		}
		return sql;
	}
}