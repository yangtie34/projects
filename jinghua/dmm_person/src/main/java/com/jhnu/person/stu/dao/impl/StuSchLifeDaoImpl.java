package com.jhnu.person.stu.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.person.stu.dao.StuSchLifeDao;
import com.jhnu.person.sys.Code;
import com.jhnu.system.common.page.Page;
@Repository("stuSchLifeDao")
public class StuSchLifeDaoImpl implements StuSchLifeDao {
	@Autowired
	private BaseDao baseDao;
	@Override
	public List yktxf(String id, String startTime, String endTime) {
		
		String sql = " SELECT  TCCP.NAME_ field,SUM(TCCP.pay_money) value ,1 bz "
				+ " FROM (SELECT TCCD.NAME_,TCP.pay_money "
				+ " FROM T_CARD_PAY TCP "
				+ " LEFT JOIN T_CODE_CARD_DEAL TCCD ON TCCD.ID = TCP.CARD_DEAL_ID "
				+ "  left join T_CARD TC on tc.NO_=TCP.CARD_ID "
				+ "   WHERE tc.people_id = '"
				+ id
				+ "' AND TCP.TIME_ BETWEEN '"
				+ startTime
				+ "' and '"
				+ endTime
				+ "' and (TCP.CARD_DEAL_ID='G18' OR TCP.CARD_DEAL_ID='G2')) TCCP "
				+ " GROUP BY TCCP.NAME_ ";
				//+ " UNION "
				//+ " SELECT '余额' field,to_number(TC.CARD_BALANCE) value,0 bz FROM T_CARD TC WHERE TC.PEOPLE_ID='"
				//+ id + "' ";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	@Override
	public List yktye(String id) {
		//String sql = " SELECT to_number(TC.CARD_BALANCE) value FROM T_CARD TC WHERE TC.PEOPLE_ID='"
		//		+ id + "' ";
		 String sql="select W.SURPLUS_MONEY value from T_CARD_PAY W "
			        + "  WHERE W.CARD_ID = (SELECT no_ FROM T_CARD TC WHERE TC.PEOPLE_ID = '"+ id + "') "
			                + "  AND TIME_ = "
			                + "  (select MAX(T.TIME_) "
			                + "  from T_CARD_PAY T "
			                + "  WHERE T.CARD_ID = "
			                + "  (SELECT no_ FROM T_CARD TC WHERE TC.PEOPLE_ID = '"+ id + "')) ";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	@Override
	public List yktcz(String id, String startTime, String endTime) {
		

		String sql = " SELECT DISTINCT DZTYK.code_, tcre.time_ time_, DZTYK.name_ field,tcre.Pay_Money value "
				+ " FROM t_card_PAY TCRE "
				+ " LEFT JOIN T_CODE_CARD_DEAL DZTYK ON TCRE.CARD_DEAL_ID=DZTYK.id "
				+ "  left join T_CARD TC on tc.NO_=TCRE.CARD_ID "
				+ "   WHERE tc.people_id = '" + id + "' "
						+ " AND TCRE.TIME_ BETWEEN '"
				+ startTime
				+ "' and '"
				+ endTime
				+ "' "
						+ " and( tcre.card_deal_id='G19' or tcre.card_deal_id='G21' or tcre.card_deal_id='G24')  "
						+ " order by  tcre.time_ desc";
	           
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List yktxffx(String id, String startTime, String endTime) {
		
		String sql = " SELECT '金额' NAME,SUBSTR(TCP.TIME_,0,7) FIELD,SUM(TCP.PAY_MONEY) VALUE  FROM T_CARD_PAY TCP  "
				+ "  left join T_CARD TC on tc.NO_=TCP.CARD_ID "
				+ "   WHERE tc.people_id = '"
				+ id
				+ "' "
				+ "AND SUBSTR(TCP.TIME_,0,7) BETWEEN  '"
				+ startTime
				+ "' and '"
				+ endTime
				+ "'"
				+ " GROUP BY TCP.CARD_ID,SUBSTR(TCP.TIME_,0,7) ORDER BY SUBSTR(TCP.TIME_, 0, 7) ASC ";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public Page yktxfmx(String id, String startTime, String endTime,
			int currentPage, int numPerPage) {
			String sql = " SELECT TCCD.NAME_ cl01,TCP.PAY_MONEY cl02,pcde.name_ cl03,TCP.TIME_ cl04"
					+ " FROM T_CARD_PAY TCP "
					+ " LEFT JOIN T_CODE_CARD_DEAL TCCD ON TCP.CARD_DEAL_ID=TCCD.ID "
					+ "  left join T_CARD TC on tc.no_=TCP.CARD_ID "
					+"   left join T_CARD_PORT TP on Tcp.card_port_id=TP.NO_ "
					+"	left join T_CARD_DEPT cde on TP.card_DEPT_id=cde.id "
					+"  left join T_CARD_DEPT pcde on cde.pid=pcde.id   "
					+ "   WHERE tc.people_id = '"
					+ id
					+ "' "
					+ "  AND SUBSTR(TCP.TIME_,0,10) BETWEEN  '"
					+ startTime
					+ "' and '"
					+ endTime
					+ "' order by TCP.TIME_ desc";

			return new Page(sql.toString(), currentPage, numPerPage, baseDao
					.getBaseDao().getJdbcTemplate(), null);

	}

	@Override
	public List ieAvgTime(String id, String startTime, String endTime) {
		//TODO 学生上网信息
		String sql = " SELECT TNR.ACCOUNT_ID 上网账号,SUBSTR(TNR.ON_TIME,0,10) 开始时间,MIN(to_number(to_date(TNR.OFF_TIME,'yyyy-mm-dd hh24:mi:ss')-to_date(TNR.ON_TIME,'yyyy-mm-dd hh24:mi:ss'))*24*60) 最小值,MAX(to_number(to_date(TNR.OFF_TIME,'yyyy-mm-dd hh24:mi:ss')-to_date(TNR.ON_TIME,'yyyy-mm-dd hh24:mi:ss'))*24*60) 最大值,(sum(to_number(to_date(TNR.OFF_TIME,'yyyy-mm-dd hh24:mi:ss')-to_date(TNR.ON_TIME,'yyyy-mm-dd hh24:mi:ss'))*24*60)/(to_date('"+startTime+"','yyyy-mm-dd')-to_date('"+endTime+"','yyyy-mm-dd'))) 日均上网时间  "
				+ " FROM T_NET_RECORD TNR "
				+ " left join T_NET_USER tnu on TNR.ACCOUNT_ID=tnu.NO_"
				+ " WHERE tnu.NO_='"
				+ id
				+ "' AND SUBSTR(TNR.ON_TIME,0,10) BETWEEN '"
				+ startTime
				+ "' and '"
				+ endTime
				+ "' "
				+ " GROUP BY TNR.ACCOUNT_ID,SUBSTR(TNR.ON_TIME,0,10) ";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List ieAllTime(String id, String startTime, String endTime) {
		// 创建上网时间视图
		String sqlselect= "select ACCOUNT_,sum(TIME_NUM) SSUM_ from( "
				+"　SELECT TNR.ACCOUNT_ID ACCOUNT_ , "
				+"　floor(to_number(to_date(TNR.OFF_TIME,'yyyy-mm-dd hh24:mi:ss')-to_date(TNR.ON_TIME,'yyyy-mm-dd hh24:mi:ss'))*24*60) TIME_NUM  " 
				+"  FROM T_NET_RECORD TNR " 
				+"  WHERE to_char(TO_DATE(SUBSTR(TNR.ON_TIME,1,10),'yyyy-mm-dd'),'yyyy-mm-dd') "
				+"  BETWEEN '"+startTime+"' AND '"+endTime+"' ) ";
		//baseDao.getBaseDao().getJdbcTemplate().execute(sql);
		//本人累计上网时长
		//上网账号 累计上网时长
		String sql=sqlselect+"where ACCOUNT_='"+id+"' GROUP BY ACCOUNT_";
		List list=new ArrayList();
		Map map=new HashMap();
		map.put("grsw", baseDao.getBaseDao().getJdbcTemplate().queryForList(sql));
		sql="SELECT MIN(TNV.SSUM_) MIN_, MAX(TNV.SSUM_) MAX_ ,avg(TNV.SSUM_) avg_,SUM(TNV.SSUM_) SUM_ FROM ("+sqlselect+" GROUP BY ACCOUNT_)TNV  ";
		map.put("xxsw", baseDao.getBaseDao().getJdbcTemplate().queryForList(sql));
		list.add(map);
		return list;
	}

	@Override
	public List schFirst(String id) {
		
		// 首次餐费支出 餐费支出(CARD_ID)代码是210
		String sql = " CREATE  OR  REPLACE  VIEW  TCP_VIEW_SCXF "
				+ " (CARD_ID,NAME_,PAY_MONEY,TIME_) "
				+ " AS "
				+ " SELECT TCCD.CODE_,TCCD.NAME_ ,TCP.PAY_MONEY ,TCP.TIME_ "
				+ " FROM T_CARD_PAY TCP "
				+ " LEFT JOIN T_CODE_CARD_DEAL TCCD ON TCP.CARD_DEAL_ID=TCCD.ID "
				+ "  left join T_CARD TC on tc.NO_=TCP.CARD_ID "
				+ "   WHERE tc.people_id = '" + id + "' ";
		baseDao.getBaseDao().getJdbcTemplate().execute(sql);
		sql = " SELECT t.*,t1.bl FROM TCP_VIEW_SCXF  t "
				+ " LEFT JOIN ( "
				+ " select c1.c1/c2.c2 bl from  "
				+ " (SELECT COUNT(*) c1 FROM TCP_VIEW_SCXF "
				+ " WHERE PAY_MONEY<( "
				+ " SELECT PAY_MONEY FROM TCP_VIEW_SCXF WHERE CARD_ID='G18'AND ROWNUM=1)) c1 "
				+ " left join "
				+ " (SELECT COUNT(*) c2 FROM TCP_VIEW_SCXF)c2 on 1=1 "
				+ " ) T1 ON 1=1 " + " WHERE CARD_ID='"+Code.getKey("ykt.rice")+"'AND ROWNUM=1";
		List list=new ArrayList();
		Map map=new HashMap();
		map.put("cf", baseDao.getBaseDao().getJdbcTemplate().queryForList(sql));
		// 首次购物支出 购物支出(CARD_ID)代码是215
		sql = " SELECT t.*,t1.bl FROM TCP_VIEW_SCXF  t "
				+ " LEFT JOIN ( "
				+ " select c1.c1/c2.c2 bl from  "
				+ " (SELECT COUNT(*) c1 FROM TCP_VIEW_SCXF "
				+ " WHERE PAY_MONEY<( "
				+ " SELECT PAY_MONEY FROM TCP_VIEW_SCXF WHERE CARD_ID='G2'AND ROWNUM=1)) c1 "
				+ " left join "
				+ " (SELECT COUNT(*) c2 FROM TCP_VIEW_SCXF)c2 on 1=1 "
				+ " ) T1 ON 1=1 " + " WHERE CARD_ID='"+Code.getKey("ykt.shop")+"'AND ROWNUM=1 ";
		map.put("gw", baseDao.getBaseDao().getJdbcTemplate().queryForList(sql));
		// 矿大首次--首次借阅
		sql = " SELECT TT.NAME_ NAME_, TT.BORROW_TIME TIME_ "
				+ " FROM (SELECT T.NAME_, T.BORROW_TIME "
				+ " FROM (SELECT TB.NAME_, TBB.BORROW_TIME, TB.NO_ "
				+ "  FROM T_BOOK_BORROW TBB "
				+ "  LEFT JOIN T_BOOK TB ON TB.NO_ = TBB.BOOK_ID "
				+ "  left join T_BOOK_READER tbr on tbr.NO_=TBB.BOOK_READER_ID "
				+ "  WHERE tbr.PEOPLE_ID = '" + id + "' "
				+ " ORDER BY TBB.BORROW_TIME ASC) T WHERE ROWNUM = 1) TT ";
		map.put("jy", baseDao.getBaseDao().getJdbcTemplate().queryForList(sql));
		// 首次获得荣誉
		sql = " SELECT T.DATE_ TIME_,TC1.NAME_ NAME_ "
				+ " FROM T_STU_GLORY T "
				+ " LEFT JOIN T_CODE TC1 on TC1.CODE_ = T.GLORY_CODE and TC1.CODE_TYPE='GLORY_CODE' "
				+ " WHERE T.STU_ID='" + id + "' " + " ORDER BY T.DATE_ ASC";
		map.put("ry", baseDao.getBaseDao().getJdbcTemplate().queryForList(sql));
		list.add(map);
		return list;
	}

}