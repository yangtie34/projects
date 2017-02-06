package cn.gilight.research.project.serivice.impl;


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
import cn.gilight.research.project.serivice.ProjectNumsService;
 
/**   
* @Description: 论文作者相关查询service
* @author Sunwg
* @date 2016年6月21日 下午2:17:07   
*/
@Service
public class ProjectNumsServiceImpl implements ProjectNumsService{ 
	@Resource
	private BaseDao baseDao;
	
	@Resource
	private BusinessService businessService;
	
	/** 
	* @Description: 查询科研项目总数
	* @param xkmlid
	* @param startYear
	* @param endYear
	* @param zzjgid
	* @return: Integer
	*/
	@Override
	public Integer queryTotalNums(String startYear,String endYear,String zzjgid,String shiroTag ) {
		String sql = "SELECT COUNT(1) VALUE"
				+ " FROM T_RES_PROJECT T"
				+ " WHERE T.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+" )"
				+ " AND T.SETUP_YEAR BETWEEN '"+startYear+"' AND '"+endYear+"'";
		return baseDao.queryForInt(sql);
	}
	
	/** 
	* @Description: 查询每年科研项目数量
	* @param startYear
	* @param endYear
	* @param zzjgid
	* @return: List<Map<String,Object>>
	*/
	@Override
	public List<Map<String, Object>> queryProjectNumsOfYears(String startYear,String endYear,String zzjgid,String shiroTag) {
		String sql = "SELECT COUNT(1) VALUE,T.SETUP_YEAR NAME"
				+ "  FROM T_RES_PROJECT T "
				+ " WHERE T.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+" )"
				+ " AND T.SETUP_YEAR BETWEEN '"+startYear+"' AND '"+endYear+"'"
				+ " GROUP BY T.SETUP_YEAR"
				+ " ORDER BY T.SETUP_YEAR";
		List<Map<String, Object>> temp = baseDao.queryListInLowerKey(sql);
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		for (int i = TypeConvert.toInteger(startYear); i <= TypeConvert.toInteger(endYear); i++) {
			Map<String, Object> it = new HashMap<String, Object>();
			it.put("name",i);
			it.put("value", 0);
			for (int j = 0; j < temp.size(); j++) {
				Map<String, Object> item = temp.get(j);
				if(item.get("name").equals(TypeConvert.toString(i))){
					it.put("value", item.get("value"));
					break;
				}
			}
			result.add(it);
		}
		return result;
	}
	
	/** 
	* @Description: 各单位各级别项目总数
	* @param startYear
	* @param endYear
	* @param zzjgid
	* @return: List<Map<String,Object>>
	*/
	@Override
	public List<Map<String, Object>> queryProjectNumsOfDeptAndLevel(String startYear,String endYear,String zzjgid,String shiroTag) {
		String sql = "SELECT COUNT(1) VALUE,D.NAME_  FIELD, L.NAME_ NAME" 
				+ " FROM T_RES_PROJECT T"
				+ " INNER JOIN T_CODE_DEPT D ON T.DEPT_ID = D.ID "
					+ " AND D.ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+" )"
				+ " INNER JOIN T_CODE L ON T.LEVEL_CODE = L.CODE_ AND L.CODE_TYPE = 'RES_PROJECT_LEVEL_CODE'"
				+ " WHERE T.SETUP_YEAR BETWEEN '"+startYear+"' AND '"+endYear+"'"
				+ " GROUP BY D.NAME_ ,L.NAME_,D.ID,L.ORDER_ ORDER BY L.ORDER_,D.ID";
		return baseDao.queryListInLowerKey(sql);
	}
	
	/** 
	* @Description: 项目级别分布
	* @param startYear
	* @param endYear
	* @param zzjgid
	* @return: List<Map<String,Object>>
	*/
	@Override
	public List<Map<String, Object>> queryProjectNumsOfLevel(String startYear,String endYear,String zzjgid,String shiroTag) {
		String sql = " SELECT COUNT(1) VALUE , L.NAME_ NAME,T.LEVEL_CODE"
				+ " FROM T_RES_PROJECT T"
				+ " INNER JOIN T_CODE_DEPT D ON T.DEPT_ID = D.ID "
					+ " AND D.ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+" )"
				+ " LEFT JOIN T_CODE L ON T.LEVEL_CODE = L.CODE_ AND L.CODE_TYPE = 'RES_PROJECT_LEVEL_CODE'"
				+ " WHERE T.SETUP_YEAR BETWEEN '"+startYear+"' AND '"+endYear+"'"
				+ " GROUP BY L.NAME_,T.LEVEL_CODE ORDER BY VALUE DESC";
		return baseDao.queryListInLowerKey(sql);
	}

	/** 
	* @Description: 各级项目下达部门分布
	* @param startYear
	* @param endYear
	* @param zzjgid
	* @return: List<Map<String,Object>>
	*/
	@Override
	public List<Map<String, Object>> queryIssuedDeptNumsOfProjectLevel(String startYear,String endYear,String zzjgid,String shiroTag) {
		String sql = "SELECT L.NAME_ NAME, COUNT(DISTINCT T.ISSUED_DEPT) VALUE,T.LEVEL_CODE"
				+ " FROM T_RES_PROJECT T"
				+ " INNER JOIN T_CODE_DEPT D ON T.DEPT_ID = D.ID "
					+ " AND D.ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+" )"
				+ " LEFT JOIN T_CODE L ON T.LEVEL_CODE = L.CODE_ AND L.CODE_TYPE = 'RES_PROJECT_LEVEL_CODE'"
				+ " WHERE T.SETUP_YEAR BETWEEN '"+startYear+"' AND '"+endYear+"'"
				+ " GROUP BY T.LEVEL_CODE,L.NAME_,L.ORDER_ ORDER BY L.ORDER_";
		return baseDao.queryListInLowerKey(sql);
	}
	
	/** 
	* @Description: 项目等级分布
	* @param startYear
	* @param endYear
	* @param zzjgid
	* @return: List<Map<String,Object>>
	*/
	@Override
	public List<Map<String, Object>> queryProjectRankNumsOfProjectLevel(String startYear,String endYear,String zzjgid,String shiroTag) {
		String sql = " SELECT COUNT(1) VALUE,L.NAME_ FIELD,NVL(J.NAME_,'未说明') NAME "
				+ " FROM T_RES_PROJECT T"
				+ " INNER JOIN T_CODE_DEPT D ON T.DEPT_ID = D.ID "
					+ " AND D.ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+" )"
				+ " INNER JOIN T_CODE L ON T.LEVEL_CODE = L.CODE_ AND L.CODE_TYPE = 'RES_PROJECT_LEVEL_CODE'"
				+ " LEFT JOIN T_CODE J ON T.RANK_CODE = J.CODE_ AND J.CODE_TYPE = 'RES_PROJECT_RANK_CODE'"
				+ " WHERE T.SETUP_YEAR BETWEEN '"+startYear+"' AND '"+endYear+"'"
				+ " GROUP BY L.NAME_,J.NAME_,L.ORDER_ ORDER BY L.ORDER_";
		return baseDao.queryListInLowerKey(sql);
	}
	
	/** 
	* @Description: 科研项目列表
	* @param startYear
	* @param endYear	
	* @param zzjgid
	* @return: List<Map<String,Object>>
	*/
	@Override
	public Map<String, Object> queryProjectList(Page page,String queryString,
			String startYear,String endYear,String zzjgid,String shiroTag,String setupYear,String dept,String level,String issuedDept) {
		String sql = "SELECT T.ID,T.NAME_,T.COMPERE,D.NAME_ DEPT,LB.NAME_ LEVEL_NAME,JB.NAME_ RANK_NAME,"
				+ "T.ISSUED_DEPT,T.SETUP_YEAR,T.START_TIME,T.END_TIME,T.FUND  FROM T_RES_PROJECT T"
				+ " INNER JOIN T_CODE_DEPT D ON D.ID = T.DEPT_ID"
				+ " LEFT JOIN T_CODE LB ON LB.CODE_ = T.LEVEL_CODE AND LB.CODE_TYPE = 'RES_PROJECT_LEVEL_CODE'"
				+ " LEFT JOIN T_CODE JB ON JB.CODE_ = T.RANK_CODE AND JB.CODE_TYPE = 'RES_PROJECT_RANK_CODE'"
				+ " WHERE T.NAME_ LIKE '%"+queryString+"%' AND T.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+" )"
				+ "  AND T.SETUP_YEAR BETWEEN '"+startYear+"' AND '"+endYear+"' ";
		if(setupYear!= null && !setupYear.equals("")){
			sql += " AND T.SETUP_YEAR = '"+ setupYear +"' ";
		}
		if(dept!= null && !dept.equals("")){
			sql += " AND D.NAME_ = '"+ dept +"' ";
		}
		if(level!= null && !level.equals("")){
			sql += " AND LB.NAME_ = '"+ level +"' ";
		}
		if(issuedDept!= null && !issuedDept.equals("")){
			sql += " AND T.ISSUED_DEPT = '"+ issuedDept +"' ";
		}
		sql += " ORDER BY END_TIME DESC ,START_TIME DESC,FUND DESC ";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}
	
}