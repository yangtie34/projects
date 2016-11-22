package com.jhkj.mosdc.sc.job.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.jhkj.mosdc.framework.dao.impl.BaseDaoImpl;
import com.jhkj.mosdc.framework.util.StringUtils;
import com.jhkj.mosdc.newoutput.util.DateUtils;
import com.jhkj.mosdc.sc.job.BzxrstjDao;
import com.jhkj.mosdc.sc.job.JobStatusState;

/**   
* @Description: TODO 不在校人数统计
* @author Sunwg  
* @date 2014-9-9 下午4:06:49   
*/
public class BzxrstjDaoImpl extends BaseDaoImpl implements BzxrstjDao{

	/** 
	* @Title: bzxxsThreeDay 
	* @Description: TODO 统计三天内不在校人数
	* @return void
	*/
	@SuppressWarnings("rawtypes")
	public void bzxxsThreeDay() throws Exception{
		System.out.println("++++++++++++开始计算三天不在校学生：");
		JobStatusState.THREE_DAY_NOT_IN_SCHOOL = 0;
		long beginTime = System.currentTimeMillis();
		List<Map> stuList = this.queryXslb();
		System.out.println("----------------查询结束，删除历史数据：");
		this.deleteBySql("delete from tb_ykt_bzx t where t.days = 3");
		System.out.println("++++++++++++++++删除结束，开始插入：");
		int num = 0;
		Map sxMap = getSxMap();		
		for (int i = 0; i < stuList.size(); i++) {
			Map stu = stuList.get(i);
			String xfsj = (String) stu.get("xfsj");
			xfsj = ((xfsj == "") ? "0":xfsj);
			String mjsj = (String) stu.get("mjsj");
			mjsj = (mjsj == "") ? "0":mjsj;
			Calendar calen = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");
			String today = sdf.format(calen.getTime());
			Boolean notInSchool =(daysBetween(xfsj,today) > 3) && (daysBetween(mjsj,today) > 3);
			String xh = (String) stu.get("xh");
			if(sxMap.containsKey(xh))continue;
			if (notInSchool) {
				
				String xm = (String) stu.get("xm");
				String yxId = (String) stu.get("yx_id");
				String yx = (String) stu.get("yx");
				String zyId = (String) stu.get("zy_id");
				String zy = (String) stu.get("zy");
				String scxfsj = (String) stu.get("xfsj");
				String scmjsj = (String) stu.get("mjsj");
				String insertSql = "insert into tb_ykt_bzx  (xh, xm, yx_id, yx, zy_id, zy, scxfsj, scmjsj, days, tjsj) " +
						"values (''{0}'', ''{1}'', {2}, ''{3}'', {4}, ''{5}'', to_date(''{6}'',''yyyymmddhh24mi''),  to_date(''{7}'',''yyyymmddhh24mi''), 3, sysdate)";
				insertSql = StringUtils.format(insertSql, xh,xm,yxId,yx,zyId,zy,scxfsj,scmjsj);
				this.insert(insertSql);
				num++ ;
			}
		}
		JobStatusState.THREE_DAY_NOT_IN_SCHOOL = 1;
		System.out.println("------------结束计算三天不在校学生,共产生"+ num +"条不在校记录，耗时:" + (System.currentTimeMillis() - beginTime)/1000 + "秒");
	}
	
