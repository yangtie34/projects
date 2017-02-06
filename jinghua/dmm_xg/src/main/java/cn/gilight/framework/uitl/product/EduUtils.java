package cn.gilight.framework.uitl.product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.StringUtils;

/**
 * 大学业务相关的工具类
 * @author Administrator
 */
public class EduUtils {
	/**
	 * 大学四年对照表
	 */
	private static Map<Integer,String> nameVal = new HashMap<Integer,String>();
	static{
		nameVal.put(1, "大一上");
		nameVal.put(2, "大一下");
		nameVal.put(3, "大二上");
		nameVal.put(4, "大二下");
		nameVal.put(5, "大三上");
		nameVal.put(6, "大三下");
		nameVal.put(7, "大四上");
		nameVal.put(8, "大四下");
		nameVal.put(9, "大五上");
		nameVal.put(10, "大五下");
	}
	/**
	 * 年级-标准代码
	 */
	private static List<Map<String, Object>> NjBzdm = new ArrayList<Map<String, Object>>();
	static {
		Map<String, Object> m1=new HashMap<>(), m2=new HashMap<>(), m3=new HashMap<>(), m4=new HashMap<>(), m5=new HashMap<>();
		m1.put("id", 1); m1.put("mc", "大一");
		m2.put("id", 2); m2.put("mc", "大二");
		m3.put("id", 3); m3.put("mc", "大三");
		m4.put("id", 4); m4.put("mc", "大四");
		m5.put("id", 5); m5.put("mc", "大五");
		NjBzdm.add(m1); NjBzdm.add(m2); NjBzdm.add(m3); NjBzdm.add(m4); NjBzdm.add(m5); 
	}
	/**
	 * 年级-代码-名称对照
	 */
	private static Map<Integer,String> NjCodeName = new HashMap<Integer,String>();
	static {
		for(Map<String, Object> map : NjBzdm)
			NjCodeName.put(Integer.valueOf(map.get("id").toString()), map.get("mc").toString());
	}
	/**
	 * 学年日期设置 ["08-01", "01-31", "02-01", "07-31"]
	 */
	private static final String[] School_Year_Date = {"08-01", "01-31", "02-01", "07-31"};
	
	/**
	 * 日期分隔符
	 */
	private static final String Separator = "-";
	
	/**
	 * 给定一个日期返回该日期所处的学年学期
	 * @param date
	 * @return ['2015-2016','02']
	 */
	public static String[] getSchoolYearTerm(Date date){
		int month = Integer.parseInt(DateUtils.MONTH.format(date));
		int year = Integer.parseInt(DateUtils.YEAR.format(date));
//		String da = DateUtils.SDF.format(date); // TODO
		String[] result = new String[2];
		if(month>=8){
			result[0] = year+Separator+(year+1);
			result[1] = Globals.TERM_FIRST;
		}else if(month==1){
			result[0] = (year-1)+Separator+(year);
			result[1] = Globals.TERM_FIRST;
		}else{
			result[0] = (year-1)+Separator+(year);
			result[1] = Globals.TERM_SECOND;
		}
		return result;
	}
	/**
	 * 给定一个日期返回该日期所处的学年学期
	 * @param strDate
	 * @return  ['2015-2016','02']
	 */
	public static String[] getSchoolYearTerm(String strDate){
		return getSchoolYearTerm(DateUtils.string2Date(strDate));
	}
	/**
	 * 给定一个日期返回该日期搜出学年学期的上一学年学期。
	 * @param date
	 * @return
	 */
	public static String[] getProSchoolYearTerm(Date date){
		String[] syt = getSchoolYearTerm(date);
		if(Globals.TERM_SECOND.equals(syt[1])){
			syt[1] = Globals.TERM_FIRST;
			return syt;
		}else{
			String[] temp = syt[0].split(Separator);
			int from = Integer.parseInt(temp[0])-1,to = Integer.parseInt(temp[1])-1;
			syt[0]=from+Separator+to;
			syt[1]=Globals.TERM_SECOND;
			return syt;
		}
		
	}

	/**
	 * 获取当前时间所对应的学年（9位）
	 * @return int eg：2015-2016
	 */
	public static String getSchoolYear9(){
		return getSchoolYear9(getSchoolYear4());
	}
	/**
	 * 获取指定学年的学年（9位）
	 * @param year
	 * @return String
	 */
	public static String getSchoolYear9(int year){
		return year+Separator+(year+1);
	}
	/**
	 * 获取学年（如果学年为空，返回当前学年）
	 * @param schoolYear
	 * @return String
	 */
	public static String getSchoolYear9(String schoolYear){
		return (schoolYear==null||schoolYear.length()!=9) ? EduUtils.getSchoolYear9() : schoolYear;
	}
	
