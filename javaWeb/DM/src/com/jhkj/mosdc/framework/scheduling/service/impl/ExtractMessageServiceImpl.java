package com.jhkj.mosdc.framework.scheduling.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.jhkj.mosdc.framework.scheduling.po.Message;
import com.jhkj.mosdc.framework.scheduling.service.ExtractMessageService;
import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.jhkj.mosdc.permission.util.UserPermissionUtil;

public class ExtractMessageServiceImpl extends BaseServiceImpl implements ExtractMessageService {
	private String types = "jzg,role";
	private String TYPE_JZG_ID = "TEACHER_ID";

	@Override
	public List<Message> queryMessageByJzg() {
		// TODO Auto-generated method stub
		UserInfo user = UserPermissionUtil.getUserInfo();
		Long zgId = user.getZgId();
		
		String sql = "select msg.id,msg.title,msg.content from ts_event_send_range range " +
				" inner join ts_event_msg msg on range.event_id=msg.id and range.read_yet = 0 and range.RANGE_TYPE=''{0}'' and range.range_id={1}";
		String formatSql = MessageFormat.format(sql, TYPE_JZG_ID,zgId==null?"''":zgId.toString());
		
		List<Map> list = this.queryListMapInLowerKeyBySql(formatSql);
		List<Message> messageList = new ArrayList<Message>();
		for(Map map : list){
			Message msg = new Message();
			msg.setId(MapUtils.getLong(map, "id"));
			msg.setTitle(MapUtils.getString(map, "title"));
			msg.setContent(MapUtils.getString(map, "content"));
			messageList.add(msg);
		}
		return messageList;
		
	}
	public List<Message> queryMessageInit(){
		List<Message> list = new ArrayList<Message>();
		Message message = new Message();
		message.setId(1000l);
		message.setTitle("第一条测试信息");
		message.setContent("第一次内容");
//		list.add(message);
		return list;
	}	

}
