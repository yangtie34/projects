package cn.gilight.product.dorm.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.gilight.framework.code.Code;
import cn.gilight.framework.enums.ShiroTagEnum;
import cn.gilight.framework.uitl.common.SqlUtil;

public class DormSqlUtil {

	
	public static String getSonDorm(String parentDorm){
		String [] dorms=Code.getKey("dorm.level.type").split("-");
		if("".equals(parentDorm)){
			return dorms[dorms.length-1];
		}
		for (int i = 0; i < dorms.length; i++) {
			if(parentDorm.equals(dorms[i])){
				if(i!=0){
					return dorms[i-1];
				}else{
					return dorms[i];
				}
			}
		}
		return parentDorm;
	}
	
	public static String getDormTj(Map<String, String> dorm){
		if("".equals(dorm.get("level_type"))){
			return " ";
		}else{
			return " and t."+dorm.get("level_type")+"_id='"+dorm.get("id")+"' ";
		}
	}
	
	public static String getQueryTj(List<Map<String, Object>> query ,boolean isTL){
		String tj="";
		for(Map<String, Object> map:query){
			if(map.get("queryCode").toString().equals("deptTeachTree")){
				Map<String,String> newMap =new HashMap<String,String>();
				for (Map.Entry<String, Object> entry : map.entrySet()) {
				     newMap.put(entry.getKey(),entry.getValue().toString());
				 }
				SqlUtil.getDeptTeachTj(newMap,ShiroTagEnum.DORM_STU.getCode(),"t");
			}else{
				tj+="and t."+map.get("queryCode")+"='"+map.get("id")+"' ";
			}
		}
		if(!isTL){
			tj=tj.replaceAll("t.SEX_ID", "t.SEX_CODE");
			tj=tj.replaceAll("t.MZ_ID", "t.NATION_CODE");
			tj=tj.replaceAll("t.NJ_ID", "(TO_CHAR(sysdate, 'yyyy' )-T.ENROLL_GRADE)");
		}
		return tj;
	}
	
	public static String getDateTJ(String startDate,String endDate){
		
		String tj="and t.year_month>=to_date('"+startDate+"','yyyy-mm') "+
					"and t.year_month<to_date('"+endDate+"','yyyy-mm')";
		return tj;
	}
}
