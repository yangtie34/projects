package com.jhkj.mosdc.framework.util.mail;

public class MailTest {
	
    public static void main(String[] args){  
    	
        String smtp = "smtp.163.com";  
        String from = "vbxnt521_edu@163.com";  
        String to = "lantingde@163.com";  
        String copyto = "492274248@qq.com";  
        String subject = "邮件主题-测试";  
        String content = "邮件内容-测试";  
        String username="vbxnt521_edu@163.com";  
        String password="*****";  
        String filename = "F:\\test.txt";  
        for(int i =0 ;i<4;i++){
        	Mail.sendAndCc(smtp, from, to, copyto, subject, content, username, password, filename); 
        }
        
//        Mail.send(smtp, from, copyto, subject, content, username, password, filename);
    }  
}
