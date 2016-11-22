package com.jhkj.mosdc.sc.service;

import java.io.UnsupportedEncodingException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public interface IThesisNumsRanksService {

	public String eINumsRanks(String typeCode);

	public String eIYears(String typeCode);

	public String iSTPNumsRanks(String typeCode);

	public String iSYears(String params);

	public String cPCINumsRanks(String params);

	public String cPYears(String params);

	public String allRanks(String params);

	/**
	 * @description 各院系论文数
	 * @param year
	 * @return
	 */
	public String departmentThesisNums(String datas);

	/**
	 * @description 各学院获奖论文的比率
	 * @param datas
	 * @return
	 */
	public String awardThesisRate(String datas);
	
	/**
	 * @description 各年各院系的论文数量
	 * @param datas
	 * @return
	 */
	public String kindOfYearsDeptsCounts(String datas);

	/**
	 * @description 被收录的论文数量和院系名称
	 * @param datas
	 * @return
	 */
	public String thesisInclude(String datas);

	/**
	 * @description 查询所有的科研论文数
	 * @param datas
	 * @return
	 */
	public String queryAllCountsThesis(String datas);

	/**
	 * @description 查询某院系某年发布的期刊数量和名称
	 * @param datas
	 * @return
	 */
	public String pulishThesis(String datas);

	/**
	 * @description 查询某院系某年的获奖数量、等级以及名称
	 * @param datas
	 * @return
	 */
	public String awardThesisRanks(String datas);

	/**
	 * @description 查询某院系某年的EI收录数量
	 * @param datas
	 * @return
	 */
	public String eIInclude(String datas);

	/**
	 * @description 查询某院系某年的ISTP收录数量
	 * @param datas
	 * @return
	 */
	public String iSInclude(String datas);

	/**
	 * @description 查询某院系某年的CPCI-SI收录数量
	 * @param datas
	 * @return
	 */
	public String cPInclude(String datas);
	
	/**
	 * @description 历年的获奖趋势
	 * @param datas
	 * @return
	 */
	public String awardTrend(String datas);
	
	/**
	 * @description 发布的期刊数量和名称以及年份
	 * @param datas
	 * @return
	 */
	public String includeTrend(String datas);
	
	/**
	 * @description 发布的期刊所有名称
	 * @param datas
	 * @return
	 */
	public String includeTrendNames(String datas);
	
	/**
	 * @description 收录的期刊数量和名称以及年份
	 * @param datas
	 * @return
	 */
	public String thesisTrend(String datas);
	
	/**
	 * @description 根据学科门类查询所有获奖院系数量
	 * @param datas
	 * @return
	 */
	public String awardRatesAll(String datas);
	
	/**
	 * @description 查询所有获奖数量
	 * @param datas
	 * @return
	 */
	public String queryAllAwardCounts(String datas);
	
	/**
	 * @description 查询所有收录的数量
	 * @param datas
	 * @return
	 */
	public String queryAllIncludeCounts(String datas);
	
	/**
	 * @description 根据某院系，某年，某种期刊进行分页查询
	 * @param datas
	 * @return
	 */
	public String queryThesisLimitPage(String datas);
	
	/**
	 * @description 论文明细导出excel表格
	 * @param datas
	 * @return
	 */
	public HSSFWorkbook exportExcelPilish(String datas) throws Exception;
	
	/**
	 * @description 根据某院系，某年，获奖论文进行分页查询
	 * @param datas
	 * @return
	 */
	public String queryAwardThesisLimitPage(String datas);
	
	/**
	 * @description 获奖论文明细导出excel表格
	 * @param datas
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook exportExcelAward(String datas) throws Exception;
	
	/**
	 * @description 根据某院系，某年，EI收录论文进行分页查询
	 * @param datas
	 * @return
	 */
	public String queryEIThesisLimitPage(String datas);
	
	/**
	 * @description 被EI收录论文明细导出excel表格
	 * @param datas
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook exportExcelEIInclude(String datas) throws Exception;
	
	/**
	 * @description 根据某院系，某年，IS收录论文进行分页查询
	 * @param datas
	 * @return
	 */
	public String queryISThesisLimitPage(String datas);
	
	/**
	 * @description 被IS收录论文明细导出excel表格
	 * @param datas
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook exportExcelISInclude(String datas) throws Exception;
	
	/**
	 * @description 根据某院系，某年，CP-CI收录论文进行分页查询
	 * @param datas
	 * @return
	 */
	public String queryCPThesisLimitPage(String datas);
	
	/**
	 * @description 被CP-CI收录论文明细导出excel表格
	 * @param datas
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook exportExcelCPInclude(String datas) throws Exception;
	
}
