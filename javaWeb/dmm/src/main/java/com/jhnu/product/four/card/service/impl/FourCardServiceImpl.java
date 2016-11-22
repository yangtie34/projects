package com.jhnu.product.four.card.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhnu.product.common.stu.service.StuService;
import com.jhnu.product.four.card.dao.FourCardDao;
import com.jhnu.product.four.card.service.FourCardService;
import com.jhnu.product.four.common.entity.ResultBean;
import com.jhnu.system.common.condition.Condition;
import com.jhnu.system.common.page.Page;
import com.jhnu.util.common.MapUtils;
import com.jhnu.util.common.MathUtils;
@Service("fourCardService")   
public class FourCardServiceImpl implements FourCardService{
	
	@Autowired
	private StuService stuService;
	
	@Autowired
	private FourCardDao fourCardDao;
	
	private static final Logger logger = Logger.getLogger(FourCardServiceImpl.class);
	
	@Override
    public ResultBean getFirstPayMsg(String Id){
    	
    	ResultBean firstEat=new ResultBean();
    	List<Map<String,Object>> result=fourCardDao.getFirstPayMsg(Id);
    	firstEat.setName("getFirstPayMsg");
        firstEat.getData().put("getFirstPayMsg",result );
    	return firstEat;
    	
    	
    }
	
	public ResultBean getSumMoneyAndPayTimes(String Id){
		
		
		ResultBean sum=new ResultBean();
		sum.setName("getSumMoneyAndPayTimes");
		sum.getData().put("getSumMoneyAndPayTimes", fourCardDao.getSumMoneyAndPayTimes(Id));
		return sum;
		
	}
	
	
	
	public ResultBean getWashTimes(String Id){
		
		ResultBean result=new ResultBean();
		result.setName("getWashTimes");
		result.getData().put("getWashTimes", fourCardDao.getWashTimes(Id));
		return result;
	}
	
	public void saveCardJob(){
		// TODO saveBookJob 未完成
	}

	@Override
	@Transactional
	public void saveFirstJob() {
		List<Map<String, Object>> all=fourCardDao.getFirstPay();
		fourCardDao.saveFirstJob(all);
	}
	
	@Override
	@Transactional
	public void saveAllPayMoneyAndTimes(){
		List<Map<String,Object>> all=fourCardDao.getSumMoneyAndPayTimes();
		double sum=-1;
		int byn=-1;
		String moreThanS="";
		double mc=1,qmc=0;
		int endYear=0;
		for (int i = 0; i < all.size(); i++) {
			
			if(byn!=MapUtils.getIntValue(all.get(i),"byn")){
				byn=MapUtils.getIntValue(all.get(i),"byn");
				
				qmc=MapUtils.getIntValue(all.get(i),"r")-1;
				sum=-1;
				endYear=stuService.getStuCByEndYear(byn);
			}
			if(sum!=MapUtils.getDoubleValue(all.get(i),"AVG_M")){
				sum=MapUtils.getDoubleValue(all.get(i),"AVG_M");
				mc=MapUtils.getIntValue(all.get(i),"r")-qmc;
				double moreThan=MathUtils.get2Point((mc-1)/endYear*100);
				moreThanS=moreThan+"%";
				
			}
			//存放计算好的值
			all.get(i).put("more_than", moreThanS);
		}
		
		fourCardDao.savePayTimesAndMoneyLog(all);
		
	}
	
