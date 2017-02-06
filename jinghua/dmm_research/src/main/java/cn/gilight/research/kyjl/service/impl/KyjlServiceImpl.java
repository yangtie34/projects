package cn.gilight.research.kyjl.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gilight.business.service.BusinessService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.TypeConvert;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.research.kyjl.service.KyjlService;
import cn.gilight.research.kyjl.service.RefreshResultService;

@Service("kyjlService")
public class KyjlServiceImpl implements KyjlService{
	
	@Resource
	private BaseDao baseDao;
	
	@Resource
	private RefreshResultService refreshResultService;
	
	@Resource
	private BusinessService businessService;

	@Override
	@Transactional
	public void refreshResult(String year) {
		//删除当前年奖励结果
		String sql = "delete from t_res_award_result t where t.year_ = '"+year+"'";
		baseDao.delete(sql);
		
		//调用刷新奖励结果借口
		refreshResultService.refreshProjectSetup(year);
		refreshResultService.refreshProjectEnd(year);
		refreshResultService.refreshAchievementAward(year);
		refreshResultService.refreshThesisAward(year);
		refreshResultService.refreshPatentAward(year);
		refreshResultService.refreshProjectFundAward(year);
		refreshResultService.refreshTransform(year);
	}

	@Override
	public Map<String, Object> queryFund(String year,String xkmlid,String zzjgid,String shiroTag) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		//查本年奖励金额
		String sql = "select nvl(sum(t.fund),0) total_fund from t_res_award_result t left join t_tea tea on tea.tea_no = t.tea_id"
				+ " where t.year_ = '"+year+"' and t.project_id in"
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id) "
				+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		float total_fund = 0;
		if(list != null && list.size() >0){
			total_fund = MapUtils.getFloatValue(list.get(0), "total_fund");
		}
		//查上一年奖励金额
		String lastYear = TypeConvert.toString(TypeConvert.toInteger(year)-1);
		String lastSql = "select nvl(sum(t.fund),0) last_fund from t_res_award_result t left join t_tea tea on tea.tea_no = t.tea_id"
				+ " where t.year_ = '"+lastYear+"' and t.project_id in"
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id) "
				+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")";
		List<Map<String,Object>> lastList = baseDao.queryListInLowerKey(lastSql);
		float last_fund = 0;
		if(lastList != null && lastList.size() >0){
			last_fund = MapUtils.getFloatValue(lastList.get(0), "last_fund");
		}
		
		//增长金额
		float increase = total_fund - last_fund;
		
		//增幅
		String prop = "";
		if(last_fund != 0 && total_fund != 0){
			prop = MathUtils.getPercent(increase, last_fund);
		}else if(last_fund != 0 && total_fund == 0){
			prop = "0%";
		}else{
			prop = "—";
		}
		
		resultMap.put("total_fund", total_fund);
		resultMap.put("increase", increase);
		resultMap.put("prop", prop);
		return resultMap;
	}

	@Override
	public List<Map<String,Object>> queryAwardPie(String year,String xkmlid,String zzjgid,String shiroTag) {
		String sql = "select co.code_,co.name_ name,nvl(tt.fund,0) value from t_res_code co left join ("
				+ " select t.code_,t.name_,sum(re.fund) fund from t_res_code t left join t_res_award_result re on re.award_code = t.code_"
				+ " left join t_tea tea on tea.tea_no = re.tea_id where re.year_ = '"+year+"' and "
				+ " re.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id) "
				+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") "
				+ " group by t.code_,t.name_) tt on tt.code_ = co.code_ where co.is_true = 1";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public List<Map<String, Object>> queryAwardDept(String year,String xkmlid,String zzjgid,String shiroTag) {
		String sql = "select cd.id code_,cd.name_,count(distinct t.tea_id) nums,nvl(sum(t.fund),0) fund from t_res_award_result t "
				+ "left join t_tea tea on tea.tea_no = t.tea_id"
				+ " left join t_code_dept cd on cd.id = tea.dept_id where t.year_ = '"+year+"'"
				+ " and t.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id) "
				+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") group by cd.id,cd.name_,cd.order_ order by cd.order_ asc ";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Page queryAwardPeople(String year,String xkmlid,String zzjgid, Page page, String param,String shiroTag) {
		String sql2 = "";
		if(StringUtils.hasText(param)){
			sql2 = " where ts.tea_id like '%"+param+"%' or ts.tea_name like '%"+param+"%'";
		}
		String sql = "select ts.tea_id,ts.tea_name,ts.dept_name,ts.fund, ts.rn from ("
				+ " select tt.tea_id,tt.tea_name,tt.dept_name,tt.fund,rownum rn from ("
				+ " select t.tea_id,tea.name_ tea_name,cd.name_ dept_name,nvl(sum(t.fund),0) fund from t_res_award_result t "
				+ " left join t_tea tea on tea.tea_no = t.tea_id left join t_code_dept cd on cd.id = tea.dept_id "
				+ " where t.year_ = '"+year+"' and t.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id) "
				+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") "
				+ " group by cd.name_,tea.name_,t.tea_id order by fund desc) tt)ts "+sql2;
		return baseDao.queryPageWithPageInLowerKey(sql, page);
	}

	@Override
	public Map<String,Object> querySetupList(Page page, String year,String xkmlid,String zzjgid,String shiroTag) {
		String sql = "select t.tea_id,tea.name_ tea_name,co.name_ level_,ps.project_type,c.name_ rank_,t.fund from t_res_award_result t "
				+ " left join t_res_code_pro_setup ps on ps.code_ = t.standard_code"
				+ " left join t_tea tea on tea.tea_no = t.tea_id left join t_code co on co.code_type = 'RES_PROJECT_LEVEL_CODE' "
				+ " and co.code_ = ps.project_level_code"
				+ " left join t_code c on c.code_type = 'RES_PROJECT_RANK_CODE' and c.code_ = ps.project_rank_code "
				+ " where t.award_code = '01' and t.year_ = '"+year+"' and t.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id) "
				+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") ";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}

	@Override
	public List<Map<String, Object>> querySetupLevel(String year,String xkmlid,String zzjgid,String shiroTag) {
		String sql = "select ts.code_,ts.name_ name,nvl(tt.fund,0) value from ("
				+" select c.code_,c.name_ from t_res_code_pro_setup psu left join t_code c on c.code_ = psu.project_level_code"
				+" where c.code_type = 'RES_PROJECT_LEVEL_CODE' group by c.code_,c.name_) ts left join ("
				+" select ps.project_level_code,co.name_ ,sum(t.fund) fund from t_res_award_result t "
				+ " left join t_tea tea on tea.tea_no = t.tea_id "
				+" left join t_res_code_pro_setup ps on t.standard_code = ps.code_ "
				+" left join t_code co on co.code_type = 'RES_PROJECT_LEVEL_CODE' and co.code_ = ps.project_level_code" 
				+" where t.award_code = '01' and t.year_ = '"+year+"' and t.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id) "
				+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")  group by ps.project_level_code,co.name_) tt "
				+" on tt.project_level_code = ts.code_";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public List<Map<String, Object>> querySetupRank(String year,String xkmlid,String zzjgid,String shiroTag) {
		String sql = "select co.code_,co.name_ name,nvl(tt.fund,0) value from t_code co left join ("
				+ " select ps.project_rank_code,co.name_ ,sum(t.fund) fund from t_res_award_result t "
				+ " left join t_res_code_pro_setup ps on t.standard_code = ps.code_  left join t_tea tea on tea.tea_no = t.tea_id"
				+ " left join t_code co on co.code_type = 'RES_PROJECT_RANK_CODE' and co.code_ = ps.project_rank_code "
				+ " where t.award_code = '01' and t.year_ = '"+year+"' and t.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") group by ps.project_rank_code,co.name_) tt "
				+ " on tt.project_rank_code = co.code_ where co.code_type = 'RES_PROJECT_RANK_CODE'";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public List<Map<String, Object>> querySetupDept(String year,String xkmlid,String zzjgid,String shiroTag) {
		String sql = "select tea.dept_id,cd.name_ dept_name,count(distinct t.tea_id) nums,sum(t.fund) fund from t_res_award_result t "
				+ " left join t_res_code_pro_setup ps on ps.code_ = t.standard_code left join t_tea tea on tea.tea_no = t.tea_id"
				+ " left join t_code_dept cd on cd.id = tea.dept_id where t.award_code = '01' and t.year_ = '"+year+"'"
				+ " and t.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") group by tea.dept_id,cd.name_,cd.order_ order by cd.order_ asc";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Map<String, Object> queryEndList(Page page, String year,String xkmlid,String zzjgid,String shiroTag) {
		String sql = "select t.tea_id,tea.name_ tea_name,co.name_ level_,p.category project_type,c.name_ rank_,t.fund from t_res_award_result t "
				+" left join t_tea tea on tea.tea_no = t.tea_id left join t_res_project p on p.id = t.res_id"
				+" left join t_code co on co.code_type = 'RES_PROJECT_LEVEL_CODE'  and co.code_ = p.level_code"
				+" left join t_code c on c.code_type = 'RES_PROJECT_RANK_CODE' and c.code_ = p.rank_code"
				+" where t.award_code = '02' and t.year_ = '"+year+"'"
				+ " and t.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}

	@Override
	public List<Map<String, Object>> queryEndDept(String year,String xkmlid,String zzjgid,String shiroTag) {
		String sql = "select tea.dept_id,cd.name_ dept_name,count(distinct t.tea_id) nums,sum(t.fund) fund from t_res_award_result t "
				+ " left join t_tea tea on tea.tea_no = t.tea_id"
				+ " left join t_code_dept cd on cd.id = tea.dept_id where t.award_code = '02' and t.year_ = '"+year+"'"
				+ " and t.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") group by tea.dept_id,cd.name_,cd.order_ order by cd.order_ asc";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Map<String, Object> queryThesisInList(Page page, String year,String xkmlid,String zzjgid,String shiroTag) {
		String sql = "select co.name_ periodical_type,tea.tea_no tea_id,tea.name_ tea_name,t.fund "
				+ " from t_res_award_result t left join t_res_code_thesis th on th.code_ = t.standard_code"
				+ " left join t_code co on co.code_ = th.periodical_type_code left join t_tea tea on tea.tea_no = t.tea_id"
				+ " where t.award_code = '04' and t.year_ = '"+year+"'"
				+ " and t.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") and th.is_in = 1 and co.code_type = 'RES_PERIODICAL_TYPE_CODE'";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}
	
	@Override
	public Map<String, Object> queryThesisReshipList(Page page, String year,String xkmlid,String zzjgid,String shiroTag) {
		String sql = "select co.name_ periodical_type,tea.tea_no tea_id,tea.name_ tea_name,t.fund from t_res_award_result t "
				+ " left join t_res_code_thesis th on th.code_ = t.standard_code"
				+ " left join t_code co on co.code_ = th.periodical_type_code left join t_tea tea on tea.tea_no = t.tea_id"
				+ " where t.award_code = '04' and t.year_ = '"+year+"'"
				+ " and t.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") and th.is_in = 0 and co.code_type = 'RES_THESIS_RESHIP_CODE'";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}

	@Override
	public List<Map<String, Object>> queryThesisIn(String year,String xkmlid,String zzjgid,String shiroTag) {
		String sql = "select ct.periodical_type_code,c.name_ name,nvl(tt.fund,0) value from  t_res_code_thesis ct"
				+ " left join t_code c on ct.periodical_type_code = c.code_ left join (select co.code_,co.name_ ,sum(t.fund) fund"
				+ " from t_res_award_result t left join t_res_code_thesis th on th.code_ = t.standard_code"
				+ " left join t_tea tea on tea.tea_no = t.tea_id left join t_code co on co.code_ = th.periodical_type_code where t.award_code = '04' and t.year_ = '"+year+"'"
				+ " and t.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") and th.is_in = 1 and co.code_type = 'RES_PERIODICAL_TYPE_CODE'"
				+ " group by co.code_,co.name_) tt on tt.code_ = ct.periodical_type_code where ct.is_in = 1 and c.code_type = 'RES_PERIODICAL_TYPE_CODE'";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public List<Map<String, Object>> queryThesisReship(String year,String xkmlid,String zzjgid,String shiroTag) {
		String sql = "select ct.periodical_type_code,c.name_ name,nvl(tt.fund,0) value from  t_res_code_thesis ct "
				+ " left join t_code c on ct.periodical_type_code = c.code_ left join (select co.code_,co.name_ ,sum(t.fund) fund from t_res_award_result t "
				+ " left join t_res_code_thesis th on th.code_ = t.standard_code left join t_code co on co.code_ = th.periodical_type_code "
				+ " left join t_tea tea on tea.tea_no = t.tea_id"
				+ " where t.award_code = '04' and t.year_ = '"+year+"'"
				+ " and t.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " and th.is_in = 0 and co.code_type = 'RES_THESIS_RESHIP_CODE'"
				+ " group by co.code_,co.name_) tt on tt.code_ = ct.periodical_type_code where ct.is_in = 0 and c.code_type = 'RES_THESIS_RESHIP_CODE'";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public List<Map<String, Object>> queryThesisDept(String year,String xkmlid,String zzjgid,String shiroTag) {
		String sql = "select cd.id dept_id,cd.name_ dept_name,count(distinct t.tea_id) nums,nvl(sum(t.fund),0) fund from t_res_award_result t "
				+ " left join t_tea tea on tea.tea_no = t.tea_id left join t_code_dept cd on cd.id = tea.dept_id"
				+ " where t.award_code = '04' and t.year_ = '"+year+"'"
				+ " and t.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") group by cd.id,cd.name_ ,cd.order_ order by cd.order_ asc";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Map<String, Object> queryPatentList(Page page, String year,String xkmlid,String zzjgid,String shiroTag) {
		String sql = "select c.name_ patent_scope,co.name_ patent_type,t.tea_id,tea.name_ tea_name,cd.name_ dept_name,t.fund from t_res_award_result t "
				+" left join t_res_code_patent cp on cp.code_ = t.standard_code"
				+" left join t_code co on co.code_ = cp.patent_type_code and co.code_type = 'RES_PATENT_TYPE_CODE'"
				+" left join t_code c on c.code_ = cp.patent_scope_code and c.code_type = 'RES_PATENT_SCOPE_CODE'"
				+" left join t_tea tea on tea.tea_no = t.tea_id left join t_code_dept cd on cd.id = tea.dept_id"
				+" where t.award_code = '05' and t.year_ = '"+year+"' and t.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}

	@Override
	public List<Map<String, Object>> queryPatentType(String year,String xkmlid,String zzjgid,String shiroTag) {
		String sql = "select c.code_,c.name_ name ,nvl(tt.fund,0) value from t_res_code_patent cp "
				+" left join t_code c on c.code_ = cp.patent_scope_code left join (select co.code_,co.name_ ,sum(t.fund) fund from t_res_award_result t "
				+" left join t_res_code_patent cp on cp.code_ = t.standard_code left join t_tea tea on tea.tea_no = t.tea_id"
				+" left join t_code co on co.code_ = cp.patent_scope_code and co.code_type = 'RES_PATENT_SCOPE_CODE'"
				+" where t.award_code = '05' and t.year_ = '"+year+"'"
				+ " and t.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " group by co.code_,co.name_) tt on tt.code_ = cp.patent_scope_code"
				+" where c.code_type = 'RES_PATENT_SCOPE_CODE'";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public List<Map<String, Object>> queryPatentDept(String year,String xkmlid,String zzjgid,String shiroTag) {
		String sql = "select tea.dept_id,cd.name_ dept_name,count(distinct t.tea_id) nums,nvl(sum(t.fund),0) fund from t_res_award_result t "
				+ " left join t_res_code_patent cp on cp.code_ = t.standard_code"
				+ " left join t_tea tea on tea.tea_no = t.tea_id left join t_code_dept cd on cd.id = tea.dept_id"
				+ " where t.award_code = '05' and t.year_ = '"+year+"'"
				+ " and t.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") group by tea.dept_id,cd.name_,cd.order_ order by cd.order_ asc";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Map<String, Object> queryFundList(Page page, String year,String xkmlid,String zzjgid,String shiroTag) {
		String sql = "select p.name_ project_name,pf.fund,pf.time_,t.tea_id,tea.name_ tea_name,round(pf.fund*0.06,2) award_fund from t_res_award_result t "
				+" left join t_res_project_fund pf on pf.project_id = t.res_id and substr(pf.time_,0,4) = t.year_"
				+" left join t_res_project p on p.id = t.res_id left join t_tea tea on tea.tea_no = t.tea_id"
				+" where t.award_code = '06' and t.year_ = '"+year+"'"
				+ " and t.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}

	@Override
	public List<Map<String, Object>> queryFundDept(String year,String xkmlid,String zzjgid,String shiroTag) {
		String sql = "select tea.dept_id,cd.name_ dept_name,count(distinct t.tea_id) nums,nvl(sum(round(pf.fund*0.06,2)),0) fund from t_res_award_result t "
				+ " left join t_res_project_fund pf on pf.project_id = t.res_id and substr(pf.time_,0,4) = t.year_"
				+ " left join t_tea tea on tea.tea_no = t.tea_id left join t_code_dept cd on cd.id = tea.dept_id"
				+ " where t.award_code = '06' and t.year_ = '"+year+"'"
				+ " and t.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") group by tea.dept_id,cd.name_,cd.order_ order by cd.order_ asc";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Map<String, Object> queryPersonalFundTotal(String year, String tea_id) {
		Map<String, Object> resultMap = new HashMap<String,Object>();
		String sql = "select ts.tea_id,ts.tea_name, ts.fund,ts.rn from (select tt.tea_id,tt.name_ tea_name,tt.fund,rownum rn from "
				+ " (select t.tea_id,tea.name_,nvl(sum(t.fund),0) fund from t_res_award_result t left join t_tea tea on tea.tea_no = t.tea_id "
				+ " where t.year_ = '"+year+"' group by t.tea_id,tea.name_ order by fund desc) tt) ts where ts.tea_id = '"+tea_id+"'";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list!=null && list.size()>0){
			resultMap = list.get(0);
		}
		return resultMap;
	}

	@Override
	public List<Map<String, Object>> queryPersonalAward(String year,
			String tea_id) {
		String sql = "select c.code_,c.name_ name,nvl(tt.fund,0) value from t_res_code c left join ("
				+ " select rc.code_,rc.name_ ,sum(t.fund) fund from t_res_award_result t left join t_res_code rc on rc.code_ = t.award_code "
				+ " where t.tea_id = '"+tea_id+"' and t.year_ = '"+year+"' group by rc.code_,rc.name_) tt on tt.code_ = c.code_ where c.is_true = 1";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Map<String, Object> queryPersonalSetup(Page page,String year,
			String tea_id) {
		String sql = "select ts.name_,ts.project_level,ts.project_type,ts.project_rank,ts.teas,ts.fund,ts.year_ from ("
				+ " select tt.name_,tt.project_level,tt.project_type,tt.project_rank,wmsys.wm_concat(tt.people_name)"
				+ " OVER(PARTITION BY tt.name_,tt.project_level,tt.project_type,tt.project_rank,tt.fund,tt.year_"
				+ " ORDER BY tt.order_) teas, row_number() OVER(PARTITION BY tt.name_,tt.project_level,tt.project_type,tt.project_rank,tt.fund,tt.year_"
				+ " ORDER BY tt.order_ desc) rs ,tt.fund,tt.year_ from ("
				+ " select p.name_,co.name_ project_level,p.category project_type,c.name_ project_rank,pa.people_name ,pa.order_,t.fund,t.year_ "
				+ " from t_res_award_result t left join t_res_code_pro_setup ps on ps.code_ = t.standard_code"
				+ " left join t_res_project p on p.id = t.res_id left join t_code co on co.code_ = p.level_code and co.code_type = 'RES_PROJECT_LEVEL_CODE'"
				+ " left join t_code c on c.code_ = p.rank_code and c.code_type = 'RES_PROJECT_RANK_CODE'"
				+ " left join t_res_project_auth pa on pa.pro_id = p.id "
				+ " where t.tea_id = '"+tea_id+"' and t.year_ = '"+year+"' and t.award_code = '01') tt) ts where ts.rs = 1";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}

	@Override
	public Map<String, Object> queryPersonalEnd(Page page,String year, String tea_id) {
		String sql = "select ts.name_,ts.project_level,ts.project_type,ts.project_rank,ts.teas,ts.fund,ts.year_ from ("
				+ " select tt.name_,tt.project_level,tt.project_type,tt.project_rank,wmsys.wm_concat(tt.people_name)"
				+ " OVER(PARTITION BY tt.name_,tt.project_level,tt.project_type,tt.project_rank,tt.fund,tt.year_"
				+ " ORDER BY tt.order_) teas, row_number() OVER(PARTITION BY tt.name_,tt.project_level,tt.project_type,tt.project_rank,tt.fund,tt.year_"
				+ " ORDER BY tt.order_ desc) rs ,tt.fund,tt.year_ from ("
				+ " select p.name_,co.name_ project_level,p.category project_type,c.name_ project_rank,pa.people_name,pa.order_,t.fund,p.stat_year year_ "
				+ " from t_res_award_result t "
				+ " left join t_res_project p on p.id = t.res_id left join t_code co on co.code_ = p.level_code and co.code_type = 'RES_PROJECT_LEVEL_CODE'"
				+ " left join t_code c on c.code_ = p.rank_code and c.code_type = 'RES_PROJECT_RANK_CODE'"
				+ " left join t_res_project_auth pa on pa.pro_id = p.id "
				+ " where t.tea_id = '"+tea_id+"' and t.year_ = '"+year+"' and t.award_code = '02' ) tt) ts where ts.rs = 1";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}

	@Override
	public Map<String, Object> queryPersonalAchievement(Page page,String year,
			String tea_id) {
		String sql = "select ts.name_,ts.level_,ts.award_name,ts.rank_,ts.teas,ts.fund,ts.year_ from ("
				+ " select tt.name_,tt.level_,tt.award_name,tt.rank_,wmsys.wm_concat(tt.people_name)"
				+ " OVER(PARTITION BY tt.name_,tt.level_,tt.award_name,tt.rank_,tt.fund,tt.year_"
				+ " ORDER BY tt.order_) teas, row_number() OVER(PARTITION BY tt.name_,tt.level_,tt.award_name,tt.rank_,tt.fund,tt.year_"
				+ " ORDER BY tt.order_ desc) rs ,tt.fund,tt.year_ from ("
				+ " select hjcg.name_,c.name_ level_,hjcg.award_name,co.name_ rank_,ha.people_name,ha.order_,t.fund,t.year_ from t_res_award_result t "
				+ " left join t_res_hjcg hjcg on hjcg.id = t.res_id "
				+ " left join t_code c on c.code_ = hjcg.level_code and c.code_type = 'RES_AWARD_LEVEL_CODE'"
				+ " left join t_code co on co.code_ = hjcg.rank_code and co.code_type = 'RES_AWARD_RANK_CODE'"
				+ " left join t_res_hjcg_auth ha on ha.outcome_award_id = hjcg.id"
				+ " where t.tea_id = '"+tea_id+"' and t.award_code = '03' and t.year_ = '"+year+"') tt) ts where ts.rs = 1 ";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}

	@Override
	public Map<String, Object> queryPersonalThesis(Page page,String year,
			String tea_id) {
		String sql = "select title_,periodical_type,teas,fund,year_ from ("
				+ " select ts.title_,ts.periodical_type,ts.teas,ts.fund,ts.year_ from (                                 "
				+ " select tt.title_,tt.periodical_type,wmsys.wm_concat(tt.people_name)"
				+ " OVER(PARTITION BY tt.title_,tt.periodical_type,tt.fund,tt.year_"
				+ " ORDER BY tt.order_) teas, row_number() OVER(PARTITION BY tt.title_,tt.periodical_type,tt.fund,tt.year_"
				+ " ORDER BY tt.order_ desc) rs  ,tt.fund,tt.year_ from ("
				+ " select rt.title_,co.name_ periodical_type,ta.people_name,ta.order_,t.fund,t.year_ from t_res_award_result t "
				+ " left join t_res_code_thesis th on th.code_ = t.standard_code "
				+ " left join t_res_thesis rt on rt.id = t.res_id left join t_res_thesis_author ta on ta.thesis_id = rt.id"
				+ " left join t_res_thesis_in ti on ti.thesis_id = t.res_id"
				+ " left join t_code co on co.code_ = ti.periodical_type_code and co.code_type = 'RES_PERIODICAL_TYPE_CODE'"
				+ " where t.award_code = '04' and t.tea_id = '"+tea_id+"' and  t.year_ = '"+year+"' and th.is_in = 1 ) tt ) ts where ts.rs = 1)        "
				+ " union (select ts.title_,ts.periodical_type,ts.teas,ts.fund,ts.year_ from ("
				+ " select tt.title_,tt.periodical_type,wmsys.wm_concat(tt.people_name) "
				+ " OVER(PARTITION BY tt.title_,tt.periodical_type,tt.fund,tt.year_"
				+ " ORDER BY tt.order_) teas, row_number() OVER(PARTITION BY tt.title_,tt.periodical_type,tt.fund,tt.year_"
				+ " ORDER BY tt.order_ desc) rs ,tt.fund,tt.year_ from ("
				+ " select rt.title_,co.name_ periodical_type,ta.people_name,ta.order_,t.fund,t.year_ from t_res_award_result t "
				+ " left join t_res_code_thesis th on th.code_ = t.standard_code "
				+ " left join t_res_thesis rt on rt.id = t.res_id left join t_res_thesis_author ta on ta.thesis_id = rt.id"
				+ " left join t_res_thesis_reship tr on tr.thesis_id = t.res_id"
				+ " left join t_code co on co.code_ = tr.thesis_reship_code and co.code_type = 'RES_THESIS_RESHIP_CODE'"
				+ " where t.award_code = '04' and t.tea_id = '"+tea_id+"' and  t.year_ = '"+year+"' and th.is_in = 0 order by ta.order_ ) tt) ts where ts.rs = 1)";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}

	@Override
	public Map<String, Object> queryPersonalPatent(Page page,String year,
			String tea_id) {
		String sql = "select ts.name_,ts.patent_scope,ts.patent_type,ts.teas,ts.fund,ts.year_ from ("
				+ " select tt.name_,tt.patent_scope,tt.patent_type,wmsys.wm_concat(tt.people_name) "
				+ " OVER(PARTITION BY tt.name_,tt.patent_scope,tt.patent_type,tt.fund,tt.year_"
				+ " ORDER BY tt.order_) teas, row_number() OVER(PARTITION BY tt.name_,tt.patent_scope,tt.patent_type,tt.fund,tt.year_"
				+ " ORDER BY tt.order_ desc) rs,tt.fund,tt.year_ from ("
				+ " select p.name_,c.name_ patent_scope,co.name_ patent_type,pa.people_name,pa.order_,t.fund,p.stat_year year_ from t_res_award_result t "
				+ " left join t_res_patent p on p.id = t.res_id left join t_code co on co.code_ = p.patent_type_code and co.code_type = 'RES_PATENT_TYPE_CODE'"
				+ " left join t_code c on c.code_ = p.patent_scope_code and c.code_type = 'RES_PATENT_SCOPE_CODE'"
				+ " left join t_res_patent_auth pa on pa.patent_id = p.id "
				+ " where t.tea_id = '"+tea_id+"' and t.award_code = '05' and t.year_ = '"+year+"') tt ) ts where ts.rs = 1";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}

	@Override
	public Map<String, Object> queryPersonalFund(Page page,String year,
			String tea_id) {
		String sql = "select ts.name_,ts.fund,ts.time_,ts.teas,ts.award_fund,ts.year_ from ("
				+ " select tt.name_,tt.fund,tt.time_,wmsys.wm_concat(tt.people_name) "
				+ " OVER(PARTITION BY tt.name_,tt.fund,tt.time_,tt.award_fund,tt.year_"
				+ " ORDER BY tt.order_) teas, row_number() OVER(PARTITION BY tt.name_,tt.fund,tt.time_,tt.award_fund,tt.year_"
				+ " ORDER BY tt.order_ desc) rs,tt.award_fund,tt.year_ from ("
				+ " select p.name_,pf.fund,pf.time_,round(pf.fund*0.06,2) award_fund,pa.people_name,pa.order_,t.year_ from t_res_award_result t "
				+ " left join t_res_project_fund pf on pf.project_id = t.res_id and t.year_ = substr(pf.time_,0,4)"
				+ " left join t_res_project p on p.id = t.res_id left join t_res_project_auth pa on pa.pro_id = t.res_id "
				+ " where t.tea_id = '"+tea_id+"' and t.year_ = '"+year+"' and t.award_code = '06' order by pa.order_) tt) ts where ts.rs = 1";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}
	
	@Override
	public Map<String, Object> queryPersonalTransform(Page page,String year,
			String tea_id) {
		String sql = "select ts.contract_no,ts.name_,ts.owner_,ts.fund_,ts.get_fund,ts.teas,ts.award_fund,ts.year_ from ("
				+ " select tt.contract_no,tt.name_,tt.owner_,tt.fund_,tt.get_fund,wmsys.wm_concat(tt.people_name) "
				+ " OVER(PARTITION BY tt.contract_no,tt.name_,tt.owner_,tt.fund_,tt.get_fund,tt.award_fund,tt.year_"
				+ " ORDER BY tt.order_) teas, row_number() OVER(PARTITION BY tt.contract_no,tt.name_,tt.owner_,tt.fund_,tt.get_fund,tt.award_fund,tt.year_"
				+ " ORDER BY tt.order_ desc) rs,tt.award_fund,tt.year_ from ("
				+ " select trans.contract_no,trans.name_,trans.owner_,ta.people_name,ta.order_,trans.fund_,trans.get_fund,t.fund award_fund,t.year_ from t_res_award_result t "
				+ " left join t_res_transform trans on trans.contract_no = t.res_id"
				+ " left join t_res_transform_author ta on ta.trans_no = trans.contract_no"
				+ " where t.tea_id = '"+tea_id+"' and t.award_code = '07' and t.year_ = '"+year+"') tt) ts where ts.rs = 1";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}

	@Override
	public Map<String, Object> queryAchievementList(Page page, String year,String xkmlid,String zzjgid,String shiroTag) {
		String sql = "select co.name_ level_,hjcg.award_name,t.tea_id,tea.name_ tea_name,t.fund,t.year_ from t_res_award_result t "
				+ " left join t_res_hjcg hjcg on hjcg.id = t.res_id"
				+ " left join t_code co on co.code_ = hjcg.level_code and co.code_type = 'RES_AWARD_LEVEL_CODE'"
				+ " left join t_tea tea on tea.tea_no = t.tea_id where t.award_code = '03' and t.year_ = '"+year+"'"
				+ " and t.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") and hjcg.rank_ = 1";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}

	@Override
	public Map<String, Object> queryAchievementList2(Page page, String year,String xkmlid,String zzjgid,String shiroTag) {
		String sql = "select co.name_ level_,hjcg.award_name,t.tea_id,tea.name_ tea_name,hjcg.dept_nums,hjcg.rank_,t.fund,t.year_ from t_res_award_result t "
				+ " left join t_res_hjcg hjcg on hjcg.id = t.res_id"
				+ " left join t_code co on co.code_ = hjcg.level_code and co.code_type = 'RES_AWARD_LEVEL_CODE'"
				+ " left join t_tea tea on tea.tea_no = t.tea_id where t.award_code = '03' and t.year_ = '"+year+"'"
				+ " and t.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") and hjcg.rank_ != 1";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}

	@Override
	public List<Map<String, Object>> queryAchievementDept(String year,String xkmlid,String zzjgid,String shiroTag) {
		String sql = "select cd.id dept_id,cd.name_ dept_name,count(distinct t.tea_id) nums,nvl(sum(t.fund),0) fund from t_res_award_result t "
				+ " left join t_tea tea on tea.tea_no = t.tea_id left join t_code_dept cd on cd.id = tea.dept_id"
				+ " where t.award_code = '03' and t.year_ = '"+year+"'"
				+ " and t.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") group by cd.id,cd.name_,cd.order_ order by cd.order_ asc";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Map<String, Object> queryTransformList(Page page, String year,String xkmlid,String zzjgid,String shiroTag) {
		String sql = "select trans.contract_no,trans.name_,trans.owner_,trans.fund_,trans.get_fund,t.tea_id,tea.name_ tea_name,"
				+ " t.fund award_fund,t.year_ from t_res_award_result t left join t_res_transform trans on trans.contract_no = t.res_id"
				+ " left join t_tea tea on tea.tea_no = t.tea_id where t.award_code = '07' and t.year_ = '"+year+"'"
				+ " and t.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}

	@Override
	public List<Map<String, Object>> queryTransformDept(String year,String xkmlid,String zzjgid,String shiroTag) {
		String sql = "select tea.dept_id,cd.name_ dept_name,count(distinct t.tea_id) nums,nvl(sum(t.fund),0) fund from t_res_award_result t "
				+ " left join t_tea tea on tea.tea_no = t.tea_id left join t_code_dept cd on cd.id = tea.dept_id"
				+ " where t.award_code = '07' and t.year_ = '"+year+"'"
				+ " and t.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") group by tea.dept_id,cd.name_,cd.order_ order by cd.order_ asc";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Map<String,Object> queryAwardDetail(Page page, String year,String xkmlid,String zzjgid ,String paramName,
			String paramValue,String shiroTag) {
		String paramSql = "";
		if(StringUtils.hasText(paramName)){
			if("pie".equals(paramName)){
				paramSql = " and rc.name_ = '"+paramValue+"'";
			}else if("dept".equals(paramName)){
				paramSql = " and cd.name_ = '"+paramValue+"' ";
			}
		}
		String sql = "select rownum rn ,tt.tea_id,tt.tea_name,tt.dept_name,tt.award_name,tt.fund,tt.year_ from ("
				+ " select t.tea_id,tea.name_ tea_name,cd.name_ dept_name,rc.name_ award_name,t.fund,t.year_ from t_res_award_result t "
				+ " left join t_res_code rc on rc.code_ = t.award_code left join t_tea tea on tea.tea_no = t.tea_id left join t_code_dept cd on cd.id = tea.dept_id"
				+ " where t.year_ = '"+year+"' and t.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") "
				+ paramSql+" order by t.fund desc) tt";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}

	@Override
	public Map<String, Object> queryDetail(Page page, String year,String xkmlid,String zzjgid,String name,
			String paramName, String paramValue,String shiroTag) {
		String sql= "";
		String paramSql = "";
		if("dept".equals(paramName)){
			if(StringUtils.hasText(paramValue)){
				paramSql = " and cd.name_ = '"+paramValue+"'";
			}
			sql= "select rownum rn ,tt.tea_name,tt.dept_name,tt.award_name,tt.fund,tt.year_ from ("
					+ " select t.tea_id,tea.name_ tea_name,cd.name_ dept_name,rc.name_ award_name,t.fund,t.year_ from t_res_award_result t "
					+ " left join t_res_code rc on rc.code_ = t.award_code left join t_tea tea on tea.tea_no = t.tea_id "
					+ " left join t_code_dept cd on cd.id = tea.dept_id "
					+ " where rc.name_ = '"+name+"' and t.year_ = '"+year+"' "
					+ " and t.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id) "
					+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") "+paramSql+" order by t.fund desc)tt";
		}else if("level_".equals(paramName)){
			if("高层次项目立项奖".equals(name)){
				if(StringUtils.hasText(paramValue)){
					paramSql = " and c.name_ = '"+paramValue+"'";
				}
			}
			sql = "select rownum rn ,tt.tea_name,tt.dept_name,tt.award_name,tt.fund,tt.year_ from ("
					+ " select t.tea_id,tea.name_ tea_name,cd.name_ dept_name,rc.name_ award_name,t.fund,t.year_ from t_res_award_result t "
					+ " left join t_res_code rc on rc.code_ = t.award_code left join t_tea tea on tea.tea_no = t.tea_id "
					+ " left join t_code_dept cd on cd.id = tea.dept_id left join t_res_project p on p.id = t.res_id "
					+ " left join t_code c on  c.code_ = p.level_code and c.code_type = 'RES_PROJECT_LEVEL_CODE'"
					+ " left join t_code ce on ce.code_ = p.rank_code and ce.code_type = 'RES_PROJECT_RANK_CODE'"
					+ " where rc.name_ = '"+name+"' and t.year_ = '"+year+"'"
					+ " and t.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
					+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"+paramSql+" order by t.fund desc)tt";
			
		}else if("rank_".equals(paramName)){
			if("高层次项目立项奖".equals(name)){
				if(StringUtils.hasText(paramValue)){
					paramSql = " and ce.name_ = '"+paramValue+"'";
				}
			}
			sql = "select rownum rn ,tt.tea_name,tt.dept_name,tt.award_name,tt.fund,tt.year_ from ("
					+ " select t.tea_id,tea.name_ tea_name,cd.name_ dept_name,rc.name_ award_name,t.fund,t.year_ from t_res_award_result t "
					+ " left join t_res_code rc on rc.code_ = t.award_code left join t_tea tea on tea.tea_no = t.tea_id "
					+ " left join t_code_dept cd on cd.id = tea.dept_id left join t_res_project p on p.id = t.res_id "
					+ " left join t_code c on  c.code_ = p.level_code and c.code_type = 'RES_PROJECT_LEVEL_CODE'"
					+ " left join t_code ce on ce.code_ = p.rank_code and ce.code_type = 'RES_PROJECT_RANK_CODE'"
					+ " where rc.name_ = '"+name+"' and t.year_ = '"+year+"'"
					+ " and t.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id) "
					+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") "+paramSql+" order by t.fund desc)tt";
			
		}else if("in".equals(paramName)){
			if("高层次学术论文奖".equals(name)){
				if(StringUtils.hasText(paramValue)){
					paramSql = " and c.name_ = '"+paramValue+"'";
				}
			}
			sql = "select rownum rn ,tt.tea_name,tt.dept_name,tt.award_name,tt.fund,tt.year_ from ("
					+ " select t.tea_id,tea.name_ tea_name,cd.name_ dept_name,rc.name_ award_name,t.fund,t.year_ from t_res_award_result t "
					+ " left join t_res_code rc on rc.code_ = t.award_code left join t_tea tea on tea.tea_no = t.tea_id "
					+ " left join t_code_dept cd on cd.id = tea.dept_id inner join t_res_thesis_in ti on ti.thesis_id = t.res_id"
					+ " left join t_code c on c.code_ = ti.periodical_type_code and c.code_type = 'RES_PERIODICAL_TYPE_CODE'"
					+ " where rc.name_ = '"+name+"' and t.year_ = '"+year+"'"
					+ " and t.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
					+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") "+paramSql+" order by t.fund desc)tt";
			
		}else if("reship".equals(paramName)){
			if("高层次学术论文奖".equals(name)){
				if(StringUtils.hasText(paramValue)){
					paramSql = " and c.name_ = '"+paramValue+"'";
				}
			}
			sql = "select rownum rn ,tt.tea_name,tt.dept_name,tt.award_name,tt.fund,tt.year_ from ("
					+ " select t.tea_id,tea.name_ tea_name,cd.name_ dept_name,c.name_,rc.name_ award_name,t.fund,t.year_ from t_res_award_result t "
					+ " left join t_res_code rc on rc.code_ = t.award_code left join t_tea tea on tea.tea_no = t.tea_id "
					+ " left join t_code_dept cd on cd.id = tea.dept_id inner join t_res_thesis_reship tr on tr.thesis_id = t.res_id "
					+ " left join t_code c on c.code_ = tr.thesis_reship_code and c.code_type = 'RES_THESIS_RESHIP_CODE'"
					+ " where rc.name_ = '"+name+"' and t.year_ = '"+year+"'"
					+ " and t.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id) "
					+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"+paramSql+" order by t.fund desc)tt";
			
		}else if("scope".equals(paramName)){
			if("发明专利奖".equals(name)){
				if(StringUtils.hasText(paramValue)){
					paramSql = " and c.name_ = '"+paramValue+"'";
				}
			}
			sql = "select rownum rn ,tt.tea_name,tt.dept_name,tt.award_name,tt.fund,tt.year_ from ("
					+" select t.tea_id,tea.name_ tea_name,cd.name_ dept_name,c.name_,rc.name_ award_name,t.fund,t.year_ from t_res_award_result t "
					+" left join t_res_code rc on rc.code_ = t.award_code left join t_tea tea on tea.tea_no = t.tea_id "
					+" left join t_code_dept cd on cd.id = tea.dept_id inner join t_res_patent p on p.id = t.res_id"
					+" left join t_code c on c.code_ = p.patent_scope_code and c.code_type = 'RES_PATENT_SCOPE_CODE'"           
					+" where rc.name_ = '"+name+"' and t.year_ = '"+year+"'"
					+ " and t.project_id in (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
					+ " and tea.dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+") "+paramSql+" order by t.fund desc)tt";
			
		}
		return baseDao.queryWithPageInLowerKey(sql, page);
	}

}
