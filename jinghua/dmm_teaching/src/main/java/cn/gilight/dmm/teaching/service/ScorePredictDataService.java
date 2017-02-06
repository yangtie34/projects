package cn.gilight.dmm.teaching.service;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.teaching.entity.TStuScorepredTermGroup;

public interface ScorePredictDataService {

	/**
	 * 接口-获取真分组
	 * @param start_schoolYear 开始学年
	 * @param start_termCode 开始学期
	 * @param end_schoolYear 结束学年
	 * @param end_termCode 结束学期
	 * @return List<Group>
	 */
	public List<TStuScorepredTermGroup> getRealGroup(String start_schoolYear, String start_termCode,
			String end_schoolYear, String end_termCode);

	/**
	 * 接口-根据真分组获取伪分组
	 * @param start_schoolYear 开始学年
	 * @param start_termCode 开始学期
	 * @param end_schoolYear 结束学年
	 * @param end_termCode 结束学期
	 * @param groupList 真分组
	 * @return Map<String,Object> eg:{hasMode:List<Group>,noMode:List<Group>}
	 */
	public Map<String,Object> getFakeGroup(String start_schoolYear, String start_termCode,
			String end_schoolYear, String end_termCode, List<TStuScorepredTermGroup> groupList);

	/**
	 * 接口-获取训练集学生成绩列表
	 * @param group 分组
	 * @return List<Map<String, Object>> 
	 */
	public List<Map<String, Object>> getTrainingData(TStuScorepredTermGroup group);

	
	/**
	 * 接口-获取测试集学生成绩列表
	 * @param group 分组
	 * @return List<Map<String, Object>> 
	 */
	public List<Map<String, Object>> getTestData(TStuScorepredTermGroup group);

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
