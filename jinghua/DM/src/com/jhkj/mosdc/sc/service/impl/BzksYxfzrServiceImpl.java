package com.jhkj.mosdc.sc.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.sc.service.BzksYxfzrService;

/**   
* @Description: TODO 院系负责人
* @author Sunwg  
* @date 2014-10-27 下午3:58:46   
*/
public class BzksYxfzrServiceImpl implements BzksYxfzrService {
	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	/** 
	* @Title: queryYxfzrList 
	* @Description: TODO 查询院系负责人列表
	* @return String
	*/
	@Override
	public String queryYxfzrList(String params){
		String sql = "select t.yx_id,zzjg.mc,t.zgh,jzg.xm,jzg.dzxx from tb_yxgly t " +
				" left join tb_xzzzjg zzjg on t.yx_id = zzjg.id " +
				" left join tb_jzgxx jzg on jzg.zgh = t.zgh order by t.yx_id ";
		@SuppressWarnings("rawtypes")
		List result = baseDao.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.list2json(result);
	}
	
	/** 
	* @Title: updateYxfzr 
	* @Description: TODO 更新院系负责人
	* @return String
	*/
	@Override
	public String updateYxfzr(String params){
		JSONObject json = JSONObject.fromObject(params);
		String zgh = json.getString("zgh");
		String yxid = json.getString("yxid");
		String email = json.getString("email");
		if(yxid != null && yxid != "" && zgh != null && zgh != ""){
			String sql1 = "update tb_yxgly t set t.zgh = '"+zgh+"' where t.yx_id = '" + yxid+ "'";
			baseDao.update(sql1);
			if (email != null && email != "") {
				sql1 = "update tb_jzgxx t set t.dzxx = '"+email+"' where t.zgh = '"+zgh+"'";
				baseDao.update(sql1);
			}
		}
		return "{'success':true}";
	}

	@Override
	public String queryJzglist(String params) {
		String sql = "select t.zgh,t.xm,t.xm||'-'||t.zgh xmzgh,t.dzxx email from tb_jzgxx t order by t.xm";
		@SuppressWarnings("rawtypes")
		List result = baseDao.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.list2json(result);
	}
}
