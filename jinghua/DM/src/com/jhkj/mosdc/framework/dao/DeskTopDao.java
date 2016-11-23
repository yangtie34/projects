package com.jhkj.mosdc.framework.dao;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.dto.DesktopShortCut;

/**
 * 桌面处理实现Dao接口
 * @Comments: 
 * Company: 河南精华科技有限公司
 * Created by wangyongtai(490091105@.com)
 * @DATE:2012-11-29
 * @TIME: 上午10:32:47
 */
public interface DeskTopDao {

	/***
	 * 根据用户名查询其对应的快捷方式
	 * @param userid
	 */
	public List<DesktopShortCut> queryShortCutsByUserId(Long userid);
	/***
	 * 根据快捷方式id删除快捷方式
	 * @param id
	 * @return
	 */
	public boolean deleteShortCutById(Long id);
	/***
	 * 添加用户的快捷方式
	 * @throws Exception 
	 */
	public void saveShortCutByUser(DesktopShortCut shortcut) throws Exception;
	/***
	 * 修改系统图标样式
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public boolean updateSystemIcons(Map<String, String> map) throws Exception;
	/***
	 * 查询系统菜单图标样式
	 * @param params
	 * @return
	 */
	public Map<String, String> querySystemIcons();
	/***
	 * 查询用户桌面墙纸
	 * @param userId 用户id
	 */
	public Map<String, String> queryUserWallpaper(Long userId);
	/***
	 * 修改用户桌面墙纸
	 * @param userId 用户id
	 * @param wallpaper
	 * @throws Exception 
	 */
	public boolean updateUserWallpaper(Long userId, JSONObject json) throws Exception;
	/***
	 * 查询用户系统风格
	 * @param userId 用户id
	 */
	public Map<String, String> queryUserSystemPage(Long userId);
	/***
	 * 修改用户系统风格
	 * @param login_page 用户登录界面
	 * @param userId 用户id
	 * @throws Exception 
	 */
	public boolean updateUserSystemPage(JSONObject json,Long userId) throws Exception;
	/***
	 * 保存上传用户图片
	 * @throws Exception 
	 */
	public boolean saveImgs(List<String> saveNames, List<String> imgsUrl,
			Long userId);
	/****
	 * 查询桌面图片
	 */
	public List<Map> queryImgs(Long userId);
	/****
	 * 查询功能菜单头信息
	 * @throws Exception 
	 */
	public List<Map> queryMenuHeader();
	/****
	 * 保存或者修改菜单页面顶部信息
	 * @throws Exception 
	 */
	public boolean updateMenuHeader(List<Map<String,Object>> list) throws Exception;

}
