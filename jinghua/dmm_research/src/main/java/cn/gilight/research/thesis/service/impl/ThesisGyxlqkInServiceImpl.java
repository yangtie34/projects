package cn.gilight.research.thesis.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.gilight.business.service.BusinessService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.research.thesis.service.ThesisGyxlqkInService;
import cn.gilight.research.util.Config;
 
/**   
* @Description: SCI论文发表量service
* @author Sunwg  
* @date 2016年6月12日 下午5:12:17   
*/
@Service
public class ThesisGyxlqkInServiceImpl implements ThesisGyxlqkInService {
	
	@Resource
	private BaseDao baseDao;
	
	private String sciTypes = Config.instance().getFamousTypes();
	
	@Resource
	private BusinessService businessService;

	@Override
	public List<Map<String, Object>> getFamousTypes() {
		String sql = "SELECT T.CODE_ code,T.NAME_ name FROM T_CODE T WHERE T.Code_Type ='PERIODICAL_TYPE_CODE' AND T.CODE_ IN ("+sciTypes+") AND T.ISTRUE = 1";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Integer getIncludeNumsOfPeriodical(String periodicalType,String xkmlid, String startYear,
			String endYear, String zzjgid,String shiroTag) {
		String sql = "SELECT COUNT(1) FROM T_RES_THESIS_IN T"
				+ " INNER JOIN T_RES_THESIS S ON T.THESIS_ID = S.ID"
				+ " AND S.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " AND S.PROJECT_ID IN (SELECT DISTINCT D.ID FROM T_CODE_SUBJECT D START WITH D.ID IN ('"+xkmlid+"') CONNECT BY D.PID = PRIOR D.ID)"
				+ " WHERE T.YEAR_ BETWEEN '"+startYear+"' AND '"+endYear+"' AND T.PERIODICAL_TYPE_CODE = '"+periodicalType+"'";
		return baseDao.queryForInt(sql);
	}

	@Override
	public List<Map<String,Object>> getIncludeNumsOfPeriodicalByDept(String periodicalType,
			String xkmlid, String startYear, String endYear, String zzjgid,String shiroTag) {
		String sql = "SELECT D.NAME_ NAME,COUNT(1) VALUE FROM T_RES_THESIS_IN T"
				+ " INNER JOIN T_RES_THESIS S ON T.THESIS_ID = S.ID"
				+ " INNER JOIN T_CODE_DEPT D ON S.DEPT_ID = D.ID "
				+ " AND S.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " AND S.PROJECT_ID IN (SELECT DISTINCT D.ID FROM T_CODE_SUBJECT D START WITH D.ID IN ('"+xkmlid+"') CONNECT BY D.PID = PRIOR D.ID)"
				+ " WHERE T.YEAR_ BETWEEN '"+startYear+"' AND '"+endYear+"' AND T.PERIODICAL_TYPE_CODE = '"+periodicalType+"'"
				+ " GROUP BY D.NAME_,D.ID ORDER BY D.ID";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public List<Map<String,Object>> getIncludeNumsOfPeriodicalByYear(String periodicalType,
			String xkmlid, String startYear, String endYear, String zzjgid,String shiroTag) {
		String sql = "SELECT S.YEAR_ NAME,COUNT(1) VALUE FROM T_RES_THESIS_IN T"
				+ " INNER JOIN T_RES_THESIS S ON T.THESIS_ID = S.ID"
				+ " AND S.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " AND S.PROJECT_ID IN (SELECT DISTINCT D.ID FROM T_CODE_SUBJECT D START WITH D.ID IN ('"+xkmlid+"') CONNECT BY D.PID = PRIOR D.ID)"
				+ " WHERE T.YEAR_ BETWEEN '"+startYear+"' AND '"+endYear+"' AND T.PERIODICAL_TYPE_CODE = '"+periodicalType+"'"
				+ " GROUP BY S.YEAR_ ORDER BY NAME";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public List<Map<String,Object>> getSCIImpactFactor(String periodicalType,String xkmlid, String startYear,
			String endYear, String zzjgid,String shiroTag) {
		String sql = "SELECT NVL(T.IMPACT_FACTOR,0) NAME,COUNT(DISTINCT T.PERIODICAL) VALUE1,COUNT(T.ID) VALUE2 FROM T_RES_THESIS_IN T"
				+ " INNER JOIN T_RES_THESIS S ON T.THESIS_ID = S.ID"
				+ " AND S.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " AND S.PROJECT_ID IN (SELECT DISTINCT D.ID FROM T_CODE_SUBJECT D START WITH D.ID IN ('"+xkmlid+"') CONNECT BY D.PID = PRIOR D.ID)"
				+ " WHERE T.YEAR_ BETWEEN '"+startYear+"' AND '"+endYear+"' AND T.PERIODICAL_TYPE_CODE = '"+periodicalType+"'"
				+ " GROUP BY T.IMPACT_FACTOR ORDER BY NAME ";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public List<Map<String,Object>> getSCIZone(String periodicalType,String xkmlid, String startYear, String endYear,
			String zzjgid,String shiroTag) {
		String sql = "SELECT NVL(CODE.NAME_,'未知') NAME,COUNT(1) VALUE FROM T_RES_THESIS_IN T"
				+ " LEFT JOIN T_CODE CODE ON CODE.CODE_ = T.SCI_ZONE AND CODE.CODE_TYPE = 'THESIS_IN_SCI_CODE' "
				+ " INNER JOIN T_RES_THESIS S ON T.THESIS_ID = S.ID "
				+ " AND S.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " AND S.PROJECT_ID IN (SELECT DISTINCT D.ID FROM T_CODE_SUBJECT D START WITH D.ID IN ('"+xkmlid+"') CONNECT BY D.PID = PRIOR D.ID)"
				+ " WHERE T.YEAR_ BETWEEN '"+startYear+"' AND '"+endYear+"' AND T.PERIODICAL_TYPE_CODE = '"+periodicalType+"'"
				+ " GROUP BY CODE.NAME_";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Map<String, Object> queryIncludeListOfPeriodical(Page page,
			String periodicalType, String xkmlid, String startYear,
			String endYear, String zzjgid,String shiroTag, String zzjgmc, String pubYear,String thesisInSci) {
		
		String sql = "SELECT T.ID,"
				+ " T.PERIODICAL IN_PERIODICAL,"
				+ " T.YEAR_ IN_YEAR,"
				+ " T.NJQY IN_NJQY,"
				+ " T.IMPACT_FACTOR,"
				+ " S.TITLE_ THESIS_NAME,"
				+ " S.PERIODICAL PUB_PERIODICAL,"
				+ " S.NJQY PUB_NJQY,"
				+ " S.YEAR_ PUB_YEAR FROM T_RES_THESIS_IN T"
				+ " INNER JOIN T_RES_THESIS S ON T.THESIS_ID = S.ID"
				+ " INNER JOIN T_CODE_DEPT D ON S.DEPT_ID = D.ID "
				+ " AND S.DEPT_ID IN ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " AND S.PROJECT_ID IN (SELECT DISTINCT D.ID FROM T_CODE_SUBJECT D START WITH D.ID IN ('"+xkmlid+"') CONNECT BY D.PID = PRIOR D.ID)"
				+ " LEFT JOIN T_CODE CODE ON CODE.CODE_ = T.SCI_ZONE AND CODE.CODE_TYPE = 'THESIS_IN_SCI_CODE' "
				+ " WHERE T.YEAR_ BETWEEN '"+startYear+"' AND '"+endYear+"' AND T.PERIODICAL_TYPE_CODE = '"+periodicalType+"'";
		if(zzjgmc != null && !zzjgmc.equals("")){
			sql += "  AND D.NAME_ = '"+zzjgmc+"'";
		}
		if(pubYear != null && !pubYear.equals("")){
			sql += "  AND S.YEAR_  = '"+pubYear+"'";
		}
		if(thesisInSci != null && !thesisInSci.equals("")){
			sql += "  AND NVL(CODE.NAME_,'未知') = '"+thesisInSci+"'";
		}
		return baseDao.queryWithPageInLowerKey(sql, page);
	}
	
	
}
