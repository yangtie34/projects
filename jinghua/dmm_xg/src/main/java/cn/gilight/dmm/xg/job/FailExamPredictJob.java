package cn.gilight.dmm.xg.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.xg.util.CsvUtils;
import cn.gilight.dmm.xg.util.RUtils;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.product.EduUtils;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;
@Service("failExamPredict")
public class FailExamPredictJob {
	@Resource
	private BaseDao baseDao;
	@Resource
	private HibernateDao hibernateDao;
	private Logger log = Logger.getLogger(this.getClass());
	/**
	 * 初始化挂科预测-挂科预测表中数据
	 * @return
	 * @throws Exception 
	 */
	
	@Transactional
	public JobResultBean failExamPredictData() throws Exception{
		String jobName = "向挂科预测-挂科预测表中添加数据";
		Long beginTime = System.currentTimeMillis();
		begin("#"+jobName+"#");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date=df.format(new Date());
		//定义结果
		JobResultBean result = new JobResultBean();
		String sql="INSERT INTO T_STU_SCORE_PREDICT_BEH_HIS (SELECT * FROM T_STU_SCORE_PREDICT_BEH)";
		baseDao.getJdbcTemplate().update(sql);
		sql="DELETE FROM T_STU_SCORE_PREDICT_BEH";
		baseDao.getJdbcTemplate().execute(sql);
		sql="SELECT TSSMPB.MAJOR_ID MAJORID,TSSMPB.GRADE_ID GRADEID,TSSMPB.COURSE_ID COURSEID  FROM T_STU_SCORE_MODE_PREDICT_BEH TSSMPB";
		List<Map<String,Object>> pList =baseDao.getJdbcTemplate().queryForList(sql); 
		String sql2="",majorid,gradeid,courseid;
		String stu_id,school_year,term_code;
		double predectScore,count,a,b;
		for(Map<String,Object> if_map :pList){
			majorid=if_map.get("MAJORID").toString();
			gradeid=if_map.get("GRADEID").toString();
			courseid=if_map.get("COURSEID").toString();
		//处理数据
		sql="SELECT * FROM T_STU_SCORE_MODE_PREDICT_BEH T3 WHERE T3.MAJOR_ID ='"+majorid+"' AND T3.GRADE_ID='"+gradeid+"' AND T3.COURSE_ID='"+courseid+"'  ";
		List<Map<String,Object>> mod_data=baseDao.getJdbcTemplate().queryForList(sql);
		 sql=getPridectData(majorid, gradeid, courseid);
		List<Map<String,Object>> reList=baseDao.getJdbcTemplate().queryForList(sql);
		if(reList.size()!=0&&mod_data.size()!=0){
		 for(Map<String,Object> map :reList){
			 count= Double.parseDouble(map.get("COUNT_").toString());
			 b=Double.parseDouble(mod_data.get(0).get("B_CONSTANT").toString());
			 a=Double.parseDouble(mod_data.get(0).get("A_SKIP_CLASS_COUNT").toString());;
			 predectScore=b+a*count;
			 stu_id=map.get("STU_ID").toString();
			 school_year=map.get("SCHOOL_YEAR").toString();
			 term_code=map.get("TERM_CODE").toString();
			 sql2="INSERT INTO T_STU_SCORE_PREDICT_BEH VALUES('"+stu_id+"','"+school_year+"','"
					 +term_code+"',"+predectScore+",'"+gradeid+"','"+courseid+"','"+date+"',"+count+",1)";
			 baseDao.getJdbcTemplate().update(sql2);
//			 System.out.println("=="+predectScore);
		 }
		}
		}
		if(result.getIsTrue()){
			String info = jobName+"结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			end(info);
			result.setMsg(info);
			DevUtils.p(info);
		}
		return result;
	}
	/**
	 * 建立挂科预测模型
	 * @return
	 * @throws Exception 
	 */
	
