package com.jhkj.mosdc.output.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.Response;

import net.sf.cglib.core.Converter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.bean.PageParam;
import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.output.dao.TbTjgnZdytjymDao;
import com.jhkj.mosdc.output.service.TbTjgnZdytjymService;

/**
 * @comments:统计功能-自定义统计页面serivce实现
 * @author hanking(E-mail : IsaidIwillgoon@gmail.com)
 * @date:2012-12-4
 * @time:下午06:02:26
 * @version :
 */
public class TbTjgnZdytjymServiceImpl extends BaseServiceImpl implements
		TbTjgnZdytjymService {

	private TbTjgnZdytjymDao tbTjgnZdytjymDao;

	public TbTjgnZdytjymDao getTbTjgnZdytjymDao() {
		return tbTjgnZdytjymDao;
	}

	public void setTbTjgnZdytjymDao(TbTjgnZdytjymDao tbTjgnZdytjymDao) {
		this.tbTjgnZdytjymDao = tbTjgnZdytjymDao;
	}

	@Override
	public String deleteTjgnZdytjym(String params) {
		JSONObject obj = JSONObject.fromObject(params);
		try {
			JSONArray ids = obj.getJSONArray("ids");
			Boolean flag = this.tbTjgnZdytjymDao.deleteTjgnZdytjym(ids);
			if (flag) {
				return SysConstants.JSON_SUCCESS_TRUE;
			}
			return SysConstants.JSON_SUCCESS_FALSE;
		} catch (NullPointerException npe) {
			npe.printStackTrace();
			return SysConstants.JSON_SUCCESS_FALSE;
		}
	}

	@Override
	public String queryTjgnZdytjym(String params) {
		JSONObject obj = JSONObject.fromObject(params);
		try {
			Map paramList = this.getSQLParams(obj);
			Integer start = obj.getInt("start");
			Integer limit = obj.getInt("limit");
			PageParam pageParam = new PageParam(start, limit);
			List<String> retList = (List) paramList.get("list");
			String seniorStr = (String) paramList.get("senior");
			List list = this.tbTjgnZdytjymDao.queryTjgnZdytjym(retList,seniorStr,
					pageParam);
			int totalCount = pageParam.getRecordCount();// pageParam 要不要置空？ 按理说
														// 局部变量Java内存回收机制应该可以自动回收的
			String jsonData = Struts2Utils.list2json(list);
			jsonData = "{success:true,\"data\":" + jsonData + " ,\"count\":"
					+ totalCount + "}";
			return jsonData;
		} catch (Exception e) {
			e.printStackTrace();
			return SysConstants.JSON_SUCCESS_FALSE;
		}
	}

	/**
	 * params:{ "fid":"123456","ymmc": "页面名称", "sfsy": "是否私有id", "mbid": "模板id",
	 * "gnlist": [ { "gnbt": "功能标题1", "zjlist": [ { "zjid": "组件id1" }, { "zjid":
	 * "组件id2" } ] }, { "gnbt": "功能标题2", "zjlist": [ { "zjid": "组件id3" }, {
	 * "zjid": "组件id4" } ] } ] }
	 * */
	@Override
	public String saveTjgnZdytjym(String params) {

		JSONObject obj = JSONObject.fromObject(params);
		try {
			Map map = (Map) obj.get("params");// 解析参数
			HashMap<String, Object> newMap = this.getHashMap(map);
			if (null == newMap) {
				return SysConstants.JSON_SUCCESS_FALSE;
			}
			Boolean flag = this.tbTjgnZdytjymDao.saveTjgnZdytjym(newMap);
			if (flag) {
				return SysConstants.JSON_SUCCESS_TRUE;
			}
			return SysConstants.JSON_SUCCESS_FALSE;
		} catch (Exception e) {
			e.getStackTrace();
			return SysConstants.JSON_SUCCESS_FALSE;
		}
	}

	@Override
	public String updateTjgnZdytjym(String params) {
		JSONObject obj = JSONObject.fromObject(params);
		try {
			Map map = (Map) obj.get("params");// 解析参数
			HashMap<String, Object> newMap = this.getHashMap(map);
			if (null == newMap) {
				return SysConstants.JSON_SUCCESS_FALSE;
			}
			Boolean flag = this.tbTjgnZdytjymDao.updateTjgnZdytjym(newMap);
			if (flag) {
				return SysConstants.JSON_SUCCESS_TRUE;
			}
			return SysConstants.JSON_SUCCESS_FALSE;
		} catch (Exception e) {
			e.printStackTrace();
			return SysConstants.JSON_SUCCESS_FALSE;
		}
	}

	/**
	 * 封装了修改和新增时的数据校验和转换方法
	 * 
	 * @param map
	 * @return
	 */
	private HashMap<String, Object> getHashMap(Map map) {
		HashMap<String, Object> newMap = new HashMap<String, Object>();
		Object fid = map.get("fid"), ymmc = map.get("ymmc"), sfsy = map
				.get("sfsy"), mbid = map.get("mbid");
		if ("".equals(fid) || "".equals(ymmc) || "null".equals(mbid)
				|| "".equals(sfsy) || mbid == null) {
			return null;// 后台再次校验数据是否为空,为空则返回null
		}
		if (null != map.get("id")) {
			newMap.put("id", map.get("id"));// 与修改时需要id
		}
		newMap.put("fid", fid);// 将父id添加到newMap
		newMap.put("ymmc", ymmc);// 将页面名称添加到newMap
		newMap.put("sfsy", sfsy);// 将是否私有添加到newMap
		newMap.put("mbid", mbid);// 将模板id->到tb_sc_mb表后转化为模板类型的id号
		newMap.put("gnlist", map.get("gnlist"));// 将功能集合添加到newMap

		return newMap;
	}

	@Override
	public String updateTjgnZdytjymSftgSh(String params) {
		JSONObject obj = JSONObject.fromObject(params);
		try {
			String shzt = obj.getString("shzt");
			String sftgsh = this.tbTjgnZdytjymDao.getSftgshFlag(shzt);
			if (null != sftgsh) {
				JSONArray ids = obj.getJSONArray("ids");
				Boolean flag = this.tbTjgnZdytjymDao.updateTjgnZdytjymSftgSh(
						ids, sftgsh);
				if (flag) {
					return SysConstants.JSON_SUCCESS_TRUE;
				}
			}
			return SysConstants.JSON_SUCCESS_FALSE;
		} catch (Exception e) {
			e.printStackTrace();
			return SysConstants.JSON_SUCCESS_FALSE;
		}
	}

	@Override
	public String loadTjgnZdytjymById(String params) {
		JSONObject obj = JSONObject.fromObject(params);
		try {
			Long id = Long.valueOf(obj.getString("id"));// 获取页面的id
			List list = this.tbTjgnZdytjymDao.loadTjgnZdytjymById(id);
			if (null == list || list.size() == 0) {
				// 如果数据为空,代表普通用户更改公有且已通过审核的数据操作
				return SysConstants.JSON_SUCCESS_FALSE;
			}
			JSONArray jsonArray = JSONArray.fromObject(Struts2Utils
					.list2json(list));
			String ymmc = null;
			Long fid = null, mbid = null, sfsy = null;
			// 暂适用于单页面单模板
			JSONObject jsonObj = JSONObject.fromObject(jsonArray.get(0));
			Long gnid = null;// 功能标题
			Map allMap = new HashMap();
			ymmc = jsonObj.getString("ymmc");
			fid = jsonObj.getLong("fid");
			sfsy = jsonObj.getLong("sfsy");
			mbid = jsonObj.getLong("mbid");

			List gnqList = new ArrayList();
			for (int i = 0, len = jsonArray.size(); i < len; i++) {
				gnid = JSONObject.fromObject(jsonArray.get(i)).getLong("gnqid");
				if (isIn(gnqList, gnid)) {
				} else {
					gnqList.add(gnid);
				}
			}
			List gnList = new ArrayList();
			for (int k = 0, gnLen = gnqList.size(); k < gnLen; k++) {
				Map gnMap = new HashMap();
				Long gnId = (Long) gnqList.get(k);
				List zjList = new ArrayList();
				String gnbt = null;
				String gnsm = null;
				for (int j = 0, jsonLen = jsonArray.size(); j < jsonLen; j++) {
					Map zjidMap = new HashMap();
					if (gnId.equals(JSONObject.fromObject(jsonArray.get(j))
							.getLong("gnqid"))) {
						gnbt = JSONObject.fromObject(jsonArray.get(j))
								.getString("gnbt");
						gnsm = JSONObject.fromObject(jsonArray.get(j))
								.getString("gnsm");
						Long zjid = JSONObject.fromObject(jsonArray.get(j))
								.getLong("zjid");
						zjidMap.put("zjid", zjid);
						zjList.add(zjidMap);
					}
				}
				gnMap.put("gnbt", gnbt);
				gnMap.put("gnsm", gnsm);
				gnMap.put("zjlist", zjList);
				gnList.add(gnMap);
			}
			allMap.put("id", id);
			allMap.put("ymmc", ymmc);
			allMap.put("fid", fid);
			allMap.put("sfsy", sfsy);
			allMap.put("mbid", mbid);
			allMap.put("gnlist", gnList);
			String jsonData = Struts2Utils.map2json(allMap);
			jsonData = "{success:true,\"data\":" + jsonData + "}";
			return jsonData;
		} catch (Exception e) {
			e.printStackTrace();
			return SysConstants.JSON_SUCCESS_FALSE;
		}
	}

	@Override
	public String queryZjData(String params) {
		JSONObject obj = JSONObject.fromObject(params);
		try {
			Integer start = obj.getInt("start");
			Integer limit = obj.getInt("limit");
			PageParam pageParam = new PageParam(start, limit);
			String name = obj.get("mc") == null ? null : obj.getString("mc")
					.toString();
			// 因为乱码,转码
			name = Struts2Utils.isoToUTF8(name);

			List list = this.tbTjgnZdytjymDao.queryZjData(name, pageParam);
			int totalCount = pageParam.getRecordCount();
			String jsonData = Struts2Utils.list2json(list);
			jsonData = "{success:true,\"data\":" + jsonData + " ,\"count\":"
					+ totalCount + "}";
			return jsonData;
		} catch (Exception e) {
			e.printStackTrace();
			return SysConstants.JSON_SUCCESS_FALSE;
		}
	}

	/**
	 * 
	 * @param list
	 *            list集合
	 * @param obj
	 *            对象
	 * @return true表示已经存在 false表示不存在
	 */
	private boolean isIn(List<Object> list, Object obj) {
		for (int i = 0, len = list.size(); i < len; i++) {
			if (obj != null && obj.equals(list.get(i))) {
				return true;// 如果存在返回true
			}
		}
		return false;
	}
}
