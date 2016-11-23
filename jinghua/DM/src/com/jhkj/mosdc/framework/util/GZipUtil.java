package com.jhkj.mosdc.framework.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.tools.zip.ZipEntry;

/**
 * 动态数据压缩工具
 * 
 * @author Administrator
 * 
 */
public class GZipUtil {

	/**
	 * 动态压缩字节流
	 * @param source
	 * @return
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 */
	public static byte[] gZip(String source) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();  
	    GZIPOutputStream gout = new GZIPOutputStream(out);  
	    gout.write(source.getBytes("UTF-8"));  
	    gout.close();  
	    return out.toByteArray();  
	}

}