	/**
	 * 获取当前时间所对应的学年（4位）
	 * @return int eg：2015
	 */
	public static int getSchoolYear4(){
		int schoolYear = 0,
			this_year = Integer.valueOf(DateUtils.getNowYear());
		String date_cmp = this_year+Separator+School_Year_Date[3], // 去年第二学期结束时间，即今年+第二学期结束时间
			   data_now = DateUtils.getNowDate();
		long time_cmp = DateUtils.string2Date(date_cmp).getTime(),
			 time_now = DateUtils.string2Date(data_now).getTime();
		if(time_now >= time_cmp){
			schoolYear = this_year;
		}else
			schoolYear = this_year-1;
		return schoolYear;
	}
	
	/**
	 * 获取对应学年的学年（4位）
	 * @param schoolYear
	 * @return Integer
	 */
	public static Integer getSchoolYear4(String schoolYear){
		if(schoolYear==null || schoolYear.length()<4) return null;
		return Integer.valueOf(schoolYear.substring(0, 4));
	}
	
	/**
	 * 获取当前学年的上一个学年
	 * @return String
	 */
	public static String getLastSchoolYear9(){
		return getLastSchoolYear9(getSchoolYear9());
	}
	/**
	 * 获取指定学年的上一个学年
	 * @param schoolYear 学年
	 * @return String
	 */
	public static String getLastSchoolYear9(String schoolYear){
		if(schoolYear==null || schoolYear.length() < 4){
			return null;
		}
		int last_year = Integer.valueOf(schoolYear.substring(0, 4))-1;
		return last_year+Separator+(last_year+1);
	}
	
	/**
	 * 给定一个学年返回该学年对应的日期时间段
	 * @param schoolYear 2015-2016
	 * @return [2015-08-01, 2016-07-01]
	 */
	public static String[] getTimeQuantum(String schoolYear){
		String[] result = new String[2],
				 years  = schoolYear.split(Separator);
		if(years.length == 1)
			StringUtils.addStringToArray(years, String.valueOf(schoolYear+1));
		result[0] = years[0]+Separator+School_Year_Date[0];
		result[1] = years[1]+Separator+School_Year_Date[3];
		return result;
	}
	/**
	 * 给定一个学年返回该学年对应的日期时间段
	 * @param schoolYear 2015
	 * @return [2015-08-01, 2016-07-01]
	 */
	public static String[] getTimeQuantum(int schoolYear){
		String[] result = new String[2],
				 years  = {String.valueOf(schoolYear), String.valueOf(schoolYear+1)};
		result[0] = years[0]+Separator+School_Year_Date[0];
		result[1] = years[1]+Separator+School_Year_Date[3];
		return result;
	}
	
	/**
	 * 返回当前时间所对应的 学年时间段
	 * @return String[]
	 * <br> 2015-04-01 -> [2015-02-02, 2015-07-01]
	 */
	public static String[] getTimeQuantumXn(){
		String[] result = new String[2];
		String this_year = DateUtils.getNowYear(),
			   date_cmp  = this_year+Separator+School_Year_Date[3], // 第二学期结束时间
			   date_now  = DateUtils.getNowDate();
		long time_cmp = DateUtils.string2Date(date_cmp).getTime(),
			 time_now = DateUtils.string2Date(date_now).getTime();
		if(time_now >= time_cmp){
			result[0] = this_year+Separator+School_Year_Date[0];
			result[1] = Integer.valueOf(this_year)+1+Separator+School_Year_Date[3];
		}else{
			result[0] = Integer.valueOf(this_year)-1+Separator+School_Year_Date[0];
			result[1] = date_cmp;
		}
		return result;
	}
	
	/**
	 * 返回当前时间所对应的 学期时间段
	 * @return String[]
	 * <br> 2015-04-01 -> [2014-08-01, 2015-07-01]
	 */
	public static String[] getTimeQuantumXq(){
		String[] result = new String[2];
		String this_year = DateUtils.getNowYear(),
			   date_cmp  = this_year+Separator+School_Year_Date[2], // 第二学期开始时间
			   data_now  = DateUtils.getNowDate();
		long time_cmp = DateUtils.string2Date(date_cmp).getTime(),
			 time_now = DateUtils.string2Date(data_now).getTime();
		if(time_now >= time_cmp){
			result[0] = date_cmp;
			result[1] = this_year+Separator+School_Year_Date[3];
		}else{
			result[0] = Integer.valueOf(this_year)-1+Separator+School_Year_Date[0];
			result[1] = this_year+Separator+School_Year_Date[1];
		}
		return result;
	}
	
