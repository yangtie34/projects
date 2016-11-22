package com.jhkj.mosdc.sc.job.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.sc.job.StudentXfmxService;

/**   
* @Description: TODO 学生消费数据相关处理JOB
* @author Sunwg  
* @date 2014-10-31 下午3:22:01   
*/
public class StudentXfmxServiceImpl implements StudentXfmxService{
	
	private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	/** 
	* @Title: queryXsxfqk 
	* @Description: TODO 定时计算学生的消费天数和日均消费额，
	* 		将查询的结果插入表 TB_YKT_PKS_TEMP（学生ID，消费天数，日均消费金额）
	* 		根据计算的数据进行学生贫困情况的分析
	* @return void
	*/
	@SuppressWarnings("rawtypes")
	public void queryXsxfqk(){
		Logger log = Logger.getLogger(StudentXfmxServiceImpl.class);
		long beginTime = System.currentTimeMillis();
		log.info("=======开始查询计算学生的消费天数和日均消费额========");
		String sql = "select t.ryid, count(t.xfsj) xfts, round(avg(t.xfje),2) rjxfje from " +
				"(select t.ryid,sum(t.xfje) xfje,to_char(t.xfsj_date, 'yyyy-mm-dd') xfsj" +
				" from tb_ykt_xfmx t where t.xfsj_date > (sysdate-90) group by t.ryid, to_char(t.xfsj_date, 'yyyy-mm-dd')) t group by t.ryid";
		List<Map> xslist = baseDao.queryListMapInLowerKeyBySql(sql);
		log.info("=======查询学生的消费天数和日均消费金额结束，开始清除临时表的数据========");
		String deleteSql = "delete TB_YKT_PKS_TEMP";
		baseDao.update(deleteSql);
		log.info("=======清除临时表的数据完成，开始插入新的消费数据========");
		int i = 0;
		for (Map map : xslist) {
			String insertSql = "insert into TB_YKT_PKS_TEMP(RYID,XFTS,RJXFJE) VALUES("+map.get("ryid")+","+map.get("xfts")+","+map.get("rjxfje")+")";
			baseDao.insert(insertSql);
			i++;
		}
		log.info("=======插入新的消费数据完成，共插入  " + i +" 条数据,耗时："+(System.currentTimeMillis() - beginTime)/1000+"秒========");
	}
	
	/** 
	* @Title: queryYxxsxfqk 
	* @Description: TODO 查询院系的学生的消费情况
	* @return String
	*/
	@SuppressWarnings("rawtypes")
	public String queryYxxsxfqk(String params){
		Calendar c = Calendar.getInstance();
		int month = c.getTime().getMonth();
		String sql = "select tt.*,count(dxf.ryid) dypjxf,nvl(round(avg(dxf.rjxfje),1),0) dypjxfje," +
				"count(gxf.ryid) gypjxf,nvl(round(avg(gxf.rjxfje),1),0) gypjxfje from " +
				"(select yx.mc yx,yx.id yxid,count(xj.id) zrs,count(pks.ryid) xfdbrs,nvl(round(avg(pks.rjxfje),1),0) pjxf from tb_jxzzjg yx " +
				" left join tb_xjda_xjxx xj on yx.id = xj.yx_id and xj.xjzt_id = 1000000000000101 and xj.xszt_id = 1  and substr(xj.yjbysj,0,4) " +
				((month < 7)?">=":">" ) +
				"to_char(sysdate,'yyyy')" +
				" left join tb_ykt_pks_temp pks on xj.id = pks.ryid and pks.xfts > 30 " +
				" where yx.cclx  = 'YX' group by yx.mc,yx.id )tt" +
				" left join tb_xjda_xjxx xjxx on xjxx.yx_id = tt.yxid" +
				" left join tb_ykt_pks_temp dxf on dxf.rjxfje < tt.pjxf and dxf.ryid = xjxx.id and dxf.xfts > 30 " +
				" left join tb_ykt_pks_temp gxf on gxf.rjxfje > tt.pjxf  and gxf.ryid = xjxx.id and gxf.xfts > 30 " +
				" group by tt.yx,tt.yxid,tt.zrs,tt.xfdbrs,tt.pjxf order by tt.yxid";
		List<Map> list = baseDao.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.list2json(list);
	}
	
	/** 
	* @Title: quertYxyspkxs 
	* @Description: TODO 查询院系疑似贫困学生名单
	* @return String
	*/
	@SuppressWarnings("rawtypes")
	public String quertYxyspkxs(String params){
		JSONObject json = JSONObject.fromObject(params);
		String yxid = json.getString("yxid");
		String yxpje = json.getString("yxpje");
		String zrs = json.getString("zrs");
		String sql = "select xj.xh, xj.xm,zy.mc zy, yx.mc yx,t.rjxfje rjxf,t.xfts,hk.mc hk from tb_ykt_pks_temp t" +
				" inner join tb_xjda_xjxx xj on xj.id = t.ryid and xj.yx_id = "+ yxid +
				" left join tb_jxzzjg zy on zy.id = xj.zy_id " +
				" left join tc_xzqh hk on xj.hkszd_id = hk.id " +
				" inner join tb_jxzzjg yx on yx.id = xj.yx_id" +
				" where t.rjxfje < " + yxpje + " and t.xfts > 30 and rownum <= 0.05*"+zrs+" order by t.rjxfje";
		List<Map> list = baseDao.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.list2json(list);
	}
	
	/** 
	* @Title: queryXfpje 
	* @Description: TODO 查询消费平均额
	* @return String
	*/
	@SuppressWarnings("rawtypes")
	public String queryXfpje(String params){
		String sql = "select dxf.rje dxfrje, gxf.rje gxfrje, pxf.rje pjxfje " +
				" from (select round(avg(d.rjxfje), 1) rje from tb_ykt_pks_temp d " +
				" where d.rjxfje < (select avg(t.rjxfje) from tb_ykt_pks_temp t)) dxf, " +
				" (select round(avg(g.rjxfje), 1) rje from tb_ykt_pks_temp g " +
				" where g.rjxfje > (select avg(t.rjxfje) from tb_ykt_pks_temp t)) gxf, " +
				" (select round(avg(t.rjxfje), 1) rje from tb_ykt_pks_temp t) pxf ";
		List<Map> list = baseDao.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.map2json(list.get(0));
	};
}
