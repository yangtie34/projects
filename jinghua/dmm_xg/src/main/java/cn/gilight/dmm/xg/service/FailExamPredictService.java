package cn.gilight.dmm.xg.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.framework.page.Page;

/**
 * 挂科预测
 * @author lijun
 *
 */
public interface FailExamPredictService {
	/**
	 * 得到学年 
	 * @return
	 */
	Map<String, Object> getYearAndTerm();
	/**
	 * 得到当前学期挂科预测学生期末考试人数和挂科概率
	 * @param deptList
	 * @return
	 */
	Map<String, Object> queryGkInfo(List<AdvancedParam> advancedList);
	Map<String, Object> queryGkInfo(List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 得到挂科类型分布
	 * @param deptList
	 * @return
	 */
	List<Map<String, Object>> queryGklxfb(List<AdvancedParam> advancedList);
	List<Map<String, Object>> queryGklxfb(List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 得到挂科预测学生类型分布
	 * @param deptList
	 * @return
	 */
	List<Map<String, Object>> queryXslxfb(List<AdvancedParam> advancedList);
	List<Map<String, Object>> queryXslxfb(List<String> deptList,List<AdvancedParam> advancedList);	
	/**
	 * 各院系挂科预测人数及挂科概率
	 * @param deptList
	 * @return
	 */
	Map<String, Object> queryGkrsAndRadio(List<AdvancedParam> advancedList);
	/**
	 * 得到挂科预测的详细信息
	 * @param edu 学历
	 * @param lx  欠费类型
	 * @param slx 在籍状态
	 * @return
	 */
	Map<String, Object> queryGkxxInfo(List<AdvancedParam> advancedList);
	/**
	 * 发送邮件
	 * @param pid 院系id
	 * @return
	 */
	Map<String, Object> sendGkInfo(String sendType,String pid,String list,HttpServletRequest request);
	/**
	 * 全部发送
	 * @return 发送后的状态(成功或者是失败)
	 */
	Map<String, Object> sendAll(HttpServletRequest request);
	/**
	 * 导出excel文件
	 * @return
	 */
	void exportGkInfo(String pid,String list,HttpServletResponse response);
	/**
	 * 全部导出
	 * @param response 返回导出的资源
	 */
	void excelGkAll(HttpServletRequest request,HttpServletResponse response );
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
