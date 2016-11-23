package com.jhnu.util.product;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.util.common.DateUtils;
import com.jhnu.util.common.MapUtils;
import com.jhnu.util.common.MathUtils;

@Service("zcService")
public class ZcServiceImpl implements ZcService{

	@Autowired
	private BaseDao baseDao;
	
	
	@Override
	public void addZc() {
		String sql="select a.xh from "+
					"(select xh from  "+
					"(select kh,count(*) d from tl_ykt_xfmx x group by kh order by count(*) desc) t "+
					"inner join tb_xjda_xjxx xx on t.kh = xx.xh where xx.yjbysj between '2016' and '2018'and xx.jdxl_id=20 and d >250 "+
					" ) a  "+
					"inner join ( "+
					"select xh from t_xs_kscj x where x.xn=2014 and x.xq=1 group by xh having count(*) >2  "+
					") b on a.xh=b.xh";
		List<Map<String,Object>>  list= baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
		System.out.println(list.size());
		String sss="";
		for (int i = 0; i < 100; i++) {
			int j=(int) (Math.random()*5342);
			sss+="'"+list.get(j).get("XH").toString()+"',";
		//	System.out.println(list.get(j).get("XH").toString());
			list.remove(j);
		}
		System.out.println(sss);
		
		
		
//		String sql="select * from ZC_TEST ";
//		List<Map<String,Object>> list=baseDao.getLogDao().getJdbcTemplate().queryForList(sql);
//		List<Map<String,Object>> allList=new ArrayList<Map<String,Object>>();
//		int id=100000;
//		for (int i = 0; i < list.size(); i++) {
//			int sl=MapUtils.getIntValue(list.get(i), "SL");
//			int flh=MapUtils.getIntValue(list.get(i), "FLH");
//			if(flh==4||flh==6||flh==8){
//				flh=2;
//			}else if(flh==7){
//				flh=3;
//			}else if(flh==5){
//				flh=5;
//			}else if(flh==3||flh==12){
//				flh=8;
//			} else{
//				flh=10;
//			}
//			int code_dwlx=MapUtils.getIntValue(list.get(i), "CODE_DWLX");
//			if(code_dwlx==2){
//				code_dwlx=3;
//			}
//			String gzrq=MapUtils.getString(list.get(i), "GZRQ");
//			long rql=0l;
//			try {
//				Date rq=DateUtils.YEAR.parse(gzrq);
//				rql=rq.getTime();
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//			int id_yx=MapUtils.getIntValue(list.get(i), "id_yx");
//			double zje=MapUtils.getDoubleValue(list.get(i), "ZJE");
//			double dj=MathUtils.get2Point(zje/sl);
//			for (int j = 0; j < sl; j++) {
//				Map<String,Object> map=new HashMap<String, Object>();
//				int thisId=id++;
//				map.put("id", thisId);
//				map.put("flh", flh);
//				map.put("id_yx", id_yx);
//				map.put("rql", rql);
//				map.put("code_dwlx", code_dwlx);
//				map.put("dj", dj);
//				allList.add(map);
//			}
//		}
//		saveZc(allList);
	}
	
	private void saveZc(List<Map<String,Object>> list) {
		final int COUNT = list.size();
		final List<Map<String,Object>> LIST=list;
		baseDao.getLogDao().getJdbcTemplate().batchUpdate(
				"insert into t_yqsb(wid,yqbh,gdzcfldm,dj,gzrq,xz,lydwdm,jfkmdm,syfxdm,zclyfs) values (?, ?, ?, ?,?, ?, ?, ?,?,?)",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
		                ps.setString(1, MapUtils.getString(LIST.get(i), "id"));    
		                ps.setString(2, MapUtils.getString(LIST.get(i), "id"));    
		                ps.setString(3, MapUtils.getString(LIST.get(i), "flh"));    
		                ps.setDouble(4, MapUtils.getDouble(LIST.get(i), "dj"));  
		                ps.setDate(5, new java.sql.Date(MapUtils.getLongValue(LIST.get(i), "rql")));
		                ps.setString(6, "1");  
		                ps.setString(7, MapUtils.getString(LIST.get(i), "id_yx"));  
		                ps.setString(8, "4");
		                ps.setString(9, MapUtils.getString(LIST.get(i), "code_dwlx"));
		                ps.setString(10, "1");
		              }    
		              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 }); 
	}

}
