package cn.gilight.dmm.xg.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import cn.gilight.dmm.business.util.Constant;

/**
 * 发送邮件
 * @author lijun
 *
 */
public class MailUtils { 

	private MimeMessage mimeMsg; //MIME邮件对象 
	private Session session; //邮件会话对象 
	private Properties props; //系统属性 
	private boolean needAuth = false; //smtp是否需要认证 
	//smtp认证用户名和密码 
	private String username; 
	private String password; 
	private Multipart mp; //Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象 
	
	private static final String SMTP = Constant.MAIL_SMTP; //"SMTP服务器";
	private static final String MAIL_USERNAME = Constant.MAIL_USERNAME;// "发信人";
	private static final String MAIL_PASSWORK = Constant.MAIL_PASSWORK;// "发信人密码";
	
	/**
	 * Constructor
	 * @param smtp 邮件发送服务器
	 */
	public MailUtils(String smtp){ 
		setSmtpHost(smtp); 
		createMimeMessage(); 
	} 

	/**
	 * 设置邮件发送服务器
	 * @param hostName String 
	 */
	public void setSmtpHost(String hostName) { 
		System.out.println("设置系统属性：mail.smtp.host = "+hostName); 
		if(props == null)
			props = System.getProperties(); //获得系统属性对象 	
		props.put("mail.smtp.host",hostName); //设置SMTP主机 
	} 


	/**
	 * 创建MIME邮件对象  
	 * @return
	 */
	public boolean createMimeMessage() 
	{ 
		try { 
			System.out.println("准备获取邮件会话对象！"); 
			session = Session.getDefaultInstance(props,null); //获得邮件会话对象 
		} 
		catch(Exception e){ 
			System.err.println("获取邮件会话对象时发生错误！"+e); 
			return false; 
		} 
	
		System.out.println("准备创建MIME邮件对象！"); 
		try { 
			mimeMsg = new MimeMessage(session); //创建MIME邮件对象 
			mp = new MimeMultipart(); 
		
			return true; 
		} catch(Exception e){ 
			System.err.println("创建MIME邮件对象失败！"+e); 
			return false; 
		} 
	} 	
	
	/**
	 * 设置SMTP是否需要验证
	 * @param need
	 */
	public void setNeedAuth(boolean need) { 
		System.out.println("设置smtp身份认证：mail.smtp.auth = "+need); 
		if(props == null) props = System.getProperties(); 
		if(need){ 
			props.put("mail.smtp.auth","true"); 
		}else{ 
			props.put("mail.smtp.auth","false"); 
		} 
	} 

	/**
	 * 设置用户名和密码
	 * @param name
	 * @param pass
	 */
	public void setNamePass(String name,String pass) { 
		username = name; 
		password = pass; 
	} 

	/**
	 * 设置邮件主题
	 * @param mailSubject
	 * @return
	 */
	public boolean setSubject(String mailSubject) { 
		System.out.println("设置邮件主题！");
		if(mailSubject==null) return true; // 主题为空
		try{ 
			mimeMsg.setSubject(mailSubject); 
			return true; 
		} 
		catch(Exception e) { 
			System.err.println("设置邮件主题发生错误！"); 
			return false; 
		} 
	}
	
	/** 
	 * 设置邮件正文
	 * @param mailBody String 
	 */ 
	public boolean setBody(String mailBody) {
		if(mailBody == null) return true; // 正文为空
		try{ 
			BodyPart bp = new MimeBodyPart(); 
			bp.setContent(""+mailBody,"text/html;charset=GBK"); 
			mp.addBodyPart(bp); 
			return true; 
		} catch(Exception e){ 
			System.err.println("设置邮件正文时发生错误！"+e); 
			return false; 
		} 
	} 
	/** 
	 * 添加附件
	 * @param filename String 
	 * @throws UnsupportedEncodingException 
	 */ 
	@SuppressWarnings("restriction")
	public boolean addFileAffix(String filename) throws UnsupportedEncodingException { 
	
		System.out.println("增加邮件附件："+filename); 
		if(filename==null) return true; // 没有附件
		System.out.println("增加邮件附件："+new String(filename.getBytes("utf-8"),"utf-8")); 
		try{ 
			BodyPart bp = new MimeBodyPart(); 
			FileDataSource fileds = new FileDataSource(new String(filename.getBytes("utf-8"),"utf-8")); 
			bp.setDataHandler(new DataHandler(fileds)); 
			bp.setFileName("=?GBK?B?"+new sun.misc.BASE64Encoder().encode(fileds.getName().getBytes("GBK"))+"?="); 
			mp.addBodyPart(bp); 
			return true; 
		} catch(Exception e){ 
			System.err.println("增加邮件附件："+filename+"发生错误！"+e); 
			return false; 
		} 
	} 
	
