package cn.gilight.product.dorm.dao.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.code.Code;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.product.dorm.dao.DormJobDao;

@Repository("dormJobDao")
public class DormJobDaoImpl implements DormJobDao{

	@Autowired
	private BaseDao baseDao;
	
	@Override
	public int dormStuJob() {
		String delSql="DELETE TL_DORM_STU";
		baseDao.update(delSql);
		
		String[] levelTypes=Code.getKey("dorm.level.type").split("-");
	//	String[] allTypes={"QS","LC","LY","XQ","XX"};
		String levelTJ="";
		String title="";
		String fastOne="";
		for (int i = 0; i < levelTypes.length; i++) {
			if(i==0){
				fastOne=" B."+levelTypes[i]+"_ID ";
			}
			levelTJ+="regexp_substr(code_path,'[^/]+[A-Za-z0-9_]*',1,"+(i+1)+") "+levelTypes[i]+"_ID, ";
			levelTJ+="regexp_substr(name_path,'[^/]+[A-Za-z0-9_]*',1,"+(i+1)+") "+levelTypes[i]+"_NAME, ";
			if(i==levelTypes.length-1){
				title+=" B."+levelTypes[i]+"_ID, ";
				title+=" B."+levelTypes[i]+"_NAME ";
			}else{
				title+=" B."+levelTypes[i]+"_ID, ";
				title+=" B."+levelTypes[i]+"_NAME, ";
			}
		}
//		int j=0;
//		for (int i = 0; i <allTypes.length; i++) {
//			if(levelTypes[j].equals(allTypes[i])){
//				levelTJ+="regexp_substr(code_path,'[^/]+[A-Za-z0-9_]*',1,"+(i+1)+") "+allTypes[i]+"_ID, ";
//				levelTJ+="regexp_substr(name_path,'[^/]+[A-Za-z0-9_]*',1,"+(i+1)+") "+allTypes[i]+"_NAME, ";
//				j++;
//			}else{
//				levelTJ+="'' "+allTypes[i]+"_ID, ";
//				levelTJ+="'' "+allTypes[i]+"_NAME, ";
//			}
//		}
		String schoolYear=EduUtils.getSchoolYearTerm(new Date())[0];
		String endYear=schoolYear.substring(schoolYear.indexOf("-")+1, schoolYear.length());
		String sql="INSERT INTO  TL_DORM_STU                                                                                            "+
				"select S.NO_ STU_ID,S.NAME_ STU_NAME,S.SEX_CODE SEX_ID,TC.NAME_ SEX_NAME,S.DEPT_ID,CD.NAME_ DEPT_NAME,S.MAJOR_ID,             "+
				"CDT.NAME_ MAJOR_NAME,S.CLASS_ID,CL.NAME_ CLASS_NAME,S.EDU_ID,CE.NAME_ EDU_NAME,S.NATION_CODE MZ_ID,MZ.NAME_ MZ_NAME, "+
				"("+endYear+"-S.ENROLL_GRADE) NJ_ID,("+endYear+"-S.ENROLL_GRADE)||'年级' NJ_NAME,(S.ENROLL_GRADE+S.LENGTH_SCHOOLING) END_YEAR,DB.ID BERTH_ID,             "+
				"DB.BERTH_NAME, "+title+
				"from T_DORM d inner join T_DORM_BERTH db on d.id=db.dorm_id                                                            "+
				"left join T_DORM_BERTH_STU dbs on db.id= dbs.berth_id                                                                  "+
				"left join t_stu s on dbs.stu_id=s.no_                                                                                  "+
				"left join t_code_dept_teach cd on s.dept_id =cd.id                                                                     "+
				" left join t_code_dept_teach cdt on s.major_id=cdt.id                                                                  "+
				" left join t_code_education ce on s.edu_id=ce.id                                                                       "+
				" left join t_code tc on s.sex_code=tc.code_ and tc.code_type='SEX_CODE'                                                "+
				" left join t_code mz on s.nation_code=mz.code_ and mz.code_type='NATION_CODE'                                          "+
				" left join t_classes cl on s.class_id=cl.no_                                                                           "+
				"left join  (  select       "+levelTJ+
				"      '1' LEVEL_TYPE_TEMP                                                       "+
				"    from (  select SYS_CONNECT_BY_PATH(a.name_, '/')  name_path ,                                                      "+
				"                  SYS_CONNECT_BY_PATH(a.id, '/') code_path                           "+
				"             from T_DORM a                                                                                             "+
				"            where a.level_type='"+levelTypes[levelTypes.length-1]+"'                                                                                    "+
				"            start with a.level_type='"+levelTypes[0]+"'                                                                               "+
				"       connect by prior a.pid = a.id ) tt ) b on "+fastOne+"=d.id                                                          ";
		return baseDao.getJdbcTemplate().update(sql);
	}

