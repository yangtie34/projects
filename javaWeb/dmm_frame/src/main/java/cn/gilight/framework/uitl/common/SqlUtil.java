package cn.gilight.framework.uitl.common;

import java.util.List;
import java.util.Map;

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
	
}
