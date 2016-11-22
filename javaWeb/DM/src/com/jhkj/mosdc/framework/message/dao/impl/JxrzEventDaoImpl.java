package com.jhkj.mosdc.framework.message.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import com.jhkj.mosdc.framework.dao.impl.BaseDaoImpl;
import com.jhkj.mosdc.framework.message.dao.JxrzEventDao;
import com.jhkj.mosdc.framework.message.po.TsEventMsg;
import com.jhkj.mosdc.framework.message.po.TsEventSendRange;
import com.jhkj.mosdc.framework.po.TbJxzzjg;
import com.jhkj.mosdc.framework.po.TbJzgxx;
import com.jhkj.mosdc.framework.util.DateUtils;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.jhkj.mosdc.permission.util.UserPermissionUtil;

public class JxrzEventDaoImpl extends BaseDaoImpl implements JxrzEventDao {

	@SuppressWarnings({ "rawtypes", "unused" })
	@Override
	public void addMessageForBzrEveryday() throws Exception {
		// TODO Auto-generated method stub
		String yesterday = DateUtils.getYesterday();
		String sql = "select bj.bzr_id,count(case when kq.late = 'true' then '1' else null end) late , "+
               " count(case when kq.truant = 'true' then '1' else null end) truant ,"+
               " count(case when kq.vacate = 'true' then '1' else null end) vacate ,"+
               " count(case when kq.leave = 'true' then '1' else null end) leave  "+
               " from tb_jw_jxhd_xskq kq inner join tb_jw_jxhd_jxrz rz on kq.jxrz_id = rz.id"+
               " inner join tb_xxzy_bjxxb bj on rz.bj_id = bj.id and rz.skrq = '"+yesterday+"'"+
               " group by bj.bzr_id";
		
		List<Map> list = this.queryListMapInLowerKeyBySql(sql);
		for(Map map : list){
			String late = map.get("late").toString();
			String truant = map.get("truant").toString();
			String vacate = map.get("vacate").toString();
			String leave = map.get("leave").toString();
			if(exceptZero(late,truant,vacate,leave))continue;
			Long bzrId = MapUtils.getLong(map, "bzr_id");
			TsEventMsg msg = new TsEventMsg();
			Long id = getId();
			msg.setId(id);
			msg.setTitle("班级考勤情况");
			msg.setContent(getStringMessage("您班级",yesterday,late, truant, vacate, leave));
			msg.setCreateTime(DateUtils.date2String(new Date()));
			msg.setEventType("考勤");
			msg.setEventLevel("1");
			this.save(msg);
			TsEventSendRange range = new TsEventSendRange();
			range.setId(getId());
			range.setRangeId(bzrId);
			range.setReadYet(false);
			range.setEventId(id);
			range.setRangeType("TEACHER_ID");
			this.save(range);
		}
	}
	public String getStringMessage(String desc,String date,String late,String truant,String vacate,String leave){
		StringBuilder msg = new StringBuilder(date+"号:");
		msg.append(desc).append("的学生");
		if(!late.equals("0")){
		   msg.append("迟到").append(late).append("节次;");
		}
		if(!truant.equals("0")){
		   msg.append("旷课").append(truant).append("节次;");
		}
		if(!vacate.equals("0")){
		   msg.append("请假").append(vacate).append("节次;");
		}
		if(!leave.equals("0")){
		   msg.append("早退").append(leave).append("节次;");
		}
		return msg.toString();
	}
	public boolean exceptZero(String... args){
		boolean flag = true;
		for(String s : args ){
			if(!"0".equals(s)){
			   flag = false;
			   return flag;
			}
		}
		return flag;
	}
	/**
	 * 为系部主任添加推送信息
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addMessageForXbzr() throws Exception{
		Session session = this.getSession();
		Transaction tranc = session.getTransaction();
		String yesterday = DateUtils.getYesterday();
		String xbSql = "select id,mc,qxm from tb_jxzzjg t where t.cclx= 'YX'";
		List<TbJxzzjg> zzjglist = (List<TbJxzzjg>) session.createSQLQuery(xbSql).addScalar("id", Hibernate.LONG).addScalar("mc").addScalar("qxm").setResultTransformer(Transformers.aliasToBean(TbJxzzjg.class)).list();
		String sql = "select  count(case when kq.late = 'true' then '1' else null end) late , "+
				" count(case when kq.truant = 'true' then '1' else null end) truant , "+
				" count(case when kq.vacate = 'true' then '1' else null end) vacate , "+
				" count(case when kq.leave = 'true' then '1' else null end) leave   "+
				" from tb_jw_jxhd_xskq kq inner join tb_jw_jxhd_jxrz rz on kq.jxrz_id = rz.id "+
				" inner join tb_xxzy_bjxxb bj on rz.bj_id = bj.id and rz.skrq = '"+yesterday+"' "+
				" inner join tb_jxzzjg zzjg on bj.fjd_id = zzjg.id  " +
				" and zzjg.qxm like '";
		String xbfzrSql = "select jzg.id,jzg.xm from ts_js js inner join tc_xxbzdmjg dm on js.jslx_id = dm.id and dm = '6' "+
              " inner join ts_user_js uj on js.id = uj.js_id  "+
              " inner join ts_user u on uj.user_id = u.id  "+
              " inner join tb_jzgxx jzg on jzg.id = u.zg_id and jzg.yx_id = '";
		for(TbJxzzjg jg : zzjglist){
			String qxm = jg.getQxm();
			String retsq = sql+qxm+"%'";
			List<Map> list = this.queryListMapInLowerKeyBySql(retsq);
			if(list.size() == 0)continue;
			Map map = list.get(0);
			String late = map.get("late").toString();
			String truant = map.get("truant").toString();
			String vacate = map.get("vacate").toString();
			String leave = map.get("leave").toString();
			if(exceptZero(late,truant,vacate,leave))continue;
			List<TbJzgxx> jzglist = session.createSQLQuery(xbfzrSql+jg.getId()+"'").addScalar("id",Hibernate.LONG).addScalar("xm").setResultTransformer(Transformers.aliasToBean(TbJzgxx.class)).list();
			TsEventMsg msg = new TsEventMsg();
			Long id = getId();
			msg.setId(id);
			msg.setTitle("系部考勤情况");
			msg.setContent(getStringMessage("您系部",yesterday,late, truant, vacate, leave));
			msg.setCreateTime(DateUtils.date2String(new Date()));
			msg.setEventType("考勤");
			msg.setEventLevel("1");
			this.save(msg);
			for(TbJzgxx jzg : jzglist){
				TsEventSendRange range = new TsEventSendRange();
				range.setId(getId());
				range.setRangeId(jzg.getId());
				range.setReadYet(false);
				range.setEventId(id);
				range.setRangeType("TEACHER_ID");
				this.save(range);
			}
		}
		session.close();
	}
	public void addMessageForJwczAndXz() throws Exception{
		Session session = this.getSession();
		Transaction tranc = session.getTransaction();
		String yesterday = DateUtils.getYesterday();
		String sql = "select  count(case when kq.late = 'true' then '1' else null end) late , "+
				" count(case when kq.truant = 'true' then '1' else null end) truant , "+
				" count(case when kq.vacate = 'true' then '1' else null end) vacate , "+
				" count(case when kq.leave = 'true' then '1' else null end) leave   "+
				" from tb_jw_jxhd_xskq kq inner join tb_jw_jxhd_jxrz rz on kq.jxrz_id = rz.id "+
				" inner join tb_xxzy_bjxxb bj on rz.bj_id = bj.id and rz.skrq = '"+yesterday+"' "+
				" inner join tb_jxzzjg zzjg on bj.fjd_id = zzjg.id  ";
		String jwczSql = " select jzg.id,jzg.xm from ts_js js inner join tc_xxbzdmjg dm on js.jslx_id = dm.id and dm = '4' "+
				" inner join ts_user_js uj on js.id = uj.js_id  "+
				" inner join ts_user u on uj.user_id = u.id  "+
				" inner join tb_jzgxx jzg on jzg.id = u.zg_id and u.grouppermiss!=0";
		List<Map> list = this.queryListMapInLowerKeyBySql(sql);
		Map map = list.get(0);
		String late = map.get("late").toString();
		String truant = map.get("truant").toString();
		String vacate = map.get("vacate").toString();
		String leave = map.get("leave").toString();
		
		//保存全校级考勤情况
		TsEventMsg msg = new TsEventMsg();
		Long id = getId();
		msg.setId(id);
		msg.setTitle("全校考勤情况");
		msg.setContent(getStringMessage("全校",yesterday,late, truant, vacate, leave));
		msg.setCreateTime(DateUtils.date2String(new Date()));
		msg.setEventType("考勤");
		msg.setEventLevel("1");
		this.save(msg);
		
		//教务处处长信息
		List<TbJzgxx> jzglist = session.createSQLQuery(jwczSql).addScalar("id",Hibernate.LONG).addScalar("xm").setResultTransformer(Transformers.aliasToBean(TbJzgxx.class)).list();
		if(!exceptZero(late,truant,vacate,leave)){
			for(TbJzgxx jzg : jzglist){
				TsEventSendRange range = new TsEventSendRange();
				range.setId(getId());
				range.setRangeId(jzg.getId());
				range.setReadYet(false);
				range.setEventId(id);
				range.setRangeType("TEACHER_ID");
				this.save(range);
			}
		}
		//校长信息
		session.close();
		
	}
	public Long getZgId() {
		UserInfo user = UserPermissionUtil.getUserInfo();
		Long zgId = user.getZgId();
		return zgId;
	}
}
