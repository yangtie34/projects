package cn.gilight.business.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.jhnu.syspermiss.GetCachePermiss;
import com.jhnu.syspermiss.school.entity.Dept;
import com.jhnu.syspermiss.school.entity.DeptTeach;
import com.jhnu.syspermiss.util.UserUtil;

import cn.gilight.business.dao.BusinessDao;
import cn.gilight.business.service.BusinessService;
import cn.gilight.business.util.Constant;
import cn.gilight.business.model.TCode;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.product.EduUtils;

/**
 * 业务service
 * 
 * @author xuebl
 * @date 2016年5月3日 下午12:29:58
 */
@Service("businessService")
public class BusinessServiceImpl implements BusinessService {

	@Resource
	private BusinessDao businessDao;
	
	@Resource BaseDao baseDao;
	
	/**
	 * 学校数据 'schoolId, schoolName, 2016-05-01'
	 */
	private static String SchoolStr = "";
	
	
	@Override
	public String getSchoolId(){
		if(isNeedInitSchoolData()) initSchoolData();
		return SchoolStr.split(",")[0];
	}

	@Override
	public String getSchoolName(){
		if(isNeedInitSchoolData()) initSchoolData();
		return SchoolStr.split(",")[1];
	}
	
	/**
	 * 是否需要初始化学校数据
	 * @return boolean
	 */
	private boolean isNeedInitSchoolData(){
		boolean isNeed = false;
		String[] ary = SchoolStr.split(",");
		String nowDate = DateUtils.getNowDate();
		if(ary.length < 2 || !nowDate.equals(ary[1])){
			isNeed = true;
		}
		return isNeed;
	}
	/**
	 * 初始化学校数据
	 */
	private void initSchoolData(){
		Map<String, Object> map = businessDao.querySchoolData();
		String schoolId   = MapUtils.getString(map, "id"),
			   schoolName = MapUtils.getString(map, "name");
		if(schoolId != null)
			SchoolStr = schoolId +","+ schoolName +","+ DateUtils.getNowDate();
	}
	
	
	/**
	 * 标准代码
	 */
	@Override
	public List<TCode> queryBzdmList(String codeType){
		return queryBzdmList(codeType, null);
	}
	
	@Override
	public List<TCode> queryBzdmList(String codeType, String codes){
		return businessDao.queryBzdmList(codeType, codes);
	}
	
	@Override
	public List<Map<String, Object>> queryBzdmListMap(String codeType){
		List<Map<String, Object>> reList = new ArrayList<>();
		List<TCode> list =  queryBzdmList(codeType);
		for(TCode t : list){
			Map<String, Object> map = new HashMap<>();
			map.put("id", t.getCode_());
			map.put("mc", t.getName_());
			reList.add(map);
		}
		return reList;
	}

	@Override
	public List<String> queryBzdmNameList(String codeType, String codes){
		List<String> li = new ArrayList<>();
		List<TCode> list = queryBzdmList(codeType, codes);
		for(TCode t : list)
			li.add(t.getName_());
		return li;
	}
	
	@Override
	public List<String> queryBzdmCodeList(String codeType){
		List<String> li = new ArrayList<>();
		List<TCode> list = queryBzdmList(codeType);
		for(TCode t : list)
			li.add(t.getCode_());
		return li;
	}
	
