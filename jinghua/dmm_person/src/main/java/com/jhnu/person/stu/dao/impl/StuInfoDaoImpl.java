package com.jhnu.person.stu.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.person.stu.dao.StuInfoDao;
import com.jhnu.system.common.page.Page;
@Repository("stuInfoDao")
public class StuInfoDaoImpl implements StuInfoDao {
	@Autowired
	private BaseDao baseDao;
/**
 * 个人档案
 */
	@Override
	public Map getInfo(String id) {
		String sql = "select t.name_ cl01,t.NO_ cl02, "
				+ " tc1.name_ cl03,tc4.name_ cl04, "
				+ " tc2.name_ cl05,t.ENROLL_DATE cl06, "
				+ " t.BIRTHDAY cl07,tcdt2.NAME_ cl08, "
				+ " t.IDNO cl09,x.NAME_ cl10, "
				+ " t.PLACE_DOMICILE cl11,tct.name_ cl12, "
				+ " CASE t.MARRIED "
				+ " WHEN 1 THEN '已婚'   "
				+ " WHEN 0 THEN '未婚'   "
				+ " ELSE '未维护' END cl13,tc3.name_ cl14, "
				+ " tsc.tel cl15"
				+ " from t_stu t "
				+ " left join t_code tc1 on tc1.code_ = t.sex_code  and tc1.code_type='SEX_CODE'  "
				+ " left join t_code tc4 on tc4.code_ = t.politics_code and tc4.code_type='POLITICS_CODE' "
				+ " left join t_code tc2 on tc2.code_ = t.nation_code  and tc2.code_type='NATION_CODE' "
				+ " left join t_code_dept_teach tcdt2 on tcdt2.id=t.MAJOR_ID "
				+ " left join T_CLASSES x on x.no_=t.class_id  "
				+ " left join T_CODE_TRAINING tct on tct.code_ = t.TRAINING_ID  "
				+ " left join t_code tc3 on tc3.code_ = t.STU_ROLL_CODE  and tc3.code_type='STU_ROLL_CODE' "
				+ " left join T_STU_COMM tsc on tsc.STU_ID = t.NO_  "
				+ " where t.NO_='" + id + "'";

		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql).get(0);
	}
