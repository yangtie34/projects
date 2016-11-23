package cn.gilight.product.equipment.dao;

import java.util.Date;

import cn.gilight.framework.code.Code;
import cn.gilight.framework.uitl.common.DateUtils;

public class EquipmentSqlUtil {
	
	public static String getNumMoney(){
		return "select nvl(sum(t.count_),0) NUMS ,round(nvl(sum(t.price*t.count_)/10000,0),2) moneys from t_equipment t where 1=1 ";
	}
	
	public static String getEmDetil(){
		return "select t.*,nvl(ec.name_,'未维护') equi_name,nvl(fsc.name_,'未维护') equi_fund_name, "+
				"nvl(cd.name_,'未维护') dept_name,nvl(dcc.name_,'未维护') dept_group_name,tea.name_ tea_name, "+
				"cesc.name_ equi_status_name from t_equipment t left join t_tea tea on t.manager = tea.tea_no "+
				"left join t_code ec on t.EQUI_CODE=ec.code_ and ec.code_type='EQUI_CODE' "+
				"left join t_code fsc on t.EQUI_FUND_CODE=fsc.code_ and fsc.code_type='EQUI_FUND_CODE' "+
				"left join t_code_dept cd on tea.DEPT_ID=cd.id and cd.level_>0 "+
				"left join t_code dcc on cd.dept_category_code=dcc.code_ and dcc.code_type='DEPT_CATEGORY_CODE' "+
				"left join t_code cesc on( t.equi_status_code= cesc.code_ and cesc.code_type='EQUI_STATUS_CODE' ) "+
				"where 1=1 ";
	}
	
	public static String getManager(){
		return "select distinct t.manager tea_no,tea.name_,tea.sex_code,sex.name_ sex_name,tea.dept_id, "+
				  "teacd.name_ dept_name,tea.edu_id,edu.name_ edu_name,tea.zyjszw_id,zc.name_ zyjszw_name "+
				  "from t_equipment t left join t_tea tea on t.manager = tea.tea_no "+
				  "left join t_code sex on ( tea.sex_code = sex.code_ and sex.code_type='SEX_CODE' ) "+
				  "left join t_code_education edu on tea.edu_id = edu.id "+
				  "left join t_code_zyjszw zc on tea.zyjszw_id = zc.id "+
				  "left join t_code_dept cd on tea.DEPT_ID = cd.id and cd.level_>0 "+
				  "left join t_code_dept teacd on tea.DEPT_ID = teacd.id "+
				  "where t.manager is not null ";
	}
	
	public static String getIsOT(boolean isOT){
	///	String tj=" and t.EQUI_STATUS_CODE = '1' ";
		String tj="";
		if(isOT){
			tj+=" and (to_number(substr(t.buy_date,0,4))+t.usr_years)||substr(t.buy_date,5,6) < to_char(sysdate,'yyyy-mm-dd') and t.EQUI_STATUS_CODE = '1' ";
		}
		return tj;
	}
	
	public static String getEmType(String emType){
		String tj="";
		switch (emType) {
		case "val":
			tj=" and t.price>= "+Code.getKey("em.val")+" ";
			break;
		case "now":
			tj=" and t.buy_date like '"+DateUtils.YEAR.format(new Date())+"%' ";
			break;
		case "nowVal":
			tj=" and t.price>="+Code.getKey("em.val")+" and t.buy_date like '"+DateUtils.YEAR.format(new Date())+"%' ";
			break;
		default:
			break;
		}
		return tj;
	}
	
	public static String getGroup(String type){
		String tj="";
		switch (type) {
		case "emType":
			tj=" left join t_code c on t.EQUI_CODE=c.code_ and c.code_type='EQUI_CODE' ";
			break;
		case "moneyFrom":
			tj=" left join t_code c on t.EQUI_FUND_CODE=c.code_ and c.code_type='EQUI_FUND_CODE' ";
			break;
		default:
			break;
		}
		return tj;
	}
	
	public static String getMoneyCount(String type){
		String name[]={"1万以下","1万至5万","5万至10万","10万至40万","40万以上","未维护"};
		String code[]={"1","1.5","5.10","10.40","40","0"};
		String sql="case ";
		if("name".equals(type)){
			for (int i = 0; i < code.length; i++) {
				if(i!=code.length-1){
					sql+=" when "+getMoneyCountTj(code[i])+" then '"+name[i]+"' ";
				}else{
					sql+=" else '"+name[i]+"' end name ";
				}
			}
		}else{
			for (int i = 0; i < code.length; i++) {
				if(i!=code.length-1){
					sql+=" when "+getMoneyCountTj(code[i])+" then '"+code[i]+"' ";
				}else{
					sql+=" else '"+code[i]+"' end code ";
				}
			}
		}
		return sql;
	}
	public static String getMoneyCountTj(String code){
		String tj="";
		switch (code) {
		case "1":
			tj=" t.price< 10000 ";
			break;
		case "1.5":
			tj=" t.price< 50000 and t.price>= 10000 ";
			break;
		case "5.10":
			tj=" t.price< 100000 and t.price>= 50000 ";
			break;
		case "10.40":
			tj=" t.price< 400000 and t.price>= 100000 ";
			break;
		case "40":
			tj=" t.price>= 400000 ";
			break;
		case "0":
			tj=" t.price is null ";
			break;
		default:
			break;
		}
		return tj;
	}
	
	
	
}
