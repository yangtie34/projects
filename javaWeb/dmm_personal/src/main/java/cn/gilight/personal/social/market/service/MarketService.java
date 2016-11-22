package cn.gilight.personal.social.market.service;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

public interface MarketService {
	
	
	/**
	 * 查询学生发布及已售商品数量
	 * @param stu_id
	 * @return
	 */
	public Map<String,Object> getCommodityNums(String stu_id);
	
	/**
	 * 查询商品分类
	 * @return
	 */
	public List<Map<String,Object>> getCommodityType();
	
	/**
	 * 查询所有商品
	 * @param Page  Page
	 * @param type_code  商品类别
	 * @param keyword   关键字
	 * @param flag  商品状态（所有：all,未售：notSold）   
	 * @return
	 */
	public Page getCommoditys(Page page,String type_code,String keyword,String flag);
	
	/**
	 * 查询商品详情
	 * @param commodity_id  商品ID
	 * @return
	 */
	public Map<String,Object> getCommodityDetails(String commodity_id);
	
	/**
	 * 查询我发布的商品
	 * @param stu_id
	 * @return
	 */
	public List<Map<String,Object>> getMyCommoditys(String stu_id);
	
	/**
	 * 修改商品状态
	 * @param commodity_id  商品ID
	 * @param is_sold  是否已售
	 * @return
	 */
	public int modifyCommodityState(String commodity_id,String is_sold);
	
	
}
