package cn.gilight.research.thesis.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.gilight.business.service.BusinessService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.research.thesis.service.ThesisAuthorService;
 
/**   
* @Description: 论文作者相关查询service
* @author Sunwg
* @date 2016年6月21日 下午2:17:07   
*/
@Service
public class ThesisAuthorServiceImpl implements ThesisAuthorService { 
	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	private BaseDao baseDao;
	
	@Resource
	private BusinessService businessService;
	
	@Override
	public Integer queryTotalNums(String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag) {
		log.debug("查询作者人数！");
		String sql = "select nvl(sum(tt.value),0) value from ( SELECT  t.people_identity_code, COUNT(DISTINCT NVL(T.TEA_NO,T.PEOPLE_NAME)) VALUE "
				+ "FROM T_RES_THESIS_AUTHOR T "
				+ " INNER JOIN T_TEA TEA ON T.TEA_NO = TEA.TEA_NO " //该行添加为了只查询教职工
				+ "INNER JOIN T_RES_THESIS S ON T.THESIS_ID = S.ID AND S.PROJECT_ID IN"
					+ "	(SELECT DISTINCT D.ID FROM T_CODE_SUBJECT D START WITH D.ID IN ('"+xkmlid+"') CONNECT BY D.PID = PRIOR D.ID) "
					+ " AND S.YEAR_ BETWEEN '"+startYear+"' AND '"+endYear+"'"
				+ "INNER JOIN T_CODE_DEPT D ON S.DEPT_ID = D.ID AND S.DEPT_ID IN "
					+ "	("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") "
					+ " group by t.people_identity_code) tt";
		return baseDao.queryForInt(sql);
	}
	
	@Override
	public List<Map<String, Object>> queryAuthorNumsByGender(String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag) {
		log.debug("按照性别查询人数！");
		String sql = "select tt.name name,sum(tt.value) value from ( SELECT t.people_identity_code, COUNT(DISTINCT  NVL(T.TEA_NO,T.PEOPLE_NAME)) VALUE,NVL( SEX.NAME_,'未说明') NAME"
				+ " FROM T_RES_THESIS_AUTHOR T "
				+ " INNER JOIN T_RES_THESIS S ON T.THESIS_ID = S.ID AND S.PROJECT_ID IN"
					+ "	(SELECT DISTINCT D.ID FROM T_CODE_SUBJECT D START WITH D.ID IN ('"+xkmlid+"') CONNECT BY D.PID = PRIOR D.ID) "
					+ " AND S.YEAR_ BETWEEN '"+startYear+"' AND '"+endYear+"'"
				+ "INNER JOIN T_CODE_DEPT D ON S.DEPT_ID = D.ID AND S.DEPT_ID IN "
					+ "	("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")" 
				+ " INNER JOIN T_TEA TEA ON T.TEA_NO = TEA.TEA_NO" //该行修改： left —— inner  为了只查询教职工
				+ " LEFT JOIN T_CODE SEX ON SEX.CODE_ = TEA.SEX_CODE AND SEX.CODE_TYPE = 'SEX_CODE'"
				+ " GROUP BY SEX.NAME_ ,t.people_identity_code) tt group by name";
		return baseDao.queryListInLowerKey(sql);
	}
	
	@Override
	public List<Map<String, Object>> queryAuthorNumsByType(String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag) {
		log.debug(" 按照类别查询作者人数！");
		String sql = " SELECT COUNT(DISTINCT  NVL(T.TEA_NO,T.PEOPLE_NAME)) VALUE,TY.NAME_ NAME"
				+ " FROM T_RES_THESIS_AUTHOR T "
				+ " INNER JOIN T_RES_THESIS S ON T.THESIS_ID = S.ID AND S.PROJECT_ID IN"
					+ "	(SELECT DISTINCT D.ID FROM T_CODE_SUBJECT D START WITH D.ID IN ('"+xkmlid+"') CONNECT BY D.PID = PRIOR D.ID) "
					+ " AND S.YEAR_ BETWEEN '"+startYear+"' AND '"+endYear+"'"
				+ "INNER JOIN T_CODE_DEPT D ON S.DEPT_ID = D.ID AND S.DEPT_ID IN "
					+ "	("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")" 
				+ " RIGHT JOIN T_CODE TY ON TY.CODE_ = T.PEOPLE_IDENTITY_CODE WHERE  TY.CODE_TYPE = 'PEOPLE_IDENTITY_CODE' GROUP BY TY.NAME_";
		return baseDao.queryListInLowerKey(sql);
	}
	
