package com.jhkj.mosdc.sc.job.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;
import com.jhkj.mosdc.framework.util.DateUtils;
import com.jhkj.mosdc.sc.job.IndexShowService;
import com.jhkj.mosdc.sc.job.IndexShowState;

public class IndexShowServiceImpl extends BaseServiceImpl implements IndexShowService{
	
	
	@Override
	public void saveindexShowJob() {
		clearIndexShow();
		lastDay();
		tryToday();
		lastWorkTop();
	}
	
	@Override
	public void saveindexToday() {
		clearIndexToday();
		toDay();
	}
	
	
	private void clearIndexShow(){
		String sql="DELETE TB_JOB_INDEX";
		baseDao.deleteBySql(sql);
		System.out.println("---已清空统计信息---");
		sql="DELETE TB_JOB_INDEX_TOP";
		baseDao.deleteBySql(sql);
		System.out.println("---已清空Top信息---");
	}
	
	private void clearIndexToday(){
		String sql="DELETE TB_JOB_INDEX_TODAY";
		baseDao.deleteBySql(sql);
		System.out.println("---已清空今日统计信息---");
	}
	
	private void toDay(){
		String dateString=DateUtils.SDF.format(new Date());
		
		System.out.println("---今日：开始BOOK_READ---");
		Map<String,String> map=getLastDayData(dateString,"t_ts_jy","jsrq","count(*)",13);
		insertIndexToday(map.get("sums"),map.get("peak"),map.get("peakTime"),IndexShowState.BOOK_READ);
		
		System.out.println("---今日：结束BOOK_READ---");
		System.out.println("---今日：开始BOOK_RKE---");
		
		map=getLastDayData(dateString,"tb_ykt_mjmx_tsg","sksj","count(*)",13);
		insertIndexToday(map.get("sums"),map.get("peak"),map.get("peakTime"),IndexShowState.BOOK_RKE);

		System.out.println("---今日：结束BOOK_RKE---");
		System.out.println("---今日：开始CARD_SUM---");
		
		map=getLastDayData(dateString,"tb_ykt_xfmx","xfsj","count(*)",13);
		insertIndexToday(map.get("sums"),map.get("peak"),map.get("peakTime"),IndexShowState.CARD_SUM);

		System.out.println("---今日：结束CARD_SUM---");
		System.out.println("---今日：开始CARD_MONEY---");
		
		map=getLastDayData(dateString,"tb_ykt_xfmx","xfsj","sum(xfje)",13);
		insertIndexToday(map.get("sums"),map.get("peak"),map.get("peakTime"),IndexShowState.CARD_MONEY);
		System.out.println("---今日：结束CARD_MONEY---");
		
	}
	
	private void insertIndexToday(String sums,String peak,String peakTime,int type){
		String addTime=DateUtils.SSS.format(new Date());
		String insert = "INSERT INTO TB_JOB_INDEX_TODAY "
				+ "VALUES('"+sums+"','"+peak+"','"+peakTime+"','"+addTime+"',"+type+")";
		baseDao.insert(insert);
	}

	private void lastDay() {
		String dateString=DateUtils.getYesterday();
		
		System.out.println("---昨日：开始BOOK_READ---");
		Map<String,String> map=getLastDayData(dateString,"t_ts_jy","jsrq","count(*)",13);
		insertTable(map.get("sums"),map.get("peak"),map.get("peakTime"),IndexShowState.BOOK_READ,1);
		
		System.out.println("---昨日：结束BOOK_READ---");
		System.out.println("---昨日：开始BOOK_RKE---");
		
		map=getLastDayData(dateString,"tb_ykt_mjmx_tsg","sksj","count(*)",13);
		insertTable(map.get("sums"),map.get("peak"),map.get("peakTime"),IndexShowState.BOOK_RKE,1);

		System.out.println("---昨日：结束BOOK_RKE---");
		System.out.println("---昨日：开始CARD_SUM---");
		
		map=getLastDayData(dateString,"tb_ykt_xfmx","xfsj","count(*)",13);
		insertTable(map.get("sums"),map.get("peak"),map.get("peakTime"),IndexShowState.CARD_SUM,1);

		System.out.println("---昨日：结束CARD_SUM---");
		System.out.println("---昨日：开始CARD_MONEY---");
		
		map=getLastDayData(dateString,"tb_ykt_xfmx","xfsj","sum(xfje)",13);
		insertTable(map.get("sums"),map.get("peak"),map.get("peakTime"),IndexShowState.CARD_MONEY,1);
		System.out.println("---昨日：结束CARD_MONEY---");
	}

