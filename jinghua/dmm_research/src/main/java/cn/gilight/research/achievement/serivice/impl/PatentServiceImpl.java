package cn.gilight.research.achievement.serivice.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import cn.gilight.business.service.BusinessService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.TypeConvert;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.research.achievement.serivice.PatentService;
 
@Service("patentService")
public class PatentServiceImpl implements PatentService {
	
	@Resource
	private BaseDao baseDao;
	
	@Resource
	private BusinessService businessService;
	
	@Override
	public int queryPatentNums(String xkmlid, String startYear, String endYear,
			String zzjgid,String shiroTag) {
		String sql = "select count(t.id) from t_res_patent t where substr(t.accept_time,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in "
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.inventor_dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")";
		return baseDao.queryForInt(sql);
	}

	
	@Override
	public List<Map<String, Object>> queryPatentType(String xkmlid,
			String startYear, String endYear, String zzjgid,String shiroTag) {
		String sql = "select tc.name_ name ,tc.order_, nvl(tt.nums,0) value from t_code tc left join (select co.code_, co.name_ ,count(t.id) nums from t_res_patent t "
				+ " left join t_code co on co.code_type = 'RES_PATENT_TYPE_CODE' and co.code_ = t.patent_type_code and co.istrue = 1 "
				+ " where substr(t.accept_time,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in "
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.inventor_dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " group by co.code_,co.name_)tt on tt.code_ = tc.code_ where tc.code_type = 'RES_PATENT_TYPE_CODE' and tc.istrue = 1 order by tc.order_";
		return baseDao.queryListInLowerKey(sql);
	}
	

	@Override
	public List<Map<String, Object>> queryPatentState(String xkmlid,
			String startYear, String endYear, String zzjgid,String shiroTag) {
		String sql = "select tc.name_ name,tc.order_,nvl(tt.nums,0) value from t_code tc left join (select co.code_,co.name_ ,count(t.id) nums from t_res_patent t "
				+ " left join t_code co on co.code_type = 'RES_PATENT_STATE_CODE' and co.code_ = t.patent_state_code and co.istrue = 1 "
				+ " where substr(t.accept_time,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in "
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.inventor_dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " group by co.code_,co.name_) tt on tt.code_ = tc.code_ where tc.code_type = 'RES_PATENT_STATE_CODE' and tc.istrue = 1 order by tc.order_";
		return baseDao.queryListInLowerKey(sql);
	}
	
	
	@Override
	public List<Map<String, Object>> queryPatentChange(String xkmlid,
			String startYear, String endYear,String zzjgid, String param,String shiroTag) {
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		String sql = "";
		if(StringUtils.hasText(param) && "type".equals(param)){
			sql = "select substr(t.accept_time,0,4) year_, co.name_ name,count(t.id) value from t_res_patent t "
					+ " left join t_code co on co.code_type = 'RES_PATENT_TYPE_CODE' and co.code_ = t.patent_type_code and co.istrue = 1 "
					+ " where substr(t.accept_time,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in "
					+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
					+ " and t.inventor_dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
					+ " group by substr(t.accept_time,0,4),co.name_ order by year_";
			String typeSql = "select t.name_ from t_code t where t.code_type = 'RES_PATENT_TYPE_CODE' and t.istrue = 1";
			List<Map<String,Object>> typeList = baseDao.queryListInLowerKey(typeSql);
			List<Map<String,Object>> tempList = baseDao.queryListInLowerKey(sql);
			if(typeList != null && typeList.size()>0){
				for(Map<String,Object> sm : typeList){
					Map<String, Object> it = new HashMap<String, Object>();
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					String name = MapUtils.getString(sm, "name_");
					it.put("name", name);
					for (int i = TypeConvert.toInteger(startYear); i <= TypeConvert.toInteger(endYear); i++) {
						Map<String,Object> m = new HashMap<String,Object>();
						int year = i;
						int value=0;
						for(int j=0;j<tempList.size();j++){
							Map<String,Object> item = tempList.get(j);
							if(name.equals(MapUtils.getString(item, "name")) && MapUtils.getIntValue(item, "year_") == year){
								value = MapUtils.getIntValue(item, "value");
							}
						}
						m.put("year", year);
						m.put("value", value);
						list.add(m);
					}
					it.put("list", list);
					result.add(it);
				}
			}
		}else if(StringUtils.hasText(param) && "state".equals(param)){
			sql = "select substr(t.accept_time,0,4) year_, co.name_ name,count(t.id) value from t_res_patent t "
				    + " left join t_code co on co.code_type = 'RES_PATENT_STATE_CODE' and co.code_ = t.patent_state_code and co.istrue = 1"
				    + " where substr(t.accept_time,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in "
				    + " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				    + " and t.inventor_dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				    + " group by substr(t.accept_time,0,4),co.name_ order by year_";
			String stateSql = "select t.name_ from t_code t where t.code_type = 'RES_PATENT_STATE_CODE' and t.istrue = 1";
			List<Map<String,Object>> stateList = baseDao.queryListInLowerKey(stateSql);
			List<Map<String,Object>> tempList = baseDao.queryListInLowerKey(sql);
			if(stateList != null && stateList.size()>0){
				for(Map<String,Object> sm : stateList){
					Map<String, Object> it = new HashMap<String, Object>();
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					String name = MapUtils.getString(sm, "name_");
					it.put("name", name);
					for (int i = TypeConvert.toInteger(startYear); i <= TypeConvert.toInteger(endYear); i++) {
						Map<String,Object> m = new HashMap<String,Object>();
						int year = i;
						int value=0;
						for(int j=0;j<tempList.size();j++){
							Map<String,Object> item = tempList.get(j);
							if(name.equals(MapUtils.getString(item, "name")) && MapUtils.getIntValue(item, "year_") == year){
								value = MapUtils.getIntValue(item, "value");
							}
						}
						m.put("year", year);
						m.put("value", value);
						list.add(m);
					}
					it.put("list", list);
					result.add(it);
				}
			}
		}else if(StringUtils.hasText(param) && "zl".equals(param)){
			sql = "select substr(t.accept_time,0,4) year_,'专利数' name,count(t.id) value from t_res_patent t "
					+ " where substr(t.accept_time,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in "
					+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
					+ " and t.inventor_dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
					+ " group by substr(t.accept_time,0,4) order by year_";
			List<Map<String, Object>> temp = baseDao.queryListInLowerKey(sql);
			if(temp != null && temp.size()>0){
				Map<String, Object> it = new HashMap<String, Object>();
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				String name = MapUtils.getString(temp.get(0), "name");
				it.put("name", name);
				for (int i = TypeConvert.toInteger(startYear); i <= TypeConvert.toInteger(endYear); i++) {
					Map<String,Object> m = new HashMap<String,Object>();
					int year = i;
					int value=0;
					for(int j=0;j<temp.size();j++){
						Map<String,Object> item = temp.get(j);
						if(name.equals(MapUtils.getString(item, "name")) && MapUtils.getIntValue(item, "year_") == year){
							value = MapUtils.getIntValue(item, "value");
						}
					}
					m.put("year", year);
					m.put("value", value);
					list.add(m);
				}
				it.put("list", list);
				result.add(it);
			}
		}
		return result;
	}


