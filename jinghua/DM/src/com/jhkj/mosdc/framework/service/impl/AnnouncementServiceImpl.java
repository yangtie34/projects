package com.jhkj.mosdc.framework.service.impl;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.dto.Announcement;
import com.jhkj.mosdc.framework.service.AnnouncementService;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.permiss.util.JSONUtil;
public class AnnouncementServiceImpl extends BaseServiceImpl implements AnnouncementService {

	@Override
	public String queryNewAnnouncement(String params) {
		// TODO Auto-generated method stub
		String sql = "select * from  (select t.*,rownum rn from ts_announcement t where t.sfky = 1 order by t.fbsj) T where rn=1";
		return null;
	}

	@Override
	public String queryLaterAnnouncement(String params) {
		String sql = "select * from  (select t.*,rownum rn from ts_announcement t where t.sfky = 1 order by t.fbsj desc) T  where T.rn<=5";
		List<Map> ret = this.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.list2json(ret);
	}
	
	@Override
	public String getAnnouncement(String params){
		JSONObject json = JSONObject.fromObject(params);
		Long id = JSONUtil.getLong(json, "id");
		Announcement at = new Announcement();
		at = (Announcement) this.getBaseDao().get(Announcement.class, id);
		return Struts2Utils.bean2json(at);
	}
}
