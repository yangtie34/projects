package com.jhkj.mosdc.sc.service.impl;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.sc.service.TeacherJxrwService;

public class TeacherJxrwServiceImpl extends TeacherServiceImpl implements TeacherJxrwService {

	@Override
	public String queryZyjszwJxrw(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		String xn = json.containsKey("xn")?json.get("xn").toString():"0";
		String xq = json.containsKey("xq")?json.get("xq").toString():"0";
		Map node = getXzzzjgNode(zzjgId);
		String sql ="select nvl(jszw.mc,'职称未维护') name,sum(kczxs) y from t_bzks_jxjh jxjh "+
				" inner join tb_jxzzjg zzjg on zzjg.dm = jxjh.kcyxdm and cc=1 "+
				" inner join tb_jzgxx jzg on jzg.zgh = jxjh.skjszgh "+
				" left join dm_zxbz.t_zxbz_zyjszw jszw on jzg.zyjszw_id = jszw.dm "+
				" where 1=1 and jzg.yx_id in(select id from tb_xzzzjg " +
				" where qxm like '"+node.get("qxm")+"%' ) and jxjh.kkxn = "+xn+" and jxjh.kkxq="+xq+
				" group by jszw.mc";
		
		List<Map> result = baseDao.querySqlList(sql);
		return Struts2Utils.list2json(result);
	}
	@Override
	public String queryBzlbJxrw(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		String xn = json.containsKey("xn")?json.get("xn").toString():"0";
		String xq = json.containsKey("xq")?json.get("xq").toString():"0";
		Map node = getXzzzjgNode(zzjgId);
		String sql ="select nvl(jszw.mc,'编制类别未维护') name,sum(kczxs) y from t_bzks_jxjh jxjh "+
				" inner join tb_jxzzjg zzjg on zzjg.dm = jxjh.kcyxdm and cc=1 "+
				" inner join tb_jzgxx jzg on jzg.zgh = jxjh.skjszgh "+
				" left join dm_zxbz.t_zxbz_bzlb jszw on jzg.bzlb_id = jszw.dm "+
				" where 1=1 and jzg.yx_id in(select id from tb_xzzzjg " +
				" where qxm like '"+node.get("qxm")+"%' ) and jxjh.kkxn = "+xn+" and jxjh.kkxq="+xq+
				" group by jszw.mc";
		
		List<Map> result = baseDao.querySqlList(sql);
		return Struts2Utils.list2json(result);
	}
	
	@Override
	public String queryYxBzlbJxrw(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		String xn = json.containsKey("xn")?json.get("xn").toString():"0";
		String xq = json.containsKey("xq")?json.get("xq").toString():"0";
		Map node = getXzzzjgNode(zzjgId);
		String sql ="select nvl(jszw.mc,'编制类别未维护') name,sum(kczxs) y,zzjg.dm,zzjg.mc yxmc from t_bzks_jxjh jxjh "+
				" inner join tb_jxzzjg zzjg on zzjg.dm = jxjh.kcyxdm and cc=1 "+
				" inner join tb_jzgxx jzg on jzg.zgh = jxjh.skjszgh "+
				" left join dm_zxbz.t_zxbz_bzlb jszw on jzg.bzlb_id = jszw.dm "+
				" where 1=1 and jzg.yx_id in(select id from tb_xzzzjg " +
				" where qxm like '"+node.get("qxm")+"%' ) and jxjh.kkxn = "+xn+" and jxjh.kkxq="+xq+
				" group by jszw.mc,zzjg.mc,zzjg.dm order by dm";
		
		List<Map> result = baseDao.querySqlList(sql);
		/*for(Map obj : result){
			obj.put("field", obj.get("MC"));
			obj.put("name", obj.get("YXMC"));
			obj.put("value", obj.get("ZS"));
		}*/
		
		return Struts2Utils.list2json(result);
	}
	
	@Override
	public String queryYxZyjszwJxrw(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		String xn = json.containsKey("xn")?json.get("xn").toString():"0";
		String xq = json.containsKey("xq")?json.get("xq").toString():"0";
		Map node = getXzzzjgNode(zzjgId);
		String sql ="select nvl(jszw.mc,'职称未维护') name,sum(kczxs) y,zzjg.mc yxmc,zzjg.dm from t_bzks_jxjh jxjh "+
				" inner join tb_jxzzjg zzjg on zzjg.dm = jxjh.kcyxdm and cc=1 "+
				" inner join tb_jzgxx jzg on jzg.zgh = jxjh.skjszgh "+
				" left join dm_zxbz.t_zxbz_zyjszw jszw on jzg.zyjszw_id = jszw.dm "+
				" where 1=1 and jzg.yx_id in(select id from tb_xzzzjg " +
				" where qxm like '"+node.get("qxm")+"%' ) and jxjh.kkxn = "+xn+" and jxjh.kkxq="+xq+
				" group by jszw.mc,zzjg.mc,zzjg.dm order by dm";
		
		List<Map> result = baseDao.querySqlList(sql);
//		for(Map obj : result){
//			obj.put("field", obj.get("MC"));
//			obj.put("name", obj.get("YXMC"));
//			obj.put("value", obj.get("ZS"));
//		}
		System.out.println(result);
		return Struts2Utils.list2json(result);
	}
}
