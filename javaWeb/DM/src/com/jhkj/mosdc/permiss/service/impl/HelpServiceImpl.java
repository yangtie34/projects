package com.jhkj.mosdc.permiss.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.po.TbJxzzjg;
import com.jhkj.mosdc.framework.po.TbXzzzjg;
import com.jhkj.mosdc.framework.service.BaseService;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.permiss.domain.Node;
import com.jhkj.mosdc.permiss.service.HelpService;
import com.jhkj.mosdc.permiss.util.JSONUtil;
import com.jhkj.mosdc.xxzy.po.TbXxzyBjxxb;
import com.jhkj.mosdc.xxzy.po.TbXxzyJxzzjg;

public class HelpServiceImpl implements HelpService {
	private BaseDao baseDao;
	private BaseService baseService;

	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	/**
	 * 查询教学组织结构，并把班级数据绑定到教学组织结构树上
	 */
	@Override
	public String queryJxzzjg(String params) {
		// TODO Auto-generated method stub
		List<Node> zzjgNList = this.translateZzjgToNode(this.queryJxzzjgList());
		List<Node> bjList = this.translateBjToNode(this.queryBjList());
		zzjgNList.addAll(bjList);
		return Struts2Utils.list2json(zzjgNList);
	}
	/**
	 * 查询教学组织结构，排除班级
	 */
	public String queryJxzzjgWithOutBj(String params){
		List<Node> zzjgNList = this.translateZzjgToNode(this.queryJxzzjgList());
		return Struts2Utils.list2json(zzjgNList);
	}
	/**
	 * 查询用户组数据权限，并把数据权限绑定到教学组织结构上
	 * @param zzjgNList
	 * @param bjList
	 * @return
	 */
	public String queryJxzzjgWithGrouperDataPermiss(String params){
		Long usergroupId = JSONUtil.getLong(JSONObject.fromObject(params), "usergroupId");
		List<Node> zzjgNList = this.translateZzjgToNode(this.queryJxzzjgList());
		List<Node> bjList = this.translateBjToNode(this.queryGroupDataPermissBj(usergroupId));
//		zzjgNList.addAll(bjList);
//		return Struts2Utils.list2json(zzjgNList);
//		Map<Long,Node> hash = getNodeHash(zzjgNList, bjList);
//		Node.generateNodeTree(hash);
//		List<Node> ret = new ArrayList<Node>();
//		
//		Set<Entry<Long, Node>> set = hash.entrySet();
//		for(Entry<Long, Node> e : set){
//			Node d = e.getValue();
//			if(!(d.getChildren().size() == 0 && !d.getCclx().equals(1))){
//				ret.add(d);
//				d.setChildren(new ArrayList());
//			}
//		}
		return Struts2Utils.list2json(getGrouperNodes(zzjgNList,bjList));
	}
	/**
	 * 查询行政组织结构
	 */
	public String queryXzzzjg(String params){
		TbXzzzjg txj = new TbXzzzjg();
		txj.setSfky("1");
		List<TbXzzzjg> list = baseDao.loadEqual(txj);
		return Struts2Utils.list2json(this.translateXzZzjgToNode(list));
	}
	public List<Node> getGrouperNodes(List<Node> zzjgNList,List<Node> bjList){
		List<Node> olist = new ArrayList<Node>();
		Set<Node> set = new HashSet<Node>();
		Map<Long,Node>hash = new HashMap<Long,Node>();
		for(Node n : zzjgNList){
			hash.put(n.getId(), n);
		}
		for(Node n : bjList){
			set.add(n);//首先将班级加入集合中
			hash.put(n.getId(), n);
			while(hash.get(n.getPid())!= null){
				set.add(hash.get(n.getPid()));
				n = hash.get(n.getPid());
			}
		}
		olist.addAll(set);
		return olist;
	}
	public List<Node> translateZzjgToNode(List<TbJxzzjg> list){
		List<Node> nlist = new ArrayList<Node>();
		for(TbJxzzjg j : list){
			Node n = new Node();
			if(j.getCclx().equals("ZY")){
			   n.setCclx(2);//这里转置只有3种类型，一种是班级1，一种是专业2，一种是其他3
			}else{
			   n.setCclx(3);
			}
			n.setText(j.getMc());
			n.setId(j.getId());
			n.setPid(j.getFjdId());
			n.setChildren(new ArrayList<Node>());
			nlist.add(n);
		}
		return nlist;
	}
	public List<Node> translateBjToNode(List<TbXxzyBjxxb> list){
		List<Node> nlist = new ArrayList<Node>();
		for(TbXxzyBjxxb b : list){
			Node n = new Node();
			n.setCclx(1);//这里转置只有2种类型，一种是班级1，一种是2其他
			n.setText(b.getMc());
			n.setId(b.getId());
			n.setPid(b.getFjdId());
			nlist.add(n);
		}
		return nlist;
	}
	public List<Node> translateXzZzjgToNode(List<TbXzzzjg> list){
		List<Node> nlist = new ArrayList<Node>();
		for(TbXzzzjg j : list){
			Node n = new Node();
			n.setText(j.getMc());
			n.setId(j.getId());
			n.setPid(j.getFjdId());
			n.setChildren(new ArrayList<Node>());
			nlist.add(n);
		}
		return nlist;
	}
	
	public List<TbJxzzjg> queryJxzzjgList(){
		TbJxzzjg txj = new TbJxzzjg();
		txj.setSfky("1");
		return baseDao.loadEqual(txj);
	}
	public List<TbXxzyBjxxb> queryBjList(){
		TbXxzyBjxxb bj = new TbXxzyBjxxb();
		bj.setSfky(1l);
		return baseDao.loadEqual(bj);
	}
	public List<TbXxzyBjxxb> queryGroupDataPermissBj(Long usergroupId){
		String sql = "select txb.* from TP_USERGROUP_DATAPERMISS tuu "
				+ "inner join tb_xxzy_bjxxb txb on tuu.bj_id = txb.id and tuu.usergroup_id="+usergroupId;
		return baseDao.querySqlEntityList(sql, TbXxzyBjxxb.class);
	}

}
