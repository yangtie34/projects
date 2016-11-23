package com.jhkj.mosdc.framework.service;

import java.util.List;
import java.util.Map;

import com.jhkj.mosdc.framework.po.TbTyShlcHj;


/**
 * 
* @ClassName: AuditProcessService 
* @Description: 通用简单的审核流程接口
* @author zhangyc
* @date 2013年12月12日 上午11:19:49 
*
 */

public interface AuditProcessService {
	
	/**
	 * 
	* @Title: getShztAndCzlxShowStr 
	* @Description: 获取当前信息审核状态和操作列的显示
	* @param @param id
	* @param @return    设定文件 
	* @return Map    返回类型 
	* @throws
	 */
	 public Map getShztAndCzlxShowStr(Long id,String lsmk) throws Throwable;
	 /**
	  * 获取当前审核状态和操作列显示
	  * @Title: getShztAndCzlxShowStr 
	  * @param @param id
	  * @param @param lcId
	  * @param @return
	  * @param @throws Throwable
	  * @return Map
	  * @throws
	  */
	 public Map getShztAndCzlxShowStr(Long id,Long lcId) throws Throwable;
	 /**
	  * 
	 * @Title: saveShInfo 
	 * @Description: 保存审核信息 
	 * @param     设定文件 
	 * @return void    返回类型 
	 * @throws
	  */
	 public void saveShInfo(List<Map> idlist,String lsmk,Long hjId,String shyj,Long shztId);
	 
	 /**
	  * 
	 * @Title: isOk 
	 * @Description: 判断审核信息记录是否 通过
	 * @param @param id
	 * @param @return    设定文件 
	 * @return Boolean    返回类型 
	 * @throws
	  */
	 public boolean isOk(Long id,String lsmk);
	 /**
	  * 判断审核信息记录是否 通过
	  * @Title: isOk 
	  * @param @param id
	  * @param @param lcId
	  * @param @return
	  * @param @throws Throwable
	  * @return boolean
	  * @throws
	  */
	 public boolean isOk(Long id,Long lcId) throws Throwable;
	 /**
	  * 获取流程模块流程第一个环节id
	  * @Title: getFirstHjId 
	  * @param @return
	  * @return Long
	  * @throws
	  */
	 public TbTyShlcHj getFirstHj(String lsmk);
	 /**
	  * 获取流程模块流程第一个环节id
	  * @Title: getFirstHj 
	  * @param @param lcId
	  * @param @return
	  * @param @throws Throwable
	  * @return TbTyShlcHj
	  * @throws
	  */
	 public TbTyShlcHj getFirstHj(Long lcId) throws Throwable;
	 
	 
	 /**
	  * 获取角色的环节
	  * @Title: getHjByRole 
	  * @param @param lsmk
	  * @param @return
	  * @return TbTyShlcHj
	  * @throws
	  */
	 public TbTyShlcHj getHjByRole(String lsmk) ;
	 /**
	  * 获取角色的环节
	  * @Title: getHjByRole 
	  * @param @param lcId
	  * @param @return
	  * @param @throws Throwable
	  * @return TbTyShlcHj
	  * @throws
	  */
	 public TbTyShlcHj getHjByRole(Long lcId) throws Throwable ;
	 /**
	  * 获取当前环节id
	  * @Title: getNowHj 
	  * @param @param id
	  * @param @return
	  * @return TbTyShlcHj
	  * @throws
	  */
	 public TbTyShlcHj getNowHj(Long id) throws Throwable;
	 /**
	  * 获取下个环节id
	  * @Title: getNextHj 
	  * @param @param id
	  * @param @return
	  * @return Long
	  * @throws
	  */
	 public TbTyShlcHj getNextHj(TbTyShlcHj dqhj) throws Throwable;
	 /**
	  * 获取上个环节id
	  * @Title: getUpHj 
	  * @param @param id
	  * @param @return
	  * @return Long
	  * @throws
	  */
	 public TbTyShlcHj getUpHj(TbTyShlcHj dqhj) throws Throwable;
	 /**
	  * 获取第一个环节id
	  * @Title: getFirstHjId 
	  * @param @return
	  * @return Long
	  * @throws
	  */
	 public Long getFirstHjId(String lsmk);
	 /**
	  * 获取第一个环节id
	  * @Title: getFirstHjId 
	  * @param @param lcId
	  * @param @return
	  * @param @throws Throwable
	  * @return Long
	  * @throws
	  */
	 public Long getFirstHjId(Long lcId) throws Throwable;
	 /**
	  * 获取最后一个环节Id
	  * @param lsmk
	  * @return
	  */
	 public Long getLastHjId(String lsmk);
	 /**
	  * 获取最后一个环节Id
	  * @Title: getLastHjId 
	  * @param @param lcId
	  * @param @return
	  * @param @throws Throwable
	  * @return Long
	  * @throws
	  */
	 public Long getLastHjId(Long lcId) throws Throwable;
	 /**
	  * 获取角色拥有的环节id
	  * @Title: getHjIdByRole 
	  * @param @param lsmk
	  * @param @return
	  * @return Long
	  * @throws
	  */
	 public Long getHjIdByRole(String lsmk);
	 /**
	  * 获取角色拥有的环节id
	  * @Title: getHjIdByRole 
	  * @param @param lcId
	  * @param @return
	  * @param @throws Throwable
	  * @return Long
	  * @throws
	  */
	 public Long getHjIdByRole(Long lcId) throws Throwable;
	 /**
	  * 获取下个环节
	  * @Title: getNextHjId 
	  * @param @param id
	  * @param @return
	  * @return Long
	  * @throws
	  */
	 public Long getNextHjId(Long id) throws Throwable;
	 /**
	  * 获取上个环节
	  * @Title: getUpHjId 
	  * @param @param id
	  * @param @return
	  * @return Long
	  * @throws
	  */
	 public Long getUpHjId(Long id) throws Throwable;
	 /**
	  * 获取审核历史记录
	  * @Title: getHistoryInfo 
	  * @param @param id
	  * @param @return
	  * @return List
	  * @throws
	  */
	 public List getHistoryInfo(Long id);
	 /**
	 * @throws Throwable 
	  * 获取隶属模块
	  * @Title: getLsmkById 
	  * @param @param id
	  * @param @return
	  * @return String
	  * @throws
	  */
	 public String getLsmkById(Long lcId,Long  id) throws Throwable;
	 /**
	  * 获取流程信息根据隶属模块
	  * @Title: getLcInfoByLsmk 
	  * @param @param lsmk
	  * @param @return
	  * @return List<Map>
	  * @throws
	  */
	 public List<Map> getLcInfoByLsmk(String lsmk);
	 /**
	 * @throws Throwable 
	  * 根据环节ID获取角色ID
	  * @Title: getRoleIdByHjId 
	  * @param @param hjId
	  * @param @return
	  * @return Long
	  * @throws
	  */
	 public Long getRoleIdByHjId(Long hjId) throws Throwable;
	 /**
	  * 获取流程信息根据流程Ids
	  * @Title: getLcInfoByLcIds 
	  * @param @param ids
	  * @param @return
	  * @return List<Map>
	  * @throws
	  */
	 public List<Map> getLcInfoByLcIds(String ids);
	 
	 
	
	 
	
	
	
	
}
