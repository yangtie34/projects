package cn.gilight.product.net.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.enums.ShiroTagEnum;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.SqlUtil;
import cn.gilight.product.net.dao.NetTeaWarnDao;

@Repository("netTeaWarnDao")
public class NetTeaWarnDaoImpl implements NetTeaWarnDao{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Map<String,Object>> getTeaWarn(String startDate, String endDate, Map<String, String> dept) {
		String tj=SqlUtil.getDateTJ(startDate, endDate,"t")+SqlUtil.getDeptTj(dept,ShiroTagEnum.NET_TEA_WARN.getCode(),"t");
		String sql="select t.people_id,t.people_name,t.sex_code,t.sex_name,t.dept_id,t.dept_name, "+ 
          "sum(case when t.stu_num <> 0 then t.on_work_num else 0 end  ) WP,  "+ 
          "sum(case when t.stu_num <> 0 then t.out_work_num else 0 end  ) OWP,  "+ 
          "sum(case when t.stu_num = 0 then t.on_work_num else 0 end  ) WNP,  "+ 
          "sum(case when t.stu_num = 0 then t.out_work_num else 0 end  ) OWNP  "+ 
          "from (select t.people_id,t.people_name,t.sex_code,t.sex_name, "+ 
          "t.dept_id,t.dept_name,t.on_ip, "+ 
          "sum(t.on_work_num) on_work_num , "+ 
          "sum(t.out_work_num) out_work_num , "+ 
          "sum(t.stu_num) stu_num  "+ 
          "from tl_net_tea_warn t where 1=1 "+tj+" group by t.people_id,t.people_name, "+ 
          "t.sex_code,t.sex_name,t.dept_id,t.dept_name,t.on_ip ) t   "+ 
          "group by t.people_id,t.people_name,t.sex_code, "+ 
          "t.sex_name,t.dept_id,t.dept_name order by t.people_id ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public Page getTeaWarnDetil(int currentPage, int numPerPage, int totalRow,
			String startDate, String endDate, String teaId) {
		String tj=SqlUtil.getDateTJ(startDate, endDate,"t");
		String sql="select t.* from tl_net_tea_warn t where t.people_id='"+teaId+"' "+tj;
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

}