	private void tryToday() {
		List<Date> dates=DateUtils.getBeforeDates(new Date(), 7);
		
		System.out.println("---预测：开始BOOK_READ---");
		
		String sums=getTodaySums(dates,"t_ts_jy","jsrq","count(*)");
		Map<String,String> map=getTodayPeak(dates,"t_ts_jy","jsrq","count(*)",13);
		insertTable(sums,map.get("peak"),map.get("peakTime"),IndexShowState.BOOK_READ,0);

		System.out.println("---预测：结束BOOK_READ---");
		System.out.println("---预测：开始BOOK_RKE---");
		
		sums=getTodaySums(dates,"tb_ykt_mjmx_tsg","sksj","count(*)");
		map=getTodayPeak(dates,"tb_ykt_mjmx_tsg","sksj","count(*)",13);
		insertTable(sums,map.get("peak"),map.get("peakTime"),IndexShowState.BOOK_RKE,0);

		System.out.println("---预测：结束BOOK_RKE---");
		System.out.println("---预测：开始CARD_SUM---");
		
		sums=getTodaySums(dates,"tb_ykt_xfmx","xfsj","count(*)");
		map=getTodayPeak(dates,"tb_ykt_xfmx","xfsj","count(*)",13);
		insertTable(sums,map.get("peak"),map.get("peakTime"),IndexShowState.CARD_SUM,0);

		System.out.println("---预测：结束CARD_SUM---");
		System.out.println("---预测：开始CARD_MONEY---");
		
		sums=getTodaySums(dates,"tb_ykt_xfmx","xfsj","sum(xfje)");
		map=getTodayPeak(dates,"tb_ykt_xfmx","xfsj","sum(xfje)",13);
		insertTable(sums,map.get("peak"),map.get("peakTime"),IndexShowState.CARD_MONEY,0);
		System.out.println("---预测：结束CARD_MONEY---");
		
	}

	private void lastWorkTop() {
		List<Date> dates=DateUtils.getBeforeDates(new Date(), 7);
		System.out.println("---TOP：开始BOOK---");
		String sql="select b.*,'' xh  from (select a.*, rownum r  from (select * "+
					  "from (select jy.tsmc mc, count(*) sl from t_ts_jy jy "+
					  "inner join t_ts ts on jy.tstm = ts.tstm "+
					  "inner join tb_xjda_xjxx xs on jy.dztm = xs.xh "+
					  "where jy.tsmc is not null and jy.jsrq between "+
					  "'"+DateUtils.SDF.format(dates.get(6))+"'and '"+DateUtils.SDF.format(dates.get(0))+"' "+
					  "group by jy.tsmc) order by sl desc) a)b "+
					  "where b.r > 0and b.r <= 3 ";
		insertTop(sql,IndexShowState.BOOK);
		System.out.println("---TOP：结束BOOK---");
		
		System.out.println("---TOP：开始RKE---");
		sql="select * from ( select a.*,rownum r from ( select * "+
			"from ( select xs.xh xh ,xs.xm mc,count(*) sl from tb_ykt_mjmx_tsg jy  "+
			"inner join tb_ykt_kh kh on jy.dzzh=kh.kh "+
			"inner join tb_xjda_xjxx xs on kh.xgh=xs.xh "+
			"where xs.xm is not null and jy.sksj between  "+
			"'"+DateUtils.SDF.format(dates.get(6))+"'and '"+DateUtils.SDF.format(dates.get(0))+"' "+
			"group by xs.xh,xs.xm )  order by sl desc ) a ) "+
			"where r>0and r<=3";
		insertTop(sql,IndexShowState.RKE);
		System.out.println("---TOP：结束RKE---");
		
		System.out.println("---TOP：开始JY---");
		sql="select * from ( select a.*,rownum r from ( select * "+
			"from ( select xs.xh xh ,xs.xm mc,count(*) sl from t_ts_jy jy  "+
			"inner join t_ts ts on jy.tstm=ts.tstm "+
			"inner join tb_xjda_xjxx xs on jy.dztm=xs.xh "+
			"where xs.xm is not null and jy.jsrq between  "+
			"'"+DateUtils.SDF.format(dates.get(6))+"'and '"+DateUtils.SDF.format(dates.get(0))+"' "+
			"group by xs.xh,xs.xm )  order by sl desc ) a ) "+
			"where r>0and r<=3";
		insertTop(sql,IndexShowState.JY);
		System.out.println("---TOP：结束JY---");
	}
	
	private Map<String,String> getLastDayData(String dateString,String table,String time,String type,int sub){
		Map<String,String> map=new HashMap<String, String>();
		String sql="select nvl("+type+",'0') sums from "+table+" where "+time+" like '"+dateString+"%'";
		String sums=baseDao.querySqlCount(sql) +"";
		String sql2=" select * from  ( "+
					" select a,substr(b,"+(sub-1)+",2) b,rownum r from (select nvl("+type+",'0') a, substr("+time+", 0, "+sub+") b "+
					"  from "+table+" where "+time+" like '"+dateString+"%' "+
					" group by substr("+time+", 0, "+sub+") order by a desc ) )where r=1";
		List<Map<String,Object>> list=baseDao.querySqlList(sql2);
		int peak=0;
		String peakTime="";
		if(list.size()>0){
			peak=(int) Double.parseDouble(list.get(0).get("a").toString());
			peakTime=list.get(0).get("b").toString();
		}
		map.put("sums", sums);
		map.put("peak", peak+"");
		map.put("peakTime", peakTime);
		return map;
	}
	
