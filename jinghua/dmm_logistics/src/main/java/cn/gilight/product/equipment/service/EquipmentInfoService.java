package cn.gilight.product.equipment.service;

import java.util.List;
import java.util.Map;

public interface EquipmentInfoService {

	/**
	 * 获取仪器设备信息
	 * @return
	 * @param emType 仪器设备概要类型all,val, now ,nowVal
	 * Map内容：<br>
	 * Key:NUMS   	  value:数量<br>
	 * Key:MONEYS	  value:价值<br>
	 */
	public Map<String,Object> getCount(String emType);
	
	/**
	 * 仪器设备分类型对比
	 * @param type 对比类型 emType,moneyFrom,moneyCount
	 * @param emType 仪器设备概要类型all,val, now,nowVal
	 * @return
	 * Map内容：<br>
	 * Key:CODE  	value:类型ID<br>
	 * Key:NAME  	value:类型名称<br>
	 * Key:VALUE  	value:类型数量<br>
	 */
	public List<Map<String,Object>> getContrastByType(String type,String emType);
	
	/**
	 * 仪器设备分类型对比趋势
	 * @param type 对比类型 emType,moneyFrom,moneyCount
	 * @param emType 仪器设备概要类型all,val, now ,nowVal
	 * @return
	 * Map内容：<br>
	 * key:YEAR_	value:年份<br>
	 * Key:CODE  	value:类型ID<br>
	 * Key:NAME  	value:类型名称<br>
	 * Key:VALUE  	value:类型数量<br>
	 */
	public List<Map<String,Object>> getContrastTrendByType(String type,String emType);
	
	/**
	 * 获取仪器设备数量与价值以及增长数的趋势
	 * @return
	 * List中Map内容：<br>
	 * Key:YEAR_  value:学年<br>
	 * Key:NUMS  value:设备数量<br>
	 * Key:MONEYS  value:设备价值<BR>
	 * KEY:UPNUMS  value:增长设备数量<br>
	 * Key:UPMONEYS  value:增长设备价值<br>
	 */
	public List<Map<String,Object>> getEquipmentTrend();
	
	
}
