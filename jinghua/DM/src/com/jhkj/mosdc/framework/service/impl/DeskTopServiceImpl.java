package com.jhkj.mosdc.framework.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.dao.DeskTopDao;
import com.jhkj.mosdc.framework.dto.DesktopShortCut;
import com.jhkj.mosdc.framework.service.DeskTopService;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.jhkj.mosdc.permission.util.UserPermissionUtil;
/***
 * 桌面处理实现类
 * @Comments: 
 * Company: 河南精华科技有限公司
 * Created by wangyongtai(490091105@.com)
 * @DATE:2012-11-29
 * @TIME: 上午10:32:00
 */
public class DeskTopServiceImpl implements DeskTopService {

	private DeskTopDao dao;
	/***
	 * 根据用户信息查询其对应的桌面快捷方式信息
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String queryShortCutsByUser(String params) throws Exception {
		Long userid = getUserId();//获取用户id
		List<DesktopShortCut> list = dao.queryShortCutsByUserId(userid);
		return "{success:true,\"data\":" + Struts2Utils.list2json(list) + "}";
	}
	/***
	 * 删除快捷方式
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String deleteShortCutById(String params) throws Exception {
		JSONObject json = JSONObject.fromObject(params);
		Long id = json.getLong("id");
		boolean b = dao.deleteShortCutById(id);
		if(b)return SysConstants.JSON_SUCCESS_TRUE;
		return SysConstants.JSON_SUCCESS_FALSE;
	}
	
	/***
	 * 添加用户的快捷方式
	 * @throws Exception 
	 */
	public String saveShortCutByUser(String params) throws Exception {
		JSONObject json = JSONObject.fromObject(params);
		ObjectMapper objectMapper = new ObjectMapper();
		DesktopShortCut shortcut = objectMapper.readValue(json.toString(),DesktopShortCut.class);
		shortcut.setUserId(getUserId());//设置用户id
		dao.saveShortCutByUser(shortcut);
		return SysConstants.JSON_SUCCESS_TRUE;
	}
	/***
	 * 保存系统树结构菜单图片样式
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@SuppressWarnings("unchecked")
	public String updateSystemIcons(String params) throws Exception{
		JSONObject json = JSONObject.fromObject(params);
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String,String> map = objectMapper.readValue(json.toString(), Map.class);
		boolean b = dao.updateSystemIcons(map);
		if(b)return SysConstants.JSON_SUCCESS_TRUE;
		else return SysConstants.JSON_SUCCESS_FALSE;
	}
	/***
	 * 查询系统菜单图标样式
	 * @param params
	 * @return
	 */
	public String querySystemIcons(String params){
		Map<String,String> map = null;
		map = dao.querySystemIcons();
		return "{success:true,\"data\":" + Struts2Utils.map2json(map) + "}"; 
	}
	/***
	 * 查询用户桌面墙纸
	 * @throws Exception 
	 */
	public String queryUserWallpaper(String params) throws Exception{
		Map<String,String> map = dao.queryUserWallpaper(getUserId());
		return "{success:true,\"data\":" + Struts2Utils.map2json(map) + "}"; 
	}
	/***
	 * 修改用户桌面墙纸
	 * @throws Exception 
	 */
	public String updateUserWallpaper(String params) throws Exception{
		JSONObject json = JSONObject.fromObject(params);
		boolean b = dao.updateUserWallpaper(getUserId(),json);
		if(b)return SysConstants.JSON_SUCCESS_TRUE;
		else return SysConstants.JSON_SUCCESS_FALSE;
	}
	/***
	 * 查询用户系统风格
	 */
	public Map queryUserSystemPage(String params) throws Exception{
		Map<String,String> map = dao.queryUserSystemPage(getUserId());
		return map;  
	}
	/***
	 * 修改用户系统风格
	 */
	public String updateUserSystemPage(String params) throws Exception{
		JSONObject json = JSONObject.fromObject(params);
		boolean b = dao.updateUserSystemPage(json,getUserId());
		if(b)return SysConstants.JSON_SUCCESS_TRUE;
		else return SysConstants.JSON_SUCCESS_FALSE;
	}
	/***
	 * 保存上传用户图片
	 * @throws Exception 
	 */
	public boolean saveImgs(List<String> saveNames,List<String> imgsUrl) throws Exception{
		Long userId = getUserId();
		boolean b = dao.saveImgs(saveNames,imgsUrl,userId);
		return b;
	}
	/****
	 * 查询桌面图片
	 * @throws Exception 
	 */
	public String queryImgs(String params) throws Exception{
		Long userId = getUserId();
		List<Map> list = dao.queryImgs(userId);
		return "{success:true,\"data\":" + Struts2Utils.list2json(list) + "}"; 
	}
	/****
	 * 查询功能菜单头信息
	 * @throws Exception 
	 */
	public String queryMenuHeader(String params) throws Exception {
		Long userId = getUserId();
		List<Map> list = dao.queryMenuHeader();
		return "{success:true,\"data\":" + Struts2Utils.list2json(list) + "}"; 
	}
	/****
	 * 保存或者修改菜单页面顶部信息
	 */
	public String updateMenuHeader(String params) throws Exception {
		JSONObject json = JSONObject.fromObject(params);
		ObjectMapper objectMapper = new ObjectMapper();
		List<Object>list  = objectMapper.readValue(json.get("data").toString(), ArrayList.class);
		List<Map<String,Object>> mlist = new ArrayList<Map<String,Object>>();
		for(Object obj : list){
			mlist.add(objectMapper.readValue(JSONObject.fromObject(obj).toString(),Map.class));
		}
		boolean b = dao.updateMenuHeader(mlist);
		if(b) return SysConstants.JSON_SUCCESS_TRUE;
		else  return SysConstants.JSON_SUCCESS_FALSE;
	}
	/***
	 * 获取天气数据
	 */
	public String getWeatherData(String params) throws Exception{
		JSONObject json = JSONObject.fromObject(params);
		String cityCode = json.getString("cityCode");
		String url = "http://m.weather.com.cn/data/"+cityCode+".html";
		URL conn = new URL(url);
		InputStream input = conn.openStream();
		byte[] buffer = new byte[1024];
		StringBuilder builder = new StringBuilder();
		int len = 0;
		while((len = input.read(buffer))>0){
			String s = new String(Arrays.copyOfRange(buffer, 0, len),"utf-8");
			builder.append(s);
		}
		input.close();
		return "{success:true,\"data\":" + builder.toString() + "}"; 
	}
	/**
	 * 获取当前用户Id
	 * @return
	 * @throws Exception
	 */
	private Long getUserId() throws Exception{
		UserInfo user = new UserInfo();
		user = UserPermissionUtil.getUserInfo();
		return user.getId();
	}
	public DeskTopDao getDao() {
		return dao;
	}
	public void setDao(DeskTopDao dao) {
		this.dao = dao;
	}
}
