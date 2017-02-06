package cn.gilight.dmm.business.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import cn.gilight.dmm.business.dao.AdvancedDao;
import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedSource;
import cn.gilight.dmm.business.service.AdvancedService;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.PmsUtils;
import cn.gilight.framework.entity.NodeAngularTree;
import cn.gilight.framework.uitl.ApplicationComponentStaticRetriever;
import cn.gilight.framework.uitl.common.ListUtils;
import cn.gilight.framework.uitl.common.ReflectUtil;

/**
 * 高级查询服务
 * 
 * @author xuebl
 * @date 2016年5月18日 上午11:36:43
 */
@Service("advancedService")
public class AdvancedServiceImpl implements AdvancedService {

	@Resource
	private AdvancedDao advancedDao;
	@Resource
	private BusinessService businessService;
	@Resource
	private BusinessDao businessDao;
	
	
	/**
	 * 树形数据-type
	 */
	private static final String Type_Tree = "tree";
	/**
	 * 不限-name
	 */
	private static final String ALL_NAME = "不限";
	/**
	 * 不限-id-null
	 */
	private static final String ALL_ID_NULL = null;
	

	@Cacheable(value="xgTeaCache",key="'AdvancedService.queryAdvancedList'+#tag")
	@Override
	public List<Map<String, Object>> queryAdvancedList(String tag){
		List<Map<String, Object>> list = new ArrayList<>();
		List<AdvancedSource> sourcelist = advancedDao.getAdvancedList(tag);
		for(AdvancedSource t : sourcelist){
			Map<String, Object> map = new HashMap<>();
			map.put("group", t.getGroup());
			map.put("queryCode", t.getCode());
			map.put("queryName", t.getName());
			map.put("items", getServiceData(t));
			if(t.getType()!=null && Type_Tree.equals(t.getType()))
				map.put("queryType", "comboTree");
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 获取每一个服务源-数据
	 * @param t
	 * @return Object
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object getServiceData(AdvancedSource t){
		Object data = null;
		/**
		 * 1.有自定义服务 -> 使用服务提取数据
		 * 		如果是数据权限，需要用户权限参数
		 * 2.有自定义table且type=='tree'，查询标准树形数据
		 * 3.查询标准编码表数据
		 * 		是否不限，isAll
		 */
		String service = t.getService();
		if(service != null){
		  try {
			String[] ary = null;
			String beanName   = null,
				   methodName = null;
			Object bean   = null;
			Method method = null;
			// 是否有参数服务
			List paramList = new ArrayList<>();
			String param_service = t.getParam();
			if(param_service != null){
				ary = param_service.split("\\?");
				if(ary.length != 2){
					ary = param_service.split(",");
					paramList = Arrays.asList(ary);
					paramList = ListUtils.ary2List(ary);
				}else{
					beanName   = ary[0];
					methodName = ary[1];
					bean   = ApplicationComponentStaticRetriever.getComponentByItsName(beanName);
					method = ReflectionUtils.findMethod(bean.getClass(), methodName);
					paramList = (List) ReflectionUtils.invokeMethod(method, bean);
					/*if(data instanceof List){
						List col = (List)paramList;
						paramCls = new Class[col.size()];
						for(int i=0,len=paramCls.length; i<len; i++){
							paramCls[i] = col.get(i).getClass();
						}
					}else{
						paramCls = new Class[]{data.getClass()};
					}*/
				}
			}
			// 根据服务方法取数据
			ary = service.split("\\?");
			beanName   = ary[0];
			methodName = ary[1];
			bean   = ApplicationComponentStaticRetriever.getComponentByItsName(beanName);
			// 使用 ReflectUtil中的 findMethod、invoke方法，其他办法没搞定
			method = ReflectUtil.findMethod(bean.getClass(), methodName, paramList.size());
			if(paramList.size() > 0){
				String json = JSONArray.toJSONString(paramList);
				JSONArray ary2 = JSON.parseArray(json);
				data = ReflectUtil.invoke(bean, method, ary2);
			}
			else 
				data = ReflectionUtils.invokeMethod(method, bean);
		  } catch (NoSuchMethodException e) {
			  e.printStackTrace();
		  } catch (IllegalArgumentException e) {
			  e.printStackTrace();
		  } catch (IllegalAccessException e) {
			  e.printStackTrace();
		  } catch (InvocationTargetException e) {
			  e.printStackTrace();
		  } 
		}else if(t.getTable() != null && Type_Tree.equals(t.getType())){
			// TODO 查询标准树形编码
		}else{
			data = businessService.queryBzdmListMap(t.getCode());
		}
		if(data instanceof List && t.isIsall()){
			Map<String, Object> map = new HashMap<>();
			map.put("id", ALL_ID_NULL);
			map.put("mc", ALL_NAME);
			((List)data).add(0, map);
		}
		return data;
	};
	
	
	@Override
	public Object getDeptTeachDataService(String shiroTag){
		String deptIds = getDeptIds(shiroTag);
		List<NodeAngularTree> nodeList = advancedDao.getDeptTeachList(businessDao.getDeptTeachStuIdSqlByDeptIds(deptIds));
		return getTree(nodeList);
	}

	@Override
	public Object getDeptTeachTeachDataService(String shiroTag){
		String deptIds = getDeptIds(shiroTag);
		List<NodeAngularTree> nodeList = advancedDao.getDeptTeachList(businessDao.getDeptTeachTeachIdSqlByDeptIds(deptIds));
		return getTree(nodeList);
	}
	
	@Override
	public Object getDeptDataService(String shiroTag){
		String deptIds = getDeptIds(shiroTag);
		List<NodeAngularTree> nodeList = advancedDao.getDeptList(businessDao.getDeptIdSqlByDeptIds(deptIds));
		return getTree(nodeList);
	}

	@Cacheable(value="xgTeaCache")
	@Override
	public Object getOriginDataService(){
		List<NodeAngularTree> nodeList = advancedDao.getOriginList();
		String id = businessService.getOriginIdByAbsoluteScale();
		if (id != null){
			for (NodeAngularTree node : nodeList){
				if(id.equals(node.getId())){
					node.setChecked(true);
				}
			}
		}
		return getTree(nodeList);
	}
	
	/**
	 * 获取组织机构IDs
	 * @param shiroTag
	 * @return String
	 */
	private String getDeptIds(String shiroTag){
		List<String> deptList = businessService.getDeptDataList(shiroTag, null);
		// 公共组织机构（带权限）
		String deptIds = null;
		// 权限不是学校
		if(PmsUtils.isAllPmsData(deptList)){
			deptIds = businessService.getSchoolId(); // 权限ID 是 学校ID
		}else{ // 可能有其他节点
			if(!PmsUtils.isNullPmsData(deptList.get(1))){
				deptIds = deptList.get(1);
			}else if(!PmsUtils.isNullPmsData(deptList.get(2))){
				deptIds = deptList.get(2);
			}else if(!PmsUtils.isNullPmsData(deptList.get(3))){
				deptIds = deptList.get(3);
			}
		}
		return deptIds;
	}
	
	/**
	 * List 组装 Tree
	 * @param list
	 * @return NodeAngularTree
	 */
	private NodeAngularTree getTree(List<NodeAngularTree> list){
		NodeAngularTree root=null;
		Map<String, NodeAngularTree> deptMap=new HashMap<String, NodeAngularTree>();
		for(NodeAngularTree dept: list){
			deptMap.put(dept.getId(), dept);
		}
		for(NodeAngularTree dept: list){
			String pid = dept.getPid();
			if(!deptMap.containsKey(pid)){
				root = dept;
			}else if(deptMap.get(pid) != null){
				deptMap.get(pid).getChildren().add(dept);
			}
		}
		return root;
	}
	
}
