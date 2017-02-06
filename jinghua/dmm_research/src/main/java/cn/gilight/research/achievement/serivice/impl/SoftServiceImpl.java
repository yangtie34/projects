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
import cn.gilight.research.achievement.serivice.SoftService;
 
@Service("softService")
public class SoftServiceImpl implements SoftService {
	
	@Resource
	private BaseDao baseDao;
	
	@Resource
	private BusinessService businessService;
	
	@Override
	public int querySoftNums(String xkmlid, String startYear, String endYear,
			String zzjgid,String shiroTag) {
		String sql = "select count(t.id) from t_res_soft t where substr(t.regist_time,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in"
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")";
		return baseDao.queryForInt(sql);
	}

	@Override
	public List<Map<String, Object>> querySoftChange(String xkmlid,
			String startYear, String endYear, String zzjgid,String shiroTag) {
		String sql = "select substr(t.regist_time,0,4) year_,count(t.id) nums from t_res_soft t where substr(t.regist_time,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in"
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " group by substr(t.regist_time,0,4) order by year_";
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
	public List<Map<String, Object>> querySoftGet(String xkmlid,
			String startYear, String endYear, String zzjgid,String shiroTag) {
		String sql = "select tc.name_ name ,tc.order_,nvl(tt.nums,0) value from t_code tc left join (select co.code_, co.name_ ,count(t.id) nums from t_res_soft t"
				+ " left join t_code co on co.code_type = 'RES_SOFTWARE_GET_CODE' and co.code_ = t.get_code and co.istrue = 1 "
				+ " where substr(t.regist_time,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in"
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " group by co.code_,co.name_) tt on tt.code_ = tc.code_ where tc.code_type = 'RES_SOFTWARE_GET_CODE' and tc.istrue = 1 order by tc.order_";
		return baseDao.queryListInLowerKey(sql);
	}
	
	@Override
	public List<Map<String, Object>> querySoftPeopleDept(String xkmlid,
			String startYear, String endYear, String zzjgid,String shiroTag) {
		String sql = "select cod.name_ name,count(t.id) value from t_res_soft t "
				+ " left join t_code_dept cod on cod.id = t.dept_id"
				+ " where substr(t.regist_time,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in"
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") "
				+ " group by cod.name_ order by value desc";
	return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Page queryList(String xkmlid, String startYear, String endYear,
			String zzjgid, String param, Page page,String copyright,String getcode,String shiroTag) {
		String sql2 = "";
		if(StringUtils.hasText(param)){
			sql2 = " and t.soft_name like '%"+param+"%'";
		}
		String sql3 = "";String sql4 = "";
		if(StringUtils.hasText(copyright)){
			sql3 = " and tcd.code_ = '"+copyright+"'";
		}
		if(StringUtils.hasText(getcode)){
			sql4 = "and co.code_ = '"+getcode+"'";
		}
		String sql = "select rownum rn,tt.* from (select t.soft_name,cod.name_ dept_name,t.owner,t.complete_man,tcd.name_ copyright_,co.name_ get_,t.complete_time,"
				+ " t.dispatch_time,t.regist_time from t_res_soft t "
				+ " left join t_code_dept cod on cod.id = t.dept_id"
				+ " left join t_code co on co.code_type = 'RES_SOFTWARE_GET_CODE' and co.code_ = t.get_code and co.istrue = 1 "
				+ " left join t_code tcd on tcd.code_type = 'RES_SOFTWARE_COPYRIGHT_CODE' and tcd.code_ = t.copyright_code and tcd.istrue = 1"
				+ " where substr(t.regist_time,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in"
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") "
				+ sql2 +sql3 +sql4 +" order by t.regist_time desc)tt";
		return baseDao.queryPageWithPageInLowerKey(sql, page);
	}

	@Override
	public List<Map<String, Object>> querySoftAuthor(String xkmlid,
			String startYear, String endYear, String zzjgid,String shiroTag) {
		String sql = "select s.tea_no,s.name_,s.nums,s.rn from (select tt.*,rownum rn from ("
				+ " select sa.tea_no,tea.name_,count(t.id) nums from t_res_soft t left join t_res_soft_auth sa on sa.soft_id = t.id"
				+ " left join t_tea tea on tea.tea_no = sa.tea_no"
				+ "  where substr(t.regist_time,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in"
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " group by sa.tea_no,tea.name_ order by nums desc) tt )s where s.rn <= 10";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Map<String, Object> querySoftCode() {
		Map<String,Object> result = new HashMap<String, Object>();
		String sql1 = "select t.code_,t.name_ from t_code t where t.code_type = 'RES_SOFTWARE_COPYRIGHT_CODE' and t.istrue = 1 order by order_";
		List<Map<String,Object>> list1 = baseDao.queryListInLowerKey(sql1);
		String sql2 = "select t.code_,t.name_ from t_code t where t.code_type = 'RES_SOFTWARE_GET_CODE' and t.istrue = 1 order by order_";
		List<Map<String,Object>> list2 = baseDao.queryListInLowerKey(sql2);
		result.put("copyright_code", list1);
		result.put("get_code", list2);
		return result;
	}

	@Override
	public Map<String, Object> querySoftDetail(Page page, String xkmlid,
			String startYear, String endYear, String zzjgid, String name,
			String flag,String shiroTag) {
		String querySql = "";
		if("dept".equals(flag)){
			querySql = " and cod.name_ = '"+name+"'";
		}else if("year".equals(flag)){
			querySql = " and substr(t.regist_time,0,4) = '"+name+"'";
		}else if("get".equals(flag)){
			querySql = " and co.name_ = '"+name+"'";
		}else if("author".equals(flag)){
			querySql = " and sa.tea_no = '"+name+"'";
		}
		
		String sql = "select rownum rn,tt.* from (select t.soft_name,cod.name_ dept_name,t.owner,t.complete_man,tcd.name_ copyright_,co.name_ get_,t.complete_time,"
				+ " t.dispatch_time,t.regist_time from t_res_soft t "
				+ " left join t_res_soft_auth sa on sa.soft_id = t.id"
				+ " left join t_code_dept cod on cod.id = t.dept_id"
				+ " left join t_code co on co.code_type = 'RES_SOFTWARE_GET_CODE' and co.code_ = t.get_code and co.istrue = 1 "
				+ " left join t_code tcd on tcd.code_type = 'RES_SOFTWARE_COPYRIGHT_CODE' and tcd.code_ = t.copyright_code and tcd.istrue = 1 "
				+ " where substr(t.regist_time,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in"
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") "
				+ querySql +" order by t.regist_time desc)tt";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}
	
}
