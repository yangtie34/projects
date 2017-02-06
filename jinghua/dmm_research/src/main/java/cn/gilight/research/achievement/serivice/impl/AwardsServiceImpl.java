package cn.gilight.research.achievement.serivice.impl;

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
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.research.achievement.serivice.AwardsService;
 
@Service("awardsService")
public class AwardsServiceImpl implements AwardsService {
	
	@Resource
	private BaseDao baseDao;
	
	@Resource
	private BusinessService businessService;
	
	@Override
	public int queryAwardsNums(String xkmlid, String startYear, String endYear,
			String zzjgid,String shiroTag) {
		String sql = "select count(t.id) from t_res_hjcg t where substr(t.award_time,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in "
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")";
		return baseDao.queryForInt(sql);
	}

	@Override
	public List<Map<String, Object>> queryAwardsChange(String xkmlid,
			String startYear, String endYear, String zzjgid,String shiroTag) {
		String sql = "select substr(t.award_time,0,4) year_,count(t.id) nums from t_res_hjcg t where substr(t.award_time,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in "
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " group by substr(t.award_time,0,4) order by year_";
		List<Map<String, Object>> temp = baseDao.queryListInLowerKey(sql);
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		for (int i = TypeConvert.toInteger(startYear); i <= TypeConvert.toInteger(endYear); i++) {
			Map<String, Object> it = new HashMap<String, Object>();
			it.put("year_",i);
			it.put("nums", 0);
			for (int j = 0; j < temp.size(); j++) {
				Map<String, Object> item = temp.get(j);
				if(item.get("year_").equals(TypeConvert.toString(i))){
					it.put("nums", item.get("nums"));
					break;
				}
			}
			result.add(it);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> queryAwardsLevel(String xkmlid,
			String startYear, String endYear, String zzjgid,String shiroTag) {
		String sql = "select tc.name_ name,tc.order_,nvl(tt.nums,0) value from t_code tc left join (select co.code_,co.name_ ,count(t.id) nums from t_res_hjcg t "
				+ " left join t_code co on co.code_type = 'RES_AWARD_LEVEL_CODE' and co.code_ = t.level_code and co.istrue = 1"
				+ " where substr(t.award_time,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in "
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " group by co.code_,co.name_) tt on tt.code_ = tc.code_ where tc.code_type = 'RES_AWARD_LEVEL_CODE' and tc.istrue = 1 order by tc.order_";
		return baseDao.queryListInLowerKey(sql);
	}
	
	@Override
	public Object queryPeopleDept(String xkmlid, String startYear,
			String endYear, String zzjgid,String shiroTag) {
		String sql = "select tcd.name_ name,count(t.id) value from t_res_hjcg t left join t_code_dept tcd on tcd.id = t.dept_id"
				+ " where substr(t.award_time,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in"
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") "
				+ " group by tcd.name_ order by value desc";
		return baseDao.queryListInLowerKey(sql);
	}
	
	@Override
	public List<Map<String, Object>> queryAwardsType(String xkmlid,
			String startYear, String endYear, String zzjgid,String shiroTag) {
		String sql = "select tc.name_ name ,tc.order_,nvl(tt.nums,0) value from t_code tc left join (select co.code_, co.name_,count(t.id) nums from t_res_hjcg t "
				+ " left join t_code co on co.code_type = 'RES_AWARD_CATEGORY_CODE' and co.code_ = t.category_code and co.istrue = 1"
				+ " and co.istrue = 1 where substr(t.award_time,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in "
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") "
				+ " group by co.code_,co.name_) tt on tt.code_ = tc.code_ where tc.code_type = 'RES_AWARD_CATEGORY_CODE' and tc.istrue = 1 order by tc.order_";
		return baseDao.queryListInLowerKey(sql);
	}
	
	@Override
	public List<Map<String, Object>> queryAwardsPeopleRole(String xkmlid,
			String startYear, String endYear, String zzjgid,String role,String shiroTag) {
		String sql2 = "";
		if(StringUtils.hasText(role) && !"qb".equals(role)){
			sql2 = " and ha.award_auth_role_code = '"+role+"'";
		}
		String sql = "select nvl(co.name_,'未维护') name,co.order_,count(t.id) value from t_res_hjcg t "
				+ " left join t_res_hjcg_auth ha on ha.outcome_award_id = t.id"
				+ " left join t_code co on co.code_type = 'PEOPLE_IDENTITY_CODE' and co.code_ = ha.people_identity_code and co.istrue = 1"
				+ " where substr(t.award_time,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in "
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ sql2 +" group by co.name_,co.order_ order by order_";
	return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Page queryList(String xkmlid, String startYear, String endYear,
			String zzjgid, String param, Page page,String level,String shiroTag) {
		String sql2 = "";
		if(StringUtils.hasText(param)){
			sql2 = " and t.name_ like '%"+param+"%'";
		}
		String sql3 = "";
		if(StringUtils.hasText(level)){
			sql3 = " and tc.code_ = '"+level+"'";
		}
		String sql = "select rownum rn ,dept_name,prizewinner,name_,award_name,awards_level,awards_category,awards_rank,award_dept,award_time from("
				+ " select tcd.name_ dept_name,wmsys.wm_concat(tea.name_) prizewinner,t.name_,t.award_name,tc.name_ awards_level,co.name_ awards_category,"
				+ " tco.name_ awards_rank,t.award_dept,t.award_time from t_res_hjcg t "
				+ " left join t_res_hjcg_auth ha on ha.outcome_award_id = t.id "
				+ " left join t_tea tea on tea.tea_no = ha.people_id left join t_code_dept tcd on tcd.id = t.dept_id"
				+ " left join t_code tc on tc.code_type = 'RES_AWARD_LEVEL_CODE' and tc.code_ = t.level_code and tc.istrue = 1 "
				+ " left join t_code co on co.code_type = 'RES_AWARD_CATEGORY_CODE' and co.code_ = t.category_code and co.istrue = 1 "
				+ " left join t_code tco on tco.code_type = 'RES_AWARD_RANK_CODE' and tco.code_ = t.rank_code and tco.istrue = 1"
				+ " where substr(t.award_time,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in "
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") "+ sql2 +sql3
				+ " group by tcd.name_,t.name_,t.award_name,tc.name_,co.name_,tco.name_,t.award_dept,t.award_time order by award_time desc)";
		return baseDao.queryPageWithPageInLowerKey(sql, page);
	}

	@Override
	public Map<String, Object> queryAwardsCode() {
		Map<String, Object> result = new HashMap<String, Object>();
		String sql = "select t.code_,t.name_ from t_code t where t.code_type = 'RES_AWARD_LEVEL_CODE' and t.istrue = 1 order by order_";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		result.put("level_code", list);
		return result;
	}

	@Override
	public Map<String, Object> queryDetailList(Page page, String xkmlid,
			String startYear, String endYear, String zzjgid, String name,
			String flag,String shiroTag) {
		String querySql = "";
		if("dept".equals(flag)){
			querySql = " and tcd.name_ = '"+name+"'";
		}else if("year".equals(flag)){
			querySql = " and substr(t.award_time,0,4) = '"+name+"'";
		}else if("level".equals(flag)){
			querySql = " and tc.name_ = '"+name+"'";
		}else if("type".equals(flag)){
			querySql = " and co.name_ = '"+name+"'";
		}else if("people".equals(flag)){
			if("未维护".equals(name)){
				querySql = " and ha.people_identity_code is null";
			}else{
				querySql = " and c.name_ = '"+name+"'";
			}
		}
		String sql = "select rownum rn ,dept_name,prizewinner,name_,award_name,awards_level,awards_category,awards_rank,award_dept,award_time from("
				+ " select tcd.name_ dept_name,wmsys.wm_concat(tea.name_) prizewinner,t.name_,t.award_name,tc.name_ awards_level,co.name_ awards_category,"
				+ " tco.name_ awards_rank,t.award_dept,t.award_time from t_res_hjcg t "
				+ " left join t_res_hjcg_auth ha on ha.outcome_award_id = t.id "
				+ " left join t_tea tea on tea.tea_no = ha.people_id left join t_code_dept tcd on tcd.id = t.dept_id"
				+ " left join t_code tc on tc.code_type = 'RES_AWARD_LEVEL_CODE' and tc.code_ = t.level_code and  tc.istrue = 1"
				+ " left join t_code co on co.code_type = 'RES_AWARD_CATEGORY_CODE' and co.code_ = t.category_code and co.istrue = 1"
				+ " left join t_code tco on tco.code_type = 'RES_AWARD_RANK_CODE' and tco.code_ = t.rank_code and tco.istrue = 1 "
				+ " left join t_code c on c.code_type = 'PEOPLE_IDENTITY_CODE' and c.code_ = ha.people_identity_code and c.istrue = 1"
				+ " where substr(t.award_time,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in "
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") "+ querySql
				+ " group by tcd.name_,t.name_,t.award_name,tc.name_,co.name_,tco.name_,t.award_dept,t.award_time order by award_time desc)";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}

	
	
}
