package cn.gilight.research.project.serivice.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.gilight.business.service.BusinessService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.research.project.serivice.ProjectProgressService;

/**   
* @Description: 项目进度service
* @author Sunwg
* @date 2016年6月27日 下午2:51:04   
*/
@Service
public class ProjectProgressServiceImpl implements ProjectProgressService{ 
	
	@Resource
	private BaseDao baseDao;

	@Resource
	private BusinessService businessService;
	/** 
	* @Description: 查询在研项目数量
	* @param level
	* @param startYear
	* @param endYear
	* @param zzjgid
	* @return: Integer
	*/
	@Override
	public Integer queryGoingOnProjectNums(String level,
			String startYear,String endYear,String zzjgid,String shiroTag){
		String sql = "SELECT  COUNT(1) VALUE "
				+ " FROM T_RES_PROJECT T"
				+ " INNER JOIN T_CODE C ON T.STATE_CODE = C.CODE_ AND C.CODE_TYPE = 'RES_PROJECT_STATE_CODE'"
				+ " WHERE T.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " AND T.SETUP_YEAR BETWEEN '"+startYear+"' AND '"+endYear+"'"
				+ " AND C.NAME_ = '进行'"
				+ " AND T.LEVEL_CODE = '"+level+"'";
		return baseDao.queryForInt(sql);
	}

	/** 
	* @Description: 查询各单位不同状态的项目数
	* @param level
	* @param startYear
	* @param endYear
	* @param zzjgid
	* @return: List<Map<String,Object>>
	*/
	@Override
	public List<Map<String,Object>> queryProjectNumsByDeptAndState(String level,
			String startYear,String endYear,String zzjgid,String shiroTag){
		String sql = "SELECT TT.DEPT, SUM(DECODE(TT.STATE,'在研',1,0)) GOINGON,SUM(DECODE(TT.STATE,'结项',1,0)) COMPLETE,SUM(DECODE(TT.STATE,'超期',1,0)) TIMEOUT  FROM ("
				+ " SELECT CASE WHEN C.NAME_ = '完成' THEN '结项'"
				+ "    WHEN C.NAME_ = '进行' AND (TO_CHAR(SYSDATE, 'YYYY') <= T.END_TIME or t.end_time is null) THEN '在研'"
				+ "    ELSE '超期' END STATE,D.NAME_ DEPT, C.NAME_,T.END_TIME  FROM T_RES_PROJECT T"
				+ "  INNER JOIN T_CODE C ON T.STATE_CODE = C.CODE_ AND C.CODE_TYPE = 'RES_PROJECT_STATE_CODE'"
				+ "  INNER JOIN T_CODE_DEPT D ON T.DEPT_ID = D.ID"
				+ " WHERE T.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " AND T.SETUP_YEAR BETWEEN '"+startYear+"' AND '"+endYear+"'"
				+ " AND T.LEVEL_CODE = '"+level+"'  ORDER BY D.ID"
				+ ") TT GROUP BY TT.DEPT";
		return baseDao.queryListInLowerKey(sql);
	}

	/** 
	 * @Description: 科研项目超期时长分布
	 * @param level
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String,Object>>
	 */
	@Override
	public List<Map<String,Object>> queryTimeoutProjectNums(String level,
			String startYear,String endYear,String zzjgid,String shiroTag){
		String sql = "SELECT COUNT(1) VALUE,TT.YEARS NAME FROM ("
				+ "SELECT TO_CHAR(SYSDATE,'YYYY') - SUBSTR(T.END_TIME,0,4) YEARS FROM T_RES_PROJECT T"
				+ "  INNER JOIN T_CODE C ON T.STATE_CODE = C.CODE_ AND C.CODE_TYPE = 'RES_PROJECT_STATE_CODE'"
				+ "  WHERE T.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ "  AND T.SETUP_YEAR BETWEEN '"+startYear+"' AND '"+endYear+"'"
				+ "  AND T.LEVEL_CODE = '"+level+"'"
				+ "  AND C.NAME_ = '进行'"
				+ "  AND SUBSTR(T.END_TIME,0,4) < TO_CHAR(SYSDATE,'YYYY')) TT GROUP BY TT.YEARS";
		return baseDao.queryListInLowerKey(sql);
	}

