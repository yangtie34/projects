package com.jhnu.person.manage.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.person.manage.dao.TeaStatisticsDao;
@Repository("teaStatisticsDao")
public class TeaStatisticsDaoImpl implements TeaStatisticsDao {
	@Autowired
	private BaseDao basedao;
	@Override
	public List<Map<String, Object>> getSchoolName(){//院系名称
		String sql = "select a.name_ as name ,a.id from t_code_dept_teach a where a.pid = '0' and a.istrue = 1 " ;
		List<Map<String,Object>> yx = basedao.getBaseDao().querySqlList(sql);
//		if (yx.isEmpty()){
//			sql = "select a.name_ as name,a.no_ as id from t_classes a where a.teach_dept_id = '"+zzjgid+"' and a.islive='1' ";
//		}
		return basedao.getBaseDao().querySqlList(sql);
	}
	@Override
	public List<Map<String, Object>> getRs(String pid) {//性别比例
		String st_xl="and 1=1";
		if(pid.equals("0")==false){
			st_xl="and t.dept_id="+pid;
		}
		// 男生数量 女生数量
		String sql="select tc.name_ ,count(*) COUNT_ "
				  +" from t_tea t "
				  +" left  join t_code tc on tc.code_=t.sex_code and tc.code_type='SEX_CODE'  "
				  +" where t.sex_code is not null  "
				  + st_xl
				  +" group by tc.name_"	;
		List list=new ArrayList();
		Map map=new HashMap();
		map.put("xbrs", basedao.getBaseDao().querySqlList(sql));
		//教职工类别_CODE
		sql="select tc5.name_ ,count(*) COUNT_ "
				  +" from t_tea t "
				  + "left join t_code tc5 on tc5.code_ = t.AUTHORIZED_STRENGTH_ID and tc5.code_type like '%AUTHORIZED_STRENGTH%' "
				  +" where 1=1  "
				  + st_xl
				  +" group by tc5.name_"	;
		map.put("zglb", basedao.getBaseDao().querySqlList(sql));
		//编制类别_CODE
				sql="select tc3.name_ ,count(*) COUNT_ "
						  +" from t_tea t "
						+ "left join t_code tc3 on tc3.code_ = t.BZLB_CODE  and tc3.code_type='BZLB_CODE' "
						  +" where 1=1  "
						  + st_xl
						  +" group by tc3.name_"	;
				map.put("bzlb", basedao.getBaseDao().querySqlList(sql));
		list.add(map);
		return list;
	}
	@Override
	public List<Map<String, Object>> getJszt(String pid) {//教师状态
		// TODO Auto-generated method stub
		String sql="";
		return basedao.getBaseDao().querySqlList(sql);
	}
	@Override
	public List<Map<String, Object>> getNl(String pid) {//年龄分布
		String st_xl="1=1";
		if(pid.equals("0")==false){
			st_xl="tt.dept_id="+pid;
		}
		//教师年龄
		String sql="select field, sum(value) as value ,nvl(t.name_,'未维护') name"
				  +" from (select tcdt.name_, case when nvl(floor(MONTHS_BETWEEN(sysdate, to_date(tt.birthday,'yyyy-mm-dd')) / 12), '0') < 17 then '16岁及以下' "
				  +"              when nvl(floor(MONTHS_BETWEEN(sysdate,to_date(tt.birthday,'yyyy-mm-dd')) / 12),'0') > 26 then '27岁及以上' "
				  +"               else "
				  +"               to_char(nvl(floor(MONTHS_BETWEEN(sysdate,to_date(tt.birthday,'yyyy-mm-dd')) / 12), '0')) "
				  +"             end as field,  "
				  +"             count(*) as value "
				  +"       from t_tea tt "
				  +"       left join t_code_dept_teach tcdt on tcdt.id=tt.dept_id"
				  +"       where  "+st_xl
				  +"       group by tcdt.name_, nvl(floor(MONTHS_BETWEEN(sysdate,to_date(tt.birthday, 'yyyy-mm-dd')) / 12), '0'))  t "
				  +" group by field ,t.name_"
				  +" order by field ";
	return basedao.getBaseDao().querySqlList(sql);
	}
	@Override
	public List<Map<String, Object>> getMz(String pid) {//民族组成
		String st_xl="1=1";
		if(pid.equals("0")==false){
			st_xl="tt.dept_id="+pid;
		}
		// 民族名称  数量
		String sql="select a.name_ as field, count(*) as value, '民族' name "
				  +" from t_tea tt "
				  +" left join (select * from t_code code where code.code_type = 'NATION_CODE') a "
				  +" on tt.NATION_CODE = a.code_ "
				  +"  where  "+st_xl
				  +" and a.name_ is not null "
				  +" group by a.name_ "
				  +" order by count(*) desc";
		return basedao.getBaseDao().querySqlList(sql);
	}
	@Override
	public List<Map<String, Object>> getZzmm(String pid) {//政治面貌
		String st_xl="1=1";
		if(pid.equals("0")==false){
			st_xl="tt.dept_id="+pid;
		}
		// 面貌名称 数量
		String sql="SELECT TC.NAME_ field ,COUNT(*) value ,'教师' NAME"
					+" FROM T_TEA TT "
					+" LEFT JOIN T_CODE TC ON TC.CODE_=TT.POLITICS_CODE AND TC.CODE_TYPE= 'POLITICS_CODE' " 
					+" WHERE  "+st_xl
					+" and tc.name_ is not null"
					+" GROUP BY TC.NAME_   ";
		return basedao.getBaseDao().querySqlList(sql);
	}
	@Override
	public List<Map<String, Object>> getLyd(String pid) {//教师来源地
		String st_xl="1=1";
		if(pid.equals("0")==false){
			st_xl="tt.dept_id="+pid;
		}
		// 来源地名称 数量
		String sql="SELECT TCTS.NAME_ name,COUNT(*) value "
					+" FROM T_TEA TT "
					+" LEFT JOIN T_CODE_ADMINI_DIV TCTS ON TCTS.ID=TT.PLACE_DOMICILE "
					+" WHERE  "+st_xl
					+" and TCTS.NAME_ is not null"
					+" GROUP BY TCTS.NAME_";
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
	public List<Map<String, Object>> getXl(String pid) {//学历组成
		String st_xl="1=1";
		if(pid.equals("0")==false){
			st_xl="tt.dept_id="+pid;
		}
		// 学历名称 数量
		String sql="SELECT TCE.NAME_  FIELD,COUNT(*) value "
					+" FROM T_TEA TT "
					+" LEFT JOIN T_CODE_EDUCATION TCE ON TCE.ID=TT.EDU_ID "
					+" WHERE  "
					+st_xl
					+" and TCE.NAME_ IS NOT NULL"
					+" GROUP BY TCE.NAME_";
		/*List<Map<String, Object>> xl = basedao.getBaseDao().querySqlList(sql);
		List<Map<String, Object>> xl1 =  new ArrayList<Map<String,Object>>();
		for (int i=0;i<xl.size();i++){
			Map<String,Object> temp = new HashMap<String,Object>();
			Map<String, Object> map = xl.get(i);
			temp.put("FIELD", map.get("NAME").toString());
			temp.put("VALUE", map.get("VALUE").toString());
			xl1.add(temp);
		}*/
		return basedao.getBaseDao().querySqlList(sql);
	}
	@Override
	public List<Map<String, Object>> getZcjb(String pid) {//职称级别统计
		String st_xl="1=1";
		if(pid.equals("0")==false){
			st_xl="tt.dept_id="+pid;
		}
		//TODO职称级别
		String sql="SELECT TCZ.NAME_ FIELD,COUNT(*) VALUE "
					+" FROM T_TEA TT "
					+" LEFT JOIN T_CODE_ZYJSZW TCZ ON TCZ.ID=TT.ZYJSZW_ID  "
					+" WHERE   "
					+st_xl
					+" AND TCZ.NAME_ IS NOT NULL"
					+" GROUP BY TCZ.NAME_";
		return basedao.getBaseDao().querySqlList(sql);
	}
	@Override
	public List<Map<String, Object>> getZc(String pid) {//职称统计
		String st_xl="1=1";
		if(pid.equals("0")==false){
			st_xl="tt.dept_id="+pid;
		}
		String sql="SELECT TCZ.NAME_ FIELD,COUNT(*) VALUE "
					+" FROM T_TEA TT "
					+" LEFT JOIN T_CODE_ZYJSZW TCZ ON TCZ.ID=TT.ZYJSZW_ID  "
					+" WHERE   "
					+st_xl
					+" AND TCZ.NAME_ IS NOT NULL"
					+" GROUP BY TCZ.NAME_";
		return basedao.getBaseDao().querySqlList(sql);
	}
	@Override
	public List<Map<String, Object>> queryZzmm1(String Id){
		Map node = getZzjgNode(Id);
		String sql = "(select count(*) as rs from t_tea stu left join (select * from  t_code code where "
				+ " code.code_type = 'POLITICS_CODE' and code.code_ = '01' ) a on stu.POLITICS_CODE = a.code_ where  stu.DEPT_ID in (select id from t_code_dept_teach tea where tea.path_ like '"+node.get("qxm")+"%') "
						+ " and a.name_ is not null )";
		String sql1 = "(select count(*) as rs from t_tea stu left join (select * from  t_code code where "
				+ " code.code_type = 'POLITICS_CODE'  and code.code_ <> '03' ) a on stu.POLITICS_CODE = a.code_ where  stu.DEPT_ID in (select id from t_code_dept_teach tea where tea.path_ like '"+node.get("qxm")+"%') "
						+ " and a.name_ is not null)";
		String sql2 = "(select count(*) as rs from t_tea stu left join (select * from  t_code code where "
				+ " code.code_type = 'POLITICS_CODE') a on stu.POLITICS_CODE = a.code_ where   stu.DEPT_ID in (select id from t_code_dept_teach tea where tea.path_ like '"+node.get("qxm")+"%') "
						+ " and a.name_ is null)";
		List<Map<String, Object>> zs =  new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> ty = basedao.getBaseDao().querySqlList(sql);
		List<Map<String, Object>> bsty = basedao.getBaseDao().querySqlList(sql1);
		List<Map<String, Object>> wwh = basedao.getBaseDao().querySqlList(sql2);
		zs.addAll(0,ty);zs.addAll(1,bsty);zs.addAll(2,wwh);
		return zs;	
	}
	@Override
	public List<Map<String, Object>> getMzCount(String pid) {
		Map node = getZzjgNode(pid);
		String hz = "select count(*) as value from t_tea stu left join (select * from t_code code where code.code_type = 'NATION_CODE') a on stu.NATION_CODE = a.code_ where  stu.DEPT_ID in (select id from t_code_dept_teach tea where tea.path_ like '"+node.get("qxm")+"%') "
				+ " and a.name_ = '汉族' ";
String ssmz = "select count(*) as value from t_tea stu left join (select * from t_code code where code.code_type = 'NATION_CODE') a on stu.NATION_CODE = a.code_ where  stu.DEPT_ID in (select id from t_code_dept_teach tea where tea.path_ like '"+node.get("qxm")+"%') "
		+ " and a.name_ <> '汉族' ";
String wwh = "select count(*) as value from t_tea stu left join (select * from t_code code where code.code_type = 'NATION_CODE') a on stu.NATION_CODE = a.code_ where  stu.DEPT_ID in (select id from t_code_dept_teach tea where tea.path_ like '"+node.get("qxm")+"%') "
		+ " and a.name_ is null ";
List<Map<String, Object>> zs =  new ArrayList<Map<String,Object>>();
List<Map<String, Object>> hzrs = basedao.getBaseDao().querySqlList(hz);
List<Map<String, Object>> ssmzrs = basedao.getBaseDao().querySqlList(ssmz);
List<Map<String, Object>> wwh1 = basedao.getBaseDao().querySqlList(wwh);
zs.addAll(0,hzrs);zs.addAll(1,ssmzrs);zs.addAll(2,wwh1);
return zs;
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
