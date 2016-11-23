package cn.gilight.product.bookRke.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.product.bookRke.dao.JobBookRkeDao;

@Repository("jobBookRkeDao")
public class JobBookRkeDaoImpl implements JobBookRkeDao {
	@Autowired
	private BaseDao baseDao;

	@Override
	public Map<String, Integer> updateBookRkeStuMonth(String yearMonth) {
		Map<String, Integer> map = new HashMap<String, Integer>();

		String delSql = "delete tl_book_rke_stu_month where year_month= to_date(?,'yyyy-mm') ";
		int delNum = baseDao.getJdbcTemplate().update(delSql,
				new Object[] { yearMonth });
		map.put("delNum", delNum);

		String sql ="insert into tl_book_rke_stu_month                                     "+
					" select                                                                 "+    
					" to_date(substr(t.time_, 0, 7),'yyyy-mm') year_month,                	 "+//--年月
					" to_char(to_date(substr(t.time_, 0, 10),'YYYY-MM-DD'),'d') week_id,	 "+//--周次id
					" to_char(to_date(substr(t.time_, 0, 10),'YYYY-MM-DD'),'day') week_name, "+//--周次
					" substr(t.time_, 12, 2) hour_,                                     	 "+//--时段
					" s.dept_id,                                                             "+    
					" cd.name_ dept_name,                                              		 "+//--学院
					" s.major_id,                                                            "+    
					" cdt.name_ major_name,                                              	 "+//--专业 
					" s.sex_code,                                                            "+    
					" tc.name_ sex_name,                                                  	 "+//--性别
					" s.edu_id,                                                              "+    
					" ce.name_ edu_name,                                                 	 "+//--学历 
					" tc2.code_ nation_code,                                                 "+    
					" tc2.name_ nation_name,                                             	 "+//--民族 
					" count(t.id) count_                                              		 "+//--出入次数
					" from t_book_rke t                                                      "+    
					" left join t_stu s on t.book_reader_id = s.no_                          "+    
					" left join t_code_dept_teach cd on s.dept_id =cd.id                     "+    
					" left join t_code_dept_teach cdt on s.major_id=cdt.id                   "+    
					" left join t_code_education ce on s.edu_id=ce.id                        "+    
					" left join t_code tc on s.sex_code=tc.code_ and tc.code_type='SEX_CODE' "+ 
					"left join t_code tc2 on tc2.code_ = s.nation_code  and tc2.code_type='NATION_CODE'"+    
					" where substr(t.time_, 0, 7)=?                                  		"+    
					" group by                                                               "+    
					" to_date(substr(t.time_, 0, 7),'yyyy-mm'),                         	 "+//--年月
					" to_char(to_date(substr(t.time_, 0, 10),'YYYY-MM-DD'),'d') ,       	 "+//--周次id
					" to_char(to_date(substr(t.time_, 0, 10),'YYYY-MM-DD'),'day') ,       	 "+//--周次
					" substr(t.time_, 12, 2) ,                                         		 "+//--时段， 
					" s.dept_id,                                                             "+    
					" cd.name_ ,                                                          	 "+//--学院
					" s.major_id,                                                            "+    
					" cdt.name_ ,                                                        	 "+//--专业 
					" s.sex_code,                                                            "+    
					" tc.name_ ,                                                         	 "+//--性别 
					" s.edu_id,                                                              "+         
					" ce.name_,                                                              "+//--学历  
					" tc2.code_,															 "+ 
					" tc2.name_																 ";//--民族
		int addNum = baseDao.getJdbcTemplate().update(sql,
				new Object[] {yearMonth });
		map.put("addNum", addNum);

		return map;
	}

	@Override
	public Map<String, Integer> updateStuRkeBookMonth(String yearMonth) {
		Map<String, Integer> map = new HashMap<String, Integer>();

		String delSql = "delete tl_stu_rke_book_month where year_month= to_date(?,'yyyy-mm') ";
		int delNum = baseDao.getJdbcTemplate().update(delSql,
				new Object[] { yearMonth });
		map.put("delNum", delNum);

		String sql="insert into tl_stu_rke_book_month                                     				"+
				" select                                                                             	"+
				" to_date(?,'yyyy-mm') year_month,                                                      "+//--年月
				" s.no_,                                                                                "+
				" s.name_,                                                                              "+//--学生
				" s.dept_id,                                                                            "+
				" cd.name_ dept_name,                                                                  	"+//--学院， 
				" s.major_id,                                                                           "+
				" cdt.name_ major_name,                                                                 "+//--专业 
				" s.class_id,                                                                           "+
				" cl.name_ class_name,                                                                  "+//--班级  
				" s.sex_code,                                                                           "+
				" tc.name_ sex_name,                                                                    "+//--性别  
				" s.edu_id,                                                                             "+
				" ce.name_ edu_name,                                                                    "+//--学历
				" tc2.code_ nation_code,                                                 				"+    
				" tc2.name_ nation_name,                                             	 				"+//--民族
				" count(t.id) count_                                                                 	"+//--出入次数 
				" from t_stu s                                                                          "+
				" left join t_book_rke t on t.book_reader_id = s.no_ and substr(t.time_, 0, 7)=?		"+
				" left join t_code_dept_teach cd on s.dept_id =cd.id                                    "+
				" left join t_code_dept_teach cdt on s.major_id=cdt.id                                  "+
				" left join t_classes cl on s.class_id=cl.no_                                           "+
				" left join t_code_education ce on s.edu_id=ce.id                                       "+
				" left join t_code tc on s.sex_code=tc.code_ and tc.code_type='SEX_CODE'                "+ 
				" left join t_code tc2 on tc2.code_ = s.nation_code  and tc2.code_type='NATION_CODE'	"+  
				" where ? >= substr(s.enroll_date,0,7)                                          		"+
				" and  ?  <to_char(add_months(to_date(s.enroll_date,'yyyy-mm-dd'),             		"+
				" s.length_schooling*12),'yyyy-mm')                                                     "+
				" group by                                                                              "+
				" ?,                                                       								"+//--年月  
				" s.no_,                                                                                "+
				" s.name_,                                                                             	"+//--学生 
				" s.dept_id,                                                                            "+
				" cd.name_ ,                                                                     	 	"+//--学院，  
				" s.major_id,                                                                           "+
				" cdt.name_ ,                                                                       	"+//--专业 
				" s.class_id,                                                                           "+
				" cl.name_ ,                                                                            "+//--班级
				" s.sex_code,                                                                           "+
				" tc.name_ ,                                                                            "+//--性别  
				" s.edu_id,                                                                             "+
				" ce.name_,                                                                             "+//--学历  
				" tc2.code_,																			"+ 
				" tc2.name_																				";//--民族

		int addNum = baseDao.getJdbcTemplate().update(sql,
				new Object[] { yearMonth,yearMonth,yearMonth,yearMonth,yearMonth });
		map.put("addNum", addNum);

		return map;
	}

}
