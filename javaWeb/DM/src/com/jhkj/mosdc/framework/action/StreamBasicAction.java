package com.jhkj.mosdc.framework.action;

import java.io.InputStream;   
import java.io.UnsupportedEncodingException;  

/**
 * @Comments: 字符转换Action
 * Company: 河南精华科技有限公司
 * Created by gaodongjie(gaodongjie@126.com) 
 * @DATE:2012-8-21
 * @TIME: 下午3:09:59
 */
public class StreamBasicAction extends BaseAction {
	 private static final long serialVersionUID = 1L;   
     
	    protected InputStream inputStream;   
	    protected String contentType;   
	    protected String contentLength;   
	    protected String contentDisposition;   
	       
	    public InputStream getInputStream() {   
	        return inputStream;   
	    }   
	    public void setInputStream(InputStream inputStream) {   
	        this.inputStream = inputStream;   
	    }   
	    public String getContentType() {   
	        return contentType;   
	    }   
	    public void setContentType(String contentType) {   
	        this.contentType = contentType;   
	    }   
	    public String getContentLength() {   
	        return contentLength;   
	    }   
	    public void setContentLength(String contentLength) {   
	        this.contentLength = contentLength;   
	    }   
	       
	    public String getContentDisposition() {   
	        return contentDisposition;   
	    }   
	    public void setContentDisposition(String contentDisposition) {   
	        this.contentDisposition = contentDisposition;   
	    }   
	       
	    public String changeCoding(String str){   
	        try {   
	            return new String(str.getBytes("GBK"),"ISO-8859-1");   
	        } catch (UnsupportedEncodingException e) {   
	            return str;   
	        }   
	    }   

}
