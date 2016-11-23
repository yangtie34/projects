package com.jhnu.system.common.code.dao.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.system.common.code.dao.CodeDao;
import com.jhnu.system.common.code.entity.Code;
import com.jhnu.util.common.StringUtils;

@Repository("codeDao")
public class CodeDaoImpl implements CodeDao{
	
	@Autowired
	private BaseDao baseDao;
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Code> getCode(Code code) {
		StringBuffer sql=new StringBuffer("select * from t_code where 1=1 ");
		if(code != null){
			if(StringUtils.hasLength(code.getCode_type())){
				sql.append(" and code_type= '"+code.getCode_type()+"'");
			}
			if(code.getIstrue()!=null){
				sql.append(" and istrue= "+code.getIstrue());
			}
		}
		return baseDao.getBaseDao().getJdbcTemplate().query(sql.toString(), (RowMapper) new BeanPropertyRowMapper(Code.class));
	}

}
