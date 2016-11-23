package com.jhkj.mosdc.framework.message.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;


import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.message.dao.SuggestDao;
import com.jhkj.mosdc.framework.message.po.TsMsgSuggest;
import com.jhkj.mosdc.framework.message.service.SuggestService;
import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;
import com.jhkj.mosdc.framework.util.DateUtils;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.jhkj.mosdc.permission.util.UserPermissionUtil;

public class SuggestServiceImpl  extends BaseServiceImpl implements SuggestService{
	private SuggestDao suggestDao;

	@Override
	public String addSuggest(String params) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		TsMsgSuggest suggest = (TsMsgSuggest) JSONObject.toBean(json, TsMsgSuggest.class);
		
		suggest.setCreaterId(getUserId());
		suggest.setCreateDate(DateUtils.date2StringV2(new Date()));
		suggest.setCreaterName(getUserName());
		suggest.setSfky(true);
		suggest.setIsReply(false);
		
		suggestDao.addSuggest(suggest);
		return SysConstants.JSON_SUCCESS_TRUE;
	}

	@Override
	public String updateSuggest(String params) {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		TsMsgSuggest suggest = (TsMsgSuggest) JSONObject.toBean(json, TsMsgSuggest.class);
		
		suggest.setReplyDate(DateUtils.date2StringV2(new Date()));
		suggest.setIsReply(true);
		
		suggestDao.updateSuggest(suggest);
		return SysConstants.JSON_SUCCESS_TRUE;
	}

	@Override
	public String deleteSuggest(String params) {
		// TODO Auto-generated method stub
		String ids = JSONObject.fromObject(params).getString("ids");
		suggestDao.deleteSuggest(ids);
		return SysConstants.JSON_SUCCESS_TRUE;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String queryMineSuggest(String params) throws Exception {
		// TODO Auto-generated method stub
		String[] columnArray= "id, title, content, create_date, creater_name, creater_id, is_reply, reply_content, reply_date, sfky, sfmy".split(",");
		String[] aliasArray = "id, title, content, createDate, createrName, createrId, isReply, replyContent, replyDate, sfky, sfmy".split(",");
		
		String sql = generateAliasSql(columnArray, aliasArray, " (select * from ts_msg_suggest where creater_id="+getUserId()+")");
		String result = this.queryTableDataBySqlWithAlias(sql, params); 
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String queryAllSuggest(String params) throws Exception {
		String[] columnArray= "id, title, content, create_date, creater_name, creater_id, is_reply, reply_content, reply_date, sfky, sfmy".split(",");
		String[] aliasArray = "id, title, content, createDate, createrName, createrId, isReply, replyContent, replyDate, sfky, sfmy".split(",");
		
		String sql = generateAliasSql(columnArray, aliasArray, "ts_msg_suggest");
		String result = this.queryTableDataBySqlWithAlias(sql, params); 
		return result;
	}
	public String generateAliasSql(String []array,String[] alias,String table){
		StringBuilder sql = new StringBuilder("select ");
		for(int i=0;i<array.length;i++){
			sql.append(array[i].trim()).append(" as \"").append(alias[i].trim()).append("\"");
			if(i != array.length-1){
			   sql.append(",");
			}
		}
		sql.append(" from ").append(table);
		return sql.toString();
	}
	public Long getUserId(){
		UserInfo user = UserPermissionUtil.getUserInfo();
		return user.getId();
	}
	public String getUserName(){
		UserInfo user = UserPermissionUtil.getUserInfo();
		return user.getUsername();
	}

	public void setSuggestDao(SuggestDao suggestDao) {
		this.suggestDao = suggestDao;
	}
	
}
