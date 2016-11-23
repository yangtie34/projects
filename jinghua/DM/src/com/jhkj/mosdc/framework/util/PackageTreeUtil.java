package com.jhkj.mosdc.framework.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jhkj.mosdc.framework.dto.CenterConfig;

/**
 * 通用工具类。
 * 
 * @Comments: Company: 河南精华科技有限公司 Created by zhangzg(vbxnt521_edu@163.com)
 * @DATE: 2012-12-5
 * @TIME: 下午06:55:40
 */
public class PackageTreeUtil {
	
	/**
	 * 根据数据库中返回的CenterConfig对象列表和根节点id获取一个树结构的CenterConfig出来。
	 * 
	 * @param ccs
	 * @param rootId
	 * @return
	 */
	public static CenterConfig listCc2TreeCc(List<CenterConfig> ccs, Long rootId) {
		Map<Long, CenterConfig> ccTree = new HashMap<Long, CenterConfig>();
		// 第一步将数组中的对象放知道map中，以id为键以自身为值。
		for (int i = 0; i < ccs.size(); i++) {
			ccTree.put(ccs.get(i).getId(), ccs.get(i));
		}
		// 循环遍历每个map中的值，得到每个值时在map中找它的父节点id对象。
		// 找不到就算了，找到后将它自己添加父节点对象的子孙列表中。
		Iterator<Long> it = ccTree.keySet().iterator();
		while (it.hasNext()) {
			Long key = it.next();
			CenterConfig value = ccTree.get(key);
			if (ccTree.containsKey(value.getFjdid())) {
				ccTree.get(value.getFjdid()).getChilds().add(value);
			}
		}
		CenterConfig result = ccTree.containsKey(rootId) ? ccTree.get(rootId) : null;
		return result;
	}
	/**
	 * 将传递进来的所有配置对象合并成一个大的配置中心对象。
	 * 合并的时候需要去除重复的配置对象节点。
	 * @param ccs
	 * @return
	 */
	public static CenterConfig mergeRemoveRepeat(List<CenterConfig> ccs){
		CenterConfig bigCenter = new CenterConfig();
		for(int i=0;i<ccs.size();i++){
			//TODO:合并的逻辑暂停。
		}
		return bigCenter;
	}
}
