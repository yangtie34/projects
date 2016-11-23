package com.jhkj.mosdc.permiss.domain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.nobject.common.bean.BeanUtils;

import com.jhkj.mosdc.framework.po.TbJxzzjg;
import com.jhkj.mosdc.permiss.po.TpUsergroupUserdatapermissLd;
import com.jhkj.mosdc.permiss.util.UserPermiss;
import com.jhkj.mosdc.xxzy.po.TbXxzyBjxxb;
import com.jhkj.mosdc.xxzy.po.TbXxzyJxzzjg;

/**
 * 教学组织结构数据权限
 * @author Administrator
 *
 */
public class JxzzjgDataTreePermission {
	
	private Base base;
	
	public TeachingOrganizationalStructureNode tosn = null;
	public String ids = null;
	
	public void initJxzzjgTree(){
		List<TbJxzzjg> zzjglist = getJxzzjg();
		List<TbXxzyBjxxb> bjlist = getBjxx();
		Map<Long,TeachingOrganizationalStructureNode> hash = TeachingOrganizationalStructureNode.translateNodeHashForTbJxzzjgAndTbXxzyBjxxb(zzjglist, bjlist);
		TeachingOrganizationalStructureNode.generateNodeTree(hash);//生成树
		TeachingOrganizationalStructureNode.clearNodeNotHash(hash);//把无班级的所有节点移除，并生成教学组织结构树
		tosn = TeachingOrganizationalStructureNode.getRoot(hash);
		List<Long> list = org.nobject.common.lang.ListUtils.extract(zzjglist, "id");
		list.addAll(org.nobject.common.lang.ListUtils.extract(bjlist, "id"));
		ids = StringUtils.join(list, ",");
	}
	public void initJxzzjgTreeWithOutBj(){
		List<TbJxzzjg> zzjglist = getJxzzjg();
		List<TbXxzyBjxxb> bjlist = getBjxx();
		Map<Long,TeachingOrganizationalStructureNode> hash = TeachingOrganizationalStructureNode.translateNodeHashForTbJxzzjgAndTbXxzyBjxxb(zzjglist, bjlist);
		TeachingOrganizationalStructureNode.generateNodeTree(hash);//生成树
		TeachingOrganizationalStructureNode.clearNodeNotHashAndBj(hash);//把无班级的所有节点移除,并移除班级，并生成教学组织结构树
		tosn = TeachingOrganizationalStructureNode.getRoot(hash);
		
		List<TeachingOrganizationalStructureNode> tlist = new ArrayList<TeachingOrganizationalStructureNode>();
		Set<Entry<Long, TeachingOrganizationalStructureNode>> set = hash.entrySet();
		for(Entry<Long, TeachingOrganizationalStructureNode> e : set){
			tlist.add(e.getValue());
		}
		 
		List<Long> list = org.nobject.common.lang.ListUtils.extract(tlist, "id");
		ids = StringUtils.join(list, ",");
	}
	public List<TbJxzzjg> getJxzzjg(){
		TbJxzzjg txj = new TbJxzzjg();
		txj.setSfky("1");
		return (List<TbJxzzjg>) base.getEntityList(txj);
	}
	@SuppressWarnings("unchecked")
	public List<TbXxzyBjxxb> getBjxx(){
		String rsql = UserPermiss.getUser().getCurrentSqlDataPermiss();
		if(!StringUtils.isBlank(rsql)){
			String bjxxSql = "select * from TB_XXZY_BJXXB txb where txb.id in("+rsql+")";
			return base.getEntityListBySql(TbXxzyBjxxb.class, bjxxSql);
		}else{
			return new ArrayList<TbXxzyBjxxb>();
		}
		
	}
	@SuppressWarnings("unchecked")
	public List<Long> getJxzzjgPermissIds(){
//		String sql = "select jxzzjg_id from TP_USERGROUP_UDPERMISS_LD";
		TpUsergroupUserdatapermissLd tul = new TpUsergroupUserdatapermissLd();
		tul.setUserId(UserPermiss.getUser().getUserId());
		List<TpUsergroupUserdatapermissLd> list = (List<TpUsergroupUserdatapermissLd>) base.getEntityList(tul);
		List<Long> ids = new ArrayList<Long>();
		for(TpUsergroupUserdatapermissLd t : list){
			ids.add(t.getJxzzjgId());
		}
		return ids;
	}
	public void initJxzzjgPermissIds(){
		List<TbJxzzjg> zzjglist = getJxzzjg();
		List<Long> list = getJxzzjgPermissIds();//获取当前的教学组织结构权限IDs
		Map<Long,TeachingOrganizationalStructureNode> hash = TeachingOrganizationalStructureNode.translateNodeHashForTbJxzzjg(zzjglist);
		Map<Long,TeachingOrganizationalStructureNode> hashbak = TeachingOrganizationalStructureNode.translateNodeHashForTbJxzzjg(zzjglist);
		TeachingOrganizationalStructureNode.generateNodeTree(hashbak);
//		TeachingOrganizationalStructureNode root = TeachingOrganizationalStructureNode.getRoot(hashbak);
		
		
		
		Map<Long,TeachingOrganizationalStructureNode> retHash = new HashMap<Long,TeachingOrganizationalStructureNode>();
		List<TeachingOrganizationalStructureNode> retList = new ArrayList<TeachingOrganizationalStructureNode>();
		
		for(Long l : list){
			//首先把其父祖节点取出
			TeachingOrganizationalStructureNode ton = hash.get(l);
			TeachingOrganizationalStructureNode pton = hash.get(ton.getPid());
			while(pton!=null){
				retHash.put(pton.getId(),pton);
				
				pton = hash.get(pton.getPid());
			}
			//然后将其子孙节点取出添加到列表中
			retHash.put(l,hashbak.get(l));
		}
		TeachingOrganizationalStructureNode.generateNodeTree(retHash);//生成树
		TeachingOrganizationalStructureNode root = TeachingOrganizationalStructureNode.getRoot(retHash);//获取根节点
		retList = TeachingOrganizationalStructureNode.toList((TeachingOrganizationalStructureNode) deepCopy(root));//获取节点集合
		tosn = TeachingOrganizationalStructureNode.getRoot(retHash);
		ids = StringUtils.join(org.nobject.common.lang.ListUtils.extract(retList, "id"),",");
	}
	 public Object deepCopy(Object src){   
		 	Object dest = null;
	        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();   
	        ObjectOutputStream out;
			try {
				out = new ObjectOutputStream(byteOut);
				out.writeObject(src);   
			       
		        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());   
		        ObjectInputStream in =new ObjectInputStream(byteIn);   
		        dest = (Object)in.readObject();   
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				dest = null;
			}   
	        return dest;   
	}   
	/**
	 * 获取教学组织结构权限
	 * @return
	 */
	public TeachingOrganizationalStructureNode getJxzzjgDataTree(){
		this.initJxzzjgTree();
		return tosn;
	}
	/**
	 * 获取教学组织结构权限树，without 班级
	 */
	public TeachingOrganizationalStructureNode getJxzzjgDataTreeWidthOutBj(){
		this.initJxzzjgPermissIds();
		return tosn;
	}
	/**
	 * 获取教学组织结构IDS
	 * @return
	 */
	public String getJxzzjgIds(){
		this.initJxzzjgPermissIds();
		return ids;
	}
	public Base getBase() {
		return base;
	}
	public void setBase(Base base) {
		this.base = base;
	}
	
}
