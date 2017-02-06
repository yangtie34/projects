package cn.gilight.research.xkjs.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.research.xkjs.service.InitXkDataService;


@Service("initXkData")
public class InitXkDataServiceImpl implements InitXkDataService{
	
	@Resource
	private BaseDao baseDao;

	@Override
	public void updateNationProject(String year) {
		//承担国家级科研项目（项）  id:1
		String sql = "UPDATE T_RES_XKJS_MX MX SET MX.WCZBS = (SELECT COUNT(*)"
                   +" FROM (SELECT TT.* FROM T_RES_XKJS_XKCY CY INNER JOIN "
                   +" (SELECT TA.TEA_NO, T.* FROM T_RES_PROJECT T "
                   +" LEFT JOIN T_RES_PROJECT_AUTH TA ON TA.PRO_ID = T.ID"
                   +" WHERE TA.TEA_NO IS NOT NULL AND T.LEVEL_CODE = '03'"
                   +" AND TO_NUMBER(SUBSTR(T.END_TIME,0,4)) <= '"+year+"') TT ON TT.TEA_NO = CY.TEA_NO"
                   +" WHERE CY.IS_LEADER = '1') TT WHERE TT.TEA_NO = MX.LEADER_ID)"
                   +" WHERE MX.ZB_ID = '1' AND MX.YEAR_ = '"+year+"'";
		baseDao.update(sql);
	}

	@Override
	public void updateNationProjectFund(String year) {
		//承担国家级科研项目经费（万元） id:2
		String sql = "UPDATE T_RES_XKJS_MX MX SET MX.WCZBS = (SELECT NVL(SUM(TT.FUND),0)"
                   +" FROM (SELECT TT.* FROM T_RES_XKJS_XKCY CY INNER JOIN "
                   +" (SELECT TA.TEA_NO, T.* FROM T_RES_PROJECT T "
                   +" LEFT JOIN T_RES_PROJECT_AUTH TA ON TA.PRO_ID = T.ID"
                   +" WHERE TA.TEA_NO IS NOT NULL AND T.LEVEL_CODE = '03'"
                   +" AND TO_NUMBER(SUBSTR(T.END_TIME,0,4)) <= '"+year+"') TT ON TT.TEA_NO = CY.TEA_NO"
                   +" WHERE CY.IS_LEADER = '1') TT WHERE TT.TEA_NO = MX.LEADER_ID)"
                   +" WHERE MX.ZB_ID = '2' AND MX.YEAR_ = '"+year+"'";
		baseDao.update(sql);
	}

	@Override
	public void updateProvinceProject(String year) {
		//承担省部级科研项目（项） id:3
		String sql = " UPDATE T_RES_XKJS_MX MX SET MX.WCZBS = (SELECT COUNT(*)"
                  +" FROM (SELECT TT.* FROM T_RES_XKJS_XKCY CY INNER JOIN "
                  +" (SELECT TA.TEA_NO, T.* FROM T_RES_PROJECT T "
                  +" LEFT JOIN T_RES_PROJECT_AUTH TA ON TA.PRO_ID = T.ID"
                  +" WHERE TA.TEA_NO IS NOT NULL AND T.LEVEL_CODE = '02'"
                  +" AND TO_NUMBER(SUBSTR(T.END_TIME,0,4)) <= '"+year+"') TT ON TT.TEA_NO = CY.TEA_NO"
                  +" WHERE CY.IS_LEADER = '1') TT WHERE TT.TEA_NO = MX.LEADER_ID)"
                  +" WHERE MX.ZB_ID = '3' AND MX.YEAR_ = '"+year+"'";
		baseDao.update(sql);
	}

	@Override
	public void updateProvinceProjectFund(String year) {
		//承担省部级科研项目经费（万元） id:4
		String sql = " UPDATE T_RES_XKJS_MX MX SET MX.WCZBS = (SELECT NVL(SUM(TT.FUND),0)"
                   +" FROM (SELECT TT.* FROM T_RES_XKJS_XKCY CY INNER JOIN "
                   +" (SELECT TA.TEA_NO, T.* FROM T_RES_PROJECT T "
                   +" LEFT JOIN T_RES_PROJECT_AUTH TA ON TA.PRO_ID = T.ID"
                   +" WHERE TA.TEA_NO IS NOT NULL AND T.LEVEL_CODE = '02'"
                   +" AND TO_NUMBER(SUBSTR(T.END_TIME,0,4)) <= '"+year+"') TT ON TT.TEA_NO = CY.TEA_NO"
                   +" WHERE CY.IS_LEADER = '1') TT WHERE TT.TEA_NO = MX.LEADER_ID)"
                   +" WHERE MX.ZB_ID = '4' AND MX.YEAR_ = '"+year+"'";
		baseDao.update(sql);
	}

	@Override
	public void updateThesisCSSCI(String year) {
		
	}

	@Override
	public void updateThesisIn(String year) {
		//zb_id = 16  被SCI/SSCI/AHCI、EI收录的论文（篇）
		String sql = "UPDATE T_RES_XKJS_MX XKJS SET XKJS.WCZBS =(SELECT NVL(COUNT(*),0) FROM "
                  +" (SELECT XKCY.ID,TT.* FROM T_RES_XKJS_XKCY XKCY LEFT JOIN (SELECT * FROM T_RES_THESIS_IN TA "
                  +" LEFT JOIN T_RES_THESIS_AUTHOR TB ON TA.THESIS_ID=TB.THESIS_ID"
                  +" LEFT JOIN T_CODE TC ON TC.CODE_TYPE='PERIODICAL_TYPE_CODE' AND TC.CODE_=TA.PERIODICAL_TYPE_CODE"
                  +" LEFT JOIN T_RES_THESIS TD ON TD.ID=TA.THESIS_ID WHERE TA.YEAR_<='"+year+"' "
                  +" AND TC.CODE_ IN('006','007','008','009','010') )TT ON TT.TEA_NO=XKCY.TEA_NO"
                  +" WHERE XKCY.IS_LEADER = '1')TS "
                  +" WHERE TS.TEA_NO=XKJS.LEADER_ID )WHERE XKJS.ZB_ID='16' AND XKJS.YEAR_='"+year+"'";
		
		baseDao.update(sql);
	}

