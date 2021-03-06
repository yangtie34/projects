package cn.gilight.dmm.teaching.controller;

/**
 * 
 * 
 * @author xuebl
 * @date 2017年1月7日 下午7:07:59
 */
public class A {

	/**
	 * 总纲：预测今年数据，需要最合理的真分组及模型
	 * 原因：对于今年目前最合理的真分组是由去年的真实成绩验证得来的前年的分组
	 */
	
	/**
	 * 需求：预测今年的课程成绩成绩—根据最优模型匹配得到最佳预测成绩
	 * 
	 * 解释：
	 * 	伪分组：上一年真分组 和 今年新课程的分组
	 * 	伪模型：上一年真模型 和 今年新课程的随机模型
	 * 
	 * 
	 * 分析：预测今年的数据：
	 * 1. 去年的伪分组 与伪模型
	 * 2. 预测今年成绩
	 * 
	 * 
	 * 1.去年的伪分组 与伪模型
	 * 		1.1 前年的真分组 和 去年的真成绩（准备数据）
	 * 			查：前年的真分组（接口）
	 * 			        去年的真成绩（接口）
	 * 			存：前年真分组
	 * 				【学生成绩预测（学期）-分组表（T_STU_SCORE_PREDICT_TERM_GROUP）】
	 * 		1.2 预测去年成绩（四种模型）并存储
	 * 			查：前年的训练集、去年的测试集（根据前年的真分组循环处理）
	 * 			R预测
	 * 			存：四种模型预测成绩（已有分组ID、模型ID）
	 * 				【学生成绩预测（学期）-分组成绩表（T_STU_SCORE_PREDICT_TERM_GP_CJ）】
	 * 		1.3 确定前年真模型
	 * 			查：去年的真成绩、去年的预测成绩（四种）（根据 分组+课程 循环处理）
	 * 			R分析（最优模型）
	 * 			存：前年真模型（更新）
	 * 				【学生成绩预测（学期）-分组模型表（T_STU_SCORE_PREDICT_TERM_GP_MD）】
	 * 		1.4 去年的伪分组和伪模型
	 * 			查：前年真分组真模型（已有）（接口）
	 * 				接口内：去年课程数据
	 * 				得：今年的伪分组和部分伪模型
	 * 			填充未知模型（未知课程）
	 * 			存：去年伪分组和伪模型
	 * 				【学生成绩预测（学期）-分组表（T_STU_SCORE_PREDICT_TERM_GROUP），
	 * 				    学生成绩预测（学期）-分组模型表（T_STU_SCORE_PREDICT_TERM_GP_MD）】
	 * 
	 * 2.预测今年成绩
	 * 		2.1 预测
	 * 			查：训练集、测试集、模型（按分组循环）
	 * 			R预测
	 * 			存：预测成绩
	 * 				【学生成绩预测（学期）-历次预测成绩表（T_STU_SCORE_PREDICT_TERM_HIS），
	 * 				    学生成绩预测（学期）表（T_STU_SCORE_PREDICT_TERM）（去重）】
	 * 		2.2 今年新开课程成绩预测
	 * 			置为无效成绩（成绩：0，是否有效：0）
	 * 
	 */
	
	/**
	 * 真分组接口
	 * 	参数：开始学年、开始学期、结束学年、结束学期
	 * 	返回值：List<Group>
	 * 
	 * 伪分组接口
	 * 	参数：真分组、开始学年、开始学期、结束学年、结束学期
	 * 	返回值：[List<Group>, List<Group>]（第一个有模型，第二个没有模型）
	 * 
	 * 训练集接口（可能重抽样）
	 * 	参数：分组（Group）
	 * 	返回值：学生成绩列表[List<Map>]
	 * 	
	 * 测试集接口
	 * 	参数：分组（Group）
	 * 	返回值：学生成绩列表[List<Map>]
	 * 
	 * 分组模型更新接口
	 * 	参数：分组
	 * 	返回值：无
	 * 
	 * 
	 * 去年真实成绩接口
	 * 去年预测成绩接口
	 * 
	 * 	
	 * 分组成绩保存接口
	 * 	
	 * 	
	 * 判断最优模型接口
	 * 	
	 * 	
	 * 预测成绩保存接口
	 * 	
	 * 	
	 */
	
}