	@Override
	public List<Map<String, Object>> queryPatentDept(String xkmlid,
			String startYear, String endYear, String zzjgid, String param,String shiroTag) {
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		String sql = "";
		if(StringUtils.hasText(param) && "type".equals(param)){
			sql = "select  co.name_ dept,tc.name_ name,count(t.id) value from t_res_patent t "
					+ " left join t_code tc on tc.code_type = 'RES_PATENT_TYPE_CODE' and tc.code_ = t.patent_type_code and tc.istrue = 1"
					+ " left join t_code_dept co on co.id = t.inventor_dept_id"
					+ " where substr(t.accept_time,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in "
					+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
					+ " and t.inventor_dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
					+ " group by co.name_,tc.name_ ";
			String typeSql = "select t.name_ from t_code t where t.code_type = 'RES_PATENT_TYPE_CODE' and t.istrue = 1";
			String deptSql = "select  co.name_ dept from t_res_patent t left join t_code_dept co on co.id = t.inventor_dept_id"
					+ " where substr(t.accept_time,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in "
					+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
					+ " and t.inventor_dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
					+ " group by co.name_";
			List<Map<String,Object>> typeList = baseDao.queryListInLowerKey(sql);
			List<Map<String,Object>> tempList = baseDao.queryListInLowerKey(typeSql);
			List<Map<String,Object>> deptList = baseDao.queryListInLowerKey(deptSql);
			if(typeList != null && typeList.size()>0){
				for(Map<String,Object> sm : tempList){
					Map<String, Object> it = new HashMap<String,Object>();
					List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
					String name = MapUtils.getString(sm, "name_");
					it.put("name", name);
					for(Map<String,Object> dm : deptList){
						Map<String,Object> tm = new HashMap<String,Object>();
						String dept = MapUtils.getString(dm, "dept");
						int value = 0;
						for(int i=0;i<typeList.size();i++){
							Map<String,Object> m = typeList.get(i);
							if(name.equals(MapUtils.getString(m, "name")) && dept.equals(MapUtils.getString(m, "dept"))){
								value = MapUtils.getIntValue(m, "value");
							}
						}
						tm.put("dept", dept);
						tm.put("value", value);
						list.add(tm);
					}
					it.put("list", list);
					result.add(it);
				}
			}
		}else if(StringUtils.hasText(param) && "state".equals(param)){
			sql = "select  co.name_ dept,tc.name_ name,count(t.id) value from t_res_patent t "
					+ " left join t_code tc on tc.code_type = 'RES_PATENT_STATE_CODE' and tc.code_ = t.patent_state_code and tc.istrue = 1"
					+ " left join t_code_dept co on co.id = t.inventor_dept_id"
					+ " where substr(t.accept_time,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in "
					+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
					+ " and t.inventor_dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
					+ " group by co.name_,tc.name_ ";
			String stateSql = "select t.name_ from t_code t where t.code_type = 'RES_PATENT_STATE_CODE' and t.istrue = 1";
			String deptSql = "select  co.name_ dept from t_res_patent t left join t_code_dept co on co.id = t.inventor_dept_id"
					+ " where substr(t.accept_time,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in "
					+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
					+ " and t.inventor_dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
					+ " group by co.name_";
			List<Map<String,Object>> stateList = baseDao.queryListInLowerKey(sql);
			List<Map<String,Object>> tempList = baseDao.queryListInLowerKey(stateSql);
			List<Map<String,Object>> deptList = baseDao.queryListInLowerKey(deptSql);
			if(stateList != null && stateList.size()>0){
				for(Map<String,Object> sm : tempList){
					Map<String, Object> it = new HashMap<String,Object>();
					List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
					String name = MapUtils.getString(sm, "name_");
					it.put("name", name);
					for(Map<String,Object> dm : deptList){
						Map<String,Object> tm = new HashMap<String,Object>();
						String dept = MapUtils.getString(dm, "dept");
						int value = 0;
						for(int i=0;i<stateList.size();i++){
							Map<String,Object> m = stateList.get(i);
							if(name.equals(MapUtils.getString(m, "name")) && dept.equals(MapUtils.getString(m, "dept"))){
								value = MapUtils.getIntValue(m, "value");
							}
						}
						tm.put("dept", dept);
						tm.put("value", value);
						list.add(tm);
					}
					it.put("list", list);
					result.add(it);
				}
			}
		}else if(StringUtils.hasText(param) && "zl".equals(param)){
			sql = "select  co.name_ dept,'专利数' name,count(t.id) value from t_res_patent t "
					+ " left join t_code_dept co on co.id = t.inventor_dept_id"
					+ " where substr(t.accept_time,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in "
					+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
					+ " and t.inventor_dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
					+ " group by co.name_ ";
			List<Map<String, Object>> temp = baseDao.queryListInLowerKey(sql);
			if(temp != null && temp.size()>0){
				Map<String,Object> map = new HashMap<String,Object>();
				String name = MapUtils.getString(temp.get(0), "name");
				map.put("name", name);
				List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
				for(int i=0;i<temp.size();i++){
					Map<String,Object> item = temp.get(i);
					Map<String,Object> zl = new HashMap<String,Object>();
					zl.put("dept", MapUtils.getString(item, "dept"));
					zl.put("value", MapUtils.getString(item, "value"));
					list.add(zl);
				}
				map.put("list", list);
				result.add(map);
			}
			
		}
		return result;
	}