	@Override
	@Transactional
	public void saveWashJob(){
		
		logger.info("开始保存洗浴信息");
	
		
		
		
		List<Map<String, Object>> all=fourCardDao.getWashTimes();	
		List<Map<String, Object>> ac=fourCardDao.getPeopleNumByBynAndSex();
		//计算超过了多少百分比学生
		int sum=-1,byn=-1,bynCon=-1;
		int sex_code=-1;
		String moreThanS="";
		double mc=1,qmc=0;
		int flag=0;
		List<Map<String,Object>> avg_t_l;
		String avg_t="";
		for (int i = 0; i < all.size(); i++) {
			if(sex_code!=MapUtils.getIntValue(all.get(i),"sex_code")){
				
				sex_code=MapUtils.getIntValue(all.get(i),"sex_code");
				if(flag!=0){
					byn=MapUtils.getIntValue(all.get(i),"byn");
					bynCon++;
					qmc=MapUtils.getIntValue(all.get(i),"r")-1;
					sum=-1;
				}
				
				
				
				avg_t_l=fourCardDao.getAvgWashTimes(all.get(i).get("sex_code").toString(),all.get(i).get("byn").toString());
				avg_t=avg_t_l.get(0).get("avg_t").toString();
				
				
				
				
				flag++;
				
				
			}
			if(byn!=MapUtils.getIntValue(all.get(i),"byn")){
				byn=MapUtils.getIntValue(all.get(i),"byn");
				bynCon++;
				qmc=MapUtils.getIntValue(all.get(i),"r")-1;
				sum=-1;
				
				avg_t_l=fourCardDao.getAvgWashTimes(all.get(i).get("sex_code").toString(),all.get(i).get("byn").toString());
				avg_t=avg_t_l.get(0).get("avg_t").toString();
				
				
			}
			if(sum!=MapUtils.getIntValue(all.get(i),"times")){
				sum=MapUtils.getIntValue(all.get(i),"times");
				mc=MapUtils.getIntValue(all.get(i),"r")-qmc;
				double moreThan=MathUtils.get2Point((mc-1)/MapUtils.getIntValue(ac.get(bynCon),"p_num")*100);
				moreThanS=moreThan+"%";
				
			}
			//存放计算好的值
			
			all.get(i).put("avg_t", avg_t);
			all.get(i).put("more_than", moreThanS);
		}
		
		fourCardDao.saveWashLog(all);
	
		
		
	}
	
	
	@Override
	public void saveMealsCount() {
		logger.info("开始学生三餐消费次数及超越比例的计算");
		Map<String, List<Map<String, Object>> > result = fourCardDao.getMealsCount();
		Iterator<String> it = result.keySet().iterator();
		Map<String,Map<String,Object>> map = new HashMap<String,Map<String,Object>>();
		
		while(it.hasNext()){
			String key = it.next();
			List<Map<String, Object>> meal = result.get(key);
			
			int sum=-1,byn=-1,stuCount=1;
			String moreThanS="",sex="-1";
			double mc=1,qmc=0;
			for (int i = 0; i < meal.size(); i++) {
				Map<String,Object> temp = meal.get(i);
				int endYear = MapUtils.getIntValue(temp,"BYXN"),
					  iSum = MapUtils.getIntValue(temp,"COUNT_ZC");
				String sexCode = temp.get("SEX_CODE").toString();
				if(key.equals("lunch")){
					iSum = MapUtils.getIntValue(temp,"COUNT_MIDDLE");
				}else if(key.equals("dinner")){
					iSum = MapUtils.getIntValue(temp,"COUNT_WC");
				}
				
				if(byn!=endYear){
					byn=endYear;
					qmc=MapUtils.getIntValue(temp,"RN")-1;
					if(sex.equals(sexCode))
						stuCount = stuService.getStuCByEndYearAndSexcode(byn, sex);
				}
				if(!sex.equals(sexCode)){
					qmc = MapUtils.getIntValue(temp,"RN")-1;
					sex = sexCode;
					stuCount = stuService.getStuCByEndYearAndSexcode(byn, sex);
				}
				if(sum!=iSum){
					sum=iSum;
					mc=MapUtils.getIntValue(temp,"RN")-qmc;
					double moreThan=MathUtils.get2Point((mc-1)/stuCount*100);
					moreThanS=moreThan+"%";
				}
				String stuNo = temp.get("STU_NO").toString();
				//存放计算好的值
				if(key.equals("breakfast")){
					temp.put("BREAKFAST_MORE_THAN", moreThanS);
					if(map.containsKey(stuNo)){
						map.get(stuNo).put("BREAKFAST_MORE_THAN", moreThanS);
					}else{
						Map<String,Object> mealsMap = new HashMap<String,Object>();
						mealsMap.put("BREAKFAST_MORE_THAN", moreThanS);
						map.put(stuNo, mealsMap);
					}
					
				}else if(key.equals("lunch")){
					temp.put("LUNCH_MORE_THAN", moreThanS);
					if(map.containsKey(stuNo)){
						map.get(stuNo).put("LUNCH_MORE_THAN", moreThanS);
					}else{
						Map<String,Object> mealsMap = new HashMap<String,Object>();
						mealsMap.put("LUNCH_MORE_THAN", moreThanS);
						map.put(stuNo, mealsMap);
					}
				}else if(key.equals("dinner")){
					temp.put("DINNER_MORE_THAN", moreThanS);
					if(map.containsKey(stuNo)){
						map.get(stuNo).put("DINNER_MORE_THAN", moreThanS);
					}else{
						Map<String,Object> mealsMap = new HashMap<String,Object>();
						mealsMap.put("DINNER_MORE_THAN", moreThanS);
						map.put(stuNo, mealsMap);
					}
				}
			}
			
		}
		List<Map<String, Object>>  tempList1  = result.get("breakfast");
		for(int i =0 ;i<tempList1.size();i++){
			tempList1.get(i).putAll(map.get(tempList1.get(i).get("STU_NO").toString()));
		}
		fourCardDao.saveMealsCount(tempList1);
		fourCardDao.dropTempMealsCount();
		logger.info("结束学生三餐消费次数及超越比例的计算");
	}
	
	
	@Override
	public void saveMealsAvg() {
		fourCardDao.saveMealsAvg(fourCardDao.getMealsAvg());
		fourCardDao.dropTempMealsAvg();
	}
	
