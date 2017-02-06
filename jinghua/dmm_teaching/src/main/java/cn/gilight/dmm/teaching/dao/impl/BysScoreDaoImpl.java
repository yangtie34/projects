package cn.gilight.dmm.teaching.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.teaching.dao.BysScoreDao;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.product.Globals;

@Repository("bysScoreDao")
public class BysScoreDaoImpl implements BysScoreDao{
	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessDao businessDao;
	
	private static final String[] ZXXQ_NAME = {"大一上","大一下","大二上","大二下","大三上","大三下","大四上","大四下","大五上","大五下"};
 	
	private static final String SCORE_AVG = Constant.SCORE_AVG;
	private static final String SCORE_MIDDLE = Constant.SCORE_MIDDLE;
	private static final String SCORE_MODE = Constant.SCORE_MODE;
	private static final String SCORE_FC  = Constant.SCORE_FC;
	private static final String SCORE_BZC = Constant.SCORE_BZC;
	@Override
	public List<Map<String,Object>> queryCourse(){
		String sql = " select t.id,t.name_ as mc from t_course t ";
		return baseDao.queryListInLowerKey(sql);
	}
 	@Override
	public List<Map<String,Object>> queryStuScoreList(List<String> deptList,List<AdvancedParam> stuAdvancedList,int xz,int period,String scoreType,String target){
         String grade = String.valueOf(period - xz);
     	AdvancedParam gradeAdp = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_ENROLL_GRADE, grade);
     	AdvancedUtil.add(stuAdvancedList,gradeAdp);
     	String stu = businessDao.getStuSql(deptList, stuAdvancedList)+" and t.length_schooling = '"+xz+"'";
     	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String sql = getStuScoreAvgSql(stu); 
	    String scoreSql = "select a.school_year as year,a.term_code as  term,a.weight_avg as  score from ("+sql+") a";
	    if(scoreType.equals("gpa")){
	    	scoreSql ="select t.school_year as year,t.term_code as term,t.gpa as score from ("+getStuGpaSql(stu)+") t";
	    }
	    List<Map<String,Object>> scoreList = baseDao.queryListInLowerKeyLarge(scoreSql);
	    int k=0;
	    for(int i = period-xz;i<period;i++){
	    	
	    	for(int j=0;j<2;j++){
	    		Map<String, Object> temp = new HashMap<String, Object>();
	    		List<Double> tempList = new ArrayList<Double>();
	    		String schoolYear = String.valueOf(i)+"-"+String.valueOf(i+1),
	    		       termCode = j==0?Globals.TERM_FIRST:Globals.TERM_SECOND;
	    		for(Map<String,Object> temp_1:scoreList){
	    			String schoolYear_1 = MapUtils.getString(temp_1, "year"),
	    				   termCode_1 = MapUtils.getString(temp_1, "term");
	    			Double score = MapUtils.getDouble(temp_1, "score");
	    			if(score == null){
	    				continue;
	    			}
	    			if(schoolYear_1.equals(schoolYear) && termCode_1.equals(termCode)){
	    				tempList.add(score);
	    			}
	    		}
	    	    Double value = queryValueByType(target,tempList);
	    	    temp.put("name", period+"届");
	    	    temp.put("field", ZXXQ_NAME[k]);
	    	    temp.put("value", value);
	    	    list.add(temp);
	    	    k++;
	    	}
	    	
	    }
     	return list;
	}
 	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> queryScoreFb(List<AdvancedParam> stuAdvancedList,List<String> deptList,int period,int xz){
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		String grade = String.valueOf(period - xz);
     	AdvancedParam gradeAdp = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_ENROLL_GRADE, grade);
     	AdvancedUtil.add(stuAdvancedList,gradeAdp); 
    	String stu = businessDao.getStuSql(deptList, stuAdvancedList)+" and t.length_schooling = '"+xz+"'";
     	String sql =  getStuScoreAvgSql(stu);
    	String scoreSql = "select mc as name ,year,term,count(0) as value from (select "+getCaseWhenSql()+" as mc,t.school_year as year,t.term_code as term from (select a.weight_avg as score,a.school_year,a.term_code from ("+sql+") a) t) group by mc,year,term";
     	List<Map<String, Object>> scoreList = baseDao.queryListInLowerKeyLarge(scoreSql);
    	int j=0,sb=0;
     	for(int i= period-xz;i<period;i++){
     		String year = String.valueOf(i)+"-"+String.valueOf(i+1);
     		for(int k=0;k<2;k++){
     			String xq = k==0?Globals.TERM_FIRST:Globals.TERM_SECOND;
     			Map<String,Object> temp = new HashMap<String, Object>();
     			List<Map<String,Object>> tempList = new ArrayList<Map<String,Object>>();
     			for(Map<String,Object> temp1 : scoreList){
     				String schoolYear = MapUtils.getString(temp1, "year"),
     					   termCode = MapUtils.getString(temp1, "term"),
     					   name = MapUtils.getString(temp1, "name");
     				int count = MapUtils.getIntValue(temp1, "value");
     				if(schoolYear.equals(year) && termCode.equals(xq)){
     					if(temp.containsKey(name)){
     					   int count1 = MapUtils.getIntValue(temp, name);
     					   count1 = count1+count;
     					  temp.put(name, count1);
     					}else if(!temp.containsKey(name)){
     						  temp.put(name, count);  
     					}
     				}
     			}
     			List<Map<String,Object>> groupList = Constant.getScoreGroup();
     			if(temp == null || temp.isEmpty()){
       		    	tempList = groupList;
       		    	sb = 1;
       		    }else{
       		    	for(Map.Entry<String, Object> entry : temp.entrySet()){ 
         				Map<String,Object> temp_2 = new HashMap<String, Object>();
       		    		String key = entry.getKey();
         				int countValue = (int) entry.getValue();
         				int order = 99;
         				for(Map<String, Object> group :groupList){
         					String mc = MapUtils.getString(group, "mc");
         					if(mc.equals(key)){
         						order = MapUtils.getIntValue(group, "order");
         					}
         				}
         				temp_2.put("name", key);temp_2.put("value", countValue);
         				temp_2.put("order", order);
         				tempList.add(temp_2);
         			}
       		    }
     			compareCount(tempList,"order",true);
     		    for(Map<String,Object> map : tempList){
     		    	if(sb == 1){
     		    		map.put("value", 0);
     		    		map.put("name", MapUtils.getString(map, "mc"));
     		    	}
     		     	map.put("order", MapUtils.getIntValue(map, "order"));
     		    	map.put("field", ZXXQ_NAME[j]);
     		    }
     		    sb=0;
     		    result.addAll(tempList);
     		    j++;
     		}
    	}
		return result;
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
  private Double queryValueByType(String target,List<Double> valueList){
		Double value = 0.0;
	  switch (target) {
		case SCORE_AVG:
			value = MathUtils.getAvgValue(valueList);
			break;
		case SCORE_MIDDLE:
			value = MathUtils.getMiddleValue(valueList);
			break;
		case SCORE_MODE:
			value = MathUtils.getModeValue(valueList);
			break;
		case SCORE_FC: 
			value = MathUtils.getVariance(valueList);
			break;
		case SCORE_BZC:
			value = MathUtils.getStandardDeviation(valueList);
			break;
		default:
			break;
		}
	  return value;
  }
  @Override
  public String getCaseWhenSql(){
		List<Map<String,Object>> list = Constant.getScoreGroup();
		String str = "case ";
		for(Map<String,Object> temp : list ){
			String id = MapUtils.getString(temp, "id"),
				   mc = MapUtils.getString(temp, "mc");
			String[] ary = id.split(",");
			String start = ary[0].equals("null") || ary[0] == null?
					"":" t.score >= "+ary[0]+" ",
					end = ary[1].equals("null") || ary[1] == null?
					"":" t.score < "+ary[1]+" ";
		    String sql = "";
		    if(start.equals("") && !end.equals("")){
		    	 sql = " when "+end+" ";
		    }else if(!start.equals("") && !end.equals("")){
		    	 sql = " when "+start+" and "+end+"  ";
		    }else if(!start.equals("") && end.equals("")){
		    	 sql = " when "+start+" ";
		    }
		    sql = sql +" then '"+mc+"'";
		    str += sql;
		}
		str = str + " else '未知' end ";
		return str;
	}
  private String getStuScoreAvgSql(String stuSql){
	  stuSql = (stuSql==null||"".equals(stuSql)) ? "select * from t_stu" : stuSql;
		String sql = "select t.* from t_stu_score_avg t,("+stuSql+") a where t.stu_id = a.no_ "
				+ " and t.school_year is not null and t.term_code is not null ";
		return sql;
  }
  private String getStuScoreAvgSql(String stuSql,String schoolYear,String termCode){
	  stuSql = (stuSql==null||"".equals(stuSql)) ? "select * from t_stu" : stuSql;
		String matchSql = (schoolYear==null||"".equals(schoolYear)) ? "and t.school_year is null" : " and t.school_year='"+schoolYear+"' ";
		matchSql += (termCode==null||"".equals(termCode)) ? "and t.term_code is null" : " and t.term_code='"+termCode+"' ";
		String sql = "select t.* from t_stu_score_avg t,("+stuSql+") a where t.stu_id = a.no_ "+ matchSql;
		return sql;
  }
  private String getStuGpaSql(String stuSql){
	  stuSql = (stuSql==null||"".equals(stuSql)) ? "select * from t_stu" : stuSql;
		String sql = "select t.* from t_stu_score_gpa t,("+stuSql+") a where t.stu_id = a.no_ "
				+ " and t.gpa_code = '"+Constant.SCORE_GPA_BASE_CODE+"' and t.school_year is not null "
						+ " and t.term_code is not null";
		return sql;
  }
 }
