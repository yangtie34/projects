package com.jhnu.product.four.wall.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.four.wall.dao.FourWallDao;

@Repository("fourWallDao")
public class FourWallDaoImpl implements FourWallDao{

	@Autowired
	private BaseDao baseDao;
	
	@Override
	public List<Map<String, Object>> getWallForLog(String id) {
		String sql=" select case                                                                                                                                        "+
				" when to_number(substr(more_than, 0, length(more_than) - 1)) >= 67 then '手不释卷'                                                                  "+
				" when to_number(substr(more_than, 0, length(more_than) - 1)) >= 34 and to_number(substr(more_than, 0, length(more_than) - 1)) < 67 then '读书平平'  "+
				" else '寡闻' end as name, more_than, '读书'as  type                                                                                                 "+
				" from tl_book_rke where sno= ?                                                                                                          "+
				" union all                                                                                                                                          "+
				" select case                                                                                                                                        "+
				" when to_number(substr(more_than, 0, length(more_than) - 1)) >= 67 then  '土豪'                                                                     "+
				" when to_number(substr(more_than, 0, length(more_than) - 1)) >= 34 and to_number(substr(more_than, 0, length(more_than) - 1)) < 67 then '小资'      "+
				" else  '小康' end as name, more_than, '消费' as type                                                                                                "+
				" from TL_PAY_TIMES_MONEY where no_= ?                                                                                                   "+
				" union all                                                                                                                                          "+
				" select case                                                                                                                                        "+
				" when to_number(substr(more_than, 0, length(more_than) - 1)) >= 67 then '学霸'                                                                      "+
				" when to_number(substr(more_than, 0, length(more_than) - 1)) >= 10 and to_number(substr(more_than, 0, length(more_than) - 1)) < 67 then '成绩平平'      "+
				" else '学渣' end as name, more_than, '学习' as type                                                                                                  "+
				" from tl_score_avg where stu_id= ?                                                                                                      ";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql, new Object[]{id,id,id});
	}
}
