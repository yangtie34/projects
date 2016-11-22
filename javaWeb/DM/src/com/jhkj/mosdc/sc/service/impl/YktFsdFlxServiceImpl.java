package com.jhkj.mosdc.sc.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;

import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.sc.service.YktFsdFlxService;
/**
 * 这个类里的实现是个机井，修改时要有耐心！！！
 * @author Administrator
 *
 */
public class YktFsdFlxServiceImpl extends BaseServiceImpl implements
		YktFsdFlxService {
	
	@Override
	public String getTextData(String params) {// 餐厅消费
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
		Map map = new HashMap();
		DecimalFormat df = new DecimalFormat("#.00");
		map.put("start", start);
		map.put("end", end);
		double xy = 0.00,cs=0.00,ct=0.00;
		int xybs =0,csbs=0,ctbs=0;
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
			if(dbm.contains("餐厅")){
				double tempxy = MapUtils.getDoubleValue(temp, "ZXFJE");
				ct += tempxy;
				int tempbs = MapUtils.getIntValue(temp, "ZXFCS");
				ctbs += tempbs;
				continue;
			}
		}
		Map<String,Map> result = new HashMap<String,Map>();
		Map temp1 = new HashMap();
		temp1.put("ZXFJE", String.format("%.2f", ct));
		temp1.put("ZXFBS", ctbs);
		Map temp2 = new HashMap();
		temp2.put("ZXFJE", String.format("%.2f", xy));
		temp2.put("ZXFBS", xybs);
		Map temp3 = new HashMap();
		temp3.put("ZXFJE", String.format("%.2f", cs));
		temp3.put("ZXFBS", csbs);
		result.put("cs", temp3);
		result.put("ct", temp1);
		result.put("xy", temp2);
		map.put("data", result);// ZXFJE、ZXFBS
		return Struts2Utils.map2json(map);
	}


	@Override
	public String getChartData1(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String start = json.getString("fromDate");
		String end = json.getString("toDate");
		String sql = "select cbdm,xflx,sum(jyze) zxfje,sum(cbrc) zxfcs from ( "
				+ " select xfmx.*,dk.mc,bm.mc xbm,bm1.mc dbm,"
				+ " case when instr(bm.mc,'餐厅')>0 then '餐厅'"
				+ " when instr(bm.mc,'超市') >0 then  '超市'"
				+ " when instr(bm.mc,'洗浴')>0 then  '洗浴' else '餐厅' end as xflx from tb_ykt_dk_count_day xfmx "
				+ " left join tb_ykt_dk dk on dk.dkh = xfmx.dkh "
				+ " left join tb_ykt_ywbm bm on bm.id = dk.lx "
				+ " left join tb_ykt_ywbm bm1 on bm1.id = bm.fjd_id  "
				+ " where dk.mc not in ('教育系机房POS','数学系机房POS','网络中心测试','24','84') "
				+ " and to_char(xfmx.jyrq,'yyyy-MM-dd') between '" + start
				+ "' and '" + end
				+ "'  ) group by xflx,cbdm order by xflx,cbdm";
		List<Map> results = this.querySqlList(sql);
		List<Map> result1 = new ArrayList<Map>();
		List<Map> result2 = new ArrayList<Map>();
		for(Map temp : results){
			String xflx = temp.get("XFLX").toString();
			String cbdm = temp.get("CBDM").toString();
			if("超市".equals(xflx)){
				if("1".equals(cbdm)){
					temp.put("CBMC", "上午");
				}else if("2".equals(cbdm)){
					temp.put("CBMC", "下午");
				}else if("3".equals(cbdm)){
					temp.put("CBMC", "晚上");
				}
				
				result1.add(temp);
			}
			if("餐厅".equals(xflx)){
				if("1".equals(cbdm)){
					temp.put("CBMC", "早餐");
				}else if("2".equals(cbdm)){
					temp.put("CBMC", "午餐");
				}else if("3".equals(cbdm)){
					temp.put("CBMC", "晚餐");
				}
				
				result2.add(temp);
			}
		}
		Map backval = new HashMap();
		backval.put("cs", result1);
		backval.put("ct", result2);
		return Struts2Utils.map2json(backval);
	}

	@Override
	public String getChartData2(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String start = json.getString("fromDate");
		String end = json.getString("toDate");
		String sql = "select xfjd,count(*) zxfbs,sum(xfje) zxfje from ( "+
       " select xfmx.*,dk.mc,bm.mc xbm,bm1.mc dbm, "+
       "  case when substr(xfsj,12,5) between '06:00' and '12:00' then 1 "+
       "  when substr(xfsj,12,5) between '12:01' and '17:00' then 2 "+
       "  when substr(xfsj,12,5) between '17:01' and '22:00' then 3 "+
       "  end as xfjd from tb_ykt_xfmx xfmx  "+
       "  left join tb_ykt_dk dk on dk.dkh = xfmx.dkh  "+
       "  left join tb_ykt_ywbm bm on bm.id = dk.lx  "+
       "  left join tb_ykt_ywbm bm1 on bm1.id = bm.fjd_id   "+
       "  where bm1.mc ='洗浴中心' "+
       "  and substr(xfsj,0,10) between '"+start+"' and '"+end+"'  ) group by xfjd order by xfjd"; 
		List<Map> results = this.querySqlList(sql);
		for(Map temp : results){
			String cbdm = MapUtils.getString(temp, "XFJD");
			if("1".equals(cbdm)){
				temp.put("CBMC", "上午");
			}else if("2".equals(cbdm)){
				temp.put("CBMC", "下午");
			}else if("3".equals(cbdm)){
				temp.put("CBMC", "晚上");
			}
		}
		return Struts2Utils.list2json(results);
	}

	@Override
	public String getTableData(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String start = json.getString("fromDate");
		String end = json.getString("toDate");
		String sql = "select xflx,dbm,xbm,cbdm,sum(jyze) zxfje,sum(cbrc) zxfcs from ( "+
				 " select xfmx.*,dk.mc,bm.mc xbm,bm1.mc dbm,"+
				 "  case when instr(bm.mc,'餐厅')>0 then '餐厅'"+
				 "  when instr(bm.mc,'超市') >0 then  '超市'"+
				 "  when instr(bm.mc,'洗浴')>0 then  '洗浴' else '餐厅' end as xflx from tb_ykt_dk_count_day xfmx "+
				 "  left join tb_ykt_dk dk on dk.dkh = xfmx.dkh "+
				 "  left join tb_ykt_ywbm bm on bm.id = dk.lx "+
				 "  left join tb_ykt_ywbm bm1 on bm1.id = bm.fjd_id  "+
				 "  where dk.mc not in ('教育系机房POS','数学系机房POS','网络中心测试','24','84') "+
				 "  and to_char(xfmx.jyrq,'yyyy-MM-dd') between '"+start+"' and '"+end+"'  ) WHERE dbm is not null and xbm is not null "+
				 "  group by xflx,dbm,xbm,cbdm order by xflx,dbm,cbdm"; 
		List<Map> results = this.querySqlList(sql);
		List<Map> result1 = new ArrayList<Map>();
		List<Map> result2 = new ArrayList<Map>();
		for(Map temp:results){
			String xflx = MapUtils.getString(temp, "XFLX");
			if("超市".equals(xflx)){
				result1.add(temp);
			}
			if("餐厅".equals(xflx)){
				result2.add(temp);
			}
		}
		Map backval = new HashMap();
		backval.put("cs", xxx(result1));
		backval.put("ct", xxx(result2));
		return Struts2Utils.map2json(backval);
	}

	@Override
	public String getTableData1(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String start = json.getString("fromDate");
		String end = json.getString("toDate");
		String sql = "select xfjd CBDM,dbm,xbm,count(*) zxfbs,sum(xfje) zxfje from ( "+
       " select xfmx.*,dk.mc,bm.mc xbm,bm1.mc dbm, "+
       "  case when substr(xfsj,12,5) between '06:00' and '12:00' then 1 "+
       "  when substr(xfsj,12,5) between '12:01' and '17:00' then 2 "+
       "  when substr(xfsj,12,5) between '17:01' and '22:00' then 3 "+
       "  end as xfjd from tb_ykt_xfmx xfmx  "+
       "  left join tb_ykt_dk dk on dk.dkh = xfmx.dkh  "+
       "  left join tb_ykt_ywbm bm on bm.id = dk.lx  "+
       "  left join tb_ykt_ywbm bm1 on bm1.id = bm.fjd_id   "+
       "  where bm1.mc ='洗浴中心' "+
       "  and substr(xfsj,0,10) between '"+start+"' and '"+end+"'  ) group by xfjd,dbm,xbm order by dbm,xbm,CBDM"; 
		List<Map> results = this.querySqlList(sql);
		for(Map temp : results){
			String cbdm = MapUtils.getString(temp, "XFJD");
			if("1".equals(cbdm)){
				temp.put("CBMC", "上午");
			}else if("2".equals(cbdm)){
				temp.put("CBMC", "下午");
			}else if("3".equals(cbdm)){
				temp.put("CBMC", "晚上");
			}
		}
		
		return Struts2Utils.list2json(xxx(results));
	}

	private List xxx(List<Map> results) {
		Map<String, Map<String, Map<String, Object>>> backval = new TreeMap<String, Map<String, Map<String, Object>>>(); // 大部门
		for (Map temp : results) {
			String dbm = MapUtils.getString(temp, "DBM");
			String xbm = MapUtils.getString(temp, "XBM");
			String cbmc = MapUtils.getString(temp, "CBDM");
			if("1".equals(cbmc)){
				cbmc ="a";
			}else if("2".equals(cbmc)){
				cbmc ="b";
			}else if("3".equals(cbmc)){
				cbmc ="c";
			}
			if (backval.containsKey(dbm)) {
				Map<String, Map<String, Object>> tm1 = backval.get(dbm);
				if (tm1.containsKey(xbm)) {// 小部门
					Map<String, Object> tm2 = tm1.get(xbm);
					if (tm2.containsKey(cbmc)) {
						// 不作更改
					} else {
						tm2.put(cbmc, temp);
					}
				} else {
					Map<String, Object> tm2 = new TreeMap<String, Object>(); // 创建新的餐别map
					tm2.put(cbmc, temp);
					tm1.put(xbm, tm2);
				}
			} else {
				Map<String, Map<String, Object>> atm1 = new TreeMap<String, Map<String, Object>>();// 创建个新的小部门map
				Map<String, Object> tm2 = new TreeMap<String, Object>(); // 创建新的餐别map
				tm2.put(cbmc, temp);
				atm1.put(xbm, tm2);
				backval.put(dbm, atm1);
			}
		}
		List<Map> aaaa = new ArrayList<Map>();
		Iterator it = backval.keySet().iterator();
		while(it.hasNext()){
			String dbmmc =  (String)it.next();
			Map<String, Map<String, Object>> inb = backval.get(dbmmc);
			Map temp = new TreeMap();
			temp.put("name", dbmmc);
			temp.put("length", inb.size());
			List templist = new ArrayList();
			Iterator it1 = inb.keySet().iterator();
			while(it1.hasNext()){
				String xbmmc = (String)it1.next();// 小部门
				Map<String,Object> temp1 = inb.get(xbmmc);
				temp1.put("name",xbmmc);
				templist.add(temp1);
			}
			temp.put("items", templist);
			aaaa.add(temp);
		}
		return aaaa;
	}
	
	
}
