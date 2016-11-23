package com.jhkj.mosdc.jxpg.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.jxpg.service.JxpgJxzyService;
import com.jhkj.mosdc.jxpg.util.JxpgUtil;

public class JxpgJxzyServiceImpl implements JxpgJxzyService {
	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public void saveInitTs() {
		//图书总量
		String tszlSql = "select count(*) from t_ts";
		int tszl = this.baseDao.querySqlCount(tszlSql);
		String utszlSql = "update tb_jxpg_jxzy_xyw2ts set xxqk_t="+tszl+" where id=4";
		this.baseDao.update(utszlSql);
		//学生总量
		String xszlSql="select count(*) from tb_xjda_xjxx where xjzt_id='1000000000000101'";
		int xszl = this.baseDao.querySqlCount(xszlSql);
		//生均图书量
		int sjts = tszl/xszl;
		String usjtsSql = "update tb_jxpg_jxzy_xyw2ts set xxqk_t="+sjts+" where id=5";
		this.baseDao.update(usjtsSql);
		//当年图书总量
		String dntszlSql ="select count(*) from t_ts where substr(rdrq,0,4) = to_char(sysdate,'yyyy')";
		int dntszl = this.baseDao.querySqlCount(dntszlSql);
		String udntszlSql ="update tb_jxpg_jxzy_xyw2ts set xxqk_t="+dntszl+" where id=8";
		this.baseDao.update(udntszlSql);
		//生均年进纸质图书量
		int dnsjts = dntszl/xszl;
		String udnsjtsSql ="update tb_jxpg_jxzy_xyw2ts set xxqk_t="+dnsjts+" where id=9";
		this.baseDao.update(udnsjtsSql);
		//图书流通率
		String ltlSql = "select round(count(jy.dztm)/count(t.wid)*100,4) as ltl "
				+ "from t_ts t left join t_ts_jy jy on jy.tstm = t.tstm "
				+ "left join dm_zxbz.tb_zxbz_gcdm fl on t.gcdm = fl.dm ";
		List<Map> ltlList = this.baseDao.querySqlList(ltlSql);
		if(ltlList.size()!=0){
			Map m = ltlList.get(0);
			String ltl = MapUtils.getString(m, "LTL");
			String ultlSql = "update tb_jxpg_jxzy_xyw2ts set xxqk_t="+ltl+" where id=11";
			this.baseDao.update(ultlSql);
		}
		
		
		
	}
	//各教学单位开设情况
	public void saveInitDwKsQk(){
		String sql = "select id,mc from tb_jxzzjg t where t.cclx='YX' order by id";
		List<Map> list = this.baseDao.querySqlList(sql);
		
		for(Map m : list){
			Long id = MapUtils.getLong(m, "ID");
			String mc = MapUtils.getString(m, "MC");
			String  JSRJSKS="";
			String zks = this.getZksByDwId(id);
			String zrs = this.getRsByDwId(id);
			if(zrs!="" && !"".equals(zrs)){
				JSRJSKS = Integer.parseInt(zks)/Integer.parseInt(zrs)+""; 
				if(Integer.parseInt(JSRJSKS)<0.5){
					JSRJSKS="0.5";
				}else if(Integer.parseInt(JSRJSKS)>0.5 && Integer.parseInt(JSRJSKS)<1){
					JSRJSKS="1";
				}
				this.updateSql(mc, JSRJSKS, id);
			}else{
				this.updateSql(mc, JSRJSKS, id);
			}
			
		}
	}
	//获取学生总人数
	private int getXsNumById(Long zyId){
		String xszlSql="select count(*) from tb_xjda_xjxx where xjzt_id='1000000000000101'";
		StringBuffer strb = new StringBuffer(xszlSql);
		if(zyId!=null){
			strb.append(" and zy_id="+zyId);
		}
		int xszl = this.baseDao.querySqlCount(strb.toString());
		return xszl;
	}
	
	
	//获取院系教授、副教授上课总时
	private String getZksByDwId(Long dwId){
		String ksSql="select zks from (select id,mc,sum(kss) as zks"
				+ " from (select id,mc,zykc.* from tb_jxzzjg jg,"
				+ "(select bj.fjd_id,kc.* from tb_xxzy_bjxxb bj ,"
				+ "(select kc.bjdm,sum(kc.skzc) as kss from t_kcapzb kc,tb_jzgxx jzg "
				+ "where kc.jszgh=jzg.zgh and jzg.zyjszc_id in(11,12) group by kc.bjdm) kc "
				+ "where bj.id=kc.bjdm ) zykc  where jg.id=zykc.fjd_id ) group by id,mc order by id) where id="+dwId;
		List<Map> list = this.baseDao.querySqlList(ksSql);
		if(list.size()!=0){
			Map m = list.get(0);
			return MapUtils.getString(m, "ZKS");
		}
		return "";
	}
	//获取院系教授、副教授上课时人数
	private String getRsByDwId(Long dwId){
		String sql="select zrs from (select id,mc,sum(rs) as zrs "
				+ "from(select jg.id,mc,rs from tb_jxzzjg jg,"
				+ "(select id,fjd_id,rs from tb_xxzy_bjxxb t ,"
				+ "(select bjdm from t_kcapzb kc,tb_jzgxx jzg  "
				+ "where kc.jszgh=jzg.zgh and jzg.zyjszc_id in(11,12) "
				+ "group by bjdm) t1 "
				+ "where t.id=t1.bjdm) rs where jg.id=rs.fjd_id) group by id,mc order by id) "
				+ "where id="+dwId;
		List<Map> list = this.baseDao.querySqlList(sql);
		if(list.size()!=0){
			Map m = list.get(0);
			return MapUtils.getString(m, "ZRS");
		}
		return "";
	}
	
	private void updateSql(String mc,String JSRJSKS,Long id){
		String ssql = "select * from tb_jxpg_jxzy_jxdwksqk where dwid="+id;
		List l = this.baseDao.querySqlList(ssql);
		if(l.size()!=0){
			String uSql ="update tb_jxpg_jxzy_jxdwksqk set dw='"+mc+"',JSRJSKS_T='"+JSRJSKS+"' where dwid="+id;
			this.baseDao.update(uSql);
		}else{
			SimpleDateFormat ndf = new SimpleDateFormat("yyyy");
			String year = ndf.format(new Date());
			String xn = JxpgUtil.getNowXn();
			String iid = year+id.toString();
			String iSql = "insert into tb_jxpg_jxzy_jxdwksqk(id,dw,jsrjsks_t,xn,dwid)"
					+ " values('"+iid+"','"+mc+"','"+JSRJSKS+"','"+xn+"','"+id+"')";
			this.baseDao.insert(iSql);
		}
		
		
	}

	@Override
	public void saveInitXsNum() {
		
		String[]  ta = {"TB_JXPG_JXZY_ZYQK","TB_JXPG_JXZY_YSZY","TB_JXPG_JXZY_XSZY"}; 
		
		for(String str : ta){
			
			String sql = "select zyid from "+str;
			List<Map> list = this.baseDao.querySqlList(sql);
			for(Map m : list){
				String zyid = MapUtils.getString(m, "ZYID");
				if(zyid!=null && !"".equals(zyid)){
					Long zyId = Long.parseLong(zyid);
					int xsn = this.getXsNumById(zyId);
					String usql = "update "+str+" set BKXSS_T="+xsn+" where zyid="+zyId;
					this.baseDao.update(usql);
				}
				
			}
			
		}
		
		
	}

	

	
}
