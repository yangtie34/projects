package cn.gilight.personal.social.market.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.test1.util.MapUtils;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.personal.social.market.service.MarketService;

@Service("marketService")
public class MarketServiceImpl implements MarketService{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public Map<String, Object> getCommodityNums(String stu_id) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		String mySql = "select t.name_ stu_name,wb.wechat_head_img from t_stu t left join t_wechat_bind wb on wb.username = t.no_ where t.no_ = '"+stu_id+"'";
		List<Map<String,Object>> myList = baseDao.queryListInLowerKey(mySql);
		if(myList != null && myList.size()>0){
			resultMap.put("stu_name", MapUtils.getString(myList.get(0), "stu_name"));
			resultMap.put("wechat_head_img", MapUtils.getString(myList.get(0), "wechat_head_img"));
		}
		
		String putSql = "select count(*) put_nums from t_commodity t where t.stu_id = '"+stu_id+"'";
		List<Map<String,Object>> putList = baseDao.queryListInLowerKey(putSql);
		if(putList != null && putList.size()>0){
			resultMap.put("put_nums", MapUtils.getIntValue(putList.get(0), "put_nums"));
		}
		
		String soldSql = "select count(*) sold_nums from t_commodity t where t.stu_id = '"+stu_id+"' and t.is_sold = 1";
		List<Map<String,Object>> soldList = baseDao.queryListInLowerKey(soldSql);
		if(soldList != null && soldList.size()>0){
			resultMap.put("sold_nums", MapUtils.getIntValue(soldList.get(0), "sold_nums"));
		}
		
		return resultMap;
	}

	@Override
	public List<Map<String, Object>> getCommodityType() {
		String sql = "select t.code_,t.name_ from t_code t where t.code_type = 'COMMODITY_TYPE_CODE' and t.istrue = 1";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Page getCommoditys(Page page,String type_code, String keyword,String flag) {
		String sql1 = "";
		if(StringUtils.hasText(type_code)){
			sql1 = "and t.commodity_type_code = '"+type_code+"'";
		}
		String sql2 = "";
		if(StringUtils.hasText(keyword)){
			sql2 = "and t.keyword like '%"+keyword+"%'";
		}
		String sql3 = "";
		if(StringUtils.hasText(flag)){
			if("notSold".equals(flag)){
				sql2 = " and t.is_sold = 0 ";
			}
		}
		String sql = "select t.id,t.name_,t.use_time,t.price,t.stu_name,t.tel,t.create_time,ci.image_url,t.is_sold from t_commodity t "
				+ " left join t_commodity_image ci on ci.commodity_id = t.id"
				+ " and ci.order_ = 1 where 1=1 "+sql1+sql2+sql3+" order by t.create_time desc";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}

	@Override
	public Map<String, Object> getCommodityDetails(String commodity_id) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		String sql = "select t.name_,t.desc_,t.price,t.stu_name,t.tel,ci.image_url,wb.wechat_head_img from t_commodity t "
				+ " left join t_commodity_image ci on ci.commodity_id = t.id "
				+ " left join t_wechat_bind wb on wb.username = t.stu_id where t.id = '"+commodity_id+"' and ci.order_ = 1";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list!=null && list.size()>0){
			resultMap = list.get(0);
		}
		
		String imgSql = "select t.image_url,t.order_ from t_commodity_image t where t.commodity_id = '"+commodity_id+"' order by order_";
		List<Map<String,Object>> imgList = baseDao.queryListInLowerKey(imgSql);
		if(imgList!=null && imgList.size()>0){
			resultMap.put("imgList", imgList);
		}
		
		return resultMap;
	}

	@Override
	public List<Map<String, Object>> getMyCommoditys(String stu_id) {
		String sql = "select t.id, ci.image_url,t.name_,t.use_time,t.price,t.stu_name,t.tel,t.is_sold,t.create_time,t.sold_time from t_commodity t "
				+ " left join t_commodity_image ci on ci.commodity_id = t.id and ci.order_ = 1  where t.stu_id = '"+stu_id+"' order by t.create_time desc";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public int modifyCommodityState(String commodity_id,String is_sold) {
		String sold_time = DateUtils.getNowDate();
		String sql = "update t_commodity t set t.is_sold = '"+is_sold+"',t.sold_time = '"+sold_time+"' where t.id = '"+commodity_id+"'";
		return baseDao.update(sql);
	}

}
