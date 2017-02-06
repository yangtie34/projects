package cn.gilight.research.kyjl.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.research.kyjl.service.RefreshResultService;

@Service("refreshResultService")
public class RefreshResultServiceImpl implements RefreshResultService{
	
	@Resource
	private BaseDao baseDao;

	@Override
	public void refreshProjectSetup(String year) {
		//刷新项目立项奖励结果
		String sql = "insert into t_res_award_result(id,tea_id,award_code,standard_code,fund,res_id,year_,project_id) "
				+ " select ID_SEQ.NEXTVAL,pa.people_id,'01',t.code_,t.award_fund,pro.id,pro.stat_year,pro.project_id from t_res_code_pro_setup t "
				+" left join t_res_project pro on pro.category = t.project_type "
				+" left join t_res_project_auth pa on pa.pro_id = pro.id"
				+" where pro.level_code = t.project_level_code and pro.rank_code = t.project_rank_code" 
				+" and pro.rank_ = 1 and pa.order_ = 1 and pro.stat_year = '"+year+"'";
		baseDao.insert(sql);
	}

	@Override
	public void refreshProjectEnd(String year) {
		//刷新项目结项奖励结果
		String sql = "insert into t_res_award_result(id,tea_id,award_code,standard_code,fund,res_id,year_,project_id) "
				+ " select ID_SEQ.NEXTVAL,pa.people_id,'02','',1,pro.id,pro.stat_year,pro.project_id from t_res_project pro "
				+ " left join t_res_project_auth pa on pa.pro_id = pro.id"
				+ " where pro.rank_ = 1 and pro.has_end_award = 1 and pa.order_ = 1 and pro.stat_year = '"+year+"'";
		baseDao.insert(sql);
	}

	@Override
	public void refreshAchievementAward(String year) {
		//刷新获奖成果奖励结果
		//国家级level_code='10'
		String sql1 = "insert into t_res_award_result(id,tea_id,award_code,standard_code,fund,res_id,year_,project_id) "
				+ " select ID_SEQ.NEXTVAL,ha.people_id,'03',ca.code_,case when t.rank_ = 1 then ca.award_fund "
				+ " else round(ca.award_fund*cdr.prop,2) end fund,t.id,t.stat_year,t.project_id from t_res_hjcg t "
				+ " inner join t_res_code_achievement ca on t.award_name = ca.award_name "
				+ " left join t_res_hjcg_auth ha on ha.outcome_award_id = t.id"
				+ " left join t_res_code_dept_rank cdr on cdr.dept_nums = t.dept_nums and cdr.dept_rank = t.rank_"
				+ " where t.level_code = '10' and ha.order_ = 1 and t.stat_year = '"+year+"'";
		baseDao.insert(sql1);
		//省部级 level_code='20'
		String sql2 = "insert into t_res_award_result(id,tea_id,award_code,standard_code,fund,res_id,year_,project_id) "
				+ " select ID_SEQ.NEXTVAL,ha.people_id,'03',ca.code_,case when t.rank_ = 1 then ca.award_fund "
				+ " else round(ca.award_fund*cdr.prop,2) end fund,t.id,t.stat_year,t.project_id from t_res_hjcg t "
				+ " inner join t_res_code_achievement ca on ca.award_level_code = t.level_code and t.rank_code = ca.award_rank_code"
				+ " left join t_res_code_dept_rank cdr on cdr.dept_nums = t.dept_nums and cdr.dept_rank = t.rank_"
				+ " left join t_res_hjcg_auth ha on ha.outcome_award_id = t.id "
				+ " where ha.order_ = 1 and t.level_code = '20' and t.stat_year = '"+year+"'";
		baseDao.insert(sql2);
		//一级学会特等奖level_code = '03'
		String sql3 = "insert into t_res_award_result(id,tea_id,award_code,standard_code,fund,res_id,year_,project_id) "
				+ " select ID_SEQ.NEXTVAL,ha.people_id,'03',ca.code_,case when t.rank_ = 1 then ca.award_fund "
				+ " else round(ca.award_fund*cdr.prop,2) end fund,t.id,t.stat_year,t.project_id from t_res_hjcg t "
				+ " inner join t_res_code_achievement ca on ca.award_level_code = t.level_code"
				+ " left join t_res_code_dept_rank cdr on cdr.dept_nums = t.dept_nums and cdr.dept_rank = t.rank_"
				+ " left join t_res_hjcg_auth ha on ha.outcome_award_id = t.id "
				+ " where ha.order_ = 1 and t.is_highest_award = 1 and t.level_code = '03' and t.stat_year = '"+year+"'";
		baseDao.insert(sql3);
		
	}

