package cn.gilight.dmm.xg.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.xg.service.ScholarshipService;
import cn.gilight.dmm.xg.service.SubsidyService;
import cn.gilight.framework.page.Page;

/**
 * 助学金
 * 
 * @author xuebl
 * @date 2016年5月12日 下午5:17:45
 */
@Service("subsidyService")
public class SubsidyServiceImpl implements SubsidyService {

	@Resource
	private BusinessService businessService;
	@Resource
	private ScholarshipService scholarshipService;
	
	
	/**
	 * shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Xg+"subsidy";
	/**
	 * 助学金表
	 */
	public static Constant.JCZD_Table TABLE = Constant.JCZD_Table.SUBSIDY;
	
	/**
	 * 获取数据权限
	 */
	private List<String> getDeptDataList(){
		return businessService.getDeptDataList(ShiroTag, null);
	}

	@Override
	public Map<String, Object> getBzdm(){
		Map<String, Object> map = DevUtils.MAP();
		map.put("xn", businessService.queryBzdmSchoolYear());
		map.put(AdvancedUtil.Stu_EDU_ID, businessService.queryBzdmStuEducationList(getDeptDataList()));
		return map;
	}
	
	@Override
	public Map<String, Object> getAbstract(String schoolYear, String edu, List<AdvancedParam> advancedParamList){
		return scholarshipService.getAbstract(getDeptDataList(), advancedParamList, schoolYear, edu, TABLE);
	}
	
	@Override
	public Map<String, Object> queryTypeList(String schoolYear, String edu, List<AdvancedParam> advancedParamList){
		return scholarshipService.queryTypeList(getDeptDataList(), advancedParamList, schoolYear, edu, TABLE);
	}
	
	@Override
	public Map<String, Object> queryDeptDataList(String schoolYear, String edu, List<AdvancedParam> advancedParamList){
		return scholarshipService.queryDeptDataList(getDeptDataList(), advancedParamList, schoolYear, edu, TABLE);
	}
	
	@Override
	public Map<String, Object> getBehavior(String schoolYear, String edu, List<AdvancedParam> advancedParamList){
		return scholarshipService.getBehavior(getDeptDataList(), advancedParamList, schoolYear, edu, TABLE);
	}
	
	@Override
	public Map<String, Object> getCoverageGrade(String schoolYear, String edu, List<AdvancedParam> advancedParamList){
		return scholarshipService.getCoverageGrade(getDeptDataList(), advancedParamList, schoolYear, edu, TABLE);
	}
	
	@Override
	public Map<String, Object> getCoverageDept(String schoolYear, String edu, List<AdvancedParam> advancedParamList){
		return scholarshipService.getCoverageDept(getDeptDataList(), advancedParamList, schoolYear, edu, TABLE);
	}
	
	@Override
	public Map<String, Object> getHistory(String schoolYear, String edu, List<AdvancedParam> advancedParamList){
		return scholarshipService.getHistory(getDeptDataList(), advancedParamList, schoolYear, edu, TABLE);
	}

	@Override
	public Map<String, Object> getSubsidyStuDetail(List<AdvancedParam> advancedParamList, Page page,
			Map<String, Object> keyValue, List<String> fields) {
		return scholarshipService.getStuScholarshipDetail(advancedParamList, page, keyValue, fields,TABLE);
	}
	
	@Override
	public List<Map<String,Object>> getStuDetailList(List<AdvancedParam> advancedParamList,Page page,
			 Map<String, Object> keyValue, List<String> fields){
		return scholarshipService.getStuDetailList(advancedParamList,page, keyValue, fields,TABLE);
	}
}