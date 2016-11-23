package com.jhkj.mosdc.permission.po;

import com.jhkj.mosdc.output.po.Node;


/**
 * @Comments: 菜单资源树
 * Company: 河南精华科技有限公司
 * Created by gaodongjie(gaodongjie@126.com) 
 * @DATE:2012-6-2
 * @TIME: 下午8:27:59
 */
public class MenuTree
{
//根节点
public MenuNode root;
//根据ID获取到的MenuNode对象
private static MenuNode anode;
//public Node root = new Node(new Long(0),new Long(-1),"根节点");

public void addNode(MenuNode node)
{
  eqNode(root,node);
}
private void eqNode(MenuNode fN,MenuNode node)
{
	if(null==node.getPid()){
		node.setPid(new Long(0));
	}
  if(fN.getId().equals(node.getPid()))
  {
   fN.children.add(node);
  }
  else
  {
   for(MenuNode n :fN.children)
   {
     eqNode(n,node);
   }
  }
}


public void display(MenuNode node,String s)
{
  System.out.println(s+node.getText());
  s+="|------";
  if(node.children.size()>0)
  {
   for(MenuNode n: node.children)
   {
    display(n,s);
   }
  }
  
}

//-----------------------------------------------------2013-05-18-------------------------------------------
public static MenuNode getNodeById(MenuNode node,Long id){
		if(id.longValue()==node.getId().longValue()){
			anode = node;
			return anode;
		}else{
			for(MenuNode cNode : node.getChildren()){
				getNodeById(cNode,id);
			}
		}
		return anode;
	}
public MenuNode getRoot() {
	return root;
}
public void setRoot(MenuNode root) {
	this.root = root;
}
}

