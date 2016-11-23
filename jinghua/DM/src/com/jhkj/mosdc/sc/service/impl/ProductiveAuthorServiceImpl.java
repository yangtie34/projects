package com.jhkj.mosdc.sc.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.types.CommandlineJava.SysProperties;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.Utils4Service;
import com.jhkj.mosdc.sc.service.ProductiveAuthorService;

public class ProductiveAuthorServiceImpl implements ProductiveAuthorService {

	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public String getTotalAndChangeByYear(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String year = json.get("year").toString();
		String deptId = json.get("deptId").toString();
		StringBuffer sql = new StringBuffer("select count(*) zznum from (select people_id, count(*) lunum from (select ta.people_id from (select * from t_res_thesis_author where people_id is not null) ta left join t_res_thesis tr on ta.thesis_id = tr.id left join tb_jzgxx tj on ta.people_id = tj.zgh where tr.year_='" + year + "'");
		if (!"0".equals(deptId)) {
			sql.append("and tj.dwd_id = '" + deptId + "'");
		}
		sql.append(") a group by people_id having count(*)>2) b");

		int currentYearTotal = this.baseDao.querySqlCount(sql.toString());
		int prevYear = Integer.parseInt(year) - 1;

		StringBuffer prevSql = new StringBuffer("select count(*) zznum from (select people_id, count(*) lunum from (select ta.people_id from (select * from t_res_thesis_author where people_id is not null) ta left join t_res_thesis tr on ta.thesis_id = tr.id left join tb_jzgxx tj on ta.people_id = tj.zgh where tr.year_='" + prevYear + "'");
		if (!"0".equals(deptId)) {
			prevSql.append("and tj.dwd_id = '" + deptId + "'");
		}
		prevSql.append(") a group by people_id having count(*)>2) b");

		int prevYearTotal = this.baseDao.querySqlCount(prevSql.toString());
		String change = (currentYearTotal - prevYearTotal > 0) ? "增加" + (currentYearTotal - prevYearTotal) : "减少" + (prevYearTotal - currentYearTotal);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("year", year);
		result.put("currentYearTotal", currentYearTotal);
		result.put("change", change);
		return JSONObject.fromObject(result).toString();
	}

	/**
	 * 说明：如果tb_jzgxx表中对应的xwd_id在t_code_degree表中不存在，则数据会归到未维护的数据中
	 */
	@Override
	public String getXwByYear(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String year = json.get("year").toString();
		String deptId = json.get("deptId").toString();
		StringBuffer sql = new StringBuffer("select decode(tcd1.name_, '全部数据', '未维护', tcd1.name_) name, f.zznum value from (select substr(e.path_, 0, 6) path_, count(*) zznum  from (select d.*, nvl(tcd.path_, '000') path_ from (select b.people_id, nvl(c.xwd_id, '-1') xwd_id from (select a.people_id, count(*) zznum from (select ta.people_id from (select * from t_res_thesis_author where people_id is not null) ta left join t_res_thesis tr on ta.thesis_id = tr.id left join tb_jzgxx tj on ta.people_id = tj.zgh where tr.year_= '" + year + "'");
		if (!"0".equals(deptId)) {
			sql.append(" and tj.dwd_id = '" + deptId + "'");
		}
		sql.append(") a group by a.people_id having count(*) > 2) b left join tb_jzgxx c on b.people_id = c.zgh) d left join t_code_degree tcd on d.xwd_id = tcd.id) e group by substr(e.path_, 0, 6)) f left join t_code_degree tcd1 on f.path_ = tcd1.path_ order by f.zznum");
		return Struts2Utils.list2json(baseDao.querySqlList(sql.toString()));
	}

	@Override
	public String getXlByYear(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String year = json.get("year").toString();
		String deptId = json.get("deptId").toString();
		StringBuffer sql = new StringBuffer("select decode(tcd1.name_, '全部数据', '未维护', tcd1.name_) name, f.zznum value from (select substr(e.path_, 0, 6) path_, count(*) zznum  from (select d.*, nvl(tcd.path_, '000') path_ from (select b.people_id, nvl(c.whc_id, '-1') whc_id from (select a.people_id, count(*) zznum from (select ta.people_id from (select * from t_res_thesis_author where people_id is not null) ta left join t_res_thesis tr on ta.thesis_id = tr.id left join tb_jzgxx tj on ta.people_id = tj.zgh where tr.year_= '" + year + "'");
		if (!"0".equals(deptId)) {
			sql.append(" and tj.dwd_id = '" + deptId + "'");
		}
		sql.append(") a group by a.people_id having count(*) > 2) b left join tb_jzgxx c on b.people_id = c.zgh) d left join t_code_education tcd on d.whc_id = tcd.id) e group by substr(e.path_, 0, 6)) f left join t_code_education tcd1 on f.path_ = tcd1.path_ order by f.zznum");
		return Struts2Utils.list2json(baseDao.querySqlList(sql.toString()));
	}

