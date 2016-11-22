/**
 * DeptRowMapper.java
 * @author:党冬(mrdangdong@163.com)
 * @DATE:2012-6-10  下午8:55:47
 *  Copyright (C) 2012 topMan
 */
package com.jhkj.mosdc.permission.po;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


/**
 * 功能说明:部门树VO
 * @author: 党冬(mrdangdong@163.com)
 * @DATE:2012-6-10 @TIME: 下午8:55:47
 */
public class DeptRowMapper implements RowMapper {

	/* 
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		TbLzBm tbLzBm=new TbLzBm();
		tbLzBm.setDwdm(rs.getString("dwdm"));
		tbLzBm.setDwmc(rs.getString("dwmc"));
		tbLzBm.setLbm(rs.getString("lbm"));
		return tbLzBm;
	}

}
