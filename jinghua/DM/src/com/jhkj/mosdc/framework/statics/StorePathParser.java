package com.jhkj.mosdc.framework.statics;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Properties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component("storePathParser")
public class StorePathParser implements  InitializingBean {

	protected String baseStorePath;
	
	protected String hostPaht;
	
	protected String codeImgName;
	
	protected String codeImgType;
	
	protected String stuPhotoName;
	
	protected String edu;

	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		URL filePath = this.getClass().getClassLoader().getResource("storePath.properties");
		Properties properties = new Properties();
		properties.load(new FileInputStream(new File(filePath.toURI().getPath())));
		baseStorePath = properties.getProperty("basePath"); 
		hostPaht = properties.getProperty("hostPath");
		codeImgName = properties.getProperty("codeImgName");
		codeImgType =properties.getProperty("codeImgType");
		stuPhotoName=properties.getProperty("stuPhotoName");
		edu = properties.getProperty("edu");
		
		checkIfExsitAndCreate(baseStorePath);
		Field[] fields = StorePathParser.class.getFields();
		for(Field f : fields){
			if(f.getType().equals(String.class)){
				checkIfExsitAndCreate(baseStorePath+f.get(this).toString());
			}
		}
	}
	public void checkIfExsitAndCreate(String path){
		System.out.println(path);
		File file = new File(path);
		if(!file.isFile() && !file.isDirectory()){
			file.mkdirs();
		}
	}
	
	/*public void storePathParser() throws Exception{
		afterPropertiesSet();
	}*/
	/**
	 * 学工-学籍-图片
	 */
	public String XG_XJ_IMG_Path = "xg/xj/img";
	public String XG_XJ_MB_Path = "xg/xj/mb";
	/**
	 * 学工-学生胸卡
	 */
	public String XG_XSXK_PATH = "xsxkPrint/";
	
	public String WELCOME_CODE_SAVE_Path = "welcome/";
	

	/********************************** 程序日志 ********************************/
	/**
	 * 学工-学号-日志
	 */
	public String LOG_XG_XJ_XH_PATH = "log/xg/xh/";
	
	
	
	/**
	 * @throws Exception *****************************************************/
	/*public static void main(String[] args) throws Exception{
//		String path = "E:/store/";
//		File file = new File(path);
//		file.mkdirs();s
		StorePathParser path = new StorePathParser();
		path.afterPropertiesSet();
		Field[] fields = StorePathParser.class.getFields();
		System.out.println(path.baseStorePath);
		System.out.println(fields.length);
		for(Field f : fields){
			if(f.getType().equals(String.class)){
				System.out.println(f.get(path));
			}
		}
	}*/
}
