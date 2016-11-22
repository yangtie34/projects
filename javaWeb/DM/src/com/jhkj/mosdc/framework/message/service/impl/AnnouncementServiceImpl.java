package com.jhkj.mosdc.framework.message.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;



import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import com.jhkj.mosdc.framework.message.dao.AnnouncementDao;
import com.jhkj.mosdc.framework.message.po.TsAnnouncement;
import com.jhkj.mosdc.framework.message.service.AnnouncementService;
import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;
import com.jhkj.mosdc.framework.util.DateUtils;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.permiss.util.UserPermiss;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.jhkj.mosdc.permission.util.UserPermissionUtil;

public class AnnouncementServiceImpl extends BaseServiceImpl implements AnnouncementService {
	private AnnouncementDao dao;
	
	public void setDao(AnnouncementDao dao) {
		this.dao = dao;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String queryRecentAnnouncement(String params) {
		String zzjgId = getBmId();
		List<TsAnnouncement> list = new ArrayList<TsAnnouncement>();
		if(!"0".equals(zzjgId)){
			list = dao.queryRencentAnnouncement(zzjgId.toString());
		}
		return Struts2Utils.list2json(list);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String queryAnnouncement(String params) {
		// TODO Auto-generated method stub
		Long zgId = UserPermiss.getUser().getCurrentId();
		JSONObject json = JSONObject.fromObject(params);
		String start = json.getString("start");
		String limit = json.getString("limit");
		String end = new Integer(Integer.parseInt(start)+Integer.parseInt(limit)).toString();
		List<Map> list = dao.queryMineAnnouncement(zgId, start, end);
		return Struts2Utils.list2json(list);
	}

	@Override
	public String addAnnouncement(String params) throws Exception {
		// TODO Auto-generated method stub
		UserInfo user = UserPermissionUtil.getUserInfo();
		JSONObject json = JSONObject.fromObject(params);
		String title = json.getString("title");
		String content = json.getString("content");
		String sendTime = json.getString("send_time");
		String createTime = DateUtils.date2StringV2(new Date());
		Long jzgId = user.getZgId();
		String jzgXm = user.getUsername();
		Long yxId = user.getYxId();
		
		TsAnnouncement anment = new TsAnnouncement();
		anment.setTitle(title);
		anment.setContent(content);
		anment.setSendTime(createTime);
		anment.setCreateTime(createTime);
		anment.setJzgId(jzgId);
		anment.setJzgXm(jzgXm);
		anment.setZzjgId(yxId);
		anment.setReadTimes(0);
		dao.addAnnouncement(anment);
		return SysConstants.JSON_SUCCESS_TRUE;
	}

	@Override
	public String removeAnnouncement(String params) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		JSONArray ids = json.getJSONArray("ids");
		String id = ids.getString(0);
		dao.removeAnnouncement(id);
		return SysConstants.JSON_SUCCESS_TRUE;
	}
	/**
	 * 修改公告
	 */
	public String updateAnnouncement(String params) throws Exception{
		JSONObject json = JSONObject.fromObject(params);
		String content = json.getString("content");
		String sendTime = json.getString("send_time");
		String title = json.getString("title");
		String id = json.getString("id");
		
		TsAnnouncement anment = new TsAnnouncement();
		anment.setId(Long.parseLong(id));
		anment.setTitle(title);
		anment.setContent(content);
		anment.setSendTime(sendTime);
		
		dao.updateAnnouncement(anment);
		return SysConstants.JSON_SUCCESS_TRUE;
	}
	/**
	 * 获取组织机构ID
	 * @return
	 */
	public String getZzjgId(){
		UserInfo user = UserPermissionUtil.getUserInfo();
		Long xyId = user.getYxId();
		if(xyId == null)return "";
		return xyId.toString();
	}
	public String getBmId(){
		UserInfo user = UserPermissionUtil.getUserInfo();
		Long bmId = user.getBmId();
		if(bmId == null)return "";
		return bmId.toString();
	}

}
