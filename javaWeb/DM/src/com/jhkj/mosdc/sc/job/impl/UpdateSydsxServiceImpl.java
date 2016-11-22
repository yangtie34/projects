package com.jhkj.mosdc.sc.job.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;
import com.jhkj.mosdc.sc.job.UpdateSydsxService;
import com.jhkj.mosdc.sc.job.dao.UpdateSydsxDao;

public class UpdateSydsxServiceImpl  extends BaseServiceImpl implements UpdateSydsxService {
	private UpdateSydsxDao dao;
	
	public void setDao(UpdateSydsxDao dao) {
		this.dao = dao;
	}
	private Map<String,String> SYD_SF = new HashMap<String,String>();
	@Override
	public String updateSydsx(String args) {
		
		// 初始化身份证代码与系统DM 的键值对
		if(SYD_SF.size()==0) initSYD_SF();
		// 查询出所有没有位数生源地属性的学生
		String xsSql ="SELECT * FROM TB_XJDA_XJXX WHERE (SYDSX_ID IS NULL AND SFZH IS NOT NULL) OR SYDSX_ID = 990000";
		String countSql = "SELECT COUNT(*) FROM TB_XJDA_XJXX WHERE (SYDSX_ID IS NULL AND SFZH IS NOT NULL) OR SYDSX_ID = 990000";
		xsSql ="SELECT * FROM TB_JZGXX WHERE (HKSZD IS NULL AND SFZH IS NOT NULL)";
		countSql = "SELECT count(*) FROM TB_JZGXX WHERE (HKSZD IS NULL AND SFZH IS NOT NULL)";
		int count = dao.querySqlCount(countSql);
		int pageSize = 1000,page=count/pageSize+1;
		
		List<Map> xslb = new ArrayList<Map>();
		for(int i=1;i<=page;i++){
			String xsSql2= "SELECT XH,SFZH FROM (SELECT ROWNUM RN , T.* FROM ("+xsSql
					+") T ) WHERE RN BETWEEN "+(pageSize*(i-1)+1)+" AND "+(pageSize*i);
			xsSql2 = "SELECT ZGH AS XH,SFZH FROM (SELECT ROWNUM RN , T.* FROM ("+xsSql
					+") T ) WHERE RN BETWEEN "+(pageSize*(i-1)+1)+" AND "+(pageSize*i);
			xslb = dao.querySqlList(xsSql2);
			List<Map<String,String>> aXslb = new ArrayList<Map<String,String>>();
			try{
				for(Map xs : xslb){
					String sfdm = xs.get("SFZH").toString().substring(0,2);
					String xtdm = SYD_SF.get(sfdm);
					String xh = xs.get("XH").toString();
					Map<String,String> axs = new HashMap<String,String>();
					axs.put("XH", xh);
					axs.put("SYDDM", xtdm);
					
					aXslb.add(axs);
				}
				dao.batchUpdateSydsx(aXslb);
			}catch(Exception e){
				e.printStackTrace();
			}
			System.out.println("更新"+i+"批！");
		}
		
		return "";
		
	}
	/**
	 * 初始化生源地属性信息
	 */
	private void initSYD_SF(){
		String syd = " SELECT QH1.DM,QH1.MC,QH1.YWM,QH2.DM AS XTDM FROM DM_ZXBZ.T_ZXBZ_XZQH QH1" +
				",DM_ZXBZ.T_ZXBZ_XZQH QH2 WHERE QH1.CC IS NULL AND QH1.DM = QH2.LS";
		List<Map> result = dao.querySqlList(syd);
		for(Map temp : result){
			String key =  temp.get("DM").toString();
			String value = temp.get("XTDM").toString();
			SYD_SF.put(key, value);
		}
	}
	@Override
	public String updateStaticData(String args) {
		String rqs = "select substr(tjsj,0,10) as rq,count(*) as zs from tb_ykt_temp_wzs t where substr(tjsj,0,10) between '2014-12-17' AND '2014-12-29' group by substr(tjsj,0,10) order by substr(tjsj,0,10)";
		List<Map> results= dao.querySqlList(rqs);
		for(Map map : results){
			String rq = map.get("RQ").toString();
			int zs = Integer.parseInt(map.get("ZS").toString());
			String xss = "select * from tb_ykt_temp_wzs where tjsj like '"+rq+"%'";
			List<Map> stus = dao.querySqlList(xss);
			
			for(int i = 0,size = stus.size();i<size;i++){
				if(i>=1300){
					break;
				}else{
					String xh = stus.get(i).get("XH").toString();
					String del = "delete from tb_ykt_temp_wzs where xh ='"+xh+"' and tjsj like '"+rq+"%'";
					dao.deleteBySql(del);
				}
			}
		}
		return "";
	}
	
	private int getDeleteCount(int count){
		
		return count;
		
	}
}