	/**
	 * 给定学年、学期返回该学年对应的日期时间段
	 * @param schoolYear
	 * @param term
	 * @return
	 */
	public static String[] getTimeQuantum(String schoolYear,String term){
		String[] result = new String[2],years = schoolYear.split(Separator);
		if(Globals.TERM_FIRST.equals(term)){
			result[0] = years[0]+Separator+School_Year_Date[0];
			result[1] = years[1]+Separator+School_Year_Date[1];
		}else if(Globals.TERM_SECOND.equals(term)){
			result[0] = years[1]+Separator+School_Year_Date[2];
			result[1] = years[1]+Separator+School_Year_Date[3];
		}
		return result;
	}
	
	/**
	 * 给定时间段内所包含的学年、学期
	 * @param start_date
	 * @param end_date
	 * @return List<List<String>>
	 * <br> [ [2014,01],[2014,02],[2015,01] ]
	 */
	public static List<List<String>> getXnXqByDate(String start_date, String end_date){
		List<List<String>> list = new ArrayList<>();
		int start_year = Integer.valueOf(start_date.substring(0, 4)) - 1,
			end_year   = Integer.valueOf(end_date.substring(0, 4)) + 1;
		List<List<String>> compareList = new ArrayList<>();
		for(int i=0,len=end_year-start_year+1; i<len; i++){
			int year = start_year+i, year2 = year+1;
			List<String> li_1 = new ArrayList<>();
			li_1.add(year+Separator+School_Year_Date[0]); //开始时间
			li_1.add(year2+Separator+School_Year_Date[1]); //结束时间
			li_1.add(year+""); //年
			li_1.add(Globals.TERM_FIRST); //学期
			List<String> li_2 = new ArrayList<>();
			li_2.add(year2+Separator+School_Year_Date[2]);
			li_2.add(year2+Separator+School_Year_Date[3]);
			li_2.add(year+"");
			li_2.add(Globals.TERM_SECOND);
			compareList.add(li_1);
			compareList.add(li_2);
		}
		long start_time = DateUtils.string2Date(start_date).getTime(),
			 end_time   = DateUtils.string2Date(end_date).getTime();
		for(List<String> cpLi : compareList){
			long time_1 = DateUtils.string2Date(cpLi.get(0)).getTime(),
				 time_2 = DateUtils.string2Date(cpLi.get(1)).getTime();
			if( (start_time <= time_1 && end_time >= time_1) || (start_time <= time_2 && end_time >= time_2) ){
				List<String> reOneL = new ArrayList<>();
				reOneL.add(cpLi.get(2));
				reOneL.add(cpLi.get(3));
				list.add(reOneL);
			}
		}
		return list;
	}
	
	/**
	 * 获取学年学期列表（倒序）
	 * @param minSchoolYear 最小学年
	 * @param minTermCode 最小学期
	 * @param maxSchoolYear 最大学年
	 * @param maxTermCode 最大学期
	 * @return List<Map<String,Object>>
	 * [
	 * {id:'2015-2016,02',mc:'2015-2016学年 第二学期'},
	 * {id:'2015-2016,01',mc:'2015-2016学年 第一学期'},
	 * {id:'2014-2015,02',mc:'2014-2015学年 第二学期'},
	 * {id:'2014-2015,01',mc:'2014-2015学年 第一学期'}
	 * ]
	 */
	public static List<Map<String, Object>> getBzdmXnXq(String minSchoolYear, String minTermCode, String maxSchoolYear, String maxTermCode) {
		List<Map<String, Object>> list = new ArrayList<>();
		
		// 最小学年 最小学期；最大学年 最大学期
		minSchoolYear = minSchoolYear.substring(0, 4);
		minSchoolYear = minSchoolYear.substring(0, 4);
		Integer minYear = minSchoolYear!=null ? Integer.valueOf(minSchoolYear) : null,
				maxYear = maxSchoolYear!=null ? Integer.valueOf(maxSchoolYear) : null;
		String minTerm  = minTermCode,
			   maxTerm  = maxTermCode,
			   yearSuf = "学年", middle = " ",
			   term1 = Globals.TERM_FIRST, term2 = Globals.TERM_SECOND,
			   termName1 = "第一学期", termName2 = "第二学期",
			   name1 = middle+termName1, name2 = middle+termName2;
		if(minYear != null && maxYear != null){
			if(minTerm != null && maxTerm != null){
				Integer year = minYear;
				String yearId = null;
				for( ; year<=maxYear; year++){
					yearId = year+"-"+(year+1);
					for(int i=1; i<=2; i++){
						if( (year.equals(minYear) && i==1 && minTerm.equals(term2)) 
							|| (year.equals(maxYear) && i==2 && maxTerm.equals(term1)) ){
							continue;
						}
						Map<String, Object> m3 = new HashMap<>();
						m3.put("id", yearId+","+ (i==1 ? term1 : term2) );
						m3.put("mc", yearId+yearSuf+ (i==1 ? name1 : name2) );
						list.add(m3);
					}
				}
				Collections.reverse(list);
			}
		}
		return list;
	}
	