	/** 
	 * @Description: 科研项目状态组成
	 * @param level
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: List<Map<String,Object>>
	 */
	@Override
	public List<Map<String,Object>> queryProjectNumsByState(String level,
			String startYear,String endYear,String zzjgid,String shiroTag){
		String sql = "SELECT COUNT(1) VALUE,TT.STATE NAME FROM ("
				+ "SELECT CASE WHEN C.NAME_ = '完成' THEN '结项'"
				+ "    WHEN C.NAME_ = '进行'AND  (TO_CHAR(SYSDATE, 'YYYY') <= T.END_TIME or t.end_time is null) THEN '在研'"
				+ "    ELSE '超期' END STATE"
				+ " FROM T_RES_PROJECT T"
				+ "  INNER JOIN T_CODE C ON T.STATE_CODE = C.CODE_ AND C.CODE_TYPE = 'RES_PROJECT_STATE_CODE'"
				+ "  WHERE T.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ "  AND T.SETUP_YEAR BETWEEN '"+startYear+"' AND '"+endYear+"'"
				+ "  AND T.LEVEL_CODE = '"+level+"' ORDER BY T.END_TIME DESC"
				+ ")TT GROUP BY TT.STATE";
		return baseDao.queryListInLowerKey(sql);
	}
	
	/** 
	 * @Description: 各单位科研项目到期完成率排名
	 * @param page
	 * @param level
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: Map<String,Object>
	 */
	@Override
	public Map<String,Object> queryOrderByDept(Page page,String level,
			String startYear,String endYear,String zzjgid,String shiroTag){
		String sql = "SELECT tt.dept_id,TT.DEPT ,COUNT(1) TOTAL,SUM(DECODE(TT.STATE,'结项',1,0)) COMPLETE FROM ("
				+ "  SELECT CASE WHEN C.NAME_ = '完成' THEN '结项'"
				+ "    WHEN C.NAME_ = '进行' AND  (TO_CHAR(SYSDATE, 'YYYY') <= T.END_TIME or t.end_time is null) THEN '在研'"
				+ "    ELSE '超期' END STATE,t.dept_id,D.NAME_ DEPT FROM T_RES_PROJECT T"
				+ "  INNER JOIN T_CODE C ON T.STATE_CODE = C.CODE_ AND C.CODE_TYPE = 'RES_PROJECT_STATE_CODE'"
				+ "  INNER JOIN T_CODE_DEPT D ON T.DEPT_ID = D.ID"
				+ "  WHERE T.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ "  AND T.SETUP_YEAR BETWEEN '"+startYear+"' AND '"+endYear+"'"
				+ "  AND T.LEVEL_CODE = '"+level+"'"
				+ ")TT GROUP BY tt.dept_id,TT.DEPT  ORDER BY COMPLETE/TOTAL DESC,TOTAL DESC";
		return baseDao.queryWithPageInLowerKey(sql,page);
	}
	
	/** 
	 * @Description: 单位 主持人完成率排名
	 * @param page
	 * @param level
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: Map<String,Object>
	 */
	@Override
	public Map<String,Object> queryOrderByCompere(Page page,String level,
			String startYear,String endYear,String zzjgid,String shiroTag){
		String sql = "SELECT tt.tea_no,TT.TEA ,COUNT(1) TOTAL,SUM(DECODE(TT.STATE,'结项',1,0)) COMPLETE FROM ("
				+ "  SELECT CASE WHEN C.NAME_ = '完成' THEN '结项'"
				+ "    WHEN C.NAME_ = '进行' AND (TO_CHAR(SYSDATE, 'YYYY') <= T.END_TIME or t.end_time is null) THEN '在研'"
				+ "    ELSE '超期' END STATE, tea.tea_no,TEA.NAME_ TEA"
				+ " FROM T_RES_PROJECT T"
				+ " INNER JOIN T_RES_PROJECT_AUTH A ON A.PRO_ID = T.ID"
				+ " INNER JOIN T_TEA TEA ON TEA.TEA_NO = A.TEA_NO"
				+ "  INNER JOIN T_CODE C ON T.STATE_CODE = C.CODE_ AND C.CODE_TYPE = 'RES_PROJECT_STATE_CODE'"
				+ "  WHERE T.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ "  AND T.SETUP_YEAR BETWEEN '"+startYear+"' AND '"+endYear+"'"
				+ "  AND T.LEVEL_CODE = '"+level+"'"
				+ ")TT GROUP BY tt.tea_no,TT.TEA ORDER BY COMPLETE/TOTAL DESC,TOTAL DESC";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}
	

