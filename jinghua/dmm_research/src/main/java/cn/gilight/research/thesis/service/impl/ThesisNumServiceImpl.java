package cn.gilight.research.thesis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.gilight.business.service.BusinessService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.TypeConvert;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.research.thesis.service.ThesisNumService;
 
/**   
* @Description: 论文发表量service
* @author Sunwg  
* @date 2016年6月12日 下午5:12:17   
*/
@Service("thesisNumService")
public class ThesisNumServiceImpl implements ThesisNumService {
	
	@Resource
	private BaseDao baseDao;
	
	@Resource
	private BusinessService businessService;
	
	@Override
	public int queryTotalNums(String xkmlid, String startYear, String endYear,
			String zzjgid,String shiroTag) {
		String sql = "SELECT COUNT(T.ID) FROM T_RES_THESIS T "
				+ "WHERE T.YEAR_ BETWEEN '"+startYear+"' AND '"+endYear+"' "
				+ " AND T.PROJECT_ID IN (SELECT DISTINCT D.ID FROM T_CODE_SUBJECT D START WITH D.ID IN ('"+xkmlid+"') CONNECT BY D.PID = PRIOR D.ID) "
				+ "AND T.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")";
		return baseDao.queryForInt(sql);
	}

	@Override
	public int firstAuthThesisNums(String xkmlid, String startYear,
			String endYear, String zzjgid,String shiroTag, boolean isFirstAuth) {
		String sql = "SELECT COUNT(T.ID) FROM T_RES_THESIS T "
				+ "WHERE T.YEAR_ BETWEEN '"+startYear+"' AND '"+endYear+"' "
				+ " AND T.PROJECT_ID IN (SELECT DISTINCT D.ID FROM T_CODE_SUBJECT D START WITH D.ID IN ('"+xkmlid+"') CONNECT BY D.PID = PRIOR D.ID) "
				+ "AND T.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") "
				+ "AND T.IS_FIRST_DEPT = "+(isFirstAuth?1:0);
		return baseDao.queryForInt(sql);
	}

	@Override
	public List<Map<String, Object>> queryThesisNumsByDepts(String xkmlid,
			String startYear, String endYear,String shiroTag) {
		String deptsql = "SELECT * FROM T_CODE_DEPT WHERE LEVEL_ = '0'";
		Map<String, Object> root = baseDao.queryMapInLowerKey(deptsql);
		String rootId = MapUtils.getString(root, "id");
		String sql = "SELECT D.ID,D.NAME_ NAME,COUNT(T.ID) NUMS FROM T_CODE_DEPT D "
				+ "LEFT JOIN T_RES_THESIS T ON T.DEPT_ID = D.ID "
				+ "AND T.YEAR_ BETWEEN '"+startYear+"' AND '"+endYear+"' "
				+ "AND T.PROJECT_ID IN (SELECT DISTINCT D.ID FROM T_CODE_SUBJECT D START WITH D.ID IN ('"+xkmlid+"') CONNECT BY D.PID = PRIOR D.ID) "
				+ "WHERE D.LEVEL_ = '1' "
				+ "AND D.ID IN ("+businessService.getDeptDataPermissIdsQuerySql(rootId, shiroTag)+") "
				+ "GROUP BY D.ID,D.NAME_ ORDER BY D.ID";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public List<Map<String, Object>> queryThesisNumsByYears(String xkmlid,
			String startYear, String endYear, String zzjgid,String shiroTag) {
		String sql = "SELECT T.YEAR_ NAME, COUNT(T.ID) NUMS FROM T_RES_THESIS T "
				+ " WHERE  T.YEAR_ BETWEEN '"+startYear+"' AND '"+endYear+"' "
				+ " AND T.PROJECT_ID IN (SELECT DISTINCT D.ID FROM T_CODE_SUBJECT D START WITH D.ID IN ('"+xkmlid+"') CONNECT BY D.PID = PRIOR D.ID) "
				+ " AND T.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " GROUP BY T.YEAR_"
				+ " ORDER BY T.YEAR_";
		List<Map<String, Object>> temp = baseDao.queryListInLowerKey(sql);
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		for (int i = TypeConvert.toInteger(startYear); i <= TypeConvert.toInteger(endYear); i++) {
			Map<String, Object> it = new HashMap<String, Object>();
			it.put("name",i);
			it.put("nums", 0);
			for (int j = 0; j < temp.size(); j++) {
				Map<String, Object> item = temp.get(j);
				if(item.get("name").equals(TypeConvert.toString(i))){
					it.put("nums", item.get("nums"));
					break;
				}
			}
			result.add(it);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> queryGywsykLwfbl(String xkmlid,
			String startYear, String endYear, String zzjgid,String shiroTag) {
		String sql ="SELECT P.CODE_,P.NAME_ NAME, COUNT(T.ID) NUMS"
				+ " FROM T_CODE P"
				+ " INNER JOIN T_RES_THESIS T"
					+ " ON T.PERIODICAL_TYPE_CODE = P.CODE_"
					+ " AND T.YEAR_ BETWEEN '"+startYear+"' AND '"+endYear+"' "
					+ " AND T.PROJECT_ID IN (SELECT DISTINCT D.ID FROM T_CODE_SUBJECT D START WITH D.ID IN ('"+xkmlid+"') CONNECT BY D.PID = PRIOR D.ID) "
					+ " AND T.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " WHERE P.CODE_TYPE = 'PERIODICAL_TYPE_CODE'"
				+ " GROUP BY P.NAME_,P.CODE_ ORDER BY NUMS DESC";
		List<Map<String, Object>> temp = baseDao.queryListInLowerKey(sql);
		int total = 0;
		for (int i = 0; i < temp.size(); i++) {
			Map<String, Object> item = temp.get(i);
			int nums =  TypeConvert.toInteger(item.get("nums"));
			item.put("value", nums);
			item.put("order", i+1);
			total += nums;
		}
		
		for (int i = 0; i < temp.size(); i++) {
			Map<String, Object> item = temp.get(i);
			double persent = TypeConvert.toDouble(item.get("nums"))*100/total;
			item.put("persent", persent);
		}
		return temp;
	}

	//各部门论文发表量点击下钻事件
	@Override
	public Map<String,Object> queryNumsList(Page page, String xkmlid, String startYear,
			String endYear, String zzjgid,String shiroTag,String name,String flag) {
		String sql2 = "select * from t_code_dept t where t.id = '"+zzjgid+"'";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql2);
		String dept_type = "";
		if(list != null){
			dept_type = MapUtils.getString(list.get(0), "level_type");
		}
		String querySql = "";
		if("sorts".equals(flag)){
				querySql = "";
		}else if("dept".equals(flag)){
			if("XX".equals(dept_type)){
				querySql = " and tcd.name_ = '"+name+"'";
			}else{
				querySql = " and tt.year_ = '"+name+"'";
			}
		}else if("gywlw".equals(flag)){
				querySql = " and tco.name_ = '"+name+"'";
		}else if("year".equals(flag)){
			querySql = " and tt.year_ = '"+name+"'";
		}
		String sql="select rownum,tcd.name_ zzjg,tcs.name_ xkml,tco.name_ qklb,tt.year_ fwnx,"
				  +" tt.periodical cbdw,tt.authors lwzz,tt.title_ lwmc "
                  +" from (select t.id,t.dept_id,t.year_,t.periodical_type_code,t.project_id,"
                  +" t.title_,t.authors,t.periodical"
                  +" from t_res_thesis t ) tt left join t_code_dept tcd"
                  +" on tcd.code_ = tt.dept_id left join t_code_subject tcs on tcs.code_ = tt.project_id"
                  +" left join t_code tco on tco.code_type = 'PERIODICAL_TYPE_CODE'"
                  +" and tco.code_ = tt.periodical_type_code"
                  +" where tt.year_ between '"+startYear+"' and '"+endYear+"'"
                  +" and tt.project_id IN (SELECT DISTINCT D.ID FROM T_CODE_SUBJECT D"
                  +" START WITH D.ID IN ('"+xkmlid+"') CONNECT BY D.PID = PRIOR D.ID)and tt.dept_id IN"
                  +" ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"+querySql;
		return baseDao.queryWithPageInLowerKey(sql, page);
	}
}
