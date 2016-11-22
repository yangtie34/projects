package com.jhkj.mosdc.permiss.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.jhkj.mosdc.framework.po.TbJxzzjg;
import com.jhkj.mosdc.framework.po.TbXzzzjg;
import com.jhkj.mosdc.xxzy.po.TbXxzyBjxxb;

/**
 * 针对教学组织结构以及班级信息表做转置的Node类型
 * @author Administrator
 *
 */
public class  TeachingOrganizationalStructureNode implements Serializable{
	//子id
	private Long id;
	//父id
	private Long pid;
	//是否叶子节点
	private Boolean leaf;
	//描述
	private String text;
	//菜单存储路径
	private String url;
	//所属分类
	private String cclx;
	//排序号
	private Integer pxh;
	/**
	 * 班级个数
	 */
	private int bjnum;
	//子节点
	public List<TeachingOrganizationalStructureNode> children = new ArrayList<TeachingOrganizationalStructureNode>();
	public TeachingOrganizationalStructureNode(Long id, Long pid, Boolean leaf,
			String text, String url, String cclx, Integer pxh,
			List<TeachingOrganizationalStructureNode> children) {
		super();
		this.id = id;
		this.pid = pid;
		this.leaf = leaf;
		this.text = text;
		this.url = url;
		this.cclx = cclx;
		this.pxh = pxh;
		this.children = children;
	}
	public TeachingOrganizationalStructureNode() {
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public Boolean getLeaf() {
		return leaf;
	}
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCclx() {
		return cclx;
	}
	public void setCclx(String cclx) {
		this.cclx = cclx;
	}
	public Integer getPxh() {
		return pxh;
	}
	public void setPxh(Integer pxh) {
		this.pxh = pxh;
	}
	public List<TeachingOrganizationalStructureNode> getChildren() {
		return children;
	}
	public void setChildren(List<TeachingOrganizationalStructureNode> children) {
		this.children = children;
	}
	public int getBjnum() {
		return bjnum;
	}
	public void setBjnum(int bjnum) {
		this.bjnum = bjnum;
	}
	/**
	 * 将TbJxzzjg集合转换成Map<Long,Node>
	 * @param list
	 * @return
	 */
	public static Map<Long,TeachingOrganizationalStructureNode> translateNodeHashForTbJxzzjg(List<TbJxzzjg> list){
		Map<Long,TeachingOrganizationalStructureNode> nodeHash = new TreeMap<Long, TeachingOrganizationalStructureNode>();
		
		for(TbJxzzjg tm : list){
			//节点转换
			TeachingOrganizationalStructureNode node  = new TeachingOrganizationalStructureNode();
			node.setId(tm.getId());
			node.setPid(tm.getFjdId());
			node.setPxh(tm.getPxh());
			node.setCclx(tm.getCclx());
			node.setText(tm.getMc());
			
			nodeHash.put(node.getId(), node);
		}
		return nodeHash;
	}
	/**
	 * 将TbXzzzjg集合转换成Map<Long,Node>
	 * @param list
	 * @return
	 */
	public static Map<Long,TeachingOrganizationalStructureNode> translateNodeHashForTbXzzzjg(List<TbXzzzjg> list){
		Map<Long,TeachingOrganizationalStructureNode> nodeHash = new TreeMap<Long, TeachingOrganizationalStructureNode>();
		
		for(TbXzzzjg tm : list){
			//节点转换
			TeachingOrganizationalStructureNode node  = new TeachingOrganizationalStructureNode();
			node.setId(tm.getId());
			node.setPid(tm.getFjdId());
			node.setPxh(tm.getPxh());
			node.setCclx(tm.getCclx());
			node.setText(tm.getMc());
			
			nodeHash.put(node.getId(), node);
		}
		return nodeHash;
	}
	/**
	 * 将TbJxzzjg集合转换成Map<Long,Node>
	 * @param list
	 * @return
	 */
	public static Map<Long,TeachingOrganizationalStructureNode> translateNodeHashForTbJxzzjgAndTbXxzyBjxxb(List<TbJxzzjg> list,List<TbXxzyBjxxb> bjlist){
		Map<Long,TeachingOrganizationalStructureNode> nodeHash = new TreeMap<Long, TeachingOrganizationalStructureNode>();
		
		for(TbJxzzjg tm : list){
			//节点转换
			TeachingOrganizationalStructureNode node  = new TeachingOrganizationalStructureNode();
			node.setId(tm.getId());
			node.setPid(tm.getFjdId());
			node.setPxh(tm.getPxh());
			node.setCclx(tm.getCclx());
			node.setText(tm.getMc());
			
			nodeHash.put(node.getId(), node);
		}
		
		for(TbXxzyBjxxb bj : bjlist){
			TeachingOrganizationalStructureNode node  = new TeachingOrganizationalStructureNode();
			node.setId(bj.getId());
			node.setPid(bj.getFjdId());
			node.setCclx("BJ");
			node.setText(bj.getMc());
			
			nodeHash.put(node.getId(), node);
		}
		
		return nodeHash;
	}
	/**
	 * 生成节点树
	 * @param nodeHash
	 */
	public static void generateNodeTree(Map<Long,TeachingOrganizationalStructureNode> nodeHash){
		Set<Entry<Long, TeachingOrganizationalStructureNode>> set = nodeHash.entrySet();
		Iterator<Entry<Long, TeachingOrganizationalStructureNode>> iterator = set.iterator();
		while(iterator.hasNext()){
			Entry<Long, TeachingOrganizationalStructureNode> entry = iterator.next();
			TeachingOrganizationalStructureNode node = entry.getValue();
			if(node == null)continue;
//				System.out.println(entry.getValue());
			if(node.getPid() != null){
				TeachingOrganizationalStructureNode pnode = nodeHash.get(node.getPid());
				if(pnode != null) pnode.getChildren().add(node);
			}			
		}
		iterator = set.iterator();
		while(iterator.hasNext()){
			Entry<Long, TeachingOrganizationalStructureNode> entry = iterator.next();
			TeachingOrganizationalStructureNode node = entry.getValue();
			if(node == null)continue;
			if("BJ".equals(node.getCclx())){
				node.setLeaf(true);
				addBjNum(node,nodeHash);
			}
		}
	}
	public static void clearNodeNotHash(Map<Long,TeachingOrganizationalStructureNode> nodeHash){
		List<TeachingOrganizationalStructureNode> rlist = new ArrayList<TeachingOrganizationalStructureNode>();
		Set<Entry<Long, TeachingOrganizationalStructureNode>> set = nodeHash.entrySet();
		for(Entry<Long, TeachingOrganizationalStructureNode> entry : set){
			TeachingOrganizationalStructureNode node = entry.getValue();
			if(node.bjnum == 0 && !node.cclx.equals("BJ")){
				TeachingOrganizationalStructureNode pnode = nodeHash.get(node.getPid());
				if(pnode != null){
					pnode.getChildren().remove(node);
					rlist.add(node);
				}
			}
		}
		for(TeachingOrganizationalStructureNode n : rlist){
			nodeHash.remove(n.getId());
		}
	}
	public static void clearNodeNotHashAndBj(Map<Long,TeachingOrganizationalStructureNode> nodeHash){
		List<TeachingOrganizationalStructureNode> rlist = new ArrayList<TeachingOrganizationalStructureNode>();
		Set<Entry<Long, TeachingOrganizationalStructureNode>> set = nodeHash.entrySet();
		for(Entry<Long, TeachingOrganizationalStructureNode> entry : set){
			TeachingOrganizationalStructureNode node = entry.getValue();
			if(node.bjnum == 0){
				TeachingOrganizationalStructureNode pnode = nodeHash.get(node.getPid());
				if(pnode != null){
					pnode.getChildren().remove(node);
					rlist.add(node);
				}
			}
		}
		set = nodeHash.entrySet();
		for(Entry<Long, TeachingOrganizationalStructureNode> entry : set){
			TeachingOrganizationalStructureNode node = entry.getValue();
			if(node.getChildren().size()==0){
				node.setLeaf(true);
			}
		}
		for(TeachingOrganizationalStructureNode n : rlist){
			nodeHash.remove(n.getId());
		}
	}
	public static void addBjNum(TeachingOrganizationalStructureNode node,Map<Long,TeachingOrganizationalStructureNode> nodeHash){
		TeachingOrganizationalStructureNode pnode = null;
		pnode = nodeHash.get(node.getPid());
		while(pnode != null){
			pnode.bjnum = pnode.bjnum+1;
			pnode = nodeHash.get(pnode.getPid());
		}
	}
	public static TeachingOrganizationalStructureNode getRoot(Map<Long,TeachingOrganizationalStructureNode> nodeHash){
		Set<Entry<Long, TeachingOrganizationalStructureNode>> set = nodeHash.entrySet();
		for(Entry<Long, TeachingOrganizationalStructureNode> entry : set){
			TeachingOrganizationalStructureNode node = entry.getValue();
			if("XX".equals(node.getCclx()))return node;
		}
		return null;
	}
	public static List<TeachingOrganizationalStructureNode> toList(TeachingOrganizationalStructureNode root){
		List ret = new ArrayList();
		if(root == null) return ret;
		List<TeachingOrganizationalStructureNode> list = new ArrayList<TeachingOrganizationalStructureNode>();
		List<TeachingOrganizationalStructureNode> baklist = new ArrayList<TeachingOrganizationalStructureNode>();
		list = root.children;
		root.children = new ArrayList();
		ret.add(root);
		
		while(list.size()!=0){
			for(TeachingOrganizationalStructureNode to : list){
				baklist.addAll(to.children);
				to.children = new ArrayList();
				ret.add(to);
			}
			list = baklist;
			baklist = new ArrayList();
		}
		return ret;
	}
} 
