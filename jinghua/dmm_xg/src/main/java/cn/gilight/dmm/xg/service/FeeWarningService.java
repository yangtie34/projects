package cn.gilight.dmm.xg.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.framework.page.Page;

/**
 * 学费预警
 * @author lijun
 *
 */
public interface FeeWarningService {
	/**
	 * 得到当前学年未缴纳学费学生总人数和总金额
	 * @param deptList
	 * @return
	 */
	Map<String, Object> queryXfInfo(List<AdvancedParam> advancedList);
	Map<String, Object> queryXfInfo(List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 得到欠费详情的筛选条件
	 * @return
	 */
	List<Map<String, Object>> querySelectType();
	List<Map<String, Object>> queryfeeWarningTypeList();
	/**
	 * 未缴纳学费类型分布
	 * @param deptList
	 * @return
	 */
	List<Map<String, Object>> queryXflxfb(List<AdvancedParam> advancedList);
	List<Map<String, Object>> queryXflxfb(List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 未缴纳学费学生类型分布
	 * @param deptList
	 * @return
	 */
	List<Map<String, Object>> queryXslxfb(List<AdvancedParam> advancedList);
	List<Map<String, Object>> queryXslxfb(List<String> deptList,List<AdvancedParam> advancedList);	
	/**
	 * 历年未缴费金额及比例
	 * @param deptList
	 * @return
	 */
	Map<String, Object> queryQfjeAndRadio(List<AdvancedParam> advancedList);
	/**
	 * 得到欠费的详细信息
	 * @param edu 学历
	 * @param lx  欠费类型
	 * @param slx 在籍状态
	 * @return
	 */
	Map<String, Object> queryQfInfo(Integer year,String edu,String lx,String slx);
	/**
	 * 发送邮件
	 * @param pid 院系id
	 * @return
	 */
	Map<String, Object> sendQfInfo(String sendType,String pid,String list,String year,String edu, String lx,
			String slx,HttpServletRequest request);
	/**
	 * 全部发送
	 * @return 发送后的状态(成功或者是失败)
	 */
	Map<String, Object> sendAll(HttpServletRequest request);
	/**
	 * 导出excel文件
	 * @return
	 */
	void exportQfInfo(String pid,String list,String year,String edu, String lx,
			String slx,HttpServletResponse response);
	/**
	 * 全部导出
	 * @param response 返回导出的资源
	 */
	void excelQfAll(HttpServletRequest request,HttpServletResponse response );
	/**
	 * 得到学籍状态
	 * @return
	 */
	List<Map<String, Object>> queryXjList();
	/**
	 * 学生 详情
	 * @param advancedParamList 高级查询参数
	 * @param page 分页参数
	 * @param keyValue 其他参数
	 * @param fields 查询字段
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getTeaDetail(List<AdvancedParam> advancedParamList, Page page, 
			Map<String, Object> keyValue, List<String> fields);

	/**
	 * 学生详情（全部）
	 * @param advancedParamList 高级查询参数
	 * @param keyValue 其他参数
	 * @param fields 查询字段
	 * @return String
	 */
	public List<Map<String, Object>> getStuDetailAll(List<AdvancedParam> advancedParamList,Page page, Map<String, Object> keyValue, List<String> fields);
}
