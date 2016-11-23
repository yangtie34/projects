package com.jhkj.mosdc.sc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.service.BaseService;
import com.jhkj.mosdc.framework.util.StringUtils;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.sc.service.XsxfqktjService;

@SuppressWarnings("rawtypes")
public class XsxfqktjServiceImpl implements XsxfqktjService{
	private BaseService baseService;
	
	
	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}	
		
	@Override
	public String queryXsxfgk(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String start = json.getString("fromDate");
		String end = json.getString("toDate");
		
		String tableName ="tb_ykt_xfmx";
		if(json.containsKey("init")){
			tableName = "tb_ykt_xfmx_log";
		}
		//消费总额
		String xfze = "select sum(t.xfje) xfze from "+tableName+" t where t.xfsj between ''{0}'' and ''{1}''   and t.jykm=210 ";
		xfze = StringUtils.format(xfze, start,end);
		//性别分类
		String xbfl = "select sum(t.xfje) xfje,bz.mc xb from "+tableName+" t left join tb_xjda_xjxx xj on xj.id = t.ryid left join tc_xxbzdmjg bz on xj.xb_id = bz.id " +
				" where t.xfsj between ''{0}'' and ''{1}''  and t.jykm=210 group by bz.mc";
		xbfl = StringUtils.format(xbfl,start,end);
		//户口类型分类
		String hklxfl = "select sum(t.xfje) xfje,bz.mc hklx from "+tableName+" t left join tb_xjda_xjxx xj on xj.id = t.ryid left join tc_xxbzdmjg bz on decode(xj.hklx_id,null,1001000000273147,1001000000273148,1001000000731382,1001000000273147) = bz.id" +
				" where t.xfsj between ''{0}'' and ''{1}'' and t.jykm=210  group by bz.mc";
		hklxfl = StringUtils.format(hklxfl,start,end);
		//贫困类型分类
		String pklxfl = "select decode(jz.id,null,''非贫困'',''贫困'') zt, sum(t.xfje) xfje  from "+tableName+" t left join  tb_xg_jz_zxj jz on t.ryid = jz.xs_id and jz.xszt = 3 " +
				" where t.xfsj between ''{0}'' and ''{1}''  and t.jykm=210 group by decode(jz.id,null,''非贫困'',''贫困'')";
		pklxfl = StringUtils.format(pklxfl,start,end);
		Map<String, List> result = new HashMap<String, List>();
		result.put("xfze", baseService.queryListMapInLowerKeyBySql(xfze));
		result.put("xbfl", baseService.queryListMapInLowerKeyBySql(xbfl));
		result.put("hklxfl", baseService.queryListMapInLowerKeyBySql(hklxfl));
		result.put("pklxfl", baseService.queryListMapInLowerKeyBySql(pklxfl));
		return Struts2Utils.map2json(result);
	}
	@Override
	public String queryRjxfqk(String params){
		JSONObject json = JSONObject.fromObject(params);
		String start = json.getString("fromDate");
		String end = json.getString("toDate");
		
		String tableName ="tb_ykt_xfmx";
		if(json.containsKey("init")){
			tableName = "tb_ykt_xfmx_log";
		}
		String sql = "select count(distinct(t.ryid)) xfrs,sum(t.xfje) xfje,to_char(t.xfsj_date,''yyyy-mm-dd'') xfrq,round(sum(t.xfje)/count(distinct(t.ryid)),2) rjxfe from "+tableName+" t" +
				" where t.xfsj between ''{0}'' and ''{1}''  and t.jykm=210 group by to_char(t.xfsj_date,''yyyy-mm-dd'')";
		sql = StringUtils.format(sql,start,end);
		List<Map> list = baseService.queryListMapInLowerKeyBySql(sql);
		return  Struts2Utils.list2json(list);
	}

	@Override
	public String querySjdxfqk(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String start = json.getString("fromDate");
		String end = json.getString("toDate");
		
		String tableName ="tb_ykt_xfmx";
		if(json.containsKey("init")){
			tableName = "tb_ykt_xfmx_log";
		}
		String sql = "select tt.sj,tt.dd,sum(tt.xfje) xfje from (select CASE " +
				" WHEN substr(t.xfsj,12,2 ) <= ''09'' THEN ''早上''" +
				" WHEN substr(t.xfsj,12,2 ) between ''09'' and ''16'' THEN ''中午'' " +
				" WHEN substr(t.xfsj,12,2 ) >= ''16'' THEN ''晚上''  ELSE  ''''  END AS sj, " +
				" b.mc dd, t.* " +
				" from "+tableName+" t  LEFT join tb_ykt_dk d on t.dkh = d.dkh " +
				" LEFT join tb_ykt_ywbm b on d.lx = to_number(b.id) where t.xfsj between ''{0}'' and ''{1}''  and t.jykm=210 " +
				")tt group by tt.sj,tt.dd order by tt.dd";
		sql = StringUtils.format(sql,start,end);
		List<Map> list = baseService.queryListMapInLowerKeyBySql(sql);
		return  Struts2Utils.list2json(list);
	}
	
	@Override
	public String queryCtcbxfqk(String params){
		JSONObject json = JSONObject.fromObject(params);
		String xfrq = json.getString("xfrq");
		String sql = "select t.jyrq," +
				"b.mc kj, bm.mc bm,sum(t.jyze) fz,sum(t.cbrc) rz," +
				"max(case t.cbdm when 1 then t.jyze end) f1," +
				"max(case t.cbdm when 2 then t.jyze end) f2," +
				"max(case t.cbdm when 3 then t.jyze end) f3," +
				"max(case t.cbdm when 1 then t.cbrc end) r1," +
				"max(case t.cbdm when 2 then t.cbrc end) r2," +
				"max(case t.cbdm when 3 then t.cbrc end) r3," +
				"trunc(sum(t.jyze)/nvl(sum(t.cbrc),1),2) rj" +
				" from tb_ykt_dk_count_day t" +
				" inner join tb_ykt_dk d on t.dkh = d.dkh" +
				" inner join tb_ykt_ywbm b on d.lx = b.id " +
				" inner join tb_ykt_ywbm bm on b.fjd_id = bm.id " +
				" where to_char(t.jyrq,''yyyy-mm-dd'') = ''{0}'' and b.mc not in(''超市'',''西一超市'',''西面超市'',''西面超市群'',''东面超市群'',''教育系机房'',''代扣电费'')  " +
				" group by t.jyrq,b.mc,b.id,bm.mc order by bm.mc,b.id";
		sql = StringUtils.format(sql,xfrq);
		List<Map> list = baseService.queryListMapInLowerKeyBySql(sql);
		return  Struts2Utils.list2json(list);
	}
	
}
