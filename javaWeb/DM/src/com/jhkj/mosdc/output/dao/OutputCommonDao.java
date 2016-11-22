package com.jhkj.mosdc.output.dao;

import java.util.List;

import com.jhkj.mosdc.framework.dao.BaseDao;
/**
 * 
 * 
 * @Comments: 
 * Company: 河南精华科技有限公司
 * Created by zhangzg(vbxnt521_edu@163.com)
 * @DATE: 2012-11-6
 * @TIME: 下午05:07:54
 */
public interface OutputCommonDao extends BaseDao {
	/**
	 * 根据页面id获取页面数据。
	 * 页面-模板-功能区-组件。
	 * @param pageId
	 * @return
	 */
	public List queryPageDataByPageId(String pageId);
	/**
	 * 根据页面id和模板id获取页面数据。
	 * @param pageId
	 * @param templateId
	 * @return
	 */
	public List queryPageDataByPageIdAndTempId(String pageId,String templateId);
	/**
	 * 根据组件信息获取组件数据。
	 * @param id
	 * @return
	 */
	public List queryCompoById(String id);
	/**
	 * 根据给定日期获取当前日期所在的学期的起止日期。
	 * @param 当前日期。
	 * @return
	 */
	public List queryStartstopDataBydate(String currentDate);
	
	/**
	 * 根据文本id获取文本功能表对象。
	 * @param wbid
	 * @return
	 */
	public List queryWbgnByWbid(String wbid);
}
