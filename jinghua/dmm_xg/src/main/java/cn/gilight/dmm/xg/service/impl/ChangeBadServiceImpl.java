package cn.gilight.dmm.xg.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.xg.dao.ChangeDao;
import cn.gilight.dmm.xg.service.ChangeBadService;
import cn.gilight.dmm.xg.service.ChangeService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.product.EduUtils;

/**
 * 学籍不良异动
 * 
 * @author xuebl
 * @date 2016年5月10日 下午5:32:26
 */
@Service("changeBadService")
public class ChangeBadServiceImpl implements ChangeBadService {

	@Resource
	private ChangeDao changeDao;
	@Resource
	private ChangeService changeService;
	@Resource
	private BusinessService businessService;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BaseDao baseDao;
	/**
	 * 学籍不良异动 shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Xg+"changeBad";
	/**
	 * 获取学籍不良异动数据权限
	 * @return List<String>
	 */
	private List<String> getDeptDataList(){
		return getDeptDataList(null);
	}
	/**
	 * 获取学籍不良异动数据权限
	 * @return List<String>
	 */
	private List<String> getDeptDataList(String id){
		return businessService.getDeptDataList(ShiroTag, id);
	}

	@Override
	public Map<String, Object> getChangeBadList(){
		// 获取有数据的不良异动类型
		List<Map<String, Object>> list = changeDao.queryChangeBzdmList(null, null, StringUtils.join(Constant.Change_Bad_Code, ","));
		// return
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", list);
		return map;
	}

	@Override
	public Map<String, Object> getStuChangeAbstract(String change_bad_codes, List<AdvancedParam> advancedParamList){
		return changeService.getStuChangeAbstract(getDeptDataList(), formatChangeCode(change_bad_codes), advancedParamList);
	}
	
	@Override
	public Map<String, Object> getStuChange(String change_bad_codes, List<AdvancedParam> advancedParamList){
		return changeService.getStuChange(getDeptDataList(), formatChangeCode(change_bad_codes), advancedParamList);
	}

	@Override
	public Map<String, Object> getDeptStuChange(String change_bad_codes, List<AdvancedParam> advancedParamList){
		return changeService.getDeptStuChange(getDeptDataList(), formatChangeCode(change_bad_codes), advancedParamList);
	}

	@Override
	public Map<String, Object> getStuChangeMonth(String change_bad_codes, List<AdvancedParam> advancedParamList){
		return changeService.getStuChangeMonth(getDeptDataList(), formatChangeCode(change_bad_codes), advancedParamList);
	}

	@Override
	public Map<String, Object> getStuChangeYear(String change_bad_codes, List<AdvancedParam> advancedParamList){
		return changeService.getStuChangeYear(getDeptDataList(), formatChangeCode(change_bad_codes), advancedParamList);
	}
	
	@Override
	public Map<String, Object> getStuChangeHistory(String change_bad_codes, List<AdvancedParam> advancedParamList){
		return changeService.getStuChangeHistory(getDeptDataList(), formatChangeCode(change_bad_codes), advancedParamList);
	}
	
	private String formatChangeCode(String change_bad_codes){
		return change_bad_codes==null ? StringUtils.join(Constant.Change_Bad_Code, ",") : change_bad_codes;
	}
	@Override
	public Map<String, Object> getStuChangeBadDetail(List<AdvancedParam> advancedParamList, Page page,
			Map<String, Object> keyValue, List<String> fields) {
		String change_codes=(String)keyValue.get("changeCode");
		change_codes=formatChangeCode(change_codes);
		List<String> deptList = getDeptDataList(); // 权限
		String getStuChangeDetailSql=changeDao.getStuChangeDetailSql(advancedParamList, keyValue, fields,change_codes,deptList);
		Map<String,Object> map =baseDao.createPageQueryInLowerKey(getStuChangeDetailSql, page);
		return map;//下钻学生信息
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> getStuDetailList(List<AdvancedParam> advancedParamList, Page page,
			Map<String, Object> keyValue, List<String> fields){
		Map<String, Object> map=getStuChangeBadDetail(advancedParamList, page, keyValue, fields);
		return (List<Map<String, Object>>) map.get("rows");
	}
	@Override
	public Map<String, Object> getChangeAgain(String change_bad_codes, List<AdvancedParam> advancedParamList){
		List<String> deptList = getDeptDataList(); // 权限
		int thisYear = EduUtils.getSchoolYear4();
		String start_date = null, end_date = null; // 查询时间段
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		// data
		List<Map<String, Object>> reList = new ArrayList<>();
		for(int i=Constant.Year_His_Len; i>0; i--){
			int schoolYear = thisYear+1-i; //哪些学年
			String[] schoolYearQuant = EduUtils.getTimeQuantum(schoolYear);
			start_date = schoolYearQuant[0];
			end_date   = schoolYearQuant[1];
			Map<String, Object> map = new HashMap<>();
			int stuChangeCount = changeDao.queryPunishAgainCount(schoolYear, deptList, stuAdvancedList, start_date, end_date, formatChangeCode(change_bad_codes));
			List<Map<String, Object>> list = changeDao.queryChangeAbstract(schoolYear, deptList, stuAdvancedList, start_date, end_date, null);
			int count_all = 0;
			for(Map<String, Object> m : list){
				int count = MapUtils.getInteger(m, "value");
				count_all += count;
			}
			map.put("name", schoolYear+"-"+(schoolYear+1));
			map.put("value", MathUtils.getPercentNum(stuChangeCount, count_all));
			reList.add(map);
		}
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", reList);
		return map;
	}
	
}
