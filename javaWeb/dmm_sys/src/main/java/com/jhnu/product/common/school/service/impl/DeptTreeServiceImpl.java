package com.jhnu.product.common.school.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.jhnu.framework.entity.NodeAngularTree;
import com.jhnu.framework.entity.NodeTree;
import com.jhnu.framework.entity.NodeZtree;
import com.jhnu.product.common.school.entity.Dept;
import com.jhnu.product.common.school.entity.DeptTeach;
import com.jhnu.product.common.school.service.DeptPermissionService;
import com.jhnu.product.common.school.service.DeptTreeService;
@Service("deptTreeService")
public class DeptTreeServiceImpl implements DeptTreeService {
	
	@Autowired
	private DeptPermissionService dataPermissionService;

	@Override
	public Object getDeptPerms() {
		List<Dept> deptList=dataPermissionService.getAllDept();
		Map<String,Dept> deptMap=new HashMap<String,Dept>();
		Dept root=null;
		for(Dept dept:deptList){
			deptMap.put(dept.getId(), dept);
		}
		for(Dept dept:deptList){
			if(dept.getLevel_().intValue()==0){
				root=dept;
			}else{
				if(deptMap.get(dept.getPid())!=null)
				deptMap.get(dept.getPid()).getChildren().add(dept);
			}
			
		}
		if(root!=null){
			return root;
		}
		return null;
	}
	@Override
	public Object getDeptAglTree() {
		List<NodeAngularTree> deptList=dataPermissionService.getAllDeptPermsAgl();
		Map map0=new HashMap();
		for(NodeAngularTree tree:deptList){
			map0.put(tree.getId(), null);
		}
		Map<String,NodeAngularTree> deptMap=new HashMap<String,NodeAngularTree>();
		NodeAngularTree root=null;
		for(NodeAngularTree tree:deptList){
			deptMap.put(tree.getId(), tree);
		}
		for(NodeAngularTree tree:deptList){
			if(map0.containsKey(tree.getId())){
				tree.setChecked(false);
			}
			if("-1".equals(tree.getPid())){
				root=tree;
			}else{
				if(deptMap.get(tree.getPid())!=null)
				deptMap.get(tree.getPid()).getChildren().add(tree);
			}
			
		}
		if(root!=null){
			return root;
		}
		return null;
	}
	@Override
	public List<NodeTree> getDeptJL() {
		List<NodeTree> deptList=dataPermissionService.getAllDeptPermsJL();
		return deptList;
	}
	@Override
	public Object getDeptTeach(String shiroTag) {
		return dataPermissionService.getDeptTeachByShiroTag(shiroTag);
	}
	@Override
	public Object getDept(String shiroTag) {
		return dataPermissionService.getDeptByShiroTag(shiroTag);
	}
	@Override
	public Object getDeptTeachAglTree() {
		List<NodeAngularTree> deptTeachList=dataPermissionService.getAllDeptTeachAgl();
		Map map0=new HashMap();
		for(NodeAngularTree tree:deptTeachList){
			map0.put(tree.getId(), null);
		}
		Map<String,NodeAngularTree> deptMap=new HashMap<String,NodeAngularTree>();
		NodeAngularTree root=null;
		for(NodeAngularTree tree:deptTeachList){
			deptMap.put(tree.getId(), tree);
		}
		for(NodeAngularTree tree:deptTeachList){
			if(map0.containsKey(tree.getId())){
				tree.setChecked(false);
			}
			if("-1".equals(tree.getPid())){
				root=tree;
			}else{
				if(deptMap.get(tree.getPid())!=null)
				deptMap.get(tree.getPid()).getChildren().add(tree);
			}
			
		}
		if(root!=null){
			return root;
		}
		return null;
	}	
	@Override
	public List<NodeTree> getDeptTeachJL() {
		List<NodeTree> deptTeachList=dataPermissionService.getAllDeptTeachJL();
		return deptTeachList;
	}

	@Override
	public String getDeptJson() {
		List<Dept> deptList=dataPermissionService.getAllDept();
		List<NodeZtree> nodes=new ArrayList<NodeZtree>();
		for(Dept dept:deptList){
			NodeZtree node=new NodeZtree();
			if(dept.getPid().equals("-1")){
				node.setOpen(true);
			}
			node.setId(dept.getId());
			node.setLevel(dept.getLevel_().toString());
			node.setpId(dept.getPid());
			node.setName(dept.getName_());
			nodes.add(node);
		}
		return JSON.toJSONString(nodes);
	}

	@Override
	public String getDeptTeachJson() {
		List<DeptTeach> deptTeachList=dataPermissionService.getAllDeptTeach();
		List<NodeZtree> nodes=new ArrayList<NodeZtree>();
		for(DeptTeach deptTeach:deptTeachList){
			NodeZtree node=new NodeZtree();
			if(deptTeach.getPid().equals("-1")){
				node.setOpen(true);
			}
			node.setId(deptTeach.getId());
			node.setLevel(deptTeach.getLevel_().toString());
			node.setpId(deptTeach.getPid());
			node.setName(deptTeach.getName_());
			nodes.add(node);
		}
		return JSON.toJSONString(nodes);
	}

}