	@Override
	@Transactional
	public void savePayCountJob() {
		Page page=fourCardDao.getPayCount(1,50000,null,0);
		fourCardDao.savePayCountLog(page.getResultList(),true);
		for (int i = 2; i <= page.getTotalPages(); i++) {
			if(i==page.getTotalPages()){
				fourCardDao.savePayCountLog(fourCardDao.getPayCount(i,50000,page.getTotalRows(),-1).getResultList(),false);
			}else{
				fourCardDao.savePayCountLog(fourCardDao.getPayCount(i,50000,page.getTotalRows(),1).getResultList(),false);
			}
			
		}
	}

	@Override
	public void saveLikeEatJob() {
		Page page=fourCardDao.getLikeEat(1,50000,null,0);
		fourCardDao.saveLikeEatLog(page.getResultList(),true);
		for (int i = 2; i <= page.getTotalPages(); i++) {
			if(i==page.getTotalPages()){
				fourCardDao.saveLikeEatLog(fourCardDao.getLikeEat(i,50000,page.getTotalRows(),-1).getResultList(),false);
			}else{
				fourCardDao.saveLikeEatLog(fourCardDao.getLikeEat(i,50000,page.getTotalRows(),1).getResultList(),false);
			}
			
		}
	}

	@Override
	@Transactional
	public void saveLikeShopJob() {
		Page page=fourCardDao.getLikeShop(1,50000,null,0);
		fourCardDao.saveLikeShopLog(page.getResultList(),true);
		for (int i = 2; i <= page.getTotalPages(); i++) {
			if(i==page.getTotalPages()){
				fourCardDao.saveLikeShopLog(fourCardDao.getLikeShop(i,50000,page.getTotalRows(),-1).getResultList(),false);
			}else{
				fourCardDao.saveLikeShopLog(fourCardDao.getLikeShop(i,50000,page.getTotalRows(),1).getResultList(),false);
			}
			
		}
	}

	@Override
	@Transactional
	public void saveLikeWashJob() {
		Page page=fourCardDao.getLikeWash(1,50000,null,0);
		fourCardDao.saveLikeWashLog(page.getResultList(),true);
		for (int i = 2; i <= page.getTotalPages(); i++) {
			if(i==page.getTotalPages()){
				fourCardDao.saveLikeWashLog(fourCardDao.getLikeWash(i,50000,page.getTotalRows(),-1).getResultList(),false);
			}else{
				fourCardDao.saveLikeWashLog(fourCardDao.getLikeWash(i,50000,page.getTotalRows(),1).getResultList(),false);
			}
			
		}
	}

