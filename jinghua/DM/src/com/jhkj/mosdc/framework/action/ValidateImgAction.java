package com.jhkj.mosdc.framework.action;

import java.io.ByteArrayInputStream;   
import java.io.ByteArrayOutputStream;   
import java.io.IOException;   
import java.util.Map;   


import org.apache.struts2.interceptor.SessionAware;

import com.jhkj.mosdc.framework.service.ValidateImageService;

public class ValidateImgAction extends StreamBasicAction implements
		SessionAware {

	private static final long serialVersionUID = 6894525175454169331L;   
	   
    private static final String Default_ValidateCode = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";   
       
    private Map session;   
       
    private int width;   
    private int height;   
    private int fontSize;   
    private int codeLength;   
       
    private ValidateImageService validateImageService;   
       
        
    public ValidateImgAction(){   
        this.contentType = "image/jpeg";       
        width = 80;   
        height = 20;   
        fontSize = 16;   
        codeLength = 4;   
    }   
         
    public void setSession(Map session) {   
        this.session = session;   
    }    
       
       
    public void setWidth(int width) {   
        this.width = width;   
    }   
   
    public void setHeight(int height) {   
        this.height = height;   
    }   
   
   
    public void setFontSize(int fontSize) {   
        this.fontSize = fontSize;   
    }   
   
    public void setCodeLength(int codeLength) {   
        this.codeLength = codeLength;   
    }   
   
    public void setValidateImageService(ValidateImageService validateImageService) {   
        this.validateImageService = validateImageService;   
    }   
   
    public String execute() {     
        try {
			session.put("validateCodeByLogin", createInputStream(ValidateImageService.Disturb_Type_Simple));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
        return SUCCESS;   
    }   
       
       
    private String createInputStream(int disturbType) throws IOException{   
        ByteArrayOutputStream bos =new ByteArrayOutputStream();   
        String validateCode = null;   
        validateCode = validateImageService.createValidateCode(disturbType,fontSize, bos, width, height, getText("System.validateCode",Default_ValidateCode), codeLength);   
        inputStream = new ByteArrayInputStream(bos.toByteArray());   
        bos.close();   
        return validateCode;   
    }                            


}
