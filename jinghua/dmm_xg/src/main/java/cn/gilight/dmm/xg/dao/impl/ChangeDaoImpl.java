package cn.gilight.dmm.xg.dao.impl;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.PmsUtils;
import cn.gilight.dmm.xg.dao.ChangeDao;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.product.EduUtils;

/**
 * 学籍异动
 * 
 * @author xuebl
 * @date 2016年5月4日 上午9:57:45
 */
@Repository("changeDao")
public class ChangeDaoImpl implements ChangeDao {

	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BusinessService businessService;
	private static final String Type_Type = "type";
	private static final String Type_Grade = "grade";
	private static final String Type_Subject = "subject";
	private static final String Type_Sex = "sex";
	private static final String Type_Dept="dept";
	private static final String Type_Month="month";
	private static final String Type_Year="year";
	private static final String Type_AllChange="allChange";
	private static final String Type_BadChange="badChange";
	private static final String Type_changeMajor="changeMajor";
	
	
	@Override
	public List<Map<String, Object>> queryChangeAbstract(int schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList,
			String start_date, String end_date, String change_codes){
		String changeSql = getChangeSql(schoolYear, deptList, stuAdvancedList, start_date, end_date, change_codes);
		String sql = "select t.value, t.code code from"
				+ " (select count(0) value, stu_change_code code from (" +changeSql+ ") t group by stu_change_code) t, t_code code"
				+ " where t.code=code.code_ and code.code_type='" +Constant.CODE_STU_CHANGE_CODE+ "' order by code.order_,code.code_";
		return baseDao.queryListInLowerKey(sql, Types.NUMERIC, Types.VARCHAR);
	}
	
	@Override
	public List<Map<String, Object>> queryChangeType(int schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, 
			String start_date, String end_date, String change_codes){
		String changeSql = getChangeSql(schoolYear, deptList, stuAdvancedList, start_date, end_date, change_codes);
		String sql = "select t.count value, code.name_ name, t.stu_change_code code from"
				+ " (select count(0) count, t.stu_change_code from (" +changeSql+ ") t"
				+ " group by t.stu_change_code)t, t_code code"
				+ " where t.stu_change_code=code.code_ and code.code_type='" +Constant.CODE_STU_CHANGE_CODE+ "' order by code.order_,code.code_";
		return baseDao.queryListInLowerKey(sql, Types.NUMERIC, Types.VARCHAR, Types.VARCHAR);
	}
	
	@Override
	public List<Map<String, Object>> queryChangeGrade(int schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, 
			String start_date, String end_date, String change_codes){
		String changeSql = getChangeSql(schoolYear, deptList, stuAdvancedList, start_date, end_date, change_codes);
		String sql = "select count(0) value, t.grade code from"
				+ " (select t.stu_id, stu.enroll_grade, substr(t.school_year,0,4)+1-stu.enroll_grade as grade"
				+ " from (" +changeSql+ ") t, t_stu stu"
				+ " where t.stu_id=stu.no_)t where grade is not null group by t.grade order by grade";
		return baseDao.queryListInLowerKey(sql, Types.NUMERIC, Types.VARCHAR, Types.VARCHAR);
	}
	
	@Override
	public List<Map<String, Object>> queryChangeSubject(int schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, 
			String start_date, String end_date, String change_codes){
		String changeSql = getChangeSql(schoolYear, deptList, stuAdvancedList, start_date, end_date, change_codes);
		String sql = "select value, case when tp.name_ is null or tp.id is null then '"+Constant.CODE_Unknown_Name+"' else tp.name_ end name,"
				+ " case when tp.id is null then 'null' else tp.id end id from"
				+ " (select count(0) value, pid from"
					+ " (select t.stu_id, stu.major_id, dept.name_, dept.subject_id, sj.pid"
					+ " from (" +changeSql+ ") t, t_stu stu, t_code_dept_teach dept"
							+ " left join (select t.id,t.name_, tp.name_ pname, tp.id pid"
							+ " from "+Constant.TABLE_T_Code_Subject_Degree+" t, "+Constant.TABLE_T_Code_Subject_Degree+" tp"
							+ " where substr(t.path_,1,4)=tp.path_ order by t.level_,t.pid,t.order_ ) sj"
							+ " on dept.subject_id=sj.id"
				+ " where t.stu_id=stu.no_ and stu.major_id=dept.id) group by pid)t"
				+ " left join "+Constant.TABLE_T_Code_Subject_Degree+" tp on t.pid = tp.id"
				+ " order by tp.order_,tp.code_";
		return baseDao.queryListInLowerKey(sql, Types.NUMERIC, Types.VARCHAR, Types.VARCHAR);
	}
	
