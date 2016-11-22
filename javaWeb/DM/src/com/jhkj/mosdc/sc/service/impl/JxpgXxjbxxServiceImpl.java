package com.jhkj.mosdc.sc.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.sc.service.JxpgXxjbxxService;

public class JxpgXxjbxxServiceImpl implements JxpgXxjbxxService {
	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * 查询学校基本情况
	 */
	@SuppressWarnings("rawtypes")
	public String queryXxjbqk(String params){
		JSONObject json = JSONObject.fromObject(params);
		String pageCode = json.getString("pageCode");
		String xn = json.getString("xn");
		String sql = "select t.item_code,t.item_value from tb_jxpg_items t" +
				" where t.page_code = '"+pageCode+"' and t.xn = '"+xn+"'";
		List items = baseDao.queryListMapInLowerKeyBySql(sql);
		Map<String,String> result = new HashMap<String, String>();
		if (items.size() > 0) {
			for (int i = 0; i < items.size(); i++) {
				Map item = (Map) items.get(i);
				result.put((String)item.get("item_code"), (String)item.get("item_value"));
			}
		}
		result.put("xn", xn);
		return Struts2Utils.map2json(result);
	}

	@Override
	public String autoFillXxjbqk(String params) {
		
		return this.queryXxjbqk(params);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String saveXxjbqk(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String pageCode = json.getString("pageCode");
		String pageName = json.getString("pageName");
		String xn = json.getString("xn");
		List<Map> items = json.getJSONArray("items");
		String sql ="select t.item_code from tb_jxpg_items t where t.page_code = '"+pageCode+"' and t.xn = '"+xn+"'";
		List<Map> itemCodes = baseDao.queryListMapInLowerKeyBySql(sql);
		Set<String> codes = new HashSet<String>();
		for (Map itemCode : itemCodes) {
			codes.add((String) itemCode.get("item_code"));
		}
		for (int i = 0; i < items.size(); i++) {
			String itemCode = (String) items.get(i).get("itemCode");
			String itemSql = "";
			if (codes.contains(itemCode)) {
				itemSql = "update tb_jxpg_items t set t.item_value = '"+(String) items.get(i).get("itemValue")+"' " +
						" where t.page_code = '"+pageCode+"' and t.xn = '"+xn+"' and item_code='"+(String) items.get(i).get("itemCode")+"'";
			}else{
				itemSql = "insert into tb_jxpg_items(id, page_name, page_code, item_name, item_code, item_value, create_time, xn)" +
						"values( SEQ_SYSTEM.Nextval, " +
								"'"+pageName+"'," +
								"'"+pageCode+"', " +
								"'"+(String) items.get(i).get("itemName")+"'," +
								"'"+(String) items.get(i).get("itemCode")+"'," +
								"'"+(String) items.get(i).get("itemValue")+"', " +
								"sysdate, " +
								"'"+xn+"')";
			}
			baseDao.update(itemSql);
		}
		return this.queryXxjbqk(params);
	}
}
