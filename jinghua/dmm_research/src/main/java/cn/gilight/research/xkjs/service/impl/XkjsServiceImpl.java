package cn.gilight.research.xkjs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.research.xkjs.service.InitXkDataService;
import cn.gilight.research.xkjs.service.XkjsService;

@Service("xkjsService")
public class XkjsServiceImpl implements XkjsService{
	
	@Resource
	private BaseDao baseDao;
	
	@Resource
	private InitXkDataService initXkData;

	@Override
	public Map<String, Object> queryXkryzc(String year) {
		String sql = "SELECT T.XK_ID,XK.NAME_ XKMC,NVL(T.ZRS,0) ZRS,NVL(T.BSS,0) BSS,"
				+ " NVL(T.GJZCRS,0) GJZCRS,NVL(T.SDS,0) SDS,NVL(T.BDS,0) BDS FROM T_RES_XKJS_XKCYXX T "
				+ " INNER JOIN T_RES_XKJS_XK XK ON T.XK_ID = XK.ID "
				+ " WHERE T.YEAR_ = '"+year+"'";
		List<Map<String, Object>> list = baseDao.queryListInLowerKey(sql);
		
		int zrsTotal = 0,bssTotal = 0,gjzcrsTotal = 0,sdsTotal = 0,bdsTotal = 0;
		for (Map<String, Object> it : list) {
			int zrs = MapUtils.getIntValue(it, "zrs");
			zrsTotal += zrs;
			int bss = MapUtils.getIntValue(it, "bss");
			bssTotal += bss;
			int gjzcrs = MapUtils.getIntValue(it, "gjzcrs");
			gjzcrsTotal += gjzcrs;
			int sds = MapUtils.getIntValue(it, "sds");
			sdsTotal += sds;
			int bds = MapUtils.getIntValue(it, "bds");
			bdsTotal += bds;
			zrs = (zrs == 0 ? 1 : zrs);
			it.put("bss_zb", bss*100/zrs + "%");
			it.put("gjzcrs_zb", gjzcrs*100/zrs + "%");
			it.put("sds_zb", sds*100/zrs + "%");
			it.put("bds_zb", bds*100/zrs + "%");
		}
		//生成汇总信息
		Map<String, Object> totalInfo = new HashMap<String, Object>();
		totalInfo.put("zrs", zrsTotal);
		totalInfo.put("bss", bssTotal);
		totalInfo.put("gjzcrs", gjzcrsTotal);
		totalInfo.put("sds", sdsTotal);
		totalInfo.put("bds", bdsTotal);
		zrsTotal = (zrsTotal == 0 ? 1 : zrsTotal);
		totalInfo.put("bss_zb", bssTotal*100/zrsTotal + "%");
		totalInfo.put("gjzcrs_zb", gjzcrsTotal*100/zrsTotal + "%");
		totalInfo.put("sds_zb", sdsTotal*100/zrsTotal + "%");
		totalInfo.put("bds_zb", bdsTotal*100/zrsTotal + "%");
		totalInfo.put("isTotal", true);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", list);
		result.put("total", totalInfo);
		return result;
	}