	/** 
	 * 设置发信人
	 * @param from String 
	 */ 
	public boolean setFrom(String from) { 
		System.out.println("设置发信人！"); 
		if(from == null) return false; 
		try{ 
			mimeMsg.setFrom(new InternetAddress(from)); //设置发信人 
			return true; 
		} catch(Exception e) { 
			return false; 
		} 
	} 
	/** 
	 * 设置收信人
	 * @param to String 
	 */ 
	public boolean setTo(String to){ 
		if(to == null) return false; 
		try{ 
			mimeMsg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to)); 
			return true; 
		} catch(Exception e) { 
			return false; 
		} 	
	} 
	
	/** 
	 * 设置抄送人
	 * @param copyto String  
	 */ 
	public boolean setCopyTo(String copyto) {
		if(copyto == null) return true; // 没有抄送人 
		try{
			mimeMsg.setRecipients(Message.RecipientType.CC,(Address[])InternetAddress.parse(copyto)); 
			return true; 
		} 
		catch(Exception e) 
		{ return false; } 
	} 
	
	/** 
	 * 发送邮件
	 */ 
	public boolean sendOut() 
	{ 
		try{ 
			mimeMsg.setContent(mp); 
			mimeMsg.saveChanges(); 
			System.out.println("正在发送邮件...."); 
			
			Session mailSession = Session.getInstance(props,null); 
			Transport transport = mailSession.getTransport("smtp"); 
			transport.connect((String)props.get("mail.smtp.host"),username,password); 
			transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
			// 判断是否存在抄送
			if(mimeMsg.getRecipients(Message.RecipientType.CC) != null) 
				transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.CC)); 
	         // 设置信件头的发送日期  
	        mimeMsg.setSentDate(new Date()); 