	@Override
	public void updateWork(String year) {
		//zb_id = 17 出版专著（部）
		String sql = "UPDATE T_RES_XKJS_MX XKJS SET XKJS.WCZBS = (SELECT COUNT(*) FROM "
                  +" (SELECT XKCY.ID,TT.* FROM T_RES_XKJS_XKCY XKCY INNER JOIN (SELECT * FROM"
                  +" T_RES_WORK WO LEFT JOIN T_RES_WORK_AUTHOR TA ON TA.WORK_ID=WO.ID"
                  +" WHERE SUBSTR(WO.PRESS_TIME,0,4) <= '"+year+"') TT ON TT.TEA_NO = XKCY.TEA_NO "
                  +" WHERE XKCY.IS_LEADER='1')S" 
                  +" WHERE S.TEA_NO = XKJS.LEADER_ID)" 
                  +" WHERE XKJS.ZB_ID = '17' AND XKJS.YEAR_ = '"+year+"'";
		baseDao.update(sql);
	}

	@Override
	public void updatePatent(String year) {
		//zb_id = 18 获得专利（项）
		String sql = "UPDATE T_RES_XKJS_MX XKJS SET XKJS.WCZBS = (SELECT COUNT(*) "
                  +" FROM(SELECT XKCY.ID,TT.* FROM T_RES_XKJS_XKCY XKCY INNER JOIN( "
                  +" SELECT * FROM T_RES_PATENT PT LEFT JOIN T_RES_PATENT_AUTH PA "
                  +" ON PA.PATENT_ID=PT.ID WHERE SUBSTR(PT.ACCREDIT_TIME,0,4) <= '"+year+"')TT "
                  +" ON TT.TEA_NO=XKCY.TEA_NO WHERE XKCY.IS_LEADER='1')S "
                  +" WHERE S.TEA_NO =XKJS.LEADER_ID) "
                  +" WHERE XKJS.ZB_ID = '18' AND XKJS.YEAR_ = '"+year+"'";
		baseDao.update(sql);
	}

	@Override
	public void updateAchievementAward(String year) {
		//获省部级以上科研成果奖励（项） zb_id:20
		String sql = "UPDATE T_RES_XKJS_MX XKJS SET XKJS.WCZBS = (SELECT COUNT(*) FROM("
                  +" SELECT XKCY.ID,TD.* FROM T_RES_XKJS_XKCY XKCY INNER JOIN ("
                  +" SELECT * FROM T_RES_HJCG TA LEFT JOIN T_RES_HJCG_AUTH TB ON TA.ID=TB.OUTCOME_AWARD_ID"
                  +" LEFT JOIN T_CODE TC ON TC.CODE_TYPE='RES_AWARD_LEVEL_CODE' AND TC.CODE_=TA.LEVEL_CODE"
                  +" WHERE TC.CODE_='02' AND SUBSTR(TA.AWARD_TIME,0,4)<='"+year+"')TD ON TD.TEA_NO=XKCY.TEA_NO "
                  +" WHERE XKCY.IS_LEADER='1')S WHERE S.TEA_NO =XKJS.LEADER_ID)"
                  +" WHERE XKJS.ZB_ID = '20' AND XKJS.YEAR_ = '"+year+"'";
		baseDao.update(sql);
	}

	@Override
	public void updatePurchaseEquipment(String year) {
		//购置万元以上仪器设备（件、台） id:37
		String sql = " UPDATE T_RES_XKJS_MX XKJS SET XKJS.WCZBS =(SELECT NVL(SUM(TS.COUNT_),0)"
                  +" FROM (SELECT XKCY.ID,TT.* FROM T_RES_XKJS_XKCY XKCY LEFT JOIN (SELECT T.*"
                  +" FROM T_EQUIPMENT T WHERE TO_NUMBER(T.PRICE) > '10000' AND SUBSTR(T.BUY_DATE, 0, 4) <= '"+year+"') TT"
                  +" ON TT.MANAGER = XKCY.TEA_NO) TS WHERE TS.MANAGER = XKJS.LEADER_ID) WHERE XKJS.ZB_ID = '37'"
                  +" AND XKJS.YEAR_ = '"+year+"'";
		baseDao.update(sql);
	}

	@Override
	public void updatePurchaseEquipmentFund(String year) {
		//仪器设备总值（万元）
		String sql = " UPDATE T_RES_XKJS_MX XKJS SET XKJS.WCZBS =(SELECT NVL(SUM(TO_NUMBER(TA.PRICE*TA.COUNT_))/10000, 0) NUMS"
                  +" FROM (SELECT XKCY.ID,TT.* FROM T_RES_XKJS_XKCY XKCY LEFT JOIN (SELECT T.* FROM T_EQUIPMENT T"
                  +" WHERE TO_NUMBER(T.PRICE) > '10000' AND SUBSTR(T.BUY_DATE, 0, 4) <= '"+year+"') TT"
                  +" ON TT.MANAGER = XKCY.TEA_NO) TA WHERE TA.MANAGER = XKJS.LEADER_ID)"
                  +" WHERE XKJS.ZB_ID = '38' AND XKJS.YEAR_ = '"+year+"'";
		baseDao.update(sql);
	}
	
}
