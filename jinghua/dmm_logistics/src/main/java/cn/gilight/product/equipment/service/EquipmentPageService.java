package cn.gilight.product.equipment.service;

import cn.gilight.framework.page.Page;


public interface EquipmentPageService {

	/**
	 * 获取仪器设备列表
	 * @return
	 * Map内容：<br>
	 * 
	 */
	public Page getEmDetil(int currentPage,int numPerPage,int totalRow,String sort,boolean isAsc,boolean isOT,String emType);
	
	/**
	 * 获取仪器设备列表
	 * @return
	 * Map内容：<br>
	 * 
	 */
	public Page getEmDetilByType(int currentPage,int numPerPage,int totalRow,String sort,boolean isAsc,boolean isOT,String emType,String type,String typeValue);
	
	/**
	 * 获取仪器设备列表
	 * @return
	 * Map内容：<br>
	 * 
	 */
	public Page getEmDetilByDeptGroup(int currentPage,int numPerPage,int totalRow,String sort,boolean isAsc,boolean isOT,String emType,String deptGroup);
	
	/**
	 * 获取仪器设备列表
	 * @return
	 * Map内容：<br>
	 * 
	 */
	public Page getEmDetilByDeptId(int currentPage,int numPerPage,int totalRow,String sort,boolean isAsc,boolean isOT,String emType,String deptGroup,String deptId);
	
	/**
	 * 获取仪器设备列表
	 * @return
	 * Map内容：<br>
	 * 
	 */
	public Page getEmDetilByYear(int currentPage,int numPerPage,int totalRow,String sort,boolean isAsc,boolean isOT,String emType,boolean isUp,int year);
	
	/**
	 * 获取仪器设备列表
	 * @return
	 * Map内容：<br>
	 * 
	 */
	public Page getEmDetilByLastYear(int currentPage,int numPerPage,int totalRow,String sort,boolean isAsc,boolean isOT,String emType,int year);
	
	/**
	 * 获取仪器设备列表
	 * @return
	 * Map内容：<br>
	 * 
	 */
	public Page getEmDetilByManagerId(int currentPage,int numPerPage,int totalRow,String sort,boolean isAsc,boolean isOT,String emType,String managerId);
	
	public Page getManager(int currentPage,int numPerPage,int totalRow,String sort,boolean isAsc,boolean isOT, String emType,String deptGroup);
	
	/**
	 * 获取管理者列表
	 * @return
	 * Map内容：<br>
	 * 
	 */
	public Page getManagerByType(int currentPage,int numPerPage,int totalRow,String sort,boolean isAsc,boolean isOT,String emType,String deptGroup,String type,String typeValue);
	
	/**
	 * 获取管理者列表
	 * @return
	 * Map内容：<br>
	 * 
	 */
	public Page getManagerByDeptGroup(int currentPage,int numPerPage,int totalRow,String sort,boolean isAsc,boolean isOT,String emType,String deptGroup);
	
	/**
	 * 获取管理者列表
	 * @return
	 * Map内容：<br>
	 * 
	 */
	public Page getManagerByDeptId(int currentPage,int numPerPage,int totalRow,String sort,boolean isAsc,boolean isOT,String emType,String deptGroup,String deptId);
	
}
