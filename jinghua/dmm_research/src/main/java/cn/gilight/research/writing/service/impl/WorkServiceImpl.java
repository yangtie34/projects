package cn.gilight.research.writing.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.gilight.business.service.BusinessService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.research.writing.service.WorkService;

@Service("workService")
public class WorkServiceImpl implements WorkService{
	
	@Resource
	private BaseDao baseDao;

	@Resource
	private BusinessService businessService;
	
	@Override
	public int queryWorkNums(String xkmlid, String startYear, String endYear,String zzjgid,String shiroTag) {
		String sql = "select count(w.id) from t_res_work w where substr(w.press_time,0,4) between '"+startYear+"' and '"+endYear+"' and w.project_id in"
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and w.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")";
		return baseDao.queryForInt(sql);
	}

	@Override
	public List<Map<String, Object>> queryWorkAuthorRole(String xkmlid,String startYear, String endYear, String zzjgid,String shiroTag) {
		String sql = "select c.code_,c.name_,count(distinct w.id) nums from t_res_work_author t "
				+ " inner join t_res_work w on w.id = t.work_id left join t_code c "
				+ " on c.code_type = 'WORK_AUTHOR_ROLE_CODE' and c.code_ = t.work_author_role_code and c.istrue = 1"
				+ " where substr(w.press_time,0,4) between '"+startYear+"' and '"+endYear+"'  and w.project_id in"
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and w.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " group by c.code_,c.name_";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public List<Map<String, Object>> queryWorkNumsChange(String xkmlid,String startYear, String endYear, String zzjgid,String shiroTag) {
		String sql = "select substr(t.press_time,0,4) year_ ,count(*) nums from t_res_work t "
				+ " where substr(t.press_time,0,4) between '"+startYear+"' and '"+endYear+"'  and t.project_id in "
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " group by substr(t.press_time,0,4) order by year_";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Page queryWorkList(Page page, String xkmlid,String startYear, String endYear, String zzjgid, String param,String shiroTag) {
		String sql2 = "";
		if(StringUtils.hasText(param)){
			sql2 = " and t.title_ like '%"+param+"%'";
		}
		String sql = "select rownum rn ,tt.* from (select t.id,cd.name_ dept_name,t.authors,t.title_,t.press_name, t.number_,"
				+ " t.press_time  from t_res_work t left join t_code_dept cd on cd.id = t.dept_id"
				+ " where substr(t.press_time,0,4) between '"+startYear+"' and '"+endYear+"'  and t.project_id in "
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ sql2 +" order by t.press_time desc) tt ";
		return baseDao.queryPageWithPageInLowerKey(sql, page);
	}

	@Override
	public List<Map<String, Object>> queryWorkDept(String xkmlid,String startYear, String endYear, String zzjgid,String shiroTag) {
		String sql = "select cd.name_ name ,count(*) value from t_res_work t left join t_code_dept cd on cd.id = t.dept_id"
				+ " where substr(t.press_time,0,4) between '"+startYear+"' and '"+endYear+"'  and t.project_id in "
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " group by cd.name_ order by value";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Map<String,Object> queryClickDetail(Page page, String xkmlid, String startYear,String endYear, String zzjgid, String name, String flag,String shiroTag) {
		String sql2 = "";
		if("year".equals(flag)){
			sql2 = " and substr(t.press_time,0,4) = '"+name+"'";
		}else if("dept".equals(flag)){
			sql2 = " and cd.name_ = '"+name+"'";
		}else if("role".equals(flag)){
			sql2 = " and co.code_ = '"+name+"'";
		}
		String sql = "select rownum rn ,tt.* from (select t.id,cd.name_ dept_name,t.authors,t.title_,t.press_name, t.number_,"
				+ " t.press_time  from t_res_work t left join t_code_dept cd on cd.id = t.dept_id"
				+ " left join t_res_work_author wa on wa.work_id = t.id left join t_code co on co.code_type = 'WORK_AUTHOR_ROLE_CODE' "
				+ " and co.code_ = wa.work_author_role_code and co.istrue = 1"
				+ " where substr(t.press_time,0,4) between '"+startYear+"' and '"+endYear+"'  and t.project_id in "
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ sql2 + " group by t.id,cd.name_ ,t.authors,t.title_,t.press_name, t.number_,t.press_time order by t.press_time desc) tt ";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}


}
