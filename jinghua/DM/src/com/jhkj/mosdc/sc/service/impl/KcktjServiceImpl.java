package com.jhkj.mosdc.sc.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.jhkj.mosdc.framework.service.BaseService;
import com.jhkj.mosdc.framework.util.StringUtils;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.sc.service.KcktjService;
import com.jhkj.mosdc.sc.util.BzksUtils;

public class KcktjServiceImpl implements KcktjService {
	private BaseService baseService;
	
	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}	
	
	@Override
	public String queryKckgk(String params) {
		String sql = "select t1.num kczs,t2.num jszkc,t3.num fqkc from (select count(1) num from T_KCXX)t1," +
				"(select count(1) num from T_KCXX where sfky = '1')t2," +
				"(select count(1) num from T_KCXX where sfky = '0')t3";
		List<Map> result = baseService.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.list2json(result);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String queryBxqksl(String params) {
		Map xnxq = baseService.getCurrentXnxq();
		String xnmc = (String) xnxq.get("xnMc");
		String xqmc = (String) xnxq.get("xqMc");
		String sql = "select sum(kczxs) kczxs,sum(skxs) llxs,sum(syxs) sjxs from t_bzks_jxjh where kkxn=2014 and kkxq=0";
//		sql = StringUtils.format(sql, xnmc,xqmc);
		List<Map> list = baseService.queryListMapInLowerKeyBySql(sql);
		Map map = list.get(0);
		List<Map> result = new ArrayList<Map>();
		int llxss = MapUtils.getIntValue(map, "llxs");
		int sjxss = MapUtils.getIntValue(map, "sjxs");
		//理论学时
		Map llxs = new HashMap();
		llxs.put("name","理论学时");
		llxs.put("num",map.get("llxs"));
		llxs.put("y", String.format("%.2f", (float)llxss/(sjxss+llxss)*100));
		//实践学时
		Map sjxs = new HashMap();
		sjxs.put("name","实践学时");
		sjxs.put("num", map.get("sjxs"));
		sjxs.put("y",   String.format("%.2f", (float)sjxss/(sjxss+llxss)*100));
		/*//上机学时
		Map compsc = new HashMap();
		compsc.put("name","上机学时");
		compsc.put("num", map.get("compsc"));
		compsc.put("y",   (float)(Math.round( (Float.parseFloat((String) map.get("compsc"))*100/Float.parseFloat((String) map.get("zxs")))*100))/100);
		//一体化学时
		Map ythxs = new HashMap();
		ythxs.put("name","一体化学时");
		ythxs.put("num", map.get("ythxs"));
		ythxs.put("y", (float)(Math.round( (Float.parseFloat((String) map.get("ythxs"))*100/Float.parseFloat((String) map.get("zxs")))*100))/100);		*/
		result.add(llxs);
		result.add(sjxs);/*
		result.add(compsc);
		result.add(ythxs);*//*
		Map backval = new HashMap();
		backval.put("xn", "2014-2015学年");
		backval.put("xq", "第一学期");
		backval.put("list", result);*/
		return Struts2Utils.list2json(result);
	}

	@Override
	public String queryBxqkkqk(String params) {
		
		String sql = "select t1.kczs,t2.bxqkcs,round(100*t2.bxqkcs/t1.kczs,2) bxqkkzb from (select count(1) kczs from T_KCXX) t1," +
				"(select count(distinct kcdm) BXQKCS from t_bzks_jxjh  where kkxn='2014' and kkxq='0') t2";
		List<Map> result = baseService.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.list2json(result);
	}

	@Override
	public String queryKcflqk(String params) {
		String sql = "select '课程分类' as name,mk.mc field,count(1) value from dual";
		List<Map> result = baseService.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.list2json(result);
	}

	@Override
	public String queryKccbdw(String params) {
		String sql = "select '建设课程数' as name,zzjg.mc field,zzjg.dm ,count(*) value from t_kcxx kcxx" +
				" inner join tb_jxzzjg zzjg on zzjg.dm = kcxx.cddwdm and cc=1 and zzjg.mc not in ('思想政治理论教研部','公共艺术与职业技能教学部','软件学院','继续教育学院') " +
				" group by zzjg.mc,zzjg.dm order by zzjg.dm";
		List<Map> result = baseService.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.list2json(result);
	}
	@Override
	public String queryYxZxs(String params) {
		String sql = "select kcyxdm,kkxn,zzjg.mc as yxmc,sum(kczxs) kczxs,sum(skxs) llxs,sum(syxs) sjxs "
				+ " from t_bzks_jxjh jxjh inner join tb_jxzzjg zzjg on zzjg.dm = jxjh.kcyxdm and cc=1  "
				+ " group by kcyxdm,kkxn,zzjg.mc order by kcyxdm,kkxn";

		List<Map> result = baseService.querySqlList(sql);
		
		return Struts2Utils.list2json(result);
	}
}
