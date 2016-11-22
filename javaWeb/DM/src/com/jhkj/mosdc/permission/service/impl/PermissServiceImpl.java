package com.jhkj.mosdc.permission.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.json.JSONArray;

import com.jhkj.mosdc.framework.bean.CacheManager;
import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.dto.Cache;
import com.jhkj.mosdc.permission.dao.MenuDao;
import com.jhkj.mosdc.permission.dao.UserDao;
import com.jhkj.mosdc.permission.po.MenuNode;
import com.jhkj.mosdc.permission.po.MenuTree;
import com.jhkj.mosdc.permission.po.TsCdzy;
import com.jhkj.mosdc.permission.po.TsUserDataPermiss;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.jhkj.mosdc.permission.service.MenuService;
import com.jhkj.mosdc.permission.service.PermissService;
import com.jhkj.mosdc.permission.util.TypeConvert;
import com.jhkj.mosdc.permission.util.UserPermissionUtil;


/**
 * @Comments: 权限查询接口实现类
 * Company: 河南精华科技有限公司
 * Created by gaodongjie(gaodongjie@126.com) 
 * @DATE:2012-7-9
 * @TIME: 上午11:21:02
 */
public class PermissServiceImpl implements PermissService {
	
	private MenuService menuService;
	
	private MenuDao menuDao;
	
	private BaseDao baseDao;
	
	private UserDao userDao;
	
