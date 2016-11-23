package com.jhkj.mosdc.jxpg.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jhkj.mosdc.jxpg.service.ByzhxlqkService;
import com.jhkj.mosdc.jxpg.service.JxpgService;
import com.jhkj.mosdc.jxpg.util.JxpgUtil;

public class ByzhxlqkServiceImpl implements ByzhxlqkService {
	private JxpgService jxpgService;

	public void setJxpgService(JxpgService jxpgService) {
		this.jxpgService = jxpgService;
	}


	/**
	 * 保存数据之前调用
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map invokeBefore(String params) {
		Map result = new HashMap();
		boolean success = true;
		String error_message = "";
		
		List<Map> items = jxpgService.sendChangeItemsIntoDatabaseList(params);
		/* 开始数据校验 */
		for (int i = 0; i < items.size(); i++) {
			Map item = items.get(i);
			String ktsStr = String.valueOf(item.get("KTS_F"));
			String wcsStr = String.valueOf(item.get("WCS_F"));
			ktsStr = (ktsStr.equals("null") ? "0" : ktsStr);
			wcsStr = (wcsStr.equals("null") ? "0" : wcsStr);
			try{
				Long kts = Long.valueOf(ktsStr);
				Long wcs = Long.valueOf(wcsStr);
				if (wcs > kts || kts < 0 || wcs < 0) {
					success = false;
					error_message = "输入数据应大于0，且在社会实践中完成数必须小于或等于课题数！";
					break;
				}else{
					kts = kts==0 ? 1 :kts;
					item.put("BL_F",wcs*100/kts);
				}
			}catch (NumberFormatException e) {
				success = false;
				error_message = "输入数据格式不合法！";
			}
		}
		result.put("items", JxpgUtil.changeObjectAttrToMap(items));
		result.put("success", success);
		result.put("error_message", error_message);
		return result;
	}
	
	
}
