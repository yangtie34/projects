package com.jhnu.product.manager.student.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.manager.student.dao.StuTop5Dao;
import com.jhnu.util.common.StringUtils;
@Repository("stuTop5Dao")
public class StuTop5DaoImpl implements StuTop5Dao{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Map<String, Object>> getStuTop5ByAvg(boolean isLeaf,String year,String dept_id) {
		String sql2 = "";
		if(!StringUtils.hasText(dept_id)){
			return null;
		}else if("0".equals(dept_id)){
			sql2 = "";//显示全校数据
		}else if(!dept_id.contains(",")){
			sql2="and (stu.dept_id = '"+ dept_id +"' or stu.major_id = '"+ dept_id +"' )";//显示某个院系或某个专业数据
		}else{
			if(isLeaf){
				sql2 = "and stu.major_id in ("+ dept_id +") "; //显示某些专业数据
			}else{
				sql2 = "and stu.dept_id in ("+ dept_id +") "; //显示某些学院数据
			}
		}
		
		// TODO 学霸计算规则：只算了大三的学生和总成绩大于3000的
		String sql = "select * from ( select s.*,rownum rm from ("
				+ " select d.stu_id,stu.name_ stu_name,xb.mc stu_sex,th.name_ major_name,stu.enroll_grade,d.zcj,d.pjcj from ("
				+ " select stu_id,sum(score) as zcj,round(avg(score),2) pjcj from ( "
				+ " select t.*,case when t.hierarchical_score_code='01'then 95  "
				+ " when t.hierarchical_score_code is null then t.centesimal_score   "
				+ " when t.hierarchical_score_code='04'then 60  "
				+ " when t.hierarchical_score_code='02'then 85  "
				+ " when t.hierarchical_score_code='05'then 50   "
				+ " when t.hierarchical_score_code='03'then 70     "
				+ "	end as score from t_stu_score t ) group by stu_id order by stu_id) d "
				+ " left join t_stu stu on stu.no_ = d.stu_id"
				+ " left join dm_zxbz.t_zxbz_xb xb on xb.dm = stu.sex_code"
				+ " left join t_code_dept_teach th on th.id = stu.major_id "
				+ "  left join t_code_dept_teach te on te.id = stu.dept_id "
				+ "  where to_number(stu.enroll_grade+stu.length_schooling) - to_number(to_char(sysdate, 'yyyy')) = 1"
				+ " and stu.no_ like '"+ year +"%' and zcj > 3000" 
				+ sql2 + "  order by pjcj desc, zcj desc ) s )where rm <=5";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	
	@Override
	public List<Map<String,Object>> getStuTop5ByGpa(boolean isLeaf,String year,String dept_id){
		String sql2 = "";
		if(!StringUtils.hasText(dept_id)){
			return null;
		}else if("0".equals(dept_id)){
			sql2 = "";//显示全校数据
		}else if(!dept_id.contains(",")){
			sql2="and (stu.dept_id = '"+ dept_id +"' or stu.major_id = '"+ dept_id +"' )";//显示某个院系或某个专业数据
		}else{
			if(isLeaf){
				sql2 = "and stu.major_id in ("+ dept_id +") "; //显示某些专业数据
			}else{
				sql2 = "and stu.dept_id in ("+ dept_id +") "; //显示某些学院数据
			}
		}
		
		String sql = "select * from ("
				+ " select d.*,rownum rm from("
				+ " select t.stu_id,stu.name_ stu_name,xb.mc stu_sex,th.name_ major_name,stu.enroll_grade,t.zcj,t.pjcj,t.gpa from ("
				+ " select stu_id, nvl(sum(centesimal_score),0) as zcj, nvl(round(avg(centesimal_score), 2),0) pjcj,nvl(round(sum(hdxf*theory_credit)/sum(theory_credit),2),0) gpa"
				+ " from (select s.* ,case"
				+ " when s.centesimal_score between 90 and 100 then 4"
				+ " when s.centesimal_score between 85 and 89 then 3.7"
				+ "  when s.centesimal_score between 82 and 84 then 3.3"
				+ " when s.centesimal_score between 78 and 81 then 3.0"
				+ "  when s.centesimal_score between 75 and 77 then 2.7"
				+ "  when s.centesimal_score between 71 and 74 then 2.3"
				+ "  when s.centesimal_score between 66 and 70 then 2.0"
				+ "  when s.centesimal_score between 62 and 65 then 1.7"
				+ " when s.centesimal_score between 60 and 61 then 1.3"
				+ " when s.centesimal_score between 0 and 59 then 0"
				+ "  end as hdxf,cou.theory_credit"
				+ " from t_stu_score s left join t_course cou on cou.code_ = s.coure_code where s.hierarchical_score_code is null)"
				+ "  group by stu_id ) t "
				+ " left join t_stu stu on stu.no_ = t.stu_id"
				+ " left join dm_zxbz.t_zxbz_xb xb on xb.dm = stu.sex_code"
				+ " left join t_code_dept_teach th on th.id = stu.major_id "
				+ "  left join t_code_dept_teach te on te.id = stu.dept_id "
				+ "  where to_number(stu.enroll_grade+stu.length_schooling) - to_number(to_char(sysdate, 'yyyy')) = 1"
				+ " and stu.no_ like '"+ year +"%' and zcj > 3000"
				+ sql2 + "  order by gpa desc,pjcj desc, zcj desc"
				+ " )d ) where rm<=5";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	
}
