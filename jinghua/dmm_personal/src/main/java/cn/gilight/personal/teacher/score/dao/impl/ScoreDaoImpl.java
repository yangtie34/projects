package cn.gilight.personal.teacher.score.dao.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.personal.teacher.score.dao.ScoreDao;

@Repository("scoreDao")
public class ScoreDaoImpl implements ScoreDao {
	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Map<String, Object>> getScoreClasses(String school_year,String term_code,String tea_id) {
		String mysql = "select t.class_id,cl.name_ classname,stu.no_ stu_id,stu.name_ stu_name,score.coure_code course_id,cou.name_ course_name,"
				+ " case when score.hierarchical_score_code is null then score.centesimal_score else csh.centesimal_score end course_score from t_classes_instructor t"
				+ " left join t_stu stu on stu.class_id = t.class_id"
				+ " inner join t_classes cl on cl.no_ = t.class_id"
				+ " left join t_stu_score score on score.stu_id = stu.no_ and score.school_year = t.school_year and score.term_code = t.term_code"
				+ " inner join t_course cou on cou.code_ = score.coure_code"
				+ " left join t_code_score_hierarchy csh on csh.code_ = score.hierarchical_score_code"
				+ " where  t.school_year='"+school_year+"' and t.term_code = '"+term_code+"' "
				+ " and t.tea_id='"+tea_id+"' order by class_id,stu_id";
		
		String sql = "select aa.class_id,aa.classname,aa.stunums,bb.stu_id maxstu,bb.stu_name maxscorestu,bb.sums maxscore,cc.stu_id minstu,cc.stu_name minscorestu,"
				+ " cc.sums minscore,nvl(dd.flunknums,0) flunknums"
				+ " ,round(nvl(dd.flunknums,0)/aa.stunums,3)*100||'%' flunk_of_stunums ,ee.course_id,ee.flunk_course from ("
				+ " select class_id,classname,count(distinct stu_id) stunums from (" 
				+ mysql + " ) group by class_id,classname) aa "
				+ " left join (select class_id,classname,stu_id,stu_name,sums from ("
				+ " select s.class_id,s.classname,s.stu_id ,s.stu_name,sum(course_score) sums,row_number() "
				+ " over(partition by class_id order by sum(course_score) desc,stu_name asc ) rn  from ("
				+ mysql + " )s group by s.class_id,s.classname,s.stu_id,s.stu_name order by s.class_id,s.classname,rn )"
				+ " m where rn=1 ) bb on aa.class_id= bb.class_id"
				+ " left join (select class_id,classname,stu_id,stu_name,sums from ("
				+ " select s.class_id,s.classname,s.stu_id ,s.stu_name,sum(course_score) sums,row_number() "
				+ " over(partition by class_id order by sum(course_score) asc,stu_name asc ) rn  from ("
				+ mysql + " )s group by s.class_id,s.classname,s.stu_id,s.stu_name order by s.class_id,s.classname,rn ) m where rn=1 ) "
				+ " cc on cc.class_id = aa.class_id"
				+ " left join (select class_id,classname,count(distinct stu_id) flunknums from ("
				+ " select s.class_id,s.classname,s.stu_id ,s.stu_name,s.course_score,"
				+ " case when s.course_score<60 then s.course_name end as flunk_course from ("
				+ mysql + " )s )d  where d.flunk_course is not null group by class_id,classname ) dd on dd.class_id = aa.class_id left join ("
				+ " select class_id,classname,course_id,flunk_course from ("
				+ " select class_id,classname,course_id,flunk_course,nums,row_number() over(partition by class_id order by nums desc,flunk_course asc) rn from ("
				+ " select class_id,classname,course_id,flunk_course,count(*) nums from ("
				+ " select s.class_id,s.classname,s.stu_id ,s.stu_name,s.course_score,s.course_id,"
				+ " case when s.course_score<60 then s.course_name end as flunk_course from ("
				+ mysql + "  )s )d where flunk_course is not null"
				+ " group by class_id,classname,course_id,flunk_course order by class_id,nums desc) m ) n where rn=1) ee on ee.class_id = aa.class_id";
		return baseDao.queryListInLowerKey(sql);
	}
	
	

