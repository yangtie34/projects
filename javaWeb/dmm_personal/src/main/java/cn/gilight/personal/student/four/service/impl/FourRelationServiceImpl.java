package cn.gilight.personal.student.four.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.personal.student.four.service.FourRelationService;

@Service("fourRelationService")
public class FourRelationServiceImpl implements FourRelationService{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Map<String, Object>> getFdys(String username) {
		String sql  = "select t.school_year,tea.tea_no,tea.name_,cd.name_ degree_,wb.wechat_head_img from t_classes_instructor t "
				+ " left join t_stu stu on stu.class_id = t.class_id"
				+ " left join t_tea tea on tea.tea_no = t.tea_id"
				+ " left join t_code_zyjszw cd on cd.code_ = tea.zyjszw_id"
				+ " left join t_wechat_bind wb on wb.username = tea.tea_no where stu.no_ = '"+username+"'"
				+ " group by t.school_year,t.tea_id,tea.tea_no,tea.name_,cd.name_,wb.wechat_head_img";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public List<Map<String, Object>> queryTea(String xn, String xq,
			String username) {
		String sql  = "select distinct tea.tea_no no_,tea.name_,co.name_ sex,cz.name_ zyjszw,wb.wechat_head_img,cou.name_ course_name from t_class_teaching_stu cts"
				+ " left join t_course_arrangement ca on ca.teachingclass_id = cts.teach_class_id inner join t_course cou on cou.code_ = ca.course_id"
				+ " inner join t_tea tea on tea.tea_no = ca.tea_id left join t_code co on co.code_type='SEX_CODE' and co.code_ = tea.sex_code"
				+ " left join t_code_zyjszw cz on cz.code_ = tea.zyjszw_id"
				+ " left join t_wechat_bind wb on wb.username = tea.tea_no where cts.stu_id = '"+username+"' and "
				+ " ca.school_year = '"+xn+"' and ca.term_code = '"+xq+"'";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Map<String, Object> mySchool(String username) {
		String sql = "select tt.enroll_date ,ts.school_name from ("
				+ " select t.enroll_date from t_stu t where t.no_ = '"+username+"') tt left join ("
				+ " select cdt.name_ school_name from t_code_dept_teach cdt where cdt.level_ = 0) ts on 1=1";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	

}


