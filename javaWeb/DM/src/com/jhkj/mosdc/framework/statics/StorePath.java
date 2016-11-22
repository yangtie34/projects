package com.jhkj.mosdc.framework.statics;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 基础存储路径,相对于Base路径、配置在storePath.properties文件中
 * @author Administrator
 *
 */
@Component
public class StorePath implements InitializingBean{
	private static String basePath;//基本路径
	public static String hostPath;//访问路径
	public static String codeImgName;//定义生成条形码或二维码命名和生成内容
	public static String codeImgType;//定义生成图片的类型-one条形码,two-二维码
	private static StorePathParser parser;
	public static String stuPhotoName;//照片命名
	public static String edu;//学校标示

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		setBasePath(parser.baseStorePath);
		hostPath = parser.hostPaht;
		codeImgName=parser.codeImgName;
		codeImgType=parser.codeImgType;
		stuPhotoName=parser.stuPhotoName;
		edu = parser.edu;
		
	}
	@Resource(name="storePathParser")
	public void setParser(StorePathParser parser){
		this.parser = parser;
	}
	///////////////////////////////////////自定义存储路径路径////////////////////////////////////////////////////////////
	/**************************************************************************************************************/
	
	/**
	 * 获取学籍-学工-图片路径
	 * @return
	 */
	public static String getXgxjImgPath() {
		String path = parser.baseStorePath+parser.XG_XJ_IMG_Path;
		return path;
	}
	/**
	 * 获取学籍-学工-图片文件夹{不包含 E:\store\，只有xg/xj/img}
	 * @return
	 */
	public static String getXgXjImgDir(){
		return parser.XG_XJ_IMG_Path;
	}
	
	public static String getXgxjMbPath(){
		return  parser.baseStorePath+parser.XG_XJ_MB_Path;
	}
	//迎新存二维码路径
	public static String getWelcomeSaveCodePath(){
		return  parser.baseStorePath+parser.WELCOME_CODE_SAVE_Path;
	}
	//迎新访问路径
	public static String getWelcomeCodePath(){
		return parser.hostPaht+"images/welcome/code/";
	}
	
	/**
	 * 获取学生胸卡硬盘存放路径
	 * @return
	 */
	public static String getXgXsxkSavePath(){
		return parser.baseStorePath+parser.XG_XSXK_PATH;
	}
	/**
	 * 获取学生胸卡硬盘存放文件夹
	 * @return
	 */
	public static String getXgXsxkSaveDir(){
		return parser.XG_XSXK_PATH;
	}
	/**
	 * 获取学生胸卡url路径
	 * @return
	 */
	public static String getXgXsxkUrlPath(){
		return parser.hostPaht;
	}
	
	/********************************** 程序日志 ********************************/
	/**
	 * 获取编学号日志路径
	 * @return
	 */
	public static String getLogXgBxhPath(){
		return parser.baseStorePath+parser.LOG_XG_XJ_XH_PATH;
	}
	
	
	public static void main(String[] args) throws Exception {
		StorePath storeP = new StorePath();
		storeP.afterPropertiesSet();
	     System.out.println(storeP.getXgxjImgPath());
	}
	public static String getBasePath() {
		return basePath;
	}
	public static void setBasePath(String basePath) {
		StorePath.basePath = basePath;
	}

}
