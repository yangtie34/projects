package com.jhkj.mosdc.framework.util;

/**
 * @Comments: 自定义异常
 * Company: 河南精华科技有限公司
 * Created by gaodongjie(gaodongjie@126.com) 
 * @DATE:2012-8-28
 * @TIME: 下午12:03:38
 */
public class MOSDCException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * 操作提示信息
	 */
	private String info = "";

	/**
	 * 信息的详情，如：Exception的堆栈信息或其他自定义详细信息;
	 */
	private String detailInfo;
	
	/**
	 * 信息显示方式,默认为1<br/>
	 * 0：以ext的MessageBox方式显示 ；1：以ext的window窗口方式显示 ；9：自定义处理显示(不期望走统一处理程序的情况)
	 */
	private int showType = 1;

	/**
	 * 信息分类代码，默认为3<br/>
	 * 0：提示；1：警告；2：错误；3：异常 ；4：成功
	 */
	private int infoType = 3;
	

	public MOSDCException() {
		super();
	}

	/**
	 * 
	 * @param info 操作提示信息
	 */
	public MOSDCException(String info) {
		super(info);
		this.info = info;
	}

	/**
	 * @param info 操作提示信息
	 * @param cause
	 */
	public MOSDCException(String info, Throwable cause) {
		super(info, cause);
		this.info = info;
	}

	/**
	 * @param info 提示信息
	 * @param showType 信息显示方式，引用常量：MessageInfoJson.SHOWTYPE_WINDOW \ MessageInfoJson.SHOWTYPE_NONE \ MessageInfoJson.SHOWTYPE_USERDEFINED
	 * @param infoType 信息分类，引用常量：MessageInfoJson.INFOTYPE_INFO \ MessageInfoJson.INFOTYPE_WARN \  MessageInfoJson.INFOTYPE_ERROR \ MessageInfoJson.INFOTYPE_EXCEPTION \ MessageInfoJson.INFOTYPE_SUCCESS 
	 */
	public MOSDCException(String info,int showType, int infoType) {
		super(info);
		this.info = info;
		this.showType = showType;
		this.infoType = infoType;	
	}

	/**
	 * @param info	提示信息
	 * @param detailInfo	详细信息
	 * @param showType 信息显示方式，引用常量：MessageInfoJson.SHOWTYPE_WINDOW \ MessageInfoJson.SHOWTYPE_NONE \ MessageInfoJson.SHOWTYPE_USERDEFINED
	 * @param infoType 信息分类，引用常量：MessageInfoJson.INFOTYPE_INFO \ MessageInfoJson.INFOTYPE_WARN \  MessageInfoJson.INFOTYPE_ERROR \ MessageInfoJson.INFOTYPE_EXCEPTION \ MessageInfoJson.INFOTYPE_SUCCESS
	 */
	public MOSDCException(String info, String detailInfo, int showType, int infoType) {
		super(info);
		this.info = info;
		this.detailInfo = detailInfo;
		this.showType = showType;
		this.infoType = infoType;	
	}

	
	/**
	 * @param info 提示信息
	 * @param detailInfo	详细信息
	 * @param showType 信息显示方式，引用常量：MessageInfoJson.SHOWTYPE_WINDOW \ MessageInfoJson.SHOWTYPE_NONE \ MessageInfoJson.SHOWTYPE_USERDEFINED
	 * @param infoType 信息分类，引用常量：MessageInfoJson.INFOTYPE_INFO \ MessageInfoJson.INFOTYPE_WARN \  MessageInfoJson.INFOTYPE_ERROR \ MessageInfoJson.INFOTYPE_EXCEPTION \ MessageInfoJson.INFOTYPE_SUCCESS
	 * @param cause 异常
	 */
	public MOSDCException(String info, String detailInfo, int showType, int infoType, Throwable cause) {
		super(info, cause);
		this.info = info;
		this.detailInfo = detailInfo;
		this.showType = showType;
		this.infoType = infoType;		
	}

	/**
	 * @param cause
	 */
	public MOSDCException(Throwable cause) {
		super(cause);
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
	 * @return the detialInfo
	 */
	public String getDetailInfo() {
		return detailInfo;
	}

	/**
	 * @param detialInfo the detialInfo to set
	 */
	public void setDetailInfo(String detailInfo) {
		this.detailInfo = detailInfo;
	}

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
}
