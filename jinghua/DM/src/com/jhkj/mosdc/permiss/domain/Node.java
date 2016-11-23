package com.jhkj.mosdc.permiss.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.jhkj.mosdc.permiss.po.TpMenu;


public class Node {
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
	private Integer cclx;
	//排序号
	private Integer pxh;
	//功能标识--针对以按钮权限为代表的功能权限
	private String funTag;
	//功能描述--针对按钮权限为代表的功能权限
	private String funDesc;
	/**
	 * 代理用户ID
	 */
	private Long proxyUserId;//代理用户ID
	/**
	 * 代理菜单ID
	 */
	private Long proxyMenuId;//代理菜单ID
	//子节点
	public List<Node> children = new ArrayList<Node>();
	
	public Node() {
		super();
	}

	public Node(Long id, Long pid, Boolean leaf, String text, String url,
			Integer cclx, Integer pxh, String funTag, String funDesc) {
		super();
		this.id = id;
		this.pid = pid;
		this.leaf = leaf;
		this.text = text;
		this.url = url;
		this.cclx = cclx;
		this.pxh = pxh;
		this.funTag = funTag;
		this.funDesc = funDesc;
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
	public Boolean isLeaf() {
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
	public Integer getCclx() {
		return cclx;
	}
	public void setCclx(Integer cclx) {
		this.cclx = cclx;
	}
	public Integer getPxh() {
		return pxh;
	}
	public void setPxh(Integer pxh) {
		this.pxh = pxh;
	}
	public String getFunTag() {
		return funTag;
	}
	public void setFunTag(String funTag) {
		this.funTag = funTag;
	}
	public String getFunDesc() {
		return funDesc;
	}
	public void setFunDesc(String funDesc) {
		this.funDesc = funDesc;
	}
	public List<Node> getChildren() {
		return children;
	}
	public void setChildren(List<Node> children) {
		this.children = children;
	}
	public Boolean getLeaf() {
		return leaf;
	}

	public Long getProxyUserId() {
		return proxyUserId;
	}

	public void setProxyUserId(Long proxyUserId) {
		this.proxyUserId = proxyUserId;
	}

	public Long getProxyMenuId() {
		return proxyMenuId;
	}

	public void setProxyMenuId(Long proxyMenuId) {
		this.proxyMenuId = proxyMenuId;
	}

	/**
	 * 将TpMenu集合转换成Map<Long,Node>,并对其子节点做排序
	 * @param list
	 * @return
	 */
	public static Map<Long,Node> translateNodeHashForTpMenu(List<TpMenu> list){
		Map<Long,Node> nodeHash = new HashMap<Long, Node>();
		
		for(TpMenu tm : list){
			//节点转换
			Node node  = new Node(tm.getId(),tm.getParentId(),tm.getLeaf(),tm.getMc(),tm.getUrl(),tm.getCdssfl(),tm.getSortnum(),tm.getFuntype(),tm.getFundesc());
			nodeHash.put(node.getId(), node);
		}
		return nodeHash;
	}
	/**
	 * 生成节点树
	 * @param nodeHash
	 */
	public static void generateNodeTree(Map<Long,Node> nodeHash){
		Set<Entry<Long, Node>> set = nodeHash.entrySet();
		Iterator<Entry<Long, Node>> iterator = set.iterator();
		while(iterator.hasNext()){
			Entry<Long, Node> entry = iterator.next();
			Node node = entry.getValue();
			if(node == null)continue;
//				System.out.println(entry.getValue());
			Node pnode = nodeHash.get(node.getPid());
			if(pnode != null) pnode.getChildren().add(node);
		}
	}
	/**
	 * 对节点的子节点进行排序
	 * @param nodeHash
	 */
	public static void sort(Map<Long,Node> nodeHash){
		Set<Entry<Long, Node>> set = nodeHash.entrySet();
		Iterator<Entry<Long, Node>> iterator = set.iterator();
		
		while(iterator.hasNext()){
			Entry<Long, Node> entry = iterator.next();
			Node node = entry.getValue();
			Collections.sort(node.getChildren(),new Comparator<Node>() {

				@Override
				public int compare(Node o1, Node o2) {
					// TODO Auto-generated method stub
					if(o1.getPxh() != null && o2.getPxh() != null){
						return o1.getPxh()> o2.getPxh()? 1 : -1;
					}
					if(o1.getPxh() == null){
					   return o2.getPxh() == null ? 1 : -1;
					}else{
					   return 1;
					}
				}
			});
		}
	}

}
