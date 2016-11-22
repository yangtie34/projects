package cn.gilight.product.equipment.service;

import java.util.List;
import java.util.Map;

public interface EquipOutTimeService {

	/**
	 * 获取过期在用的仪器设备信息
	 * @param emType 仪器设备类型all,val, now 
	 * @return
	 * Map内容：<br>
	 * Key:NUMS   	  value:数量<br>
	 * Key:MONEYS	  value:价值<br>
	 */
	public Map<String,Object> getCount(String emType);
	
	/**
	 * 过期在用的仪器设备分类型对比
	 * @param type 对比类型 emType,moneyFrom,moneyCount
	 * @param emType 仪器设备类型all,val, now 
	 * @return
	 * Map内容：<br>
	 * Key:CODE  	value:类型ID<br>
	 * Key:NAME  	value:类型名称<br>
	 * Key:VALUE  	value:类型数量<br>
	 */
	public List<Map<String,Object>> getContrastByType(String type,String emType);
	
	/**
	 * 过期在用的仪器设备分类型对比趋势
	 * @param type 对比类型 emType,moneyFrom,moneyCount
	 * @param emType 仪器设备类型all,val, now 
	 * @return
	 * Map内容：<br>
	 * key:YEAR_	value:年份<br>
	 * Key:CODE  	value:类型ID<br>
	 * Key:NAME  	value:类型名称<br>
	 * Key:VALUE  	value:类型数量<br>
	 */
	public List<Map<String,Object>> getContrastTrendByType(String type,String emType);
	
	/**
	 * 过期在用的仪器设备分使用者部门对比
	 * @return
	 * Map内容：<br>
	 * Key:CODE  	value:单位ID<br>
	 * Key:NAME  	value:单位名称<br>
	 * Key:NUMS  	value:过期数量<br>
	 * Key:MONEYS  	value:过期价值<br>
	 */
	public List<Map<String,Object>> getCountByUseDept(String emType);
	
	/**
	 * 过期在用的仪器设备分使用者部门对比
	 * @param deptId 单位ID
	 * @param deptGroup 单位类别 取自 getCountByUseDept 中的CODE
	 * @return
	 * Map内容：<br>
	 * Key:CODE  	value:单位ID<br>
	 * Key:NAME  	value:单位名称<br>
	 * Key:NUMS  	value:过期数量<br>
	 * Key:MONEYS  	value:过期价值<br>
	 */
	public List<Map<String,Object>> getCountByUseDept(String deptId,String deptGroup,String emType);
	
	/**
	 * 仪器设备将要过期情况
	 * @return
	 * Map内容：<br>
	 * key:YEAR_	value:年份<br>
	 * Key:CODE  	value:年份<br>
	 * Key:VALUE  	value:数量<br>
	 */
	public List<Map<String,Object>> getWillOutTime(String emType);
	
}