	@Override
	@Transactional
	public void calculateXkryzc() {
		//检测当前年的统计是否已经执行
		String year = DateUtils.getNowYear();
		String sql = "SELECT COUNT(1) FROM T_RES_XKJS_XKCYXX T WHERE T.YEAR_ = '"+year+"'";
		int nums =  baseDao.queryForInt(sql);
		if (nums == 0) {
			//如果未执行，先初始化学科数据
			sql = "INSERT INTO T_RES_XKJS_XKCYXX(ID,XK_ID,YEAR_) SELECT ID_SEQ.NEXTVAL,T.ID,'"+year+"' FROM T_RES_XKJS_XK T";
			baseDao.insert(sql);
		}
		//计算总人数
		sql = "UPDATE T_RES_XKJS_XKCYXX T SET T.ZRS = (SELECT COUNT(CY.TEA_NO) FROM T_RES_XKJS_XKCY CY WHERE CY.XK_ID = T.XK_ID) WHERE T.YEAR_ = '"+year+"'";
		baseDao.update(sql);
		//计算博士数
		sql = "UPDATE T_RES_XKJS_XKCYXX T SET T.BSS = ("
				+ "SELECT COUNT(1) FROM T_RES_XKJS_XKCY CY "
				+ "INNER JOIN T_TEA TEA ON CY.TEA_NO = TEA.TEA_NO AND TEA.DEGREE_ID IN (SELECT ID FROM T_CODE_DEGREE T START WITH T.ID=2 CONNECT BY T.PID = PRIOR T.ID) "
				+ "WHERE CY.XK_ID = T.XK_ID"
				+ ") WHERE T.YEAR_ = '"+year+"'";
		baseDao.update(sql);
		
		//计算高级职称数
		sql = "UPDATE T_RES_XKJS_XKCYXX T SET T.GJZCRS = ("
				+ "SELECT COUNT(CY.TEA_NO) FROM T_RES_XKJS_XKCY CY "
				+ " INNER JOIN T_TEA TEA ON CY.TEA_NO = TEA.TEA_NO "
				+ " INNER JOIN T_CODE_ZYJSZW ZW ON TEA.ZYJSZW_ID = ZW.ID "
				+ " INNER JOIN T_CODE JB ON JB.CODE_TYPE = 'ZYJSZW_JB_CODE' AND ZW.ZYJSZW_JB_CODE = JB.CODE_  AND JB.CODE_ IN ('01','02')"
				+ " WHERE CY.XK_ID = T.XK_ID"
				+ ") WHERE T.YEAR_ = '"+year+"'";
		baseDao.update(sql);
		
		//计算硕导
		
		//计算博导
		
	}
	
	@Override
	public List<Map<String, Object>> queryZblist() {
		String sql = "SELECT * FROM T_RES_XKJS_ZB ORDER BY TO_NUMBER(ID)";
		return baseDao.queryListInLowerKey(sql);
	}
	
	/** 
	* @Description: 根据年份查询学科建设应完成指标明细
	* @param year
	* @return: Map<String,Object>
	*/
	@Override
	public Map<String, Object> queryYwczbOfYear(String year){
		String sql = "SELECT * FROM T_RES_XKJS_XK ORDER BY ID";
		List<Map<String, Object>> xklist = baseDao.queryListInLowerKey(sql);
		sql = "SELECT TC.ID ZBID,TC.NAME_ ZBMC";
		for (Map<String, Object> xk : xklist) {
			String xkid = MapUtils.getString(xk , "id");
			sql += ",NVL(MAX(DECODE(TD.XK_ID,'"+xkid+"',TD.YWCZBS)),0) \""+xkid+"\"";
		}
		sql +=  ",SUM(TD.YWCZBS) TOTAL FROM T_RES_XKJS_ZB TC"
				+ " INNER JOIN(SELECT * FROM T_RES_XKJS_MX TA LEFT JOIN T_RES_XKJS_XKCY TB ON TB.TEA_NO=TA.LEADER_ID)TD"
				+ " ON TC.ID=TD.ZB_ID WHERE TD.YEAR_ = '"+year+"'"
				+ " GROUP BY TC.ID,TC.NAME_ "
				+ " ORDER BY TO_NUMBER(TC.ID) ";
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("xklist", xklist);
		result.put("xkjsmx", baseDao.queryListInLowerKey(sql));
		return result;
	}

	@Override
	public Map<String, Object> queryZbwclOfYear(String year) {
		String sql = "SELECT * FROM T_RES_XKJS_XK";
		List<Map<String, Object>> xklist = baseDao.queryListInLowerKey(sql);
		sql = "SELECT TC.ID ZBID,TC.NAME_ ZBMC";
		for (Map<String, Object> xk : xklist) {
			String xkid = MapUtils.getString(xk , "id");
			sql += ",MAX(DECODE(TD.XK_ID,'"+xkid+"',ROUND(TD.WCZBS/DECODE(TD.YWCZBS,0,1,TD.YWCZBS)*100)||'%')) \""+xkid+"\"";
		}
		sql +=  "  FROM T_RES_XKJS_ZB TC"
				+ " INNER JOIN (SELECT * FROM T_RES_XKJS_MX TA"
				+ " LEFT JOIN T_RES_XKJS_XKCY TB ON TB.TEA_NO=TA.LEADER_ID)TD "
				+ " ON TD.ZB_ID=TC.ID WHERE TD.YEAR_='"+year+"'"
				+ " GROUP BY TC.ID,TC.NAME_"
				+ " ORDER BY TO_NUMBER(TC.ID)";
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("xklist", xklist);
		result.put("xkjsmx", baseDao.queryListInLowerKey(sql));
		return result;
	}