	/** 
	* @Title: queryXslb 
	* @Description: TODO 查询所有有效学生列表
	* @return List
	*/
	@SuppressWarnings("rawtypes")
	public List<Map> queryXslb(){
		Calendar c = Calendar.getInstance();
		int month = c.getTime().getMonth();
		String sql = "select xj.xh,xj.xh,xj.xm,xj.yx_id,xj.zy_id,yx.mc yx,zy.mc zy,to_char(max(xf.xfsj_date),'yyyymmddhh24mi') xfsj, to_char(max(mj.sksj),'yyyymmddhh24mi') mjsj from tb_xjda_xjxx xj " +
				"left join tb_ykt_xfmx xf on xj.xh = xf.ryid" +
				" left join tb_ykt_mjmx mj on mj.xh = xj.xh " +
				" left join tb_jxzzjg yx on yx.id = xj.yx_id and yx.cc = 1 " +
				" left join tb_jxzzjg zy on zy.id = xj.zy_id and zy.cc = 2 " +
				" where xj.xszt_id = 1 and xj.xjzt_id =1000000000000101 and substr(xj.yjbysj,0,4) " +
				((month < 7)?">=":">" ) +
				"to_char(sysdate,'yyyy')" +
				"group by xj.xh,xj.xh,xj.xm,xj.yx_id,xj.zy_id,yx.mc,zy.mc";
		
		return this.queryListMapInLowerKeyBySql(sql);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void saveYesterDayWG() throws Exception {
		JobStatusState.YESTERDAY_WG = 0;
		// 获取昨天的日期
		Date date = DateUtils.getNextDay(new Date(),-1);
		String yesterday = DateUtils.getDateFormat(date, "yyyy-MM-dd");
		String now = DateUtils.getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
		// 获取晚归的时间点
		String hours = ((Map)this.querySqlList("SELECT TJSJJX FROM TB_YKT_TEMP_WG_TJSJ")
				.get(0)).get("TJSJJX").toString();
		// 计算在指定时间点后刷卡的学生列表
		String xslb ="select t.*,xx.yx_id,xx.zy_id,xx.bj_id,xx.xb_id from "+
			"(select xh,xm,kh,skdd,to_char(max(sksj),'yyyy-mm-dd hh24:mi:ss') AS SKSJ from tb_ykt_mjmx "+
			"where to_char(sksj,'yyyy-mm-dd') = '"+yesterday+"' and to_char(sksj,'hh24:mi')>'"+hours+
			"' group by xh,xm,kh,skdd) t left join tb_xjda_xjxx xx on xx.xh = t.xh ";
		// 首先删除刷卡时间为昨天的临时记录！
		this.deleteBySql("delete from tb_ykt_temp_wg t where t.sksj like '"+yesterday+"%'");
		List<Map> xslbs = this.querySqlList(xslb);
		Map sxMap = getSxMap();
		// 保存学生列表 到指定的中间表
		for(Map xs : xslbs){
			String xh = MapUtils.getString(xs, "XH");
			if(sxMap.containsKey(xh))continue;
			String xm = MapUtils.getString(xs, "XM");
			String kh = MapUtils.getString(xs, "KH");
			String skdd = MapUtils.getString(xs, "SKDD");
			String sksj = MapUtils.getString(xs, "SKSJ");
			String yx_id = MapUtils.getString(xs, "YX_ID");
			String zy_id = MapUtils.getString(xs, "ZY_ID");
			String bj_id = MapUtils.getString(xs, "BJ_ID");
			String xb_id = MapUtils.getString(xs, "XB_ID");
			if("null".equals(yx_id)){
				continue;
			}
			String insertsql ="insert into tb_ykt_temp_wg values(''{0}'',''{1}'',''{2}'',''{3}'',''{4}'',''{5}'',''{6}'',''{7}'',''{8}'',''{9}'')";
			insertsql = MessageFormat.format(insertsql, xh,xm,kh,skdd,sksj,yx_id,zy_id,bj_id,xb_id,now);
			this.insert(insertsql);
		}
		JobStatusState.YESTERDAY_WG =1;
		System.out.println("=====晚寝晚归名单，插入完毕=====");
	}
	@Override
	public void saveYesterDayWZS() throws Exception {
		JobStatusState.YESTERDAY_WZS =0;
		Calendar c = Calendar.getInstance();
		int month = c.getTime().getMonth();
		// 获取昨天的日期
		Date date = DateUtils.getNextDay(new Date(),-1);
		String yesterday = DateUtils.getDateFormat(date, "yyyy-MM-dd");
		String now = DateUtils.getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
		
		System.out.println("=============查询数据未住宿学生");
		String xslb = "select * from (select xjxx.xh,xjxx.xm,xjxx.yx_id,xjxx.zy_id, xjxx.bj_id,xfmx.XFSJ," +
				"case when xfmx.xfcs is not null and to_char(mjmx.sksj,'yyyy-mm-dd hh24:mi:ss')<xfmx.xfsj then 1 "+
           " when xfmx.xfcs is not null and mjmx.skcs is null then 1"+
           "  else 0 end as zt,mjsj.scsksj from tb_xjda_xjxx xjxx " +
				"left join (select count(1) xfcs,ryid,max(xfsj) xfsj from tb_ykt_xfmx " +
				" Where to_char(xfsj_date, 'yyyy-mm-dd') = '"+yesterday+"'   group by ryid )xfmx  on xjxx.id = xfmx.ryid" +
				"  left join (select count(1) skcs,xh,max(sksj) sksj from tb_ykt_mjmx" +
				"  where to_char(sksj, 'yyyy-mm-dd') = '"+yesterday+"'  group by xh) mjmx on mjmx.xh = xjxx.xh " +
				"  left join (select xh,max(sksj) scsksj from tb_ykt_mjmx   where to_char(sksj, 'yyyy-mm-dd') <= '"+yesterday+"'  group by xh) mjsj on mjsj.xh = xjxx.xh " +
				"where xjxx.xszt_id = 1 and xjxx.xjzt_id = 1000000000000101  and substr(xjxx.yjbysj,0,4) " +
				((month < 7)?">=":">" ) +
				"to_char(sysdate,'yyyy') ) where zt = 1" ;
		// 首先删除刷卡时间为昨天的临时记录！
		this.deleteBySql("delete from tb_ykt_temp_wzs t where t.xfsj like '"+yesterday+"%'");
		List<Map> xslbs = this.querySqlList(xslb);
		List<Map> axslb = new ArrayList<Map>();
		Map sxMap = getSxMap();
		for(Map temp : xslbs){
			String xh = MapUtils.getString(temp, "XH");
			if(sxMap.containsKey(xh))continue;
			axslb.add(temp);
		}
		System.out.println("开始插入未住宿名单！");
		final List<Map> xslbs1 = axslb;
		
		String insertSql ="insert into tb_ykt_temp_wzs values(?,?,?,?,?,?,?,?)";
		this.getJdbcTemplate().batchUpdate(insertSql,new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, MapUtils.getString(xslbs1.get(i), "XH"));
				ps.setString(2, MapUtils.getString(xslbs1.get(i), "XM"));
				ps.setString(3, MapUtils.getString(xslbs1.get(i), "YX_ID"));
				ps.setString(4, MapUtils.getString(xslbs1.get(i), "ZY_ID"));
				ps.setString(5,  MapUtils.getString(xslbs1.get(i), "BJ_ID"));
				ps.setString(6,  MapUtils.getString(xslbs1.get(i), "SCSKSJ"));
				ps.setString(7,  MapUtils.getString(xslbs1.get(i), "XFSJ"));
				String now = DateUtils.getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
				ps.setString(8, now);
			}
			
			@Override
			public int getBatchSize() {
				return xslbs1.size();
			}
		});
		JobStatusState.YESTERDAY_WZS =1;
		System.out.println("=====未住宿名单，计算完毕！=====");
	}
	@Override
	public void saveDayWZS() throws Exception {
		Calendar c = Calendar.getInstance();
		int month = c.getTime().getMonth();
		// 获取当前日期
		Date date = DateUtils.getCurrentDate();
		String today = DateUtils.getDateFormat(date, "yyyy-MM-dd");
		String now = DateUtils.getDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
		
		System.out.println("=======>>23点半，查询当前疑似未住宿名单。");
		String xslb = "select * from (select xjxx.xh,xjxx.xm,xjxx.yx_id,xjxx.zy_id, xjxx.bj_id,xfmx.XFSJ," +
				"case when xfmx.xfcs is not null and to_char(mjmx.sksj,'yyyy-mm-dd hh24:mi:ss')<xfmx.xfsj then 1 "+
           " when xfmx.xfcs is not null and mjmx.skcs is null then 1"+
           "  else 0 end as zt,mjsj.scsksj from tb_xjda_xjxx xjxx " +
				"left join (select count(1) xfcs,ryid,max(xfsj) xfsj from tb_ykt_xfmx " +
				" Where to_char(xfsj_date, 'yyyy-mm-dd') = '"+today+"'   group by ryid )xfmx  on xjxx.id = xfmx.ryid" +
				"  left join (select count(1) skcs,xh,max(sksj) sksj from tb_ykt_mjmx" +
				"  where to_char(sksj, 'yyyy-mm-dd') = '"+today+"'  group by xh) mjmx on mjmx.xh = xjxx.xh " +
				"  left join (select xh,max(sksj) scsksj from tb_ykt_mjmx   where to_char(sksj, 'yyyy-mm-dd') <= '"+today+"'  group by xh) mjsj on mjsj.xh = xjxx.xh " +
				"where xjxx.xszt_id = 1 and xjxx.xjzt_id = 1000000000000101  and substr(xjxx.yjbysj,0,4) " +
				((month < 7)?">=":">" ) +
				"to_char(sysdate,'yyyy') ) where zt = 1" ;
		// 首先删除刷卡时间为今天的临时记录！
		this.deleteBySql("delete from TB_XTJC_WZS4SG t where t.xfsj like '"+today+"%'");
		List<Map> xslbs = this.querySqlList(xslb);
		System.out.println("=======>>开始插入23点半计算的疑似未住宿名单！");
		Map sxMap = getSxMap();
		int i = 0;
		// 保存学生列表 到指定的中间表
		for(Map xs : xslbs){
			String xh = MapUtils.getString(xs, "XH");
			if(sxMap.containsKey(xh)) continue;
			String xm = MapUtils.getString(xs, "XM");
			String xfsj = MapUtils.getString(xs, "XFSJ");
			String sksj = MapUtils.getString(xs, "SCSKSJ");
			String yx_id = MapUtils.getString(xs, "YX_ID");
			String zy_id = MapUtils.getString(xs, "ZY_ID");
			String bj_id = MapUtils.getString(xs, "BJ_ID");
			String insertsql ="insert into TB_XTJC_WZS4SG values(''{0}'',''{1}'',''{2}'',''{3}'',''{4}'',''{5}'',''{6}'',''{7}'')";
			insertsql = MessageFormat.format(insertsql, xh,xm,yx_id,zy_id,bj_id,sksj,xfsj,now);
			this.insert(insertsql);
			i++;
		}
		System.out.println("=======>>23点30分计入今日疑似未住宿名单，计算完毕，共插入 " +i+ "条数据到TB_YKT_TEMP_WZS4SG！=====");
	}
	/** 
     * 计算两个日期之间相差的天数 
     * @param str1 
     * @param str2 
     * @return 
     */  
	private int daysBetween(String str1,String str2){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		Calendar cal = Calendar.getInstance();
        try {
			cal.setTime(sdf.parse(str1));
			long time1 = cal.getTimeInMillis();               
	        cal.setTime(sdf.parse(str2));  
	        long time2 = cal.getTimeInMillis();     

	        long between_days=(time2-time1)/(1000*3600*24);  

	          
	        int betws = Integer.parseInt(String.valueOf(between_days)); 
        	return betws;
		} catch (ParseException e) {
			System.out.println("["+str1+"],不能按照yyyyMMddHHmm转化为日期。");
	        return -1;
		}
          
	}
	/**
	 * 获取在实习的学生名单
	 * @return
	 */
	private Map getSxMap(){
		String sxSql ="SELECT CODE,to_char(ENDTIME,'yyyy-MM-dd') endtime FROM TB_SC_LEAVESCHOOL LEAVE WHERE to_char(leave.endtime,'yyyy-MM-dd')>to_char(sysdate,'yyyy-MM-dd')";
		List<Map> sxStuList =this.queryListMapInLowerKeyBySql(sxSql); // 查询出还在实习的学生名单
		
		Map<String,String> sxMap = new HashMap<String,String>();
		for(Map temp : sxStuList){
			String code = temp.get("code").toString();
			String count = temp.get("endtime")==null ? "" : temp.get("endtime").toString();
			sxMap.put(code, count);
		}
		return sxMap;
	}
}
