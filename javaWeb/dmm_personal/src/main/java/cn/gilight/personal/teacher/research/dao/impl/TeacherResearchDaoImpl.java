package cn.gilight.personal.teacher.research.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.personal.teacher.research.dao.TeacherResearchDao;

@Repository("teacherResearchDao")
public class TeacherResearchDaoImpl implements TeacherResearchDao{

	@Autowired
	private BaseDao baseDao;

	@Override
	public int getProjectCounts(String tea_id) {
		String sql = "select * from t_res_project_auth t where t.people_id = '"+ tea_id +"'";
		return baseDao.queryForInt(sql);
	}

	@Override
	public int getWorkCounts(String tea_id) {
		String sql = "select * from t_res_work_author t where t.people_id = '"+ tea_id +"'";
		return baseDao.queryForInt(sql);
	}

	@Override
	public int getPatentCounts(String tea_id) {
		String sql = "select * from t_res_patent_auth t where t.people_id = '"+ tea_id +"'";
		return baseDao.queryForInt(sql);
	}

	@Override
	public int getThesisCounts(String tea_id) {
		String sql ="select * from t_res_thesis_author t where t.people_id = '"+ tea_id +"'";
		return baseDao.queryForInt(sql);
	}

	@Override
	public int getOutcomeAwardCounts(String tea_id) {
		String sql = "select * from t_res_hjcg_auth t where t.people_id = '"+ tea_id +"'";
		return baseDao.queryForInt(sql);
	}

	@Override
	public int getOutcomeAppraisalCount(String tea_id) {
		String sql = "select * from t_res_jdcg_auth t where t.people_id = '"+ tea_id +"'";
		return baseDao.queryForInt(sql);
	}

	@Override
	public int getAwardThesisCounts(String tea_id) {
		String sql = "select * from t_res_thesis_author t inner join t_res_thesis_award ta on t.thesis_id = ta.thesis_id where t.people_id='"+ tea_id +"'";
		return baseDao.queryForInt(sql);
	}

	@Override
	public int getInThesisCounts(String tea_id) {
		String sql = "select * from t_res_thesis_author t inner join t_res_thesis_in ti on t.thesis_id = ti.thesis_id where t.people_id='"+ tea_id +"'";
		return baseDao.queryForInt(sql);
	}
	
	@Override
	public int getMeetingThesisCounts(String tea_id) {
		String sql = "select * from t_res_thesis_author t inner join t_res_thesis_meeting ti on t.thesis_id = ti.thesis_id where t.people_id='"+ tea_id +"'";
		return baseDao.queryForInt(sql);
	}

	@Override
	public int getReshipThesisCounts(String tea_id) {
		String sql = "select * from t_res_thesis_author t inner join t_res_thesis_reship tr on t.thesis_id = tr.thesis_id where t.people_id='"+ tea_id +"'";
		return baseDao.queryForInt(sql);
	}

	@Override
	public List<Map<String, Object>> getThesises(String tea_id) {
		String sql = "select tt.thesis_id,title_ title_,year_,wmsys.wm_concat(rta.people_name) authors ,authors first_author,periodical,periodical_type,njqy from ("
				+ " select ta.thesis_id,t.title_,t.year_,t.authors,t.periodical,co.name_ periodical_type ,t.njqy from t_res_thesis_author ta inner join t_res_thesis t on ta.thesis_id = t.id"
				+ " left join t_code co on co.code_type = 'RES_PERIODICAL_TYPE_CODE' and co.code_ = t.periodical_type_code where ta.people_id = '"+tea_id+"') tt"
				+ " left join t_res_thesis_author rta"
				+ " on rta.thesis_id = tt.thesis_id group by "
				+ " tt.thesis_id,tt.title_,tt.year_,tt.authors,tt.periodical,tt.periodical_type,tt.njqy order by year_ desc";
		
		return baseDao.queryForList(sql);
	}
	
	@Override
	public int getLeaderProject(String tea_id) {
		String sql = "select * from  t_res_project_auth pa"
				+ " left join t_res_project t on t.id = pa.pro_id where pa.people_id = '"+ tea_id +"' and pa.order_ = 1";
		return baseDao.queryForInt(sql);
	}

	@Override
	public int getNationalProject(String tea_id) {
		String sql = "select * from t_res_project_auth t left join t_res_project pro on t.pro_id = pro.id where t.people_id = '"+ tea_id +"' and pro.level_code = '03'";
		return baseDao.queryForInt(sql);
	}