	@Override
	public List<Map<String, Object>> queryBzdmStuEducationList(){
		List<Map<String, Object>> list = new ArrayList<>();
		String[][] array = Constant.Stu_Education_Group;
		for(String[] ary : array){
			Map<String, Object> map = new HashMap<>();
			map.put("id", ary[0]);
			map.put("mc", ary[1]);
			list.add(map);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> queryBzdmSchoolYear(){
		int len = 5;
		return packageBzdmSchoolYear(EduUtils.getSchoolYear4()+1-len);
	}

	@Override
	public List<Map<String, Object>> queryBzdmSchoolYear(String table, String column_schoolYear){
		Integer year = businessDao.queryMinSchoolYear(table, column_schoolYear);
		return packageBzdmSchoolYear(year);
	}

	/**
	 * 标准代码学年（最小年到现在的学年）
	 * @param min_year 最小年
	 */
	private List<Map<String, Object>> packageBzdmSchoolYear(Integer min_year) {
		List<Map<String, Object>> list = new ArrayList<>();
		int this_year = EduUtils.getSchoolYear4();
		min_year = min_year!=null ? min_year : this_year;
		String year2 = null;
		for( ; this_year>=min_year; this_year--){
			Map<String, Object> map = new HashMap<>();
			year2 = this_year+"-"+(this_year+1);
			map.put("id", year2);
			map.put("mc", year2+"学年");
			list.add(map);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> queryCodeSubject() {
		return businessDao.queryCodeSubject();
	}
	
	/** 
	* @Description: 根据节点id和页面标识符获取行政组织机构树的数据权限id
	* @param deptId 行政组织机构树节点
	* @param shiroTag 页面标识符
	* @return: String 所拥有的行政组织机构数据权限id拼接的字符串
	*/
	public String getDeptDataPermissIdsQuerySql(String deptId,String shiroTag){
		List<String> datas = GetCachePermiss.getDataServeSqlbyUserIdShrio(UserUtil.getCasLoginName(),shiroTag);
		String resultSql = GetCachePermiss.getDataSqlByDeptAndData("dept", deptId, datas);
		return  resultSql ;
	}
	
	/** 
	* @Description: 获取权限配置的行政组织机构树
	* @param shiroTag 页面标识符
	* @return: JSONObject 行政组织机构树权限
	*/
	@Override
	public JSONObject getDeptDataPermissTree(String shiroTag){
		Dept deptObject = (Dept) GetCachePermiss.getDeptByShiroTag(shiroTag);
		JSONObject permissTree = (JSONObject) JSONObject.toJSON(deptObject);
		insertMcFieldOfDeptTree(permissTree);
		return  permissTree;
	}
	
	@Override
	public JSONObject getTeachDeptDataPermissTree(String shiroTag) {
		DeptTeach deptObject = (DeptTeach) GetCachePermiss.getDeptTeachByShiroTag(shiroTag);
		JSONObject permissTree = (JSONObject) JSONObject.toJSON(deptObject);
		insertMcFieldOfDeptTree(permissTree);
		return  permissTree;
	}
	
	/** 
	* @Description: 根据节点id和页面标识符获取行政组织机构树的数据权限id
	* @param deptId 行政组织机构树节点
	* @param shiroTag 页面标识符
	* @return: String 所拥有的行政组织机构数据权限id拼接的字符串
	*/
	@Override
	public String getTeachDeptDataPermissIdsQuerySql(String deptId,String shiroTag){
		List<String> datas = GetCachePermiss.getDataServeSqlbyUserIdShrio(UserUtil.getCasLoginName(),shiroTag);
		String resultSql = GetCachePermiss.getDataSqlByDeptAndData("deptTeach", deptId, datas);
		return  resultSql ;
	}
	
	/*
	* @Description: 将map数据中的name_复制一份存到mc，为了前台的树组件的显示
	* @param source 数据源（JSONObject）
	*/
	private void insertMcFieldOfDeptTree(JSONObject source){
		if(source!=null){
			source.put("mc", source.get("name_"));
			@SuppressWarnings("unchecked")
			List<JSONObject> children = (List<JSONObject>) source.get("children");
			for (int i = 0; i < children.size(); i++) {
				insertMcFieldOfDeptTree(children.get(i));
			}
		}
	}
	
	public String queryChangePasswdSystemUrl(){
		String sql = "SELECT T.URL_,T.NAME_,T.LEVEL_ FROM T_SYS_RESOURCES  T WHERE T.SHIRO_TAG = 'ht'";
		List<Map<String, Object>> list = baseDao.queryListInLowerKey(sql);
		if (list.size() > 0) {
			Map<String, Object> sysObj = list.get(0);
			return MapUtils.getString(sysObj,"url_");
		}
		return "";
	}

}
