package com.jhkj.mosdc.output.process.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.bean.ApplicationComponentStaticRetriever;
import com.jhkj.mosdc.framework.bean.PageParam;
import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.output.dao.OutPutChartDao;
import com.jhkj.mosdc.output.dao.OutPutTextDao;
import com.jhkj.mosdc.output.process.OutPutCommonProcess;

/**
 * @comments:统计细粒度service实现方法
 * @author hanking(E-mail : IsaidIwillgoon@gmail.com)
 * @date:2012-11-6
 * @time:上午11:02:49
 * @version :
 */
public class OutPutCommonProcessImpl extends BaseServiceImpl implements
		OutPutCommonProcess {

	private OutPutChartDao outPutChartDao;
	private OutPutTextDao outPutTextDao;

	public void setOutPutChartDao(OutPutChartDao outPutChartDao) {
		this.outPutChartDao = outPutChartDao;
	}

	public void setOutPutTextDao(OutPutTextDao outPutTextDao) {
		this.outPutTextDao = outPutTextDao;
	}

	/**
	 * 固定指标 多种范围情况下--2、3维统计
	 * 
	 * @param params
	 * @return
	 */
	public String getResultOneToMany(String params) {
		return null;
	}

	@Override
	public String queryResultManyToOne(String params) {
		/*
		 * 流程：多种指标，则对应多种计算方式 则对应的表也可能不同 指标表： id name sql 表说明使用范围 组件id bz --(+fw)
		 */
		JSONObject obj = JSONObject.fromObject(params);
		String fw = this.getLdCondition(obj.getString("fw"));// 范围撤了
		String wdId = obj.getString("wd");// 以时间为例--自定义纬度id-->纬度值
		String zbIds = obj.getString("zb");// 应该作为一个","分隔的 串
		// 拿纬度结果集 遍历 指标数据（相当于一个条件+fw）
		List<Object> wdList = this.outPutChartDao.queryWdCxzdmSql(wdId);// ??？？？
		List<Object> zbList = this.outPutChartDao.queryZbLists(zbIds);
		// for (int j = 0; j < wdList.size(); j++) {
		// 得到纬度的值
		for (int i = 0; i < zbList.size(); i++) {
			Object[] zbObj = (Object[]) zbList.get(i);
			String zbSql = (String) zbObj[1];// 得到指标的sql
			// 通过指标的SQL后加上纬度的值作为条件 + 范围这个条件 得到指标下的数值
			List<Object> resultList = this.outPutChartDao.queryZbSqlResult(
					zbSql, fw);// 并添加上纬度的信息
		}
		// }
		return zbList.toString();
	}

	// 2维统计 作废
	@Override
	public String queryResultForChartSimple(String params) {
		JSONObject obj = JSONObject.fromObject(params);
		Map<String, Object> allMap = obj.getJSONObject("componentTableContent");// 组件表内的数据
		ArrayList initRangeComponentArray = new ArrayList();// 用于初始化时范围组件的map
		Boolean Initialization = this.isInitialization(obj);// 是否初始化
		// 如果是初始化的话
		if (Initialization) {
			params = this.getInitParams(obj, initRangeComponentArray);
		}
		obj = JSONObject.fromObject(params);
		String wdbId_ = obj.getString("wd");
		String fw_ = this.getLdCondition(obj.getString("fw"));// 范围撤了
		// String zb_ = obj.getString("zb");// 率 或 值
		List<Object> dmSql = this.outPutChartDao.queryWdCxzdmSql(wdbId_);// 纬度表Id
		Object[] dmSqlObj = (Object[]) dmSql.get(0);
		String cxdm = (String) dmSqlObj[0];// 查询代码
		String wdSql = (String) dmSqlObj[1];// 纬度下的SQL
		List<Object> wdList = this.outPutChartDao.queryWdList(wdSql);// 得到纬度自身的值
		// id/mc
		// 如果zb是值的话
		List<String> modelList = new ArrayList<String>();
		Map<String, String> mapData = new HashMap<String, String>();
		for (int i = 0; i < wdList.size(); i++) {
			Object[] wdObj = (Object[]) wdList.get(i);
			Long wdId = (Long) wdObj[0];
			String wdmc = (String) wdObj[1];
			List<Object> zsList = this.outPutChartDao
					.queryStudentStatisticsTable(cxdm, wdId.toString(), fw_);
			String tjzs = zsList.get(0).toString();// 纬度下的统计总数

			mapData.put(wdmc, tjzs);// 此方法内 只做 二维 总数统计
			/*
			 * if ("zhi".equals(zb_)) { mapData.put(wdmc, tjzs); } else if
			 * ("lv".equals(zb_)) { String total = this.outPut1Dao
			 * .getStudentStatisticsTableCount(fw_); Double ratio =
			 * this.getStatisticsRatio(tjzs, total);// 比率 mapData.put(wdmc,
			 * ratio.toString()); } else { }
			 */
			modelList.add(wdmc);
		}
		allMap.put("items", modelList);
		allMap.put("data", mapData);
		allMap.put("rangeComponent", initRangeComponentArray);
		return allMap.toString();
	}

	// 3维统计--方法跟统计类型有些耦合 作废
	@Override
	public String queryResultForChartComplex(String params) {
		JSONObject obj = JSONObject.fromObject(params);
		String wdbId_ = obj.getString("wd");
		String zb = obj.getString("zb");//
		String fw = this.getLdCondition(obj.getString("fw"));// 范围撤了~~

		List<Object> dmSqlCid = this.outPutChartDao.queryWdCxzdmSql(wdbId_);// 纬度表Id
		Object[] dmSqlCidObj = (Object[]) dmSqlCid.get(0);
		String cxdm = (String) dmSqlCidObj[0];// 查询代码
		String wdSql = (String) dmSqlCidObj[1];// 纬度SQL
		BigDecimal cId = (BigDecimal) dmSqlCidObj[2];// 子id
		List<Object> wdList = this.outPutChartDao.queryWdList(wdSql);// 得到纬度自身的值
		// id/mc

		List<String> modelList = null;
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		for (int i = 0, len = wdList.size(); i < len; i++) {
			Map<String, String> mapData = new HashMap<String, String>();
			Object[] obj_ = (Object[]) wdList.get(i);
			Long wdId_ = (Long) obj_[0];// 外层的纬度id
			String wdmc_ = (String) obj_[1];// 外层的纬度名称
			if (null == cId) {
				return null;
			}
			// 第二纬度数据
			List<Object> dmSqlCid2 = this.outPutChartDao.queryWdCxzdmSql(cId
					.toString());
			Object[] dmSqlCid2Obj = (Object[]) dmSqlCid2.get(0);
			String cxdm2 = (String) dmSqlCid2Obj[0];// 第二纬度的查询代码--给大表的
			String wdSql2 = (String) dmSqlCid2Obj[1];// 第二纬度的查询sql
			List<Object> wdList2 = this.outPutChartDao.queryWdList(wdSql2);// 得到的是第二纬度id和名称
			modelList = new ArrayList<String>();

			for (int j = 0, len2 = wdList2.size(); j < len2; j++) {
				StringBuilder sb = new StringBuilder();
				Object[] obj2_ = (Object[]) wdList2.get(j);
				Long wdId2_ = (Long) obj2_[0];
				String wdmc2_ = (String) obj2_[1];
				String fwCondition = fw.concat(sb.append(" and ").append(cxdm2)
						.append("='").append(wdId2_).append("'").toString());
				List<Object> zsList = this.outPutChartDao
						.queryStudentStatisticsTable(cxdm, wdId_.toString(),
								fwCondition);

				modelList.add(wdmc2_);// 多个的纬度名称
				if ("lv".equals(zb)) {
					String total = this.outPutChartDao
							.queryStudentStatisticsTableCount(fw);// 率值唯一不同点
					Double ratio = this.getStatisticsRatio(zsList.get(0)
							.toString(), total);
					mapData.put(wdmc2_, ratio.toString());
				} else if ("zhi".equals(zb)) {
					mapData.put(wdmc2_, zsList.get(0).toString());
				} else {
				}

			}
			mapData.put("seriesname", wdmc_);
			dataList.add(mapData);
		}
		// dataList
		return modelList.toString();
	}

	/*****************************************************************
	 * 文本统计结果--全部显示完各个参数
	 *****************************************************************/
	@Override
	public Map<String, Object> queryTextResult(String params) {

		JSONObject obj = JSONObject.fromObject(params);
		Map<String, Object> allMap = new HashMap<String, Object>();
		String ld = "";
		Boolean isInit = this.isInitialization(obj);
		/* 初始化 非初始化通用部分 */
		if (!isInit) {
			ld = this.getLdCondition((String) obj.get("ld"));// 联动数据
		}
		List<Map<String, Object>> textData = new ArrayList<Map<String, Object>>();
		List<Object> textList = this.outPutTextDao.queryTextList(obj
				.getString("componentId"));
		Object[] textListObj = (Object[]) textList.get(0);
		BigDecimal textId = (BigDecimal) textListObj[0];
		String htmlStr = (String) textListObj[1];
		// 得到文本内容
		List<Object> fzTextList = this.outPutTextDao.queryGlTextList(textId
				.toString());
		// 得到sql
		for (int i = 0; i < fzTextList.size(); i++) {
			Map<String, Object> textMap = new HashMap<String, Object>();
			Map<String, Object> wbTextParams = new HashMap<String, Object>();// 声明文本查询参数
			Object[] fzTextListObj = (Object[]) fzTextList.get(i);
			String wbTextvalue = "0";
			String sql = (String) fzTextListObj[3];
			List<Object> listValue = this.outPutTextDao
					.queryListValue(sql + ld);// 添加相应的联动条件
			BigDecimal wbTextId = (BigDecimal) fzTextListObj[0];// 文本id
			if(null!=listValue){
				 wbTextvalue = listValue.get(0) == null ? "0" : listValue
							.get(0).toString();// 文本sql执行的查询结果值
			}
			String wbTextStsxm = (String) fzTextListObj[4];// 该文本统计对应的实体属性名（供Grid使用，查询grid所需数据）
			wbTextParams.put("id", wbTextId);
			wbTextParams.put("stsxm", wbTextStsxm);
			textMap.put("text", wbTextvalue);
			textMap.put("params", wbTextParams);
			textData.add(textMap);
		}
		allMap.put("htmlStr", htmlStr);
		allMap.put("data", textData);
		return allMap;
	}

	/**
	 * 统计比率方法
	 * 
	 * @param tjzs
	 *            根据纬度和范围计算后的统计总数
	 * @param total
	 *            根据范围计算后的统计总数
	 * @return
	 */
	private Double getStatisticsRatio(String tjzs, String total) {
		Double ratio = null;
		try {
			ratio = Double.valueOf(tjzs) / (Double.valueOf(total));// 保留小数点后两位
		} catch (ArithmeticException e) {
			e.printStackTrace();
		}
		// 处理精度问题...
		return ratio;
	}

	/**
	 * 用于得到联动组件的拼接数据的工具方法
	 * 
	 * @param ld
	 * @return
	 */
	private String getLdCondition(String ld) {
		StringBuilder sb = new StringBuilder("");
		try {
			Map map = JSONObject.fromObject(ld);// 转map形式
			// 解析map形式的key与value
			for (Iterator it = map.keySet().iterator(); it.hasNext();) {
				Object key = it.next();
				sb.append(" and (t." + key + "='" + map.get(key) + "')");// 添加了括号
				// 但是还要做相应的更改
				// 比如说 范围的 相似的 等等。
			}
		} catch (JSONException e) {

		} finally {
			return sb.toString();
		}
	}

	/**
	 * 用以判断是否是初始化
	 * 
	 * @param obj
	 * @return
	 */
	private Boolean isInitialization(JSONObject obj) {
		Boolean Initialization = false;
		try {
			// 只要初始化init字段的值不为null 均返回true，否则包括没有init字段的情况下 均认为不是初始化
			Initialization = obj.get("init") != null ? true : false;
		} catch (JSONException e) {
			System.out.println("无法取得初始化！" + e.getMessage());
		} finally {
			return Initialization;
		}
	}

	// 这块可能要重构下即可
	/**
	 * 得到范围附件里的数据工具方法
	 */
	private Map getComponentMap(List list, String name) {
		Map<String, Object> componentMap = new HashMap<String, Object>();
		ArrayList array = new ArrayList();// 纬度范围组件的数组
		ArrayList ids_ = new ArrayList();
		String comType = null;
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String mc = null;
			String sfmr = null;
			Object[] obj = (Object[]) list.get(i);
			BigDecimal id = (BigDecimal) obj[0];
			mc = (String) obj[1];
			if ("wd" == name) {
				sfmr = (String) obj[8];// 是否默认值
				comType = (String) obj[9];
			} else if ("zb" == name) {
				sfmr = (String) obj[6];
				comType = (String) obj[7];
			} else if ("fw" == name) {
				sfmr = (String) obj[3];
				comType = (String) obj[4];
			}
			map.put("value", id.toString());
			map.put("name", mc);
			array.add(map);
			if (sfmr.equals("1")) {
				ids_.add(id.toString());
			}
		}
		// 如果无该条数据的话 则不添加下列数据
		if (list.size() != 0) {
			componentMap.put("comType", comType);
			componentMap.put("valueRange", name);
			componentMap.put("defaultValue", ids_);// 这里将数据转为了数组
			componentMap.put("data", array);
		}
		return componentMap;
	}

	/**
	 * 用以拼接初始化参数的
	 * 
	 * @param obj
	 * @param initRangeComponentArray
	 * @return
	 */
	private String getInitParams(JSONObject obj,
			ArrayList initRangeComponentArray) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();// --------------
		Map<String, Object> initMap = new HashMap<String, Object>();// 用于初始化时params的map

		// 根据componentId全部 获取统计组件的范围、纬度、指标等信息 --1、为初始化提供范围组件数据 2、通过判断默认数据
		// 供params使用
		String componentId = obj.getString("componentId");
		List<Object> fwList = this.outPutChartDao
				.queryFwList(componentId, null);// 范围一个的时候,指标可多个
		List<Object> wdList = this.outPutChartDao
				.queryWdList(componentId, null);// 默认一个,选择也必须是一个
		List<Object> zbList = this.outPutChartDao
				.queryZbList(componentId, null);// 指标一个的时候
		// 范围可多个
		// 变量依次代表的含义是：纬度id、指标id、范围id默认集合，组件的的Data数据，判断data是为控制前端是否显示组件。
		JSONArray wdids_ = new JSONArray(), zbids_ = new JSONArray(), fwids_ = new JSONArray(), componentData = new JSONArray();
		Map<String, Object> wdComponentMap = this.getComponentMap(wdList, "wd");
		Map<String, Object> zbComponentMap = this.getComponentMap(zbList, "zb");

		Map<String, Object> fwComponentMap = this.getComponentMap(fwList, "fw");
		// 如果纬度、范围、指标的map有空值的话，这不添加进这个初始化数组内。
		if (zbComponentMap.size() != 0) {
			zbids_ = JSONObject.fromObject(zbComponentMap).getJSONArray(
					"defaultValue");
			initRangeComponentArray.add(JSONObject.fromObject(zbComponentMap));
		}
		if (fwComponentMap.size() != 0) {
			fwids_ = JSONObject.fromObject(fwComponentMap).getJSONArray(
					"defaultValue");
			initRangeComponentArray.add(JSONObject.fromObject(fwComponentMap));
		}
		if (wdComponentMap.size() != 0) {
			wdids_ = JSONObject.fromObject(wdComponentMap).getJSONArray(
					("defaultValue"));
			initRangeComponentArray.add(JSONObject.fromObject(wdComponentMap));
		}
		initMap.put("wd", wdids_);
		initMap.put("zb", zbids_);
		initMap.put("fw", fwids_);
		initMap.put("ld", "{}");// 初始化联动条件是空的--要舍弃的。

		paramsMap.put("params", initMap);// =====================
		paramsMap.put("totalParams", "{}");// 联动参数初始化为空
		return JSONObject.fromObject(paramsMap).toString();// =================
		// return JSONObject.fromObject(initMap).toString();
	}

	/**********************************************************************
	 * 得到图形的通用返回结果
	 *********************************************************************/
	@Override
	public Map<String, Object> queryCommonChartResult(String params) {
		JSONObject obj = JSONObject.fromObject(params);
		Map<String, Object> allMap = new HashMap<String, Object>();// obj.getJSONObject("componentTableContent");//
		// 组件表内的数据
		Boolean isInit = this.isInitialization(obj);
		ArrayList initRangeComponentArray = new ArrayList();
		/* 如果是初始化的话 */
		if (isInit) {
			// 得到初始化的params 并得到initRangeComponentArray
			params = this.getInitParams(obj, initRangeComponentArray);
		}
		/* 如果不是初始化的话：含有联动组件数据 */
		params = JSONObject.fromObject(params).getString("params");
		JSONObject newObj = JSONObject.fromObject(params);
		String wdId = newObj.getJSONArray("wd").getString(0);// 纬度每次仅仅只能有一个SQL得到的数据
		// 范围或指标 两者中：范围1：指标多；指标1：范围多
		JSONArray fwIdsArray = newObj.getJSONArray("fw");// 范围数组{fw:[]}
		JSONArray zbIdsArray = newObj.getJSONArray("zb");// 指标数组
		String ldConditions = this.getLdCondition((String) newObj.get("ld"));// 联动条件
		// 如果范围一个 指标多个 考虑：若是不指定范围呢

		String unit = null;// 单位
		String title = null;// 标题
		if (fwIdsArray.size() == 1 && zbIdsArray.size() == 1) {
			// 范围和指标都单一的时候  查找哪个默认的数据大 如果默认的数据量都为1 这执行范围多个 指标单一方法
//			if(isInit){
//				allMap = this.getAllMapForOneWdMoreFw(allMap, fwIdsArray,
//						zbIdsArray, title, unit, wdId, ldConditions);
//			}else{
				//通过范围或者维度的id 找到组件id 然后再查找组件对应范围以及维度的个数
				String zjId_ = obj.getString("componentId");
				boolean flag = this.outPutChartDao.isFwLessZb(zjId_);
				if(!flag){
					allMap = this.getAllMapForOneWdMoreFw(allMap, fwIdsArray,
							zbIdsArray, title, unit, wdId, ldConditions);
				}else{
					allMap = this.getAllMapForOneFwMoreWd(allMap, fwIdsArray,
							zbIdsArray, title, unit, wdId, ldConditions);
				}
//			}
		} else if (fwIdsArray.size() > 1 && zbIdsArray.size() == 1) {
			//范围多个   指标单一的时候
			allMap = this.getAllMapForOneWdMoreFw(allMap, fwIdsArray,
					zbIdsArray, title, unit, wdId, ldConditions);
		} else if (fwIdsArray.size() == 1 && zbIdsArray.size() > 1) {
			//范围单一 指标多个的时候
			allMap = this.getAllMapForOneFwMoreWd(allMap, fwIdsArray,
					zbIdsArray, title, unit, wdId, ldConditions);
		} else {
			// 暂未想到如此数据
		}
		if (allMap.size() != 0) {
			allMap.put("components", initRangeComponentArray);
		}
		return allMap;
	}

	@Override
	public String queryGridResult(String params) {
		JSONObject obj = JSONObject.fromObject(params);
		Map paramsList = null;
		String listToJson = "[]";
		StringBuilder resultAll = null;
		int totalCount = 0;
		try {

			// 得到sql分页拼接参数
			// 能否再给一个是否初始化grid的参数呢？ 非初始化的话应该是不含有联动参数的
			int start = 0, limit = 25;
			String condition = null;
			String seniorQuery = null;
			Map<String, Object> map = new HashMap<String, Object>();
			if (params.contains("limit")) {
				// 如果包含limit数据
				// 初始化数据--这样的信息,我个人感觉应该上层给是否第一次初始化数据 来做判定
				start = obj.getInt("start");
				limit = obj.getInt("limit");
				// condition = obj.getString("condition");
			}
			String wbgnId = obj.getString("id");// 文本功能id
			obj.remove("id");// 移除id
			// 应该还需要联动范围数据 分页呢？
			// 即使是初始化弹窗的时候，也应该包含着对应的联动数据吧？
			List<Object> wbgnList = this.outPutChartDao.queryWbtjgnList(wbgnId);
			// 需要得到grid的SQL 以及 以及对应的GridService路径名称？
			String gridView = String.valueOf(((Object[]) wbgnList.get(0))[8]);// gridView
			PageParam pageParam = new PageParam(start, limit);// 分页参数
			paramsList = getSQLParams(obj);
			List<String> conditionList = (List<String>) paramsList.get("list");
			if (conditionList.size() == 0) {
				seniorQuery = (String) paramsList.get("seniorStr");
			}
			List<Map> listMap = this.outPutTextDao.queryGridListForText(
					gridView, conditionList, seniorQuery, pageParam);
			listToJson = Struts2Utils.list2json(listMap);
			totalCount = pageParam.getRecordCount();
			resultAll = new StringBuilder("{\"success\":\"true\",\"data\":");
			resultAll.append(listToJson).append(",\"totalCount\":\"").append(
					totalCount).append("\"}");
		} catch (Exception e) {
			resultAll = new StringBuilder("{\"success\":\"false\"}");
			e.printStackTrace();
		} finally {
			return resultAll.toString();
		}

		// // 然后将分页数据也对应添加至此 用于传递参数 供test()方法内部调用
		// // 拼接相应数据
		// map.put("start", start);
		// map.put("limit", limit);
		// map.put("condition", condition);
		// map.put("gridView", gridView);
		//
		// String wbgnGridUrl = String.valueOf(((Object[]) wbgnList.get(0))[6]);
		// String[] serviceURL = wbgnGridUrl.split("\\?");
		// String serviceName = serviceURL[0];
		// String serviceMethod = serviceURL[1];
		// Object bean = ApplicationComponentStaticRetriever
		// .getComponentByItsName(serviceName);
		// String resultTest = "";
		// // Grid_URL————>反射调用对应的service ==>这块有点毛病!
		// try {
		// Class[] args = new Class[1];
		// Class clazz = bean.getClass();
		// // 内部还要对args做一定的处理 args处理成map里的数据
		// args[0] = map.toString().getClass();// --------------------------
		// Method method = clazz.getMethod(serviceMethod, args);
		// resultTest = (String) method.invoke(bean, args);
		// } catch (SecurityException e) {
		// e.printStackTrace();
		// } catch (NoSuchMethodException e) {
		// e.printStackTrace();
		// } catch (IllegalArgumentException e) {
		// e.printStackTrace();
		// } catch (InvocationTargetException e) {
		// e.printStackTrace();
		// } catch (IllegalAccessException e) {
		// e.printStackTrace();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//
		// return resultTest.toString();
	}

	@Override
	public String querytest(String params) {
		String listToJson = "[]";
		StringBuilder resultAll = null;
		int totalCount = 0;
		try {
			// 接收的是：GridView start limit condition
			JSONObject obj = JSONObject.fromObject(params);
			Map paramsList = this.getSQLParams(obj);// 得到sql分页拼接参数
			String gridView = obj.getString("gridview");// 得到grid的视图
			int start = Integer.parseInt(obj.getString("start"));
			int limit = Integer.parseInt(obj.getString("limit"));
			PageParam pageParam = new PageParam(start, limit);// 分页参数
			List<String> conditionList = (List<String>) paramsList.get("list");
			String seniorQuery = (String) paramsList.get("seniorStr");
			List<Map> listMap = this.outPutTextDao.queryGridListForText(
					gridView, conditionList, seniorQuery, pageParam);
			listToJson = Struts2Utils.list2json(listMap);
			totalCount = pageParam.getRecordCount();
			resultAll = new StringBuilder("{\"data\":");
			resultAll.append(listToJson).append(",\"totalCount\":\"").append(
					totalCount).append("\"}");
		} catch (Exception e) {
			// 此异常扑捉方法需要调整
		} finally {
			return resultAll.toString();
		}
	}

	@Override
	public Map getSQLParams(Map maps) throws Exception {
		Map<String, Object> map = new HashMap();
		Set<String> key = maps.keySet();
		List<String> retList = new ArrayList<String>();
		Iterator<String> iter = key.iterator();
		StringBuffer rqStr = new StringBuffer();
		String seniorStr = "";
		while (iter.hasNext()) {
			String field = iter.next();
			String value = "";
			if ("seniorQuery".equals(field)) {
				// 判断条件，并组装相应SQL
				value = String.valueOf(maps.get(field));
				// 高级查询条件
				JSONArray jsonArray = JSONArray.fromObject(value);
				// 判断jsonArray是否有值
				if (jsonArray.size() > 0) {
					seniorStr = this.seniorSQL(jsonArray);
				}
			} else {
				String[] paramFields = null;
				if (field.indexOf(".") > 0) {
					paramFields = field.split("\\.");
				}
				StringBuffer str = new StringBuffer();
				value = String.valueOf(maps.get(field));
				if (value.startsWith("[") && value.endsWith("]")
						&& value.contains(",")) {
					List list = (List) maps.get(field);
					value = (String) list.get(0);
				}
				value = Struts2Utils.isoToUTF8(value);
				// 目前可确定的以下几种类型不是查询条件
				if ("start".equals(field) || "limit".equals(field)
						|| "page".equals(field) || "error".equals(field)
						|| "entityName".equals(field) || "menuId".equals(field)
						|| "buttonId".equals(field)
						|| "singleReturnNoComponent".equals(field)) {
					continue;
				} else if (paramFields != null) {
					String pf = paramFields[0].toString();
					if (paramFields[1].equals("equals")) {
						str.append(pf + " ");
						str.append("= '" + value + "' ");
					} else if (paramFields[1].equals("like")) {
						str.append(pf + " ");
						str.append("like '%" + value + "%' ");
					} else if (("date1").equals(paramFields[1])) {
						rqStr.append(" and " + pf + " " + ">= '" + value + "'");
					} else if (("date2").equals(paramFields[1])) {
						rqStr
								.append(" and " + pf + " " + " <= '" + value
										+ "'");
					} else if ("in".equals(paramFields[1])) {
						str.append(pf + " ");
						str.append(" in ( " + value + " )");
					} else if ("notequals".equals(paramFields[1])) {
						str.append(pf + " ");
						str.append(" <>  " + value + " ");
					} else {
						str.append(pf + " ");
						str.append("= '" + value + "' ");
					}

				} else if (!value.equals("")) {
					// 如果field字符内包含Id字段,则需添加双引号用以查询
					if (field.endsWith("Id") && field.length() > 2) {
						str.append("\"" + field + "\" = '" + value + "' ");
					} else {
						str.append(field + " = '" + value + "' ");
					}
				}
				if (str.toString().length() != 0) {
					retList.add(str.toString());
				}
			}
		}
		if (rqStr.toString().length() != 0) {
			retList.add(rqStr.toString().replaceFirst("and", ""));
		}
		map.put("list", retList);
		map.put("seniorStr", seniorStr);
		return map;
	}

	private String seniorSQL(JSONArray seniorArray) throws Exception {
		StringBuffer sb = new StringBuffer();
		// 实现SQL拼装
		for (int i = 0; i < seniorArray.size(); i++) {
			Map objMap = (Map) seniorArray.get(i);
			String fullField = objMap.get("field").toString();
			// 去掉实体名
			String[] fieldName = fullField.split("\\.");
			if (fieldName[0].toString().endsWith("Id")
					&& fieldName[0].toString().length() > 2) {
				sb.append(" ( t.\"" + fieldName[0] + "\" ");
			} else {
				sb.append(" ( t." + fieldName[0] + " ");
			}

			String value = Struts2Utils.isoToUTF8(objMap.get("value")
					.toString());
			// 操作符为like的加上　％％，同时把汉字转码为utf-8
			if (objMap.get("operator").equals("like")) {
				sb.append(objMap.get("operator") + " '%" + value + "%')");
			} else {
				sb.append(objMap.get("operator") + " '" + value + "')");
			}
			// 增加 and或or关联
			if (objMap.get("logical") != null && objMap.get("logical") != "") {
				sb.append(" " + objMap.get("logical"));
			}
		}
		return sb.toString();
	}

	// 获取当一个范围 多个维度时的allMap
	private Map<String, Object> getAllMapForOneFwMoreWd(
			Map<String, Object> allMap, JSONArray fwIdsArray,
			JSONArray zbIdsArray, String title, String unit, String wdId,
			String ldConditions) {
		// 范围一个 指标多个 1、要得到范围的查询字段以及条件；2、遍历指标id 得到相应的指标计算sql 3、得到纬度 遍历纬度 4、
		// “2+纬度+范围+联动条件”
		String fwCondition = "";// 声明范围条件
		if (fwIdsArray.size() != 0) {// 如果范围id数组集合不为空
			List<Object> fwList = this.outPutChartDao.queryFwList(null,
					fwIdsArray.getString(0));
			if(fwList!=null){
				fwCondition = String.valueOf(((Object[]) fwList.get(0))[2]);
			}
		} else {
			return allMap;// 范围如果是空的话，也返回map空数据--范围配置，默认可为1=1,
		}
		List<String> modelList = null;
		List<Object> mapList = new ArrayList<Object>();
		for (int i = 0; i < zbIdsArray.size(); i++) {
			List<Object> zbList = this.outPutChartDao.queryZbList(null,
					zbIdsArray.getString(i));
			String zbMc = String.valueOf(((Object[]) zbList.get(0))[1]);// 得到指标名称
			String zbSQL = String.valueOf(((Object[]) zbList.get(0))[2]);// 得到指标的sql
			title = String.valueOf(((Object[]) zbList.get(0))[9]);
			unit = String.valueOf(((Object[]) zbList.get(0))[8]);
			List<Object> wdList = this.outPutChartDao.queryWdList(null, wdId);
			// 纬度的一条集合 然后得到 纬度的SQL 和查询 代码
			String wdSQL = String.valueOf(((Object[]) wdList.get(0))[2]);// 查询纬度的SQL
			String glzd = String.valueOf(((Object[]) wdList.get(0))[3]);// 用于与指标sql关联的字段
			// 执行SQL 得到各个纬度（思考：纬度为时间等可变值怎么办？）
			List<Object> getWdList = this.outPutChartDao.queryCommonSQL(wdSQL);// 得到纬度sql下的各个纬度
			Map<String, String> mapData = new HashMap<String, String>();
			modelList = new ArrayList<String>();
			for (int j = 0; j < getWdList.size(); j++) {
				String xcid = String.valueOf(((Object[]) getWdList.get(j))[0]);
				String xsmc = String.valueOf(((Object[]) getWdList.get(j))[1]);// 显示名称
				modelList.add(xsmc);
				StringBuilder sb = new StringBuilder();
				sb.append(zbSQL).append(" and " + glzd + "=" + xcid);// 此处要更改的
				if ("" != fwCondition) {
					sb.append(" and " + fwCondition);
				}
				sb.append(ldConditions);
				List<Object> resultList = this.outPutChartDao.queryCommonSQL(sb
						.toString());
				mapData.put(xsmc, resultList.get(0) == null ? "0" : resultList
						.get(0).toString());
			}
			mapData.put("seriesname", zbMc);
			mapList.add(mapData);
			// 通过纬度和查询代码 添加到指标sql后 再添加 fw的查询西段和条件 以及联动的条件
		}
		allMap.put("data", mapList);
		allMap.put("unit", unit.equals("null") ? "" : unit);
		allMap.put("title", title.equals("null") ? "" : title);
		allMap.put("model", modelList);
		return allMap;
	}

	// 获取当多个范围 一个维度时的allMap
	private Map<String, Object> getAllMapForOneWdMoreFw(
			Map<String, Object> allMap, JSONArray fwIdsArray,
			JSONArray zbIdsArray, String title, String unit, String wdId,
			String ldConditions) {
		// 如果范围多个 指标一个
		String zbSQL = "";
		if (zbIdsArray.size() != 0) {// 指标不可能是空的？
			List<Object> zbList = this.outPutChartDao.queryZbList(null,
					zbIdsArray.getString(0));
			zbSQL = String.valueOf(((Object[]) zbList.get(0))[2]);// 得到指标sql
			unit = String.valueOf(((Object[]) zbList.get(0))[8]);
			title = String.valueOf(((Object[]) zbList.get(0))[9]);
		} else {
			return allMap;// 指标为空时 返回map空数据
		}
		List<String> modelList = null;
		ArrayList<Object> mapList = new ArrayList<Object>();
		// 多个范围 循环范围
		for (int i = 0; i < fwIdsArray.size(); i++) {
			List<Object> fwList = this.outPutChartDao.queryFwList(null,
					fwIdsArray.get(i).toString());
			String fwMc = String.valueOf(((Object[]) fwList.get(0))[1]);
			String fwCondition = String.valueOf(((Object[]) fwList.get(0))[2]);

			List<Object> wdList = this.outPutChartDao.queryWdList(null, wdId);
			// 纬度的一条集合 然后得到 纬度的SQL 和查询 代码
			String wdSQL = String.valueOf(((Object[]) wdList.get(0))[2]);// 查询纬度的SQL
			String glzd = String.valueOf(((Object[]) wdList.get(0))[3]);// 用于与指标sql关联的字段
			// 执行SQL 得到各个纬度（思考：纬度为时间等可变值怎么办？）
			List<Object> getWdList = this.outPutChartDao.queryCommonSQL(wdSQL);// 得到纬度sql下的各个纬度
			modelList = new ArrayList<String>();
			Map<String, String> dataMap = new HashMap<String, String>();
			for (int j = 0; j < getWdList.size(); j++) {
				String xcid = String.valueOf(((Object[]) getWdList.get(j))[0]);
				String xsmc = String.valueOf(((Object[]) getWdList.get(j))[1]);// 显示名称
				modelList.add(xsmc);
				StringBuilder sb = new StringBuilder();
				sb.append(zbSQL).append(" and " + glzd + "=" + xcid);
				if ("" != fwCondition) {
					sb.append(" and " + fwCondition);
				}
				sb.append(ldConditions);
				List<Object> resultList = this.outPutChartDao.queryCommonSQL(sb
						.toString());
				dataMap.put(xsmc, resultList.get(0) == null ? "0" : resultList
						.get(0).toString());
			}
			dataMap.put("seriesname", fwMc);
			mapList.add(dataMap);
		}
		allMap.put("data", mapList);
		allMap.put("unit", unit.equals("null") ? "" : unit);
		allMap.put("title", title.equals("null") ? "" : title);
		allMap.put("model", modelList);
		return allMap;
	}
}
