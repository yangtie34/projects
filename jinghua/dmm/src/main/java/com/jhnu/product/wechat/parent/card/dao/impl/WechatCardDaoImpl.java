package com.jhnu.product.wechat.parent.card.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.wechat.parent.card.dao.WechatCardDao;
import com.jhnu.product.wechat.parent.card.entity.TlWechatConsumAnalyze;

@Repository("wechatCardDao")
public class WechatCardDaoImpl implements WechatCardDao{
	@Autowired
	private BaseDao baseDao;
	@Override
	public List<Map<String, Object>> getCustomAnalyzeResult(String stuId,String anaType,String timeType) {
		String sql ="SELECT * FROM tl_wechat_consum_analyze WHERE STU_ID=? AND ANALYZE_PAYDEAL_ID =? AND TIME_TYPE=?";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql, new Object[]{stuId,anaType,timeType});
	}
	@Override
	public List<Map<String, Object>> getCustomAnalyzeResult(String stuId,String timeType) {
		String sql ="SELECT * FROM tl_wechat_consum_analyze WHERE STU_ID=? AND TIME_TYPE=?";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql, new Object[]{stuId,timeType});
	}
	@Override
	public double getCustomAvg4AllStu(String anaType, String timeType,String xbCode) {
		String sql ="SELECT NVL(ROUND(AVG(XFLX_AVG),2),0) FROM tl_wechat_consum_analyze WHERE ANALYZE_PAYDEAL_ID =? AND TIME_TYPE=? AND SEX=?";
		return baseDao.getLogDao().getJdbcTemplate().queryForObject(sql, Double.class, new Object[]{anaType,timeType,xbCode});
	}
	
	@Override
	public void saveWachatCardAna2Log(final List<TlWechatConsumAnalyze> cads) {
		String sql ="INSERT INTO TL_WECHAT_CONSUM_ANALYZE VALUES(?,?,?,?,?,?,?,?)";
		baseDao.getLogDao().getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				TlWechatConsumAnalyze tca = cads.get(i);
				ps.setString(1, tca.getStuId());
				ps.setString(2, tca.getSex());
				ps.setString(3, tca.getTimeType());
				ps.setString(4, tca.getAnalyzePaydealId());
				ps.setString(5, tca.getSum_());
				ps.setString(6, tca.getXflxZb());
				ps.setString(7, tca.getXflxAvg());
				ps.setString(8, tca.getMoreThan());
			}
			
			@Override
			public int getBatchSize() {
				return cads.size();
			}
		} );
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<TlWechatConsumAnalyze> getWachatCardAnaData(String timeType,String[] dateSection,String groupCode,List<String> cardDealIds) {
		StringBuffer sb = new StringBuffer();
		for(String str : cardDealIds){
			sb.append("'").append(str).append("',");
		}
		String ids = sb.toString().substring(0, sb.length()-1);
		String sql ="SELECT S.NO_ STU_ID,CO.NAME_ SEX,SUM(P.PAY_MONEY) SUM_,ROUND(AVG(P.PAY_MONEY),2) XFLX_AVG"
				+ ",'"+timeType+"' TIMETYPE,'"+groupCode+"' ANALYZE_PAYDEAL_ID FROM T_CARD_PAY P "
						+ "LEFT JOIN T_CARD C ON C.NO_ = P.CARD_ID INNER JOIN T_STU S ON S.NO_ = C.PEOPLE_ID "
						+ "LEFT JOIN T_CODE CO ON CO.CODE_ = S.SEX_CODE AND CO.CODE_TYPE='SEX_CODE'"
				+ "WHERE P.TIME_ BETWEEN '"+dateSection[0]+"' AND '"+dateSection[1]+"' AND P.CARD_DEAL_ID IN ("+ids+") GROUP BY S.NO_,CO.NAME_";
		
		return baseDao.getBaseDao().getJdbcTemplate().
				query(sql/*, new Object[]{timeType,dateSection[0],dateSection[1],groupCode}*/,
				new BeanPropertyRowMapper(TlWechatConsumAnalyze.class));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<TlWechatConsumAnalyze> getTlWechatConsumAnalyzes(String timeType ,String groupCode) {
		String sql ="SELECT * FROM tl_wechat_consum_analyze t WHERE TIME_TYPE=? AND ANALYZE_PAYDEAL_ID =?";
		return baseDao.getLogDao().getJdbcTemplate().
				query(sql,new Object[]{timeType,groupCode},new BeanPropertyRowMapper(TlWechatConsumAnalyze.class));
	}
	@Override
	public void updateTlWechatConsumAnalyzes(final List<TlWechatConsumAnalyze> list) {
		String updateSql ="update tl_wechat_consum_analyze set more_than=? where stu_id = ? and TIME_TYPE=? and ANALYZE_PAYDEAL_ID=?";
		baseDao.getLogDao().getJdbcTemplate().batchUpdate(updateSql, new BatchPreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				TlWechatConsumAnalyze ta = list.get(i);
				ps.setDouble(1,Double.parseDouble(ta.getMoreThan()));
				ps.setString(2, ta.getStuId());
				ps.setString(3, ta.getTimeType());
				ps.setString(4, ta.getAnalyzePaydealId());
			}
			@Override
			public int getBatchSize() {
				return list.size();
			}
		});
		
	}
}
