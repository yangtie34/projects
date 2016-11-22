package cn.gilight.product.equipment.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

public interface EquipManagerDao {
	
	/**
	 * 获取管理者数量
	 * @return
	 */
	public int getManagers();
	
	/**
	 * 管理者数量分部门对比
	 * @return
	 * Map内容：<br>
	 * Key:CODE  	value:单位ID<br>
	 * Key:NAME  	value:单位名称<br>
	 * Key:USERS  	value:人数<br>
	 */
	public List<Map<String,Object>> getManagersByDept();
	
	/**
	 * 仪器设备管理者分学历对比
	 * @param type 对比类型
	 * @param DeptGroup 所属单位类型
	 * @return
	 * Map内容：<br>
	 * Key:CODE  	value:类型ID<br>
	 * Key:NAME  	value:类型名称<br>
	 * Key:VALUE  	value:类型数量<br>
	 */
	public List<Map<String, Object>> getContrastByEdu(String DeptGroup);

	/**
	 * 仪器设备管理者分学历对比趋势
	 * @param DeptGroup 所属单位类型
	 * @return
	 * Map内容：<br>
	 * key:YEAR_	value:年份<br>
	 * Key:CODE  	value:类型ID<br>
	 * Key:NAME  	value:类型名称<br>
	 * Key:VALUE  	value:类型数量<br>
	 */
	public List<Map<String, Object>> getContrastTrendByEdu(String DeptGroup);
	
	/**
	 * 仪器设备管理者分职称对比
	 * @param type 对比类型
	 * @param DeptGroup 所属单位类型
	 * @return
	 * Map内容：<br>
	 * Key:CODE  	value:类型ID<br>
	 * Key:NAME  	value:类型名称<br>
	 * Key:VALUE  	value:类型数量<br>
	 */
	public List<Map<String, Object>> getContrastByZC(String DeptGroup);

	/**
	 * 仪器设备管理者分职称对比趋势
	 * @param DeptGroup 所属单位类型
	 * @return
	 * Map内容：<br>
	 * key:YEAR_	value:年份<br>
	 * Key:CODE  	value:类型ID<br>
	 * Key:NAME  	value:类型名称<br>
	 * Key:VALUE  	value:类型数量<br>
	 */
	public List<Map<String, Object>> getContrastTrendByZC(String DeptGroup);
	
	
	/**
	 * 过期在用的仪器设备分使用者部门对比
	 * @param deptId 单位ID
	 * @param deptGroup 单位类别
	 * @return
	 * Map内容：<br>
	 * Key:CODE  	value:单位ID<br>
	 * Key:NAME  	value:单位名称<br>
	 * Key:USERS  	value:人数<br>
	 */
	public List<Map<String,Object>> getManagersByDept(String deptId,String deptGroup);
	
	/**
	 * 过期在用的仪器设备分使用者部门对比
	 * @param deptId 单位ID
	 * @param deptGroup 单位类别
	 * @return
	 * Map内容：<br>
	 * Key:CODE  	value:单位ID<br>
	 * Key:NAME  	value:单位名称<br>
	 * Key:NUMS  	value:设备数<br>
	 * Key:MONEYS  	value:价值<br>
	 * Key:AVGSTU  	value:生均价值<br>
	 */
	public List<Map<String,Object>> getCountByUseDept(String deptId,String deptGroup);
	
	/**
	 * 获取排名情况
	 * @param rankType 排名类型 all,val,money
	 * @param queryType 查询类型 manager,dept
	 * @param deptGroup 所属单位类型 取自getManagersByDept中的code
	 * @param rank 名次
	 * @param currentPage 当前页码
	 * @param numPerPage 每页条数
	 * 
	 * @return
	 * Page中List的值<br>
	 * key:rank_ 		value:名次 <br>
	 * key:tea_id 		value:教师ID<br>
	 * key:tea_name 	value:教师名次<br>
	 * key:dept_id 		value:单位ID<br>
	 * key:dept_name 	value:单位名称<br>
	 * key:nums 		value:数量<br>
	 * key:moneys 		value:金额<br>
	 * 
	 */
	public Page getEmTop(int currentPage,int numPerPage,int totalRow,String rankType,String queryType, String deptGroup,int rank);
	
}
