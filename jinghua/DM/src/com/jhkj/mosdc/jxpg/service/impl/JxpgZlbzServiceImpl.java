package com.jhkj.mosdc.jxpg.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.jxpg.service.JxpgZlbzService;
import com.jhkj.mosdc.jxpg.util.JxpgUtil;
import com.jhkj.mosdc.jxpg.util.MapUtils;

public class JxpgZlbzServiceImpl implements JxpgZlbzService {

	private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public JxpgZlbzServiceImpl() {
		
	}

	@Override
	public Map saveBeforeJxdwjg(String params) {
		JSONObject objMap = JSONObject.fromObject(params);
		List<Map<String,String>> ary = objMap.getJSONArray("items");
		//页面ID，只能唯一
		String ids = "";
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
		List<Map<String,String>> list = baseDao.querySqlList("select * from TB_JXPG_ZLBZ_JXGLJG  where xn = '" +objMap.getString("xn")+ "' order by id asc");
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
								errorL.add(mc+"下：职称、学位、年龄三者总人数不相同");
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
	public Map saveBeforeJxyjqk(String params) {
		JSONObject objMap = JSONObject.fromObject(params);
		List<Map<String,String>> ary = objMap.getJSONArray("items");
		//页面ID，只能唯一
		String ids = "";
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
		List<Map> list = baseDao.querySqlList("select * from TB_JXPG_ZLBZ_JXYJQK  where xn = '" +objMap.getString("xn")+ "' order by id asc");
		// 1 找到更新的ID， 先将数据放入总数据中，然后再校验
		for (Map.Entry<String, ArrayList<String[]>> en : updateM.entrySet()) {
			String id = en.getKey();
			ArrayList<String[]> valL = en.getValue();
			for (int i=0;i<list.size();i++) {
				Map map = list.get(i);
				if (id.equals(String.valueOf(map.get("ID")))) {
					for (String[] ar : valL) {
						list.get(i).put(ar[0], ar[1]);
					}
				}
			}
		}
		//校验数据
		long  A = 0L,
				 B = 0L,
				 C = 0L,
				 D = 0L,
				 E = 0L,
				 F = 0L,
				 K = 0L;
		float H = (float) 0.00,
				 I = (float) 0.00,
				 J = (float) 0.00,
				 G = (float) 0.00;
		String aMc = null,
				bMc = null,
				cMc = null,
				dMc = null,
				eMc = null,
				fMc = null,
				gMc = null,
				hMc = null,
				iMc = null,
				jMc = null,
				kMc = null;
		for(Map m : list){
			String xm = String.valueOf(m.get("XM"));
			if(xm.equals("A")){
				A = MapUtils.getLong(m, "ZS_F");
				aMc = String.valueOf(m.get("MC"));
			}else if(xm.equals("B")){
				B = MapUtils.getLong(m, "ZS_F");
				bMc = String.valueOf(m.get("MC"));
			}else if(xm.equals("C")){
				C = MapUtils.getLong(m, "ZS_F");
				cMc = String.valueOf(m.get("MC"));
			}else if(xm.equals("D")){
				D = MapUtils.getLong(m, "ZS_F");
				dMc = String.valueOf(m.get("MC"));
			}else if(xm.equals("E")){
				E = MapUtils.getLong(m, "ZS_F");
				eMc = String.valueOf(m.get("MC"));
			}else if(xm.equals("F")){
				F = MapUtils.getLong(m, "ZS_F");
				fMc = String.valueOf(m.get("MC"));
			}else if(xm.equals("K")){
				K = MapUtils.getLong(m, "ZS_F");
				kMc = String.valueOf(m.get("MC"));
			}else if(xm.equals("G")){
				G = Float.valueOf(String.valueOf(m.get("ZS_F")));
				gMc = String.valueOf(m.get("MC"));
			}else if(xm.equals("H")){
				H = Float.valueOf(String.valueOf(m.get("ZS_F")));
				hMc = String.valueOf(m.get("MC"));
			}else if(xm.equals("I")){
				I = Float.valueOf(String.valueOf(m.get("ZS_F")));
				iMc = String.valueOf(m.get("MC"));
			}else if(xm.equals("J")){
				J = Float.valueOf(String.valueOf(m.get("ZS_F")));
				jMc = String.valueOf(m.get("MC"));
			}
		}
//		if(B+C != A){
//			errorL.add(bMc+"与"+cMc+"之和应等于"+aMc+"\n");
//		}
		if(E+F+K != D){
			errorL.add(eMc+"与"+fMc+"与"+kMc+"之和应等于"+dMc+"\n");
		}
		BigDecimal nH = new BigDecimal(Float.toString(H)); 
		BigDecimal nI = new BigDecimal(Float.toString(I)); 
		BigDecimal nJ = new BigDecimal(Float.toString(J)); 
		BigDecimal nG = new BigDecimal(Float.toString(G)); 
		if(nH.add(nI).add(nJ).floatValue() != nG.floatValue()){
			errorL.add(hMc+"与"+iMc+"与"+jMc+"之和应等于"+hMc+"\n");
		}
		if(errorL.size()>0){
			resultM.put("success", false);
			resultM.put("error_message", errorL);
			resultM.put("items", resultM);
		}else{
			resultM.put("success", true);
			resultM.put("items", JxpgUtil.changeObjectAttrToMap(list));
		}
		System.out.println(resultM);
		return resultM;
	}
}
