package cn.gilight.dmm.business.dao;

import java.util.List;

import cn.gilight.dmm.business.entity.AdvancedSource;
import cn.gilight.framework.entity.NodeAngularTree;

/**
 * 高级查询
 * 
 * @author xuebl
 * @date 2016年5月18日 下午2:23:20
 */
public interface AdvancedDao {

	/**
	 * 根据tag 获取条件列表
	 * @param tag
	 * @return List<Map<String,Object>>
	 */
	List<AdvancedSource> getAdvancedList(String tag);
	
	/**
	 * 查询 angularjs tree组件数据（教学组织机构）
	 * @param deptSql
	 * @return List<NodeAngularTree>
	 */
	public List<NodeAngularTree> getDeptTeachList(String deptSql);

	/**
	 * 查询 angularjs tree组件数据（行政组织机构）
	 * @param deptSql
	 * @return List<NodeAngularTree>
	 */
	public List<NodeAngularTree> getDeptList(String deptSql);
    /**
     * 查询 angularjs tree组件数据（生源地）
     * @return List<NodeAngularTree>
     */
	public List<NodeAngularTree> getOriginList();
	
}