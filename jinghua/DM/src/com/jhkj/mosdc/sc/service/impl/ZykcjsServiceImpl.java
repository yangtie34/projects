package com.jhkj.mosdc.sc.service.impl;

import com.jhkj.mosdc.framework.service.BaseService;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.sc.service.ZykcjsService;

public class ZykcjsServiceImpl implements ZykcjsService {
	private BaseService baseService;
	
	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

	@Override
	public String queryZylbs(String params) {
		String sql = "select count(1) num from TC_XXBZDMJG zylb where zylb.bzdm = 'DM-ZYKL' and zylb.sfky = 1";
		return Struts2Utils.map2json(baseService.queryListMapInLowerKeyBySql(sql).get(0));
	}

	@Override
	public String queryZys(String params) {
		String sql = "select count(1) num from TB_XXZY_ZYXX zy";
		return Struts2Utils.map2json(baseService.queryListMapInLowerKeyBySql(sql).get(0));
	}

	@Override
	public String queryKcs(String params) {
		String sql = "select count(1) num from TB_KCK_KCXXB t where t.sfky = 1 ";
		return Struts2Utils.map2json(baseService.queryListMapInLowerKeyBySql(sql).get(0));
	}

	@Override
	public String queryJcs(String params) {
		String sql = "select count(kcjc.jc_id) num from TB_KCK_KCXXB kc left join TB_KCK_KCDYJCXX kcjc on kc.id = kcjc.kc_id ";
		return Struts2Utils.map2json(baseService.queryListMapInLowerKeyBySql(sql).get(0));
	}

	@Override
	public String queryZyflsl(String params) {
		String sql = "select zylb.mc name,count(zy.mc) y from TC_XXBZDMJG zylb inner join TB_XXZY_ZYXX zy on zy.zykl_id = zylb.id" +
				" where zylb.bzdm = 'DM-ZYKL'and zylb.sfky = 1 group by zylb.mc";
		return Struts2Utils.list2json(baseService.queryListMapInLowerKeyBySql(sql));
	}

	@Override
	public String queryZyflxx(String params) {
		String sql = "select zylb.mc zylb,zy.mc zymc,count(xj.id) xssl from TC_XXBZDMJG zylb inner join TB_XXZY_ZYXX zy on zy.zykl_id = zylb.id" +
				" left join TB_XJDA_XJXX XJ on zy.id = xj.zy_id where zylb.bzdm = 'DM-ZYKL'and zylb.sfky = 1 group by zylb.mc,zy.mc";
		return Struts2Utils.list2json(baseService.queryListMapInLowerKeyBySql(sql));
	}	
	

}
