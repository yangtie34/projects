package cn.gilight.framework.uitl.common;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.code.Code;
import cn.gilight.product.card.dao.CardTjUtil;

import com.jhnu.syspermiss.GetCachePermiss;

public class SqlUtil {
	
	/**
	 * 将查询出来的结果集与教学组织机构结合，根据deptTeachs关联下一级的ID,NAME
	 * @param deptTeachs 父节点的值
	 * @param querySql 要查询出的结果集
	 * @param isClass 是否精确到班级
	 * @param joinType 关联方式
	 * @return
	 */
	public static String getDeptTeachGroup(Map<String,String> deptTeachs,String querySql,boolean isClass,String joinType){
		if(isClass){
			String sql="select b.*,a.id next_dept_code,a.name_ next_dept_name "+
					 " from ( select id, name_, path_ "+
					 "         from (select id, name_, pid, path_ "+
					 "                 from t_code_dept_teach "+
					 "               union all "+
					  "              select cl.no_ id, "+
					  "                     cl.name_, "+
					  "                     cl.teach_dept_id pid, "+
					  "                     cdt.path_ || cl.no_ path_ "+
					  "                from t_classes cl "+
					  "               inner join t_code_dept_teach cdt "+
					  "                  on cl.teach_dept_id = cdt.id) "+
					  "       where pid = '"+deptTeachs.get("id")+"' "+
					  "       group by id, name_, path_) a "+
					  joinType+
					 "(select s.*,cdt.path_ || cl.no_ path_ "+
					 "   from ("+querySql+") s "+
					 "  inner join t_classes cl "+
					 "     on s.class_id = cl.no_ "+
					 "  inner join t_code_dept_teach cdt "+
					 "     on cl.teach_dept_id = cdt.id "+
					 "     ) b "+
					 "   on b.path_ like a.path_ || '%' ";
			return sql;
		}else{
			String pid="pid";
			if("3".equals(MapUtils.getString(deptTeachs, "level"))){
				pid="id";
			}
			String sql="select b.*,a.id next_dept_code,a.name_ next_dept_name "+
						 " from ( select id, name_, path_ "+
						 "         from t_code_dept_teach  "+    
						 "        where "+pid+" = '"+deptTeachs.get("id")+"'  "+
						 "        group by id, name_, path_) a "+
						 joinType+
						" (select s.*,cdt.path_  "+
						"    from ("+querySql+") s "+
						"   inner join t_code_dept_teach cdt "+
						"      on S.MAJOR_ID = cdt.id "+
						"      ) b  on b.path_ like a.path_ || '%'";
			return sql;
		}
	}

	/**
	 * 通过权限，获取教学组织机构筛选条件
	 * @param deptTeach 教学组织机构
	 * @param shiroTag 权限
	 * @param name 要筛选条件的表的别名
	 * @return
	 */
	public static String getDeptTeachTj(Map<String,String> deptTeach,String shiroTag,String name){
		String tj=" ";
		if(deptTeach==null){
			return tj;
		}
		if(name==null){
			name="";
		}else{
			name+=".";
		}
		if("0".equals(MapUtils.getString(deptTeach, "istrue"))){
			List<String> b=GetCachePermiss.getDataServeSqlbyUserIdShrio(UserUtil.getCasLoginName(), shiroTag);
			List<String> list=GetCachePermiss.getDataByDeptAndData("deptTeach",deptTeach.get("id"),b);
			tj=" and( "+name+"dept_id in ("+list.get(1)+") or "+name+"major_id in (select id from t_code_dept_teach where pid in ("+list.get(2)+") ) or "+name+"major_id in ("+list.get(3)+") ) ";
		}else{
			if("0".equals(MapUtils.getString(deptTeach, "level"))){
				
			}else if("1".equals(MapUtils.getString(deptTeach, "level"))){
				tj=" and "+name+"dept_id='"+deptTeach.get("id")+"' ";
			}else if("2".equals(MapUtils.getString(deptTeach, "level"))){
				tj=" and "+name+"major_id in (select id from t_code_dept_teach where pid='"+deptTeach.get("id")+"') ";
			}else if("3".equals(MapUtils.getString(deptTeach, "level"))){
				tj=" and "+name+"major_id='"+deptTeach.get("id")+"' ";
			}
		}
		return tj;
	}
	
	/**
	 * 通过权限，获取组织机构筛选条件
	 * @param deptTeach 组织机构
	 * @param shiroTag 权限
	 * @param name 要筛选条件的表的别名
	 * @return
	 */
	public static String getDeptTj(Map<String,String> dept,String shiroTag,String name){
		String tj=" ";
		if(dept==null){
			return tj;
		}
		if(name==null){
			name="";
		}else{
			name+=".";
		}
		if("0".equals(MapUtils.getString(dept, "istrue"))){
			List<String> b=GetCachePermiss.getDataServeSqlbyUserIdShrio(UserUtil.getCasLoginName(), shiroTag);
			List<String> list=GetCachePermiss.getDataByDeptAndData("dept",dept.get("id"),b);
			tj=" and( "+name+"dept_id in ("+list.get(1)+") or "+name+"dept_id in ("+list.get(2)+")  ) ";
		}else{
			if("0".equals(MapUtils.getString(dept, "level"))){
				
			}else if("1".equals(MapUtils.getString(dept, "level"))){
				tj=" and "+name+"dept_id='"+dept.get("id")+"' ";
			}else if("2".equals(MapUtils.getString(dept, "level"))){
				tj=" and "+name+"dept_id='"+dept.get("id")+"' ";
			}
		}
		return tj;
	}
	
