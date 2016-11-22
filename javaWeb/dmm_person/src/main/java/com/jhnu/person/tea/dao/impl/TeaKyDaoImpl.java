package com.jhnu.person.tea.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.person.tea.dao.TeaKyDao;
import com.jhnu.system.common.page.Page;

@Repository("teaKyDao")
public class TeaKyDaoImpl implements TeaKyDao {
	@Autowired
	private BaseDao baseDao;

	@Override
	public List pushBooks(String id, String startTime, String endTime) {
		String sql = "select t.BOOK_ID,tb.NAME_ ,tb.writer write_,tb.press press,count(t.id) count_ from T_BOOK_BORROW t  "
				+ " left join T_BOOK tb on tb.NO_=t.BOOK_ID "
				+ " where t.BOOK_READER_ID in( "
				+ " select TEA_NO from t_tea  where DEPT_ID =(select DEPT_ID from t_tea  where TEA_NO='"
				+ id
				+ "')) "
				+ " and t.BORROW_TIME between '"
				+ startTime
				+ "' and '"
				+ endTime
				+ "' and rownum<6"
				+ " group by t.BOOK_ID ,tb.NAME_,tb.writer,tb.press  order by count_ desc ";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List jyfl(String id, String startTime, String endTime) {
		String sql = "select tc.name_ field,count(book.no_) value,'借阅分类' name "// --book.name_
																				// book_name,t.borrow_time
				+ " from t_book_borrow t  "
				+ "  left join t_book_reader br on br.no_ = t.book_reader_id  "
				+ "  left join t_book book on book.no_ = t.book_id "
				+ "  left join t_code tc on book.store_code=tc.code_ and code_type like '%STORE_CODE%' "
				+ "  where br.people_id = '"
				+ id
				+ "' and t.borrow_time between'"
				+ startTime
				+ "' and '"
				+ endTime + "'  group by tc.name_ ";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List jysl(String id, String startTime, String endTime) {
		String sql=" select max(count(*)) max_ ,min(count(*)) min_ from T_BOOK_BORROW t "
				+ "where t.borrow_time between '" + startTime + "' and '"+ endTime + "'"
				+ "group by t.book_reader_id";//最大 最小
		List<Map<String, Object>> list= baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
		
		 sql = " select count(t.id) counts,t.borrow_time time "
				+ " from t_book_borrow t  "
				+ "left join t_book_reader br on br.no_ = t.book_reader_id "
				+ "where br.people_id =  '" + id
				+ "' and t.borrow_time between'" + startTime + "' and '"
				+ endTime + "' group by t.borrow_time order by counts ";
		sql = " select sum(t.counts) sum_,min(t.counts) min_,sum(t.counts) avg_ from ("

				+ sql + ") t";
		List<Map<String, Object>> list1= baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
		list.get(0).put("AVG_", list1.get(0).get("AVG_"));
		list.get(0).put("SUM_", list1.get(0).get("SUM_"));
		return list;
	}

	@Override
	public Page jymx(String id, String startTime, String endTime,
			int currentPage, int numPerPage) {
		//id,名称,借书时间,应还时间,归还时间,借书状态
		String sql = "select book.name_ cl02, "
				+ " t.borrow_time cl03,t.SHOULD_RETURN_TIME cl04,t.return_time cl05, "

				+ "  case "
				+ "   when t.return_time is null and t.should_return_time < t.return_time then "
				+ "    '超期' "
				+ "    when t.return_time is null and t.should_return_time > t.return_time then "
				+ "     '在阅' " + "    else " + "      '已还' "
				+ "     end as cl06 " + "  from t_book_borrow t "
				+ "  left join t_book_reader br on br.no_ = t.book_reader_id "
				+ "  left join t_book book on book.no_ = t.book_id "
				+ "  where br.people_id =  '" + id
				+ "' and t.borrow_time between'" + startTime + "' and '"
				+ endTime + "'   order by t.borrow_time desc ";
		return new Page(sql.toString(), currentPage, numPerPage, baseDao
				.getBaseDao().getJdbcTemplate(), null);
	}

	@Override
	public List kyxx(String id, String startTime, String endTime) {
		
		List list=new ArrayList();
		Map map=new HashMap();
		// 科研信息
		// 项目名称 开始时间 参与人(校外作者姓名 +教职工姓名)
		String kyxm = " SELECT TRP.NAME_ cl01,TRP.START_TIME cl02, "
				+ " CONCAT(CASE TRPA.PEOPLE_IDENTITY_CODE "
				+ " WHEN '0' THEN (SELECT T.NAME_   FROM T_STU T WHERE T.NO_=TRPA.PEOPLE_ID) "
				+ " WHEN '1' THEN (SELECT TE.NAME_  FROM T_TEA TE WHERE TE.TEA_NO=TRPA.PEOPLE_ID) "
				+ " END,(','||TRPA.PEOPLE_NAME)) CL03 "
				+ " FROM T_RES_PROJECT_AUTH TRPA "
				+ " LEFT JOIN T_RES_PROJECT TRP ON TRP.ID=TRPA.PRO_ID "
				+ " WHERE TRPA.PEOPLE_ID='" + id+ "' "
						+ "and TRP.START_TIME between'" + startTime + "' and '"
				+ endTime + "'";
		
		map.put("kyxm", baseDao.getBaseDao().getJdbcTemplate().queryForList(kyxm));
		// 获得成果 
		// 成果 获奖名称 获奖时间  参与人(校外作者姓名 +教职工姓名)
		String hjcg = " SELECT TROA.TEA_NO cl01,TRP.NAME_ cl02,TRP.AWARD_NAME cl03,TRP.AWARD_TIME cl04,TROA.OUTSIDE_AUTHOR cl05, "
				+ " CASE TROA.PEOPLE_IDENTITY_CODE "
				+ " WHEN '0' THEN (SELECT T.NAME_  FROM T_STU T WHERE T.NO_=TROA.PEOPLE_ID) "
				+ " WHEN '1' THEN (SELECT TE.NAME_ FROM T_TEA TE WHERE TE.TEA_NO=TROA.PEOPLE_ID)  "
				+ " END as cl06"
				+ " FROM T_RES_HJCG_AUTH TROA "
				+ " LEFT JOIN T_RES_HJCG TRP ON TRP.ID=TROA.OUTCOME_AWARD_ID "
				+ " WHERE TROA.PEOPLE_ID='" + id+ "' "
				+ "and TRP.AWARD_TIME between'" + startTime + "' and '"
				+ endTime + "'";
		map.put("hjcg", baseDao.getBaseDao().getJdbcTemplate().queryForList(hjcg));
		// 发表论文
		//论文名称 发表年份  参与人(校外作者姓名 +教职工姓名)
		String lw = " SELECT TRT.TITLE_ cl01,TRT.YEAR_ cl02, "
				+ " CONCAT(CASE TRTA.PEOPLE_IDENTITY_CODE  "
				+ " WHEN '0' THEN (SELECT T.NAME_   FROM T_STU T WHERE T.NO_=TRTA.PEOPLE_ID) "
				+ " WHEN '1' THEN (SELECT TE.NAME_  FROM T_TEA TE WHERE TE.TEA_NO=TRTA.PEOPLE_ID) "
				+ " END,','||TRTA.PEOPLE_NAME) CL03 "
				+ " FROM T_RES_THESIS_AUTHOR TRTA "
				+ " LEFT JOIN T_RES_THESIS TRT ON TRT.ID=TRTA.THESIS_ID "
				+ " WHERE TRTA.PEOPLE_ID='" + id+ "' "
				+ "and TRT.YEAR_ between'" + startTime + "' and '"
						+ endTime + "'";
		map.put("lw", baseDao.getBaseDao().getJdbcTemplate().queryForList(lw));
		//获得专利
		//专利名称  获得专利时间  发明人   参与人(校外作者姓名 +教职工姓名)
		String hdzl=" SELECT TRT.NAME_ CL01,TRT.ACCREDIT_TIME CL02, TRT.INVENTORS CL03,"+
					" CONCAT(CASE TRTA.PEOPLE_IDENTITY_CODE "+
				    " WHEN '0' THEN "+
				    " (SELECT T.NAME_ FROM T_STU T WHERE T.NO_ = TRTA.PEOPLE_ID) "+
				    " WHEN '1' THEN "+
				    " (SELECT TE.NAME_ FROM T_TEA TE WHERE TE.TEA_NO = TRTA.PEOPLE_ID) "+
				    " END, ',' || TRTA.PEOPLE_NAME) CL04 "+
				    " FROM T_RES_PATENT_AUTH TRTA "+
				    " LEFT JOIN T_RES_PATENT TRT "+
				    " ON TRT.ID = TRTA.PATENT_ID "+
				    " WHERE TRTA.PEOPLE_ID = '" + id+ "' "+
				   "  and TRT.ACCREDIT_TIME between '" + startTime + "' and '"+ endTime + "' ";
		map.put("hdzl", baseDao.getBaseDao().getJdbcTemplate().queryForList(hdzl));
		//项目经费
		// 部门号 项目号 部门名称 项目信息 项目金额 
		String xmjf="SELECT TT.DEPT_ID CL01,TRP.PRO_NO CL02,TCD.NAME_ CL03,TRP.NAME_ CL04,TRP.FUND CL05  "
					+" FROM T_RES_PROJECT_AUTH TRPA "
					+" LEFT JOIN T_TEA TT ON TT.TEA_NO=TRPA.PEOPLE_ID "
					+" LEFT JOIN T_RES_PROJECT TRP ON TRP.ID=TRPA.PRO_ID "
					+" LEFT JOIN T_CODE_DEPT TCD ON TCD.ID=TT.DEPT_ID  "
					+" WHERE TRPA.PEOPLE_ID='"+id+"' AND SUBSTR(TRP.START_TIME, 1, 4) BETWEEN " 
					+" SUBSTR('"+startTime+"',1,4) AND SUBSTR('"+endTime+"',1,4)";
		map.put("xmjf", baseDao.getBaseDao().getJdbcTemplate().queryForList(xmjf));
		list.add(map);
		return list;
	}
}