	@Override
	public int getInquestsProject(String tea_id) {
		String sql = "select * from t_res_project_auth t left join t_res_project pro on t.pro_id = pro.id where t.people_id = '"+ tea_id +"' and pro.state_code = '01'";
		return baseDao.queryForInt(sql);
	}

	@Override
	public List<Map<String, Object>> getProjects(String tea_id,String flag) {
		String sql2 = "";
		if("national".equals(flag)){
			sql2 = " and pro.level_code = '03'";
		}else if("leader".equals(flag)){
			sql2 = " and pa.order_ = 1";
		}else if("inquest".equals(flag)){
			sql2  = " and pro.state_code = '01'";
		}
		String sql = "select pro.name_ name_,pro.compere,cd.name_ dept_name,pro.start_time,pro.end_time,cod.name_ issued_dept,pro.category,co.name_ level_name,"
				+ " pro.fund,pro.setup_year from t_res_project_auth pa"
				+ " left join t_res_project pro on pro.id = pa.pro_id"
				+ " left join t_code cod on cod.code_type = 'T_ZXBZ_XMLY' and cod.code_ = pro.issued_dept "
				+ " left join t_code_dept cd on cd.code_ = pro.dept_id"
				+ " left join t_code co on co.code_type = 'RES_PROJECT_LEVEL_CODE' and co.code_ = pro.level_code"
				+ " where pa.people_id = '"+tea_id+"' "+sql2+" order by pro.start_time desc";
		
		return baseDao.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getWorks(String tea_id,String flag) {
		String sql2 = "";
		if("chief".equals(flag)){
			sql2 = " and wa.order_ = 1";
		}else if("partake".equals(flag)){
			sql2 = " and wa.order_ > 1";
		}
		String sql = "select tt.title_ title_,wmsys.wm_concat(tea.name_) authors ,tt.dept_name,tt.press_name,tt.press_time,tt.number_,tt.work_no from("
				+ " select t.id work_id, t.title_,cd.name_ dept_name,t.press_name,t.press_time,t.number_,t.work_no from t_res_work_author wa "
				+ " left join t_res_work t on wa.work_id = t.id"
				+ " left join t_code_dept cd on cd.code_ = t.dept_id "
				+ " where wa.people_id = '"+tea_id+"' "+ sql2 +" order by t.press_time desc) tt left join t_res_work_author rwa on rwa.work_id = tt.work_id"
				+ " left join t_tea tea on tea.tea_no = rwa.people_id  "
				+ " group by  tt.title_,tt.dept_name,tt.press_name,tt.press_time,tt.number_,tt.work_no";
		return baseDao.queryForList(sql);
	}

	@Override
	public int getAcceptPatent(String tea_id) {
		String sql ="select * from t_res_patent_auth t left join t_res_patent pat on t.patent_id = pat.id  where t.people_id = '"+ tea_id +"' and pat.patent_state_code = '01'";
		return baseDao.queryForInt(sql);
	}

	@Override
	public int getAccreditPatent(String tea_id) {
		String sql = "select * from t_res_patent_auth t left join t_res_patent pat on t.patent_id = pat.id where t.people_id = '"+ tea_id +"' and pat.patent_state_code = '02'";
		return baseDao.queryForInt(sql);
	}

	@Override
	public List<Map<String, Object>> getPatents(String tea_id,String flag) {
		String sql2 = "";
		if("accept".equals(flag)){
			sql2 = " and pat.patent_state_code = '01'";
		}else if("accredit".equals(flag)){
			sql2 = " and pat.patent_state_code = '02'";
		}
		String sql = "select tt.name_ name_,wmsys.wm_concat(tea.name_) inventors ,tt.patent_dept,tt.patent_type_name,tt.patent_state_name,tt.accept_time,tt.accredit_time,tt.patent_no from ("
				+ " select pat.name_,pat.patent_dept,co.name_ patent_type_name,cd.name_ patent_state_name,pat.accept_time,pat.accredit_time,pat.patent_no from t_res_patent_auth t"
				+ " left join t_res_patent pat on t.patent_id = pat.id "
				+ " left join t_code co on co.code_type = 'RES_PATENT_TYPE_CODE' and co.code_ = pat.patent_type_code"
				+ " left join t_code cd on cd.code_type = 'RES_PATENT_STATE_CODE' and cd.code_ = pat.patent_state_code"
				+ " where t.people_id = '"+tea_id+"' "+ sql2 +" order by pat.accept_time desc ) tt left join t_res_patent_auth rpa on rpa.patent_id = tt.patent_no"
				+ " left join t_tea tea on tea.tea_no = rpa.people_id group by tt.name_,tt.patent_dept,tt.patent_type_name,"
				+ " tt.patent_state_name,tt.accept_time,tt.accredit_time,tt.patent_no";
		return baseDao.queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getAwardOutcomes(String tea_id) {
		
		String sql = "select tt.oa_id,tt.name_ name_,wmsys.wm_concat(tea.name_) prizewinner,tt.award_name,tt.level_name,tt.category_name,tt.rank_name,tt.award_dept,tt.award_time from("
				+ " select oa.id oa_id,oa.name_,oa.award_name,co.name_ level_name,cd.name_ category_name,ce.name_ rank_name,"
				+ " coded.name_ award_dept,oa.award_time from t_res_hjcg_auth t left join t_res_hjcg oa on oa.id = t.outcome_award_id"
				+ " left join t_code_dept coded on coded.code_ = oa.dept_id"
				+ " left join t_code co on co.code_type ='RES_AWARD_LEVEL_CODE' and co.code_ = oa.level_code"
				+ " left join t_code cd on cd.code_type = 'RES_AWARD_CATEGORY_CODE' and cd.code_ = oa.category_code"
				+ " left join t_code ce on ce.code_type = 'RES_AWARD_RANK_CODE' and ce.code_ = oa.rank_code "
				+ " where t.people_id = '"+tea_id+"' order by oa.award_time desc) tt left join t_res_hjcg_auth rha on rha.outcome_award_id = tt.oa_id"
				+ " left join t_tea tea on tea.tea_no = rha.people_id "
				+ " group by tt.oa_id,tt.name_,tt.award_name,tt.level_name,tt.category_name,tt.rank_name,tt.award_dept,tt.award_time ";
		return baseDao.queryForList(sql);
	}


	@Override
	public List<Map<String, Object>> getAppraisalOutcomes(String tea_id) {
		String sql = "select tt.jd_id,tt.name_ name_,wmsys.wm_concat(tea.name_) authors ,tt.appraisal_dept,tt.identifymode_name,tt.identifylevel_name,tt.time_,tt.identify_regist_no from("
				+ " select t.id jd_id, t.name_,t.appraisal_dept,co.name_ identifymode_name, cd.name_ identifylevel_name,t.time_,t.identify_regist_no from  t_res_jdcg_auth oaa "
				+ " left join  t_res_jdcg t on t.id = oaa.appraisal_id "
				+ " left join t_code co on co.code_type = 'RES_IDENTIFYMODE_CODE' and co.code_ = t.identifymode_code"
				+ " left join t_code cd on cd.code_type = 'RES_IDENTIFYLEVEL_CODE' and cd.code_ = t.identifylevel_code"
				+ " where oaa.people_id = '"+ tea_id +"' order by t.time_ desc) tt left join t_res_jdcg_auth rja on rja.appraisal_id = tt.jd_id"
				+ " left join t_tea tea on tea.tea_no = rja.people_id"
				+ " group by tt.jd_id,tt.name_,tt.appraisal_dept,tt.identifymode_name,tt.identifylevel_name,tt.time_,tt.identify_regist_no";
		return baseDao.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getAwardThesis(String tea_id) {
		String sql = "select tt.thesis_id,title_,wmsys.wm_concat(rta.people_name) authors,award_name,award_rank,award_dept,award_time,certificate_no from ("
				+ " select t.thesis_id,thesis.title_,thesis.authors,t.award_name,co.name_ award_rank,t.award_dept,t.award_time,t.certificate_no"
				+ " from t_res_thesis_award t"
				+ " left join t_res_thesis_author ta on ta.thesis_id = t.thesis_id "
				+ " left join t_res_thesis thesis on t.thesis_id = thesis.id"
				+ " left join t_code co on co.code_type = 'RES_THESIS_RANK_CODE' and co.code_ = t.award_rank_code where ta.people_id = '"+tea_id+"') tt "
				+ " left join t_res_thesis_author rta on rta.thesis_id = tt.thesis_id group by tt.thesis_id,tt.title_,award_name,award_rank,award_dept,award_time,certificate_no";
		return baseDao.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getInThesis(String tea_id) {
		String sql = "select tt.thesis_id,title_,wmsys.wm_concat(rta.people_name) authors,periodical,year_,njqy,issn,"
				+ " impact_factor,sci_zone,periodical_type from ("
				+ " select t.thesis_id,th.title_,th.authors,t.periodical,t.year_,t.njqy,t.issn,t.impact_factor,"
				+ " cd.name_ sci_zone,cod.name_  periodical_type from t_res_thesis_in t "
				+ " left join t_res_thesis th on t.thesis_id = th.id"
				+ " left join t_code cod on cod.code_type = 'PERIODICAL_TYPE_CODE' and cod.code_ = t.periodical_type_code"
				+ " left join t_code cd on cd.code_type = 'THESIS_IN_SCI_CODE' and cd.code_ = t.sci_zone"
				+ " left join t_res_thesis_author ta on ta.thesis_id = t.thesis_id where ta.people_id = '"+tea_id+"') tt left join t_res_thesis_author rta on rta.thesis_id = tt.thesis_id"
				+ " group by  tt.thesis_id,title_,periodical,year_,njqy,issn,impact_factor,sci_zone,periodical_type";
		return baseDao.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getMeetingThesis(String tea_id) {
		String sql = "select  tt.thesis_id,tt.title_,wmsys.wm_concat(rta.people_name) authors, tt.comefrom,tt.year_,tt.conference,tt.conference_time,tt.accession_number,tt.meeting_type from("
				+ " select t.thesis_id,th.title_,th.authors, t.comefrom,t.year_,t.conference,t.conference_time,t.accession_number,co.name_ meeting_type from t_res_thesis_meeting t"
				+ " left join t_res_thesis th on t.thesis_id = th.id"
				+ " left join t_code co on co.code_type = 'RES_MEETING_TYPE_CODE' and co.code_ = t.meeting_type_code"
				+ " left join t_res_thesis_author ta on ta.thesis_id = t.thesis_id where ta.people_id = '"+tea_id+"') tt left join t_res_thesis_author rta on rta.thesis_id = tt.thesis_id"
				+ " group by  tt.thesis_id,tt.title_, tt.comefrom,tt.year_,tt.conference,tt.conference_time,tt.accession_number,tt.meeting_type ";
		return baseDao.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getReshipThesis(String tea_id) {
		String sql = "select tt.thesis_id,tt.title_,wmsys.wm_concat(rta.people_name) authors,tt.periodical,tt.njqy,tt.year_,tt.theisi_reship from ("
				+ " select t.thesis_id,th.title_,th.authors,t.periodical,t.njqy,t.year_,co.name_ theisi_reship from t_res_thesis_reship t "
				+ " left join t_res_thesis th on t.thesis_id = th.id"
				+ " left join t_code co on co.code_type = 'RES_PERIODICAL_TYPE_CODE' and co.code_ = t.thesis_reship_code"
				+ " left join t_res_thesis_author ta on ta.thesis_id = t.thesis_id where ta.people_id = '"+tea_id+"') tt left join t_res_thesis_author rta on rta.thesis_id = tt.thesis_id "
				+ " group by tt.thesis_id,tt.title_,tt.periodical,tt.njqy,tt.year_,tt.theisi_reship";
		return baseDao.queryForList(sql);
	}

	@Override
	public int getChiefEditor(String tea_id) {
		String sql = "select * from t_res_work_author t where t.people_id = '"+ tea_id +"' and t.order_ = 1";
		return baseDao.queryForInt(sql);
	}
	
	
	@Override
	public int getPartake(String tea_id) {
		String sql = "select * from t_res_work_author t where t.people_id = '"+ tea_id +"' and t.order_ > 1";
		return baseDao.queryForInt(sql);
	}

	@Override
	public int getSoftCounts(String tea_id) {
		String sql = "select * from t_res_soft_auth t where t.people_id = '"+ tea_id +"' ";
		return baseDao.queryForInt(sql);
	}

	@Override
	public List<Map<String, Object>> getSofts(String tea_id) {
		String sql = "select t.soft_id,sc.soft_name soft_name,sc.complete_man,sub.name_ subject,co.name_ copyright,cd.name_ get,"
				+ " sc.regist_no,sc.certificate_no,sc.complete_time,sc.dispatch_time,sc.regist_time from t_res_soft_auth t "
				+ " left join t_res_soft sc on sc.id = t.soft_id"
				+ " left join t_code_subject sub on sub.code_ = sc.project_id"
				+ " LEFT JOIN T_CODE co on co.code_type = 'RES_SOFTWARE_COPYRIGHT_CODE' and co.code_ = sc.copyright_code"
				+ " left join t_code cd on cd.code_type = 'RES_SOFTWARE_GET_CODE' and cd.code_ = sc.get_code"
				+ " where t.people_id = '"+ tea_id +"'";
		return baseDao.queryForList(sql);
	}

	
	
	
	

}