	public static String getWhere(String startDate,
			String endDate, Map<String, String> deptTeach,String enum_){
		return CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, enum_,"t");
	}
	
	public static String[] getZDBylb(String lb){
		
		if(lb.equalsIgnoreCase("all")){
			return new String[]{" 'all' code,'all' name "," 'all','all' "};
		}else if(lb.equalsIgnoreCase("xb")){
			return new String[]{" nvl(t.sex_code,'null') code,nvl(t.sex_name,'未维护') name "," t.sex_code,t.sex_name "};
		}else if(lb.equalsIgnoreCase("xl")){
			return new String[]{" nvl(t.edu_id,'null') code,nvl(t.edu_name,'未维护') name "," t.edu_id,t.edu_name "};
		}else if(lb.equalsIgnoreCase("mz")){
			String hz=Code.getKey("mz.hz");String zz=Code.getKey("mz.zz.code");
			return new String[]{" case "+
					" when t.nation_code='"+hz+"' then t.nation_code  "+
					" when t.nation_code='"+zz+"' then t.nation_code "+
					" else 'null' end code, "+
					" case  "+
					" when t.nation_code='"+hz+"' then t.nation_name  "+
					" when t.nation_code='"+zz+"' then t.nation_name "+
					" else '其他' end name"," t.nation_code,t.nation_name "};
		}else if(lb.equalsIgnoreCase("allMz")){
			return new String[]{" nvl(t.nation_code,'null') code,nvl(t.nation_name,'未维护') name "," t.nation_code,t.nation_name "};
		}else if(lb.equalsIgnoreCase("week")){
			return new String[]{" nvl(t.week_id,'null') fieldcode,nvl(t.week_name,'未维护') fieldname "," t.week_id,t.week_name "};
		}else if(lb.equalsIgnoreCase("hour")){
			return new String[]{" nvl(t.hour_,'null') fieldcode,nvl(t.hour_,'未维护') fieldname "," t.hour_,t.hour_ "};
		}else if(lb.equalsIgnoreCase("onType")){
			return new String[]{" nvl(t.on_type_code,'null') code,nvl(t.on_type_name,'未维护') name "," t.on_type_code,t.on_type_name "};
		}
		return null;
	}
	
	public static String[] getTeaBylb(String lb){
		
		if(lb.equalsIgnoreCase("all")){
			return new String[]{" 'all' code,'all' name "," 'all','all' "," "};
		}else if(lb.equalsIgnoreCase("xb")){
			return new String[]{" nvl(t.sex_code,'null') code,nvl(t.sex_name,'未维护') name "," t.sex_code,t.sex_name "," "};
		}else if(lb.equalsIgnoreCase("xl")){
			return new String[]{" nvl(t.edu_id,'null') code,nvl(t.edu_name,'未维护') name "," t.edu_id,t.edu_name "," and t.edu_id in ('10','20','30','01') "};
		}else if(lb.equalsIgnoreCase("status")){
			return new String[]{" nvl(t.status_code,'null') code,nvl(t.status_name,'未维护') name "," t.status_code,t.status_name "," and t.status_code in ("+Code.getKey("tea.status.code")+") "};
		}else if(lb.equalsIgnoreCase("bzlb")){
			return new String[]{" nvl(t.bzlb_code,'null') code,nvl(t.bzlb_name,'未维护') name "," t.bzlb_code,t.bzlb_name "," and t.bzlb_code in ("+Code.getKey("tea.bzlb.code")+") "};
		}else if(lb.equalsIgnoreCase("zwjb")){
			return new String[]{" nvl(t.zw_jb_code,'null') code,nvl(t.zw_jb_name,'未维护') name "," t.zw_jb_code,t.zw_jb_name "," and t.zw_jb_code in ("+Code.getKey("tea.zwjb.code")+") "};
		}else if(lb.equalsIgnoreCase("hour")){
			return new String[]{" nvl(t.hour_,'null') fieldcode,nvl(t.hour_,'未维护') fieldname "," t.hour_,t.hour_ "," "};
		}else if(lb.equalsIgnoreCase("onType")){
			return new String[]{" nvl(t.on_type_code,'null') code,nvl(t.on_type_name,'未维护') name "," t.on_type_code,t.on_type_name "," "};
		}
		return null;
	}
	
	
	
	public static String getDateTJ(String startDate,String endDate,String t){
		String tj=" and "+t+".year_month>=to_date('"+startDate+"','yyyy-mm') and "+t+".year_month<to_date('"+endDate+"','yyyy-mm') ";
		return tj;
	}
}
