package com.jhkj.mosdc.sc.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;
import com.jhkj.mosdc.framework.util.DateUtils;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.sc.job.IndexShowState;
import com.jhkj.mosdc.sc.service.IndexShowDataService;

public class IndexShowDataServiceImpl extends BaseServiceImpl implements IndexShowDataService{

	@Override
	public String getTop3(String params) {
		String sql="select xs.*,yx.mc as yx from tb_job_index_top xs "+
					"left join tb_xjda_xjxx xj on xs.xh=xj.xh "+
					"left join tb_jxzzjg yx on xj.yx_id = yx.id  "+
					"order by type,px";
		List<Map<String,Object>> result = baseDao.querySqlList(sql);
		return Struts2Utils.list2json(result);
	}

	@Override
	public String getLastAndTry(String params) {
		String sql="select sums,peak,case when  peak_time is null then '0' when peak_time='--' then peak_time else  peak_time||':00-'||peak_time||':59' end time   from tb_job_index order by islast desc,data_type";
		List<Map<String,Object>> result = baseDao.querySqlList(sql);
		return Struts2Utils.list2json(result);
	}

	@Override
	public String getTodayBookReak(String params) {
		List<Map<String,Object>> list=getToDayData(IndexShowState.BOOK_READ);
		
		return Struts2Utils.list2json(list);
	}

	@Override
	public String getTodayBookRKE(String params) {
		List<Map<String,Object>> list=getToDayData(IndexShowState.BOOK_RKE);
		
		return Struts2Utils.list2json(list);
	}

	@Override
	public String getTodayCardNum(String params) {
		List<Map<String,Object>> list=getToDayData(IndexShowState.CARD_SUM);
		
		return Struts2Utils.list2json(list);
	}

	@Override
	public String getTodayCardMoney(String params) {
		List<Map<String,Object>> list=getToDayData(IndexShowState.CARD_MONEY);
		
		return Struts2Utils.list2json(list);
	}
	
	private List<Map<String,Object>> getToDayData(int type){
		String sql=" select sums,peak,case when  peaktime is null then '0' when peaktime='--' then peaktime else  peaktime||':00-'||peaktime||':59' end peaktime,addtime from tb_job_index_today where type="+type;
		List<Map<String,Object>> list=baseDao.querySqlList(sql);
		return list;
	}

}
