package cn.gilight.product.common.school.service;

public interface DeptTreeService {
	/**
	 * 获取行政组织机构树
	 * @return
	 */
	public Object getDeptPerms();
	/**
	 * 获取教学组织机构树
	 * @return
	 */
	public Object getDeptTeach(String shiroTag);
	/**
	 * 获取行政组织机构树
	 * @return
	 */
	public Object getDept(String shiroTag);
	
	/**
	 * 获取组织机构树的JSON字符串
	 * @return
	 */
	public String getDeptJson();
	
	/**
	 * 获取教学组织机构树的JSON字符串
	 * @return
	 */
	public String getDeptTeachJson();
}