	@Override
	public List<Map<String, Object>> queryChangeSex(int schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, 
			String start_date, String end_date, String change_codes){
		String changeSql = getChangeSql(schoolYear, deptList, stuAdvancedList, start_date, end_date, change_codes);
		String sql = "select t.value, code.name_ name, t.code from ("
				+ " select count(0) value, t.sex_code code from (select t.stu_id, stu.sex_code"
				+ " from (" +changeSql+ ") t, t_stu stu where t.stu_id=stu.no_)t group by t.sex_code) t,"
				+ " t_code code where t.code=code.code_ and code.code_type='" +Constant.CODE_SEX_CODE+ "' order by code.order_,code.code_";
		return baseDao.queryListInLowerKey(sql, Types.NUMERIC, Types.VARCHAR, Types.VARCHAR);
	}
	
	/**
	 * 获取某学年、日期段 学籍异动数据sql
	 * @param schoolYear 学年；eg：2015
	 * @param deptList 标准权限
	 * @param start_date 开始日期
	 * @param end_date 截止日期
	 * @return String （select * from t_stu_change）
	 */
	private String getChangeSql(int schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList,
			String start_date, String end_date, String change_codes){
		// 某学年学生数据sql
		String stuSql = businessDao.getStuSql(schoolYear, deptList, stuAdvancedList);
		// 符合日期条件的过滤sql；匹配code的sql
		String matchingDateSql = getMatchingDateSql(start_date, end_date),
			   matchingCodeSql = change_codes==null ? "" : (" and t.stu_change_code in ("+businessDao.formatInSql(change_codes)+")");
		matchingDateSql = matchingDateSql!=null ? (" and "+matchingDateSql) : "";
		return "select t.* from t_stu_change t, (" +stuSql+ ") stu where t.stu_id = stu.no_" +matchingDateSql+matchingCodeSql;
	}
	
	/**
	 * 获取学籍异动人员匹配sql（日期）
	 * @param start_date
	 * @param end_date
	 * @return String || null
	 */
	private String getMatchingDateSql(String start_date, String end_date){
		String dateSql = null;
		if(start_date != null && end_date != null){
			dateSql = " t.date_ >= '"+start_date+"' and t.date_ <= '" +end_date+ "'";
		}else if(start_date != null || end_date != null){
			dateSql = " t.date_ " + (start_date!=null ? ">='"+start_date+"'" : "<='"+end_date+"'");
		}
		return dateSql;
	}
	
	@Override
	public int queryStuChangeCountByDeptId(int schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, 
			String start_date, String end_date, String deptIds, String change_codes){
		String sql = getChangeSql(schoolYear, deptList, stuAdvancedList, start_date, end_date, change_codes), // 符合权限的数据
			   classSql = businessDao.getClassesIdSqlByDeptIds(deptIds), // 符合异动源权限的数据（是在哪个机构异动的）
			   countSql = "select count(0) from"
				+ " (select distinct(change.stu_id) from (" +sql+ ")change, (" +classSql+ ") class"
				+ " where change.old_class_id = class.no)";
		return baseDao.queryForInt(countSql);
	}
	@Override
	public String queryStuChangeCountByDeptIdSql(int schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, 
			String start_date, String end_date, String change_codes){
		String sql = getChangeSql(schoolYear, deptList, stuAdvancedList, start_date, end_date, change_codes), // 符合权限的数据
			   classSql = businessDao.getClassesIdSqlByDeptList(deptList,schoolYear), // 符合异动源权限的数据（是在哪个机构异动的）
			   countSql = "select t.stu_id,stu.class_id,stu.MAJOR_ID,stu.dept_id from"
				+ " (select distinct(change.stu_id) from (" +sql+ ")change, (" +classSql+ ") class"
				+ " where change.old_class_id = class.no) t , t_stu stu where t.stu_id=stu.no_";
		return countSql;
	}
	
