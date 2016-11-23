package com.jhnu.person.tea.dao.impl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.person.tea.dao.TeaInfoDao;

@Repository("teaInfoDao")
public class TeaInfoDaoImpl implements TeaInfoDao {
	@Autowired
	private BaseDao baseDao;

	@Override
	public Map getInfo(String id) {
		String sql = "select t.name_  cl01,t.TEA_NO cl02,"
				+ "tc1.name_ cl03,tc4.name_ cl04, "
				+ "tc2.name_ cl05,t.IN_DATE cl06,"
				+ "t.BIRTHDAY cl07,t.JOIN_PARTY_DATE cl08, "
				+ "t.IDNO cl09,tcts.name_ cl10,"
				+ "t.PLACE_DOMICILE cl11,tc5.name_ cl12, "
				+ "CASE t.MARRIED "
				+ "WHEN 1 THEN '已婚'  "
				+ "WHEN 0 THEN '未婚'  "
				+ "ELSE '未维护' END  cl13,tce.name_ cl14, "
				+ "tc.TEL cl15,tcd.name_ cl16, "
				+ "tcdt.name_ cl17,tc6.name_ cl18, "
				+ "tc3.name_ cl19,	CASE t.SFSSJS "
				+ "			WHEN 1 THEN '是'   "
				+ "			WHEN 0 THEN '否'   "
				+ "			ELSE '未维护' END cl20 "
				+ "from t_tea t "
				+ "left join t_code tc1 on tc1.code_ = t.sex_code  and tc1.code_type='SEX_CODE'  "
				+ "left join t_code tc2 on tc2.code_ = t.nation_code  and tc2.code_type='NATION_CODE' "
				+ "left join T_TEA_COMM tc on tc.TEA_ID = t.TEA_NO  "
				+ "left join T_CODE_DEPT_TEACH tcdt on tcdt.code_ = t.DEPT_ID  "
				+ "left join t_code tc3 on tc3.code_ = t.BZLB_CODE  and tc3.code_type='BZLB_CODE' "
				+ "left join t_code tc4 on tc4.code_ = t.politics_code and tc4.code_type='POLITICS_CODE' "
				+ "left join T_CODE_TEA_SOURCE tcts on tcts.CODE_ = t.TEA_SOURCE_ID  "
				+ "left join t_code tc5 on tc5.code_ = t.AUTHORIZED_STRENGTH_CODE and tc5.code_type='AUTHORIZED_STRENGTH_CODE' "
				+ "left join T_CODE_EDUCATION tce on tce.CODE_ = t.EDU_ID  "
				+ "left join T_CODE_DEGREE tcd on tcd.CODE_ = t.DEGREE_ID  "
				+ "left join t_code tc6 on tc6.code_ = t.TEA_STATUS_CODE and tc6.code_type='TEA_STATE_CODE' "// --教职工状态
				+ "where t.TEA_NO='" + id + "'";

		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql).get(0);
	}

	@Override
	public Map getHisInfo(String id) {
		List list = new LinkedList();
		Map map = new LinkedHashMap();
		// 教师职称变动
		String sql = "select tc1.name_ ,t.date_ from T_TEA_ZCPD t "
				+ "left join t_code tc1 on tc1.code_ = t.ZYJSZW_CODE and tc1.code_type='ZYJSZW_CODE' "
				+ "where t.TEA_ID='" + id + "' order by t.date_ ";
		list = baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
		map.put("职称变动", list);
		// 教师岗位变动
		sql = " select tc1.name_,t.START_DATE date_ from T_TEA_PERSON_CHANGE t "
				+ "left join t_code tc1 on tc1.code_ = t.AFTER_GW_CODE and tc1.code_type='AFTER_GW_CODE' "
				+ "where t.TEA_ID='" + id + "' order by t.START_DATE";
		list = baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
		map.put("岗位变动", list);
		return map;
	}

	@Override
	public List OtherUser(String id) {
		String sql = "select t.* from ( "
				+ " select tea_no id,tea_no username,idno,idno||'教师' name_ from T_TEA "
				+ " union all  "
				+ " select t.no_ id,no_ username,t.idno,tc.name_  from T_STU t "
				+ " left join T_CODE_EDUCATION tc on tc.CODE_=t.stu_category_id "
				+ " ) t "
				+ " inner join T_STU tt on tt.idno=t.idno and tt.no_='" + id
				+ "' where t.username !='" + id+ "'";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	@Override
	public boolean isTeacher(String id) {
		String sql =" select * from T_TEA where tea_no='" + id+ "'";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql).size()>0;
	}
	@Override
	public String getUserSex(String id) {
		String sql=" select tc1.name_ sex_ "
				+ "from t_tea t "
				+ "left join t_code tc1 on tc1.code_ = t.sex_code  and tc1.code_type='SEX_CODE'  "
				+ "where t.TEA_NO='" + id + "'";
		List<Map<String, Object>> list=baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
		if(list.size()==0) return "";
		return list.get(0).get("SEX_").toString();
	}

}
