package cn.gilight.dmm.teaching.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;













import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.business.util.PmsUtils;
import cn.gilight.dmm.teaching.dao.ScorePredictGroupDao;
import cn.gilight.dmm.teaching.entity.TStuScorepredTermGpMd;
import cn.gilight.dmm.teaching.entity.TStuScorepredTermGroup;
import cn.gilight.dmm.teaching.service.ScorePredictGroupService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.Main;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.ListUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.framework.uitl.common.MathUtils;

@Service("scorePredictGroupService")
public class ScorePredictGroupServiceImpl implements ScorePredictGroupService {
	
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BusinessService businessService;
	@Resource
	private HibernateDao hibernateDao;
	@Resource
	private BaseDao baseDao;
	@Resource
	private ScorePredictGroupDao scorePredictGroupDao;
	
	private static final int     Stu_Count_least = 15;//训练集的合格标准，要求训练集中数量大于该数值
	private static final int     Stu_Count_min   = 50;//训练集的最少人数
	private static final Double  Group_Scale = 0.80;//大于分组最大值的比例在这之内认为相近
	private static final Double  Merge_Scale = 0.80;//大于这个值就让合并
	private static final int     Next_Xq_Min_Size = 1;//训练集最小课程数
	private static final int     Last_Xq_Min_Size = 2;//训练集上学期最小课程数
	private static final Double  Kz_Stu_Count_Scale = 0.75;//训练集上学期最小课程数
	private static final String  Type_XX = Constant.Level_Type_XX;
	private static final String  Type_YX = Constant.Level_Type_YX;
	private static final String  Type_ZY = Constant.Level_Type_ZY;
	private static final int     Group_Course_Split_Num = 7;//分组课程达到这个数值就进行拆分
	private static final int     Group_Course_LastXq_Count = 4;//分组课程拆分后，参考课程拆分为4门
	private static final int     Group_Course_NextXq_Count = 3;//分组课程拆分后，预测课程拆分为3门
	private Logger log = Logger.getLogger(this.getClass());

	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getRealGroup(String start_schoolYear,String start_termCode,
			String end_schoolYear,String end_termCode,String yc_start_schoolYear,String yc_start_termCode,
			 String yc_end_schoolYear,String yc_end_termCode){
		/**
		 * 0.先查询分组表中是否存在该分组，如果存在直接返回，不存在就接着走然后保存并返回；
		 * 1.根据开始结束学年学期获取到之间所有的学年学期，并区分已知课程成绩的学年学期（结束学年学期除外）和需要预测的学年学期（结束学年学期）；
		 * 2.查询出要预测学年学期的学生的年级集合；（循环年级:一个年级一个年级的查询分组）
		 * 3.查询出学院集合；（循环学院:在年级条件下一个学院一个学院的查询分组）
		 * 4.查询出该院系所有有成绩的课程的课程id,课程属性，课程性质，课程类别，上这门课且有成绩的班级数，上这门课且有成绩的学生数，
		 * 以及学年学期，对已知课程成绩的课程标记上上学期的标记（ycXq:"last"），未知课程成绩的课程标记上下学期的标记（ycXq:"next"）；
		 * 5.筛选掉学生数小于分析最低标准数值(15人)的记录，并且按照学生数排序；
		 * 6.根据实际有成绩的学生数查询出分组合并的标准；(从最大开始学生数相差 20%以内的分为一组，如果有与其中最小学生数相差在10人以内的也放进来)
		 * 7.遍历这些分组把实际的班级集合和有成绩的学生集合放进去；(会导致方法执行缓慢)
		 * 8.遍历这些分组把班级完全相同的进行合并；（学生人数减少在一定范围内合并后放到list中）（可能会影响预测精准度）
		 * 9.遍历list把已达到分组条件的分组放入到List<TStuScorePredictTermGroup>中；（分组条件：已知成绩课程数大于等于2 且 总课程数大于3）
		 * 10.遍历list把未达到分组条件的课程找到最优的组合，每找到一个符合分组条件的就放入到List<TStuScorePredictTermGroup>中，直到每个课程都找到
		 * 自己的最优组合，并标记上是哪些课程用这个模型预测；（自身最优原则:根据自己找到的组合才是他自己的最优组合）；（可能会影响预测精准度）
		 * 11.如果有分组的实际上课人数占75%以上的就扩展到全校寻找共同上这门课的学生，找到了做上标记并放到List<TStuScorePredictTermGroup>中；
		 * 
		 * 难点:1.加快把班级集合与有成绩学生集合放入到每个课程的对象中；
		 *     2.寻找自己的最优组合；
		 */
		 Map<String,Object> reMap = new HashMap<String, Object>(); 
		List<TStuScorepredTermGroup> groupAllList = scorePredictGroupDao.getGroup(start_schoolYear, start_termCode, end_schoolYear, end_termCode,1,0),
				                     groupXxList = scorePredictGroupDao.getGroup(start_schoolYear, start_termCode, end_schoolYear, end_termCode, 1, 1);
		 List<TStuScorepredTermGroup> groupList = new ArrayList<TStuScorepredTermGroup>(),
                                      noHgGroupList = new ArrayList<TStuScorepredTermGroup>(); 
		if(groupAllList != null && !groupAllList.isEmpty() && groupXxList != null && !groupXxList.isEmpty()){
			 groupAllList.addAll(groupXxList);
			 reMap.put("isHg", groupAllList);
			 reMap.put("noHg", noHgGroupList);
			 return reMap;
		 }
	     List<String> deptList = PmsUtils.getPmsAll();
         int schoolYear = Integer.parseInt(end_schoolYear.substring(0,4));
         List<String> gradeList = scorePredictGroupDao.queryGroupGradeList(schoolYear, deptList);
         if(gradeList == null || gradeList.isEmpty()){
        	 reMap.put("isHg", groupList);
			 reMap.put("noHg", noHgGroupList);
        	 return reMap;
         }
         for(String grade : gradeList){
        	 if(groupAllList == null || groupAllList.isEmpty()){
        		Map<String,Object> bxMap = getBxCourseGroup(start_schoolYear, start_termCode, end_schoolYear, end_termCode, grade,yc_start_schoolYear,yc_start_termCode,
       				 yc_end_schoolYear,yc_end_termCode);
                List<TStuScorepredTermGroup> isHgBx = (List<TStuScorepredTermGroup>) MapUtils.getObject(bxMap, "isHg"),
                		                     noHgBx = (List<TStuScorepredTermGroup>) MapUtils.getObject(bxMap, "noHg");
        	    if(isHgBx != null && !isHgBx.isEmpty()){
        	    	groupList.addAll(isHgBx);
        	    }
        	    if(noHgBx != null && !noHgBx.isEmpty()){
        	    	noHgGroupList.addAll(noHgBx);
        	    }
        	 }else{
        		 groupList.addAll(groupAllList);
        	 }
        	 if(groupXxList == null || groupXxList.isEmpty()){
        		 Map<String,Object> xxMap = getXxCourseGroup(start_schoolYear, start_termCode, end_schoolYear, end_termCode, grade,yc_start_schoolYear,yc_start_termCode,
           				 yc_end_schoolYear,yc_end_termCode);
        		 List<TStuScorepredTermGroup> isHgXx = (List<TStuScorepredTermGroup>) MapUtils.getObject(xxMap, "isHg"),
                                              noHgXx = (List<TStuScorepredTermGroup>) MapUtils.getObject(xxMap, "noHg");
				if(isHgXx != null && !isHgXx.isEmpty()){
					groupList.addAll(isHgXx);
				}
				if(noHgXx != null && !noHgXx.isEmpty()){
					noHgGroupList.addAll(noHgXx);
				}
        	 }else{
        		 groupList.addAll(groupXxList);
        	 }
         }
         reMap.put("isHg", groupList);
         reMap.put("noHg", noHgGroupList);
         return reMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getFakeGroup(String start_schoolYear,String start_termCode,
			String end_schoolYear,String end_termCode,List<TStuScorepredTermGroup> groupList){
		/**
		 * 1.遍历真分组，取出学年与真分组中学年差一年，学期相等，课程id，性质，属性等相等的分组放入到Map中的hasMode中。
		 * 2.剩余的课程与已经匹配上真分组的未分组进行组合，找到自己最优的组合放入到Map中的noMode中。
		 * 难点:剩余课程寻找自己的最优组合；
		 */
		Map<String,Object> reMap = new HashMap<String, Object>(); 
		List<TStuScorepredTermGroup> groupAllList_s = scorePredictGroupDao.getGroup(start_schoolYear, start_termCode, end_schoolYear, end_termCode,0,0),
				                     groupXxList_s = scorePredictGroupDao.getGroup(start_schoolYear, start_termCode, end_schoolYear, end_termCode, 0, 1);
		 List<TStuScorepredTermGroup> groupList_s = new ArrayList<TStuScorepredTermGroup>(),
                                      noHgGroupList_s = new ArrayList<TStuScorepredTermGroup>(); 
		if(groupAllList_s != null && !groupAllList_s.isEmpty() && groupXxList_s != null && !groupXxList_s.isEmpty()){
			groupAllList_s.addAll(groupXxList_s);
			reMap.put("hasMode", groupAllList_s);
			 reMap.put("noMode", noHgGroupList_s);
			 reMap.put("noHg", noHgGroupList_s);
			 return reMap;
		 }
		String type="all";List<String> xxbxList = new ArrayList<String>();
		if(groupAllList_s == null || groupAllList_s.isEmpty()){
			if(groupXxList_s != null && !groupXxList_s.isEmpty()){
				type = "bx";
				xxbxList.add("0");
			}
		}
		if(groupXxList_s == null || groupXxList_s.isEmpty()){
			if(groupAllList_s != null && !groupAllList_s.isEmpty()){
				type = "xx";
				xxbxList.add("1");
			}
		}
		List<Map<String,Object>> allCourseList = scorePredictGroupDao.getWfzCourseList(start_schoolYear, start_termCode, end_schoolYear, end_termCode,type),
				                 isUsedCourse = new ArrayList<Map<String,Object>>();
		List<TStuScorepredTermGroup> wfzGroupList = new ArrayList<TStuScorepredTermGroup>(),
				                     otherGroupList = new ArrayList<TStuScorepredTermGroup>(),
				                     onHgGroupListAll = new ArrayList<TStuScorepredTermGroup>();
		for(TStuScorepredTermGroup group : groupList){
			List<Map<String,Object>> termList = scorePredictGroupDao.getYearTermAry(group.getStartSchoolyear(), group.getStartTermcode(), group.getEndSchoolyear(), group.getEndTermCode()),
					                 termList1 = scorePredictGroupDao.getYearTermAry(start_schoolYear, start_termCode, end_schoolYear, end_termCode);
			if(xxbxList.contains(group.getIselective().toString())){
				continue;
			}
			String grade = EduUtils.getEnrollGradeByYearAndGrade(end_schoolYear,group.getGrade());
			List<TStuScorepredTermGpMd> kcList = group.getList();
			TStuScorepredTermGroup group_a = new TStuScorepredTermGroup();
			List<TStuScorepredTermGpMd> kcList_a = new ArrayList<TStuScorepredTermGpMd>();
			String deptType = group.getDeptType();String dept = group.getDeptValue();
			String[] deptAry = dept.split(",",-1);
			for(TStuScorepredTermGpMd kcM:kcList){
				Map<String,Object> dateMap = new HashMap<String, Object>();
				dateMap.put("schoolYear", kcM.getSchoolYear());
				dateMap.put("termCode", kcM.getTermCode());
				dateMap.put("ycXq", kcM.getIspredict() == 0 ? "last":"next");
				int index = termList.indexOf(dateMap);
				Map<String,Object> dateMap1 = termList1.get(index);
				String kc_schoolYear = MapUtils.getString(dateMap1, "schoolYear"),
					   kc_termCode = MapUtils.getString(dateMap1, "termCode");
				TStuScorepredTermGpMd kc_a = new TStuScorepredTermGpMd();
				String kcId = kcM.getCourseId(),
					   kcSx = kcM.getCourseAttrCode()==null?"":kcM.getCourseAttrCode(),
					   kcXz = kcM.getCourseNatureCode()==null? "":kcM.getCourseNatureCode() ,
					   kcLb = kcM.getCourseCategoryCode() == null?"":kcM.getCourseCategoryCode() ;
				String kcAll = kcId+","+kcSx+","+kcXz+","+kcLb;
				for(Map<String,Object> kcMap_1 : allCourseList){
				     String kcId_1 = MapUtils.getString(kcMap_1,"kc"),
				    		xn     = MapUtils.getString(kcMap_1,"xn"),
				    		xq     = MapUtils.getString(kcMap_1,"xq"),
				    		grade_1= MapUtils.getString(kcMap_1,"grade"),
				    		dept_1 = MapUtils.getString(kcMap_1,"dept"),
				    		major_1 = MapUtils.getString(kcMap_1,"major");
				     String dept_2 = dept_1;
				     if(deptType.equals(Type_ZY)){
				    	 dept_2 = major_1;
				     }
				     if(kcAll.equals(kcId_1) && grade.equals(grade_1) && kc_termCode.equals(xq) && kc_schoolYear.equals(xn) && Arrays.asList(deptAry).contains(dept_2)){
                         Map<String,Object> isUsedMap = new HashMap<String, Object>();
                         isUsedMap.put("kcid", kcId_1+","+xn+","+xq);
                         isUsedMap.put("dept", dept_1);
                         isUsedCourse.add(isUsedMap);
				    	 kcMap_1.put("haveGroup", 1);
				    	 kc_a.setCourseAttrCode(kcM.getCourseAttrCode());
				    	 kc_a.setCourseId(kcM.getCourseId());
				    	 kc_a.setCourseNatureCode(kcM.getCourseNatureCode());
				    	 kc_a.setCourseCategoryCode(kcM.getCourseCategoryCode());
				    	 kc_a.setIspredict(kcM.getIspredict());
				    	 kc_a.setIssave(kcM.getIssave());
				    	 kc_a.setMoldId(kcM.getMoldId());
				    	 kc_a.setOrder_(kcM.getOrder_());
				    	 kc_a.setSchoolYear(xn);kc_a.setTermCode(xq);
				    	 break;
				     }
				}
				kcList_a.add(kc_a);
			}
			group_a.setDeptType(deptType);
			group_a.setDeptValue(group.getDeptValue());
			group_a.setEndSchoolyear(end_schoolYear);
			group_a.setEndTermCode(end_termCode);
			group_a.setGrade(group.getGrade());
			group_a.setIselective(group.getIselective());
			group_a.setList(kcList_a);
			group_a.setStartSchoolyear(start_schoolYear);
			group_a.setStartTermcode(start_termCode);
			group_a.setTruth(0);
			saveGroupNoHaveGroupId(group_a);
			wfzGroupList.add(group_a);
		}
		int schoolYear = Integer.parseInt(end_schoolYear.substring(0, 4));
		List<String> gradeList = scorePredictGroupDao.queryGroupGradeList(schoolYear, PmsUtils.getPmsAll());
		 Map<String,Object> bxMap = (type.equals("all")||type.equals("bx"))?getBxWfzCourseGroup(start_schoolYear, start_termCode, end_schoolYear, end_termCode, isUsedCourse,gradeList):new HashMap<String, Object>(),
				            xxMap = (type.equals("all")||type.equals("xx"))?getXxWfzCourseGroup(start_schoolYear, start_termCode, end_schoolYear, end_termCode, isUsedCourse,gradeList):new HashMap<String, Object>();
		 List<TStuScorepredTermGroup> bxNoMode = (type.equals("all")||type.equals("bx"))?(List<TStuScorepredTermGroup>) MapUtils.getObject(bxMap, "noMode"):new ArrayList<TStuScorepredTermGroup>(),
				                     bxNoHg = (type.equals("all")||type.equals("bx"))?(List<TStuScorepredTermGroup>) MapUtils.getObject(bxMap, "noHg"):new ArrayList<TStuScorepredTermGroup>(),
				                     xxNoMode = (type.equals("all")||type.equals("xx"))?(List<TStuScorepredTermGroup>) MapUtils.getObject(xxMap, "noMode"):new ArrayList<TStuScorepredTermGroup>(),
				                     xxNoHg = (type.equals("all")||type.equals("xx"))?(List<TStuScorepredTermGroup>) MapUtils.getObject(xxMap, "noHg"):new ArrayList<TStuScorepredTermGroup>();
		 otherGroupList.addAll(bxNoMode);otherGroupList.addAll(xxNoMode);
		 onHgGroupListAll.addAll(bxNoHg);onHgGroupListAll.addAll(xxNoHg);
		 Map<String,Object> reMapAll = new HashMap<String, Object>();
		 reMapAll.put("hasMode", wfzGroupList);
		 reMapAll.put("noMode", otherGroupList);
		 reMapAll.put("noHg", onHgGroupListAll);
		return reMapAll;
	}

	@Override
	public boolean isValidGroup(TStuScorepredTermGroup group){
		Long begin = System.currentTimeMillis();
		System.out.println("进入1");
		List<List<String>> list = queryScoreData(group, false, "");
		System.out.println(new Double(System.currentTimeMillis()-begin)/1000);
		System.out.println("退出1");
		return isValidTrainingData(list);
	}
	
	@Override
	public boolean isValidTrainingData(List<List<String>> list){
		/**
		 * 2.排除掉成绩异常的学生的成绩；（异常暂时未定义2017-01-08）
		 * 		成绩为0的数据，其他成绩(除去0)总和在前20%的要删掉，后20%的要保留，中间的也保留
		 * 2.1 记录出现成绩为0的课程（课程下标从0开始），记录出现成绩为0的学生及课程（课程下标从0开始）
		 * 2.2 循环计算成绩为0课程的其他成绩和（所有学生都计算）并排序
		 * 2.3 循环计算成绩为0学生的其他成绩和所占位置，大于20%要去除这个异常值（记录学生下标）
		 * 2.4剔除异常成绩的学生
		 */
		// 2.1
		System.out.println("进入2");
		Long begin = System.currentTimeMillis();
		if(!list.isEmpty()){
			List<String> stuScoreList = null; // 单个学生成绩列表
			int kcCount = list.get(0).size()-1; // 该分组的课程数量
			Set<Integer> errKcSet = new HashSet<>(); // 出现0的课程下标
			List<List<String>> errStuList = new ArrayList<>(); // 出现0的学生下标及课程下标（2, "0,3"）
			for(int i=0,len=list.size(); i<len; i++){
				stuScoreList = list.get(i).subList(1, kcCount+1); // 单个学生成绩
				boolean isNull = false;
				List<Integer> errKcOneList = new ArrayList<>(); // 每个学生为0的课程
				for(int j=0,lenJ=stuScoreList.size(); j<lenJ; j++){ // 课程下标，从0开始
					double score = Double.valueOf(stuScoreList.get(j));
					if(score == 0){
						isNull = true;
						errKcOneList.add(j);
					}
				}
				if(isNull){
					// 记录为0的学生、课程下标
					List<String> errStuOneList = new ArrayList<>();
					errStuOneList.add(i+"");
					errStuOneList.add(ListUtils.join(errKcOneList, ","));
					errStuList.add(errStuOneList);
					// 记录出现过成绩0的课程
					if(errKcSet.size() != kcCount) errKcSet.addAll(errKcOneList);
				}
			}
			// 2.2
			if(!errKcSet.isEmpty()){
				// 成绩为0课程的其他课程成绩集合 { 1:[98,83,65,32], 2:[87,65,59,32] }
				Map<Integer, List<Double>> kcOtherScoreMap = new HashMap<>();
				for(Integer kcIndex : errKcSet){
					List<Double> otherScoreList = new ArrayList<>();
					for(int i=0,len=list.size(); i<len; i++){
						stuScoreList = list.get(i).subList(1, kcCount+1); // 单个学生成绩
						double otherScore = 0D;
						for(int j=0,lenJ=stuScoreList.size(); j<lenJ; j++){
							if(j != kcIndex) otherScore+=Double.valueOf(stuScoreList.get(j));
						}
						otherScoreList.add(otherScore);
					}
					Collections.sort(otherScoreList);
					Collections.reverse(otherScoreList);
					kcOtherScoreMap.put(kcIndex, otherScoreList);
				}
				// 2.3
				List<Integer> errStuIndexList = new ArrayList<>(); // 要剔除的学生下标
				for(List<String> li : errStuList){
					int stu_index = Integer.valueOf(li.get(0)); // 学生下标
					stuScoreList = list.get(stu_index).subList(1, kcCount+1); // 单个学生成绩
					String[] stuErrKcAry = li.get(1).split(","); // 学生异常课程下标
					for(String stuKc_index_ : stuErrKcAry){
						int stuKc_index = Integer.valueOf(stuKc_index_); // 课程下标从0开始
						double scores = 0D;
						for(String kcScore : stuScoreList){
							scores += Double.valueOf(kcScore);
						}
						if(MathUtils.getScaleNum(kcOtherScoreMap.get(stuKc_index), scores) <= 20){
							errStuIndexList.add(stu_index);
						};
					}
				}
				// 2.4
				if(!errStuIndexList.isEmpty()){
					List<List<String>> list2 = new ArrayList<>();
					for(int i=0,len=list.size(); i<len; i++){
						if(!errStuIndexList.contains(i)) list2.add(list.get(i));
					}
					list = list2;
				}
			}
		}
		// 是否有效的分组
		boolean isValidGroup = true;
		int xsCount = list.size();
		if(xsCount <= Stu_Count_least){
			isValidGroup = false;
		}
		System.out.println(new Double(System.currentTimeMillis()-begin)/1000);
		System.out.println("退出2");
		return isValidGroup;
	}
	
	@Override
	public List<List<String>> getTrainingData(TStuScorepredTermGroup group){
		/**
		 * 1.查询分组中对应的学生成绩；
		 * 2.排除掉成绩异常的学生的成绩；（异常暂时未定义2017-01-08）
		 * 		成绩为0的数据，其他成绩(除去0)总和在前20%的要删掉，后20%的要保留，中间的也保留
		 * 3.不符合标准的重抽样（N：人数，P：课程； Max(N*10,P的3次方) 取较大值）
		 * 		重抽样条件：合格人数>15  且 （最少人数>=50 或 人数>课程的平方）
		 */
		// 1
		List<List<String>> list = queryScoreData(group, false, "");
		// 2
		isValidTrainingData(list);
		// 3
		int xsCount = list.size();
		int kcCount = list.get(0).get(1).split(",").length;
		if(xsCount > Stu_Count_least && 
			(xsCount < kcCount*kcCount || xsCount < Stu_Count_min) ){
			int count10 = xsCount*10, count3x = kcCount*kcCount*kcCount;
			int maxSize = count10 > count3x ? count10 : count3x;
			list = MathUtils.getMultipleSampling(list, maxSize);
		}
		return list;
	}
	
	@Override
	public List<List<String>> getTestData(TStuScorepredTermGroup group){
		/**
		 * 1.查询出分组对应的学生成绩；
		 * 注:需要和训练集的课程id顺序一样。
		 */
		return queryScoreData(group, true, "");
	}
	
	@Override
	public List<List<String>> getScoreData(TStuScorepredTermGroup group){
		return queryScoreData(group, false, null);
	}
	
	/**
	 * 获取学生成绩
	 * @param group 分组
	 * @param emptyValue 空值
	 * @param isNeedNull 是否需要空值
	 * @return List<List<String>>
	 */
	private List<List<String>> queryScoreData(TStuScorepredTermGroup group, boolean isNeedNull, String emptyValue){
//		-- 按学生分组，按课程排序 的合并成绩字段（逗号分隔）
//		select stu_id, max(scores) from (
//		   select t.stu_id, wm_concat(t.score) over (partition by t.stu_id order by order_) scores from
//		   (
//			  -- 所有学生、所有课程的成绩
//		      select stu.no_ stu_id, stu.kc, stu.order_, nvl(score.score, 'null') score from
//		      (
//			  -- 学生、课程、排序 的空组
//		      select stu.no_, 'kc1' kc, 1 order_ from (select * from t_stu) stu
//		      union all select stu.no_, 'kc2', 2 order_ from (select * from t_stu) stu
//		      ) stu 
//		      left join (
//			  -- 所有课程的真实成绩
//		      select stu_id, coure_code, score from (scoreSql)
//		      union all select stu.stu_id, coure_code, score from (scoreSql)
//		      ) score on stu.no_=score.stu_id and score.coure_code=stu.kc
//		   )t
//		) group by stu_id
		// 学生IDsql 和 学生sql
		String stuIdSql = getStuIdSqlByGroup(group),
			   stuSql   = "select t.* from t_stu t,("+stuIdSql+")stu where t.no_=stu.stu_id";
		// 
		List<String> stuKcSqlList = new ArrayList<>();
		List<TStuScorepredTermGpMd> kcList = group.getList();
		
		Map<String, List<String>> kcSqlMap = new HashMap<String, List<String>>();
		List<String> kcSqlList = null;
		String key_xnxq = null;
		String kcId = null;
		for(int i=0,len=kcList.size(); i<len; i++){
			kcId = PmsUtils.formatInSql(kcList.get(i).getCourseId());
			stuKcSqlList.add("select stu.stu_id, "+kcId+" kc,  "+(i+1)+" order_ from ("+stuIdSql+") stu");
//			if(i==0) DevUtils.p("kc one："+stuKcSqlList.get(i));
			key_xnxq = kcList.get(i).getSchoolYear()+"-"+kcList.get(i).getTermCode();
			if(kcSqlMap.containsKey(key_xnxq)){
				kcSqlList = kcSqlMap.get(key_xnxq);
			}else{
				kcSqlList = new ArrayList<>();
				kcSqlMap.put(key_xnxq, kcSqlList);
			}
			String kcSql_ = "select "+kcId+" kcId from dual";
			if(!kcSqlList.contains(kcSql_)) kcSqlList.add("select "+kcId+" kcId from dual");
		}
//		String kcSql = ListUtils.join(kcSqlList, " union all ");
		String stuKcSql = ListUtils.join(stuKcSqlList, " union all ");
//		DevUtils.p("kc all： "+stuKcSql);
		// select stu_id, coure_code, credit, score, COURSE_ATTR_CODE, COURSE_NATURE_CODE from t
		List<String> stuScoreSqlList = new ArrayList<>();
		List<Map<String, Object>> xnxqList = scorePredictGroupDao.getYearTermAry(group.getStartSchoolyear(), group.getStartTermcode(), group.getEndSchoolyear(), group.getEndTermCode());
		String schoolYear_ = null, termCode_ = null;
		for(Map<String, Object> map : xnxqList){
			schoolYear_ = MapUtils.getString(map, "schoolYear");
			termCode_   = MapUtils.getString(map, "termCode");
			key_xnxq = schoolYear_+"-"+termCode_;
			String kcSql = ListUtils.join(kcSqlMap.get(key_xnxq), " union ");
//			kcSql = "select '51110401' kcId from dual ";
			stuScoreSqlList.add("select stu_id, coure_code, score from ("+businessDao.getStuScoreExamSql(stuSql, schoolYear_, termCode_)+")t,"
					+ "("+kcSql+")kc where t.coure_code=kc.kcId");
//			if(stuScoreSqlList.size() == 1) DevUtils.p("score one:"+stuScoreSqlList.get(0));
		}
		String stuScoreSql = ListUtils.join(stuScoreSqlList, " union all ");
		DevUtils.p("score all: "+stuScoreSql);
		// 所有学生、所有课程的成绩
		String emptyScore = "null";
		String stuScoreAllSql = "select stu.stu_id, stu.kc, stu.order_, nvl(TO_CHAR(score.score), '"+emptyScore+"') score"
				+ " from ("+stuKcSql+") stu"
				+ " left join ("+stuScoreSql+") score"
				+ " on stu.stu_id=score.stu_id and score.coure_code=stu.kc";
		// 按学生分组，按课程排序 的合并成绩字段（逗号分隔）
		String sql2 = "select stu_id, wm_concat(score) scores from ("+stuScoreAllSql+")t"
				+ " group by stu_id";
		DevUtils.p("stuScoreAllSql: "+stuScoreAllSql);
		DevUtils.p("sql2: "+sql2);
		String sql = "select stu_id, max(scores) scores from ("
				+ "select t.stu_id, wm_concat(t.score) over (partition by t.stu_id order by order_) scores from ("+stuScoreAllSql+")t"
				+ ") group by stu_id";
		// 查询数据之后拆分
		List<List<String>> scoreList = new ArrayList<>();
		System.out.println(sql);
		List<Map<String, Object>> list = baseDao.queryListInLowerKey(sql);
		String score = null;
		for(Map<String, Object> map : list){
			List<String> scoreOneList = new ArrayList<>();
			scoreOneList.add(MapUtils.getString(map, "stu_id"));
			String[] scoreAry = MapUtils.getString(map, "scores").split(",");
			boolean isNullScore = false;
			for(int k=0,lenK=scoreAry.length; k<lenK; k++){
				score = scoreAry[k];
				if(null==score||emptyScore.equals(score)||"".equals(score)){
					score = emptyValue;
					isNullScore = true;
				}
				scoreOneList.add(score);
			}
			if(isNeedNull || !isNullScore)
				scoreList.add(scoreOneList);
		}
		return scoreList;
	}

	@Override
	public String getStuIdSqlByGroup(TStuScorepredTermGroup group){
		Integer iselective  = group.getIselective(),
				grade       = group.getGrade(),
				year        = Integer.valueOf(group.getEndSchoolyear().substring(0, 4));
		List<TStuScorepredTermGpMd> list = group.getList();
		List<String> sqlList   = new ArrayList<>(),
					 whereList = new ArrayList<>();
		String deptValue = group.getDeptValue();
		// 走通用学生sql
		List<AdvancedParam> stuAdvancedList = new ArrayList<>();
		AdvancedParam adp_grade = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_GRADE, grade+"");
		stuAdvancedList.add(adp_grade);
		switch (group.getDeptType()) {
		case Type_YX:
		case Type_ZY:
			AdvancedParam adp_dept = new AdvancedParam(AdvancedUtil.Group_Common, AdvancedUtil.Common_DEPT_TEACH_ID, deptValue);
			stuAdvancedList.add(adp_dept);
			break;
		default:
			break;
		}
		String stuSql = businessDao.getStuSql(year, PmsUtils.getPmsAll(), stuAdvancedList);
		stuSql = "select no_ stu_id, class_id from ("+stuSql+")";
		
		sqlList.add("("+stuSql+") t");
		// TODO
		List<String> kc_li = new ArrayList<String>();
		if(iselective == 1){ // 选修
			for(int i=0,len=list.size(); i<len; i++){
				TStuScorepredTermGpMd t = list.get(i);
				if(kc_li.contains(t.getCourseId())) continue;
				sqlList.add("(select stu_id from T_STU_COURSE_CHOOSE where school_year = '"+t.getSchoolYear()+"' and term_code = '"+t.getTermCode()+"' and scoure_code = '"+t.getCourseId()+"') t"+i);
				whereList.add("t.stu_id = t"+i+".stu_id");
				kc_li.add(t.getCourseId());
			}
		}else{
			for(int i=0,len=list.size(); i<len; i++){
				TStuScorepredTermGpMd t = list.get(i);
				if(kc_li.contains(t.getCourseId())) continue;
				sqlList.add("(select class_id from T_COURSE_ARRANGEMENT_PLAN where school_year = '"+t.getSchoolYear()+"' and term_code = '"+t.getTermCode()+"' and course_code = '"+t.getCourseId()+"') t"+i);
				whereList.add("t.class_id = t"+i+".class_id");
				kc_li.add(t.getCourseId());
			}
		}
		String sql = "select t.stu_id from "+ListUtils.join(sqlList, ",");
		if(!whereList.isEmpty()){
			sql += " where "+ListUtils.join(whereList, " and ");
		}
		return sql;
	}
	
