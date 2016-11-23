package com.jhnu.util.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jhnu.framework.entity.NodeTree;

public class TreeUtil {
	public static Map<String,Object> getTreeMap(List<NodeTree> list){
		Map<String,Integer> idMap=new HashMap<String,Integer>();
		
		Map<String,Object> map=new HashMap<String,Object>();
		
		for(int i=0;i<list.size();i++){
			NodeTree nat=list.get(i);
			idMap.put(nat.getId(), i);
			//Map<String,Object> lm=BeanMapUtil.toMap(nat);
		}
		map.put("idMap",idMap);
		for(int i=0;i<list.size();i++){
			String pid=list.get(i).getPid();
			if(pid.equalsIgnoreCase("-1")){
				map.put("rootId", i);	
			}else{
				if(idMap.get(pid)!=null&&list.get(idMap.get(pid)) !=null){
					list.get(idMap.get(pid)).setChildrenId(i);
				}
			}
		}
		map.put("data", list);
		return map;
	}

}
