package cn.gilight.research.mbrws.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.gilight.business.service.BusinessService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.research.mbrws.service.GdMbrwsService;
 
/**   
* @Description: 论文发表量service
* @author Sunwg  
* @date 2016年6月12日 下午5:12:17   
*/
@Service("gdMbrwsServiceImpl")
public class GdMbrwsServiceImpl implements GdMbrwsService {
	
	@Resource
	private BaseDao baseDao;
	
	@Resource
	private BusinessService businessService;

	@Override
	public List<Map<String, Object>> queryKhztList() {
		String sql = "SELECT T.ID,T.ZTMC  FROM T_RWMBS_KHZT T ORDER BY T.ID ";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public List<Map<String, Object>> queryKhjhList() {
		String sql = "SELECT T.ID,T.JHMC,T.KSNF,T.JSNF FROM T_RWMBS_KHJH T ORDER BY T.KSNF DESC";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public List<Map<String, Object>> queryXkpmList(String zzjgid, String khjhid,String shiroTag) {
		//查询计划
		String sql = "SELECT T.ID, T.KHJH_ID, JH.JHMC, T.KHDW_ID, DW.NAME_ KHDW, T.XKMC, T.KHMB"
				+ "  FROM T_RWMBS_KHJH_XKPMMB T"
				+ " INNER JOIN T_RWMBS_KHJH JH ON T.KHJH_ID = JH.ID"
				+ " INNER JOIN T_CODE_DEPT_TEACH DW ON T.KHDW_ID = DW.ID"
				+ " WHERE T.KHJH_ID = '"+khjhid+"'"
				+ " AND T.KHDW_ID IN ("+businessService.getTeachDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " ORDER BY T.KHDW_ID ";
		List<Map<String,Object>> mblist = baseDao.queryListInLowerKey(sql);
		//查询结果
		sql = "SELECT T.ID,T.DEPT_ID KHDW_ID, T.KHNF,T.KHJG,T.XKMC FROM T_RWMBS_XKPM T "
				+ " INNER JOIN T_RWMBS_KHJH JH ON JH.ID =  '"+khjhid+"' AND T.KHNF BETWEEN JH.KSNF AND JH.JSNF"
				+ " WHERE T.DEPT_ID IN ("+businessService.getTeachDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")";
		List<Map<String,Object>> jglist = baseDao.queryListInLowerKey(sql);
		//组装数据
		for (Map<String,Object> mb : mblist) {
			String khdwid = MapUtils.getString(mb, "khdw_id");
			String xkmc = MapUtils.getString(mb, "xkmc");
			int total = 0;
			for (int i = 0; i < jglist.size(); i++) {
				Map<String,Object> jg = jglist.get(i);
				String jgkhdwid = MapUtils.getString(jg, "khdw_id");
				String jgxkmc = MapUtils.getString(jg, "xkmc");
				if(jgkhdwid.equals(khdwid) && jgxkmc.equals(xkmc)){
					int khjg = MapUtils.getIntValue(jg, "khjg");
					mb.put(MapUtils.getString(jg, "khnf"), khjg);
					total += khjg;
				}
			};
			mb.put("total", total);
		}
		return mblist;
	}

	@Override
	public List<Map<String, Object>> queryKhxmListOfkhzt(String khztid,
			String zzjgid, String khjhid ) {
		//查询主题下的所有考核项目（包含有计划 ，项目，单位，目标）
		String sql = "SELECT T.ID,T.KHJH_ID,JH.JHMC,T.KHDW_ID, DW.NAME_ KHDW,T.KHXM_ID,XM.KHXM KHXM,T.KHMB,XM.VAL_TYPE,ZT.ZTMC "
				+ "  FROM T_RWMBS_KHJH_KHXM T"
				+ "  INNER JOIN T_RWMBS_KHJH JH ON T.KHJH_ID = JH.ID"
				+ "  INNER JOIN T_CODE_DEPT_TEACH DW ON T.KHDW_ID = DW.ID"
				+ "  INNER JOIN T_RWMBS_KHXM XM ON T.KHXM_ID = XM.ID"
				+ " INNER JOIN T_RWMBS_KHZT ZT ON XM.KHZT_ID = ZT.ID"
				+ " WHERE T.KHJH_ID = '"+khjhid+"' AND T.KHDW_ID = '"+zzjgid+"' AND XM.KHZT_ID = '"+khztid+"'"
				+ " ORDER BY T.KHXM_ID";
		List<Map<String,Object>> khxmlist = baseDao.queryListInLowerKey(sql);
		//查询每一个考核项的信息（每年的实际完成量）
		for (Map<String, Object> khxm : khxmlist) {
			int total = 0, count = 0;
			String khxmid = MapUtils.getString(khxm, "khxm_id");
			String valtype = MapUtils.getString(khxm, "val_type");
			sql = "SELECT T.ID,T.KHDW_ID,T.KHXM_ID,T.KHNF,T.KHJG "
				+ " FROM T_RWMBS_KHJG T"
				+ " INNER JOIN T_RWMBS_KHJH JH ON T.KHNF BETWEEN JH.KSNF AND JH.JSNF"
				+ " WHERE T.KHDW_ID = '"+zzjgid+"'  AND T.KHXM_ID ='"+khxmid+"' AND JH.ID = '"+khjhid+"'"
				+ " ORDER BY T.KHNF";
			//遍历考核结果列表，计算总值
			List<Map<String,Object>> khjglist = baseDao.queryListInLowerKey(sql);
			for (Map<String, Object> jg : khjglist) {
				total += MapUtils.getIntValue(jg, "khjg");
				count++;
			}
			if (valtype.equals("number")) {
				khxm.put("total", total);
			}else if(valtype.equals("persent")){
				khxm.put("total", total/(count == 0?1:count));
			}
			khxm.put("detail", khjglist);
		}
		return khxmlist;
	}
	
	
	
}
