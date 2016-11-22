package com.jhkj.mosdc.sc.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;

import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.sc.service.YktXfqkService;

public class YktXfqkServiceImpl extends BaseServiceImpl implements YktXfqkService {

	@Override
	public String getTextData(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String start = json.getString("fromDate");
		String end = json.getString("toDate");
		String sql ="select sum(xfje) zxfje,count(*) zxfbs from tb_ykt_xfmx where substr(xfsj,0,10) between '"+start+"' and '"+end+"' ";
		List<Map> results = this.querySqlList(sql);
		Map map = new HashMap();
		map.put("start", start);
		map.put("end", end);
		map.put("data", results.get(0));
		return Struts2Utils.map2json(map);
	}

	@Override
	public String getChartData1(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String start = json.getString("fromDate");
		String end = json.getString("toDate");
		
		String sql ="select lx.mc as ckrsf,sum(xfje) zxfje,count(*) zxfcs from tb_ykt_xfmx xfmx "+
						" left join tb_ykt_kh kh on kh.zh = xfmx.kh "+
						" left join tb_ykt_kh_lx lx on lx.dm = kh.ckrsf "+
						" where substr(xfsj,0,10) between '"+start+"' and '"+end+"' group by lx.mc";
		sql ="select ckrsf,sum(xfje) zxfje,count(*) zxfcs from ( "+
				" select case when jzg.xm is not null then '教职工' when xjxx.xm is not null then '学生' "+
				" when jzg.xm is null and xjxx.xm is null then '临时卡' end as  ckrsf,xfmx.* from tb_ykt_xfmx xfmx "+ 
				" left join tb_jzgxx jzg on jzg.zgh = xfmx.ryid "+
				" left join tb_xjda_xjxx xjxx on xjxx.xh = xfmx.ryid "+
				" where substr(xfsj,0,10) between '"+start+"' and '"+end+"') group by ckrsf ";
		List<Map> results = this.querySqlList(sql);
		DecimalFormat df = new DecimalFormat("#.00");
		for(Map obj : results){
			obj.put("name", obj.get("CKRSF")==null?"其他": obj.get("CKRSF"));
			double zxfje = MapUtils.getDoubleValue(obj, "ZXFJE",0.00);
			obj.put("y", zxfje);
			int zxfcs = MapUtils.getIntValue(obj, "ZXFCS");
			obj.put("vv",zxfcs);
			obj.put("avg", df.format(zxfje/zxfcs));
		}
		return Struts2Utils.list2json(results);
	}

	@Override
	public String getChartData2(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String start = json.getString("fromDate");
		String end = json.getString("toDate");
		String sql ="select xbm,dbm,sum(xfje) zxfje,count(*) zxfcs from ( "+
						"select xfmx.*,dk.mc,bm.mc xbm,bm1.mc dbm from tb_ykt_xfmx xfmx "+
						"left join tb_ykt_dk dk on dk.dkh = xfmx.dkh "+
						"left join tb_ykt_ywbm bm on bm.id = dk.lx "+
						"left join tb_ykt_ywbm bm1 on bm1.id = bm.fjd_id "+ 
						"where dk.mc not in ('教育系机房POS','数学系机房POS','网络中心测试','24','84') "+
						"and substr(xfsj,0,10) between '"+start+"' and '"+end+"'  "+
						") group by xbm,dbm order by dbm,xbm";
		List<Map> results = this.querySqlList(sql);
		List<Map> backval = new ArrayList<Map>();
		double xy = 0.00,cs=0.00,ct=0.00;
		int xybs =0,csbs=0,ctbs=0;
		DecimalFormat df = new DecimalFormat("#.00");
		for(Map temp : results){
			// 过滤封装，超市，餐厅，洗浴
			String xbm = MapUtils.getString(temp, "XBM");
			String dbm = MapUtils.getString(temp, "DBM");
			if(xbm==null||dbm==null){
				continue;
			}
			if(dbm.contains("洗浴")){
				double tempxy = MapUtils.getDoubleValue(temp, "ZXFJE");
				xy += tempxy;
				int tempbs = MapUtils.getIntValue(temp, "ZXFCS");
				xybs += tempbs;
				continue;
			}
			if(xbm.contains("超市")){
				double tempxy = MapUtils.getDoubleValue(temp, "ZXFJE");
				cs += tempxy;
				int tempbs = MapUtils.getIntValue(temp, "ZXFCS");
				csbs += tempbs;
				continue;
			}
			if(dbm.contains("食堂")){
				double tempxy = MapUtils.getDoubleValue(temp, "ZXFJE");
				ct += tempxy;
				int tempbs = MapUtils.getIntValue(temp, "ZXFCS");
				ctbs += tempbs;
				continue;
			}
		}
		Map map1 = new HashMap();
		map1.put("name", "洗浴");
		map1.put("y", df.format(xy));
		map1.put("vv", xybs);
		map1.put("avg", df.format(xy/xybs));
		backval.add(map1);
		Map map2 = new HashMap();
		map2.put("name", "超市");
		map2.put("y", df.format(cs));
		map2.put("vv", csbs);
		map2.put("avg", df.format(cs/csbs));
		backval.add(map2);
		Map map3 = new HashMap();
		map3.put("name", "餐厅");
		map3.put("y", df.format(ct));
		map3.put("vv", ctbs);
		map3.put("avg", df.format(ct/ctbs));
		backval.add(map3);
		return Struts2Utils.list2json(backval);
	}

	@Override
	public String getChartData3(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String start = json.getString("fromDate");
		String end = json.getString("toDate");
		String sql ="select  deal.name_,count(*) cs,sum(rec.money_) je from t_card_recharge rec "+
							" left join t_code_card_deal deal on deal.id = rec.card_deal_id "+
							" where rec.time_ between '"+start+"' and '"+end+"' "+
							" group by deal.name_";
		DecimalFormat df = new DecimalFormat("#.00");
		List<Map> results = this.querySqlList(sql);
		for(Map obj : results){
			obj.put("name", obj.get("NAME_"));
			double zxfje = MapUtils.getDoubleValue(obj, "JE",0.00);
			obj.put("y", zxfje);
			int zxfcs = MapUtils.getIntValue(obj, "CS");
			obj.put("vv",zxfcs);
			obj.put("avg", df.format(zxfje/zxfcs));
		}
		return Struts2Utils.list2json(results);
	}

	@Override
	public String getTableData(String params) {
		
		return null;
	}

}
