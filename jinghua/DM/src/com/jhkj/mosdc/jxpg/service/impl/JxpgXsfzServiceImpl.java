package com.jhkj.mosdc.jxpg.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.jxpg.service.JxpgXsfzService;
import com.jhkj.mosdc.jxpg.util.JxpgUtil;
import com.jhkj.mosdc.jxpg.util.MapUtils;

public class JxpgXsfzServiceImpl implements JxpgXsfzService {

	private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public JxpgXsfzServiceImpl() {
	}

	@Override
	public Map saveBeforeXsbdqk(String params) {
		/**
		 * 获取前台改变的数据
		 */
		JSONObject obj = JSONObject.fromObject(params);
		Map<String, ArrayList<String[]>> updateM = JxpgUtil.changeMapToObject(obj.getString("items"));
		/**
		 * 获取数据库中所有数据 找到要更新的数据放入总数据中
		 */
		List<Map> list = baseDao.querySqlList("select * from TB_JXPG_XSFZ_ZSBDQK where xn = '" +obj.getString("xn")+ "'");
		for(Map.Entry<String, ArrayList<String[]>> en : updateM.entrySet()){
			String id = en.getKey();
			ArrayList<String[]> valL = en.getValue();
			for(Map<String, String> map : list){
				if(id.equals(String.valueOf(map.get("ID")))){
					for(String[] ar : valL){
						map.put(ar[0], ar[1]);
					}
				}
			}
		}
		/**
		 * 校验录入数据是否合法
		 */
		Map resultM = new HashMap();
		List<String> errorL = new ArrayList<String>();
		Long sjlqs_f = 0L,
				sjbds_f = 0L;
		for(Map m : list){
			String zymc = String.valueOf(m.get("ZYMC"));
			sjlqs_f = MapUtils.getLong(m, "SJLQS_F");
			sjbds_f = MapUtils.getLong(m, "SJBDS_F");
			if(sjbds_f > sjlqs_f){
				errorL.add(zymc+"下:实际报到数不能大于实际录取数!\n");
			}else{
				m.put("BDL_F",JxpgUtil.getPercent(String.valueOf(sjbds_f), String.valueOf(sjlqs_f)));
			}
		}
		if(errorL.size()>0){
			resultM.put("success", false);
			resultM.put("error_message", errorL);
		}else{
			resultM.put("success", true);
			resultM.put("items", JxpgUtil.changeObjectAttrToMap(list));
		}
		return resultM;
	}

