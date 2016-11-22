package com.jhnu.product.four.punish.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.four.punish.dao.FourPunishDao;
import com.jhnu.util.common.MapUtils;
@Repository("fourPunishDao")
public class FourPunishDaoImpl implements FourPunishDao {
	
	@Autowired
	private  BaseDao baseDao;
	public List<Map<String,Object>> getPunishMsg()
	{
		String sql = "select a.no_,a.name_,nvl(b.JGaoT,0) JGao,nvl(c.YZJGaoT,0) YZJGao, "
				+ "nvl(JGuoT,0) JGuo,nvl(LXCKanT,0) LXCKan,nvl(ZDTXueT,0) ZDTXue,                      "
				+ "nvl(b.JGaoT,0)+nvl(c.YZJGaoT,0)+nvl(JGuoT,0)+nvl(LXCKanT,0)+nvl(ZDTXueT,0) as total "
				+ "from t_stu a left join(                                                             "
				+ "select stu_id,count(punish_code) as JGaoT from t_stu_punish where punish_code='01'  "
				+ "group by stu_id, punish_code  ) b   on a.no_=b.stu_id                               "
				+ "left join (                                                                         "
				+ "select stu_id,count(punish_code) as YZJGaoT from t_stu_punish where punish_code='02'"
				+ "group by stu_id, punish_code  ) c   on a.no_=c.stu_id                               "
				+ "left join (                                                                         "
				+ "select stu_id,count(punish_code) as JGuoT from t_stu_punish where punish_code='03'  "
				+ "group by stu_id, punish_code  ) d   on a.no_=d.stu_id                               "
				+ "left join (                                                                         "
				+ "select stu_id,count(punish_code) as LXCKanT from t_stu_punish where punish_code='04'"
				+ "group by stu_id, punish_code  ) e   on a.no_=e.stu_id                               "
				+ "left join (                                                                         "
				+ "select stu_id,count(punish_code) as ZDTXueT from t_stu_punish where punish_code='05'"
				+ "group by stu_id, punish_code  ) f   on a.no_=f.stu_id";
		
		        return baseDao.getBaseDao().querySqlList(sql);
		
	}
	
	public void savePunishLog(List<Map<String,Object>> list)
	{
		
		String sql="delete tl_punish";
		baseDao.getLogDao().executeSql(sql);
		
		final int COUNT = list.size();
		final List<Map<String,Object>> LIST=list;
		baseDao.getLogDao().getJdbcTemplate().batchUpdate(
				"insert into tl_punish values(?,?,?,?,?,?,?,?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
		                ps.setString(1, MapUtils.getString(LIST.get(i),"no_").toString());    
		                ps.setString(2, MapUtils.getString(LIST.get(i),"name_").toString());    
		                ps.setString(3, MapUtils.getString(LIST.get(i),"jgao").toString());    
		                ps.setString(4, MapUtils.getString(LIST.get(i),"yzjgao").toString());    
		                ps.setString(5, MapUtils.getString(LIST.get(i),"jguo").toString()); 
		                ps.setString(6, MapUtils.getString(LIST.get(i),"lxckan").toString());  
		                ps.setString(7, MapUtils.getString(LIST.get(i),"zdtxue").toString());  
		                ps.setString(8, MapUtils.getString(LIST.get(i),"total").toString());  
		              }    
		              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 }); 
		
		
	}
	
	public List<Map<String,Object>> getPunishMsg(String Id){
		
		String sql="select * from tl_punish where no_='"+Id+"'";
		return baseDao.getLogDao().querySqlList(sql);
		
	}

}