	private String getTodaySums(List<Date> dates,String table,String time,String type){
		String sql="";
		for (int i = 0; i < dates.size(); i++) {
			if(i==0){
				sql="select nvl("+type+",'0') sums from "+table+" where "+time+" like '"+DateUtils.SDF.format(dates.get(i))+"%' ";
			}else{
				sql+=" union all select nvl("+type+",'0') sums from "+table+" where "+time+" like '"+DateUtils.SDF.format(dates.get(i))+"%' ";
			}
		}
		sql=" select * from ( "+sql+" ) order by sums";
		List<Map<String,Object>> list=baseDao.querySqlList(sql);
		int sum =0;
		int num=0;
		int zhong=list.size()/2;
		for (int i = 0; i < list.size(); i++) {
			double thisSum=Double.parseDouble(list.get(i).get("sums").toString());
			double zSum= Double.parseDouble(list.get(zhong).get("sums").toString());
			if(!(zSum/3>thisSum || thisSum/3>zSum)){
				sum=(int) (sum+thisSum);
				num++;
			}
		}
		if(num==0){
			return "0";
		}
		int sums=sum/num;
		return sums+"";
	}
	
	private Map<String,String> getTodayPeak(List<Date> dates,String table,String time,String type,int sub){
		Map<String,String> map=new HashMap<String, String>();
		String sql="";
		for (int i = 0; i < dates.size(); i++) {
			if(i==0){
				sql="select * from ( select a,b,rownum r from (select nvl("+type+",'0') a, substr("+time+", 0, "+sub+") b "+
						" from "+table+"  where "+time+" like '"+DateUtils.SDF.format(dates.get(i))+"%' "+
						" group by substr("+time+", 0, "+sub+") order by a desc ) ) where r=1 ";
			}else{
				sql+=" union all select * from ( select a,b,rownum r from (select nvl("+type+",'0') a, substr("+time+", 0, "+sub+") b "+
						" from "+table+"  where "+time+" like '"+DateUtils.SDF.format(dates.get(i))+"%' "+
						" group by substr("+time+", 0, "+sub+") order by a desc ) ) where r=1 ";
			}
		}
		String exeSql=" select * from ( "+sql+" ) order by a";
		List<Map<String,Object>> list=baseDao.querySqlList(exeSql);
		int sum =0;
		int num=0;
		int zhong=list.size()/2;
		for (int i = 0; i < list.size(); i++) {
			double thisSum=Double.parseDouble(list.get(i).get("a").toString());
			double zSum= Double.parseDouble(list.get(zhong).get("a").toString());
			if(!(zSum/3>thisSum || thisSum/3>zSum)){
				sum=(int) (sum+thisSum);
				num++;
			}
		}
		int peak=0;
		if(num!=0){
			peak=sum/num;
		}
		//主要是当为空时，无数据，若有4天无数据，则可认为今日为空（这样推出仍有漏洞，日后需要进步不修改，如今是要与“今天预测总量”同步，故强行设置为空值）
		if(list.size()<4){
			peak=0;
		}
		map.put("peak",peak+"");
		exeSql="select mc from ( select a,mc,rownum r from (select count(*) a,substr(b,"+(sub-1)+",2) mc "+
				"from ( "+sql+" ) group by substr(b,"+(sub-1)+",2) order by a desc) ) where r=1";
		list=baseDao.querySqlList(exeSql);
		if(list.size()>0){
			map.put("peakTime", list.get(0).get("mc").toString());
		}
		if(list.size()==0 || peak==0){
			map.put("peakTime", "");
		}
		return map;
	} 
	
	private void insertTable(String sums,String peak,String peakTime,int state,int type){
		String addTime=DateUtils.SSS.format(new Date());
		String insert = "INSERT INTO TB_JOB_INDEX (sums,peak,peak_time,data_type,islast,add_time) "
				+ "VALUES('"+sums+"','"+peak+"','"+peakTime+"',"+state+","+type+",'"+addTime+"')";
		baseDao.insert(insert);
	}
	
	private void insertTop(String sql,int type){
		List<Map<String,Object>> list=baseDao.querySqlList(sql);
		String addTime=DateUtils.SSS.format(new Date());
		for (int i = 0; i < list.size(); i++) {
			Map<String,Object> map=list.get(i);
			String insert = "INSERT INTO TB_JOB_INDEX_TOP (XH,MC,SL,PX,TYPE,add_time) "
					+ "VALUES('"+map.get("XH")+"','"+map.get("MC")+"','"
					+map.get("SL")+"',"+map.get("R")+","+type+",'"+addTime+"')";
			baseDao.insert(insert);
		}
		int i=3-list.size();
		for (int j = 1; j <= i; j++) {
			String insert = "INSERT INTO TB_JOB_INDEX_TOP (XH,MC,SL,PX,TYPE,add_time) "
					+ "VALUES('','--','--',"+(i+list.size())+","+type+",'"+addTime+"')";
			baseDao.insert(insert);
		}
	}
	
}
