package com.jhkj.mosdc.sc.job;

/**   
* @Description: TODO 学生消费数据相关处理JOB
* @author Sunwg  
* @date 2014-10-31 下午3:22:01   
*/
public interface StudentXfmxService {
	/** 
	* @Title: queryXsxfqk 
	* @Description: TODO 定时计算学生的消费天数和日均消费额，
	* 		将查询的结果插入表 TB_YKT_PKS_TEMP（学生ID，消费天数，日均消费金额）
	* 		根据计算的数据进行学生贫困情况的分析
	* @return void
	*/
	public void queryXsxfqk();
	
	/** 
	* @Title: queryYxxsxfqk 
	* @Description: TODO 查询院系的学生的消费情况
	* @return String
	*/
	public String queryYxxsxfqk(String params);
	
	/** 
	* @Title: quertYxyspkxs 
	* @Description: TODO 查询院系疑似贫困学生名单
	* @return String
	*/
	public String quertYxyspkxs(String params);
	
	/** 
	* @Title: queryXfpje 
	* @Description: TODO 查询消费平均额
	* @return String
	*/
	public String queryXfpje(String params);
}
