package cn.gilight.product.net.dao.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.enums.ShiroTagEnum;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.SqlUtil;
import cn.gilight.product.net.dao.NetTeaTopDao;

@Repository("netTeaTopDao")
public class NetTeaTopDaoImpl implements NetTeaTopDao{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public Page getTeaTop(int currentPage, int numPerPage, int totalRow,
			String startDate, String endDate, Map<String, String> dept,
			String type, int rank) {
		String tj=SqlUtil.getDateTJ(startDate, endDate, "t")+SqlUtil.getDeptTj(dept, ShiroTagEnum.NET_TEA_RANK.getCode(), "t");
		String sql="select * from ( "+
					"select dense_rank() over(order by sum(t.use_"+type+") desc) rank_, "+
					"sum(t.use_time) use_time,sum(t.use_flow) use_flow,sum(t.use_money) use_money, "+
					"sum(t.all_counts) all_counts,t.tea_no,t.tea_name,t.sex_code,t.sex_name,t.dept_id, "+
					"t.dept_name,t.edu_id,t.edu_name from tl_net_tea_month t where 1=1 "+tj+
					"group by t.tea_no,t.tea_name,t.sex_code,t.sex_name, "+
					"t.dept_id,t.dept_name,t.edu_id,t.edu_name "+
					") where rank_ <="+rank;
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}
	
}
