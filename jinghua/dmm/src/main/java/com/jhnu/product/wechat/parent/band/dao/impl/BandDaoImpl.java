package com.jhnu.product.wechat.parent.band.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.wechat.parent.band.dao.BandDao;
import com.jhnu.product.wechat.parent.band.entity.Band;
import com.jhnu.product.wechat.parent.card.entity.TlWechatConsumAnalyze;
import com.jhnu.util.common.DateUtils;

@Repository("bandDao")
public class BandDaoImpl implements BandDao{

	@Autowired
	private BaseDao baseDao;
	
	
	@Override
	public int addBand(final Band band) {
		final String sql = "insert into t_wechat_band(id,wechat_no,stu_id,band_time,stu_name,stu_idno) values(ID_SEQ.NEXTVAL,?,?,?,?,?)";
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		int end=baseDao.getBaseDao().getJdbcTemplate().update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement psst = connection.prepareStatement(sql, new String[]{"id"});
                psst.setString(1, band.getWeChat_no());
                psst.setString(2, band.getStu_id());
                psst.setString(3, DateUtils.SSS.format(new Date()));
                psst.setString(4, band.getStu_name());
                psst.setString(5, band.getStu_idno());
                return psst;
            }
        }, keyHolder);

		band.setId(keyHolder.getKey().longValue());
		
		return end;
	}

	@Override
	public int removeBandById(String stuId) {
		String sql = "delete from t_wechat_band where stu_id=?";
		return baseDao.getBaseDao().getJdbcTemplate().update(sql, stuId);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Band> getBandByThis(Band band) {
		String sql="select * from t_wechat_band where 1=1 ";
		if(band != null){
			if(band.getId() != null){
				sql+=" and id="+band.getId();
			}
			if(band.getStu_id() != null && !band.getStu_id().equals("")){
				sql+=" and stu_id='"+band.getStu_id()+"'";
			}
			if(band.getStu_idno()!=null && !band.getStu_idno().equals("")){
				sql+=" and stu_idno = '"+band.getStu_idno()+"' ";
			}
			if(band.getStu_name()!=null && !band.getStu_name().equals("")){
				sql+=" and stu_name='"+band.getStu_name()+"' ";
			}
			if(band.getWeChat_no()!=null && !band.getWeChat_no().equals("")){
				sql+=" and wechat_no='"+band.getWeChat_no()+"' ";
			}
		}
		return baseDao.getBaseDao().getJdbcTemplate().query(sql, (RowMapper) new BeanPropertyRowMapper(Band.class));
	}

	@Override
	public void addVisitLogging(String visitMenu, int is_wechat,String visitDate, String username) {
		String sql ="INSERT INTO T_WECHAT_PARENT_LOGGING VALUES(?,?,?,?,?)";
		long id = baseDao.getBaseDao().getSeqGenerator().nextLongValue();
		baseDao.getBaseDao().getJdbcTemplate().update(sql, id,username,visitDate,visitMenu,is_wechat);
	}

}
