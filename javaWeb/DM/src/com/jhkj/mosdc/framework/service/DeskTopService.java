package com.jhkj.mosdc.framework.service;

import java.util.List;
import java.util.Map;

import com.jhkj.mosdc.framework.dto.DesktopShortCut;

/***
 * 桌面处理service接口
 * @Comments: 
 * Company: 河南精华科技有限公司
 * Created by wangyongtai(490091105@.com)
 * @DATE:2012-11-29
 * @TIME: 上午10:31:31
 */
public interface DeskTopService {

	/***
	 * 根据用户信息查询其对应的桌面快捷方式信息
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String queryShortCutsByUser(String params) throws Exception;
	
	/***
	 * 删除快捷方式
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String deleteShortCutById(String params) throws Exception;
	
	/***
	 * 添加用户的快捷方式
	 */
	public String saveShortCutByUser(String params) throws Exception;
	/***
	 * 修改系统图标样式
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String updateSystemIcons(String params) throws Exception;
	/***
	 * 查询系统菜单图标样式
	 * @param params
	 * @return
	 */
	public String querySystemIcons(String params);
	/***
	 * 查询用户桌面墙纸
	 * @throws Exception 
	 */
	public String queryUserWallpaper(String params) throws Exception;
	/***
	 * 修改用户桌面墙纸
	 * @throws Exception 
	 */
	public String updateUserWallpaper(String params) throws Exception;
	/***
	 * 查询用户系统风格
	 */
	public Map queryUserSystemPage(String params) throws Exception;
	/***
	 * 修改用户系统风格
	 */
	public String updateUserSystemPage(String params) throws Exception;
	/***
	 * 上传用户图片
	 * @throws Exception 
	 */
	public boolean saveImgs(List<String> saveNames,List<String> imgsUrl) throws Exception;
	/****
	 * 查询桌面图片
	 * @throws Exception 
	 */
	public String queryImgs(String params) throws Exception;
	/****
	 * 查询功能菜单头信息
	 * @throws Exception 
	 */
	public String queryMenuHeader(String params) throws Exception;
	/****
	 * 保存或者修改菜单页面顶部信息
	 */
	public String updateMenuHeader(String params) throws Exception;
	/***
	 * 获取天气数据
	 */
	public String getWeatherData(String params) throws Exception;
}