	@Transactional
	public JobResultBean failExamPredictMode() throws Exception{
		String jobName = "挂科预测模型";
		String filename=System.getProperty("webapp.root").toString()+"static\\csv\\";//+grades[2]+".csv";//
//		System.out.println(filename);
		Long beginTime = System.currentTimeMillis();
		begin("#"+jobName+"#");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date=df.format(new Date());
		//定义结果
		JobResultBean result = new JobResultBean();
		String sql1="DELETE FROM T_STU_SCORE_MODE_PREDICT_BEH";
		baseDao.delete(sql1);
		sql1="DELETE FROM T_STU_SCORE_MODE_DATA_BEH";
		baseDao.delete(sql1);
		sql1="INSERT INTO T_STU_SCORE_MODE_DATA_BEH ("+ys_sql+")";
		baseDao.getJdbcTemplate().update(sql1);
		String sql="",sql2="",majorid,gradeid,courseid;
		List<Map<String,Object>> pList =baseDao.getJdbcTemplate().queryForList(if_sql); 
		//执行sql
		for(Map<String,Object> if_map :pList){
			majorid=if_map.get("MAJORID").toString();
			gradeid=if_map.get("GRADEID").toString();
			courseid=if_map.get("COURSEID").toString();
			String fileName =filename+ majorid+"_"+gradeid+"_"+courseid+".csv";
//			File file=new File(fileName); 
//			if(!file.exists()){
			sql=getModeDataSql(majorid, gradeid, courseid);
			List<Map<String,Object>> reList=baseDao.getJdbcTemplate().queryForList(sql);
			if(reList.size()!=0){
				CsvUtils.createCsvFile(fileName,reList);
				String[] fileNames =filename.split("\\\\");
				String filenames="";
				for(int j=0;j<fileNames.length;j++){
					if(j!=fileNames.length-1)
					{
						filenames+=fileNames[j]+"\\\\";
					}else{
						filenames+=fileNames[j];
					}
				}
//				System.out.println(filenames);
				filename.replaceAll("\\\\", "\\\\\\\\");
				double[] rradio = RUtils.getEquationData((majorid+"_"+gradeid+"_"+courseid+".csv"), filenames);
				sql2="INSERT INTO T_STU_SCORE_MODE_PREDICT_BEH VALUES('"+majorid+"','"+gradeid+"','"+courseid+"',"
						+rradio[0]+","+rradio[1]+","+rradio[2]+","+rradio[3]+","+rradio[4]+","+rradio[5]+","+rradio[6]+",'"+date+"')";
				baseDao.getJdbcTemplate().update(sql2);
				System.out.println("====");
			}
		}
		if(result.getIsTrue()){
			String info = jobName+"结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			end(info);
			result.setMsg(info);
			DevUtils.p(info);
		}
		return result;
	}
	
