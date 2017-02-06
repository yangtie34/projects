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
import cn.gilight.research.achievement.serivice.AppraisalService;
 
@Service("appraisalService")
public class AppraisalServiceImpl implements AppraisalService {
	
	@Resource
	private BaseDao baseDao;
	
	@Resource
	private BusinessService businessService;
	
	@Override
	public int queryNums(String xkmlid, String startYear, String endYear,
			String zzjgid,String shiroTag) {
		String sql = "select count(t.id) from t_res_jdcg t where substr(t.time_,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in"
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")";
		return baseDao.queryForInt(sql);
	}

	@Override
	public List<Map<String, Object>> queryChange(String xkmlid,
			String startYear, String endYear, String zzjgid,String shiroTag) {
		String sql = "select substr(t.time_,0,4) year_,count(t.id) nums from t_res_jdcg t where substr(t.time_,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in "
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " group by substr(t.time_,0,4) order by year_";
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
	public Map<String,Object> queryChangeClick(Page page,String xkmlid,
			String startYear, String endYear, String zzjgid, String name,String flag,String shiroTag) {
		String querySql = "";
		if("grade".equals(flag)){
			querySql = " and tco.name_ = '"+name+"'";
		}else if("year".equals(flag)){
			querySql = " and substr(t.time_,0,4) = '"+name+"'";
		}else if("level".equals(flag)){
			querySql = " and tc.name_ = '"+name+"'";
		}else if("mode".equals(flag)){
			querySql = " and co.name_ = '"+name+"'";
		}else if("people".equals(flag)){
			if("未维护".equals(name)){
				querySql = " and ja.people_identity_code is null";
			}else{
				querySql = " and cd.name_ = '"+name+"'";
			}
		}
		String sql = "select tt.*,rownum rn from (select t.id,tcd.name_ dept_name,wmsys.wm_concat(tea.name_) authors,t.name_,t.appraisal_dept,tc.name_ level_,"
				+ " co.name_ mode_,tco.name_ grade_,t.time_ from t_res_jdcg t "
				+ " left join t_res_jdcg_auth ja on ja.appraisal_id = t.id left join t_code_dept tcd on tcd.id = t.dept_id"
				+ " left join t_code co on co.code_type = 'RES_IDENTIFYMODE_CODE' and co.code_ = t.identifymode_code and co.istrue = 1"
				+ " left join t_code tc on tc.code_type = 'RES_IDENTIFYLEVEL_CODE' and tc.code_ = t.identifylevel_code and tc.istrue = 1"
				+ " left join t_code tco on tco.code_type = 'RES_IDENTIFYGRADE_CODE' and tco.code_ = t.identifygrade_code  and tco.istrue = 1"
				+ " left join t_code cd on cd.code_type = 'PEOPLE_IDENTITY_CODE' and cd.code_ = ja.people_identity_code  and cd.istrue = 1 "
				+ " left join t_tea tea on tea.tea_no = ja.tea_no"
				+ " where substr(t.time_,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in "
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") "+querySql
				+ " group by t.id,tcd.name_,t.name_,t.appraisal_dept,tc.name_,co.name_,tco.name_,t.time_ order by t.time_ desc) tt";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}

	@Override
	public List<Map<String, Object>> queryGrade(String xkmlid, String startYear, String endYear,
			String zzjgid,String shiroTag) {
		String sql = "select cd.name_ name,cd.order_,nvl(tt.value,0) value from t_code cd left join ("
				+ " select co.code_, co.name_ name,count(t.id) value from  t_code co"
				+ " left join t_res_jdcg t on co.code_ = t.identifygrade_code and co.code_type = 'RES_IDENTIFYGRADE_CODE' and co.istrue = 1"
				+ " where substr(t.time_,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in "
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") "
				+ " group by co.code_,co.name_,co.order_ ) tt on tt.code_ = cd.code_ where cd.code_type = 'RES_IDENTIFYGRADE_CODE' and cd.istrue = 1 order by cd.order_";
		return baseDao.queryListInLowerKey(sql);
	}
	@Override
	public List<Map<String, Object>> queryLevel(String xkmlid, String startYear, String endYear,
			String zzjgid,String shiroTag) {
		String sql = "select tc.name_ name,tc.order_ ,nvl(tts.nums,0) value from t_code tc left join ("
				+ " select co.code_, co.name_,count(t.id) nums from t_res_jdcg t "
				+ " left join t_code co on co.code_type = 'RES_IDENTIFYLEVEL_CODE' and co.code_ = t.identifylevel_code and co.istrue = 1"
				+ " where substr(t.time_,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in "
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") "
				+ " group by co.code_,co.name_) tts on tts.code_ = tc.code_ where tc.code_type = 'RES_IDENTIFYLEVEL_CODE' and tc.istrue = 1 order by tc.order_";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public List<Map<String, Object>> queryMode(String xkmlid, String startYear, String endYear,
			String zzjgid,String shiroTag) {
		String sql = "select tc.name_ name ,tc.order_,nvl(tt.nums,0) value from t_code tc left join ("
				+ " select co.code_,co.name_,count(t.id) nums from t_res_jdcg t "
				+ " left join t_code co on co.code_type = 'RES_IDENTIFYMODE_CODE' and co.code_ = t.identifymode_code and co.istrue = 1"
				+ " where substr(t.time_,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in "
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") "
				+ " group by co.code_, co.name_) tt on tt.code_ = tc.code_ where tc.code_type = 'RES_IDENTIFYMODE_CODE' and tc.istrue = 1 order by tc.order_";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public List<Map<String, Object>> queryPeopleRole(String xkmlid, String startYear,
			String endYear, String zzjgid,String role,String shiroTag) {
		String sql2 = "";
		if(StringUtils.hasText(role) && !"qb".equals(role)){
			sql2 = " and ja.appraisal_auth_role_code = '"+role+"'";
		}
		String sql = "select nvl(co.name_,'未维护') name,co.order_,count(t.id) value from t_res_jdcg t "
				+ " left join t_res_jdcg_auth ja on ja.appraisal_id = t.id"
				+ " left join t_code co on co.code_type = 'PEOPLE_IDENTITY_CODE' and co.code_ = ja.people_identity_code and co.istrue = 1"
				+ " where substr(t.time_,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in "
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ sql2 + " group by co.name_,co.order_ order by order_";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Page queryList(String xkmlid, String startYear, String endYear,
			String zzjgid, String param,Page page,String grade,String level,String mode,String shiroTag) {
		String sql2 = "";
		if(StringUtils.hasText(param)){
			sql2 = " and t.name_ like '%"+param+"%'";
		}
		String sql3 = "";String sql4 = "";String sql5 ="";
		if(StringUtils.hasText(grade)){
			sql3 = " and tco.code_ = '"+grade+"'";
		}
		if(StringUtils.hasText(level)){
			sql4 = " and tc.code_ = '"+level+"'";
		}
		if(StringUtils.hasText(mode)){
			sql5 = " and co.code_ = '"+mode+"'";
		}
		String sql = "select rownum rn ,tt.dept_name,tt.authors,tt.name_,tt.appraisal_dept,tt.level_,tt.mode_,tt.grade_,tt.time_ from ("
				+ " select t.id,tcd.name_ dept_name,wmsys.wm_concat(tea.name_) authors,t.name_,t.appraisal_dept,tc.name_ level_,co.name_ mode_,tco.name_ grade_,t.time_ from t_res_jdcg t" 
				+ " left join t_res_jdcg_auth ja on ja.appraisal_id = t.id left join t_code_dept tcd on tcd.id = t.dept_id  "
				+ " left join t_code co on co.code_type = 'RES_IDENTIFYMODE_CODE' and co.code_ = t.identifymode_code and co.istrue = 1"
				+ " left join t_code tc on tc.code_type = 'RES_IDENTIFYLEVEL_CODE' and tc.code_ = t.identifylevel_code and tc.istrue = 1 "
				+ " left join t_code tco on tco.code_type = 'RES_IDENTIFYGRADE_CODE' and tco.code_ = t.identifygrade_code and tco.istrue = 1"
				+ " left join t_tea tea on tea.tea_no = ja.tea_no"
				+ " where substr(t.time_,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in "
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") "+ sql2 +sql3 +sql4+ sql5
				+ " group by t.id,tcd.name_,t.name_,t.appraisal_dept,tc.name_,co.name_,tco.name_,t.time_ order by time_ desc) tt ";
		return baseDao.queryPageWithPageInLowerKey(sql, page);
	}

	@Override
	public Map<String, Object> queryAchievementCode() {
		Map<String,Object> result = new HashMap<String, Object>();
		String sql1 = "select t.code_,t.name_ from t_code t where t.code_type = 'RES_IDENTIFYGRADE_CODE' and t.istrue = 1 order by order_";
		List<Map<String,Object>> list1 = baseDao.queryListInLowerKey(sql1);
		String sql2 = "select t.code_,t.name_ from t_code t where t.code_type = 'RES_IDENTIFYLEVEL_CODE' and t.istrue = 1 order by order_";
		List<Map<String,Object>> list2 = baseDao.queryListInLowerKey(sql2);
		String sql3 = "select t.code_,t.name_ from t_code t where t.code_type = 'RES_IDENTIFYMODE_CODE' and t.istrue = 1 order by order_";
		List<Map<String,Object>> list3 = baseDao.queryListInLowerKey(sql3);
		result.put("grade_code", list1);
		result.put("level_code", list2);
		result.put("mode_code", list3);
		return result;
	}

	
	
}