	@Override
	public List<Map<String, Object>> queryChangeMonth(int schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, 
			String start_date, String end_date, String change_codes){
		String changeSql = getChangeSql(schoolYear, deptList, stuAdvancedList, start_date, end_date, change_codes);
		String sql = "select count(0) value, month name from"
				+ " (select substr(t.date_, 6, 2) as month from (" +changeSql+ ") t) group by month order by month";
		return baseDao.queryListInLowerKey(sql, Types.NUMERIC, Types.NUMERIC);
	}
	@Override
	public String getStuChangeByDeptOrMajorSql(int schoolYear, List<String> deptList,
			List<AdvancedParam> stuAdvancedList, String start_date, String end_date, String id, String level,String type) {
		deptList=PmsUtils.getPmsAll();
		String changeSql=getChangeSql(schoolYear, deptList, stuAdvancedList, start_date, end_date, Constant.ChangeMajor_Code),sql=null;
		if(type.equals("out")){
			sql="select t.*,'out' as code from ("+changeSql+") t where t.old_"+level+"_id='"+id+"' and t.old_"+level+"_id !=t.now_"+level+"_id";
		}else{
			sql="select t.*,'in' as code from ("+changeSql+") t where t.now_"+level+"_id='"+id+"' and t.old_"+level+"_id !=t.now_"+level+"_id";
		}
		return sql;
	}