	@Override
	@Transactional
	public void saveBestMode(List<TStuScorepredTermGroup> groupList){
		/**
		 * 1.获取所有分组的最优模型并更新。
		 */
		if(groupList != null && !groupList.isEmpty()){
			List<TStuScorepredTermGpMd> list = new ArrayList<>();
			for(TStuScorepredTermGroup t : groupList){
				list.addAll(t.getList());
			}
			if(!list.isEmpty())
				try {
					hibernateDao.saveAll(list);
				} catch (SecurityException | NoSuchFieldException e) {
					e.printStackTrace();
				}
		}
	}
	@Override
	@Transactional
	public void saveBestMode(TStuScorepredTermGroup group){
		/**
		 * 1.根据分组中课程等字段进行最优模型字段的更新。
		 */
		if(group != null && !group.getList().isEmpty()){
			try {
				hibernateDao.saveAll(group.getList());
			} catch (SecurityException | NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
	}
	@Transactional
	public void saveGroupData(List<TStuScorepredTermGpMd> groupList){
	    if(groupList != null && !groupList.isEmpty()){
	    	try {
				hibernateDao.saveAll(groupList);
				info("插入"+groupList.size()+" 条");
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
	@Transactional
	public void saveGroupData(TStuScorepredTermGroup group){
	    if(group != null ){
	    	try {
				hibernateDao.save(group);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
	private void info(String info){
		log.warn("======== info["+DateUtils.getNowDate2()+"]: "+info+" ========");
	}
	/**
	 * 集合排序
	 * 
	 * @param list
	 *            void
	 * @param compareField
	 */
	private void compareCount(List<Map<String, Object>> list,
			final String compareField, final boolean asc) {
		Collections.sort(list, new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				int count1 = MapUtils.getIntValue(o1, compareField), count2 = MapUtils
						.getIntValue(o2, compareField); int flag = 0, pare = asc
						? 1
						: -1; // 正序为1
				if (count1 > count2)
					flag = pare;
				else if (count1 < count2)
					flag = -pare;
				return flag;
			}
		});
	}
	/**
	 * 根据学院id找到该学院的课程集合并在原集合中删除
	 * @param list 全部学院课程集合
	 * @param deptId 组织机构id
	 * @return List<Map<String,Object>>
	 */
	private List<Map<String,Object>> getCourseListByDeptId(List<Map<String,Object>> list,String deptId){
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map:list){
			String id = MapUtils.getString(map, "dept");
			if(id.equals(deptId)){
				result.add(map);
			}
		}
		list.removeAll(result);
		return result;
	}
	/**
	 * 合并完全相同的记录
	 * 
	 * @param list
	 * @return List<Map<String,Object>>
	 */
	private List<Map<String, Object>> mergeList(List<Map<String, Object>> list,Boolean isXx) {
		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
		if(list == null || list.isEmpty()){
			return retList;
		}
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			Map<String, Object> newMap = isExistSame(list, map, retList,isXx);
			if (null == newMap) {
				continue;
			} else {
				retList.add(newMap);
			}
		}
		return retList;
	}

	/**
	 * 合并完全相同的记录
	 * @param i
	 * @param value
	 * @param name
	 * @param list
	 * @return Map<String,Object>
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> isExistSame(List<Map<String, Object>> list,
			Map<String, Object> map, List<Map<String, Object>> result,Boolean isXx) {
		Map<String,Object> backVal = new HashMap<String, Object>();
		List<String> courseList = new ArrayList<String>();
		String kcid = MapUtils.getString(map, "kc"),
				 xq = MapUtils.getString(map, "xq"),
				 xn = MapUtils.getString(map, "xn"),
			   ycXq = MapUtils.getString(map, "ycxq"),
			   dept = isXx?null:MapUtils.getString(map, "dept");
		int lastXq = 0,nextXq = 0;
		kcid = kcid+","+xn+","+xq+","+ycXq;
		List<String> bjList = isXx? new ArrayList<String>():(List<String>) MapUtils.getObject(map, "bjList"),
	                 stuList = (List<String>) MapUtils.getObject(map, "stuList"),
	                 allStuList = (List<String>)MapUtils.getObject(map, "allStuList");
		int size = stuList.size();
		for (Map<String, Object> temp : result) {
			List<String> course_1 = (List<String>) temp.get("kcList");
			if (course_1.contains(kcid)) {
				return null;
			}
		}
		for (int j = 0; j < list.size(); j++) {
			Map<String, Object> m = list.get(j);
			String kcid_1 = MapUtils.getString(m, "kc"),
					 xq_1 = MapUtils.getString(m, "xq"),
					 xn_1 = MapUtils.getString(m, "xn"),
				   ycXq_1 = MapUtils.getString(m, "ycxq");
			List<String> bjList_1 = isXx?new ArrayList<String>():(List<String>) MapUtils.getObject(m, "bjList"),
		                 stuList_1 = (List<String>)MapUtils.getObject(m, "stuList"),
		                 allStuList_1 = (List<String>)MapUtils.getObject(m, "allStuList");
			kcid_1 = kcid_1+","+xn_1+","+xq_1+","+ycXq_1;
			if((bjList.containsAll(bjList_1) && bjList_1.containsAll(bjList))|| isXx){
				courseList.add(kcid_1);
				List<String> tempList = new ArrayList<String>();
				if(stuList_1 !=null && !stuList_1.isEmpty()){
					tempList.addAll(stuList_1);
				}
				tempList.retainAll(stuList);
				if(tempList == null||tempList.size() < size*Merge_Scale || tempList.size()<=Stu_Count_least){
					continue;
				}else if(tempList.size()>= size*Merge_Scale && tempList.size()>Stu_Count_least){
					 stuList.retainAll(tempList);
					 allStuList.retainAll(allStuList_1);
					if("last".equals(ycXq_1)){
						lastXq++;
					}else if("next".equals(ycXq_1)){
						nextXq++;
					}
				}
			}
		}
		backVal.put("kcList", courseList);
//		backVal.put("bjList", bjList);
		backVal.put("stuList", stuList);
		backVal.put("stu", stuList.size());
		backVal.put("allStu", allStuList.size());
		backVal.put("lastXq", lastXq);
		backVal.put("nextXq", nextXq);
		backVal.put("dept", isXx?null:dept);
		backVal.put("allStuList", allStuList);
		return backVal;
	}
	/**
	 * 根据传过来的课程集合，找到他们的最优组合
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String,Object> getZyList(List<Map<String,Object>> list){
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>(),
				                 noHg = new ArrayList<Map<String,Object>>();
		if(list == null || list.isEmpty()){
			return null;
		}else{
			for(int i = 0;i<list.size();i++){
				Map<String,Object> map= list.get(i);
				if(map.containsKey("zyKcList") || MapUtils.getIntValue(map, "nextXq")==0 ){
					if(MapUtils.getIntValue(map, "nextXq")!=0){
						result.add(map);
					}else{
						map.put("zyKcList", new ArrayList<String>());
					}
					continue;
				}else{
					List<String> kcList = (List<String>) MapUtils.getObject(map, "kcList"),
						         zyKcList = new ArrayList<String>();
				 for(String kcX : kcList){
					 String[] ary = kcX.split(",",-1);
					 if(ary[6].equals("next")){
						 zyKcList.add(kcX); 
					 }
				 }
					map.put("zyKcList", zyKcList);
				}
				List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
				list1.addAll(list);
				list1.remove(list.get(i));
				Map<String,Object> zyMap = getZyMap(list1,map);
				if(zyMap == null ){
					noHg.add(map);
					continue;
				}
				result.add(zyMap);
			}
		}
		Map<String,Object> reMap = new HashMap<String, Object>();
		reMap.put("isHg", result);
		reMap.put("noHg", noHg);
		return reMap;
	}
	/**
	 * 获取某课程的最优组合
	 * @param list 所有课程集合
	 * @param index 某课程在list中的index
	 * @return Map<String,Object>
	 */
	@SuppressWarnings("unchecked")
	private Map<String,Object> getZyMap(List<Map<String,Object>> list,Map<String,Object> courseMap){
		// TODO 现在这地方是循环到达标的组合就直接设置为最优并退出循环；以后要优化成每次都得到一个
		// 达标的集合和不达标的集合，从不达标的集合中继续循环，直到找到一个小于达标集合的学生人数最大值的退出循环。
		if(list == null || list.isEmpty() || courseMap == null || courseMap.isEmpty()){
			return null;
		}else{
			List<Map<String,Object>> list_1 = new ArrayList<Map<String,Object>>();
			list_1.addAll(list);
			List<String> stuList = (List<String>) MapUtils.getObject(courseMap, "stuList"),
				     kcList = (List<String>) MapUtils.getObject(courseMap, "kcList"),
				     allStuList = (List<String>) MapUtils.getObject(courseMap, "allStuList"),
				     zyKcList = (List<String>) MapUtils.getObject(courseMap, "zyKcList");
			int lastXq = MapUtils.getIntValue(courseMap, "lastXq"),
				nextXq = MapUtils.getIntValue(courseMap, "nextXq");
			String dept = MapUtils.getString(courseMap,"dept"),
			     deptType = MapUtils.getString(courseMap,"deptType");
			if(stuList == null || stuList.isEmpty() ){
				return null;
			}
			List<Map<String,Object>> dbList = new ArrayList<Map<String,Object>>(),
					                 bdbList = new ArrayList<Map<String,Object>>();
			for(int i= 0;i<list_1.size();i++){
				Map<String,Object> temp = new HashMap<String, Object>();
				if(list_1.get(i) == null || list_1.get(i).isEmpty()){
					continue;
				}else{
					temp.putAll(list_1.get(i));
				}
				List<String> stuList_1 = (List<String>) MapUtils.getObject(temp, "stuList"),
					         kcList_1 = (List<String>) MapUtils.getObject(temp, "kcList"),
					         allStuList_1 = (List<String>) MapUtils.getObject(temp, "allStuList"),
					         stuList_1_1 = new ArrayList<String>(),
					         kcList_1_1 = new ArrayList<String>(),
					         allStuList_1_1 = new ArrayList<String>();
				stuList_1_1.addAll(stuList_1);
				kcList_1_1.addAll(kcList_1);
				allStuList_1_1.addAll(allStuList_1);
				
				int lastXq_1 = MapUtils.getIntValue(temp, "lastXq"),
				    nextXq_1 = MapUtils.getIntValue(temp, "nextXq");
				if(stuList_1_1 == null || stuList_1_1.isEmpty()){
					continue;
				}
				stuList_1_1.retainAll(stuList);
				allStuList_1_1.retainAll(allStuList);
				kcList_1_1.addAll(kcList);
				Map<String,Object> temp_1 = new HashMap<String, Object>();
				temp_1.put("lastXq", lastXq_1+lastXq);
				temp_1.put("nextXq", nextXq_1+nextXq);
				temp_1.put("stu", stuList_1_1.size());
				temp_1.put("allStu", allStuList_1_1.size());
				temp_1.put("index", i);
				temp_1.put("stuList", stuList_1_1);
				temp_1.put("allStuList", allStuList_1_1);
				temp_1.put("kcList", kcList_1_1);
				temp_1.put("dept", dept);
				temp_1.put("deptType", deptType);
				temp_1.put("zyKcList", zyKcList);
				if((lastXq+lastXq_1)>=Last_Xq_Min_Size && (nextXq+nextXq_1)>=Next_Xq_Min_Size && stuList_1.size() > Stu_Count_least){
					dbList.add(temp_1);
				}else{
					bdbList.add(temp_1);
				}
			}
			if(dbList.size() > 0){
				compareCount(dbList, "stu", false);
				return dbList.get(0);
			}else{
				compareCount(bdbList, "stu", false);
				if(bdbList.size() ==0){
					return null;
				}else if(MapUtils.getIntValue(bdbList.get(0), "stu")<= Stu_Count_least){
					return null;
				}
				List<Map<String,Object>> tempList_1 = new ArrayList<Map<String,Object>>();
				for(int j = 0;j<bdbList.size();j++){
					List<Map<String,Object>> list_2 = new ArrayList<Map<String,Object>>();
					Map<String,Object> bdbMap = bdbList.get(j);
					int index = MapUtils.getIntValue(bdbMap, "index");
					list_2.addAll(list_1);
					list_2.remove(list_1.get(index));
					Map<String,Object> temp_2 = getZyMap(list_2, bdbMap);
					if(temp_2 != null){
						tempList_1.add(temp_2);
					}
				}
				if(tempList_1.size() >0){
					compareCount(tempList_1, "stu", false);
					return tempList_1.get(0);
				}else{
					return null;
				}
			}
		}
	}
	@SuppressWarnings("unchecked")
	public TStuScorepredTermGroup castMapToGroup(Map<String,Object> map,String start_schoolYear,String start_termCode,
			String end_schoolYear,String end_termCode,int truth,int isXx){
			TStuScorepredTermGroup group = new TStuScorepredTermGroup();
			String deptType = MapUtils.getString(map, "deptType"),
				   deptId = MapUtils.getString(map,"dept"),
				   grade = MapUtils.getString(map, "grade");
			List<String> kcList = (List<String>) MapUtils.getObject(map, "kcList"),
					     zyKcList = (List<String>) MapUtils.getObject(map, "zyKcList");
			List<TStuScorepredTermGpMd> kcGroupList= new ArrayList<TStuScorepredTermGpMd>();
			int i = 0,j = kcList.size(); 
			for(String kc : kcList){
				TStuScorepredTermGpMd kcGroup = new TStuScorepredTermGpMd();
				String[] ary = kc.split(",",-1);
				kcGroup.setCourseAttrCode((ary[1].equals("")||ary[1].equals("null"))?null:ary[1]);
				kcGroup.setCourseCategoryCode((ary[3].equals("")||ary[3].equals("null"))?null:ary[3]);
				kcGroup.setCourseId(ary[0]);
				kcGroup.setCourseNatureCode((ary[2].equals("")||ary[2].equals("null"))?null:ary[2]);
				kcGroup.setSchoolYear(ary[4]);kcGroup.setTermCode(ary[5]);
				if(ary.length == 8){
					kcGroup.setMoldId((ary[7].equals("")||ary[7].equals("null"))?null:ary[7]);	
				}else{
				    kcGroup.setMoldId(null);
				}
				if(ary[6].equals("next")){
					kcGroup.setIspredict(1);
					kcGroup.setOrder_(j);
					j++;
				}else{
					kcGroup.setIspredict(0);
					kcGroup.setOrder_(i);
					i++;
				}
				if(zyKcList.contains(kc)){
					kcGroup.setIssave(1);
				}else{
					kcGroup.setIssave(0);
				}
				kcGroupList.add(kcGroup);
			}
			group.setDeptType(deptType);
			group.setDeptValue(deptId);
			group.setEndSchoolyear(end_schoolYear);
			group.setEndTermCode(end_termCode);
			group.setGrade(EduUtils.getGradeByYearAndEnrollGrade(end_schoolYear,grade));
			group.setList(kcGroupList);
			group.setStartSchoolyear(start_schoolYear);
			group.setStartTermcode(start_termCode);
			group.setTruth(truth);
			group.setIselective(isXx);
		return group;
	}
	@SuppressWarnings("unchecked")
	public TStuScorepredTermGroup castMapToGroup(Map<String,Object> map,String start_schoolYear,String start_termCode,
			String end_schoolYear,String end_termCode,int truth,int isXx,String grade){
			TStuScorepredTermGroup group = new TStuScorepredTermGroup();
			String deptType = MapUtils.getString(map, "deptType"),
				   deptId = isXx == 1 ? null:MapUtils.getString(map,"dept");
			List<String> kcList  = (List<String>) MapUtils.getObject(map, "kcList");
			List<TStuScorepredTermGpMd> kcGroupList= new ArrayList<TStuScorepredTermGpMd>();
			int i = 0; 
			for(String kc : kcList){
				TStuScorepredTermGpMd kcGroup = new TStuScorepredTermGpMd();
				String[] ary = kc.split(",",-1);
				kcGroup.setCourseAttrCode((ary[1].equals("")||ary[1].equals("null"))?null:ary[1]);
				kcGroup.setCourseCategoryCode((ary[3].equals("")||ary[3].equals("null"))?null:ary[3]);
				kcGroup.setCourseId(ary[0]);
				kcGroup.setCourseNatureCode((ary[2].equals("")||ary[2].equals("null"))?null:ary[2]);
				kcGroup.setSchoolYear(ary[4]);kcGroup.setTermCode(ary[5]);
				kcGroup.setMoldId(null);
				kcGroup.setIspredict(0);
				kcGroup.setOrder_(i);
				kcGroup.setIssave(0);
				kcGroupList.add(kcGroup);
				i++;
			}
			group.setDeptType(deptType);
			group.setDeptValue(deptId);
			group.setEndSchoolyear(end_schoolYear);
			group.setEndTermCode(end_termCode);
			group.setGrade(EduUtils.getGradeByYearAndEnrollGrade(end_schoolYear,grade));
			group.setList(kcGroupList);
			group.setStartSchoolyear(start_schoolYear);
			group.setStartTermcode(start_termCode);
			group.setTruth(truth);
			group.setIselective(isXx);
		return group;
	}
	@SuppressWarnings("unchecked")
	public List<TStuScorepredTermGroup> castAndSaveListMapToGroup(List<Map<String,Object>> list,String start_schoolYear,String start_termCode,
			String end_schoolYear,String end_termCode,int truth,int isXx){
		List<TStuScorepredTermGpMd> kcGroupAll = new ArrayList<TStuScorepredTermGpMd>();
		List<TStuScorepredTermGroup> groupAll = new ArrayList<TStuScorepredTermGroup>();
		for(Map<String,Object> map : list){
			TStuScorepredTermGroup group = new TStuScorepredTermGroup();
			
			String deptType = MapUtils.getString(map, "deptType"),
				   deptId = MapUtils.getString(map,"dept"),
				   grade = MapUtils.getString(map, "grade");
			List<String> kcList = (List<String>) MapUtils.getObject(map, "kcList"),
					     zyKcList = (List<String>) MapUtils.getObject(map, "zyKcList");
			List<TStuScorepredTermGpMd> kcGroupList= new ArrayList<TStuScorepredTermGpMd>();
			int i = 0,j = kcList.size(); 
			for(String kc : kcList){
				TStuScorepredTermGpMd kcGroup = new TStuScorepredTermGpMd();
				String[] ary = kc.split(",",-1);
				kcGroup.setCourseAttrCode((ary[1].equals("")||ary[1].equals("null"))?null:ary[1]);
				kcGroup.setCourseCategoryCode((ary[3].equals("")||ary[3].equals("null"))?null:ary[3]);
				kcGroup.setCourseId(ary[0]);
				kcGroup.setCourseNatureCode((ary[2].equals("")||ary[2].equals("null"))?null:ary[2]);
				kcGroup.setSchoolYear(ary[4]);kcGroup.setTermCode(ary[5]);
				kcGroup.setMoldId(null);
				if(ary[6].equals("next")){
					kcGroup.setIspredict(1);
					kcGroup.setOrder_(j);
					j++;
				}else{
					kcGroup.setIspredict(0);
					kcGroup.setOrder_(i);
					i++;
				}
				if(zyKcList.contains(kc)){
					kcGroup.setIssave(1);
				}else{
					kcGroup.setIssave(0);
				}
			}
			group.setDeptType(deptType);
			group.setDeptValue(deptId);
			group.setEndSchoolyear(end_schoolYear);
			group.setEndTermCode(end_termCode);
			group.setGrade(EduUtils.getGradeByYearAndEnrollGrade(end_schoolYear,grade));
			group.setList(kcGroupList);
			group.setStartSchoolyear(start_schoolYear);
			group.setStartTermcode(start_termCode);
			group.setTruth(truth);
			group.setIselective(isXx);
			saveGroupData(group);
			for(TStuScorepredTermGpMd kcGroup_1:kcGroupList){
				kcGroup_1.setGroupId(group.getId());
			}
			kcGroupAll.addAll(kcGroupList);
			groupAll.add(group);
		}
		saveGroupData(kcGroupAll);
		return groupAll;
	}
	@SuppressWarnings("unchecked")
	private List<String> getZyByStu(Map<String,Object> zyMap){
		List<String> allStuList = (List<String>) MapUtils.getObject(zyMap, "allStuList"),
				     result = new ArrayList<String>();
		for(String stu : allStuList){
			String[] ary = stu.split(",",-1);
			if(ary[1].equals("") || ary[1].equals("null")){
				continue;
			}else if(!result.contains(ary[1])){
				result.add(ary[1]);
			}
		}
		return result;
	}
	private List<Map<String,Object>> getKzZyList(List<Map<String,Object>> kzList){
       //TODO 扩展集合的组合，暂时做成完全一样的组合，以后待优化
		if(kzList==null || kzList.isEmpty()){
        	return new ArrayList<Map<String,Object>>();
        }
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		for(int i=0;i<kzList.size();i++){
			Map<String, Object> map = kzList.get(i);
			Map<String, Object> newMap = isRetain(kzList, map, result);
			if (null == newMap) {
				continue;
			} else {
				result.add(newMap);
			}
        }
		return result;
	}
	@SuppressWarnings("unchecked")
	private Map<String,Object> isRetain(List<Map<String,Object>> kzList,Map<String,Object> map,List<Map<String,Object>> result){
        List<String> kcList = (List<String>) MapUtils.getObject(map, "kcList"),
        		     stuList = (List<String>) MapUtils.getObject(map, "stuList"),
        		     allStuList = (List<String>) MapUtils.getObject(map, "allStuList");
        String dept = MapUtils.getString(map, "dept");
        int stu = MapUtils.getIntValue(map, "stu");
        int allStu = MapUtils.getIntValue(map, "allStu");
        List<Map<String,Object>> kzList_1 = new ArrayList<Map<String,Object>>();
        kzList_1.addAll(kzList);
        for(Map<String,Object> reMap : result){
			 List<String> kcList_1 = (List<String>) MapUtils.getObject(reMap, "kcList");
             if(kcList_1.containsAll(kcList) && kcList.containsAll(kcList_1)){
            	 return null;
             }
		}
		for(int i=0;i<kzList_1.size();i++){
			  if(kzList_1.contains(map)){
				continue;
			  }
			  List<String> kcList_2 = (List<String>) MapUtils.getObject(map, "kcList"),
	        		     stuList_2 = (List<String>) MapUtils.getObject(map, "stuList"),
	        		     allStuList_2 = (List<String>) MapUtils.getObject(map, "allStuList");
	        String dept_2 = MapUtils.getString(map, "dept");
	        int stu_2 = MapUtils.getIntValue(map, "stu");
	        int allStu_2 = MapUtils.getIntValue(map, "allStu");
	        if(kcList_2.containsAll(kcList) && kcList.containsAll(kcList_2)){
	            
	        	  dept += ","+dept_2;
	              stu += stu_2;
	              allStu += allStu_2;
	              stuList.addAll(stuList_2);
	              allStuList.addAll(allStuList_2);
	              map.put("dept", dept);
	              map.put("stu", stu);
	              map.put("allStu", allStu);
	        }
        }
		
		return map;
	}
	@SuppressWarnings("unchecked")
	private Map<String,Object> getXxCourseGroup(String start_schoolYear,String start_termCode,String end_schoolYear,String end_termCode,String grade,String yc_start_schoolYear,String yc_start_termCode,
			 String yc_end_schoolYear,String yc_end_termCode){
		List<TStuScorepredTermGroup> groupList = new ArrayList<TStuScorepredTermGroup>(),
				                     noHgGroupList = new ArrayList<TStuScorepredTermGroup>();
		Map<String,Object> reMap= new HashMap<String, Object>();
		List<Map<String,Object>> allCourseList = scorePredictGroupDao.queryAllCourseList(start_schoolYear, start_termCode, end_schoolYear, end_termCode, grade,yc_start_schoolYear,yc_start_termCode,
				 yc_end_schoolYear,yc_end_termCode,true),
				                 otherCourseList = new ArrayList<Map<String,Object>>();
    	List<String> courseSize = new ArrayList<String>();
		for(Map<String,Object> allCourseMap : allCourseList){
    		 String sxxq = MapUtils.getString(allCourseMap,"ycxq"),
		    		  kcMx = MapUtils.getString(allCourseMap,"kc"),
		    		  xnM = MapUtils.getString(allCourseMap,"xn"),
		    		  xqM = MapUtils.getString(allCourseMap,"xq");
    		 int courseStuCount = MapUtils.getIntValue(allCourseMap,"stu");
    		 allCourseMap.put("deptType", Type_XX);
    		 if(!courseSize.contains(sxxq)){
    			 courseSize.add(sxxq);
    		 }
    		 if(courseStuCount <= Stu_Count_least){
	    			 if(sxxq.equals("next")){
	    				 String noHgStr = kcMx+","+xnM+","+xqM+",next";
	        			 List<String> noHgZyList = new ArrayList<String>();
	        			 noHgZyList.add(noHgStr);
	        			 allCourseMap.put("zyKcList", noHgZyList);
	        			 allCourseMap.put("kcList", noHgZyList);
	        			 otherCourseList.add(allCourseMap);
	        			 TStuScorepredTermGroup noHgG = castMapToGroup(allCourseMap, start_schoolYear, start_termCode, end_schoolYear, end_termCode, 1, 1, grade);
	        			 noHgGroupList.add(noHgG); 
	    			 }else{
	    				 otherCourseList.add(allCourseMap); 
	    			 }
	    		 }
	    	 }
	    	 allCourseList.removeAll(otherCourseList);
	    	 if(allCourseList == null || allCourseList.isEmpty()){
	    		 reMap.put("isHg", groupList);
	     		 reMap.put("noHg", noHgGroupList);
	    		 return reMap;
			 }
	 		 if(courseSize.size() == 1 && courseSize.get(0).equals("next")){
				 for(Map<String,Object> yxKcMap_1 : allCourseList){
					 String sxxq = MapUtils.getString(yxKcMap_1,"ycxq"),
				    		  kcMx = MapUtils.getString(yxKcMap_1,"kc"),
				    		  xnM = MapUtils.getString(yxKcMap_1,"xn"),
				    		  xqM = MapUtils.getString(yxKcMap_1,"xq");
						 String noHgStr_1 = kcMx+","+xnM+","+xqM+","+sxxq;
	        			 List<String> noHgZyList = new ArrayList<String>();
	        			 noHgZyList.add(noHgStr_1);
	        			 yxKcMap_1.put("zyKcList", noHgZyList);
	        			 yxKcMap_1.put("kcList", noHgZyList);
	        			 TStuScorepredTermGroup noHgG = castMapToGroup(yxKcMap_1, start_schoolYear, start_termCode, end_schoolYear, end_termCode, 1, 1, grade);
	        			 noHgGroupList.add(noHgG);  
				 }
				 reMap.put("isHg", groupList);
	    		 reMap.put("noHg", noHgGroupList);
				 return reMap;
			 }else if(courseSize.size() == 1 && courseSize.get(0).equals("last")){
				 reMap.put("isHg", groupList);
	    		 reMap.put("noHg", noHgGroupList);
				 return reMap;
			 }
    		 List<Map<String,Object>> courseMergeList = new ArrayList<Map<String,Object>>();
    		 compareCount(allCourseList, "stu", false);
    		 int max = MapUtils.getIntValue(allCourseList.get(0), "stu");
    	     Double x = max*Group_Scale;
    		 for(Double i =x,j=Double.valueOf(max+1);j>Stu_Count_least;){
    			 List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
    			 for(Map<String,Object> allCourseMap : allCourseList){
    				 allCourseMap.put("deptType", Type_XX);
    				int stu = MapUtils.getIntValue(allCourseMap, "stu"); 
    				if(stu>=i-10 && stu <j){
    					allCourseMap = scorePredictGroupDao.getXxCourseStuList(allCourseMap, grade,true);
    					tempList.add(allCourseMap);
    				}
    			 }
    			 allCourseList.removeAll(tempList);
    			 tempList=mergeList(tempList,true);
 				 courseMergeList.addAll(tempList);
 				 if(allCourseList.size() == 0){
 					 break;
 				 }
    			 compareCount(allCourseList, "stu", false);
    			 int max_1 = MapUtils.getIntValue(allCourseList.get(0), "stu");
    			 j = Double.valueOf(max_1+1);
    			 i = max_1*Group_Scale;
    		 }
    		 for(Map<String,Object> courseMergeMap:courseMergeList){
    			 Integer lastXqCount = MapUtils.getInteger(courseMergeMap, "lastXq"),
    					 nextXqCount = MapUtils.getInteger(courseMergeMap, "nextXq");
    			 int stu = MapUtils.getIntValue(courseMergeMap, "stu");
    			 lastXqCount = lastXqCount == null ? 0 :lastXqCount;
    			 nextXqCount = nextXqCount == null ? 0 :nextXqCount;
    			 if(lastXqCount >= Last_Xq_Min_Size && nextXqCount >= Next_Xq_Min_Size && stu>Stu_Count_least){
    				 List<String> kcList_x = (List<String>) MapUtils.getObject(courseMergeMap, "kcList"),
    						      zyKcList = new ArrayList<String>();
    				 for(String kcX : kcList_x){
    					 String[] ary = kcX.split(",",-1);
    					 if(ary[6].equals("next")){
    						 zyKcList.add(kcX); 
    					 }
    				 }
    				 courseMergeMap.put("zyKcList",zyKcList);
    			 }
    		 }
    		 compareCount(courseMergeList, "stu", false);
    		 Map<String,Object> zyMapHgBhg = getZyList(courseMergeList);
    		 List<Map<String,Object>> zyList = (List<Map<String, Object>>) zyMapHgBhg.get("isHg"),
	                  newZyList = new ArrayList<Map<String,Object>>(),
	                  newZyList_1 = new ArrayList<Map<String,Object>>(),
	                  wzyList = (List<Map<String, Object>>) zyMapHgBhg.get("noHg");
    		 List<Map<String,Object>> zyList_bb = getSplitList(zyList);
    		 zyList.addAll(zyList_bb);
    		 Map<String,Object> hgbhgMap = getZyMapByList(zyList, courseMergeList, grade, Type_XX, start_schoolYear, start_termCode, end_schoolYear, end_termCode, true, 1, 1,true),
				               hgbhgMap_1 = getZyMapByList(wzyList, courseMergeList, grade,Type_XX, start_schoolYear, start_termCode, end_schoolYear, end_termCode, false, 1, 1,true);
			newZyList = (List<Map<String, Object>>) MapUtils.getObject(hgbhgMap, "isHg");
			newZyList_1 = (List<Map<String, Object>>) MapUtils.getObject(hgbhgMap_1, "isHg");
			List<TStuScorepredTermGroup> noHgZyList =  (List<TStuScorepredTermGroup>) MapUtils.getObject(hgbhgMap, "noHg"),
				                      noHgZyList_1 = (List<TStuScorepredTermGroup>) MapUtils.getObject(hgbhgMap_1, "noHg");
			noHgGroupList.addAll(noHgZyList);noHgGroupList.addAll(noHgZyList_1);
			newZyList.addAll(newZyList_1);
//			 List<Map<String,Object>> newZyList_bb = getSplitList(newZyList);
//			 newZyList.addAll(newZyList_bb);
			for(Map<String,Object> newZy : newZyList){
				newZy.put("grade", grade);
			}
    		List<TStuScorepredTermGroup> zyGroup = castAndSaveListMapToGroup(newZyList, start_schoolYear, start_termCode, end_schoolYear, end_termCode, 1,1);
    		groupList.addAll(zyGroup);
    		reMap.put("isHg", groupList);
    		reMap.put("noHg", noHgGroupList);
    	 return reMap;
	}
	@SuppressWarnings("unchecked")
	private Map<String,Object> getBxCourseGroup(String start_schoolYear,String start_termCode,String end_schoolYear,String end_termCode,String grade,String yc_start_schoolYear,String yc_start_termCode,
			 String yc_end_schoolYear,String yc_end_termCode){
		 Map<String,Object> reMap = new HashMap<String, Object>(); 
		 List<TStuScorepredTermGroup> groupList = new ArrayList<TStuScorepredTermGroup>(),
				                      noHgGroupList = new ArrayList<TStuScorepredTermGroup>();
		 List<String> deptList = PmsUtils.getPmsAll();
		 List<Map<String,Object>> yxList = businessDao.queryYxList(deptList).subList(0, 1); 
		 List<Map<String,Object>> allCourseList = scorePredictGroupDao.queryAllCourseList(start_schoolYear, start_termCode, end_schoolYear, end_termCode, grade,yc_start_schoolYear,yc_start_termCode,
				 yc_end_schoolYear,yc_end_termCode,false);
    	 if(allCourseList == null || allCourseList.isEmpty()){
    		 reMap.put("isHg", groupList);
    		 reMap.put("noHg", noHgGroupList);
			 return reMap;
		 }
    	 List<Map<String,Object>> other_allCourseList= new ArrayList<Map<String,Object>>();
    	 for(Map<String,Object> allCourseMap : allCourseList){
    		 String sxxq = MapUtils.getString(allCourseMap,"ycxq"),
		    		  kcMx = MapUtils.getString(allCourseMap,"kc"),
		    		  xnM = MapUtils.getString(allCourseMap,"xn"),
		    		  xqM = MapUtils.getString(allCourseMap,"xq");
    		 int courseStuCount = MapUtils.getIntValue(allCourseMap,"stu");
    		 allCourseMap.put("deptType", Type_YX);
    		 if(courseStuCount <= Stu_Count_least){
    			 if(sxxq.equals("next")){
    				 String noHgStr = kcMx+","+xnM+","+xqM+",next";
        			 List<String> noHgZyList = new ArrayList<String>();
        			 noHgZyList.add(noHgStr);
        			 allCourseMap.put("zyKcList", noHgZyList);
        			 allCourseMap.put("kcList", noHgZyList);
        			 other_allCourseList.add(allCourseMap);
        			 TStuScorepredTermGroup noHgG = castMapToGroup(allCourseMap, start_schoolYear, start_termCode, end_schoolYear, end_termCode, 1, 0, grade);
        			 noHgGroupList.add(noHgG); 
    			 }else{
    				 other_allCourseList.add(allCourseMap);
    			 }
    	
    		 }
    	 }
    	 allCourseList.removeAll(other_allCourseList);
    	 List<Map<String,Object>> kzAllList = new ArrayList<Map<String,Object>>();
    	 for(Map<String,Object> yxMap:yxList){
    		 String deptId = MapUtils.getString(yxMap, "id");
    		 List<String> deptList_1 = businessService.packageDeptListById(deptId);
    		 List<AdvancedParam> advancedParamsList = new ArrayList<AdvancedParam>();
    		 AdvancedParam gradeAdp = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_ENROLL_GRADE, grade);
    		 AdvancedUtil.add(advancedParamsList, gradeAdp);
    		 String deptSql = businessDao.getStuSql(deptList_1, advancedParamsList);
    		 int deptCount = baseDao.queryForCount(deptSql);
    		 List<Map<String,Object>> yxCourseList = getCourseListByDeptId(allCourseList, deptId),
    				                  courseMergeList = new ArrayList<Map<String,Object>>();
    		 List<String> ycXqList = new ArrayList<String>();
    		 for(Map<String,Object> yxKcMap : yxCourseList){
    			 String ycXqSS = MapUtils.getString(yxKcMap, "ycxq");
    			 if(!ycXqList.contains(ycXqSS)){
    				 ycXqList.add(ycXqSS);
    			 }
    		 }
    		 if(ycXqList.size() == 1 && ycXqList.get(0).equals("next")){
    			 for(Map<String,Object> yxKcMap_1 : yxCourseList){
    				 String sxxq = MapUtils.getString(yxKcMap_1,"ycxq"),
    			    		  kcMx = MapUtils.getString(yxKcMap_1,"kc"),
    			    		  xnM = MapUtils.getString(yxKcMap_1,"xn"),
    			    		  xqM = MapUtils.getString(yxKcMap_1,"xq");
    					 String noHgStr_1 = kcMx+","+xnM+","+xqM+","+sxxq;
            			 List<String> noHgZyList = new ArrayList<String>();
            			 noHgZyList.add(noHgStr_1);
            			 yxKcMap_1.put("zyKcList", noHgZyList);
            			 yxKcMap_1.put("kcList", noHgZyList);
            			 TStuScorepredTermGroup noHgG = castMapToGroup(yxKcMap_1, start_schoolYear, start_termCode, end_schoolYear, end_termCode, 1, 0, grade);
            			 noHgGroupList.add(noHgG);  
    			 }
    			 reMap.put("isHg", groupList);
        		 reMap.put("noHg", noHgGroupList);
    			 return reMap;
    		 }else if(ycXqList.size() == 1 && ycXqList.get(0).equals("last")){
    			 reMap.put("isHg", groupList);
        		 reMap.put("noHg", noHgGroupList);
    			 return reMap;
    		 }
    		 compareCount(yxCourseList, "stu", false);
    		 int max = MapUtils.getIntValue(yxCourseList.get(0), "stu");
    	     Double x = max*Group_Scale;
    		 for(Double i =x,j=Double.valueOf(max+1);j>Stu_Count_least;){
    			 List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
    			 for(Map<String,Object> yxCourseMap : yxCourseList){
    				 yxCourseMap.put("deptType", Type_YX);
    				int stu = MapUtils.getIntValue(yxCourseMap, "stu"); 
    				if(stu>=i-10 && stu <j){
    					yxCourseMap = scorePredictGroupDao.getBjXsList(yxCourseMap,grade,true);
    					tempList.add(yxCourseMap);
    				}
    			 }
    			 yxCourseList.removeAll(tempList);
    			 tempList=mergeList(tempList,false);
 				 courseMergeList.addAll(tempList);
 				 if(yxCourseList.size() == 0){
 					 break;
 				 }
    			 compareCount(yxCourseList, "stu", false);
    			 int max_1 = MapUtils.getIntValue(yxCourseList.get(0), "stu");
    			 j = Double.valueOf(max_1+1);
    			 i = max_1*Group_Scale;
    		 }
    		 for(Map<String,Object> courseMergeMap:courseMergeList){
    			 Integer lastXqCount = MapUtils.getInteger(courseMergeMap, "lastXq"),
    					 nextXqCount = MapUtils.getInteger(courseMergeMap, "nextXq");
    			 int stu = MapUtils.getIntValue(courseMergeMap, "stu");
    			 lastXqCount = lastXqCount == null ? 0 :lastXqCount;
    			 nextXqCount = nextXqCount == null ? 0 :nextXqCount;
    			 if(lastXqCount >= Last_Xq_Min_Size && nextXqCount >= Next_Xq_Min_Size && stu>Stu_Count_least){
    				 List<String> kcList_x = (List<String>) MapUtils.getObject(courseMergeMap, "kcList"),
    						      zyKcList = new ArrayList<String>();
    				 for(String kcX : kcList_x){
    					 String[] ary = kcX.split(",",-1);
    					 if(ary[6].equals("next")){
    						 zyKcList.add(kcX); 
    					 }
    				 }
    				 courseMergeMap.put("zyKcList",zyKcList);
    			 }
    		 }
    		 compareCount(courseMergeList, "stu", false);
    		 System.out.println(courseMergeList.size());
    		 Map<String,Object> zyMapHgBhg = getZyList(courseMergeList);
    		 List<Map<String,Object>> zyList = (List<Map<String, Object>>) zyMapHgBhg.get("isHg"),
	                  tempList_1= new ArrayList<Map<String,Object>>(),
	                  newZyList = new ArrayList<Map<String,Object>>(),
	                  newZyList_1 = new ArrayList<Map<String,Object>>(),
	                  wzyList = (List<Map<String, Object>>) zyMapHgBhg.get("noHg");
    		 List<Map<String,Object>> zyList_bb = getSplitList(zyList);
    		 zyList.addAll(zyList_bb);
    		 for(Map<String,Object> zyMap_1 : zyList){
			    int stu_zy = MapUtils.getIntValue(zyMap_1,"allStu");
				if(stu_zy >= deptCount*Kz_Stu_Count_Scale){
					 tempList_1.add(zyMap_1);
				}
			}
			Map<String,Object> hgbhgMap = getZyMapByList(zyList, courseMergeList, grade, Type_YX, start_schoolYear, start_termCode, end_schoolYear, end_termCode, true, 1, 0,true),
				            hgbhgMap_1 = getZyMapByList(wzyList, courseMergeList, grade,Type_YX, start_schoolYear, start_termCode, end_schoolYear, end_termCode, false, 1, 0,true);
			newZyList = (List<Map<String, Object>>) MapUtils.getObject(hgbhgMap, "isHg");
			newZyList_1 = (List<Map<String, Object>>) MapUtils.getObject(hgbhgMap_1, "isHg");
			List<TStuScorepredTermGroup> noHgZyList =  (List<TStuScorepredTermGroup>) MapUtils.getObject(hgbhgMap, "noHg"),
				                      noHgZyList_1 = (List<TStuScorepredTermGroup>) MapUtils.getObject(hgbhgMap_1, "noHg");
			noHgGroupList.addAll(noHgZyList);noHgGroupList.addAll(noHgZyList_1);
			newZyList.addAll(newZyList_1);
//			 List<Map<String,Object>> newZyList_bb = getSplitList(newZyList);
//			 newZyList.addAll(newZyList_bb);
    		 if(tempList_1.size()>0){
    			 zyList.removeAll(tempList_1);
    			 kzAllList.addAll(tempList_1);
    		 }
    		 for(Map<String,Object> zyMap : newZyList){
    			 zyMap.put("grade", grade);
    			 List<String> stuZyList = getZyByStu(zyMap);
    			 if(stuZyList != null && stuZyList.size() == 1){
    				 zyMap.put("deptType", Type_ZY);
    			 }else{
    				 zyMap.put("deptType", Type_YX);
    			 }
    		 }
    		List<TStuScorepredTermGroup> zyGroup = castAndSaveListMapToGroup(newZyList, start_schoolYear, start_termCode, end_schoolYear, end_termCode, 1,0);
    		groupList.addAll(zyGroup);
    	 }
    	kzAllList = getKzZyList(kzAllList);
    	for(Map<String,Object> kzMapss : kzAllList ){
    		kzMapss.put("grade", grade);
    	}
    	List<TStuScorepredTermGroup> kzGroup =castAndSaveListMapToGroup(kzAllList, start_schoolYear, start_termCode, end_schoolYear, end_termCode, 1,0);
    	groupList.addAll(kzGroup);
	    reMap.put("isHg", groupList); 
    	reMap.put("noHg", noHgGroupList); 
		return reMap;
	}
	@SuppressWarnings("unchecked")
	private Map<String,Object> getBxWfzCourseGroup(String start_schoolYear,String start_termCode,String end_schoolYear,String end_termCode,List<Map<String,Object>> isUsedCourse,List<String> gradeList){
		List<TStuScorepredTermGroup> otherGroupList = new ArrayList<TStuScorepredTermGroup>(),
                onHgGroupListAll = new ArrayList<TStuScorepredTermGroup>();
		 for(String wfzGrade : gradeList){
			 Map<String,Object> reMap = new HashMap<String, Object>(); 
			 List<TStuScorepredTermGroup> groupList_x = new ArrayList<TStuScorepredTermGroup>(),
					                      noHgGroupList = new ArrayList<TStuScorepredTermGroup>();
			 List<String> deptList = PmsUtils.getPmsAll();
			 List<Map<String,Object>> yxList = businessDao.queryYxList(deptList).subList(0, 1); 
			 List<Map<String,Object>> allCourseList_1 = scorePredictGroupDao.getWfzCourseList(start_schoolYear, start_termCode, end_schoolYear, end_termCode, wfzGrade, true, false);
	    	 if(allCourseList_1 == null || allCourseList_1.isEmpty()){
	    		 reMap.put("isHg", groupList_x);
	    		 reMap.put("noHg", noHgGroupList);
			 }else{
				 List<Map<String,Object>> otherAllCourseList_1 = new ArrayList<Map<String,Object>>();
	    	 for(Map<String,Object> allCourseMap : allCourseList_1){
	    		 String sxxq = MapUtils.getString(allCourseMap,"ycxq"),
			    		  kcMx = MapUtils.getString(allCourseMap,"kc"),
			    		  xnM = MapUtils.getString(allCourseMap,"xn"),
			    		  xqM = MapUtils.getString(allCourseMap,"xq");
	    		 int courseStuCount = MapUtils.getIntValue(allCourseMap,"stu");
	    		 allCourseMap.put("deptType", Type_YX);
	    		 if(courseStuCount <= Stu_Count_least){
                     if(sxxq.equals("next")){
                    	 String noHgStr = kcMx+","+xnM+","+xqM+",next";
    	    			 List<String> noHgZyList = new ArrayList<String>();
    	    			 noHgZyList.add(noHgStr);
    	    			 allCourseMap.put("zyKcList", noHgZyList);
    	    			 allCourseMap.put("kcList", noHgZyList);
    	    			 otherAllCourseList_1.add(allCourseMap);
    	    			 TStuScorepredTermGroup noHgG = castMapToGroup(allCourseMap, start_schoolYear, start_termCode, end_schoolYear, end_termCode, 0, 0, wfzGrade);
    	    			 noHgGroupList.add(noHgG); 
                     }else{
                    	 otherAllCourseList_1.add(allCourseMap);
                     }
	    		
	    		 }
	    	 }
	    	 allCourseList_1.removeAll(otherAllCourseList_1);
	    	 List<Map<String,Object>> kzAllList = new ArrayList<Map<String,Object>>();
	    	 for(Map<String,Object> yxMap:yxList){
	    		 String deptId = MapUtils.getString(yxMap, "id");
	    		 List<String> deptList_1 = businessService.packageDeptListById(deptId);
	    		 List<AdvancedParam> advancedParamsList = new ArrayList<AdvancedParam>();
	    		 AdvancedParam gradeAdp = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_ENROLL_GRADE, wfzGrade);
	    		 AdvancedUtil.add(advancedParamsList, gradeAdp);
	    		 String deptSql = businessDao.getStuSql(deptList_1, advancedParamsList);
	    		 int deptCount = baseDao.queryForCount(deptSql);
	    		 List<Map<String,Object>> yxCourseList = getCourseListByDeptId(allCourseList_1, deptId),
	    				                  courseMergeList = new ArrayList<Map<String,Object>>();
	    		 List<String> ycXqList = new ArrayList<String>();
	    		 for(Map<String,Object> yxKcMap : yxCourseList){
	    			 String ycXqSS = MapUtils.getString(yxKcMap, "ycxq");
	    			 if(!ycXqList.contains(ycXqSS)){
	    				 ycXqList.add(ycXqSS);
	    			 }
	    		 }
	    		 if(ycXqList.size() == 1 && ycXqList.get(0).equals("next")){
	    			 for(Map<String,Object> yxKcMap_1 : yxCourseList){
	    				 String sxxq = MapUtils.getString(yxKcMap_1,"ycxq"),
	    			    		  kcMx = MapUtils.getString(yxKcMap_1,"kc"),
	    			    		  xnM = MapUtils.getString(yxKcMap_1,"xn"),
	    			    		  xqM = MapUtils.getString(yxKcMap_1,"xq");
	    					 String noHgStr_1 = kcMx+","+xnM+","+xqM+","+sxxq;
	            			 List<String> noHgZyList = new ArrayList<String>();
	            			 noHgZyList.add(noHgStr_1);
	            			 yxKcMap_1.put("zyKcList", noHgZyList);
	            			 yxKcMap_1.put("kcList", noHgZyList);
	            			 TStuScorepredTermGroup noHgG = castMapToGroup(yxKcMap_1, start_schoolYear, start_termCode, end_schoolYear, end_termCode, 0, 0, wfzGrade);
	            			 noHgGroupList.add(noHgG);  
	    			 }
	    			 reMap.put("isHg", groupList_x);
	        		 reMap.put("noHg", noHgGroupList);
	    			 return reMap;
	    		 }else if(ycXqList.size() == 1 && ycXqList.get(0).equals("last")){
	    			 reMap.put("isHg", groupList_x);
	        		 reMap.put("noHg", noHgGroupList);
	    			 return reMap;
	    		 }
	    		 compareCount(yxCourseList, "stu", false);
	    		 int max = MapUtils.getIntValue(yxCourseList.get(0), "stu");
	    	     Double x = max*Group_Scale;
	    		 for(Double i =x,j=Double.valueOf(max+1);j>Stu_Count_least;){
	    			 List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
	    			 for(Map<String,Object> yxCourseMap : yxCourseList){
	    				 yxCourseMap.put("deptType", Type_YX);
	    				int stu = MapUtils.getIntValue(yxCourseMap, "stu"); 
	    				if(stu>=i-10 && stu <j){
	    					yxCourseMap = scorePredictGroupDao.getBjXsList(yxCourseMap,wfzGrade,false);
	    					tempList.add(yxCourseMap);
	    				}
	    			 }
	    			 yxCourseList.removeAll(tempList);
	    			 tempList=mergeList(tempList,false);
	 				 courseMergeList.addAll(tempList);
	 				 if(yxCourseList.size() == 0){
	 					 break;
	 				 }
	    			 compareCount(yxCourseList, "stu", false);
	    			 int max_1 = MapUtils.getIntValue(yxCourseList.get(0), "stu");
	    			 j = Double.valueOf(max_1+1);
	    			 i = max_1*Group_Scale;
	    		 }
	    		 for(Map<String,Object> courseMergeMap:courseMergeList){
	    			 Integer lastXqCount = MapUtils.getInteger(courseMergeMap, "lastXq"),
	    					 nextXqCount = MapUtils.getInteger(courseMergeMap, "nextXq");
	    			 int stu = MapUtils.getIntValue(courseMergeMap, "stu");
	    			 lastXqCount = lastXqCount == null ? 0 :lastXqCount;
	    			 nextXqCount = nextXqCount == null ? 0 :nextXqCount;
	    			 List<String> kcList_x = (List<String>) MapUtils.getObject(courseMergeMap, "kcList");
	    			 int jvb = 0;
	    			 for(String kcX : kcList_x){
	    				 String kcY = kcX.substring(0,kcX.length()-5);
    					 for(Map<String,Object> isUsedMap_1 : isUsedCourse){
    						 String kc_isUsed = MapUtils.getString(isUsedMap_1,"kc");
    						 String dept_isUsed =MapUtils.getString(isUsedMap_1, "dept");
    						 String[] dept_isUsedAry = dept_isUsed.split(",",-1);
    						 if(Arrays.asList(dept_isUsedAry).contains(deptId) && kcY.equals(kc_isUsed)){
    							 if(kcX.substring(kcX.length()-5).equals("next")){
    								 courseMergeMap.put("nextXq", nextXqCount-1);
    								 kcList_x.set(jvb,kc_isUsed+",other");
    							 }
    						 }
    					 }
    					 jvb++;
	    			 }
	    			 if(lastXqCount >= Last_Xq_Min_Size && nextXqCount >= Next_Xq_Min_Size && stu>Stu_Count_least){
		    			 List<String>    zyKcList = new ArrayList<String>();
	    				 for(String kcX_1 : kcList_x){
	    					 String[] ary_1 = kcX_1.split(",",-1);
	    					 if(ary_1[6].equals("next")){//判断是否已经匹配
	    						 zyKcList.add(kcX_1); 
	    					 }
	    				 }
	    				 courseMergeMap.put("zyKcList",zyKcList);
	    					 
	    			 }
	    		 }
	    		 compareCount(courseMergeList, "stu", false);
	    		 Map<String,Object> zyMapHgBhg = getZyList(courseMergeList);
	    		 List<Map<String,Object>> zyList = (List<Map<String, Object>>) zyMapHgBhg.get("isHg"),
	    				                  tempList_1= new ArrayList<Map<String,Object>>(),
	    				                  newZyList = new ArrayList<Map<String,Object>>(),
	    				                  newZyList_1 = new ArrayList<Map<String,Object>>(),
	    				                  wzyList = (List<Map<String, Object>>) zyMapHgBhg.get("noHg");
	    		 List<Map<String,Object>> zyList_bb = getSplitList(zyList);
	    		 zyList.addAll(zyList_bb);
	    		 for(Map<String,Object> zyMap_1 : zyList){
	    			 int stu_zy = MapUtils.getIntValue(zyMap_1,"allStu");
	    			 if(stu_zy >= deptCount*Kz_Stu_Count_Scale){
	    				 tempList_1.add(zyMap_1);
	    			 }
	    		 }
	    		 Map<String,Object> hgbhgMap = getZyMapByList(zyList, courseMergeList, wfzGrade, Type_YX, start_schoolYear, start_termCode, end_schoolYear, end_termCode, true, 0, 0,false),
	    				            hgbhgMap_1 = getZyMapByList(wzyList, courseMergeList, wfzGrade,Type_YX, start_schoolYear, start_termCode, end_schoolYear, end_termCode, false, 0, 0,false);
	    		 newZyList = (List<Map<String, Object>>) MapUtils.getObject(hgbhgMap, "isHg");
	    		 newZyList_1 = (List<Map<String, Object>>) MapUtils.getObject(hgbhgMap_1, "isHg");
	    		 List<TStuScorepredTermGroup> noHgZyList =  (List<TStuScorepredTermGroup>) MapUtils.getObject(hgbhgMap, "noHg"),
	    				                      noHgZyList_1 = (List<TStuScorepredTermGroup>) MapUtils.getObject(hgbhgMap_1, "noHg");
	    		 noHgGroupList.addAll(noHgZyList);noHgGroupList.addAll(noHgZyList_1);
	    		 newZyList.addAll(newZyList_1);
	    		 if(tempList_1.size()>0){
	    			 zyList.removeAll(tempList_1);
	    			 kzAllList.addAll(tempList_1);
	    		 }
//	    		 List<Map<String,Object>> newZyList_bb = getSplitList(newZyList);
//	    		 newZyList.addAll(newZyList_bb);
	    		 for(Map<String,Object> zyMap : newZyList){
	    			 zyMap.put("grade", wfzGrade);
	    			 List<String> stuZyList = getZyByStu(zyMap);
	    			 if(stuZyList != null && stuZyList.size() == 1){
	    				 zyMap.put("deptType", Type_ZY);
	    				 zyMap.put("dept", stuZyList.get(0));
	    			 }else{
	    				 zyMap.put("deptType", Type_YX);
	    			 }
	    		 }
	    		List<TStuScorepredTermGroup> zyGroup = castAndSaveListMapToGroup(newZyList, start_schoolYear, start_termCode, end_schoolYear, end_termCode, 0,0);
	    		groupList_x.addAll(zyGroup);
	    	 }
	    	kzAllList = getKzZyList(kzAllList);
	    	for(Map<String,Object> kzAll:kzAllList){
	    		kzAll.put("grade", wfzGrade);
	    	}
	    	List<TStuScorepredTermGroup> kzGroup =castAndSaveListMapToGroup(kzAllList, start_schoolYear, start_termCode, end_schoolYear, end_termCode, 0,0);
	    	groupList_x.addAll(kzGroup);
		 }
	    	 otherGroupList.addAll(groupList_x);
	    	 onHgGroupListAll.addAll(noHgGroupList);
		 }
		 
		 Map<String,Object> reMapAll = new HashMap<String, Object>();
		 reMapAll.put("noMode", otherGroupList);
		 reMapAll.put("noHg", onHgGroupListAll);
		 return reMapAll;
	}
	@SuppressWarnings("unchecked")
	private Map<String,Object> getXxWfzCourseGroup(String start_schoolYear,String start_termCode,String end_schoolYear,String end_termCode,List<Map<String,Object>> isUsedCourse,List<String> gradeList){
		List<TStuScorepredTermGroup> otherGroupList = new ArrayList<TStuScorepredTermGroup>(),
                onHgGroupListAll = new ArrayList<TStuScorepredTermGroup>();
		 for(String wfzGrade : gradeList){
			 Map<String,Object> reMap = new HashMap<String, Object>(); 
			 List<TStuScorepredTermGroup> groupList_x = new ArrayList<TStuScorepredTermGroup>(),
					                      noHgGroupList = new ArrayList<TStuScorepredTermGroup>();
			 List<Map<String,Object>> allCourseList_1 = scorePredictGroupDao.getWfzCourseList(start_schoolYear, start_termCode, end_schoolYear, end_termCode, wfzGrade, true, true);
	    	 if(allCourseList_1 == null || allCourseList_1.isEmpty()){
	    		 reMap.put("isHg", groupList_x);
	    		 reMap.put("noHg", noHgGroupList);
			 }else{
				 List<Map<String,Object>> otherAllCourseList_1 = new ArrayList<Map<String,Object>>();
				 List<String> courseSize = new ArrayList<String>();
				 for(Map<String,Object> allCourseMap : allCourseList_1){
	    		 String sxxq = MapUtils.getString(allCourseMap,"ycxq"),
			    		  kcMx = MapUtils.getString(allCourseMap,"kc"),
			    		  xnM = MapUtils.getString(allCourseMap,"xn"),
			    		  xqM = MapUtils.getString(allCourseMap,"xq");
			     if(!courseSize.contains(sxxq)){
			    	 courseSize.add(sxxq);
			     }
	    		 int courseStuCount = MapUtils.getIntValue(allCourseMap,"stu");
	    		 allCourseMap.put("deptType", Type_XX);
	    		 if(courseStuCount <= Stu_Count_least){
	    			 if(sxxq.equals("next")){
	    				 String noHgStr = kcMx+","+xnM+","+xqM+",next";
		    			 List<String> noHgZyList = new ArrayList<String>();
		    			 noHgZyList.add(noHgStr);
		    			 allCourseMap.put("zyKcList", noHgZyList);
		    			 allCourseMap.put("kcList", noHgZyList);
		    			 otherAllCourseList_1.add(allCourseMap);
		    			 TStuScorepredTermGroup noHgG = castMapToGroup(allCourseMap, start_schoolYear, start_termCode, end_schoolYear, end_termCode, 0, 0, wfzGrade);
		    			 noHgGroupList.add(noHgG);
	    			 }else{
	    				 otherAllCourseList_1.add(allCourseMap);
	    			 }
	    			
	    		 }
	    	 }
	    	 allCourseList_1.removeAll(otherAllCourseList_1);
	    	 if(courseSize.size() == 1 && courseSize.get(0).equals("next")){
				 for(Map<String,Object> yxKcMap_1 : allCourseList_1){
					 String sxxq = MapUtils.getString(yxKcMap_1,"ycxq"),
				    		  kcMx = MapUtils.getString(yxKcMap_1,"kc"),
				    		  xnM = MapUtils.getString(yxKcMap_1,"xn"),
				    		  xqM = MapUtils.getString(yxKcMap_1,"xq");
						 String noHgStr_1 = kcMx+","+xnM+","+xqM+","+sxxq;
	        			 List<String> noHgZyList = new ArrayList<String>();
	        			 noHgZyList.add(noHgStr_1);
	        			 yxKcMap_1.put("zyKcList", noHgZyList);
	        			 yxKcMap_1.put("kcList", noHgZyList);
	        			 TStuScorepredTermGroup noHgG = castMapToGroup(yxKcMap_1, start_schoolYear, start_termCode, end_schoolYear, end_termCode, 0, 0, wfzGrade);
	        			 noHgGroupList.add(noHgG);  
				 }
				 reMap.put("isHg", groupList_x);
	    		 reMap.put("noHg", noHgGroupList);
				 return reMap;
			 }else if(courseSize.size() == 1 && courseSize.get(0).equals("last")){
				 reMap.put("isHg", groupList_x);
	    		 reMap.put("noHg", noHgGroupList);
				 return reMap;
			 }
	    		 List<Map<String,Object>> courseMergeList = new ArrayList<Map<String,Object>>();
	    		 compareCount(allCourseList_1, "stu", false);
	    		 int max = MapUtils.getIntValue(allCourseList_1.get(0), "stu");
	    	     Double x = max*Group_Scale;
	    		 for(Double i =x,j=Double.valueOf(max+1);j>Stu_Count_least;){
	    			 List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
	    			 for(Map<String,Object> yxCourseMap : allCourseList_1){
	    				 yxCourseMap.put("deptType", Type_XX);
	    				int stu = MapUtils.getIntValue(yxCourseMap, "stu"); 
	    				if(stu>=i-10 && stu <j){
	    					yxCourseMap = scorePredictGroupDao.getXxCourseStuList(yxCourseMap,wfzGrade,false);
	    					tempList.add(yxCourseMap);
	    				}
	    			 }
	    			 allCourseList_1.removeAll(tempList);
	    			 tempList=mergeList(tempList,true);
	 				 courseMergeList.addAll(tempList);
	 				 if(allCourseList_1.size() == 0){
	 					 break;
	 				 }
	    			 compareCount(allCourseList_1, "stu", false);
	    			 int max_1 = MapUtils.getIntValue(allCourseList_1.get(0), "stu");
	    			 j = Double.valueOf(max_1+1);
	    			 i = max_1*Group_Scale;
	    		 }
	    		 for(Map<String,Object> courseMergeMap:courseMergeList){
	    			 Integer lastXqCount = MapUtils.getInteger(courseMergeMap, "lastXq"),
	    					 nextXqCount = MapUtils.getInteger(courseMergeMap, "nextXq");
	    			 int stu = MapUtils.getIntValue(courseMergeMap, "stu");
	    			 lastXqCount = lastXqCount == null ? 0 :lastXqCount;
	    			 nextXqCount = nextXqCount == null ? 0 :nextXqCount;
	    			 List<String> kcList_x = (List<String>) MapUtils.getObject(courseMergeMap, "kcList");
	    			 int jvb = 0;
	    			 for(String kcX : kcList_x){
	    				 String kcY = kcX.substring(0,kcX.length()-5);
    					 for(Map<String,Object> isUsedMap_1 : isUsedCourse){
    						 String kc_isUsed = MapUtils.getString(isUsedMap_1,"kc");
    						 if( kcY.equals(kc_isUsed)){
    							 if(kcX.substring(kcX.length()-5).equals("next")){
    								 courseMergeMap.put("nextXq", nextXqCount-1);
    								 kcList_x.set(jvb,kc_isUsed+",other");
    							 }
    						 }
    					 }
    					 jvb++;
	    			 }
	    			 if(lastXqCount >= Last_Xq_Min_Size && nextXqCount >= Next_Xq_Min_Size && stu>Stu_Count_least){
		    			 List<String>    zyKcList = new ArrayList<String>();
	    				 for(String kcX_1 : kcList_x){
	    					 String[] ary_1 = kcX_1.split(",",-1);
	    					 if(ary_1[6].equals("next")){//判断是否已经匹配
	    						 zyKcList.add(kcX_1); 
	    					 }
	    				 }
	    				 courseMergeMap.put("zyKcList",zyKcList);
	    					 
	    			 }
	    		 }
	    		 compareCount(courseMergeList, "stu", false);
	    		 Map<String,Object> zyMapHgBhg = getZyList(courseMergeList);
	    		 List<Map<String,Object>> zyList = (List<Map<String, Object>>) zyMapHgBhg.get("isHg"),
	    				                  newZyList = new ArrayList<Map<String,Object>>(),
	    				                  newZyList_1 = new ArrayList<Map<String,Object>>(),
	    				                  wzyList = (List<Map<String, Object>>) zyMapHgBhg.get("noHg");
	    		 List<Map<String,Object>> zyList_bb = getSplitList(zyList);
	    		 zyList.addAll(zyList_bb);
	    		 Map<String,Object> hgbhgMap = getZyMapByList(zyList, courseMergeList, wfzGrade, Type_XX, start_schoolYear, start_termCode, end_schoolYear, end_termCode, true, 0, 1,false),
	    				            hgbhgMap_1 = getZyMapByList(wzyList, courseMergeList, wfzGrade,Type_XX, start_schoolYear, start_termCode, end_schoolYear, end_termCode, false, 0, 1,false);
	    		 newZyList = (List<Map<String, Object>>) MapUtils.getObject(hgbhgMap, "isHg");
	    		 newZyList_1 = (List<Map<String, Object>>) MapUtils.getObject(hgbhgMap_1, "isHg");
	    		 List<TStuScorepredTermGroup> noHgZyList =  (List<TStuScorepredTermGroup>) MapUtils.getObject(hgbhgMap, "noHg"),
	    				                      noHgZyList_1 = (List<TStuScorepredTermGroup>) MapUtils.getObject(hgbhgMap_1, "noHg");
	    		 noHgGroupList.addAll(noHgZyList);noHgGroupList.addAll(noHgZyList_1);
	    		 newZyList.addAll(newZyList_1);
//	    		 List<Map<String,Object>> newZyList_bb = getSplitList(newZyList);
//	    		 newZyList.addAll(newZyList_bb);
	    		 for(Map<String,Object> newZyMap:newZyList){
	    			 newZyMap.put("grade", wfzGrade);
	    		 }
	    		List<TStuScorepredTermGroup> zyGroup = castAndSaveListMapToGroup(newZyList, start_schoolYear, start_termCode, end_schoolYear, end_termCode, 0,1);
	    		groupList_x.addAll(zyGroup);
		  }
	    	 otherGroupList.addAll(groupList_x);
	    	 onHgGroupListAll.addAll(noHgGroupList);
		 }
		 
		 Map<String,Object> reMapAll = new HashMap<String, Object>();
		 reMapAll.put("noMode", otherGroupList);
		 reMapAll.put("noHg", onHgGroupListAll);
		 return reMapAll;
	}
	private TStuScorepredTermGroup saveGroupNoHaveGroupId(TStuScorepredTermGroup group){
		List<TStuScorepredTermGpMd> kcList = group.getList();
		saveGroupData(group);
		String id = group.getId();
		for(TStuScorepredTermGpMd kc : kcList){
			kc.setGroupId(id);
		}
		return group;
	}
	@SuppressWarnings("unchecked")
	private Map<String,Object> getZyMapByList(List<Map<String,Object>> List,List<Map<String,Object>> allList,String grade,String deptType,
			String start_year,String start_term,String end_year,String end_term,Boolean isZyList,int truth,int isXx,Boolean hasScore){
		Map<String,Object> reMap = new HashMap<String, Object>();
		List<Map<String,Object>> isHgList = new ArrayList<Map<String,Object>>(),
				                 noHgList_1 = new ArrayList<Map<String,Object>>();
		List<TStuScorepredTermGroup>  noHgList = new ArrayList<TStuScorepredTermGroup>();
		 for(Map<String,Object> temp : List){
			 temp.put("grade", grade);
			 temp.put("deptType", deptType);
			 int nextcount= MapUtils.getIntValue(temp, "nextXq");
			 if(nextcount == 0){
				 continue;
			 }
			 TStuScorepredTermGroup group_1 = castMapToGroup(temp, start_year, start_term, end_year, end_term, truth, isXx);
			 System.out.println("进入");
			 Long begin = System.currentTimeMillis();
			 Boolean isHg = isValidGroup(group_1);
			 System.out.println(new Double(System.currentTimeMillis()-begin)/1000);
			 System.out.println("出来");
			 if(!isHg){
				 List<String> bhgKcList = (List<String>) MapUtils.getObject(temp,"zyKcList");
				 if(bhgKcList.size() > 1){
					 noHgList_1.add(temp);
					 String bhgDept = MapUtils.getString(temp,"dept");
					 for(String bhgKc : bhgKcList){
						 Map<String,Object> bhgmap = new HashMap<String, Object>();
						 List<String> bhgList_sb = new ArrayList<String>();
						 bhgList_sb.add(bhgKc);
						 String[] ary_kc = bhgKc.split(",",-1);
						 String bhgKcSx = ary_kc[1] == null?"":ary_kc[1],
 								bhgKcXz = ary_kc[2] == null?"":ary_kc[2],
 							    bhgKcLb = ary_kc[3] == null?"":ary_kc[3];
						 bhgmap.put("deptType", deptType);
 						 bhgmap.put("kc", ary_kc[0]+","+bhgKcSx+","+bhgKcXz+","+bhgKcLb);
						 bhgmap.put("xn", ary_kc[4]); bhgmap.put("xq", ary_kc[5]);
						 bhgmap.put("dept", bhgDept);bhgmap.put("lastXq", 0);bhgmap.put("nextXq", 1);
						 bhgmap.put("zyKcList", bhgList_sb);bhgmap.put("kcList", bhgList_sb);
						 bhgmap = isXx == 1?scorePredictGroupDao.getXxCourseStuList(bhgmap, grade,hasScore):scorePredictGroupDao.getBjXsList(bhgmap, grade,hasScore);
						 List<Map<String,Object>> newcourseMergeList = new ArrayList<Map<String,Object>>(),
								                  newcourseMergeList_1 = new ArrayList<Map<String,Object>>();
						 newcourseMergeList.addAll(allList);
						 for(Map<String,Object> bhgMap_1: newcourseMergeList){
							 List<String> bhgKcList_1 = (List<String>) MapUtils.getObject(bhgMap_1,"zyKcList");
						     if(bhgKcList_1.contains(bhgKc)){
						    	 newcourseMergeList_1.add(bhgMap_1);
						     }
						 }
						 newcourseMergeList.removeAll(newcourseMergeList_1);
						 Map<String,Object> temp_bhg = getZyMap(newcourseMergeList, bhgmap);
						 if(temp_bhg != null){
							 List<Map<String,Object>> temp_list = new ArrayList<Map<String,Object>>(),
									                  temp_list_1 = new ArrayList<Map<String,Object>>();
							 temp_list.add(temp_bhg);
							 temp_list_1 = getSplitList(temp_list);
							 temp_list.addAll(temp_list_1);
							 for(Map<String,Object> xx_temp : temp_list){
								 TStuScorepredTermGroup group_2 =  castMapToGroup(xx_temp, start_year, start_term, end_year, end_term, truth, isXx,grade);
								 Boolean isHg_1 = isValidGroup(group_2);  
								 if(isHg_1){
									 isHgList.add(xx_temp); 
								 }else{
									 TStuScorepredTermGroup xxxNoHg = castMapToGroup(xx_temp, start_year, start_term, end_year, end_term, truth, isXx, grade);
									 noHgList.add(xxxNoHg);  
								 }
							 }
							
						 }else{
							 TStuScorepredTermGroup xxxNoHg_2 = castMapToGroup(bhgmap, start_year, start_term, end_year, end_term, truth, isXx, grade);
							 noHgList.add(xxxNoHg_2);  
						 }
					 }
				 }else{
					 TStuScorepredTermGroup xxxNoHg_1 = castMapToGroup(temp, start_year, start_term, end_year, end_term, truth, isXx, grade);
					 noHgList.add(xxxNoHg_1); 
				 }
			 }else{
				 if(isZyList){
					 isHgList.add(temp); 
				 }else{
					 noHgList.add(castMapToGroup(temp, start_year, start_term, end_year, end_term, truth, isXx));
				 }
			 }
		 }
		 List.removeAll(noHgList_1);
		 reMap.put("isHg", isHgList);
		 reMap.put("noHg", noHgList);
		 return reMap;
	}
	@SuppressWarnings("unchecked")
	private List<Map<String,Object>> getSplitList(List<Map<String,Object>> list){
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>(),
				                 splitList = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map : list){
			List<String> kcList = (List<String>) MapUtils.getObject(map, "kcList");
			if(kcList!= null && kcList.size()>Group_Course_Split_Num){
				splitList.add(map);	
			}
		}
		list.removeAll(splitList);
		for(Map<String,Object> temp : splitList){
			List<String> kcList_1 = (List<String>) MapUtils.getObject(temp, "kcList"),
					     zyKcList_1 = (List<String>) MapUtils.getObject(temp, "zyKcList"),
				         nextKcList = new ArrayList<String>(),
				         lastKcList = new ArrayList<String>();
			for(String kc : kcList_1){
				String[] kcAry = kc.split(",",-1);
				if(kcAry[6].equals("next")){
					nextKcList.add(kc);
				}else if(kcAry[6].equals("last")){
					lastKcList.add(kc);
				}
			}
			if(lastKcList != null && zyKcList_1 != null && !lastKcList.isEmpty() && !zyKcList_1.isEmpty()){
				lastKcList = getZyCourseByAllCourse(lastKcList);
				Double max = MathUtils.getDivisionResult(zyKcList_1.size(), Group_Course_NextXq_Count, 4);
				for(int j =0;j<Math.ceil(max);j++){
					Map<String,Object> temp1 = new HashMap<String, Object>();
					List<String>  zyKcList_2 = new ArrayList<String>(),
							lastKcList_1 = new ArrayList<String>();
					lastKcList_1.addAll(lastKcList);
					for(int k = j*Group_Course_NextXq_Count;k<Group_Course_NextXq_Count*(j+1);k++){
						if(k==zyKcList_1.size()){
							break;
						}
						zyKcList_2.add(zyKcList_1.get(k));
					}
					temp1.putAll(temp);
					temp1.put("lastXq",lastKcList_1.size());
					lastKcList_1.addAll(zyKcList_2);
					temp1.put("kcList", lastKcList_1);
					temp1.put("zyKcList",zyKcList_2);
					temp1.put("nextXq",zyKcList_2.size());
					result.add(temp1);
				}
			
			}
		}
		return result;
	}
	private List<String> getZyCourseByAllCourse(List<String> lastKcList){
		if(lastKcList == null){
		   return null;	
		}
		List<String> result = new ArrayList<String>(),
				     list = new ArrayList<String>();
		list.addAll(lastKcList);
		String[] xzGroup = Constant.Code_Course_Nature_Code_Group,
				 sxGroup = Constant.Code_Course_attr_Code_Group;		   
		
			for(String xz_1 : xzGroup){
				for(String sx_1 : sxGroup){
					for(String kc: lastKcList){
						String[] ary = kc.split(",",-1);
						String xz = ary[2],sx = ary[1];
						 if(xz.equals(xz_1) && sx_1.equals(sx)){
	                    	 result.add(kc);
	                     }
					}
				}
			}
			list.removeAll(result);
			result.addAll(list);
		if(result.size()>Group_Course_LastXq_Count){
			return result.subList(0, Group_Course_LastXq_Count);
		}else{
			return result;
		}
	}
 	public static void main(String[] args) {
		DevUtils.p("start");
		long time1 = System.currentTimeMillis();
	    /*List<String> list1 = new ArrayList<String>(),
	    		                 list2 = new ArrayList<String>();
	    for(int i =0;i<1001;i++){
	    	if(i<500){
	    		list2.add(String.valueOf(i));
	    	}
	    	list1.add(String.valueOf(i));
	    }
	    list1=list2;
	    System.out.println(list1);
	    }
	    System.out.println(list1);*/
		
		ClassPathXmlApplicationContext context = Main.getContext();
		ScorePredictGroupServiceImpl bean = context.getBean(ScorePredictGroupServiceImpl.class);
		
//		TStuScorepredTermGroup group = getGroup();
////		 test 分组学生sql
//		DevUtils.p(bean.getStuIdSqlByGroup(group));
		// test 分组学生真实成绩
//		List<List<String>> scoreList = bean.getScoreData(group);
		// test 获取真分组
		bean.getFakeGroup("2013-2014", "01", "2013-2014", "02",new ArrayList<TStuScorepredTermGroup>());
//        bean.getRealGroup("2014-2015", "01", "2014-2015", "02","2014-2015", "01", "2014-2015", "02");
		// test 获取训练集 [541401010102, 60, 95, 60, 60] index=1
//		List<List<String>> scoreList = bean.getTrainingData(group);
		// test 保存最优模型
//		bean.saveBestMode(new ArrayList<TStuScorepredTermGroup>());
		
		long time2 = System.currentTimeMillis();
		DevUtils.p("end："+(time2-time1)/1000);
	}
	
	/** 获取静态分组 */
	private static TStuScorepredTermGroup getGroup(){
		// 工大数据
		TStuScorepredTermGroup group = new TStuScorepredTermGroup();
		group.setId("1");
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
		list.add(new TStuScorepredTermGpMd("11", "1", "52110821",null,null,null,"2015-2016","01",1,0,0,"mx3"));
		list.add(new TStuScorepredTermGpMd("21", "1", "51416040",null,null,null,"2015-2016","01",2,0,0,"mx3"));
		list.add(new TStuScorepredTermGpMd("31", "1", "52110831",null,null,null,"2015-2016","02",3,1,1,"mx3"));
		list.add(new TStuScorepredTermGpMd("41", "1", "52310906",null,null,null,"2015-2016","02",4,1,0,"mx3"));
	
//		TStuScorepredTermGroup group = new TStuScorepredTermGroup();
//		group.setId("1");
//		group.setGrade(2);
//		group.setDeptType(Constant.Level_Type_YX);
//		group.setDeptValue("000089");
//		group.setStartSchoolyear("2015-2015");
//		group.setStartTermcode("01");
//		group.setEndSchoolyear("2015-2016");
//		group.setEndTermCode("02");
//		group.setTruth(1);
//		List<TStuScorepredTermGpMd> list = new ArrayList<>();
//		group.setList(list);
		
		// test 开课计划
//		group.setGrade(2);
//		group.setIselective(0);
		//-- YX 000089  2014010101 2014010102  0902103 1012103（000009 000010）  0103102 0123101(0103102 0103200)
//		list.add(new TStuScorepredTermGpMd("11", "1", "0902103",null,null,null,"2015-2016","01",1,0,0,"mx3"));
//		list.add(new TStuScorepredTermGpMd("21", "1", "1012103",null,null,null,"2015-2016","01",2,0,0,"mx3"));
//		list.add(new TStuScorepredTermGpMd("31", "1", "0103102",null,null,null,"2015-2016","02",3,1,1,"mx3"));
//		list.add(new TStuScorepredTermGpMd("411", "1", "0123101",null,null,null,"2015-2016","02",4,1,0,"mx9"));
		//-- YX 000089  2014020101 2014020102 2014020103  01S5232 01S5236  0253100 0257100
//		list.add(new TStuScorepredTermGpMd("1", "1", "01S5232",null,null,null,"2015-2016","01",1,0,0,"mx"));
//		list.add(new TStuScorepredTermGpMd("2", "1", "01S5236",null,null,null,"2015-2016","01",2,0,0,"mx"));
//		list.add(new TStuScorepredTermGpMd("3", "1", "0253100",null,null,null,"2015-2016","02",3,1,1,"mx"));
//		list.add(new TStuScorepredTermGpMd("4", "1", "0257100",null,null,null,"2015-2016","02",4,1,0,"mx"));
		
		// test 选课
//		group.setIselective(1);
//		group.setGrade(3);
		//-- 521507110301 521507110303  1803200 1501100  1501101 1023118 (0427205 0427204 没成绩)
//		list.add(new TStuScorepredTermGpMd("1", "1", "1803200",null,null,null,"2015-2016","01",1,0,0,"mx"));
//		list.add(new TStuScorepredTermGpMd("2", "1", "1501100",null,null,null,"2015-2016","01",2,0,0,"mx"));
//		list.add(new TStuScorepredTermGpMd("3", "1", "1501101",null,null,null,"2015-2016","02",3,1,1,"mx"));
//		list.add(new TStuScorepredTermGpMd("4", "1", "1023118",null,null,null,"2015-2016","02",4,1,0,"mx"));
		return group;
	}
}
