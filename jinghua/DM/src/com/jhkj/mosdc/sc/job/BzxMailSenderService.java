package com.jhkj.mosdc.sc.job;
/**
 * 不在校学生名单邮件发送接口
 * @author Administrator
 *
 */
public interface BzxMailSenderService {

	public void sendMail();
	
	public boolean sendMail(String yxid);
	
	public boolean sendMail4WG(String yxid,String rq);
	public boolean sendMail4WG();
	
	public boolean sendMail4WZS();
	public boolean sendMail4WZS(String yxid,String rq);
}