	@Override
	@Transactional
	public void saveRecCountJob() {
		Page page=fourCardDao.getRecCount(1,50000,null,0);
		fourCardDao.saveRecCountLog(page.getResultList(),true);
		for (int i = 2; i <= page.getTotalPages(); i++) {
			if(i==page.getTotalPages()){
				fourCardDao.saveRecCountLog(fourCardDao.getRecCount(i,50000,page.getTotalRows(),-1).getResultList(),false);
			}else{
				fourCardDao.saveRecCountLog(fourCardDao.getRecCount(i,50000,page.getTotalRows(),1).getResultList(),false);
			}
		}
	}

	@Override
	@Transactional
	public void saveAllRecCountJob() {
		fourCardDao.saveAllRecCountLog(fourCardDao.getAllRecCount());
	}

	@Override
	public ResultBean getPayCount(String id) {
		ResultBean rb=new ResultBean();
		rb.setName("PayCount");
		rb.getData().put("PayCount", fourCardDao.getPayCountLog(id));
		rb.getData().put("LikeEat", fourCardDao.getLikeEatLog(id, 3));
		rb.getData().put("LikeShop", fourCardDao.getLikeShopLog(id, 3));
		rb.getData().put("LikeWash", fourCardDao.getLikeWashLog(id, 3));
		return rb;
	}

	@Override
	public ResultBean getRecCount(String id) {
		ResultBean rb=new ResultBean();
		rb.setName("RecCount");
		rb.getData().put("RecCount", fourCardDao.getRecCountLog(id, 3));
		rb.getData().put("AllRecCount", fourCardDao.getAllRecCountLog(stuService.getEndYearByStuId(id)+""));
		return rb;
	}
	@Override
	public ResultBean getCompareMealsAvg(String id) {
		ResultBean rb=new ResultBean();
		rb.setName("Meals");
		rb.getData().put("MealCount", fourCardDao.getMealsCountLog(id));
		rb.getData().put("MealAvg", fourCardDao.getMealsAvgLog(id));
		return rb;
	}

	@Override
	public ResultBean getCardHabit(String id) {
		ResultBean rb=new ResultBean();
		rb.setName("MealHabit");
		List<Map<String,Object>> habits = fourCardDao.getMealsCountLog(id);
		if(habits.size()==0) {
			rb.getData().put("MealHabit", new HashMap<String,Object>());
		} else{
			rb.getData().put("MealHabit", habits.get(0));
		}
		
		List<Map<String,Object>> list = fourCardDao.getMealsAvgLog(id);
		if(list.size()==0) {
			rb.getData().put("MealsAvgStu", new HashMap<String,Object>());
			rb.getData().put("MealsAvgEndyearSex", new HashMap<String,Object>());
			return rb;	
		}
		String endYear = list.get(0).get("BYXN").toString();
		String sexCode = list.get(0).get("SEX_CODE").toString();
		rb.getData().put("MealsAvgStu", list.get(0));
		rb.getData().put("MealsAvgEndyearSex", fourCardDao.getMealsAvgLogByYearSex(endYear, sexCode).get(0));
		return rb;
	}

	@Override
	public Page getCardDetailLog(String id,Page page, List<Condition> conditions) {
		String tj=" and stu_id='"+id+"' ";
		for (int i = 0; i < conditions.size(); i++) {
			Condition con=conditions.get(i);
			tj+=(" and "+con.getQueryCode()+" like '"+con.getId()+"%' ");
		}
		return fourCardDao.getCardDetailLog(page.getCurrentPage(), page.getNumPerPage(), tj);
	}

	@Override
	public List<Map<String, Object>> getCardDetailGroupByDeal(String id) {
		return fourCardDao.getCardDetailGroupByDeal(id);
	}

	@Override
	public List<Map<String, Object>> getCardDetailGroupByTime(String id) {
		return fourCardDao.getCardDetailGroupByTime(id);
	}
}
