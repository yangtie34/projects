package com.jhnu.product.wechat.parent.card.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.common.card.service.CardService;
import com.jhnu.product.common.stu.entity.Student;
import com.jhnu.product.common.stu.service.StuService;
import com.jhnu.product.wechat.parent.card.dao.WechatCardDao;
import com.jhnu.product.wechat.parent.card.entity.CardAnalyzeData;
import com.jhnu.product.wechat.parent.card.entity.TlWechatConsumAnalyze;
import com.jhnu.product.wechat.parent.card.service.WechatCardService;
import com.jhnu.util.common.DateUtils;
import com.jhnu.util.common.MapUtils;
import com.jhnu.util.common.MathUtils;
import com.jhnu.util.product.EduUtils;

@Service("wechatCardService")
public class WechatCardServiceImpl implements WechatCardService {
	// TODO 该类中存在硬编码问题，后续迭代修改。
	private enum TIMETYPE{bxq,by,bz,rxyl};
	
	
	@Autowired
	private WechatCardDao wechatCardDao;
	@Autowired
	private StuService stuService;
	@Autowired
	private CardService cardService;
	
	@Override
	public CardAnalyzeData getAnalyzeData(String stuId,String timeType) {
		if("default".equals(timeType)) timeType=TIMETYPE.rxyl.toString();
		Student stu = stuService.getStudentInfo(stuId);
		List<Map<String,Object>> rs1 = wechatCardDao.getCustomAnalyzeResult(stuId, timeType);
		List<Map<String,Object>> rs2 = wechatCardDao.getCustomAnalyzeResult(stuId, TIMETYPE.rxyl.toString());
		
		CardAnalyzeData cad = new CardAnalyzeData();
		
		for(Map<String,Object> map : rs2){
			String analyze_paydeal_id = map.get("ANALYZE_PAYDEAL_ID").toString();
			if("05".equals(analyze_paydeal_id)){
				double morethan =MapUtils.getDoubleValue(map, "MORE_THAN");
				cad.setAvg_morethan(morethan);
			}
			
		}
		
		cad.setStu(stu);
		if(rs1.size()==0){ return cad; }
		Map<String,Double> type_avg_my = new TreeMap<String,Double>();
		for(Map<String,Object> map : rs1){
			String analyze_paydeal_id = map.get("ANALYZE_PAYDEAL_ID").toString();
			double val1 = MapUtils.getDoubleValue(map, "XFLX_AVG");
			
			if("05".equals(analyze_paydeal_id)){// 总计消费
				cad.setAvg_my(val1);
				
			}
			type_avg_my.put(analyze_paydeal_id, val1);
		}
		cad.setType_avg_my(type_avg_my);
		double blance = cardService.getCardBlanceById(stuId);
		cad.setBalance(blance);
		cad.setTotal(MapUtils.getDoubleValue(rs1.get(0), "SUM_"));
		
		
		double avg_all = wechatCardDao.getCustomAvg4AllStu("05", TIMETYPE.rxyl.toString(),stu.getSex());
		cad.setAvg_all(avg_all);
		
		// TODO 获取全校学生的超市消费占总消费的普遍比例。
		cad.setVal1(0.0d);
		List<Map<String, Object>> list05 = wechatCardDao.getCustomAnalyzeResult(stuId, "05", timeType);
		List<Map<String, Object>> list03 = wechatCardDao.getCustomAnalyzeResult(stuId, "03", timeType);
		double val1 = Double.parseDouble(list05.size()==0?"0.00":list05.get(0).get("XFLX_AVG").toString());
		double val2 = Double.parseDouble(list03.size()==0?"0.00":list03.get(0).get("XFLX_AVG").toString());
		
		cad.setVal2(val1==0.00d?0.00d:MathUtils.get2Point(val2/val1));
		
		return cad;
	}
	/**
	 * 依据不同的日期类型获取对应的日期时间段；
	 * @param timetype
	 * @return
	 */
	private String[] getDateSectionByTimetype(String timetype){
		String[] startEnd = new String[2];
		startEnd[1] = DateUtils.getNowDate();
		if(TIMETYPE.rxyl.toString().equals(timetype)){
			startEnd[0] = "1990-01-01";
		}
		if(TIMETYPE.by.toString().equals(timetype)){
			Date now = new Date();
			startEnd[1] = DateUtils.getMonthStart(now);
			startEnd[0] = DateUtils.getMonthEnd(now);
		}
		
		if(TIMETYPE.bz.toString().equals(timetype)){
			startEnd[0] = DateUtils.getWeekFirstDay();
		}
		if(TIMETYPE.bxq.toString().equals(timetype)){
			String[] syTc = EduUtils.getSchoolYearTerm(new Date());
			startEnd = EduUtils.getTimeQuantum(syTc[0],syTc[1]);
		}
		return startEnd;
	}
	
	
	@Override
	public void saveWachatCardAna2Log(List<TlWechatConsumAnalyze> cads) {
		wechatCardDao.saveWachatCardAna2Log(cads);
	}
	
	@Override
	public Map<String,List<TlWechatConsumAnalyze>> getWachatCardAnaData(String groupCode){
		Map<String,List<TlWechatConsumAnalyze>> result = new HashMap<String,List<TlWechatConsumAnalyze>>();
		TIMETYPE[] tps = TIMETYPE.values();
		List<String> carDealIds = cardService.getCardDealGroup(groupCode);
		for(TIMETYPE tp : tps){
			String[] dateSection = getDateSectionByTimetype(tp.toString());
			List<TlWechatConsumAnalyze> aas = wechatCardDao.getWachatCardAnaData(tp.toString(), dateSection,groupCode, carDealIds);
			result.put(tp.toString(),aas);
		}
		return result;
	}
	
	@Override
	public List<TlWechatConsumAnalyze> getTlWechatConsumAnalyzes(String timeType ,String groupCode) {
		return wechatCardDao.getTlWechatConsumAnalyzes(timeType ,groupCode);
	}
	
	@Override
	public void updateTlWechatConsumAnalyzes(List<TlWechatConsumAnalyze> list) {
		wechatCardDao.updateTlWechatConsumAnalyzes(list);;
	}
}
