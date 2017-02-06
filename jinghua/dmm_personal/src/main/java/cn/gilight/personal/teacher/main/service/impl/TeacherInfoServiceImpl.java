package cn.gilight.personal.teacher.main.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.personal.teacher.main.service.TeacherInfoService;

@Service("teacherInfoService")
public class TeacherInfoServiceImpl implements TeacherInfoService {
	
	@Resource
	private BaseDao baseDao;
	@Resource
	private HibernateDao hibernate;
	
	/* (non-Javadoc)
	 * @see cn.gilight.personal.teacher.main.service.impl.TeacherInfoService#getTeacherSimpleInfo(java.lang.String)
	 */
	@Override
	public Map<String, Object> getTeacherSimpleInfo(String teano){
		String sql = "SELECT t.id id,w.wechat_head_img headimg,t.tea_no tno,t.name_ name, dept.name_ dept, sex.name_ gender from T_TEA t "
				+ " left join t_wechat_bind w on w.username = t.tea_no"
				+ " left join T_CODE_DEPT dept"
				+ " on t.dept_id = dept.id"
				+ " left join t_code sex"
				+ " on sex.code_ = t.sex_code and sex.code_type = 'SEX_CODE' WHERE T.tea_no = '" + teano + "'";
		List<Map<String, Object>> userlist = baseDao.queryListInLowerKey(sql);
		if (userlist.size() > 0) {
			return userlist.get(0);
		} else 
			return null;
	}
	
	/* (non-Javadoc)
	 * @see cn.gilight.personal.teacher.main.service.impl.TeacherInfoService#getTeacherDetailInfo(java.lang.String)
	 */
	@Override
	public Map<String, Object> getTeacherDetailInfo(String teano){
		String sql = "select t.id, t.tea_no,       t.name_ name,       t.idno, t.birthday,"
				+ " dept.name_ dept,sex.name_ gender,nation.name_ nation,t.married,xl.name_ edu,"
				+ " xw.name_ degree,cad.name_ place_domicile,t.join_party_date, zzmm.name_ politics,"
				+ " t.in_date,  jzglb.name_ jzglb, jzgly.name_ jzgly, bzlb.name_ bzlb, zyjszw.name_ zyjszw,"
				+ " grjsdj.name_ grjsdj, t.teaching_date, jzgzt.name_ jzgzt,case when sfssjs = 0 then '不是' when sfssjs = 1 then '是' end sfssjs,wechat.wechat_head_img headimg,"
				+ " TO_CHAR(SYSDATE,'YYYY') - SUBSTR(T.IN_DATE,0,4) RXSJ "
				+ "from t_tea t"
				+ "  left join T_CODE_DEPT dept on dept.id = t.dept_id"
				+ "  left join t_code_admini_div cad on cad.code_ = t.domicile_id"
				+ "  left join t_code sex on sex.code_ = t.sex_code and sex.code_type = 'SEX_CODE'"
				+ "  left join t_code nation on nation.code_ = t.nation_code and nation.code_type = 'NATION_CODE'"
				+ "  left join T_CODE_EDUCATION xl on xl.code_ = t.edu_id"
				+ "  left join t_code_degree xw on xw.code_ = t.degree_id"
				+ "  left join t_code zzmm on zzmm.code_ = t.politics_code and zzmm.code_type = 'POLITICS_CODE'"
				+ "  left join T_CODE_AUTHORIZED_STRENGTH jzglb on jzglb.id = t.authorized_strength_id "
				+ "  left join t_code bzlb on bzlb.code_ = t.bzlb_code and bzlb.code_type = 'BZLB_CODE'"
				+ "  left join t_code_tea_source jzgly on jzgly.id = t.tea_source_id"
				+ "  left join t_code_zyjszw zyjszw on zyjszw.id = t.zyjszw_id"
				+ "  left join t_code grjsdj on grjsdj.code_ = t.skill_moves_code and grjsdj.code_type = 'SKILL_MOVES_CODE'"
				+ "  left join t_code jzgzt on jzgzt.code_ = t.tea_status_code and jzgzt.code_type = 'TEA_STATUS_CODE'"
				+ "  left join t_wechat_bind wechat on wechat.username = t.tea_no"
				+ " where t.tea_no = '"+ teano + "'";
		List<Map<String, Object>> userlist = baseDao.queryListInLowerKey(sql);
		if (userlist.size() > 0) {
			return userlist.get(0);
		} else
			return null;
	}

	@Override
	public List<Map<String, Object>> queryTeacherHistoryList(String username) {
		//详细信息
		String sql = "SELECT T.TEA_NO,T.YEAR,DATETIME,T.CONTENTS,T.INFO_SOURCE FROM T_TEA_HISTORY T WHERE T.TEA_NO = '"+username+"' ORDER BY T.DATETIME DESC ";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Map<String, Object> queryTeacherHistoryInfo(String username) {
		String sql = "SELECT T.IN_DATE INDATE,TO_CHAR(SYSDATE,'YYYY')-SUBSTR(T.IN_DATE,0,4) YEARS "
				+ " FROM T_TEA T WHERE T.TEA_NO = '"+username+"'";
		List<Map<String, Object>> result = baseDao.queryListInLowerKey(sql);
		if (result.size() > 0) return result.get(0);
		else return null;
	}

	@Override
	public void submitAdvice(String username, String advice) {
		String time = DateUtils.getCurrentTime();
		String sql = "insert into t_sys_feedback(id,username,advice,create_time) values(ID_SEQ.NEXTVAL,'"+username+"','"+advice+"','"+time+"')";
		baseDao.insert(sql);
	}
}