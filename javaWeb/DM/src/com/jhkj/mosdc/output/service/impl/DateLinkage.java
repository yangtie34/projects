package com.jhkj.mosdc.output.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jhkj.mosdc.output.po.FunComponent;

/***
 * 日期联动日期工具类。
 * 
 * @Comments: 
 * Company: 河南精华科技有限公司
 * Created by zhangzg(vbxnt521_edu@163.com)
 * @DATE: 2012-11-13
 * @TIME: 上午11:06:20
 */
public class DateLinkage {
	private DateUtils dateUtil;
	
	public void setDateUtil(DateUtils dateUtil) {
		this.dateUtil = dateUtil;
	}
	/***
	 * 获取日期组件数据。
	 * @param 需要提供的日期类型的信息。example ："本年,本季度,上月,本月,本周,今日,自定义"
	 * @return
	 */
	public FunComponent queryDateData(FunComponent fc , String param){
		String str ="本年,本学期,本季度,上月,本月,本周,今日,自定义";
		if(param==null||"".equals(param)){
			param ="本年,本学期,本季度,上月,本月,本周,今日,自定义";
		}
		/*KLUDGE : 权宜之计，我先将param赋值成如下值，这样方法将返回全部的日期列表。*/
		param ="本年,本学期,本季度,上月,本月,本周,今日,自定义";
		DateLinkage.LinkageObj ldo = new DateLinkage.LinkageObj("linkage",new ArrayList<IDateObj>());
		Map result  = new HashMap();
		String[] iStrs = param.split(",");
		try{
			for(String istr : iStrs){
				int index = str.indexOf(istr);
				switch(index){
					case 0 : // 本年
						Map<String,String> yearSS = DateUtils.getStartstopDateOfCurrentYear();
						DateLinkage.IDateObj innerIdo = new DateLinkage.IDateObj(istr,yearSS);
						ldo.getIdos().add(innerIdo);
						break;
					case 7 : // 本季度
						Map<String,String> quarterSS = DateUtils.getStartstopDateOfCurrentQuarter();
						DateLinkage.IDateObj quarterIdo = new DateLinkage.IDateObj(istr,quarterSS);
						ldo.getIdos().add(quarterIdo);
						break;
					case 11 : // 上月
						DateLinkage.IDateObj lastMonthIdo = new DateLinkage.IDateObj(istr,new HashMap<String,String>());
						ldo.getIdos().add(lastMonthIdo);
						break;
					case 14 : // 本月
						Map<String,String> monthSS = DateUtils.getStartstopDateOfCurrentMonth();
						DateLinkage.IDateObj monthIdo = new DateLinkage.IDateObj(istr,monthSS);
						ldo.getIdos().add(monthIdo);
						break;
					case 17 : // 本周
						Map<String,String> weekSS = DateUtils.getStartstopDateOfCurrentWeek();
						DateLinkage.IDateObj weekIdo = new DateLinkage.IDateObj(istr,weekSS);
						ldo.getIdos().add(weekIdo);
						break;
					case 20 : // 今日
						Map<String,String> today = new HashMap<String,String>();
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						today.put("startDate", df.format(new Date()));
						today.put("endDate", df.format(new Date()));
						DateLinkage.IDateObj todayIdo = new DateLinkage.IDateObj(istr,today);
						ldo.getIdos().add(todayIdo);
						break;
					case 23 : // 自定义
						DateLinkage.IDateObj myIdo = new DateLinkage.IDateObj(istr,new HashMap<String,String>());
						ldo.getIdos().add(myIdo);
						break;
					case 3: // 本学期
						Map<String,String> semesterSS = dateUtil.getStartstopDateOfCurrentSemester();
						DateLinkage.IDateObj semesterIdo = new DateLinkage.IDateObj(istr,semesterSS);
						ldo.getIdos().add(semesterIdo);
						break;
				}
			}
			// 将对象转换为map
			//result = Struts2Utils.jsonToMap(Struts2Utils.object2json(ldo));
			result.put("data", ldo);
			fc.setDisplayData(result);
		}catch(Exception e){
			e.printStackTrace();
		}
		return fc;
	}
	/**
	 * 参数数据对象内部类。
	 * 
	 * @Comments: 
	 * Company: 河南精华科技有限公司
	 * Created by zhangzg(vbxnt521_edu@163.com)
	 * @DATE: 2012-11-14
	 * @TIME: 下午12:05:18
	 */
	public class IDateObj {
		private String text;
		private Map<String,String> params;
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public Map<String, String> getParams() {
			return params;
		}
		public void setParams(Map<String, String> params) {
			this.params = params;
		}
		public IDateObj(String text, Map<String, String> params) {
			super();
			this.text = text;
			this.params = params;
		}
	}
	/**
	 * 联动数据对象。
	 * 
	 * @Comments: 
	 * Company: 河南精华科技有限公司
	 * Created by zhangzg(vbxnt521_edu@163.com)
	 * @DATE: 2012-11-14
	 * @TIME: 下午12:06:30
	 */
	public class LinkageObj{
		private String type;
		private List<IDateObj> idos;
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public List<IDateObj> getIdos() {
			return idos;
		}
		public void setIdos(List<IDateObj> idos) {
			this.idos = idos;
		}
		public LinkageObj(String type, List<IDateObj> idos) {
			super();
			this.type = type;
			this.idos = idos;
		}
	}
}
