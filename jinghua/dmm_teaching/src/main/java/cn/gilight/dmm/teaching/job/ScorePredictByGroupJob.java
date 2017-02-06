package cn.gilight.dmm.teaching.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;

import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.teaching.entity.TStuScorepredTerm;
import cn.gilight.dmm.teaching.entity.TStuScorepredTermGpCj;
import cn.gilight.dmm.teaching.entity.TStuScorepredTermGpMd;
import cn.gilight.dmm.teaching.entity.TStuScorepredTermGroup;
import cn.gilight.dmm.teaching.entity.TStuScorepredTermHis;
import cn.gilight.dmm.teaching.service.ScorePredictGroupService;
import cn.gilight.dmm.teaching.util.CsvUtils;
import cn.gilight.dmm.teaching.util.RUtils;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.product.EduUtils;

/**
 * 该job是用于四种模型方法对成绩进行预测并存储
 * @author lijun
 *
 */
@Service("scorePredictByGroupJob")
public class ScorePredictByGroupJob {

	@Resource
	private BaseDao baseDao;
	@Resource
	private ScorePredictGroupService scorePredictGroupService;
	@Resource
	private HibernateDao hibernateDao;
	private Logger log = Logger.getLogger(this.getClass());
//	private String filename="E:\\predictData1\\";//+grades[2]+".csv";//
	private String filename=System.getProperty("webapp.root").toString()+"static\\predictCsv\\";//+grades[2]+".csv";//
	private String filenames= getFileName();
	/**
	 * 预测成绩并存储
	 * @param start_schoolYear 开始学年
	 * @param start_termCode   开始学期
	 * @param end_schoolYear   结束学年
	 * @param end_termCode     结束学期
	 */
	public void predictGroupScoreAndSave(String start_schoolYear, String start_termCode,
			String end_schoolYear, String end_termCode){
//		Map<String,Object> qzGroupMap=scorePredictGroupService.getRealGroup(start_schoolYear, start_termCode, end_schoolYear, end_termCode, yc_start_schoolYear, yc_start_termCode, yc_end_schoolYear, yc_end_termCode);//得到前年真分组List
////		 Map<String, Object> lastWGroupMap = scorePredictDataService.getFakeGroup(start_schoolYear, start_termCode, end_schoolYear, end_termCode, qzGroup);//根据前年发真分组，得到去年的伪分组
//		for(int i=0;i<qzGroupList.size();i++){
//	    int predictkcNumber=0;
//		TStuScorepredTermGroup qzGroup=qzGroupList.get(i);//得到前年真分组
////		String groupId=qzGroup.getId();//得到分组id
//		Integer gradeid=qzGroup.getGrade();//得到年级
//		String trainingCSV=filename+qzGroup.getId()+"_training.csv";//前年训练集CSV文件 
//		String testCsv=filename+qzGroup.getId()+"_test.csv";//去年训练集CSV文件 
//		List<TStuScorepredTermGpMd> groupMoldList = qzGroup.getList();//得到该分组的课程信息
//		List<String> csvTitleList=new ArrayList<>();//目的是得到CSV文件标题信息（第一行）
//		csvTitleList.add("studentid");
//		for(TStuScorepredTermGpMd groupMold:groupMoldList){
//			csvTitleList.add("c"+groupMold.getCourseId());//向CSV文件第一行添加课程列
//			if(groupMold.getIspredict()==1){predictkcNumber++;}//计算要预测的课程数
//		}
//		//CSV数据待处理
//		List<List<String>> qTrainingList = scorePredictGroupService.getTrainingData(qzGroup);//获得前年的训练集List<Map<String,Object>>
//		CsvUtils.createCsvFileByList(trainingCSV,csvTitleList, qTrainingList);//得到前年训练集的CSV文件（需要从写Csv方法，此方法不通）
//		List<List<String>> lastTestList = scorePredictGroupService.getTestData(qzGroup);//得到去年的测试集List<Map<String,Object>>
//		CsvUtils.createCsvFileByList(testCsv,csvTitleList, lastTestList);//得到前年训练集的CSV文件（需要从写Csv方法，此方法不通）
//		List<Map<String,Object>> predictScoreList=RUtils.predictModeFunction(trainingCSV, testCsv, filenames, predictkcNumber,false);
//		try {
//			/**
//			 * 将用四种成绩预测的学生成绩存储到T_STU_SCOREPRED_TERM_GP_CJ表中
//			 */
//			savePredictMoldData(predictScoreList, groupMoldList, gradeid);
//		} catch (SecurityException | NoSuchFieldException e) {
//			e.printStackTrace();
//		}
//		}
		
	}
	/**********************************************************************************************************************************/
	/**
	 * 判断最优模型并回写到T_STU_SCOREPRED_TERM_GP_MD表中
	 * @param start_schoolYear 开始学年
	 * @param start_termCode   开始学期
	 * @param end_schoolYear   结束学年
	 * @param end_termCode     结束学期
	 * @return List<TStuScorepredTermGroup> 返回伪分组（合并有模型和没有模型的分组之后返回）
	 */
	@Transactional
	public List<TStuScorepredTermGroup> chooseBestMode(String start_schoolYear, String start_termCode,
			String end_schoolYear, String end_termCode,String yc_start_schoolYear,
			String yc_start_termCode,String yc_end_schoolYear,String yc_end_termCode){
		Map<String, Object> qzGroupMap = scorePredictGroupService.getRealGroup(start_schoolYear, start_termCode, end_schoolYear, end_termCode, yc_start_schoolYear, yc_start_termCode, yc_end_schoolYear, yc_end_termCode);//得到去年真分组List
		List<TStuScorepredTermGroup> qzGroupList = (List<TStuScorepredTermGroup>) qzGroupMap.get("isHg");//(去年真分组)
		//		List<TStuScorepredTermGroup> qzGroupList=getReal();//得到前年真分组List(测试)
		 Map<String, Object> qwGroupList = scorePredictGroupService.getFakeGroup(start_schoolYear, start_termCode, end_schoolYear, end_termCode, qzGroupList);//得到去年真分组List
		 List<TStuScorepredTermGroup> qwHasModeList = (List<TStuScorepredTermGroup>) qwGroupList.get("hasMode");//得到去年伪分组的有模型的list
//		 List<TStuScorepredTermGroup> qwHasModeList = getFake();//(测试)
		for(int i=0;i<qzGroupList.size();i++){
	    int predictkcNumber=0;
		TStuScorepredTermGroup qzGroup=qzGroupList.get(i);//得到前年真分组
		String trainingCSV=qzGroup.getId()+"_training.csv";//前年训练集CSV文件 
		String testCsv=qzGroup.getId()+"_test.csv";//去年测试集CSV文件 （包括去年所需预测课程的真成绩）
		List<TStuScorepredTermGpMd> groupMoldList = qzGroup.getList();//得到该分组的课程信息
		List<String> csvTitleList=new ArrayList<>();//目的是得到CSV文件标题信息（第一行）
		csvTitleList.add("studentid");
		for(TStuScorepredTermGpMd groupMold:groupMoldList){
			csvTitleList.add("c"+groupMold.getCourseId());//向CSV文件第一行添加课程列
			if(groupMold.getIspredict()==1){predictkcNumber++;}//计算要预测的课程数
		}
		//CSV数据待处理
		List<List<String>> qTrainingList = scorePredictGroupService.getTrainingData(qzGroup);//获得前年的训练集List<Map<String,Object>>
		updateStuId(qTrainingList);//在生成csv文件之前，在学号字段前加个字符串"s"
		CsvUtils.createCsvFileByList(filename+trainingCSV,csvTitleList, qTrainingList);//得到前年训练集的CSV文件（需要从写Csv方法，此方法不通）
		List<List<String>> lastTestList = scorePredictGroupService.getScoreData(qwHasModeList.get(i));//得到去年的测试集List<Map<String,Object>>
		updateStuId(lastTestList);//在生成csv文件之前，在学号字段前加个字符串"s"
		CsvUtils.createCsvFileByList(filename+testCsv,csvTitleList, lastTestList);//得到前年训练集的CSV文件（需要从写Csv方法，此方法不通）
		List<Map<String,Object>> bestMoldList = RUtils.predictModeFunction(trainingCSV, testCsv, filenames, predictkcNumber,true);
		try {
			/**
			 * 得到最优模型并更新到T_STU_SCOREPRED_TERM_GP_MD表中
			 */
			if("noPredict".equals(bestMoldList.get(0).get("exception"))){
				for(TStuScorepredTermGpMd groupMold:groupMoldList){
					if((groupMold.getIssave()).equals(1)){
						groupMold.setMoldId("X");
					}
				}
			}else{
			saveBestMold(bestMoldList, groupMoldList);//将选出的最优模型放到TStuScorepredTermGpMd中
			}
			scorePredictGroupService.saveBestMode(qzGroup);//更新到数据库
		} catch (SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
		}
		/**
		 * 去年无法得到最优模型的分组，直接随机一个模型ID保存到库
		 */
//		List<TStuScorepredTermGroup> lastNoModeGroupList = (List<TStuScorepredTermGroup>) qwGroupList.get("noMode");//得到去年没有模型的分组
//		for(TStuScorepredTermGroup noModeGroup:lastNoModeGroupList){
//			for(TStuScorepredTermGpMd groupMold:noModeGroup.getList()){
//				groupMold.setMoldId(String.valueOf(((int)(Math.random()*3))));//随机一个模型插入到分组模型表
//			}
//			scorePredictGroupService.saveBestMode(noModeGroup);//插入到数据库
//		}
//		qwHasModeList.addAll(lastNoModeGroupList);//合并去年的两个分组，现在去年的伪分组为lastHasModeGroupList
		return 	qwHasModeList;
	}
	/**
	 * 预测所需学年的成绩
	 * @param start_schoolYear 开始学年
	 * @param start_termCode   开始学期
	 * @param end_schoolYear   结束学年
	 * @param end_termCode     结束学期
	 * @param lastHasModeGroupList 传入去年的伪分组
	 */
	@Transactional
	public void predictScoreAndSave(String start_schoolYear, String start_termCode,
			String end_schoolYear, String end_termCode,List<TStuScorepredTermGroup> lastHasModeGroupList){
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date=DateUtils.getNowDate();
//		List<TStuScorepredTermGroup> qzGroupList=scorePredictGroupService.getRealGroup(start_schoolYear, start_termCode, end_schoolYear, end_termCode);//得到前年真分组List
//		Map<String,Object> qwGroupMap=scorePredictGroupService.getFakeGroup(start_schoolYear, start_termCode, end_schoolYear, end_termCode, qzGroupList);//由前年的真分组得到去年伪分组List
//		List<TStuScorepredTermGroup> lastNoModeGroupList = (List<TStuScorepredTermGroup>) qwGroupMap.get("noMode");//得到去年没有模型的分组
//		List<TStuScorepredTermGroup> lastHasModeGroupList = (List<TStuScorepredTermGroup>) qwGroupMap.get("hasMode");//得到去年没有模型的分组
//		for(TStuScorepredTermGroup noModeGroup:lastNoModeGroupList){
//			for(TStuScorepredTermGpMd groupMold:noModeGroup.getList()){
//				groupMold.setMoldId(String.valueOf(((int)(Math.random()*3))));//随机一个模型插入到分组模型表
//			}
//			scorePredictGroupService.saveBestMode(noModeGroup);//插入到数据库
//		}
//		 lastHasModeGroupList.addAll(lastNoModeGroupList);//合并去年的两个分组，现在去年的伪分组为lastHasModeGroupList
//		Map<String,Object> currentYearwGroupMap=scorePredictGroupService.getFakeGroup(start_schoolYear, start_termCode, end_schoolYear, end_termCode, lastHasModeGroupList);//得到今年的伪分组
//		List<TStuScorepredTermGroup> currenthasModeGroupList= (List<TStuScorepredTermGroup>) currentYearwGroupMap.get("hasMode");	
		List<TStuScorepredTermGroup> currenthasModeGroupList= getFake(); 
		//删掉T_STU_SCOREPRED_TERM表中对当前学年学期的预测
		end_schoolYear="2015-2016";end_termCode="02";
		String sql="DELETE FROM T_STU_SCOREPRED_TERM TSST WHERE TSST.SCHOOL_YEAR='"+end_schoolYear+"' AND TSST.TERM_CODE='"+end_termCode+"'";
		baseDao.getJdbcTemplate().execute(sql);
		for(TStuScorepredTermGroup currentHasModeGroup:currenthasModeGroupList){//根据去年伪分组进行预测
		    int predictkcNumber=0,qwIndex=0;
			String trainingCSV=currentHasModeGroup.getId()+"_training.csv";//去年训练集CSV文件 
			String testCsv=currentHasModeGroup.getId()+"_test.csv";//今年测试集CSV文件 
			Integer gradeId=currentHasModeGroup.getGrade();//得到年级
			List<TStuScorepredTermGpMd> groupMoldList = currentHasModeGroup.getList();//得到该分组的课程信息
			List<String> csvTitleList=new ArrayList<>();//目的是得到CSV文件标题信息（第一行）
			csvTitleList.add("studentid");
			for(TStuScorepredTermGpMd groupMold:groupMoldList){
				csvTitleList.add("c"+groupMold.getCourseId());//向CSV文件第一行添加课程列
				if(groupMold.getIspredict()==1){predictkcNumber++;}//计算要预测的课程数
			}
			//CSV数据待处理
			List<List<String>> qTrainingList = scorePredictGroupService.getTrainingData(lastHasModeGroupList.get(qwIndex));qwIndex++;//获得去年的训练集List<Map<String,Object>>
			updateStuId(qTrainingList);//在生成csv文件之前，在学号字段前加个字符串"s"
//			CsvUtils.createCsvFileByList(filename+trainingCSV,csvTitleList, qTrainingList);//得到去年训练集的CSV文件
			List<List<String>> lastTestList = scorePredictGroupService.getTestData(currentHasModeGroup);//得到今年的测试集List<Map<String,Object>>
			updateStuId(lastTestList);//在生成csv文件之前，在学号字段前加个字符串"s"
//			CsvUtils.createCsvFileByList(filename+testCsv,csvTitleList, lastTestList);//得到今年测试集的CSV文件
			for(TStuScorepredTermGpMd groupMold:groupMoldList){
				String courseId = groupMold.getCourseId();
				String schoolYear=groupMold.getSchoolYear();
				String termCode=groupMold.getTermCode();
				String moldId=groupMold.getMoldId();
//				 moldId = Integer.parseInt(groupMold.getMoldId());//得到该门课程所需的最优模型
				if(!("MX".equals(moldId))){
				if(!("X".equals(moldId))){
				List<Map<String,Object>> predictScoreList=RUtils.predictScoreByBestMold(trainingCSV, testCsv, filenames, predictkcNumber, Integer.parseInt(moldId));
				
			    /**
			     * 将预测的成绩保存到T_STU_SCOREPRED_TERM表
			     */
				for(Map<String,Object> predictData:predictScoreList){
			    	if((((String)predictData.get("course_id")).substring(1)).equals(courseId)){
			    		TStuScorepredTerm scorepredTerm=new TStuScorepredTerm();//成绩预测表对象
			    		TStuScorepredTermHis scorepredTermHis=new TStuScorepredTermHis();//成绩预测历史表对象
			    		scorepredTerm.setGrade(gradeId);//年级
			    		scorepredTermHis.setGrade(gradeId);//年级
			    		scorepredTerm.setSchoolYear(schoolYear);//课程学年
			    		scorepredTerm.setTermCode(termCode);//课程学期
			    		scorepredTerm.setCourseId(courseId);//要预测的课程
			    		scorepredTerm.setDate_(date);//预测的日期
			    		scorepredTermHis.setSchoolYear(schoolYear);//课程学年
			    		scorepredTermHis.setTermCode(termCode);//课程学期
			    		scorepredTermHis.setCourseId(courseId);//要预测的课程
			    		scorepredTermHis.setDate_(date);//预测日期
			    		scorepredTerm.setStuId(((String) predictData.get("stu_id")).substring(1));
			    		scorepredTerm.setPredictScore((String) predictData.get("predict_Score"));
			    		scorepredTerm.setIstrue(1);//成绩是否有效（这个是如果今年有新开的课程则设置成0）
			    		scorepredTermHis.setStuId(((String) predictData.get("stu_id")).substring(1));
			    		scorepredTermHis.setPredictScore((String) predictData.get("predict_Score"));
			    		scorepredTermHis.setIstrue(1);//成绩是否有效（这个是如果今年有新开的课程则设置成0）
			    		//保存成绩到T_STU_SCOREPRED_TERM表中，同时追加到T_STU_SCOREPRED_TERM_HIS表中
			    		try {
//			    			hibernateDao.deleteAll(TStuScorepredTerm.class);//删除T_STU_SCOREPRED_TERM表中的全部数据
			    			hibernateDao.save(scorepredTerm);//保存数据到T_STU_SCOREPRED_TERM表中
			    			hibernateDao.save(scorepredTermHis);//保存数据到T_STU_SCOREPRED_TERM_HIS
			    		} catch (SecurityException e) {
			    			e.printStackTrace();
			    		} catch (NoSuchFieldException e) {
			    			e.printStackTrace();
			    		}
			    	}
			    }
				}else{
					//存储预测模型为X的信息
					for(List<String> predictData:lastTestList){
				    		TStuScorepredTerm scorepredTerm=new TStuScorepredTerm();//成绩预测表对象
				    		TStuScorepredTermHis scorepredTermHis=new TStuScorepredTermHis();//成绩预测历史表对象
				    		scorepredTerm.setGrade(gradeId);//年级
				    		scorepredTermHis.setGrade(gradeId);//年级
				    		scorepredTerm.setSchoolYear(schoolYear);//课程学年
				    		scorepredTerm.setTermCode(termCode);//课程学期
				    		scorepredTerm.setCourseId(courseId);//要预测的课程
				    		scorepredTerm.setDate_(date);//预测的日期
				    		scorepredTermHis.setSchoolYear(schoolYear);//课程学年
				    		scorepredTermHis.setTermCode(termCode);//课程学期
				    		scorepredTermHis.setCourseId(courseId);//要预测的课程
				    		scorepredTermHis.setDate_(date);//预测日期
				    		scorepredTerm.setStuId(predictData.get(0));
				    		scorepredTerm.setPredictScore("0");
				    		scorepredTerm.setIstrue(1);//成绩是否有效（这个是如果今年有新开的课程则设置成0）
				    		scorepredTermHis.setStuId(predictData.get(0));
				    		scorepredTermHis.setPredictScore("0");
				    		scorepredTermHis.setIstrue(1);//成绩是否有效（这个是如果今年有新开的课程则设置成0）
				    		//保存成绩到T_STU_SCOREPRED_TERM表中，同时追加到T_STU_SCOREPRED_TERM_HIS表中
				    		try {
//				    			hibernateDao.deleteAll(TStuScorepredTerm.class);//删除T_STU_SCOREPRED_TERM表中的全部数据
				    			hibernateDao.save(scorepredTerm);//保存数据到T_STU_SCOREPRED_TERM表中
				    			hibernateDao.save(scorepredTermHis);//保存数据到T_STU_SCOREPRED_TERM_HIS
				    		} catch (SecurityException e) {
				    			e.printStackTrace();
				    		} catch (NoSuchFieldException e) {
				    			e.printStackTrace();
				    		}
					    }
				}
				}
			}
		}
		/**
		 * 今年伪分组中，没法预测的部分（即今年新开的课程）
		 */
//		List<TStuScorepredTermGroup> currentNoModeGroupList= (List<TStuScorepredTermGroup>) currentYearwGroupMap.get("noMode");
//		for(TStuScorepredTermGroup currentNoModeGroup:currentNoModeGroupList){
//			Integer gradeId=currentNoModeGroup.getGrade();//得到年级
//			String stuIdSql = scorePredictGroupService.getStuIdSqlByGroup(currentNoModeGroup);//得到学生学号sql
//			List<Map<String,Object>> listStuId=baseDao.getJdbcTemplate().queryForList(stuIdSql);
//			for(TStuScorepredTermGpMd scoreSave:currentNoModeGroup.getList()){
//				String courseId = scoreSave.getCourseId();
//				String schoolYear=scoreSave.getSchoolYear();
//				String termCode=scoreSave.getTermCode();
//				for(Map<String,Object> stuIdMap:listStuId){
//					TStuScorepredTerm scorepredTerm=new TStuScorepredTerm();//成绩预测表对象
//					TStuScorepredTermHis scorepredTermHis=new TStuScorepredTermHis();//成绩预测历史表对象
//					scorepredTerm.setGrade(gradeId);//年级
//					scorepredTermHis.setGrade(gradeId);//年级
//					scorepredTerm.setSchoolYear(schoolYear);//课程学年
//					scorepredTerm.setTermCode(termCode);//课程学期
//					scorepredTerm.setCourseId(courseId);//要预测的课程
//					scorepredTerm.setPredictScore("0");//要预测的成绩
//					scorepredTerm.setIstrue(0);//是否有效（0）代表不可预测
//					scorepredTerm.setDate_(date);//预测的日期
//					scorepredTermHis.setSchoolYear(schoolYear);//课程学年
//					scorepredTermHis.setTermCode(termCode);//课程学期
//					scorepredTermHis.setCourseId(courseId);//要预测的课程
//					scorepredTermHis.setPredictScore("0");//要预测的成绩
//					scorepredTermHis.setIstrue(0);//是否有效（0）代表不可预测
//					scorepredTermHis.setDate_(date);//预测日期
//					scorepredTerm.setStuId((String) stuIdMap.get("stu_id"));
//					scorepredTermHis.setStuId((String) stuIdMap.get("stu_id"));
//					//保存成绩到T_STU_SCOREPRED_TERM表中，同时追加到T_STU_SCOREPRED_TERM_HIS表中
//		    		try {
////		    			hibernateDao.deleteAll(TStuScorepredTerm.class);//删除T_STU_SCOREPRED_TERM表中的全部数据
//		    			hibernateDao.save(scorepredTerm);//保存数据到T_STU_SCOREPRED_TERM表中
//		    			hibernateDao.save(scorepredTermHis);//保存数据到T_STU_SCOREPRED_TERM_HIS
//		    		} catch (SecurityException e) {
//		    			e.printStackTrace();
//		    		} catch (NoSuchFieldException e) {
//		    			e.printStackTrace();
//		    		}
//				}
//			}
//		}
		
	}
	/**
	 * 预测当前学年成绩并且保存
	 * 步骤：1.得到去年的最优模型;2.预测今年成绩
	 */
	@Transactional
	public JobResultBean predictCurrentYearScore(){
		String jobName = "预测成绩--根据上一届学生的成绩";
		Long beginTime = System.currentTimeMillis();
		begin("#"+jobName+"#");
		JobResultBean result = new JobResultBean();
		 int year =Integer.parseInt((EduUtils.getSchoolYear9()).split("-")[0]);//得到学年
		 String termCode = EduUtils.getSchoolYearTerm(DateUtils.getNowDate())[1];//得到当前学期
		 String start_schoolYear,start_termCode,end_schoolYear,end_termCode,begin_year;
		 if("02".equals(termCode)){
			 start_schoolYear=(year-1)+"-"+year;
			 begin_year= EduUtils.getSchoolYear9();
			 start_termCode="01";
			 end_schoolYear=(year-1)+"-"+year;
			 end_termCode="02";
		 }else {
			 start_schoolYear=(year-2)+"-"+(year-1);
			 begin_year=(year-1)+"-"+year;
			 start_termCode="02";
			 end_schoolYear=(year-1)+"-"+year;
			 end_termCode="01";
		 }
		 System.out.println(filename);
		 String trainingCSV="f14.csv";
		 String testCsv="f15.csv";
//		String filenames= getFileName();
//		 List<Map<String, Object>> list = RUtils.predictModeFunction(trainingCSV, testCsv, filenames, 2, true);//验证选择最优模型（测试成功）
//		 System.out.println(list);
//		  List<Map<String, Object>> listScore = RUtils.predictScoreByBestMold(trainingCSV, testCsv, filenames, 2, 1);//验证最优模型预测成绩（测试成功）
//		  System.out.println(listScore);
		/**
		 * 1.得到去年的最优模型
		 * 传入的是前年的开始学年，开始学期，结束学年，结束学期
		 */
		 List<TStuScorepredTermGroup> lastHasModeGroupList = chooseBestMode(start_schoolYear, start_termCode, end_schoolYear, end_termCode,begin_year, start_termCode, EduUtils.getSchoolYear9(), end_termCode);
		/**
		 * 2.预测今年成绩
		 * 传入的值是当前学年的开始学年，开始学期，结束学年，结束学期
		 */
		predictScoreAndSave(begin_year, start_termCode, EduUtils.getSchoolYear9(), end_termCode,lastHasModeGroupList);
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
	private void info(String info){
		log.warn("======== info["+DateUtils.getNowDate2()+"]: "+info+" ========");
	}
	private void end(String info){
		log.warn("======== end["+DateUtils.getNowDate2()+"]: "+info+" ========");
	}
	private void error(String info){
		log.warn("======== error["+DateUtils.getNowDate2()+"]: "+info+" 停止执行========");
	}
	/************************************工具类*******************************************************/
	/**
	 * 处理文件路径
	 * @return csv文件路径
	 */
	public String getFileName(){
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
//		System.out.println(filenames);
		filename.replaceAll("\\\\", "\\\\\\\\");
		return filenames;
	}
	/**
	 * 在学生学号前加一个字符串"s"
	 * @param list
	 */
	public void updateStuId(List<List<String>> list){
		for(int j=0;j<list.size();j++){
			List<String> stuid=list.get(j);
			stuid.set(0, "s"+stuid.get(0));
		}
	}
    /**
     * 存储所有预测模型方法预测的成绩数据到数据库 
     * @param predictScoreList R语言预测成绩结果
     * @param groupMoldList    分组模型表List
     * @param gradeid          传入分组年级
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
	private void savePredictMoldData(List<Map<String,Object>> predictScoreList,List<TStuScorepredTermGpMd> groupMoldList,Integer gradeid) throws SecurityException, NoSuchFieldException {
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date=DateUtils.getNowDate();
		for(Map<String,Object> predictScoreData: predictScoreList){
			for(TStuScorepredTermGpMd groupMold:groupMoldList){
				if((predictScoreData.get("course_id")).equals(groupMold.getCourseId())){
					TStuScorepredTermGpCj groupScoreTable=new TStuScorepredTermGpCj();
					groupScoreTable.setSchoolYear(groupMold.getSchoolYear());//学年
					groupScoreTable.setTermCode(groupMold.getTermCode());//学期
					groupScoreTable.setGradeId(gradeid);//年级
					groupScoreTable.setStuId(((String)predictScoreData.get("stu_id")).substring(1));//学号
					groupScoreTable.setCourseId(groupMold.getCourseId());//课程id
					groupScoreTable.setPredictScore((Double)predictScoreData.get("predict_Score"));//预测成绩--可能会发生格式转换错误
					groupScoreTable.setGroupId(groupMold.getGroupId());//分组id
					groupScoreTable.setMoldId((String) predictScoreData.get("mold_id"));//模型id
					groupScoreTable.setDate(date);//预测日期
					hibernateDao.save(groupScoreTable);
				}
			}
		}
	}
	/**
	 * 存储所有预测模型方法预测的成绩数据到数据库 
	 * @param predictScoreList R语言预测成绩结果
	 * @param groupMoldList    分组模型表List
	 * @param gradeid          传入分组年级
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	private void saveBestMold(List<Map<String,Object>> bestMoldList,List<TStuScorepredTermGpMd> groupMoldList) throws SecurityException, NoSuchFieldException {
		for(Map<String,Object> predictScoreData: bestMoldList){
			for(TStuScorepredTermGpMd groupMold:groupMoldList){
				String courseId=((String) predictScoreData.get("courseId")).substring(1);
				String groupCourseId=groupMold.getCourseId();
				Integer isSave = groupMold.getIssave();
				if(((courseId).equals(groupCourseId))&&(isSave).equals(1)){
					groupMold.setMoldId(String.valueOf(predictScoreData.get("moldId")));
				}
			}
		}
	}
	public List<TStuScorepredTermGroup> getReal(){
		List<TStuScorepredTermGroup> listGroup=new ArrayList<>();
		// 工大数据
		TStuScorepredTermGroup group = new TStuScorepredTermGroup();
		group.setId("1");
		group.setDeptType(Constant.Level_Type_YX);
		group.setDeptValue("0103");
		group.setStartSchoolyear("2014-2015");
		group.setStartTermcode("01");
		group.setEndSchoolyear("2014-2015");
		group.setEndTermCode("02");
		group.setTruth(1);
		List<TStuScorepredTermGpMd> list = new ArrayList<>();
		group.setList(list);
		
		// test 开课计划
		group.setGrade(2);
		group.setIselective(0);
		//-- YX 0103  建环1401   52110821  51416040   52110831 52310906
		list.add(new TStuScorepredTermGpMd("11", "1", "52110821",null,null,null,"2014-2015","01",1,0,0,"mx"));
		list.add(new TStuScorepredTermGpMd("21", "1", "51416040",null,null,null,"2014-2015","01",2,0,0,"mx"));
		list.add(new TStuScorepredTermGpMd("31", "1", "52110831",null,null,null,"2014-2015","02",3,1,1,"mx"));
		list.add(new TStuScorepredTermGpMd("41", "1", "52310906",null,null,null,"2014-2015","02",4,1,0,"mx"));
//		TStuScorepredTermGroup group = new TStuScorepredTermGroup();
//		group.setId("1");
//		group.setGrade(2);
//		group.setDeptType(Constant.Level_Type_YX);
//		group.setDeptValue("000089");
//		group.setStartSchoolyear("2014-2015");
//		group.setStartTermcode("01");
//		group.setEndSchoolyear("2014-2015");
//		group.setEndTermCode("02");
//		group.setTruth(1);
//		List<TStuScorepredTermGpMd> list = new ArrayList<>();
//		group.setList(list);
//		
//		// test 开课计划
//		group.setGrade(2);
//		group.setIselective(0);
//		//-- YX 000089  2014010101 2014010102  0902103 1012103（000009 000010）  0103102 0123101(0103102 0103200)
//		list.add(new TStuScorepredTermGpMd("1", "1", "0902103",null,null,null,"2014-2015","01",1,0,0,"mx"));
//		list.add(new TStuScorepredTermGpMd("2", "1", "1012103",null,null,null,"2014-2015","01",2,0,0,"mx"));
//		list.add(new TStuScorepredTermGpMd("3", "1", "0103102",null,null,null,"2014-2015","02",3,1,1,"mx"));
//		list.add(new TStuScorepredTermGpMd("4", "1", "0123101",null,null,null,"2014-2015","02",4,1,0,"mx"));
		listGroup.add(group);
		return listGroup; 
	}
	public List<TStuScorepredTermGroup> getFake(){
		List<TStuScorepredTermGroup> listGroup=new ArrayList<>();
		// 工大数据
		TStuScorepredTermGroup group = new TStuScorepredTermGroup();
		group.setId("2");
		group.setDeptType(Constant.Level_Type_YX);
		group.setDeptValue("0103");
		group.setStartSchoolyear("2015-2016");
		group.setStartTermcode("01");
		group.setEndSchoolyear("2015-2016");
		group.setEndTermCode("02");
		group.setTruth(1);
		List<TStuScorepredTermGpMd> list = new ArrayList<>();
		group.setList(list);
		
		// test 开课计划
		group.setGrade(2);
		group.setIselective(0);
		//-- YX 0103  建环1401   52110821  51416040   52110831 52310906
		list.add(new TStuScorepredTermGpMd("11", "2", "52110821",null,null,null,"2015-2016","01",1,0,0,"mx"));
		list.add(new TStuScorepredTermGpMd("21", "2", "51416040",null,null,null,"2015-2016","01",2,0,0,"mx"));
		list.add(new TStuScorepredTermGpMd("31", "2", "52110831",null,null,null,"2015-2016","02",3,1,1,"mx"));
		list.add(new TStuScorepredTermGpMd("41", "2", "52310906",null,null,null,"2015-2016","02",4,1,0,"mx"));
//		TStuScorepredTermGroup group = new TStuScorepredTermGroup();
//		group.setId("2");
//		group.setGrade(2);
//		group.setDeptType(Constant.Level_Type_YX);
//		group.setDeptValue("000089");
//		group.setStartSchoolyear("2015-2016");
//		group.setStartTermcode("01");
//		group.setEndSchoolyear("2015-2016");
//		group.setEndTermCode("02");
//		group.setTruth(1);
//		List<TStuScorepredTermGpMd> list = new ArrayList<>();
//		group.setList(list);
//		
//		// test 开课计划
//		group.setGrade(2);
//		group.setIselective(0);
//		//-- YX 000089  2014010101 2014010102  0902103 1012103（000009 000010）  0103102 0123101(0103102 0103200)
//		list.add(new TStuScorepredTermGpMd("1", "2", "0902103",null,null,null,"2015-2016","01",1,0,0,"MX"));
//		list.add(new TStuScorepredTermGpMd("2", "2", "1012103",null,null,null,"2015-2016","01",2,0,0,"MX"));
//		list.add(new TStuScorepredTermGpMd("3", "2", "0103102",null,null,null,"2015-2016","02",3,1,1,"X"));
//		list.add(new TStuScorepredTermGpMd("4", "2", "0123101",null,null,null,"2015-2016","02",4,1,0,"MX"));
		listGroup.add(group);
		return listGroup; 
	}
	public static void main(String[] args) {
		String s="s541201030220";
		s=s.substring(1);
		System.out.println(s);
	}
//		 int year =Integer.parseInt((EduUtils.getSchoolYear9()).split("-")[0]);//得到学年
//		 String termCode = EduUtils.getSchoolYearTerm(DateUtils.getNowDate())[1];//得到当前学期
//		 String start_schoolYear,start_termCode,end_schoolYear,end_termCode,begin_year;
//		 if("02".equals(termCode)){
//			 start_schoolYear=(year-2)+"-"+(year-1);
//			 begin_year= EduUtils.getSchoolYear9();
//			 start_termCode="01";
//			 end_schoolYear=(year-2)+"-"+(year-1);
//			 end_termCode="02";
//		 }else {
//			 start_schoolYear=(year-3)+"-"+(year-2);
//			 begin_year=(year-1)+"-"+year;
//			 start_termCode="02";
//			 end_schoolYear=(year-2)+"-"+(year-1);
//			 end_termCode="01";
//		 }
//		 String s="123456";
//		 s="s"+s;
//		 System.out.println(s);
//	}
}
