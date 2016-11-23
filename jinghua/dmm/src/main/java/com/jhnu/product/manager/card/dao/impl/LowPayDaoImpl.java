package com.jhnu.product.manager.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.manager.card.dao.LowPayDao;
import com.jhnu.system.common.page.Page;
import com.jhnu.util.common.StringUtils;

@Repository("lowPayDao")
public class LowPayDaoImpl implements LowPayDao {

	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Map<String, Object>> getAvgEatMoneyBySex(String startDate, String endDate) {
		String sql = "select stu_sex,sj,round(sum(pay_money)/count(distinct stu_id),2) pay_mpney from (" + " select stu.no_ stu_id,xb.mc stu_sex,sj,round(sum(pay_money)/count(distinct pay_date),2) pay_money from (" + " select card_id,pay_date,sj,sum(pay_money) pay_money from(" + " select pay.card_id,pay.pay_money,substr(time_,0,10) pay_date,case when substr(pay.time_,12,2) <= 9 then '早餐'" + " when  substr(pay.time_,12,2) between '11' and '14' then '午餐'" + " when substr(pay.time_,12,2) between '16' and '24' then '晚餐'" + " else '' end as sj" + " from t_card_pay pay where pay.card_deal_id = '210' and pay.time_ between '" + startDate + "' and '" + endDate + "' ) where sj is not null " + " group by  card_id,pay_date,sj ) left join t_card card on card.no_ = card_id" + " inner join t_stu stu on stu.no_ = card.people_id" + " left join dm_zxbz.t_zxbz_xb xb on xb.dm = stu.sex_code " + " group by stu.no_,xb.mc,sj ) group by stu_sex,sj";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public void saveStuEatDetail(String sex_code) {
		String table = "";
		if ("1".equals(sex_code)) {
			String delSql = "delete  t_card_low_detail_boy";
			baseDao.getBaseDao().executeSql(delSql);
			table = "t_card_low_detail_boy";
		} else if ("2".equals(sex_code)) {
			String delSql = "delete  t_card_low_detail_gril";
			baseDao.getBaseDao().executeSql(delSql);
			table = "t_card_low_detail_gril";
		}
		String sql = "insert into " + table + " select t1.*,t2.pj2,case when T1.zxfje>=pj2 then 1 else 0 end SFGXF from (   " + "	SELECT no_ STUID,CBDM,SUBSTR(time_,1,10) AS XFRQ,SUM(pay_money) ZXFJE,COUNT(pay_money) SKCS FROM  " + "	(select pay.pay_money,pay.time_,stu.no_,                " + "	case when SUBSTR(pay.time_,12,5) < '09:00' then '1'         " + "		 when SUBSTR(pay.time_,12,5) BETWEEN '11:00' AND '14:00' then '2'  " + "		 when SUBSTR(pay.time_,12,5) BETWEEN '16:00' AND '24:00' then '3'                                                    " + "		 end cbdm                                            " + "	from t_card_pay pay                                      " + "	inner join t_card card on pay.card_id=card.no_           " + "	inner join t_stu stu on stu.no_ = card.people_id         " + "	where pay.card_deal_id='210' and stu.sex_code='" + sex_code + "' and card.istrue = 1 and pay.time_ >TO_CHAR((SYSDATE-180),'yyyy-MM-dd'))  " + "	GROUP BY SUBSTR(time_,1,10),CBDM,no_                     " + "	) t1 left join (                                         " + "	SELECT CBDM,SUBSTR(time_,1,10) AS XFRQ,SUM(pay_money) ZXFJE,COUNT(DISTINCT no_) SKCS,round(sum(pay_money)/count(DISTINCT no_),2) pj2 FROM  " + "	(select pay.pay_money,pay.time_,stu.no_,                                  " + "	case when SUBSTR(pay.time_,12,5) < '09:00' then '1'                       " + "		 when SUBSTR(pay.time_,12,5) BETWEEN '09:00' AND '17:00' then '2'     " + "		 else '3'                                                             " + "		 end cbdm                                                             " + "	from t_card_pay pay                                                       " + "	inner join t_card card on pay.card_id=card.no_                            " + "	inner join t_stu stu on stu.no_ = card.people_id                          " + "	where pay.card_deal_id='210' and stu.sex_code='" + sex_code + "' and card.istrue = 1 and pay.time_ >TO_CHAR((SYSDATE-180),'yyyy-MM-dd') )   " + "	GROUP BY SUBSTR(time_,1,10),CBDM ORDER BY XFRQ,CBDM   " + "	) t2 on t1.cbdm = t2.cbdm and t1.xfrq = t2.xfrq";
		baseDao.getBaseDao().executeSql(sql);
	}

	@Override
	public void saveStuEatResult(String fz) {
		String delSql = "delete  t_card_low_result";
		baseDao.getBaseDao().executeSql(delSql);
		String sql = "insert into t_card_low_result " + " SELECT T1.STUID,BDBS,CFCS,ROUND(BDBS/CFCS*100,2) BDBL FROM (" + " SELECT STUID,COUNT(*) BDBS FROM (SELECT * FROM t_card_low_detail_boy UNION ALL SELECT * FROM t_card_low_detail_gril) " + " WHERE SFGXF=0 GROUP BY STUID ORDER BY BDBS DESC)T1,(" + " SELECT STUID,CFCS FROM (SELECT STUID,COUNT(*) CFCS FROM (SELECT * FROM t_card_low_detail_boy " + " UNION ALL SELECT * FROM t_card_low_detail_gril ) GROUP BY STUID ORDER BY CFCS) " + " WHERE CFCS >=" + fz + ")T2 WHERE T1.STUID = T2.STUID ORDER BY BDBL DESC ";
		baseDao.getBaseDao().executeSql(sql);

	}

	@Override
	public List<Map<String, Object>> getFaZhi() {
		String sql = "SELECT CASE" + " WHEN MEDIAN(CFCS) >= ROUND(AVG(CFCS), 1) THEN" + " ROUND(AVG(CFCS), 1) ELSE" + " MEDIAN(CFCS)" + " END YZ" + " FROM (SELECT STUID, COUNT(*) CFCS" + " FROM (SELECT * FROM t_card_low_detail_boy UNION ALL" + " SELECT * FROM t_card_low_detail_gril )" + " GROUP BY STUID" + " ORDER BY CFCS)";

		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public Page getLowPayStu(String dept_id, boolean isLeaf, int currentPage, int pageSize) {
		String sql2 = "";
		if (!StringUtils.hasText(dept_id)) {
			return null;
		} else if ("0".equals(dept_id)) {
			sql2 = "";// 显示全校数据
		} else if (!dept_id.contains(",")) {
			sql2 = "where stu.dept_id = '" + dept_id + "' or stu.major_id = '" + dept_id + "' ";// 显示某个院系或某个专业数据
		} else {
			if (isLeaf) {
				sql2 = "where stu.major_id in (" + dept_id + ") "; // 显示某些专业数据
			} else {
				sql2 = "where stu.dept_id in (" + dept_id + ") "; // 显示某些学院数据
			}
		}
		String sql = "select clr.stuid stu_id,stu.name_ stu_name,th.name_ dept,cdt.name_ major," + "stu.sex_code, xb.mc stu_sex,stu.enroll_grade,clr.cfcs,clr.bdbs,clr.bdbl" + " from t_card_low_result clr " + " left join t_stu stu on stu.no_ = clr.stuid" + " left join t_code_dept_teach th on th.id = stu.dept_id " + " left join t_code_dept_teach cdt on cdt.id = stu.major_id" + " left join dm_zxbz.t_zxbz_xb xb on xb.dm = stu.sex_code " + sql2 + " order by clr.bdbl desc";
		return new Page(sql, currentPage, pageSize, baseDao.getBaseDao().getJdbcTemplate(), null);
	}

	@Override
	public Page getLowPayDetailStu(String stuId, String sexCode, String startDate, String endDate, int currentPage, int pageSize) {
		String sql = "select * from " + ("1".equals(sexCode) ? "T_CARD_LOW_DETAIL_BOY" : "T_CARD_LOW_DETAIL_GRIL") + " where stuid = '" + stuId + "' and xfrq BETWEEN '" + startDate + "' and '" + endDate + "' order by xfrq desc, cbdm asc";
		return new Page(sql, currentPage, pageSize, baseDao.getBaseDao().getJdbcTemplate(), null);
	}

}