//			transport.send(mimeMsg);
			
			System.out.println("发送邮件成功！"); 
			transport.close(); 
			
			return true; 
		} catch(Exception e) { 
			System.err.println("邮件发送失败！"+e); 
			return false; 
		} 
	} 

	/**
	 * 调用sendOut方法完成邮件发送
	 * @param smtp
	 * @param from
	 * @param to
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @return boolean
	 */
	public static boolean send(String smtp,String from,String to,String subject,String content,String username,String password) {
		MailUtils theMail = new MailUtils(smtp);
		theMail.setNeedAuth(true); //需要验证
		
		if(!theMail.setSubject(subject)) return false;
		if(!theMail.setBody(content)) return false;
		if(!theMail.setTo(to)) return false;
		if(!theMail.setFrom(from)) return false;
		theMail.setNamePass(username,password);
		
		if(!theMail.sendOut()) return false;
		return true;
	}
	
	/**
	 * 调用sendOut方法完成邮件发送,带抄送
	 * @param smtp
	 * @param from
	 * @param to
	 * @param copyto
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @return boolean
	 */
	public static boolean sendAndCc(String smtp,String from,String to,String copyto,String subject,String content,String username,String password) {
		MailUtils theMail = new MailUtils(smtp);
		theMail.setNeedAuth(true); //需要验证
		
		if(!theMail.setSubject(subject)) return false;
		if(!theMail.setBody(content)) return false;
		if(!theMail.setTo(to)) return false;
		if(!theMail.setCopyTo(copyto)) return false;
		if(!theMail.setFrom(from)) return false;
		theMail.setNamePass(username,password);
		
		if(!theMail.sendOut()) return false;
		return true;
	}
	
	/**
	 * 调用sendOut方法完成邮件发送,带附件
	 * @param smtp
	 * @param from
	 * @param to
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @param filename 附件路径
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static boolean send(String smtp,String from,String to,String subject,String content,String username,String password,String filename) throws UnsupportedEncodingException {
		MailUtils theMail = new MailUtils(smtp);
		theMail.setNeedAuth(true); //需要验证
		
		if(!theMail.setSubject(subject)) return false;
		if(!theMail.setBody(content)) return false;
		if(!theMail.addFileAffix(filename)) return false; 
		if(!theMail.setTo(to)) return false;
		if(!theMail.setFrom(from)) return false;
		theMail.setNamePass(username,password);
		
		if(!theMail.sendOut()) return false;
		return true;
	}
	
	/**
	 * 调用sendOut方法完成邮件发送,带附件和抄送
	 * @param smtp
	 * @param from
	 * @param to
	 * @param copyto
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @param filename
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static boolean sendAndCc(String smtp,String from,String to,String copyto,String subject,String content,String username,String password,String filename) throws UnsupportedEncodingException {
		MailUtils theMail = new MailUtils(smtp);
		theMail.setNeedAuth(true); //需要验证
		
		if(!theMail.setSubject(subject)) return false;
		if(!theMail.setBody(content)) return false;
		if(!theMail.addFileAffix(filename)) return false; 
		if(!theMail.setTo(to)) return false;
		if(!theMail.setCopyTo(copyto)) return false;
		if(!theMail.setFrom(from)) return false;
		theMail.setNamePass(username, password);
		if(!theMail.sendOut()) return false;
		return true;
	}
	public static boolean send(String filename,String receiveMail) throws Exception{
		String smtp ="smtp.163.com"; //"SMTP服务器";
		String from ="18339919808@163.com";// "发信人";
		String to = receiveMail;//"1782840861@qq.com";	//收信人
		String copyto ="18339919808@163.com";// "抄送人";
		String subject ="郑州轻工业学院"; //"邮件主题";
		String content = "未缴费学生详细信息";//"邮件内容";
		String username="18339919808@163.com";//"用户名";
		String password="lijun5618";//"密码";
//	 	String filename ="E:\\测试.xls";// "附件路径，如：F:\\笔记<a>\\struts2</a>与mvc.txt";
		return MailUtils.sendAndCc(smtp, from, to, copyto, subject, content, username, password, filename);
	}

	/**
	 * 发送邮件
	 * @param receiveMail 收件人
	 * @param filepath 附件路径  eg: E:\\测试.xls
	 * @param title 邮件主题
	 * @return
	 * @throws Exception boolean
	 */
	public static boolean send(String receiveMail, String filepath, String title) throws Exception{
		String smtp = SMTP; //"SMTP服务器";
		String from = MAIL_USERNAME; // "发信人";
		String username = MAIL_USERNAME; //"用户名";
		String password = MAIL_PASSWORK; //"密码";
		String to = receiveMail; //收信人
		String copyto = null; //"抄送人";
		String subject = title; //"邮件主题";
		String content = null; //"邮件内容";
		return MailUtils.sendAndCc(smtp, from, to, copyto, subject, content, username, password, filepath);
	}
	
	/**
	 * 测试邮件发送
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException{
		String smtp = SMTP; //"SMTP服务器";
		String from = MAIL_USERNAME; // "发信人";
		String to = "1782840861@qq.com";	//收信人
		String copyto ="18339919808@163.com";// "抄送人";
		String subject ="郑州轻工业学院"; //"邮件主题";
		String content = "未缴费学生详细信息";//"邮件内容";
		String username="18339919808@163.com";//"用户名";
		String password="lijun5618";//"密码";
	 	String filename ="d:\\测试.jpg";// "附件路径，如：F:\\笔记<a>\\struts2</a>与mvc.txt";
		MailUtils.sendAndCc(smtp, from, to, copyto, subject, content, username, password, filename);
	}	
} 