	@Override
	public List<Map<String, Object>> queryZbjzOfYear(String year) {
		String sql = "SELECT ZB.ID ZBID, ZB.NAME_ ZBMC, SUM(T.WCZBS) WCZBS, SUM(T.YWCZBS) YWCZBS"
				+ " FROM T_RES_XKJS_MX T"
				+ " INNER JOIN T_RES_XKJS_ZB ZB ON ZB.ID = T.ZB_ID"
				+ " WHERE T.YEAR_ = '"+year+"' "
				+ " GROUP BY ZB.ID, ZB.NAME_ ORDER BY TO_NUMBER(ZB.ID)";
		List<Map<String, Object>> list = baseDao.queryListInLowerKey(sql);
		for (Map<String, Object> it : list) {
			float wczbs = MapUtils.getFloatValue(it, "wczbs"),
				ywczbs = MapUtils.getFloatValue(it, "ywczbs");
			ywczbs = (ywczbs == 0 ? 1 : ywczbs);
			it.put("wcl", wczbs * 100 / ywczbs + "%");
		}
		return list;
	}

	@Override
	public Map<String, Object>  queryGxkzbjzOfYearAndZb(String year, String zbid) {
		String sql = "SELECT TC.ID XKID,TC.NAME_ XKMC, SUM(TD.WCZBS) WCZBS, SUM(TD.YWCZBS) YWCZBS "
				+ "FROM T_RES_XKJS_XK TC INNER JOIN (SELECT * FROM T_RES_XKJS_MX TA LEFT JOIN T_RES_XKJS_XKCY TB "
				+ "ON TA.LEADER_ID=TB.TEA_NO)TD ON TC.ID=TD.XK_ID WHERE TD.YEAR_='"+year+"' AND TD.ZB_ID='"+zbid+"'"
				+ "GROUP BY TC.ID,TC.NAME_ ORDER BY TC.ID";
		float wczbzs = 0,ywczbzs = 0;
		List<Map<String, Object>> list = baseDao.queryListInLowerKey(sql);
		for (Map<String, Object> it : list) {
			float wczbs = MapUtils.getFloatValue(it, "wczbs"),
				ywczbs = MapUtils.getFloatValue(it, "ywczbs");
			wczbzs += wczbs;
			ywczbzs += ywczbs;
			ywczbs = (ywczbs == 0 ? 1 : ywczbs);
			it.put("wcl", wczbs * 100 / ywczbs + "%");
		}
		Map<String, Object> total = new HashMap<String, Object>();
		total.put("wczbzs", wczbzs);
		total.put("ywczbzs", ywczbzs);
		ywczbzs = (ywczbzs == 0 ? 1 : ywczbzs);
		total.put("wcl",  wczbzs * 100 / ywczbzs + "%");
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("xklist", list);
		result.put("total", total);
		return result;
	}
	
	@Override
	@Transactional
	public void calculateZbwcsj() {
		//检测当前年的统计是否已经执行
		String year = DateUtils.getNowYear();
		String sql = "SELECT COUNT(1) FROM T_RES_XKJS_MX T WHERE T.YEAR_ = '"+year+"'";
		int nums =  baseDao.queryForInt(sql);
		if (nums == 0) {
			//如果未执行，先初始化学科数据
			sql = "insert into t_res_xkjs_mx(ID,xk_id,zb_id,year_,ywczbs,wczbs) select ID_SEQ.NEXTVAL,tt.xk_id,tt.zb_id,'"+year+"',0,0 from"
					+ " (select xk.id xk_id,zb.id zb_id from t_res_xkjs_xk xk left join t_res_xkjs_zb zb on 1=1) tt";
			baseDao.insert(sql);
		}
		
		//调用查询指标完成量接口
		initXkData.updateNationProject(year);
		initXkData.updateNationProjectFund(year);
		initXkData.updateProvinceProject(year);
		initXkData.updateProvinceProjectFund(year);
		initXkData.updateThesisIn(year);
		initXkData.updateWork(year);
		initXkData.updatePatent(year);
		initXkData.updateAchievementAward(year);
		initXkData.updatePurchaseEquipment(year);
		initXkData.updatePurchaseEquipmentFund(year);
	}

