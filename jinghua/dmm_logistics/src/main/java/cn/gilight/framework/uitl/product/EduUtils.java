package cn.gilight.framework.uitl.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.gilight.framework.uitl.common.DateUtils;

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
		nameVal.put(1, "一年级上");
		nameVal.put(2, "一年级下");
		nameVal.put(3, "二年级上");
		nameVal.put(4, "二年级下");
		nameVal.put(5, "三年级上");
		nameVal.put(6, "三年级下");
		nameVal.put(7, "四年级上");
		nameVal.put(8, "四年级下");
		nameVal.put(9, "五年级上");
		nameVal.put(10, "五年级下");
	}
	/**
	 * 给定一个日期返回该日期所处的学年学期
	 * @param date
	 * @return
	 */
	public static String[] getSchoolYearTerm(Date date){
		int month = Integer.parseInt(DateUtils.MONTH.format(date));
		int year = Integer.parseInt(DateUtils.YEAR.format(date));
		String[] result = new String[2];
		String term=getTermByMonth(month);
		if(Globals.TERM_FIRST.equals(term)){
			result[0] = year+"-"+(year+1);
		}else{
			result[0] = (year-1)+"-"+(year);
		}
		result[1]=term;
		return result;
	}
	
	/**
	 * 通过月份获取当前学期
	 * @param month
	 * @return
	 */
	public static String getTermByMonth(int month){
		if(month>=Integer.parseInt(Globals.CUT_TERM)&&month<Integer.parseInt(Globals.CUT_YEAR)){
			return Globals.TERM_SECOND;
		}else{
			return Globals.TERM_FIRST;
		}
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
			String[] temp = syt[0].split("-");
			int from = Integer.parseInt(temp[0])-1,to = Integer.parseInt(temp[1])-1;
			syt[0]=from+"-"+to;
			syt[1]=Globals.TERM_SECOND;
			return syt;
		}
		
	}
	/**
	 * 给定一个学年返回该学年对应的日期时间段
	 * @param schoolYear
	 * @return
	 */
	public static String[] getTimeQuantum(String schoolYear){
		String[] result = new String[2],years = schoolYear.split("-");
		result[0] = years[0]+"-08-01";
		result[1] = years[1]+"-07-01";
		return result;
	}
	
	
	
	/**
	 * 给定学年、学期返回该学年对应的日期时间段
	 * @param schoolYear
	 * @param term
	 * @return
	 */
	public static String[] getTimeQuantum(String schoolYear,String term){
		String[] result = new String[2],years = schoolYear.split("-");
		if(Globals.TERM_FIRST.equals(term)){
			result[0] = years[0]+"-08-01";
			result[1] = years[1]+"-02-01";
		}else if(Globals.TERM_SECOND.equals(term)){
			result[0] = years[1]+"-02-01";
			result[1] = years[1]+"-07-01";
		}
		return result;
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
			sb.append(schoolYear).append("-").append(schoolYear+1).append(":").append(Globals.TERM_FIRST);
			Code code = new Code(nameVal.get(i),sb.toString());
			codes.add(code);
			i++;
			
			StringBuffer sb1 = new StringBuffer();
			sb1.append(schoolYear).append("-").append(schoolYear+1).append(":").append(Globals.TERM_SECOND);
			Code code1 = new Code(nameVal.get(i),sb1.toString());
			codes.add(code1);
			i++;
		}
		return codes;
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
		List<Code> codes = getFourSchoolyearByEndYear(2015, 4);
		for(Code code : codes){
			System.out.println(code);
		}
	}
}
