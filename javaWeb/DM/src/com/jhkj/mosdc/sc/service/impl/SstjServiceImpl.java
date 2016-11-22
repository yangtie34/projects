package com.jhkj.mosdc.sc.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.permiss.domain.User;
import com.jhkj.mosdc.permiss.util.UserPermiss;
import com.jhkj.mosdc.sc.service.SstjService;
import com.jhkj.mosdc.sc.util.TreeNode;

public class SstjServiceImpl extends BaseServiceImpl implements SstjService {
	protected Map getSszzjgNode(String zzjgId){
		Map backVal = new HashMap();
		String sql ="Select * from tb_dorm_ccjg where id ="+zzjgId;
		List<Map> result = baseDao.querySqlList(sql);
		if(result.size()!=0){
			backVal.put("id", result.get(0).get("ID"));
			backVal.put("qxm",result.get(0).get("QXM"));
			backVal.put("mc",result.get(0).get("MC"));
		}
		
		return backVal;
	}
	@Override
	public String getSsRzData(String params) {
		User user = UserPermiss.getUser();
		String jxzzjgids = user.getCurrentJxzzjgIds();
		String sql="";
		List<Map> result = new ArrayList<Map>();
		JSONObject json = JSONObject.fromObject(params);
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		if ("".equals(jxzzjgids)||jxzzjgids==null) {
			
			Map node = getSszzjgNode(zzjgId);
				sql  ="select id,mc,qxm,fjd_id,cc,cclx from tb_dorm_ccjg where cclx='LY'";
			if(node.size()!=0){
				String zzjgqxm=node.get("qxm").toString();
				sql ="select id,mc,qxm,fjd_id,cc,cclx from tb_dorm_ccjg where cclx='LY' and qxm like '"+zzjgqxm+"%' order by xh";
			}
			result = baseDao.querySqlList(sql);
		}else{
			if(zzjgId.equals("0")){
				sql ="SELECT ID,MC,FJD_ID,CC FROM TB_JXZZJG"
		  				  + " WHERE SFKY=1 AND CC=1 AND ID IN ("+jxzzjgids+") ORDER BY CC";
					List<Map<String,Object>> xy = baseDao.querySqlList(sql);
					for(Map<String,Object> obj : xy){
						String ssSql =" SELECT SSID ID,NAME MC,SSID QXM FROM (SELECT B.SSID,B.NAME,B.CS,B.FJS,B.CWS,(A.CWS/B.CWS*100) ZSL "+
		    					" FROM ( SELECT L.ID SSID,L.MC NAME ,COUNT(CW.ID) CWS FROM TB_DORM_CCJG L"+
		    					" LEFT JOIN TB_DORM_CCJG C ON L.ID = C.FJD_ID "+
		    					" LEFT JOIN TB_DORM_CCJG FJ ON C.ID = FJ.FJD_ID "+
		    					" LEFT JOIN TB_DORM_CW CW ON CW.FJ_ID = FJ.ID "+
		    					" left join tb_dorm_zy zy on zy.cw_id = cw.id "+
		    					" left join tb_xjda_xjxx xj on xj.id = zy.xs_id "+
		    					" left join tb_xzzzjg bz on bz.id = xj.yx_id "+
		    					" WHERE L.CCLX = 'LY'and bz.id = "+obj.get("ID").toString()+" GROUP BY L.MC,L.ID ORDER BY L.ID ) a "+
		    					" inner join (SELECT L.ID SSID,L.MC NAME,COUNT(DISTINCT(C.ID)) CS , "+
		    					" COUNT(DISTINCT(FJ.ID)) FJS,COUNT(CW.ID) CWS FROM TB_DORM_CCJG L "+
		    					" LEFT JOIN TB_DORM_CCJG C ON L.ID = C.FJD_ID "+
		    					" LEFT JOIN TB_DORM_CCJG FJ ON C.ID = FJ.FJD_ID "+
		    					" LEFT JOIN TB_DORM_CW CW ON CW.FJ_ID = FJ.ID "+
		    					" WHERE L.CCLX = 'LY'GROUP BY L.MC,L.ID ORDER BY L.ID) b on a.ssid =b.ssid) where zsl>=50";
						List<Map<String,Object>> ssResult = baseDao.querySqlList(ssSql);
		    			for(Map<String,Object> ss : ssResult){
		    				result.add(ss);
		    			}
					}
			}else{
				String zzjgPid = json.containsKey("zzjgPid")?json.get("zzjgPid").toString():"0";
				if(zzjgPid.equals("0")){
					String ssSql =" SELECT SSID ID,NAME MC,SSID QXM FROM (SELECT B.SSID,B.NAME,B.CS,B.FJS,B.CWS,(A.CWS/B.CWS*100) ZSL "+
	    					" FROM ( SELECT L.ID SSID,L.MC NAME ,COUNT(CW.ID) CWS FROM TB_DORM_CCJG L"+
	    					" LEFT JOIN TB_DORM_CCJG C ON L.ID = C.FJD_ID "+
	    					" LEFT JOIN TB_DORM_CCJG FJ ON C.ID = FJ.FJD_ID "+
	    					" LEFT JOIN TB_DORM_CW CW ON CW.FJ_ID = FJ.ID "+
	    					" left join tb_dorm_zy zy on zy.cw_id = cw.id "+
	    					" left join tb_xjda_xjxx xj on xj.id = zy.xs_id "+
	    					" left join tb_xzzzjg bz on bz.id = xj.yx_id "+
	    					" WHERE L.CCLX = 'LY'and bz.id = "+zzjgId+" GROUP BY L.MC,L.ID ORDER BY L.ID ) a "+
	    					" inner join (SELECT L.ID SSID,L.MC NAME,COUNT(DISTINCT(C.ID)) CS , "+
	    					" COUNT(DISTINCT(FJ.ID)) FJS,COUNT(CW.ID) CWS FROM TB_DORM_CCJG L "+
	    					" LEFT JOIN TB_DORM_CCJG C ON L.ID = C.FJD_ID "+
	    					" LEFT JOIN TB_DORM_CCJG FJ ON C.ID = FJ.FJD_ID "+
	    					" LEFT JOIN TB_DORM_CW CW ON CW.FJ_ID = FJ.ID "+
	    					" WHERE L.CCLX = 'LY'GROUP BY L.MC,L.ID ORDER BY L.ID) b on a.ssid =b.ssid) where zsl>=50";
					List<Map<String,Object>> ssResult = baseDao.querySqlList(ssSql);
	    			for(Map<String,Object> ss : ssResult){
	    				result.add(ss);
	    			}
				}else{
					sql ="select id,mc,qxm,fjd_id,cc,cclx from tb_dorm_ccjg where cclx='LY' and qxm like '"+zzjgId+"%' order by xh";
					result = baseDao.querySqlList(sql);
				}
			}
		}
		
		
		for(Map temp : result){
			String qxm = temp.get("QXM").toString();
			// 获取楼层数
			String lcSql = "select count(*) as zs from tb_dorm_ccjg where cclx='LC' and qxm like '"+qxm+"%' ";
			// 获取房间数
			String fjSql = "select count(*) as zs from tb_dorm_ccjg where cclx='QS' and qxm like '"+qxm+"%' ";
			// 获取床位数
			String cwSql  ="select count(*) as zs from tb_dorm_ccjg ccjg inner join tb_dorm_cw cw on cw.fj_id = ccjg.id where ccjg.cclx='QS' and ccjg.qxm like '"+qxm+"%'";
			
			int lcs = baseDao.querySqlCount(lcSql);
			int fjs = baseDao.querySqlCount(fjSql);
			int cws = baseDao.querySqlCount(cwSql);
			temp.put("lcs",lcs);
			temp.put("fjs",fjs);
			temp.put("cws",cws);
			
			// 计算入住率
			String rzrsSql ="select count(*) as zs from tb_dorm_zy zyb inner join (select cw.id as cwid from tb_dorm_ccjg ccjg " +
					"inner join tb_dorm_cw cw on cw.fj_id = ccjg.id where ccjg.cclx='QS' and ccjg.qxm like '"+qxm+"%') t on t.cwid = zyb.cw_id";
			int rzrs = baseDao.querySqlCount(rzrsSql);
			float rzlv = cws==0?0l:(float)rzrs/cws*100;
			DecimalFormat df = new DecimalFormat("0.00");
			int kcw = cws-rzrs<0?0:cws-rzrs;
			temp.put("kcw", kcw);
			temp.put("rzl", df.format(rzlv));
			
			String zsbzSql ="select zsbz.zsbzdm_id,count(*) as zs,dm.mc from tb_dorm_ccjg ccjg inner join tb_dorm_cw cw on cw.fj_id = ccjg.id "+
					" inner join tb_dorm_zsbz zsbz on zsbz.cw_id = cw.id  "+
					" left join dm_zxbz.t_zxbz_zsbz dm on dm.wid = zsbz.zsbzdm_id  "+
					"  where ccjg.cclx='QS' and ccjg.qxm like '"+qxm+"%' group by zsbz.zsbzdm_id,dm.mc order by zs";
			List<Map> zsbzList = baseDao.querySqlList(zsbzSql);
			
			// 获得平均住宿标准
			temp.put("zsbz", zsbzList);
			
			
			String view ="select zyb.xs_id,xjxx.xb_id,xjxx.yx_id,zzjg.mc as yx,xb.mc as xbmc,xb.dm as xbdm,xjxx.jdXL_id,whcd.mc as whcd from tb_dorm_zy zyb "+
								" inner join tb_xjda_xjxx xjxx on xjxx.id = zyb.xs_id "+
								" inner join (select cw.* from tb_dorm_ccjg ccjg "+
								" inner join tb_dorm_cw cw on cw.fj_id = ccjg.id " +
								" where ccjg.cclx='QS' and ccjg.qxm like '"+qxm+"%') t on t.id = zyb.cw_id"+
								" left join dm_zxbz.t_zxbz_xb xb on xb.wid = xjxx.xb_id"+
								" left join tb_jxzzjg zzjg on zzjg.id = xjxx.yx_id " +
								" left join dm_zxbz.T_ZXBZ_WHCD whcd on whcd.dm = xjxx.jdxl_id";
			// 获得性别比例
			String xbSql = "SELECT COUNT(*) AS ZS ,XB_ID,XBDM FROM ("+view+") GROUP BY XB_ID,XBDM";
			List<Map> xbResult = baseDao.querySqlList(xbSql);
			
			if(xbResult.size()==0){
				Map manMap = new HashMap();
				manMap.put("count", 0);
				manMap.put("zb","0.00");
				Map woMap = new HashMap();
				woMap.put("count", 0);
				woMap.put("zb","0.00");
				temp.put("nan",manMap);
				temp.put("nv",woMap);
			}
			for(Map xbTemp : xbResult){
				String dm = xbTemp.get("XBDM").toString();
				int count = Integer.parseInt(xbTemp.get("ZS").toString());
				
				if("1".equals(dm)){
					Map manMap = new HashMap();
					manMap.put("count", count);
					float num= rzrs==0?0:(float)count/rzrs*100;
					manMap.put("zb",df.format(num));
					temp.put("nan",manMap);
				}else if("2".equals(dm)){
					Map manMap = new HashMap();
					manMap.put("count", count);
					float num= rzrs==0?0:(float)count/rzrs*100;
					manMap.put("zb",df.format(num));
					temp.put("nv",manMap);
				}
			}
			// 获得院系比例
			String yxSql = "SELECT COUNT(*) AS ZS ,YX_ID,YX FROM ("+view+") GROUP BY YX_ID,YX ORDER BY ZS DESC";
			List<Map> yxResult = baseDao.querySqlList(yxSql);
			if(yxResult.size()==0){
				temp.put("xy","--");
				temp.put("rszb","0.00");
			}else{
				temp.put("xy", yxResult.get(0).get("YX"));
				int count = Integer.parseInt(yxResult.get(0).get("ZS").toString());
				float num= rzrs==0?0:(float)count/rzrs*100;
				temp.put("rszb", df.format(num));
			}
			// 获取宿舍楼类型
			String rylb = "SELECT COUNT(*) AS ZS ,JDXL_ID,WHCD FROM ("+view+") GROUP BY JDXL_ID,WHCD ORDER BY ZS DESC";
			List<Map> rylbResult = baseDao.querySqlList(rylb);
			if(rylbResult.size()==0){
				temp.put("ssllx", "--");
			}else{
				temp.put("ssllx", rylbResult.get(0).get("WHCD"));
			}
		}
		
		return Struts2Utils.list2json(result);
	}

}
