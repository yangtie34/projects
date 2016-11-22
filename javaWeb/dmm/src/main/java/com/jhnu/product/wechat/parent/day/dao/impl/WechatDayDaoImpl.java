package com.jhnu.product.wechat.parent.day.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jhnu.enums.ActionEnum;
import com.jhnu.enums.ActionFromEnum;
import com.jhnu.enums.CardPayEnum;
import com.jhnu.enums.WarnLevelEnum;
import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.wechat.parent.day.dao.WechatDayDao;
import com.jhnu.product.wechat.parent.day.entity.WechatDay;
import com.jhnu.util.common.DateUtils;
import com.jhnu.util.common.PropertiesUtils;
import com.jhnu.util.common.StringUtils;

@Repository("wechatDayDao")
public class WechatDayDaoImpl implements WechatDayDao{

	@Autowired
	private BaseDao baseDao;
	
	@Override
	public void saveWechatDays(List<WechatDay> days) {
		final int COUNT = days.size();
		final List<WechatDay> DAYS=days;
		baseDao.getBaseDao().getJdbcTemplate().batchUpdate(
				"insert into t_wechat_day(id,stu_id,action_code,warn_level_code,address,action,start_time "+
				",end_time,action_date,exe_time,exe_from,class_id) values (id_seq.nextval,?, ?, ?, ?, ?, ?, ?, ? , ? ,?,?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
		                ps.setString(1, DAYS.get(i).getStu_id());
		                ps.setString(2, DAYS.get(i).getAction_code());  
		                ps.setString(3, DAYS.get(i).getWarn_level_code());  
		                ps.setString(4, DAYS.get(i).getAddress());  
		                ps.setString(5, DAYS.get(i).getAction());  
		                ps.setString(6, DAYS.get(i).getStart_time());  
		                ps.setString(7, DAYS.get(i).getEnd_time());  
		                ps.setString(8, DAYS.get(i).getAction_date());  
		                ps.setString(9, DAYS.get(i).getExe_time());
		                ps.setString(10, DAYS.get(i).getExe_from());
		                ps.setString(11, DAYS.get(i).getClass_id());
		              }    
		              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 }); 
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<WechatDay> getWechatDaysByCard(String lastTime,String exeTime) {
		String action =" '消费 '|| to_char(a.pay_money,'fm99999990.999') ||' 元' ";
		String sql="select s.no_ stu_id, case a.card_deal_id when '"+CardPayEnum.CTXF.getCode()+"' then '"+ActionEnum.CTXF.getCode()+"' "+
				  "when '"+CardPayEnum.CSXF.getCode()+"' then '"+ActionEnum.CSXF.getCode()+"' when '"+CardPayEnum.XYXF.getCode()+"' then '"+ActionEnum.XYXF.getCode()+"' "+
				  "else '"+ActionEnum.YLXF.getCode()+"' end action_code, "+
				  "'"+WarnLevelEnum.PT.getCode()+"' warn_level_code,d.name_ address, "+
				  action+" action, substr(a.time_,12,8) start_time,'' end_time,substr(a.time_,0,10) action_date,? exe_time ,'"+ActionFromEnum.XF.getCode()+"' exe_from "+
				  "from (select * from t_card_pay where time_ between ? and ? "+
				  "and card_deal_id in("+CardPayEnum.CTXF.getCode()+","+CardPayEnum.CSXF.getCode()+","+CardPayEnum.XYXF.getCode()+","+CardPayEnum.YLXF.getCode()+") ) a  "+
		          "inner join t_card c on c.no_=a.card_id  "+
		          "inner join t_stu s on s.no_=c.people_id "+
		          "left join t_card_port p on a.card_port_id = p.no_ "+
		          "left join t_card_dept d on p.card_dept_id=d.id "+
		          "left join t_code_card_deal cd on a.card_deal_id= cd.id ";
		return baseDao.getBaseDao().getJdbcTemplate().query(sql,new Object[]{exeTime,lastTime,exeTime}, (RowMapper) new BeanPropertyRowMapper(WechatDay.class));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<WechatDay> getWechatDaysByCourse(String year,String term,int zc,int weak,String action_date) {
		String sql="select ca.period || '上“' || c.name_||'”' action,ctx.class_id class_id,? action_date,? exe_time,'"+ActionEnum.KC.getCode()+"' action_code, '"+WarnLevelEnum.PT.getCode()+"' warn_level_code, "+
					 "cpt.start_time start_time ,'"+ActionFromEnum.KC.getCode()+"' exe_from "+
					 "from t_course_arrangement ca "+
					 "inner join t_course c on ca.course_id = c.id "+
					 "inner join t_class_teaching_xzb ctx on ca.teachingclass_id = ctx.teach_class_id "+
					 "inner join T_COURSE_PERIOD_TIME  cpt on substr(ca.period, 2, instr(ca.period, '-') -2 )=cpt.id "+
					 "where ca.school_year=? and ca.term_code=? and ca.weeks like ? and ca.day_of_week=? ";
		return baseDao.getBaseDao().getJdbcTemplate().query(sql,new Object[]{action_date,DateUtils.SSS.format(new Date()),year,term,"%,"+zc+",%",weak}, (RowMapper) new BeanPropertyRowMapper(WechatDay.class));
	}

	@Override
	public List<WechatDay> getWechatDaysByWarn() {
		// TODO 一天生活警告信息没有做
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<WechatDay> getWechatDaysByDormRKE(String lastTime,String exeTime) {
		String sql="select s.no_ stu_id,'"+ActionEnum.SSMJ.getCode()+"' action_code, '"+WarnLevelEnum.PT.getCode()+"' warn_level_code,a.address address,'进出宿舍' action,  "+
					"substr(a.time_,12,8) start_time,'' end_time,substr(a.time_,0,10) action_date,? exe_time  ,'"+ActionFromEnum.SSMJ.getCode()+"' exe_from "+
					"from ( select b.dorm_proof_id rno,b.time_,b.address from t_dorm_rke b where b.time_ between ? and ? ) a  "+
					"inner join t_dorm_proof r on a.rno=r.no_  "+
					"inner join t_stu s on r.people_id=s.no_    ";
		return baseDao.getBaseDao().getJdbcTemplate().query(sql,new Object[]{exeTime,lastTime,exeTime}, (RowMapper) new BeanPropertyRowMapper(WechatDay.class));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<WechatDay> getWechatDaysByBookRKE(String lastTime,String exeTime) {
		String sql="select s.no_ stu_id,'"+ActionEnum.TSMJ.getCode()+"' action_code, '"+WarnLevelEnum.PT.getCode()+"' warn_level_code,'图书馆' address,'进出图书馆' action, "+
				"substr(a.time_,12,8) start_time,'' end_time,substr(a.time_,0,10) action_date,? exe_time ,'"+ActionFromEnum.TSMJ.getCode()+"' exe_from "+
				"from ( select b.book_reader_id rno,b.time_ from t_book_rke b where b.time_ between ? and ? ) a "+
				"inner join t_book_reader r on a.rno=r.no_  "+
				"inner join t_stu s on r.people_id=s.no_  ";
		return baseDao.getBaseDao().getJdbcTemplate().query(sql,new Object[]{exeTime,lastTime,exeTime}, (RowMapper) new BeanPropertyRowMapper(WechatDay.class));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<WechatDay> getWechatDaysByLeave(String lastTime, String exeTime) {
		
		String sql = "select leave.stu_id,'"+ActionEnum.QJ.getCode()+"' action_code, '"+WarnLevelEnum.JG.getCode()+"' warn_level_code,'' address,'请假' action, "+
					 "substr(leave.start_time,12,8) start_time,'' end_time,substr(leave.start_time,0,10) action_date,? exe_time ,'"+ActionFromEnum.QJ.getCode()+"' exe_from "+
		       		 " from t_stu_leave leave "+
		       		 "inner join t_code co on co.code_ = leave.leave_code and co.code_type = 'LEAVE_CODE' "+
		       		 "where leave.add_time between ? and ?";
		
		return  baseDao.getBaseDao().getJdbcTemplate().
				query(sql,new Object[]{exeTime,lastTime,exeTime}, 
						(RowMapper) new BeanPropertyRowMapper(WechatDay.class));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<WechatDay> getWechatDaysByCancelLeave(String lastTime, String exeTime) {
		
		String sql = "select leave.stu_id,'"+ActionEnum.QJ.getCode()+"' action_code, '"+WarnLevelEnum.JG.getCode()+"' warn_level_code,'' address,'销假' action, "+
				 "substr(leave.cancel_time,12,8) start_time,'' end_time,substr(leave.cancel_time,0,10) action_date,? exe_time ,'"+ActionFromEnum.QJ.getCode()+"' exe_from "+
	       		 " from t_stu_leave leave "+
	       		 "inner join t_code co on co.code_ = leave.leave_code and co.code_type = 'LEAVE_CODE' "+
	       		 "where leave.cancel_time between ? and ?";
		
		return  baseDao.getBaseDao().getJdbcTemplate().
				query(sql,new Object[]{exeTime,lastTime,exeTime}, 
						(RowMapper) new BeanPropertyRowMapper(WechatDay.class));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<WechatDay> getWechatDaysByThis(WechatDay day) {
		String sql="select id,stu_id, action_code, warn_level_code, address, action, "+
					"start_time , end_time, action_date, exe_time, exe_from,class_id  "+
				 "from t_wechat_day where 1=1 ";
		if(day != null){
			if(day.getId() != null){
				sql+=" and id="+day.getId();
			}
			if(StringUtils.hasText(day.getStu_id())&&!StringUtils.hasText(day.getClass_id())){
				sql+=" and stu_id='"+day.getStu_id()+"'";
			}else if(!StringUtils.hasText(day.getStu_id())&&StringUtils.hasText(day.getClass_id())){
				sql+=" and class_id='"+day.getClass_id()+"'";
			}else if(StringUtils.hasText(day.getStu_id())&&StringUtils.hasText(day.getClass_id())){
				sql+=" and ( stu_id='"+day.getStu_id()+"' or class_id='"+day.getClass_id()+"' )";
			}
			if(day.getAction_date() != null && !"".equals(day.getAction_date())){
				sql+=" and action_date='"+day.getAction_date()+"'";
			}
		}
		
		String mesql="select 0 id,'me' stu_id,'' action_code,'' warn_level_code,'' address,'' action, "+
					 "to_char(sysdate,'hh24:mi:ss') start_time ,'' end_time,'' action_date,'' exe_time,'' exe_from ,'' class_id from dual";
		
		String endsql="select * from ( "+ sql +" union "+mesql+" ) order by start_time";
		
		return baseDao.getBaseDao().getJdbcTemplate().query(endsql, (RowMapper) new BeanPropertyRowMapper(WechatDay.class));
	}

	@Override
	public String getLaseExeTimeByFrom(String exe_from) {
		String sql="select max(action_date||' '||start_time) from t_wechat_day where exe_from = ? ";
		return baseDao.getBaseDao().getJdbcTemplate().queryForObject(sql,new Object[]{exe_from}, String.class);
	}

	@Override
	public void moveDayToLog(String action_date) {
		String logName=PropertiesUtils.getDBPropertiesByName("dataSource.username.log");
		String sql="insert into "+logName+".tl_wechat_day select * from t_wechat_day where action_date= ? ";
		baseDao.getBaseDao().getJdbcTemplate().update(sql, new Object[]{action_date});
		sql="delete from t_wechat_day where action_date= ?";
		baseDao.getBaseDao().getJdbcTemplate().update(sql, new Object[]{action_date});
	}


}