	@Override
	public Map saveBeforeXsglyjg(String params) {
		JSONObject objMap = JSONObject.fromObject(params);
		List<Map<String,String>> ary = objMap.getJSONArray("items");
		Map<String,ArrayList<String[]>> updateM = new HashMap<String,ArrayList<String[]>>();
		for(Map<String,String> map : ary){
			String id = map.get("rowid");
			if(!updateM.containsKey(id)){
				updateM.put(id, new ArrayList<String[]>());
			}
			updateM.get(id).add(new String[]{map.get("itemCode"),map.get("itemValue")});
		}
		Map resultM = new HashMap();
		List<String> errorL = new ArrayList<String>();
		List<Map> itemList = new ArrayList<Map>();
		//数据库数据必须保持  第一条为数据  第二条为比率 第三条为数据  第四条为比率
		List<Map<String,String>> list = baseDao.querySqlList("select * from TB_JXPG_XSFZ_XSGLYJG  where xn = '" +objMap.getString("xn")+ "' order by id asc");
		// 1 找到更新的ID， 先将数据放入总数据中，然后再校验
		for(Map.Entry<String, ArrayList<String[]>> en : updateM.entrySet()){
			String id = en.getKey();
			ArrayList<String[]> valL = en.getValue();
			for(Map<String, String> map : list){
				if(id.equals(String.valueOf(map.get("ID")))){
					for(String[] ar : valL){
						map.put(ar[0], ar[1]);
					}
					//校验
					String bs = null;
					String group_id = null;
					String mc = null;
					long zs_f = 0L,
							zc_zgj_f = 0L,
							zc_fgj_f = 0L,
							zc_zj_f = 0L,
							zc_cj_f = 0L,
							zc_wzc_f = 0L,
							xw_bs_f = 0L,
							xw_ss_f = 0L,
							xw_xs_f = 0L,
							xw_wxw_f = 0L,
							nl_lower35_f = 0L,
							nl_3645_f = 0L,
							nl_4655_f = 0L,
							nl_56_f = 0L;
						bs = map.get("BS");
						group_id = map.get("GROUP_ID");
						mc = map.get("XM");
						if(null != bs && "SL".equals(bs)){
							zc_zgj_f = MapUtils.getLong(map, "ZC_ZGJ_F");
							zc_fgj_f = MapUtils.getLong(map, "ZC_FGJ_F");
							zc_zj_f = MapUtils.getLong(map, "ZC_ZJ_F");
							zc_cj_f = MapUtils.getLong(map, "ZC_CJ_F");
							zc_wzc_f = MapUtils.getLong(map, "ZC_WZC_F");
							xw_bs_f = MapUtils.getLong(map, "XW_BS_F");
							xw_ss_f = MapUtils.getLong(map, "XW_SS_F");
							xw_xs_f = MapUtils.getLong(map, "XW_XS_F");
							xw_wxw_f = MapUtils.getLong(map, "XW_WXW_F");
							nl_lower35_f = MapUtils.getLong(map, "NL_LOWER_35_F");
							nl_3645_f = MapUtils.getLong(map, "NL_36_45_F");
							nl_4655_f = MapUtils.getLong(map, "NL_46_55_F");
							nl_56_f = MapUtils.getLong(map, "NL_56_F");
							
							long zj1 = zc_zgj_f+zc_fgj_f+zc_zj_f+zc_cj_f+zc_wzc_f,
									zj2 = xw_bs_f+xw_ss_f+xw_xs_f+xw_wxw_f,
									zj3 = nl_lower35_f+nl_3645_f+nl_4655_f+nl_56_f;
							if(zj1 == zj2 && zj2 == zj3){
								zs_f = zj1;
								map.put("ZS_F", String.valueOf(zs_f));
								itemList.add(map);
								//计算比率
								String sql = "SELECT * FROM TB_JXPG_ZLBZ_JXGLJG WHERE BS='BL' AND GROUP_ID='"+group_id+"'";
								List<Map> bList = baseDao.querySqlList(sql);
								if(null != bList && bList.size()>0){
									Map bMap = bList.get(0);
									bMap.put("ZS_F", "100");
									bMap.put("ZC_ZGJ_F",JxpgUtil.getNewPercent(String.valueOf(zc_zgj_f), String.valueOf(zs_f),1));
									bMap.put("ZC_FGJ_F",JxpgUtil.getNewPercent(String.valueOf(zc_fgj_f), String.valueOf(zs_f),1));
									bMap.put("ZC_ZJ_F",JxpgUtil.getNewPercent(String.valueOf(zc_zj_f), String.valueOf(zs_f),1));
									bMap.put("ZC_CJ_F",JxpgUtil.getNewPercent(String.valueOf(zc_cj_f), String.valueOf(zs_f),1));
									bMap.put("ZC_WZC_F",JxpgUtil.getNewPercent(String.valueOf(zc_wzc_f), String.valueOf(zs_f),1));
									bMap.put("XW_BS_F",JxpgUtil.getNewPercent(String.valueOf(xw_bs_f), String.valueOf(zs_f),1));
									bMap.put("XW_SS_F",JxpgUtil.getNewPercent(String.valueOf(xw_ss_f), String.valueOf(zs_f),1));
									bMap.put("XW_XS_F",JxpgUtil.getNewPercent(String.valueOf(xw_xs_f), String.valueOf(zs_f),1));
									bMap.put("XW_WXW_F",JxpgUtil.getNewPercent(String.valueOf(xw_wxw_f), String.valueOf(zs_f),1));
									bMap.put("NL_LOWER_35_F",JxpgUtil.getNewPercent(String.valueOf(nl_lower35_f), String.valueOf(zs_f),1));
									bMap.put("NL_36_45_F",JxpgUtil.getNewPercent(String.valueOf(nl_3645_f), String.valueOf(zs_f),1));
									bMap.put("NL_46_55_F",JxpgUtil.getNewPercent(String.valueOf(nl_4655_f), String.valueOf(zs_f),1));
									bMap.put("NL_56_F",JxpgUtil.getNewPercent(String.valueOf(nl_56_f), String.valueOf(zs_f),1));
									itemList.add(bMap);
								}
							}else{
								errorL.add(mc+":下职称、学位、年龄三者总人数不相同\n");
							}
						}
				}
			}
		}
		if(errorL.size()>0){
			resultM.put("success", false);
			resultM.put("error_message", errorL);
			resultM.put("items", resultM);
		}else{
			resultM.put("success", true);
			resultM.put("items", JxpgUtil.changeObjectAttrToMap(itemList));
		}
		return resultM;
	}

