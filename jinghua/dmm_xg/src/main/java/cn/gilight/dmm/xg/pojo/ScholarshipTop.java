package cn.gilight.dmm.xg.pojo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 学生奖学金总览-奖学金排行榜
 * 
 * @author xuebl
 * @date 2016年5月26日 上午10:33:26
 */
public class ScholarshipTop implements Comparable<ScholarshipTop>{

	/**
	 * 学生名称
	 */
	private String name;
	/**
	 * 学生id
	 */
	private String stu_id;
	/**
	 * 获奖数量
	 */
	private Integer count;
	/**
	 * 获奖总金额
	 */
	private Double money;
	/**
	 * 获奖学年s
	 */
	private String school_years;
	/**
	 * 连续获奖次数
	 */
	private Integer continue_count;
	
	
	public ScholarshipTop() {
	}
	public ScholarshipTop(String name, String stu_id, Integer count, Double money, String school_years, Integer continues) {
		this.name = name;
		this.stu_id = stu_id;
		this.count = count;
		this.money = money;
		this.school_years = school_years;
		this.continue_count = continues;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStu_id() {
		return stu_id;
	}
	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getSchool_years() {
		return school_years;
	}
	public void setSchool_years(String school_years) {
		this.school_years = school_years;
		initContinue_count();
	}
	public Integer getContinue_count() {
		return continue_count;
	}
	public void setContinue_count(Integer continue_count) {
		this.continue_count = continue_count;
	}
	private List<String> list = null;
	/**
	 * 初始化 最高连续次数
	 */
	private void initContinue_count(){
		if(school_years != null){
			String[] ary = school_years.split(",");
			list = Arrays.asList(ary);
			Collections.sort(list);
			int last_year  = 0, //上一个年
				last_count = 0, //上次持续
				this_count = 0; //这次持续
			boolean model_this = false; //启动本次模式
			for(int i=0,len=list.size(); i<len; i++){
				int year = Integer.valueOf(list.get(i).substring(0, 4));
				if(year - last_year == 1){
					if(!model_this){ //上次模式
						last_count++;
					}else{
						this_count++;
					}
				}else{
					model_this = true;
					this_count++;
				}
				// 超过上次之后，关闭本次模式，启动上次模式
				if(this_count > last_count){
					model_this = false;
					last_count = this_count;
					this_count = 0;
				}
				last_year = year;
			}
			this.continue_count = last_count;
		}
	}
	@Override
	public int compareTo(ScholarshipTop o) {
		/**
		 * 连续次数倒序排列
		 */
		int i = 0;
		if(this.continue_count > o.getContinue_count()) // 第一个数比第二个数大，不交换（1交换）
			i = -1;
		else if(this.continue_count < o.getContinue_count())
			i = 1;
		return i;
	}
	public static void main(String[] args) {
		/*ScholarshipTop t = new ScholarshipTop();
		t.setSchool_years("2001-2002,2002,2015-2016,2016,2011,2012-2013,2013,2014,2017");
		System.out.println(t.getContinue_count());*/
	}
}
