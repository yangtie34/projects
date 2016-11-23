package com.jhkj.mosdc.pano.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.pano.service.IPanoService;
import com.jhkj.mosdc.pano.wedgit.appService.IXsjbxxService;
/**
 * 全景入口实现类。
 * @ClassName: PanoServiceImpl 
 * @author zhangzg 
 * @date 2013年11月1日 下午4:15:22 
 *
 */
public class PanoServiceImpl extends BaseServiceImpl implements IPanoService{
	private static Map serviceMap = new HashMap();
	static{
		serviceMap.put("zp", "photoPathService");
		serviceMap.put("wjcf", "panoWjcfService");
		serviceMap.put("pjpy", "panoPjpyService");
		serviceMap.put("xszt", "panoXsztService");
		serviceMap.put("cjtj", "panoWhcjService");
		serviceMap.put("lhkh", "panoLhkhService");
		serviceMap.put("jnzs", "panoJnzsService");
	}
	private IXsjbxxService xsjbxxService;
	
	public void setXsjbxxService(IXsjbxxService xsjbxxService) {
		this.xsjbxxService = xsjbxxService;
	}

	@Override
	public String getStuMiniPanoById(String params) {
		JSONObject paramObj = JSONObject.fromObject(params);
		long pageId = paramObj.containsKey("pageId")?paramObj.getLong("pageId"):-1l;
		long xsId = paramObj.containsKey("xsId")?paramObj.getLong("xsId"):-1l;
		Map reqParams = new HashMap();
		/*查询弹出页中需要显示的基本信息*/
		List<Map> jbxxs = xsjbxxService.getXjxxByXsId(xsId);
		reqParams.put("jbxx", jbxxs!=null && jbxxs.size()!=0 ? jbxxs.get(0) : null);
		
		/*将弹出页中其他方面的信息请求service传递出去*/
		/*照片、违纪类型及次数、奖励类型及次数、学生状态、成绩统计、量化成绩、技能证书、宿舍、班主任、职务*/
		reqParams.put("serviceMap", serviceMap);
		reqParams.put("serviceMethod", "getStuMiniPanoById");
		
		return Struts2Utils.map2json(reqParams);
	} 
	
	@Override
	public String getTeacherMiniPanoById(String params) {
		return null;
	}
}