	@Override
	public Map saveBeforeJxdwXsqk(String params) {
		/**
		 * 获取前台改变的数据
		 */
		JSONObject obj = JSONObject.fromObject(params);
		Map<String, ArrayList<String[]>> updateM = JxpgUtil.changeMapToObject(obj.getString("items"));
		/**
		 * 获取数据库中所有数据 找到要更新的数据放入总数据中
		 */
		List<Map> list = baseDao.querySqlList("select * from TB_JXPG_XSFZ_JXDWXSQK where xn = '" +obj.getString("xn")+ "'");
		for(Map.Entry<String, ArrayList<String[]>> en : updateM.entrySet()){
			String id = en.getKey();
			ArrayList<String[]> valL = en.getValue();
			for(Map<String, String> map : list){
				if(id.equals(String.valueOf(map.get("ID")))){
					for(String[] ar : valL){
						map.put(ar[0], ar[1]);
					}
				}
			}
		}
		/**
		 * 校验录入数据是否合法
		 */
		Map resultM = new HashMap();
		List<String> errorL = new ArrayList<String>();
		Long xsglrs_f = 0L,
				bkszxrs_f = 0L;
		for(Map m : list){
			xsglrs_f = MapUtils.getLong(m, "XSGLRS_F");
			bkszxrs_f = MapUtils.getLong(m, "BKSZXRS_F");
			m.put("RYBL_F",JxpgUtil.getNewNum(String.valueOf(bkszxrs_f),String.valueOf(xsglrs_f)));
		}
		if(errorL.size()>0){
			resultM.put("success", false);
			resultM.put("error_message", errorL);
		}else{
			resultM.put("success", true);
			resultM.put("items", JxpgUtil.changeObjectAttrToMap(list));
		}
		return resultM;
	}

