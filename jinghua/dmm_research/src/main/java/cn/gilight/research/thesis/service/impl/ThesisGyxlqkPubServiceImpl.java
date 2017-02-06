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
import cn.gilight.research.thesis.service.ThesisGyxlqkPubService;
import cn.gilight.research.util.Config;
 
/**   
* @Description: SCI论文发表量service
* @author Sunwg  
* @date 2016年6月12日 下午5:12:17   
*/
@Service
public class ThesisGyxlqkPubServiceImpl implements ThesisGyxlqkPubService {
	
	@Resource
	private BaseDao baseDao;
	
	@Resource
	private BusinessService businessService;
	
	private String sciTypes = Config.instance().getFamousTypes();
	
	@Override
	public int querySCINums(String xkmlid, String startYear, String endYear,
			String zzjgid,String shiroTag) {
		String sql = "SELECT COUNT(T.ID) FROM T_RES_THESIS T "
				+ "WHERE t.is_first_dept=1 and T.YEAR_ BETWEEN '"+startYear+"' AND '"+endYear+"' "
				+ " AND T.PROJECT_ID IN (SELECT DISTINCT D.ID FROM T_CODE_SUBJECT D START WITH D.ID IN ('"+xkmlid+"') CONNECT BY D.PID = PRIOR D.ID) "
				+ " AND T.PERIODICAL_TYPE_CODE IN("+this.sciTypes+") "
				+ " AND T.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")";
		return baseDao.queryForInt(sql);
	}

	@Override
	public List<Map<String, Object>> querySCINumsByYear(String xkmlid,
			String startYear, String endYear, String zzjgid,String shiroTag) {
		String sql = "SELECT T.YEAR_ NAME,COUNT(T.ID) NUMS FROM T_RES_THESIS T "
				+ "WHERE t.is_first_dept=1 and T.YEAR_ BETWEEN '"+startYear+"' AND '"+endYear+"' "
				+ " AND T.PROJECT_ID IN (SELECT DISTINCT D.ID FROM T_CODE_SUBJECT D START WITH D.ID IN ('"+xkmlid+"') CONNECT BY D.PID = PRIOR D.ID) "
				+ "AND T.PERIODICAL_TYPE_CODE IN("+this.sciTypes+") "
				+ "AND T.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ "GROUP BY T.YEAR_ ORDER BY NAME";
		List<Map<String, Object>> temp = baseDao.queryListInLowerKey(sql);
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		for (int i = TypeConvert.toInteger(startYear); i <= TypeConvert.toInteger(endYear); i++) {
			Map<String, Object> it = new HashMap<String, Object>();
			it.put("name",i);
			it.put("value",0);
			for (int j = 0; j < temp.size(); j++) {
				Map<String, Object> item = temp.get(j);
				if(item.get("name").equals(TypeConvert.toString(i))){
					it.put("value", TypeConvert.toInteger(item.get("nums")));
					break;
				}
			}
			result.add(it);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> querySCINumsByDept(String xkmlid,
			String startYear, String endYear, String zzjgid,String shiroTag) {
		String sql = "SELECT D.ID,D.NAME_ NAME,COUNT(T.ID) VALUE FROM T_CODE_DEPT D "
				+ "INNER JOIN T_RES_THESIS T ON T.DEPT_ID = D.ID "
				+ "AND T.YEAR_ BETWEEN '"+startYear+"' AND '"+endYear+"' "
				+ " AND T.PROJECT_ID IN (SELECT DISTINCT D.ID FROM T_CODE_SUBJECT D START WITH D.ID IN ('"+xkmlid+"') CONNECT BY D.PID = PRIOR D.ID) "
				+ "AND T.PERIODICAL_TYPE_CODE IN("+this.sciTypes+") "
				+ "AND T.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") "
				+ "WHERE  t.is_first_dept=1 and D.LEVEL_ = '1' "
				+ "GROUP BY D.ID,D.NAME_ ORDER BY D.ID";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public List<Map<String, Object>> queryIncludeNumsByPeriodical(
			String xkmlid, String startYear, String endYear, String zzjgid,String shiroTag) {
		String sql = "SELECT TT.NUMS NAME,COUNT(TT.NAME) VALUE FROM ("
				+ "SELECT T.PERIODICAL NAME,COUNT(T.ID) NUMS FROM T_RES_THESIS T "
				+ "WHERE  t.is_first_dept=1 and T.YEAR_ BETWEEN '"+startYear+"' AND '"+endYear+"' "
				+ " AND T.PROJECT_ID IN (SELECT DISTINCT D.ID FROM T_CODE_SUBJECT D START WITH D.ID IN ('"+xkmlid+"') CONNECT BY D.PID = PRIOR D.ID) "
				+ "AND T.PERIODICAL_TYPE_CODE IN("+this.sciTypes+") "
				+ "AND T.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") "
				+ "GROUP BY T.PERIODICAL) TT GROUP BY TT.NUMS ORDER BY NAME ";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Map<String, Object> queryGyxlqkPubList(Page page, String xkmlid,
			String startYear, String endYear, String zzjgid,String shiroTag,String name,String flag) {
		String sql1 = "";
		if("nums".equals(flag)){
			sql1 = "";
		}else if("year".equals(flag)){
			sql1 = " and t.year_='"+name+"'";
		}else if("dept".equals(flag)){
			sql1 = " and tcd.name_='"+name+"'";
		}
		String sql=" select rownum,t.title_ lwmc,t.authors lwzz,t.periodical cbdw,t.year_ fwnx,tcd.name_ zzjg,tcs.name_ xkml,"
                  +" tco.name_ qklb from t_res_thesis t "
                  +" left join t_code_dept tcd on tcd.code_=t.dept_id"
                  +" left join t_code_subject tcs on tcs.code_=t.project_id"
                  +" left join t_code tco on tco.code_type='PERIODICAL_TYPE_CODE' and tco.code_=t.periodical_type_code"
                  +" where t.is_first_dept=1"
                  +" AND T.PERIODICAL_TYPE_CODE IN("+this.sciTypes+") "
                  +" and t.year_ between '"+startYear+"' and '"+endYear+"'"
                  +" and t.dept_id IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
                  +" and t.project_id IN (SELECT DISTINCT D.ID FROM T_CODE_SUBJECT D START WITH D.ID "
                  +" IN ('"+xkmlid+"') CONNECT BY D.PID = PRIOR D.ID)"+sql1;
		return baseDao.queryWithPageInLowerKey(sql, page);
	}
}
