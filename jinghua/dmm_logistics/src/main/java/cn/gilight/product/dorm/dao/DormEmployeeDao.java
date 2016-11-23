package cn.gilight.product.dorm.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

import com.jhnu.syspermiss.school.entity.Dept;

public interface DormEmployeeDao {
	/**
	 * 获取宿舍机构树
	 */
	public List<Dept> GetDormTree();
	
	/**
	 * 通过当前宿舍获取住宿基本信息
	 * @param dorm
	 * @return
	 */
	public Map<String,Object> getDormInfo(Map<String,String> dorm);
	
	/**
	 * 通过当前宿舍获取子节点学生住宿情况
	 * @param dorm
	 * @return
	 */
	public List<Map<String,Object>> getDormByType(Map<String,String> dorm);
	
	/**
	 * 通过当前宿舍获取子节点迎新力量
	 * @param dorm
	 * @return
	 */
	public List<Map<String,Object>> getDormByNews(Map<String,String> dorm);
	
	/**
	 * 通过当前宿舍和学生分布类型获取学生在该类型下的分布情况
	 * @param dorm
	 * @param stuType
	 * @return
	 */
	public List<Map<String,Object>> getDormByStuType(Map<String,String> dorm,String stuType);
	
	/**
	 * 通过当前宿舍获取明细分组
	 * @param dorm
	 * @param type 'LY','FJ'
	 * @return
	 */
	public Page getDormTopByGroup(int currentPage,int numPerPage,int totalRow,Map<String,String> dorm,String type);
	
	/**
	 * 通过当前宿舍获取明细
	 * @param dorm 
	 * @param type 'CW','KCW','DORM_RZ','DORM_WRZ','NEWS','NJ','EDU','SEX','MZ','DEPT','MAJOR','CLASS'
	 * @param id 'CW','KCW'时，次值为空，其余次值为所选的ID值
	 * @return
	 */
	public Page getDormTopPage(int currentPage,int numPerPage,int totalRow,Map<String,String> dorm,String type,String id);
	
	
}
