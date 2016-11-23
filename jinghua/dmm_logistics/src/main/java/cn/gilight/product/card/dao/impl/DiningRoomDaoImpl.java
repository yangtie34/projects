package cn.gilight.product.card.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.product.card.dao.CardTjUtil;
import cn.gilight.product.card.dao.DiningRoomDao;

@Repository("diningRoomDao")
public class DiningRoomDaoImpl implements DiningRoomDao{

	@Autowired
	private BaseDao baseDao;
	
	@Override
	public Page getDiningRoom(int currentPage, int numPerPage, int totalRow,
			String startDate, String endDate, String queryType) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate);
		String sql="select t.card_dept_pid id,card_dept_pname name,sum(t.pay_money) all_money,sum(t.pay_count) all_count,  "+
				"round(sum(t.pay_money)/round(to_date('"+endDate+"','yyyy-mm')-to_date('"+startDate+"','yyyy-mm'),0 ),2) day_money,    "+
				"round(sum(t.pay_count)/round(to_date('"+endDate+"','yyyy-mm')-to_date('"+startDate+"','yyyy-mm'),0 ),0) day_count,    "+
				"round(sum(t.pay_money)/sum(t.pay_count),2) one_money "+
				"from tl_card_pay_stu_month t where 1=1 "+tj+
				"and t.card_dept_pname is not null "+
				//"and t.card_deal_id='"+Code.getKey("card.ct")+"'    "+
				"and t.card_dept_type LIKE '%RES%' "+
				CardTjUtil.getHourTj(queryType)+
				"group by t.card_dept_pid,t.card_dept_pname";
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getDiningPort(int currentPage, int numPerPage, int totalRow,
			String startDate, String endDate, String queryType) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate);
		String sql="select t.card_dept_id id,card_dept_name name,sum(t.pay_money) all_money,sum(t.pay_count) all_count,    "+
				"round(sum(t.pay_money)/round(to_date('"+endDate+"','yyyy-mm')-to_date('"+startDate+"','yyyy-mm'),0 ),2) day_money,    "+
				"round(sum(t.pay_count)/round(to_date('"+endDate+"','yyyy-mm')-to_date('"+startDate+"','yyyy-mm'),0 ),0) day_count,    "+
				"round(sum(t.pay_money)/sum(t.pay_count),2) one_money "+
				"from tl_card_pay_stu_month t where 1=1 "+tj+
				"and t.card_dept_name is not null "+
				//"and t.card_deal_id='"+Code.getKey("card.ct")+"'    "+
				"and t.card_dept_type LIKE '%RES%' "+
				CardTjUtil.getHourTj(queryType)+
				"group by t.card_dept_id,t.card_dept_name ";
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}
	
}
