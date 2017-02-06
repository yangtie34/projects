package cn.gilight.dmm.xg.service;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.framework.page.Page;

public interface StuFromService {
	/**
	 * 查询最小的入学年级
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getMinGrade();
	/**
	 * 查询所有学生就读学历
	 * @return Map<String,Object>
	 */
	public List<Map<String, Object>> getStuEdu();
	/**
	 * 查询各地区学生人数
	 * @param pid 部门id
	 * @param from 起始时间
	 * @param to 结束时间
	 * @param edu 就读学历
	 * @param Id 生源地id
	 * @param updown 上钻下钻   true为下钻，false为上钻
	 * @param pagesize 分页数据：每页条数
	 * @param index 分页数据：当前页数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getStuFrom(List<AdvancedParam> advancedParamList, String from, String to,
			String edu, String divid,Boolean updown,int pagesize,int index);
	/**
	 * 查询各地区学生人数变化趋势
	 * @param pid 部门id
	 * @param from 起始时间
	 * @param to 结束时间
	 * @param edu 就读学历
	 * @param Id 生源地id
	 * @param updown 上钻下钻   true为下钻，false为上钻
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getCountLine(List<AdvancedParam> advancedParamList, String from, String to,
			String edu, String divid);
	/**
	 * 查询各地区各学校的来校人数
	 * @param pid 部门id
	 * @param from 起始时间
	 * @param to 结束时间
	 * @param edu 就读学历
	 * @param Id 生源地id
	 * @param updown 上钻下钻   true为下钻，false为上钻
	 * @param pagesize 分页数据：每页条数
	 * @param index 分页数据：当前页数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getSchoolTag(List<AdvancedParam> advancedParamList, String from, String to,
			String edu, String divid, Boolean updown,int pagesize,int index,Boolean Order);
	/**
	 * 查询各地区增长数和增长率
     * @param pid 部门id
	 * @param from 起始时间
	 * @param to 结束时间
	 * @param edu 就读学历
	 * @param Id 生源地id
	 * @param updown 上钻下钻   true为下钻，false为上钻
	 * @param pagesize 分页数据：每页条数
	 * @param index 分页数据：当前页数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getGrowth(List<AdvancedParam> advancedParamList, String from, String to,
			String edu, String divid, Boolean updown);
	/**
	 * 查询各地区学生人数表格数据
     * @param pid 部门id
	 * @param from 起始时间
	 * @param to 结束时间
	 * @param edu 就读学历
	 * @param Id 生源地id
	 * @param updown 上钻下钻   true为下钻，false为上钻
	 * @param pagesize 分页数据：每页条数
	 * @param index 分页数据：当前页数
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getStuFromTab(List<AdvancedParam> advancedParamList, String from, String to,
			String edu, String divid, Boolean updown,int pagesize,int index);
    /**
     * 查询学生名单
     * @param advancedParamList 高级查询参数
     * @param page 分页数据
     * @param keyValue 其他参数
     * @param fields 查询字段
     * @param divid 行政区划
     * @param updown 上钻还是下钻
     * @param from 开始学年
     * @param to 结束学年
     * @param edu 就读学历
     * @return Map<String,Object>
     */
	public Map<String, Object> getStuList(List<AdvancedParam> advancedParamList,
			Page page, Map<String, Object> keyValue, List<String> fields/*,
			String divid, Boolean updown, String from, String to, String edu*/);
	/**
	 * 导出学生名单
	 * @param advancedParamList
	 * @param keyValue
	 * @param fields
	 * @param divid
	 * @param updown
	 * @param from
	 * @param to
	 * @param edu
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getStuDetail(
			List<AdvancedParam> advancedParamList,
			Map<String, Object> keyValue, List<String> fields/*, String divid,
			Boolean updown, String from, String to, String edu*/);
	/**
	 * 获取近5年某地区学生人数变化
	 * @param advancedParamList 高级查询参数
	 * @param to 年级
	 * @param edu 就读学历
	 * @param divid 生源地id
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getCountAndLv(List<AdvancedParam> advancedParamList,String from,
			String to, String edu, String divid);
	/**
	 * 获取导出数据
	 * @param advancedParamList 高级查询参数
	 * @param from 开始日期
	 * @param to  结束日期
	 * @param edu 就读学历
	 * @param divid 生源地id
	 * @param updown 上钻还是下钻
	 * @param bs 是哪个导出
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getExportData(
			List<AdvancedParam> advancedParamList, String from, String to,
			String edu, String divid, Boolean updown, String bs);
}