	@Override
	public Map saveBeforeXsfzqk(String params) {
		/**
		 * 获取前台改变的数据
		 */
		JSONObject obj = JSONObject.fromObject(params);
		Map<String, ArrayList<String[]>> updateM = JxpgUtil.changeMapToObject(obj.getString("items"));
		/**
		 * 获取数据库中所有数据 找到要更新的数据放入总数据中
		 */
		List<Map> list = baseDao.querySqlList("select * from TB_JXPG_XSFZ_XSFZQK where xn = '" +obj.getString("xn")+ "'");
		for(Map.Entry<String, ArrayList<String[]>> en : updateM.entrySet()){
			String id = en.getKey();
			ArrayList<String[]> valL = en.getValue();
			for(Map<String, String> map : list){
				if(id.equals(String.valueOf(map.get("ID")))){
					for(String[] ar : valL){
						map.put(ar[0], ar[1]);
					}
				}
			}
		}
		Map resultM = new HashMap();
		List<String> errorL = new ArrayList<String>();
		/**
		 * 校验数据
		 */
		//校验数据
				long  A = 0L,
						 B = 0L,
						 C = 0L,
						 D = 0L,
						 E = 0L,
						 F = 0L,
						 G = 0L,
						 H = 0L,
						 I = 0L;
				String aMc = null,
						bMc = null,
						cMc = null,
						dMc = null,
						eMc = null,
						fMc = null,
						gMc = null,
						hMc = null,
						iMc = null;
				for(Map m : list){
					String xm = String.valueOf(m.get("DM"));
					if(xm.equals("A")){
						A = MapUtils.getLong(m, "XMZ_F");
						aMc = String.valueOf(m.get("MC"));
					}else if(xm.equals("B")){
						B = MapUtils.getLong(m, "XMZ_F");
						bMc = String.valueOf(m.get("MC"));
					}else if(xm.equals("C")){
						C = MapUtils.getLong(m, "XMZ_F");
						cMc = String.valueOf(m.get("MC"));
					}else if(xm.equals("D")){
						D = MapUtils.getLong(m, "XMZ_F");
						dMc = String.valueOf(m.get("MC"));
					}else if(xm.equals("E")){
						E = MapUtils.getLong(m, "XMZ_F");
						eMc = String.valueOf(m.get("MC"));
					}else if(xm.equals("F")){
						F = MapUtils.getLong(m, "XMZ_F");
						fMc = String.valueOf(m.get("MC"));
					}else if(xm.equals("G")){
						G = MapUtils.getLong(m, "XMZ_F");
						gMc = String.valueOf(m.get("MC"));
					}else if(xm.equals("H")){
						H = MapUtils.getLong(m, "XMZ_F");
						hMc = String.valueOf(m.get("MC"));
					}else if(xm.equals("I")){
						I = MapUtils.getLong(m, "XMZ_F");
						iMc = String.valueOf(m.get("MC"));
					}
				}
				if(B+C != A){
					errorL.add("学科竞赛获奖（项）中:"+bMc+"与"+cMc+"之和应等于"+aMc+"\n");
				}
				if(E+F != D){
					errorL.add("本科生创新活动、技能竞赛获奖中:"+eMc+"与"+fMc+"之和应等于"+dMc+"\n");
				}
				if(H+I != G){
					errorL.add("文艺、体育竞赛获奖（项）中:"+hMc+"与"+iMc+"之和应等于"+gMc+"\n");
				}
				if(errorL.size()>0){
					resultM.put("success", false);
					resultM.put("error_message", errorL);
					resultM.put("items", resultM);
				}else{
					resultM.put("success", true);
					resultM.put("items", JxpgUtil.changeObjectAttrToMap(list));
				}
				return resultM;
	}

	@Override
	public Map saveBeforeJyqxfb(String params) {
		/**
		 * 获取前台改变的数据
		 */
		JSONObject obj = JSONObject.fromObject(params);
		Map<String, ArrayList<String[]>> updateM = JxpgUtil.changeMapToObject(obj.getString("items"));
		/**
		 * 获取数据库中所有数据 找到要更新的数据放入总数据中
		 */
		List<Map> list = baseDao.querySqlList("select * from TB_JXPG_XSFZ_JYQXFB where xn = '" +obj.getString("xn")+ "'");
		for(Map.Entry<String, ArrayList<String[]>> en : updateM.entrySet()){
			String id = en.getKey();
			ArrayList<String[]> valL = en.getValue();
			for(Map<String, String> map : list){
				if(id.equals(String.valueOf(map.get("ID")))){
					for(String[] ar : valL){
						map.put(ar[0], ar[1]);
					}
				}
			}
		}
		/**
		 * 校验录入数据是否合法
		 */
		Map resultM = new HashMap();
		List<String> errorL = new ArrayList<String>();
		Long zfjg_f = 0L,
				sydw_f = 0L,
				qy_f = 0L,
				bd_f = 0L,
				lhjy_f = 0L,
				sx_f = 0L,
				dfxm_f = 0L,
				qt_f = 0L,
				zj_f = 0L;
		for(Map m : list){
			String bs = String.valueOf(m.get("BS"));
			if("SL".equals(bs)){
				zfjg_f = MapUtils.getLong(m, "ZFJG_F");
				sydw_f = MapUtils.getLong(m, "SYDW_F");
				qy_f = MapUtils.getLong(m, "QY_F");
				bd_f = MapUtils.getLong(m, "BD_F");
				lhjy_f = MapUtils.getLong(m, "LHJY_F");
				sx_f = MapUtils.getLong(m, "SX_F");
				dfxm_f = MapUtils.getLong(m, "DFXM_F");
				qt_f = MapUtils.getLong(m, "QT_F");
				zj_f = MapUtils.getLong(m, "ZJ_F");
				if(zfjg_f+sydw_f+qy_f+bd_f+lhjy_f+sx_f+dfxm_f+qt_f != zj_f){
					errorL.add("各就业去向之和应等于总计!");
				}
			}else if("BL".equals(bs)){
				m.put("ZFJG_F", JxpgUtil.getNewPercent(String.valueOf(zfjg_f), String.valueOf(zj_f),1));
				m.put("SYDW_F", JxpgUtil.getNewPercent(String.valueOf(sydw_f), String.valueOf(zj_f),1));
				m.put("QY_F", JxpgUtil.getNewPercent(String.valueOf(qy_f), String.valueOf(zj_f),1));
				m.put("BD_F", JxpgUtil.getNewPercent(String.valueOf(bd_f), String.valueOf(zj_f),1));
				m.put("LHJY_F", JxpgUtil.getNewPercent(String.valueOf(lhjy_f), String.valueOf(zj_f),1));
				m.put("SX_F", JxpgUtil.getNewPercent(String.valueOf(sx_f), String.valueOf(zj_f),1));
				m.put("DFXM_F", JxpgUtil.getNewPercent(String.valueOf(dfxm_f), String.valueOf(zj_f),1));
				m.put("QT_F", JxpgUtil.getNewPercent(String.valueOf(qt_f), String.valueOf(zj_f),1));
				m.put("ZJ_F", "100");
			}
		}
		if(errorL.size()>0){
			resultM.put("success", false);
			resultM.put("error_message", errorL);
		}else{
			resultM.put("success", true);
			resultM.put("items", JxpgUtil.changeObjectAttrToMap(list));
		}
		return resultM;
	}

