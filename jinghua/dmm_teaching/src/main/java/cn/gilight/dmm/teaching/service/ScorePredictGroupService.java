package cn.gilight.dmm.teaching.service;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.teaching.entity.TStuScorepredTermGroup;

public interface ScorePredictGroupService {

	/**
	 * 接口-获取真分组
	 * @param start_schoolYear 开始学年
	 * @param start_termCode 开始学期
	 * @param end_schoolYear 结束学年
	 * @param end_termCode 结束学期
	 * @param yc_start_schoolYear 预测开始学年
	 * @param yc_start_termCode 预测开始学期
	 * @param yc_end_schoolYear 预测结束学年
	 * @param yc_end_termCode 预测结束学期
	 * @return Map<String,Object> {isHg:[合格的分组集合],noHg:[不合格的分组集合]}
	 */
	public Map<String,Object> getRealGroup(String start_schoolYear, String start_termCode,
			String end_schoolYear, String end_termCode,String yc_start_schoolYear,
			String yc_start_termCode,String yc_end_schoolYear,String yc_end_termCode);

	/**
	 * 接口-根据真分组获取伪分组
	 * @param start_schoolYear 开始学年
	 * @param start_termCode 开始学期
	 * @param end_schoolYear 结束学年
	 * @param end_termCode 结束学期
	 * @param groupList 真分组
	 * @return Map<String,Object>
	 *  eg:{hasMode:List<TStuScorePredictTermGroup>,noMode:List<TStuScorePredictTermGroup>,noHg:List<TStuScorePredictTermGroup>}
	 *  hasMode：有模型 ，noMode：无模型，noHg：没有找到合格的分组
	 */
	public Map<String,Object> getFakeGroup(String start_schoolYear, String start_termCode,
			String end_schoolYear, String end_termCode, List<TStuScorepredTermGroup> groupList);


	/**
	 * 是否是合格的分组
	 * @param group
	 * @return boolean
	 */
	public boolean isValidGroup(TStuScorepredTermGroup group);
	
	/**
	 * 是否是合格的训练集
	 * @param group
	 * @return boolean <br>
	 * true:'合格'
	 */
	public boolean isValidTrainingData(List<List<String>> list);
	
	/**
	 * 接口-获取训练集学生成绩列表
	 * @param group 分组
	 * @return List<List<String>> <br>
	 * [
	 * [20120101, 54, 76, 93, 50],
	 * [20120101, 54, 76, 93, 50]
	 * ]
	 */
	public List<List<String>> getTrainingData(TStuScorepredTermGroup group);

	
	/**
	 * 接口-获取测试集学生成绩列表（需要空值）
	 * @param group 分组
	 * @return List<List<String>> <br>
	 * [
	 * [20120101, 54, "", 93, 50],
	 * [20120101, 54, 76, "", 50]
	 * ]
	 */
	public List<List<String>> getTestData(TStuScorepredTermGroup group);

	/**
	 * 接口-获取学生成绩列表（不需要空值）
	 * @param group
	 * @return List<List<String>> <br>
	 * [
	 * [20120101, 54, 76, 93, 50],
	 * [20120101, 54, 76, 93, 50]
	 * ]
	 */
	public List<List<String>> getScoreData(TStuScorepredTermGroup group);
	
	/**
	 * 根据分组获取学生ID sql
	 * 
	 * @param group
	 * @return String
	 * <br> select stu_id from t
	 */
	public String getStuIdSqlByGroup(TStuScorepredTermGroup group);
	
	/**
	 * 接口-更新分组最优模型
	 * @param groupList 分组集合
	 */
	public void saveBestMode(List<TStuScorepredTermGroup> groupList);
	
	/**
	 * 接口-更新分组最优模型
	 * @param group 单个分组
	 */
	public void saveBestMode(TStuScorepredTermGroup group);
	

}
