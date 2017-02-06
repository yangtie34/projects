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
import cn.gilight.research.project.serivice.ProjectFundService;
 
/**   
* @Description: 科研项目经费Service
* @author Sunwg
* @date 2016年6月24 日 下午2:17:07   
*/
@Service
public class ProjectFundServiceImpl implements ProjectFundService { 
	@Resource
	private BaseDao baseDao;
	
	@Resource
	private BusinessService businessService;
	
	/** 
	* @Description: 科研项目经费总额
	* @param startYear
	* @param endYear	
	* @param zzjgid
	* @return: List<Map<String,Object>>
	*/
	@Override
	public Integer queryProjectFundTotal(String startYear,String endYear,String zzjgid,String shiroTag) {
		String sql = "SELECT NVL(SUM(T.FUND),0) VALUE "
				+ "FROM T_RES_PROJECT T"
				+ " WHERE T.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " AND T.SETUP_YEAR BETWEEN '"+startYear+"' AND '"+endYear+"'";
		return baseDao.queryForInt(sql);
	}
	
	/** 
	 * @Description: 项目金额投入变化趋势
	 * @param startYear
	 * @param endYear	
	 * @param zzjgid
	 * @return: List<Map<String,Object>>
	 */
	@Override
	public List<Map<String, Object>> queryFundTotalByYears(String startYear,String endYear,String zzjgid,String shiroTag) {
		String sql = "SELECT SUM(T.FUND) VALUE ,T.SETUP_YEAR NAME"
				+ " FROM T_RES_PROJECT T"
				+ " WHERE T.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
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
	 * @Description: 各单位各级别项目投入总额
	 * @param startYear
	 * @param endYear	
	 * @param zzjgid
	 * @return: List<Map<String,Object>>
	 */
	@Override
	public List<Map<String, Object>> queryFundTotalByDeptAndProjectLevel(String startYear,String endYear,String zzjgid,String shiroTag) {
		String sql = "SELECT NVL(SUM(T.FUND),0) VALUE ,D.NAME_ FIELD,L.NAME_ NAME"
				+ " FROM T_RES_PROJECT T"
				+ " INNER JOIN T_CODE_DEPT D ON D.ID = T.DEPT_ID"
				+ " INNER JOIN T_CODE L ON L.CODE_ = T.LEVEL_CODE AND L.CODE_TYPE = 'RES_PROJECT_LEVEL_CODE'"
				+ " WHERE T.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " AND T.SETUP_YEAR BETWEEN '"+startYear+"' AND '"+endYear+"'"
				+ " GROUP BY D.NAME_,L.NAME_,D.ID ORDER BY D.ID";
		return baseDao.queryListInLowerKey(sql);
	}
	
	/** 
	 * @Description: 各级别项目投入总额
	 * @param startYear
	 * @param endYear	
	 * @param zzjgid
	 * @return: List<Map<String,Object>>
	 */
	@Override
	public List<Map<String, Object>> queryFundTotalByProjectLevel(String startYear,String endYear,String zzjgid,String shiroTag) {
		String sql = "SELECT NVL(SUM(T.FUND),0) VALUE ,L.NAME_ NAME"
				+ " FROM T_RES_PROJECT T"
				+ " INNER JOIN T_CODE L ON L.CODE_ = T.LEVEL_CODE AND L.CODE_TYPE = 'RES_PROJECT_LEVEL_CODE' "
				+ " WHERE T.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " AND T.SETUP_YEAR BETWEEN '"+startYear+"' AND '"+endYear+"'"
				+ " GROUP BY L.NAME_ ORDER BY VALUE DESC";
		return baseDao.queryListInLowerKey(sql);
	}
	/** 
	 * @Description: 各单位项目投入平均额
	 * @param startYear
	 * @param endYear	
	 * @param zzjgid
	 * @return: List<Map<String,Object>>
	 */
	@Override
	public List<Map<String, Object>> queryFundAvgByDept(String startYear,String endYear,String zzjgid,String shiroTag) {
		String sql = "SELECT D.NAME_ NAME,NVL(SUM(T.FUND),0) TOTAL,COUNT(T.ID) NUMS,  ROUND(NVL(SUM(T.FUND),0)/COUNT(T.ID),2) AVGVAL"
				+ " FROM T_RES_PROJECT T"
				+ " INNER JOIN T_CODE_DEPT D ON D.ID = T.DEPT_ID"
				+ " WHERE T.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " AND T.SETUP_YEAR BETWEEN '"+startYear+"' AND '"+endYear+"'"
				+ " GROUP BY D.NAME_,D.ID ORDER BY D.ID ";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Map<String, Object> queryProjectDetail(Page page, String startYear,
			String endYear, String zzjgid,String shiroTag, String name, String flag) {
		String querySql = "";
		if("dept".equals(flag)){
			querySql = " and d.name_ = '"+name+"'";
		}
		String sql = "select tt.*,rownum rn from (SELECT t.name_,t.compere,d.name_ dept_name,t.fund,substr(t.start_time,0,4)||'-'||substr(t.end_time,0,4) time_,"
				+ " l.name_ level_ FROM T_RES_PROJECT T INNER JOIN T_CODE_DEPT D ON D.ID = T.DEPT_ID"
				+ " INNER JOIN T_CODE L ON L.CODE_ = T.LEVEL_CODE AND L.CODE_TYPE = 'RES_PROJECT_LEVEL_CODE' "
				+ " WHERE T.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " AND T.SETUP_YEAR BETWEEN '"+startYear+"' AND '"+endYear+"'"+querySql+" )tt";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}
	
	
}