	@Override
	public Map saveBeforeBysqk(String params) {
		/**
		 * 获取前台改变的数据
		 */
		JSONObject obj = JSONObject.fromObject(params);
		Map<String, ArrayList<String[]>> updateM = JxpgUtil.changeMapToObject(obj.getString("items"));
		/**
		 * 获取数据库中所有数据 找到要更新的数据放入总数据中
		 */
		List<Map> list = baseDao.querySqlList("select * from TB_JXPG_XSFZ_GZYBYSQK where xn = '" +obj.getString("xn")+ "'");
		for(Map.Entry<String, ArrayList<String[]>> en : updateM.entrySet()){
			String id = en.getKey();
			ArrayList<String[]> valL = en.getValue();
			for(Map<String, String> map : list){
				if(id.equals(String.valueOf(map.get("ID")))){
					for(String[] ar : valL){
						map.put(ar[0], ar[1]);
					}
				}
			}
		}
		/**
		 * 校验录入数据是否合法
		 */
		Map resultM = new HashMap();
		List<String> errorL = new ArrayList<String>();
		String zymc = null;
		Long yjbys_f = 0L,
				yjwasby_f = 0L,
				xwsys_f = 0L,
				yjsjyl_f = 0L;
		for(Map m : list){
			zymc = String.valueOf(m.get("ZYMC"));
			yjbys_f = MapUtils.getLong(m, "YJBYS_F");
			yjwasby_f = MapUtils.getLong(m, "YJWASBY_F");
			xwsys_f = MapUtils.getLong(m, "XWSYS_F");
			yjsjyl_f = MapUtils.getLong(m, "YJSJYL_F");
			if(yjwasby_f > yjbys_f){
				errorL.add(zymc+"专业下:应届未按时毕业人数不能大于应届毕业人数!\n");
			}
			if(xwsys_f > yjbys_f){
				errorL.add(zymc+"专业下:学位授予人数不能大于应届毕业人数!\n");
			}
			if(yjsjyl_f > yjbys_f){
				errorL.add(zymc+"专业下:应届毕业生就业人数不能大于应届毕业人数!\n");
			}
			m.put("BYL_F", JxpgUtil.getNewPercent(String.valueOf(yjbys_f-yjwasby_f), String.valueOf(yjbys_f),1));
			m.put("XWSYL_F", JxpgUtil.getNewPercent(String.valueOf(xwsys_f), String.valueOf(yjbys_f),1));
			m.put("CCJYL_F", JxpgUtil.getNewPercent(String.valueOf(yjsjyl_f), String.valueOf(yjbys_f),1));
		}
		if(errorL.size()>0){
			resultM.put("success", false);
			resultM.put("error_message", errorL);
		}else{
			resultM.put("success", true);
			resultM.put("items", JxpgUtil.changeObjectAttrToMap(list));
		}
		return resultM;
	}
}