	@Override
	public int dormRkeUsedStuJob(String yearMonth) {
		String delSql="DELETE TL_DORM_RKE_USED_STU WHERE YEAR_MONTH= TO_DATE('"+yearMonth+"','YYYY-MM') ";
		baseDao.update(delSql);
		
		String sql="INSERT INTO  TL_DORM_RKE_USED_STU "+
					"select TO_DATE('"+yearMonth+"','YYYY-MM') year_month,nvl(rke_num,0) rke_num ,s.*  "+
					"from (select dp.people_id,count(*) rke_num from t_dorm_rke dr  "+
					"inner join t_dorm_proof dp on dr.dorm_proof_id=dp.no_ "+
					"where substr(dr.time_,0,7)='"+yearMonth+"' "+
					"group by dp.people_id ) a "+
					"right join tl_dorm_stu s on a.people_id=s.stu_id "+
					"where s.stu_id is not null ";
		return baseDao.getJdbcTemplate().update(sql);
	}

	@Override
	public int dormTrend(String yearMonth) {
		
		int all=0;
		
		String delSql="DELETE TL_DORM_TREND WHERE YEAR_MONTH= TO_DATE('"+yearMonth+"','YYYY-MM') ";
		baseDao.update(delSql);
		
		String sql="insert into TL_DORM_TREND "+
					"select to_date('"+yearMonth+"','YYYY-MM') year_month,b.dept_id,b.major_id,'' type_code,'' type_name, "+
					"'use_all' flag,nvl(a.nums,0) use_Num,DECODE(b.nums, 0, 1, b.nums) all_Num "+
					"from (select count(*) nums,t.dept_id,t.major_id "+
					"from tl_dorm_rke_USED_stu t "+
					"where t.rke_count >="+Code.getKey("dorm.uesd")+" and t.year_month=to_date('"+yearMonth+"','YYYY-MM') "+
					"group by t.dept_id,t.major_id ) a "+
					"right join (select count(*) nums,dept_id,major_id "+
					"from (select * from tl_dorm_stu where stu_id is not null) "+
					"group by dept_id,major_id ) b "+
					"on a.dept_id=b.dept_id and a.major_id=b.major_id ";
		 all+=baseDao.getJdbcTemplate().update(sql);
		 
		 sql="insert into TL_DORM_TREND "+
				"select to_date('"+yearMonth+"','YYYY-MM') year_month,b.dept_id,b.major_id,b.sex_id,b.sex_name, "+
				"'use_sex' flag,nvl(a.nums,0) use_Num,DECODE(b.nums, 0, 1, b.nums) all_Num "+
				"from (select count(*) nums,t.dept_id,t.major_id, sex_id, sex_name "+
				"from tl_dorm_rke_USED_stu t "+
				"where t.rke_count >="+Code.getKey("dorm.uesd")+" and t.year_month=to_date('"+yearMonth+"','YYYY-MM') "+
				"group by t.dept_id,t.major_id,t.sex_id, t.sex_name ) a "+
				"right join (select count(*) nums,dept_id,major_id, sex_id, sex_name "+
				"from (select * from tl_dorm_stu where stu_id is not null) "+
				"group by dept_id,major_id,sex_id, sex_name) b "+
				"on a.dept_id=b.dept_id and a.major_id=b.major_id and a.sex_id = b.sex_id";
		 all+=baseDao.getJdbcTemplate().update(sql);
		 
		 sql="insert into TL_DORM_TREND "+
				"select to_date('"+yearMonth+"','YYYY-MM') year_month,b.dept_id,b.major_id,b.edu_id,b.edu_name, "+
				"'use_edu' flag,nvl(a.nums,0) use_Num,DECODE(b.nums, 0, 1, b.nums) all_Num "+
				"from (select count(*) nums,t.dept_id,t.major_id, edu_id, edu_name "+
				"from tl_dorm_rke_USED_stu t "+
				"where t.rke_count >="+Code.getKey("dorm.uesd")+" and t.year_month=to_date('"+yearMonth+"','YYYY-MM') "+
				"group by t.dept_id,t.major_id,t.edu_id, t.edu_name ) a "+
				"right join (select count(*) nums,dept_id,major_id, edu_id, edu_name "+
				"from (select * from tl_dorm_stu where stu_id is not null) "+
				"group by dept_id,major_id,edu_id, edu_name) b "+
				"on a.dept_id=b.dept_id and a.major_id=b.major_id and a.edu_id = b.edu_id";
		 all+=baseDao.getJdbcTemplate().update(sql);
		 
		return all;
	}

}
