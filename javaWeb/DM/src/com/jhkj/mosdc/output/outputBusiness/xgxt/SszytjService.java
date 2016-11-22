package com.jhkj.mosdc.output.outputBusiness.xgxt;

import com.jhkj.mosdc.output.po.FunComponent;

/***
 * 宿舍资源以及使用情况统计service接口
 * @Comments: 
 * Company: 河南精华科技有限公司
 * Created by wangyongtai(490091105@.com)
 * @DATE:2012-11-27
 * @TIME: 下午2:44:19
 */
public interface SszytjService {
   
	/***
	 * 查询宿舍资源使用信息
	 * 学校共有宿舍楼12栋，可入住人数为2323人。
	 * @param fun
	 * @param params
	 * @return
	 */
	public FunComponent querySszyText(FunComponent fun,String params);
	/***
	 * 查询宿舍入住情况
	 *  宿舍已经入住人数为2123人，未入住学生为323人。 
	 * @param fun
	 * @param params
	 * @return
	 */
	public FunComponent querySsRzqkText(FunComponent fun,String params);
	/***
	 * 查询宿舍入住情况(分系部/性别)
	 * 1 维度 系部/性别
	   2 范围  已入住/未入住
	   3 指标 统计学生数量 

	 * @param fun
	 * @param params
	 * @return
	 */
	public FunComponent queryRzqkChart(FunComponent fun,String params);
	/***
	 * 查询宿舍入住率
	 * 宿舍入住率为80%，男生入住率为83%，女生入住率为78%。
	 * @param fun
	 * @param params
	 * @return
	 */
	public FunComponent querySsRzlText(FunComponent fun,String params);
	/***
	 * 查询宿舍入住率(分系部/性别)
	 * 1 维度 系部/性别
	   2 范围  全体学生
	   3 指标 统计学生入住率
	 * @param fun
	 * @param params
	 * @return
	 */
	public FunComponent querySsRzlChart(FunComponent fun,String params);
	/***
	 * 查询宿舍分年级入住情况统计
	 * 一年级学生入住人数为564人，二年级学生入住人数为433人，三年级入住人数为343人。
   	       一年级学生入住人率为90%人，二年级学生入住率为87%人，三年级入住人率为78%人。
	 * @param fun
	 * @param params
	 * @return
	 */
	public FunComponent querySsFnjRzqkText(FunComponent fun,String params);
	/***
	 * 查询宿舍分年级入住情况统计(分系部/性别)
	 * 1 维度 年级
	   2 范围  已入住/未入住
	   3 指标 统计学生入住率/入住人数
	 * @param fun
	 * @param params
	 * @return
	 */
	public FunComponent querySsFnjRzqkChart(FunComponent fun,String params);
}
