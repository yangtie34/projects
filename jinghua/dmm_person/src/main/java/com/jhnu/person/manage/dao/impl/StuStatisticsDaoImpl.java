package com.jhnu.person.manage.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.person.manage.dao.StuStatisticsDao;
@Repository("stuStatisticsDao")
public class StuStatisticsDaoImpl implements StuStatisticsDao {
	@Autowired
	private BaseDao basedao;
	@Override
	public List<Map<String, Object>> getSchoolName(String pid){
		String zzjgid = pid.equals("")?"0":pid;
		String sql = "select a.name_ as name ,a.id from t_code_dept_teach a where a.pid = '"+zzjgid+"' and a.istrue = 1 " ;
		List<Map<String,Object>> yx = basedao.getBaseDao().querySqlList(sql);
		if (yx.isEmpty()){
			sql = "select a.name_ as name,a.no_ as id from t_classes a where a.teach_dept_id = '"+zzjgid+"' and a.islive='1' ";
		}
		return basedao.getBaseDao().querySqlList(sql);
	}
	@Override
	public List<Map<String, Object>> getYxdName(String pid){
		String id = pid.equals("")?"0":pid;
		List<Map<String, Object>> li = new ArrayList<Map<String,Object>>();
		while(id != "-1"){
			Map<String, Object> map = getM(id);
			if(map != null){
				li.add(0,map);
				id = (String) map.get("PID");
			}else{
				id = "-1";
			}
		}
		return li;
	}
	private Map<String, Object> getM(String id){
		String sql = "select a.name_ as name,a.id,a.pid from t_code_dept_teach a where id = '"+id+"' and istrue = 1 " ;
		List<Map<String, Object>> li = basedao.getBaseDao().querySqlList(sql);
		if (li.isEmpty()){
			 sql = "select a.name_ as name,a.no_ as id,a.teach_dept_id as pid from t_classes a where a.no_ = '"+id+"' and a.islive='1'";
		}
		List<Map<String, Object>> bj = basedao.getBaseDao().querySqlList(sql);
		return bj.isEmpty() ? null : bj.get(0);
	}

