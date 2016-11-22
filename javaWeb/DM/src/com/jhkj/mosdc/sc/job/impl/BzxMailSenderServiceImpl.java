package com.jhkj.mosdc.sc.job.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.jhkj.mosdc.framework.util.mail.Mail;
import com.jhkj.mosdc.newoutput.util.DateUtils;
import com.jhkj.mosdc.sc.job.BzxMailSenderService;
import com.jhkj.mosdc.sc.job.JobStatusState;
import com.jhkj.mosdc.sc.service.impl.ScServiceImpl;

public class BzxMailSenderServiceImpl extends ScServiceImpl implements
		BzxMailSenderService {
	private static String smtp ="mail.zknu.edu.cn";
	private static String from ="zknudata@zknu.edu.cn";
	private static String username="zknudata@zknu.edu.cn";  
	private static  String password="8178555";
	private static String copyto = "504045694@qq.com";
	@Override
	public void sendMail() {
		if(JobStatusState.EXPORT_OVER_YX==1){
			String sql ="SELECT ZZJG.MC AS YX,ZZJG.ID AS ZZJGID,GLY.ZGH,FJ.FJML,JZG.DZXX FROM TB_YKT_BZX_MAIL_FJLB FJ "+
								" LEFT JOIN TB_YXGLY GLY ON GLY.YX_ID = FJ.JXZZJGID "+
								" LEFT JOIN TB_JXZZJG ZZJG ON ZZJG.ID = FJ.JXZZJGID "+
								" LEFT JOIN TB_JZGXX JZG ON JZG.ZGH = GLY.ZGH ";
			List<Map> result = baseDao.querySqlList(sql);
	        for(int i =0 ;i<result.size();i++){
	        	Map temp = result.get(i);
	        	if(temp.get("DZXX")==null || temp.get("DZXX").toString().equals("")){
					continue;
				}
		        String to = temp.get("DZXX").toString();   
		        System.out.println(to);
		        String subject = "近3日疑似不在校学生名单";  
		        String content = "近3日疑似不在校学生名单，详见附件！";  
		        String filename = temp.get("FJML").toString();   
//	        	Mail.send(smtp, from, to, subject, content, username, password, filename);
		        boolean flag = Mail.sendAndCc(smtp, from, to, copyto, subject, content, username, password, filename);
		        String update ="UPDATE TB_YKT_BZX_MAIL_FJLB SET SFZT=1";
		        if(!flag){
		        	update ="UPDATE TB_YKT_BZX_MAIL_FJLB SET SFZT=-1";
		        }
		        baseDao.update(update);
	        }
		}
		
	}
	@Override
	public boolean sendMail(String yxid) {
		String sql = "SELECT ZZJG.MC AS YX,ZZJG.ID AS ZZJGID,GLY.ZGH,FJ.FJML,JZG.DZXX FROM TB_YKT_BZX_MAIL_FJLB FJ "
				+ " LEFT JOIN TB_YXGLY GLY ON GLY.YX_ID = FJ.JXZZJGID "
				+ " LEFT JOIN TB_JXZZJG ZZJG ON ZZJG.ID = FJ.JXZZJGID "
				+ " LEFT JOIN TB_JZGXX JZG ON JZG.ZGH = GLY.ZGH WHERE ZZJG.ID = "+yxid;
		boolean flag = false;
		List<Map> result = baseDao.querySqlList(sql);
		for (int i = 0; i < result.size(); i++) {
			Map temp = result.get(i);
			if(temp.get("DZXX")==null || temp.get("DZXX").toString().equals("")){
				continue;
			}
			String to = temp.get("DZXX").toString();
			System.out.println(to);
			String subject = "近3日疑似不在校学生名单";
			String content = "近3日疑似不在校学生名单，详见附件！";
			String filename = temp.get("FJML").toString();
			// Mail.send(smtp, from, to, subject, content, username, password,
			// filename);
			flag = Mail.sendAndCc(smtp, from, to, copyto, subject,
					content, username, password, filename);
			String update = "UPDATE TB_YKT_BZX_MAIL_FJLB SET SFZT=1 WHERE JXZZJGID = "+yxid;
			if (!flag) {
				update = "UPDATE TB_YKT_BZX_MAIL_FJLB SET SFZT=-1 WHERE JXZZJGID = "+yxid;
			}
			baseDao.update(update);
		}
		return flag;
	}
	
	@Override
	public boolean sendMail4WG(String yxid,String rq) {
		String sql = "SELECT ZZJG.MC AS YX,ZZJG.ID AS ZZJGID,GLY.ZGH,FJ.FJML,JZG.DZXX FROM TB_YKT_TEMP_WG_MAIL FJ "
				+ " LEFT JOIN TB_YXGLY GLY ON GLY.YX_ID = FJ.JXZZJGID "
				+ " LEFT JOIN TB_JXZZJG ZZJG ON ZZJG.ID = FJ.JXZZJGID "
				+ " LEFT JOIN TB_JZGXX JZG ON JZG.ZGH = GLY.ZGH WHERE ZZJG.ID = "+yxid+" AND FJ.DYRQ='"+rq+"'";
		boolean flag = false;
		List<Map> result = baseDao.querySqlList(sql);
		if(result.size()==0){
			return false;
		}
		for (int i = 0; i < result.size(); i++) {
			Map temp = result.get(i);
			if(temp.get("DZXX")==null || temp.get("DZXX").toString().equals("")){
				continue;
			}
			String to = temp.get("DZXX").toString();
			System.out.println(to);
			String subject = "昨日晚寝晚归学生名单";
			String content = "昨日晚寝晚归学生名单，详见附件！";
			String filename = temp.get("FJML").toString();
			// Mail.send(smtp, from, to, subject, content, username, password,
			// filename);
			flag = Mail.sendAndCc(smtp, from, to, copyto, subject,
					content, username, password, filename);
			String update = "UPDATE TB_YKT_TEMP_WG_MAIL SET SFZT=1 WHERE JXZZJGID = "+yxid+" AND DYRQ='"+rq+"'";
			if (!flag) {
				update = "UPDATE TB_YKT_TEMP_WG_MAIL SET SFZT=-1 WHERE JXZZJGID = "+yxid+" AND DYRQ='"+rq+"'";
			}
			baseDao.update(update);
		}
		return flag;
	}
	@Override
	public boolean sendMail4WG() {
		// 获取昨天的日期
		Date date = DateUtils.getNextDay(new Date(),-1);
		String yesterday = DateUtils.getDateFormat(date, "yyyy-MM-dd");
		String sql = "SELECT ZZJG.MC AS YX,ZZJG.ID AS ZZJGID,GLY.ZGH,FJ.FJML,JZG.DZXX FROM tb_ykt_temp_wg_mail FJ "
				+ " LEFT JOIN TB_YXGLY GLY ON GLY.YX_ID = FJ.JXZZJGID "
				+ " LEFT JOIN TB_JXZZJG ZZJG ON ZZJG.ID = FJ.JXZZJGID "
				+ " LEFT JOIN TB_JZGXX JZG ON JZG.ZGH = GLY.ZGH where fj.dyrq ='"+yesterday+"' ";
		List<Map> result = baseDao.querySqlList(sql);
		for (int i = 0; i < result.size(); i++) {
			Map temp = result.get(i);
			if(temp.get("DZXX")==null || temp.get("DZXX").toString().equals("")){
				continue;
			}
			String zzjgid = MapUtils.getString(temp, "ZZJGID");
			String to = temp.get("DZXX").toString();
			String subject = "晚寝晚归学生名单";
			String content = "晚寝晚归学生名单，详见附件！";
			String filename = temp.get("FJML").toString();
			boolean flag = Mail.sendAndCc(smtp, from, to, copyto, subject,
					content, username, password, filename);
			String update = "UPDATE tb_ykt_temp_wg_mail SET SFZT=1 WHERE JXZZJGID="+zzjgid +" AND DYRQ='"+yesterday+"'";
			if (!flag) {
				update = "UPDATE tb_ykt_temp_wg_mail SET SFZT=-1 WHERE JXZZJGID="+zzjgid +" AND DYRQ='"+yesterday+"'";
			}
			baseDao.update(update);
		}
		return true;
	}
	
	@Override
	public boolean sendMail4WZS() {
		// 获取昨天的日期
		Date date = DateUtils.getNextDay(new Date(),-1);
		String yesterday = DateUtils.getDateFormat(date, "yyyy-MM-dd");
		String sql = "SELECT ZZJG.MC AS YX,ZZJG.ID AS ZZJGID,GLY.ZGH,FJ.FJML,JZG.DZXX FROM tb_ykt_temp_wzs_mail FJ "
				+ " LEFT JOIN TB_YXGLY GLY ON GLY.YX_ID = FJ.JXZZJGID "
				+ " LEFT JOIN TB_JXZZJG ZZJG ON ZZJG.ID = FJ.JXZZJGID "
				+ " LEFT JOIN TB_JZGXX JZG ON JZG.ZGH = GLY.ZGH where fj.dyrq ='"+yesterday+"' ";
		List<Map> result = baseDao.querySqlList(sql);
		for (int i = 0; i < result.size(); i++) {
			Map temp = result.get(i);
			String zzjgid = MapUtils.getString(temp, "ZZJGID");
			if(temp.get("DZXX")==null || temp.get("DZXX").toString().equals("")){
				continue;
			}
			String to = temp.get("DZXX").toString();
			String subject = "昨日疑似未住宿学生名单";
			String content = "昨日疑似未住宿学生名单，详见附件！";
			String filename = temp.get("FJML").toString();
			boolean flag = Mail.sendAndCc(smtp, from, to, copyto, subject,
					content, username, password, filename);
			String update = "UPDATE tb_ykt_temp_wzs_mail SET SFZT=1 WHERE JXZZJGID="+zzjgid +" AND DYRQ='"+yesterday+"'";
			if (!flag) {
				update = "UPDATE tb_ykt_temp_wzs_mail SET SFZT=-1 WHERE JXZZJGID="+zzjgid +" AND DYRQ='"+yesterday+"'";
			}
			baseDao.update(update);
		}
		return true;
	}
	@Override
	public boolean sendMail4WZS(String yxid, String rq) {
		String sql = "SELECT ZZJG.MC AS YX,ZZJG.ID AS ZZJGID,GLY.ZGH,FJ.FJML,JZG.DZXX FROM TB_YKT_TEMP_WZS_MAIL FJ "
				+ " LEFT JOIN TB_YXGLY GLY ON GLY.YX_ID = FJ.JXZZJGID "
				+ " LEFT JOIN TB_JXZZJG ZZJG ON ZZJG.ID = FJ.JXZZJGID "
				+ " LEFT JOIN TB_JZGXX JZG ON JZG.ZGH = GLY.ZGH WHERE ZZJG.ID = "+yxid+" AND FJ.DYRQ='"+rq+"'";
		boolean flag = false;
		List<Map> result = baseDao.querySqlList(sql);
		if(result.size()==0){
			return false;
		}
		for (int i = 0; i < result.size(); i++) {
			Map temp = result.get(i);
			if(temp.get("DZXX")==null || temp.get("DZXX").toString().equals("")){
				continue;
			}
			String to = temp.get("DZXX").toString();
			String subject = "昨日疑似未住宿学生名单";
			String content = "昨日疑似未住宿学生名单，详见附件！";
			String filename = temp.get("FJML").toString();
			// Mail.send(smtp, from, to, subject, content, username, password,
			// filename);
			flag = Mail.sendAndCc(smtp, from, to, copyto, subject,
					content, username, password, filename);
			String update = "UPDATE TB_YKT_TEMP_WZS_MAIL SET SFZT=1 WHERE JXZZJGID = "+yxid+" AND DYRQ='"+rq+"'";
			if (!flag) {
				update = "UPDATE TB_YKT_TEMP_WZS_MAIL SET SFZT=-1 WHERE JXZZJGID = "+yxid+" AND DYRQ='"+rq+"'";
			}
			baseDao.update(update);
		}
		return flag;
	}
}
