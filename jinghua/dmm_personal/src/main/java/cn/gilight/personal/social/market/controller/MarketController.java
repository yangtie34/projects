package cn.gilight.personal.social.market.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.jhnu.syspermiss.util.ContextHolderUtils;

import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.HttpResult;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.personal.po.TCommodity;
import cn.gilight.personal.po.TCommodityImage;
import cn.gilight.personal.social.market.service.MarketService;


@Controller("marketController")
@RequestMapping("/social/market")
public class MarketController {
	
	private Logger log = Logger.getLogger(MarketController.class);
	
	@Resource
	private HibernateDao hibernate;
	
	@Autowired
	private MarketService marketService;
	
	/** 
	* @Description: 查询学生发布及已售商品数量
	* @return Map<String,Object>
	*/
	@RequestMapping("/queryCommodityNums")
	@ResponseBody
	public HttpResult queryCommodityNums() {
		HttpResult result = new HttpResult();
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username+ "查询发布及已售商品数");
		try {
			result.setResult(marketService.getCommodityNums(username));
			result.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}
	
	/** 
	* @Description: 查询商品分类
	* @return 
	*/
	@RequestMapping("/queryCommodityType")
	@ResponseBody
	public HttpResult queryCommodityType() {
		HttpResult result = new HttpResult();
		log.debug("查询商品类型");
		try {
			result.setResult(marketService.getCommodityType());
			result.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}
	
	/** 
	* @Description: 查询所有商品
	* @return 
	*/
	@RequestMapping("/queryAllCommodity")
	@ResponseBody
	public HttpResult queryAllCommodity(Page page,String type_code,String keyword,String flag) {
		HttpResult result = new HttpResult();
		log.debug("查询所有商品");
		try {
			result.setResult(marketService.getCommoditys(page, type_code, keyword, flag));
			result.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}
	
	/** 
	* @Description: 查询商品详情
	* @return 
	*/
	@RequestMapping("/queryCommodityDetails")
	@ResponseBody
	public HttpResult queryCommodityDetails(String commodity_id) {
		HttpResult result = new HttpResult();
		log.debug("查询商品详情");
		try {
			result.setResult(marketService.getCommodityDetails(commodity_id));
			result.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}
	
	/** 
	* @Description: 查询我发布的商品
	* @return 
	*/
	@RequestMapping("/queryMyCommoditys")
	@ResponseBody
	public HttpResult queryMyCommoditys() {
		HttpResult result = new HttpResult(); 
		// 获取cas中登陆者的登陆名称
		AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
		String username = principal.getName();
		log.debug(username+ "查询发布的商品");
		try {
			result.setResult(marketService.getMyCommoditys(username));
			result.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}
	
	/** 
	* @Description: 修改商品状态
	* @return boolean
	*/
	@RequestMapping("/modifyCommodityState")
	@ResponseBody
	@Transactional
	public HttpResult modifyCommodityState(String commodity_id,String is_sold) {
		HttpResult result =  new HttpResult();
		try {
			marketService.modifyCommodityState(commodity_id, is_sold);
			result.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}
	
	@RequestMapping("/saveCommodity")
	@ResponseBody
	@Transactional
	public HttpResult saveCommodity(TCommodity commodity,String images){
		HttpResult result = new HttpResult();
		try{
			//保存商品信息
			AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
			String username = principal.getName();
			commodity.setStuId(username);
			commodity.setCreateTime(DateUtils.getNowDate());
			commodity.setIsSold(false);
			hibernate.save(commodity);
			
			//保存图片
			JSONArray imagesList = JSONArray.parseArray(images);
			if(imagesList.size() > 0){
				List<TCommodityImage> imagesStore = new ArrayList<TCommodityImage>();
				for (int i = 0; i < imagesList.size(); i++) {
					TCommodityImage image = new TCommodityImage();
					image.setCommodityId(commodity.getId());
					image.setImageUrl(imagesList.getString(i));
					image.setOrder(i+1);
					imagesStore.add(image);
				}
				hibernate.saveAll(imagesStore);
			}
			result.setSuccess(true);
		} catch (Exception e) {
			 result.setSuccess(false);
			 log.error(e.getMessage());
			 result.setErrmsg("保存失败，请重试");
		}
		return result;
	}

}