	private void begin(String info){
		log.warn("======== begin["+DateUtils.getNowDate2()+"]: "+info+" 初始化 ========");
	}
	@SuppressWarnings("unused")
	private void info(String info){
		log.warn("======== info["+DateUtils.getNowDate2()+"]: "+info+" ========");
	}
	private void end(String info){
		log.warn("======== end["+DateUtils.getNowDate2()+"]: "+info+" ========");
	}
	@SuppressWarnings("unused")
	private void error(String info){
		log.warn("======== error["+DateUtils.getNowDate2()+"]: "+info+" 停止执行========");
	}
	@SuppressWarnings("unused")
	private int year =Integer.parseInt((EduUtils.getSchoolYear9()).split("-")[0]);
	@SuppressWarnings("unused")
	private String termCode = EduUtils.getSchoolYearTerm(DateUtils.getNowDate())[1];
	private String getPridectData(String majorid,String gradeid,String courseid){
		String yc_sql="SELECT * FROM ("
				     + " SELECT TSWSC.STU_ID,TSWSC.SCHOOL_YEAR,TSWSC.TERM_CODE,TS.MAJOR_ID MAJORID,TCA.COURSE_ID COURSEID,TSWSC.COUNT_ ,  "
		             + " (TO_CHAR(TO_DATE("+year+",'yyyy'),'yyyy')+1-TS.ENROLL_GRADE)  GRADEID  "    
		             + "         FROM (SELECT TSWSC.STU_ID,TSWSC.SCHOOL_YEAR,TSWSC.TERM_CODE,TSWSC.COURSE_ARRANGEMENT_ID,COUNT(TSWSC.STU_ID) COUNT_    "
		             + "                  FROM T_STU_WARNING_SKIP_CLASSES TSWSC   "
		             + "                  GROUP BY TSWSC.STU_ID,TSWSC.SCHOOL_YEAR,TSWSC.TERM_CODE,TSWSC.COURSE_ARRANGEMENT_ID   "
		             + "         ) TSWSC   "
		             + "         LEFT JOIN (select t.* from t_stu t  where  (t.ENROLL_GRADE <= '"+year+"' and (t.ENROLL_GRADE+t.LENGTH_SCHOOLING) > '"+year+"')) TS ON TS.NO_=TSWSC.STU_ID   " 
		             + "         LEFT JOIN T_CODE_DEPT_TEACH MAJOR_ ON MAJOR_.CODE_=TS.MAJOR_ID   "
				     + "         LEFT JOIN T_COURSE_ARRANGEMENT TCA ON TCA.ID=TSWSC.COURSE_ARRANGEMENT_ID "
		             + "   GROUP BY   TSWSC.STU_ID,TSWSC.SCHOOL_YEAR,TSWSC.TERM_CODE,TS.MAJOR_ID ,TCA.COURSE_ID ,TSWSC.COUNT_ ,   (TO_CHAR(TO_DATE("+year+",'yyyy'),'yyyy')+1-TS.ENROLL_GRADE)      "
				 	+ "       ) T3 WHERE  T3.MAJORID ='"+majorid+"' AND T3.GRADEID='"+gradeid+"' AND T3.COURSEID='"+courseid+"'  ";
		 return yc_sql;
	}
	/**
	 * 用于建模条件控制的sql语句
	 */
	//专业 年级 课程
	private String if_sql="SELECT T.MAJORID,T.GRADEID,T.COURSEID FROM ( "
					+ " SELECT TCL.TEACH_DEPT_ID MAJORID, "
					+ "        ((SUBSTR(TCAP.SCHOOL_YEAR,1,4)-TCL.GRADE)+1) GRADEID, "
					+ "        TCAP.COURSE_CODE COURSEID "
					+ " FROM T_CLASSES TCL  "
					+ " LEFT JOIN T_COURSE_ARRANGEMENT_PLAN TCAP ON TCAP.CLASS_ID=TCL.NO_ "
					+ " ) T "
					+ " WHERE (T.MAJORID IS NOT NULL) AND (T.GRADEID IS NOT NULL) AND (T.COURSEID IS NOT NULL) "
					+ " GROUP BY T.MAJORID,T.GRADEID,T.COURSEID  ORDER BY T.MAJORID"
					;
	/**
	 * 得到建模数据--目的是预测出回归方程
	 * @param majorid 专业id
	 * @param gradeid 年级id
	 * @param courseid 课程id
	 * @return
	 */
	private String getModeDataSql(String majorid,String gradeid,String courseid){
		String sql="SELECT T3.STU_ID CL01,T3.TK_COUNT CL02,T3.SCORE CL03 FROM ( T_STU_SCORE_MODE_DATA_BEH"
					+ "       ) T3 WHERE T3.SCORE IS NOT NULL AND T3.MAJOR_ID ='"+majorid+"' AND T3.GRADE_ID='"+gradeid+"' AND T3.COURSE_ID='"+courseid+"'  ";
		
		return sql;
	}
	/**
	 * 得到预测条件数据--目的是根据预测条件预测出该学生的成绩
	 * @param majorid 专业id
	 * @param gradeid 年级id
	 * @param courseid 课程id
	 * @return
	 */
//	private String getPridectDataSql(String majorid,String gradeid,String courseid){
//		String sql="SELECT T3.STU_ID CL01,T3.COUNT_ CL02,T3.CENTESIMAL_SCORE CL03 FROM ( "
//				+ " SELECT T1.*,T2.CENTESIMAL_SCORE FROM ( " 
//				+ "              SELECT TSWSC.STU_ID,TSWSC.SCHOOL_YEAR,TSWSC.TERM_CODE,TS.MAJOR_ID MAJORID,TCA.COURSE_ID COURSEID,TSWSC.COUNT_ ,  "
//				+ "              (TO_CHAR(TO_DATE("+year+",'yyyy'),'yyyy')+1-TS.ENROLL_GRADE)  GRADEID  "   
//				+ "                      FROM (SELECT TSWSC.STU_ID,TSWSC.SCHOOL_YEAR,TSWSC.TERM_CODE,TSWSC.COURSE_ARRANGEMENT_ID,COUNT(TSWSC.STU_ID) COUNT_   "
//				+ "                               FROM T_STU_WARNING_SKIP_CLASSES TSWSC   "
//				+ "                               GROUP BY TSWSC.STU_ID,TSWSC.SCHOOL_YEAR,TSWSC.TERM_CODE,TSWSC.COURSE_ARRANGEMENT_ID  "
//				+ "                      ) TSWSC   "
//				+ "                      LEFT JOIN (select t.* from t_stu t  where  (t.ENROLL_GRADE <= '"+year+"' and (t.ENROLL_GRADE+t.LENGTH_SCHOOLING) > '"+year+"')) TS ON TS.NO_=TSWSC.STU_ID  " 
//				+ "                      LEFT JOIN T_CODE_DEPT_TEACH MAJOR_ ON MAJOR_.CODE_=TS.MAJOR_ID  " 
//				+ " 		                 LEFT JOIN T_COURSE_ARRANGEMENT TCA ON TCA.ID=TSWSC.COURSE_ARRANGEMENT_ID   "
//				+ " 		                 ) T1 ,  "
//				+ " 				  (SELECT TSSDH.ID,TSSDH.STU_ID,TSSDH.SCHOOL_YEAR,TSSDH.TERM_CODE,TSSDH.COURSE_CODE,TSSDH.CENTESIMAL_SCORE,TSSDH.DATE_  "   
//				+ " 				  FROM T_STU_SCORE_HISTORY TSSDH   "
//				+ " 				  WHERE TSSDH.HIERARCHICAL_SCORE_CODE IS NULL  "             
//				+ " 				  UNION    "             
//				+ " 				  SELECT TSSDH.ID,TSSDH.STU_ID,TSSDH.SCHOOL_YEAR,TSSDH.TERM_CODE,TSSDH.COURSE_CODE,TCSH.CENTESIMAL_SCORE,TSSDH.DATE_   "
//				+ " 				  FROM  T_STU_SCORE_HISTORY TSSDH   "    
//				+ " 				  LEFT JOIN t_code_score_hierarchy  TCSH ON TCSH.CODE_=TSSDH.HIERARCHICAL_SCORE_CODE   "     
//				+ " 				  WHERE TSSDH.HIERARCHICAL_SCORE_CODE IS NOT NULL ) T2  "
//				+ "           WHERE T1.STU_ID=T2.STU_ID AND T1.COURSEID=T2.COURSE_CODE  "
//				+ "       ) T3 WHERE T3.MAJORID ='"+majorid+"' AND T3.GRADEID='"+gradeid+"' AND T3.COURSEID='"+courseid+"'  ";
//		
//		return sql;
//	}
	//T_FAIL_TABLE 表中数据
	String ys_sql=" SELECT T1.*,T2.CENTESIMAL_SCORE,"+year+" DATE_ FROM ( " 
			+ "              SELECT TSWSC.STU_ID,TSWSC.SCHOOL_YEAR,TSWSC.TERM_CODE,TS.MAJOR_ID MAJORID,TCA.COURSE_ID COURSEID,TSWSC.COUNT_ ,  "
			+ "              (TO_CHAR(TO_DATE("+year+",'yyyy'),'yyyy')+1-TS.ENROLL_GRADE)  GRADEID  "   
			+ "                      FROM (SELECT TSWSC.STU_ID,TSWSC.SCHOOL_YEAR,TSWSC.TERM_CODE,TSWSC.COURSE_ARRANGEMENT_ID,COUNT(TSWSC.STU_ID) COUNT_   "
			+ "                               FROM T_STU_WARNING_SKIP_CLASSES TSWSC   "
			+ "                               GROUP BY TSWSC.STU_ID,TSWSC.SCHOOL_YEAR,TSWSC.TERM_CODE,TSWSC.COURSE_ARRANGEMENT_ID  "
			+ "                      ) TSWSC   "
			+ "                      LEFT JOIN (select t.* from t_stu t  where  (t.ENROLL_GRADE <= '"+year+"' and (t.ENROLL_GRADE+t.LENGTH_SCHOOLING) > '"+year+"')) TS ON TS.NO_=TSWSC.STU_ID  " 
			+ "                      LEFT JOIN T_CODE_DEPT_TEACH MAJOR_ ON MAJOR_.CODE_=TS.MAJOR_ID  " 
			+ " 		                 LEFT JOIN T_COURSE_ARRANGEMENT TCA ON TCA.ID=TSWSC.COURSE_ARRANGEMENT_ID   "
			+ " 		                 ) T1 ,  "
			+ " 				  (SELECT TSSDH.ID,TSSDH.STU_ID,TSSDH.SCHOOL_YEAR,TSSDH.TERM_CODE,TSSDH.COURSE_CODE,TSSDH.CENTESIMAL_SCORE,TSSDH.DATE_  "   
			+ " 				  FROM T_STU_SCORE_HISTORY TSSDH   "
			+ " 				  WHERE TSSDH.HIERARCHICAL_SCORE_CODE IS NULL  "             
			+ " 				  UNION    "             
			+ " 				  SELECT TSSDH.ID,TSSDH.STU_ID,TSSDH.SCHOOL_YEAR,TSSDH.TERM_CODE,TSSDH.COURSE_CODE,TCSH.CENTESIMAL_SCORE,TSSDH.DATE_   "
			+ " 				  FROM  T_STU_SCORE_HISTORY TSSDH   "    
			+ " 				  LEFT JOIN t_code_score_hierarchy  TCSH ON TCSH.CODE_=TSSDH.HIERARCHICAL_SCORE_CODE   "     
			+ " 				  WHERE TSSDH.HIERARCHICAL_SCORE_CODE IS NOT NULL ) T2  "
			+ "           WHERE T1.STU_ID=T2.STU_ID AND T1.COURSEID=T2.COURSE_CODE  ";
}
