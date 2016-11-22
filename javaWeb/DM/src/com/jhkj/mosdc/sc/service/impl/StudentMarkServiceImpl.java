package com.jhkj.mosdc.sc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.Utils4Service;
import com.jhkj.mosdc.permiss.domain.User;
import com.jhkj.mosdc.permiss.util.UserPermiss;
import com.jhkj.mosdc.sc.service.StudentMarkService;

public class StudentMarkServiceImpl extends BaseServiceImpl implements StudentMarkService{

	@Override
	public String getLv(String params) {
	
		
		
		System.out.println("I am getlv "+params+"");
		JSONObject json = JSONObject.fromObject(params);
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		//第一次进入时无法判断其权限，故先判断是否是第一次进入
		if(!json.containsKey("zzjgId")){
			User user = UserPermiss.getUser();
			String jxzzjgids = user.getCurrentJxzzjgIds();
			if("".equals(jxzzjgids)||jxzzjgids==null){
				zzjgId ="0";
			}else{
				String sql ="SELECT * FROM TB_JXZZJG WHERE SFKY=1 AND id in ("+jxzzjgids+") and id !=0";
				List<Map<String,Object>> result = baseDao.querySqlList(sql);
				for(Map<String,Object> obj : result){
					if(obj.get("CC").toString().equals("1")){
						zzjgId=obj.get("ID").toString();
						break;
					}
					zzjgId=obj.get("ID").toString();
				}
			}
		}
		String xn=json.get("xn").toString();
		String xq=json.get("xq").toString();
		String tj=" ";
		int cc=1;
		if(!zzjgId.equals("0")){
			tj=" and p_id="+zzjgId;
			cc=2;
		}
		String sql="select MC as field,HGL as value,'合格率(%)' as name from tb_job_xscj_yx_zy where cc="+cc+" and xn="+xn+" and xq="+xq+" " +tj;
		sql=sql + " union all  select MC as field,yxl as value,'优秀率(%)' as name from tb_job_xscj_yx_zy where cc="+cc+" and xn="+xn+" and xq="+xq+" "+tj;
		List<?> result = baseDao.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.list2json(result);
	}
	
	
	public String queryGridContent(String params){
		
		System.out.println("queryGridContent "+params+"");
		JSONObject json = JSONObject.fromObject(params);
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		//第一次进入时无法判断其权限，故先判断是否是第一次进入
		if(!json.containsKey("zzjgId")){
			User user = UserPermiss.getUser();
			String jxzzjgids = user.getCurrentJxzzjgIds();
			if("".equals(jxzzjgids)||jxzzjgids==null){
				zzjgId ="0";
			}else{
				String sql ="SELECT * FROM TB_JXZZJG WHERE SFKY=1 AND id in ("+jxzzjgids+") and id !=0";
				List<Map<String,Object>> result = baseDao.querySqlList(sql);
				for(Map<String,Object> obj : result){
					if(obj.get("CC").toString().equals("1")){
						zzjgId=obj.get("ID").toString();
						break;
					}
					zzjgId=obj.get("ID").toString();
				}
			}
		}
		String xn=json.get("xn").toString();
		String xq=json.get("xq").toString();
		int cc=1;
		String tj="";
		if(!zzjgId.equals("0"))
		{
			cc=2;
			tj="and p_id="+zzjgId;
		}
		
		User user = UserPermiss.getUser();
		String jxzzjgids = user.getCurrentJxzzjgIds();
		Map paramsMap = Utils4Service.packageParams(params);
		
		
		String excuteSql ="select ID,MC as XY,HGL,PJF,ZGF,JC,ZWS,FC,BZC,YXL from tb_job_xscj_yx_zy where cc="+cc+" and xn="+xn+" and xq="+xq+""+tj;
		
		Map result = baseDao.queryTableContentBySQL(excuteSql,paramsMap);
		Map backval = new HashMap();
		backval.put("success", true);
		backval.put("data", result.get("queryList"));
		backval.put("count", result.get("count"));
		return Struts2Utils.map2json(backval);
	}
	
