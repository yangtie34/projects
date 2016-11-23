package com.jhkj.mosdc.sc.job.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.jhkj.mosdc.framework.dao.impl.BaseDaoImpl;
import com.jhkj.mosdc.sc.job.dao.UpdateSydsxDao;

public class UpdateSydsxDaoImpl extends BaseDaoImpl implements UpdateSydsxDao {

	@Override
	public void batchUpdateSydsx(List<Map<String, String>> xslb) {
		final List<Map<String, String>> tempXslb = xslb;
		String sql = "UPDATE TB_XJDA_XJXX SET SYDSX_ID = ?,HKSZD_ID = ? WHERE XH=?";
		sql = "UPDATE TB_JZGXX SET HKSZD = ? WHERE ZGH=?";
		this.getJdbcTemplate().batchUpdate(sql,
				new BatchPreparedStatementSetter() {
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						Map<String,String> temp = tempXslb.get(i);
						String sydsxId = temp.get("SYDDM")==null?"":temp.get("SYDDM").toString();
						String xh = temp.get("XH").toString();
						ps.setString(1, sydsxId);
//						ps.setString(2, sydsxId);
						ps.setString(2, xh);
					}

					public int getBatchSize() {
						return tempXslb.size();
					}
				});
		
	}

}
