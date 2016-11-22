package cn.gilight.product.dorm.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.product.dorm.dao.DormEmployeeDao;
import cn.gilight.product.dorm.dao.DormSqlUtil;

import com.jhnu.syspermiss.school.entity.Dept;
@Repository("dormEmployeeDao")
public class DormEmployeeDaoImpl implements  DormEmployeeDao{
	
	@Autowired
	private BaseDao baseDao;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Dept> GetDormTree() {
		String sql="SELECT * FROM T_DORM WHERE LEVEL_TYPE<>'QS' ORDER BY LEVEL_";
		return baseDao.getJdbcTemplate().query(sql, new Object[] {},(RowMapper) new BeanPropertyRowMapper(Dept.class));
	}

	@Override
	public Map<String, Object> getDormInfo(Map<String, String> dorm) {
		String sql="select nvl(count(distinct ly_id),0) LY_SUM,nvl(count(distinct qs_id),0) QS_SUM,nvl(count(distinct berth_id),0) CW_SUM, "+
					"nvl(count(case when stu_id is null then '1' else null end ),0) KCW_SUM , "+
					"round(nvl((count(distinct berth_id)-count(case when stu_id is null then '1'  "+
					"else null end )),0)/decode(count(distinct berth_id),0,1,count(distinct berth_id))*100,2) RZ_RATE "+
					"from tl_dorm_stu t where 1=1 "+DormSqlUtil.getDormTj(dorm);
		return baseDao.getJdbcTemplate().queryForMap(sql);
	}

	@Override
	public List<Map<String, Object>> getDormByType(Map<String, String> dorm) {
		String sonDorm=DormSqlUtil.getSonDorm(dorm.get("level_type"));
		String sql="select "+sonDorm+"_id code,"+sonDorm+"_name name,nvl(count(distinct berth_id),0) CW_SUM, "+
					"nvl(count(case when stu_id is null then '1' else null end ),0) KCW_SUM , "+
					"round(nvl((count(distinct berth_id)-count(case when stu_id is null then '1'  "+
					"else null end )),0)/decode(count(distinct berth_id),0,1,count(distinct berth_id))*100,2) RZ_RATE "+
					"from tl_dorm_stu t where 1=1 "+DormSqlUtil.getDormTj(dorm)+" group by "+sonDorm+"_id,"+sonDorm+"_name order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getDormByNews(Map<String, String> dorm) {
		String schoolYear=EduUtils.getSchoolYearTerm(new Date())[0];
		String endYear=schoolYear.substring(schoolYear.indexOf("-")+1, schoolYear.length());
		String sonDorm=DormSqlUtil.getSonDorm(dorm.get("level_type"));
		String sql="select "+sonDorm+"_id code,"+sonDorm+"_name name,nvl(count(distinct berth_id),0) CW_SUM "+
					"from tl_dorm_stu t where  1=1 "+DormSqlUtil.getDormTj(dorm)+
					"and ( t.end_year <= '"+endYear+"' OR T.STU_ID IS NULL ) group by "+sonDorm+"_id,"+sonDorm+"_name order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getDormByStuType(Map<String, String> dorm,
			String stuType) {
		String sql="select nvl(t."+stuType+"_id,'') code,nvl(t."+stuType+"_name,'未维护') name,count(distinct stu_id) value "+
					"from tl_dorm_stu t where  1=1 "+DormSqlUtil.getDormTj(dorm)+
					"and t.stu_id is not null group by t."+stuType+"_id,t."+stuType+"_name order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public Page getDormTopByGroup(int currentPage,int numPerPage,int totalRow,Map<String, String> dorm, String type) {
		String sql="select nvl(count(distinct berth_id),0) CW_SUM , "+ 
					"nvl(count(case when stu_id is null then '1' else null end ),0) KCW_SUM ,"+
					type+"_id code,"+type+"_name name from tl_dorm_stu t where 1=1 "+DormSqlUtil.getDormTj(dorm)+
					" group by "+type+"_id,"+type+"_name order by "+type+"_id ";
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public Page getDormTopPage(int currentPage,int numPerPage,int totalRow,Map<String, String> dorm, String type,
			String id) {
		String schoolYear=EduUtils.getSchoolYearTerm(new Date())[0];
		String endYear=schoolYear.substring(schoolYear.indexOf("-")+1, schoolYear.length());
		String sonDorm=DormSqlUtil.getSonDorm(dorm.get("level_type"));
		String tj="";
		switch (type) {
		case "CW":
			break;
		case "KCW":
			tj="and t.stu_id is null ";
			break;
		case "NEWS":
			tj="and ( t.end_year <= '"+endYear+"' OR T.STU_ID IS NULL ) and t."+sonDorm+"_id='"+id+"' ";
			break;
		case "DORM_RZ":
			tj="and t.stu_id is not null and t."+sonDorm+"_id='"+id+"' ";
			break;
		case "DORM_WRZ":
			tj="and t.stu_id is null and t."+sonDorm+"_id='"+id+"' ";
			break;
		default:
			if("".equals(id)){
				tj="and t."+type+"_id is null and t.stu_id is not null ";
			}else{
				tj="and t."+type+"_id='"+id+"' ";
			}
			break;
		}
		
		String sql="select * from tl_dorm_stu t where 1=1 "+DormSqlUtil.getDormTj(dorm)+tj;
		
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

}
