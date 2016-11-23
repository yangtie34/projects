package com.jhkj.mosdc.sc.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.Utils4Service;
import com.jhkj.mosdc.permiss.domain.User;
import com.jhkj.mosdc.permiss.util.UserPermiss;
import com.jhkj.mosdc.sc.service.LeaveSchoolService;

public class LeaveSchoolServiceImpl implements LeaveSchoolService {
	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}


	@Override
	public void saveStudent(Map<String, Object> map) {
		User user = UserPermiss.getUser();
		String userName = user.getCurrentLoginname();
		String sql="insert into TB_SC_LEAVESCHOOL (ID,CODE,ADDTIME,STARTTIME,ENDTIME,ADDER) VALUES"
				+ " (leaveschool_seq.nextval,'"+map.get("CODE")+"'"
				+ ",to_timestamp('"+map.get("TIME")+"','yyyy-mm-dd hh24:mi:ss')"
				+ ",to_timestamp('"+map.get("STARTTIME")+"','yyyy-mm-dd' )"
				+ ",to_timestamp('"+map.get("ENDTIME")+"','yyyy-mm-dd' )"
				+ ",'"+userName+"')";
		baseDao.updateSqlExec(sql);
	}
	public void deleteStudent(String id) {
		JSONObject json = JSONObject.fromObject(id);
		String lcid = json.get("lcid").toString();
		String sql="delete from TB_SC_LEAVESCHOOL where id="+lcid;
		baseDao.updateSqlExec(sql);
	}


	@Override
	public boolean haveCode(String code) {
		String sql ="SELECT COUNT(0) FROM TB_SC_LEAVESCHOOL where code='"+code+"'";
		int sum =baseDao.querySqlCount(sql);
		if(sum>0){
			return true;
		}else{
			return false;
		}
	}
	@Override
	public String queryGridContent(String params) {
		User user = UserPermiss.getUser();
		String jxzzjgids = user.getCurrentJxzzjgIds();
		Map paramsMap = Utils4Service.packageParams(params);
		String tj=" ";
		if(!"".equals(jxzzjgids)&&jxzzjgids!=null){
			tj=" WHERE  YX.ID IN ("+jxzzjgids+")";
		}
		String excuteSql ="select l.ID,l.CODE,to_char(l.ADDTIME, 'yyyy-mm-dd hh24:mi:ss') as addtime,"
				+"to_char(l.STARTTIME, 'yyyy-mm-dd') as starttime,"
				+"to_char(l.ENDTIME, 'yyyy-mm-dd') as endtime,"
				+ "l.ADDER,xj.xm as xm,yx.mc as yx,zy.mc as zy,bj.bm as bj "
				+ "from TB_SC_LEAVESCHOOL  l inner join tb_xjda_xjxx xj on l.code=xj.xh"
						+" left join tb_jxzzjg yx on xj.yx_id = yx.id"
						+" left join tb_jxzzjg zy on xj.zy_id = zy.id"
						+" left join tb_xxzy_bjxxb bj on xj.bj_id=bj.id"
						+tj;
		
		Map result = baseDao.queryTableContentBySQL(excuteSql,paramsMap);
		Map backval = new HashMap();
		backval.put("success", true);
		backval.put("data", result.get("queryList"));
		backval.put("count", result.get("count"));
		return Struts2Utils.map2json(backval);
	}
}
