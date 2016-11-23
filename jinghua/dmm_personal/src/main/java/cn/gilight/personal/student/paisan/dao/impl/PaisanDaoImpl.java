package cn.gilight.personal.student.paisan.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.personal.student.paisan.dao.PaisanDao;

@Repository("paisanDao")
public class PaisanDaoImpl implements PaisanDao {
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public Page getPaisan(String stu_id, String stu_name, String flag, Page page) {
		String sql1 = "";
		if(StringUtils.hasText(stu_name)){
			sql1 = " and t.name_ like '%"+stu_name+"%' ";
		}
		String sql2 = "";
		if(StringUtils.hasText(flag)){
			if("tx".equals(flag)){
				sql2 = "";
			}
			if("tmx".equals(flag)){
				sql2 = " and t.schooltag = stu.schooltag";
			}
			if("tzy".equals(flag)){
				sql2 = " and t.major_id = stu.major_id";
			}
			if("tyx".equals(flag)){
				sql2 = " and t.dept_id = stu.dept_id";
			}
		}
		String sql = "select t.no_ stu_id,t.name_ stu_name,co.name_ sex,t.enroll_grade,comm.tel,bind.wechat_head_img from t_stu t "
				+ " left join t_stu stu on stu.stu_origin_id = t.stu_origin_id"
				+ " left join t_code co on co.code_type = 'SEX_CODE' and co.code_ = t.sex_code"
				+ " left join t_stu_comm comm on comm.stu_id = t.no_"
				+ " left join t_wechat_bind bind on bind.username = t.no_"
				+ " where t.stu_state_code = 1 and stu.no_ = '"+ stu_id +"' and t.no_ != '"+stu_id+"'"+ sql2 + sql1;
		return baseDao.queryWithPageInLowerKey(sql, page);
	}

	@Override
	public Map<String, Object> getPaisanStu(String stu_id) {
		String sql = "select t.no_,t.name_ stu_name,co.name_ sex,cdt.name_ dept_name,dt.name_||cl.name_ major,t.enroll_grade,cad.name_ place_domicile,co.tel tel,t.schooltag,bind.wechat_head_img from t_stu t "
				+ " left join t_code_admini_div cad on cad.code_ = t.stu_origin_id"
				+ " left join t_code_dept_teach cdt on cdt.id = t.dept_id"
				+ " left join t_code_dept_teach dt on dt.id = t.major_id"
				+ " left join t_classes cl on cl.no_ = t.class_id "
				+ " left join t_code co on co.code_type = 'SEX_CODE' and co.code_ = t.sex_code "
				+ " left join t_wechat_bind bind on bind.username = t.no_"
				+ " left join t_stu_comm co on co.stu_id = t.no_ where t.no_ = '"+ stu_id +"'";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		Map<String,Object> map = null;
		if(list != null && list.size()>0){
			map = list.get(0);
		}
		return map;
	}

	
}
