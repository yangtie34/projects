package com.jhkj.mosdc.framework.message.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.enums.LetterCodedLabeledEnum;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.message.dao.StationLettersDao;
import com.jhkj.mosdc.framework.message.po.TsLetter;
import com.jhkj.mosdc.framework.message.po.TsLetterAddressee;
import com.jhkj.mosdc.framework.message.po.TsLetterAttachment;
import com.jhkj.mosdc.framework.message.service.StationLettersService;
import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;
import com.jhkj.mosdc.framework.util.DateUtils;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.jhkj.mosdc.permission.util.UserPermissionUtil;

public class StationLettersServiceImpl extends BaseServiceImpl implements
		StationLettersService {

	private StationLettersDao dao;

	public StationLettersDao getDao() {
		return dao;
	}

	public void setDao(StationLettersDao dao) {
		this.dao = dao;
	}
	/**
	 * 查询信件
	 */
	public String queryLetters(String params){
		JSONObject json = JSONObject.fromObject(params);
		String type = json.getString("type");
		params = json.toString();
		json.remove("type");
		if(type.equals("Receive")){
			return this.queryReciveLetters(params);
		}else if(type.equals("Send")){
			return this.querySendLetters(params);
		}else if(type.equals("Draft")){
			return this.queryDraftLetters(params);
		}else if(type.equals("Garbage")){
			return this.queryGarbageLetters(params);
		}else{
			return "";
		}
	}
	/**
	 * 发件箱信息
	 * @param params
	 * @return
	 */
	public String queryReciveLetters(String params) {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		Integer start = json.getInt("start");
		Integer limit = json.getInt("limit");
		String zgId = getZgId();
		List<TsLetter> list = dao
				.queryReciveLetters(zgId, start, start + limit);
		int count = dao.queryReciveLettersCount(zgId, start, start + limit);
		Map map = new HashMap();
		map.put("count", count);
		map.put("data", list);
		map.put("success", true);
		return Struts2Utils.map2json(map);
	}
	/**
	 * 收件箱信息
	 * @param params
	 * @return
	 */
	public String querySendLetters(String params) {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		Integer start = json.getInt("start");
		Integer limit = json.getInt("limit");
		String zgId = getZgId();
		List<TsLetter> list = dao.querySendLetters(zgId, start, start + limit);
		int count = dao.querySendLettersCount(zgId, start, start + limit);
		Map map = new HashMap();
		map.put("count", count);
		map.put("data", list);
		map.put("success", true);
		return Struts2Utils.map2json(map);
	}
	/**
	 * 草稿箱信息
	 * @param params
	 * @return
	 */
	public String queryDraftLetters(String params) {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		Integer start = json.getInt("start");
		Integer limit = json.getInt("limit");
		String zgId = getZgId();
		List<TsLetter> list = dao.queryDraftLetters(zgId, start, start + limit);
		return Struts2Utils.list2json(list);
	}
	/**
	 * 垃圾箱信息
	 * @param params
	 * @return
	 */
	public String queryGarbageLetters(String params) {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		Integer start = json.getInt("start");
		Integer limit = json.getInt("limit");
		String zgId = getZgId();
		List<TsLetter> list = dao.queryGarbageLetters(zgId, start, start
				+ limit);
		int count = dao.queryGarbageLettersCount(zgId, start, start + limit);
		Map map = new HashMap();
		map.put("count", count);
		map.put("data", list);
		map.put("success", true);
		return Struts2Utils.map2json(map);
	}

	@Override
	public String saveLetter(String params) throws Exception {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		JSONObject letterJson = JSONObject.fromObject(json.get("letter"));
		JSONArray jsrJson = JSONArray.fromObject(json.get("jsr"));
		JSONArray attachJson = JSONArray.fromObject(json.get("attach"));// 附件JSON

		long zgId = Long.parseLong(getZgId());
		String title = letterJson.getString("title");
		String content = letterJson.getString("content");
//		String sendTime = letterJson.getString("sendTime");
		// Long replyLetterId =
		// json.containsKey("replyLetterId")?json.getLong("replyLetterId"):null;
		String cjsj = DateUtils.date2StringV2(new Date());
		String sendTime = cjsj;
		String cjr = getUsername();

		TsLetter letter = new TsLetter();
		letter.setJzgId(zgId);
		letter.setTitle(title);
		letter.setContent(content);
		letter.setSendTime(sendTime);
		letter.setReplyLetterId(null);
		letter.setCjr(cjr);
		letter.setCjsj(cjsj);
		
		letter.setSfky(true);
		letter.setIsReply(false);

		List<TsLetterAddressee> addressList = this.convertAddress(jsrJson);
		String sjrmc = this.convertSjr(jsrJson);
		letter.setSjrmc(sjrmc);

		dao.saveLetter(letter, addressList, new ArrayList<TsLetterAttachment>());

		return SysConstants.JSON_SUCCESS_TRUE;
	}

	private String convertSjr(JSONArray jsrJson) {
		// TODO Auto-generated method stub
		StringBuilder xms = new StringBuilder();;
		for(int i=0;i<jsrJson.size();i++){
			Object obj = jsrJson.get(i);
			JSONObject json = JSONObject.fromObject(obj);
			String xm = json.getString("jzgxm");
			xms.append(xm.split("-")[0]);
			if(i != jsrJson.size()-1) xms.append(",");
		}
		return xms.toString();
		
	}

	@Override
	public String updateLetter(String params) {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		JSONObject letterJson = JSONObject.fromObject(json.get("letter"));
		JSONArray jsrJson = JSONArray.fromObject(json.get("jsr"));
		JSONArray attachJson = JSONArray.fromObject(json.get("attach"));// 附件JSON

		long zgId = Long.parseLong(getZgId());
		String title = letterJson.getString("title");
		Long id = letterJson.getLong("id");
		String content = letterJson.getString("content");
		String sendTime = letterJson.getString("sendTime");
		// Long replyLetterId =
		// json.containsKey("replyLetterId")?json.getLong("replyLetterId"):null;
		String cjsj = DateUtils.date2StringV2(new Date());
		String cjr = getUsername();

		TsLetter letter = new TsLetter();
		letter.setId(id);
		letter.setJzgId(zgId);
		letter.setTitle(title);
		letter.setContent(content);
		letter.setSendTime(sendTime);
		letter.setReplyLetterId(null);
		letter.setCjr(cjr);
		letter.setCjr(cjr);
		letter.setSfky(true);
		letter.setIsReply(false);

		List<TsLetterAddressee> addressList = this.convertAddress(jsrJson);

		dao.updateLetter(letter, addressList,
				new ArrayList<TsLetterAttachment>());

		return SysConstants.JSON_SUCCESS_TRUE;
	}

	@Override
	public String deleteLetterLogic(String params) {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		JSONArray ids = json.getJSONArray("ids");
		dao.deleteLetterLogic(translateToLong(ids));
		return SysConstants.JSON_SUCCESS_TRUE;
	}
	/**
	 * 物理删除信件
	 */
	public String deleteLetterPhysical(String params) throws Exception{
		JSONObject json = JSONObject.fromObject(params);
		JSONArray ids = json.getJSONArray("ids");
		dao.deleteLetterPhysical(translateToLong(ids));
		return SysConstants.JSON_SUCCESS_TRUE;
	}
	public List<TsLetterAddressee> convertAddress(JSONArray json) {
		List<TsLetterAddressee> list = new ArrayList<TsLetterAddressee>();
		for (Object obj : json) {
			JSONObject map = JSONObject.fromObject(obj);
			TsLetterAddressee ta = new TsLetterAddressee();
			ta.setJzgId(Long.parseLong(map.getString("jzgId")));
			ta.setJzgxm(map.getString("jzgxm"));
			ta.setReadYet(false);
			ta.setSfky(true);
			list.add(ta);
		}
		return list;
	}
	public List<Long> translateToLong(List<String> list){
		List<Long> llist = new ArrayList<Long>();
		for(String s : list){
			llist.add(Long.parseLong(s));
		}
		return llist;
	}
	@Override
	public String queryJzg(String params) {
		// TODO Auto-generated method stub
		List<Map> list = dao.queryJzg();
		return Struts2Utils.list2json(list);
	}
	public String getZgId() {
		UserInfo user = UserPermissionUtil.getUserInfo();
		Long zgId = user.getZgId();
		if (zgId == null)
			return null;
		return zgId.toString();
	}

	public String getUsername() {
		UserInfo user = UserPermissionUtil.getUserInfo();
		return user.getUsername();
	}

	

}