	@Override
	public List<Map<String, Object>> queryAuthorNumsByOutput(String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag) {
		log.debug("按作者产量查询作者人数！");
		String sql = " SELECT  t.people_identity_code,NVL(T.TEA_NO,T.PEOPLE_NAME) TEA, COUNT(T.THESIS_ID) VALUE "
				+ "FROM T_RES_THESIS_AUTHOR T "
				+ " INNER JOIN T_TEA TEA ON T.TEA_NO = TEA.TEA_NO " //该行添加为了只查询教职工
				+ "INNER JOIN T_RES_THESIS S ON T.THESIS_ID = S.ID AND S.PROJECT_ID IN"
					+ "	(SELECT DISTINCT D.ID FROM T_CODE_SUBJECT D START WITH D.ID IN ('"+xkmlid+"') CONNECT BY D.PID = PRIOR D.ID) "
					+ " AND S.YEAR_ BETWEEN '"+startYear+"' AND '"+endYear+"'"
				+ "INNER JOIN T_CODE_DEPT D ON S.DEPT_ID = D.ID AND S.DEPT_ID IN "
					+ "	("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")" 
				+ "  GROUP BY NVL(T.TEA_NO,T.PEOPLE_NAME) ,t.people_identity_code";
		return baseDao.queryListInLowerKey(sql);
	}
	
	@Override
	public List<Map<String, Object>>  queryAuthorNumsByEducation(String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag) {
		log.debug("查询学历分布！");
		String sql = "SELECT COUNT(DISTINCT TEA.TEA_NO) VALUE, DECODE(E2.LEVEL_,1, E2.NAME_,2,E1.NAME_,NULL,'未说明') NAME"
			+ " FROM T_RES_THESIS_AUTHOR T "
			+ " INNER JOIN T_RES_THESIS S ON T.THESIS_ID = S.ID AND S.PROJECT_ID IN"
				+ "	(SELECT DISTINCT D.ID FROM T_CODE_SUBJECT D START WITH D.ID IN ('"+xkmlid+"') CONNECT BY D.PID = PRIOR D.ID) "
				+ " AND S.YEAR_ BETWEEN '"+startYear+"' AND '"+endYear+"'"
			+ "INNER JOIN T_CODE_DEPT D ON S.DEPT_ID = D.ID AND S.DEPT_ID IN "
				+ "	("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")" 
			+ " INNER JOIN T_TEA TEA ON TEA.TEA_NO = T.TEA_NO "
			+ " LEFT JOIN T_CODE_EDUCATION E2 ON TEA.EDU_ID = E2.ID  AND E2.ISTRUE = 1"
			+ " LEFT JOIN T_CODE_EDUCATION E1 ON  E1.ID = E2.PID AND E1.LEVEL_ = 1  AND E1.ISTRUE = 1"
			+ " GROUP BY DECODE(E2.LEVEL_,1, E2.NAME_,2,E1.NAME_,NULL,'未说明')";
		return baseDao.queryListInLowerKey(sql);
	}
	
	@Override
	public List<Map<String, Object>> queryAuthorNumsByAge(String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag) {
		log.debug("查询作者年龄分布！");
		String sql = "SELECT DISTINCT TEA.TEA_NO, NVL(floor(MONTHS_BETWEEN(sysdate,TO_DATE(TEA.BIRTHDAY,'YYYY-MM-DD'))/12),0) AGE"
				+ " FROM T_RES_THESIS_AUTHOR T "
				+ " INNER JOIN T_RES_THESIS S ON T.THESIS_ID = S.ID AND S.PROJECT_ID IN"
					+ "	(SELECT DISTINCT D.ID FROM T_CODE_SUBJECT D START WITH D.ID IN ('"+xkmlid+"') CONNECT BY D.PID = PRIOR D.ID) "
					+ " AND S.YEAR_ BETWEEN '"+startYear+"' AND '"+endYear+"'"
				+ "INNER JOIN T_CODE_DEPT D ON S.DEPT_ID = D.ID AND S.DEPT_ID IN "
					+ "	("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")" 
				+ " INNER JOIN T_TEA TEA ON TEA.TEA_NO = T.TEA_NO";
		
		
		return baseDao.queryListInLowerKey(sql);
	}
	