	//个人指标完成率
	@Override
	public List<Map<String, Object>> queryGrzbwcl(String year,String zgh,String xkid) {
		String sql=" SELECT TC.ID ZBID,TC.NAME_ ZBMC,TD.YWCZBS YWCZBS,TD.WCZBS WCZBS,TD.YEAR_ YEAR_"
                +" FROM T_RES_XKJS_ZB TC LEFT JOIN (SELECT * FROM T_RES_XKJS_XKCY TA "
  	            +" LEFT JOIN T_RES_XKJS_MX TB ON TA.TEA_NO = TB.ZB_ID"
                +" LEFT JOIN T_RES_XKJS_XK TE ON TE.YX_ID = TA.XK_ID) TD ON TD.ZB_ID = TC.ID"
                +" WHERE TD.YEAR_ = '"+year+"' AND TD.TEA_NO = '"+zgh+"' AND TD.ZB_ID = '"+xkid+"'";
		List<Map<String, Object>> list = baseDao.queryListInLowerKey(sql);
		for (Map<String, Object> it : list) {
			float wczbs = MapUtils.getFloatValue(it, "wczbs"),
				ywczbs = MapUtils.getFloatValue(it, "ywczbs");
			ywczbs = (ywczbs == 0 ? 1 : ywczbs);
			it.put("wcl", wczbs * 100 / ywczbs + "%");
		}
		return list;
	}
	
	//查询带头人职工号
	@Override
	public List<Map<String, Object>> queryGrzgh(String xkid){
		String sql = "SELECT T.TEA_NO,TEA.NAME_ FROM T_RES_XKJS_XKCY t "
				+ " LEFT JOIN T_TEA TEA ON TEA.TEA_NO = T.TEA_NO WHERE T.XK_ID = '"+xkid+"' "
				+ " AND T.IS_LEADER='1' ORDER BY TEA.NAME_";
		return baseDao.queryListInLowerKey(sql);
	}
	
	//查询学科名称
	@Override
	public List<Map<String, Object>> queryXkmc(){
		String sql = "SELECT * FROM T_RES_XKJS_XK";
		return baseDao.queryListInLowerKey(sql);
	}
	
	/*@RequestMapping("/test/init")
	@Transactional
	@ResponseBody
	public HttpResult testInit() {
		log.debug("学科建设初始化测试数据");
		HttpResult result = new HttpResult();
		try {
			String sql = "DELETE FROM T_RES_XKJS_MX";
			baseDao.delete(sql);
			//取得所有的学科（院系）
			sql = "SELECT * FROM T_RES_XKJS_XK";
			List<Map<String, Object>> xkList = baseDao.queryListInLowerKey(sql);
			//取得所有的指标
			sql = "SELECT * FROM T_RES_XKJS_ZB";
			List<Map<String, Object>> zbList = baseDao.queryListInLowerKey(sql);
			int nowYear = new Integer(DateUtils.getNowYear());
			//插入数据
			List<TResXkjsMx> resultlist = new ArrayList<TResXkjsMx>();
			for (int i = 0; i < 2; i++) {
				String curYear = (nowYear - i*2) + "";
				for (Map<String,Object> xk : xkList) {
					for (Map<String, Object> zb : zbList) {
						TResXkjsMx mx = new TResXkjsMx();
						mx.setXkId(MapUtils.getString(xk, "id"));
						mx.setZbId(MapUtils.getString(zb, "id"));
						mx.setYear(curYear);
						mx.setWczbs((new Float(Math.random()*10)).intValue());
						mx.setYwczbs((new Float(Math.random()*8)).intValue());
						resultlist.add(mx);
					}
				}
			}
			hibernate.saveAll(resultlist);
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}*/
}
