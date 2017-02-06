package cn.gilight.business.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.gilight.business.model.SelfDefinedYear;
import cn.gilight.business.service.SelfDefinedCodeService;
import cn.gilight.framework.uitl.TypeConvert;
import cn.gilight.framework.uitl.common.DateUtils;

/**   
* @Description: 自定义的编码
* @author Sunwg  
* @date 2016年6月8日 下午4:11:52   
*/
@Service
public class SelfDefinedCodeServiceImpl implements SelfDefinedCodeService {
	
	@Override
	public List<SelfDefinedYear> getSelfDefinedYearCode(){
		String nowYear = DateUtils.getNowYear();
		int curyear = TypeConvert.toInteger(nowYear);
		SelfDefinedYear curYear = new SelfDefinedYear("今年", nowYear);
		SelfDefinedYear lastYear = new SelfDefinedYear("去年",TypeConvert.toString(curyear - 1));
		SelfDefinedYear lastThree = new SelfDefinedYear("近三年",TypeConvert.toString(curyear - 2),nowYear);
		SelfDefinedYear lastFive = new SelfDefinedYear("近五年", TypeConvert.toString(curyear -4), nowYear);
		SelfDefinedYear lastTen  = new SelfDefinedYear("近十年", TypeConvert.toString(curyear -9), nowYear);
		List<SelfDefinedYear> result = new ArrayList<SelfDefinedYear>();
		result.add(curYear);
		result.add(lastYear);
		result.add(lastThree);
		result.add(lastFive);
		result.add(lastTen);
		return result;
	}

	@Override
	public List<Map<String,Object>> getYears() {
		List<Map<String,Object>> years = new ArrayList<Map<String,Object>>();
		int year = TypeConvert.toInteger(DateUtils.getNowYear());
		for(int i=year;i>=year-10;i--){
			Map<String,Object> m = new HashMap<String,Object>();
			m.put("value", i);
			m.put("mc", TypeConvert.toString(i)+"年");
			years.add(m);
		}
		return years;
	}
}