	@Override
	public List<Map<String, Object>> queryAuthorNumsByZyjszwJb(String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag) {
		log.debug("作者职称类别！");
		String sql = " SELECT NVL(JB.NAME_,'未说明') NAME, COUNT(DISTINCT TEA.TEA_NO) VALUE"
				+ " FROM T_RES_THESIS_AUTHOR T "
				+ "INNER JOIN T_RES_THESIS S ON T.THESIS_ID = S.ID AND S.PROJECT_ID IN"
					+ "	(SELECT DISTINCT D.ID FROM T_CODE_SUBJECT D START WITH D.ID IN ('"+xkmlid+"') CONNECT BY D.PID = PRIOR D.ID) "
					+ " AND S.YEAR_ BETWEEN '"+startYear+"' AND '"+endYear+"'"
				+ "INNER JOIN T_CODE_DEPT D ON S.DEPT_ID = D.ID AND S.DEPT_ID IN "
					+ "	("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")" 
				+ " INNER JOIN T_TEA TEA ON TEA.TEA_NO = T.TEA_NO"
				+ " LEFT JOIN T_CODE_ZYJSZW Z2 ON TEA.ZYJSZW_ID = Z2.ID AND Z2.ISTRUE = 1"
				+ " LEFT JOIN  t_code JB ON JB.CODE_ = Z2.ZYJSZW_JB_CODE AND JB.code_type = 'ZYJSZW_JB_CODE'"
				+ " GROUP BY  JB.NAME_ ";
		return baseDao.queryListInLowerKey(sql);
	}
	
	@Override
	public List<Map<String, Object>> queryAuthorNumsByZyjszw(String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag) {
		log.debug("作者职称！");
		String sql = " SELECT NVL(Z1.NAME_,'未说明') NAME, COUNT(DISTINCT TEA.TEA_NO) VALUE "
				+ "FROM T_RES_THESIS_AUTHOR T "
				+ "INNER JOIN T_RES_THESIS S ON T.THESIS_ID = S.ID AND S.PROJECT_ID IN"
					+ "	(SELECT DISTINCT D.ID FROM T_CODE_SUBJECT D START WITH D.ID IN ('"+xkmlid+"') CONNECT BY D.PID = PRIOR D.ID) "
					+ " AND S.YEAR_ BETWEEN '"+startYear+"' AND '"+endYear+"'"
				+ "INNER JOIN T_CODE_DEPT D ON S.DEPT_ID = D.ID AND S.DEPT_ID IN "
					+ "	("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")" 
				+ " INNER JOIN T_TEA TEA ON TEA.TEA_NO = T.TEA_NO"
				+ " LEFT JOIN T_CODE_ZYJSZW Z2 ON TEA.ZYJSZW_ID = Z2.ID AND Z2.ISTRUE = 1"
				+ " LEFT JOIN T_CODE_ZYJSZW Z1 ON Z2.PID = Z1.ID AND Z1.ISTRUE = 1"
				+ " GROUP BY  Z1.NAME_ ";
		return baseDao.queryListInLowerKey(sql);
	}
	
	@Override
	public List<Map<String, Object>> queryAuthorNumsByRylb(String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag) {
		log.debug("人员类别！");
		String sql = " SELECT NVL(LB.NAME_,'未说明') NAME, COUNT(DISTINCT TEA.TEA_NO) VALUE"
				+ " FROM T_RES_THESIS_AUTHOR T "
				+ "INNER JOIN T_RES_THESIS S ON T.THESIS_ID = S.ID AND S.PROJECT_ID IN"
					+ "	(SELECT DISTINCT D.ID FROM T_CODE_SUBJECT D START WITH D.ID IN ('"+xkmlid+"') CONNECT BY D.PID = PRIOR D.ID) "
					+ " AND S.YEAR_ BETWEEN '"+startYear+"' AND '"+endYear+"'"
				+ "INNER JOIN T_CODE_DEPT D ON S.DEPT_ID = D.ID AND S.DEPT_ID IN "
					+ "	("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")" 
				+ " INNER JOIN T_TEA TEA ON TEA.TEA_NO = T.TEA_NO "
				+ " LEFT JOIN  T_CODE_AUTHORIZED_STRENGTH LB ON LB.ID = TEA.AUTHORIZED_STRENGTH_ID "
				+ " GROUP BY  LB.NAME_ ";
		return baseDao.queryListInLowerKey(sql);
	}
	
