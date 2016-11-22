package com.jhkj.mosdc.framework.message.dao;

import java.util.List;
import java.util.Map;

import com.jhkj.mosdc.framework.message.po.TsAnnouncement;

public interface AnnouncementDao {
	/**
	 * 查询最近的公告（最近10条）
	 * @param params
	 * @return
	 */
	public List<TsAnnouncement> queryRencentAnnouncement(String zzjgId);
	/**
	 * 查询我发出的公告
	 * @param zgId
	 * @return
	 */
	public List<Map> queryMineAnnouncement(Long zgId,String start,String end);
	/**
	 * 添加公告
	 * @throws Exception 
	 */
	public boolean addAnnouncement(TsAnnouncement anment) throws Exception;
	/**
	 * 移除公告
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean removeAnnouncement(String ids) throws Exception;
	/**
	 * 更新公告
	 */
	public boolean updateAnnouncement(TsAnnouncement anment) throws Exception;
}
