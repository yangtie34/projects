package com.jhnu.product.common.school.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
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
	public Object getDeptTeach() {
		List<DeptTeach> deptTeachList=dataPermissionService.getAllDeptTeach();
		Map<String,DeptTeach> deptMap=new HashMap<String,DeptTeach>();
		DeptTeach root=null;
		for(DeptTeach dept:deptTeachList){
			deptMap.put(dept.getId(), dept);
		}
		for(DeptTeach dept:deptTeachList){
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
	public String getDeptJson() {
		List<Dept> deptList=dataPermissionService.getAllDept();
		List<NodeZtree> nodes=new ArrayList<NodeZtree>();
		for(Dept dept:deptList){
			NodeZtree node=new NodeZtree();
			if(dept.getPid().equals("-1")){
				node.setOpen(true);
			}
			node.setId(dept.getId());
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
			node.setpId(deptTeach.getPid());
			node.setName(deptTeach.getName_());
			nodes.add(node);
		}
		return JSON.toJSONString(nodes);
	}

}