	@Override
	public List<Map<String, Object>> queryAuthorNumsByTeaSource(String xkmlid,String startYear,String endYear,String zzjgid,String shiroTag) {
		log.debug("教职工来源！");
		String sql = "SELECT DECODE(SC2.LEVEL_,1,SC2.NAME_,2,SC1.NAME_,'未说明') NAME,  COUNT(DISTINCT TEA.TEA_NO) VALUE"
				+ " FROM T_RES_THESIS_AUTHOR T "
				+ "INNER JOIN T_RES_THESIS S ON T.THESIS_ID = S.ID AND S.PROJECT_ID IN"
					+ "	(SELECT DISTINCT D.ID FROM T_CODE_SUBJECT D START WITH D.ID IN ('"+xkmlid+"') CONNECT BY D.PID = PRIOR D.ID) "
					+ " AND S.YEAR_ BETWEEN '"+startYear+"' AND '"+endYear+"'"
				+ "INNER JOIN T_CODE_DEPT D ON S.DEPT_ID = D.ID AND S.DEPT_ID IN "
					+ "	("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")" 
				+ " INNER JOIN T_TEA TEA ON TEA.TEA_NO = T.TEA_NO"
				+ " LEFT JOIN  T_CODE_TEA_SOURCE SC2 ON SC2.ID = TEA.TEA_SOURCE_ID"
				+ " LEFT JOIN  T_CODE_TEA_SOURCE SC1 ON SC2.PID = SC1.ID"
				+ " GROUP BY  DECODE(SC2.LEVEL_,1,SC2.NAME_,2,SC1.NAME_,'未说明')";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Map<String, Object> queryAuthorListByPage(Page page,String xkmlid, String startYear, String endYear, String zzjgid,String shiroTag
			,String gender,String value,String education,String age,String zcjb,String zc,String rylb,String source) {
		String sql = "SELECT TEA.TEA_NO," + //职工号 
       "TEA.NAME_," + //姓名
       " NVL( SEX.NAME_,'未说明') GENDER," + //性别
       "DECODE(SC2.LEVEL_, 1, SC2.NAME_, 2, SC1.NAME_, '未说明') TEA_SOURCE," + //来源
       "NVL(floor(MONTHS_BETWEEN(sysdate,  TO_DATE(TEA.BIRTHDAY, 'YYYY-MM-DD')) / 12),0) AGE," + //年龄
       "DECODE(E2.LEVEL_, 1, E2.NAME_, 2, E1.NAME_, NULL, '未说明') EDUCATION," + //学历
       "NVL(JB.NAME_,'未说明') ZCJB," + //职称级别
       "NVL(Z1.NAME_,'未说明') ZC," + //职称
       "NVL(LB.NAME_,'未说明') LB," + //类别
       "COUNT(T.THESIS_ID) VALUE " + // 产量
       " FROM T_RES_THESIS_AUTHOR T "+
       " INNER JOIN T_RES_THESIS S ON T.THESIS_ID = S.ID AND S.PROJECT_ID IN"+
        "	(SELECT DISTINCT D.ID FROM T_CODE_SUBJECT D START WITH D.ID IN ('"+xkmlid+"') CONNECT BY D.PID = PRIOR D.ID) "
			+ " AND S.YEAR_ BETWEEN '"+startYear+"' AND '"+endYear+"'"
		+ " INNER JOIN T_CODE_DEPT D ON S.DEPT_ID = D.ID AND S.DEPT_ID IN "
			+ "	("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") "
		+ " INNER JOIN T_TEA TEA ON TEA.TEA_NO = T.TEA_NO "
		+ " LEFT JOIN T_CODE SEX ON SEX.CODE_ = TEA.SEX_CODE AND SEX.CODE_TYPE = 'SEX_CODE'"
		+ "  LEFT JOIN T_CODE_TEA_SOURCE SC2 ON SC2.ID = TEA.TEA_SOURCE_ID"
		+ "  LEFT JOIN T_CODE_TEA_SOURCE SC1 ON SC2.PID = SC1.ID"
		+ " LEFT JOIN T_CODE_EDUCATION E2 ON TEA.EDU_ID = E2.ID   AND E2.ISTRUE = 1"
		+ " LEFT JOIN T_CODE_EDUCATION E1 ON  E1.ID = E2.PID AND E1.LEVEL_ = 1  AND E1.ISTRUE = 1"
		+ " LEFT JOIN T_CODE_ZYJSZW Z2 ON TEA.ZYJSZW_ID = Z2.ID AND Z2.ISTRUE = 1"
		+ " LEFT JOIN T_CODE_ZYJSZW Z1 ON Z2.PID = Z1.ID AND Z1.ISTRUE = 1"
		+ " LEFT JOIN  t_code JB ON JB.CODE_ = Z2.ZYJSZW_JB_CODE AND JB.code_type = 'ZYJSZW_JB_CODE'"
		+ " LEFT JOIN  T_CODE_AUTHORIZED_STRENGTH LB ON LB.ID = TEA.AUTHORIZED_STRENGTH_ID WHERE 1=1";
		if(gender != null && !gender.equals("") ){
			sql += "AND NVL( SEX.NAME_,'未说明') = '"+gender+"' ";
		}
		
		if(education != null && !education.equals("") ){
			sql += "AND DECODE(E2.LEVEL_, 1, E2.NAME_, 2, E1.NAME_, NULL, '未说明') = '"+education+"' ";
		}
		
		if(age != null && !age.equals("") ){
			if(age.equals("无法解析"))sql += " AND NVL(floor(MONTHS_BETWEEN(sysdate,  TO_DATE(TEA.BIRTHDAY, 'YYYY-MM-DD')) / 12),0) = 0 ";
			if(age.equals("青年教师"))sql += " AND NVL(floor(MONTHS_BETWEEN(sysdate,  TO_DATE(TEA.BIRTHDAY, 'YYYY-MM-DD')) / 12),0) BETWEEN 1 AND 34";
			if(age.equals("中年教师"))sql += " AND NVL(floor(MONTHS_BETWEEN(sysdate,  TO_DATE(TEA.BIRTHDAY, 'YYYY-MM-DD')) / 12),0) BETWEEN 35 AND 50 ";
			if(age.equals("老年教师"))sql += " AND NVL(floor(MONTHS_BETWEEN(sysdate,  TO_DATE(TEA.BIRTHDAY, 'YYYY-MM-DD')) / 12),0) > 50 ";
		}
		
		if(zcjb != null && !zcjb.equals("") ){
			sql += "AND NVL(JB.NAME_,'未说明') = '"+zcjb+"' ";
		}
		
		if(zc != null && !zc.equals("") ){
			sql += "AND NVL(Z1.NAME_,'未说明') = '"+zc+"' ";
		}
		
		if(rylb != null && !rylb.equals("") ){
			sql += "AND NVL(LB.NAME_,'未说明') = '"+rylb+"' ";
		}
		
		if(source != null && !source.equals("") ){
			sql += "AND DECODE(SC2.LEVEL_, 1, SC2.NAME_, 2, SC1.NAME_, '未说明') = '"+source+"' ";
		}
		sql += "  GROUP BY TEA.TEA_NO,TEA.NAME_,NVL(SEX.NAME_, '未说明'), DECODE(SC2.LEVEL_, 1, SC2.NAME_, 2, SC1.NAME_, '未说明'),NVL(floor(MONTHS_BETWEEN(sysdate,TO_DATE(TEA.BIRTHDAY,'YYYY-MM-DD'))/12),0),"
		+ " DECODE(E2.LEVEL_,1, E2.NAME_,2,E1.NAME_,NULL,'未说明'), NVL(JB.NAME_,'未说明'),NVL(Z1.NAME_,'未说明'), NVL(LB.NAME_,'未说明')";
		
		if(value != null && !value.equals("") ){
			if(value.equals("年产1篇"))sql += " HAVING COUNT(T.THESIS_ID) = 1 ";
			if(value.equals("年产2篇"))sql += " HAVING COUNT(T.THESIS_ID) = 2 ";
			if(value.equals("高产作者"))sql += " HAVING COUNT(T.THESIS_ID) > 2 ";
		}
		return baseDao.queryWithPageInLowerKey(sql, page);
	}
}