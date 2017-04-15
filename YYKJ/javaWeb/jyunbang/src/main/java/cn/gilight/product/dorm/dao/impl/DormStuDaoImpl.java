package cn.gilight.product.dorm.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.code.Code;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.product.dorm.dao.DormSqlUtil;
import cn.gilight.product.dorm.dao.DormStuDao;

@Repository("dormStuDao")
public class DormStuDaoImpl implements DormStuDao{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public Map<String, Object> getDormStuInfoByQuery(
			List<Map<String, Object>> query) {
		String schoolYear=EduUtils.getSchoolYearTerm(new Date())[0];
		String endYear=schoolYear.substring(schoolYear.indexOf("-")+1, schoolYear.length());
		String sql="select this_sum,all_sum,round(this_sum/decode(all_sum,0,1,all_sum) *100,2) rz_rate from "+
				"(select count(*) this_sum,'1' flag from tl_dorm_stu t where 1=1 "+DormSqlUtil.getQueryTj(query,true)+" )a "+
				"left join  "+
				"(select count(*) all_sum,'1' flag from t_stu t where t.enroll_date<=to_char(sysdate,'yyyy-mm-dd')  "+
				"and (T.ENROLL_GRADE+T.LENGTH_SCHOOLING)>='"+endYear+"' "+DormSqlUtil.getQueryTj(query,false)+" )b on a.flag=b.flag ";
		
		return baseDao.getJdbcTemplate().queryForMap(sql);
	}

	@Override
	public List<Map<String, Object>> getDormStuByQueryAndDrom(
			List<Map<String, Object>> query, Map<String, String> dorm) {
		
		String sonDorm=DormSqlUtil.getSonDorm(dorm.get("level_type"));
		if(sonDorm.equals(dorm.get("level_type"))){
			return getDormStuByQueryAndEndId(query,dorm.get("id"));
		}
		String sql="select a."+sonDorm+"_id code,a."+sonDorm+"_name name,'"+sonDorm+"' level_type,'"+
					dorm.get("level_type")+"' par_level_type,this_sum, "+
					"all_sum,round(this_sum/decode(all_sum,0,1,all_sum) *100,2) rz_rate from  "+
					"(select count(*) this_sum,"+sonDorm+"_id,"+sonDorm+"_name from tl_dorm_stu t "+
					"where 1=1 "+DormSqlUtil.getDormTj(dorm)+DormSqlUtil.getQueryTj(query,true)+" group by "+sonDorm+"_id,"+sonDorm+"_name )a "+
					"left join  "+
					"(select count(*) all_sum,"+sonDorm+"_id,"+sonDorm+"_name from tl_dorm_stu t  "+
					"where 1=1 "+DormSqlUtil.getDormTj(dorm)+" group by "+sonDorm+"_id,"+sonDorm+"_name  )b "+
					"on a."+sonDorm+"_id=b."+sonDorm+"_id order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
		
	}
	
	@Override
	public List<Map<String, Object>> getDormStuByQueryAndEndId(List<Map<String, Object>> query, String endId){
		String [] dorms=Code.getKey("dorm.level.type").split("-");
		String sql="select * from tl_dorm_stu t where t.stu_id is not null and "+
		dorms[0]+"_id='"+endId+"' "+DormSqlUtil.getQueryTj(query,true);
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	
	
	@Override
	public List<Map<String,Object>> getQueryCode(
			List<Map<String, Object>> query) {
		String[] tjs={"SEX","EDU","NJ","MZ"};
		String[] names={"性别","学历","年级","民族"};
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		for (int i = 0; i < tjs.length; i++) {
			Map<String,Object> map=new HashMap<String, Object>();
			String sql="SELECT NVL("+tjs[i]+"_ID,'') CODE,NVL("+tjs[i]+"_NAME,'未维护') NAME,COUNT(*) VALUE "+
					"FROM TL_DORM_STU T WHERE 1=1 "+DormSqlUtil.getQueryTj(query,true)+" GROUP BY "+tjs[i]+"_ID,"+tjs[i]+"_NAME ORDER BY CODE";
			map.put("queryCode",tjs[i]+"_ID");
			map.put("queryName",names[i]);
			map.put("queryValue",baseDao.getJdbcTemplate().queryForList(sql));
			list.add(map);
		}
		return list;
	}
	
}