	@Override
	public void refreshThesisAward(String year) {
		//刷新学术论文奖励结果
		//收录论文奖励
		String inSql = "insert into t_res_award_result(id,tea_id,award_code,standard_code,fund,res_id,year_,project_id) "
				+" select ID_SEQ.NEXTVAL,ta.people_id,'04',t.code_,t.award_fund,ti.thesis_id,ti.stat_year,th.project_id "
				+" from t_res_code_thesis t left join t_res_thesis_in ti on ti.periodical_type_code = t.periodical_type_code"
				+" left join t_res_thesis th on th.id = ti.thesis_id left join t_res_thesis_author ta on ta.thesis_id = th.id "
				+" where th.rank_ = 1 and t.is_in = 1 and ta.order_ = 1 and ti.stat_year = '"+year+"'";
		baseDao.insert(inSql);
		
		//转载论文奖励
		String reshipSql = "insert into t_res_award_result(id,tea_id,award_code,standard_code,fund,res_id,year_,project_id) "
				+" select ID_SEQ.NEXTVAL,ta.people_id,'04',t.code_,t.award_fund,tr.thesis_id,tr.stat_year,th.project_id from t_res_code_thesis t "
				+" left join t_res_thesis_reship tr on tr.thesis_reship_code = t.periodical_type_code"
				+" left join t_res_thesis th on th.id = tr.thesis_id left join t_res_thesis_author ta on ta.thesis_id = tr.thesis_id"
				+" where th.rank_ = 1 and t.is_in = 0 and ta.order_ = 1 and tr.stat_year = '"+year+"'";
		baseDao.insert(reshipSql);
		
	}

	@Override
	public void refreshPatentAward(String year) {
		//刷新发明专利奖励结果
		String sql = "insert into t_res_award_result(id,tea_id,award_code,standard_code,fund,res_id,year_,project_id)"
				+" select ID_SEQ.NEXTVAL,pa.people_id,'05',t.code_,t.award_fund,p.id,p.stat_year,p.project_id"
				+" from t_res_code_patent t left join t_res_patent p on p.patent_type_code = t.patent_type_code and t.patent_scope_code = p.patent_scope_code"
				+" left join t_res_patent_auth pa on pa.patent_id = p.id "
				+" where p.dept_nums = 1 and p.rank_ = 1 and  pa.order_ = 1 and p.stat_year = '"+year+"'";
		baseDao.insert(sql);
		
	}

	@Override
	public void refreshProjectFundAward(String year) {
		//刷新科研经费奖励结果
		String sql = "insert into t_res_award_result(id,tea_id,award_code,standard_code,fund,res_id,year_,project_id) "
				+ " select ID_SEQ.NEXTVAL,tt.people_id,'06','',round(tt.fund*0.06,2),tt.id,tt.time_,tt.project_id  from"
				+ " ( select pa.people_id,nvl(sum(t.fund),0) fund,pro.id,substr(t.time_,0,4) time_,pro.project_id from t_res_project_fund t "
				+ " inner join t_res_project pro on pro.id = t.project_id"
				+ " left join t_res_project_auth pa on pa.pro_id = pro.id "
				+ " where pro.rank_ = 1 and pa.order_ = 1 and substr(t.time_,0,4) = '"+year+"' group by pa.people_id,pro.id,substr(t.time_,0,4),pro.project_id ) tt";
		baseDao.insert(sql);
	}
	
	@Override
	public void refreshTransform(String year) {
		//刷新科研成果转化奖励结果
		String sql = "insert into t_res_award_result(id,tea_id,award_code,standard_code,fund,res_id,year_,project_id) "
         +" select ID_SEQ.NEXTVAL,ta.people_id,'07','',round(trans.get_fund*0.85,2),trans.contract_no,trans.stat_year,trans.project_id from t_res_transform trans "
         +" left join t_res_transform_author ta on ta.trans_no = trans.contract_no"
         +"　where trans.stat_year = '"+year+"' and ta.order_ = 1";
		baseDao.insert(sql);
	}
	
	

}
