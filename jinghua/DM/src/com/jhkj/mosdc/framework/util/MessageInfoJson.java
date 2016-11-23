package com.jhkj.mosdc.framework.util;

/**
 * 功能:处理操作提示信息，用于返回操作结果信息
 * @author Created by gaodongjie 
 * @DATE:2010-1-12
 * @TIME: 下午03:17:09
 */

public class MessageInfoJson {
	
	/**
	 * 信息分类代码<br/>
	 * 0：提示；1：警告；2：错误；3：异常 ；4：成功
	 */
	public static final int INFOTYPE_INFO=0;
	/**
	 * 1：警告
	 */
	public static final int INFOTYPE_WARN=1;
	/**
	 * 2：错误
	 */
	public static final int INFOTYPE_ERROR=2;
	/**
	 * 3：异常 
	 */
	public static final int INFOTYPE_EXCEPTION=3;
	/**
	 * 4：成功
	 */
	public static final int INFOTYPE_SUCCESS=4;
	
	/**
	 * 信息显示方式<br/>
	 * 0：以ext的MessageBox方式显示 ；<br>
	 * 1：以ext的window窗口方式显示 ；<br>
	 * 8: 不弹出任何提示窗口<br>
	 * 9：自定义处理显示(不期望走统一处理程序的情况)
	 */
	public static final int SHOWTYPE_MESSAGEBOX=0;
	
	/**
	 * 以ext的window窗口方式显示 
	 */
	public static final int SHOWTYPE_WINDOW=1;
	/**
	 * 不弹出任何提示窗口
	 */
	public static final int SHOWTYPE_NONE=8;
	/**
	 * 自定义处理显示(不期望走统一处理程序的情况)
	 */
	public static final int SHOWTYPE_USERDEFINED=9;
	

	/**
	 * 是否操作成功
	 */
	private boolean success=false;
	
	/**
	 * 操作提示信息
	 */
	private String info="";
	
	/**
	 * js格式的对象字符串
	 */
	private String jsObj="''";
	
	/**
	 * 信息分类代码<br/>
	 * 0：提示；1：警告；2：错误；3：异常 ；4：成功
	 */
	private int infoType=3;
	
	/**
	 * 信息的详情，如：Exception的堆栈信息或其他自定义详细信息;
	 */
	private String detialInfo;
	
	/**
	 * 是否显示详细信息
	 */
	private boolean showDetial=false;
	
	/**
	 * 提示窗口的title信息
	 */
	private String title="提示";
	
	/**
	 * 信息显示方式<br/>
	 * 0：以ext的MessageBox方式显示 ；1：以ext的window窗口方式显示 ；8: 不弹出任何提示窗口;9：自定义处理显示(不期望走统一处理程序的情况)
	 */
	private int showType=1;
	
	/**
	 * @return the showType
	 */
	public int getShowType() {
		return showType;
	}

	/**
	 * @param showType the showType to set
	 */
	public void setShowType(int showType) {
		this.showType = showType;
	}

	/**
	 * 功能描述：无参构造函数
	 */
	public MessageInfoJson(){
	}
	

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * @param info the info to set
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	
	/**
	 * @return the jsObj
	 */
	public String getJsObj() {
		return jsObj;
	}
	/**
	 * @param jsObj the jsObj to set
	 */
	public void setJsObj(String jsObj) {
		this.jsObj = jsObj;
	}
	/**
	 * @return the infoType
	 */
	public int getInfoType() {
		return infoType;
	}
	/**
	 * @param infoType the infoType to set
	 */
	public void setInfoType(int infoType) {
		this.infoType = infoType;
	}
	/**
	 * @return the detialInfo
	 */
	public String getDetialInfo() {
		return detialInfo;
	}
	/**
	 * @param detialInfo the detialInfo to set
	 */
	public void setDetialInfo(String detialInfo) {
		this.detialInfo = detialInfo;
	}
	/**
	 * @return the showDetial
	 */
	public boolean isShowDetial() {
		return showDetial;
	}
	/**
	 * @param showDetial the showDetial to set
	 */
	public void setShowDetial(boolean showDetial) {
		this.showDetial = showDetial;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}