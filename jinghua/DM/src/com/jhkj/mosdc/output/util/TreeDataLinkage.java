package com.jhkj.mosdc.output.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.output.dao.OutputCommonDao;
import com.jhkj.mosdc.output.po.FunComponent;
import com.jhkj.mosdc.output.po.Node;
import com.jhkj.mosdc.output.po.Tree;
import com.jhkj.mosdc.permission.po.MenuTree;
import com.jhkj.mosdc.permission.util.UserPermissionUtil;

/**
 * 树形结果数据的组件数据处理类。
 * 
 * @Comments: 
 * Company: 河南精华科技有限公司
 * Created by zhangzg(vbxnt521_edu@163.com)
 * @DATE: 2012-11-13
 * @TIME: 下午04:23:11
 */
public class TreeDataLinkage {
	/**
	 * 查询教学组织结构树
	 * @param param
	 * @return
	 */
	public  FunComponent queryJxzzjg(FunComponent fc,String params){
		Map map = new HashMap();
		try{
			MenuTree tree = UserPermissionUtil.getJxzzjgTree();
			map.put("menuTree", tree.getRoot());
			fc.setDisplayData(map);
		}catch(Exception e){
			e.printStackTrace();
		}
		return fc;
	}
	/***
	 * 查询行政组织结构树
	 * 
	 */
	public FunComponent queryXzzzjg(FunComponent fc,String params){
		Map map = new HashMap();
		try{
			MenuTree tree = UserPermissionUtil.getXzzzjgTree();
			map.put("menuTree", tree);
			fc.setDisplayData(map);
		}catch(Exception e){
			e.printStackTrace();
		}
		return fc;
	}
	/***
	 * 查询行政组织结构树
	 */
	public FunComponent queryZzjg(FunComponent fc,String params){
		Map map = new HashMap();
		try{
			MenuTree tree = UserPermissionUtil.getZzjgTree();
			map.put("menuTree", tree);
			fc.setDisplayData(map);
		}catch(Exception e){
			e.printStackTrace();
		}
		return fc;
	}
}