	@Override
	public List<Map<String, Object>> getStuScore(String school_year,
			String term_code, String class_id, String param,String paramFlag) {
		String sql = "";
		String sql2 = "";
		if(StringUtils.hasText(param)){
			sql2 = " and tt.stu_id like '%"+ param +"%' or tt.stu_name like '%"+ param +"%'";
		}
		
		if("xzb".equals(paramFlag)){
			sql = "select class_id,class_name,stu_id,stu_name, sex,tel,wechat_head_img,sum(course_score) total_score from ("
					+ " select cl.no_ class_id,cl.name_ class_name,stu.no_ stu_id,stu.name_ stu_name,co.name_ sex,comm.tel,bind.wechat_head_img,"
					+ " case when t.hierarchical_score_code is null then t.centesimal_score else csh.centesimal_score end course_score from t_stu_score t "
					+ " left join t_code_score_hierarchy csh on csh.code_ = t.hierarchical_score_code"
					+ " left join t_stu stu on t.stu_id = stu.no_"
					+ " left join t_code co on co.code_type = 'SEX_CODE' and co.code_ = stu.sex_code"
					+ " left join t_classes cl on cl.no_ = stu.class_id"
					+ " left join t_stu_comm comm on comm.stu_id = t.stu_id "
					+ " left join t_wechat_bind bind on bind.username = t.stu_id"
					+ " where t.school_year = '"+school_year+"' and t.term_code = '"+term_code+"' and stu.class_id = '"+class_id+"') tt where 1=1 " + sql2 
					+ " group by class_id,class_name,stu_id,stu_name,sex,tel,wechat_head_img order by total_score desc,stu_name asc";
		}else if("xzbgk".equals(paramFlag)){
			sql = "select tt.class_id,tt.class_name,tt.stu_id,tt.stu_name,tt.sex,tt.course_name,tt.tel,tt.wechat_head_img,tt.course_score from ("
					+ " select cl.no_ class_id,cl.name_ class_name,stu.no_ stu_id,stu.name_ stu_name,co.name_ sex,cou.name_ course_name,comm.tel,bind.wechat_head_img,"
					+ " case when t.hierarchical_score_code is null then t.centesimal_score else csh.centesimal_score end course_score from t_stu_score t "
					+ " left join t_code_score_hierarchy csh on csh.code_ = t.hierarchical_score_code"
					+ " left join t_stu stu on t.stu_id = stu.no_"
					+ " left join t_code co on co.code_type = 'SEX_CODE' and co.code_ = stu.sex_code"
					+ " left join t_classes cl on cl.no_ = stu.class_id left join t_course cou on cou.code_ = t.coure_code"
					+ " left join t_stu_comm comm on comm.stu_id = t.stu_id "
					+ " left join t_wechat_bind bind on bind.username = t.stu_id"
					+ " where t.school_year = '"+school_year+"' and t.term_code = '"+term_code+"' and stu.class_id = '"+class_id+"')tt where tt.course_score < 60 "+sql2;
		}else if(StringUtils.hasText(paramFlag) && paramFlag.contains("xzb_")){
			String course_id = paramFlag.substring(4);
			sql = "select tt.class_id,tt.class_name,tt.stu_id,tt.stu_name,tt.course_name,tt.sex,tt.tel,tt.wechat_head_img,tt.course_score from ("
					+ " select stu.class_id,cl.name_ class_name,t.stu_id,stu.name_ stu_name,co.name_ sex,cou.name_ course_name,sc.tel,wb.wechat_head_img,"
					+ " case when t.hierarchical_score_code is null then t.centesimal_score else csh.centesimal_score end course_score "
					+ " from t_stu_score t left join t_stu stu on stu.no_ = t.stu_id left join t_classes cl on cl.no_ = stu.class_id"
					+ " left join t_code_score_hierarchy csh on csh.code_ = t.hierarchical_score_code "
					+ " left join t_code co on co.code_type = 'SEX_CODE' and co.code_ = stu.sex_code"
					+ " left join t_stu_comm sc on sc.stu_id = t.stu_id left join t_wechat_bind wb on wb.username = t.stu_id"
					+ " left join t_course cou on cou.code_ = t.coure_code"
					+ " where t.school_year = '"+school_year+"' and t.term_code = '"+term_code+"' and stu.class_id = '"+class_id+"' and t.coure_code = '"+course_id+"') tt where tt.course_score < 60 "+sql2;
		}
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public List<Map<String, Object>> getStuTotalScore(String school_year,
			String term_code, String stu_id) {
		String sql = "select tt.* from (select mm.stu_id,mm.stu_name,mm.sex,mm.wechat_head_img,mm.total_score,mm.course_nums,rownum rank_ from ("
				+ " select cl.no_ class_id,cl.name_ class_name, stu.no_ stu_id,stu.name_ stu_name,co.name_ sex,bind.wechat_head_img,"
				+ " sum(case when t.hierarchical_score_code is null then t.centesimal_score else csh.centesimal_score end) total_score,count(*) course_nums "
				+ " from t_stu_score t "
				+ " left join t_code_score_hierarchy csh on csh.code_ = t.hierarchical_score_code"
				+ " left join t_stu stu on t.stu_id = stu.no_"
				+ " left join t_code co on co.code_type = 'SEX_CODE' and co.code_ = stu.sex_code"
				+ " left join t_wechat_bind bind on bind.username = stu.no_"
				+ " left join t_classes cl on cl.no_ = stu.class_id"
				+ " where  t.school_year = '"+school_year+"' and t.term_code = '"+term_code+"' and cl.no_ = "
				+ " (select s.class_id from t_stu s where s.no_ = '"+stu_id+"')"
				+ " group by  cl.no_ ,cl.name_ , stu.no_ ,stu.name_,co.name_,bind.wechat_head_img order by total_score desc)  mm ) "
				+ " tt where tt.stu_id = '"+ stu_id +"'";
		return baseDao.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getStuScoreDetail(String school_year,
			String term_code, String stu_id) {
		String sql = "select t.stu_id stu_id,cou.name_ course_name,"
				+ " case when t.hierarchical_score_code is null then to_char(nvl(t.centesimal_score,0))"
				+ " when t.hierarchical_score_code is not null then co.name_ end as value_ from t_stu_score t "
				+ " left join t_course cou on cou.code_ = t.coure_code"
				+ " left join t_code co on co.code_type = 'HIERARCHICAL_SCORE_CODE' and co.code_ = t.hierarchical_score_code"
				+ " where  t.school_year = '"+ school_year +"' and t.term_code = '"+ term_code +"' and t.stu_id = '"+ stu_id +"'";
		return baseDao.queryForList(sql);
	}



	@Override
	public List<Map<String, Object>> getCourseScore(String school_year,
			String term_code, String tea_id) {
		String sql1 = "select distinct(t.teachingclass_id) class_id,t.course_id,ct.name_ classname,stu.no_ stu_id,stu.name_ stu_name,cou.name_ course_name,"
				+ " case when sc.hierarchical_score_code is null then sc.centesimal_score else csh.centesimal_score end course_score from t_course_arrangement t"
				+ " left join t_class_teaching ct on ct.code_ = t.teachingclass_id and ct.course_id = t.course_id"
				+ " inner join t_class_teaching_stu cts on cts.teach_class_id = ct.code_"
				+ " inner join t_stu_score sc on sc.stu_id = cts.stu_id and sc.coure_code = t.course_id and sc.school_year = t.school_year"
				+ " left join t_code_score_hierarchy csh on csh.code_ = sc.hierarchical_score_code"
				+ " left join t_stu stu on stu.no_ = sc.stu_id"
				+ " left join t_course cou on cou.code_ = t.course_id and cou.code_ = sc.coure_code"
				+ " and sc.term_code = t.term_code"
				+ " where t.tea_id = '"+ tea_id +"' and t.school_year = '"+ school_year +"' and t.term_code = '"+ term_code+ "'";
		String sql  ="select aa.class_id,aa.course_id,aa.classname,aa.course_name,aa.nums stunums,bb.stu_id maxstu,bb.stu_name maxscorestu,bb.course_score maxscore,cc.stu_id minstu,cc.stu_name minscorestu ,cc.course_score minscore"
				+ " ,nvl(dd.flunknums,0) flunknums,round(nvl(dd.flunknums,0)/aa.nums,3)*100||'%' flunk_of_stunums  from ("
				+ " select tt.class_id,tt.course_id,tt.classname,tt.course_name,count(*) nums from ("
				+ sql1 +") tt group by class_id,course_id,classname,course_name ) aa left join ("
				+ " select dd.class_id,dd.classname,dd.stu_id,dd.stu_name,course_name,course_score,rn from ("
				+ " select tt.class_id,tt.classname,tt.stu_id,tt.stu_name,course_name,course_score,row_number() over(partition by class_id order by course_score desc,stu_name asc ) rn from ("
				+ sql1 + ") tt) dd where dd.rn = 1 ) bb on  aa.class_id = bb.class_id left join ("
				+ " select dd.class_id,dd.classname,dd.stu_id,dd.stu_name,course_name,course_score,rn from ("
				+ " select tt.class_id,tt.classname,tt.stu_id,tt.stu_name,course_name,course_score,row_number() over(partition by class_id order by course_score asc,stu_name asc ) rn from ("
				+ sql1 +") tt) dd where dd.rn = 1 ) cc on  aa.class_id = cc.class_id left join ("
				+ " select tt.class_id,tt.classname,count(case when tt.course_score < 60 then 1 end) flunknums from ("
				+ sql1 + ")tt group by tt.class_id,classname) dd on dd.class_id = aa.class_id";
		return baseDao.queryListInLowerKey(sql);
	}



	@Override
	public List<Map<String, Object>> getStuScoreJxb(String school_year,
			String term_code, String class_id,String course_id , String param,String paramFalg) {
		String sql1 = "";
		String sql2 = "";
		if(StringUtils.hasText(param)){
			sql2 = " and (stu.no_ like '%"+param+"%' or stu.name_ like '%"+param+"%')";
		}
		if(StringUtils.hasText(paramFalg) && paramFalg.contains("jxb_")){
			String stu_id = paramFalg.substring(4);
			sql1 = " where tt.stu_id = '"+stu_id+"'";
		}else if("jxbgk".equals(paramFalg)){
			sql1 = " where tt.course_score < 60";
		}
		
		String sql  ="select tt.stu_id,tt.stu_name,tt.course_name,tt.tel,tt.sex,tt.wechat_head_img,tt.course_score from ("
				+ " select cts.stu_id,stu.name_ stu_name,cou.name_ course_name,cmmm.tel,co.name_ sex,bind.wechat_head_img,"
				+ " case when sc.hierarchical_score_code is null then sc.centesimal_score else csh.centesimal_score end course_score from t_class_teaching ct"
				+ " left join t_class_teaching_stu cts on cts.teach_class_id = ct.code_"
				+ " left join t_stu_score sc on sc.stu_id = cts.stu_id and sc.coure_code = ct.course_id"
				+ " left join t_code_score_hierarchy csh on csh.code_ = sc.hierarchical_score_code"
				+ " left join t_stu stu on stu.no_ = cts.stu_id"
				+ " left join t_course cou on cou.code_ = sc.coure_code"
				+ " left join t_code co on co.code_type = 'SEX_CODE' and co.code_ = stu.sex_code"
				+ " left join t_stu_comm cmmm on cmmm.stu_id = stu.no_"
				+ " left join t_wechat_bind bind on bind.username = cts.stu_id"
				+ " where  ct.code_ = '"+ class_id +"' and ct.course_id = '"+course_id+"' and sc.school_year = '"+ school_year +"' and sc.term_code = '"+ term_code +"'" + sql2 
				+ " order by course_score desc,stu_name asc ) tt "+sql1;
		
		return baseDao.queryListInLowerKey(sql);
	}

}