	@Override
	public String getZcByYear(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String year = json.get("year").toString();
		String deptId = json.get("deptId").toString();
		StringBuffer sql = new StringBuffer("select decode(tcd1.name_, '全部数据', '未维护', tcd1.name_) name, f.zznum value from (select substr(e.path_, 0, 6) path_, count(*) zznum  from (select d.*, nvl(tcd.path_, '000') path_ from (select b.people_id, nvl(c.zyjszw_id, '-1') zyjszw_id from (select a.people_id, count(*) zznum from (select ta.people_id from (select * from t_res_thesis_author where people_id is not null) ta left join t_res_thesis tr on ta.thesis_id = tr.id left join tb_jzgxx tj on ta.people_id = tj.zgh where tr.year_= '" + year + "'");
		if (!"0".equals(deptId)) {
			sql.append(" and tj.dwd_id = '" + deptId + "'");
		}
		sql.append(") a group by a.people_id having count(*) > 2) b left join tb_jzgxx c on b.people_id = c.zgh) d left join t_code_zyjszw tcd on d.zyjszw_id = tcd.id) e group by substr(e.path_, 0, 6)) f left join t_code_zyjszw tcd1 on f.path_ = tcd1.path_ order by f.zznum");
		return Struts2Utils.list2json(baseDao.querySqlList(sql.toString()));
	}

	@Override
	public String getLxsjByYear(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String year = json.get("year").toString();
		String deptId = json.get("deptId").toString();
		StringBuffer sql = new StringBuffer("select case when e.rxsj <= 3 then '3年以内' when e.rxsj > 3 and e.rxsj <=5 then '3-5年' when e.rxsj >5 and e.rxsj <=10 then '5-10年' when e.rxsj > 10 then '10年以上' else '未维护' end name, count(*) value from (select a.people_id,(a.year_ - substr(a.rxrq, 0, 4)) rxsj, count(*) zznum from (select ta.people_id,tr.year_, tj.rxrq from (select * from t_res_thesis_author where people_id is not null) ta left join t_res_thesis tr on ta.thesis_id = tr.id left join tb_jzgxx tj on ta.people_id = tj.zgh where tr.year_='" + year + "'");
		if (!"0".equals(deptId)) {
			sql.append(" and tj.dwd_id='" + deptId + "'");
		}
		sql.append(") a group by a.people_id,a.year_, a.rxrq having count(*) > 2) e group by case when e.rxsj <= 3 then '3年以内' when e.rxsj > 3 and e.rxsj <=5 then '3-5年' when e.rxsj >5 and e.rxsj <=10 then '5-10年' when e.rxsj > 10 then '10年以上' else '未维护' end order by sum(e.zznum)");
		return Struts2Utils.list2json(baseDao.querySqlList(sql.toString()));
	}

	@Override
	public String getPylxByYear(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String year = json.get("year").toString();
		String deptId = json.get("deptId").toString();

		return Struts2Utils.list2json(null);
	}

	@Override
	public String getXyByYear(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String year = json.get("year").toString();
		String deptId = json.get("deptId").toString();

		return Struts2Utils.list2json(null);
	}

	@Override
	public String getAgeScatterByYear(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String year = json.get("year").toString();
		String deptId = json.get("deptId").toString();
		StringBuffer sql = new StringBuffer("select decode(c.mc,null,'未维护',c.mc) mc , decode(b.age,null, '未维护', b.age) age, b.zznum from (select a.people_id,(a.year_ - substr(a.csrq, 0, 4)) age,a.xb_id, count(*) zznum from (select ta.people_id,tr.year_, tj.csrq, tj.xb_id from (select * from t_res_thesis_author where people_id is not null) ta left join t_res_thesis tr on ta.thesis_id = tr.id left join tb_jzgxx tj on ta.people_id = tj.zgh where tr.year_='" + year + "'");
		if (!"0".equals(deptId)) {
			sql.append(" and tj.dwd_id = '" + deptId + "'");
		}
		sql.append(") a group by a.people_id,a.year_, a.csrq, a.xb_id having count(*) > 2) b left join (select wid, mc from dm_zxbz.t_zxbz_xb) c on b.xb_id = c.wid");
		return Struts2Utils.list2json(baseDao.querySqlList(sql.toString()));
	}