	@Override
	public List<Map<String, Object>> queryChangeBzdmList(String start_date, String end_date, String change_codes){
		change_codes = businessDao.formatInSql(change_codes);
		String matchingDateSql = getMatchingDateSql(start_date, end_date),
			   matchingCodeSql = change_codes==null ? "" : (" and t.stu_change_code in ("+change_codes+")");
		matchingDateSql = matchingDateSql!=null ? (" and "+matchingDateSql) : "";
		String sql = "select code.name_ name, code.code_ code from"
				+ " (select distinct(t.stu_change_code) code from t_stu_change t where 1=1" +matchingCodeSql+matchingDateSql+ ")t, t_code code"
				+ " where t.code=code.code_ and code.code_type='" +Constant.CODE_STU_CHANGE_CODE+ "'"
				+ " order by code.order_,code.code_";
		return baseDao.queryListInLowerKey(sql);
	}
	@Override
	public String getStuChangeDetailSql(List<AdvancedParam> advancedParamList, Map<String, Object> keyValue,List<String> fields,String change_codes,List<String> deptList){
		Integer schoolyear = EduUtils.getSchoolYear4();
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList);
		if(keyValue != null){
			for(Map.Entry<String, Object> entry : keyValue.entrySet()){
				if(entry.getKey().equals(Type_Year)){
				schoolyear=Integer.parseInt(((String.valueOf(entry.getValue())).substring(0,4)));
				}else{
				AdvancedUtil.add(stuAdvancedList, getDetailAdp(entry.getKey(), String.valueOf(entry.getValue())));
				}
			}
		}
		String changeSql=null,stuSql=null,stuChangeDetailSql=null;
		String start_date = null, end_date = null; // 查询时间段
		if(keyValue != null){
			for(Map.Entry<String, Object> entry : keyValue.entrySet()){
				String type = entry.getKey(), code = String.valueOf(entry.getValue());
				if(code!=null&&!code.equals("")){
				switch (type) {
				case Type_AllChange:
					changeSql=getChangeSql(schoolyear, deptList, stuAdvancedList, start_date, end_date, change_codes);
					stuSql="select stu.*,t.date_ from (select t.stu_id ,t.date_ from ("+changeSql+") t) t,t_stu stu where "
							+ "t.stu_id=stu.no_";
					break;
				case Type_BadChange:
					changeSql=getChangeSql(schoolyear, deptList, stuAdvancedList, start_date, end_date, change_codes);
					stuSql="select stu.*,t.date_ from (select t.stu_id ,t.date_ from ("+changeSql+") t where t.stu_change_code in ( "+StringUtils.join(Constant.Change_Bad_Code, ",")+" )) t,t_stu stu where "
							+ "t.stu_id=stu.no_";
					break;
				case Type_Type:
					// 符合日期条件的过滤sql；匹配code的sql
					changeSql=getChangeSql(schoolyear, deptList, stuAdvancedList, start_date, end_date, change_codes);
					stuSql="select stu.*,t.date_ from (select t.stu_id ,t.date_ from ("+changeSql+") t where t.stu_change_code in ( '"+code+"' )) t,t_stu stu where "
							+ "t.stu_id=stu.no_";
					break;
				case Type_Grade:
					String grade=MapUtils.getString(keyValue, Type_Grade);
					changeSql=getChangeSql(schoolyear, deptList, stuAdvancedList, start_date, end_date, change_codes);
					changeSql="select t.*,substr(t.school_year,0,4)+1-stu.enroll_grade as grade from ("+changeSql+") t,t_stu stu where t.stu_id=stu.no_";
					stuSql="select stu.* ,t.date_ from (select t.stu_id ,t.date_ from ("+changeSql+") t where grade="+grade+" ) t , t_stu stu where t.stu_id=stu.no_";
					break;
				case Type_Subject:
					changeSql=getChangeSql(schoolyear, deptList, stuAdvancedList, start_date, end_date, change_codes);
					changeSql = " select t.stu_id,t.date_, case when tp.id is null then 'null' else tp.id end id from (select stu_id,pid ,date_ from"
								+ " (select t.stu_id, t.date_,stu.major_id, dept.name_, dept.subject_id, sj.pid"
								+ " from (" + changeSql + ") t, t_stu stu, t_code_dept_teach dept"
								+ " left join (select t.id,t.name_, tp.name_ pname, tp.id pid" + " from "
								+ Constant.TABLE_T_Code_Subject_Degree + " t, " + Constant.TABLE_T_Code_Subject_Degree
								+ " tp" + " where substr(t.path_,1,4)=tp.path_ order by t.level_,t.pid,t.order_ ) sj"
								+ " on dept.subject_id=sj.id" + " where t.stu_id=stu.no_ and stu.major_id=dept.id) )t"
								+ " left join " + Constant.TABLE_T_Code_Subject_Degree + " tp on t.pid = tp.id"
								+ " order by tp.order_,tp.code_ ";
					stuSql="select stu.*,t.date_ from (select t.stu_id,t.date_ from ("+changeSql+") t where t.id ='"+code+"') t , t_stu stu where t.stu_id=stu.no_";
					 break;
				case Type_Sex:
					changeSql=getChangeSql(schoolyear, deptList, stuAdvancedList, start_date, end_date, change_codes);
					stuSql="select stu.* ,t.date_ from (select t.stu_id,t.date_ from ("+changeSql+") t ) t , t_stu stu where t.stu_id=stu.no_";
					break;
				case Type_Dept:
					changeSql=getChangeSql(schoolyear, deptList, stuAdvancedList, start_date, end_date, change_codes);
					String classSql = businessDao.getClassesIdSqlByDeptIds(code); // 符合异动源权限的数据（是在哪个机构异动的）
					stuSql = "select stu_id,date_ from"
								+ " (select change.stu_id, wm_concat(change.date_) as date_ from (" +changeSql+ ")change, (" +classSql+ ") class"
								+ " where change.old_class_id = class.no group by change.stu_id )";
					stuSql="select stu.*,t.date_ from ("+stuSql+" t ) t , t_stu stu where t.stu_id=stu.no_";
					break;
				case Type_Month:
					String month=code.replaceAll("[^(0-9)]", "");
					if(month.length()==1){
						month="0"+month;
					}
					changeSql=getChangeSql(schoolyear, deptList, stuAdvancedList, start_date, end_date, change_codes);
					stuSql="select stu_id,date_ from "
							+ "(select substr(t.date_, 6, 2) as month,t.stu_id,t.date_ from (" +changeSql+ ") t) where month='"+month+"'";
					stuSql="select stu.*,t.date_ from ("+stuSql+" ) t , t_stu stu where t.stu_id=stu.no_";
					break;
				case Type_Year:
					String[] schoolYearQuant = EduUtils.getTimeQuantum(schoolyear);
					 start_date = schoolYearQuant[0];
					 end_date  = schoolYearQuant[1];
					 changeSql=getChangeSql(schoolyear, deptList, stuAdvancedList, start_date, end_date, change_codes);
					 String deptIds=businessService.getSchoolId();
					 classSql = businessDao.getClassesIdSqlByDeptIds(deptIds); // 符合异动源权限的数据（是在哪个机构异动的）
				     stuSql = " (select change.stu_id, wm_concat(change.date_) as date_ from "
				     		+ "(select t.stu_id,t.old_class_id,t.date_ from ("+changeSql+") t )change, (" +classSql+ ") class "
								+ " where change.old_class_id = class.no group by change.stu_id ) t ";
					 stuSql="select stu.*,t.date_ from "+stuSql+" , t_stu stu where t.stu_id=stu.no_";
					 break;
				case Type_changeMajor:
					Map<String,Object> map=(Map<String,Object>)MapUtils.getMap(keyValue, Type_changeMajor);
					String tag=MapUtils.getString(map, "tag"),id=MapUtils.getString(map, "id");
					code=MapUtils.getString(map, "code");
					changeSql=getStuChangeByDeptOrMajorSql(schoolyear, deptList, stuAdvancedList, start_date, end_date, id,tag, code);
					stuSql="select stu.* ,t.date_ from (select t.stu_id,t.date_ from ("+changeSql+") t ) t , t_stu stu where t.stu_id=stu.no_";
					break;
				case "again":
					schoolyear=Integer.parseInt(code.substring(0,4));
					schoolYearQuant = EduUtils.getTimeQuantum(schoolyear);
					start_date = schoolYearQuant[0];
					end_date  = schoolYearQuant[1];
					changeSql=getChangeSql(schoolyear, deptList, stuAdvancedList, start_date, end_date, null);
					changeSql="select stu_id,max(date_) as date_ from ("+changeSql+") group by stu_id";
					String sql = "select t.stu_id from t_stu_change t , (select t.stu_id, max(t.date_) as date_ from t_stu_change t, (" +changeSql+ ") z "
							+ " where t.date_ < z.date_ and t.stu_id=z.stu_id group by t.stu_id ) change "
							+ " where  t.stu_id=change.stu_id and t.date_=change.date_ and t.stu_change_code in ("+change_codes+")";
					stuSql="select stu.* ,'null' as date_ from (select t.stu_id from ("+sql+") t ) t , t_stu stu where t.stu_id=stu.no_";
					break;
				default:
					break;
				}
				}
			}
		}
		String stuDetailSql = getStuDetailSql(stuSql);
		if(fields!=null && !fields.isEmpty()){
			stuChangeDetailSql =  "select "+StringUtils.join(fields, ",")+ " from ("+stuDetailSql+") ";
		}
		return stuChangeDetailSql;
	}
	private AdvancedParam getDetailAdp(String type, String value){
		AdvancedParam adp = null;
		if(type != null){
			String group = AdvancedUtil.Group_Stu, code = null;
			switch (type) {
			case Type_Sex:
				group = AdvancedUtil.Group_Common;
				code  = AdvancedUtil.Common_SEX_CODE;
				break;
			case Type_Dept:
				group = AdvancedUtil.Group_Common;
				code  = AdvancedUtil.Common_DEPT_TEACH_ID;
				break;
			default:
				break;
			}
			adp = new AdvancedParam(group, code, value);
		}
		return adp;
	}
	private String getStuDetailSql(String stuSql){
		String sql = "select t.id,t.no_ no,t.name_ name, sex.name_ sexmc, sex.code_ sexid, dept.name_ deptmc, t.dept_id deptid,"
				+ " major.name_ majormc, t.major_id majorid, class.name_ classmc, t.class_id,t.enroll_grade,t.length_schooling,"
				+ " xzqh.sname shengmc, xzqh.sid shengid, shi.sname shimc, shi.sid shiid, shi.name xianmc, shi.id xianid,t.date_ changedate "
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
	public int queryPunishAgainCount(int schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList,
			String start_date, String end_date, String change_codes){
		String changeSql=getChangeSql(schoolYear, deptList, stuAdvancedList, start_date, end_date, null);
		changeSql="select stu_id,max(date_) as date_ from ("+changeSql+") group by stu_id";
		//changeSql="select distinct(t.stu_id) from ("+changeSql+") t";
		String sql = "select count(0) count from "
				+ " (select t.stu_id from t_stu_change t , (select t.stu_id, max(t.date_) as date_ from t_stu_change t, (" +changeSql+ ")z"
				+ " where t.date_ < z.date_ and t.stu_id=z.stu_id group by t.stu_id ) change "
				+ " where  t.stu_id=change.stu_id and t.date_=change.date_ and t.stu_change_code in ("+change_codes+"))";
		return baseDao.queryForInt(sql);
	} 
}
