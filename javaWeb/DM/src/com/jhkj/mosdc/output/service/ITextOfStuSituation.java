package com.jhkj.mosdc.output.service;

import com.jhkj.mosdc.output.po.FunComponent;
/**
 * 学生人数概况统计功能。
 * the number of students general situation
 * 
 * @Comments: 
 * Company: 河南精华科技有限公司
 * Created by zhangzg(vbxnt521_edu@163.com)
 * @DATE: 2012-11-19
 * @TIME: 下午04:58:05
 */
public interface ITextOfStuSituation {
	public FunComponent initData(FunComponent fc,String params);
	/**
	 * 获取文本形式的学生人数概况。
	 * @param fc 统计功能组件。
	 * @param params 参数列表。
	 * @return
	 * 学生人数概况：
	 * 	全校总人数有201012人，全校13个专业中的在校学生有201012名。
	 * 	一年级有2001名，二年级有2010名，三年级有2779名。
	 * 	这一新的学年中，新招6000名学生，毕业离校6700名学生。
	 */
	public FunComponent queryTextNumOfStu(FunComponent fc , String params);
	/**
	 * 获取文本形式的在校生性别构成。
	 * @param fc
	 * @param params
	 * @return
	 * 全校总人数有201012人，男生101010人，女生100000人。未维护性别2人，数据误差0.001%。
	 */
	public FunComponent queryTextSexOfStu(FunComponent fc , String params);
	/**
	 * 获取文本形式的在校生民族构成。
	 * @param fc
	 * @param params
	 * @return
	 * 在全校13个专业中，汉族人口为32641人，占91.51%；各少数民族人口为92211人，占8.49%。
	 */
	public FunComponent queryTextNationOfStu(FunComponent fc, String params);
	/**
	 * 人数增长。
	 * @param fc
	 * @param params
	 * @return
	 * 在全校13个专业中，在校学生数增加3400人，比去年的3300人多出100人，平均增长提高了1.78%
	 */
	public FunComponent queryTextAddOfStu(FunComponent fc, String params);
	/**
	 * 政治面貌
	 * @param fc
	 * @param params
	 * @return
	 * 在全校13个专业中，党员人数为2000人，占10%；团员人数有17000人，占86%；其他1030人，占6.57%。
	 */
	public FunComponent queryTextZzmmOfStu(FunComponent fc, String params);
	/**
	 * 获取简单图形数据。
	 * @param fc
	 * @param params
	 * @return
	 * 范围 1年级、2年级、3年级
	 * 维度 性别
	 * 指标 性别人数
	 */
	public FunComponent querySimpleChartOfStu(FunComponent fc, String params);
}
