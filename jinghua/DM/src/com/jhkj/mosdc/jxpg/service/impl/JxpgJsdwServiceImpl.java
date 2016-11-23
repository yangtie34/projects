package com.jhkj.mosdc.jxpg.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.jxpg.service.JxpgJsdwService;
import com.jhkj.mosdc.jxpg.util.JxpgUtil;
import com.jhkj.mosdc.jxpg.util.MapUtils;

public class JxpgJsdwServiceImpl implements JxpgJsdwService{
	
	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map saveBeforeXxssbjjsqk(String params){
		/**
		 * 1 将公共接口的items数据格式化
		 */
		JSONObject obj = JSONObject.fromObject(params);
		Map<String, ArrayList<String[]>> updateM = JxpgUtil.changeMapToObject(obj.getString("items"));
		/**
		 * 2 找到更新的ID， 先将数据放入总数据中，然后再校验
		 */
		String table = "TB_JXPG_JSDW_XXSSBJJSQK";
		List<Map> list = baseDao.querySqlList("select * from " +table+ "  where xn = '" +obj.getString("xn")+ "'");
		JxpgUtil.mergeMap2List(updateM, list);
		/**
		 * 3 再校验
		 * A > (A1 + A2 + A3 + A4 + A5)
		 * B > (B1)
		 */
		Map resultM = new HashMap();
		List<String> errorL = new ArrayList<String>();
		String dm = null;
		boolean b = true;
		long A  = 0L,
			 A1 = 0L,
			 A2 = 0L,
			 A3 = 0L,
			 A4 = 0L,
			 A5 = 0L,
			 B  = 0L,
			 B1 = 0L;
		for(Map<String, String> map : list){
			dm = map.get("DM");
			if(null != dm){
				if("A".equals(dm)) A = MapUtils.getLong(map, "SL_F");
				else if("A1".equals(dm)) A1 = MapUtils.getLong(map, "SL_F");
				else if("A2".equals(dm)) A2 = MapUtils.getLong(map, "SL_F");
				else if("A3".equals(dm)) A3 = MapUtils.getLong(map, "SL_F");
				else if("A4".equals(dm)) A4 = MapUtils.getLong(map, "SL_F");
				else if("A5".equals(dm)) A5 = MapUtils.getLong(map, "SL_F");
				else if("B".equals(dm))  B  = MapUtils.getLong(map, "SL_F");
				else if("B1".equals(dm)) B1 = MapUtils.getLong(map, "SL_F");
			}
		}
		if(A < A1){ errorL.add("专任教师总数小于“硕士学位”教师数"); b = false; }
		if(A < A2){ errorL.add("专任教师总数小于“博士学位”教师数"); b = false; }
		if(A < A3){ errorL.add("专任教师总数小于“双师型”教师数"); b = false; }
		if(A < A4){ errorL.add("专任教师总数小于“具有工程背景”教师数"); b = false; }
		if(A < A5){ errorL.add("专任教师总数小于“具有行业背景”教师数"); b = false; }
		if(B < B1){ errorL.add("外聘教师总数小于“境外教师”教师数"); b = false; }
		/**
		 * 4 填值
		 */
		if(b){
			for(Map<String, String> map : list){
				dm = map.get("DM");
				if(null != dm){
					if("A1".equals(dm)) map.put("BL_F", JxpgUtil.getPercent(A1, A));
					else if("A2".equals(dm)) map.put("BL_F", JxpgUtil.getPercent(A2, A));
					else if("A3".equals(dm)) map.put("BL_F", JxpgUtil.getPercent(A3, A));
					else if("A4".equals(dm)) map.put("BL_F", JxpgUtil.getPercent(A4, A));
					else if("A5".equals(dm)) map.put("BL_F", JxpgUtil.getPercent(A5, A));
					else if("B1".equals(dm)) map.put("BL_F", JxpgUtil.getPercent(B1, B));
				}
			}
			// 转换 items
			resultM.put("items", JxpgUtil.changeObjectAttrToMap(list));
		}else{
			resultM.put("error_message", JxpgUtil.listSpilt(errorL));
		}
		resultM.put("success", b);
		return resultM;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map saveBeforeJsdwjg(String params){
		/**
		 * 1 将公共接口的items数据格式化
		 */
		JSONObject obj = JSONObject.fromObject(params);
		Map<String, ArrayList<String[]>> updateM = JxpgUtil.changeMapToObject(obj.getString("items"));
		/**
		 * 2 找到更新的ID， 先将数据放入总数据中，然后再校验
		 */
		String table = "TB_JXPG_JSDW_JSDWJG";
		List<Map> list = baseDao.querySqlList("select * from " +table+ "  where xn = '" +obj.getString("xn")+ "'");
		JxpgUtil.mergeMap2List(updateM, list);
		/**
		 * 3 再校验
		 * GROUPS == A   DM='SL'/'BL'
		 * GROUPS == B   DM='SL'/'BL'
		 */
		Map resultM = new HashMap();
		List<String> errorL = new ArrayList<String>();
		boolean b = true;
		String[][] groupAry = new String[][]{{"A","专任教师："}, {"B","外聘教师："}};
		for(String[] ary : groupAry){
			String group = ary[0], _group = null, dm = null;
			long ZJ  = 0L,
				 ZJ1 = 0L,
				 ZJ2 = 0L,
				 ZJ3 = 0L,
				 ZJ4 = 0L;
			long JS  = 0L,
				 FJS = 0L,
				 JS1 = 0L,
				 ZJZC= 0L,
				 QTZGJ = 0L,
				 QTFGJ = 0L,
				 QTZJ  = 0L,
				 QTCJ  = 0L,
				 WPJ   = 0L,
				 BS   = 0L,
				 SS   = 0L,
				 XS   = 0L,
				 WXW  = 0L,
				 N35     = 0L,
				 N36_45  = 0L,
				 N46_55  = 0L,
				 N56     = 0L,
				 BX  = 0L,
				 JN  = 0L,
				 JW  = 0L;
			// GROUPS == A | B
			// 先处理数量
			for(Map<String, String> map : list){
				_group = map.get("GROUPS");
				dm = map.get("DM");
				if(null != _group && group.equals(_group) && null != dm && "SL".equals(dm)){
					// 职称
					JS    = MapUtils.getLong(map, "JS_F");
					FJS   = MapUtils.getLong(map, "FJS_F");
					JS1   = MapUtils.getLong(map, "JS1_F");
					ZJZC  = MapUtils.getLong(map, "ZJZC_F");
					QTZGJ = MapUtils.getLong(map, "QTZGJ_F");
					QTFGJ = MapUtils.getLong(map, "QTFGJ_F");
					QTZJ  = MapUtils.getLong(map, "QTZJ_F");
					QTCJ  = MapUtils.getLong(map, "QTCJ_F");
					WPJ   = MapUtils.getLong(map, "WPJ_F");
					// 最高学历
					BS  = MapUtils.getLong(map, "BS_F");
					SS  = MapUtils.getLong(map, "SS_F");
					XS  = MapUtils.getLong(map, "XS_F");
					WXW = MapUtils.getLong(map, "WXW_F");
					// 年龄
					N35    = MapUtils.getLong(map, "N35_F");
					N36_45 = MapUtils.getLong(map, "N36_45_F");
					N46_55 = MapUtils.getLong(map, "N46_55_F");
					N56    = MapUtils.getLong(map, "N56_F");
					// 学缘
					BX = MapUtils.getLong(map, "BX_F");
					JN = MapUtils.getLong(map, "JN_F");
					JW = MapUtils.getLong(map, "JW_F");
					ZJ1 = JS + FJS + JS1 + ZJZC + QTZGJ + QTFGJ + QTZJ + QTCJ + WPJ;
					ZJ2 = BS + SS + XS + WXW;
					ZJ3 = N35 + N36_45 + N46_55 + N56;
					ZJ4 = BX + JN + JW;
					if(ZJ1 != ZJ2 || ZJ2 != ZJ3 || ZJ3 != ZJ4){
						b = false;
						errorL.add(ary[1]+"职称"+ZJ1+"人，最高学历"+ZJ2+"人，年龄"+ZJ3+"人，学缘"+ZJ4+"人；各分类总人数不相同！");
					}else{
						ZJ = ZJ1;
						map.put("ZJ_F", ZJ+""); //总计
					}
				}
			}
			// 再处理比例
			if(b){
				for(Map<String, String> map : list){
					_group = map.get("GROUPS");
					dm = map.get("DM");
					if(null != _group && group.equals(_group) && null != dm && "BL".equals(dm)){
						// 职称
						map.put("JS_F", JxpgUtil.getPercent(JS, ZJ));
						map.put("FJS_F", JxpgUtil.getPercent(FJS, ZJ));
						map.put("JS1_F", JxpgUtil.getPercent(JS1, ZJ));
						map.put("ZJZC_F", JxpgUtil.getPercent(ZJZC, ZJ));
						map.put("QTZGJ_F", JxpgUtil.getPercent(QTZGJ, ZJ));
						map.put("QTFGJ_F", JxpgUtil.getPercent(QTFGJ, ZJ));
						map.put("QTZJ_F", JxpgUtil.getPercent(QTZJ, ZJ));
						map.put("QTCJ_F", JxpgUtil.getPercent(QTCJ, ZJ));
						map.put("WPJ_F", JxpgUtil.getPercent(WPJ, ZJ));
						// 最高学历
						map.put("BS_F", JxpgUtil.getPercent(BS, ZJ));
						map.put("SS_F", JxpgUtil.getPercent(SS, ZJ));
						map.put("XS_F", JxpgUtil.getPercent(XS, ZJ));
						map.put("WXW_F", JxpgUtil.getPercent(WXW, ZJ));
						// 年龄
						map.put("N35_F", JxpgUtil.getPercent(N35, ZJ));
						map.put("N36_45_F", JxpgUtil.getPercent(N36_45, ZJ));
						map.put("N46_55_F", JxpgUtil.getPercent(N46_55, ZJ));
						map.put("N56_F", JxpgUtil.getPercent(N56, ZJ));
						// 学缘
						map.put("BX_F", JxpgUtil.getPercent(BX, ZJ));
						map.put("JN_F", JxpgUtil.getPercent(JN, ZJ));
						map.put("JW_F", JxpgUtil.getPercent(JW, ZJ));
						//总计
						map.put("ZJ_F", 100+""); 
					}
				}
			}

		}
		/**
		 * 4 填值 {步骤3 已经填充比例 这里只进行转换}
		 */
		if(b){
			resultM.put("items", JxpgUtil.changeObjectAttrToMap(list));
		}else{
			resultM.put("error_message", JxpgUtil.listSpilt(errorL));
		}
		resultM.put("success", b);
		return resultM;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map saveBeforeZydtrqk(String params) {
		/**
		 * 1 将公共接口的items数据格式化
		 */
		JSONObject obj = JSONObject.fromObject(params);
		Map<String, ArrayList<String[]>> updateM = JxpgUtil.changeMapToObject(obj.getString("items"));
		/**
		 * 2 找到更新的ID， 先将数据放入总数据中，然后再校验
		 */
		String table = "TB_JXPG_JSDW_ZYDTRQK";
		List<Map> list = baseDao.querySqlList("select * from " +table+ "  where xn = '" +obj.getString("xn")+ "'");
		JxpgUtil.mergeMap2List(updateM, list);
		/**
		 * 3 再校验
		 * GROUPS == A   DM='SL'/'BL'
		 * GROUPS == B   DM='SL'/'BL'
		 */
		Map resultM = new HashMap();
		List<String> errorL = new ArrayList<String>();
		boolean b = true;
		String[][] groupAry = new String[][]{{"A",""}};
		for(String[] ary : groupAry){
			String group = ary[0], _group = null, dm = null;
			long ZJ  = 0L,
				 ZJ1 = 0L,
				 ZJ2 = 0L,
				 ZJ3 = 0L,
				 ZJ4 = 0L;
			long ZCZGJ = 0L,
				 ZCFGJ = 0L,
				 ZCQT  = 0L,
				 XWBS = 0L,
				 XWSS = 0L,
				 XWQT = 0L,
				 N35     = 0L,
				 N36_45  = 0L,
				 N46_55  = 0L,
				 N56     = 0L,
				 BX  = 0L,
				 JN  = 0L,
				 JW  = 0L;
			// GROUPS == A | B
			// 先处理数量
			for(Map<String, String> map : list){
				_group = map.get("GROUPS");
				dm = map.get("DM");
				if(null != _group && group.equals(_group) && null != dm && "SL".equals(dm)){
					// 职称
					ZCZGJ = MapUtils.getLong(map, "ZCZGJ_F");
					ZCFGJ = MapUtils.getLong(map, "ZCFGJ_F");
					ZCQT  = MapUtils.getLong(map, "ZCQT_F");
					// 最高学历
					XWBS  = MapUtils.getLong(map, "XWBS_F");
					XWSS  = MapUtils.getLong(map, "XWSS_F");
					XWQT = MapUtils.getLong(map, "XWQT_F");
					// 年龄
					N35    = MapUtils.getLong(map, "N35_F");
					N36_45 = MapUtils.getLong(map, "N36_45_F");
					N46_55 = MapUtils.getLong(map, "N46_55_F");
					N56    = MapUtils.getLong(map, "N56_F");
					// 学缘
					BX = MapUtils.getLong(map, "BX_F");
					JN = MapUtils.getLong(map, "JN_F");
					JW = MapUtils.getLong(map, "JW_F");
					ZJ1 = ZCZGJ + ZCFGJ + ZCQT;
					ZJ2 = XWBS + XWSS + XWQT;
					ZJ3 = N35 + N36_45 + N46_55 + N56;
					ZJ4 = BX + JN + JW;
					if(ZJ1 != ZJ2 || ZJ2 != ZJ3 || ZJ3 != ZJ4){
						b = false;
						errorL.add(ary[1]+"职称"+ZJ1+"人，学位"+ZJ2+"人，年龄"+ZJ3+"人，学缘"+ZJ4+"人；各分类总人数不相同！");
					}else{
						ZJ = ZJ1;
						map.put("ZJ_F", ZJ+""); //总计
					}
				}
			}
			// 再处理比例
			if(b){
				for(Map<String, String> map : list){
					_group = map.get("GROUPS");
					dm = map.get("DM");
					if(null != _group && group.equals(_group) && null != dm && "BL".equals(dm)){
						// 职称
						map.put("ZCZGJ_F", JxpgUtil.getPercent(ZCZGJ, ZJ));
						map.put("ZCFGJ_F", JxpgUtil.getPercent(ZCFGJ, ZJ));
						map.put("ZCQT_F", JxpgUtil.getPercent(ZCQT, ZJ));
						// 最高学历
						map.put("XWBS_F", JxpgUtil.getPercent(XWBS, ZJ));
						map.put("XWSS_F", JxpgUtil.getPercent(XWSS, ZJ));
						map.put("XWQT_F", JxpgUtil.getPercent(XWQT, ZJ));
						// 年龄
						map.put("N35_F", JxpgUtil.getPercent(N35, ZJ));
						map.put("N36_45_F", JxpgUtil.getPercent(N36_45, ZJ));
						map.put("N46_55_F", JxpgUtil.getPercent(N46_55, ZJ));
						map.put("N56_F", JxpgUtil.getPercent(N56, ZJ));
						// 学缘
						map.put("BX_F", JxpgUtil.getPercent(BX, ZJ));
						map.put("JN_F", JxpgUtil.getPercent(JN, ZJ));
						map.put("JW_F", JxpgUtil.getPercent(JW, ZJ));
						//总计
						map.put("ZJ_F", 100+""); 
					}
				}
			}

		}
		/**
		 * 4 填值 {步骤3 已经填充比例 这里只进行转换}
		 */
		if(b){
			resultM.put("items", JxpgUtil.changeObjectAttrToMap(list));
		}else{
			resultM.put("error_message", JxpgUtil.listSpilt(errorL));
		}
		resultM.put("success", b);
		return resultM;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map saveBeforeXxsyxlryjg(String params){
		/**
		 * 1 将公共接口的items数据格式化
		 */
		JSONObject obj = JSONObject.fromObject(params);
		Map<String, ArrayList<String[]>> updateM = JxpgUtil.changeMapToObject(obj.getString("items"));
		/**
		 * 2 找到更新的ID， 先将数据放入总数据中，然后再校验
		 */
		String table = "TB_JXPG_JSDW_XXSYXLRYJG";
		List<Map> list = baseDao.querySqlList("select * from " +table+ "  where xn = '" +obj.getString("xn")+ "'");
		JxpgUtil.mergeMap2List(updateM, list);
		/**
		 * 3 再校验
		 * GROUPS == A   DM='SL'/'BL'
		 * GROUPS == B   DM='SL'/'BL'
		 */
		Map resultM = new HashMap();
		List<String> errorL = new ArrayList<String>();
		boolean b = true;
		String[][] groupAry = new String[][]{{"A",""}};
		for(String[] ary : groupAry){
			String group = ary[0], _group = null, dm = null;
			long ZJ  = 0L,
				 ZJ1 = 0L,
				 ZJ2 = 0L,
				 ZJ3 = 0L;
			long ZCZGJ = 0L,
				 ZCFJG = 0L,
				 ZCZJ  = 0L,
				 ZCCJ  = 0L,
				 WZC   = 0L,
				 XWBS = 0L,
				 XWSS = 0L,
				 XWXS = 0L,
				 WXW  = 0L,
				 N35     = 0L,
				 N36_45  = 0L,
				 N46_55  = 0L,
				 N56     = 0L;
			// GROUPS == A | B
			// 先处理数量
			for(Map<String, String> map : list){
				_group = map.get("GROUPS");
				dm = map.get("DM");
				if(null != _group && group.equals(_group) && null != dm && "SL".equals(dm)){
					// 职称
					ZCZGJ = MapUtils.getLong(map, "ZCZGJ_F");
					ZCFJG = MapUtils.getLong(map, "ZCFJG_F");
					ZCZJ  = MapUtils.getLong(map, "ZCZJ_F");
					ZCCJ  = MapUtils.getLong(map, "ZCCJ_F");
					WZC   = MapUtils.getLong(map, "WZC_F");
					// 最高学历
					XWBS  = MapUtils.getLong(map, "XWBS_F");
					XWSS  = MapUtils.getLong(map, "XWSS_F");
					XWXS  = MapUtils.getLong(map, "XWXS_F");
					WXW = MapUtils.getLong(map, "WXW_F");
					// 年龄
					N35    = MapUtils.getLong(map, "N35_F");
					N36_45 = MapUtils.getLong(map, "N36_45_F");
					N46_55 = MapUtils.getLong(map, "N46_55_F");
					N56    = MapUtils.getLong(map, "N56_F");
					ZJ1 = ZCZGJ + ZCFJG + ZCZJ + ZCCJ + WZC;
					ZJ2 = XWBS + XWSS + XWXS + WXW;
					ZJ3 = N35 + N36_45 + N46_55 + N56;
					if(ZJ1 != ZJ2 || ZJ2 != ZJ3){
						b = false;
						errorL.add(ary[1]+"职称"+ZJ1+"人，学位"+ZJ2+"人，年龄"+ZJ3+"人；各分类总人数不相同！");
					}else{
						ZJ = ZJ1;
						map.put("ZJ_F", ZJ+""); //总计
					}
				}
			}
			// 再处理比例
			if(b){
				for(Map<String, String> map : list){
					_group = map.get("GROUPS");
					dm = map.get("DM");
					if(null != _group && group.equals(_group) && null != dm && "BL".equals(dm)){
						// 职称
						map.put("ZCZGJ_F", JxpgUtil.getPercent(ZCZGJ, ZJ));
						map.put("ZCFJG_F", JxpgUtil.getPercent(ZCFJG, ZJ));
						map.put("ZCZJ_F", JxpgUtil.getPercent(ZCZJ, ZJ));
						map.put("ZCCJ_F", JxpgUtil.getPercent(ZCCJ, ZJ));
						map.put("WZC_F", JxpgUtil.getPercent(WZC, ZJ));
						// 最高学历
						map.put("XWBS_F", JxpgUtil.getPercent(XWBS, ZJ));
						map.put("XWSS_F", JxpgUtil.getPercent(XWSS, ZJ));
						map.put("XWXS_F", JxpgUtil.getPercent(XWXS, ZJ));
						map.put("WXW_F", JxpgUtil.getPercent(WXW, ZJ));
						// 年龄
						map.put("N35_F", JxpgUtil.getPercent(N35, ZJ));
						map.put("N36_45_F", JxpgUtil.getPercent(N36_45, ZJ));
						map.put("N46_55_F", JxpgUtil.getPercent(N46_55, ZJ));
						map.put("N56_F", JxpgUtil.getPercent(N56, ZJ));
						//总计
						map.put("ZJ_F", 100+""); 
					}
				}
			}

		}
		/**
		 * 4 填值 {步骤3 已经填充比例 这里只进行转换}
		 */
		if(b){
			resultM.put("items", JxpgUtil.changeObjectAttrToMap(list));
		}else{
			resultM.put("error_message", JxpgUtil.listSpilt(errorL));
		}
		resultM.put("success", b);
		return resultM;
	}
	
	@Override
	public Map saveBeforeJsjyjxggycg(String params) {
		/**
		 * 1 将公共接口的items数据格式化
		 */
		JSONObject obj = JSONObject.fromObject(params);
		Map<String, ArrayList<String[]>> updateM = JxpgUtil.changeMapToObject(obj.getString("items"));
		/**
		 * 2 找到更新的ID， 先将数据放入总数据中，然后再校验
		 */
		String table = "tb_jxpg_jsdw_jsjyjxggycg";
		List<Map> list = baseDao.querySqlList("select * from " +table+ "  where xn = '" +obj.getString("xn")+ "'");
		JxpgUtil.mergeMap2List(updateM, list);
		
		/**
		 * 3 再校验
		 * 1 = 2+3
		 * 4 = 5+6+7
		 * 8 = 9+10+11
		 * 
		 */
		Map resultM = new HashMap();
		int mc1 = 0;
		int mc4 = 0;
		double mc8 =0.0;
		for(Map<String, String> map : list){
			String mc = map.get("MC");
			String sl = map.get("SL_F");
			if(sl!=null && !"".equals(sl)){
				 if("2".equals(mc)){
					mc1 = mc1+Integer.parseInt(sl);
				}else if("3".equals(mc)){
					mc1 = mc1+Integer.parseInt(sl);
				}else if("5".equals(mc)){
					mc4 = mc4+Integer.parseInt(sl);
				}else if("6".equals(mc)){
					mc4 = mc4+Integer.parseInt(sl);
				}else if("7".equals(mc)){
					mc4 = mc4+Integer.parseInt(sl);
				}else if("9".equals(mc)){
					mc8 = mc8+Double.parseDouble(sl);
				}else if("10".equals(mc)){
					mc8 = mc8+Double.parseDouble(sl);
				}else if("11".equals(mc)){
					mc8 = mc8+Double.parseDouble(sl);
				}
			}
		}
		for(Map<String, String> map : list){
			String mc = map.get("MC");
			if("1".equals(mc)){
				if(mc1!=0){
					map.put("SL_F", mc1+"");
				}
			}else if("4".equals(mc)){
				if(mc4!=0){
					map.put("SL_F", mc4+"");
				}
			}else if("8".equals(mc)){
				if(mc8!=0.0){
					map.put("SL_F", mc8+"");
				}
			}
		}
		resultM.put("items", JxpgUtil.changeObjectAttrToMap(list));
		resultM.put("success", true);
		return resultM;
	}

	@Override
	public Map saveBeforeJspypxqk(String params) {
		
		/**
		 * 1 将公共接口的items数据格式化
		 */
		JSONObject obj = JSONObject.fromObject(params);
		Map<String, ArrayList<String[]>> updateM = JxpgUtil.changeMapToObject(obj.getString("items"));
		/**
		 * 2 找到更新的ID， 先将数据放入总数据中，然后再校验
		 */
		String table = "TB_JXPG_JSDW_JSPYPXQK";
		List<Map> list = baseDao.querySqlList("select * from " +table+ "  where xn = '" +obj.getString("xn")+ "'");
		JxpgUtil.mergeMap2List(updateM, list);
		/**
		 * 3 = 2/1*100;
		 */
		Map resultM = new HashMap();
	
		int mc1 = 0;
		int mc2 =0;
		for(Map<String, String> map : list){
			String mc = map.get("MC");
			String sl = map.get("SL_F");
			if(sl!=null && !"".equals(sl)){
				 if("1".equals(mc)){
					mc1 = Integer.parseInt(sl);
				}else if("2".equals(mc)){
					mc2 = Integer.parseInt(sl);
				}
			}
		}
		String mc3 = JxpgUtil.getPercent(mc2+"", mc1+"");
		for(Map<String, String> map : list){
			String mc = map.get("MC");
			if("3".equals(mc)){
				map.put("SL_F", mc3);
			}
		}
		resultM.put("items", JxpgUtil.changeObjectAttrToMap(list));
		resultM.put("success", true);
		return resultM;
	}


	@Override
	public Map saveBeforeGjxdwjsybksqk(String params) {
		/**
		 * 1 将公共接口的items数据格式化
		 */
		JSONObject obj = JSONObject.fromObject(params);
		Map<String, ArrayList<String[]>> updateM = JxpgUtil.changeMapToObject(obj.getString("items"));
		/**
		 * 2 找到更新的ID， 先将数据放入总数据中，然后再校验
		 */
		String table = "TB_JXPG_JSDW_GJXDWJSYBKSQK";
		List<Map> list = baseDao.querySqlList("select * from " +table+ "  where xn = '" +obj.getString("xn")+ "'");
		JxpgUtil.mergeMap2List(updateM, list);
		/**
		 * 3   
		 */
		Map resultM = new HashMap();
		List<String> errorL = new ArrayList<String>();
		boolean b = true;
		for(Map<String, String> map : list){
			String mc = map.get("MC");
			Long GJSL_F = map.get("GJSL_F")!=null?Long.parseLong(map.get("GJSL_F")):0;
			Long ZS_F  = map.get("ZS_F")!=null?Long.parseLong(map.get("ZS_F")):0;
			Long N35SL_F = map.get("N35SL_F")!=null?Long.parseLong(map.get("N35SL_F")):0;
			Long N5NXZSL_F = map.get("N5NXZSL_F")!=null?Long.parseLong(map.get("N5NXZSL_F")):0;
			if(GJSL_F>ZS_F){
				errorL.add(mc+"具有高级职称教师的数量大于总数");
				b = false;
			}
			if(N35SL_F>ZS_F){
				errorL.add(mc+"35岁以下青年教师的数量大于总数");
				b = false;
			}
			if(N5NXZSL_F>ZS_F){
				errorL.add(mc+"近五年新增教师的数量大于总数");
				b = false;
			}
		}
		if(b){
			for(Map<String, String> map : list){
				int GJSL_F = map.get("GJSL_F")!=null?Integer.parseInt(map.get("GJSL_F")):0;
				int ZS_F  = map.get("ZS_F")!=null?Integer.parseInt(map.get("ZS_F")):0;
				int N35SL_F = map.get("N35SL_F")!=null?Integer.parseInt(map.get("N35SL_F")):0;
				int N5NXZSL_F = map.get("N5NXZSL_F")!=null?Integer.parseInt(map.get("N5NXZSL_F")):0;
				int BKSS_F = map.get("BKSS_F")!=null?Integer.parseInt(map.get("BKSS_F")):0;
				if(ZS_F!=0){
					map.put("GJBL_F", JxpgUtil.getPercent(GJSL_F, ZS_F));
					map.put("N35BL_F", JxpgUtil.getPercent(N35SL_F, ZS_F));
					map.put("N5NXZBL_F", JxpgUtil.getPercent(N5NXZSL_F, ZS_F));
					map.put("BKSYZRJSZB_F", JxpgUtil.getNewNum(BKSS_F+"", ZS_F+""));
				}
			}
			resultM.put("items", JxpgUtil.changeObjectAttrToMap(list));
		}else{
			resultM.put("error_message", JxpgUtil.listSpilt(errorL));
		}
		resultM.put("success", b);
		return resultM;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map saveBeforeZjjsbkskqk(String params) {
		/**
		 * 1 将公共接口的items数据格式化
		 */
		JSONObject obj = JSONObject.fromObject(params);
		Map<String, ArrayList<String[]>> updateM = JxpgUtil.changeMapToObject(obj.getString("items"));
		/**
		 * 2 找到更新的ID， 先将数据放入总数据中，然后再校验
		 */
		String table = "TB_JXPG_JSDW_ZJJSBKSKQK";
		List<Map> list = baseDao.querySqlList("select * from " +table+ "  where xn = '" +obj.getString("xn")+ "'");
		JxpgUtil.mergeMap2List(updateM, list);
		/**
		 * 3   
		 */
		Map resultM = new HashMap();
		List<String> errorL = new ArrayList<String>();
		boolean b = true;
		String dm = null;
		long ZJ = 0L,
			 ZJGJZC = 0L,
			 ZJJS   = 0L,
			 ZJDNJJS= 0L,
			 ZJSSBS = 0L;
		for(Map<String, String> map : list){
			dm = map.get("dm");
			String mc = "“"+map.get("MC")+"”";
			Long SKJSS_F  = MapUtils.getLong(map, "SKJSS_F");
			Long GJZCSL_F = MapUtils.getLong(map, "GJZCSL_F");
			Long JSSL_F = MapUtils.getLong(map, "JSSL_F");
			Long DNJSKJSSL_F = MapUtils.getLong(map, "DNJSKJSSL_F");
			Long SSBSXWSL_F = MapUtils.getLong(map, "SSBSXWSL_F");
			if(GJZCSL_F>SKJSS_F){
				errorL.add(mc+"具有高级职称教师的数量大于总数");
				b = false;
			}
			if(JSSL_F>SKJSS_F){
				errorL.add(mc+"教授的数量大于总数");
				b = false;
			}
			if(DNJSKJSSL_F>SKJSS_F){
				errorL.add(mc+"低年级授课教授的数量大于总数");
				b = false;
			}
			if(SSBSXWSL_F>SKJSS_F){
				errorL.add(mc+"具有硕士、博 士学位的数量大于总数");
				b = false;
			}
			if(!"ZJ".equals(dm)){
				 ZJ     += SKJSS_F;
				 ZJGJZC += GJZCSL_F;
				 ZJJS   += JSSL_F;
				 ZJDNJJS+= DNJSKJSSL_F;
				 ZJSSBS += SSBSXWSL_F;
			}
		}
		if(b){
			for(Map<String, String> map : list){
				Long SKJSS_F  = MapUtils.getLong(map, "SKJSS_F");
				Long GJZCSL_F = MapUtils.getLong(map, "GJZCSL_F");
				Long JSSL_F = MapUtils.getLong(map, "JSSL_F");
				Long DNJSKJSSL_F = MapUtils.getLong(map, "DNJSKJSSL_F");
				Long SSBSXWSL_F = MapUtils.getLong(map, "SSBSXWSL_F");
				if(SKJSS_F != 0L){
					map.put("GJZCBL_F", JxpgUtil.getPercent(GJZCSL_F, SKJSS_F));
					map.put("JSBL_F", JxpgUtil.getPercent(JSSL_F, SKJSS_F));
					map.put("DNJSKJSBL_F", JxpgUtil.getPercent(DNJSKJSSL_F, SKJSS_F));
					map.put("SSBSXWBL_F", JxpgUtil.getPercent(SSBSXWSL_F, SKJSS_F));
				}
				String dm2 = map.get("dm");
				if(null != dm2 && "ZJ".equals(dm2)){
					map.put("SKJSS_F", ZJ+"");
					map.put("GJZCSL_F", ZJGJZC+"");
					map.put("GJZCBL_F", "-"); //JxpgUtil.getPercent(ZJGJZC, ZJ)
					map.put("JSSL_F", ZJJS+"");
					map.put("JSBL_F", "-"); // JxpgUtil.getPercent(ZJJS, ZJ)
					map.put("DNJSKJSSL_F", ZJDNJJS+"");
					map.put("DNJSKJSBL_F", "-"); // JxpgUtil.getPercent(ZJDNJJS, ZJ)
					map.put("SSBSXWSL_F", ZJSSBS+"");
					map.put("SSBSXWBL_F", "-"); // JxpgUtil.getPercent(ZJSSBS, ZJ)
				}
				
			}
			resultM.put("items", JxpgUtil.changeObjectAttrToMap(list));
		}else{
			resultM.put("error_message", JxpgUtil.listSpilt(errorL));
		}
		resultM.put("success", b);
		return resultM;
	}
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map saveBeforeJsfjsjsbkkcbl(String params){
		/**
		 * 1 将公共接口的items数据格式化
		 */
		JSONObject obj = JSONObject.fromObject(params);
		Map<String, ArrayList<String[]>> updateM = JxpgUtil.changeMapToObject(obj.getString("items"));
		/**
		 * 2 找到更新的ID， 先将数据放入总数据中，然后再校验
		 */
		String table = "TB_JXPG_JSDW_JSFJSJSBKKCBL";
		List<Map> list = baseDao.querySqlList("select * from " +table+ "  where xn = '" +obj.getString("xn")+ "'");
		JxpgUtil.mergeMap2List(updateM, list);
		/**
		 * 3 && 4
		 */
		Map resultM = new HashMap();
		List<String> errorL = new ArrayList<String>();
		String dm = null, mc = null;
		boolean b = true;
		long ZJ = 0L, SL = 0L;
		for(Map<String, String> map : list){
			dm = map.get("dm");
			if(null != dm && "ZJ".equals(dm)){
				ZJ = MapUtils.getLong(map, "SL_F");
			}
		}
		for(Map<String, String> map : list){
			dm = map.get("dm");
			mc = "“"+map.get("MC")+"”";
			if(!"ZJ".equals(dm)){
				SL = MapUtils.getLong(map, "SL_F");
				if(ZJ < SL){b=false; errorL.add("课程总计小于"+mc+"数量");}
				map.put("BL_F", JxpgUtil.getPercent(MapUtils.getLong(map, "SL_F"), ZJ));
			}
		}
		/**
		 * 4
		 */
		if(b){
			resultM.put("items", JxpgUtil.changeObjectAttrToMap(list));
		}else{
			resultM.put("error_message", JxpgUtil.listSpilt(errorL));
		}
		resultM.put("success", b);
		return resultM;
	}
	
}