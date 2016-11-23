package com.jhkj.mosdc.jxpg.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.jxpg.service.JxpgDwmbService;
import com.jhkj.mosdc.jxpg.util.JxpgUtil;
import com.jhkj.mosdc.jxpg.util.MapUtils;

public class JxpgDwmbServiceImpl implements JxpgDwmbService {
	
	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map saveBeforeXldnlhxwjg(String params){
		/**
		 * 1 将公共接口的items数据格式化
		 */
		JSONObject obj = JSONObject.fromObject(params);
		Map<String, ArrayList<String[]>> updateM = JxpgUtil.changeMapToObject(obj.getString("items"));
		/**
		 * 2 找到更新的ID， 先将数据放入总数据中，然后再校验
		 */
		String table = "TB_JXPG_DWMB_XLDNLHXWJG";
		List<Map> list = baseDao.querySqlList("select * from " +table+ "  where xn = '" +obj.getString("xn")+ "'");
		JxpgUtil.mergeMap2List(updateM, list);
		/**
		 * 3 再校验
		 */
		Map resultM = new HashMap();
		List<String> errorL = new ArrayList<String>();
		String dm = null;
		long ZJ_F = 0L,
			  bs  = 0L,
			 ss   = 0L,
			 xs   = 0L,
			 wxw  = 0L,
			 n35   = 0L,
			 n3645 = 0L,
			 n4655 = 0L,
			 n56   = 0L;
		boolean b = false;
		for(Map<String, String> map : list){
			dm = map.get("DM");
			if(null != dm && "SL".equals(dm)){
				bs = MapUtils.getLong(map, "BS_F");
				 ss = MapUtils.getLong(map, "SS_F");
				 xs = MapUtils.getLong(map, "XS_F");
				 wxw= MapUtils.getLong(map, "WXW_F");
				 n35   = MapUtils.getLong(map, "N35_F");
				 n3645 = MapUtils.getLong(map, "N36_45_F");
				 n4655 = MapUtils.getLong(map, "N46_55_F");
				 n56   = MapUtils.getLong(map, "N56_F");
				long zj1 = bs + ss + xs + wxw,
					 zj2 = n35 + n3645 + n4655 + n56;
				if(zj1 != zj2){
					b = false;
					errorL.add("学位"+zj1+"人，年龄"+zj2+"人；总人数不相同");
				}else{
					b = true;
					ZJ_F = zj1;
				}
			}
		}
		/**
		 * 4 更新数据
		 */
		if(b){
			for(Map<String, String> map : list){
				dm = map.get("DM");
				if(null != dm){
					if("SL".equals(dm)){
						map.put("ZJ_F", String.valueOf(ZJ_F));
					}
					if("BL".equals(dm)){
						map.put("BS_F", JxpgUtil.getPercent(bs, ZJ_F));
						map.put("SS_F", JxpgUtil.getPercent(ss, ZJ_F));
						map.put("XS_F", JxpgUtil.getPercent(xs, ZJ_F));
						map.put("WXW_F", JxpgUtil.getPercent(wxw, ZJ_F));
						map.put("N35_F", JxpgUtil.getPercent(n35, ZJ_F));
						map.put("N36_45_F", JxpgUtil.getPercent(n3645, ZJ_F));
						map.put("N46_55_F", JxpgUtil.getPercent(n4655, ZJ_F));
						map.put("N56_F", JxpgUtil.getPercent(n56, ZJ_F));
						//保存总数比例
						map.put("ZJ_F", "100");
					}
				}
			}
			// 转换 items
			resultM.put("items", JxpgUtil.changeObjectAttrToMap(list));
		}else{
			String str = "";
			for(String s : errorL){
				if(!"".equals(str)) s += ",";
				str += s;
			}
			resultM.put("error_message", str);
		}
		resultM.put("success", b);
		return resultM;
	}
	

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map saveBeforeXjjxglryjg(String params){
		/**
		 * 1 将公共接口的items数据格式化
		 */
		JSONObject obj = JSONObject.fromObject(params);
		Map<String, ArrayList<String[]>> updateM = JxpgUtil.changeMapToObject(obj.getString("items"));
		/**
		 * 2 找到更新的ID， 先将数据放入总数据中，然后再校验
		 */
		String table = "TB_JXPG_DWMB_XJJXGLRYJG";
		List<Map> list = baseDao.querySqlList("select * from " +table+ "  where xn = '" +obj.getString("xn")+ "'");
		JxpgUtil.mergeMap2List(updateM, list);
		/**
		 * 3 再校验
		 */
		Map resultM = new HashMap();
		List<String> errorL = new ArrayList<String>();
		String dm = null;
		long ZJ_F = 0L,
			 ZGJ  = 0L,
			 FGJ  = 0L,
			 ZJZC = 0L,
			 CJZC = 0L,
			 WZC  = 0L,
			  BS  = 0L,
			 SS   = 0L,
			 XS   = 0L,
			 WXW  = 0L,
			 N35   = 0L,
			 N3645 = 0L,
			 N4655 = 0L,
			 N56   = 0L;
		boolean b = false;
		for(Map<String, String> map : list){
			dm = map.get("DM");
			if(null != dm && "SL".equals(dm)){
				ZGJ  = MapUtils.getLong(map, "ZGJ_F");
				FGJ  = MapUtils.getLong(map, "FGJ_F");
				ZJZC = MapUtils.getLong(map, "ZJZC_F");
				CJZC = MapUtils.getLong(map, "CJZC_F");
				WZC  = MapUtils.getLong(map, "WZC_F");
				BS = MapUtils.getLong(map, "BS_F");
				SS = MapUtils.getLong(map, "SS_F");
				XS = MapUtils.getLong(map, "XS_F");
				WXW= MapUtils.getLong(map, "WXW_F");
				N35   = MapUtils.getLong(map, "N35_F");
				N3645 = MapUtils.getLong(map, "N36_45_F");
				N4655 = MapUtils.getLong(map, "N46_55_F");
				N56   = MapUtils.getLong(map, "N56_F");
				long zj1 = ZGJ + FGJ + ZJZC + CJZC + WZC,
					 zj2 = BS + SS + XS + WXW,
					 zj3 = N35 + N3645 + N4655 + N56;
				if(zj1 != zj2 || zj2 != zj3){
					b = false;
					errorL.add("职称、最高学历、年龄的总人数不相同");
				}else{
					b = true;
					ZJ_F = zj1;
				}
			}
		}
		/**
		 * 4 更新数据
		 */
		if(b){
			for(Map<String, String> map : list){
				dm = map.get("DM");
				if(null != dm){
					if("SL".equals(dm)){
						map.put("ZJ_F", String.valueOf(ZJ_F));
					}
					if("BL".equals(dm)){
						map.put("ZGJ_F", JxpgUtil.getPercent(ZGJ, ZJ_F));
						map.put("FGJ_F", JxpgUtil.getPercent(FGJ, ZJ_F));
						map.put("ZJZC_F", JxpgUtil.getPercent(ZJZC, ZJ_F));
						map.put("CJZC_F", JxpgUtil.getPercent(CJZC, ZJ_F));
						map.put("WZC_F", JxpgUtil.getPercent(WZC, ZJ_F));
						map.put("BS_F", JxpgUtil.getPercent(BS, ZJ_F));
						map.put("SS_F", JxpgUtil.getPercent(SS, ZJ_F));
						map.put("XS_F", JxpgUtil.getPercent(XS, ZJ_F));
						map.put("WXW_F", JxpgUtil.getPercent(WXW, ZJ_F));
						map.put("N35_F", JxpgUtil.getPercent(N35, ZJ_F));
						map.put("N36_45_F", JxpgUtil.getPercent(N3645, ZJ_F));
						map.put("N46_55_F", JxpgUtil.getPercent(N4655, ZJ_F));
						map.put("N56_F", JxpgUtil.getPercent(N56, ZJ_F));
						map.put("ZJ_F", "100");
					}
				}
			}
			// 转换 items
			resultM.put("items", JxpgUtil.changeObjectAttrToMap(list));
		}else{
			String str = "";
			for(String s : errorL){
				if(!"".equals(str)) s += ",";
				str += s;
			}
			resultM.put("error_message", str);
		}
		resultM.put("success", b);
		return resultM;
	}
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map saveBeforeJyjxggycg(String params){
		/**
		 * 1 将公共接口的items数据格式化
		 */
		JSONObject obj = JSONObject.fromObject(params);
		Map<String, ArrayList<String[]>> updateM = JxpgUtil.changeMapToObject(obj.getString("items"));
		/**
		 * 2 找到更新的ID， 先将数据放入总数据中，然后再校验
		 */
		String table = "TB_JXPG_DWMB_JYJXGGYCG";
		List<Map> list = baseDao.querySqlList("select * from " +table+ "  where xn = '" +obj.getString("xn")+ "'");
		JxpgUtil.mergeMap2List(updateM, list);
		/**
		 * 3 再校验
		 */
		Map resultM = new HashMap();
		List<String> errorL = new ArrayList<String>();
		String dm = null;
		double A  = 0L,
			 A1 = 0L,
			 A2 = 0L,
			 B  = 0L,
			 B1 = 0L,
			 B2 = 0L,
			 SL_F = 0L;
		for(Map<String, String> map : list){
			SL_F  = MapUtils.getDouble(map, "SL_F");
			dm = map.get("DM");
			if("A".equals(dm)){
				A = SL_F;
			}else if("A1".equals(dm)){
				A1 = SL_F;
			}else if("A2".equals(dm)){
				A2 = SL_F;
			}else if("B".equals(dm)){
				B = SL_F;
			}else if("B1".equals(dm)){
				B1 = SL_F;
			}else if("B2".equals(dm)){
				B2 = SL_F;
			}
		}
		boolean b = true;
		if(A < A1+A2){
			b = false;
			errorL.add("“教学成果奖（项）”总数小于各项之和");
		}
		if(B < B1+B2){
			b = false;
			errorL.add("“教育学研究与改革项目项目”总数小于各项之和");
		}
		/**
		 * 4 更新数据
		 */
		if(b){
			// 转换 items
			resultM.put("items", JxpgUtil.changeObjectAttrToMap(list));
		}else{
			String str = "";
			for(String s : errorL){
				if(!"".equals(str)) s += ",";
				str += s;
			}
			resultM.put("error_message", str);
		}
		resultM.put("success", b);
		return resultM;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map saveBeforeZybjgl(String params){
		/**
		 * 1 将公共接口的items数据格式化
		 */
		JSONObject obj = JSONObject.fromObject(params);
		Map<String, ArrayList<String[]>> updateM = JxpgUtil.changeMapToObject(obj.getString("items"));
		/**
		 * 2 找到更新的ID， 先将数据放入总数据中，然后再校验
		 */
		String table = "TB_JXPG_DWMB_ZYBJGL";
		List<Map> list = baseDao.querySqlList("select * from " +table+ "  where xn = '" +obj.getString("xn")+ "'");
		JxpgUtil.mergeMap2List(updateM, list);
		/**
		 * 3 获取数据/再校验
		 */
		Map resultM = new HashMap();
		List<String> errorL = new ArrayList<String>();
		String dm = null;
		long ZJ_F  = 0L,
			 JJX_F = 0L,
			 FX_F  = 0L,
			 JYX_F = 0L,
			 WX_F  = 0L,
			 LSX_F = 0L,
			 LX_F  = 0L,
			 GX_F  = 0L,
			 GLX_F = 0L,
			 YSX_F = 0L;
		boolean b = true;
		for(Map<String, String> map : list){
			dm = map.get("DM");
			if(dm == null || "BL".equals(dm)){
				continue;
			}
			long JJX_F1 = MapUtils.getLong(map, "JJX_F"),
				FX_F1  = MapUtils.getLong(map, "FX_F"),
				JYX_F1 = MapUtils.getLong(map, "JYX_F"),
				WX_F1  = MapUtils.getLong(map, "WX_F"),
				LSX_F1 = MapUtils.getLong(map, "LSX_F"),
				LX_F1  = MapUtils.getLong(map, "LX_F"),
				GX_F1  = MapUtils.getLong(map, "GX_F"),
				GLX_F1 = MapUtils.getLong(map, "GLX_F"),
				YSX_F1 = MapUtils.getLong(map, "YSX_F"),
				// 得到总数
				ZJ_F1 = JJX_F1 + FX_F1 + JYX_F1 + WX_F1 + LSX_F1 + LX_F1 + GX_F1 + GLX_F1 + YSX_F1;
				// 直接更新总数 {本来应该再第4个方法，根据表格实际需求、为了较少代码量}
				map.put("ZJ_F", ZJ_F1+"");
			if("ZJ".equals(dm)){
				JJX_F  = JJX_F1;
				 FX_F  = FX_F1;
				 JYX_F = JYX_F1;
				 WX_F  = WX_F1;
				 LSX_F = LSX_F1;
				 LX_F  = LX_F1;
				 GX_F  = GX_F1;
				 GLX_F = GLX_F1;
				 YSX_F = YSX_F1;
				 ZJ_F  = ZJ_F1; //总量
			}
		}
		/**
		 * 4 更新数据
		 */
		if(b){
			for(Map<String, String> map : list){
				dm = map.get("DM");
				if(null != dm && "BL".equals(dm)){
					map.put("JJX_F", JxpgUtil.getPercent(JJX_F, ZJ_F));
					map.put("FX_F", JxpgUtil.getPercent(FX_F, ZJ_F));
					map.put("JYX_F", JxpgUtil.getPercent(JYX_F, ZJ_F));
					map.put("WX_F", JxpgUtil.getPercent(WX_F, ZJ_F));
					map.put("LSX_F", JxpgUtil.getPercent(LSX_F, ZJ_F));
					map.put("LX_F", JxpgUtil.getPercent(LX_F, ZJ_F));
					map.put("GX_F", JxpgUtil.getPercent(GX_F, ZJ_F));
					map.put("GLX_F", JxpgUtil.getPercent(GLX_F, ZJ_F));
					map.put("YSX_F", JxpgUtil.getPercent(YSX_F, ZJ_F));
					//保存总数比例
					map.put("ZJ_F", "100");
				}
			}
			// 转换 items
			resultM.put("items", JxpgUtil.changeObjectAttrToMap(list));
		}else{
			String str = "";
			for(String s : errorL){
				if(!"".equals(str)) s += ",";
				str += s;
			}
			resultM.put("error_message", str);
		}
		resultM.put("success", b);
		return resultM;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}