	/** 
	 * @Description: 超期项目列表
	 * @param page
	 * @param level
	 * @param startYear
	 * @param endYear
	 * @param zzjgid
	 * @return: Map<String,Object>
	 */
	public Map<String,Object> queryTimeOutProjectList(Page page,String level,
			String startYear, String endYear, String zzjgid,String shiroTag){
		String sql = "SELECT  D.NAME_ DEPT, T.COMPERE, T.NAME_ NAME, L.NAME_ LB, T.ISSUED_DEPT, T.START_TIME, T.END_TIME,"
				+ " TO_CHAR(SYSDATE,'YYYY') - SUBSTR(T.END_TIME,0,4) YEARS"
				+ "  FROM T_RES_PROJECT T"
				+ " INNER JOIN T_CODE_DEPT D ON T.DEPT_ID = D.ID"
				+ " INNER JOIN T_CODE L ON T.LEVEL_CODE = L.CODE_ AND L.CODE_TYPE = 'RES_PROJECT_LEVEL_CODE'"
				+ " INNER JOIN T_CODE C ON T.STATE_CODE = C.CODE_ AND C.CODE_TYPE = 'RES_PROJECT_STATE_CODE'"
				+ "  WHERE T.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ "  AND T.SETUP_YEAR BETWEEN '"+startYear+"' AND '"+endYear+"'"
				+ "  AND T.LEVEL_CODE = '"+level+"'"
				+ " AND C.NAME_ = '进行'"
				+ "  AND SUBSTR(T.END_TIME,0,4) < TO_CHAR(SYSDATE,'YYYY') "
				+ " ORDER BY YEARS DESC";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}

	@Override
	public Map<String, Object> queryProgressDetail(Page page, String level,
			String startYear, String endYear, String zzjgid,String shiroTag, String name,
			String flag) {
		String querySql  = "";
		String sql2 = "";
		String sql3 = "";
		if("in".equals(flag)){
			querySql = " and c.name_ = '进行'";
		}else if("dept".equals(flag)){
			querySql = " and d.name_ = '"+name+"'";
		}else if("state".equals(flag)){
			sql2 = " where ts.state = '"+name+"'";
		}else if("tea".equals(flag)){
			querySql = " and tea.tea_no = '"+name+"'";
		}else if("years".equals(flag)){
			sql3 = " where tt.years = '"+name+"'";
		}
		
		String sql = "select tt.* ,rownum rn from(select ts.*,case when ts.state = '超期' then TO_CHAR(SYSDATE,'YYYY') - SUBSTR(ts.END_TIME,0,4)  else 0 end YEARS"
				+ " from (SELECT t.dept_id,D.NAME_ DEPT,"
				+ " tea.tea_no,tea.name_ tea, T.NAME_ NAME, L.NAME_ LB, T.ISSUED_DEPT, t.end_time,"
				+ " SUBSTR(T.START_TIME,0,4)||'-'||SUBSTR(T.END_TIME,0,4) time_,t.fund,t.setup_year,"
				+ " CASE WHEN C.NAME_ = '完成' THEN '结项' WHEN C.NAME_ = '进行' AND (TO_CHAR(SYSDATE, 'YYYY') <= T.END_TIME or t.end_time is null) THEN '在研'"
				+ " ELSE '超期' END STATE  FROM T_RES_PROJECT T INNER JOIN T_CODE_DEPT D ON T.DEPT_ID = D.ID"
				+ " INNER JOIN T_CODE L ON T.LEVEL_CODE = L.CODE_ AND L.CODE_TYPE = 'RES_PROJECT_LEVEL_CODE'"
				+ " INNER JOIN T_CODE C ON T.STATE_CODE = C.CODE_ AND C.CODE_TYPE = 'RES_PROJECT_STATE_CODE'"
				+ " LEFT JOIN T_RES_PROJECT_AUTH A ON A.PRO_ID = T.ID AND A.ORDER_ = 1 left JOIN T_TEA TEA ON TEA.TEA_NO = A.TEA_NO"
				+ " WHERE T.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " AND T.SETUP_YEAR BETWEEN '"+startYear+"' AND '"+endYear+"' AND T.LEVEL_CODE = '"+level+"' "+querySql
				+ " ORDER BY SUBSTR(T.START_TIME,0,4) DESC)ts "+ sql2 +") tt "+sql3;
		
		return baseDao.queryWithPageInLowerKey(sql, page);
	}
}