	@Override
	public Map<String, Object> queryConDept(String xkmlid, String startYear,String endYear, String zzjgid,String shiroTag) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String totalSql = "select cod.name_ state,count(t.id) value from t_res_patent t "
				+ " left join t_code cod on cod.code_type = 'RES_PATENT_STATE_CODE' and cod.code_ = t.patent_state_code and cod.istrue = 1 "
				+ " left join t_code cd on cd.code_type = 'RES_PATENT_TYPE_CODE' and cd.code_ = t.patent_type_code and cd.istrue = 1"
				+ " left join t_code_dept co on co.id = t.inventor_dept_id"
				+ " where substr(t.accept_time,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in "
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.inventor_dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " and cd.name_ = '发明专利' group by cod.name_";
		List<Map<String,Object>> totalList = baseDao.queryListInLowerKey(totalSql);
		int sq_total = 0;
		int sl_total = 0;
		if(totalList != null && totalList.size()>0){
			for(Map<String,Object> map : totalList){
				if("授权".equals(MapUtils.getString(map, "state"))){
					sq_total = MapUtils.getIntValue(map, "value");
				}
				if("受理".equals(MapUtils.getString(map, "state"))){
					sl_total = MapUtils.getIntValue(map, "value");
				}
			}
		}
		String sql = "select tt.dept,tt.state,tt.value,s.rn from ( select dept,rn from ( select dept, state,value,"
				+ " row_number()over(partition by state order by value desc) rn from ( select  co.name_ dept,cod.name_ state,count(t.id) value from t_res_patent t "
				+ " left join t_code cod on cod.code_type = 'RES_PATENT_STATE_CODE' and cod.code_ = t.patent_state_code and cod.istrue = 1 "
				+ " left join t_code cd on cd.code_type = 'RES_PATENT_TYPE_CODE' and cd.code_ = t.patent_type_code and cd.istrue = 1"
				+ " left join t_code_dept co on co.id = t.inventor_dept_id"
				+ " where substr(t.accept_time,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in "
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.inventor_dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " and cd.name_ = '发明专利'  group by co.name_ ,cod.name_) ) aa  where aa.rn<=2 and aa.state = '授权') s left join ("
				+ " select dept, state,value,row_number()over(partition by state order by value desc) rn from ("
				+ " select  co.name_ dept,cod.name_ state,count(t.id) value from t_res_patent t "
				+ " left join t_code cod on cod.code_type = 'RES_PATENT_STATE_CODE' and cod.code_ = t.patent_state_code and cod.istrue = 1 "
				+ " left join t_code cd on cd.code_type = 'RES_PATENT_TYPE_CODE' and cd.code_ = t.patent_type_code  and cd.istrue = 1 "
				+ " left join t_code_dept co on co.id = t.inventor_dept_id"
				+ " where substr(t.accept_time,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in "
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.inventor_dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"
				+ " and cd.name_ = '发明专利' group by co.name_ ,cod.name_) )tt on tt.dept = s.dept";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		String dept1 = "";
		String dept2 = "";
		int sq1 = 0;
		int sq2 = 0;
		int sl1 = 0;
		int sl2 = 0;
		double sqd = 0d;
		double sld = 0d;
		if(list != null && list.size()>0){
			for(Map<String,Object> map : list){
				if(MapUtils.getIntValue(map, "rn") == 1){
					dept1 = MapUtils.getString(map, "dept");
					if("授权".equals(MapUtils.getString(map, "state"))){
						sq1 = MapUtils.getIntValue(map, "value");
					}
					if("受理".equals(MapUtils.getString(map, "state"))){
						sl1 = MapUtils.getIntValue(map, "value");
					}
				}
				if(MapUtils.getIntValue(map, "rn") == 2){
					dept2 = MapUtils.getString(map, "dept");
					if("授权".equals(MapUtils.getString(map, "state"))){
						sq2 = MapUtils.getIntValue(map, "value");
					}
					if("受理".equals(MapUtils.getString(map, "state"))){
						sl2 = MapUtils.getIntValue(map, "value");
					}
				}
			}
		}
		if(sq_total > 0){
			sqd = sq1+sq2;
			sqd = (sqd/sq_total)*100;
			sqd = MathUtils.get2Point(sqd);
		}
		if(sl_total > 0){
			sld = sl1+sl2;
			sld = (sld/sl_total)*100;
			sld = MathUtils.get2Point(sld);
		}
		
