package cn.gilight.personal.student.concat.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.personal.student.concat.dao.ConcatDao;
@Repository("concatDao")
public class ConcatDaoImpl implements ConcatDao{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Map<String, Object>> getConcat(String stu_id,String school_year,String term_code,String param) {
		String sql2 = "";
		if(StringUtils.hasText(param)){
			sql2  = " where no_ like '%"+param+"%' or name_ like '%"+param+"%' or course_name like '%"+param+"%' ";
		}
		String sql = "select no_,name_,sex,tel,flag,course_name,wb.wechat_head_img from (select no_,name_,sex,tel,flag,course_name from ("
				+ " select tea.tea_no no_,tea.name_,co.name_ sex,tc.tel,'授课教师' flag ,cou.name_ course_name from t_class_teaching_stu cts"
				+ " left join t_course_arrangement ca on ca.teachingclass_id = cts.teach_class_id inner join t_course cou on cou.code_ = ca.course_id"
				+ " inner join t_tea tea on tea.tea_no = ca.tea_id left join t_code co on co.code_type='SEX_CODE' and co.code_ = tea.sex_code"
				+ " left join t_tea_comm tc on tc.tea_id = tea.tea_no where cts.stu_id = '"+stu_id+"' and ca.school_year = '"+school_year+"' and ca.term_code = '"+term_code+"'"
				+ " group by tea.tea_no,tea.name_,co.name_,tc.tel,cou.name_) "
				+ " union all"
				+ " (select t.tea_no no_,t.name_,cd.name_ sex,co.tel,'辅导员' flag,'' course_name from t_tea t inner join "
				+ " (select tea_id from (select ci.tea_id,rownum rn from t_stu stu left join t_classes_instructor ci on ci.class_id = stu.class_id "
				+ " where stu.no_ = '"+stu_id+"' order by school_year desc, term_code desc) where rn = 1) s on s.tea_id = t.tea_no"
				+ " left join t_code cd on cd.code_type='SEX_CODE' and cd.code_ = t.sex_code left join t_tea_comm co on co.tea_id = t.tea_no)"
				+ " union all ("
				+ " select stu.no_,stu.name_,cd.name_ sex,co.tel,'同学' flag ,'' course_name from t_stu stu"
				+ " left join t_code cd on cd.code_type='SEX_CODE' and cd.code_ = stu.sex_code "
				+ " left join t_stu_comm co on co.stu_id = stu.no_ where stu.class_id = (select class_id from t_stu where no_ = '"+stu_id+"'))) tt"
				+ " left join t_wechat_bind wb on wb.username = tt.no_  "+sql2+" order by flag";
		return baseDao.queryListInLowerKey(sql);
	}

}