/**
 * 学生获得荣誉
 */
	@Override
	public List getGlory(String id) {
		//学年  学期 类型 金额 奖励日期
		String sql = "select t.SCHOOL_YEAR c1,tc1.name_ c2,tc2.name_ c3,t.MONEY c4,t.DATE_ c5 "
				+ " from T_STU_GLORY t  "
				+ " left join t_code tc1 on tc1.code_ = t.TERM_CODE  and tc1.code_type='TERM_CODE'  "
				+ " left join t_code tc2 on tc2.code_ = t.GLORY_CODE  and tc1.code_type='GLORY_CODE'  "
				+ " where t.stu_id='" + id + "' order by t.SCHOOL_YEAR,t.TERM_CODE,t.DATE_" ;
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}


	@Override
	public List OtherUser(String id) {
		String sql = "select t.* from ( "
				+ " select tea_no id,tea_no username,idno,idno||'教师' name_ from T_TEA "
				+ " union all  "
				+ " select t.no_ id,no_ username,t.idno,tc.name_  from T_STU t "
				+" left join T_CODE_EDUCATION tc on tc.CODE_=t.stu_category_id "
				+ " ) t "
				+ " inner join T_TEA tt on tt.idno=t.idno and tt.TEA_NO='" + id
				+ "' where t.username !='" + id+ "'";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List getXqzc(String id) {
		
		return null;
	}
/**
 * 学生学籍异动信息
 */
	@Override
	public List getXjyd(String id) {
		//异动日期 异动类型 异动前班级名称 异动前专业名称 异动前院系名称 异动后班级名称 异动后专业名称 异动后院系名称
		
		String sql="  SELECT DISTINCT TSC.DATE_  c1 ,tclx.name_ c2 , "
				   +" tc.name_ c3,tcdtq.name_ c4,tcdtt.name_ c5, "
				   +" tc1.name_ c6,tcdtq1.name_ c7,tcdtt1.name_ c8  "
				   +" FROM T_STU_CHANGE TSC "
				   //+" LEFT JOIN T_STU TTS ON TTS.NO_=TSC.STU_ID "
				   +" LEFT JOIN T_CLASSES tc on tc.no_ = TSC.OLD_CLASS_ID "
				   +" LEFT JOIN t_code tclx on tclx.code_ = TSC.STU_CHANGE_CODE and tclx.code_type like '%STU_CHANGE_CODE%' "
				   +" LEFT JOIN t_code_dept_teach tcdtq  ON tcdtq.id = TSC.OLD_MAJOR_ID "
				   +" LEFT JOIN t_code_dept_teach tcdtt  ON tcdtt.id=TSC.OLD_DEPT_ID "
				   +" LEFT JOIN T_CLASSES tc1 on tc1.no_ = TSC.NOW_CLASS_ID "
				   +" LEFT JOIN t_code_dept_teach tcdtq1  ON tcdtq1.id = TSC.NOW_MAJOR_ID "
				   +" LEFT JOIN t_code_dept_teach tcdtt1  ON tcdtt1.id=TSC.NOW_DEPT_ID "
				   +" WHERE TSC.STU_ID='"+id+"' order by TSC.DATE_";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
/**
 * 获得学生宿舍信息
 */
	@Override
	public Page getSsZsxx(String id, int currentPage, int numPerPage) {
		
		// 床位号  姓名  年龄 电话号码 班级 班主任 生源地
		String sql=" select distinct berth.berth_name c1,stu.name_ c2, "
		           +" floor(months_between(sysdate,to_date(substr(stu.idno,7,8), 'yyyymmdd'))/12) c3, "
		           +" TSC.TEL c4,TCB.NAME_ c5,concat(TCADSS.NAME_,TCADS.NAME_) c6, "
		           +" DIV2.NAME_||DIV1.NAME_||DIV.NAME_ c7,TDLC.NAME_ c8,TD.NAME_ c9"
		           +" from t_stu stu "
		           +" LEFT JOIN t_dorm_berth_stu berth_stu ON berth_stu.stu_id=stu.no_ "
		           +" LEFT JOIN t_dorm_berth berth ON berth.id=berth_stu.berth_id "
		           +" LEFT JOIN T_DORM TD ON TD.ID=berth.dorm_id "
		           +" LEFT JOIN T_DORM TDLC ON TDLC.ID=TD.PID  "
		           +" LEFT JOIN T_DORM TDLY ON TDLY.ID=TDLC.PID "
		           +" LEFT JOIN t_code_admini_div DIV  ON SUBSTR(STU.IDNO,0,6)=DIV.CODE_ LEFT JOIN t_code_admini_div DIV1 "
		           +" ON DIV.PID=DIV1.CODE_ LEFT JOIN t_code_admini_div DIV2 ON DIV1.PID=DIV2.CODE_ "
		           +" LEFT JOIN T_STU_COMM TSC ON TSC.STU_ID=STU.NO_ "
		           +" LEFT JOIN T_CLASSES TCB ON TCB.NO_=STU.CLASS_ID "
		           +" LEFT JOIN T_CLASSES_INSTRUCTOR TCI ON TCI.CLASS_ID=TCB.NO_ "
		           +" LEFT JOIN T_TEA TT ON TT.TEA_NO=TCI.TEA_ID "
		           +" LEFT JOIN T_CODE_ADMINI_DIV TCAD ON TCAD.ID=STU.STU_ORIGIN_ID "
		           +" LEFT JOIN T_CODE_ADMINI_DIV TCADS ON TCADS.ID=TCAD.PID "
		           +" LEFT JOIN T_CODE_ADMINI_DIV TCADSS ON TCADSS.ID=TCADS.PID "
		           +" where berth_stu.stu_id in"
		           + "(select no_ from t_stu where CLASS_ID="
		           + "(select CLASS_ID from t_stu where no_='"+id+"')"
		           + ")";
		         /*  + "berth.dorm_id = (select dorm_id  "
		           +" from t_dorm_berth "
		           +" where id = (select berth_id "
		           +" from t_dorm_berth_stu "
		           +" where stu_id = '"+id+"')) ";*/
		return new Page(sql.toString(), currentPage, numPerPage, baseDao
				.getBaseDao().getJdbcTemplate(), null);
	}
/**
 * 获得学生宿舍调整信息
 */
	@Override
	public List getSsTzxx(String id) {
		// 变动时间 变动前楼 变动前楼层 变动前寝室名  变动后所在楼 变动后所在楼层 变动后所在寝室
		String sql="SELECT TDC.CHANGE_DATE c1, "
				   +" TDLY.NAME_ c2, "
				   +" TDLC.NAME_ c3, "
				   +" TD.NAME_   c4, "
				   +" TDLYH.NAME_ c5, "
				   +" TDLCH.NAME_ c6, "
				   +" TDH.NAME_   c7 "
				   +" FROM T_DORM_CHANGE TDC "
				   //+" LEFT JOIN T_DORM_BERTH TDB ON TDB.DORM_ID=TDC.BEFORECHANGE_DORM_ID "
				   +" LEFT JOIN T_DORM TD ON TD.ID=TDC.Beforechange_Dorm_Id "
				   +" LEFT JOIN T_DORM TDLC ON TDLC.ID=TD.PID "
				   +" LEFT JOIN T_DORM TDLY ON TDLY.ID=TDLC.PID "
				   //+" LEFT JOIN T_DORM_BERTH TDBH ON TDBH.DORM_ID=TDC.AFTERCHANGE_DORM_ID "
				   +" LEFT JOIN T_DORM TDH ON TDH.ID=TDC.AFTERCHANGE_DORM_ID "
				   +" LEFT JOIN T_DORM TDLCH ON TDLCH.ID=TDH.PID "
				   +" LEFT JOIN T_DORM TDLYH ON TDLYH.ID=TDLCH.PID "
				   +" WHERE TDC.STU_ID='"+id+"' order by TDC.CHANGE_DATE";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	@Override
	public String getUserSex(String id) {
		String sql=" select tc1.name_ sex_"
				   +" from t_stu stu "
				+ "left join t_code tc1 on tc1.code_ = t.sex_code  and tc1.code_type='SEX_CODE'  "
				+ "where stu.NO_='" + id + "'";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql).get(0).get("SEX_").toString();
	}
}
