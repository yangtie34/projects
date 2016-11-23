package com.jhkj.mosdc.framework.message.service;
/**
 * 站内信接口
 * @author Administrator
 *
 */
public interface StationLettersService {
	public String queryLetters(String params);
//	/**
//	 * 查询收到的站内信件
//	 * @param params
//	 * @return
//	 */
//	public String queryReciveLetters(String params);
//	/**
//	 * 查询发出的站内信件
//	 * @param params
//	 * @return
//	 */
//	public String querySendLetters(String params);
//	/**
//	 * 查询站内信件草稿
//	 * @param params
//	 * @return
//	 */
//	public String queryDraftLetters(String params);
	/**
	 * 查询垃圾箱信件
	 * @param params
	 * @return
	 */
	public String queryJzg(String params);
	/**
	 * 保存信件
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String saveLetter(String params) throws Exception;
	/**
	 * 修改信件
	 */
	public String updateLetter(String params) throws Exception;
	/**
	 * 逻辑删除信件
	 */
	public String deleteLetterLogic(String params) throws Exception;
	/**
	 * 物理删除信件
	 */
	public String deleteLetterPhysical(String params) throws Exception;
}
