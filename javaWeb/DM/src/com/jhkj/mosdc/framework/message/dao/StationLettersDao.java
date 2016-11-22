package com.jhkj.mosdc.framework.message.dao;

import java.util.List;
import java.util.Map;

import com.jhkj.mosdc.framework.message.po.TsLetter;
import com.jhkj.mosdc.framework.message.po.TsLetterAddressee;
import com.jhkj.mosdc.framework.message.po.TsLetterAttachment;

/**
 * 站内信dao接口
 * @author Administrator
 *
 */
public interface StationLettersDao {

	/**
	 * 查询接受的信件
	 * @param zgId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<TsLetter> queryReciveLetters(String zgId, Integer start, Integer end);
	/**
	 * 查询接受的信件数量
	 * @param zgId
	 * @param start
	 * @param end
	 * @return
	 */
	public int queryReciveLettersCount(String zgId, Integer start, Integer end);
	/**
	 * 查询发送的信件
	 * @param zgId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<TsLetter> querySendLetters(String zgId, Integer start, Integer end);
	/**
	 * 查询发送的信件数量
	 * @param zgId
	 * @param start
	 * @param end
	 * @return
	 */
	public int querySendLettersCount(String zgId, Integer start, Integer end);
	/**
	 * 查询草稿的信件
	 * @param zgId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<TsLetter> queryDraftLetters(String zgId, Integer start, Integer end);
	/**
	 * 查询垃圾箱的信件
	 * @param zgId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<TsLetter> queryGarbageLetters(String zgId, Integer start, Integer end);
	/**
	 * 查询发送的信件数量
	 * @param zgId
	 * @param start
	 * @param end
	 * @return
	 */
	public int queryGarbageLettersCount(String zgId, Integer start, Integer end);
	
	/**
	 * 保存信件
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public Boolean saveLetter(TsLetter letter,List<TsLetterAddressee> addressList,List<TsLetterAttachment> attachList) throws Exception;
	/**
	 * 修改信件
	 */
	public Boolean updateLetter(TsLetter letter,List<TsLetterAddressee> addressList,List<TsLetterAttachment> attachList);
	/**
	 * 删除信件
	 */
	public Boolean deleteLetterLogic(List ids);
	/**
	 * 物理删除信件
	 */
	public Boolean deleteLetterPhysical(List ids);
	/**
	 *  查询教职工
	 */
	public List<Map> queryJzg();
} 
