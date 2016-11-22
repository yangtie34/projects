package com.jhnu.product.wechat.parent.warn.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jhnu.enums.WarnLevelEnum;
import com.jhnu.enums.WarnTypeEnum;
import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.wechat.parent.warn.dao.PunishWarnDao;
import com.jhnu.product.wechat.parent.warn.entity.WechatWarn;

@Repository("punishWarnDao")
public class PunishWarnDaoImpl implements PunishWarnDao{

	@Autowired
	private BaseDao baseDao;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<WechatWarn> getPunishWarn(String exe_time, String last_time) {
		//TODO text要修改成模板，不用硬编码。
		String text = "  '你的孩子因' || cvc.name_ || ',受到学校：' || cpc.name_ || '处分' ";
		
		String sql = "SELECT sp.stu_id,'"+WarnTypeEnum.CFYJ.getCode()+"' warn_type_code, "+
				"'"+WarnLevelEnum.YZJG.getCode()+"' warn_level_code,cvc.name_ warn_why, "+
				text+" warn_text,sp.add_time warn_date,? exe_time "+
				"FROM T_STU_PUNISH sp inner join t_stu s on sp.stu_id=s.no_ "+
				"left join t_code cpc on cpc.code_=sp.punish_code and cpc.code_type = 'PUNISH_CODE' "+
				"left join t_code cvc on cvc.code_=sp.violate_code and cvc.code_type = 'VIOLATE_CODE' "+
				"where sp.add_time  between ? and ? ";

		
		return  baseDao.getBaseDao().getJdbcTemplate().query(sql,new Object[]{exe_time,last_time,exe_time}, 
						(RowMapper) new BeanPropertyRowMapper(WechatWarn.class));
	}
	
}
