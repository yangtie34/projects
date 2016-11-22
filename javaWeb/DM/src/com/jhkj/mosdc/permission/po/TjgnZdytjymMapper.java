package com.jhkj.mosdc.permission.po;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jhkj.mosdc.permission.po.TsCdzy;
/**
 * 
 * @Comments: 输出组自定义菜单
 * Company: 河南精华科技有限公司
 * Created by gaodj(gaodongjie@126.com) 
 * @DATE:2012-12-7
 * @TIME: 下午3:36:58
 */
public class TjgnZdytjymMapper implements RowMapper {
	
	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		TsCdzy menu = new TsCdzy();
		menu.setId(rs.getLong("ID"));
		menu.setMc(rs.getString("MC"));
		menu.setCdlj(rs.getString("CDLJ") == null ? "":rs.getString("CDLJ"));
//		menu.setAnlxId(rs.getString("ANLX_ID")== null ? new Long(0):new Long(rs.getString("ANLX_ID")));
		menu.setFjdId(rs.getLong("FID"));
		menu.setCdssflId(3L);
		menu.setPxh(999L);
		return menu;
	}

}
