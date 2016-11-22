package com.jhkj.mosdc.sc.service.impl;

import java.util.List;
import java.util.Map;

import com.jhkj.mosdc.framework.service.BaseService;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.sc.service.ZjxssztjService;

@SuppressWarnings("rawtypes")
public class ZjxssztjServiceImpl implements ZjxssztjService {
	private BaseService baseService;
	
	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}	

	@Override
	public String queryRxxl(String params) {
		String sql = " select nvl(xl.mc,'未知')field,'入学前学历'name ,count(1) value  from tb_xjda_xjxx t " +
				" left join tc_xxbzdmjg xl on xl.id = t.rxqxl_id " +
				" where t.xjzt_id = 1000000000000101 group by xl.mc ";
		List<Map> result = baseService.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.list2json(result);
	}

	@Override
	public String queryPycc(String params) {
		 String sql = " select nvl(cc.mc,'未知') name, count(1) y from tb_xjda_xjxx t " +
		 		" left join tc_xxbzdmjg cc on cc.id = t.pycc_id " +
		 		" where t.xjzt_id = 1000000000000101 group by cc.mc";
			List<Map> result = baseService.queryListMapInLowerKeyBySql(sql);
			return Struts2Utils.list2json(result);
	}

	@Override
	public String queryXz(String params) {
		String sql = "select nvl(xz.mc,'未知')field,'学制'name ,count(1) value from tb_xjda_xjxx t " +
		 		" left join tc_xz xz on xz.id = t.xz_id where t.xjzt_id = 1000000000000101 group by xz.mc order by xz.mc";
		List<Map> result = baseService.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.list2json(result);
	}
	
}