	@Override
	public String getLngczzsbhjsByYear(String params) {
		String sql = "select d.lwnum, d.year_, d.zznum from (select case when lwnum='3' then '发表三篇' when lwnum ='4' then '发表四篇' else '发表五篇及以上' end lwnum, c.year_, sum(c.zznum) zznum from (select year_, lwnum, count(*) zznum  from (select * from (select a.people_id,a.year_, count(*) lwnum from (select ta.people_id, tr.year_ from (select * from t_res_thesis_author where people_id is not null) ta left join t_res_thesis tr on ta.thesis_id = tr.id) a group by a.people_id, a.year_) a where a.lwnum>2)b group by year_, lwnum) c group by case when lwnum='3' then '发表三篇' when lwnum ='4' then '发表四篇' else '发表五篇及以上' end, c.year_)  d order by d.year_";
		List<Map<String, Object>> result = baseDao.querySqlList(sql.toString());
		Set<Integer> years = new HashSet<Integer>();
		for (Iterator<Map<String, Object>> i = result.iterator(); i.hasNext();) {
			Map<String, Object> map = i.next();
			years.add(Integer.parseInt(map.get("YEAR_").toString()));
		}
		List<Integer> xData = new ArrayList<Integer>();
		List<Object> threeData = new ArrayList<Object>();
		List<Object> fourData = new ArrayList<Object>();
		List<Object> fiveData = new ArrayList<Object>();
		Integer yearMax = Collections.max(years);
		Integer yearMin = Collections.min(years);
		for (int i = yearMin; i <= yearMax; i++) {
			xData.add(i);
		}
		for (Iterator<Integer> j = xData.iterator(); j.hasNext();) {
			Integer year = j.next();
			boolean flag = true;
			// 封装三篇
			for (Iterator<Map<String, Object>> k = result.iterator(); k.hasNext();) {
				Map<String, Object> map = k.next();
				Integer element = Integer.parseInt(map.get("YEAR_").toString());
				String type = map.get("LWNUM").toString();
				if (element.equals(year) && "发表三篇".equals(type.trim())) {
					Integer zznum = Integer.parseInt(map.get("ZZNUM").toString());
					threeData.add(zznum);
					flag = false;
				}
			}
			if (flag) {
				threeData.add(0);
			}
			// 封装四篇
			flag = true;
			for (Iterator<Map<String, Object>> k = result.iterator(); k.hasNext();) {
				Map<String, Object> map = k.next();
				Integer element = Integer.parseInt(map.get("YEAR_").toString());
				String type = map.get("LWNUM").toString();
				if (element.equals(year) && "发表四篇".equals(type.trim())) {
					Integer zznum = Integer.parseInt(map.get("ZZNUM").toString());
					fourData.add(zznum);
					flag = false;
				}
			}
			if (flag) {
				fourData.add(0);
			}
			// 封装五篇及五篇以上
			flag = true;
			for (Iterator<Map<String, Object>> k = result.iterator(); k.hasNext();) {
				Map<String, Object> map = k.next();
				Integer element = Integer.parseInt(map.get("YEAR_").toString());
				String type = map.get("LWNUM").toString();
				if (element.equals(year) && "发表五篇及以上".equals(type.trim())) {
					Integer zznum = Integer.parseInt(map.get("ZZNUM").toString());
					fiveData.add(zznum);
					flag = false;
				}
			}
			if (flag) {
				fiveData.add(0);
			}
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("xData", xData);
		resultMap.put("threeData", threeData);
		resultMap.put("fourData", fourData);
		resultMap.put("fiveData", fiveData);
		return JSONObject.fromObject(resultMap).toString();
	}

	@Override
	public String getGdwdbByType(String params) {

		JSONObject json = JSONObject.fromObject(params);
		String type = json.get("type").toString();
		String year = json.get("year").toString();
		String deptId = json.get("deptId").toString();
		StringBuffer sql = new StringBuffer();
		Set<String> xData = new HashSet<String>();
		Set<String> legendData = new HashSet<String>();
		List<Map<String, Object>> serieData = new ArrayList<Map<String, Object>>();
		if ("xueWeiPie".equals(type)) {
			sql.append("select nvl(tbx.mc, '未维护') mc, tbx.id, g.name_, g.zznum  from (select decode(tcd1.name_, '全部数据', '未维护', tcd1.name_) name_, f.zznum, f.dwd_id from (select substr(e.path_, 0, 6) path_, e.dwd_id, count(*) zznum from (select d.*, nvl(tcd.path_, '000') path_ from (select b.people_id, nvl(c.xwd_id, '-1') xwd_id, c.dwd_id from (select a.people_id, count(*) zznum from (select ta.people_id from (select * from t_res_thesis_author where people_id is not null) ta left join t_res_thesis tr on ta.thesis_id = tr.id left join tb_jzgxx tj on ta.people_id = tj.zgh where tr.year_ = '" + year + "'");
			if (!"0".equals(deptId)) {
				sql.append(" and tj.dwd_id = '" + deptId + "'");
			}
			sql.append(") a group by a.people_id having count(*) > 2) b left join tb_jzgxx c on b.people_id = c.zgh) d left join t_code_degree tcd on d.xwd_id = tcd.id) e group by substr(e.path_, 0, 6), e.dwd_id  ) f left join t_code_degree tcd1 on f.path_ = tcd1.path_ order by f.zznum) g left join tb_xzzzjg tbx on g.dwd_id = tbx.id");
		}

		if ("xueliPie".equals(type)) {
			sql.append("select nvl(tbx.mc, '未维护') mc, tbx.id, g.name_, g.zznum  from (select decode(tcd1.name_, '全部数据', '未维护', tcd1.name_) name_, f.zznum, f.dwd_id from (select substr(e.path_, 0, 6) path_, e.dwd_id, count(*) zznum from (select d.*, nvl(tcd.path_, '000') path_ from (select b.people_id, nvl(c.whc_id, '-1') whc_id, c.dwd_id from (select a.people_id, count(*) zznum from (select ta.people_id from (select * from t_res_thesis_author where people_id is not null) ta left join t_res_thesis tr on ta.thesis_id = tr.id left join tb_jzgxx tj on ta.people_id = tj.zgh where tr.year_ = '" + year + "'");
			if (!"0".equals(deptId)) {
				sql.append(" and tj.dwd_id = '" + deptId + "'");
			}
			sql.append(") a group by a.people_id having count(*) > 2) b left join tb_jzgxx c on b.people_id = c.zgh) d left join t_code_education tcd on d.whc_id = tcd.id) e group by substr(e.path_, 0, 6), e.dwd_id  ) f left join t_code_education tcd1 on f.path_ = tcd1.path_ order by f.zznum) g left join tb_xzzzjg tbx on g.dwd_id = tbx.id");
		}

		if ("zhichengPie".equals(type)) {
			sql.append("select nvl(tbx.mc, '未维护') mc, tbx.id, g.name_, g.zznum  from (select decode(tcd1.name_, '全部数据', '未维护', tcd1.name_) name_, f.zznum, f.dwd_id from (select substr(e.path_, 0, 6) path_, e.dwd_id, count(*) zznum from (select d.*, nvl(tcd.path_, '000') path_ from (select b.people_id, nvl(c.zyjszw_id, '-1') zyjszw_id, c.dwd_id from (select a.people_id, count(*) zznum from (select ta.people_id from (select * from t_res_thesis_author where people_id is not null) ta left join t_res_thesis tr on ta.thesis_id = tr.id left join tb_jzgxx tj on ta.people_id = tj.zgh where tr.year_ = '" + year + "'");
			if (!"0".equals(deptId)) {
				sql.append(" and tj.dwd_id = '" + deptId + "'");
			}
			sql.append(") a group by a.people_id having count(*) > 2) b left join tb_jzgxx c on b.people_id = c.zgh) d left join t_code_zyjszw tcd on d.zyjszw_id = tcd.id) e group by substr(e.path_, 0, 6), e.dwd_id  ) f left join t_code_zyjszw tcd1 on f.path_ = tcd1.path_ order by f.zznum) g left join tb_xzzzjg tbx on g.dwd_id = tbx.id");
		}

		if ("laixiaoshijianPie".equals(type)) {
			sql.append("select nvl(d.mc, '未维护') mc , d.id, case when d.rxsj <= 3 then '3年以内' when d.rxsj > 3 and d.rxsj <= 5 then '3-5年' when d.rxsj > 5 and d.rxsj <= 10 then '5-10年' when d.rxsj > 10 then  '10年以上' else  '未维护' end name_, count(*) zznum from (select c.people_id, c.rxsj,  c.lwnum, tbx.mc, tbx.id  from (select b.*, c.dwd_id, c.xm, nvl(c.xwd_id, '-1') xwd_id from (select a.people_id, (a.year_ - substr(a.rxrq, 0, 4)) rxsj, count(*) lwnum from (select ta.people_id, tr.year_, tj.rxrq from (select *  from t_res_thesis_author where people_id is not null) ta left join t_res_thesis tr on ta.thesis_id = tr.id left join tb_jzgxx tj on ta.people_id = tj.zgh where tr.year_ = '" + year + "'");
			if (!"0".equals(deptId)) {
				sql.append(" and tj.dwd_id = '" + deptId + "'");
			}
			sql.append(") a group by a.people_id, a.year_, a.rxrq having count(*) > 2) b left join tb_jzgxx c on b.people_id = c.zgh) c left join tb_xzzzjg tbx on c.dwd_id = tbx.id) d group by case when d.rxsj <= 3 then '3年以内' when d.rxsj > 3 and d.rxsj <= 5 then '3-5年' when d.rxsj > 5 and d.rxsj <= 10 then '5-10年' when d.rxsj > 10 then  '10年以上' else  '未维护' end,d.mc, d.id");
		}

		List<Map<String, Object>> result = baseDao.querySqlList(sql.toString());
		if (result.size() > 0) {
			for (Iterator<Map<String, Object>> i = result.iterator(); i.hasNext();) {
				Map<String, Object> map = i.next();
				xData.add(map.get("MC").toString());
				legendData.add(map.get("NAME_").toString());
			}
			for (Iterator<String> j = legendData.iterator(); j.hasNext();) {
				String legend = j.next();
				Map<String, Object> mh = new HashMap<String, Object>();
				List<Integer> sData = new ArrayList<Integer>();
				for (Iterator<String> k = xData.iterator(); k.hasNext();) {
					String x = k.next();
					boolean flag = true;
					for (Iterator<Map<String, Object>> m = result.iterator(); m.hasNext();) {
						Map<String, Object> lwNumMap = m.next();
						int lwNum = Integer.parseInt(lwNumMap.get("ZZNUM").toString());
						if (legend.trim().equals(lwNumMap.get("NAME_").toString()) && x.trim().equals(lwNumMap.get("MC").toString())) {
							sData.add(lwNum);
							flag = false;
						}
					}
					if (flag) {
						sData.add(0);
					}
				}
				mh.put("name", legend);
				mh.put("type", "bar");
				mh.put("data", sData);
				serieData.add(mh);
			}
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("xData", xData);
		data.put("legendData", legendData);
		data.put("serieData", serieData);
		return JSONObject.fromObject(data).toString();
	}

	@Override
	public String getLnqsByType(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String type = json.get("type").toString();
		String deptId = json.get("deptId").toString();
		StringBuffer sql = new StringBuffer();
		List<Integer> xData = new ArrayList<Integer>();
		Set<String> legendData = new HashSet<String>();
		List<Map<String, Object>> serieData = new ArrayList<Map<String, Object>>();
		if ("xueWeiPie".equals(type)) {
			sql.append("select * from ( select e.year_, e.name_, sum(e.zznum) zznum from (select d.year_, d.zznum, decode(tcd.name_,  '全部数据', '未维护', null, '未维护', tcd.name_) name_ from (select c.year_, substr(c.path_, 0, 6) path_, count(*) zznum from (select b.year_, b.xwd_id, lwnum, tcd.name_, tcd.path_ from (select a.year_, a.xwd_id, count(*) lwnum  from (select ta.people_id, tr.year_, nvl(tj.xwd_id, '-1') xwd_id from (select * from t_res_thesis_author where people_id is not null) ta left join t_res_thesis tr on ta.thesis_id = tr.id left join tb_jzgxx tj on ta.people_id = tj.zgh where 1=1 ");
			if (!"0".equals(deptId)) {
				sql.append(" and tj.dwd_id = '" + deptId + "'");
			}
			sql.append(") a group by a.people_id, a.year_, a.xwd_id having count(*) > 2) b  left join t_code_degree tcd on b.xwd_id = tcd.id) c group by substr(c.path_, 0, 6), c.year_) d  left join t_code_degree tcd  on d.path_ = tcd.path_) e group by e.year_, e.name_ ) f");
		}
		if ("xueliPie".equals(type)) {
			sql.append("select * from ( select e.year_, e.name_, sum(e.zznum) zznum from (select d.year_, d.zznum, decode(tcd.name_,  '全部数据', '未维护', null, '未维护', tcd.name_) name_ from (select c.year_, substr(c.path_, 0, 6) path_, count(*) zznum from (select b.year_, b.whc_id, lwnum, tcd.name_, tcd.path_ from (select a.year_, a.whc_id, count(*) lwnum  from (select ta.people_id, tr.year_, nvl(tj.whc_id, '-1') whc_id from (select * from t_res_thesis_author where people_id is not null) ta left join t_res_thesis tr on ta.thesis_id = tr.id left join tb_jzgxx tj on ta.people_id = tj.zgh where 1 = 1");
			if (!"0".equals(deptId)) {
				sql.append(" and tj.dwd_id = '" + deptId + "'");
			}
			sql.append(") a group by a.people_id, a.year_, a.whc_id having count(*) > 2) b  left join t_code_education tcd on b.whc_id = tcd.id) c group by substr(c.path_, 0, 6), c.year_) d  left join t_code_education tcd  on d.path_ = tcd.path_) e group by e.year_, e.name_ )f");
		}
		if ("zhichengPie".equals(type)) {
			sql.append("select * from ( select e.year_, e.name_, sum(e.zznum) zznum from (select d.year_, d.zznum, decode(tcd.name_,  '全部数据', '未维护', null, '未维护', tcd.name_) name_ from (select c.year_, substr(c.path_, 0, 6) path_, count(*) zznum from (select b.year_, b.zyjszw_id, lwnum, tcd.name_, tcd.path_ from (select a.year_, a.zyjszw_id, count(*) lwnum  from (select ta.people_id, tr.year_, nvl(tj.zyjszw_id, '-1') zyjszw_id from (select * from t_res_thesis_author where people_id is not null) ta left join t_res_thesis tr on ta.thesis_id = tr.id left join tb_jzgxx tj on ta.people_id = tj.zgh where 1 = 1");
			if (!"0".equals(deptId)) {
				sql.append(" and tj.dwd_id = '" + deptId + "'");
			}
			sql.append(") a group by a.people_id, a.year_, a.zyjszw_id having count(*) > 2) b  left join t_code_zyjszw tcd on b.zyjszw_id = tcd.id) c group by substr(c.path_, 0, 6), c.year_) d  left join t_code_zyjszw tcd  on d.path_ = tcd.path_) e group by e.year_, e.name_ )f");
		}
		if ("laixiaoshijianPie".equals(type)) {
			sql.append("select * from (select case when e.rxsj <= 3 then '3年以内' when e.rxsj > 3 and e.rxsj <= 5 then '3-5年' when e.rxsj > 5 and e.rxsj <= 10 then '5-10年' when e.rxsj > 10 then  '10年以上' else  '未维护' end name_, year_, count(*) zznum from (select  c.countnum, c.year_,(c.year_ - d.rxrq) rxsj from (select people_id, year_,  countnum from (select people_id, a.year_, count(*) countnum from (select ta.people_id, tr.year_ from (select * from t_res_thesis_author where people_id is not null) ta left join t_res_thesis tr on ta.thesis_id = tr.id left join tb_jzgxx tj on tj.zgh = ta.people_id where 1 = 1");
			if (!"0".equals(deptId)) {
				sql.append(" and tj.dwd_id = '" + deptId + "'");
			}
			sql.append(") a group by a.people_id, a.year_) b  where b.countnum > 2) c left join (select zgh, substr(rxrq, 0, 4) rxrq from tb_jzgxx) d on d.zgh = c.people_id) e group by case when e.rxsj <= 3 then '3年以内' when e.rxsj > 3 and e.rxsj <= 5 then '3-5年' when e.rxsj > 5 and e.rxsj <= 10 then '5-10年' when e.rxsj > 10 then  '10年以上' else '未维护' end, year_) f");
		}
		List<Map<String, Object>> result = baseDao.querySqlList(sql.toString());
		if (result != null && result.size() > 0) {
			Set<Integer> year = new HashSet<Integer>();
			for (Iterator<Map<String, Object>> i = result.iterator(); i.hasNext();) {
				Map<String, Object> map = i.next();
				year.add(Integer.parseInt(map.get("YEAR_").toString()));
				legendData.add(map.get("NAME_").toString());
			}
			int minYear = Collections.min(year);
			int maxYear = Collections.max(year);
			for (int i = minYear; i <= maxYear; i++) {
				xData.add(i);
			}
			for (Iterator<String> k = legendData.iterator(); k.hasNext();) {
				String l = k.next();
				Map<String, Object> mh = new HashMap<String, Object>();
				List<Integer> sData = new ArrayList<Integer>();
				for (Iterator<Integer> j = xData.iterator(); j.hasNext();) {
					Integer x = j.next();
					boolean flag = true;
					for (Iterator<Map<String, Object>> m = result.iterator(); m.hasNext();) {
						Map<String, Object> obj = m.next();
						int y = Integer.parseInt(obj.get("YEAR_").toString());
						String name = obj.get("NAME_").toString();
						int lwnum = Integer.parseInt(obj.get("ZZNUM").toString());
						if (x.equals(y) && l.equals(name)) {
							sData.add(lwnum);
							flag = false;
						}
					}
					if (flag) {
						sData.add(0);
					}
				}
				mh.put("name", l);
				mh.put("type", "line");
				mh.put("data", sData);
				serieData.add(mh);
			}
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("xData", xData);
		data.put("legendData", legendData);
		data.put("serieData", serieData);
		return JSONObject.fromObject(data).toString();
	}

	@Override
	public String getTableXueWeiPage(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String deptId = json.get("deptId").toString();
		String year = json.get("year").toString();
		String jzgname = json.containsKey("jzgname") ? json.getString("jzgname") : "";
		String deptName = json.getJSONObject("data").containsKey("deptName") ? json.getJSONObject("data").getString("deptName") : "";
		String typeName = json.getJSONObject("data").containsKey("name") ? json.getJSONObject("data").getString("name") : "";
		String type = json.containsKey("type") ? json.getString("type") : "";
		StringBuffer sql = new StringBuffer();
		if ("xueWeiPie".equals(type)) {
			sql.append("select  g.*, tbx.mc from (select  e.people_id, f.xm, f.dwd_id, e.lunum lwnum, decode(e.name_, '全部数据','未维护',e.name_) value from (select * from (select c.*, nvl(tcd.name_, '全部数据') name_, nvl(tcd.path_, '000') path_ from (select b.*, nvl(tj.xwd_id, '-1') xwd_id from (select people_id, count(*) lunum from (select ta.people_id from (select * from t_res_thesis_author where people_id is not null) ta left join t_res_thesis tr on ta.thesis_id = tr.id left join tb_jzgxx tj on ta.people_id = tj.zgh where tr.year_ = '" + year + "'");
			if (!"0".equals(deptId)) {
				sql.append(" and tj.dwd_id = '" + deptId + "'");
			}
			sql.append(") a group by people_id having count(*) > 2) b left join tb_jzgxx tj on b.people_id = tj.zgh) c left join t_code_degree tcd on c.xwd_id = tcd.id) d  where substr(d.path_, 0, 6) = (select path_ from t_code_degree where name_ = '" + (("未维护".equals(typeName)) ? "全部数据" : typeName) + "')) e left join tb_jzgxx f on e.people_id = f.zgh)g left join tb_xzzzjg tbx on g.dwd_id = tbx.id where 1=1 ");
			if (!StringUtils.isEmpty(jzgname)) {
				sql.append("and g.xm like '%" + jzgname + "%'");
			}

			if (!StringUtils.isEmpty(deptName)) {
				sql.append("and tbx.mc = '" + deptName + "'");
			}
		}
		if ("xueliPie".equals(type)) {
			sql.append("select  g.*, tbx.mc from (select  e.people_id, f.xm, f.dwd_id, e.lunum lwnum, decode(e.name_, '全部数据','未维护',e.name_) value from (select * from (select c.*, nvl(tcd.name_, '全部数据') name_, nvl(tcd.path_, '000') path_ from (select b.*, nvl(tj.whc_id, '-1') whc_id from (select people_id, count(*) lunum from (select ta.people_id from (select * from t_res_thesis_author where people_id is not null) ta left join t_res_thesis tr on ta.thesis_id = tr.id left join tb_jzgxx tj on ta.people_id = tj.zgh where tr.year_ = '" + year + "'");
			if (!"0".equals(deptId)) {
				sql.append(" and tj.dwd_id = '" + deptId + "'");
			}
			sql.append(") a group by people_id having count(*) > 2) b left join tb_jzgxx tj on b.people_id = tj.zgh) c left join t_code_education tcd on c.whc_id = tcd.id) d  where substr(d.path_, 0, 6) = (select path_ from t_code_education where name_ = '" + (("未维护".equals(typeName)) ? "全部数据" : typeName) + "')) e left join tb_jzgxx f on e.people_id = f.zgh)g left join tb_xzzzjg tbx on g.dwd_id = tbx.id where 1=1 ");
			if (!StringUtils.isEmpty(jzgname)) {
				sql.append("and g.xm like '%" + jzgname + "%'");
			}
			if (!StringUtils.isEmpty(deptName)) {
				sql.append("and tbx.mc = '" + deptName + "'");
			}
		}
		if ("zhichengPie".equals(type)) {
			sql.append("select  g.*, tbx.mc from (select  e.people_id, f.xm, f.dwd_id, e.lunum lwnum, decode(e.name_, '全部数据','未维护',e.name_) value from (select * from (select c.*, nvl(tcd.name_, '全部数据') name_, nvl(tcd.path_, '000') path_ from (select b.*, nvl(tj.zyjszw_id, '-1') zyjszw_id from (select people_id, count(*) lunum from (select ta.people_id from (select * from t_res_thesis_author where people_id is not null) ta left join t_res_thesis tr on ta.thesis_id = tr.id left join tb_jzgxx tj on ta.people_id = tj.zgh where tr.year_ = '" + year + "'");
			if (!"0".equals(deptId)) {
				sql.append(" and tj.dwd_id = '" + deptId + "'");
			}
			sql.append(") a group by people_id having count(*) > 2) b left join tb_jzgxx tj on b.people_id = tj.zgh) c left join t_code_zyjszw tcd on c.zyjszw_id = tcd.id) d  where substr(d.path_, 0, 6) = (select path_ from t_code_zyjszw where name_ = '" + (("未维护".equals(typeName)) ? "全部数据" : typeName) + "')) e left join tb_jzgxx f on e.people_id = f.zgh)g left join tb_xzzzjg tbx on g.dwd_id = tbx.id where 1=1 ");
			if (!StringUtils.isEmpty(jzgname)) {
				sql.append("and g.xm like '%" + jzgname + "%'");
			}
			if (!StringUtils.isEmpty(deptName)) {
				sql.append("and tbx.mc = '" + deptName + "'");
			}
		}
		if ("laixiaoshijianPie".equals(type)) {
			sql.append("select d.* from (select c.people_id,c.rxsj,c.zznum lwnum,c.xm, tbx.mc, decode(tcd.name_, '全部数据','未维护',tcd.name_) value from (select b.*, c.dwd_id, c.xm,nvl(c.xwd_id, '-1') xwd_id from (select a.people_id, (a.year_ - substr(a.rxrq, 0, 4)) rxsj, count(*) zznum from (select ta.people_id, tr.year_, tj.rxrq from (select * from t_res_thesis_author where people_id is not null) ta left join t_res_thesis tr on ta.thesis_id = tr.id left join tb_jzgxx tj on ta.people_id = tj.zgh where tr.year_ = '" + year + "'");
			if (!"0".equals(deptId)) {
				sql.append(" and tj.dwd_id = '" + deptId + "'");
			}
			sql.append(") a group by a.people_id, a.year_, a.rxrq having count(*) > 2) b left join tb_jzgxx c on b.people_id = c.zgh) c left join tb_xzzzjg tbx on c.dwd_id = tbx.id left join t_code_degree tcd on c.xwd_id = tcd.id) d where 1=1 ");

			if ("3年以内".equals(typeName)) {
				sql.append(" and d.rxsj <= 3");
			} else if ("3-5年".equals(typeName)) {
				sql.append(" and (d.rxsj > 3 and d.rxsj <= 5)");
			} else if ("5-10年".equals(typeName)) {
				sql.append(" and (d.rxsj > 5 and d.rxsj <= 10)");
			} else if ("10年以上".equals(typeName)) {
				sql.append(" and d.rxsj > 10 ");
			} else {
				sql.append(" and d.rxsj is null");
			}

			if (!StringUtils.isEmpty(jzgname)) {
				sql.append(" and d.xm like '%" + jzgname + "%'");
			}
			if (!StringUtils.isEmpty(deptName)) {
				sql.append(" and d.mc = '" + deptName + "'");
			}
		}
		Map paramsMap = Utils4Service.packageParams(params);
		Map result = baseDao.queryTableContentBySQL(sql.toString(), paramsMap);
		Map backval = new HashMap();
		backval.put("success", true);
		backval.put("data", result.get("queryList"));
		backval.put("count", result.get("count"));
		return Struts2Utils.map2json(backval);
	}

	@Override
	public String getTableLngczzPage(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String typeName = json.get("typeName").toString().trim();
		String deptId = json.get("deptId").toString();
		String year = json.get("year").toString();
		String jzgname = json.containsKey("jzgname") ? json.getString("jzgname") : "";
		StringBuffer sql = new StringBuffer();
		sql.append("select d.* from (select c.people_id, nvl(c.xm, '未维护') xm,c.lwnum, nvl(tcd.name_, '未维护') value, nvl(tbx.mc, '未维护') mc  from (select b.*, tbj.dwd_id, tbj.xm,tbj.xwd_id from (select a.people_id, count(*) lwnum  from (select ta.people_id from (select * from t_res_thesis_author where people_id is not null) ta left join t_res_thesis tr on ta.thesis_id = tr.id left join tb_jzgxx tj on ta.people_id = tj.zgh where tr.year_ = '" + year + "'");
		if (!"0".equals(deptId)) {
			sql.append(" and tj.dwd_id = '" + deptId + "'");
		}
		sql.append(") a group by people_id having ");
		if ("发表三篇".equals(typeName)) {
			sql.append("count(*) = 3");
		} else if ("发表四篇".equals(typeName)) {
			sql.append("count(*) = 4");
		} else if ("发表五篇及以上".equals(typeName)) {
			sql.append("count(*) > 4");
		}
		sql.append(" ) b left join tb_jzgxx tbj on b.people_id = tbj.zgh) c left join t_code_degree tcd on c.xwd_id = tcd.id left join tb_xzzzjg tbx on c.dwd_id = tbx.id) d where 1=1 ");

		if (!StringUtils.isEmpty(jzgname)) {
			sql.append(" and d.xm like '%" + jzgname + "%'");
		}
		Map paramsMap = Utils4Service.packageParams(params);
		Map result = baseDao.queryTableContentBySQL(sql.toString(), paramsMap);
		Map backval = new HashMap();
		backval.put("success", true);
		backval.put("data", result.get("queryList"));
		backval.put("count", result.get("count"));
		return Struts2Utils.map2json(backval);
	}

	@Override
	public String getTableLwSexPage(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String sex = json.get("sex").toString().trim();
		String deptId = json.get("deptId").toString();
		String year = json.get("year").toString();
		String age = json.get("age").toString();
		String jzgname = json.containsKey("jzgname") ? json.getString("jzgname") : "";
		StringBuffer sql = new StringBuffer();
		sql.append("select g.* from (select f.people_id, f.csrq, nvl(f.mc,'未维护') sex, f.lwnum,nvl(f.xm, '未维护') xm,  nvl(tcd.name_, '未维护') value, nvl(tbx.mc, '未维护') mc from (select c.people_id, c.csrq, d.mc, c.lwnum, tbj.dwd_id,tbj.xm,tbj.xwd_id from (select b.*, tbj.xb_id, substr(tbj.csrq, 0, 4) csrq from (select a.people_id, count(*) lwnum from (select ta.people_id from (select * from t_res_thesis_author where people_id is not null) ta left join t_res_thesis tr on ta.thesis_id = tr.id left join tb_jzgxx tj on ta.people_id = tj.zgh where 1 = 1 and tr.year_ = '" + year + "'");
		if (!"0".equals(deptId)) {
			sql.append(" and tj.dwd_id = '" + deptId + "'");
		}
		sql.append(") a group by a.people_id  having count(*) > 2) b left join tb_jzgxx tbj on b.people_id = tbj.zgh) c left join dm_zxbz.t_zxbz_xb d on c.xb_id = d.wid left join tb_jzgxx tbj on c.people_id = tbj.zgh ) f left join t_code_degree tcd on f.xwd_id = tcd.id left join tb_xzzzjg tbx on f.dwd_id = tbx.id) g where 1=1 ");
		if ("女性".equals(sex.trim())) {
			sql.append(" and g.sex = '女' ");
		}
		if ("男性".equals(sex.trim())) {
			sql.append(" and g.sex='男' ");
		}
		if ("未维护".equals(sex.trim())) {
			sql.append(" and g.sex='未维护'");
		}
		if ("20岁以下".equals(age)) {
			sql.append("and ('" + year + "' - g.csrq) <= 20");
		} else if ("20-25岁".equals(age)) {
			sql.append(" and (('" + year + "' - g.csrq) > 20 and ('" + year + "' - g.csrq) <= 25) ");
		} else if ("25-30岁".equals(age)) {
			sql.append(" and (('" + year + "' - g.csrq) > 25 and ('" + year + "' - g.csrq) <= 30)");
		} else if ("30-35岁".equals(age)) {
			sql.append(" and (('" + year + "' - g.csrq) > 30 and ('" + year + "' - g.csrq) <= 35)");
		} else if ("35-40岁".equals(age)) {
			sql.append(" and (('" + year + "' - g.csrq) > 35 and ('" + year + "' - g.csrq) <= 40)");
		} else if ("40-45岁".equals(age)) {
			sql.append(" and (('" + year + "' - g.csrq) > 40 and ('" + year + "' - g.csrq) <= 45)");
		} else if ("45-50岁".equals(age)) {
			sql.append(" and (('" + year + "' - g.csrq) > 45 and ('" + year + "' - g.csrq) <= 50)");
		} else if ("50-55岁".equals(age)) {
			sql.append(" and (('" + year + "' - g.csrq) > 50 and ('" + year + "' - g.csrq) <= 55)");
		} else if ("55-60岁".equals(age)) {
			sql.append(" and (('" + year + "' - g.csrq) > 55 and ('" + year + "' - g.csrq) <= 60)");
		} else if ("60岁以上".equals(age)) {
			sql.append(" and ('" + year + "' - g.csrq) > 60");
		} else {
			sql.append(" and g.csrq is null ");
		}
		if (!StringUtils.isEmpty(jzgname)) {
			sql.append(" and g.xm like '%" + jzgname + "%'");
		}
		Map paramsMap = Utils4Service.packageParams(params);
		Map result = baseDao.queryTableContentBySQL(sql.toString(), paramsMap);
		Map backval = new HashMap();
		backval.put("success", true);
		backval.put("data", result.get("queryList"));
		backval.put("count", result.get("count"));
		return Struts2Utils.map2json(backval);
	}

	@Override
	public String getTable2(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String prople_id = json.get("prople_id").toString();
		String lwtitle = json.containsKey("lwtitle") ? json.getString("lwtitle") : "";
		String year = json.get("year").toString();
		StringBuffer sql = new StringBuffer();
		sql.append("select c.* from (select a.*, b.name_ periodicalName from (select trt.*, tra.people_id from (select * from t_res_thesis_author where people_id is not null) tra left join t_res_thesis trt on tra.thesis_id = trt.id) a left join (select *  from t_code where code_type = 'RES_PERIODICAL_TYPE_CODE') b on a.periodical_type_code = b.code_) c where 1=1 and c.people_id = '" + prople_id + "' and c.year_ = '" + year + "'");
		if (!StringUtils.isEmpty(lwtitle)) {
			sql.append(" and c.title_ like  '%" + lwtitle + "%' ");
		}
		Map paramsMap = Utils4Service.packageParams(params);
		Map result = baseDao.queryTableContentBySQL(sql.toString(), paramsMap);
		Map backval = new HashMap();
		backval.put("success", true);
		backval.put("data", result.get("queryList"));
		backval.put("count", result.get("count"));
		return Struts2Utils.map2json(backval);
	}
}