		resultMap.put("dept1", dept1);
		resultMap.put("dept2", dept2);
		resultMap.put("sq1", sq1);
		resultMap.put("sq2", sq2);
		resultMap.put("sl1", sl1);
		resultMap.put("sl2", sl2);
		resultMap.put("sqd", sqd);
		resultMap.put("sld", sld);
		
		return resultMap;
	}


	@Override
	public Map<String, Object> queryPatentDetail(Page page, String xkmlid,
			String startYear, String endYear, String zzjgid, String name,
			String flag,String shiroTag) {
		String querySql = "";
		if("dept".equals(flag)){
			querySql = " and dept.name_ = '"+name+"'";
		}else if("year".equals(flag)){
			querySql = " and substr(t.accept_time,0,4) = '"+name+"'";
		}else if("state".equals(flag)){
			querySql = " and cd.name_ = '"+name+"'";
		}else if("type".equals(flag)){
			querySql = " and co.name_ = '"+name+"'";
		}
		
		String sql = "select tt.* ,rownum rn from (select t.id,t.name_,dept.name_ dept_name,t.patent_dept,t.inventors,"
				+ " co.name_ type_,cd.name_ state_,t.accept_time,t.accredit_time,"
				+ " t.patent_no from t_res_patent t left join t_code_dept dept on dept.id = t.inventor_dept_id"
				+ " left join t_code co on co.code_type = 'RES_PATENT_TYPE_CODE' and co.code_ = t.patent_type_code and co.istrue = 1"
				+ " left join t_code cd on cd.code_type = 'RES_PATENT_STATE_CODE' and cd.code_ = t.patent_state_code and cd.istrue = 1 "
				+ " where substr(t.accept_time,0,4) between '"+startYear+"' and '"+endYear+"' and t.project_id in "
				+ " (select distinct s.id from t_code_subject s start with s.id in ('"+xkmlid+"') connect by s.pid = prior s.id)"
				+ " and t.inventor_dept_id in ("+businessService.getDeptDataPermissIdsQuerySql(zzjgid, shiroTag)+")"+querySql
				+ " order by t.accept_time desc ) tt";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}
	
	
	
	
}