	/**
	 * 获取当前学年的开始月份
	 * @return String
	 */
	public static int getMonthThisXn(){
		return Integer.valueOf(School_Year_Date[0].substring(0, 2).replaceAll("^0*", ""));
	}
	
	/**
	 * 获取当前学期的开始月份
	 * @return String
	 */
	public static int getMonthThisXq(){
		return Integer.valueOf(getTimeQuantumXq()[0].substring(5, 7).replaceAll("^0*", ""));
	}
	
	/**
	 * 根据身份证号返回用户密码
	 * @param idno
	 * @param defaultValue
	 * @return
	 */
	public static String getPasswordByIdno(String idno,String defaultValue){
		String password = "";
		if("".equals(idno) || idno==null) return defaultValue;
		if(idno.length()==18){
			 password=idno.substring(11, 17);// 索引，包含11 不包含17
		}else if(idno.length()==16) {
			password=idno.substring(10);// 截取最后6位
		}else{
			password = defaultValue;
		}
		return password;
	}
	
	/**
	 * 获取年级标准代码
	 * @return List<Map<String,Object>>
	 * <br> [ {name:'一年级', id:1}, {} ]
	 */
	public static List<Map<String, Object>> getBzdmNj(){
		return NjBzdm;
	}
	/**
	 * 获取某个学年，这个入学年级的学生是几年级
	 * @param schoolYear 学年；eg:2014
	 * @param rxnj 入学年级；eg:2012
	 * @return String；eg:'三年级'
	 */
	public static String getNjName(int schoolYear, int rxnj){
		int realNjCode = schoolYear + 1 - rxnj; // 在这个学年，这个入学年级的学生是哪个年级
		return getNjNameByCode(realNjCode);
	}
	/**
	 * 在某个学年，不同入学年级
	 * @param realNjCode eg:1/2/3/4/5
	 * @return String "一年级"/"二年级"...
	 */
	public static String getNjNameByCode(int realNjCode){
		String name = "";
		if(NjCodeName.containsKey(realNjCode))
			name = NjCodeName.get(realNjCode);
		return name;
	}
	
	/**
	 * 根据毕业届别、学制获取大学4年的8个学期对应的值
	 * @param endYear 2014
	 * @param schoolLength 学制
	 * @return
	 */
	public static List<Code> getFourSchoolyearByEndYear(int endYear,int schoolLength){
		List<Code> codes = new ArrayList<Code>();
		List<Integer> schoolYears = new ArrayList<Integer>();
		for(int i=schoolLength;i>=1;i--){
			schoolYears.add(endYear-i);
		}
		int i = 1;
		for(int schoolYear : schoolYears){
			StringBuffer sb = new StringBuffer();
			sb.append(schoolYear).append(Separator).append(schoolYear+1).append(":").append(Globals.TERM_FIRST);
			Code code = new Code(nameVal.get(i),sb.toString());
			codes.add(code);
			i++;
			
			StringBuffer sb1 = new StringBuffer();
			sb1.append(schoolYear).append(Separator).append(schoolYear+1).append(":").append(Globals.TERM_SECOND);
			Code code1 = new Code(nameVal.get(i),sb1.toString());
			codes.add(code1);
			i++;
		}
		return codes;
	}
	/**
	 * 根据入学年级和学年获得年级
	 * @param schoolYear 学年
	 * @param enrollGrade 入学年级
	 * @return int  eg:1,2,3,4,5
	 */
    public static int getGradeByYearAndEnrollGrade(String schoolYear,String enrollGrade){
        int year = Integer.parseInt(schoolYear.substring(0, 4));
        int enroll = Integer.parseInt(enrollGrade);
    	return year-enroll+1;
    }
    /**
	 * 根据年级和学年获得入学年级
	 * @param schoolYear 学年
	 * @param grade 年级 eg:1,2,3,4,5
	 * @return String eg:'2015'  
	 */
    public static String getEnrollGradeByYearAndGrade(String schoolYear,int grade){
        int year = Integer.parseInt(schoolYear.substring(0, 4));
    	return String.valueOf(year-grade+1);
    }
	/** 编码对象类，内部类*/
	public static class Code{
		private String name;
		private String value;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public Code(String name, String value) {
			super();
			this.name = name;
			this.value = value;
		}
		@Override
		public String toString() {
			StringBuffer sb = new StringBuffer("[");
			sb.append(this.name).append("=").append(this.value).append("]");
			return sb.toString();
		}
		
	}
	
	public static void main(String[] args){
		/*List<Code> codes = getFourSchoolyearByEndYear(2015, 4);
		for(Code code : codes){
			System.out.println(code);
		}*/
		/*String [] ary = getTimeQuantumXn();
		for(String s : ary){
			System.out.println(s);
		}
		ary = getTimeQuantumXq();
		for(String s : ary){
			System.out.println(s);
		}*/
		System.out.println(getMonthThisXn());
	}
}