	private List<TsCdzy> list;
	
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public MenuTree queryPermissCdzy(String param) throws Exception {
//		long a=System.currentTimeMillis();
//		List list=null;
//		// 获取用户信息
//		UserInfo userInfo = UserPermissionUtil.getUserInfo();
//		if(userInfo != null){
//			//根据用户信息获取角色和权限信息
////			list =  menuService.getAllMenuListByUserId(userInfo.getId());
//			/*Cache cache = CacheManager.getCacheInfo(userInfo.getId().toString());
//			if(cache !=null){
//				//
//				Cache caches = (Cache) cache.getValue();//获取缓存对象
//				Map map = (Map) caches.getValue(); //获取缓存对象的map
//				list = (List) map.get("menuList");//获取list
//			}*/
//			if(list == null){
//				//获取当前用户的菜单资源
//				list =  menuService.getAllMenuListByUserId(userInfo.getId(),userInfo.getGroupPermiss());
//				//缓存数据
////				cacheData(userInfo.getId().toString(), "menuList", list);
//			}
//			
////			System.out.println("\r<br>执行耗时0000000000 : "+(System.currentTimeMillis()-a)/1000f+" 秒 ");
//			MenuTree tree = new MenuTree();
//	        MenuNode root = new MenuNode(new Long(0), new Long(-1), "", "");
//	        tree.setRoot(root);
//	        MenuNode treeNode = null;
//	        for (int i = 0; i < list.size(); i++) {
//	            TsCdzy tsCdzy = (TsCdzy) list.get(i);
//	            Long bm = Long.valueOf(baseDao.queryBmById(tsCdzy.getCdssflId(),"XXDM-LZCDFL"));
//	            if(bm.equals(3L)){
//	            	List<String> permissList = getButtons(userInfo.getId(), tsCdzy.getId());
//					treeNode = new MenuNode(tsCdzy.getId(), tsCdzy.getFjdId(), tsCdzy.getMc(), tsCdzy.getCdlj() == "" ? "":tsCdzy.getCdlj(), "0", bm, permissList == null ? "" : "{buttons:"+permissList.toString()+"}");
//		            tree.addNode(treeNode);
//	            }else if(!bm.equals(4L) && !bm.equals(5L)){
//	            	treeNode = new MenuNode(tsCdzy.getId(), tsCdzy.getFjdId(), tsCdzy.getMc(), "", "1", bm, "");
//	            	tree.addNode(treeNode);
//	            }
//	        }
////	        System.out.println("\r<br>执行耗时TTTTTTTTTT : "+(System.currentTimeMillis()-a)/1000f+" 秒 ");
//	      //在最好的一行加上:
//	        //增加输出自定义菜单
//	        //获取当前用户自定义的私有的菜单
//	        List<TsCdzy> syList = menuDao.getSyCdzy(userInfo.getId());
//	        if(syList != null && syList.size()>0){
//	        	for(int i=0;i<syList.size();i++){
//	        		TsCdzy tsCdzy = syList.get(i);
//	        		treeNode =  new MenuNode(tsCdzy.getId(), tsCdzy.getFjdId(), tsCdzy.getMc(), tsCdzy.getCdlj() == "" ? "":tsCdzy.getCdlj(), "0", tsCdzy.getCdssflId(), "");
//		            tree.addNode(treeNode);
//	        	}
//	        }
////	        System.out.println("\r<br>执行耗时111111111 : "+(System.currentTimeMillis()-a)/1000f+" 秒 ");
//	        //获取当前公共的自定义菜单，且审核通过
//	        List<TsCdzy> gyList = menuDao.getGyCdzy();
//	        if(gyList != null && gyList.size()>0){
//	        	for(int i=0;i<gyList.size();i++){
//	        		TsCdzy tsCdzy = gyList.get(i);
//	        		treeNode =  new MenuNode(tsCdzy.getId(), tsCdzy.getFjdId(), tsCdzy.getMc(), tsCdzy.getCdlj() == "" ? "":tsCdzy.getCdlj(), "0", tsCdzy.getCdssflId(), "");
//		            tree.addNode(treeNode);
//	        	}
//	        }
////	        System.out.println("\r<br>执行耗时22222 : "+(System.currentTimeMillis()-a)/1000f+" 秒 ");
//	        //获取自定义校情分析
//	       List<TbPorZdyxqym> xqfxList=baseDao.queryEntityList("TbPorZdyxqym", "");
//	       if(xqfxList !=null && xqfxList.size()>0){
//	    	   for(int i=0;i<xqfxList.size();i++){
//	    		   TbPorZdyxqym tbPorZdyxqym = xqfxList.get(i);
//	        		treeNode =  new MenuNode(tbPorZdyxqym.getId(), tbPorZdyxqym.getCdsid(), tbPorZdyxqym.getPagetitle(), tbPorZdyxqym.getCdlj() == "" ? "":tbPorZdyxqym.getCdlj(), "0", 4L, "");
//		            tree.addNode(treeNode);
//	        	}
//	       }
//	       System.out.println("\r<br>执行耗时33333 : "+(System.currentTimeMillis()-a)/1000f+" 秒 ");
//	       //获取组权限-下放菜单资源
//	       List<TsCdzy> groupXfCdzyList = menuDao.getGroupDecentralizationCdzy(userInfo);
//	       if(groupXfCdzyList.size()>0){
//	    	   for(int i=0;i<groupXfCdzyList.size();i++){
//	        		TsCdzy tsCdzy = groupXfCdzyList.get(i);
//	        		Long bm = Long.valueOf(baseDao.queryBmById(tsCdzy.getCdssflId(),"XXDM-LZCDFL"));
//		            if(bm.equals(3L)){
//		            	List<String> permissList = getButtons(userInfo.getId(), tsCdzy.getId());
//						treeNode = new MenuNode(tsCdzy.getId(), tsCdzy.getFjdId(), tsCdzy.getMc(), tsCdzy.getCdlj() == "" ? "":tsCdzy.getCdlj(), "0", bm, permissList == null ? "" : "{buttons:"+permissList.toString()+"}");
//			            tree.addNode(treeNode);
//		            }else if(!bm.equals(4L) && !bm.equals(5L)){
//		            	treeNode = new MenuNode(tsCdzy.getId(), tsCdzy.getFjdId(), tsCdzy.getMc(), "", "1", bm, "");
//		            	tree.addNode(treeNode);
//		            }
//	        	}
//	       }
//	       
//	        return tree;
//		}else{
//			return null; 
//		}
//	};
//	@Override
//	public MenuTree queryPermissCdzy(String param) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}
	public MenuTree queryPermissCdzy(String param) throws Exception {
		long a=System.currentTimeMillis();
		List list=null;
		
		//存储树结构
		Map<Long,MenuNode> nodeHash = new HashMap<Long,MenuNode>();
		
		
		
		// 获取用户信息
		UserInfo userInfo = UserPermissionUtil.getUserInfo();
		if(userInfo != null){
			if(list == null){
				//获取当前用户的菜单资源
				list =  menuService.getAllMenuListByUserId(userInfo.getId(),userInfo.getGroupPermiss());
			}
//			System.out.println("\r<br>执行耗时0000000000 : "+(System.currentTimeMillis()-a)/1000f+" 秒 ");
			MenuTree tree = new MenuTree();
	        MenuNode root = new MenuNode(new Long(0), new Long(-1), "", "");
//	        nodeHash.put(root.getId(), root);
	        putNode(nodeHash,root);
	        tree.setRoot(root);
	        
	        MenuNode treeNode = null;
	        for (int i = 0; i < list.size(); i++) {
	            TsCdzy tsCdzy = (TsCdzy) list.get(i);
	            Object bmI = baseDao.queryBmById(tsCdzy.getCdssflId(),"XXDM-LZCDFL");
	            Long bm = null;
	            if("".equals(bmI)){
	               continue;
	            }else{
	               bm = TypeConvert.getLong(bmI);
	            }
	            if(bm.equals(3L)){
	            	List<String> permissList = getButtons(userInfo.getId(), tsCdzy.getId());
					treeNode = new MenuNode(tsCdzy.getId(), tsCdzy.getFjdId(), tsCdzy.getMc(), tsCdzy.getCdlj() == "" ? "":tsCdzy.getCdlj(), "0", bm, permissList == null ? "" : "{buttons:"+permissList.toString()+"}");
//		            tree.addNode(treeNode);
//					nodeHash.put(treeNode.getId(), treeNode);//将生成的节点放置进入hash表中
					putNode(nodeHash,treeNode);
	            }else if(!bm.equals(4L) && !bm.equals(5L)){
	            	treeNode = new MenuNode(tsCdzy.getId(), tsCdzy.getFjdId(), tsCdzy.getMc(), "", "1", bm, "");
//	            	tree.addNode(treeNode);
//	            	nodeHash.put(treeNode.getId(), treeNode);//将生成的节点放置进入hash表中
	            	putNode(nodeHash,treeNode);
	            }
	            treeNode.setPxh(tsCdzy.getPxh());
	        }
//	        System.out.println("\r<br>执行耗时TTTTTTTTTT : "+(System.currentTimeMillis()-a)/1000f+" 秒 ");
	      //在最好的一行加上:
	        //增加输出自定义菜单
	        //获取当前用户自定义的私有的菜单
	        List<TsCdzy> syList = menuDao.getSyCdzy(userInfo.getId());
	        if(syList != null && syList.size()>0){
	        	for(int i=0;i<syList.size();i++){
	        		TsCdzy tsCdzy = syList.get(i);
	        		treeNode =  new MenuNode(tsCdzy.getId(), tsCdzy.getFjdId(), tsCdzy.getMc(), tsCdzy.getCdlj() == "" ? "":tsCdzy.getCdlj(), "0", tsCdzy.getCdssflId(), "");
		            treeNode.setPxh(tsCdzy.getPxh());
	        		putNode(nodeHash,treeNode);
	        	}
	        }
//	        System.out.println("\r<br>执行耗时111111111 : "+(System.currentTimeMillis()-a)/1000f+" 秒 ");
	        //获取当前公共的自定义菜单，且审核通过
	        List<TsCdzy> gyList = menuDao.getGyCdzy();
	        if(gyList != null && gyList.size()>0){
	        	for(int i=0;i<gyList.size();i++){
	        		TsCdzy tsCdzy = gyList.get(i);
	        		treeNode =  new MenuNode(tsCdzy.getId(), tsCdzy.getFjdId(), tsCdzy.getMc(), tsCdzy.getCdlj() == "" ? "":tsCdzy.getCdlj(), "0", tsCdzy.getCdssflId(), "");
	        		treeNode.setPxh(tsCdzy.getPxh());
	        		putNode(nodeHash,treeNode);
	        	}
	        }
//	        System.out.println("\r<br>执行耗时22222 : "+(System.currentTimeMillis()-a)/1000f+" 秒 ");
	        //获取自定义校情分析
//	       List<TbPorZdyxqym> xqfxList=baseDao.queryEntityList("TbPorZdyxqym", "");
//	       if(xqfxList !=null && xqfxList.size()>0){
//	    	   for(int i=0;i<xqfxList.size();i++){
//	    		   TbPorZdyxqym tbPorZdyxqym = xqfxList.get(i);
//	        		treeNode =  new MenuNode(tbPorZdyxqym.getId(), tbPorZdyxqym.getCdsid(), tbPorZdyxqym.getPagetitle(), tbPorZdyxqym.getCdlj() == "" ? "":tbPorZdyxqym.getCdlj(), "0", 4L, "");
//	        		treeNode.setPxh(null);
//	        		putNode(nodeHash,treeNode);
//	        	}
//	       }
//	       System.out.println("\r<br>执行耗时33333 : "+(System.currentTimeMillis()-a)/1000f+" 秒 ");
	       //获取组权限-下放菜单资源
	       List<TsCdzy> groupXfCdzyList = menuDao.getGroupDecentralizationCdzy(userInfo);
	       if(groupXfCdzyList.size()>0){
	    	   for(int i=0;i<groupXfCdzyList.size();i++){
	        		TsCdzy tsCdzy = groupXfCdzyList.get(i);
	        		Long bm = Long.valueOf(baseDao.queryBmById(tsCdzy.getCdssflId(),"XXDM-LZCDFL"));
		            if(bm.equals(3L)){
		            	List<String> permissList = getButtons(userInfo.getId(), tsCdzy.getId());
						treeNode = new MenuNode(tsCdzy.getId(), tsCdzy.getFjdId(), tsCdzy.getMc(), tsCdzy.getCdlj() == "" ? "":tsCdzy.getCdlj(), "0", bm, permissList == null ? "" : "{buttons:"+permissList.toString()+"}");
			            treeNode.setPxh(tsCdzy.getPxh());
			            
						putNode(nodeHash,treeNode);
		            }else if(!bm.equals(4L) && !bm.equals(5L)){
		            	treeNode = new MenuNode(tsCdzy.getId(), tsCdzy.getFjdId(), tsCdzy.getMc(), "", "1", bm, "");
		            	treeNode.setPxh(tsCdzy.getPxh());
		            	
		            	putNode(nodeHash,treeNode);
		            }
	        	}
	       }
	       generateTree(nodeHash);
	       sort(nodeHash);
	       return tree;
		}else{
		   return null; 
		}
	};
	public void sort(Map<Long,MenuNode> nodeHash){
		Set<Entry<Long, MenuNode>> set = nodeHash.entrySet();
		for(Entry<Long, MenuNode> entry : set){
			MenuNode menuNode = entry.getValue();
			List<MenuNode> list = menuNode.getChildren();
			Collections.sort(list, new Comparator<MenuNode>() {

				@Override
				public int compare(MenuNode o1, MenuNode o2) {
					// TODO Auto-generated method stub
					if(o1.getPxh() != null && o2.getPxh() != null){
						return o1.getPxh()> o2.getPxh()? 1 : 0;
					}
					if(o1.getPxh() == null){
					   return o2.getPxh() == null ? 1 : 0;
					}else{
					   return 1;
					}
				}
			});
		}
	}
	public void generateTree(Map<Long,MenuNode> nodeHash){
		Set<Entry<Long, MenuNode>> set = nodeHash.entrySet();
		for(Entry<Long, MenuNode> entry : set){
			if(entry.getValue().getPid() == null){
				entry.getValue().setPid(0l);
			}
//			if(entry.getValue().getPid() == 0l){
//				System.out.println(entry.getValue().getText());
//			}
			MenuNode parent = nodeHash.get(entry.getValue().getPid());
			if(parent != null){
				parent.getChildren().add(entry.getValue());
			}
		}
	}
	public void putNode(Map<Long,MenuNode> nodeHash,MenuNode node){
		if(null==node.getPid()){
			node.setPid(new Long(0));
		}
//		if(nodeHash.get(node.getId())!=null){
//		   System.out.println("debug");
//		   node.getId();
//		}
		if(!(nodeHash.get(node.getId())!=null && nodeHash.get(node.getId()).getPid() == 0l)){
			nodeHash.put(node.getId(), node);
		}
	}
	/**
	 * 获取当前节点下有权限的按钮
	 * @param userId
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private List<String> getButtons(Long userId,Long id) throws Exception{
		//定义list对象
//		long a=System.currentTimeMillis();
		List<String> buttonsPer = new ArrayList<String>();
		//定义菜单资源List
		List<TsCdzy> buttons =null;
		//查询当前用户的button信息
		Cache cache = CacheManager.getCacheInfo(userId.toString());
		if(cache !=null){
			Cache caches =  (Cache) cache.getValue();
			Map map = (Map) caches.getValue();
			//判断当前用户的buttonList是否存在
			if(map.containsKey("buttonList")){
				buttons = (List<TsCdzy>) map.get("buttonList");
			}
		}
		if(buttons == null){
			buttons = menuService.getMenuListByUserIdAndMenuId(userId);
			cacheData(userId.toString(), "buttonList", buttons);
		}
		if(buttons.size()>0){
			for(TsCdzy tsCdzy:buttons){
				if(!tsCdzy.getFjdId().equals(id)){
					continue;
				}
				String buttonBm = baseDao.queryBmById(tsCdzy.getAnlxId(),"XXDM-ANLX");
				if(!"".equals(buttonBm))
					buttonsPer.add("{\"buttonId\":\""+tsCdzy.getId()+"\",\"type\":\""+buttonBm+"\",\"disable\":false}");
			}
		}
		//在最好的一行加上:
//        System.out.println("\r<br>执行耗时11111111 : "+(System.currentTimeMillis()-a)/1000f+" 秒 ");
		return buttonsPer;
	}
	
	/**
	 * 检查子节点
	 * @param treeNode
	 * @param id
	 * @return
	 */
	@Override
	public MenuNode checkChildNode(MenuNode treeNode,Long id){
		if(treeNode != null){
			List<MenuNode> treeNodeList = treeNode.children;
			if(treeNodeList.size()>0){
				for(int i = 0;i<treeNodeList.size();i++){
					MenuNode childNode = treeNodeList.get(i);
					if(childNode != null){
						Long childId = childNode.getId();
						if(childId == id){
							return childNode;
						}else{
							checkChildNode(childNode, id);
						}
					}
				}
			}
		}
		return null;
	}
	
	@Override
	public Map<String,MenuTree> queryDataPermiss(String userId){
		Map<String,MenuTree> permissMap = new HashMap<String,MenuTree>();
		List jxzzjgTreeList = null;
		List xzzzjgTreeList = null;
		//获取当前用户的数据权限(TsUserDataPermiss)
		List<TsUserDataPermiss> dataPermissList = userDao.getUserPermiss(Long.valueOf(userId));
		StringBuffer xzzzjgPermissIds = new StringBuffer();
		StringBuffer jxzzjgPermissIds = new StringBuffer();
		if(dataPermissList != null && dataPermissList.size()>0){
			for(TsUserDataPermiss userDataPermiss :dataPermissList){
				if(userDataPermiss.getZzjglb() == 1){
					jxzzjgPermissIds.append(userDataPermiss.getZzjgId()+",");
				}else{
					xzzzjgPermissIds.append(userDataPermiss.getZzjgId()+",");
				}
			}
			MenuTree zzjgTree = new MenuTree();
			MenuNode root = new MenuNode(0L,null,true,"",false);
			zzjgTree.setRoot(root);
			permissMap.put("jxzzjgTree",null);
			permissMap.put("xzzzjgTree",null);
			if(jxzzjgPermissIds.length()>0){
				jxzzjgPermissIds.toString().substring(0, jxzzjgPermissIds.length()-1);
				//查询教学组织机构数据权限树
				jxzzjgTreeList = userDao.getUserDataPermiss(Long.valueOf(userId), jxzzjgPermissIds.toString().substring(0, jxzzjgPermissIds.length()-1));
				MenuTree jxzzjgTree = getTreeJson(jxzzjgTreeList,dataPermissList);
				permissMap.put("jxzzjgTree",jxzzjgTree);
				zzjgTree.addNode(jxzzjgTree.root);
			}
			if(xzzzjgPermissIds.length()>0){
				xzzzjgPermissIds.toString().substring(0, xzzzjgPermissIds.length()-1);
				//查询行政组织机构数据权限树
				xzzzjgTreeList = userDao.getUserDataPermiss(Long.valueOf(userId), xzzzjgPermissIds.toString().substring(0, xzzzjgPermissIds.length()-1));
				MenuTree xzzzjgTree = getTreeJson(xzzzjgTreeList,dataPermissList);
				permissMap.put("xzzzjgTree",xzzzjgTree);
				zzjgTree.addNode(xzzzjgTree.root);
			}
			permissMap.put("zzjgTree",zzjgTree);
		}
		return permissMap;
	}
	
	private MenuTree getTreeJson(List treeList,List<TsUserDataPermiss> userPermissList){
		MenuTree tree = new MenuTree();
		MenuNode root = new MenuNode(0L,new Long(-1),true,"",false);
		tree.setRoot(root);
		if(treeList != null && treeList.size()>0){
			for(int i = 0 ;i<treeList.size();i++){
				JSONArray array = JSONArray.fromObject(treeList.get(i));
				Long pid = 0L;
				for(TsUserDataPermiss userPermissObj:userPermissList){
					if(userPermissObj.getZzjgId() == array.getLong(1)){
						pid = array.getLong(1);
					}
				}
				MenuNode node = new MenuNode(array.getLong(0), pid, true,array.get(2).toString(), (array.getLong(3) == 0 ? false:true),array.get(4).toString().equals("null") ? 0L:array.getLong(4));
				tree.addNode(node);
			}
		}
		return tree;
	}
	/**
	 * 缓存数据
	 * @param userId
	 * @param key
	 * @param obj
	 */
	private void cacheData(String userId,String key,Object obj){
		Map map =null;
		//实例化缓存对象
//		Cache cache = new Cache();
		Cache cache =CacheManager.getCacheInfo(userId);
		//缓存数据
		if(cache == null ){
			//判断当前缓存数据是否存在
			map = new HashMap<String, Object>();
			map.put(key, obj);
			cache = new Cache();
			//将map放到缓存对象中
			cache.setValue(map);
			//再将cache放到缓存管理中
			CacheManager.putCacheInfo(userId, cache,1200);//缓存创建1小时后，自动重新缓存。
		}else{
			cache = CacheManager.getCacheInfo(userId);
			Cache caches = (Cache) cache.getValue();
			map = (Map) caches.getValue();//获取当前缓存对象转化为map
			if(!map.containsKey(key)){ //如果在map中不能够找到指定的key，就当前的key和obj放到map中
				map.put(key, obj);
			}
		}
		//超过一个小时自动清空指定键的缓存
		if(CacheManager.cacheExpired(cache) == true){
			CacheManager.clearOnly(userId);
		}
	}

	
}