	@Override
	public List<Map<String, Object>> getRs(String Id,String stu_id){
		String zzid = Id.equals("")?"0":Id;
		Map node = getZzjgNode(zzid);
		String st_xl="1=1";
		if(stu_id.equals("")==false){
			st_xl="stu.edu_id="+stu_id;
		}
			
		
		
		
		String sql = "select nvl(count(*),0) as rs from t_stu stu  where stu.major_id in (select id from t_code_dept_teach tea where tea.path_ like '"+node.get("qxm")+"%')"
				+ " and STU_ROLL_CODE = '1' and "+st_xl+"";
		String sql1 = "select a.name_ as name from t_code_dept_teach a where a.id='"+zzid+"'";
		String sql2 = "select nvl(count(*),0) as count from t_code_dept_teach b where b.path_ like '"+node.get("qxm")+"%' and b.istrue='1' and b.level_type = 'ZY'";
		String sql3 = "select nvl(count(*),0) as nanrs from t_stu stu  where stu.major_id in (select id from t_code_dept_teach tea where tea.path_ like '"+node.get("qxm")+"%')"
				+ " and stu.STU_ROLL_CODE = '1' and "+st_xl+" and stu.sex_code='1' ";
		String sql4 = "select nvl(count(*),0) as nvrs from t_stu stu  where stu.major_id in (select id from t_code_dept_teach tea where tea.path_ like '"+node.get("qxm")+"%')"
				+ " and stu.STU_ROLL_CODE = '1' and "+st_xl+" and stu.sex_code='2'";
		List<Map<String, Object>> zs =  new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> rs = basedao.getBaseDao().querySqlList(sql);
		List<Map<String, Object>> xx = basedao.getBaseDao().querySqlList(sql1);
		List<Map<String, Object>> zy = basedao.getBaseDao().querySqlList(sql2);
		List<Map<String, Object>> nan = basedao.getBaseDao().querySqlList(sql3);
		List<Map<String, Object>> nv = basedao.getBaseDao().querySqlList(sql4);
		zs.addAll(0,xx);zs.addAll(1,rs);zs.addAll(2,zy);zs.addAll(3,nan);zs.addAll(4,nv);
		return zs;
	}
	@Override
	public  List<Map<String, Object>> getMzCount(String Id,String stu_id){
		String zzid = Id.equals("")?"0":Id;
		Map node = getZzjgNode(zzid);
		String st_xl="1=1";
		if(stu_id.equals("")==false){
			st_xl="stu.edu_id="+stu_id;
		}
		String hz = "select count(*) as value from t_stu stu left join (select * from t_code code where code.code_type = 'NATION_CODE') a on stu.NATION_CODE = a.code_ where stu.stu_roll_code = '1' and stu.major_id in (select id from t_code_dept_teach tea where tea.path_ like '"+node.get("qxm")+"%') "
						+ " and a.name_ = '汉族' and "+st_xl+" ";
		String ssmz = "select count(*) as value from t_stu stu left join (select * from t_code code where code.code_type = 'NATION_CODE') a on stu.NATION_CODE = a.code_ where stu.stu_roll_code = '1' and stu.major_id in (select id from t_code_dept_teach tea where tea.path_ like '"+node.get("qxm")+"%') "
				+ " and a.name_ <> '汉族' and "+st_xl+"";
		String wwh = "select count(*) as value from t_stu stu left join (select * from t_code code where code.code_type = 'NATION_CODE') a on stu.NATION_CODE = a.code_ where stu.stu_roll_code = '1' and stu.major_id in (select id from t_code_dept_teach tea where tea.path_ like '"+node.get("qxm")+"%') "
				+ " and a.name_ is null and "+st_xl+"";
		List<Map<String, Object>> zs =  new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> hzrs = basedao.getBaseDao().querySqlList(hz);
		List<Map<String, Object>> ssmzrs = basedao.getBaseDao().querySqlList(ssmz);
		List<Map<String, Object>> wwh1 = basedao.getBaseDao().querySqlList(wwh);
		zs.addAll(0,hzrs);zs.addAll(1,ssmzrs);zs.addAll(2,wwh1);
		return zs;
	}
	@Override
	public List<Map<String, Object>> getXl(String Id){
		String zzid = Id.equals("")?"0":Id;
		Map node = getZzjgNode(zzid);
		/*String xx = "select stu.edu_id CODE_ , case stu.edu_id  when '20' then '本科生' when '31' then'专科生' when '11' then '研究生' end as name, 0 as rs from t_stu stu  where"
				+ " stu.STU_ROLL_CODE = '1' and stu.major_id in (select id from t_code_dept_teach tea where tea.path_ like '0000%') group by stu.edu_id";*/
//		String sql = "select stu.edu_id CODE_ , case stu.edu_id  when '20' then '本科生' when '31' then'专科生' when '11' then '研究生' end as name, count(*) rs from t_stu stu  where"
//				+ " stu.STU_ROLL_CODE = '1' and stu.major_id in (select id from t_code_dept_teach tea where tea.path_ like '"+node.get("qxm")+"%') group by stu.edu_id";
	String sql="SELECT TCE.code_, nvl(rs,0) rs "
			  +" FROM T_CODE_EDUCATION TCE "
			  +" left join (SELECT TS.EDU_ID, COUNT(*) rs, A.NAME_ "
			  +"             FROM T_CODE_EDUCATION a, T_STU TS "
			  +"            where TS.STU_ROLL_CODE = '1' "
			  +"              and a.CODE_ = TS.EDU_ID "
			  +" and ts.major_id in (select id from t_code_dept_teach tea where tea.path_ like '"+node.get("qxm")+"%')"
			  +"            GROUP BY TS.EDU_ID, A.NAME_) a on tce.code_ = a.edu_id "
			  +" where TCE.CODE_ in ('20', '31', '11') "
			  +" order by decode (TCE.code_,'20',1, '31',2, '11',3) ";
		/*List<Map<String, Object>> xx1 = basedao.getBaseDao().querySqlList(xx);*/
		List<Map<String, Object>> sql1 = basedao.getBaseDao().querySqlList(sql);
		/*List<Map<String, Object>> jg =  new ArrayList<Map<String,Object>>();
		for (int i= 0;i<xx1.size();i++){
			Map<String, Object> map = xx1.get(i);
			if(sql1.size()!=0){
				for (int j=0;j<sql1.size();j++){
					Map<String, Object> map1=sql1.get(j);
					 if (map.get("NAME").equals(map1.get("NAME"))){
						 jg.add(map1);
					 }
					}
			}else{
				 jg.add(map);
			}
			if(jg.size()<i+1){
				 jg.add(map);
			}
		}*/
		return sql1;
	}
	@Override
	public  List<Map<String, Object>> getSyd(String Id,String stu_id){
		String zzid = Id.equals("")?"0":Id;
		Map node = getZzjgNode(zzid);
		String st_xl="1=1";
		if(stu_id.equals("")==false){
			st_xl="stu.edu_id="+stu_id;
		}
		String sql  = "select jg.name_ as name,count(*) as value from t_stu stu inner join t_code_admini_div jg on substr(stu.stu_origin_id,0,2)||'0000' = jg.id where "
				+ " stu.STU_ROLL_CODE = '1' and stu.major_id in (select id from t_code_dept_teach tea where tea.path_ like '"+node.get("qxm")+"%') and "+st_xl+" group by jg.name_"; 
		List<Map<String, Object>> syd = basedao.getBaseDao().querySqlList(sql);
		List<Map<String, Object>> syd1 =  new ArrayList<Map<String,Object>>();
		for (int i=0;i<syd.size();i++){
			Map<String,Object> temp = new HashMap<String,Object>();
			Map<String, Object> map = syd.get(i);
			temp.put("name", map.get("NAME").toString());
			temp.put("value", map.get("VALUE").toString());
			syd1.add(temp);
		}
		return syd1;
	}
	@Override
	public List<Map<String, Object>> getNl(String Id,String stu_id){
		String zzid = Id.equals("")?"0":Id;
		Map node = getZzjgNode(zzid);
		String st_xl="1=1";
		if(stu_id.equals("")==false){
			st_xl="stu.edu_id="+stu_id;
		}
		String sql = "select field,name,sum(value) as value from (select case when nvl(floor(MONTHS_BETWEEN(sysdate,to_date(stu.birthday,'yyyy-mm-dd'))/12),'0')<17 then '16岁及以下' when nvl(floor(MONTHS_BETWEEN(sysdate,to_date(stu.birthday,'yyyy-mm-dd'))/12),'0')>26 then '27岁及以上'else to_char(nvl(floor(MONTHS_BETWEEN(sysdate,to_date(stu.birthday,'yyyy-mm-dd'))/12),'0')) end"
				+ " as field,stu.enroll_grade||'级' as name,count(*) as value from t_stu stu where stu.STU_ROLL_CODE = '1' and "+st_xl+" and stu.major_id in (select id from t_code_dept_teach tea where tea.path_ like '"+node.get("qxm")+"%')"
				+ " and stu.enroll_grade >to_char(sysdate-365*5,'yyyy') group by nvl(floor(MONTHS_BETWEEN(sysdate,to_date(stu.birthday,'yyyy-mm-dd'))/12),'0'),stu.enroll_grade order by stu.enroll_grade) group by field,name order by name";
		return basedao.getBaseDao().querySqlList(sql);
	}
	@Override
	public List<Map<String, Object>> getRydb(String Id){
		String zzid = Id.equals("")?"0":Id;
		Map node = getZzjgNode(zzid);
		String sql = "";
		if (zzid.equals("0")){
			   sql = "select tea.id,case stu.edu_id  when '31' then '专科生' when '20' then'本科生' when '11' then '研究生' else '未维护' end as name,tea.name_ as field,nvl(count(*),0) as value  "
					+ " from t_code_dept_teach tea left join t_stu stu on tea.id = stu.dept_id where stu.stu_roll_code = '1' and tea.path_ like'"+node.get("qxm")+"%' and tea.level_type = 'YX' and tea.istrue = 1  group by tea.name_,stu.edu_id,tea.id order by stu.edu_id,tea.id";
		}else{
			 sql = "select tea.id,case stu.edu_id  when '31' then '专科生' when '20' then'本科生' when '11' then '研究生' else '未维护' end as name,tea.name_ as field,nvl(count(*),0) as value  "
						+ " from t_code_dept_teach tea left join t_stu stu on tea.id = stu.major_id where stu.stu_roll_code = '1' and tea.path_ like'"+node.get("qxm")+"%' and tea.istrue = 1  group by tea.name_,stu.edu_id,tea.id order by stu.edu_id,tea.id";
		}
	return basedao.getBaseDao().querySqlList(sql);
	}
	@Override
	public List<Map<String, Object>> getZzmm1(String Id,String stu_id){
		String zzid = Id.equals("")?"0":Id;
		Map node = getZzjgNode(zzid);
		String st_xl="1=1";
		if(stu_id.equals("")==false){
			st_xl="stu.edu_id="+stu_id;
		}
		String sql = "(select count(*) as rs from t_stu stu left join (select * from  t_code code where "
				+ " code.code_type = 'POLITICS_CODE' and code.code_ = '03' ) a on stu.POLITICS_CODE = a.code_ where stu.stu_roll_code = '1' and stu.edu_id='"+st_xl+"' and stu.major_id in (select id from t_code_dept_teach tea where tea.path_ like '"+node.get("qxm")+"%') "
						+ " and a.name_ is not null )";
		String sql1 = "(select count(*) as rs from t_stu stu left join (select * from  t_code code where "
				+ " code.code_type = 'POLITICS_CODE'  and code.code_ <> '03' ) a on stu.POLITICS_CODE = a.code_ where stu.stu_roll_code = '1' and "+st_xl+" and stu.major_id in (select id from t_code_dept_teach tea where tea.path_ like '"+node.get("qxm")+"%') "
						+ " and a.name_ is not null)";
		String sql2 = "(select count(*) as rs from t_stu stu left join (select * from  t_code code where "
				+ " code.code_type = 'POLITICS_CODE') a on stu.POLITICS_CODE = a.code_ where stu.stu_roll_code = '1' and "+st_xl+" and stu.major_id in (select id from t_code_dept_teach tea where tea.path_ like '"+node.get("qxm")+"%') "
						+ " and a.name_ is null)";
		List<Map<String, Object>> zs =  new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> ty = basedao.getBaseDao().querySqlList(sql);
		List<Map<String, Object>> bsty = basedao.getBaseDao().querySqlList(sql1);
		List<Map<String, Object>> wwh = basedao.getBaseDao().querySqlList(sql2);
		zs.addAll(0,ty);zs.addAll(1,bsty);zs.addAll(2,wwh);
		return zs;	
	}
	@Override
	public List<Map<String, Object>> getZzmm(String Id,String stu_id){
		String zzid = Id.equals("")?"0":Id;
		Map node = getZzjgNode(zzid);
		String st_xl="1=1";
		if(stu_id.equals("")==false){
			st_xl="stu.edu_id="+stu_id;
		}
		String sql1 = "(select a.name_ as mc,count(*) as rs from t_stu stu left join (select * from  t_code code where "
				+ " code.code_type = 'POLITICS_CODE') a on stu.POLITICS_CODE = a.code_ where stu.stu_roll_code = '1' and "+st_xl+" and stu.major_id in (select id from t_code_dept_teach tea where tea.path_ like '"+node.get("qxm")+"%') "
						+ " and a.name_ is not null group by a.name_)";
		String sql = "select * from (select a.name,a.field,a.value,b.rs from (select name ,field,value from (select case stu.edu_id  when '31' then '专科生' when '20' then'本科生' when '11' then '研究生' else '未维护' end as name,a.name_ as field,count(*) as value from t_stu stu left join(select * from  t_code code where"
				+ " code.code_type = 'POLITICS_CODE' and code.code_ <> '03' ) a on stu.POLITICS_CODE = a.code_ where stu.stu_roll_code = '1' and "+st_xl+" and stu.major_id in (select id from t_code_dept_teach tea where tea.path_ like '"+node.get("qxm")+"%') and a.name_ is not null  group by stu.edu_id,a.name_ order by stu.edu_id )"
						+ "group by name ,field,value)a left join "+sql1+" b  on a.field = b.mc group by a.name,a.field,a.value,b.rs order by b.rs) order by rs,name ";
		return  basedao.getBaseDao().querySqlList(sql);
	}
	@Override
	public List<Map<String, Object>> getMz(String Id,String stu_id){
		String zzid = Id.equals("")?"0":Id;
		Map node = getZzjgNode(zzid);
		String st_xl="1=1";
		if(stu_id.equals("")==false){
			st_xl="stu.edu_id="+stu_id;
		}
		String sql = "select * from (select a.name_ as field,count(*) as value,'民族' as name  from t_stu stu left join (select * from t_code code where code.code_type = 'NATION_CODE') a on stu.NATION_CODE = a.code_ where stu.stu_roll_code = '1' and "+st_xl+" and stu.major_id in (select id from t_code_dept_teach tea where tea.path_ like '"+node.get("qxm")+"%') "
						+ " and a.name_ <> '汉族'  group by a.name_,'民族') order by value desc";
		return basedao.getBaseDao().querySqlList(sql);
	}
	protected Map getZzjgNode(String Id ){
		Map backVal = new HashMap();
		String sql ="Select * from t_code_dept_teach where id ='"+Id+"'";
		List<Map<String, Object>> result = basedao.getBaseDao().querySqlList(sql);;
		if(result.size()!=0){
			backVal.put("qxm",result.get(0).get("PATH_"));
		}
		return backVal;
	}

}
