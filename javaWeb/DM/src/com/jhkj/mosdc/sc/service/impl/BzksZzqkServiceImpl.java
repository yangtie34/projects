package com.jhkj.mosdc.sc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.service.BaseService;
import com.jhkj.mosdc.framework.util.StringUtils;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.sc.service.BzksZzqkService;

/**   
* @Description: TODO 本专科生资助情况
* @author Sunwg  
* @date 2014-8-18 下午2:35:36   
*/
public class BzksZzqkServiceImpl implements BzksZzqkService
{
	private BaseService baseService;
	
	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}
	
	/** 
	* @Title: queryJxjInfoByYear 
	* @Description: TODO 查询年度奖学金信息
	* @return String
	*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String queryJxjInfoByYear(String json){
		JSONObject params = JSONObject.fromObject(json);
		String xn = params.getString("xn");
		String qxm = params.containsKey("qxm")?params.get("qxm").toString():"";
		String str2 = " AND XJXX.ZY_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+qxm+"%')";
		
		String zje = "select nvl(sum(t.jxje),0) num from t_BZKS_JXJ t  left join tb_xjda_xjxx xjxx on xjxx.xh = t.xh where t.xn = ''{0}'' {1}";
		zje = StringUtils.format(zje, xn,str2);
		String zrs = "select count(t.wid) num from t_BZKS_JXJ t  left join tb_xjda_xjxx xjxx on xjxx.xh = t.xh where t.xn = ''{0}'' {1} ";
		zrs = StringUtils.format(zrs, xn,str2);
		String types = "select t.jxjmc name,count(t.wid) num,avg(t.jxje) value from t_BZKS_JXJ t  left join tb_xjda_xjxx xjxx on xjxx.xh = t.xh where t.xn = ''{0}'' {1} group by t.jxjmc ";
		types = StringUtils.format(types, xn,str2);
		Map result = new HashMap();
		result.put("zje", baseService.queryListMapInLowerKeyBySql(zje).get(0).get("num"));
		result.put("zrs", baseService.queryListMapInLowerKeyBySql(zrs).get(0).get("num"));
		result.put("types",baseService.queryListMapInLowerKeyBySql(types));
		return Struts2Utils.map2json(result);
	}
	
	/** 
	* @Title: queryJxjzeByYear 
	* @Description: TODO 查询年度各院系奖学金总额
	* @return String
	*/
	public String queryJxjzeByYear(String json) {
		JSONObject params = JSONObject.fromObject(json);
		String xn = params.getString("xn");
		String cclx = params.getString("cclx");
		String qxm = params.getString("qxm");
		String str2 = " AND t.QXM LIKE '"+qxm+"%' ";
		String sql = "select t.mc field,NVL(sum(jxj.jxje),0) value,''奖学金总额'' name from tb_jxzzjg t " +
				" left join tb_xjda_xjxx xs on t.id = xs.yx_id " +
				" left join t_bzks_jxj jxj on xs.xh = jxj.xh and jxj.xn = ''{0}''" +
				" where t.sfky = 1 and t.cc = 1 group by t.mc";
		sql = StringUtils.format(sql, xn);
		if(!"XX".equals(cclx)){
			sql = "select t.mc field,NVL(sum(jxj.jxje),0) value,''奖学金总额'' name from tb_jxzzjg t " +
					" left join tb_xjda_xjxx xjxx on t.id = xjxx.zy_id " +
					" left join t_bzks_jxj jxj on xjxx.xh = jxj.xh and jxj.xn = ''{0}''" +
					" where t.sfky = 1 and t.cc=2 {1} group by t.mc";
			
			sql = StringUtils.format(sql, xn,str2);
		}
		
		
		return Struts2Utils.list2json(baseService.queryListMapInLowerKeyBySql(sql));
	}
	
	/** 
	* @Title: queryJxjhdrsByYear 
	* @Description: TODO 查询年度各院系奖学金获得人数
	* @return String
	*/
	public String queryJxjhdrsByYear(String json) {
		JSONObject params = JSONObject.fromObject(json);
		String xn = params.getString("xn");
		String cclx = params.getString("cclx");
		String qxm = params.getString("qxm");
		String str2 = " AND t.QXM LIKE '"+qxm+"%' ";
		String sql = "select t.mc field,count(jxj.wid) value,''奖学金获得人数'' name from tb_jxzzjg t " +
				" left join tb_xjda_xjxx xs on t.id = xs.yx_id " +
				" left join t_bzks_jxj jxj on xs.xh = jxj.xh and jxj.xn = ''{0}''" +
				" where  t.sfky = 1 and t.cc = 1 group by t.mc";
		sql = StringUtils.format(sql, xn);
		if(!"XX".equals(cclx)){
			sql = "select t.mc field,count(jxj.wid) value,''奖学金获得人数'' name from tb_jxzzjg t " +
					" left join tb_xjda_xjxx xs on t.id = xs.zy_id " +
					" left join t_bzks_jxj jxj on xs.xh = jxj.xh and jxj.xn = ''{0}''" +
					" where  t.sfky = 1 and t.cc = 2 {1} group by t.mc";
			
			sql = StringUtils.format(sql, xn,str2);
		}
		return Struts2Utils.list2json(baseService.queryListMapInLowerKeyBySql(sql));
	}
	
	/** 
	* @Title: queryJxjalbhdrsByYear 
	* @Description: TODO 查询年度各院系按类别分奖学金获得情况
	* @return String
	*/

	public String queryJxjalbhdrsByYear(String json) {
		JSONObject params = JSONObject.fromObject(json);
		String xn = params.getString("xn");
		String cclx = params.getString("cclx");
		String qxm = params.getString("qxm");
		String str2 = " AND t.QXM LIKE '"+qxm+"%' ";
		String sql = "select t.mc field,count(jxj.wid) value, jxj.jxjdj name from tb_jxzzjg t " +
				" left join tb_xjda_xjxx xs on t.id = xs.yx_id " +
				" inner join t_bzks_jxj jxj on xs.xh = jxj.xh and jxj.xn = ''{0}''" +
				" where  t.sfky = 1 and t.cc = 1 group by t.mc,jxj.jxjdj";
		sql = StringUtils.format(sql, xn);
		if(!"XX".equals(cclx)){
			sql = "select t.mc field,count(jxj.wid) value, jxj.jxjdj name from tb_jxzzjg t " +
					" left join tb_xjda_xjxx xs on t.id = xs.zy_id " +
					" inner join t_bzks_jxj jxj on xs.xh = jxj.xh and jxj.xn = ''{0}''" +
					" where  t.sfky = 1 and t.cc = 2 {1} group by t.mc,jxj.jxjdj";
			
			sql = StringUtils.format(sql, xn,str2);
		}
		return Struts2Utils.list2json(baseService.queryListMapInLowerKeyBySql(sql));
	}
	
	/** 
	* @Title: queryZxjInfoByYear 
	* @Description: TODO 查询年度助学金信息
	* @return String
	*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String queryZxjInfoByYear(String json){
		JSONObject params = JSONObject.fromObject(json);
		String xn = params.getString("xn");
		String qxm = params.containsKey("qxm")?params.get("qxm").toString():"";
		String str2 = " AND XJXX.ZY_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+qxm+"%')";
		
		String zje = "select nvl(sum(t.zxje),0) num from t_bzks_zxj t left join tb_xjda_xjxx xjxx on xjxx.xh = t.xh  where t.xn = ''{0}'' {1} ";
		zje = StringUtils.format(zje, xn,str2);
		String zrs = "select count(t.wid) num from t_bzks_zxj t  left join tb_xjda_xjxx xjxx on xjxx.xh = t.xh where t.xn = ''{0}'' {1} ";
		zrs = StringUtils.format(zrs, xn,str2);
		String types = "select t.zxjmc name,count(t.wid) num,avg(t.zxje) value from t_BZKS_ZXJ t  left join tb_xjda_xjxx xjxx on xjxx.xh = t.xh where t.xn = ''{0}'' {1} group by t.zxjmc ";
		types = StringUtils.format(types, xn,str2);
		Map result = new HashMap();
		result.put("zje", baseService.queryListMapInLowerKeyBySql(zje).get(0).get("num"));
		result.put("zrs", baseService.queryListMapInLowerKeyBySql(zrs).get(0).get("num"));
		result.put("types",baseService.queryListMapInLowerKeyBySql(types));
		return Struts2Utils.map2json(result);
	}
	
	/** 
	* @Title: queryZxjzeByYear 
	* @Description: TODO 查询年度各院系助学金总额
	* @return String
	*/
	public String queryZxjzeByYear(String json){
		JSONObject params = JSONObject.fromObject(json);
		String xn = params.getString("xn");
		String cclx = params.getString("cclx");
		String qxm = params.getString("qxm");
		String str2 = " AND t.QXM LIKE '"+qxm+"%' ";
		String sql = "select t.mc field,count(zxj.wid) value,''助学金获得人数'' name from tb_jxzzjg t " +
				" left join tb_xjda_xjxx xs on t.id = xs.yx_id " +
				" left join t_bzks_zxj zxj on xs.xh = zxj.xh and zxj.xn = ''{0}''" +
				" where  t.sfky = 1 and t.cc = 1 group by t.mc";
		sql = StringUtils.format(sql, xn);
		if(!"XX".equals(cclx)){
			sql ="select t.mc field,count(zxj.wid) value,''助学金获得人数'' name from tb_jxzzjg t " +
					" left join tb_xjda_xjxx xs on t.id = xs.zy_id " +
					" left join t_bzks_zxj zxj on xs.xh = zxj.xh and zxj.xn = ''{0}''" +
					" where t.sfky = 1 and  t.cc = 2 {1} group by t.mc";
			
			sql = StringUtils.format(sql, xn,str2);
		}
		return Struts2Utils.list2json(baseService.queryListMapInLowerKeyBySql(sql));
	};
	
	/** 
	* @Title: queryZxjhdrsByYear 
	* @Description: TODO 查询年度各院系助学金获得人数
	* @return String
	*/
	public String queryZxjhdrsByYear(String json){
		JSONObject params = JSONObject.fromObject(json);
		String xn = params.getString("xn");
		String cclx = params.getString("cclx");
		String qxm = params.getString("qxm");
		String str2 = " AND t.QXM LIKE '"+qxm+"%' ";
		String sql = "select t.mc field,count(zxj.wid) value,''助学金获得人数'' name from tb_jxzzjg t " +
				" left join tb_xjda_xjxx xs on t.id = xs.yx_id " +
				" left join t_bzks_zxj zxj on xs.xh = zxj.xh and zxj.xn = ''{0}''" +
				" where  t.sfky = 1 and t.cc = 1 group by t.mc";
		sql = StringUtils.format(sql, xn);
		if(!"XX".equals(cclx)){
			sql = "select t.mc field,count(zxj.wid) value,''助学金获得人数'' name from tb_jxzzjg t " +
					" left join tb_xjda_xjxx xs on t.id = xs.zy_id " +
					" left join t_bzks_zxj zxj on xs.xh = zxj.xh and zxj.xn = ''{0}''" +
					" where  t.sfky = 1 and t.cc = 2 {1} group by t.mc";
			
			sql = StringUtils.format(sql, xn,str2);
		}
		return Struts2Utils.list2json(baseService.queryListMapInLowerKeyBySql(sql));
	};
	
	/** 
	* @Title: queryZxjalbhdrsByYear 
	* @Description: TODO 查询年度各院系按类别分助学金获得情况
	* @return String
	*/
	public String queryZxjalbhdrsByYear(String json){
		JSONObject params = JSONObject.fromObject(json);
		String xn = params.getString("xn");
		String cclx = params.getString("cclx");
		String qxm = params.getString("qxm");
		String str2 = " AND t.QXM LIKE '"+qxm+"%' ";
		String sql = "select t.mc field,count(zxj.wid) value,zxj.zxjdj name from tb_jxzzjg t " +
				" left join tb_xjda_xjxx xs on t.id = xs.yx_id " +
				" inner join t_bzks_zxj zxj on xs.xh = zxj.xh and zxj.xn = ''{0}''" +
				" where  t.sfky = 1 and t.cc = 1 group by t.mc,zxj.zxjdj";
		sql = StringUtils.format(sql, xn);
		if(!"XX".equals(cclx)){
			sql = "select t.mc field,count(zxj.wid) value, zxj.zxjdj name from tb_jxzzjg t " +
					" left join tb_xjda_xjxx xs on t.id = xs.zy_id " +
					" inner join t_bzks_zxj zxj on xs.xh = zxj.xh and zxj.xn = ''{0}''" +
					" where t.sfky = 1 and  t.cc = 2 {1} group by t.mc,zxj.zxjdj";
			
			sql = StringUtils.format(sql, xn,str2);
		}
		return Struts2Utils.list2json(baseService.queryListMapInLowerKeyBySql(sql));
	};
	
	/** 
	* @Title: queryXfjmqkByYear 
	* @Description: TODO 查询年度各院系学费减免申请和通过人数
	* @return String
	*/
	public String queryXfjmqkByYear(String json){
		return null;
	};

	/** 
	* @Title: queryZxdkInfoByYear 
	* @Description: TODO 查询年度助学贷款情况
	* @return String
	*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String queryZxdkInfoByYear(String json){
		JSONObject params = JSONObject.fromObject(json);
		String xn = params.getString("xn");
		String qxm = params.containsKey("qxm")?params.get("qxm").toString():"";
		String str2 = " AND XJXX.ZY_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+qxm+"%')";
		
		String zje = "select nvl(sum(t.zje),0) num from t_bzks_dk t  left join tb_xjda_xjxx xjxx on xjxx.xh = t.xh  where t.sqxn = ''{0}'' {1} ";
		zje = StringUtils.format(zje, xn,str2);
		String zrs = "select count(t.wid) num from t_bzks_dk t  left join tb_xjda_xjxx xjxx on xjxx.xh = t.xh  where t.sqxn = ''{0}'' {1} ";
		zrs = StringUtils.format(zrs, xn,str2);
		String types = "select  count(dk.wid) num,decode(dk.XFZJE,null,''学费'',decode(dk.SHFZJE,null,decode(dk.ZSFZJE,null,''学费'',''学费+住宿费''),decode(dk.ZSFZJE,null,''学费+生活费'',''学费+住宿费+生活费''))) name" +
				" from t_bzks_dk dk  left join tb_xjda_xjxx xjxx on xjxx.xh = dk.xh   where dk.sqxn = ''{0}'' {1} group by decode(dk.XFZJE,null,''学费'',decode(dk.SHFZJE,null,decode(dk.ZSFZJE,null,''学费'',''学费+住宿费''),decode(dk.ZSFZJE,null,''学费+生活费'',''学费+住宿费+生活费'')))";
		types = StringUtils.format(types, xn,str2);
		Map result = new HashMap();
		result.put("zje", baseService.queryListMapInLowerKeyBySql(zje).get(0).get("num"));
		result.put("zrs", baseService.queryListMapInLowerKeyBySql(zrs).get(0).get("num"));
		result.put("types",baseService.queryListMapInLowerKeyBySql(types));
		return Struts2Utils.map2json(result);
	}
	
	/** 
	* @Title: queryZxdksqqkByYear 
	* @Description: TODO 查询年度各院系助学贷款申请情况
	* @return Strign
	*/
	public String queryZxdksqqkByYear(String json){
		return null;
	};
	
	/** 
	* @Title: queryZxdkalbhdrsByYear 
	* @Description: TODO 查询年度各院系助学贷款按类别获得人数
	* @return String
	*/
	public String queryZxdkalbhdrsByYear(String json){
		JSONObject params = JSONObject.fromObject(json);
		String xn = params.getString("xn");
		String cclx = params.getString("cclx");
		String qxm = params.getString("qxm");
		String str2 = " AND t.QXM LIKE '"+qxm+"%' ";
		
		String sql = "select t.mc field,count(dk.wid) value,decode(dk.XFZJE,null,''学费'',decode(dk.SHFZJE,null,decode(dk.ZSFZJE,null,''学费'',''学费+住宿费''),decode(dk.ZSFZJE,null,''学费+生活费'',''学费+住宿费+生活费''))) name from tb_jxzzjg t " +
				" left join tb_xjda_xjxx xs on t.id = xs.yx_id  left join t_bzks_dk dk on xs.xh = dk.xh  and dk.sqxn = ''{0}''  where   t.sfky = 1 and t.cc = 1 " +
				"group by t.mc,decode(dk.XFZJE,null,''学费'',decode(dk.SHFZJE,null,decode(dk.ZSFZJE,null,''学费'',''学费+住宿费''),decode(dk.ZSFZJE,null,''学费+生活费'',''学费+住宿费+生活费'')))";
		sql = StringUtils.format(sql, xn);
		if(!"XX".equals(cclx)){
			sql = "select t.mc field,count(dk.wid) value,decode(dk.XFZJE,null,''学费'',decode(dk.SHFZJE,null,decode(dk.ZSFZJE,null,''学费'',''学费+住宿费''),decode(dk.ZSFZJE,null,''学费+生活费'',''学费+住宿费+生活费''))) name from tb_jxzzjg t " +
					" left join tb_xjda_xjxx xs on t.id = xs.zy_id  left join t_bzks_dk dk on xs.xh = dk.xh  and dk.sqxn = ''{0}''  where   t.sfky = 1 and t.cc = 2 {1} " +
					"group by t.mc,decode(dk.XFZJE,null,''学费'',decode(dk.SHFZJE,null,decode(dk.ZSFZJE,null,''学费'',''学费+住宿费''),decode(dk.ZSFZJE,null,''学费+生活费'',''学费+住宿费+生活费'')))";
			
			sql = StringUtils.format(sql, xn,str2);
		}
		return Struts2Utils.list2json(baseService.queryListMapInLowerKeyBySql(sql));
	}
}
