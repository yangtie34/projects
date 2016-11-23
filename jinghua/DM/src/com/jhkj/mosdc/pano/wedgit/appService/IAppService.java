package com.jhkj.mosdc.pano.wedgit.appService;

public interface IAppService {
	/**
	 * 根据id获取json格式的数据。
	 * @Title: getDataById 
	 * @param  params
	 * @return String
	 * @throws
	 */
	public String getDataById(String params);
	/**
	 * 根据id获取它的弹出框的数据。
	 * @Title: getMiniDataById
	 * @param params 
	 * @return String
	 * @throws
	 */
	public String getMiniDataById(String params);
}