	public String getCjFb(String params){
		JSONObject json = JSONObject.fromObject(params);
		String thisId = json.containsKey("thisId")?json.get("thisId").toString():"0";
		String tj=" ";
		String xn=json.get("xn").toString();
		String xq=json.get("xq").toString();
		if(thisId.length()<4){
			tj=" and yx.id="+thisId;
		}else{
			tj=" and zy.id="+thisId;
		}
		String sql="select nvl(cj.bfzkscj,0) cj,count(*) sl "+    
					"from t_xs_kscj cj "+  
					"inner join tb_xjda_xjxx xs on cj.xh=xs.xh "+  
					"left join tb_jxzzjg yx on xs.yx_id=yx.id "+  
					"left join tb_jxzzjg zy on xs.zy_id=zy.id "+  
					"where cj.xn='"+xn+"' and cj.xq="+xq+" and cj.djzkscj is null  "+  
					tj+ 
					"  group by cj.bfzkscj "+  
					"  order by cj.bfzkscj desc";
		List<Map<String,Object>> list=baseDao.querySqlList(sql);
		int sum=0;
		int nextNum=90;
		List<Map<String,Object>> reslut=new ArrayList<Map<String,Object>>();
		Map<String,Object> map=null;
		for (int i = 0; i < list.size(); i++) {
			double cj=Double.parseDouble(list.get(i).get("CJ").toString());
			double nextCj;
			if((i+1)!=list.size()){
				nextCj=Double.parseDouble(list.get(i+1).get("CJ").toString());
			}else{
				nextCj=cj;
			}
			if(cj>=90){
				if(nextNum==90){
					map=new HashMap<String, Object>();
					map.put("field", "90-100");
					map.put("name", "人数");
					nextNum=80;
				}
				sum+=Double.parseDouble(list.get(i).get("SL").toString());
				if(nextCj<90 || cj==nextCj){
					map.put("value", sum);
					reslut.add(map);
				}
			}else if(cj>=80){
				if(nextNum==80){
					map=new HashMap<String, Object>();
					sum=0;
					map.put("field", "80-89");
					map.put("name", "人数");
					nextNum=70;
				}
				sum+=Double.parseDouble(list.get(i).get("SL").toString());
				if(nextCj<80 || cj==nextCj){
					map.put("value", sum);
					reslut.add(map);
				}
			}else if(cj>=70){
				if(nextNum==70){
					map=new HashMap<String, Object>();
					sum=0;
					map.put("field", "70-79");
					map.put("name", "人数");
					nextNum=60;
				}
				sum+=Double.parseDouble(list.get(i).get("SL").toString());
				if(nextCj<70 || cj==nextCj){
					map.put("value", sum);
					reslut.add(map);
				}
			}else if(cj>=60){
				if(nextNum==60){
					map=new HashMap<String, Object>();
					sum=0;
					map.put("field", "60-69");
					map.put("name", "人数");
					nextNum=50;
				}
				sum+=Double.parseDouble(list.get(i).get("SL").toString());
				if(nextCj<60 || cj==nextCj){
					map.put("value", sum);
					reslut.add(map);
				}
			}else if(cj>=50){
				if(nextNum==50){
					map=new HashMap<String, Object>();
					sum=0;
					map.put("field", "50-59");
					map.put("name", "人数");
					nextNum=30;
				}
				sum+=Double.parseDouble(list.get(i).get("SL").toString());
				if(nextCj<50 || cj==nextCj){
					map.put("value", sum);
					reslut.add(map);
				}
			}else if(cj>=30){
				if(nextNum==30){
					map=new HashMap<String, Object>();
					sum=0;
					map.put("field", "30-59");
					map.put("name", "人数");
					nextNum=0;
				}
				sum+=Double.parseDouble(list.get(i).get("SL").toString());
				if(nextCj<30 || cj==nextCj){
					map.put("value", sum);
					reslut.add(map);
				}
			}else{
				if(nextNum==0){
					map=new HashMap<String, Object>();
					sum=0;
					map.put("field", "0-39");
					map.put("name", "人数");
					nextNum=-1;
				}
				sum+=Double.parseDouble(list.get(i).get("SL").toString());
				if(nextCj<=0 || cj==nextCj){
					map.put("value", sum);
					reslut.add(map);
				}
			}
		}
		return Struts2Utils.list2json(reslut);
	}


	@Override
	public String getLsCj(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String thisId = json.containsKey("thisId")?json.get("thisId").toString():"0";
		String xq=json.get("xq").toString();
		String sql="select xn as field,HGL as value,'优秀率(%)' as name from tb_job_xscj_yx_zy where id="+thisId+" and xq="+xq;
		sql=sql + " union all  select xn as field,yxl as value,'合格率(%)' as name from tb_job_xscj_yx_zy where id="+thisId+" and xq="+xq+" order by field";
		List<?> result = baseDao.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.list2json(result);
	}
}
